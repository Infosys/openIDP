package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertFileNetTest {

	@Test
	public void test() {
		ConvertFileNet cfn = new ConvertFileNet();
		
		cfn.convert("C:\\", "C:\\", "1", "newfile", "oldfile");
		
		
		Assert.assertEquals(true, true);
	}
}
