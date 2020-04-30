package org.infy.idp.dataapi;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.infy.idp.entities.QueryResponse;
import org.infy.idp.utils.ConfigurationManager;
import org.infy.idp.utils.SSLUtilities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobDuration {

	@Autowired
	private ConfigurationManager configurationManager;

	protected Logger logger = LoggerFactory.getLogger(FetchDetailsBL.class);

	public static final String DURATION_MS = "durationMillis";
	public static final String NAME_TAG = "name";
	public static final String STAGES_TAG = "stages";

	public int getJobsDuration(String appName, String pipeName, String buildId, String job) {

		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();

		int totalDuration = 0;
		try {
			String jobName = appName + "_" + pipeName;
			String webPage = configurationManager.getJenkinsURL() + "/job/" + jobName + "/job/" + jobName + "_Pipeline/"
					+ buildId + "/wfapi/describe";
			String authString = configurationManager.getJenkinsID() + ":" + configurationManager.getJenkinsPassword();
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			logger.info(webPage);
			logger.info("CONNECTION SUCCESSFUL");

			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

			StringBuilder stringBuilder = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
				String line1;
				while ((line1 = br.readLine()) != null) {
					stringBuilder.append(line1 + "\n");
				}
			} catch (Exception e) {
				logger.error("Error in BufferedReader: " + e);
				
			}

			JSONObject resultApiJson = new JSONObject(stringBuilder.toString());

			if (job.equalsIgnoreCase("totalduration")) {

				JSONArray stages = resultApiJson.getJSONArray(STAGES_TAG);
				logger.info("stages array :" + stages);

				for (int i = 0; i < stages.length(); i++) {

					JSONObject iterationObject = stages.getJSONObject(i);
					String name = "";
					if (iterationObject.has(NAME_TAG)) {
						// trim needed because > name contains spaces
						name = iterationObject.getString(NAME_TAG).trim();

						// for each job duration in millisecond is captured
						String time = iterationObject.getString(DURATION_MS);
						int duration = Integer.parseInt(time);

						if (name.toLowerCase().contains("scm")) {
							totalDuration += duration;
						} else if (name.toLowerCase().contains("building")) {
							totalDuration += duration;
						} else if (name.toLowerCase().contains("deployment")) {
							totalDuration += duration;
						} else if (name.toLowerCase().contains("testing")) {
							totalDuration += duration;
						}
					}
					logger.info("Total Duration : " + totalDuration);
				}

			} else {

				JSONArray stages = resultApiJson.getJSONArray(STAGES_TAG);
				for (int i = 0; i < stages.length(); i++) {
					JSONObject iterationObject = stages.getJSONObject(i);
					String name = "";
					if (iterationObject.has(NAME_TAG)) {
						// trim needed because > name contains spaces
						name = iterationObject.getString(NAME_TAG).trim();

						// job-->(scm ,build,deploy,test) ,for each job duration in millisecond is
						// captured
						if (name.toLowerCase().contains(job)) {
							String time = iterationObject.getString(DURATION_MS);
							totalDuration = Integer.parseInt(time);
							logger.info("Duration " + job + " : " + totalDuration);
						}

					}

				}

			}

		} catch (Exception e) {
			logger.error("Error in connecting to jenkins API: " + e);

		}

		return totalDuration;

	}

}

