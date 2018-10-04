/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class SendPostRequest {
	public static String url = null;
	

	public static String sendRequest(String jsonInputString, String scheduleHost, String buildId) {
		String requestUrl = "http://" + scheduleHost + ":8222/idpschedule/schedulerService/updateScheduleDataBase";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		gson.toJson(jsonInputString);
		JsonParser jsonParser = new JsonParser();
		jsonParser.parse(jsonInputString);
		JSONObject jsonObj = new JSONObject();
		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();
		try {
			jsonObj.put("jsonObj", jsonInputString);
			jsonObj.put("buildId", buildId);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuffer jsonString = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(jsonObj.toString());
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
			e.printStackTrace();
		}
		return jsonString.toString();
	}

	public static String getLatestArtifact(String scheduleHost, String applicationName, String pipelineName,
			String releaseNumber, String envName) {
		String requestUrl = "http://" + scheduleHost + ":8222/idpschedule/schedulerService/getArtifactDetails";
		JSONObject jsonObj = new JSONObject();
		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();
		try {
			jsonObj.put("applicationName", applicationName);
			jsonObj.put("pipelineName", pipelineName);
			jsonObj.put("releaseNumber", releaseNumber);
			jsonObj.put("environmentName", envName);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuffer jsonString = new StringBuffer();
		try {
			JSONObject json = new JSONObject();
			json.put("jsonObj", json);
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(jsonObj.toString());
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
			throw new IllegalArgumentException(e.getMessage());
		}
		System.out.println(jsonString.toString());
		return jsonString.toString();
	}

	public static String test() {
		return "test";
	}

	public static void main(String[] a) {
		System.out.println("Starting schedule jar");
	}
}
