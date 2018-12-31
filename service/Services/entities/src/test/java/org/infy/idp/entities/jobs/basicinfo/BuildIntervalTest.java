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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * BuildIntervalTest is a test class for BuildInterval
 *
 * @see org.infy.idp.entities.jobs.basicinfo.BuildInterval
 * 
 */
public class BuildIntervalTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public BuildIntervalTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method BuildInterval().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildInterval#BuildInterval()
	 * 
	 * 
	 */
	@Test
	public void testBuildInterval0() throws Throwable {
		BuildInterval testedObject = new BuildInterval();
		testedObject.setPollSCM("pollSCM12");
		testedObject.setBuildInterval("buildIntrvl12");
		testedObject.setBuildIntervalValue("buildIntervalValue11");
		testedObject.setBuildAtSpecificInterval("buildAtSpecificInterval");
		testedObject.setProjectKey("projectKey");
		Event event = new Event();
		List<Event> eventList = new ArrayList<>();
		eventList.add(event);
		testedObject.setEvent(eventList);
		testedObject.setAppServer("appServer");
		testedObject.setBuildInterval("buildInterval");
		testedObject.setClientID("clientID");
		testedObject.setLang("lang");
		testedObject.setSolmanpassword("solmanpassword");
		testedObject.setSolmanuserName("solmanuserName");
//		testedObject.setPollALM("pollALM");
//		testedObject.setAlmTool("almTool");
		testedObject.setSystemNumber("systemNumber");
//		testedObject.setUserStory("userStory");

		assertEquals("appServer", testedObject.getAppServer());
		assertEquals("buildInterval", testedObject.getBuildInterval());
		assertEquals("clientID", testedObject.getClientID());
		assertEquals("lang", testedObject.getLang());
		assertEquals("solmanpassword", testedObject.getSolmanpassword());
		assertEquals("solmanuserName", testedObject.getSolmanuserName());
		assertEquals("pollSCM12", testedObject.getPollSCM());
		assertEquals("buildIntrvl12", testedObject.getBuildInterval());
		assertEquals("buildIntervalValue11", testedObject.getBuildIntervalValue());
		assertEquals("buildAtSpecificInterval", testedObject.getBuildAtSpecificInterval());
		assertEquals("projectKey", testedObject.getProjectKey());
		assertEquals(eventList, testedObject.getEvent());
//		assertEquals("pollALM", testedObject.getPollALM());
//		assertEquals("almTool", testedObject.getAlmTool());
		assertEquals("systemNumber", testedObject.getSystemNumber());
//		assertEquals("userStory", testedObject.getUserStory());
	}

	/**
	 * Test for method BuildInterval().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildInterval#BuildInterval()
	 * 
	 * 
	 */
	@Test
	public void testBuildIntervalValues2() throws Throwable {
		BuildInterval testedObject = new BuildInterval();
		testedObject.setPollSCM("pollSCM0");
		testedObject.setBuildInterval("buildIntrvl0");
		testedObject.setBuildIntervalValue((String) null);
		assertEquals("pollSCM0", testedObject.getPollSCM());
		assertEquals("buildIntrvl0", testedObject.getBuildInterval());
		assertEquals(null, testedObject.getBuildIntervalValue());
	}

	/**
	 * Test for method BuildInterval().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see BuildInterval#BuildInterval()
	 * 
	 * 
	 */
	@Test
	public void testBuildIntervalNull() throws Throwable {
		BuildInterval testedObject = new BuildInterval();

		assertNull(testedObject.getPollSCM());
		assertNull(testedObject.getBuildInterval());
		assertNull(testedObject.getBuildIntervalValue());
		assertNull(testedObject.getBuildAtSpecificInterval());
		assertNull(testedObject.getProjectKey());
		assertNull(testedObject.getEvent());
//		assertNull(testedObject.getPollALM());
//		assertNull(testedObject.getAlmTool());
		assertNull(testedObject.getSystemNumber());
//		assertNull(testedObject.getUserStory());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java BuildIntervalTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.basicInfo.BuildIntervalTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return BuildInterval.class;
	}
}
