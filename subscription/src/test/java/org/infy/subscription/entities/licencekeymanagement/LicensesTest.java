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

public class LicensesTest {

	@Test
	public void testLicensesValues() {
		Licenses testObj = new Licenses();

		List<License> licenseList = new ArrayList<>();
		License lincense = new License();
		licenseList.add(lincense);
		testObj.setLicenses(licenseList);

		assertEquals(licenseList, testObj.getLicenses());

	}

	@Test
	public void testLicensesNull() {
		Licenses testObj = new Licenses();
		assertNull(testObj.getLicenses());
	}

}
