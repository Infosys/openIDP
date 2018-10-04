
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


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
