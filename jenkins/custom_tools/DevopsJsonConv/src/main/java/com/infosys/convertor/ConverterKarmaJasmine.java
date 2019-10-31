package com.infosys.convertor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import com.infosys.json.JsonClass;
import com.infosys.json.TestCaseResult;
import com.infosys.utilities.karmajasmine.Testsuite;


public class ConverterKarmaJasmine {
	
	private static final Logger logger = Logger.getLogger(ConverterKarmaJasmine.class);

	 public static List<TestCaseResult> karmajasmineConverter(String inputPath, JsonClass json, String prefixForId) {
         List<TestCaseResult> tr=json.getTestCaseResult();
         if (tr == null)
                tr = new ArrayList<>();
         try {
        	 	
        	 	
        	 	
                EditDocType.edit(inputPath);
                File file = new File(inputPath);
                JAXBContext jaxbContext = JAXBContext.newInstance(com.infosys.utilities.karmajasmine.Testsuite.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                com.infosys.utilities.karmajasmine.Testsuite testsuite = (com.infosys.utilities.karmajasmine.Testsuite) jaxbUnmarshaller
                             .unmarshal(file);
                if (testsuite.getTestcase().equals(null)) {
                      logger.info("karmaJasmine Unit Report Converted Successfully..!!");
                      return tr;
                }
                List<Testsuite.Testcase> testCaseList = testsuite.getTestcase();
                for(int i=0;i<testCaseList.size();i++)
                {
                	TestCaseResult testCaseResult = new TestCaseResult() ;
                	if(testCaseList.get(i).getFailure() != null )
                	{
                		testCaseResult.setStatus("FAILURE");
                		testCaseResult.setMessage("Test case failed");
                    	
                	}
                	else {
                		testCaseResult.setStatus("SUCCESS");
                		testCaseResult.setMessage("Test case passed");
                    	
                	}
                	testCaseResult.setCategory("UNIT");
                	testCaseResult.setDuration(testCaseList.get(i).getTime().toString());
                	testCaseResult.setTestCaseName(testCaseList.get(i).getName());
                	testCaseResult.settestSuiteName(testsuite.getName());
                	testCaseResult.setTestToolName("KarmaJasmine");
                	tr.add(testCaseResult);
                	
                }
                
                 
                logger.info("karmaJasmine Unit Report Converted Successfully..!!");
                return tr;
         } catch (Exception e) {
                logger.error("Conversion error for " + inputPath + "Error: " + e);
         }
         return tr;
  }
  
  

}
