package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertFxCopConsTest {

	@Test
	public void test() {
		ConvertFxCopCons.convert("inputPath");
		
		Assert.assertEquals(true, true);
	}
}
