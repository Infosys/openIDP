
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;



public class RobotReportsTest {

	@Test
	public void urlTest(){
		
		RobotReports rr = new RobotReports();
		
		rr.setURL("infosys.com");
		
		Assert.assertEquals("infosys.com", rr.getURL());
	}
}
