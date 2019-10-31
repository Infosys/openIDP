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
import com.infosys.json.ParasoftSOATest;
import com.infosys.json.TestCaseResult;
import com.infosys.utilities.parasoftsoatest.ExecutedTestsDetails;
import com.infosys.utilities.parasoftsoatest.ResultsSession;

public class ConvertParasoftSOATest {
	private ConvertParasoftSOATest() {
		// TODO Auto-generated constructor stub
	}

	public static ParasoftSOATest convert(String inputPath, JsonClass json, List<TestCaseResult> listTR) {
		EditDocType.edit(inputPath);
		File file = new File(inputPath);
		JAXBContext jaxbContext;
		Unmarshaller jaxbUnmarshaller;
		ParasoftSOATest p = new ParasoftSOATest();
		// List<TestCaseResult> tr=new ArrayList<TestCaseResult>();
		try {
			jaxbContext = JAXBContext.newInstance(ResultsSession.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ResultsSession result = (ResultsSession) jaxbUnmarshaller.unmarshal(file);
			ExecutedTestsDetails executedTestDetails = result.getExecutedTestsDetails();
			ExecutedTestsDetails.Total total = executedTestDetails.getTotal();
			p.setFail(total.getFail());
			p.setPass(total.getPass());
			p.setTotal(total.getTotal());
			String totalTime = result.getFunctionalTests().getTime();
			double dTotalTime = convertTime(totalTime);
			double time = dTotalTime / (p.getTotal());
			for (int i = 0; i < p.getFail(); i++) {
				TestCaseResult tr = new TestCaseResult();
				tr.setCategory("Functional");
				tr.setStatus("FAILURE");
				tr.setMessage("Testcase failed");
				tr.setDuration(Double.toString(time));
				tr.setTestToolName("Parasoft");
				listTR.add(tr);
			}
			for (int i = 0; i < p.getPass(); i++) {
				TestCaseResult tr = new TestCaseResult();
				tr.setCategory("Functional");
				tr.setStatus("SUCCESS");
				tr.setMessage("Testcase passed");
				tr.setDuration(Double.toString(time));
				tr.setTestToolName("Parasoft");
				listTR.add(tr);
			}
			return p;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return p;
	}

	static private double convertTime(String time) {
		double result = 0.0;
		String hours;
		String minutes;
		String seconds;
		hours = time.substring(0, time.indexOf(":"));
		minutes = time.substring(time.indexOf(":") + 1, time.lastIndexOf(":"));
		seconds = time.substring(time.lastIndexOf(":") + 1);
		result = 3600 * (Double.parseDouble(hours)) + 60 * (Double.parseDouble(minutes)) + Double.parseDouble(seconds);
		return result;
	}
}
