/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.util.Base64;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.infosys.json.JsonClass;



/**
 * the class pgservice has method to handle rest connections
 * @author shivam.bhagat
 *
 */
public class PGService {
	public boolean postJSON(String jsonFileLocation,String url,String appName,String pipeline,String temp,String pgsuname,String pgspwd)
		{
			try
			{
			JsonClass json = getGsonObject(jsonFileLocation,appName,pipeline,temp);
			postJsonTOPGeService(json,url,pgsuname,pgspwd,appName,pipeline,jsonFileLocation,temp);
			return true;
			}
			catch(Exception e )
			{
				System.out.println("Exception is "+e.getMessage());
				
				return false;
			}
		}


	/**
	 * 
	 * @param json
	 * @param url
	 * @param uname
	 * @param pwd
	 * @param appName
	 * @param pipelineName
	 * @param jsonfilelocation
	 * @param temp
	 */
	private void postJsonTOPGeService(JsonClass json,String url,String uname,String pwd,String appName,String pipelineName,String jsonfilelocation,String temp)
	{
		Gson gson = new Gson();
		String input = gson.toJson(json);
		String x=File.separator;
		File f=new File(temp+x+jsonfilelocation);
		try {
			input=FileUtils.readFileToString(f);
		} catch (IOException e) {

			e.printStackTrace();
		}
		String requestUrl=url;
		String buildID= json.getbuildId();
		sendPostRequest(requestUrl, input, uname, pwd,buildID,appName,pipelineName);
}
	
	/**
	 * method to connect to dashboard rest api to send data in postgres
	 * @param requestUrl
	 * @param payload
	 * @param uname
	 * @param pwd
	 * @param buildID
	 * @param app
	 * @param pipName
	 * @return
	 */
	public static String sendPostRequest(String requestUrl, String payload,String uname,String pwd,String buildID,String app,String pipName) {
	 StringBuilder jsonString = new StringBuilder();
	 	try {
	 		
        
	 		String application=URLEncoder.encode(app, "UTF-8");
        pipName=URLEncoder.encode(pipName, "UTF-8");
        requestUrl+=application+"/"+pipName+"/"+buildID+"/update/";
        
        URL url = new URL(requestUrl);
       
        HttpURLConnection connection =(HttpURLConnection) url.openConnection();
        String userCredentials = uname+":"+pwd;
        String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
        basicAuth= basicAuth.replaceAll("\n","");
        basicAuth=basicAuth.replaceAll("\r","");
        
        connection.setRequestProperty ("Authorization", basicAuth);
        
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
         
       OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
       
       
       writer.write(payload);
        writer.close();
        connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       
        String line;
        while ((line = br.readLine()) != null) {
                jsonString.append(line);
        }
        br.close();
        connection.disconnect();
    } catch (Exception e) {
            System.out.println(e.getMessage());
    }
    return jsonString.toString();
}
private JsonClass getGsonObject(String jsonFile, String appName, String pipeline,String temp) {
	Gson gson = new Gson();
	JsonClass result = null;
	JsonReader jsonReader = null;
		if(jsonFile.contains(appName+"_"+pipeline)){
			try{
				String x=File.separator;
				
				jsonReader = new JsonReader(new FileReader(temp+x+jsonFile));
				result=gson.fromJson(jsonReader, JsonClass.class);
				System.out.println("Json is "+jsonReader.toString());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				if(jsonReader != null){
					try {
						jsonReader.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}

	
	return result;
}


}


