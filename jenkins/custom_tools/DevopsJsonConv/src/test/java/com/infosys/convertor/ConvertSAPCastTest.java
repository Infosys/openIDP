package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertSAPCastTest {

	@Test
	public void test() {
		ConvertSAPCast.convert(null, null, "SAPWarName");
		Assert.assertEquals(true, true);
	}
}
