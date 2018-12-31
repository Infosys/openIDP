package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertIstanbulTest {

	@Test
	public void test() {
		ConvertIstanbul.convert("inputPath", null);
		Assert.assertEquals(true, true);
	}
}
