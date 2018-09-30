/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * WebSecurityConfigTest is a test class for WebSecurityConfig
 *
 * @see org.infy.idp.config.WebSecurityConfig
 *  
 */

@RunWith(MockitoJUnitRunner.class)
public class WebSecurityConfigTest  {

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	
	@InjectMocks
	private WebSecurityConfig webSecurityConfig;
	
	@Spy
	private RestfulRemoteAuthenticationProvider restfulRemoteAuthenticationProvider;
	
	public WebSecurityConfigTest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}



	/**
	 * Test for method WebSecurityConfig().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see WebSecurityConfig#WebSecurityConfig()
	 *  
	 * 
	 */
	@Test
	public void testWebSecurityConfig0() throws Throwable {
		WebSecurityConfig testedObject = new WebSecurityConfig();
		// No exception thrown
		
	}

	
		

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java WebSecurityConfigTest
	 * 
	 * @param args
	 *            command line arguments are not needed
	 *  
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.config.WebSecurityConfigTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class getTestedClass() {
		return WebSecurityConfig.class;
	}
}
