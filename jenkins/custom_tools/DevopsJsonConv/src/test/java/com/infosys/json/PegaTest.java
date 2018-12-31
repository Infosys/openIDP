package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class PegaTest {

	@Test
	public void test(){
		Pega p = new Pega();
		
		p.setPegaFileList(null);
		
		Assert.assertEquals(null, p.getPegaFileList());
	}
}
