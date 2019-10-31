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

import com.infosys.json.JsonClass;
import com.infosys.json.PythonUT;
import com.infosys.json.TestCaseResult;

public class ConvertPythonUT {
	private ConvertPythonUT() {
	}

	public static List<TestCaseResult> convert(String inputPath, JsonClass json, String prefixForId) {
		List<TestCaseResult> tr=json.getTestCaseResult();
		
		if (tr == null)
			tr = new ArrayList<>();
		try {
			EditDocType.edit(inputPath);
			ArrayList<String> messages = EditDocType.getMessagesforPunit();
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(com.infosys.utilities.pythonut.Testsuite.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			com.infosys.utilities.pythonut.Testsuite c = (com.infosys.utilities.pythonut.Testsuite) jaxbUnmarshaller
					.unmarshal(file);
			if (c.getTestcase().isEmpty()) {
				System.out.println("Report Converted Successfully..!!");
				return tr;
			}
			List<com.infosys.utilities.pythonut.Testsuite.Testcase> tcList = c.getTestcase();
			int i = 0;
			for (com.infosys.utilities.pythonut.Testsuite.Testcase tc : tcList) {
				TestCaseResult tcObj = getTestCaseResultObject();
				tcObj.setId(prefixForId + (tc.getClassname() + "_" + tc.getName()).replace(".", "_"));
				tcObj.settestSuiteName(c.getName());
				tcObj.setDuration(String.valueOf(tc.getTime()));
				tcObj.setStartTime(getStartTime());
				tcObj.setCategory("Unit Test");
				tcObj.setTestToolName("Python UT");
				if (tc.getFailure() == null && tc.getError() == null) {
					tcObj.setStatus("SUCCESS");
					tcObj.setMessage("Test Case Passed");
				} else if (tc.getFailure() != null) {
					tcObj.setStatus("FAILURE");
					tcObj.setMessage(messages.get(i).replaceAll("\n", " ").replaceAll("\t", " "));
					i++;
				} else if (tc.getError() != null) {
					tcObj.setStatus("ERROR");
					tcObj.setMessage("error occured while executing test case");
					i++;
				}
				tr.add(tcObj);
			}
			System.out.println("Report Converted Successfully..!!");
			return tr;
		} catch (Exception e) {
			System.out.println("Conversion error for " + inputPath + "Error: " + e);
		}
		return tr;
	}

	private static String getStartTime() {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
	}

	private static TestCaseResult getTestCaseResultObject() {
		return new TestCaseResult();
	}

	public static PythonUT getPUnitSummary(JsonClass jsonClass) {
		List<TestCaseResult> tcResultArr = jsonClass.getTestCaseResult();
		PythonUT putObj = null;
		if (tcResultArr == null)
			return null;
		putObj = new PythonUT();
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
		putObj.setPassed(pass);
		putObj.setFailed(fail);
		putObj.setError(error);
		putObj.setSkipped(skip);
		putObj.setTotal(pass + fail + skip + error);
		return putObj;
	}
}
