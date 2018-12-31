package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class DotNetTest {

	@Test
	public void test(){
		Dotnet dt = new Dotnet();
		
		dt.setModuleList(null);
		
		Assert.assertEquals(null, dt.getModuleList());
	}
}
