
package com.infosys.jsonschema;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PmdTest {


	
	@Test
	public void testEquals2()
	{
		Pmd pmd=new Pmd();
		pmd.setBeginline(1);
		pmd.setBegincolumn(1);
		pmd.setMessage("content");
		pmd.setEndline(5);
		pmd.setEndcolumn(5);
		pmd.setExternalInfoUrl("externalInfoUrl");
		pmd.setPriority(1);
		pmd.setRule("rule");
		pmd.setRuleset("ruleset");
		
		assertEquals(1,pmd.getBeginline());
		assertEquals(1,pmd.getBegincolumn());
		assertEquals("id",pmd.getId());
		assertEquals("content",pmd.getMessage());
		assertEquals(5,pmd.getEndline());
		assertEquals(5,pmd.getEndcolumn());
		assertEquals("externalInfoUrl",pmd.getExternalInfoUrl());
		assertEquals(1,pmd.getPriority());
		assertEquals("rule",pmd.getRule());
		assertEquals("rileset",pmd.getRuleset());
	}
}
