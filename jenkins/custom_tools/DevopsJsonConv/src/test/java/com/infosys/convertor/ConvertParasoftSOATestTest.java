package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertParasoftSOATestTest {

	@Test
	public void test() {
		ConvertParasoftSOATest.convert("inputPath", null, null);
		Assert.assertEquals(true, true);
	}
}
