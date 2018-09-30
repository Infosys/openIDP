/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.buildinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.infy.entities.triggerinputs.SubModule;
import org.junit.Test;

public class SubModuleTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public SubModuleTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testSubModule() throws Throwable {
		SubModule sub = new SubModule();
		sub.setDefaultModule("ReservationSystem");
		sub.setModuleName("ReservationSystem");

		assertEquals("ReservationSystem", sub.getDefaultModule());
		assertEquals("ReservationSystem", sub.getModuleName());

	}

	@Test
	public void testNullSubModule() throws Throwable {
		SubModule sub = new SubModule();
		sub.setDefaultModule(null);
		sub.setModuleName(null);
		assertNull(sub.getDefaultModule());
		assertNull(sub.getModuleName());
	}

	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.buildInfo.SubModuleTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */

	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return SubModule.class;
	}

}
