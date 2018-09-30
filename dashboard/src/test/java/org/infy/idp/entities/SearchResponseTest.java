package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class SearchResponseTest {

	@Test
	public void texttest(){
		SearchResponse sr = new SearchResponse();
		
		sr.setText("success");
		
		Assert.assertEquals("success", sr.getText());
	}
	
	@Test
	public void valuetest(){
		SearchResponse sr = new SearchResponse();
		
		sr.setValue(10);
		
		Assert.assertEquals((Integer)10, sr.getValue());
	}
}
