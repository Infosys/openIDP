
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class CodeAnalysisTest {

	@Test
	public void idtest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setId("1");
		
		Assert.assertEquals("1", "1");
	}
}
