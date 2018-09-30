/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.jsonschema;

import static org.junit.Assert.*;

import org.junit.Test;

public class PmdTest {

	@Test
	public void testEquals1()
	{
		Pmd pmd=new Pmd(1, 1, "id", "content", 5, 5, "externalInfoUrl",1,"rule", "ruleset");
		
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
	
	@Test
	public void testNotEquals()
	{
		Pmd pmd=new Pmd(1, 1, "id", "content", 5, 5, "externalInfoUrl",1,"rule", "ruleset");
		
		assertNotEquals(2,pmd.getBeginline());
		assertNotEquals(2,pmd.getBegincolumn());
		assertNotEquals("id1",pmd.getId());
		assertNotEquals("content1",pmd.getMessage());
		assertNotEquals(6,pmd.getEndline());
		assertNotEquals(6,pmd.getEndcolumn());
		assertNotEquals("externalInfoUrl1",pmd.getExternalInfoUrl());
		assertNotEquals(2,pmd.getPriority());
		assertNotEquals("rule1",pmd.getRule());
		assertNotEquals("rileset1",pmd.getRuleset());
	}
	
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
