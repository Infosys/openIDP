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

public class LicenseKeyTest {

	@Test
	public void testLicenseKeyValues() {
		LicenseKey testObj = new LicenseKey();
		Date date = new Date(new java.util.Date().getTime());
		Date expDate = new Date(new java.util.Date().getTime() + 1000);
		testObj.setExpiryDate(expDate);
		testObj.setDate(date);
		testObj.setLicenseId(100000000L);
		testObj.setOrgName("ORGNAME");
		testObj.setLicenseKey("licenseKey");
		testObj.setLicenseType("licenseType");

		assertEquals("licenseType", testObj.getLicenseType());
		assertEquals(expDate, testObj.getExpiryDate());
		assertEquals(date, testObj.getDate());
		assertEquals("ORGNAME", testObj.getOrgName());
		assertEquals("licenseKey", testObj.getLicenseKey());
		assertEquals(100000000L, testObj.getLicenseId());
		
	}

	@Test
	public void testLicenseKeyNull() {
		LicenseKey testObj = new LicenseKey();

		assertNull(testObj.getLicenseType());
		assertNull(testObj.getExpiryDate());
		assertNull(testObj.getDate());
		assertNull(testObj.getLicenseKey());
	}

}
