package com.infosys.json;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;

public class ModuleTest {

	@Test
	public void moduletest() {
		Module mt = new Module();
		
		ArrayList<String> al = new ArrayList<>();
		al.add("m1");
		al.add("m2");
		
		mt.setModuleName(al);
		
		Assert.assertEquals(al, mt.getModuleName());
		
	}
}
