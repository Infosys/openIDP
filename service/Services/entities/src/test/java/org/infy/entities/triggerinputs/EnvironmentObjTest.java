/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class EnvironmentObjTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public EnvironmentObjTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testEnvObj() throws Throwable {
		EnvironmentObj testObject = new EnvironmentObj();
		testObject.setBizCheck("checked");
		testObject.setEnvName("dev");
		assertEquals("checked", testObject.getBizCheck());
		assertEquals("dev", testObject.getEnvName());
	}

	@Test
	public void testNullEnvObj() throws Throwable {
		EnvironmentObj testObject = new EnvironmentObj();
		testObject.setBizCheck(null);
		testObject.setEnvName(null);
		assertNull(testObject.getBizCheck());
		assertNull(testObject.getEnvName());
	}

	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.EnvironmentObjTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */

	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return EnvironmentObj.class;
	}

}
