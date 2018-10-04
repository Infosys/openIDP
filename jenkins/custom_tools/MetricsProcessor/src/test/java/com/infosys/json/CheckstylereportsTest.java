
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class CheckstylereportsTest {
	
	@Test
	public void urltest(){
		CheckStyleReports cs = new CheckStyleReports();
		cs.setURL("infosys.com");
		Assert.assertEquals("infosys.com", cs.getURL());
	}
}
