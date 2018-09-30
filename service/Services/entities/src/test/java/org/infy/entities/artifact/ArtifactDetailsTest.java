/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.artifact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArtifactDetailsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArtifactDetailsValues() {
		ArtifactDetails testObj = new ArtifactDetails();
		testObj.setActionTime("actionTime");
		testObj.setEnvironment("environment");
		testObj.setOwner("owner");
		testObj.setRemark("remark");
		testObj.setStatus("status");
		
		assertEquals("actionTime",testObj.getActionTime());
		assertEquals("environment",testObj.getEnvironment());
		assertEquals("owner",testObj.getOwner());
		assertEquals("remark",testObj.getRemark());
		assertEquals("status",testObj.getStatus());
	}
	
	@Test
	public void testArtifactDetailsNull() {
		ArtifactDetails testObj = new ArtifactDetails();
		
		assertNull(testObj.getActionTime());
		assertNull(testObj.getEnvironment());
		assertNull(testObj.getOwner());
		assertNull(testObj.getRemark());
		assertNull(testObj.getStatus());
	}

}
