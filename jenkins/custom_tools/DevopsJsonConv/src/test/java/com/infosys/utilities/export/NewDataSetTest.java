
package com.infosys.utilities.export;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
