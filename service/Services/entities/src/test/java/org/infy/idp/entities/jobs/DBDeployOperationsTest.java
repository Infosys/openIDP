/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DBDeployOperationsTest {

	@Test
	public void testDBDeployOperationsValues() {
		DBDeployOperations testObj = new DBDeployOperations();
		List<String> operations = new ArrayList<String>();
		operations.add("op1");
		testObj.setOperations(operations);

		assertEquals(operations, testObj.getOperations());
	}

	@Test
	public void testDBDeployOperationsNullValues() {
		DBDeployOperations testObj = new DBDeployOperations();
		testObj.setOperations((List<String>) null);

		assertNull(testObj.getOperations());
	}

	@Test
	public void testDBDeployOperationsNull() {
		DBDeployOperations testObj = new DBDeployOperations();

		assertNull(testObj.getOperations());
	}

}
