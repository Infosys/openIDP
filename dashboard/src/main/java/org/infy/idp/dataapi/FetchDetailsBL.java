/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.dataapi;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.infy.idp.entities.Column;
import org.infy.idp.entities.IBMMaximoDeployDetails;
import org.infy.idp.entities.QueryResponse;
import org.infy.idp.utils.ConfigurationManager;
import org.infy.idp.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * The Class FetchDetailsBL to handle timeseries,table,search request from grafana.
 */

@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class FetchDetailsBL {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	@Autowired
	private IDPPostGreSqlDbContext idpPostGreSqlDbContext;

	@Autowired
	private DBQuery dbQuery;

	@Autowired
	private ConfigurationManager configurationManager;
	
	@Value("${mtmuser}")
    private String mtmuser;
	@Value("${mtmpassword}")
    private String mtmpassword;
	@Value("${mtmurl}")
    private String mtmurl;
	@Value("${mtmproject}")
    private String mtmproject;
	@Value("${projectName}")
    private String projectName;
	@Value("${proxyip}")
    private String proxyip;
	@Value("${proxyport}")
    private String proxyport;
	@Value("${jiraurl}")
    private String jiraurl;
	@Value("${jirauser}")
    private String jirauser;
	@Value("${jirapassword}")
    private String jirapassword;
	@Value("${proxyuser}")
    private String proxyuser;
	@Value("${proxypassword}")
    private String proxypassword;
	@Value("${jenkinsID}")
    private String jenkinsID;
	@Value("${jenkinsURL}")
    private String jenkinsURL;
	@Value("${jenkinsPassword}")
    private String jenkinsPassword;
	
	
	public static final String VALUES="values are";
	protected Logger logger = LoggerFactory.getLogger(FetchDetailsBL.class);

	/**
	 * Constructor
	 * 
	 * 
	 * @param postGreSqlDbContext
	 *            the PostGreSqlDbContext
	 * 
	 */
	private FetchDetailsBL() {

	}

	/**
	 * Get permissions to the user for perticular application
	 * 
	 * 
	 * @param userName
	 *            the String
	 * @param applicationName
	 *            the String
	 * 
	 * @return permissions the List<String>
	 * 
	 */

	

	/**
	 * 
	 * Method to handle timeseries type requests
	 * @param query
	 * @return
	 */
	
	//Started new code from prod 3.4.8
	
	
	public List<QueryResponse> runQuery(String query,String userid)

	{

		query=query.replace("\\", "");
		logger.info("Executing " + query);
		///
		List<QueryResponse> resultsresponses = new ArrayList<>();
		QueryResponse response;
		
		HashMap<String, String> results = new HashMap();
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(query);
		if (query.toLowerCase().contains("analysisscore")) {
			return queryfunctionscore(resultsresponses,query,userid);
			}
		else if (query.equalsIgnoreCase("deploytrend")) {
			return queryfunctiontrend(resultsresponses, "deploytrend",userid);
			}
		else if (query.equalsIgnoreCase("buildtrend")) {
			return queryfunctiontrend(resultsresponses, "buildtrend",userid);
			}

		else if(query.toLowerCase().contains("environmentplanner"))
        {
            
            String rn;
            String env;
            logger.info(query);
            String[] temp=query.split("\\+");
            rn=temp[1];
            env=temp[2];
            logger.info("Value of rn and env"+rn+" "+env);
                    List<Pair<String,String>> list = new ArrayList<>();
                    Pair<String, String> pair = null;

                    String newQuery="select start_time,end_time,type_plan,on_plan from tenvironment_planning where release_no='"+rn+"' and environment='"+env+"'";
                        try (Connection connection = idpPostGreSqlDbContext.getConnection();
                                    PreparedStatement preparedStatement = connection.prepareStatement(newQuery);
                        		ResultSet rs = preparedStatement.executeQuery();) {
                                    
                                    String startTime;
                                    String endTime;
                                    String typePlan;
                                    String onPlan;
                                    String startTime1;
                                    while (rs.next()) {
                                   
                                    startTime = rs.getString(1);
                                    endTime = rs.getString(2);
                                    typePlan = rs.getString(3);
                                    onPlan = rs.getString(4);
                                    
                              
                                    logger.info(startTime+" "+endTime+" "+typePlan+" "+onPlan);
                                    
                                    
                                    if(typePlan.equalsIgnoreCase("day")){
                                          
                                    	logger.info("inside day");
                                          for(int i=0;i<31;i++){
                                                startTime1 = startTime;
                                                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                                Date date1 = format.parse(startTime);
                                                Date date2 = format.parse(endTime);
                                                long difference = (date2.getTime() - date1.getTime())/3600000; 

                                                logger.info("difference is : "+difference);
                                                logger.info("After adding 1 time is "+startTime);
                                                for(int j=0;j<difference+1;j++){
                                                      pair = new MutablePair<>(Integer.toString(i), LocalDate.now()+" "+startTime1+":00");
                                                      logger.info(Integer.toString(i)+"__/__"+LocalDate.now()+" "+startTime1+":00");
                                                      list.add(pair);
                                                      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");                                            
                                                      try {
                                                            Date date = new java.util.Date();
                                                            if (StringUtils.isNotEmpty(LocalDate.now()+" "+startTime1+":00")) {
                                                                  date = dateFormat.parse(LocalDate.now()+" "+startTime1+":00");
                                                                  
                                                                  int hours = date.getHours();
                                                                  int mins = date.getMinutes();
                                                                  int secs = date.getSeconds();
                                                                  if(hours!=23){
                                                                        hours++;
                                                                  }
                                                                  else
                                                                        hours = 00;
                                                                  date.setHours(hours);
                                                                  date.setMinutes(mins);
                                                                  date.setSeconds(secs);
                                                                  startTime1 = Integer.toString(hours)+":"+mins;                                       
                                                            }
                                                      } catch (ParseException e) {
                                                            logger.error("Exception in ConvertDate", e);

                                                      }
                                                }
                                          }
                                
                                    }
                                    
                                    else if(typePlan.equalsIgnoreCase("month")){
                                    	  logger.info("Inside month");
                                          onPlan=onPlan.substring(1,onPlan.length()-1);
                                          String[] onPlanData = onPlan.split(", ");
                                          logger.info(onPlan);
                                          for(int i=0;i<onPlanData.length;i++)logger.info("zzzz"+onPlanData[i]);
                                          for(int i=0;i<onPlanData.length;i++){
                                                startTime1 = startTime;
                                                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                                Date date1 = format.parse(startTime);
                                                Date date2 = format.parse(endTime);
                                                long difference = (date2.getTime() - date1.getTime())/3600000; 

                                                logger.info("difference is : "+difference);
                                                logger.info("After adding 1 time is "+startTime);
                                                for(int j=0;j<difference;j++){
                                                      pair = new MutablePair<>(onPlanData[i], LocalDate.now()+" "+startTime1+":00");
                                                      logger.info(onPlanData[i]+"__/__"+LocalDate.now()+" "+startTime1+":00");
                                                      list.add(pair);
                                                      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");                                            
                                                      try {
                                                            Date date = new java.util.Date();
                                                            if (StringUtils.isNotEmpty(LocalDate.now()+" "+startTime1+":00")) {
                                                                  date = dateFormat.parse(LocalDate.now()+" "+startTime1+":00");
                                                                  
                                                                  int hours = date.getHours();
                                                                  int mins = date.getMinutes();
                                                                  int secs = date.getSeconds();
                                                                  if(hours!=23){
                                                                        hours++;
                                                                  }
                                                                  else
                                                                        hours = 00;
                                                                  date.setHours(hours);
                                                                  date.setMinutes(mins);
                                                                  date.setSeconds(secs);
                                                                  startTime1 = Integer.toString(hours)+":"+mins;                                       
                                                            }
                                                      } catch (ParseException e) {
                                                            logger.error("Exception in ConvertDate", e);

                                                      }
                                                }
                                          }
                                          
                                    }
                                    
                					else if(typePlan.equalsIgnoreCase("week")){
                						logger.info("Inside week");
                						onPlan=onPlan.substring(1,onPlan.length()-1);
                						String[] onPlanData = onPlan.split(", ");
                						int[] onPlanDataInt=new int[onPlanData.length];
                						
                						Calendar c = new GregorianCalendar();
                						
                						for(int i=0;i<onPlanData.length;i++){
                							
                							logger.info("step1");
                							    
                							if(onPlanData[i].equalsIgnoreCase("Sun")){
                								onPlanDataInt[i] = 1;
                							}
                							
                							else if(onPlanData[i].equalsIgnoreCase("Mon")){
                								onPlanDataInt[i] = 2;
                							}
                							
                							else if(onPlanData[i].equalsIgnoreCase("Tue")){
                								onPlanDataInt[i] = 3;
                							}
                							
                							else if(onPlanData[i].equalsIgnoreCase("Wed")){
                								onPlanDataInt[i] = 4;
                							}
                							
                							else if(onPlanData[i].equalsIgnoreCase("Thu")){
                								onPlanDataInt[i] = 5;
                							}
                							
                							else if(onPlanData[i].equalsIgnoreCase("Fri")){
                								onPlanDataInt[i] = 6;
                							}
                							
                							else if(onPlanData[i].equalsIgnoreCase("Sat")){
                								onPlanDataInt[i] = 7;
                							}
                							
                							logger.info(String.valueOf(onPlanDataInt[i]));
                						}
                						logger.info("step2");
                						c.set(Calendar.DAY_OF_MONTH, 1);
                						int day = c.get(Calendar.DAY_OF_WEEK);
                						int monthDay;
                						for(int i=0;i<onPlanData.length;i++){
                							logger.info("step3"+day);
                							
                							if (onPlanDataInt[i]>=day)
                								monthDay = 1 + onPlanDataInt[i]-day;
                							else
                								monthDay = 8 - (day - onPlanDataInt[i]);
                							logger.info("P:"+onPlanDataInt[i]);
                							logger.info(c.toString());
                							logger.info("step5");
                							
                                            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                            Date date1 = format.parse(startTime);
                                            Date date2 = format.parse(endTime);
                                            long difference = (date2.getTime() - date1.getTime())/3600000; 

                                            logger.info("difference is : "+difference);
                                            logger.info("After adding 1 time is "+startTime);
                                            
                                            Calendar calday = Calendar.getInstance();
                                            int monthMaxDays = calday.getActualMaximum(Calendar.DAY_OF_MONTH);
                                            
                                            for (;monthDay<=monthMaxDays;monthDay+=7){
                    							startTime1 = startTime;


                							 for(int j=0;j<difference;j++){
                								 
                								 logger.info(monthDay+",");
                                                 pair = new MutablePair<>(Integer.toString(monthDay), LocalDate.now()+" "+startTime1+":00");
                                                 logger.info(onPlanData[i]+"__/__"+LocalDate.now()+" "+startTime1+":00");
                                                 list.add(pair);
                                                 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");                                            
                                                 try {
                                                       Date date = new java.util.Date();
                                                       if (StringUtils.isNotEmpty(LocalDate.now()+" "+startTime1+":00")) {
                                                             date = dateFormat.parse(LocalDate.now()+" "+startTime1+":00");
                                                             
                                                             int hours = date.getHours();
                                                             int mins = date.getMinutes();
                                                             int secs = date.getSeconds();
                                                             if(hours!=23){
                                                                   hours++;
                                                             }
                                                             else
                                                                   hours = 00;
                                                             date.setHours(hours);
                                                             date.setMinutes(mins);
                                                             date.setSeconds(secs);
                                                             startTime1 = Integer.toString(hours)+":"+mins;                                       
                                                       }
                                                 } catch (ParseException e) {
                                                       logger.error("Exception in ConvertDate", e);

                                                 }
                                           }
                                            }
                						}
                					}
                              }
                              logger.info("runQuery result set");
                              results.forEach((k, v) -> logger.info("value : " + k + " Timstamp : " + v));
                             


                        } catch (Exception e) {
                              logger.error("Exception in runQuery ", e);
                              return resultsresponses;
                        }
                        

                        double[][] array = new double[list.size()][2];
                        for(int i=0;i<list.size();i++)
                        {
                                        array[i][0]=Utils.convertString(list.get(i).getKey());
                                        array[i][1]= Utils.convertDate(list.get(i).getValue());
                        }
                        response = new QueryResponse();
                        response.setDatapoints(array);
                        response.setType("timeserie");
                        response.setTarget("nexus0coverage");
                        resultsresponses.add(response);
                        return resultsresponses;
                        
        }

		else if(query.toLowerCase().contains("artifactviewbuildstatus"))
		{
			String application=query.substring(query.indexOf('_')+1,query.indexOf('@'));
			String pipeline=query.substring(query.indexOf('@')+1,query.indexOf('#'));
			String releasno=query.substring(query.indexOf('#')+1,query.indexOf('^'));
			String artifactname=query.substring(query.indexOf('^')+1);
			String pipelineno="";
			String pipelineno1=artifactname.substring(artifactname.lastIndexOf('_')+1);
			String pipelineno2="";
			try{
				pipelineno2=artifactname.substring(artifactname.lastIndexOf('-')+1);
			}
				catch(Exception e){logger.error(e.getMessage());}
			if(!"".equals(pipelineno2) && pipelineno2.length()<pipelineno1.length())pipelineno=pipelineno2;
			else pipelineno=pipelineno1;
			logger.info(application+pipeline+releasno+artifactname);
			String newQuery=" select buildstatus,created_at from buildinfo where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"')  and pipelineno='"+pipelineno+"' and stagename like '%Build%' ";
			logger.info(newQuery);
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(newQuery);) {
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					results.put(rs.getString(1), rs.getString(2));
				}
				logger.info("runQuery result set");
				results.forEach((k, v) -> logger.info("value : " + k + " Timstamp : " + v));
				int index = 0;
				double[][] array = new double[results.size()][2];
				for (Map.Entry<String, String> entry : results.entrySet()) {
					array[index][0] = Utils.convertString(entry.getKey());
					array[index][1] = Utils.convertDate(entry.getValue());
					index++;
				}
				response = new QueryResponse();
				response.setDatapoints(array);
				response.setType("timeserie");
				resultsresponses.add(response);
				return resultsresponses;

			} catch (Exception e) {
				logger.error("Exception in runQuery ", e);
				return resultsresponses;
			}
		}
		
		else if (query.equalsIgnoreCase("nexusbuildduration")) {
			results.put("62345.0", "2017-12-08 12:13:14");
			int index = 0;
			double[][] array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setType("timeserie");
			response.setTarget("nexusbuildduration");
			resultsresponses.add(response);
			return resultsresponses;
		}
		else if (query.toLowerCase().contains("artifactviewbuildduration"))
		{

			String application=query.substring(query.indexOf('_')+1,query.indexOf('@'));
			String pipeline=query.substring(query.indexOf('@')+1,query.indexOf('#'));
			String artifactname=query.substring(query.indexOf('^')+1);
			String pipelineno="";
			String pipelineno1=artifactname.substring(artifactname.lastIndexOf('_')+1);
			String pipelineno2="";
			try{
				pipelineno2=artifactname.substring(artifactname.lastIndexOf('-')+1);
			}
				catch(Exception e){logger.error(e.getMessage());}
			if(!"".equals(pipelineno2) && pipelineno2.length()<pipelineno1.length())pipelineno=pipelineno2;
			else pipelineno=pipelineno1;
			
			
			String newQuery=" select buildtime,created_at from buildinfo where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"')  and pipelineno='"+pipelineno+"' and stagename like '%Build%' ";
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(newQuery);) {
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					results.put(rs.getString(1), rs.getString(2));
				}
				logger.info("runQuery result set");
				results.forEach((k, v) -> logger.info("value : " + k + " Timstamp : " + v));
				int index = 0;
				double[][] array = new double[results.size()][2];
				for (Map.Entry<String, String> entry : results.entrySet()) {
					array[index][0] = Utils.convertString(entry.getKey());
					array[index][1] = Utils.convertDate(entry.getValue());
					index++;
				}
				response = new QueryResponse();
				response.setDatapoints(array);
				response.setType("timeserie");
				resultsresponses.add(response);
				return resultsresponses;

			} catch (Exception e) {
				logger.error("Exception in runQuery ", e);
				return resultsresponses;
			}
		
		}
		
		else if (query.equalsIgnoreCase("nexus0coverage")) {
			results.put("15", "2017-12-08 12:13:14");
			int index = 0;
			double[][] array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("nexus0coverage");
			resultsresponses.add(response);
			return resultsresponses;
		}
		else if (query.toLowerCase().contains("artifactview0coverage"))
		{


			String application=query.substring(query.indexOf('_')+1,query.indexOf('@'));
			String pipeline=query.substring(query.indexOf('@')+1,query.indexOf('#'));
			String artifactname=query.substring(query.indexOf('^')+1);
			String pipelineno="";
			String pipelineno1=artifactname.substring(artifactname.lastIndexOf('_')+1);
			String pipelineno2="";
			try{
				pipelineno2=artifactname.substring(artifactname.lastIndexOf('-')+1);
			}
				catch(Exception e){logger.error(e.getMessage());}
			if(!"".equals(pipelineno2) && pipelineno2.length()<pipelineno1.length())pipelineno=pipelineno2;
			else pipelineno=pipelineno1;
			
			
			String newQuery=" select count(classname) , max(created_at) from codecoverage where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"')  and pipelineno='"+pipelineno+"' and linecoverage=0";
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(newQuery);) {
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					results.put(rs.getString(1), rs.getString(2));
				}
				logger.info("runQuery result set");
				results.forEach((k, v) -> logger.info("value : " + k + " Timstamp : " + v));
				int index = 0;
				double[][] array = new double[results.size()][2];
				for (Map.Entry<String, String> entry : results.entrySet()) {
					array[index][0] = Utils.convertString(entry.getKey());
					array[index][1] = Utils.convertDate(entry.getValue());
					index++;
				}
				response = new QueryResponse();
				response.setDatapoints(array);
				response.setType("timeserie");
				resultsresponses.add(response);
				return resultsresponses;

			} catch (Exception e) {
				logger.error("Exception in runQuery ", e);
				return resultsresponses;
			}
		
		
		}
		else if (query.equalsIgnoreCase("nexus50coverage")) {
			results.put("30", "2017-12-08 12:13:14");
			int index = 0;
			double[][] array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("nexus50coverage");
			resultsresponses.add(response);
			return resultsresponses;
		}
		else if (query.toLowerCase().contains("artifactview50coverage"))
		{


			String application=query.substring(query.indexOf('_')+1,query.indexOf('@'));
			String pipeline=query.substring(query.indexOf('@')+1,query.indexOf('#'));
			String artifactname=query.substring(query.indexOf('^')+1);
			String pipelineno="";
			String pipelineno1=artifactname.substring(artifactname.lastIndexOf('_')+1);
			String pipelineno2="";
			try{
				pipelineno2=artifactname.substring(artifactname.lastIndexOf('-')+1);
			}
				catch(Exception e){logger.error(e.getMessage());}
			if(!"".equals(pipelineno2) && pipelineno2.length()<pipelineno1.length())pipelineno=pipelineno2;
			else pipelineno=pipelineno1;
			
			
			String newQuery=" select count(classname) , max(created_at) from codecoverage where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"')  and pipelineno='"+pipelineno+"' and linecoverage>0 and linecoverage<=50";
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(newQuery);) {
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					results.put(rs.getString(1), rs.getString(2));
				}
				logger.info("runQuery result set");
				results.forEach((k, v) -> logger.info("value : " + k + " Timstamp : " + v));
				int index = 0;
				double[][] array = new double[results.size()][2];
				for (Map.Entry<String, String> entry : results.entrySet()) {
					array[index][0] = Utils.convertString(entry.getKey());
					array[index][1] = Utils.convertDate(entry.getValue());
					index++;
				}
				response = new QueryResponse();
				response.setDatapoints(array);
				response.setType("timeserie");
				resultsresponses.add(response);
				return resultsresponses;

			} catch (Exception e) {
				logger.error("Exception in runQuery ", e);
				return resultsresponses;
			}
		
		
		}
		else if (query.equalsIgnoreCase("nexus100coverage")) {
			results.put("42", "2017-12-08 12:13:14");
			int index = 0;
			double[][] array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("nexus100coverage");
			resultsresponses.add(response);
			return resultsresponses;
		}
		else if (query.toLowerCase().contains("artifactview1000coverage"))
		{


			String application=query.substring(query.indexOf('_')+1,query.indexOf('@'));
			String pipeline=query.substring(query.indexOf('@')+1,query.indexOf('#'));
			String artifactname=query.substring(query.indexOf('^')+1);
			String pipelineno="";
			String pipelineno1=artifactname.substring(artifactname.lastIndexOf('_')+1);
			String pipelineno2="";
			try{
				pipelineno2=artifactname.substring(artifactname.lastIndexOf('-')+1);
			}
				catch(Exception e){logger.error(e.getMessage());}
			if(!"".equals(pipelineno2) && pipelineno2.length()<pipelineno1.length())pipelineno=pipelineno2;
			else pipelineno=pipelineno1;
			
			
			String newQuery=" select count(classname) , max(created_at) from codecoverage where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"')  and pipelineno='"+pipelineno+"' and linecoverage>50 and linecoverage<=100";
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(newQuery);) {
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					results.put(rs.getString(1), rs.getString(2));
				}
				logger.info("runQuery result set");
				results.forEach((k, v) -> logger.info("value : " + k + " Timstamp : " + v));
				int index = 0;
				double[][] array = new double[results.size()][2];
				for (Map.Entry<String, String> entry : results.entrySet()) {
					array[index][0] = Utils.convertString(entry.getKey());
					array[index][1] = Utils.convertDate(entry.getValue());
					index++;
				}
				response = new QueryResponse();
				response.setDatapoints(array);
				response.setType("timeserie");
				resultsresponses.add(response);
				return resultsresponses;

			} catch (Exception e) {
				logger.error("Exception in runQuery ", e);
				return resultsresponses;
			}
		
		
		}
		else if (query.equalsIgnoreCase("nexuscodeanalysislow")) {
			results.put("150", "2017-12-08 12:13:14");
			int index = 0;
			double[][] array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("nexuscodeanalysislow");
			resultsresponses.add(response);
			return resultsresponses;
		}
		
		else if (query.toLowerCase().contains("artifactviewcodeanalysislow"))
		{



			String application=query.substring(query.indexOf('_')+1,query.indexOf('@'));
			String pipeline=query.substring(query.indexOf('@')+1,query.indexOf('#'));
			String artifactname=query.substring(query.indexOf('^')+1);
			String pipelineno="";
			String pipelineno1=artifactname.substring(artifactname.lastIndexOf('_')+1);
			String pipelineno2="";
			try{
				pipelineno2=artifactname.substring(artifactname.lastIndexOf('-')+1);
			}
				catch(Exception e){logger.error(e.getMessage());}
			if(!"".equals(pipelineno2) && pipelineno2.length()<pipelineno1.length())pipelineno=pipelineno2;
			else pipelineno=pipelineno1;
			
			
			String newQuery="select count(rulename),max(created_at) from codeanalysis where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"')  and pipelineno='"+pipelineno+"' and severity='low'";
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(newQuery);) {
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					results.put(rs.getString(1), rs.getString(2));
				}
				logger.info("runQuery result set");
				results.forEach((k, v) -> logger.info("value : " + k + " Timstamp : " + v));
				int index = 0;
				double[][] array = new double[results.size()][2];
				for (Map.Entry<String, String> entry : results.entrySet()) {
					array[index][0] = Utils.convertString(entry.getKey());
					array[index][1] = Utils.convertDate(entry.getValue());
					index++;
				}
				response = new QueryResponse();
				response.setDatapoints(array);
				response.setType("timeserie");
				resultsresponses.add(response);
				return resultsresponses;

			} catch (Exception e) {
				logger.error("Exception in runQuery ", e);
				return resultsresponses;
			}
		
		
		
		}
		
		else if (query.equalsIgnoreCase("nexuscodeanalysishigh")) {
			results.put("32", "2017-12-08 12:13:14");
			int index = 0;
			double[][] array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("nexuscodeanalysishigh");
			resultsresponses.add(response);
			return resultsresponses;
		}
		else if (query.toLowerCase().contains("artifactviewcodeanalysishigh"))
		{



			String application=query.substring(query.indexOf('_')+1,query.indexOf('@'));
			String pipeline=query.substring(query.indexOf('@')+1,query.indexOf('#'));
			String artifactname=query.substring(query.indexOf('^')+1);
			String pipelineno="";
			String pipelineno1=artifactname.substring(artifactname.lastIndexOf('_')+1);
			String pipelineno2="";
			try{
				pipelineno2=artifactname.substring(artifactname.lastIndexOf('-')+1);
			}
				catch(Exception e){logger.error(e.getMessage());}
			if(!"".equals(pipelineno2) && pipelineno2.length()<pipelineno1.length())pipelineno=pipelineno2;
			else pipelineno=pipelineno1;
			
			
			String newQuery="select count(rulename),max(created_at) from codeanalysis where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"')  and pipelineno='"+pipelineno+"' and severity='high'";
			logger.info(newQuery);
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(newQuery);) {
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					results.put(rs.getString(1), rs.getString(2));
				}
				logger.info("runQuery result set");
				results.forEach((k, v) -> logger.info("value : " + k + " Timstamp : " + v));
				int index = 0;
				double[][] array = new double[results.size()][2];
				for (Map.Entry<String, String> entry : results.entrySet()) {
					array[index][0] = Utils.convertString(entry.getKey());
					array[index][1] = Utils.convertDate(entry.getValue());
					index++;
				}
				response = new QueryResponse();
				response.setDatapoints(array);
				response.setType("timeserie");
				resultsresponses.add(response);
				return resultsresponses;

			} catch (Exception e) {
				logger.error("Exception in runQuery ", e);
				return resultsresponses;
			}
		
		
		
		}
		else if (query.equalsIgnoreCase("nexuscodeanalysismedium")) {
			results.put("53", "2017-12-08 12:13:14");
			int index = 0;
			double[][] array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("nexuscodeanalysismedium");
			resultsresponses.add(response);
			return resultsresponses;
		}
		else if (query.toLowerCase().contains("artifactviewcodeanalysismedium"))
		{



			String application=query.substring(query.indexOf('_')+1,query.indexOf('@'));
			String pipeline=query.substring(query.indexOf('@')+1,query.indexOf('#'));
			String artifactname=query.substring(query.indexOf('^')+1);
			String pipelineno="";
			String pipelineno1=artifactname.substring(artifactname.lastIndexOf('_')+1);
			String pipelineno2="";
			try{
				pipelineno2=artifactname.substring(artifactname.lastIndexOf('-')+1);
			}
				catch(Exception e){logger.error(e.getMessage());}
			if(!"".equals(pipelineno2) && pipelineno2.length()<pipelineno1.length())pipelineno=pipelineno2;
			else pipelineno=pipelineno1;
			
			
			String newQuery="select count(rulename),max(created_at) from codeanalysis where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"')  and pipelineno='"+pipelineno+"' and severity='medium'";
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(newQuery);) {
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					results.put(rs.getString(1), rs.getString(2));
				}
				logger.info("runQuery result set");
				results.forEach((k, v) -> logger.info("value : " + k + " Timstamp : " + v));
				int index = 0;
				double[][] array = new double[results.size()][2];
				for (Map.Entry<String, String> entry : results.entrySet()) {
					array[index][0] = Utils.convertString(entry.getKey());
					array[index][1] = Utils.convertDate(entry.getValue());
					index++;
				}
				response = new QueryResponse();
				response.setDatapoints(array);
				response.setType("timeserie");
				resultsresponses.add(response);
				return resultsresponses;

			} catch (Exception e) {
				logger.error("Exception in runQuery ", e);
				return resultsresponses;
			}
		
		
		
		}
