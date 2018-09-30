/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.entities.licencekeymanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SubscriptionTest {

	@Test
	public void testLicenseTestValues() {
		Subscription testObj = new Subscription();
		
		List<Service> subscriptionTypes = new ArrayList<>();
		testObj.setSubscriptionTypes(subscriptionTypes);
		
		assertEquals(subscriptionTypes, testObj.getSubscriptionTypes());
	}

	@Test
	public void testLicenseTestNull() {
		Subscription testObj = new Subscription();
		assertNull(testObj.getSubscriptionTypes());
	}

}
