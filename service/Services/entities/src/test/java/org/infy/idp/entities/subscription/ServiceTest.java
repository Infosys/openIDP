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

import org.junit.Test;

public class ServiceTest {
	@Test
	public void testServiceValues() {
		Service testObj = new Service();

		testObj.setOrgName("orgName");
		ServiceIdentity serviceIdentity = new ServiceIdentity();
		testObj.setServiceIdentity(serviceIdentity);

		assertEquals(serviceIdentity, testObj.getServiceIdentity());

		assertEquals("orgName", testObj.getOrgName());
	}

	@Test
	public void testServiceNull() {
		Service testObj = new Service();

		assertNull(testObj.getExpiryDate());
		assertNull(testObj.getServiceIdentity());
		assertNull(testObj.getOrgName());

	}
}
