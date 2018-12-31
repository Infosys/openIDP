
package com.infosys.jsonschema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CheckstyleTest {

	@Test
	public void testEquals()
	{
		Checkstyle checkstyle =new Checkstyle("id1",10,"message1","severity1","source1","column1");
		assertEquals("id1",checkstyle.getId());
		assertEquals(10,checkstyle.getLine());
		assertEquals("message1",checkstyle.getMessage());
		assertEquals("severity1",checkstyle.getSeverity());
		assertEquals("source1",checkstyle.getSource());
		assertEquals("column1",checkstyle.getColumn());
	}
	
	@Test
	public void testNotEquals()
	{
		Checkstyle checkstyle =new Checkstyle("id1",10,"message1","severity1","source1","column1");
		assertNotEquals("id2",checkstyle.getId());
		assertNotEquals(12,checkstyle.getLine());
		assertNotEquals("message2",checkstyle.getMessage());
		assertNotEquals("severity2",checkstyle.getSeverity());
		assertNotEquals("source2",checkstyle.getSource());
		assertNotEquals("column2",checkstyle.getColumn());
	}
	
	@Test
	public void testSeverityEquals()
	{
		Checkstyle checkstyle =new Checkstyle();
		checkstyle.setCritical(1);
		checkstyle.setBlocker(2);
		checkstyle.setMajor(3);
		checkstyle.setMinor(4);
		checkstyle.setInfo(5);
		
		assertEquals((Integer)1,checkstyle.getCritical());
		assertEquals((Integer)2,checkstyle.getBlocker());
		assertEquals((Integer)3,checkstyle.getMajor());
		assertEquals((Integer)4,checkstyle.getMinor());
		assertEquals((Integer)5,checkstyle.getInfo());
	}
}
