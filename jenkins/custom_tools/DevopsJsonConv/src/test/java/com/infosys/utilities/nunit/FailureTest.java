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

public class FailureTest {

	@Test
	public void testEquals()
	{
		Failure failure=new Failure();
		
		failure.setMessage("value");
		failure.setStackTrace("value");
		
		assertEquals("value",failure.getMessage());
		assertEquals("value",failure.getStackTrace());
	}
	
	@Test
	public void testNotEquals()
	{
		Failure failure=new Failure();
		
		failure.setMessage("value");
		failure.setStackTrace("value");
		
		assertNotEquals("value1",failure.getMessage());
		assertNotEquals("value1",failure.getStackTrace());
	}
}
