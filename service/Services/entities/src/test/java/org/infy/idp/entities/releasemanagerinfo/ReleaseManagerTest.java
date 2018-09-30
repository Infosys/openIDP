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

import org.junit.Test;

public class ReleaseManagerTest {

	@Test
	public void testReleaseManagerValues() {
		ReleaseManager testObj = new ReleaseManager();
		testObj.setApplicationName("applicationName");
		List<String> envList = new ArrayList<String>();
		List<String> accessEnvList = new ArrayList<>();
		envList.add("environment");
		accessEnvList.add("AccessedEnviroment");
		ReleasePipeline rpObj = new ReleasePipeline();
		List<ReleasePipeline> relList = new ArrayList<>();
		relList.add(rpObj);
		testObj.setAccessEnvironmentList(accessEnvList);
		testObj.setEnvironmentList(envList);
		testObj.setReleasePipelineInfo(rpObj);
		testObj.setReleasePipeline(relList);
		testObj.setApplicationId(0l);

		assertEquals("applicationName", testObj.getApplicationName());
		assertEquals(0l, testObj.getApplicationId());
		assertEquals(accessEnvList, testObj.getAccessEnvironmentList());
		assertEquals(envList, testObj.getEnvironmentList());
		assertEquals(rpObj, testObj.getReleasePipelineInfo());
		assertEquals(relList, testObj.getReleasePipeline());
	}

	@Test
	public void testReleaseManagerNull() {
		ReleaseManager testObj = new ReleaseManager();

		assertNull(testObj.getApplicationName());
		assertNull(testObj.getAccessEnvironmentList());
		assertNull(testObj.getEnvironmentList());
		assertNull(testObj.getReleasePipelineInfo());
		assertNull(testObj.getReleasePipeline());
	}
}
