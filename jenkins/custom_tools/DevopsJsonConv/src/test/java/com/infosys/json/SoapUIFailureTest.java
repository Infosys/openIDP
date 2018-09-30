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

/*This class provides Test cases for sonar UI Failure*/

public class SoapUIFailureTest {

	@Test
	public void msgtest(){
		SoapUIFailure so = new SoapUIFailure();
		
		so.setMessage("infosys");
		
		Assert.assertEquals("infosys", so.getMessage());
	}
	
	@Test
	public void typetest(){
		SoapUIFailure so = new SoapUIFailure();
		
		so.setType("infosys");
		
		Assert.assertEquals("infosys", so.getType());
	}
}
