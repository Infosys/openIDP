/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.infosys.json.JMeter;
import com.infosys.json.JmeterViewResults;
import com.infosys.json.JsonClass;
import com.infosys.json.Performance;
import com.infosys.json.TestCaseResult;
import com.infosys.utilities.jmeter.TestResults;
import com.infosys.utilities.jmeter.TestResults.HttpSample;
import com.infosys.utilities.jmeter.TestResults.Sample;

public class ConvertJMeter {
	private static final Logger logger = Logger.getLogger(ConvertJMeter.class);
	private static double tTotal = 0.0;
	private static double tCount = 0.0;
	private static double ltTotal = 0.0;
	private static double ltCount = 0.0;

	private ConvertJMeter() {
	}

	public static void convert(String inputPath, JsonClass jsonClass, List<TestCaseResult> listTR) {
		try {
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(TestResults.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			TestResults testResults = (TestResults) jaxbUnmarshaller.unmarshal(file);
			JMeter jMeter = new JMeter();
			if (testResults == null)
				return;
			if (testResults.getHttpSample() == null || testResults.getHttpSample().isEmpty()) {
				setJMeterObj(jsonClass, jMeter);
				// return;
			}
			iterateOverHttpSample(testResults.getHttpSample(), listTR);
			if (testResults.getSample() != null && !testResults.getSample().isEmpty())
				iterateOverSample(testResults.getSample(), listTR);
				
			try {
				jMeter.setThroughput(tTotal / tCount);
				jMeter.setResponseTime(ltTotal / ltCount);
			} catch (Exception e) {
				logger.error("Error while updating Jmeter json values", e);
			}
			setJMeterObj(jsonClass, jMeter);
			logger.info("JMeter report Converted Successfully..!!");
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + e);
		}
	}

	private static void setJMeterObj(JsonClass jsonClass, JMeter jMeter) {
		Performance p = jsonClass.getPerformanceTest();
		if (p == null)
			p = new Performance();
		p.setjMeter(jMeter);
		jsonClass.setPerformanceTest(p);
	}

	private static void iterateOverHttpSample(List<HttpSample> httpSampleArr, List<TestCaseResult> listTR) {
		for (HttpSample httpSample : httpSampleArr) {
			tTotal += httpSample.getT();
			tCount += 1;
			ltTotal += httpSample.getLt();
			ltCount += 1;
			TestCaseResult tr = new TestCaseResult();
			tr.settestSuiteName(httpSample.getLb());
			if (httpSample.isS()) {
				tr.setStatus("SUCCESS");
			} else {
				tr.setStatus("FAILURE");
			}
			tr.setDuration(Integer.toString(httpSample.getT()));
			tr.setId(httpSample.getTn());
			tr.setMessage(httpSample.getRm());
			tr.setCategory("Performance");
			tr.setTestToolName("JMeter");
			listTR.add(tr);
		}
	}

	private static void iterateOverSample(List<Sample> sampleArr, List<TestCaseResult> listTR) {
		for (Sample sample : sampleArr) {
			tTotal += sample.getT();
			tCount += 1;
			ltTotal += sample.getLt();
			ltCount += 1;
			TestCaseResult tr = new TestCaseResult();
			tr.settestSuiteName(sample.getLb());
			if (sample.isS()) {
				tr.setStatus("SUCCESS");
			} else {
				tr.setStatus("FAILURE");
			}
			tr.setDuration(Integer.toString(sample.getT()));
			tr.setId(sample.getTn());
			tr.setMessage(sample.getRm());
			tr.setCategory("Performance");
			tr.setTestToolName("JMeter");
			listTR.add(tr);
		}
	}
	
	
	
	public static List<JmeterViewResults> convertViewResult(String inputPath, String[] arg) {
		
		List<JmeterViewResults> finalList=new ArrayList<>();
		
		
		try {
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(TestResults.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			TestResults testResults = (TestResults) jaxbUnmarshaller.unmarshal(file);
			
//			List<Sample> sampleArr = testResults.getSample();
			List<HttpSample> sampleArr = testResults.getHttpSample();
			for (HttpSample sample : sampleArr) {
				JmeterViewResults obj =new  JmeterViewResults();
				obj.setElapsed(String.valueOf(sample.getT()));
				

				Timestamp ts=new Timestamp(sample.getTs().longValue());  
                Date date=ts;  
               
				obj.setTimeStamp(String.valueOf(date));
				
				obj.setThreadName(sample.getTn());
				obj.setLabel(sample.getLb());
				
				System.out.println(sample.isS());
				Boolean value = sample.isS();
				if(value=true) {
				obj.setSuccess("SUCCESS");	
				}
				else {
					obj.setSuccess("FAIL");
				}
				obj.setBytes(String.valueOf(sample.getBy()));
				obj.setSentBytes(String.valueOf(sample.getSby()));
				obj.setLatency(String.valueOf(sample.getLt()));
				
				
				obj.setConnect(String.valueOf(sample.getCt()));
		
				
				finalList.add(obj);
			}
			
	
		}
		catch (Exception e) {
			logger.error("Conversion error for " + inputPath + e);
		}

		return finalList;
		
	}
	
	
	
}
