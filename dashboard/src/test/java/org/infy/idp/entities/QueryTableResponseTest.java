package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class QueryTableResponseTest {

	@Test
	public void test(){
		QueryTableResponse qtr = new QueryTableResponse();
		
		qtr.setResponse("pass");
		
		Assert.assertEquals("pass", qtr.getResponse());
	}
	
	
}
