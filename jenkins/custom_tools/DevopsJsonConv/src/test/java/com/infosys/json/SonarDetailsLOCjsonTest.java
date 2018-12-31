
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class SonarDetailsLOCjsonTest {

	@Test
	public void test(){
		
		SonarDetailsLOCjson sloc = new SonarDetailsLOCjson();
		
		sloc.setComponent(null);
		
		Assert.assertEquals(null, sloc.getComponent());

	}
}
