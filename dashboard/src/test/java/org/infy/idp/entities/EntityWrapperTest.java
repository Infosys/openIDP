package org.infy.idp.entities;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;

public class EntityWrapperTest {

	@Test
	public void test(){
		
		EntityWrapper ew = new EntityWrapper();
		
		ArrayList<String> al = new ArrayList<>();
		
		al.add("one");
		al.add("two");
		
		ew.setCoverageinfo(al);
		
		Assert.assertEquals(al, ew.getCoverageinfo());
	}
}
