/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.releasemanagerinfo;

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
		testObj.setStatus("status");
		testObj.setRemarks("remarks");
		List<String> branch = new ArrayList<String>();
		branch.add("Branch1");
		testObj.setBranchList(branch);
		AdditionalMailRecipients amRecipient = new AdditionalMailRecipients();
		testObj.setAdditionalMailRecipients(amRecipient);
		testObj.setHistoryRemarks("historyRemarks");
		List<PathSequence> envPathList = new ArrayList<>();
		PathSequence psObj = new PathSequence();
		envPathList.add(psObj);
		testObj.setEnvPathList(envPathList);

		assertEquals("releaseNumber", testObj.getReleaseNumber());
		assertEquals("vstsReleaseName", testObj.getVstsReleaseName());
		assertEquals("actualStartDate", testObj.getActualStartDate());
		assertEquals("actualEndDate", testObj.getActualEndDate());
		assertEquals("expectedStartDate", testObj.getExpectedStartDate());
		assertEquals("expectedEndDate", testObj.getExpectedEndDate());
		assertEquals("status", testObj.getStatus());
		assertEquals("remarks", testObj.getRemarks());
		assertEquals("historyRemarks", testObj.getHistoryRemarks());
		assertEquals(branch, testObj.getBranchList());
		assertEquals(amRecipient, testObj.getAdditionalMailRecipients());
		assertEquals(envPathList, testObj.getEnvPathList());

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
		assertNull(testObj.getStatus());
		assertNull(testObj.getRemarks());
		assertNull(testObj.getBranchList());
		assertNull(testObj.getAdditionalMailRecipients());
		assertNull(testObj.getEnvPathList());
		assertNull(testObj.getHistoryRemarks());
	}

}
