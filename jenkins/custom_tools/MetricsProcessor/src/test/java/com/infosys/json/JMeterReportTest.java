/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

/*This class is for testing jemeter report*/
public class JMeterReportTest {
	@Test
	 public void urlTest(){
		 JMeterReports jmr = new JMeterReports();
		 
		 jmr.setURL("infosys.com");
		 
		 Assert.assertEquals("infosys.com", jmr.getURL());
	 }
}
