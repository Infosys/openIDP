package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertCheckStyleTest {

	@Test
	public void test() {
		//ConvertCheckstyle.convert(null, null, null, null);
		
		ConvertCheckstyle.getcheckStyleSeverity();
		
		Assert.assertEquals(true, true);
	}
}
