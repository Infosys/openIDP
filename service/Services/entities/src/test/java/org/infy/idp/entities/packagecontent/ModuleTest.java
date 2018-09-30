/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.packagecontent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Test;

public class ModuleTest {
	@Test
	public void testModulesValues() {
		Module testObj = new Module();
		testObj.setModuleName(new ArrayList<>());
		assertEquals(new ArrayList<>(), testObj.getModuleName());

	}

	@Test
	public void testModuleNull() {
		Module testObj = new Module();

		assertNull(testObj.getModuleName());

	}
}
