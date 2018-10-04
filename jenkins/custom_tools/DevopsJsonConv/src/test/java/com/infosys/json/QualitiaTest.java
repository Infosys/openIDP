
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class QualitiaTest {
	@Test
	public void failtest(){
		Qualitia q = new Qualitia();
		
		q.setFail(0);
		
		Assert.assertEquals((Integer)0, q.getFail());
	}
	
	@Test
	public void passtest(){
		Qualitia q = new Qualitia();
		
		q.setPass(0);
		
		Assert.assertEquals((Integer)0, q.getPass());
	}
	
	@Test
	public void totaltest(){
		Qualitia q = new Qualitia();
		
		q.setTotalTest(0);
		
		Assert.assertEquals((Integer)0, q.getTotalTest());
	}
}
