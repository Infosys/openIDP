/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.codeanalysisrecommendation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RecommendationByWebAPI {
	private static final Logger logger = Logger.getLogger(RecommendationByWebAPI.class);
	private Map<String, String> ruleRecommendationMap = new HashMap<>();
	private String host;

	public RecommendationByWebAPI() {
		super();
	}

	public RecommendationByWebAPI(String host) {
		super();
		this.host = host;
	}

	public String getRecommendationByRule(String ruleKey) {
		if (ruleRecommendationMap.containsKey(ruleKey))
			return ruleRecommendationMap.get(ruleKey);
		String sonarIssueRecommendationURL = host + "/api/rules/show?key=" + ruleKey;
		String jsonResp = hitSonarWebService(sonarIssueRecommendationURL);
		String recommendation = parseRuleDetailsJson(jsonResp);
		ruleRecommendationMap.put(ruleKey, recommendation);
		return recommendation;
	}

	private String parseRuleDetailsJson(String jsonResp) {
		JSONParser parser = new JSONParser();
		String recommendation = null;
		try {
			JSONObject json = (JSONObject) parser.parse(jsonResp);
			JSONObject ruleStruct = (JSONObject) json.get("rule");
			recommendation = ruleStruct.get("name").toString();
		} catch (org.json.simple.parser.ParseException e) {
			logger.error(e);
		}
		return recommendation;
	}

	private String hitSonarWebService(String sonarPrjctIssuesURL) {
		String jsonResponse = null;
		BufferedReader in = null;
		try {
			URL url = new URL(sonarPrjctIssuesURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);
			InputStream content = connection.getInputStream();
			in = new BufferedReader(new InputStreamReader(content));
			jsonResponse = in.readLine();
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return jsonResponse;
	}
}
