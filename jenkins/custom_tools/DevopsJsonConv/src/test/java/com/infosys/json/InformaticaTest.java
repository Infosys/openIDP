package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class InformaticaTest {

	@Test
	public void test() {
		Informatica i = new Informatica();
		
		i.setInfoObject(null);
		
		Assert.assertEquals(null, i.getInfoObject());
	}
}
