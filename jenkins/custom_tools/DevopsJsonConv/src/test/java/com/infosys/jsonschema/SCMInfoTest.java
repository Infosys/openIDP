
package com.infosys.jsonschema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class SCMInfoTest {

	@Test
	public void testEquals1()
	{
		SCMInfo scmInfo=new SCMInfo();
		assertEquals("Wed Jan 01 00:00:00 IST 1897",scmInfo.getLastModified());
		assertEquals("File(s) Checked In", scmInfo.getCommitMessage());
		assertEquals("none",scmInfo.getId());
		assertEquals("0",scmInfo.getLatestFileVersion());
		assertEquals("none", scmInfo.getLastModifiedBy());
		assertEquals("none", scmInfo.getGetAffectedPath());
		assertEquals("none", scmInfo.getRemoteUrl());
		
	}
	
	@Test
	public void testEquals2()
	{
		SCMInfo scmInfo=new SCMInfo();
		scmInfo.setLastModified("Wed Jan 01 00:00:00 IST 1897");
		scmInfo.setCommitMessage("File(s) Checked In");
		scmInfo.setId("none");
		scmInfo.setLatestFileVersion("0");
		scmInfo.setLastModifiedBy("none");
		scmInfo.setGetAffectedPath("none");
		scmInfo.setRemoteUrl("none");
		
		assertEquals("Wed Jan 01 00:00:00 IST 1897",scmInfo.getLastModified());
		assertEquals("File(s) Checked In", scmInfo.getCommitMessage());
		assertEquals("none",scmInfo.getId());
		assertEquals("0",scmInfo.getLatestFileVersion());
		assertEquals("none", scmInfo.getLastModifiedBy());
		assertEquals("none", scmInfo.getGetAffectedPath());
		assertEquals("none", scmInfo.getRemoteUrl());
		
	}
	
	@Test
	public void testNotEquals()
	{
		SCMInfo scmInfo=new SCMInfo();
		assertNotEquals("Wed Jan 01 00:00:00 IST 18971",scmInfo.getLastModified());
		assertNotEquals("File(s) Checked In1", scmInfo.getCommitMessage());
		assertNotEquals("none1",scmInfo.getId());
		assertNotEquals("01",scmInfo.getLatestFileVersion());
		assertNotEquals("none1", scmInfo.getLastModifiedBy());
		assertNotEquals("none1", scmInfo.getGetAffectedPath());
		assertNotEquals("none1", scmInfo.getRemoteUrl());
		
	}
}
