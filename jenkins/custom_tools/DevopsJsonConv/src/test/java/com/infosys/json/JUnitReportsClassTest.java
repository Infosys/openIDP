
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class JUnitReportsClassTest {

	
	@Test
	public void urltest(){
		JunitReportsclass jur = new JunitReportsclass();
		
		jur.setURL("infosys.com");
		
		Assert.assertEquals("infosys.com", jur.getURL());
	}
}
