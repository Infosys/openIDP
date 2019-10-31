package com.infosys.convertor;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.infosys.json.JsonClass;
import com.infosys.json.SahiReport;
import com.infosys.json.TestCaseResult;

import com.infosys.utilities.sahi.Suites;
import com.infosys.utilities.sahi.Suites.Suite.TestCaseSummaries.TestCaseSummary;

public class ConvertSahiReport {
	private ConvertSahiReport() {
		// TODO Auto-generated constructor stub
	}

	public static SahiReport convert(String inputPath, JsonClass json, List<TestCaseResult> listTR) {
		EditDocType.edit(inputPath);
		File file = new File(inputPath);
		JAXBContext jaxbContext;
		Unmarshaller jaxbUnmarshaller;
		SahiReport s = new SahiReport();
		Integer total=0;
		Integer success=0;
		Integer fail=0;
		// List<TestCaseResult> tr=new ArrayList<TestCaseResult>();
		try {
			jaxbContext = JAXBContext.newInstance(Suites.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Suites suite = (Suites) jaxbUnmarshaller.unmarshal(file);
			List<TestCaseSummary> lt= suite.getSuite().getTestCaseSummaries().getTestCaseSummary();
			for (TestCaseSummary testCaseSummary : lt) {
				TestCaseResult testcaseresult =new TestCaseResult();
				
				testcaseresult.setId(testCaseSummary.getID()+"");
				testcaseresult.setMessage(testCaseSummary.getDESCRIPTION());
				testcaseresult.setDuration(testCaseSummary.getTIMETAKEN()+"");
				testcaseresult.setCategory("FUNCTIONAL");
				testcaseresult.setStatus(testCaseSummary.getTCSTATUS());
				testcaseresult.setTestToolName("SAHI");
				listTR.add(testcaseresult);
				total++;
				if(testCaseSummary.getTCSTATUS().equalsIgnoreCase("SUCCESS")) {
					success++;
				}
				else {
					fail++;
				}
			}
			
			s.setTotal(total);
			s.setFail(fail);
			s.setPass(success);
			
			
		} catch (JAXBException e) {
			e.printStackTrace();

		}
		return s;
	}
}
