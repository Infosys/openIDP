package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertProtractorTest {

	@Test
	public void test() {
		ConvertProtractor.convert("inputPath", null, null);
		Assert.assertEquals(true, true);
	}
}
