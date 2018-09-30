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

public class TriggerIntervalTest {

	@Test
	public void testTriggerIntervalValues() {
		TriggerInterval testObj = new TriggerInterval();
		List<Interval> interval = new ArrayList<>();
		Interval obj = new Interval();
		interval.add(obj);
		testObj.setInterval(interval);

		assertEquals(interval, testObj.getInterval());
	}

	@Test
	public void testTriggerIntervalNull() {
		TriggerInterval testObj = new TriggerInterval();

		assertNull(testObj.getInterval());
	}

}
