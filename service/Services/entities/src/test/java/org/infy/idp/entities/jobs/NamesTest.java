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

public class NamesTest {

	@Test
	public void testNamesValues() {
		Names testObj = new Names();
		List<String> names = new ArrayList<String>();
		names.add("abc");
		testObj.setNames(names);

		assertEquals(names, testObj.getNames());
	}

	@Test
	public void testNamesNullValues() {
		Names testObj = new Names();
		testObj.setNames((List<String>) null);

		assertNull(testObj.getNames());
	}

	@Test
	public void testNamesNull() {
		Names testObj = new Names();

		assertNull(testObj.getNames());
	}

}
