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

public class OrgInfoTest {
	@Test
	public void testOrgInfoValues() {
		OrgInfo testObj = new OrgInfo();

		testObj.setDomain("domain");
		testObj.setOrgAdmin("orgAdmin");
		testObj.setOrgName("orgName");

		assertEquals("domain", testObj.getDomain());
		assertEquals("orgAdmin", testObj.getOrgAdmin());
		assertEquals("orgName", testObj.getOrgName());
	}

	@Test
	public void testOrgInfoNull() {
		OrgInfo testObj = new OrgInfo();

		assertNull(testObj.getDomain());
		assertNull(testObj.getOrgAdmin());
		assertNull(testObj.getOrgName());

	}
}
