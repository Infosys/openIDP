/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.schedulerservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;

import org.infy.idp.configure.ConfigurationManager;
import org.infy.idp.configure.PostGreSqlDbContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtifactHistory {

	
	private static final String JOB_URL = "/job/";
	private static final String AUTHORIZATION = "Authorization";
	private static final String BASIC = "Basic ";
	private static final String APPR_STEP = "/input/IDPApproval/";
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String AND_CLAUSE = " AND ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	private static final String APPLICATION_NAME = " application_name LIKE ? ";
	protected final String ERROR1 = "Postgres Error while fetching user details:";
	protected Logger logger=  LoggerFactory.getLogger(ArtifactHistory.class);

	/**
	 * 
	 * getSeedJobs
	 * 
	 * @param jobName the String
	 * 
	 * Method is used to get build numbers for the given jobs
	 *
	 * */
	
	
	@Autowired
	private ConfigurationManager configmanager;
	
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	
	public String getJobJSON(String jobName,String param){
		
		StringBuilder jobUrl = new StringBuilder();
		jobUrl.append(configmanager.getJenkinsurl());
		jobUrl.append(JOB_URL);
		jobUrl.append(jobName);
		logger.debug("Base Job Url:"+jobUrl);
		if("job".equalsIgnoreCase(param))
		{
			
			jobUrl.append("/api/json?tree=jobs[name]");
			logger.debug("job url " + jobUrl);
		}
		else if("builds".equalsIgnoreCase(param))
		{
			
			jobUrl.append("/api/json?tree=builds[number]");
			logger.debug("builds url " + jobUrl);
		}
		else if("builds_Pipeline".equalsIgnoreCase(param))
		{
			
			jobUrl.append(JOB_URL+jobName+"_Pipeline/api/json?tree=builds[number]");
			logger.debug("builds url " + jobUrl);
		}
		else if("lastBuild".equalsIgnoreCase(param))
		{
			
			jobUrl.append(JOB_URL+jobName+"_Pipeline/api/json?tree=lastBuild[number]");
			logger.debug("last build url " + jobUrl);
		}
		else if("nextBuild".equalsIgnoreCase(param))
		{
			
			jobUrl.append(JOB_URL+jobName+"_Pipeline/api/json?pretty=true");
			logger.debug("next build url " + jobUrl);
		}
		else if("nextBuild_Pipeline".equalsIgnoreCase(param))
		{
			jobUrl.append(JOB_URL+jobName+"_Pipeline/api/json?pretty=true");
			logger.debug("nextBuild_Pipeline url " + jobUrl);
		}
		else if ("buildable".equalsIgnoreCase(param))
		{
			
			jobUrl.append("_Main/api/json?pretty=true");
			logger.debug("buildable url "+jobUrl);
		}else if (param.contains("ApprovalCheck"))
		{
			jobUrl.append(JOB_URL+param.split(";")[2]+param.split(";")[0]+APPR_STEP);
			logger.debug("buildable url "+jobUrl);
			
		}else if (param.contains("apprNext;"))
		{   if("BUILD".equalsIgnoreCase(param.split(";")[1])) {
					jobUrl.append(JOB_URL+jobName+"_Build");
					//jobUrl.append(JOB_URL);
					//jobUrl.append(jobName);
				}else if("DEPLOY".equalsIgnoreCase(param.split(";")[1])) {
					String workenv= param.split(";")[2];
					jobUrl.append(JOB_URL+jobName+"_Deploy_"+workenv);
					jobUrl.append(JOB_URL+jobName+"_Deploy_"+workenv);
					//jobUrl.append(JOB_URL);
		
				}
			jobUrl.append("/api/json?tree=nextBuildNumber");
			logger.debug("buildable url "+jobUrl);
		}else if (param.contains("getJson;"))
		{
			jobUrl.append("/job/"+param.split(";")[2]+param.split(";")[0]);
			jobUrl.append("/api/json");
			logger.debug("buildable url "+jobUrl);
		}
		
		try
		{
			URL url = new URL (jobUrl.toString()); 
			logger.info("JobURL: ",jobUrl);
			StringBuilder json=new StringBuilder();
		    String line="";   
			String s = configmanager.getJenkinsuserid()+":"+configmanager.getJenkinspassword();
			String encoding = Base64.getEncoder().encodeToString(s.getBytes());
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setDoOutput(false);
	        connection.setRequestProperty  (AUTHORIZATION, BASIC + encoding);
	        InputStream content = (InputStream)connection.getInputStream();
	        BufferedReader in = new BufferedReader (new InputStreamReader (content));
	        
	        try{
	        	while ((line=(in.readLine())) != null) {
	        		logger.info("jobs are :" + line);  
	        		json.append(line);
	        		
	        	}
	        	return json.toString();
	        }finally{
	        	in.close();
	        	content.close();
	        	in = null;
	        	content=null;
	        }
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
			logger.error("malformed url",e.getMessage());
		}
		catch( IOException e)
		{
			logger.error("IO Exeption!!",e);
		}
		return "";
		
	}
	
	public Integer updateTriggerHistory(int id, String artifactName)
	  {
		  	Integer productId =null ;
		   
		  	ResultSet rs = null;
		   String queryStatement ="Update ttrigger_history set artifact_name = ? where trigger_id=?";
		   try (Connection connection = postGreSqlDbContext.getConnection();
		        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
		    	preparedStatement.setString(1, artifactName);
		    	preparedStatement.setInt(2,id);
		    	preparedStatement.executeUpdate();
		    	
		    	rs = preparedStatement.getGeneratedKeys();
		    	if (rs.next()) {
		    	    productId = (int) rs.getLong(1);
		    	}
		     }		     
		    
		     catch (SQLException | NullPointerException e) {
		          
		      return -1;
		    }finally {
				if(rs!=null) {
					try {
						rs.close();
					} catch (SQLException e) {
						  logger.error("Postgres Error while closing resultset :",e);	
					}
				}
			}
		    
		  return productId;	  
		   
		 
	  }
	public int getReleaseId(String appName, String pipelineName, String releaseNumber ,String status)
			throws SQLException {
		
		int releaseId=0;		
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("pipeline_name , release_number, vsts_release_number, ");
		queryStatement.append(" expected_start_date, expected_end_date, actaul_start_date, actual_end_date, ");
		queryStatement.append("remarks, branch_list, release_id, status , email ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append("public.tpipeline_info,tapplication_info,trelease_info ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("tapplication_info.application_id = tpipeline_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.application_id = trelease_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.pipeline_id = trelease_info.pipeline_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tapplication_info.application_name LIKE ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.pipeline_name LIKE ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("status LIKE ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("release_number LIKE ? ");
		queryStatement.append(";");
		
		ResultSet rs = null;
		
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {

			preparedStatement.setString(1, appName);
			preparedStatement.setString(2, pipelineName);
			preparedStatement.setString(3, status);
			preparedStatement.setString(4, releaseNumber);
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				releaseId = rs.getInt("release_id");
			}
		}
		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching release_ID:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		
		
		return releaseId;
		
	}
	
	 /**
	   * get application id.

	   * 
	   * @param applicationName the String
	   * 
	   *  @return applicationId the Long

	   */ 
	  public Long getApplicationId(String applicationName)
	  {
		  
		  String tableName = "tapplication_info";
		  String column = " application_id ";
		  Long applicationId= -1l;
		   StringBuilder queryStatement = new StringBuilder();
		   queryStatement.append(SELECT_CLAUSE+column+FROM_CLAUSE+tableName+WHERE_CLAUSE+APPLICATION_NAME);

			
			ResultSet rs= null;

		   try (Connection connection = postGreSqlDbContext.getConnection();
		        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
		    	preparedStatement.setString(1,applicationName);
		    	

		    	rs = 	preparedStatement.executeQuery();

		    	while(rs.next())
		    	{
		    		applicationId=rs.getLong(1);
		    	}
		    	
		     }		     
		    


		     catch(SQLException | NullPointerException e) {
		      logger.error("Postgres Error while fetching data from tapplication_info:",e);
		    }

		   finally{
		   		if(rs != null){
		   			try{
		   			rs.close();
		   			}
		   			catch(SQLException e){
		   				logger.error("Postgres Error while closing resultset :",e);
		   			}
		   		}
		   	}

		   return applicationId;
		  
	  }
	  
	  public int getEnvironmentId(String environmentName, int applicationId) throws SQLException {

			
			int envId = -1;
			StringBuilder queryStatement = new StringBuilder();

			queryStatement.append(SELECT_CLAUSE);
			queryStatement.append(" env_id ");
			queryStatement.append(FROM_CLAUSE);
			queryStatement.append(" public.tenvironment_master ");
			queryStatement.append(WHERE_CLAUSE);
			queryStatement.append(" environment_name LIKE ? ");
			queryStatement.append(AND_CLAUSE);
			queryStatement.append(" application_id = ? ; ");

			ResultSet rs =null;
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

				preparedStatement.setString(1, environmentName);
				preparedStatement.setInt(2, applicationId);
				rs = preparedStatement.executeQuery();

				while (rs.next()) {
					envId = rs.getInt("env_id");
				}

				return envId;
			} catch (SQLException e) {
				logger.error("Postgres Error while fetching permissions:", e);
				throw e;
			}  finally{
		   		if(rs != null){
		   			try{
		   			rs.close();
		   			}
		   			catch(SQLException e){
		   				logger.error("Postgres Error while closing resultset :",e);
		   			}
		   		}
		   	}

		}
	  
	  public void insertArtifacttoData(int envId , int releaseId , String artifactName , String status) throws SQLException{
					
			StringBuilder queryStatement = new StringBuilder();
			
			queryStatement.append("INSERT INTO public.tartifact_approval( ");
			queryStatement.append(" artifact_name, release_id, env_id, status) ");
			queryStatement.append(" VALUES ( ? , ?, ?, ? ) ");
			queryStatement.append(" ON CONFLICT (artifact_name , release_id , env_id ) DO UPDATE ");
			queryStatement.append(" SET artifact_name = ? ; ");
			
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

				
				
				preparedStatement.setString(1, artifactName);
				preparedStatement.setInt(2, releaseId);
				preparedStatement.setInt(3, envId);
				preparedStatement.setString(4, status);
				preparedStatement.setString(5, artifactName);
				preparedStatement.executeUpdate();
				

			} catch (SQLException | NullPointerException e) {
				logger.error("Postgres Error while fetching permissions:", e);
				throw e;
			}
			
		}
	  
	  public int getArtifactId(String artifactName) throws SQLException {

			int artifactId = -1;
			StringBuilder queryStatement = new StringBuilder();

			queryStatement.append(SELECT_CLAUSE);
			queryStatement.append(" artifact_id ");
			queryStatement.append(FROM_CLAUSE);
			queryStatement.append(" public.tartifact_approval ");
			queryStatement.append(WHERE_CLAUSE);
			queryStatement.append(" artifact_name LIKE ? ;");
			

			ResultSet rs =null;
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

				preparedStatement.setString(1, artifactName);
				rs = preparedStatement.executeQuery();

				while (rs.next()) {
					artifactId = rs.getInt("artifact_id");
				}

				return artifactId;
			} catch (SQLException e) {
				logger.error("Postgres Error while fetching permissions:", e);
				throw e;
			}  finally{
		   		if(rs != null){
		   			try{
		   			rs.close();
		   			}
		   			catch(SQLException e){
		   				logger.error("Postgres Error while closing resultset :",e);
		   			}
		   		}
		   	}
	  }

	  public void insertArtifactDetails(int artifactId , String status , String remark , String environment , String env_owner ) throws SQLException{
			
	
			StringBuilder queryStatement = new StringBuilder();
			
			queryStatement.append("INSERT INTO public.tartifact_history( ");
			queryStatement.append(" artifact_id, status, remark, environment, env_owner, action_time) ");
			queryStatement.append(" VALUES ( ?, ?, ?, ?, ?, ?) ");
			
			
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

				
				
				preparedStatement.setInt(1, artifactId);
				preparedStatement.setString(2, status);
				preparedStatement.setString(3, remark);
				preparedStatement.setString(4, environment);
				preparedStatement.setString(5, env_owner);
				Timestamp currentTime = new Timestamp(System.currentTimeMillis());
				preparedStatement.setTimestamp(6, currentTime);
				preparedStatement.executeUpdate();
				

			} catch (SQLException | NullPointerException e) {
				logger.error("Postgres Error while fetching permissions:", e);
				throw e;
			}
			
		}
	  /**
		 * 
		 * Inserting artifacts
	 * @throws JSONException 
		 * 
		 * */
		
		public void insertArtifact(JSONObject jsonInput,net.sf.json.JSONObject buildJson ,String userName) throws JSONException
		{
			try {
				int envId = 0;
				int releaseId = getReleaseId(jsonInput.getString("applicationName"),
						jsonInput.getString("pipelineName"), jsonInput.getString("releaseNumber"), "on");
				int applicationId = getApplicationId(jsonInput.getString("applicationName")).intValue();
				if(jsonInput.has("envSelected") && !"".equals(jsonInput.getString("envSelected")))
					envId = getEnvironmentId(jsonInput.getString("envSelected"), applicationId);

				String artifactName;
				if (jsonInput.has("build")) {
					artifactName =  jsonInput.getString("applicationName")+ "_" + jsonInput.getString("pipelineName")
							+ "_" + jsonInput.getString("releaseNumber") + "-" ;
					if(jsonInput.has("branchOrTag") && !"".equals(jsonInput.getString("branchOrTag"))) {
						artifactName = artifactName.concat(jsonInput.getString("branchOrTag"));
					}
					else {
						artifactName = artifactName.concat("default");
						
					}
					artifactName = artifactName.concat("-");
					artifactName = artifactName.concat(buildJson.getString("nextBuildNumber"));
				}
				else
					artifactName = jsonInput.getJSONObject("deploy").getJSONObject("deployArtifact").getString("artifactName");
				insertArtifacttoData(envId, releaseId, artifactName, "imported");
		
				int artifacID = getArtifactId(artifactName);
				
				if(jsonInput.has("build"))
					insertArtifactDetails(artifacID, "Build", "Builded Through Trigger Page",
							jsonInput.getString("envSelected"), userName );
				
				if(jsonInput.has("deploy"))
					insertArtifactDetails(artifacID, "Deploy", "Deployed Through Trigger Page",
							jsonInput.getString("envSelected"), userName );


			}
			catch(SQLException e){
				logger.error(e.getMessage());
				
			}
		}
		
		
	
}

