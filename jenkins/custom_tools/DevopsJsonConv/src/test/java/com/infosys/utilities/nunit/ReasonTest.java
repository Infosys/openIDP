/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.nunit;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReasonTest {

	@Test
	public void testEquals()
	{
		Reason reason=new Reason();
		
		reason.setMessage("value");
		
		assertEquals("value",reason.getMessage());
	}
	
	@Test
	public void testNotEquals()
	{
		Reason reason=new Reason();
		
		reason.setMessage("value");
		
		assertNotEquals("value1",reason.getMessage());
	}
}
