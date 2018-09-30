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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SubApplicationTest {

	@Test
	public void testSubApplicationValues() {
		SubApplication testObj = new SubApplication();
		List<String> subApps = new ArrayList<String>();
		subApps.add("subApp1");
		testObj.setSubApps(subApps);

		assertEquals(subApps, testObj.getSubApps());
	}

	@Test
	public void testSubApplicationNull() {
		SubApplication testObj = new SubApplication();

		assertNull(testObj.getSubApps());
	}

	@Test
	public void testSubApplicationNullValues() {
		SubApplication testObj = new SubApplication();
		testObj.setSubApps((List<String>) null);

		assertNull(testObj.getSubApps());
	}

}
