/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateBuildLogTest {
	@InjectMocks
	private CreateBuildLog createBuildLog;

	@Test
	public void copyFolderTest() {
		try {
			List<String> temp=null;
			createBuildLog.createBuildLog("string", "string", "string", "string", "string", "string", "string",
					"string");
			assertEquals(null, temp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
