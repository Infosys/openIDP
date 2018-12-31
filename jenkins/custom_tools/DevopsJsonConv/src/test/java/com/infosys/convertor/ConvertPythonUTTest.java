package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertPythonUTTest {

	@Test
	public void test() {
		ConvertPythonUT.convert("inputPath", null, null);
		Assert.assertEquals(true, true);
	}
}
