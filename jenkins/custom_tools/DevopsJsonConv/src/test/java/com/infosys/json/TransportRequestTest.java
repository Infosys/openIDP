package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class TransportRequestTest {

	@Test
	public void desttest(){
		TransportRequests tr = new TransportRequests();
		
		tr.setDestSystem("dest");
		
		Assert.assertEquals("dest", tr.getDestSystem());
	}
	
	@Test
	public void srctest(){
		TransportRequests tr = new TransportRequests();
		
		tr.setSrcSystem("src");
		
		Assert.assertEquals("src", tr.getSrcSystem());
	}
	
	@Test
	public void usertest(){
		TransportRequests tr = new TransportRequests();
		
		tr.setUser("user");
		
		Assert.assertEquals("user", tr.getUser());
	}
	
	@Test
	public void transtest(){
		TransportRequests tr = new TransportRequests();
		
		tr.setTransportReqName("trq");
		
		Assert.assertEquals("trq", tr.getTransportReqName());
	}
}
