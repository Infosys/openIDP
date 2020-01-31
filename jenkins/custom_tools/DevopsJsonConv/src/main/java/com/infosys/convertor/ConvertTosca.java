package com.infosys.convertor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.infosys.json.JsonClass;
import com.infosys.json.TestCaseResult;

import com.infosys.utilities.tosca.Testsuites;


public class ConvertTosca {
      private static final Logger logger = Logger.getLogger(ConvertTosca.class);
       
       private ConvertTosca() {
		// TODO Auto-generated constructor stub
	}

	
       
       public static List<TestCaseResult> toscaConvert(String inputPath, JsonClass json, String prefixForId) {
              List<TestCaseResult> tr=json.getTestCaseResult();
              if (tr == null)
                     tr = new ArrayList<>();
              try {
                     EditDocType.edit(inputPath);
                     File file = new File(inputPath);
                     JAXBContext jaxbContext = JAXBContext.newInstance(com.infosys.utilities.tosca.Testsuites.class);
                     Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                     com.infosys.utilities.tosca.Testsuites c = (com.infosys.utilities.tosca.Testsuites) jaxbUnmarshaller
                                  .unmarshal(file);
                     if (c.getTestsuite().equals(null)) {
                           logger.info("Report Converted Successfully..!!");
                           return tr;
                     }
                     
                     
                     List<Testsuites.Testsuite.Testcase> tc = c.getTestsuite().getTestcase();
                     
                           for (Testsuites.Testsuite.Testcase testcase : tc) {
                         
                                  TestCaseResult t = new TestCaseResult() ;
                                  
                                  t.setDuration(testcase.getTime().toString());
                                  t.setStartTime(testcase.getTimestamp());
                                  t.settestSuiteName(testcase.getName());
                                  t.setCategory("JUNIT");
                                  t.setTestToolName("TOSCA");
                                
                                  if(testcase.getContent()==null||testcase.getContent().isEmpty()) {
                                         t.setStatus("SUCCESS");
                                         t.setMessage("test case passed");
                                  }
                                  
                                  else if(testcase.getContent()!=null) {
                                	  t.setStatus("FAILURE");
                                      t.setMessage(testcase.getLog());
                                	  
                                  }
                                  
                                  System.out.println(testcase.getLog());
//                                  else if(testcase.getLog().toLowerCase().contains("error")||testcase.getLog().toLowerCase().contains("failed")||testcase.getLog().toLowerCase().contains("fail")||testcase.getLog().toLowerCase().contains("failure")){
//                                         t.setStatus("FAILURE");
//                                         t.setMessage("test case failed");
//                                  }
                                  tr.add(t); 
                     } 
                     
                      
                     logger.info("Report Converted Successfully..!!");
                     return tr;
              } catch (Exception e) {
                     logger.error("Conversion error for " + inputPath + "Error: " + e);
              }
              return tr;
       }
       
       
       
}


