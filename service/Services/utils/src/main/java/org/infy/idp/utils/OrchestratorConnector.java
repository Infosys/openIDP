/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.Map;


import org.infy.idp.entities.models.BuildStatus;

import org.infy.idp.entities.triggerparameter.ApproveBuildParams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;




@Component
public class OrchestratorConnector {
	protected Logger logger = LoggerFactory.getLogger(OrchestratorConnector.class);
	private static final String AUTHORIZATION = "Authorization";
	private static final String BASIC = "Basic ";
	private static final String TARGETPLATFORM = "targetplatform";
	private static final String JOBNAME = "jobName";
	private static final String JENKINS = "jenkins";
	private static final String JENKINSURL ="jenkinsURL";
	@Autowired
	private ConfigurationManager configmanager;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private GitLabBranchTagFetcher gitLabBranchTagFetcher;
	
	@Autowired
	public RestTemplate restTemplate;
	
    @Bean
    public RestTemplate getRestTemplate()
    {
    	return new RestTemplate();
    	
    }
    
	// Async Method Calls Go Here
	
	public void sendKafkaMessage(String message) {
		kafkaTemplate.send("ci_ops", message);
	}
	
	
	// Sync Method Calls Go here
	private String restCallToOrchestrator(String path, String jsonData) {
		try {
			String url = configmanager.getOrchestratorUrl() + path;
			logger.info("THE APPROVE PATH::::::  " +path);
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<String>(jsonData,headers);
	        String response = restTemplate.postForObject(url, entity, String.class);
	        logger.info("Response From Orchestrator",response);
	        return response;
		  }
	    catch(Exception e)
	    {
	    	logger.error("Error occurred while communication with Orchestrator",e);
	    	return "-1";
	    }
	}
	
	public int disableJob(String jobName,String jobConfigFile,String jenkinsURL) throws FileNotFoundException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TARGETPLATFORM, JENKINS);
		map.put(JOBNAME, jobName);
		map.put("jobConfigFile", jobConfigFile);
		map.put(JENKINSURL, jenkinsURL);
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(map);
		return Integer.parseInt(restCallToOrchestrator("/job/apprRejectJobs", jsonData));
	}
	
	public int addArtifactoryRepoGlobConf(String repoUrl
			, String user, String pwd, String repoName,String jenkinsURL) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TARGETPLATFORM, JENKINS);
		map.put("repoUrl", repoUrl);
		map.put("user", user);
		map.put("pwd", pwd);
		map.put("repoName", repoName);
		map.put(JENKINSURL, jenkinsURL);
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(map);
		return Integer.parseInt(restCallToOrchestrator("/artifacts/add", jsonData));
	}
	
	public int createRole(String roleName
			, String usersList
			, String pattern
			, String roleType
			, String permissions,String jenkinsURL) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TARGETPLATFORM, JENKINS);
		map.put("roleName", roleName);
		map.put("usersList", usersList);
		map.put("pattern", pattern);
		map.put("roleType", roleType);
		map.put("permissions", permissions);
		map.put(JENKINSURL, jenkinsURL);
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(map);
		return Integer.parseInt(restCallToOrchestrator("/role/create", jsonData));
	}
	
	public int copySlave(String slaveName
			, String workspace
			, String slaveLabel
			, String sshKeyPath
			, String jenkinsURL) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TARGETPLATFORM, JENKINS);
		map.put("slaveName", slaveName);
		map.put("workspace", workspace);
		map.put("slaveLabel", slaveLabel);
		map.put("sshKeyPath", sshKeyPath);
		map.put(JENKINSURL, jenkinsURL);
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(map);
		return Integer.parseInt(restCallToOrchestrator("/slave/copy", jsonData));
	}
	
	public String addALMConfig(String inputalmServerName
			, String almServerUrl, String jenkinsURL) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TARGETPLATFORM, JENKINS);
		map.put("inputalmServerName", inputalmServerName);
		map.put("almServerUrl", almServerUrl);
		map.put(JENKINSURL, jenkinsURL);
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(map);
		return restCallToOrchestrator("/config/add/alm/config", jsonData);
	}
	
	public String getStageViewUrl(String appName
			, String pipelineName,String jenkinsURL) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TARGETPLATFORM, JENKINS);
		map.put("appName", appName);
		map.put("pipelineName", pipelineName);
		map.put(JENKINSURL, jenkinsURL);
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(map);
		return restCallToOrchestrator("/config/get/stageviewurl", jsonData);
	}
	
	public String getJobJSON(String jobName
			, String param, String jenkinsURL) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TARGETPLATFORM, JENKINS);
		map.put(JOBNAME, jobName);
		map.put("param", param);
		map.put(JENKINSURL, jenkinsURL);
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(map);
		return restCallToOrchestrator("/config/get/jobjson", jsonData);
	}
	
	public String downloadArtifacts(String daiJobName
			, String daiBuildNumber
			, String daiSubJobName){
		Map<String, String> map = new HashMap<String, String>();
		map.put(TARGETPLATFORM, JENKINS);
		map.put("daiJobName", daiJobName);
		map.put("daiBuildNumber", daiBuildNumber);
		map.put("daiSubJobName", daiSubJobName);
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(map);
		return restCallToOrchestrator("/artifacts/download", jsonData);
	}
	
	public String apprRejectJobs(ApproveBuildParams approveBuildParams
			, String to,String jenkinsURL) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TARGETPLATFORM, JENKINS);
		map.put("approveBuildParams", approveBuildParams);
		map.put("to", to);
		map.put(JENKINSURL, jenkinsURL);
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(map);
		return restCallToOrchestrator("/job/approve", jsonData);
	}
	
	public BuildStatus getBuildStatus(String jobName
			, int buildnumber
			, long whenToTimeout,String jenkinsURL) throws URISyntaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TARGETPLATFORM, JENKINS);
		map.put(JOBNAME, jobName);
		map.put("buildnumber", buildnumber);
		map.put("whenToTimeout", whenToTimeout);
		map.put(JENKINSURL, jenkinsURL);
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(map);
		try {
			BuildStatus response = gson.fromJson(restCallToOrchestrator("/job/get/status", jsonData), BuildStatus.class );
			logger.info("Build Status for {} : {}" , jobName, gson.toJson(response));
			return response;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public String getSlaveStatus(String slaveName) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TARGETPLATFORM, JENKINS);
		map.put("slaveName", slaveName);
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(map);
		return restCallToOrchestrator("/slave/get/status", jsonData);
	}
	




	
	

}
