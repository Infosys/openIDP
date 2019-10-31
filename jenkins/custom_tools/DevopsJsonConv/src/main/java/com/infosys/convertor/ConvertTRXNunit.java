/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.infosys.json.JsonClass;
import com.infosys.json.TestCaseResult;

public class ConvertTRXNunit {
	private static final Logger logger = Logger.getLogger(ConvertTRXNunit.class);

	private ConvertTRXNunit() {
	}

	public static void convert(String inputPath, JsonClass json ) {
		List<TestCaseResult> tr=json.getTestCaseResult();
		if (tr == null)
			tr = new ArrayList<>();
		try {
			EditDocType.edit(inputPath);
			File file = new File(inputPath);
			
			StringBuilder sb = new StringBuilder();
			try(BufferedReader bufReader = new BufferedReader(new FileReader(file))){
				String line = bufReader.readLine();
				while (line != null) {
					sb.append(line).append("\n");
					line = bufReader.readLine();
				}
			}
			
			String xml2String = sb.toString();
			//
			//
			xml2String = xml2String.replaceAll("xmlns", "temp");
			
			byte[] strToBytes = xml2String.getBytes();
			try(FileOutputStream o = new FileOutputStream(file)){
				o.write(strToBytes);
			}

			JAXBContext jaxbContext = JAXBContext.newInstance(com.infosys.utilities.ntrxunit.TestRun.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			com.infosys.utilities.ntrxunit.TestRun testRun = (com.infosys.utilities.ntrxunit.TestRun) jaxbUnmarshaller
					.unmarshal(file);
			List<com.infosys.utilities.ntrxunit.TestRun.Results.UnitTestResult> t1 = testRun.getResults()
					.getUnitTestResult();
			for (com.infosys.utilities.ntrxunit.TestRun.Results.UnitTestResult t2 : t1) {
				TestCaseResult t = new TestCaseResult();
				t.setId(t2.getTestId());
				t.settestSuiteName(t2.getTestName());
				t.setCategory(".Net Junit TestCaseResult");
				t.setStartTime(t2.getStartTime());
				t.setDuration(t2.getDuration());
				t.setTestToolName("TRXNunit");
				if (t2.getOutcome().equalsIgnoreCase("error")) {
					t.setMessage("error");
					t.setMessage("testcase results in error");
				} else if (t2.getOutcome().equalsIgnoreCase("failed")) {
					t.setMessage("failure");
					t.setMessage("testcase results in failure");
				} else if (t2.getOutcome().equalsIgnoreCase("skipped")) {
					t.setMessage("skipped");
					t.setMessage("testcase skipped");
				} else if (t2.getOutcome().equalsIgnoreCase("passed")) {
					t.setMessage("passed");
					t.setMessage("testcase passed");
				} else {
					t.setStatus(t2.getOutcome().toLowerCase());
					t.setMessage("Result of testcase= " + t2.getOutcome().toLowerCase());
				}
				tr.add(t);
			}
			json.setTestCaseResult(tr);
			logger.info("Nunit trx report successfully parsed");
			
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
	}
}
