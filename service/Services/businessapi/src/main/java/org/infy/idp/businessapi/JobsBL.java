/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.businessapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.infy.idp.utils.OrchestratorConnector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.services.EnvironmentDetails;
import org.infy.idp.dataapi.services.JobAdditionalDetailsDL;
import org.infy.idp.dataapi.services.JobDetailsDL;
import org.infy.idp.dataapi.services.JobDetailsInsertionService;
import org.infy.idp.dataapi.services.JobInfoDL;
import org.infy.idp.dataapi.services.JobManagementDL;
import org.infy.idp.dataapi.services.ReleaseDetails;
import org.infy.idp.entities.jobs.EnvName;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.Pipelines;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.Applications;
import org.infy.idp.entities.jobs.code.JobParam;
import org.infy.idp.entities.jobs.common.Notification;
import org.infy.idp.entities.jobs.deployinfo.DeployEnv;
import org.infy.idp.entities.jobs.testinfo.TestEnv;
import org.infy.idp.entities.jobs.testinfo.TestStep;
import org.infy.idp.entities.models.BuildStatus;
import org.infy.idp.entities.triggerparameter.ApproveBuildParams;
import org.infy.idp.entities.triggerparameter.TestStepInfo;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.infy.idp.utils.BuildService;
import org.infy.idp.utils.ConfigurationManager;
import org.infy.idp.utils.JenkinsCLI;
import org.infy.idp.utils.TriggerBuilds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class JobsBL {
	protected Logger logger = LoggerFactory.getLogger(JobsBL.class);
	private static final String AUTHORIZATION = "Authorization";
	private static final String CREATE_PIPELINE = "CREATE_PIPELINE";
	private static final String EDIT_PIPELINE = "EDIT_PIPELINE";
	private static final String SUCCESS = "SUCCESS";
	private static final String CREATE = "CREATE";
	private static final String COPY = "COPY";
	private static final String BASIC = "Basic ";
	private static final String TRUE = "true";
	private static final String FALSE = "FALSE";
	private static final String JOB_URL = "/job/";
	private static final String WORKFLOW = "workflow";
	@Autowired
	private FetchJobDetails fetchJobDetails;
	@Autowired
	private JobsManagementBL jobsmgmtBL;
	@Autowired
	private JobsAdditionalInfo jobsaddInfo;
	@Autowired
	private ConfigurationManager configmanager;
	@Autowired
	private JobDetailsDL jobDetailsDL;
	@Autowired
	private JobManagementDL jobManagementDL;
	@Autowired
	private JobAdditionalDetailsDL jobAddDetailDL;

	@Autowired
	private JobInfoDL jobInfoDL;
	@Autowired
	private JobDetailsInsertionService jobDetailsInsertion;
	@Autowired
	private BuildService buildService;
	@Autowired
	private TriggerBuilds triggerBuilds;
	@Autowired
	private JenkinsCLI cli;
	@Autowired
	private EnvironmentDetails environmentDetails;
	@Autowired
	private ReleaseDetails releaseDetails;
	@Autowired
	private EmailSender emailSender;
	@Autowired
	private OrchestratorConnector orchConn;
	
	private JobsBL() {
		// constructor
	}

	public void processJobs(IDPJob idpjson) {
		buildService.createNewJob(idpjson);
	}

	public String submitJob(IDPJob inputIdp, String user) {
		IDPJob idp = inputIdp;
		Gson gson = new Gson();
		logger.debug("submit job start");
		List<String> permissions = jobsaddInfo.getPermissionForApplications(idp.getBasicInfo().getApplicationName(),
				user);
		try {
			jobDetailsDL.getOrgId(user);
			if (!jobsaddInfo.getPipelinePermissionforApplication(idp.getBasicInfo().getApplicationName(), user)
					.isEmpty())
				permissions.add(EDIT_PIPELINE);
		} catch (SQLException e1) {
			logger.error("Error while adding pipeline permission for application ", e1);
		}
		if (!(permissions.contains(CREATE_PIPELINE) || permissions.contains(EDIT_PIPELINE))) {
			return "User Does not have permission to create pipeline";
		}
		IDPJob aLocal = new IDPJob();
		Gson g1 = new Gson();
		String newidpforLocal = g1.toJson(idp);
		aLocal = g1.fromJson(newidpforLocal, IDPJob.class);

		setArtificatStage(idp);
		IDPJob a = new IDPJob();
		Gson g = new Gson();
		String newidp = g.toJson(idp);
		a = g.fromJson(newidp, IDPJob.class);
		logger.info(newidp);
		idp = fetchJobDetails.getSonarInfo(idp);
		logger.info("IDP JSON " + gson.toJson(idp, IDPJob.class).toString());
		logger.debug("IDP JSON " + gson.toJson(idp, IDPJob.class).toString());
		
		if (idp.getCode() != null && idp.getCode().getTechnology() != null
				&& idp.getCode().getTechnology().equalsIgnoreCase("pega")) {
			try {
				idp = jobsmgmtBL.setProductKey(idp);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		logger.info("creating seed jobs.");
		orchConn.sendKafkaMessage("{\"action\": \"create\",\"targetplatform\":\"jenkins\",\"idpjson\":" + gson.toJson(idp, IDPJob.class).toString() + ",\"jenkinsURL\":\"" + configmanager.getJenkinsurl() + "\"}");
		logger.info(
				idp.getBasicInfo().getApplicationName() + "_" + idp.getBasicInfo().getPipelineName() + " Job created.");
		IDPJob idpLocal = idp;
		runInThread(idpLocal, aLocal, user);
		return SUCCESS;
	}

	private void setArtificatStage(IDPJob idp) {
		if (idp.getBuildInfo() != null && idp.getBuildInfo().getArtifactToStage() != null) {
			logger.info(idp.getBuildInfo().getArtifactToStage().getArtifactRepoName());
			if (idp.getBuildInfo().getArtifactToStage().getArtifactRepoName() == null
					|| idp.getBuildInfo().getArtifactToStage().getArtifactRepoName().equalsIgnoreCase("")) {
				try {
					ApplicationInfo ap = jobInfoDL.getApplication(idp.getBasicInfo().getApplicationName());
					if (ap.getArtifactToStage() != null) {
						// for Docker registry
						if (ap.getArtifactToStage().getArtifactRepoName().equalsIgnoreCase("docker")) {
							idp.getBuildInfo().setArtifactToStage(ap.getArtifactToStage());
						} else {
						ap.getArtifactToStage().setArtifact(idp.getBuildInfo().getArtifactToStage().getArtifact());
						ap.getArtifactToStage().setFlattenFileStructure(
								idp.getBuildInfo().getArtifactToStage().getFlattenFileStructure());
						idp.getBuildInfo().setArtifactToStage(ap.getArtifactToStage());
					}
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			} else if (idp.getBuildInfo().getArtifactToStage().getArtifactRepoName().equalsIgnoreCase("jfrog")) {
				try {
					orchConn.addArtifactoryRepoGlobConf(
							idp.getBuildInfo().getArtifactToStage().getArtifactRepo().getRepoURL(),
							idp.getBuildInfo().getArtifactToStage().getArtifactRepo().getRepoUsername(),
							idp.getBuildInfo().getArtifactToStage().getArtifactRepo().getRepoPassword(),
							idp.getBuildInfo().getArtifactToStage().getArtifactRepo().getRepoUsername().toLowerCase()
									+ "_" + idp.getBuildInfo().getArtifactToStage().getArtifactRepo().getRepoName(),configmanager.getJenkinsurl());
				} catch (Exception e) {
					logger.error("error while adding artifcatory repo {}", e.getMessage());
				}
			}
		}
	}


	private void runInThread(IDPJob idpLocal, IDPJob aLocal, String user) {
		new Thread(new Runnable() {
			@Override
			public void run() {
			
				String jenkinsURL = configmanager.getJenkinsurl();
				String userName = configmanager.getJenkinsuserid();
				String password = configmanager.getJenkinspassword();
				Integer pipelineLastBuildNumber = 0;
				try {
					pipelineLastBuildNumber = jobDetailsInsertion.getLatestBuildNumber( idpLocal.getBasicInfo().getApplicationName(), idpLocal.getBasicInfo().getPipelineName());
					BuildStatus buildStatus = orchConn.getBuildStatus(
							idpLocal.getBasicInfo().getApplicationName() + "_" + idpLocal.getBasicInfo().getPipelineName() + "_Main", pipelineLastBuildNumber + 1, 120000, jenkinsURL);
		
					logger.info("creating seed jobs.");
					if (buildStatus.getState().equalsIgnoreCase(SUCCESS)) {
						jobDetailsInsertion.insertPipelineJsonDetails(aLocal, TRUE);
					} else {
						if (idpLocal.getBasicInfo().getPipelineStatus().toString().equalsIgnoreCase(CREATE)
								|| idpLocal.getBasicInfo().getPipelineStatus().toString().equalsIgnoreCase(COPY)) {
							jobDetailsInsertion.insertPipelineJsonDetails(aLocal, FALSE);
						}
					}
					// for IDP only
					if (idpLocal.getCode() != null && buildStatus.getState().equalsIgnoreCase(SUCCESS)) {
						long pipelineId = jobInfoDL.getPipelineId(idpLocal.getBasicInfo().getPipelineName(),
								idpLocal.getBasicInfo().getApplicationName());
						jobDetailsInsertion.deleteAdditionalJobParamDetails(pipelineId);
						if (aLocal.getBasicInfo().getMasterSequence() == null
								|| !WORKFLOW.equalsIgnoreCase(aLocal.getBasicInfo().getMasterSequence())) {

							for (JobParam jobParam : aLocal.getCode().getJobParam()) {
								jobDetailsInsertion.insertAdditionalJobParamDetails(jobParam,
										idpLocal.getBasicInfo().getApplicationName(),
										idpLocal.getBasicInfo().getPipelineName());
							}
						}
						jobDetailsDL.getOrgId(user);
						

					}
					if (buildStatus.getState().equalsIgnoreCase("FAILURE")) {
						jobDetailsInsertion.addNotification(idpLocal.getBasicInfo().getPipelineName(), "FAILURE", user);
					} else {
						jobDetailsInsertion.addNotification(idpLocal.getBasicInfo().getPipelineName(), SUCCESS, user);
					}
					logger.info("Pipeline information is inserted in the database");
				} catch (URISyntaxException | SQLException e) {
					jobDetailsInsertion.addNotification(idpLocal.getBasicInfo().getPipelineName(), "FAILURE", user);
					logger.error("Erorr in submitJob: ", e);
					logger.info(e.getMessage(), e);
				} catch (Exception e) {
					jobDetailsInsertion.addNotification(idpLocal.getBasicInfo().getPipelineName(), "FAILURE", user);
					logger.error("Erorr in submitJob: ", e);
					logger.info(e.getMessage(), e);
				}
			}
		}).start();
	}


	public String submitInterval(org.infy.idp.entities.jobs.basicinfo.TriggerInterval inputIdp, String applicationName,String pipelineName,String userName, String user) {
		
		org.infy.idp.entities.jobs.basicinfo.TriggerInterval idp = inputIdp;
		TriggerJobName triggerJobName = new TriggerJobName();
		triggerJobName.setApplicationName(applicationName);
		triggerJobName.setPipelineName(pipelineName);
		triggerJobName.setUserName(userName);
		Pipeline pipeline = jobManagementDL.getPipelineDetail(triggerJobName);
		idp = setEmails(idp, pipeline,applicationName,pipelineName,userName, user);
		IDPJob idpjson = pipeline.getPipelineJson();
		idpjson.getBasicInfo().setCustomTriggerInterval(idp);
		submitJob(idpjson, triggerJobName.getUserName());		
		return "SUCCESS";
	}

	public org.infy.idp.entities.jobs.basicinfo.TriggerInterval setEmails(
			org.infy.idp.entities.jobs.basicinfo.TriggerInterval triggerInterval, Pipeline pipeline,String applicationName,String pipelineName,String userName, String user) {
		TriggerJobName triggerJobName = new TriggerJobName();
		triggerJobName.setApplicationName(applicationName);
		triggerJobName.setPipelineName(pipelineName);
		triggerJobName.setUserName(user);
		List<String> users = emailSender.getUsersFromApplication(triggerJobName, user);
		String emailIds = String.join(",", users);
		logger.info("email Ids : " + emailIds);
		int intervalListSize = triggerInterval.getInterval().size();
		for (int i = 0; i < intervalListSize; i++) {
			triggerInterval.getInterval().get(i).getDetails().setEmailed(emailIds);
		}
		return triggerInterval;
	}

	public String lastbuild(String webpage, String username, String password) {
		String buildId = null;
		String authString = username + ":" + password;
		InputStream is = null;
		InputStreamReader isr = null;
		String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
		try {
			URL url = new URL(webpage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty(AUTHORIZATION, BASIC + authStringEnc);
			is = urlConnection.getInputStream();
			isr = new InputStreamReader(is);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			Element root = document.getDocumentElement();
			NodeList lastBuildList = root.getElementsByTagName("lastBuild");
			int lastBuildListLength = lastBuildList.getLength();
			for (int i = 0; i < lastBuildListLength; i++) {
				Node node = lastBuildList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (element.getElementsByTagName("number").item(0) != null)
						buildId = element.getElementsByTagName("number").item(0).getTextContent();
					else
						buildId = null;
				}
			}
		} catch (IOException | ParserConfigurationException | SAXException e) {
			logger.debug(e.getMessage());
		} finally {
			if (null != isr) {
				try {
					isr.close();
					isr = null;
				} catch (IOException e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
		}
		return buildId;
	}

	public List<String> getPipelines(String applicationName, String releaseNumber) {
		return jobDetailsDL.getPipelines(applicationName, releaseNumber);
	}

	public List<String> getReleaseInfo(String appName) {
		List<String> releaseNumber = new ArrayList<>();
		try {
			logger.info("inside getreleaseinfo");
			Integer appid = jobDetailsDL.getAppId(appName);
			logger.info("after get app id");
			releaseNumber = jobDetailsDL.getAppReleaseNo(appid);
			logger.info("after get app releaseno");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return releaseNumber;
	}

	public Applications getExistingApps(String userName) {
		Gson gson = new Gson();
		List<Application> apps = new ArrayList<>();
		try {
			if (jobDetailsDL.checkCreateAppPermission(userName)) {
				logger.info("The user is having access to create application");
				Applications applications = new Applications();
				List<String> permissions = jobsmgmtBL.getPermission(userName);
				if (permissions.isEmpty())
					return applications;
				apps = jobDetailsDL.getExistingAppDetails();
				applications.setApplications(apps);
				logger.debug("Existing applications : " + gson.toJson(applications, Applications.class).toString());
			}
		} catch (SQLException e) {
			logger.error("Existing Apps could not found!!\n" + e.getMessage(), e);
		}
		Applications applications = new Applications();
		applications.setApplications(apps);
		return applications;
	}

	public Applications getExistingApps(String userName, String platformName) {
		Gson gson = new Gson();
		List<Application> apps = new ArrayList<>();
		try {
			if (jobDetailsDL.checkCreateAppPermission(userName)) {
				logger.info("The user is having access to create application");
				Applications applications = new Applications();
				List<String> permissions = jobsmgmtBL.getPermission(userName);
				if (permissions.isEmpty())
					return applications;
				apps = jobDetailsDL.getExistingAppList(platformName);
				applications.setApplications(apps);
				logger.debug("Existing applications : " + gson.toJson(applications, Applications.class).toString());
			}
		} catch (SQLException e) {
			logger.error("Existing Apps could not found!!\n" + e.getMessage(), e);
		}
		Applications applications = new Applications();
		applications.setApplications(apps);
		return applications;
	}

	public List<Notification> getNotifications(String username) {
		List<Notification> notifList = jobDetailsDL.getNotifications(username);
		return notifList;
	}

	public Applications getExistingAppNames(String userName, String platformName) {
		Gson gson = new Gson();
		List<Application> apps = new ArrayList<>();
		try {
			Applications applications = new Applications();
			List<String> permissions = jobsmgmtBL.getAllPermission(userName);
			if (permissions.isEmpty())
				return applications;
			apps = jobAddDetailDL.getExistingAppNames(platformName);
			applications.setApplications(apps);
			logger.debug("Existing applications : " + gson.toJson(applications, Applications.class).toString());
		} catch (SQLException e) {
			logger.error("Existing Apps could not found!!\n" + e.getMessage(), e);
		}
		Applications applications = new Applications();
		applications.setApplications(apps);
		return applications;
	}

	public Applications getExistingAppNames(String userName, String orgName, String platformName) {
		Gson gson = new Gson();
		List<Application> apps = new ArrayList<>();
		try {
			logger.info("The user is having access to create application");
			Applications applications = new Applications();
			List<String> permissions = jobsaddInfo.getAllPermission(userName);
			if (permissions.isEmpty())
				return applications;
			apps = jobAddDetailDL.getExistingAppNames(orgName, platformName);
			applications.setApplications(apps);
			logger.debug("Existing applications : " + gson.toJson(applications, Applications.class).toString());
		} catch (SQLException e) {
			logger.error("Existing Apps could not found!!\n" + e.getMessage(), e);
		}
		Applications applications = new Applications();
		applications.setApplications(apps);
		return applications;
	}

	public ApplicationInfo getApplicationInfo(String appname, String userId) {
		ApplicationInfo ap = null;
		List<String> permissions = jobsaddInfo.getAllPermissionforApp(appname, userId);
		if (permissions.isEmpty()) {
			return new ApplicationInfo();
		}
		try {
			ap = jobInfoDL.getApplication(appname);
		} catch (SQLException e) {
			logger.error("Problem in fetching App info", e);
			logger.debug(e.getMessage());
		}
		return ap;
	}

	public Pipelines getExistingPipelines(String appName) {
		Gson gson = new Gson();
		Pipelines pipelines = new Pipelines();
		List<Pipeline> pipelinesList = new ArrayList<>();
		try {
			pipelinesList = jobDetailsDL.getExistingPipelines(appName);
			pipelines.setPipelines(pipelinesList);
			logger.debug("Existing applications : " + gson.toJson(pipelines, Pipelines.class).toString());
		} catch (SQLException e) {
			logger.error("Existing Apps could not found!!\n" + e.getMessage(), e);
		}
		return pipelines;
	}

	public Pipelines getpipelinesByAppNameAndUser(String appName, String user) {
		Gson gson = new Gson();
		List<Pipeline> pips = new ArrayList<>();
		try {
			logger.info("Getting pipelines for application ");
			logger.debug(appName);
			List<String> permissions = jobsaddInfo.getAllPermissionforApp(appName, user);
			if (permissions.isEmpty()) {
				Pipelines pipelines = new Pipelines();
				pipelines.setPipelines(pips);
				return pipelines;
			}
			pips = jobAddDetailDL.getPipelines(appName);
		} catch (SQLException e) {
			logger.error("Existing Apps Error!!\n" + e.getMessage());
		}
		Pipelines pipelines = new Pipelines();
		pipelines.setPipelines(pips);
		logger.debug("pipelines : " + gson.toJson(pipelines, Pipelines.class));
		return pipelines;
	}

	public EnvName getEnvironmentNames(String appName, String user) {
		Gson gson = new Gson();
		EnvName env = new EnvName();
		List<String> permissions = jobsaddInfo.getPermissionForApplications(appName, user);
		if (permissions.isEmpty()) {
			return env;
		}
		Application apps;
		try {
			logger.debug("getting environments names");
			apps = jobDetailsDL.getExistingAppDetails(appName);
			logger.debug("AppDetails : " + gson.toJson(apps, Application.class));
			env.setEnvNames(fetchJobDetails.getEnvironmentsforPipelineCreation(apps.getAppJson()));
			logger.debug("Env Names : " + gson.toJson(env, EnvName.class));
		} catch (SQLException e) {
			logger.error("Existing Apps Error!!\n" + e.getMessage(), e);
		}
		return env;
	}

	private ArrayList<TestStepInfo> addTestSteps(IDPJob idpjob, TriggerParameters triggerparameters) {
		ArrayList<TestStepInfo> testStepInfoList = new ArrayList<>();
		if (idpjob != null && null != idpjob.getTestInfo() && idpjob.getTestInfo().getTestEnv() != null
				&& idpjob.getTestInfo().getTestEnv().size() > 0) {
			for (TestEnv testenv : idpjob.getTestInfo().getTestEnv()) {
				if (testenv != null && testenv.getTestSteps() != null && testenv.getTestSteps().size() > 0) {
					for (String testStepName : triggerparameters.getTestStep()) {
						for (TestStep testStep : testenv.getTestSteps()) {
							if (testStepName.equalsIgnoreCase(testStep.getStepName())) {
								TestStepInfo testStepInfo = new TestStepInfo();
								testStepInfo.setTestStepName(testStep.getStepName());
								testStepInfo.setTestStepTool(testStep.getTest().getTestTypeName());
								testStepInfoList.add(testStepInfo);
							}
						}
					}
				}
			}
		}
		return testStepInfoList;
	}

	public String triggerJobs(TriggerParameters inputTriggerparameters, String userName) {
		logger.debug("Triggering jobs");
		TriggerParameters triggerparameters = inputTriggerparameters;
		ApplicationInfo app = null;
		IDPJob idpjob = null;
		Gson g = new Gson();
		try {
			idpjob = jobAddDetailDL.getPipelineInfo(triggerparameters.getApplicationName(),
					triggerparameters.getPipelineName());
			logger.debug(g.toJson(idpjob, IDPJob.class));
			app = jobInfoDL.getApplication(triggerparameters.getApplicationName());
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			logger.error("Could not trigger the pipeline " + triggerparameters.getApplicationName() + "_"
					+ triggerparameters.getPipelineName());
		}
		if (!validateTestStep(idpjob, triggerparameters.getTestStep())) {
			throw new IllegalStateException("Invalid Test Step");
		}
		// Populating test step details
		ArrayList<TestStepInfo> testStepInfoList = new ArrayList<>();
		testStepInfoList = addTestSteps(idpjob, triggerparameters);
		triggerparameters.setTestStepDetails(testStepInfoList);
		triggerparameters.setLanscapeName(triggerparameters.getEnvSelected());
		triggerparameters.setDashBoardLink(configmanager.getDashboardurl());
		triggerparameters = setDbDeployDetails(triggerparameters, idpjob);
		triggerJobs(triggerparameters, idpjob, app, userName);
		logger.info("Job " + triggerparameters.getApplicationName() + "_" + triggerparameters.getPipelineName()
				+ "is triggered.");
		return "SUCESS";
	}

	private void triggerJobs(TriggerParameters inputTriggerparameters, IDPJob idpjob, ApplicationInfo app,
			String userName) {
		// Modularized email ids
		TriggerParameters triggerparameters = inputTriggerparameters;
		Gson g = new Gson();
		
		List<String> users = emailSender.getUsersFromApplication(triggerparameters.getApplicationName(),
				triggerparameters.getPipelineName(), triggerparameters.getUserName());
		String emailIds = String.join(",", users);
		triggerparameters.setEmailed(emailIds);
		triggerparameters.setSonardashBoardLink(configmanager.getSonardashboardurl());
		logger.info(g.toJson(triggerparameters));
		triggerparameters.setNugetPackaging(
				(!WORKFLOW.equalsIgnoreCase(idpjob.getBasicInfo().getMasterSequence()) && idpjob.getBuildInfo() != null && null != idpjob.getBuildInfo().getArtifactToStage().getnuspecFilePath()
						&& !(idpjob.getBuildInfo().getArtifactToStage().getnuspecFilePath().equals(""))) ? true
								: false);
		// setting scm branch
		triggerparameters = setSCMBranch(triggerparameters, idpjob);
		
		if (idpjob.getBasicInfo().getMasterSequence() != null
				&& WORKFLOW.equalsIgnoreCase(idpjob.getBasicInfo().getMasterSequence()) && idpjob.getPipelines() != null
				&& idpjob.getPipelines().size() > 0) {
			triggerparameters.setPipelines(idpjob.getPipelines());
		}
		
		Integer triggerId = jobDetailsInsertion.insertTriggerHistory(triggerparameters);
		triggerparameters.setTriggerId(triggerId);
		// The pipeline is getting triggered here
		String buildObj = jobsmgmtBL.getJobJSON(
				triggerparameters.getApplicationName() + "_" + triggerparameters.getPipelineName(),
				"nextBuild_Pipeline");
		JSONObject buildJson = JSONObject.fromObject(buildObj);
		String artifactName = getAritifactName(triggerparameters, buildJson);
		jobDetailsInsertion.updateTriggerHistory(triggerId, buildJson.getString("nextBuildNumber"), artifactName);
		logger.info("nuget packaging is set to:" + triggerparameters.getNugetPackaging());
		if ((triggerparameters.getDeploy() != null || triggerparameters.getBuild() != null)) {
			insertArtifact(triggerparameters, buildJson, userName);
		}
		orchConn.sendKafkaMessage("{\"action\": \"trigger\",\"targetplatform\":\"jenkins\",\"idpjson\":"
				+ g.toJson(triggerparameters, TriggerParameters.class).toString() + ",\"jenkinsURL\":\"" + configmanager.getJenkinsurl() + "\"}");
		//triggerBuilds.buildByJobName(triggerparameters);
	}

	private String getAritifactName(TriggerParameters triggerparameters, JSONObject buildJson) {
		String artifactName = "";
		if (triggerparameters.getBuild() != null) {
			if (triggerparameters.getDeploy() != null
					&& triggerparameters.getArtifactorySelected().equalsIgnoreCase("on")) {
				triggerparameters.getDeploy().getDeployArtifact().setVersion(buildJson.getString("nextBuildNumber"));
				String scm = triggerparameters.getBranchOrTagValue();
				if (scm == null) {
					scm = "default";
				}
				artifactName = triggerparameters.getApplicationName() + "_" + triggerparameters.getPipelineName() + "_"
						+ triggerparameters.getReleaseNumber() + "-" + scm + "-"
						+ buildJson.getString("nextBuildNumber");
			} else {
				artifactName = triggerparameters.getApplicationName() + "_" + triggerparameters.getPipelineName() + "_"
						+ buildJson.getString("nextBuildNumber");
			}
		}
		return artifactName;
	}

	public TriggerParameters setSCMBranch(TriggerParameters triggerparameters, IDPJob idpjob) {
		if (idpjob.getCode() != null && idpjob.getCode().getScm() != null && idpjob.getCode().getScm().get(0) != null
				&& idpjob.getCode().getScm().get(0).getUrl() != null) {
			logger.info(idpjob.getCode().getScm().get(0).getUrl());
			String giturl = idpjob.getCode().getScm().get(0).getUrl();
			String url = giturl.substring(giturl.lastIndexOf('/') + 1).replace(".git", "");
			triggerparameters.setMtmProjectName(url);
			if (triggerparameters.getBranchOrTagValue() != null
					&& !triggerparameters.getBranchOrTagValue().equals("")) {
				triggerparameters.setScmBranch(triggerparameters.getBranchOrTagValue());
			} else {
				if (idpjob.getCode().getScm().get(0) != null && idpjob.getCode().getScm().get(0).getBranch() != null
						&& !idpjob.getCode().getScm().get(0).getBranch().equals(""))
					triggerparameters.setScmBranch(idpjob.getCode().getScm().get(0).getBranch());
			}
		} else {
			triggerparameters.setScmBranch("");
		}
		if (triggerparameters.getBranchOrTagValue() != null
				&& !triggerparameters.getBranchOrTagValue().equalsIgnoreCase("")
				&& (triggerparameters.getBranchOrTag().equalsIgnoreCase("branch")
						|| triggerparameters.getBranchOrTag().equalsIgnoreCase("tag"))) {
			List<Boolean> branchTagValues = jobsaddInfo.setBranchTagPresentValue(idpjob,
					triggerparameters.getBranchOrTagValue(), triggerparameters.getBranchOrTag());
			triggerparameters.setBranchOrTagList(branchTagValues);
			logger.debug("length of list" + branchTagValues.size());
			logger.debug("list" + branchTagValues);
		}
		return triggerparameters;
	}

	public TriggerParameters setDbDeployDetails(TriggerParameters triggerparameters, IDPJob idpjob) {
		if (triggerparameters.getDeploy() != null
				&& !"SapNonCharm".equalsIgnoreCase(triggerparameters.getTechnology())) {
			int idpJobDeployEnvSize = idpjob.getDeployInfo().getDeployEnv().size();
			for (int i = 0; i < idpJobDeployEnvSize; i++) {
				DeployEnv deployEnv = idpjob.getDeployInfo().getDeployEnv().get(i);
				if (deployEnv.getEnvName().equalsIgnoreCase(triggerparameters.getEnvSelected())) {
					String dbDeployOwners = "";
					String dbDeployPipelineList = "";
					if (null != triggerparameters.getDeploy().getDeployStep()
							&& triggerparameters.getDeploy().getDeployStep().size() > 0) {
						for (int j = 0; j < deployEnv.getDeploySteps().size(); j++) {
							if (triggerparameters.getDeploy().getDeployStep()
									.contains(deployEnv.getDeploySteps().get(j).getStepName())
									&& null != deployEnv.getDeploySteps().get(j).getDeployToContainer()
									&& null != deployEnv.getDeploySteps().get(j).getDeployToContainer()
											.getDbDeployPipelineList()
									&& deployEnv.getDeploySteps().get(j).getDeployToContainer()
											.getDbDeployPipelineList().size() > 0) {
								dbDeployOwners += deployEnv.getDeploySteps().get(j).getDeployToContainer()
										.getDbDeployDBOwners() + ",";
								dbDeployPipelineList += deployEnv.getDeploySteps().get(j).getDeployToContainer()
										.getDbDeployPipelineList().toString().replace("[", "").replace("]", "") + ",";
							}
						}
					} else {
						for (int j = 0; j < deployEnv.getDeploySteps().size(); j++) {
							if (null != deployEnv.getDeploySteps().get(j).getDeployToContainer()
									&& null != deployEnv.getDeploySteps().get(j).getDeployToContainer()
											.getDbDeployPipelineList()
									&& deployEnv.getDeploySteps().get(j).getDeployToContainer()
											.getDbDeployPipelineList().size() > 0) {
								dbDeployOwners += deployEnv.getDeploySteps().get(j).getDeployToContainer()
										.getDbDeployDBOwners() + ",";
								dbDeployPipelineList += deployEnv.getDeploySteps().get(j).getDeployToContainer()
										.getDbDeployPipelineList().toString().replace("[", "").replace("]", "") + ",";
							}
						}
					}
					triggerparameters.getDeploy().setDbDeployPipelineOwners(dbDeployOwners);
					triggerparameters.getDeploy().setDbDeployPipelineList(dbDeployPipelineList);
				}
			}
		}
		return triggerparameters;
	}

	public void insertArtifact(TriggerParameters triggerparameters, JSONObject buildJson, String userName) {
		try {
			int envId = 0;
			int releaseId = releaseDetails.getReleaseId(triggerparameters.getApplicationName(),
					triggerparameters.getPipelineName(), triggerparameters.getReleaseNumber(), "on");
			int applicationId = jobInfoDL.getApplicationId(triggerparameters.getApplicationName()).intValue();
			if (triggerparameters.getEnvSelected() != null && !(triggerparameters.getEnvSelected().equals("")))
				envId = environmentDetails.getEnvironmentId(triggerparameters.getEnvSelected(), applicationId);
			String artifactName;
			if (triggerparameters.getBuild() != null) {
				artifactName = triggerparameters.getApplicationName() + "_" + triggerparameters.getPipelineName() + "_"
						+ triggerparameters.getReleaseNumber() + "-";
				if (triggerparameters.getBranchOrTag() != null && !(triggerparameters.getBranchOrTag().equals(""))) {
					artifactName = artifactName.concat(triggerparameters.getBranchOrTag());
				} else {
					artifactName = artifactName.concat("default");
				}
				artifactName = artifactName.concat("-");
				artifactName = artifactName.concat(buildJson.getString("nextBuildNumber"));
			} else
				artifactName = triggerparameters.getDeploy().getDeployArtifact().getArtifactName();
			environmentDetails.insertArtifact(envId, releaseId, artifactName, "imported");
			int artifacID = environmentDetails.getArtifactId(artifactName);
			if (triggerparameters.getBuild() != null)
				environmentDetails.insertArtifactDetails(artifacID, "Build", "Built Through Trigger Page",
						triggerparameters.getEnvSelected(), userName);
			if (triggerparameters.getDeploy() != null)
				environmentDetails.insertArtifactDetails(artifacID, "Deploy", "Deployed Through Trigger Page",
						triggerparameters.getEnvSelected(), userName);
			triggerparameters.setArtifactName(artifactName);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public String apprRejectJobs(ApproveBuildParams approveBuildParams, String to) {

		return orchConn.apprRejectJobs(approveBuildParams, to,configmanager.getJenkinsuserid());
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

	private String getFullJobJSON(StringBuilder jobUrl) {
		StringBuilder json = new StringBuilder();
		try {
			URL url = new URL(jobUrl.toString() + "/api/json?pretty=true");
			logger.info(jobUrl.toString());
			String line = "";
			String s = configmanager.getJenkinsuserid() + ":" + configmanager.getJenkinspassword();
			String encoding = Base64.getEncoder().encodeToString(s.getBytes());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);
			connection.setRequestProperty(AUTHORIZATION, BASIC + encoding);
			InputStream content = connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			try {
				while ((line = (in.readLine())) != null) {
					logger.info("jobs are :" + line);
					json.append(line);
				}
				return json.toString();
			} finally {
				in.close();
				content.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			logger.error("Input/Output excetion :", e.getMessage());
		}
		return "";
	}

	private String emailIds(String mailed, String domain) {

		String lastMail = "";
		if (mailed != null && !"".equals(mailed)) {
			String[] array = mailed.split(",");
			Set<String> uniqueWords = new HashSet<String>(Arrays.asList(array));
			uniqueWords.remove("null");
			String str_1 = StringUtils.join(uniqueWords, ",");
			String[] comaMail = str_1.split(",");
			for (String a : comaMail) {
				if (!a.contains(domain)) {
					a += "@" + domain;
				}
				lastMail += a + ",";
			}
		}
		return lastMail;
	}

	private Boolean validateTestStep(IDPJob idpjson, ArrayList<String> testSteps) {
		try {
			boolean status = false;
			if (testSteps.isEmpty()) {
				return true;
			}
			if (idpjson != null && idpjson.getTestInfo().getTestEnv() != null
					&& idpjson.getTestInfo().getTestEnv().size() > 0) {
				for (TestEnv testenv : idpjson.getTestInfo().getTestEnv()) {
					if (testenv != null && testenv.getTestSteps() != null && testenv.getTestSteps().size() > 0) {
						for (String testStepName : testSteps) {
							if (testenv.getTestSteps().stream()
									.filter(t -> t != null && !StringUtils.isEmpty(t.getStepName())
											&& testStepName.equalsIgnoreCase(t.getStepName()))
									.count() > 0) {
								status = true;
							} else {
								status = false;
							}
						}
						if (status) {
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return true;
	}
}
