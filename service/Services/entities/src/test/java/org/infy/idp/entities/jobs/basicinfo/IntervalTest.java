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

import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.junit.Test;

public class IntervalTest {

	@Test
	public void testIntervalValues() {
		Interval testObj = new Interval();
		testObj.setHour("hour");
		testObj.setMinute("minute");
		testObj.setTime("time");
		testObj.setType("type");
		List<String> date = new ArrayList<String>();
		date.add("date");
		testObj.setDate(date);
		List<String> week = new ArrayList<>();
		week.add("week");
		testObj.setWeek(week);
		TriggerParameters details = new TriggerParameters();
		testObj.setDetails(details);

		assertEquals("hour", testObj.getHour());
		assertEquals("minute", testObj.getMinute());
		assertEquals("time", testObj.getTime());
		assertEquals("type", testObj.getType());
		assertEquals(date, testObj.getDate());
		assertEquals(week, testObj.getWeek());
		assertEquals(details, testObj.getDetails());
	}

	@Test
	public void testIntervalNull() {
		Interval testObj = new Interval();

		assertNull(testObj.getHour());
		assertNull(testObj.getMinute());
		assertNull(testObj.getTime());
		assertNull(testObj.getType());
		assertNull(testObj.getDate());
		assertNull(testObj.getWeek());
		assertNull(testObj.getDetails());
	}
}
