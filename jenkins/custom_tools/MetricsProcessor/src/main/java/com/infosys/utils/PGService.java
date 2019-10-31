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
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.infosys.json.JsonClass;

public class PGService {
	
	private static final Logger logger = Logger.getLogger(PGService.class);
	
	public boolean postJSON(String jsonFileLocation, String url, String appName, String pipeline, String temp,
			String pgsuname, String pgspwd) {
		try {
			JsonClass json = getGsonObject(jsonFileLocation, appName, pipeline, temp);
			postJsonTOPGeService(json, url, pgsuname, pgspwd, appName, pipeline, jsonFileLocation, temp);
			return true;
		} catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
			return false;
		}
	}

	private void postJsonTOPGeService(JsonClass json, String url, String uname, String pwd, String appName,
			String pipelineName, String jsonfilelocation, String temp) {
		Gson gson = new Gson();
		String input = gson.toJson(json);
		String x = File.separator;
		File f = new File(temp + x + jsonfilelocation);
		try {
			input = FileUtils.readFileToString(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String requestUrl = url;
		String buildID = json.getbuildId();
		sendPostRequest(requestUrl, input, uname, pwd, buildID, appName, pipelineName);
	}

	public static String sendPostRequest(String requestUrl, String payload, String uname, String pwd, String buildID,
			String app, String pipName) {
		StringBuilder jsonString = new StringBuilder();
		try {
			String application = URLEncoder.encode(app, "UTF-8");
			String encodedPipName;
			encodedPipName = URLEncoder.encode(pipName, "UTF-8");
			String modifiedRequestUrl;
			modifiedRequestUrl=requestUrl + application + "/" + encodedPipName + "/" + buildID + "/update/";
			URL url = new URL(modifiedRequestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			String userCredentials = uname + ":" + pwd;
			String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
			basicAuth = basicAuth.replaceAll("\n", "");
			basicAuth = basicAuth.replaceAll("\r", "");
			connection.setRequestProperty("Authorization", basicAuth);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			logger.info("Calling dashboard service at '/{appname}/{pipname}/{pipbuildno}/update'");
			logger.info("Json to be posted : ");
			JsonClass tempJsonForDebug = new Gson().fromJson(payload, JsonClass.class);
			logger.info(tempJsonForDebug.toString());
			try(OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())){
				writer.write(payload);
			}
			try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
				String line;
				while ((line = br.readLine()) != null) {
					jsonString.append(line);
				}
			}
			connection.disconnect();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return jsonString.toString();
	}

	private JsonClass getGsonObject(String jsonFile, String appName, String pipeline, String temp) {
		Gson gson = new Gson();
		JsonClass result = null;
		JsonReader jsonReader = null;
		if (jsonFile.contains(appName + "_" + pipeline)) {
			try {
				String x = File.separator;
				logger.info("Reading JSON data from file : ");
				logger.info(jsonFile);
				jsonReader = new JsonReader(new FileReader(temp + x + jsonFile));
				result = gson.fromJson(jsonReader, JsonClass.class);
				logger.info("Data read successfully");
				logger.info("Read Json is : ");
				logger.info(new Gson().toJson(result, JsonClass.class));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				if (jsonReader != null) {
					try {
						jsonReader.close();
					} catch (IOException e) {
						logger.error(e);
					}
				}
			}
		}
		return result;
	}
}
