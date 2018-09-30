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

import org.junit.Test;

public class ReleasesTest {

	@Test
	public void testReleasesValues() {
		Releases testObj = new Releases();
		testObj.setPipelineName("pipelineName");
		List<Release> rList = new ArrayList<Release>();
		Release relObj = new Release();
		rList.add(relObj);
		testObj.setRelease(rList);

		assertEquals("pipelineName", testObj.getPipelineName());
		assertEquals(rList, testObj.getRelease());
	}

	@Test
	public void testReleasesNull() {
		Releases testObj = new Releases();

		assertNull(testObj.getPipelineName());
		assertNull(testObj.getRelease());
	}

}
