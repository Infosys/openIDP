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

public class ServiceIdentityTest {
	@Test
	public void testOrgInfoValues() {
		ServiceIdentity testObj = new ServiceIdentity();
		testObj.setLicenseId(1000000000L);
		testObj.setServiceName("serviceName");
		
		assertEquals(1000000000L, testObj.getLicenseId());
		assertEquals("serviceName", testObj.getServiceName());
	}

	@Test
	public void testOrgInfoNull() {
		ServiceIdentity testObj = new ServiceIdentity();

		assertEquals(0,testObj.getLicenseId());
		assertNull(testObj.getServiceName());

	}

}
