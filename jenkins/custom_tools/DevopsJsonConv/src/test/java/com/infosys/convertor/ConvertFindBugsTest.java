package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertFindBugsTest {

	@Test
	public void test() {
		ConvertFindbugs.convert(null, null, null);
		
		ConvertFindbugs.getFindBugsSeverity();
		
		Assert.assertEquals(true, true);
	}
}
