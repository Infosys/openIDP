package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class AntTest {

	@Test
	public void test(){
		Ant a = new Ant();
		a.setModuleList(null);
		
		Assert.assertEquals(null, a.getModuleList());
	}
}
