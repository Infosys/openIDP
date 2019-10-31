/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.infosys.json.IFastReport;
import com.infosys.json.TestCaseResult;

public class ConvertIFast {
	private static final Logger logger = Logger.getLogger(ConvertIFast.class);

	private ConvertIFast() {
	}

	public static IFastReport convert(String inputPath, List<TestCaseResult> listTR) {
		IFastReport ifastReport = new IFastReport();
		
		JsonParser parser = new JsonParser();
		Path pathToIfastReport = Paths.get(inputPath);
		if(Files.exists(pathToIfastReport)) {
			try {
				Files.lines(pathToIfastReport).forEach(line -> {
					JsonObject ifastReportJson = parser.parse(line).getAsJsonObject();
					//check ExecutionResult availability
					JsonObject executionResultJson = null;
					JsonArray executionReportArray = null;
					JsonObject executionStatsJson = null;
					if(ifastReportJson.get("ExecutionResult")!=null) {
						executionResultJson = parser.parse(ifastReportJson.get("ExecutionResult").getAsString()).getAsJsonObject();
						executionReportArray = parser.parse(executionResultJson.get("ExecutionReport").getAsString()).getAsJsonArray();
					    executionStatsJson = parser.parse(executionResultJson.get("ExecutionStats").getAsString()).getAsJsonObject();
					}
					else {
						return;
					}
					//check execution report array availability
					if(executionReportArray!=null) {
						executionReportArray.forEach(tempExecutionReport -> { 
							  TestCaseResult tempTR = new TestCaseResult(); 
							  JsonObject tempExecutionReportJSON = parser.parse(tempExecutionReport.getAsString()).getAsJsonObject(); 
							  tempTR.setCategory("Service");
							  //adding response time
							  if(tempExecutionReportJSON.get("Response_Time(ms)") != null) {
								  tempTR.setDuration(tempExecutionReportJSON.get("Response_Time(ms)").getAsString());
							  }else {
								  tempTR.setDuration("NA");
								  logger.info("Response time not available for this iFast test report");
							  }
							  //adding test id & testCaseName
							  if(tempExecutionReportJSON.get("TestCaseName")!=null) {
								  tempTR.setId(tempExecutionReportJSON.get("TestCaseName").getAsString());
								  tempTR.setTestCaseName(tempExecutionReportJSON.get("TestCaseName").getAsString());
							  }else {
								  tempTR.setId("NA");
								  tempTR.setTestCaseName("NA");
								  logger.info("TestCaseName not available for this iFast test report");
							  }
							  //adding request_trigger time
							  if(tempExecutionReportJSON.get("Request_Trigger_Time")!=null) {
								  tempTR.setStartTime(tempExecutionReportJSON.get("Request_Trigger_Time").getAsString());
							  }else {
								  tempTR.setStartTime("NA");
								  logger.info("Request_Trigger_Time not available for this iFast test report");
							  }
							  //adding testcase execution result
							  String executionStatus = "";
							  if(tempExecutionReportJSON.get("TestCase_ExecutionResult")!=null) {
								  executionStatus = tempExecutionReportJSON.get("TestCase_ExecutionResult").getAsString();
							  }
							  if(executionStatus.equalsIgnoreCase("fail") && tempExecutionReportJSON.get("TestCase_FailureReason")!=null) {
								  tempTR.setMessage(tempExecutionReportJSON.get("TestCase_FailureReason").getAsString());
							  }else {
								  tempTR.setMessage("Passed");
								  logger.info("TestCase_FailureReason not available for this iFast test report");
							  }
							  tempTR.setStatus(executionStatus);
							  tempTR.settestSuiteName("NA"); 
							  tempTR.setTestToolName("iFast");
							  listTR.add(tempTR);
						   });
						}//no need of else
					if(executionStatsJson!=null) {
						ifastReport.setTotalTest(executionStatsJson.get("TotalCount").getAsInt());
						ifastReport.setFail(executionStatsJson.get("FailCount").getAsInt());
						ifastReport.setPass(executionStatsJson.get("PassCount").getAsInt());
					}
				});
			} catch (IOException e) {
				logger.error("Error while reading iFastReport.txt",e);
			}
		}
		return ifastReport;
	}
}
