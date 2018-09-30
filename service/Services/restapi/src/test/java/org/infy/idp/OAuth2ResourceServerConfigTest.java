/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class OAuth2ResourceServerConfigTest {

	@Test
	public void testAccessTokenConverter0() throws Throwable {
		OAuth2ResourceServerConfig testedObject = new OAuth2ResourceServerConfig();
		JwtAccessTokenConverter result = testedObject.accessTokenConverter();
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertEquals(2, result.getKey().size());
		assertTrue(result.getKey().containsValue("HMACSHA256"));
		assertFalse(result.getKey().containsValue(
				"-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgIK2Wt4x2EtDl41C7vfp\nOsMquZMyOyteO2RsVeMLF/hXIeYvicKr0SQzVkodHEBCMiGXQDz5prijTq3RHPy2\n/5WJBCYq7yHgTLvspMy6sivXN7NdYE7I5pXo/KHk4nz+Fa6P3L8+L90E/3qwf6j3\nDKWnAgJFRY8AbSYXt1d5ELiIG1/gEqzC0fZmNhhfrBtxwWXrlpUDT0Kfvf0QVmPR\nxxCLXT+tEe1seWGEqeOLL5vXRLqmzZcBe1RZ9kQQm43+a9Qn5icSRnDfTAesQ3Cr\nlAWJKl2kcWU1HwJqw+dZRSZ1X4kEXNMyzPdPBbGmU6MHdhpywI7SKZT7mX4BDnUK\neQIDAQAB\n-----END PUBLIC KEY-----"));
		assertFalse(result.isPublic());
		assertNotNull(result.getAccessTokenConverter());
	}

	@Test
	public void testConfigure() throws Throwable {

		OAuth2ResourceServerConfig testedObject = new OAuth2ResourceServerConfig();
		ObjectPostProcessor objectPostProcessor = mock(ObjectPostProcessor.class);
		AuthenticationManagerBuilder authenticationBuilder = new AuthenticationManagerBuilder(objectPostProcessor);
		ObjectPostProcessor objectPostProcessor_2 = mock(ObjectPostProcessor.class);
		HashMap sharedObjects = new HashMap();
		HttpSecurity http = new HttpSecurity(objectPostProcessor_2, authenticationBuilder, sharedObjects);

		testedObject.configure(http);

	}

	@Test
	public void testTokenServices0() throws Throwable {
		OAuth2ResourceServerConfig testedObject = new OAuth2ResourceServerConfig();
		DefaultTokenServices result = testedObject.tokenServices();
		assertNotNull(result);
	}

	@Test
	public void testTokenStore0() throws Throwable {
		OAuth2ResourceServerConfig testedObject = new OAuth2ResourceServerConfig();
		TokenStore result = testedObject.tokenStore();
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