package com.infosys.utilities.robot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class StatTest {

	@Test
	public void testEquals()
	{
		Stat stat=new Stat();
		
		stat.setCombined("value");
		stat.setCritical("value");
		stat.setDoc("value");
		stat.setFail(1);
		stat.setId("value");
		stat.setInfo("value");
		stat.setLinks("value");
		stat.setName("value");
		stat.setPass(1);
		stat.setValue("value");
		
		assertEquals("value",stat.getCombined());
		assertEquals("value",stat.getCritical());
		assertEquals("value",stat.getDoc());
		assertEquals(1,stat.getFail());
		assertEquals("value",stat.getId());
		assertEquals("value",stat.getInfo());
		assertEquals("value",stat.getLinks());
		assertEquals("value",stat.getName());
		assertEquals(0,stat.getOtherAttributes().size());
		assertEquals(1,stat.getPass());
		assertEquals("value",stat.getValue());
	}
	@Test
	public void testNotEquals()
	{
		Stat stat=new Stat();
		
		stat.setCombined("value");
		stat.setCritical("value");
		stat.setDoc("value");
		stat.setFail(1);
		stat.setId("value");
		stat.setInfo("value");
		stat.setLinks("value");
		stat.setName("value");
		stat.setPass(1);
		stat.setValue("value");
		
		assertNotEquals("value1",stat.getCombined());
		assertNotEquals("value1",stat.getCritical());
		assertNotEquals("value1",stat.getDoc());
		assertNotEquals(2,stat.getFail());
		assertNotEquals("value1",stat.getId());
		assertNotEquals("value1",stat.getInfo());
		assertNotEquals("value1",stat.getLinks());
		assertNotEquals("value1",stat.getName());
		assertNotEquals(2,stat.getOtherAttributes().size());
		assertNotEquals(2,stat.getPass());
		assertNotEquals("value1",stat.getValue());
	}
}
