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

public class PathSequenceTest {

	@Test
	public void testPathSequenceValues() {
		PathSequence testObj = new PathSequence();
		List<String> pathList = new ArrayList<String>();
		pathList.add("path");
		testObj.setPathList(pathList);

		assertEquals(pathList, testObj.getPathList());
	}

	@Test
	public void testPathSequenceNull() {
		PathSequence testObj = new PathSequence();

		assertNull(testObj.getPathList());
	}

}
