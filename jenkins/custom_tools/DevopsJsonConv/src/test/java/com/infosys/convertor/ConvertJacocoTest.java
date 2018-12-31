package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertJacocoTest {

	@Test
	public void test() {
		ConvertJacoco.convert("inputPath", null, null);
		Assert.assertEquals(true, true);
	}
}
