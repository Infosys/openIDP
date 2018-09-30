/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestSuitsTest {

	@Test
	public void testTestSuitsValues() {
		TestSuits testObj = new TestSuits();
		testObj.setTestSuitId(11);
		testObj.setTestSuitName("testSuitName");
		testObj.setTestSuitParent("testSuitParent");

		assertEquals((Integer) 11, testObj.getTestSuitId());
		assertEquals("testSuitName", testObj.getTestSuitName());
		assertEquals("testSuitParent", testObj.getTestSuitParent());
	}

	@Test
	public void testTestSuitsNull() {
		TestSuits testObj = new TestSuits();

		assertNull(testObj.getTestSuitId());
		assertNull(testObj.getTestSuitName());
		assertNull(testObj.getTestSuitParent());
	}

}
