package org.infy.idp.entities;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;

public class BuildOwnerTest {

	@Test
	public void test(){
		BuildOwner bo = new BuildOwner();
		
		ArrayList<String> al = new ArrayList<>();
		
		al.add("1");
		al.add("2");
		al.add("3");
		al.add("4");
		
		bo.setId(al);
		
		Assert.assertEquals(al, bo.getId());
	}
}
