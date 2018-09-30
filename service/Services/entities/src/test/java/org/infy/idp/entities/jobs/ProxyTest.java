/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.infy.idp.entities.jobs.common.Proxy;
import org.junit.Test;

public class ProxyTest {

	@Test
	public void testProxyValues() {
		Proxy testObj = new Proxy();
		testObj.setEnabled("enabled");
		testObj.setHost("host");
		testObj.setPassword("password");
		testObj.setPort("port");
		testObj.setUsername("username");

		assertEquals("enabled", testObj.getEnabled());
		assertEquals("host", testObj.getHost());
		assertEquals("password", testObj.getPassword());
		assertEquals("port", testObj.getPort());
		assertEquals("username", testObj.getUsername());
	}

	@Test
	public void testProxyNull() {
		Proxy testObj = new Proxy();

		assertNull(testObj.getEnabled());
		assertNull(testObj.getHost());
		assertNull(testObj.getPassword());
		assertNull(testObj.getPort());
		assertNull(testObj.getUsername());
	}

}
