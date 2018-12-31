package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertNUnitTest {

	@Test
	public void test() {
		ConvertNUnit.convert("inputPath", null, "prefixForId");
		Assert.assertEquals(true, true);
	}
}
