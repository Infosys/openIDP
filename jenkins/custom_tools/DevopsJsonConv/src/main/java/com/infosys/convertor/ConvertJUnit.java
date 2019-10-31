/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.infosys.json.JUnit;
import com.infosys.json.JsonClass;
import com.infosys.json.TestCaseResult;
import com.infosys.utilities.junit.Testsuite.Testcase;
import com.infosys.utilities.junitgo.Testsuites;
import com.infosys.utilities.junitgo.Testsuites.Testsuite;

public class ConvertJUnit {
	private static final Logger logger = Logger.getLogger(ConvertJUnit.class);

	private ConvertJUnit() {
	}

	public static List<TestCaseResult> convert(String inputPath, JsonClass json, String prefixForId) {
		List<TestCaseResult> tr=json.getTestCaseResult();
		if (tr == null)
			tr = new ArrayList<>();
		try {
			EditDocType.edit(inputPath);
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(com.infosys.utilities.junit.Testsuite.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			com.infosys.utilities.junit.Testsuite c = (com.infosys.utilities.junit.Testsuite) jaxbUnmarshaller
					.unmarshal(file);
			if (c.getTestcase().isEmpty()) {
				logger.info("Report Converted Successfully..!!");
				return tr;
			}
			List<Testcase> tcList = c.getTestcase();
			for (Testcase tc : tcList) {
				TestCaseResult tcObj = getTestCaseResultObject();
				tcObj.setId(prefixForId + (tc.getClassname() + "_" + tc.getName()).replace(".", "_"));
				tcObj.settestSuiteName(c.getName());
				tcObj.setDuration(String.format("%.4f", Float.parseFloat(tc.getTime())));
				tcObj.setStartTime(getStartTime());
				tcObj.setTestToolName("Junit");
				if (inputPath.toLowerCase().contains("lisa"))
					tcObj.setCategory("Functional Test");
				else
					tcObj.setCategory("Unit Test");
				setTCStatus(tcObj, tc);
				setTCMsg(tcObj, tc);
				updateTR(tcObj, tr);
			}
			logger.info("Report Converted Successfully..!!");
			return tr;
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
		return tr;
	}

	private static String getStartTime() {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
	}

	private static TestCaseResult getTestCaseResultObject() {
		return new TestCaseResult();
	}

	private static void setTCMsg(TestCaseResult tcObj, Testcase tc) {
		if (tc.getFailure() != null)
			tcObj.setMessage(tc.getFailure().getValue());
		else if (tc.getError() != null)
			tcObj.setMessage(tc.getError().getValue());
		else if (tc.getSkipped() != null)
			tcObj.setMessage("Test case Skipped");
	}

	private static void setTCStatus(TestCaseResult tcObj, Testcase tc) {
		if (tc.getError() != null)
			tcObj.setStatus("error");
		else if (tc.getFailure() != null)
			tcObj.setStatus("failure");
		else if (tc.getSkipped() != null)
			tcObj.setStatus("skipped");
		else
			tcObj.setStatus("passed");
	}

	private static void updateTR(TestCaseResult tcObj, List<TestCaseResult> tr) {
		int flag = 0;
		if (!tr.isEmpty()) {
			for (TestCaseResult i : tr) {
				if (i.gettestSuiteName().equals(tcObj.gettestSuiteName()) && i.getId().equals(tcObj.getId())
						&& i.getMessage().equals(tcObj.getMessage())) {
					flag = 1;
					break;
				}
			}
		}
		if (flag == 0) {
			tr.add(tcObj);
		}
	}

	public static JUnit getJUnitSummary(JsonClass jsonClass) {
		List<TestCaseResult> tcResultArr = jsonClass.getTestCaseResult();
		JUnit jUnitObj = null;
		if (tcResultArr == null)
			return null;
		jUnitObj = new JUnit();
		int pass = 0;
		int fail = 0;
		int skip = 0;
		int error = 0;
		for (TestCaseResult tcObj : tcResultArr) {
			if (tcObj.getStatus().equals("passed"))
				pass++;
			if (tcObj.getStatus().equals("failure"))
				fail++;
			if (tcObj.getStatus().equals("skipped"))
				skip++;
			if (tcObj.getStatus().equals("error"))
				error++;
		}
		jUnitObj.setPass(pass);
		jUnitObj.setFail(fail);
		jUnitObj.setError(error);
		jUnitObj.setSkip(skip);
		jUnitObj.setTotalTest(pass + fail + error + skip);
		return jUnitObj;
	}

	public static JsonClass convertgo(String path, JsonClass json) {
		try {
			EditDocType.edit(path);
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(Testsuites.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Testsuites root = (Testsuites) jaxbUnmarshaller.unmarshal(file);
			TestCaseResult tr;
			List<TestCaseResult> listresult;
			if (json.getTestCaseResult() == null) {
				listresult = new ArrayList<>();
			} else {
				listresult = json.getTestCaseResult();
			}
			for (Testsuites.Testsuite suit : root.getTestsuite()) {
				for (Testsuite.Testcase testcase : suit.getTestcase()) {
					tr = new TestCaseResult();
					tr.settestSuiteName(suit.getName());
					tr.setId("-");
					if (testcase.getFailure() != null) {
						tr.setStatus("FAILURE");
						tr.setMessage("Test Case Failed");
					} else {
						tr.setStatus("SUCCESS");
						tr.setMessage("Test Case Passed");
					}
					tr.setCategory("UNIT");
					tr.setDuration(testcase.getTime().toString());
					tr.setTestToolName("Junit");
					listresult.add(tr);
				}
			}
			json.setTestCaseResult(listresult);
			System.out.println("Go Lang unit test report parsed");
			return json;
		} catch (Exception e) {
			System.out.println("Exception in go Lang Junit" + e.getMessage());
			return null;
		}
	}
}
