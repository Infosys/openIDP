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

public class ModuleTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public ModuleTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testModule() throws Throwable {
		Module module = new Module();
		module.setDefaultModule("ReservationSystem");
		module.setModuleName("ReservationSystem");
		assertEquals("ReservationSystem", module.getDefaultModule());
		assertEquals("ReservationSystem", module.getModuleName());
	}

	@Test
	public void testNullModule() throws Throwable {
		Module module = new Module();
		module.setDefaultModule(null);
		module.setModuleName(null);
		assertNull(module.getDefaultModule());
		assertNull(module.getModuleName());
	}

	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.Module");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */

	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return Module.class;
	}

}
