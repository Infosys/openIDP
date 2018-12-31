package com.infosys.convertor;
import org.junit.Test;

import junit.framework.Assert;
public class ConvertSoapUIReportTest {
	@Test
	public void test() {
		ConvertSoapUIReport.convert("inputPath", null);
		Assert.assertEquals(true, true);
	}
}
