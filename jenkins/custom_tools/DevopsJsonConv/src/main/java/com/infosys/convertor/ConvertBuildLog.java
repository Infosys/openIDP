/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import com.infosys.json.JsonClass;

public class ConvertBuildLog {
	private static final Logger logger = Logger.getLogger(ConvertBuildLog.class);
	public static final String APIJSONPRETTYTRUE = "/api/json?pretty=true";
	public static final String JOB = "/job/";
	public static final String AUTHORIZATION = "Authorization";
	public static final String BASIC = "Basic ";
	public static final String PIPELINE = "_Pipeline/";
	public static final String ACTIONS = "actions";
	public static final String BUILD = "build";
	public static final String HTTPS = "https://";
	public static final String JSONINPUT = "json_input";
	public static final String MODULE = "module";
	public static final String PARAMETERS = "parameters";
	public static final String VALUE = "value";

	private ConvertBuildLog() {
	}

	public static void convert(String inputPath, JsonClass jsonClass) {
		try {
			jsonClass.setLog(new String(Files.readAllBytes(Paths.get(inputPath)), Charset.defaultCharset()));
			logger.info("Report Converted Successfully..!!");
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
	}
}
