/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

/**
 * CorsFilterTest is a test class for CorsFilter
 *
 * @see org.infy.idp.config.CorsFilter
 *  
 */

public class CorsFilterTest{

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	
	
	
	public CorsFilterTest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method destroy().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see CorsFilter#destroy()
	 *  
	 * 
	 */
	@Test
	public void testDestroy0() throws Throwable {
		CorsFilter testedObject = new CorsFilter();
		testedObject.destroy();
		// No exception thrown
		
	}

	
	/**
	 * Test for method
	 * doFilter(javax.servlet.ServletRequest,javax.servlet.ServletResponse,javax
	 * .servlet.FilterChain).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see CorsFilter#doFilter(javax.servlet.ServletRequest,javax.servlet.ServletResponse,javax.servlet.FilterChain)
	 * @author Infosys
	 * 
	 */
	@Test
	public void testDoFilter_ifCondition() throws Throwable {
		CorsFilter testedObject = new CorsFilter();
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		HttpServletResponse servletResponse = mock(HttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);
		
		when(servletRequest.getMethod()).thenReturn("OPTIONS");
		
		testedObject.doFilter(servletRequest, servletResponse, filterChain);

	}
	
	/**
	 * Test for method
	 * doFilter(javax.servlet.ServletRequest,javax.servlet.ServletResponse,javax
	 * .servlet.FilterChain).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see CorsFilter#doFilter(javax.servlet.ServletRequest,javax.servlet.ServletResponse,javax.servlet.FilterChain)
	 * @author Infosys
	 * 
	 */
	@Test
	public void testDoFilter_elseCondition() throws Throwable {
		CorsFilter testedObject = new CorsFilter();
				
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		HttpServletResponse servletResponse = mock(HttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);
		
		testedObject.doFilter(servletRequest, servletResponse, filterChain);

		
	}
	


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java CorsFilterTest
	 * 
	 * @param args
	 *            command line arguments are not needed
	 *  
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.config.CorsFilterTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class getTestedClass() {
		return CorsFilter.class;
	}
}
