
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class ServiceTestTest {

	@Test
	public void test(){
		ServiceTest st = new ServiceTest();
		
		st.setiFast(null);
		
		Assert.assertEquals(null, st.getiFast());
	}
}
