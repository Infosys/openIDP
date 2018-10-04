/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CopyFolderUtilityTest {
	@InjectMocks
	private CopyFolderUtility copyFolderUtility;

	@Test
	public void copyFolderTest() {
		try {
			File file = new File("test");
			File file1 = new File("test");
			Boolean temp=copyFolderUtility.copyFolder(file, file1, "string", "string", "string");
			assertEquals(false,temp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
