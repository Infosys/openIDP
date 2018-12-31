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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.infy.idp.entities.VSTSDataBean;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class UpdateVSTS {
	
	protected static Logger logger=LoggerFactory.getLogger(UpdateVSTS.class);

	
	
	private static String ioExceptionSt = "IO Exception: "; 
	// private member variable
	// VSTS URL detail with collection name in path
	private String url = null;
	// VSTS User name
	private String userName = null;
	// VSTS Password
	private String pass = null;
	
	// Execution Number
	private String execNo = null;
	// Execution Number Link
	private String execNoLink = null;
	// User Value
	private String user = null;
	// Build Value
	private String build = null;
	// Deploy Value
	private String deploy = null;
	// Test Value
	private String test = null;
	// SCM Branch Value
	private String scmBranch = null;
	// Environment Value
	private String env = null;
	// Build Status Value
	private String bldstatus = null;
	// deploy Status Value
	private String depStatus = null;
	// test Status Value
	private String tstStatus = null;
	// Build/Deploy Artifact Value
	private String artivalue = null;
	// Build/Deploy Artifact link
	private String artilink = null;
	
	/*
	 * Private constructor for class creation restriction
	 */
	private UpdateVSTS(){
		//constructor
		
	}

	
	@SuppressWarnings("unchecked")
	public static void restServiceUpdate( List<VSTSDataBean> vstsDataList, String workItem) {
		// Setting values
		UpdateVSTS mainObj = new UpdateVSTS();
		
		
		// data from prpoerties file
		
		ReadVSTSProperties readProp = new ReadVSTSProperties();
		readProp.setAllPropertyValues();
				

		// VSTS URL detail with collection name in path
		
		mainObj.url = readProp.getServerURL();
		
		
		logger.info("VSTS Utility Values updated");
		
		String inputHtmlNew = GetHtmlStrings.getTableHtml();
		String inputPipelineHtmlNew = GetHtmlStrings.getPipelineTableHtml();
		String inputHtmlRow = GetHtmlStrings.getRowHtml();
		
		String inputHtmlOld = "";
		boolean checkNewField = true;

		

		HttpClientBuilder builder = HttpClientBuilder.create();

		CloseableHttpClient httpclient = null;
		if("yes".equalsIgnoreCase(readProp.getProxyRequired())){


			// Create credential for authentication
			CredentialsProvider credsProvider = new BasicCredentialsProvider();

			// Create credential for authentication
			credsProvider.setCredentials(
					new AuthScope(readProp.getProxyip(),Integer.parseInt(readProp.getProxyport())),

					new UsernamePasswordCredentials(readProp.getProxyuser(), readProp.getProxypass()));
			//http proxy
			HttpHost proxy = new HttpHost(readProp.getProxyip(),Integer.parseInt(readProp.getProxyport()));
			// Build closable http client with the provided credentials
			httpclient = builder
					.setProxy(proxy)
					//.setProxyAuthenticationStrategy(proxyAuthStrategy)
					.setDefaultCredentialsProvider(credsProvider)
					.build();
		}else{
			httpclient = builder.build();
		}

		// preference to use basic Authorization scheme
		RequestConfig config = null;

			config = RequestConfig.custom()
					.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC, AuthSchemes.NTLM))
					.build();
		
		try {
			//call get method
			UpdateVSTS.restGetRequest(httpclient, readProp, workItem);
			//inputHtmlOld = 
			
			if(inputHtmlOld != null && !"".contentEquals(inputHtmlOld)){	
				checkNewField = false;
			}
			
			for(VSTSDataBean vstsdata:vstsDataList){
				
				
				
				if(checkNewField || (!inputHtmlOld.contains("testField"))){

					inputHtmlOld = SetHtmlValues.addEntireHTMLValues(vstsdata, inputHtmlNew);
					checkNewField = false;
				}
				// handle new pipeline table entry in the VSTS field
				else if( inputHtmlOld.contains("testField")  &&
						!inputHtmlOld.contains(vstsdata.getPipelineName())){
					inputHtmlOld = SetHtmlValues.setNewTableValues(inputHtmlOld, vstsdata, inputPipelineHtmlNew);
				}
				// handle existing entry or new row in the HTML table
				else if(inputHtmlOld.contains(vstsdata.getPipelineName())){
					inputHtmlOld = SetHtmlValues.addOrUpdateRow(inputHtmlOld, vstsdata, inputHtmlRow); 
				}
				
			}
			
			
			
			HttpPatch httpPatch = new HttpPatch(readProp.getServerURL()+"/_apis/wit/Workitems/"+workItem+"?api-version="+readProp.getApiVersion());

			httpPatch.addHeader("Content-Type", "application/json-patch+json");
			String userpass = readProp.getVstsUser()+":"+readProp.getVstsPassword();
			logger.info("userpass"+userpass);

			

			String encoding = new String(Base64.encodeBase64(userpass.getBytes()));	

			String basicAuth = "Basic "+encoding;	
			httpPatch.addHeader("Authorization", basicAuth);

			// verb operation add or replace
			String operation = "replace";
			if(checkNewField){
				operation = "add";
			}
			// input to PATCH HTTP verb
			logger.info(httpPatch.getURI().toString());
			logger.info(inputHtmlOld);
			JSONObject inputJson = new JSONObject();
			Map<String, String> jsonMap = new HashMap<>();
			jsonMap.put("op", operation);
			String customField = "/fields/"+readProp.getCustomFieldName();
			jsonMap.put("path", customField);
			jsonMap.put("value", inputHtmlOld);

			inputJson.putAll(jsonMap);
			JSONArray inputJArray = new JSONArray();
			inputJArray.add(inputJson);
			
			
			logger.info("Input Json Array to VSTS update: "+inputJArray.toString());
			// string to string entity
			StringEntity entity = new StringEntity(inputJArray.toString());
			// set entity
			httpPatch.setEntity(entity);
				httpPatch.setConfig(config);
			
			
			CloseableHttpResponse response2 = httpclient.execute(httpPatch);


			try {


				if((response2.getStatusLine().getStatusCode() / 100) == 2){
					logger.info("Field updated successfully: "+response2.getStatusLine());
				}
				else{
					logger.info("Failed - HTTP error code : "+response2.getStatusLine());

				}
				logger.info(response2.toString());
				
			} finally {
				response2.close();
				httpclient.close();
			}

			
		}
		catch(IOException e){
			logger.error(ioExceptionSt,e);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}


	}

	
	// Rest call to perform get request 
	private static String restGetRequest(CloseableHttpClient httpclient, 
											ReadVSTSProperties readProp, String workItem) throws Exception {
		
		String inputHtmlOldToReturn = null;
		// HTTP GET request
		HttpGet httpGet = new HttpGet(readProp.getServerURL()+"/_apis/wit/Workitems/"+workItem+"?api-version="+readProp.getApiVersion());//6861
		

		httpGet.addHeader("Content-Type", "application/json");
		String userpass = readProp.getVstsUser()+":"+readProp.getVstsPassword();
		logger.info("userpass"+userpass);
		String encoding = new String(Base64.encodeBase64(userpass.getBytes()));	

		String basicAuth = "Basic "+encoding;	
		httpGet.addHeader("Authorization", basicAuth);
		
		CloseableHttpResponse response1 = null;
		logger.info("httpGet Request: "+ httpGet.getURI());
		// to get the existing value of  

		try {
			response1 = httpclient.execute(httpGet);


			if((response1.getStatusLine().getStatusCode() / 100) == 2){
				logger.info("Field values fetched successfully using GET request: "+response1.getStatusLine());
				
			}
			else if(response1.getStatusLine().getStatusCode() == 404){
				throw new Exception("Work item '"+workItem+"' doesnot exist");
			}
			else{
				logger.info("Failed GET request - HTTP error code : "+response1.getStatusLine());
				throw new Exception("Failed to perform get request");
			}
			HttpEntity entity1 = response1.getEntity();
			BufferedReader rd = new BufferedReader(
					new InputStreamReader(entity1.getContent()));

			StringBuilder result = new StringBuilder();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			JSONParser pars = new JSONParser();
			JSONObject jObj = (JSONObject) pars.parse(result.toString());
			logger.info("object : "+jObj.toJSONString());
			JSONObject fieldsJson = (JSONObject) jObj.get("fields");
			if(fieldsJson == null){
				logger.info("Fields not present in GET request");
				throw new Exception("Fields not present in GET request");
			}

			inputHtmlOldToReturn = (String)fieldsJson.get(readProp.getCustomFieldName());
			

			
			EntityUtils.consume(entity1);
		} catch(ParseException e){
			logger.error("ParseException: ",e);
		}catch(IOException e){
			logger.error("IOException: ",e);
		} catch(Exception e){
			logger.error("Custom Exception: ",e);
			throw e;
		}finally {
			try {
				if(response1!=null){response1.close();}
			} catch (IOException e) {
				logger.error(ioExceptionSt,e);
			}

		}
		return inputHtmlOldToReturn;
	}
	
	
			

	
	// getters and Setters

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getExecNo() {
		return execNo;
	}

	public void setExecNo(String execNo) {
		this.execNo = execNo;
	}

	public String getExecNoLink() {
		return execNoLink;
	}

	public void setExecNoLink(String execNoLink) {
		this.execNoLink = execNoLink;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getDeploy() {
		return deploy;
	}

	public void setDeploy(String deploy) {
		this.deploy = deploy;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getScmBranch() {
		return scmBranch;
	}

	public void setScmBranch(String scmBranch) {
		this.scmBranch = scmBranch;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getBldstatus() {
		return bldstatus;
	}

	public void setBldstatus(String bldstatus) {
		this.bldstatus = bldstatus;
	}

	public String getDepStatus() {
		return depStatus;
	}

	public void setDepStatus(String depStatus) {
		this.depStatus = depStatus;
	}

	public String getTstStatus() {
		return tstStatus;
	}

	public void setTstStatus(String tstStatus) {
		this.tstStatus = tstStatus;
	}

	public String getArtivalue() {
		return artivalue;
	}

	public void setArtivalue(String artivalue) {
		this.artivalue = artivalue;
	}

	public String getArtilink() {
		return artilink;
	}

	public void setArtilink(String artilink) {
		this.artilink = artilink;
	}



}
