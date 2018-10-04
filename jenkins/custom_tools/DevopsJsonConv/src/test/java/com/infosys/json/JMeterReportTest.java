
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class JMeterReportTest {
	@Test
	 public void urlTest(){
		 JMeterReports jmr = new JMeterReports();
		 
		 jmr.setURL("infosys.com");
		 
		 Assert.assertEquals("infosys.com", jmr.getURL());
	 }
}
