/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.entities.models.org;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class OrgInfoTest {
	@Test
	public void testOrgInfoValues() {
		OrgInfo testObj = new OrgInfo();
		testObj.setDomain("domain");
		testObj.setOrgName("ORGNAME");
		testObj.setOrgId(1000000L);
		testObj.setOrgAdmin("orgAdmin");
		assertEquals("domain", testObj.getDomain());
		assertEquals("ORGNAME", testObj.getOrgName());
		assertEquals(1000000L, testObj.getOrgId());
		assertEquals("orgAdmin", testObj.getOrgAdmin());
	}

	@Test
	public void testOrgInfoNull() {
		OrgInfo testObj = new OrgInfo();
		assertNull(testObj.getOrgAdmin());
		assertNull(testObj.getDomain());
	}
}
