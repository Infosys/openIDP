/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.basicinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * BasicInfoTest is a test class for BasicInfo
 *
 * @see org.infy.idp.entities.jobs.basicinfo.BasicInfo
 * 
 */
public class BasicInfoTest  {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public BasicInfoTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method BasicInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BasicInfo#BasicInfo()
	 * 
	 * 
	 */
	@Test
	public void testBasicInfoValues() throws Throwable {
		BasicInfo testedObject = new BasicInfo();
		testedObject.setGroupName("groupName21");
		testedObject.setGroupId("groupId21");
		testedObject.setApplicationName("applicationName21");
		testedObject.setPipelineName("pipelineName21");
		testedObject.setBuildServerOS("buildServerOS21");
		testedObject.setEngine("engine21");
		BuildInterval buildInterval = new BuildInterval();
		testedObject.setBuildInterval(buildInterval);
		AdditionalMailRecipients additionalMailRecipients = new AdditionalMailRecipients();
		testedObject.setAdditionalMailRecipients(additionalMailRecipients);
		testedObject.setPipelineStatus("pipelineStatus");
		TriggerInterval tiObj = new TriggerInterval();
		testedObject.setCustomTriggerInterval(tiObj);
		testedObject.setUserName("userName");
		testedObject.setPlatform("platform");
//		testedObject.setJiraProjectKey("JiraKey");

		assertEquals(buildInterval, testedObject.getBuildInterval());
		assertEquals(additionalMailRecipients, testedObject.getAdditionalMailRecipients());
		assertEquals("applicationName21", testedObject.getApplicationName());
		assertEquals("groupName21", testedObject.getGroupName());
		assertEquals("engine21", testedObject.getEngine());
		assertEquals("pipelineName21", testedObject.getPipelineName());
		assertEquals("groupId21", testedObject.getGroupId());
		assertEquals("buildServerOS21", testedObject.getBuildServerOS());
		assertEquals("pipelineStatus", testedObject.getPipelineStatus());
		assertEquals(tiObj, testedObject.getCustomTriggerInterval());
		assertEquals("userName", testedObject.getUserName());
		assertEquals("platform", testedObject.getPlatform());
//		assertEquals("JiraKey", testedObject.getJiraProjectKey());
	}

	/**
	 * Test for method BasicInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BasicInfo#BasicInfo()
	 * 
	 * 
	 */
	@Test
	public void testBasicInfoValues2() throws Throwable {
		BasicInfo testedObject = new BasicInfo();
		testedObject.setGroupName("groupName0");
		testedObject.setGroupId("groupId0");
		testedObject.setApplicationName("applicationName0");
		testedObject.setPipelineName("pipelineName0");
		testedObject.setBuildServerOS("buildServerOS0");
		testedObject.setEngine("engine0");
		testedObject.setBuildInterval((BuildInterval) null);
		testedObject.setAdditionalMailRecipients((AdditionalMailRecipients) null);
		testedObject.setPipelineStatus("pipelineStatus");
		testedObject.setCustomTriggerInterval((TriggerInterval) null);

		assertNull(testedObject.getBuildInterval());
		assertNull(testedObject.getAdditionalMailRecipients());
		assertEquals("applicationName0", testedObject.getApplicationName());
		assertEquals("groupName0", testedObject.getGroupName());
		assertEquals("engine0", testedObject.getEngine());
		assertEquals("pipelineName0", testedObject.getPipelineName());
		assertEquals("groupId0", testedObject.getGroupId());
		assertEquals("buildServerOS0", testedObject.getBuildServerOS());
		assertEquals("pipelineStatus", testedObject.getPipelineStatus());
		assertNull(testedObject.getCustomTriggerInterval());

	}

	/**
	 * Test for method BasicInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BasicInfo#BasicInfo()
	 * 
	 * 
	 */
	@Test
	public void testBasicInfoNull() throws Throwable {
		BasicInfo testedObject = new BasicInfo();
		assertNull(testedObject.getBuildInterval());
		assertNull(testedObject.getAdditionalMailRecipients());
		assertNull(testedObject.getApplicationName());
		assertNull(testedObject.getGroupName());
		assertNull(testedObject.getEngine());
		assertNull(testedObject.getPipelineName());
		assertNull(testedObject.getGroupId());
		assertNull(testedObject.getBuildServerOS());
		assertNull(testedObject.getPipelineStatus());
		assertNull(testedObject.getCustomTriggerInterval());
//		assertNull(testedObject.getJiraProjectKey());
	}

	/**
	 * Test for method getAdditionalMailRecipients().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BasicInfo#getAdditionalMailRecipients()
	 * 
	 * 
	 */
	@Test
	public void testGetAdditionalMailRecipientsNull() throws Throwable {
		BasicInfo testedObject = new BasicInfo();
		AdditionalMailRecipients result = testedObject.getAdditionalMailRecipients();
		assertNull(result);
	}

	/**
	 * Test for method getBuildInterval().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BasicInfo#getBuildInterval()
	 * 
	 * 
	 */
	@Test
	public void testGetBuildIntervalNull() throws Throwable {
		BasicInfo testedObject = new BasicInfo();
		BuildInterval result = testedObject.getBuildInterval();
		assertNull(result);
	}

	@Test
	public void testGetCustomTriggerIntervalNull() throws Throwable {
		BasicInfo testedObject = new BasicInfo();
		TriggerInterval result = testedObject.getCustomTriggerInterval();
		assertNull(result);
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java BasicInfoTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.basicInfo.BasicInfoTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<BasicInfo> getTestedClass() {
		return BasicInfo.class;
	}
}
