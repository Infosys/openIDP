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

public class OrganisationInfoTest {

	@Test
	public void testOrganisationInfoValues() {
		OrganisationInfo testObj = new OrganisationInfo();
		testObj.setDomain("domain");
		testObj.setLicenseExpiryDate("licenseExpiryDate");
		testObj.setLicenseServices("licenseServices");
		testObj.setMailBody("mailBody");
		testObj.setMethod("method");
		testObj.setOrgAdmin("orgAdmin");
		testObj.setOrgName("ORGNAME");
		testObj.setUserName("userName");
		testObj.setOrgId(10000000L);

		assertEquals("domain", testObj.getDomain());
		assertEquals("licenseExpiryDate", testObj.getLicenseExpiryDate());
		assertEquals("licenseServices", testObj.getLicenseServices());
		assertEquals("mailBody", testObj.getMailBody());
		assertEquals("method", testObj.getMethod());
		assertEquals("orgAdmin", testObj.getOrgAdmin());
		assertEquals("ORGNAME", testObj.getOrgName());
		assertEquals("userName", testObj.getUserName());
		assertEquals(10000000L, testObj.getOrgId());
	}

	@Test
	public void testOrganisationInfoNull() {
		OrganisationInfo testObj = new OrganisationInfo();

		assertNull(testObj.getDomain());
		assertNull(testObj.getLicenseExpiryDate());
		assertNull(testObj.getLicenseServices());
		assertNull(testObj.getMailBody());
		assertNull(testObj.getMethod());
		assertNull(testObj.getOrgAdmin());
		assertNull(testObj.getUserName());
	}

}
