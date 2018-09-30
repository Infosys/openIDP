/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.entities.licencekeymanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LicenseTest {

	@Test
	public void testLicenseTestValues() {
		License testObj = new License();
		Date expDate = new Date(new java.util.Date().getTime());
		testObj.setExpirydate(expDate);
		testObj.setLicenseType("licenseType");
		List<ServiceType> serviceList = new ArrayList<>();
		testObj.setServiceList(serviceList);

		assertEquals("licenseType", testObj.getLicenseType());
		assertEquals(expDate, testObj.getExpirydate());
		assertEquals(serviceList, testObj.getServiceList());
		assertNotNull(testObj.getProperty());
	}

	@Test
	public void testLicenseTestNull() {
		License testObj = new License();

		assertNull(testObj.getLicenseType());
		assertNull(testObj.getExpirydate());
		assertNull(testObj.getServiceList());
	}

}
