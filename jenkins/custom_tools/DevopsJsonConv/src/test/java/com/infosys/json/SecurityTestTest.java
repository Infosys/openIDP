
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class SecurityTestTest {

	@Test
	public void test(){
		SecurityTest st = new SecurityTest();
		st.setCheckmarx(null);
		
		Assert.assertEquals(null, st.getCheckmarx());
		
	}
}
