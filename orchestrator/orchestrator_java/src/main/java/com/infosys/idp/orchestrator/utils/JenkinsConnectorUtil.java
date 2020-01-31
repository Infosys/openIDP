/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.idp.orchestrator.utils;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * JenkinsConnectorUtil
 */
@Component
public class JenkinsConnectorUtil {

    protected Logger logger = LoggerFactory.getLogger(JenkinsConnectorUtil.class);

    @Value("${jenkinsConnectorUrl}")
    private String jenkinsConnectorUrl;
    
    private static final String ACTION ="action";
    private static final String TARGET_PLATFORM ="targetplatform";
    private static final String IDPJSON ="idpjson";
    private static final String JENKINSURL ="jenkinsURL";
    private static final String BASICINFO ="basicInfo";
    private static final String PIPELINENAME ="pipelineName";
    String jsonInput="";
    String pipelineName="";
   
   
    @Autowired
	public RestTemplate restTemplate;
	
    @Bean
    public RestTemplate getRestTemplate()
    {
    	return new RestTemplate();
    	
    }
    
    
    @SuppressWarnings("deprecation")
	@Bean
    SpanAdjuster adjusterOne() {
    	 return span -> span.toBuilder().putTag("jsonInput",jsonInput).build();
    }
   
    @SuppressWarnings("deprecation")
	@Bean
    SpanAdjuster adjusterTwo() { 
    	return span -> span.toBuilder().putTag(PIPELINENAME,pipelineName).build();
    }
    
    private String restCallToJenkinsConnector(String path, String jsonData) {
    	try {
	    	jsonInput=jsonData;
	    	String url = jenkinsConnectorUrl + path;
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<String>(jsonData,headers);
	        String response = restTemplate.postForObject(url, entity, String.class);
	        logger.info("Response From Jenkins Connector",response);
	        return response;
	    }
	    catch(Exception e)
	    {
	    	logger.error("Error occurred while communication with JenkinsConnector",e);
	    	return "-1";
	    }
    }

    public ResponseEntity<String> routeconnection(String path, String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.has(TARGET_PLATFORM)
                    && jsonObject.getString(TARGET_PLATFORM).equalsIgnoreCase("jenkins")) {

                String response = restCallToJenkinsConnector(path, jsonData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("-1", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            logger.error("Exception in connecting Jenkins : {}" , ex.getMessage());
        }

        return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    public void jenkinsConnectionForKafakMessage(String message) {
    	logger.info("Retrived kafka message");
    	
    	try {
             JSONObject jsonObject = new JSONObject(message);
             if (jsonObject.has(TARGET_PLATFORM)
                     && jsonObject.getString(TARGET_PLATFORM).equalsIgnoreCase("jenkins")) {
            	 
            	 if(jsonObject.has(ACTION) && jsonObject.has(IDPJSON)){
            		 if((jsonObject.getString(ACTION).equalsIgnoreCase("create") || jsonObject.getString(ACTION).equalsIgnoreCase("edit"))) {
            			 
            			if (jsonObject.has(IDPJSON) && jsonObject.getJSONObject(IDPJSON).has(BASICINFO)
 								&& jsonObject.getJSONObject(IDPJSON).getJSONObject(BASICINFO).has(PIPELINENAME)) {
 							pipelineName = jsonObject.getJSONObject(IDPJSON).getJSONObject(BASICINFO)
 									.getString(PIPELINENAME);
 						}
            			 String response = restCallToJenkinsConnector("/job/create", jsonObject.getJSONObject(IDPJSON).toString());
            			 logger.info("Create or Edit pipeline successful : {}", response);
            		 }
            		 else if(jsonObject.getString(ACTION).equalsIgnoreCase("trigger")) {
            			 if (jsonObject.has(IDPJSON) && jsonObject.getJSONObject(IDPJSON).has(PIPELINENAME)) {
 							pipelineName = jsonObject.getJSONObject(IDPJSON).getString(PIPELINENAME);
 						} 
            			 String response = restCallToJenkinsConnector("/job/build", jsonObject.getJSONObject(IDPJSON).toString());
            			 logger.info("Trigger pipeline successful : {}", response);
            		 }
            		 else {
            			 logger.error("Kafka Message not related with Jenkins Connector Action");
            		 }
            	 }
            	 else {
            		 logger.error("Kafka Message doesn't have action or idpjson key!!!");
            	 }

                 
                 
             } else {
            	 logger.error("Kafka Message not related with Jenkins Platform");
             }
         } catch (Exception ex) {
             logger.error("Exception in connecting Jenkins : {}" , ex.getMessage());
         }
    	
    }

}