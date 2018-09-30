/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.packagecontent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class PackageContentTest {
	@Test
	public void testPackageContentValues() {
		PackageContent testObj = new PackageContent();
		testObj.setArtifactName("artifactName");
		Module dotNetM = new Module();
		Module antM = new Module();
		testObj.setDotNet(dotNetM);
		testObj.setAnt(antM);

		assertEquals(dotNetM, testObj.getDotNet());
		assertEquals(antM, testObj.getAnt());
		assertEquals("artifactName", testObj.getArtifactName());

	}

	@Test
	public void testModuleNull() {
		PackageContent testObj = new PackageContent();
		assertNull(testObj.getArtifactName());
		assertNull(testObj.getAnt());
		assertNull(testObj.getDotNet());

	}
}
