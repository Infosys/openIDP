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

import java.sql.Date;

import org.junit.Test;

public class ServiceTest {

	@Test
	public void testServiceValues() {
		Service testObj = new Service();
		Date expDate = new Date(new java.util.Date().getTime());
		testObj.setExpiryDate(expDate);
		testObj.setOrgName("ORGNAME");
		ServiceIdentity serviceIdentity = new ServiceIdentity();
		testObj.setServiceIdentity(serviceIdentity);
		assertEquals(expDate, testObj.getExpiryDate());
		assertEquals("ORGNAME", testObj.getOrgName());
		assertEquals(serviceIdentity, testObj.getServiceIdentity());
	}

	@Test
	public void testServiceNull() {
		Service testObj = new Service();

		assertNull(testObj.getExpiryDate());
		assertNull(testObj.getServiceIdentity());
	}

}
