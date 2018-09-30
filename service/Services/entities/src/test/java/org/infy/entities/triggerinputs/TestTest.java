/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/


package org.infy.entities.triggerinputs;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/**
 * TestTest is a test class for Test
 *
 * @see org.infy.entities.triggerinputs.Test
 *  
 */
public class TestTest {

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	public TestTest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}

	
	/**
	 * 
	 * Test for get and set
	 * 
	 */
	@Test
	public void testGetSet() throws Throwable{
		
		org.infy.entities.triggerinputs.Test testObject= new org.infy.entities.triggerinputs.Test();
		List<String> testEnv = null;
		testObject.setTestEnv(testEnv);
		assertEquals(null,testObject.getTestEnv());
		
	}
	
	
	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TestTest
	 * 
	 * @param args
	 *            command line arguments are not needed
	 *  
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.triggerinputs.TestTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class getTestedClass() {
		return Test.class;
	}
}
