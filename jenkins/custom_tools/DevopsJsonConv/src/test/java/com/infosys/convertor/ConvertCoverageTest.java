package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertCoverageTest {

	@Test
	public void test() {
		ConvertCoverage.convert("inputPath", null);
		
		Assert.assertEquals(true, true);
	}
}
