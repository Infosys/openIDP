/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.utils.vsts;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.infy.idp.dataapi.InsertFetchVSTS;
import org.infy.idp.entities.VSTSDataBean;
import org.infy.idp.utils.ConfigurationManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *class DetailsForVSTSFetcher provides methods for handling VSTS details
 */
@Component
public class DetailsForVSTSFetcher {
	
	private final Logger logger=LoggerFactory.getLogger(DetailsForVSTSFetcher.class);
	
	@Autowired
	private InsertFetchVSTS insertFetchVSTS;
	
	@Autowired 
	private ConfigurationManager configurationManager;
	
	private static String buildNoStr = "PIPELINE_BUILD_ID";
	 private static String apiString = "/api/json?tree=builds[number,timestamp,id,result,building,actions[parameters[name,value]]]";
		

	 /**
	  * 
	  * @param trigger
	  * @param buildNum
	  * @return
	  */
	public int createVSTSUpdateList(String trigger, String buildNum) {
		List<VSTSDataBean> vstsDataListFromDB = new ArrayList<>(); 
		Integer triggerId  = Integer.parseInt(trigger);
		int stat = -1;
		//fetching json from db 
		
		try {
			JSONObject json = fetchJson(triggerId);
			String appName = json.getString("applicationName");
			String pipName = json.getString("pipelineName");
			String buildNumber = buildNum;
			String jenkinsURL = configurationManager.getJenkinsURL()+"/job/"+appName+"_"+pipName;
			logger.info("Jenkins URL for Job: "+jenkinsURL);
			String userName = configurationManager.getJenkinsID();
			String password = configurationManager.getJenkinsPassword();

//			logger.info("Job "+appName+"_"+pipName+" is triggered.");
			String jobname = appName+"_"+pipName+"_Pipeline";
			String executionLink = jenkinsURL+"/job/"+jobname+"/"+buildNumber+"/console";
			logger.info("PIPELINE URL : "+executionLink);
			String workitemsStr = updateJobStatusToDB(appName, pipName, jenkinsURL,userName,password,executionLink,triggerId,buildNumber,json);
			if(workitemsStr	!= null && !workitemsStr.contentEquals("") && !workitemsStr.contentEquals("NA")) {
				logger.info("Workitem(s): "+workitemsStr);
				try {
					if(workitemsStr.contains("\"")) {
						workitemsStr = workitemsStr.replace("\"", "");
					}
					String [] workitems = workitemsStr.trim().split(",");
					for(String workItem : workitems){
						logger.info("workitem to VSTS update : "+workItem);
						String val = workItem.trim();
						logger.info("workitem to VAl update : "+val);
						if((!"".equalsIgnoreCase(val)) && (!"NA".equalsIgnoreCase(val) && (!"-1".contentEquals(val))) && (val.matches("[0-9]+")) ){
							vstsDataListFromDB = insertFetchVSTS.getPipelinesForWorkitem(val);
							logger.info("List size: "+vstsDataListFromDB.size());
							UpdateVSTS.restServiceUpdate(vstsDataListFromDB,val);
						}
						stat = 1;
					}	
				}catch(Exception e) {
					logger.error("Error: ",e);
				}
				
			}
			
			
			
		} catch (JSONException e) {

			logger.error(e.getMessage());
			
		}
		return stat;
	}

	
	
