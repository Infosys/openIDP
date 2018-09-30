package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class QueryResponseTest {

	@Test
	public void targettest(){
		QueryResponse qr = new QueryResponse();
		
		qr.setTarget("abc");
		
		Assert.assertEquals("abc",qr.getTarget());
	}
	
	@Test
	public void typetest(){
		QueryResponse qr = new QueryResponse();
		
		qr.setType("type");
		
		Assert.assertEquals("type",qr.getType());
	}
	
	@Test
	public void sertest(){
		QueryResponse qr = new QueryResponse();
		
		String[][] ser = new String[10][10];
		
		qr.setSeries(ser);
		
		Assert.assertEquals(ser,qr.getSeries());
	}
	
	@Test
	public void test(){
		QueryResponse qr = new QueryResponse();
		
		double[][] der = new double[10][10];
		
		qr.setDatapoints(der);
		
		Assert.assertEquals(der,qr.getDatapoints());
	}
	
}
