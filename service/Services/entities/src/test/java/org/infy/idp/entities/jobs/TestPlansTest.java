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

public class TestPlansTest {

	@Test
	public void testTestPlansValues() {
		TestPlans testObj = new TestPlans();
		testObj.setTestPlanId(10);
		testObj.setTestPlanName("testPlanName");

		assertEquals("testPlanName", testObj.getTestPlanName());
		assertEquals((Integer) 10, testObj.getTestPlanId());
	}

	@Test
	public void testTestPlansNull() {
		TestPlans testObj = new TestPlans();

		assertNull(testObj.getTestPlanName());
		assertNull(testObj.getTestPlanId());
	}

}