	/**
	 * updates job status for a specific triggerId
	 * @param appName
	 * @param pipName
	 * @param jenkinsURL
	 * @param userName
	 * @param password
	 * @param executionLink
	 * @param triggerId
	 * @param buildNumber
	 * @param triggerEntity
	 * @return
	 */
	private String updateJobStatusToDB(String appName, String pipName, String jenkinsURL, String userName,
			String password, String executionLink, Integer triggerId, String buildNumber, JSONObject triggerEntity) {

		logger.info("Updating values for trigger: "+ triggerId); 
		
		try {
			List<String> deploySteps=null; 
			JSONArray deployArr = null;
			String artifactName = null;
			JSONObject deploy = null;
			
			if(triggerEntity.has("deploy") ){
				deploy = triggerEntity.getJSONObject("deploy");
				if(deploy !=null ){
					if(deploy.has("deployStep") ){
						deployArr = triggerEntity.getJSONObject("deploy").getJSONArray("deployStep");
					}
					if(deploy.has("deployArtifact") && deploy.getJSONObject("deployArtifact")!=null && (deploy.getJSONObject("deployArtifact").has("artifactName"))){
						
						artifactName = deploy.getJSONObject("deployArtifact").getString("artifactName");
						
					}
					
				}
				
			}
			
			if(deployArr!=null && deployArr.length()>0) {
			 deploySteps = new ArrayList<>(); 
			 for(int i=0; i< deployArr.length(); i++) {
				 deploySteps.add(deployArr.getString(i));
			 	}
			}
			
			 
			List<String> testSteps = null;
			JSONArray testArr = triggerEntity.has("testStep")?triggerEntity.getJSONArray("testStep"):null;
			
			if(testArr!=null && testArr.length()>0) {
				testSteps = new ArrayList<>(); 
				 for(int i=0; i< testArr.length(); i++) {
					testSteps.add(testArr.getString(i));
			   	 	}
			   	}
			
			
			String env = triggerEntity.has("envSelected") ? triggerEntity.getString("envSelected").replace("\"",""):null;
			logger.info(env);
			String envSelected="";
			if(env==null || env.equals(""))
				envSelected="NA";
			else {
				envSelected = env;
			}
			String tfsWorkItem = triggerEntity.has("tfsWorkItem")? triggerEntity.getString("tfsWorkItem").replace("\"",""):null;
			String jiraProjectKey=triggerEntity.has("jiraProjectKey")? triggerEntity.getString("jiraProjectKey").replace("\"",""):null;
			String userStoryString=triggerEntity.has("userStoryString")? triggerEntity.getString("userStoryString").replace("\"",""):null;
			String artifactorySelected = triggerEntity.has("artifactorySelected") ? triggerEntity.getString("artifactorySelected").replace("\"",""):null;
			
			
						
			JSONObject build = triggerEntity.has("build")?triggerEntity.getJSONObject("build"):null;
			
			String test = triggerEntity.has("testSelected")?triggerEntity.getString("testSelected"):null;
			
			String branchOrTagValue=triggerEntity.has("branchOrTagValue")?triggerEntity.getString("branchOrTagValue"):null;
			
			logger.info("branchOrTagValue : "+branchOrTagValue);
			if(branchOrTagValue==null || branchOrTagValue.isEmpty() || branchOrTagValue.equals("")){
				branchOrTagValue=triggerEntity.has("scmBranch")?triggerEntity.getString("scmBranch"):null;
			}
			
			
			String scmJobUrl = jenkinsURL+"/job/"+appName+"_"+pipName+"_SCM"+apiString;
			String scmStatus = null;
			
			String scmJobUrlDeploy = jenkinsURL+"/job/"+appName+"_"+pipName+"_SCM_DEPLOY"+apiString;
			String scmStatusDeploy = null;
			
			String scmJobUrlTest = jenkinsURL+"/job/"+appName+"_"+pipName+"_SCM_TEST"+apiString;
			String scmStatusTest = null;
			
			
				
			String buildBuildStatus ;
			String deployStatus="NA";
			String testStatus= "NA";
			String artifactUploadStatus;
			String artifactDownloadStatus="NA";
			String artifactLink="NA";
			
			//for checking if nexus is present
			boolean isArtifactorySelected = artifactorySelected !=null && artifactorySelected.equalsIgnoreCase("on");
			logger.info("artifact section: "+isArtifactorySelected);
			/////for build
			if(build!=null){
				scmStatus = getBuildStatus(scmJobUrl, userName,password,buildNumber);
				if(scmStatus!=null && scmStatus.equals("SUCCESS")){
					String buildJobUrl = jenkinsURL+"/job/"+appName+"_"+pipName+"_Build"+apiString;
					buildBuildStatus = getBuildStatus(buildJobUrl, userName,password,buildNumber);							
					logger.info("Build Status is : "+buildBuildStatus);
					
					//if artifactoryseleted is on
					if(buildBuildStatus!=null && buildBuildStatus.equals("SUCCESS") && isArtifactorySelected){					
						String artifactUploadName = jenkinsURL+"/job/"+appName+"_"+pipName+"_ArtifactUpload"+apiString;								
						artifactUploadStatus =  getBuildStatus(artifactUploadName, userName,password,buildNumber);				 
						logger.info("ArtiUpload Status : "+artifactUploadStatus);
						if(artifactUploadStatus==null || !artifactUploadStatus.equals("SUCCESS")){					 

							 buildBuildStatus = "FAILURE";
							 logger.info("Build Status is set to FAILURE as artifactUploadStatus failed");
							 
							 
						 }
					}
					
					
					
				}else{
					buildBuildStatus = "FAILURE";
					logger.info("Build Status is set to FAILURE as SCM checkout failed");
				}
				
			}else{
				buildBuildStatus = "NA";
				logger.info("Build Status is NA as build is not selected");
			}
			
			
			//to check if build is valid 
			boolean checkBuildValidations = build!=null && buildBuildStatus.equals("SUCCESS");
			
			if(deploy!=null){
				if(checkBuildValidations || build==null){
					
					scmStatusDeploy = getBuildStatus(scmJobUrlDeploy, userName,password,buildNumber);
					if(scmStatusDeploy!=null && scmStatusDeploy.equals("SUCCESS")){
						//if artifactory selected
						if(isArtifactorySelected){
							 
							String artifactDownloadName = jenkinsURL+"/job/"+appName+"_"+pipName+"_ArtifactDownload"+apiString;						
							artifactDownloadStatus = getBuildStatus(artifactDownloadName,userName,password,buildNumber);
							if(artifactDownloadStatus==null || !artifactDownloadStatus.equals("SUCCESS")){				 
						
								deployStatus= "FAILURE";
								logger.info("Artifact Download Status : "+artifactDownloadStatus);
								logger.info("Deploy Status set to FAILURE as artifact download failed");
							 }
						}
						
						boolean checkDeployValidations = isArtifactorySelected && artifactDownloadStatus!=null &&
								artifactDownloadStatus.equals("SUCCESS") ;
						
						if(checkDeployValidations || !isArtifactorySelected){						
							
							String deployName =  jenkinsURL+"/job/"+appName+"_"+pipName+"_Deploy_"+envSelected+"/job/"+appName+"_"+pipName+"_Deploy_"+envSelected;
							
							if(deploySteps!=null && deploySteps.isEmpty()){
								for(int i = 0;i<deploySteps.size();i++){
									String deployEnvName=deployName+"_"+deploySteps.get(i)+apiString;
									if(i==0){								
										deployStatus = getBuildStatus(deployEnvName,userName,password,buildNumber);	
										logger.info("Delpoy Status for Step "+deploySteps.get(i)+" is: "+deployStatus);
									}
									else{
										if(deployStatus!=null && deployStatus.equals("SUCCESS")){
											deployStatus = getBuildStatus(deployEnvName,userName,password,buildNumber);			
											logger.info("Delpoy Status for Step "+deploySteps.get(i)+" is: "+deployStatus);
										}
									}
									
									
									if(deployStatus==null || !deployStatus.equals("SUCCESS")){
										deployStatus = "FAILURE";
										logger.info("Delpoy Status set to FAILURE as Step "+deploySteps.get(i)+" is: "+deployStatus);
										break;
									}	
											
										
									
								}				
							}
							else{
								deployStatus = "NA";
								logger.info("Delpoy Status is : "+deployStatus+" since no steps");
							}
						
						}
						
					}
					else{
						deployStatus= "FAILURE";						
						logger.info("Deploy Status set to FAILURE as Deploy SCM checkout failed");
					}
					
					
					
				}else{
					deployStatus = "NA";
					logger.info("Deploy Status is NA as build stage failed");
				}
				
			}else{
				deployStatus = "NA";
				logger.info("Deploy Status is NA as deploy is not selected");
			}
			
			boolean checkDeployValidations = deploy!=null && deployStatus.equals("SUCCESS");
			boolean checkTestValidations = (test !=null && test.equalsIgnoreCase("on"));
			boolean test1 = build==null && checkTestValidations;
			boolean test2 = deploy==null && checkTestValidations;
			boolean test3 = (checkBuildValidations && checkDeployValidations && checkTestValidations);
			
			//for test
				
			if(test3 || test1 || test2){
				scmStatusTest = getBuildStatus(scmJobUrlTest, userName,password,buildNumber);
				if(scmStatusTest!=null && scmStatusTest.equals("SUCCESS")){
					String testName =  jenkinsURL+"/job/"+appName+"_"+pipName+"_Test_"+envSelected+"/job/"+appName+"_"+pipName+"_Test_"+envSelected;									
					if(testSteps!=null && testSteps.isEmpty()){
						for(int i = 0;i<testSteps.size();i++){	
							String testStepName=testName+"_"+testSteps.get(i)+apiString;
							logger.info(testStepName);
							if(i==0){								
								testStatus = getBuildStatus(testStepName, userName,password,buildNumber);
								logger.info("Test Status for Step "+testSteps.get(i)+" is: "+testStatus);
							}
							else{
								if(testStatus!=null && testStatus.equals("SUCCESS")){
									testStatus = getBuildStatus(testStepName, userName,password,buildNumber);
									logger.info("Test Status for Step "+testSteps.get(i)+" is: "+testStatus);
								}
							}
						
							if(testStatus!=null && !testStatus.equals("SUCCESS")){
								testStatus = "FAILURE";
								logger.info("Test Status set to FAILURE as Step "+testSteps.get(i)+" is: "+testStatus);
								break;
							}
									
								
							
						}
					}
					else{
						testStatus = "NA";
						logger.info("Test Status : "+testStatus+" as no test steps");	
					}
				
					
				}else{
					testStatus= "FAILURE";						
					logger.info("Test Status set to FAILURE as Test SCM checkout failed");
					
				}
				
			}else{
				if(!test1 || !test2){
					testStatus = "NA";
					logger.info("Test Status is NA as test is not selected");
				}else{
					if(!checkBuildValidations){
						testStatus = "NA";
						logger.info("Test Status is NA as build stage failed");
					}
					else if(!checkDeployValidations){
						testStatus = "NA";
						logger.info("Test Status is NA as deploy stage failed");
					}
				}
				
			}
			
			//for Artifact 
			
			
			
			if(isArtifactorySelected && checkBuildValidations){
				String nameForArtifactCheck=appName+"_"+pipName+"_latestArtifact";
				logger.info("artifactName" +artifactName);
				if(null!=artifactName && artifactName.equals(nameForArtifactCheck)){
					String nexusUrl=null;
					if(deploy.has("deployArtifact") && deploy.getJSONObject("deployArtifact").has("nexusURL")){
						nexusUrl = deploy.getJSONObject("deployArtifact").getString("nexusURL");
					}
					String repoName=null;
					if(deploy.has("deployArtifact") && deploy.getJSONObject("deployArtifact").has("repoName")){
						repoName = deploy.getJSONObject("deployArtifact").getString("repoName");
					}					
			 		
			 		artifactLink = "http://"+nexusUrl+ "/repository/"+repoName+"/"+
							 appName+"/"+pipName+"/"+buildNumber+"/"+pipName+"-"+buildNumber+".zip";						
					 artifactName = appName+"/"+pipName+"/"+buildNumber+"/"+pipName+"-"+buildNumber;
				}
				else{
					if(deploy!=null) {
					if(deploy.has("deployArtifact") && deploy.getJSONObject("deployArtifact").has("artifactName")){
						artifactName = deploy.getJSONObject("deployArtifact").getString("artifactName");
					}
					if(deploy.has("deployArtifact") && deploy.getJSONObject("deployArtifact").has("downloadURL")){
						artifactLink = deploy.getJSONObject("deployArtifact").getString("downloadURL");
					}
					}
					
					
				}
				
			}
			else if(isArtifactorySelected && build==null && deploy!=null){
				if(deploy.has("deployArtifact") && deploy.getJSONObject("deployArtifact").has("artifactName")){
					artifactName = deploy.getJSONObject("deployArtifact").getString("artifactName");
				}
				if(deploy.has("deployArtifact") && deploy.getJSONObject("deployArtifact").has("downloadURL")){
					artifactLink = deploy.getJSONObject("deployArtifact").getString("downloadURL");
				}
			}
			else{
				artifactName="NA";
				artifactLink="NA";
			}
			
			String buildTriggered;
			String deployTriggered;
			String testTriggered;
			
			if(build==null){
				buildTriggered = "N";
			}
			else{
				buildTriggered ="Y";
			}
			if(deploy==null){
				deployTriggered = "N";
			}
			else{
				deployTriggered = "Y";
			}
			
			if(test==null || test.equals("off")){

				testTriggered = "N";
			}
			else{

				testTriggered= "Y";
			}
			
			logger.info("buildTriggered: "+buildTriggered);
			logger.info("deployTriggered: "+deployTriggered);
			logger.info("testTriggered: "+testTriggered);
			logger.info("Artifact name: "+artifactName);
			logger.info("Artifact Link: "+artifactLink);
			
			VSTSDataBean vstsBean = new VSTSDataBean();
			
			vstsBean.setExecNo(buildNumber);
			vstsBean.setExecNoLink(executionLink);			
			vstsBean.setScmBranch(branchOrTagValue);
			vstsBean.setArtivalue(artifactName);
			vstsBean.setArtilink(artifactLink);
			vstsBean.setEnv(envSelected);
			vstsBean.setBldstatus(buildBuildStatus);
			vstsBean.setDepStatus(deployStatus);
			vstsBean.setTstStatus(testStatus);
			vstsBean.setBuild(buildTriggered);
			vstsBean.setDeploy(deployTriggered);
			vstsBean.setTest(testTriggered);
			
			
			return insertFetchVSTS.updateJobStatus(vstsBean,tfsWorkItem,jiraProjectKey,userStoryString,triggerId);
			
			 
            
            
			

			
			
			
			
			
			
			
			
		} catch (JSONException e) {

			logger.error("JSONObject exception : "+e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e){
			logger.error("NullPointer exception : "+e.getMessage());
			e.printStackTrace();
		}
		
		return null;
		
	}
	/**
	 * returns build status for a job
	 * @param finalJobUrl
	 * @param userName
	 * @param password
	 * @param buildNumber
	 * @return
	 */
	private String getBuildStatus(String finalJobUrl, String userName, String password,String buildNumber){
		logger.info("Urls : "+finalJobUrl);
		
        String status ="NA";        
        
        try
		{
			URL url = new URL (finalJobUrl); 
			String json="";
		    String line="";   
			String s = userName+":"+password;
			logger.info("credentials : "+s);
			String encoding = new String(Base64.encodeBase64(s.getBytes()));			
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setDoOutput(false);
	        connection.setRequestProperty("Authorization", "Basic " + encoding);	        
	      
	        BufferedReader  in = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
	        logger.info(finalJobUrl);
	        try{
	        	while ((line=(in.readLine())) != null) {	        		
	        		json=json+line;	        		
	        	}
	        	logger.info(json);
	        	JSONObject jObj = new JSONObject(json);

				JSONArray buildsJsonArr = jObj.optJSONArray("builds");
				if(buildsJsonArr.length() > 0){
					logger.debug("Build Json array: "+buildsJsonArr);
				}else{
					logger.debug("Build Job array is empty");
					status = "NA";					
				}

				for(int j = 0 ; j < buildsJsonArr.length();j++){
					JSONObject buildJson = buildsJsonArr.optJSONObject(j);

					//building
					//id
					//result
					//actions
					//	- parameters, name, value

					Boolean runStatus = buildJson.optBoolean("building");
					
					String buildResult = buildJson.optString("result");
					String buildNoValue = "";
					JSONArray buildActionArr = buildJson.optJSONArray("actions");
					buildActionLoop:
						for(int k = 0 ; k < buildActionArr.length(); k++){
							if(buildActionArr.optJSONObject(k).has("parameters")){
								JSONArray buildParameterJArr = buildActionArr.optJSONObject(k).optJSONArray("parameters");
								for(int l = 0; l<buildParameterJArr.length(); l++){
									if(buildNoStr.equalsIgnoreCase(buildParameterJArr.optJSONObject(l).optString("name"))){
										buildNoValue = buildParameterJArr.optJSONObject(l).optString("value");
										break buildActionLoop;
									}

								}

							}
						}
					if(!runStatus && buildNumber.equalsIgnoreCase(buildNoValue)){
						if("success".equalsIgnoreCase(buildResult)){
							status = "SUCCESS";		
						}
						else if("unstable".equalsIgnoreCase(buildResult)){
							status = "UNSTABLE";		
						}
						else if("aborted".equalsIgnoreCase(buildResult)){
							status = "ABORTED";		
						}
						else{
							status = "FAIL";		
							
						}
						break;
					}

				}
	        	
	        	//return 
	        }finally{
	        	in.close();
	        	
	        	in = null;
	        	
	        }
		}
		catch(MalformedURLException e)
		{
			logger.error("malformed url");
		}
		catch( IOException e)
		{	
			e.printStackTrace();
			logger.error("IO Exeption!!");
		}   
        catch(JSONException e){
        	e.printStackTrace();
        	logger.error("JSON Exception : "+e.getMessage());
        }

        

		return status;
	}

	private JSONObject fetchJson(Integer triggerId) {
		
		JSONObject json = insertFetchVSTS.getTriggerJSON(triggerId);
		
		logger.info("trigger json is : "+json.toString());
		
		return json;

		
	}

}
