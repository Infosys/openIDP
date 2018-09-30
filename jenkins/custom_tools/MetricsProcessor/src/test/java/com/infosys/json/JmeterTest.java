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

/*This class is for testing jmeter*/
public class JmeterTest {

	@Test
	public void responseTimetest(){
		JMeter jm = new JMeter();
		
		jm.setResponseTime(10.00);
		
		Assert.assertEquals(10.00, jm.getResponseTime());
	}
	
	@Test
	public void test(){
		JMeter jm = new JMeter();
		
		jm.setThroughput(10.00);
		
		Assert.assertEquals(10.00, jm.getThroughput());
	}
}
