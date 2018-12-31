
package com.infosys.jsonschema;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CSVInfoTest {

	@Test
	public void testEquals1()
	{
		CSVInfo csvInfo=new CSVInfo("name","cc","mi","cp","dp");
		assertEquals("name",csvInfo.getName());
		assertEquals("cc",csvInfo.getCc());
		assertEquals("mi",csvInfo.getMi());
		assertEquals("cp",csvInfo.getCp());
		assertEquals("dp",csvInfo.getDp());
	}
	
	@Test
	public void testEquals2()
	{
		CSVInfo csvInfo=new CSVInfo();
		csvInfo.setCc("cc");
		csvInfo.setCp("cp");
		csvInfo.setDp("dp");
		csvInfo.setMi("mi");
		csvInfo.setName("name");
		assertEquals("name",csvInfo.getName());
		assertEquals("cc",csvInfo.getCc());
		assertEquals("mi",csvInfo.getMi());
		assertEquals("cp",csvInfo.getCp());
		assertEquals("dp",csvInfo.getDp());
	}
	
	@Test
	public void testNotEquals()
	{
		CSVInfo csvInfo=new CSVInfo("name","cc","mi","cp","dp");
		assertEquals("name1",csvInfo.getName());
		assertEquals("cc1",csvInfo.getCc());
		assertEquals("mi1",csvInfo.getMi());
		assertEquals("cp1",csvInfo.getCp());
		assertEquals("dp1",csvInfo.getDp());
	}
}
