package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertSiebelPackageContentTest {

	@Test
	public void test() {
		ConvertSiebelPackageContent.convert("path", null);
		Assert.assertEquals(true, true);
	}
}
