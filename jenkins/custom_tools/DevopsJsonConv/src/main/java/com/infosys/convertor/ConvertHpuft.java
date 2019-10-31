package com.infosys.convertor;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.infosys.json.HpUft;
import com.infosys.json.JsonClass;
import com.infosys.json.TestCaseResult;
import com.infosys.utilities.hpuft.Testsuites;
import com.infosys.utilities.hpuft.Testsuites.Testsuite.Testcase;

public class ConvertHpuft {
	
	private static final Logger logger = Logger.getLogger(ConvertHpuft.class);
	
	public static HpUft convert(String inputPath, JsonClass json, List<TestCaseResult> listTR,String jobName) {
		
		HpUft hp = new HpUft();
		Integer total=0;
		Integer success=0;
		Integer fail=0;
		
		EditDocType.edit(inputPath);
		File file = new File(inputPath);
		JAXBContext jaxbContext;
		Unmarshaller jaxbUnmarshaller;

		try {
			jaxbContext = JAXBContext.newInstance(Testsuites.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Testsuites test = (Testsuites) jaxbUnmarshaller.unmarshal(file);
			List<Testcase> tc = test.getTestsuite().getTestcase();
			for (Testcase testCase : tc ){
				TestCaseResult testcaseresult =new TestCaseResult();
				String tempTestcaseName=testCase.getName();
				tempTestcaseName=tempTestcaseName.substring(tempTestcaseName.indexOf(jobName)+jobName.length());
				testcaseresult.setTestSuiteName(tempTestcaseName);
				testcaseresult.setDuration(testCase.getTime()+"");
				testcaseresult.setCategory("FUNCTIONAL");
				testcaseresult.setTestToolName("HP UFT");
		
				if(testCase.getStatus().equalsIgnoreCase("pass")) {
					success++;
					testcaseresult.setMessage("Test Passed");
					testcaseresult.setStatus("SUCCESS");
				}
				else if (testCase.getStatus().equalsIgnoreCase("fail")) {
					testcaseresult.setMessage(testCase.getFailure().getMessage());
					testcaseresult.setStatus("FAILURE");
				}
				else if (testCase.getStatus().equalsIgnoreCase("error")) {
					testcaseresult.setMessage(testCase.getError().getMessage());
					testcaseresult.setStatus("FAILURE");
				}
				else {
					testcaseresult.setMessage("Timeout has expired");
					testcaseresult.setStatus("FAILURE");
				}
				
				listTR.add(testcaseresult);
			}
			total=test.getTestsuite().getTests().intValue();
			fail=total-success;
			hp.setTotal(total);
			hp.setFail(fail);
			hp.setPass(success);	
		}
		
		catch(Exception e) {
			logger.error(e.getMessage(), e);
		
		}
	 return hp;
		
	}


}
