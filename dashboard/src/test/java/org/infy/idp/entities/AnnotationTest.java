package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class AnnotationTest {

	@Test
	public void datasourcetest(){
		Annotation a = new Annotation();
		
		a.setDatasource("git");
		
		Assert.assertEquals("git", a.getDatasource());
	}
	
	@Test
	public void enabletest(){
		Annotation a = new Annotation();
		
		a.setEnable(true);
		
		Assert.assertEquals((Boolean)true, a.getEnable());
	}
	
	@Test
	public void colortest(){
		Annotation a = new Annotation();
		
		a.setIconColor("red");
		
		Assert.assertEquals("red", a.getIconColor());
	}
	
	@Test
	public void nametest(){
		Annotation a = new Annotation();
		
		a.setName("git");
		
		Assert.assertEquals("git", a.getName());
	}
	
	@Test
	public void test(){
		Annotation a = new Annotation();
		
		a.setQuery("git");
		
		Assert.assertEquals("git", a.getQuery());
	}
}
