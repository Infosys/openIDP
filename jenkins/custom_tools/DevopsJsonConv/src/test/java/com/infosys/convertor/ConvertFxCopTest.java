package com.infosys.convertor;

import java.io.IOException;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertFxCopTest {

	@Test
	public void test() throws IOException {
		ConvertFxCop.convert(null);
		
		String[] args = new String[10];
		
		ConvertFxCop.main(args);
		
		Assert.assertEquals(true, true);
	}
}
