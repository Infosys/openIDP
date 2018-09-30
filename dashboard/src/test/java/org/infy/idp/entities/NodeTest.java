package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class NodeTest {

	@Test
	public void codetest(){
		Node n = new Node();
		
		n.setCode("code");
		
		Assert.assertEquals("code", n.getCode());
	}
	
	@Test
	public void filetest(){
		Node n = new Node();
		
		n.setFileName("file");
		
		Assert.assertEquals("file", n.getFileName());
	}
	
	@Test
	public void linetest(){
		Node n = new Node();
		
		n.setLine(1);
		
		Assert.assertEquals((Integer)1, n.getLine());
	}
	
	@Test
	public void nametest(){
		Node n = new Node();
		
		n.setName("name");
		
		Assert.assertEquals("name", n.getName());
	}
}
