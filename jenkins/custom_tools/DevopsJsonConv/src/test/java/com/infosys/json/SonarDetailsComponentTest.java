
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class SonarDetailsComponentTest {

	@Test
	public void test(){
		SonarDetailsComponent sc = new SonarDetailsComponent();
		
		sc.setMeasures(null);
		
		Assert.assertEquals(null, sc.getMeasures());
	}
}
