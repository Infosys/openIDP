/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.basicinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class EventTest {

	@Test
	public void testEventValues() {
		Event testobj = new Event();
		testobj.setHour("hour");
		testobj.setMinute("minute");
		testobj.setType("type");
		List<String> dateList = new ArrayList<>();
		List<String> weekList = new ArrayList<>();
		dateList.add("date");
		weekList.add("week");
		testobj.setDate(dateList);
		testobj.setWeek(weekList);

		assertEquals("hour", testobj.getHour());
		assertEquals("minute", testobj.getMinute());
		assertEquals("type", testobj.getType());
		assertEquals(dateList, testobj.getDate());
		assertEquals(weekList, testobj.getWeek());
	}

	@Test
	public void testEventNull() {
		Event testobj = new Event();

		assertNull(testobj.getHour());
		assertNull(testobj.getMinute());
		assertNull(testobj.getType());
		assertNull(testobj.getDate());
		assertNull(testobj.getWeek());
	}

}
