package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class UnitTestingTest {
	@Test
	public void test() {
		UnitTesting ut = new UnitTesting();
		
		ut.setjUnit(null);
		
		Assert.assertEquals(null, ut.getjUnit());
	}
}
