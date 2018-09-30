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

public class ArtifactoryFileTest {

	public ArtifactoryFileTest() {

	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArtifactFileValues() {
		ArtifactoryFile testObj = new ArtifactoryFile();
		testObj.setLastModified("date-string");
		testObj.setUri("uri-of-artifactory");

		assertEquals("date-string", testObj.getLastModified());
		assertEquals("uri-of-artifactory", testObj.getUri());
	}

	@Test
	public void testArtifactNull() {
		ArtifactoryFile testObj = new ArtifactoryFile();
		assertNull(testObj.getLastModified());
		assertNull(testObj.getUri());
	}

}
