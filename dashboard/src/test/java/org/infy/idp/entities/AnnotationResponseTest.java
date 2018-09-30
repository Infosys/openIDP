package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class AnnotationResponseTest {

	@Test
	public void tagstest(){
		AnnotationResponse ar = new AnnotationResponse();
		
		ar.setTags("infosys");
		
		Assert.assertEquals("infosys", ar.getTags());
	}
	
	@Test
	public void texttest(){
		AnnotationResponse ar = new AnnotationResponse();
		
		ar.setText("infosys");;
		
		Assert.assertEquals("infosys", ar.getText());
	}
	
	@Test
	public void timetest(){
		AnnotationResponse ar = new AnnotationResponse();
		
		ar.setTime("10:00");
		
		Assert.assertEquals("10:00", ar.getTime());
	}
	
	@Test
	public void titletest(){
		AnnotationResponse ar = new AnnotationResponse();
		
		ar.setTitle("infosys");
		
		Assert.assertEquals("infosys", ar.getTitle());
	}
}
