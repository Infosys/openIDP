/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CorsFilter.class)
@RunWith(SpringRunner.class)
public class CorsFilterTest {

	@Autowired
	private CorsFilter filterObj;

	@Test
	public void testDestroy0() throws Throwable {
		filterObj.destroy();
	}

	@Test
	public void testDoFilter_ifCondition() throws Throwable {
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		HttpServletResponse servletResponse = mock(HttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);

		when(servletRequest.getMethod()).thenReturn("OPTIONS");

		filterObj.doFilter(servletRequest, servletResponse, filterChain);

	}

	@Test
	public void testDoFilter_elseCondition() throws Throwable {

		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		HttpServletResponse servletResponse = mock(HttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);

		filterObj.doFilter(servletRequest, servletResponse, filterChain);

	}

	/**
	 * executes before test
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * executes after test
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}