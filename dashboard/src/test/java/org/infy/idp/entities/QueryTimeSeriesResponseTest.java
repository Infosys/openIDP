package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class QueryTimeSeriesResponseTest {

	@Test
	public void timestamptest(){
		QueryTimeSeriesResponse qtrs = new QueryTimeSeriesResponse("10");
		
		qtrs.setTimestamp("10");
		
		Assert.assertEquals("10", qtrs.getTimestamp());
	}
	
	@Test
	public void valuetest(){
		QueryTimeSeriesResponse qtrs = new QueryTimeSeriesResponse("10");
		
		qtrs.setValue("10");
		
		Assert.assertEquals("10", qtrs.getValue());
	}
}
