/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

/*This class is for testing Lint*/
public class TSLintTest {

	@Test
	
	public void hightest(){
		TSLint ts = new TSLint();
		
		ts.setHighViolations(10);
		
		Assert.assertEquals((Integer)10, ts.getHighViolations());
	}
	
	@Test
	
	public void mediumtest(){
		TSLint ts = new TSLint();
		
		ts.setMediumViolations(10);
		
		Assert.assertEquals((Integer)10, ts.getMediumViolations());
	}
	
	@Test
	
	public void lowtest(){
		TSLint ts = new TSLint();
		
		ts.setLowViolations(10);
		
		Assert.assertEquals((Integer)10, ts.getLowViolations());
	}
}
