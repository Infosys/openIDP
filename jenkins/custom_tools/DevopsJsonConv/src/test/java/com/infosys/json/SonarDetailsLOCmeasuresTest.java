
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

/*This class provides Test cases for sonar Details of Loc*/

public class SonarDetailsLOCmeasuresTest {

	@Test
	public void metrictets(){
		
		SonarDetailsLocmeasures sloc = new SonarDetailsLocmeasures();
		
		sloc.setMetric("infosys");
		
		Assert.assertEquals("infosys", sloc.getMetric());
	}
	
	@Test
	public void valuetest(){
		
		SonarDetailsLocmeasures sloc = new SonarDetailsLocmeasures();
		
		sloc.setValue(101);
		
		Assert.assertEquals(101, sloc.getValue());
	}
}
