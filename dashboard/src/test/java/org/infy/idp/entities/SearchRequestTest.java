package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class SearchRequestTest {

	@Test
	public void test(){
		
		SearchRequest sr = new SearchRequest();
		
		sr.setTarget("True");
		
		Assert.assertEquals("True", sr.getTarget());
	}
}
