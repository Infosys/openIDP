/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.releasemanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.jobs.basicinfo.AdditionalMailRecipients;
import org.junit.Test;

public class ReleaseTest {

	@Test
	public void testReleaseValues() {
		Release testObj = new Release();
		testObj.setReleaseNumber("releaseNumber");
		testObj.setVstsReleaseName("vstsReleaseName");
		testObj.setActualStartDate("actualStartDate");
		testObj.setActualEndDate("actualEndDate");
		testObj.setExpectedStartDate("expectedStartDate");
		testObj.setExpectedEndDate("expectedEndDate");
		testObj.setClosed("closed");
		testObj.setRemarks("remarks");
		List<String> branch = new ArrayList<String>();
		branch.add("Branch1");
		testObj.setBranchList(branch);
		AdditionalMailRecipients amRecipient = new AdditionalMailRecipients();
		testObj.setAdditionalMailRecipients(amRecipient);

		assertEquals("releaseNumber", testObj.getReleaseNumber());
		assertEquals("vstsReleaseName", testObj.getVstsReleaseName());
		assertEquals("actualStartDate", testObj.getActualStartDate());
		assertEquals("actualEndDate", testObj.getActualEndDate());
		assertEquals("expectedStartDate", testObj.getExpectedStartDate());
		assertEquals("expectedEndDate", testObj.getExpectedEndDate());
		assertEquals("closed", testObj.getClosed());
		assertEquals("remarks", testObj.getRemarks());
		assertEquals(branch, testObj.getBranchList());
		assertEquals(amRecipient, testObj.getAdditionalMailRecipients());
	}

	@Test
	public void testReleaseNull() {
		Release testObj = new Release();

		assertNull(testObj.getReleaseNumber());
		assertNull(testObj.getVstsReleaseName());
		assertNull(testObj.getActualStartDate());
		assertNull(testObj.getActualEndDate());
		assertNull(testObj.getExpectedStartDate());
		assertNull(testObj.getExpectedEndDate());
		assertNull(testObj.getClosed());
		assertNull(testObj.getRemarks());
		assertNull(testObj.getBranchList());
		assertNull(testObj.getAdditionalMailRecipients());
	}

	@Test
	public void testGetAdditionalMailRecipientsValues() {
		Release testObj = new Release();
		AdditionalMailRecipients amRecipient = new AdditionalMailRecipients();
		amRecipient.setApplicationTeam("applicationTeam");
		amRecipient.setEmailIds("emailIds");

		assertEquals("applicationTeam", amRecipient.getApplicationTeam());
		assertEquals("emailIds", amRecipient.getEmailIds());
		assertNull(testObj.getAdditionalMailRecipients());

	}
}
