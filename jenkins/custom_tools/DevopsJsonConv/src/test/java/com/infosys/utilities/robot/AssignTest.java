package com.infosys.utilities.robot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
public class AssignTest {

	@Test
	public void testEquals()
	{
		Assign assign=new Assign();
		assertEquals(0,assign.getVar().size());
	}
	
	@Test
	public void testNotEquals()
	{
		Assign assign=new Assign();
		assertNotEquals(1,assign.getVar().size());
	}
}
