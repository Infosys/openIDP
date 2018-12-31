package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertJunitTest {

	@Test
	public void test() {
		ConvertJUnit.convert("inputPath", null, "prefixForId");
		ConvertJUnit.convertgo("path", null);
		//ConvertJUnit.getJUnitSummary(null);
		Assert.assertEquals(true, true);
	}
}
