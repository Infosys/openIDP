/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp;

import static org.mockito.Mockito.mock;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RequestFilterTest {

	@Autowired
	RequestFilter request;

	@Test
	public void testDestroy0() throws Throwable {
		request = new RequestFilter();
		request.destroy();
	}

	@Test
	public void testDoFilterSuccess() throws Throwable {
		request = new RequestFilter();
		ServletRequest req = mock(ServletRequest.class);
		ServletResponse res = mock(ServletResponse.class);
		FilterChain ch = mock(FilterChain.class);

		request.doFilter(req, res, ch);
	}

	@Test
	public void testInit0() throws Throwable {
		request = new RequestFilter();
		request.init((FilterConfig) null);
	}

	/**
	 * executes before test
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		/**
		 * Used to setup resource This method is called by JUnit before tests
		 */
	}

	/**
	 * executes after test
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		/**
		 * Used to clean up after the test. This method is called by JUnit after each of
		 * the tests have been completed.
		 */
	}

}