else if (query.equalsIgnoreCase("unitgraph")) {
			
			String queryTotal = "SELECT count(*) FROM public.testanalysis where UPPER(category) like 'UNIT%'";
			String querySuccess = "SELECT count(*) FROM public.testanalysis where UPPER(category) like 'UNIT%' AND (UPPER(status)='SUCCESS' OR UPPER(status)='PASSED')";
			String queryFailure = "SELECT count(*) FROM public.testanalysis where UPPER(category) like 'UNIT%' AND (UPPER(status)='FAILURE' OR UPPER(status)='FAILED')";
	      
			String sTotal="";
			String sSuccess="";
			String sFailure="";
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement1 = connection.prepareStatement(queryTotal);
					PreparedStatement preparedStatement2 = connection.prepareStatement(querySuccess);
					PreparedStatement preparedStatement3 = connection.prepareStatement(queryFailure);
					)
			{
				ResultSet rs1 = preparedStatement1.executeQuery();
				ResultSet rs2 = preparedStatement2.executeQuery();
				ResultSet rs3 = preparedStatement3.executeQuery();
				
				while(rs1.next())sTotal=rs1.getString(1);
				while(rs2.next())sSuccess=rs2.getString(1);
				while(rs3.next())sFailure=rs3.getString(1);
			}
			catch(Exception e)
			{
				logger.error("Error while getting testanalysis results");
				e.printStackTrace();
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			Date date=new Date();
			String currDate=dateFormat.format(date);
			results.put(sTotal, currDate);
			
			int index = 0;
			double[][] array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("Tests Executed");
			resultsresponses.add(response);
			
			results.clear();
			results.put(sSuccess, currDate);
			
			index = 0;
			array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("Passed");
			resultsresponses.add(response);
			
			results.clear();
			results.put(sFailure, currDate);
			
			index = 0;
			array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("Failed");
			resultsresponses.add(response);
			
			
			return resultsresponses;
		}
		else if (query.equalsIgnoreCase("functionalgraph")) {
			
			String queryTotal = "SELECT count(*) FROM public.testanalysis where UPPER(category) like 'FUNCTIONAL%'";
			String querySuccess = "SELECT count(*) FROM public.testanalysis where UPPER(category) like 'FUNCTIONAL%' AND (UPPER(status)='SUCCESS' OR UPPER(status)='PASSED')";
			String queryFailure = "SELECT count(*) FROM public.testanalysis where UPPER(category) like 'FUNCTIONAL%' AND (UPPER(status)='FAILURE' OR UPPER(status)='FAILED')";
			String sTotal="";
			String sSuccess="";
			String sFailure="";
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement1 = connection.prepareStatement(queryTotal);
					PreparedStatement preparedStatement2 = connection.prepareStatement(querySuccess);
					PreparedStatement preparedStatement3 = connection.prepareStatement(queryFailure);
					)
			{
				ResultSet rs1 = preparedStatement1.executeQuery();
				ResultSet rs2 = preparedStatement2.executeQuery();
				ResultSet rs3 = preparedStatement3.executeQuery();
				
				while(rs1.next())sTotal=rs1.getString(1);
				while(rs2.next())sSuccess=rs2.getString(1);
				while(rs3.next())sFailure=rs3.getString(1);
			}
			catch(Exception e)
			{
				logger.error("Error while getting testanalysis results");
				e.printStackTrace();
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			Date date=new Date();
			String currDate=dateFormat.format(date);
			results.put(sTotal, currDate);
			
			int index = 0;
			double[][] array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("Tests Executed");
			resultsresponses.add(response);
			
			results.clear();
			results.put(sSuccess, currDate);
			
			index = 0;
			array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("Passed");
			resultsresponses.add(response);
			
			results.clear();
			results.put(sFailure, currDate);
			
			index = 0;
			array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("Failed");
			resultsresponses.add(response);
			
			
			return resultsresponses;
		}
		else if (query.equalsIgnoreCase("performancegraph")) {
			
			String queryTotal = "SELECT count(*) FROM public.testanalysis where UPPER(category) like 'PERFORMANCE%'";
			String querySuccess = "SELECT count(*) FROM public.testanalysis where UPPER(category) like 'PERFORMANCE%' AND (UPPER(status)='SUCCESS' OR UPPER(status)='PASSED')";
			String queryFailure = "SELECT count(*) FROM public.testanalysis where UPPER(category) like 'PERFORMANCE%' AND (UPPER(status)='FAILURE' OR UPPER(status)='FAILED')";
	      
			String sTotal="";
			String sSuccess="";
			String sFailure="";
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement1 = connection.prepareStatement(queryTotal);
					PreparedStatement preparedStatement2 = connection.prepareStatement(querySuccess);
					PreparedStatement preparedStatement3 = connection.prepareStatement(queryFailure);
					)
			{
				ResultSet rs1 = preparedStatement1.executeQuery();
				ResultSet rs2 = preparedStatement2.executeQuery();
				ResultSet rs3 = preparedStatement3.executeQuery();
				
				while(rs1.next())sTotal=rs1.getString(1);
				while(rs2.next())sSuccess=rs2.getString(1);
				while(rs3.next())sFailure=rs3.getString(1);
			}
			catch(Exception e)
			{
				logger.error("Error while getting testanalysis results");
				e.printStackTrace();
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			Date date=new Date();
			String currDate=dateFormat.format(date);
			results.put(sTotal, currDate);
			
			int index = 0;
			double[][] array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("Tests Executed");
			resultsresponses.add(response);
			
			results.clear();
			results.put(sSuccess, currDate);
			
			index = 0;
			array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("Passed");
			resultsresponses.add(response);
			
			results.clear();
			results.put(sFailure, currDate);
			
			index = 0;
			array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			response.setTarget("Failed");
			resultsresponses.add(response);
			
			
			return resultsresponses;
		}
		else if (query.equalsIgnoreCase("analysisgraph")) {
			
			
			String queryNew="select count(DISTINCT(appid)),category from codeanalysis group by category";

			List<Pair<String,String>> list = new ArrayList<>();
			
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement1 = connection.prepareStatement(queryNew);
				
					)
			{
				ResultSet rs1 = preparedStatement1.executeQuery();

				while(rs1.next()){
					
                    Pair<String, String> pair = null;
                    pair=new MutablePair<>(rs1.getString(1),rs1.getString(2));
                    list.add(pair);
				}
			}
			catch(Exception e)
			{
				logger.error("Error while getting testanalysis results");
				e.printStackTrace();
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			Date date=new Date();
			String currDate=dateFormat.format(date);
			for(int i=0;i<list.size();i++)
			{
				results.put(list.get(i).getKey(), currDate);
				
				int index = 0;
				double[][] array = new double[results.size()][2];
				for (Map.Entry<String, String> entry : results.entrySet()) {
					array[index][0] = Utils.convertString(entry.getKey());
					array[index][1] = Utils.convertDate(entry.getValue());
					index++;
				}
				response = new QueryResponse();
				response.setDatapoints(array);
				response.setType("timeserie");
				response.setTarget(list.get(i).getValue());
				resultsresponses.add(response);
				
				results.clear();
			}
			
			
			
			return resultsresponses;
		}
		else if (query.equalsIgnoreCase("coveragegraph")) {
			
			String queryNew = "select count(DISTINCT(appid)),category from codecoverage group by category";
			List<Pair<String,String>> list = new ArrayList<>();
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement1 = connection.prepareStatement(queryNew);
					)
			{
				ResultSet rs1 = preparedStatement1.executeQuery();
				while(rs1.next()){
									
				                    Pair<String, String> pair = null;
				                    pair=new MutablePair<>(rs1.getString(1),rs1.getString(2));
				                    list.add(pair);
								}
				
			}
			catch(Exception e)
			{
				logger.error("Error while getting testanalysis results");
				e.printStackTrace();
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			Date date=new Date();
			String currDate=dateFormat.format(date);
			for(int i=0;i<list.size();i++)
			{
				results.put(list.get(i).getKey(), currDate);
				
				int index = 0;
				double[][] array = new double[results.size()][2];
				for (Map.Entry<String, String> entry : results.entrySet()) {
					array[index][0] = Utils.convertString(entry.getKey());
					array[index][1] = Utils.convertDate(entry.getValue());
					index++;
				}
				response = new QueryResponse();
				response.setDatapoints(array);
				response.setType("timeserie");
				response.setTarget(list.get(i).getValue());
				resultsresponses.add(response);
				
				results.clear();
			}
			
			
			
			return resultsresponses;	
			
		}
		///
		else return dbQuery.runQuery(query);

		

	}

	private List<QueryResponse> queryfunctiontrend(List<QueryResponse> resultsresponses, String string,String userid) {

		QueryResponse response;
		String stage = "";
		if (string.equalsIgnoreCase("buildtrend"))
			stage = "Build";
		else if (string.equalsIgnoreCase("deploytrend"))
			stage = "Deploy";
		HashMap<String, String> results = new HashMap();
		String query = "select application_name from apporg ao, userorg uo where ao.org_name=uo.org_name and uo.user_name='"+userid+"'";
		List<String> app;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet rs = preparedStatement.executeQuery();) {
			
			app = new ArrayList<>();
			while (rs.next()) {
				app.add(rs.getString(1));
			}
			for (String appName : app) {

				query = "select(select count(buildstatus) from buildinfo " + "where stagename like '%" + stage + "%' "
						+ "and buildstatus='SUCCESS' and appid in "
						+ "(select id from appinfo where application_name=? ))"
						+ "::decimal*100/(select count(buildstatus) from buildinfo where " + "stagename like '%" + stage
						+ "%' and appid in (select id from appinfo where application_name=? ))" + ",current_timestamp";

				try (Connection connectionNew = postGreSqlDbContext.getConnection();
						PreparedStatement preparedStatementnew = connectionNew.prepareStatement(query)) {
					int index = 0;
					preparedStatementnew.setString(1, appName);
					preparedStatementnew.setString(2, appName);
					ResultSet rsnew = preparedStatementnew.executeQuery();
					List<String> appids = new ArrayList<>();
					results = new HashMap<>();
					while (rsnew.next()) {
						String temp=rsnew.getString(1);
						
						appids.add(temp);
						results.put(temp, rsnew.getString(2));
					}
					double[][] array = new double[results.size()][2];

					for (Map.Entry<String, String> entry : results.entrySet()) {
						array[index][0] = Utils.convertString(entry.getKey());
						array[index][1] = Utils.convertDate(entry.getValue());
						index++;
					}
					
					response = new QueryResponse();
					response.setDatapoints(array);
					response.setType("timeserie");
					response.setTarget(appName);
					resultsresponses.add(response);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				
			}

			return resultsresponses;
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return resultsresponses;
	}

	private List<QueryResponse> queryfunctionscore(List<QueryResponse> resultsresponses,String queryasked,String userid) {
		QueryResponse response;
		String query = "select application_name from apporg ao, userorg uo where ao.org_name=uo.org_name and uo.user_name='"+userid+"'";

		List<String> app;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet rs = preparedStatement.executeQuery();) {
			
			app = new ArrayList<>();
			while (rs.next()) {
				app.add(rs.getString(1));
			}
			double violations = 0;
			double violationsScore=0;
			for (String appName : app) {
				
				if(queryasked.toLowerCase().contains("coverage"))
				{
					query = "select distinct appid from codecoverage where appid in (select id from appinfo where application_name=?)";
				}
				else if(queryasked.toLowerCase().contains("analysis"))
					{
					query = "select distinct appid from codeanalysis where appid in (select id from appinfo where application_name=?)";
					}
					
				try (Connection connectionNew = postGreSqlDbContext.getConnection();
						PreparedStatement preparedStatementnew = connectionNew.prepareStatement(query)) {
					preparedStatementnew.setString(1, appName);
					ResultSet rsnew = preparedStatementnew.executeQuery();
					List<String> appIds = new ArrayList<>();
					while (rsnew.next()) {
						appIds.add(rsnew.getString(1));
					}
					violations=0;
					boolean validflag=false;
					for (String appID : appIds) {
						validflag=true;
						if(queryasked.toLowerCase().contains("violations"))
							query = "select count(*) from codeanalysis where appid=? and pipelineno=(select max(pipelineno) from codeanalysis where appid=?)";
						else if(queryasked.toLowerCase().contains("coverage"))
							query = "select avg(linecoverage) from codecoverage where appid=? and pipelineno=(select max(pipelineno) from codecoverage where appid=?)";
						
						try (Connection connectionNew1 = postGreSqlDbContext.getConnection();
								PreparedStatement prps = connectionNew1.prepareStatement(query)) {
							prps.setInt(1,Integer.parseInt(appID));
							prps.setInt(2,Integer.parseInt(appID));
							
							ResultSet rsnew1 = prps.executeQuery();
							while (rsnew1.next()) {
								violations+=Double.valueOf(rsnew1.getString(1));
								

							}
						}

					}
					if(!validflag)continue;
					violationsScore=violations/appIds.size();
					double[][] array = new double[1][2];
				
						array[0][0] = violationsScore;
						array[0][1] = Utils.convertDate("2017-12-04 16:20:00.51251");

					response = new QueryResponse();
					response.setDatapoints(array);
					response.setType("timeserie");
					response.setTarget("Application Name->" + appName);
					resultsresponses.add(response);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}

			return resultsresponses;
		} catch (Exception e) {

			e.printStackTrace();
		}

		return resultsresponses;
	}




	/**
	 * Method to handle table type requests
	 * 
	 * @param query
	 * @return
	 */

	public QueryResponse runTableQuery(String inputQuery,String userid) {
		String query=inputQuery.replace("\\", "");
		
		///
		logger.info("Executing table query : %s", query);
		
		List<Column> columns = new ArrayList();
		List<List<String>> rows = new ArrayList();
		Column c;
		
		

		List<String> singleRow = null;
		
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(query);
		QueryResponse response = new QueryResponse();
		response.setTarget(query);
		
		// if(query.toLowerCase().contains("sparkartifactsdetails"))
		// {


			
		// 	try (Connection connection = idpPostGreSqlDbContext.getConnection();
		// 			PreparedStatement preparedStatement = connection.prepareStatement(query);
		// 			ResultSet rs = preparedStatement.executeQuery();) {

				
		// 		ResultSetMetaData metars = rs.getMetaData();
		// 		if (metars.getColumnCount() != 0) {
		// 			for (int i = 1; i <= 4; i++) {
		// 				c = new Column();
		// 				c.setText(metars.getColumnName(i));
		// 				c.setType(metars.getColumnTypeName(i));
		// 				columns.add(c);
		// 			}

		// 			while (rs.next()) {
		// 				singleRow = new ArrayList<>();
		// 				for (int j = 1; j <=4; j++) {
		// 					if(j==2)singleRow.add(rs.getString(5)+"_"+rs.getString(6)+"_"+rs.getString(1));
		// 					else singleRow.add(rs.getString(j));
		// 				}
		// 				rows.add(singleRow);
		// 			}
		// 			logger.info(VALUES);
		// 			if (singleRow != null) {
		// 				for (String string : singleRow) {
		// 					logger.info(string);
		// 				}
		// 			}
		// 			response.setColumns(columns);
		// 			response.setRows(rows);

		// 			response.setType("table");

		// 		}
		// 		return response;
		// 	} catch (Exception e) {
				
		// 		logger.error("Exception in runTableQuery ", e);
		// 		return response;
		// 	}
		
		
		// }
		
		// else
		 if (query.toLowerCase().contains("saptransportobjectfinalincludingold")) {

			String releaseNo = query.substring(query.indexOf('_') + 1, query.indexOf('@'));
			String userStory = query.substring(query.indexOf('@') + 1, query.indexOf('#'));
			String transportRequest = query.substring(query.indexOf('#') + 1);
			transportRequest = transportRequest.trim();
			if ("N/A".equals(userStory))
				userStory = "";
			if (userStory.contains("$"))
				userStory = "";
			if (releaseNo.contains("$"))
				releaseNo = "";
			if (transportRequest.contains("$"))
				transportRequest = "";

			transportRequest = transportRequest.replaceAll("[()]", "");
			userStory = userStory.replaceAll("[()]", "");
			releaseNo = releaseNo.replaceAll("[()]", "");

			String qreleaseNo;
			String quserStory;
			String qtransportrequest;
			if ("".equals(releaseNo) || "N/A".equals(releaseNo))
				qreleaseNo = "release_number='' ";
			else
				qreleaseNo = "release_number='" + releaseNo + "' ";
			if ("".equals(transportRequest))
				qtransportrequest = "transport_request is NULL ";
			else
				qtransportrequest = "transport_request='" + transportRequest + "' ";
			if ("".equals(userStory))
				quserStory = "user_story='' ";
			else
				quserStory = "user_story='" + userStory + "' ";

			c = new Column();
			c.setText("ObjectName");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("objectType");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("TimeStamp");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("Author");
			c.setType("String");
			columns.add(c);
			Set<String> duplicateChecker = new HashSet();
			String queryObjectsOld = "Select distinct on(object_name,object_type,username) object_name,object_type,object_timestamp,username from tsap_deploy_details where transport_request='"
					+ transportRequest + "' and user_story='" + userStory
					+ "' and trigger_id in (Select trigger_id from ttrigger_history where release_number='" + releaseNo
					+ "' and trigger_id in (Select trigger_id from tsap_deploy_details))";
			String queryObjects = "select distinct on(tsap_tr_details.object_name,tsap_tr_details.object_type,tsap_tr_details.object_timestamp) tsap_tr_details.object_name,tsap_tr_details.object_type,tsap_tr_details.object_timestamp,username from tsap_tr_details INNER JOIN tsap_trigger_details ON tsap_tr_details.transport_request=tsap_trigger_details.transport_request where tsap_trigger_details."
					+ qtransportrequest + " and tsap_trigger_details." + quserStory + " and tsap_trigger_details."
					+ qreleaseNo + "";
			logger.info(queryObjects);
			logger.info(queryObjectsOld);
			try (Connection connectionNew = idpPostGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connectionNew.prepareStatement(queryObjects,
							ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					PreparedStatement preparedStatement1 = connectionNew.prepareStatement(queryObjectsOld,
							ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = preparedStatement.executeQuery();
					ResultSet rs1 = preparedStatement1.executeQuery();) {

				ResultSetMetaData metars = rs.getMetaData();

				ResultSetMetaData metars1 = rs1.getMetaData();

				singleRow = new ArrayList<>();

				if (metars.getColumnCount() != 0) {
					while (rs.next()) {

						String objectName;
						String objectType;
						String timeStamp;
						String author;
						objectName = rs.getString(1);
						objectType = rs.getString(2);
						timeStamp = rs.getString(3);
						author = rs.getString(4);
						String temp = objectName + objectType + timeStamp + author;
						if (duplicateChecker.contains(temp))
							continue;
						else
							duplicateChecker.add(temp);
						singleRow = new ArrayList<>();
						singleRow.add("<font color='orange'>" + objectName + "</font>");
						singleRow.add("<font color='skyblue'>" + objectType + "</font>");
						singleRow.add("<font color='skyblue'>" + timeStamp + "</font>");
						singleRow.add("<font color='skyblue'>" + author + "</font>");
						rows.add(singleRow);
					}
				}
				if (metars1.getColumnCount() != 0) {
					while (rs1.next()) {
						String objectName;
						String objectType;
						String timeStamp;
						String author;
						objectName = rs1.getString(1);
						objectType = rs1.getString(2);
						timeStamp = rs1.getString(3);
						author = rs1.getString(4);
						String temp = objectName + objectType + timeStamp + author;
						if (duplicateChecker.contains(temp))
							continue;
						else
							duplicateChecker.add(temp);
						singleRow = new ArrayList<>();
						singleRow.add("<font color='orange'>" + objectName + "</font>");
						singleRow.add("<font color='skyblue'>" + objectType + "</font>");
						singleRow.add("<font color='skyblue'>" + timeStamp + "</font>");
						singleRow.add("<font color='skyblue'>" + author + "</font>");
						rows.add(singleRow);
					}
				}
				logger.info(String.valueOf(singleRow.size()));

			} catch (Exception e) {
				logger.info(e.toString());
			}
			response.setColumns(columns);
			response.setRows(rows);
			response.setType("table");
			return response;

		} else if (query.toLowerCase().contains("saptransportrequestv3finalincludingoldtable")) {

			String releaseNo = query.substring(query.indexOf('_') + 1, query.indexOf('@'));
			//String application = query.substring(query.indexOf('@') + 1, query.indexOf('^'));
			String projectname = query.substring(query.indexOf('@') + 1, query.indexOf('^'));
			String userStory = query.substring(query.indexOf('^') + 1, query.indexOf('#'));
			String transportRequest = query.substring(query.indexOf('#') + 1);
			if (userStory.contains("$"))
				userStory = "";
			if (releaseNo.contains("$"))
				releaseNo = "";
			/*if (application.contains("$"))
				application = "";*/
			if (projectname.contains("$"))
				projectname = "";
			if (transportRequest.contains("$"))
				transportRequest = "";

			if ((transportRequest.startsWith("\\(") || transportRequest.startsWith("("))
					&& (transportRequest.endsWith("\\)") || transportRequest.endsWith(")")))
				transportRequest = transportRequest.substring(1, transportRequest.length() - 1);
			if ((userStory.startsWith("\\(") || userStory.startsWith("("))
					&& (userStory.endsWith("\\)") || userStory.endsWith(")")))
				userStory = userStory.substring(1, userStory.length() - 1);

			userStory = userStory.replaceAll("[()]", "");
			if ((releaseNo.startsWith("\\(") || releaseNo.startsWith("("))
					&& (releaseNo.endsWith("\\)") || releaseNo.endsWith(")")))
				releaseNo = releaseNo.substring(1, releaseNo.length() - 1);

			/*if ((application.startsWith("\\(") || application.startsWith("("))
					&& (application.endsWith("\\)") || application.endsWith(")")))
				application = application.substring(1, application.length() - 1);*/
			
			if ((projectname.startsWith("\\(") || projectname.startsWith("("))
					&& (projectname.endsWith("\\)") || projectname.endsWith(")")))
				projectname = projectname.substring(1, projectname.length() - 1);

			String[] trlist = transportRequest.split("\\|");
			String[] urlist = userStory.split("\\|");

			HashSet<String> uSSet = new HashSet<>();

			for (int i = 0; i < urlist.length; i++) {
				uSSet.add(urlist[i]);
			}
			
			String queryAppName = "select app.application_name from tapplication_info app inner join tsap_trigger_details saptri on app.application_name = saptri.application_name where saptri.project_name ='"+projectname+"'";
			
			List<String> appNameTemp = new ArrayList<>();
			try (Connection connectionNew1 = idpPostGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement1 = connectionNew1.prepareStatement(queryAppName,
							ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
				ResultSet rs1 = preparedStatement1.executeQuery();
				ResultSetMetaData metars1 = rs1.getMetaData();
				if (metars1.getColumnCount() != 0) {
					while (rs1.next()) {
						String env = rs1.getString(1);
						appNameTemp.add(env);
					}

				}

			} catch (Exception e) {
				logger.error(e.toString());
			}
			
			List<String> envTypeList = new ArrayList<>();
			List<String> envList = new ArrayList<>();
			Collections.addAll(envTypeList, "DEV", "QA", "HOTFIX", "PREPROD", "PROD");
			HashMap<String, ArrayList<String>> envTypeMap = new HashMap<>();

			for (int i = 0; i < envTypeList.size(); i++) {
				ArrayList<String> arraylist = new ArrayList<>();
				envTypeMap.put(envTypeList.get(i), arraylist);
			}

			
			Set<String> envSequenceTemp = new HashSet<>();
			
			for(int i = 0;i < appNameTemp.size(); i++) {
				String queryEnv = "select environment_name from tenvironment_master where application_id =(Select application_id from tapplication_info where application_name='"
						+ appNameTemp.get(i) + "') order by env_id ASC";
	
				
				try (Connection connectionNew1 = idpPostGreSqlDbContext.getConnection();
						PreparedStatement preparedStatement1 = connectionNew1.prepareStatement(queryEnv,
								ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
					ResultSet rs1 = preparedStatement1.executeQuery();
					ResultSetMetaData metars1 = rs1.getMetaData();
					if (metars1.getColumnCount() != 0) {
						while (rs1.next()) {
							String env = rs1.getString(1);
							envSequenceTemp.add(env);
						}
	
					}
	
				} catch (Exception e) {
					logger.error(e.toString());
				}
			}

			
			HashMap<String, String> envAndTypeMap = new HashMap<>();
			
			for(int i = 0;i < appNameTemp.size();i++) {
				String queryEnvAndType = "select distinct json_array_elements((entity_info::json->>'environmentOwnerDetails')::json)->>'environmentName',json_array_elements((entity_info::json->>'environmentOwnerDetails')::json)->>'landscapeType' from tapplication_info where application_name='"
						+ appNameTemp.get(i) + "'";
	
				
				try (Connection connectionNew1 = idpPostGreSqlDbContext.getConnection();
						PreparedStatement preparedStatement1 = connectionNew1.prepareStatement(queryEnvAndType,
								ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
					ResultSet rs1 = preparedStatement1.executeQuery();
					ResultSetMetaData metars1 = rs1.getMetaData();
					if (metars1.getColumnCount() != 0) {
						while (rs1.next()) {
							String env = rs1.getString(1);
							String type = rs1.getString(2);
							envAndTypeMap.put(env, type);
						}
	
					}
	
				} catch (Exception e) {
					logger.error(e.toString());
				}
			}

			 Iterator value = envSequenceTemp.iterator(); 
			 
			 while (value.hasNext()) {	
				String env = (String) value.next();
				String type = envAndTypeMap.get(env);
				ArrayList temp = envTypeMap.get(type);
				temp.add(env);
				envTypeMap.put(type, temp);
			}

			for (int i = 0; i < envTypeList.size(); i++) {

				ArrayList<String> eList = envTypeMap.get(envTypeList.get(i));
				envList.addAll(eList);
			}

			c = new Column();
			c.setText("UserStory");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("UserStory_hidden");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("TransportRequest");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("TransportRequest_hidden");
			c.setType("String");
			columns.add(c);
			for (int i = 0; i < envList.size(); i++) {
				c = new Column();
				c.setText(envList.get(i) + "_hidden");
				c.setType("String");
				columns.add(c);

				c = new Column();
				c.setText(envList.get(i));
				c.setType("String");
				columns.add(c);
			}

			String qreleaseNo;
			String quserStory;
			if (releaseNo.contains("$") || "".equals(releaseNo) || "N/A".equals(releaseNo))
				qreleaseNo = " release_number='' ";
			else
				qreleaseNo = " release_number='" + releaseNo + "' ";

			HashSet<String> tempUSList = new HashSet();
			String queryUS;
			if (releaseNo.equalsIgnoreCase("ALL")) {

				queryUS = "Select distinct user_story from tsap_trigger_details where landscape !='' ";

			} else {
				queryUS = "Select distinct user_story from tsap_trigger_details where " + qreleaseNo
						+ " and landscape !='' ";
			}

			try (Connection connectionNew1 = idpPostGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement1 = connectionNew1.prepareStatement(queryUS,
							ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
				ResultSet rs1 = preparedStatement1.executeQuery();
				ResultSetMetaData metars1 = rs1.getMetaData();
				if (metars1.getColumnCount() != 0) {
					while (rs1.next()) {
						String us = rs1.getString(1);
						if ("".equals(us))
							us = "N/A";
						tempUSList.add(us);
					}

				}

			} catch (Exception e) {
				logger.info(e.toString());
			}

			logger.info("values are");
			tempUSList.forEach(logger::info);
			//
			int count = 0;
			//
			logger.info("total user_story: " + urlist.length);
			for (int i = 0; i < urlist.length; i++) {
				String us = urlist[i];

				if ("".equals(us) || "N/A".equals(us))
					quserStory = " user_story = '' ";
				else
					quserStory = " user_story = '" + us + "' ";

				HashSet<String> tempTRList = new HashSet();
				String queryTR;
				if (releaseNo.equalsIgnoreCase("ALL")) {

					queryTR = "Select distinct transport_request from tsap_trigger_details where " + quserStory + "  ";

				} else {
					queryTR = "Select distinct transport_request from tsap_trigger_details where " + qreleaseNo
							+ " and " + quserStory + "  ";
				}

				try (Connection connectionNew1 = idpPostGreSqlDbContext.getConnection();
						PreparedStatement preparedStatement1 = connectionNew1.prepareStatement(queryTR,
								ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
					ResultSet rs1 = preparedStatement1.executeQuery();
					ResultSetMetaData metars1 = rs1.getMetaData();
					if (metars1.getColumnCount() != 0) {
						while (rs1.next()) {
							String tr = rs1.getString(1);
							tempTRList.add(tr);
						}

					}

				} catch (Exception e) {
					logger.error(e.toString());
				}

				logger.info("values are");
				tempTRList.forEach(logger::info);
				for (int j = 0; j < trlist.length; j++) {
					if (tempTRList.contains(trlist[j])) {
						if (tempUSList.contains(us)) {
							logger.info("user_story: " + us + "transportReq: " + trlist[j]);

							Set<String> hs = new HashSet<>();
							String queryLandscape;
							String queryLandscapeOld;
							if (releaseNo.equalsIgnoreCase("ALL")) {
								queryLandscape = "select distinct landscape from tsap_trigger_details where transport_request='"
										+ trlist[j] + "' and " + quserStory;
								queryLandscapeOld = "select distinct landscape from tsap_deploy_details where transport_request='"
										+ trlist[j] + "'  and " + quserStory
										+ "and trigger_id in (Select trigger_id from ttrigger_history where trigger_id in (Select trigger_id from tsap_deploy_details))";

							} else {
								queryLandscape = "select distinct landscape from tsap_trigger_details where transport_request='"
										+ trlist[j] + "' and " + qreleaseNo + " and " + quserStory;
								queryLandscapeOld = "select distinct landscape from tsap_deploy_details where transport_request='"
										+ trlist[j] + "'  and " + quserStory
										+ "and trigger_id in (Select trigger_id from ttrigger_history where (release_number='"
										+ releaseNo + "' or " + qreleaseNo
										+ ") and trigger_id in (Select trigger_id from tsap_deploy_details))";
							}

							if (count == 0 || (us.equalsIgnoreCase("") || (us.equalsIgnoreCase("npscc-957")))) {
								logger.info(queryLandscape);
								logger.info(queryLandscapeOld);
								count++;
							}
							try (Connection connectionNew1 = idpPostGreSqlDbContext.getConnection();
									PreparedStatement preparedStatement1 = connectionNew1.prepareStatement(
											queryLandscape, ResultSet.TYPE_SCROLL_SENSITIVE,
											ResultSet.CONCUR_UPDATABLE);
									PreparedStatement preparedStatement2 = connectionNew1.prepareStatement(
											queryLandscapeOld, ResultSet.TYPE_SCROLL_SENSITIVE,
											ResultSet.CONCUR_UPDATABLE);) {
								ResultSet rs1 = preparedStatement1.executeQuery();
								ResultSetMetaData metars1 = rs1.getMetaData();
								if (metars1.getColumnCount() != 0) {
									while (rs1.next()) {

										hs.add(rs1.getString(1));
									}

								}
								ResultSet rs2 = preparedStatement2.executeQuery();
								ResultSetMetaData metars2 = rs2.getMetaData();
								if (metars2.getColumnCount() != 0) {
									while (rs2.next()) {

										hs.add(rs2.getString(1));
									}

								}

							} catch (Exception e) {
								logger.error("error in search query");
							}
							if (!hs.isEmpty()) {
								singleRow = new ArrayList<>();

								singleRow.add("<font color='skyblue' > " + us + " </font>");
								singleRow.add(us);
								singleRow.add("<font color='orange' > " + trlist[j] + " </font>");
								singleRow.add(trlist[j]);
								for (int k = 0; k < envList.size(); k++) {
									singleRow.add(envList.get(k));

									if (hs.contains(envList.get(k))) {
										singleRow.add("<font color='green' > &#x2714</font>");
									} 
									else {
										singleRow.add("<font color='red' > &#x2716 </font>");
									}
								}
								rows.add(singleRow);
							}

						} else {
							singleRow = new ArrayList<>();

							//logger.info("////////////////User Story Dosen't Match///////////////////////  " + us);

							singleRow.add("<font color='skyblue' > " + us + " </font>");
							singleRow.add(us);
							singleRow.add("<font color='orange' > " + trlist[j] + " </font>");
							singleRow.add(trlist[j]);
							for (int k = 0; k < envList.size(); k++) {
								singleRow.add(envList.get(k));

								singleRow.add("<font color='red' > &#x2716 </font>");
							}
							rows.add(singleRow);
						}

					} else {
						logger.info(
								"////////////////Transport Requests Dosen't Match////////////////////////" + trlist[j]);
					}
				}
			}

			response.setColumns(columns);
			response.setRows(rows);
			response.setType("table");
			return response;

		} else if (query.toLowerCase().contains("saptransportrequestwodd")) {
			logger.info(query);
			String releaseNo = query.substring(query.indexOf('_') + 1);
			if (releaseNo.contains("$"))
				releaseNo = "";
			c = new Column();
			c.setText("UserStory");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("UserStory_hidden");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("EnvironmentName");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("TransferRequest");
			c.setType("String");
			columns.add(c);

			List<String> userStoryList = new ArrayList<>();
			String queryUserStory = "Select distinct user_story from tsap_trigger_details";
			try (Connection connectionNew = idpPostGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connectionNew.prepareStatement(queryUserStory,
							ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
				ResultSet rs = preparedStatement.executeQuery();
				ResultSetMetaData metars = rs.getMetaData();
				if (metars.getColumnCount() != 0) {
					while (rs.next()) {
						userStoryList.add(rs.getString(1));
					}

				}

			} catch (Exception e) {
				logger.error("error in search query");
			}
			for (int i = 0; i < userStoryList.size(); i++) {
				String queryenvironment = "Select distinct landscape from tsap_trigger_details where user_story='"
						+ userStoryList.get(i) + "'";
				logger.info(queryenvironment);
				try (Connection connectionNew = idpPostGreSqlDbContext.getConnection();
						PreparedStatement preparedStatement = connectionNew.prepareStatement(queryenvironment,
								ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
					ResultSet rs = preparedStatement.executeQuery();
					ResultSetMetaData metars = rs.getMetaData();
					if (metars.getColumnCount() != 0) {
						while (rs.next()) {
							String env = rs.getString(1);

							String transferRequestList = "";
							String querytr = "Select distinct transport_request from tsap_trigger_details where user_story='"
									+ userStoryList.get(i) + "' and landscape='" + env + "'";
							logger.info(querytr);
							try (Connection connectionNew1 = idpPostGreSqlDbContext.getConnection();
									PreparedStatement preparedStatement1 = connectionNew1.prepareStatement(querytr,
											ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
								ResultSet rs1 = preparedStatement1.executeQuery();
								while (rs1.next()) {
									transferRequestList += (" , " + rs1.getString(1));
								}
								transferRequestList = transferRequestList
										.substring(transferRequestList.indexOf(',') + 1);
							}

							catch (Exception e) {
								logger.error("error in search query");
							}
							if (!"".equals(transferRequestList)) {
								singleRow = new ArrayList<>();
								String us = userStoryList.get(i);
								singleRow.add("<font color='orange'>" + us + "</font>");
								singleRow.add(us);
								singleRow.add("<font color='skyblue'>" + env + "</font>");
								singleRow.add(transferRequestList);
								rows.add(singleRow);
							}
						}
					}
				} catch (Exception e) {
					logger.error("error in runtable query");
				}

			}

			response.setColumns(columns);
			response.setRows(rows);
			response.setType("table");
			return response;

		} 
		else if(query.toLowerCase().contains("maximodeploydetails"))
		{
			
			String appName=query.substring(query.indexOf("_")+1,query.indexOf("@"));
			String pipeline=query.substring(query.indexOf("@")+1,query.indexOf("#"));
			String pipelineNo=query.substring(query.indexOf("#")+1);
			String jobName=appName+"_"+pipeline;
//			String appName="TestApp14thMar";
//			String pipeline="Maximo_23rdMar";
//			String pipelineNo="5";
//			String jobName="TestApp14thMar_Maximo_23rdMar";
			
			String envSelected="";
			boolean haveDBC=false;
			boolean haveEAR=false;
			boolean haveXML=false;
			boolean haveWebLogic=false;
			c = new Column();
			c.setText("Type");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("Duration");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("Status");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("Environment");
			c.setType("String");
			columns.add(c);
			c = new Column();
			c.setText("Details");
			c.setType("String");
			columns.add(c);
			c=new Column();
			c.setText("ToolTip");
			c.setType("String");
			columns.add(c);
			List<IBMMaximoDeployDetails> deployStepList=new ArrayList<>();
			
			///get deploy step from jenkins api
			
			try{
				
				
				
				
				//http://dummyv07:8085/jenkins/job/ant27_ant27/job/ant27_ant27_Pipeline/4/api/json?pretty=true
				//String webPage = "http://" + jenkinsServer + "/job/" + jobName + "/job/" + jobName + "_Pipeline/" + pipelineId + "/api/json?pretty=true";
				String webPage=jenkinsURL+"/job/"+jobName+"/job/"+jobName+"_Pipeline/"+pipelineNo+"/api/json?pretty=true";
				String authString = configurationManager.getJenkinsID() + ":" + configurationManager.getJenkinsPassword();
				
				byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
				String authStringEnc = new String(authEncBytes);
				System.out.println(webPage);
				System.out.println(authString);
				logger.info("Printing jenkins details: authString: "+authString +" webpage: "+webPage);
				URL url = new URL(webPage);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
				//inputStream = urlConnection.getInputStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		        StringBuilder sb = new StringBuilder();
		        String line1;
		        while ((line1 = br.readLine()) != null) {
		            sb.append(line1+"\n");
		        }
		        br.close();
		        JSONObject json = new JSONObject(sb.toString());
				JSONArray actions=json.getJSONArray("actions");
				boolean flag=false;
				for(int i=0;i<actions.length();i++)
				{
					if(!flag)
					{
						JSONObject jObject=actions.getJSONObject(i);
						JSONArray parameters=jObject.getJSONArray("parameters");
						String name=parameters.getJSONObject(0).getString("name");
						String value=parameters.getJSONObject(0).getString("value");
						if(name.equalsIgnoreCase("json_input"))
						{
							flag=true;
							JSONObject valueJson=new JSONObject(value);
							JSONObject build=valueJson.getJSONObject("deploy");
							envSelected=valueJson.getString("envSelected");
							JSONArray deploySteps=build.getJSONArray("deployStep");
							for(int j=0;j<deploySteps.length();j++)
							{
								IBMMaximoDeployDetails ibmMaximoDeployDetails=new IBMMaximoDeployDetails();
								ibmMaximoDeployDetails.setDeployStepName(deploySteps.getString(j));
								deployStepList.add(ibmMaximoDeployDetails);
							}
							
						}
						
					}
				}
				System.out.println("Assda");
			
			}
			catch(Exception e)
			{
				logger.error("Exception while fetching maximo deploy details",e);
				
				
				
			}
			
			///get deploy type details
			for(int i=0;i<deployStepList.size();i++)
			{
				String deployStepName=deployStepList.get(i).getDeployStepName();
				try{


					
					
					
					//String webPage = "http://" + jenkinsServer + "/job/" + jobName + "/job/" + jobName + "_Pipeline/" + pipelineId + "/api/json?pretty=true";
					
					String webPage=jenkinsURL+"/job/"+jobName+"/job/"+jobName+"_Deploy_"+envSelected+"/job/"+jobName+"_Deploy_"+envSelected+"_"+deployStepName+"/config.xml";
					String authString = configurationManager.getJenkinsID() + ":" + configurationManager.getJenkinsPassword();
					
					byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
					String authStringEnc = new String(authEncBytes);
					System.out.println(webPage);
					System.out.println(authString);
					logger.info("Printing jenkins details: authString: "+authString +" webpage: "+webPage);
					URL url = new URL(webPage);
					URLConnection urlConnection = url.openConnection();
					urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
					//inputStream = urlConnection.getInputStream();

					BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			        StringBuilder sb = new StringBuilder();
			        String line1;
			        while ((line1 = br.readLine()) != null) {
			            sb.append(line1+"\n");
			        }
			        br.close();
//			        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
//			        DocumentBuilder builder;  
//			        try {  
//			            builder = factory.newDocumentBuilder();  
//			            Document document = builder.parse(new InputSource(new StringReader(sb.toString()))); 
//			            Element root = document.getDocumentElement();
//			            NodeList nodel = root.getChildNodes();
//			            for (int a = 0; a < nodel.getLength(); a++) {
//			            	Node node = nodel.item(a);
//			            	if(node instanceof Element) {
//			            	  String data = ((Element)node).getAttribute("name");
//			            	  System.out.println(data);
//			            	}
//			            	 }
//			            
//			        } catch (Exception e) {  
//			            e.printStackTrace();  
//			        } 
			        
			        String builder=sb.toString().substring(sb.toString().indexOf("<builders>")+11,sb.toString().lastIndexOf("</builders>"));
			        
			        if(builder.toLowerCase().contains("ant") && builder.toLowerCase().contains("deploy")){
			        	deployStepList.get(i).setDeployStepType("EAR");
			        	//String temp=builder.substring(builder.indexOf("<hudson.tasks.BatchFile>"),builder.lastIndexOf("</hudson.tasks.BatchFile>"));
			        	String command=builder.substring(builder.indexOf("<command>")+10, builder.lastIndexOf("</command>"));
			        	deployStepList.get(i).setHostName(command.substring(command.indexOf("start")+7));
			        	
			        	String newQuery="select artifact_name from tartifact_approval where release_id in(Select release_id from trelease_info where pipeline_id in (select pipeline_id from tpipeline_info where pipeline_name='"+pipeline+"' and application_id in (select application_id from tapplication_info where application_name='"+appName+"')) and application_id in (select application_id from tapplication_info where application_name='"+appName+"')) order by artifact_id DESC limit 1";
			        	try(Connection connection = idpPostGreSqlDbContext.getConnection();
								PreparedStatement preparedStatement = connection.prepareStatement(newQuery);){


							ResultSet rs = preparedStatement.executeQuery();
							ResultSetMetaData metars = rs.getMetaData();
							if (metars.getColumnCount() != 0) {
								
								while (rs.next()) {
									deployStepList.get(i).setArtifactName(rs.getString(1));
								}
								

							}
							
						
			        	}
			        	catch(Exception e)
			        	{
			        		
			        	}
			        	
			        	
			        	haveEAR=true;
			        }
			        else if(builder.toLowerCase().contains("update"))
			        {
			        	deployStepList.get(i).setDeployStepType("DBC");
			        	haveDBC=true;
			        }
			        else if(builder.toLowerCase().contains("weblogic"))
			        {
			        	deployStepList.get(i).setDeployStepType("WebLogic");
			        	haveWebLogic=true;
			        }
			        else{
			        	deployStepList.get(i).setDeployStepType("XML");
			        	haveXML=true;
			        }
					
								
				}
				catch(Exception e)
				{
					logger.error("Exception while fetching maximo deploy details",e);
				}
				String buildid="";
				String newQuery="select buildid from buildinfo where appid=(select id from appinfo where application_name='"+appName+"' and pipeline_name='"+pipeline+"')  and pipelineno='"+pipelineNo+"' and stagename = 'Deploy_"+envSelected+"_"+deployStepName+"'";
	        	try(Connection connection = postGreSqlDbContext.getConnection();
						PreparedStatement preparedStatement = connection.prepareStatement(newQuery.toString());){


					ResultSet rs = preparedStatement.executeQuery();
					ResultSetMetaData metars = rs.getMetaData();
					if (metars.getColumnCount() != 0) {
						
						while (rs.next()) {
							buildid=rs.getString(1);
						}
						

					}
					
				
	        	}
				catch(Exception e)
				{
					
				}
	        	
	        	try{


					
					
					
					//http://dummyv07:8085/jenkins/job/ant27_ant27/job/ant27_ant27_Pipeline/4/api/json?pretty=true
					//String webPage = "http://" + jenkinsServer + "/job/" + jobName + "/job/" + jobName + "_Pipeline/" + pipelineId + "/api/json?pretty=true";
					String webPage=jenkinsURL+"/job/"+jobName+"/job/"+jobName+"_Deploy_"+envSelected+"/job/"+jobName+"_Deploy_"+envSelected+"_"+deployStepName+"/"+buildid+"/api/json?pretty=true";
					String authString = jenkinsID + ":" + jenkinsPassword;
					
					byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
					String authStringEnc = new String(authEncBytes);

					URL url = new URL(webPage);
					URLConnection urlConnection = url.openConnection();
					urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
					//inputStream = urlConnection.getInputStream();

					BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			        StringBuilder sb = new StringBuilder();
			        String line1;
			        while ((line1 = br.readLine()) != null) {
			            sb.append(line1+"\n");
			        }
			        br.close();
			        JSONObject json = new JSONObject(sb.toString());
					String status=json.getString("result");
					String duration =json.getString("duration");
					deployStepList.get(i).setDuration(duration);
					deployStepList.get(i).setStatus(status);
				
	        	}
				catch(Exception e)
	        	{
					
	        	}
			}
			List<String> deployTypes=new ArrayList<>();
			deployTypes.add("DBC");
			deployTypes.add("EAR");
			deployTypes.add("XML");
			
			for(int i=0;i<deployTypes.size();i++)
			{
				for(int j=0;j<deployStepList.size();j++)
				{
					if(deployTypes.get(i).equals(deployStepList.get(j).getDeployStepType()))
					{
						singleRow = new ArrayList<>();
						singleRow.add("<font color='skyblue'>"+deployStepList.get(j).getDeployStepType()+"</font>");
						if(deployStepList.get(j).getDuration().equals("NA"))singleRow.add("NA");
						else singleRow.add(deployStepList.get(j).getDuration()+"ms");
						if(deployStepList.get(j).getStatus().toLowerCase().contains("success"))singleRow.add("<font color='green'>"+deployStepList.get(j).getStatus()+"</font>");
						else singleRow.add("<font color='red'>"+deployStepList.get(j).getStatus()+"</font>");
						singleRow.add("<font color='skyblue'>"+envSelected+"</font>");
						
						if(deployTypes.get(i).equals("EAR")){
							
							int p=deployStepList.get(j).getHostName().indexOf(":");
							String temp=deployStepList.get(j).getHostName().substring(0,p);
							
							int q=temp.lastIndexOf(" ");
							
							int r=deployStepList.get(j).getHostName().substring(p+1).indexOf(" ");
							
							String host=deployStepList.get(j).getHostName().substring(q+1,r+p+1);
							singleRow.add("<font color='skyblue'>Host</font> : "+host+"</br> <font color='skyblue'>Artifact</font> : "+deployStepList.get(j).getArtifactName());
						}
						else singleRow.add("<font color='yellow'>NA</font>");
						
						if(deployTypes.get(i).equals("EAR") && deployStepList.get(j).getStatus().toLowerCase().contains("success"))singleRow.add("Target state: undeploy completed on Server");
						else if(deployTypes.get(i).equals("EAR") && deployStepList.get(j).getStatus().toLowerCase().contains("fail"))singleRow.add("Target state: undeploy failed on Server");
						else singleRow.add("NA");
						
						rows.add(singleRow);
					}
				}
			}
			if(!haveDBC)
			{
				singleRow = new ArrayList<>();
				singleRow.add("<font color='skyblue'>DBC</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("NA");
				rows.add(singleRow);
			}
			if(!haveEAR)
			{
				singleRow = new ArrayList<>();
				singleRow.add("<font color='skyblue'>EAR</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("NA");
				rows.add(singleRow);
			}
			if(!haveXML)
			{
				singleRow = new ArrayList<>();
				singleRow.add("<font color='skyblue'>XML</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("<font color='yellow'>NA</font>");
				singleRow.add("NA");
				rows.add(singleRow);
			}
			
			
			
			response.setColumns(columns);
			response.setRows(rows);
			response.setType("table");
			return response;
			
			
		}
		
		else if(query.toLowerCase().contains("sappipelinedetails"))
		{
			String app,env,pipeline;
			app=query.substring(query.indexOf("_")+1,query.indexOf("@"));
			pipeline=query.substring(query.indexOf("@")+1,query.indexOf("#"));
			env=query.substring(query.indexOf("#")+1);
			String query1="select version as \"ExecutionNo\",json_array_elements((trigger_entity::json->>\'transportRequest\')::json) as \"Transport Request\",trigger_entity::json->>\'userStories\' as \"User Story\",trigger_entity::json->>\'userName\' as \"User Name\",to_char(trigger_time,'YY/MM/DD HH12:MI')  as \"Trigger Time\" from ttrigger_history where(trigger_entity::json ->> 'applicationName'='"+app+"' and trigger_entity::json ->> 'pipelineName' ='"+pipeline+"'  and trigger_entity::json ->> 'envSelected'='"+env+"')";
			try (Connection connectionNew1 = idpPostGreSqlDbContext.getConnection();PreparedStatement preparedStatement = connectionNew1.prepareStatement(query1,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);) {
				
				ResultSet rs = preparedStatement.executeQuery();
				ResultSetMetaData metars = rs.getMetaData();
				if (metars.getColumnCount() != 0) {
					for (int i = 1; i <= metars.getColumnCount(); i++) {
						c = new Column();
						c.setText(metars.getColumnName(i));
						c.setType(metars.getColumnTypeName(i));
						columns.add(c);
					}

					while (rs.next()) {
						singleRow = new ArrayList<>();
//						for (int j = 1; j <= metars.getColumnCount(); j++) {
//							singleRow.add(rs.getString(j));
//						}
						singleRow.add(rs.getString(1));
						String temp=rs.getString(2);
						int length=temp.length();
						temp=temp.substring(1, length-2);
						singleRow.add(temp);
						singleRow.add(rs.getString(3));
						singleRow.add(rs.getString(4));
						singleRow.add(rs.getString(5));
						rows.add(singleRow);
					}
					logger.info("values are");
					if (singleRow != null) {
						for (String string : singleRow) {
							logger.info(string);
						}
					}
					response.setColumns(columns);
					response.setRows(rows);

					response.setType("table");

				}
				return response;
			} catch (Exception e) {
				logger.error("Exception in runTableQuery ", e);
				return response;
			}
		}

		
		else if(query.toLowerCase().contains("artifactviewdeploydetails"))
			
		{
			String application=query.substring(query.indexOf('_')+1,query.indexOf('@'));
			String pipeline=query.substring(query.indexOf('@')+1,query.indexOf('#'));
			
			
			String newQuery="SELECT stagename as \"Stage\",buildstatus as \"Status\",created_at as \"Timestamp\" FROM public.buildinfo where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"') and stagename like '%Deploy%' order by created_at DESC";
	
			try (Connection connectionNew1 = postGreSqlDbContext.getConnection();PreparedStatement preparedStatement = connectionNew1.prepareStatement(newQuery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);) {
				
				ResultSet rs = preparedStatement.executeQuery();
				ResultSetMetaData metars = rs.getMetaData();
				if (metars.getColumnCount() != 0) {
					for (int i = 1; i <= 3; i++) {
						c = new Column();
						c.setText(metars.getColumnName(i));
						c.setType(metars.getColumnTypeName(i));
						columns.add(c);
					}
					
					String stage="";
					Set<String> envset = new HashSet<>();
					
					while (rs.next()) {
						
						try{
							stage=rs.getString(1);
							stage=stage.substring(stage.indexOf('_')+1,stage.indexOf('_',stage.indexOf('_')+1));
							
							if(!envset.contains(stage))
							{
								singleRow = new ArrayList<>();
								singleRow.add("<font color='skyblue'>"+stage+"</font>");
								if(rs.getString(2).toLowerCase().contains("success"))singleRow.add("<font color='green'>"+rs.getString(2)+"</font>");
								else singleRow.add("<font color='red'>"+rs.getString(2)+"</font>");
								singleRow.add(rs.getString(3));

								rows.add(singleRow);
								envset.add(stage);
							}
						}
						catch(Exception e)
						{
							logger.error("Exception while fetching environment ", e);
						}
					}
					logger.info(VALUES);
					if (singleRow != null) {
						for (String string : singleRow) {
							logger.info(string);
						}
					}
					response.setColumns(columns);
					response.setRows(rows);

					response.setType("table");

				}
				return response;
			} catch (Exception e) {
				logger.error("Exception in runTableQuery ", e);
				return response;
			}
		}
		
		
else if(query.toLowerCase().contains("artifactviewtestdetails"))
			
		{
			String application=query.substring(query.indexOf('_')+1,query.indexOf('@'));
			String pipeline=query.substring(query.indexOf('@')+1,query.indexOf('#'));
			String artifactname=query.substring(query.indexOf('^')+1);
			String pipelineno="";
			String pipelineno1=artifactname.substring(artifactname.lastIndexOf('_')+1);
			String pipelineno2="";
			try{
				pipelineno2=artifactname.substring(artifactname.lastIndexOf('-')+1);
			}
				catch(Exception e){logger.error(e.getMessage());}
			if(!"".equals(pipelineno2) && pipelineno2.length()<pipelineno1.length())pipelineno=pipelineno2;
			else pipelineno=pipelineno1;
			

			
			String newQuery="SELECT stagename as \"Stage\",buildstatus as \"Status\",created_at as \"Timestamp\" FROM public.buildinfo where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"') and stagename like '%Test%' order by created_at DESC";
			logger.info(newQuery);
			try (Connection connectionNew1 = postGreSqlDbContext.getConnection();PreparedStatement preparedStatement = connectionNew1.prepareStatement(newQuery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);) {
				
				ResultSet rs = preparedStatement.executeQuery();
				ResultSetMetaData metars = rs.getMetaData();
				if (metars.getColumnCount() != 0) {
					for (int i = 1; i <= 3; i++) {
						c = new Column();
						c.setText(metars.getColumnName(i));
						c.setType(metars.getColumnTypeName(i));
						columns.add(c);
					}
					
					String stage="";
					
					Set<String> envset = new HashSet<>();
					
					while (rs.next()) {
						
						try{
							stage=rs.getString(1);
							stage=stage.substring(stage.indexOf('_')+1,stage.indexOf('_',stage.indexOf('_')+1));
							logger.info(stage);
							double pass=0;
							double total=0;
							if(!envset.contains(stage))
							{
								///
								
								newQuery="select count(*),max(created_at) from testanalysis where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"')  and pipelineno='"+pipelineno+"' and (status='SUCCESS' or status='Passed' or status ='passed')";
								logger.info(newQuery);
								try (Connection connectionNew2 = postGreSqlDbContext.getConnection();PreparedStatement preparedStatement1 = connectionNew2.prepareStatement(newQuery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);) {
								
									ResultSet rs1 = preparedStatement1.executeQuery();
									if (metars.getColumnCount() != 0) {
										while(rs1.next())
										{
											pass=Utils.convertString(rs1.getString(1));
										}
										
									}
								}
								catch(Exception e)
								{
									logger.error("Exception while fetching testresults ", e);
								}
								
								newQuery="select count(*),max(created_at) from testanalysis where appid=(select id from appinfo where application_name='"+application+"' and pipeline_name ='"+pipeline+"')  and pipelineno='"+pipelineno+"'";
								logger.info(newQuery);
								try (Connection connectionNew2 = postGreSqlDbContext.getConnection();PreparedStatement preparedStatement1 = connectionNew2.prepareStatement(newQuery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);) {
								
									ResultSet rs1 = preparedStatement1.executeQuery();
									if (metars.getColumnCount() != 0) {
										while(rs1.next())
										{
											total=Utils.convertString(rs1.getString(1));
										}
										
									}
								}
								catch(Exception e)
								{
									logger.error("Exception while fetching testresults ", e);
								}
								///
								double status=(pass/total)*100;
								singleRow = new ArrayList<>();
								singleRow.add("<font color='skyblue'>"+stage+"</font>");
								String temp=Double.toString(status)+" %";
								singleRow.add("<font color='orange'>"+temp+"</font>");
								
								singleRow.add(rs.getString(3));

								rows.add(singleRow);
								envset.add(stage);
							}
						}
						catch(Exception e)
						{
							logger.error("Exception while fetching environment ", e);
						}
					}
					logger.info(VALUES);
					if (singleRow != null) {
						for (String string : singleRow) {
							logger.info(string);
						}
					}
					response.setColumns(columns);
					response.setRows(rows);

					response.setType("table");

				}
				return response;
			} catch (Exception e) {
				logger.error("Exception in runTableQuery ", e);
				return response;
			}
		}
		
else if(query.toLowerCase().contains("artifactviewalmdetails"))
{
	String application=query.substring(query.indexOf('_')+1,query.indexOf('@'));
	String pipeline=query.substring(query.indexOf('@')+1,query.indexOf('#'));
	String releaseno=query.substring(query.indexOf('#')+1,query.indexOf('^'));
	
	

	c = new Column();
	c.setText("ID");
	c.setType("String");
	columns.add(c);
	c = new Column();
	c.setText("Title");
	c.setType("String");
	columns.add(c);

	c = new Column();
	c.setText("Type");
	c.setType("String");
	columns.add(c);
	c = new Column();
	c.setText("Link");
	c.setType("String");
	columns.add(c);

	///vsts
	String newQuery="SELECT tfs_workitem FROM ttrigger_history where (tfs_workitem!='' and trigger_entity::json->>'applicationName'='"+application+"' and trigger_entity::json->>'pipelineName'='"+pipeline+"' and trigger_entity::json->>'releaseNumber'='"+releaseno+"' and trigger_entity::json->>'artifactorySelected'='on')";
	logger.info(newQuery);
	
	
	try (Connection connectionNew1 = idpPostGreSqlDbContext.getConnection();PreparedStatement preparedStatement = connectionNew1.prepareStatement(newQuery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);) {
					
					ResultSet rs = preparedStatement.executeQuery();
					ResultSetMetaData metars = rs.getMetaData();
					if (metars.getColumnCount() != 0) {
						
						
						String tfsId="";
						String story="";
						String type="";
						
						while (rs.next()) {
							
							try{
								tfsId=rs.getString(1);
								///vsts connection
								


								
								String requesturl=mtmurl+"_apis/wit/workitems?ids=";
								requesturl+=tfsId+"&$expand=all&api-version=1.0";
										JSONObject json=getVSTSDetails(requesturl);
						                JSONArray jsonArray = json.getJSONArray("value");
						                for(int i=Math.max(0, -1);i<jsonArray.length();i++)
						                {
						                	JSONObject fields=jsonArray.getJSONObject(i).getJSONObject("fields");
						                	story=fields.getString("System.Title");
						                	type=fields.getString("System.WorkItemType");
						                	
//						                	
						                }	
						        
						        if(story!="")
						        {
						        	singleRow = new ArrayList<>();
									singleRow.add("<font color='skyblue'>"+tfsId+"</font>");
									singleRow.add(story);
									singleRow.add(type);
									singleRow.add(mtmurl+mtmproject+"/_workitems?id="+tfsId);
									rows.add(singleRow);
						        }
								
								///
							}
							catch(Exception e)
							{
								logger.error("Exception while fetching tfsWorkItem Details ", e);
							}
						}
						logger.info(VALUES);
						if (singleRow != null) {
							for (String string : singleRow) {
								logger.info(string);
							}
						}
						
	
					}
					
				} catch (Exception e) {
					logger.error("Exception while accessing vsts ", e);
					
				}
	
	///jira
	newQuery="SELECT userstorystring FROM ttrigger_history where (userstorystring!='' and trigger_entity::json->>'applicationName'='"+application+"' and trigger_entity::json->>'pipelineName'='"+pipeline+"' and trigger_entity::json->>'releaseNumber'='"+releaseno+"')";
	logger.info(newQuery);
	try (Connection connectionNew1 = idpPostGreSqlDbContext.getConnection();PreparedStatement preparedStatement = connectionNew1.prepareStatement(newQuery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);) {
		
		ResultSet rs = preparedStatement.executeQuery();
		ResultSetMetaData metars = rs.getMetaData();
		if (metars.getColumnCount() != 0) {
			
			
			String issueId="";
			String story="";
			String type="";
			
			while (rs.next()) {
				
				try{
					issueId=rs.getString(1);
					///jira connection
					
					String jiraurl1 = jiraurl;
					String username = jirauser;
					String password = jirapassword;
					String s = username+":"+password; 
					String encoding = "Basic " + new String(new Base64().encode(s.getBytes()));
					String requesturl=jiraurl1+"/rest/api/2/issue/";
					requesturl+=issueId;
					URL url = new URL(requesturl);	
					
					
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setDoOutput(false);
					connection.setRequestProperty ("Authorization", encoding);
					logger.info(String.valueOf(connection.getResponseCode()));
					 
			                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			                StringBuilder sb = new StringBuilder();
			                String line;
			                while ((line = br.readLine()) != null) {
			                    sb.append(line+"\n");
			                }
			                br.close();
			                JSONObject json = new JSONObject(sb.toString());
			                JSONObject fields = json.getJSONObject("fields");
			                JSONObject issueType = fields.getJSONObject("issuetype");
			               

			                story=issueType.getString("description");
			                type=issueType.getString("name");
			                String key=json.getString("key");

			        
			                connection.disconnect();

			                if(story!="")
					        {
					        	singleRow = new ArrayList<>();
								singleRow.add("<font color='skyblue'>"+issueId+"</font>");
								singleRow.add(story);
								singleRow.add(type);
								singleRow.add("http://dr-jiraappdb01:8080/browse/"+key);

								rows.add(singleRow);
					        }
					
					
					///
				}
				catch(Exception e)
				{
					logger.error("Exception while fetching jira issues Details ", e);
				}
			}
			logger.info(VALUES);
			if (singleRow != null) {
				for (String string : singleRow) {
					logger.info(string);
				}
			}
			

		}
		
	} catch (Exception e) {
		logger.error("Exception while accessing vsts ", e);
		
	}
	response.setColumns(columns);
	response.setRows(rows);
	response.setType("table");
	return response;
	


}
else if(query.toLowerCase().contains("artifactviewpackagecontent"))
{
	String application=query.substring(query.indexOf("_")+1,query.indexOf("@"));
	String pipeline=query.substring(query.indexOf("@")+1,query.indexOf("#"));
	String releasno=query.substring(query.indexOf("#")+1,query.indexOf("^"));
	String artifactname=query.substring(query.indexOf("^")+1);
	String pipelineno="";
	String pipelineno1=artifactname.substring(artifactname.lastIndexOf("_")+1);
	String pipelineno2="";
	try{
		pipelineno2=artifactname.substring(artifactname.lastIndexOf("-")+1);
	}
		catch(Exception e){}
	if(!"".equals(pipelineno2) && pipelineno2.length()<pipelineno1.length())pipelineno=pipelineno2;
	else pipelineno=pipelineno1;
	
	//artifactname="demoapp_demopip_2";
	String newQuery="Select package_content from tartifact_approval where artifact_name='"+artifactname+"'";
	System.out.println(newQuery);
	try (Connection connectionNew1 = idpPostGreSqlDbContext.getConnection();PreparedStatement preparedStatement = connectionNew1.prepareStatement(newQuery,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);) {
		
		ResultSet rs = preparedStatement.executeQuery();
		ResultSetMetaData metars = rs.getMetaData();
		if (metars.getColumnCount() != 0) {
			while(rs.next())
			{
				
				///for informatica
				try{

					String packageContent=rs.getString(1);
					JSONObject jsonObj = new JSONObject(packageContent);
					JSONObject informatica=jsonObj.getJSONObject("informatica");
					
					c = new Column();
					c.setText("FolderName(Technology-Informatica)");
					c.setType("String");
					columns.add(c);
					c = new Column();
					c.setText("Type(Technology-Informatica)");
					c.setType("String");
					columns.add(c);
					c = new Column();
					c.setText("FileName(Technology-Informatica)");
					c.setType("String");
					columns.add(c);
					
					JSONArray infoObject=informatica.getJSONArray("infoObject");
					for(int i=0;i<infoObject.length();i++)
					{
						String folder=infoObject.getJSONObject(i).getString("folderName");
						
						String [] types={"sequence","mapping","worklet","workflow","sessionconfig","session","sourceDefinition","mapplet","targetDefinition"};
						
						for(int k=0;k<types.length;k++)
						{
							String type=types[k];
							try{JSONArray sequence=infoObject.getJSONObject(i).getJSONArray(type);
							for(int j=0;j<sequence.length();j++)
							{
								singleRow = new ArrayList<>();
								//singleRow.add("<font color='skyblue'>Informatica</font>");
								singleRow.add(folder);
								singleRow.add(type);
								//JSONObject file=sequence.getJSONObject(j);
								String t=sequence.getString(j);
								singleRow.add(t);
								rows.add(singleRow);
							}
							}catch(Exception e){logger.error("Exception while fetching "+type+" for Informatica ");}
							}
						

					}
					
				
				}
				catch(Exception e)
				{
					logger.error("Error while fetching package content for informatica");
				}
				
				///for Dotnet
				try{
					String packageContent=rs.getString(1);
					JSONObject jsonObj = new JSONObject(packageContent);
					JSONObject dotNet=jsonObj.getJSONObject("dotNet");
					
					c = new Column();
					c.setText("ModuleName(Technology-DotNet)");
					c.setType("String");
					columns.add(c);
					
					
					JSONArray moduleList=dotNet.getJSONArray("moduleName");
					for(int j=0;j<moduleList.length();j++)
					{
						singleRow = new ArrayList<>();
						//singleRow.add("<font color='skyblue'>DotNet</font>");
						
						//JSONObject file=sequence.getJSONObject(j);
						String t=moduleList.getString(j);
						singleRow.add(t);
						rows.add(singleRow);
					}
				}
				catch(Exception e)
				{
					logger.error("Error while fetching package content for Dotnet");
				}
				
				
				///for Ant
				try{

					String packageContent=rs.getString(1);
					JSONObject jsonObj = new JSONObject(packageContent);
					JSONObject ant=jsonObj.getJSONObject("ant");
					
					c = new Column();
					c.setText("ModuleName(Technology-Ant)");
					c.setType("String");
					columns.add(c);
					
					
					JSONArray moduleList=ant.getJSONArray("moduleName");
					for(int j=0;j<moduleList.length();j++)
					{
						singleRow = new ArrayList<>();
						//singleRow.add("<font color='skyblue'>Ant</font>");
						
						//JSONObject file=sequence.getJSONObject(j);
						String t=moduleList.getString(j);
						singleRow.add(t);
						rows.add(singleRow);
					}
				
				}
				catch(Exception e)
				{
					logger.error("Error while fetching package content for Ant");
				}
				
				///for siebel
				try{
					

					String packageContent=rs.getString(1);
					JSONObject jsonObj = new JSONObject(packageContent);
					JSONObject siebel=jsonObj.getJSONObject("siebel");
					c = new Column();
					c.setText("Type(Technology-Siebel)");
					c.setType("String");
					columns.add(c);
					c = new Column();
					c.setText("File Name(Technology-Siebel)");
					c.setType("String");
					columns.add(c);
					
					JSONArray repoList=siebel.getJSONArray("repoList");
					for(int j=0;j<repoList.length();j++)
					{
						singleRow = new ArrayList<>();
						singleRow.add("<font color='skyblue'>Repo</font>");
						
						//JSONObject file=sequence.getJSONObject(j);
						String t=repoList.getString(j);
						singleRow.add(t);
						rows.add(singleRow);
					}
					JSONArray nonRepoList=siebel.getJSONArray("nonRepoList");
					for(int j=0;j<nonRepoList.length();j++)
					{
						singleRow = new ArrayList<>();
						singleRow.add("<font color='skyblue'>Non Repo</font>");
						
						//JSONObject file=sequence.getJSONObject(j);
						String t=nonRepoList.getString(j);
						singleRow.add(t);
						rows.add(singleRow);
					}
					
				}
				catch(Exception e)
				{
					logger.error("Error while fetching package content for Siebel");
				}
				
				/// for pega
				try{

					String packageContent=rs.getString(1);
					JSONObject jsonObj = new JSONObject(packageContent);
					JSONObject ant=jsonObj.getJSONObject("pega");
					
					c = new Column();
					c.setText("FileName(Technology-Pega)");
					c.setType("String");
					columns.add(c);
					
					
					JSONArray pegaFileList=ant.getJSONArray("pegaFileList");
					for(int j=0;j<pegaFileList.length();j++)
					{
						singleRow = new ArrayList<>();
						//singleRow.add("<font color='skyblue'>Ant</font>");
						
						//JSONObject file=sequence.getJSONObject(j);
						String t=pegaFileList.getString(j);
						singleRow.add(t);
						rows.add(singleRow);
					}
				
				}
				catch(Exception e)
				{
					logger.error("Error while fetching package content for Pega");
				}
				
				/// for Bigdata
				try{

					String packageContent=rs.getString(1);
					JSONObject jsonObj = new JSONObject(packageContent);
					JSONObject ant=jsonObj.getJSONObject("bigData");
					
					c = new Column();
					c.setText("ModuleName(Technology-Bigdata)");
					c.setType("String");
					columns.add(c);
					
					
					JSONArray moduleList=ant.getJSONArray("moduleName");
					for(int j=0;j<moduleList.length();j++)
					{
						singleRow = new ArrayList<>();
						//singleRow.add("<font color='skyblue'>Ant</font>");
						
						//JSONObject file=sequence.getJSONObject(j);
						String t=moduleList.getString(j);
						singleRow.add(t);
						rows.add(singleRow);
					}
				
				}
				catch(Exception e)
				{
					logger.error("Error while fetching package content for Bigdata");
				}
			}
		}
		response.setColumns(columns);
		response.setRows(rows);
		response.setType("table");
		return response;
	} catch (Exception e) {
		logger.error("Exception in runTableQuery ", e);
		return response;
	}
}
		
		else if(query.toLowerCase().contains("storystatusboard"))
		{


			
			String app=query.substring(query.indexOf('_')+1,query.indexOf('@'));
			String releaseNo=query.substring(query.indexOf('@')+1);
			logger.info(app+" : "+releaseNo);
			query = "select distinct t2.tfs_workitem as WIT " + ",t1.pipeline_name,t2.environment as Environment "
					+ "from tpipeline_info as t1 " + "inner join"
					+ "(select release_number,pipeline_id,tfs_workitem,environment from ttrigger_history "
					+ "where release_number=? " + "and pipeline_id in " + "(select pipeline_id from tpipeline_info "
					+ "where application_id in " + "(select application_id from tapplication_info "
					+ "where application_name=? ))) " + "as t2 on t1.pipeline_id=t2.pipeline_id";
		
			logger.info(query);
		try (Connection connection = idpPostGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, releaseNo);
			preparedStatement.setString(2, app);

			ResultSet rs = preparedStatement.executeQuery();
			ResultSetMetaData metars = rs.getMetaData();
			if (metars.getColumnCount() != 0) {

					c = new Column();
					c.setText("StoryID");
					c.setType("text");
					columns.add(c);
					c = new Column();
					c.setText("Story");
					c.setType("text");
					columns.add(c);
					c = new Column();
					c.setText("Pipeline");
					c.setType("text");
					columns.add(c);
					c = new Column();
					c.setText("Environment");
					c.setType("text");
					columns.add(c);
					c = new Column();
					c.setText("% Test Success");
					c.setType("text");
					columns.add(c);
		
					
				while(rs.next())
				{

					
					singleRow = new ArrayList<>();
					String storyId;
					String story;
					String pipeline;
					String environment;
					String successrate;
					storyId=rs.getString(1);
					pipeline=rs.getString(2);
					environment=rs.getString(3);
					story="";
					Random rand = new Random();
					int  n = rand.nextInt(100) + 1;
					successrate=Integer.toString(n);
					System.out.println("storyId "+storyId);
					//////
					if(storyId!=null && !"".equals(storyId))
					{

						
						String requesturl=mtmurl+"_apis/wit/workitems?ids=";
						requesturl+=storyId+"&$expand=all&api-version=1.0";
						
						

								JSONObject json=getVSTSDetails(requesturl);
				                JSONArray jsonArray = json.getJSONArray("value");
				                String temp="";
				                for(int i=Math.max(0, -1);i<jsonArray.length();i++)
				                {
				                	JSONObject fields=jsonArray.getJSONObject(i).getJSONObject("fields");
				                	temp=fields.getString("System.Title");
				                	
				                	
//				                	
				                }	
				                story=temp;
					}


					    singleRow.add(storyId);
					    singleRow.add(story);
			        singleRow.add(pipeline);
			        singleRow.add(environment);
			        singleRow.add(successrate);
			        rows.add(singleRow);
				
					
				}
				

				response.setColumns(columns);
				response.setRows(rows);

				response.setType("table");

			}
			return response;
		} catch (Exception e) {
			logger.error("Exception in runTableQuery ", e);
			return response;
		}
		}
		
		else
			return dbQuery.runTableQuery(query,userid);
		
	}


	/**
	 * 
	 * @param query
	 * @return
	 */
	
	private JSONObject getVSTSDetails(String requesturl)
	{
		
		
		String userpass = mtmuser+":"+mtmpassword;
		//String userpass="krishnakanthbn"+":"+"infy@123";
		String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));	
		HttpClientBuilder builder = HttpClientBuilder.create();

		CloseableHttpClient httpclient = null;
		
		CredentialsProvider credsProvider = new BasicCredentialsProvider();

		if (proxyip != null && !"".equalsIgnoreCase(proxyip)) {
		// Create credential for authentication
		credsProvider.setCredentials(
				//AuthScope.ANY,
				//new AuthScope(machine, port),
				new AuthScope(proxyip,Integer.parseInt(proxyport)),

				new UsernamePasswordCredentials(proxyuser, proxypassword));
		
		//http proxy
		HttpHost newProxy = new HttpHost(proxyip,Integer.parseInt(proxyport));
		
		// Build closable http client with the provided credentials
		httpclient = builder
				.setProxy(newProxy)
				//.setProxyAuthenticationStrategy(proxyAuthStrategy)
				.setDefaultCredentialsProvider(credsProvider)
				.build();
		}
		else {
			httpclient = builder
					.build();
		}
		
		// HTTP GET request
		HttpGet httpGet = new HttpGet(requesturl);//6861
		
		httpGet.addHeader("Content-Type", "application/json");
		httpGet.addHeader("Authorization", basicAuth);
		
		CloseableHttpResponse response1 = null;
		
		
		try {
			response1 = httpclient.execute(httpGet);
			
			if((response1.getStatusLine().getStatusCode() / 100) == 2){
				logger.info("Field values fetched successfully using GET request: "+response1.getStatusLine());
			}
			else{
				logger.info("Failed GET request - HTTP error code : "+response1.getStatusLine());
				throw new Exception("Failed to perform get request");
			}
			HttpEntity entity1 = response1.getEntity();
			BufferedReader rd = new BufferedReader(
					new InputStreamReader(entity1.getContent()));


			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();

			return new JSONObject(result.toString());
		}  catch(Exception e){
			
			logger.info("error");
			return null;
		}
	}

	
		public QueryResponse runTableQuerySCM(String query){
		logger.info("Executing table query ", query);
		List<Column> columns = new ArrayList<>();
		List<List<String>> rows = new ArrayList<>();
		Column c;
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(query);
		QueryResponse response = new QueryResponse();
		response.setTarget(query);
		List<String> singleRow = null;

		try(Connection connectionNew1 = postGreSqlDbContext.getConnection();PreparedStatement preparedStatement = connectionNew1.prepareStatement(queryStatement.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = preparedStatement.executeQuery();)  {
			
			
			ResultSetMetaData metars = rs.getMetaData();
			if (metars.getColumnCount() != 0) {
				for (int i = 1; i <= metars.getColumnCount(); i++) {
					c = new Column();
					c.setText(metars.getColumnName(i));
					c.setType(metars.getColumnTypeName(i));
					columns.add(c);
				}

				while (rs.next()) {
					singleRow = new ArrayList<>();
					for (int j = 1; j <= metars.getColumnCount(); j++) {
						singleRow.add(rs.getString(j));
					}
					rows.add(singleRow);
				}
				logger.info(VALUES);
				if (singleRow != null) {
					for (String string : singleRow) {
						logger.info(string);
					}
				}
				response.setColumns(columns);
				response.setRows(rows);

				response.setType("table");

			}
			return response;
		} catch (Exception e) {
			logger.error("Exception in runTableQuery ", e);
			return response;
		}

		
	}
	
		/**
		 * method to handle templating/variable based queries
		 * 
		 * @param applicationname
		 * @param pipelinename
		 * @return
		 */
	public String runFetchQuery(String applicationname,String pipelinename) {
		
		String appid = "";
		try (Connection restconnection1 = postGreSqlDbContext.getConnection();
				PreparedStatement restStatement1 = restconnection1.prepareStatement("select id from appinfo where application_name='"+applicationname+"' and pipeline_name='"+pipelinename+"'");
				ResultSet rs2 = restStatement1.executeQuery();) {
			
			if (rs2.next()) {
				appid = rs2.getString(1);
				logger.info("appid is: %s",appid);
				
			}
		} catch (Exception e) {
			logger.info("Exception Occured for query fetch details icqa:->");
			logger.error("Exception is :->", e);
			return appid;
		}
		return appid;
	}
	
	public List<String> runSearchQuery(String inputQuery,String userid) { 

		String query=inputQuery.replace("\\", "");
		query = query.trim();
		logger.info("Executing %s", query);
		List<String> results = new ArrayList<>();
		List<String> resultsi2p = new ArrayList<>();
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(query);
		if (query.equalsIgnoreCase("applicationrest")) {
			logger.info("Fetching Applications");
			try (Connection restconnection = idpPostGreSqlDbContext.getConnection();
					PreparedStatement restStatement = restconnection.prepareStatement("select "
							+ "distinct application_name from  " + "tapplication_info order by application_name");
					ResultSet rs1 = restStatement.executeQuery();) {
				
				while (rs1.next()) {
					results.add(rs1.getString(1));
				}
				
				//query for i2p by me new
				try (Connection restconnection1 = postGreSqlDbContext.getConnection();
						PreparedStatement restStatement1 = restconnection1.prepareStatement("select application_name from apporg ao, userorg uo where ao.org_name=uo.org_name and uo.user_name='"+userid+"'");
						ResultSet rs2 = restStatement1.executeQuery();) {
					
					while (rs2.next()) {
						resultsi2p.add(rs2.getString(1));
					}
					
				} catch (Exception e) {
					logger.info("Exception Occured for query :->" + query);
					logger.error("Exception is :->", e);
					return results;
				}
				

				logger.info(VALUES);
				results.forEach(logger::info);
				//till above point results will return original data
				return results;
			} catch (Exception e) {
				logger.info("Exception Occured for query :->" + query);
				logger.error("Exception is :->", e);
				return results;
			}

		}
		
		else if (query.contains("ApplicationReleaseRest")) {
			logger.info("Fetching Releases for Selected Application ");
			String application = query.substring(query.indexOf('_') + 1);
			try (Connection restconnection = idpPostGreSqlDbContext.getConnection();
					PreparedStatement restStatement = restconnection
							.prepareStatement("select " + "distinct release_number from  "
									+ "trelease_info where application_id in (select application_id "
									+ "from tapplication_info where application_name=?)order by release_number ");) {

				restStatement.setString(1, application);
				ResultSet rs1 = restStatement.executeQuery();
				while (rs1.next()) {
					results.add(rs1.getString(1));
				}
				logger.info(VALUES);
				results.forEach(logger::info);
				return results;
			} catch (Exception e) {
				logger.info("Exception Occured for query :->" + query);
				logger.error("Exception is :->", e);
				return results;

			}
		}
		
		
		else if (query.contains("ApplicationWITReleaseRestWorkItems_")) {

			logger.info("Fetching work items for Selected Application and Release");
			String application = query.substring(query.indexOf(':') + 1);
			String releasenumber = query.substring(query.indexOf('_') + 1, query.indexOf(':'));
			logger.info("Selected Application is/are " + application);
			logger.info("Selected Release is/are " + releasenumber);

			try (Connection restconnection = idpPostGreSqlDbContext.getConnection();
					PreparedStatement restStatement = restconnection.prepareStatement(
							"select tfs_workitem " + "from ttrigger_history " + "where release_number=? "
									+ "and pipeline_id in (select distinct pipeline_id from tpipeline_info "
									+ "where application_id in ( select distinct application_id from "
									+ "tapplication_info where application_name=?))");) {
				restStatement.setString(1, releasenumber);
				restStatement.setString(2, application);
				ResultSet rs1 = restStatement.executeQuery();
				while (rs1.next()) {
					results.add(rs1.getString(1));
				}
				rs1.close();
				return results;
			} catch (Exception e) {
				logger.info("Exception Occured for query :->" + query);
				logger.error("Exception is :->", e);
				return results;
			}

		} else if (query.contains("PipelineForTemplate")) {
			logger.info("Fetching Pipelines for Selected Application and Release and workitems");
			String release = query.substring(query.indexOf('_') + 1, query.indexOf(':'));
			String workitems = query.substring(query.indexOf(':') + 1);
			logger.info("Selected Release is/are " + release);
			logger.info("Selected workitem is/are " + workitems);
			String newQuery="select pipeline_name "
					+ "from tpipeline_info" + " where pipeline_id in " + "(select pipeline_id "
					+ "from ttrigger_history " + "where release_number='"+release+"' " + "and tfs_workitem='"+workitems+"')";
			logger.info(newQuery);
			try (Connection restconnection = idpPostGreSqlDbContext.getConnection();
					PreparedStatement restStatement = restconnection.prepareStatement(newQuery);
					ResultSet rs1 = restStatement.executeQuery();) {

				
				while (rs1.next()) {

					results.add(rs1.getString(1));
				}
				
				return results;
			} catch (Exception e) {
				logger.info("Exception Occured for query :->" + query);
				logger.error("Exception is :->", e);
				return results;
			}

		}
		else if (query.toLowerCase().contains("sapuserstoryincludingold")) {

			String releaseNo = query.substring(query.indexOf('_') + 1,query.indexOf('+'));

			if ((releaseNo.startsWith("\\(") || releaseNo.startsWith("("))
					&& (releaseNo.endsWith("\\)") || releaseNo.endsWith(")")))
				releaseNo = releaseNo.substring(1, releaseNo.length() - 1);
			
			String projectName = query.substring(query.indexOf('+') + 1);

			if ((projectName.startsWith("\\(") || projectName.startsWith("("))
					&& (projectName.endsWith("\\)") || projectName.endsWith(")")))
				projectName = projectName.substring(1, projectName.length() - 1);
			
			
			String qreleaseNo = "";
			if (releaseNo.contains("$") || "".equals(releaseNo) || "N/A".equals(releaseNo))
				qreleaseNo = " release_number=''";
			else
				qreleaseNo = " release_number='" + releaseNo + "' ";
			Set<String> usList = new HashSet<>();
			String queryus;
			String queryusOld;
			if (releaseNo.equalsIgnoreCase("ALL")) {

				queryus = "Select distinct user_story from tsap_trigger_details where project_name='"+projectName+"'";
				queryusOld = "Select distinct user_story from tsap_deploy_details where trigger_id in (Select trigger_id from ttrigger_history where trigger_id in (Select trigger_id from tsap_deploy_details))";

			} else {
				queryus = "Select distinct user_story from tsap_trigger_details where " + qreleaseNo +"and project_name='"+projectName+"'";
				queryusOld = "Select distinct user_story from tsap_deploy_details where trigger_id in (Select trigger_id from ttrigger_history where "
						+ qreleaseNo + " and trigger_id in (Select trigger_id from tsap_deploy_details))";
			}

			try (Connection connectionNew = idpPostGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connectionNew.prepareStatement(queryus,
							ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					PreparedStatement preparedStatement1 = connectionNew.prepareStatement(queryusOld,
							ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = preparedStatement.executeQuery();
					ResultSet rs1 = preparedStatement1.executeQuery();) {

				ResultSetMetaData metars = rs.getMetaData();
				if (metars.getColumnCount() != 0) {
					while (rs.next()) {
						String temp = rs.getString(1);
						if ("".equals(temp))
							temp = "N/A";
						usList.add(temp);

					}

				}
				ResultSetMetaData metars1 = rs1.getMetaData();
				if (metars1.getColumnCount() != 0) {
					while (rs1.next()) {
						String temp = rs1.getString(1);
						if ("".equals(temp))
							temp = "N/A";
						usList.add(temp);

					}

				}
			} catch (Exception e) {
				logger.error("error in search query");
			}
			Iterator it = usList.iterator();
			while (it.hasNext()) {
				results.add((String) it.next());
			}
			logger.info("values are");
			results.forEach(logger::info);
			return results;

		} else if (query.toLowerCase().contains("sapreleasenoincludingold")) {

			//System.out.println(query);
			
			String projectName = query.substring(query.indexOf('_') + 1);
			
			//System.out.println(projectName+"projectname1111");
			
			if ((projectName.startsWith("\\(") || projectName.startsWith("("))
					&& (projectName.endsWith("\\)") || projectName.endsWith(")")))
				projectName = projectName.substring(1, projectName.length() - 1);
			
			Set<String> rsList = new HashSet<>();
			String querytr = "Select distinct release_number from tsap_trigger_details where project_name='"+projectName+"' order by release_number ASC";
			//String querytrOld = "Select distinct release_number from tsap_deploy_details order by release_number ASC";

			try (Connection connectionNew = idpPostGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connectionNew.prepareStatement(querytr,
							ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
				ResultSet rs = preparedStatement.executeQuery();

				ResultSetMetaData metars = rs.getMetaData();
				if (metars.getColumnCount() != 0) {
					while (rs.next()) {
						String temp = rs.getString(1);
						if (temp == null || temp.equals(""))
							temp = "N/A";

						rsList.add(temp);

					}

				}

			} catch (Exception e) {
				logger.error("error in search query");
			}

			results.add("ALL");
			Iterator it = rsList.iterator();
			while (it.hasNext()) {
				results.add((String) it.next());
			}

			logger.info("values are");
			results.forEach(logger::info);
			return results;

		} else if (query.toLowerCase().contains("gettrwrtreleasenoandmultiuserstoryincludingold")) {
			//System.out.println("**************************************************");
			//System.out.println(query);
			String releaseNo = query.substring(query.indexOf('@') + 1, query.indexOf('#'));
			String userStory = query.substring(query.indexOf('#') + 1,query.indexOf('+'));
			String projectName = query.substring(query.indexOf('+') + 1);
			
			//System.out.println(releaseNo+"11111111111"+userStory+"22222222222"+projectName);
			
			if ((releaseNo.startsWith("\\(") || releaseNo.startsWith("("))
					&& (releaseNo.endsWith("\\)") || releaseNo.endsWith(")")))
				releaseNo = releaseNo.substring(1, releaseNo.length() - 1);

			if ((projectName.startsWith("\\(") || projectName.startsWith("("))
					&& (projectName.endsWith("\\)") || projectName.endsWith(")")))
				projectName = projectName.substring(1, projectName.length() - 1);
			
			if ((userStory.startsWith("\\(") || userStory.startsWith("("))
					&& (userStory.endsWith("\\)") || userStory.endsWith(")")))
				userStory = userStory.substring(1, userStory.length() - 1);
			String[] t = { "" };
			String[] usList = userStory.split("\\|");
			String qreleaseNo;
			String quserStory;
			if (releaseNo.contains("$") || "".equals(releaseNo) || "".equals(releaseNo))
				qreleaseNo = " release_number=''";
			else
				qreleaseNo = " release_number='" + releaseNo + "' ";
			if (usList.length == 0)
				usList = t;
			Set<String> trList = new HashSet<>();
			for (int i = 0; i < usList.length; i++) {
				String us = usList[i];
				if (us.contains("$") || "N/A".equals(us))
					quserStory = " user_story = '' and";
				else if ("".equals(us))
					quserStory = "";
				else
					quserStory = " user_story = '" + us + "' and ";
				String newQuery;
				String newQuerytemp;
				String newQueryOldtemp;

				if (releaseNo.equalsIgnoreCase("ALL")) {

					quserStory = quserStory.substring(0, quserStory.indexOf("and"));
					newQuery = "select distinct transport_request from tsap_trigger_details where " + quserStory+" project_name='"+projectName+"'";
					//newQueryOld = "select distinct transport_request from tsap_deploy_details where trigger_id in (Select trigger_id from ttrigger_history where trigger_id in (Select trigger_id from tsap_deploy_details)) and "
					//		+ quserStory;
					newQuerytemp = "select distinct transport_request from tsap_trigger_details where project_name='"+projectName+"'";
					//newQueryOldtemp = "select distinct transport_request from tsap_deploy_details where trigger_id in (Select trigger_id from ttrigger_history where trigger_id in (Select trigger_id from tsap_deploy_details)) ";

				} else {
					newQuery = "select distinct transport_request from tsap_trigger_details where " + quserStory + " "
							+ qreleaseNo + " and project_name='"+projectName+"'";
					/*newQueryOld = "select distinct transport_request from tsap_deploy_details where " + quserStory
							+ " trigger_id in (Select trigger_id from ttrigger_history where release_number='"
							+ releaseNo + "' and trigger_id in (Select trigger_id from tsap_deploy_details)) "*/
					newQuerytemp = "select distinct transport_request from tsap_trigger_details where " + qreleaseNo
							+ " and project_name='"+projectName+"'";
					/*newQueryOldtemp = "select distinct transport_request from tsap_deploy_details where trigger_id in (Select trigger_id from ttrigger_history where release_number='"
							+ releaseNo + "' and trigger_id in (Select trigger_id from tsap_deploy_details)) ";*/
				}

				try (Connection restconnection = idpPostGreSqlDbContext.getConnection();
						PreparedStatement restStatement = restconnection.prepareStatement(newQuery);
						//PreparedStatement restStatement1 = restconnection.prepareStatement(newQueryOld);
						PreparedStatement restStatement2 = restconnection.prepareStatement(newQuerytemp);)
				

				{
					ResultSet rs = restStatement.executeQuery();
					while (rs.next()) {
						String temp = rs.getString(1);
						trList.add(temp);
					}

					/*ResultSet rs1 = restStatement1.executeQuery();
					while (rs1.next()) {
						String temp = rs1.getString(1);
						trList.add(temp);
					}*/
					if (trList.isEmpty() && "N/A".equals(us)) {
						ResultSet rs2 = restStatement2.executeQuery();
						while (rs2.next()) {
							String temp = rs2.getString(1);
							trList.add(temp);
						}

						/*ResultSet rs3 = restStatement3.executeQuery();
						while (rs3.next()) {
							String temp = rs3.getString(1);
							trList.add(temp);
						}*/
					}

				} catch (Exception e) {
					logger.error("Exception while accessing database");
				}
			}
			Iterator it = trList.iterator();
			while (it.hasNext()) {
				results.add((String) it.next());
			}
			//logger.info("values are");
			results.forEach(logger::info);
			return results;
		} else if (query.equalsIgnoreCase("select application_name from appinfo")) {
			query = "select application_name from apporg ao, userorg uo where ao.org_name=uo.org_name and uo.user_name='"
					+ userid + "'";
		} else if (query.toLowerCase().contains("tsap_tr_details")
				|| query.toLowerCase().contains("tsap_trigger_details")
				|| query.toLowerCase().contains("tapplication_info")
				|| query.toLowerCase().contains("ttrigger_history")) {

			try (Connection connection = idpPostGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(query);) {
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					results.add(rs.getString(1));
				}
				logger.info("values are");
				results.forEach(logger::info);

				return results;
			} catch (Exception e) {

				logger.error("Exception in runSearchQuery ", e);
				return new ArrayList();
			}
		}
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());
				ResultSet rs = preparedStatement.executeQuery();) {
			
			while (rs.next()) {
				results.add(rs.getString(1));
			}
			logger.info(VALUES);
			results.forEach(logger::info);

			return results;
		} catch (Exception e) {

			logger.error("Exception in runSearchQuery ", e);
			return new ArrayList();
		}

	}

}//public.scminfo
