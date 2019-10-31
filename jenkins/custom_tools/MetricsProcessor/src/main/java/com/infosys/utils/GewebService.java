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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.infosys.json.JsonClass;

public class GewebService {
	private String urlProperties = null;
	private static final Logger logger = Logger.getLogger(GewebService.class);

	public void jsonPost(String appName, String pipeline, String jsonFileLocation) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = Application.class.getResourceAsStream("/dashboard.properties");
			prop.load(input);
			urlProperties = prop.getProperty("url");
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		JsonClass json = getGsonObject(jsonFileLocation, appName, pipeline);
		postJsonTOGeService(json);
	}

	private void postJsonTOGeService(JsonClass json) {
		Gson gson = new Gson();
		String input = gson.toJson(json);
		String requestUrl = urlProperties;
		logger.info(sendPostRequest(requestUrl, input));
	}

	public static String sendPostRequest(String requestUrl, String payload) {
		StringBuilder jsonString = new StringBuilder();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/octet-stream");
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
			logger.error(e.getMessage());
		}
		return jsonString.toString();
	}

	private JsonClass getGsonObject(String jsonFileLocation, String appName, String pipeline) {
		Gson gson = new Gson();
		JsonClass result = null;
		JsonReader jsonReader = null;
		for (File jsonFile : new File(jsonFileLocation).listFiles()) {
			if (jsonFile.toString().contains(appName + "_" + pipeline)) {
				try {
					jsonReader = createJsonReader(jsonFile);
					result = gson.fromJson(jsonReader, JsonClass.class);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				} finally {
					if (jsonReader != null) {
						try {
							jsonReader.close();
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
						}
					}
				}
			}
		}
		return result;
	}

	private static JsonReader createJsonReader(File jsonFile) throws IOException {
		// JsonReader j= new JsonReader(new FileReader(jsonFile));
		try (FileReader fr = new FileReader(jsonFile);) {
			return new JsonReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
