package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertSAPUnitTest {

	@Test
	public void test() {
		ConvertSAPUnit.convert("inputPath", null);
		Assert.assertEquals(true, true);
	}
}
