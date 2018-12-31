
package com.infosys.utilities.checkstyle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.infosys.utilities.checkstyle.Checkstyle.File;

public class CheckstyleTest {

	@Test
	public void testEquals()
	{
		Checkstyle checkstyle=new Checkstyle();
		
		checkstyle.setVersion("version");
		
		assertEquals("version",checkstyle.getVersion());
		assertEquals(0,checkstyle.getFile().size());
		
		File file=new File();
		file.setName("name");
		assertEquals("name", file.getName());
		assertEquals(0, file.getError().size());
		
		File.Error error=new File.Error();
		error.setColumn((short)1);
		error.setLine((short)1);
		error.setMessage("message");
		error.setSeverity("severity");
		error.setSource("source");
		error.setValue("value");
		
		assertEquals(1, (short)error.getColumn());
		assertEquals(1, (short)error.getLine());
		assertEquals("message",error.getMessage());
		assertEquals("severity",error.getSeverity());
		assertEquals("source",error.getSource());
		assertEquals("value",error.getValue());
		
	}
	
	@Test
	public void testNotEquals()
	{
		Checkstyle checkstyle=new Checkstyle();
		
		checkstyle.setVersion("version");
		
		assertNotEquals("version1",checkstyle.getVersion());
		assertNotEquals(1,checkstyle.getFile().size());
		
		File file=new File();
		file.setName("name");
		assertNotEquals("name1", file.getName());
		assertNotEquals(1, file.getError().size());
		
		File.Error error=new File.Error();
		error.setColumn((short)1);
		error.setLine((short)1);
		error.setMessage("message");
		error.setSeverity("severity");
		error.setSource("source");
		error.setValue("value");
		
		assertNotEquals(11, (short)error.getColumn());
		assertNotEquals(11, (short)error.getLine());
		assertNotEquals("message1",error.getMessage());
		assertNotEquals("severity1",error.getSeverity());
		assertNotEquals("source1",error.getSource());
		assertNotEquals("value1",error.getValue());
		
	}
}
