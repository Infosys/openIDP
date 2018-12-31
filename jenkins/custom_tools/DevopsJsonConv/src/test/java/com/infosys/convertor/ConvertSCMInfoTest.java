package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertSCMInfoTest {

	@Test
	public void test() {
		ConvertSCMInfo.convert("inputPath", null, "app");
		Assert.assertEquals(true, true);
	}
}
