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

public class VersionInfoTest {

	@Test
	public void testEquals()
	{
		VersionInfo versionInfo =new VersionInfo();
		
		versionInfo.setScmurl("scmurl");
		
		assertEquals("Wed Jan 01 00:00:00 IST 1897",versionInfo.getLastModified());
		assertEquals("File Checked In", versionInfo.getCommitMessage());
		assertEquals("none", versionInfo.getid());
		assertEquals("none", versionInfo.getLastModifiedBy());
		assertEquals("none", versionInfo.getCommitId());
		assertEquals("0",versionInfo.getLatestFileVersion());
		assertEquals("scmurl", versionInfo.getScmurl());
		
	}
	
	@Test
	public void testNotEquals()
	{
		VersionInfo versionInfo =new VersionInfo();
		
		versionInfo.setScmurl("scmurl");
		
		assertNotEquals("Wed Jan 01 00:00:00 IST 18971",versionInfo.getLastModified());
		assertNotEquals("File Checked In1", versionInfo.getCommitMessage());
		assertNotEquals("none1", versionInfo.getid());
		assertNotEquals("none1", versionInfo.getLastModifiedBy());
		assertNotEquals("none1", versionInfo.getCommitId());
		assertNotEquals("01",versionInfo.getLatestFileVersion());
		assertNotEquals("scmurl1", versionInfo.getScmurl());
		
	}
}
