/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.infosys.json.JsonClass;
import com.infosys.json.TestCaseResult;
import com.infosys.utilities.nunit.Failure;
import com.infosys.utilities.nunit.TestCase;
import com.infosys.utilities.nunit.TestRun;
import com.infosys.utilities.nunit.TestSuite;

public class ConvertNUnit {
	private static final Logger logger = Logger.getLogger(ConvertNUnit.class);

	private ConvertNUnit() {
	}

	public static List<TestCaseResult> convert(String inputPath, JsonClass json, String prefixForId) {
		List<TestCaseResult> tr=json.getTestCaseResult();
		if (tr == null)
			tr = new ArrayList<>();
		try {
			EditDocType.edit(inputPath);
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(TestRun.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			TestRun testRun = (TestRun) jaxbUnmarshaller.unmarshal(file);
			if (testRun.getTestSuite() == null) {
				logger.info("Report Converted Successfully..!!");
				return tr;
			}
			TestSuite testSuiteDllLevel = testRun.getTestSuite();
			if ((testSuiteDllLevel).getTestSuite() == null) {
				logger.info("Report Converted Successfully..!!");
				return tr;
			}
			recurseOnTestSuite(testSuiteDllLevel, tr, prefixForId);
			logger.info("Report Converted Successfully..!!");
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
		return tr;
	}

	private static void recurseOnTestSuite(TestSuite testSuite, List<TestCaseResult> tr, String prefixForId) {
		List<TestSuite> testSuiteList = testSuite.getTestSuite();
		if (testSuite.getTestSuite() != null) {
			for (TestSuite testSuiteNew : testSuiteList)
				recurseOnTestSuite(testSuiteNew, tr, prefixForId);
		}
		processTestSuite(testSuite, tr, prefixForId);
	}

	private static void processTestSuite(TestSuite testSuite, List<TestCaseResult> testCaseResultList,
			String prefixForId) {
		List<TestCase> testCaseList = testSuite.getTestCase();
		if (testSuite.getTestCase() == null)
			return;
		for (TestCase testCase : testCaseList) {
			TestCaseResult testCaseResult = getTestCaseResultObject();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(prefixForId);
			stringBuilder.append(testCase.getClassname().replace(".", "_"));
			stringBuilder.append("_");
			stringBuilder.append(testCase.getName().replace(".", "_"));
			testCaseResult.setId(stringBuilder.toString());
			testCaseResult.settestSuiteName(testSuite.getName());
			testCaseResult.setCategory("Unit Test");
			testCaseResult.setDuration(String.format("%.4f", testCase.getDuration()));
			testCaseResult.setTestToolName("NUnit");
			testCaseResult.setStartTime(processDate(testCase.getStartTime()));
			setTCStatus(testCaseResult, testCase);
			setTCMsg(testCaseResult, testCase);
			updateTR(testCaseResult, testCaseResultList);
		}
	}

	private static TestCaseResult getTestCaseResultObject() {
		return new TestCaseResult();
	}

	private static String processDate(String startTime) {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date t = new Date();
		try {
			t = inputDateFormat.parse(startTime);
		} catch (ParseException e) {
			logger.error(e);
		}
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(t);
	}

	private static void setTCMsg(TestCaseResult tcObj, TestCase testCase) {
		if (tcObj.getStatus().equals("failure") || tcObj.getStatus().equals("error"))
			tcObj.setMessage(((Failure) testCase.getContent()).getMessage());
		else if (tcObj.getStatus().equals("skipped"))
			tcObj.setMessage("Test case Skipped");
	}

	private static void setTCStatus(TestCaseResult tcObj, TestCase testCase) {
		if (testCase.getResult().equals("Failed") && testCase.getLabel() != null)
			tcObj.setStatus("error");
		else if (testCase.getResult().equals("Failed"))
			tcObj.setStatus("failure");
		else if (testCase.getResult().equals("Skipped"))
			tcObj.setStatus("skipped");
		else
			tcObj.setStatus("passed");
	}

	private static void updateTR(TestCaseResult tcObj, List<TestCaseResult> testCaseResultList) {
		int flag = 0;
		if (!testCaseResultList.isEmpty()) {
			for (TestCaseResult i : testCaseResultList) {
				if (i.gettestSuiteName().equals(tcObj.gettestSuiteName()) && i.getId().equals(tcObj.getId())
						&& i.getMessage().equals(tcObj.getMessage())) {
					flag = 1;
					break;
				}
			}
		}
		if (flag == 0) {
			testCaseResultList.add(tcObj);
		}
	}
}
