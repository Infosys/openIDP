
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class NodeTest {

	@Test
	public void codetest(){
		Node n = new Node();
		
		n.setCode("if condition");
		
		Assert.assertEquals("if condition", n.getCode());
	}
	
	@Test
	public void filenametest(){
		Node n = new Node();
		
		n.setFileName("jsonfile");
		
		Assert.assertEquals("jsonfile", n.getFileName());
	}
	
	@Test
	public void Linetest(){
		Node n = new Node();
		
		n.setLine(10);
		
		Assert.assertEquals((Integer)10, n.getLine());
	}
	
	@Test
	public void nametest(){
		Node n = new Node();
		
		n.setName("jsonfile");
		
		Assert.assertEquals("jsonfile", n.getName());
	}
}
