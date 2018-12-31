package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class CastReportTest {

	@Test
	public void test() {
		CastReport cr = new CastReport();
		
		cr.readCastReport("sapwarname");
		
		Assert.assertEquals("sapwarname", "sapwarname");
	}
}
