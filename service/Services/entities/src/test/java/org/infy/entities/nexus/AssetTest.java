/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.nexus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.infy.idp.entities.nexus.Asset;
import org.junit.Test;

public class AssetTest {
	public AssetTest() {

	}

	/**
	 * 
	 * Test for get and set
	 * 
	 */
	@Test
	public void testAssetsValues() throws Throwable {

		org.infy.idp.entities.nexus.Asset testObject = new org.infy.idp.entities.nexus.Asset();
		testObject.setRepository("nexus");
		testObject.setDownloadUrl("http://url");
		testObject.setFormat("format");
		testObject.setId("123");
		testObject.setPath("D:/");
		assertEquals("nexus", testObject.getRepository());
		assertEquals("http://url", testObject.getDownloadUrl());
		assertEquals("format", testObject.getFormat());
		assertEquals("123", testObject.getId());
		assertEquals("D:/", testObject.getPath());

	}

	/**
	 * 
	 * Test for get and set
	 * 
	 */
	@Test
	public void testAssetsNull() throws Throwable {

		org.infy.idp.entities.nexus.Asset testObject = new org.infy.idp.entities.nexus.Asset();

		assertNull(testObject.getRepository());
		assertNull(testObject.getDownloadUrl());
		assertNull(testObject.getFormat());
		assertNull(testObject.getId());
		assertNull(testObject.getPath());

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TestTest
	 * 
	 * @param args command line arguments are not needed
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.nexus.AssetTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */
	public Class<Asset> getTestedClass() {
		return Asset.class;
	}

}
