package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertLintReportTest {

	@Test
	public void test() {
		ConvertLintReport.convert("inputPath", null, null, null);
		Assert.assertEquals(true, true);
	}
}
