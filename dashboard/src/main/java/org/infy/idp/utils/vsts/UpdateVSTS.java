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
	// VSTS API Version
	//String apiVer = args [3];
	
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

	//public static void main(String[] args) {
	@SuppressWarnings("unchecked")
	public static void restServiceUpdate( List<VSTSDataBean> vstsDataList, String workItem) {
		// Setting values
		UpdateVSTS mainObj = new UpdateVSTS();
		
		
		// data from prpoerties file
		
		ReadVSTSProperties readProp = new ReadVSTSProperties();
		readProp.setAllPropertyValues();
				

		// VSTS URL detail with collection name in path
		//mainObj.url = vstsdata.getUrl();
		mainObj.url = readProp.getServerURL();
		// VSTS User name
		//mainObj.userName =  vstsdata.getUserName();
		// VSTS Password
		//mainObj.pass =  vstsdata.getPass();
		// VSTS API Version
		//String apiVer = args [3];
		
		logger.info("VSTS Utility Values updated");
		//String encpass = Encryption_New.encrypt(pass);
		// html for the field

		/*String inputHtmlNew = GetHtmlStrings.getTableHtml();
		String inputHtmlRow = GetHtmlStrings.getRowHtml();*/
		
		String inputHtmlNew = GetHtmlStrings.getTableHtml();
		String inputPipelineHtmlNew = GetHtmlStrings.getPipelineTableHtml();
		String inputHtmlRow = GetHtmlStrings.getRowHtml();
		
		String inputHtmlOld = "";
		boolean checkNewField = true;
		//boolean checkNewRow = true;
		
		//start try ssl certificate accept
		
		/*SSLContext sslContext=null;
		try {
			sslContext= SSLContext.getInstance("SSL");

			// set up a TrustManager that trusts everything
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					//System.out.println("getAcceptedIssuers =============");
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
					System.out.println("checkClientTrusted =============");
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
					//System.out.println("checkServerTrusted =============");
				}
			} }, new SecureRandom());
		} catch (KeyManagementException e1) {
			e1.printStackTrace();
		} catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}

		if(sslContext!=null){
			HttpsURLConnection.setDefaultSSLSocketFactory(
					sslContext.getSocketFactory());

			HttpsURLConnection
			.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					//System.out.println("hostnameVerifier =============");
					return true;
				}
			});

		}*/
		
		//end try ssl certificate accept
		

		HttpClientBuilder builder = HttpClientBuilder.create();

		CloseableHttpClient httpclient = null;
		if("yes".equalsIgnoreCase(readProp.getProxyRequired())){


			// Create credential for authentication
			CredentialsProvider credsProvider = new BasicCredentialsProvider();

			// Create credential for authentication
			credsProvider.setCredentials(
					//AuthScope.ANY,
					//new AuthScope(machine, port),
					new AuthScope(readProp.getProxyip(),Integer.parseInt(readProp.getProxyport())),
					//new UsernamePasswordCredentials(mainObj.userName, Encryption_New.decrypt(mainObj.pass)));
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
		//CloseableHttpClient httpclient = builder.build();
		// preference to use basic Authorization scheme
		RequestConfig config = null;
		//if(mainObj.pass!=null){
			config = RequestConfig.custom()
					.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC, AuthSchemes.NTLM))
					.build();
		//}
		try {
			//call get method
			UpdateVSTS.restGetRequest(httpclient, readProp, workItem);
			//inputHtmlOld = 
			
			if(inputHtmlOld != null && !"".contentEquals(inputHtmlOld)){	
				checkNewField = false;
			}
			
			for(VSTSDataBean vstsdata:vstsDataList){
				
				
				/*// handle new HTML table entry in field
				if(checkNewField || (inputHtmlOld!=null && !inputHtmlOld.contains("testField"))){
					//if(checkNewField || (!inputHtmlOld.contains("IDPInfo"))){
					inputHtmlOld = SetHtmlValues.setNewTableValues(inputHtmlNew, mainObj);
				}
				// handle existing entry or new row in the HTML table
				else{
					inputHtmlOld = SetHtmlValues.addOrUpdateRow(inputHtmlOld, mainObj, inputHtmlRow); 
				}*/
				
				// handle new HTML table entry in VSTS field
				if(checkNewField || (!inputHtmlOld.contains("testField"))){
				//if(checkNewField || (!inputHtmlOld.contains("IDPInfo"))){
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
			
			
			// escape double quotes in html commas
			//inputHtmlOld = inputHtmlOld.replace("\"", "\\\"");

			// HTTP PATCH request
			// common pattern for url rest call
			// https://{instance}[/{collection}[/{team-project}]/_apis[/{area}]/{resource}?api-version={version}
			//HttpPatch httpPatch = new HttpPatch("http://dummyHost129:8080/tfs/QTOOLSTRACKER/_apis/wit/Workitems/3?api-version=1.0");

			HttpPatch httpPatch = new HttpPatch(readProp.getServerURL()+"/_apis/wit/Workitems/"+workItem+"?api-version="+readProp.getApiVersion());
			//https://sparktrial.visualstudio.com/DefaultCollection/_apis/wit/workItems/2362/?api-version=2.2");
			//httpPatch.addHeader("Accept", "application/json");
			httpPatch.addHeader("Content-Type", "application/json-patch+json");
			//httpPatch.addHeader("Authorization", "Basic "+readProp.getAuthorizationToken());

			String userpass = readProp.getVstsUser()+":"+readProp.getVstsPassword();
			logger.info("userpass"+userpass);

			
			//String s = userName+":"+password;			
			String encoding = new String(Base64.encodeBase64(userpass.getBytes()));	
			//String basicAuth = sBasic+ javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
			String basicAuth = "Basic "+encoding;	
			httpPatch.addHeader("Authorization", basicAuth);

			// verb operation add or replace
			String operation = "replace";
			if(checkNewField){
				operation = "add";
			}
			// input to PATCH HTTP verb
			System.out.println(httpPatch.getURI());
			logger.info(inputHtmlOld);
			JSONObject inputJson = new JSONObject();
			Map<String, String> jsonMap = new HashMap<>();
			jsonMap.put("op", operation);
			String customField = "/fields/"+readProp.getCustomFieldName();
			jsonMap.put("path", customField);
			jsonMap.put("value", inputHtmlOld);
			//inputJson.put("input", jsonMap);
			inputJson.putAll(jsonMap);
			JSONArray inputJArray = new JSONArray();
			inputJArray.add(inputJson);
			/*String input = "["+
					"{"+
					" \"op\": \""+operation+"\","+
					//"\"path\": \"/fields/Infosys.domain.TestField\","+
					"\"path\": \"/fields/"+readProp.getCustomFieldName()+"\","+
					"\"value\": \""+inputHtmlOld+"\""+
					"}"+
					"]";*/
			
			logger.info("Input Json Array to VSTS update: "+inputJArray.toString());
			// string to string entity
			StringEntity entity = new StringEntity(inputJArray.toString());
			// set entity
			httpPatch.setEntity(entity);
			//password disabled
			//if(mainObj.pass!=null){
				httpPatch.setConfig(config);
			//}

			// set proxy host to tunnel the connection
			/*HttpHost proxy = new HttpHost("10.68.248.98", 80, "http");
    		// making request configuration from the proxy host
    		RequestConfig config = RequestConfig.custom()
    					.setProxy(proxy).build();
    		//setting the proxy configuration settings to the get request
    		httpGet.setConfig(config);*/
			//httpGet.addHeader("Accept", "application/json");

			//    		CloseableHttpResponse response1 = httpclient.execute(httpGet);
			CloseableHttpResponse response2 = httpclient.execute(httpPatch);


			try {
				//System.out.println(response1.getStatusLine());

				if((response2.getStatusLine().getStatusCode() / 100) == 2){
					logger.info("Field updated successfully: "+response2.getStatusLine());
				}
				else{
					logger.info("Failed - HTTP error code : "+response2.getStatusLine());

				}
				System.out.println(response2);
				/*HttpEntity entity2 = response2.getEntity();
				System.out.println(entity2);
				BufferedReader rd = new BufferedReader(
        		        new InputStreamReader(entity2.getContent()));

        		StringBuffer result = new StringBuffer();
        		String line = "";
        		while ((line = rd.readLine()) != null) {
        			result.append(line);
        		}
        		//JSONObject ob = new JSONObject();

                //System.out.println(ob.to);
        		System.out.println(result.toString());
				// do something useful with the response body
				// and ensure it is fully consumed
				EntityUtils.consume(entity2);*/
			} finally {
				response2.close();
				httpclient.close();
			}

			/*HttpPost httpPost = new HttpPost("http://httpbin.org/post");
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("username", "vip"));
            nvps.add(new BasicNameValuePair("password", "secret"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }*/
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
		//HttpGet httpGet = new HttpGet(mainObj.url+"/_apis/wit/Workitems/"+mainObj.wiNum+"?api-version=1.0");//6861
		//          HttpGet httpGet = new HttpGet("http://10.67.5.121:8080/tfs/QTOOLSTRACKER/_apis/wit/Workitems/3");//6861

		//httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Content-Type", "application/json");
//		httpGet.addHeader("Authorization", "Basic "+readProp.getAuthorizationToken());

		String userpass = readProp.getVstsUser()+":"+readProp.getVstsPassword();
		System.out.println("userpass"+userpass);
		//String s = userName+":"+password;			
		String encoding = new String(Base64.encodeBase64(userpass.getBytes()));	
		//String basicAuth = sBasic+ javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		String basicAuth = "Basic "+encoding;	
		httpGet.addHeader("Authorization", basicAuth);
		
		CloseableHttpResponse response1 = null;
		logger.info("httpGet Request: "+ httpGet.getURI());
		// to get the existing value of  

		try {
			response1 = httpclient.execute(httpGet);
			//System.out.println(response1.getStatusLine());

			if((response1.getStatusLine().getStatusCode() / 100) == 2){
				logger.info("Field values fetched successfully using GET request: "+response1.getStatusLine());
				//System.out.println(response1);
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

			//System.out.println(fieldsJson.toString());
			//inputHtmlOld = (String)fieldsJson.get("Infosys.domain.Testfield");
			inputHtmlOldToReturn = (String)fieldsJson.get(readProp.getCustomFieldName());
			

			//System.out.println(inputHtmlOld);

			//System.out.println(ob.to);
			// do something useful with the response body
			// and ensure it is fully consumed
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
	
	// create new bug work-item for failure of the IDP job
	/*@SuppressWarnings("unchecked")
	public static void restServiceCreateBugWorkItem(String workItem, String assignedTo, String title, String execLink){
		
		
		LOGGER.debug("New bug creation");
		ReadVSTSProperties readProp = new ReadVSTSProperties();
		readProp.setAllPropertyValues();
		
		HttpClientBuilder builder = HttpClientBuilder.create();
		
		// Build closable http client with the provided credentials
		CloseableHttpClient httpclient = null;
		
		// Create credential for authentication
		//CredentialsProvider credsProvider1 = new BasicCredentialsProvider();
		if("yes".equalsIgnoreCase(readProp.getProxyRequired())){


			// Create credential for authentication
			CredentialsProvider credsProvider = new BasicCredentialsProvider();

			// Create credential for authentication
			credsProvider.setCredentials(
					//AuthScope.ANY,
					//new AuthScope(machine, port),
					new AuthScope(readProp.getProxyip(),Integer.parseInt(readProp.getProxyport())),
					//new UsernamePasswordCredentials(mainObj.userName, Encryption_New.decrypt(mainObj.pass)));
					new UsernamePasswordCredentials(readProp.getProxyuser(), Encryption_New.decrypt(readProp.getProxypass())));
			//http proxy
			//HttpHost proxy = new HttpHost(readProp.getProxyip(),Integer.parseInt(readProp.getProxyport()));
			// Build closable http client with the provided credentials
			httpclient = builder
					//.setProxy(proxy)
					//.setProxyAuthenticationStrategy(proxyAuthStrategy)
					.setDefaultCredentialsProvider(credsProvider)
					.build();
		}else{
			httpclient = builder.build();
		}
		
		
		//CloseableHttpClient httpclient = builder.build();
		// preference to use basic Authorization scheme
		RequestConfig config = null;
		//if(mainObj.pass!=null){
			config = RequestConfig.custom()
					.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC, AuthSchemes.NTLM))
					.build();
		//}

		try {

			// HTTP PATCH request
			// common pattern for url rest call
			// https://{instance}[/{collection}[/{team-project}]/_apis[/{area}]/{resource}?api-version={version}
			
			HttpPatch httpPatch = new HttpPatch(readProp.getServerURL()+"/"+readProp.getProjectName()+"/_apis/wit/workItems/$Bug?api-version="+readProp.getApiVersion());
			//_apis/wit/workitems/$Bug?api-version=2.2"
			
			//httpPatch.addHeader("Accept", "application/json");
			httpPatch.addHeader("Content-Type", "application/json-patch+json");
			//httpPatch.addHeader("Authorization", "Basic OmFidWJrdXV0c2xweDJ1bHV2ZHl6dHd5aWhrajY1cHVveWh6YWx6YjNjaGc0b2lzNzRlN2E=");
			httpPatch.addHeader("Authorization", "Basic "+readProp.getAuthorizationToken());

			// verb operation add or replace
			String operation = "replace";
			//if(checkNewField){
			operation = "add";
			//}
			
			//String idpInfoStr = "<br>Link to failed job : <br>&nbsp;&nbsp;"+execLink;
			
			String idpInfoStr = "<br>&nbsp;&nbsp;Link to the failed job : "+execLink;
			idpInfoStr +="<a href=\""+execLink+"\">click to view</a>";
			idpInfoStr += "<br>";
			//idpInfoStr = idpInfoStr.replace("\"", "\\\"");
			//assignedTo = assignedTo.replace("\"", "\\\"");
			
			// input to PATCH HTTP verb
			
			// input to PATCH HTTP verb
			JSONArray inputJArray = new JSONArray();
			// 1
			JSONObject inputJson1 = new JSONObject();
			Map<String, String> jsonMap1 = new HashMap<>();
			jsonMap1.put("op", operation);
			String customField = "/fields/System.Title";
			jsonMap1.put("path", customField);
			jsonMap1.put("value", title);
			inputJson1.putAll(jsonMap1);
			inputJArray.add(inputJson1);
			// 2
			JSONObject inputJson2 = new JSONObject();
			Map<String, String> jsonMap2 = new HashMap<>();
			jsonMap2.put("op", operation);
			String customField2 = "/fields/System.AssignedTo";
			jsonMap2.put("path", customField2);
			jsonMap2.put("value", assignedTo);
			inputJson2.putAll(jsonMap2);
			inputJArray.add(inputJson2);
			// 3
			JSONObject inputJson3 = new JSONObject();
			Map<String, String> jsonMap3 = new HashMap<>();
			jsonMap3.put("op", operation);
			String customField3 = "/relations/-";
			jsonMap3.put("path", customField3);
			// 3.1
			JSONObject inputJsonRel= new JSONObject();
			Map<String, String> jsonMapRel = new HashMap<>();
			String customFieldRel = "System.LinkTypes.Hierarchy-Reverse";
			jsonMapRel.put("rel", customFieldRel);
			String parentWorkitemUrl = readProp.getServerURL()+"/"+readProp.getProjectName()+"/_apis/wit/workItems"+workItem;
			jsonMapRel.put("url", parentWorkitemUrl);
			JSONObject commentJson = new JSONObject();
			commentJson.put("comment", "Link for the new bug");
			jsonMapRel.put("attributes", commentJson.toJSONString());
			
			inputJsonRel.putAll(jsonMapRel);
			jsonMap3.put("value", inputJsonRel.toJSONString());
			
			inputJson3.putAll(jsonMap3);
			inputJArray.add(inputJson3);
			
			// 4
			JSONObject inputJson4 = new JSONObject();
			Map<String, String> jsonMap4 = new HashMap<>();
			jsonMap4.put("op", operation);
			String customField4= "/fields/"+readProp.getCustomFieldName();
			jsonMap4.put("path", customField4);
			jsonMap4.put("value", idpInfoStr);
			inputJson4.putAll(jsonMap4);
			inputJArray.add(inputJson4);
			LOGGER.debug("Body of patch request for creating bug: "+inputJArray.toJSONString());
			
			String input = "["+
					"{"+
					" \"op\": \""+operation+"\","+
					"\"path\": \"/fields/System.Title\","+
					"\"value\": \""+title+"\""+
					"},"+
					"{\"op\": \""+operation+"\", \"path\": \"/fields/System.AssignedTo\", \"value\": \""+assignedTo+"\"},"+
					"{"+
					    "\"op\": \""+operation+"\","+
					    "\"path\": \"/relations/-\","+
					    "\"value\": {"+
					    "\"rel\": \"System.LinkTypes.Hierarchy-Reverse\","+
					    "\"url\": \""+readProp.getServerURL()+"/"+readProp.getProjectName()+"/_apis/wit/workItems"+workItem+"\","+
					    "\"attributes\": {"+
					        "\"comment\": \"Link for the new bug\""+
					      "}"+
					    "}"+
					  "},"+
					  "{"+
						" \"op\": \""+operation+"\","+
						//"\"path\": \"/fields/Infosys.domain.TestField\","+
						"\"path\": \"/fields/System.Description\","+
						"\"value\": \""+idpInfoStr+"\""+
						"}"+
					"]";
			
			{"op": "add", "path": "/fields/System.AssignedTo", "value": "domain.co.in"}
			{
			    "op": "add",
			    "path": "/relations/-",
			    "value": {
			      "rel": "System.LinkTypes.Hierarchy-Reverse",
			      "url": "https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection/_apis/wit/workItems/2362",
			      "attributes": {
			        "comment": "Link for the new bug"
			      }
			    }
			  }
			
			
			 * patchDocument[0] = new { op = "add", path = "/fields/System.Title", value = "Authorization Errors" };
			   patchDocument[1] = new { op = "add", path = "/fields/Microsoft.VSTS.TCM.ReproSteps", value = "Our authorization logic needs to allow for users with Microsoft accounts (formerly Live Ids) - http://msdn.microsoft.com/en-us/library/live/hh826547.aspx" };
			 * patchDocument[2] = new { op = "add", path = "/fields/Microsoft.VSTS.Common.Priority", value = "1" };
			   patchDocument[3] = new { op = "add", path = "/fields/Microsoft.VSTS.Common.Severity", value = "2 - High" };
			//System.CreatedBy
			//System.AssignedTo
			
			// string to string entity
			StringEntity entity = new StringEntity(inputJArray.toJSONString());
			// set entity
			httpPatch.setEntity(entity);
			//password disabled
			//if(mainObj.pass!=null){
			httpPatch.setConfig(config);
			//}

			// set proxy host to tunnel the connection
			HttpHost proxy = new HttpHost("10.68.248.98", 80, "http");
			    		// making request configuration from the proxy host
			    		RequestConfig config = RequestConfig.custom()
			    					.setProxy(proxy).build();
			    		//setting the proxy configuration settings to the get request
			    		httpGet.setConfig(config);
			//httpGet.addHeader("Accept", "application/json");

			//    		CloseableHttpResponse response1 = httpclient.execute(httpGet);
			CloseableHttpResponse response2 = httpclient.execute(httpPatch);



			// to get the existing value of  

			try {
				//System.out.println(response1.getStatusLine());

				if((response2.getStatusLine().getStatusCode())/100 == 2){
					System.out.println("Field values updated successfully for Bug creation using PATCH request " +response2.getStatusLine());
				}
				else{
					System.out.println("Failed PATCH request for Bug creation - HTTP error code : "+response2.getStatusLine());
					return ;
				}
				HttpEntity entity1 = response2.getEntity();
				BufferedReader rd = new BufferedReader(
						new InputStreamReader(entity1.getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				rd.close();
				System.out.println(result);
				JSONParser pars = new JSONParser();
				JSONObject jObj;
				jObj = (JSONObject) pars.parse(result.toString());
				
				JSONObject fieldsJson = (JSONObject) jObj.get("fields");
				if(fieldsJson == null){
					System.out.println("Fields not present in GET request");
					return ;
				}

				//System.out.println(fieldsJson.toString());
				//inputHtmlOld = (String)fieldsJson.get("Infosys.domain.Testfield");
				

				//System.out.println(inputHtmlOld);

				//System.out.println(ob.to);
				// do something useful with the response body
				// and ensure it is fully consumed
				EntityUtils.consume(entity1);
			} finally {
				response2.close();

			}

			
		}
		catch (ParseException e) {
			LOGGER.error(ioExceptionSt, e);
		}
		
		catch(IOException e){
			LOGGER.error(ioExceptionSt, e);
		}
		
	}*/
			

	
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
