/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.subscription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SubscriptionTest {
	@Test
	public void testSubscriptionValues() {
		Subscription testObj = new Subscription();
		List<Service> listService = new ArrayList<>();
		Service se = new Service();
		listService.add(se);
		testObj.setService(listService);

		assertEquals(listService, testObj.getService());
	}

	@Test
	public void testSubscriptionNull() {
		Subscription testObj = new Subscription();
		assertNull(testObj.getService());

	}
}
