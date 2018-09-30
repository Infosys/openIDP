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

public class BugCollectionTest {

	@Test
	public void testEquals()
	{
		BugCollection bugCollection = new BugCollection("bug_type1","bug_category1","id1",1,2,1);
		assertEquals("bug_type1",bugCollection.getBugType());
		assertEquals("bug_category1", bugCollection.getBugCategory());
		assertEquals("id1",bugCollection.getId());
		assertEquals(1, bugCollection.getStartLine());
		assertEquals(2, bugCollection.getEndLine());
		assertEquals(1, bugCollection.getPriority());
	}
	
	@Test
	public void testNotEquals()
	{
		BugCollection bugCollection = new BugCollection("bug_type1","bug_category1","id1",1,2,1);
		assertEquals("bug_type1",bugCollection.getBugType());
		assertEquals("bug_category1", bugCollection.getBugCategory());
		assertEquals("id1",bugCollection.getId());
		assertEquals(1, bugCollection.getStartLine());
		assertEquals(2, bugCollection.getEndLine());
		assertEquals(1, bugCollection.getPriority());
	}
	
	@Test
	public void testNull()
	{
		BugCollection bugCollection = new BugCollection(null,null,null,1,2,1);
		assertNull(bugCollection.getBugType());
		assertNull(bugCollection.getBugCategory());
		assertNull(bugCollection.getId());
	}
}
