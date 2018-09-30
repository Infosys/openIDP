/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.export;

import static org.junit.Assert.*;

import org.junit.Test;

public class NewDataSetTest {

	@Test
	public void testEquals()
	{
		NewDataSet newDataSet=new NewDataSet();
		
		assertEquals(0,newDataSet.getIncludeOptionsOrSavedManifest().size());
	}
	
	@Test
	public void testNotEquals()
	{
		NewDataSet newDataSet=new NewDataSet();
		
		assertNotEquals(1,newDataSet.getIncludeOptionsOrSavedManifest().size());
	}
}
