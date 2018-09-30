/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.applicationinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * ReleaseNumberTest is a test class for ReleaseNumber
 *
 * @see org.infy.idp.entities.jobs.applicationinfo.ReleaseNumber
 * 
 */
public class ReleaseNumberTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ReleaseNumberTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testReleaseNumberValues() throws Throwable {

		ReleaseNumber testedObject = new ReleaseNumber();
		List<String> releaseList = new ArrayList<>();
		testedObject.setReleaseNumbers(releaseList);

		assertEquals(releaseList, testedObject.getReleaseNumbers());
	}

	/**
	 * Test for method ReleaseNumber().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see ReleaseNumber#ReleaseNumber()
	 * 
	 * 
	 */
	@Test
	public void testReleaseNumberNull() throws Throwable {
		ReleaseNumber testedObject = new ReleaseNumber();

		assertNull(testedObject.getReleaseNumbers());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java EnvironmentOwnerDetailTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.applicationInfo.EnvironmentOwnerDetailTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return EnvironmentOwnerDetail.class;
	}
}
