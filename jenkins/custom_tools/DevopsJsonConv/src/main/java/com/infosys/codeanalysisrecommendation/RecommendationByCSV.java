/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.codeanalysisrecommendation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class RecommendationByCSV {
	private static final Logger logger = Logger.getLogger(RecommendationByCSV.class);

	private RecommendationByCSV() {
	}

	public static Map<String, String> createMap(String fileToParse, String postfixStr) {
		Map<String, String> ruleToValue = new HashMap<>();
		// Delimiter used in CSV file
		final String DELIMITER = "\t";
		try (BufferedReader fileReader = new BufferedReader(
				new InputStreamReader(RecommendationByCSV.class.getClassLoader().getResourceAsStream(fileToParse)))) {
			String line = "";
			// Read the file line by line
			while ((line = fileReader.readLine()) != null) {
				// Get all tokens available in line
				String[] tokens = line.split(DELIMITER);
				ruleToValue.put(tokens[0] + postfixStr, tokens[1]);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return ruleToValue;
	}
}
