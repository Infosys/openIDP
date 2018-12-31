package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class TRDataTest {


	
	@Test
	public void transporttest(){
		TRData trd = new TRData();
		
		trd.setTransportRequests(null);
		
		Assert.assertEquals(null, trd.getTransportRequests());
	}
	
	@Test
	public void reqtest(){
		TRData trd = new TRData();
		
		trd.setShow(null);
		
		Assert.assertEquals(null, trd.getShow());
	}
}
