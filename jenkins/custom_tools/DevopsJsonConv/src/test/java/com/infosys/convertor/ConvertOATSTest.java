package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertOATSTest {

	@Test
	public void test() {
		ConvertOATS.convert("path", null);
		Assert.assertEquals(true, true);
	}
}
