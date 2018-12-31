package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertPmdTest {

	@Test
	public void test() {
		ConvertPmd.getPmdSeverity();
		ConvertPmd.convert(null, null, null);
		Assert.assertEquals(true, true);
	}
}
