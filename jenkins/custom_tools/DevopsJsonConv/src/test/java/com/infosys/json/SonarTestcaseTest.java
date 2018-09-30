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

/*This class provides Test cases for SonarTest*/

public class SonarTestcaseTest {

	@Test
	public void nametest(){
		SonarTestcase stc = new SonarTestcase();
		
		stc.setName("infosys");
		
		Assert.assertEquals("infosys",stc.getName());
	}
	
	@Test
	public void timetest(){
		SonarTestcase stc = new SonarTestcase();
		
		stc.setTime("10");
		
		Assert.assertEquals("10",stc.getTime());
	}
}
