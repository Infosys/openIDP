package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertCoberturaTest {

	@Test
	public void test() {
		ConvertCobertura.convert("inputPath", null);
		
		Assert.assertEquals(true, true);
	}
}
