
package com.infosys.json;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;



public class BuildOwnerTest {
	
	
	
	
	
	@Test
	public void abc(){
		ArrayList<String> al = new ArrayList<String>();
		al.add("firstdata");
		al.add("seconddata");
		al.add("thirddata");
		BuildOwner bo = new BuildOwner();
		
		bo.setId(al);
		
		Assert.assertEquals(al, bo.getId());
	}
}
