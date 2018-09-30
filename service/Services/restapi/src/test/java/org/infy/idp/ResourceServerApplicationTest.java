/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ServiceInstanceRestController.class)
@RunWith(SpringRunner.class)
public class ResourceServerApplicationTest {

	@Autowired
	private ServiceInstanceRestController rsaObj;

	@MockBean
	private DiscoveryClient discoveryClient;

	@Test
	public void testServiceInstancesByApplicationName0() throws Throwable {
		List<ServiceInstance> result = rsaObj.serviceInstancesByApplicationName("Str 1.2 #");
		assertNotNull(result);
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