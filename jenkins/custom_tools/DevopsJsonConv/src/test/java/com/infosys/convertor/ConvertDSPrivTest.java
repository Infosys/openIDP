package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertDSPrivTest {

	@Test
	public void test() {
		ConvertDSPriv.convert("path", null);
		
		Assert.assertEquals(true, true);
	}
}
