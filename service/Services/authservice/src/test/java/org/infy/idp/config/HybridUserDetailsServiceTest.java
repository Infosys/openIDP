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
import org.springframework.security.core.userdetails.UserDetails;


/**
 * HybridUserDetailsServiceTest is a test class for HybridUserDetailsService
 *
 * @see org.infy.idp.config.HybridUserDetailsService
 *  
 */

@RunWith(MockitoJUnitRunner.class)
public class HybridUserDetailsServiceTest {

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	
	@Spy
	@InjectMocks
	private HybridUserDetailsService hybridUserDetailsService;
	
	@Spy
	@InjectMocks
	private UserService userService;
	
	public HybridUserDetailsServiceTest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}


	/**
	 * Test for method loadUserByUsername(java.lang.String).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see HybridUserDetailsService#loadUserByUsername(java.lang.String)
	 *  
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testLoadUserByUsername0() throws Throwable {
		HybridUserDetailsService testedObject = new HybridUserDetailsService();
		UserDetails result = testedObject.loadUserByUsername("Str 1.2 #");

	}

	/**
	 * Test for method loadUserByUsername(java.lang.String).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see HybridUserDetailsService#loadUserByUsername(java.lang.String)
	 *  
	 * 
	 */
	@Test(expected = org.springframework.security.core.userdetails.UsernameNotFoundException.class)
	public void testLoadUserByUsername1() throws Throwable {
		HybridUserDetailsService testedObject = new HybridUserDetailsService();
		UserDetails result = testedObject.loadUserByUsername("");

	}



	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java HybridUserDetailsServiceTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.config.HybridUserDetailsServiceTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class getTestedClass() {
		return HybridUserDetailsService.class;
	}
}
