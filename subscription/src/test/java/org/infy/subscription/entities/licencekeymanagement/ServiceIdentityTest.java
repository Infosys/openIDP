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

import org.junit.Test;

public class ServiceIdentityTest {

	@Test
	public void testServiceIdentityValues() {
		ServiceIdentity testObj = new ServiceIdentity();
		testObj.setLicenseId(100000000L);
		testObj.setServiceName("serviceName");
		assertEquals(100000000L, testObj.getLicenseId());
		assertEquals("serviceName", testObj.getServiceName());
	}

	@Test
	public void testServiceIdentityNull() {
		ServiceIdentity testObj = new ServiceIdentity();
		assertNull(testObj.getServiceName());
		
	}

}
