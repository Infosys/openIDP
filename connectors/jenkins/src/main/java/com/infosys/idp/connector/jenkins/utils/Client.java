/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.idp.connector.jenkins.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import org.infy.idp.entities.jobs.IDPJob;

import org.infy.idp.entities.models.BuildStatus;
import org.infy.idp.entities.triggerparameter.ApproveBuildParams;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.FolderJob;
import com.offbytwo.jenkins.model.JobWithDetails;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class Client {
	private static final Logger logger = LoggerFactory.getLogger(Client.class);
	private static final String JOB_TEMPLATE_PATH = "job.xml";
	private static final String JOB_SHARED_PATH = "SharePath";
	private static final String PIPELINE_SCRIPT_PATH = "pipelineScriptPath";
	private static final String AUTHORIZATION = "Authorization";
	private static final String BASIC = "Basic ";
	private static final String APPR_STEP = "/input/IDPApproval/";
	private static final String JOB_URL = "/job/";
	private static final long POLLPERIOD = 2 * 4000L;

	// Next Generation Pipeline
	public static final String NECESSITY_JOB_NAME = "necessity_demo";
	private static final String ORCH_SERVER = "http://orchserver:8080";
	private static final String ORCH_USERNAME = "uname";
	private static final String ORCH_SECRET = "pwd22233";
	private static final String NG_JOB_TEMPLATE_PATH = "job_ng.xml";
	private static final String JSON_FILE_PATH = ".idp/idp_ng.json";
	private static final String JSON_FILE_PATH_PARAMETER = "JSON_Input";
	private static final String SLAVE_PARAMETER = "agent";


	private static final String ACCEPT = "Accept";

	@Autowired
	private ConfigurationManager configManager;

	@Autowired
	private Cli cli;
	
	private RestTemplate restTemplate;
	private HttpHeaders headers;
	private HttpHeaders approveJobsHeader;
	
	@PostConstruct
	public void initializeTrustCertification() {
		
		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();
		
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.set(ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		String s = configManager.getJenkinsuserid() + ":" + configManager.getJenkinspassword();
		String encoding = Base64.getEncoder().encodeToString(s.getBytes());
		headers.set(AUTHORIZATION, BASIC + encoding);
		
		approveJobsHeader = new HttpHeaders();
		approveJobsHeader.set(ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		approveJobsHeader.set(AUTHORIZATION, BASIC + encoding);
		approveJobsHeader.add("Content-Type", "application/x-www-form-urlencoded");
		
	}

	public boolean createNewJob(IDPJob idpjson) {
		try {
			logger.debug("Creating  job");
			if(idpjson.getBasicInfo().getPipelineName().equalsIgnoreCase(NECESSITY_JOB_NAME)){
				JenkinsServer ng_jenkinsServer = new JenkinsServer(new URI(ORCH_SERVER)
					, ORCH_USERNAME, ORCH_SECRET);
				JtwigTemplate ng_template = JtwigTemplate.classpathTemplate(NG_JOB_TEMPLATE_PATH);
				// String jsonPath="";
				// for(Scm scm : idpjson.getCode().getScm()){
				// 	if(scm.getRepositoryBrowser().equalsIgnoreCase("gitlab")){
				// 		String repoSanity = scm.getUrl().split("\\.git")[0];
				// 		jsonPath = repoSanity + "/raw/" + scm.getBranch() + "/" + JSON_FILE_PATH;
				// 	} else if (scm.getRepositoryBrowser().equalsIgnoreCase("github")){
				// 		String repoSanity = scm.getUrl().split("\\.git")[0];
				// 		jsonPath = repoSanity + "/raw/" + scm.getBranch() + "/" + JSON_FILE_PATH;
				// 	} else {
				// 		jsonPath = scm.getUrl() + "/" + scm.getBranch() + "/" + JSON_FILE_PATH;
				// 	}
				// }
				JtwigModel ng_model = JtwigModel.newModel().with(SLAVE_PARAMETER, "master");

				ng_jenkinsServer.createJob(idpjson.getBasicInfo().getApplicationName() + "_"
					+ idpjson.getBasicInfo().getPipelineName() + "_Pipeline", ng_template.render(ng_model), true);
					
			}
			JenkinsServer jenkinsServer = new JenkinsServer(new URI(configManager.getJenkinsurl())
			, configManager.getJenkinsuserid(), configManager.getJenkinspassword());
			JtwigTemplate template = JtwigTemplate.classpathTemplate(JOB_TEMPLATE_PATH);

			JtwigModel model = JtwigModel.newModel().with(JOB_SHARED_PATH, configManager.getSharePath())
					.with(PIPELINE_SCRIPT_PATH, configManager.getPipelineScriptPath()).with("", "forIDPRearch");

			jenkinsServer.createJob(idpjson.getBasicInfo().getApplicationName() + "_"
					+ idpjson.getBasicInfo().getPipelineName() + "_Main", template.render(model), true);
		} catch (org.apache.http.client.HttpResponseException ex) {
				logger.info("Job already exists. Trying trigger for update");
				return true;
		} catch (Exception ex) {
			logger.error("Some error ocurred while creating  job", ex);
			logger.info(ex.getMessage());
			return false;
		}
		return true;
	}
	
	public void buildByJobName(IDPJob idp) {
		try {
			JenkinsServer jenkins;
			JobWithDetails job ;
			HashMap<String, String> parameters = new HashMap<>();
			StringBuilder jobName = new StringBuilder();
			jobName.append(idp.getBasicInfo().getApplicationName());
			jobName.append("_");
			jobName.append(idp.getBasicInfo().getPipelineName());
			jobName.append("_Main");
			jenkins = new JenkinsServer(new URI(configManager.getJenkinsurl())
				, configManager.getJenkinsuserid(), configManager.getJenkinspassword());
			Gson gson = new Gson();
			String inputData = gson.toJson(idp);
			parameters.put("JSON_Input", inputData);
			job = jenkins.getJob(jobName.toString());
			job.build(parameters);
		} catch (NullPointerException ex) {
			logger.info(ex.getMessage(), ex);
		} catch (MalformedURLException ex) {
			logger.info(ex.getMessage(), ex);
		} catch (ProtocolException ex) {
			logger.info(ex.getMessage(), ex);
		} catch (URISyntaxException ex) {
			logger.info(ex.getMessage(), ex);
		} catch (IOException ex) {
			logger.info(ex.getMessage(), ex);
			throw new IllegalStateException("Backend Server is down", ex);
		} catch(Exception ex) {
			logger.info(ex.getMessage(), ex);
		}
	}

	public void buildByJobName(TriggerParameters idp) {
		try {
			HashMap<String, String> parameters = new HashMap<>();
			JenkinsServer jenkins;
			JobWithDetails job;
			if(idp.getPipelineName().equalsIgnoreCase(NECESSITY_JOB_NAME)){
				jenkins = new JenkinsServer(new URI(ORCH_SERVER)
				, ORCH_USERNAME, ORCH_SECRET);
				parameters.put(SLAVE_PARAMETER, idp.getSlaveName());
				parameters.put("JSON_Input", idp.getPipelineJson());
				job = jenkins.getJob(idp.getApplicationName() + "_" + idp.getPipelineName() + "_Pipeline");
			} else {
				Gson gson = new Gson();

				if (null != idp.getBuild()) {
					idp.getBuild().setBranchSelected("master");
				}
				String inputData = gson.toJson(idp);
				//parameters.put("JSON_Input", inputData);
				
					logger.info("Normal JSON : {}", inputData);
					parameters.put("JSON_Input", inputData);
				
				jenkins = new JenkinsServer(new URI(configManager.getJenkinsurl())
				, configManager.getJenkinsuserid(), configManager.getJenkinspassword());
				FolderJob folder = new FolderJob(idp.getApplicationName() + "_" + idp.getPipelineName(),
				configManager.getJenkinsurl() + "/job/" + idp.getApplicationName() + "_" + idp.getPipelineName() + "/");
				job = jenkins.getJob(folder, idp.getApplicationName() + "_" + idp.getPipelineName() + "_Pipeline");
			}			

			logger.info("Pipeline Job to be triggered Name : " + idp.getApplicationName() + "_" + idp.getPipelineName()
					+ "_Pipeline");
			logger.info("Debug Trigger Parameters:" + parameters);
			job.build(parameters);
		} catch (MalformedURLException ex) {
			logger.info(ex.getMessage(), ex);
		} catch (ProtocolException ex) {
			logger.info(ex.getMessage(), ex);
		} catch (IOException | URISyntaxException e) {
			logger.info(e.getMessage(), e);
			throw new IllegalStateException("Problem Submitting Job to CI");
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
	
	public int disableJob(String jobName,String jobConfigFile) throws InterruptedException, IOException {
		return cli.disableJob(jobName, jobConfigFile);
	}
	
	public int addArtifactoryRepoGlobConf(String repoUrl,String user,String pwd,String repoName) throws IOException, InterruptedException {
		return cli.addArtifactoryRepoGlobConf(repoUrl, user, pwd, repoName);
	}
	
	public int createRole(String roleName, String usersList, String pattern, String roleType, String permissions) throws IOException, InterruptedException {
		return cli.createRole(roleName, usersList, pattern, roleType, permissions);
	}
	
	public int copySlave(String slaveName, String workspace, String slaveLabel, String sshKeyPath) throws IOException, InterruptedException {
		return cli.copySlave(slaveName, workspace, slaveLabel, sshKeyPath);
	}
	
	public String addALMConfig(String inputalmServerName, String almServerUrl) throws IOException, InterruptedException {
		return cli.addALMConfig(inputalmServerName, almServerUrl);
	}
	
	public String getStageViewUrl(String appName, String pipelineName) throws Exception {
		String url = "";
		String jenkinsUrl = configManager.getJenkinsstageviewurl();
		String appNameWS = appName.replaceAll(" ", "%20");
		String pipelineNameWS = pipelineName.replaceAll(" ", "%20");
		String jobName = appNameWS + "_" + pipelineNameWS;
		url = jenkinsUrl + JOB_URL + jobName + JOB_URL + jobName + "_Pipeline/";
		return url;

	}
	
	public String getJobJSON(String jobName, String param) {
		StringBuilder jobUrl = new StringBuilder();
		jobUrl.append(configManager.getJenkinsurl());
		jobUrl.append(JOB_URL);
		jobUrl.append(jobName);
		logger.debug("Base Job Url:" + jobUrl);
		if ("job".equalsIgnoreCase(param)) {
			jobUrl.append("/api/json?tree=jobs[name]");
			logger.debug("job url " + jobUrl);
		} else if ("builds".equalsIgnoreCase(param)) {
			jobUrl.append("/api/json?tree=builds[number]");
			logger.debug("builds url " + jobUrl);
		} else if ("builds_Pipeline".equalsIgnoreCase(param)) {
			jobUrl.append(JOB_URL + jobName + "_Pipeline/api/json?tree=builds[number]");
			logger.debug("builds url " + jobUrl);
		} else if ("lastBuild".equalsIgnoreCase(param)) {
			jobUrl.append(JOB_URL + jobName + "_Pipeline/api/json?tree=lastBuild[number]");
			logger.debug("last build url " + jobUrl);
		} else if ("nextBuild".equalsIgnoreCase(param)) {
			jobUrl.append(JOB_URL + jobName + "_Pipeline/api/json?pretty=true");
			logger.debug("next build url " + jobUrl);
		} else if ("nextBuild_Pipeline".equalsIgnoreCase(param)) {
			jobUrl.append(JOB_URL + jobName + "_Pipeline/api/json?pretty=true");
			logger.debug("nextBuild_Pipeline url " + jobUrl);
		} else if ("buildable".equalsIgnoreCase(param)) {
			jobUrl.append("_Main/api/json?pretty=true");
			logger.debug("buildable url " + jobUrl);
		} else if (param.contains("ApprovalCheck")) {
			jobUrl.append(JOB_URL + param.split(";")[2] + param.split(";")[0] + APPR_STEP);
			logger.debug("buildable url " + jobUrl);
		} else if (param.contains("apprNext;")) {
			if ("BUILD".equalsIgnoreCase(param.split(";")[1])) {
				jobUrl.append(JOB_URL + jobName + "_Build");
			} else if ("DEPLOY".equalsIgnoreCase(param.split(";")[1])) {
				String workenv = param.split(";")[2];
				jobUrl.append(JOB_URL + jobName + "_Deploy_" + workenv);
				jobUrl.append(JOB_URL + jobName + "_Deploy_" + workenv);
			}
			jobUrl.append("/api/json?tree=nextBuildNumber");
			logger.debug("buildable url " + jobUrl);
		} else if (param.contains("getJson;")) {
			jobUrl.append("/job/" + param.split(";")[2] + param.split(";")[0]);
			jobUrl.append("/api/json");
			logger.debug("buildable url " + jobUrl);
		}
		try {
			
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(jobUrl.toString());

			HttpEntity<?> entity = new HttpEntity<>(headers);
			ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
					String.class);

			return response.getBody();
			
		} catch (ResourceAccessException rae) {
			logger.error("Error in communicating Jenkins : {}", rae.getMessage());
			throw rae;
		} catch (HttpClientErrorException unauthorizedException) {
			logger.error("Username (or) password incorrect : {}", unauthorizedException.getMessage());
			throw unauthorizedException;
		} 

	}
	
	public String downloadArtifacts(String daiJobName, String daiBuildNumber,String daiSubJobName){
		StringBuilder jobUrl = new StringBuilder();
		if ("SelectNumber".equalsIgnoreCase(daiBuildNumber)
				|| ("SelectJob").equalsIgnoreCase(daiSubJobName)) {
			logger.error("Build number or job is not correct!!");
			return null;
		}
		jobUrl.append(configManager.getJenkinsurl());
		jobUrl.append(JOB_URL);
		jobUrl.append(daiJobName);
		jobUrl.append(JOB_URL);
		jobUrl.append(daiSubJobName);
		jobUrl.append("/");
		jobUrl.append(daiBuildNumber);
		jobUrl.append("/artifactarchive.zip");
		String line = "";

		try {

			String s = configManager.getJenkinsuserid() + ":" + configManager.getJenkinspassword();
			String encoding = Base64.getEncoder().encodeToString(s.getBytes());
			
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(jobUrl.toString());
			RequestCallback requestCallback = request -> request.getHeaders()
			        .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
			requestCallback = request -> request.getHeaders()
			        .set(AUTHORIZATION, BASIC + encoding);
			
			File file = restTemplate.execute(builder.toUriString(), HttpMethod.GET, requestCallback, clientHttpResponse -> {
			    File ret = File.createTempFile(daiJobName + ".zip", "tmp");
			    StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
			    return ret;
			});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		return line;
	}
	
	private String getFullJobJSON(StringBuilder jobUrl) {
		
		try {
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(jobUrl.toString() + "/api/json?pretty=true");

			HttpEntity<?> entity = new HttpEntity<>(headers);
			ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
					String.class);

			return response.getBody();
			
		} catch (ResourceAccessException rae) {
			logger.error("Error in communicating Jenkins : {}", rae.getMessage());
		} catch (HttpClientErrorException unauthorizedException) {
			logger.error("Username (or) password incorrect : {}", unauthorizedException.getMessage());
		} 
		return "";
	}
	
	public String apprRejectJobs(ApproveBuildParams approveBuildParams, String to) throws UnsupportedEncodingException, InterruptedException  {
		
		String jobName = approveBuildParams.getApplicationName() + "_" + approveBuildParams.getPipelineName();
		StringBuilder jobUrl = new StringBuilder();
		jobUrl.append(configManager.getJenkinsurl());
		jobUrl.append(JOB_URL);
		jobUrl.append(jobName);
		logger.debug("Base Job Url: {}" , jobUrl);
		StringBuilder jobUrl_temp = new StringBuilder(jobUrl);
		Integer buildNum = -1;
		if ("BUILD".equalsIgnoreCase(approveBuildParams.getJobType())) {
			jobUrl.append(JOB_URL);
			jobUrl.append(jobName + "_Build");
			jobUrl_temp.append(JOB_URL);
			jobUrl_temp.append(jobName + "_Build");
			buildNum = getBuildNumber(jobUrl, approveBuildParams.getApprBuildNo());
			logger.info("Build Number for Build: {}" , buildNum);
		} else {
			jobUrl.append(JOB_URL);
			String workEnv = approveBuildParams.getEnvName();
			jobUrl.append(jobName + "_Deploy_" + workEnv + "/job/" + jobName + "_Deploy_" + workEnv);
			jobUrl_temp.append(JOB_URL);
			jobUrl_temp.append(jobName + "_Deploy_" + workEnv + "/job/" + jobName + "_Deploy_" + workEnv);
			buildNum = getBuildNumber(jobUrl, approveBuildParams.getApprBuildNo());
			logger.info("Build Number for Deploy: " + buildNum);
		}
		if (approveBuildParams.getApprComment() == null || "".equalsIgnoreCase(approveBuildParams.getApprComment())) {
			approveBuildParams.setApprComment("No Comments Recorded");
		}
		String description = approveBuildParams.getUserName() + "  Comments: " + approveBuildParams.getApprComment();
		boolean appReject = false;
		jobUrl.append("/" + buildNum + "/input/IDPApproval/submit");
		MultiValueMap<String,String> extraParams = new LinkedMultiValueMap<>();
		if ("approved".equalsIgnoreCase(approveBuildParams.getApprInput())) {
			extraParams.add("json","{\"parameter\": [{\"name\":\"APPR_INFO\", \"value\":\"" + description + "\"}]}");
			extraParams.add("proceed", "Proceed");
		} else if ("rejected".equalsIgnoreCase(approveBuildParams.getApprInput())) {
			extraParams.add("abort", "Abort");
			appReject = true;
		}
		try {
			
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(jobUrl.toString());
			builder.queryParams(extraParams);
			
			String uri = builder.build().toUriString();
			
			logger.info("URL for approve/reject: {}" , uri);
			HttpEntity<String> entity = new HttpEntity<>(approveJobsHeader);
			ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.POST, entity,
					String.class);

			logger.debug("response body for approve/reject: {}" , response.getBody());
			
			if (appReject) {
				logger.info("before sleep");
				Thread.sleep(20000);
				logger.info("after sleep");
				jobUrl_temp.append("/" + buildNum + "/input/IDPDefault/submit");
				
				extraParams.add("proceed", "Proceed");
				extraParams.add("json","{\"parameter\": [{\"name\":\"APPR_INFO\", \"value\":\"" + description + "\"}]}");
				
				builder = UriComponentsBuilder
						.fromHttpUrl(jobUrl_temp.toString());
				logger.info("URL for reject: {}" , builder.build().toUriString());
				response = restTemplate.exchange(builder.build().toUri(), HttpMethod.POST, entity,
						String.class);

				logger.debug("response body for reject api call: {}" , response.getBody());
				
			}
		} catch (ResourceAccessException rae) {
			logger.error("Error in communicating Jenkins : {}", rae.getMessage());
			throw rae;
		} catch (HttpClientErrorException unauthorizedException) {
			logger.error("Username (or) password incorrect : {}", unauthorizedException.getMessage());
			throw unauthorizedException;
		}

		logger.info("Job " + approveBuildParams.getApplicationName() + "_" + approveBuildParams.getPipelineName()
				+ " is " + approveBuildParams.getApprInput() + " by " + description);
		return "SUCCESS";
	}
	
	private Integer getBuildNumber(StringBuilder jobUrl, String apprBuildNo) {
		String json = getFullJobJSON(jobUrl);
		Integer num = null;
		if (!"".equalsIgnoreCase(json)) {
			JSONObject jo = JSONObject.fromObject(json);
			if (jo.has("builds")) {
				JSONArray builds = jo.getJSONArray("builds");
				int buildsSize = builds.size();
				for (int i = 0; i < buildsSize; i++) {
					JSONObject temp = (JSONObject) builds.get(i);
					StringBuilder url = new StringBuilder(temp.getString("url"));
					if (url != null && !"".equalsIgnoreCase(url.toString())) {
						String buildjson = getFullJobJSON(url);
						JSONObject buildJo = JSONObject.fromObject(buildjson);
						if (buildJo.has("actions")) {
							JSONArray actionJsonArr = buildJo.getJSONArray("actions");
							int actionJsonArrSize = actionJsonArr.size();
							for (int j = 0; j < actionJsonArrSize; j++) {
								JSONObject jk = (JSONObject) actionJsonArr.get(j);
								if (jk.has("parameters")) {
									JSONArray parametersJsonArr = jk.getJSONArray("parameters");
									int parametersJsonArrSize = parametersJsonArr.size();
									for (int k = 0; k < parametersJsonArrSize; k++) {
										JSONObject parameterJson = (JSONObject) parametersJsonArr.get(k);
										if ("PIPELINE_BUILD_ID".equalsIgnoreCase((String) parameterJson.get("name"))) {
											String pipelineBuildNum = (String) parameterJson.get("value");
											if (Integer.parseInt(pipelineBuildNum) == Integer.parseInt(apprBuildNo)) {
												num = buildJo.getInt("number");
												break;
											}
										}
									}
								}
								if (num != null)
									break;
							}
						}
					}
					if (num != null)
						break;
				}
			}
		}
		return num;
	}
	
	public BuildStatus getBuildStatus(String jobName, int buildnumber, long whenToTimeout) throws URISyntaxException {
		String jenkinsURL = configManager.getJenkinsurl();
		String userName = configManager.getJenkinsuserid();
		String password = configManager.getJenkinspassword();
		try {
			JenkinsServer server = new JenkinsServer(new URI(jenkinsURL), userName, password);
			Thread.sleep(20000l);
			JobWithDetails job = server.getJob(jobName);
			Build buildByNumber = null;
			if (job.getNextBuildNumber() == 1) {
				buildByNumber = job.getFirstBuild();
				logger.info("buildByNumber.getNumber()" + buildByNumber.getNumber());
			} else {
				buildByNumber = job.getLastBuild();
				logger.info("buildByNumber.getNumber()" + buildByNumber.getNumber());
			}

			logger.debug("buildByNumber.details().isBuilding(): " + buildByNumber.details().isBuilding());
			while (buildByNumber.details().isBuilding()) {
				logger.debug("buildByNumber.details().isBuilding(): " + buildByNumber.details().isBuilding());
				logger.debug("Thread sleeps for " + POLLPERIOD);
				logger.debug("Thread sleeps for " + POLLPERIOD);
				Thread.sleep(POLLPERIOD);
				logger.debug("Thread woke up for " + POLLPERIOD);
				logger.debug("Thread woke up for " + POLLPERIOD);

			}
			logger.info(
					"buildByNumber.details().getResult().toString()" + buildByNumber.details().getResult().toString());
			if(buildByNumber.getNumber() >= buildnumber)return new BuildStatus(buildByNumber.details().getResult().toString(), buildByNumber.getNumber());
			else return new BuildStatus("PENDING", buildByNumber.getNumber());
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}



	public String getSlaveStatus(String slaveName)  {

		try {
			
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(configManager.getJenkinsurl() + "/label/" + slaveName + "/api/json");

			HttpEntity<?> entity = new HttpEntity<>(headers);
			ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
					String.class);

			String responseBody = response.getBody();
			JSONObject js = JSONObject.fromObject(responseBody);
			String status = js.getString("offline");

			if (status.equalsIgnoreCase("false"))
				return "Online";
			else
				return "Offline";
		} catch (ResourceAccessException rae) {
			logger.error("Error in communicating Jenkins : {}", rae.getMessage());
			throw rae;
		} catch (HttpClientErrorException unauthorizedException) {
			logger.error("Username (or) password incorrect : {}", unauthorizedException.getMessage());
			throw unauthorizedException;
		} 


	}
}