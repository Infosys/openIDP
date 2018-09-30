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

import java.util.ArrayList;

import org.junit.Test;

public class BuildDeployEnvTest {

	/**
	 * Constructor for test class.
	 *
	 */
	public BuildDeployEnvTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testBuildDeployEnv() throws Throwable {
		BuildDeployEnv testObject = new BuildDeployEnv();
		testObject.setReleaseNumber("April2");
		ArrayList<String> env = new ArrayList<>();
		env.add("dev");
		env.add("prod");
		testObject.setEnv(env);
		assertEquals(env, testObject.getEnv());
		assertEquals("April2", testObject.getReleaseNumber());
	}

	@Test
	public void testNullBuildDeployEnv() throws Throwable {
		BuildDeployEnv testObject = new BuildDeployEnv();
		testObject.setReleaseNumber(null);
		ArrayList<String> env = null;
		testObject.setEnv(env);
		assertNull(testObject.getEnv());
		assertNull(testObject.getReleaseNumber());

	}

	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.BuildDeployEnvTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */

	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return BuildDeployEnv.class;
	}

}
