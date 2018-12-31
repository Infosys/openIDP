package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertBuildLogTest {

	@Test
	public void test() {
		ConvertBuildLog.convert("inputPath", null);
	
		Assert.assertEquals(true, true);
	}
}
