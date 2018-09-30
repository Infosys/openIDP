/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;

@SpringBootTest(classes=SwaggerConfig.class)
@RunWith(SpringRunner.class)
public class SwaggerConfigTest {

	@Autowired
	private SwaggerConfig swaggerConfig; 
	
	@Test
	public void testApi0() throws Throwable {
		Docket result = swaggerConfig.api();
		assertNotNull(result); 
		assertNotNull(result.getDocumentationType()); 
		assertEquals("swagger:2.0", result.getDocumentationType().toString()); 
		assertEquals("default", result.getGroupName()); 
		assertEquals(true, result.isEnabled()); 
	}


	@Test
	public void testOauth0() throws Throwable {
		SecurityScheme result = swaggerConfig.oauth();
		assertNotNull(result); 
		assertEquals("oauth2", result.getName()); 
		assertEquals("oauth2", result.getType()); 
		assertNotNull(result.getVendorExtensions()); 
		assertEquals(0, result.getVendorExtensions().size()); 
	}


	@Test
	public void testSecurityInfo0() throws Throwable {
		SecurityConfiguration result = swaggerConfig.securityInfo();
		assertNotNull(result); 
		assertNotNull(result.getClientSecret()); 
		assertEquals("realm", result.getRealm()); 
		assertNotNull(result.getClientId()); 
		assertEquals("", result.getApiKeyName()); 
		assertEquals("", result.getApiKey()); 
		assertEquals("swagger", result.getAppName()); 
		assertEquals("header", result.getApiKeyVehicle()); 
	}


	/**
	 * executes before test
	 * @throws Exception
	 */	
	@Before
	public void setUp() throws Exception {
		/**
		 * Used to setup resource
		 * This method is called by JUnit before tests
		 */
	}

	/**
	 * executes after test
	 * @throws Exception
	 */	
	@After
	public void tearDown() throws Exception {
		/**
		 * Used to clean up after the test. This method is called by JUnit after
		 * each of the tests have been completed.
		 */
	}

	
}