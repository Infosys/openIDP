package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class QueryRequestTest {

	@Test
	public void formattest(){
		
		QueryRequest qr = new QueryRequest();
		
		qr.setFormat("format");
		
		Assert.assertEquals("format", qr.getFormat());
	}
	
	@Test
	public void intervaltest(){
		
		QueryRequest qr = new QueryRequest();
		
		qr.setInterval("interval");
		
		Assert.assertEquals("interval", qr.getInterval());
	}
	
	@Test
	public void intervalimstest(){
		
		QueryRequest qr = new QueryRequest();
		
		qr.setIntervalMs(1);
		
		Assert.assertEquals((Integer)1, qr.getIntervalMs());
	}
	
	@Test
	public void datatest(){
		
		QueryRequest qr = new QueryRequest();
		
		qr.setMaxDataPoints(1);
		
		Assert.assertEquals((Integer)1, qr.getMaxDataPoints());
	}
	
	@Test
	public void paneltest(){
		
		QueryRequest qr = new QueryRequest();
		
		qr.setPanelId(1);
		
		Assert.assertEquals((Integer)1, qr.getPanelId());
	}
}
