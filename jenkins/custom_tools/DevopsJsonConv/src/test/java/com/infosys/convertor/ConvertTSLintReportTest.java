package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertTSLintReportTest {

	@Test
	public void test() {
		ConvertTSLintReport.convert("C:\\" );
		
		ConvertTSLintReport.getHighViolations();
		
		//ConvertTSLintReport.setTotalSeverities(null);
		/*
		ConvertTSLintReport.getLowViolations();
		
		ConvertTSLintReport.getMediumViolations();*/
		
		Assert.assertEquals(true, true);
	}
}
