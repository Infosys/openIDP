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

public class SoapUITestcaseTest {

	@Test
	public void nametest(){
		
		SoapUITestcase sc = new SoapUITestcase();
		
		sc.setName("infosys");
		
		Assert.assertEquals("infosys", sc.getName());
	}
	
	@Test
	public void timetest(){
		
		SoapUITestcase sc = new SoapUITestcase();
		
		sc.setTime("10");
		
		Assert.assertEquals("10", sc.getTime());
	}
}
