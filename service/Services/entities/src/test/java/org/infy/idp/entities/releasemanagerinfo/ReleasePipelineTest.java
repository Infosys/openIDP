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

public class ReleasePipelineTest {

	@Test
	public void testReleasePipelineValues() {
		ReleasePipeline testObj = new ReleasePipeline();
		testObj.setPipelineName("pipelineName");
		testObj.setPipelineId(0);
		testObj.setScmType("scmType");
		List<String> scmList = new ArrayList<String>();
		scmList.add("scm1");
		List<Release> relList = new ArrayList<>();
		Release e = new Release();
		relList.add(e);
		testObj.setRelease(relList);
		testObj.setScmBranches(scmList);

		assertEquals("pipelineName", testObj.getPipelineName());
		assertEquals("scmType", testObj.getScmType());
		assertEquals(0, testObj.getPipelineId());
		assertEquals(relList, testObj.getRelease());
		assertEquals(scmList, testObj.getScmBranches());
	}

	@Test
	public void testReleasePipelineNull() {
		ReleasePipeline testObj = new ReleasePipeline();

		assertNull(testObj.getPipelineName());
		assertNull(testObj.getScmType());
		assertNull(testObj.getRelease());
		assertNull(testObj.getScmBranches());
	}

}
