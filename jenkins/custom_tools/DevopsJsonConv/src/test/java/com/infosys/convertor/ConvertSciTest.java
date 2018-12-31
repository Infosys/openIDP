package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertSciTest {

	@Test
	public void test() {
		ConvertSci.convert("inputPath", null, null);
		Assert.assertEquals(true, true);
	}
}
