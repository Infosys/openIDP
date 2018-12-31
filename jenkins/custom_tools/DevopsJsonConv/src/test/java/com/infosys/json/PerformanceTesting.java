
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class PerformanceTesting {
	@Test
	public void test(){
		Performance p = new Performance();
		
		p.setjMeter(null);
		
		Assert.assertEquals(null, p.getjMeter());
	}
}
