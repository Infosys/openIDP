package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class IstanbulTest {

	@Test
	public void test(){
		
		Istanbul i = new Istanbul();
		i.setLineCoverage("100");
		
		Assert.assertEquals("100", i.getLineCoverage());
	}
}
