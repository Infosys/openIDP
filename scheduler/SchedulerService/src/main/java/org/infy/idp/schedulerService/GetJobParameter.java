/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.schedulerService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.net.util.Base64;
import org.infy.idp.configure.ConfigurationManager;
import org.infy.idp.configure.PostGreSqlDbContext;
import org.w3c.dom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import com.google.gson.Gson;


@Component
public class GetJobParameter {
	
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	
	private static final String WHERE_CLAUSE = " WHERE ";
    private static final String AND_CLAUSE = " AND ";
    private static final String SELECT_CLAUSE = " SELECT ";
    private static final String FROM_CLAUSE = " FROM ";
    private static final String APPLICATION_NAME = " application_name LIKE ? ";
    private static final String PIPELINE_NAME = " pipeline_name LIKE ? ";
    private static final String USER_ID = " user_id LIKE ? ";
    private static final String APPLICATION_ID=" tpipeline_info.application_id=tapplication_info.application_id ";
    private static final String ORDER_BY=" ORDER BY ";
    private static final String ACTIVE_PIPELINE =" and active = true ";
    protected final String ERROR1="Postgres Error while fetching user details:";
    private static final String SUB_APPLICATION_NAME = " sub_application LIKE ? ";
    private static final String TECHNOLOGY_NAME =" and technology LIKE ? ";
    private static final String INSERT_CLAUSE = "INSERT INTO  ";
	private static final String UPDATE_CLAUSE = "UPDATE  ";
	private static final String SET_CLAUSE = " SET ";
	//private BasicDataSource connectionPool = new BasicDataSource();
	@Autowired
	private ArtifactHistory artifactHistory;
	@Autowired
	private ConfigurationManager configureManager;
	
