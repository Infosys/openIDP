/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.infosys.json.SoapUIReport;
import com.infosys.json.TestCaseResult;
import com.infosys.utilities.soapuireport.Testsuite;

public class ConvertSoapUIReport {
	private ConvertSoapUIReport() {
		// TODO Auto-generated constructor stub
	}

	public static SoapUIReport convert(String inputPath, List<TestCaseResult> listTR) {
		EditDocType.edit(inputPath);
		File file = new File(inputPath);
		JAXBContext jaxbContext;
		Unmarshaller jaxbUnmarshaller;
		SoapUIReport s = new SoapUIReport();
		// List<TestCaseResult> tr=new ArrayList<TestCaseResult>();
		try {
			jaxbContext = JAXBContext.newInstance(Testsuite.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Testsuite result = (Testsuite) jaxbUnmarshaller.unmarshal(file);
			s.setErrors(result.getErrors());
			s.setFailures(result.getFailures());
			s.setName(result.getName());
			s.setTests(result.getTests());
			s.setTime(result.getTime());
			List<Testsuite.Testcase> testcaseList = result.getTestcase();
			for (Testsuite.Testcase each : testcaseList) {
				TestCaseResult tr = new TestCaseResult();
				tr.settestSuiteName(result.getName());
				tr.setCategory("Functional");
				tr.setDuration(each.getTime());
				tr.setId(each.getName());
				tr.setTestToolName("Soap UI");
				if (!each.getFailure().isEmpty()) {
					tr.setStatus("FAILURE");
					tr.setMessage("Testcase failed");
				} else {
					tr.setMessage("Testcase passed");
					tr.setStatus("SUCCESS");
				}
				listTR.add(tr);
			}
			return s;
			// json.setTestCaseResult(tr);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return s;
	}
}
