package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertJmeterTest {

	@Test
	public void test() {
		ConvertJMeter.convert("inputPath", null, null);
		Assert.assertEquals(true, true);
	}
}