	public  void getJobParameter(String jobName, String buildId ){
		System.out.println("gotit"+configureManager.getUrl());
		JSONObject JSON_Input = null;
		try {
			JSON_Input = new JSONObject(jobName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("yoyo"+JSON_Input);
	try {
		int insertion=insertTriggerHistory(JSON_Input,buildId);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		/*String server=configureManager.getJenkinsurl();
		String username=configureManager.getJenkinsuserid();
		String password=configureManager.getJenkinspassword();
		try{
		JSONObject JSON_Input=null;
		String tempJobName = jobName.replaceAll("/", "/job/").replaceAll(" ", "%20");
		String pipelineJobName=tempJobName + "_Pipeline";
		String webPage =  server + "/job/" + tempJobName + "/job/"+pipelineJobName+"/"+buildId+"/api/json?pretty=true";
		System.out.println("ffdsf"+webPage);
		String authString = username + ":" + password;
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);

		URL url = new URL(webPage);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
		InputStream is = urlConnection.getInputStream();
		StringBuilder  stringBuilder = new StringBuilder();
		String line = null;
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {	
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}
		JSONObject jsonObj = new JSONObject(stringBuilder.toString());
		JSONArray actionList=new JSONArray();
		actionList=jsonObj.getJSONArray("actions");
		for(int i=0;i<actionList.length();i++)
		{
		JSONObject temp=new JSONObject();
		temp=actionList.getJSONObject(i);
		if(temp.has("_class")  && temp.getString("_class").equalsIgnoreCase("hudson.model.ParametersAction"))
		{
			JSONArray parameterLength=temp.getJSONArray("parameters");
			for(int j=0;j<parameterLength.length();j++)
			{
				JSONObject parameterObj=parameterLength.getJSONObject(j);
				if(parameterObj.has("name")&& parameterObj.getString("name").equalsIgnoreCase("JSON_Input"))
				{
					JSON_Input=new JSONObject(parameterObj.getString("value"));
					System.out.println("yoyo"+JSON_Input);
				int insertion=insertTriggerHistory(JSON_Input,"11");
					System.out.println("letsInsert"+insertion);
				}
			}
		}
		}
		//Element root = document.getDocumentElement();
		//System.out.println(root);
		System.out.println("Parameter retrived");
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}*/
		}
	/**
	   * method getPipelineId
	   * 
	   * @param pipelineName the String
	   * @param applicationName the String
	   * 
	   * @return pipelineId the Long
	   */
	 public Long getPipelineId(String applicationName, String pipelineName) throws SQLException
	  {
		  String tableName="tpipeline_info,tapplication_info";
		  String column="pipeline_id";		  
		  StringBuilder queryStatement = new StringBuilder();
		  Long pipelineId = -1l;
		  
		   queryStatement.append(SELECT_CLAUSE);
		   queryStatement.append(column);
		   queryStatement.append( FROM_CLAUSE+tableName);
		   queryStatement.append( WHERE_CLAUSE+APPLICATION_ID);
		   queryStatement.append( AND_CLAUSE+ APPLICATION_NAME);
		   queryStatement.append( AND_CLAUSE+ PIPELINE_NAME);

			queryStatement.append(ACTIVE_PIPELINE);
			
			ResultSet rs= null;

		   try (Connection connection = postGreSqlDbContext.getConnection();
		        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
		    	preparedStatement.setString(1,applicationName);
		    	preparedStatement.setString(2,pipelineName);
		    	 rs = 	preparedStatement.executeQuery();
			    	
			    	//System.out.println("inside prepare statement"+rs.getLong(1));
			    	
		    	while(rs.next())
		    	{
		    		pipelineId = rs.getLong(1);
		    		
		    	}
		    	return pipelineId;
		     }		     
		    


		     catch(SQLException | NullPointerException e) {


		     // logger.error("Postgres Error while fetching IDPJob entity values :",e);
		      throw e;
		      
		    }

		   finally{
		   		if(rs != null){
		   			try{
		   			rs.close();
		   			}
		   			catch(SQLException e){
		   				e.printStackTrace();
		   			}
		   		}
		   	}
		  
	  }
	 
	 /**
	   * insert trigger history
	   * 
	   * @param userId the String
	   * @param emailId the String
	   * @param status the Boolean
	   * 
	   * @return Integer
	 * @throws JSONException 
	   */
	  public  Integer insertTriggerHistory(JSONObject jsonObj,String version) throws JSONException
	  {
		   long pid = 0;
			String artifactName="";

		try {
			pid = getPipelineId(jsonObj.getString("applicationName"),jsonObj.getString("pipelineName"));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Integer latestVersion=getMaxVersion(pid);
		if(Integer.parseInt(version)>latestVersion){
			
			Integer triggerId =null ;
			String tableName = "ttrigger_history";
			StringBuilder queryStatement = new StringBuilder();
			queryStatement.append(INSERT_CLAUSE+" "+tableName+" (pipeline_id, trigger_entity, release_number,version) VALUES (?, cast(? as json), ?,?);");
		   try (Connection connection = postGreSqlDbContext.getConnection();
		        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString(),new String[] {"trigger_id"})) {
		    	preparedStatement.setLong(1,pid);
		    	preparedStatement.setString(2,jsonObj.toString());
		    	preparedStatement.setString(3,jsonObj.getString("releaseNumber"));
		    	preparedStatement.setString(4,version);
		    	preparedStatement.executeUpdate();
		    	
				System.out.println("versionsdasd");
		    	ResultSet rs = preparedStatement.getGeneratedKeys();
		    	if (rs.next()) {
		    	    triggerId = (int) rs.getLong(1);
		    	}
		    	String buildObj = artifactHistory.getJobJSON(jsonObj.getString("applicationName")+"_"+jsonObj.getString("pipelineName"),"nextBuild_Pipeline");

		    	net.sf.json.JSONObject buildJson = net.sf.json.JSONObject.fromObject(buildObj);

		    	
				if (jsonObj.has("build"))
					artifactName = jsonObj.getString("applicationName") + "_" + jsonObj.getString("pipelineName") 
							+ "_" + buildJson.getString("nextBuildNumber");
				artifactHistory.updateTriggerHistory(triggerId,artifactName);
		    	
				if (jsonObj.getString("technology")!="SapNonCharm" && (jsonObj.has("deploy") || jsonObj.has("build"))) {
					artifactHistory.insertArtifact(jsonObj,buildJson , jsonObj.getString("userName"));
					
				}
		      
		     }		     
		    
		     catch (SQLException | NullPointerException e) {
		    	 //System.out.println(e);
		   e.printStackTrace();
		    	 //logger.error("Postgres Error while inserting the data in tuser_info:",e);	      
		      return -1;
		    }
		    
		  return 1;	  
		}
		return 1;
		 
	  }
	  
	  public Integer getMaxVersion(long pid) 
	  {
		 String tableName = "ttrigger_history";
		 StringBuilder queryStatement = new StringBuilder();
		 queryStatement.append(SELECT_CLAUSE+" max(version::int)"+" "+FROM_CLAUSE+" "+tableName+" "+WHERE_CLAUSE+" pipeline_id = "+pid);
		 System.out.println("DSda"+queryStatement);
		 try (Connection connection = postGreSqlDbContext.getConnection();
			  PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			  ResultSet rs=preparedStatement.executeQuery();
			  if(rs.next() && rs.getString("max")!=null)
			  {
				  Integer version=Integer.parseInt(rs.getString("max"));
				  System.out.println(version);
				  return version;
				  
			  }
		

	  } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return -1;}
	  
	  
		
	  

}
