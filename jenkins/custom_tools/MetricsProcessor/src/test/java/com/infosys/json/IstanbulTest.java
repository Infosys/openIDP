
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;



public class IstanbulTest {

	@Test
	public void test(){
		Istanbul il = new Istanbul();
		
		il.setLineCoverage("100");
		
		Assert.assertEquals("100", il.getLineCoverage());
	}
}
