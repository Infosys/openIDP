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

import com.infosys.json.JsonClass;
import com.infosys.json.Protractor;
import com.infosys.json.TestCaseResult;
import com.infosys.utilities.protractor.Testsuites;

public class ConvertProtractor {
	
	private static int fail;
	private static int skipped;
	private static int totalTest;
	private static int errors;
	
	private ConvertProtractor() {
		// TODO Auto-generated constructor stub
	}


	public static Protractor convert(String inputPath, JsonClass json, List<TestCaseResult> tr) {
		EditDocType.edit(inputPath);
		File file = new File(inputPath);
		JAXBContext jaxbContext;
		Unmarshaller jaxbUnmarshaller;
		Protractor p = new Protractor();
		try {
			jaxbContext = JAXBContext.newInstance(Testsuites.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Testsuites result = (Testsuites) jaxbUnmarshaller.unmarshal(file);
			List<Testsuites.Testsuite> testsuiteList = result.getTestsuite();
			for (Testsuites.Testsuite each : testsuiteList) {
				int f = each.getErrors() + each.getFailures() + each.getSkipped();
				int s = each.getTests() - f;
				for (int i = 0; i < f; i++) {
					TestCaseResult t = new TestCaseResult();
					t.setCategory("protractor");
					t.settestSuiteName(each.getName());
					t.setStatus("failed");
					t.setTestToolName("Protractor");
					tr.add(t);
				}
				for (int i = 0; i < s; i++) {
					TestCaseResult t = new TestCaseResult();
					t.setCategory("protractor");
					t.settestSuiteName(each.getName());
					t.setStatus("passed");
					t.setTestToolName("Protractor");
					tr.add(t);
				}
				fail += each.getFailures();
				skipped += each.getSkipped();
				totalTest += each.getTests();
				errors += each.getErrors();
			}
			// p.setErrors(errors);
			// p.setFailures(failures);
			// p.setSkipped(skipped);
			// p.setTests(tests);
			p.setErrors(errors);
			p.setFail(fail);
			p.setPass(totalTest - (fail + errors + skipped));
			p.setSkipped(skipped);
			p.setTotaltest(totalTest);
			json.setTestCaseResult(tr);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return p;
	}
}