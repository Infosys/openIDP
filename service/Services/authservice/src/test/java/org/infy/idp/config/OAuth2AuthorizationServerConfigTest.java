/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * OAuth2AuthorizationServerConfigTest is a test class for
 * OAuth2AuthorizationServerConfig
 *
 * @see org.infy.idp.config.OAuth2AuthorizationServerConfig
 *  
 */
public class OAuth2AuthorizationServerConfigTest {

	/**
	 * Constructor for test class.
	 *
	 *  
	 */
	public OAuth2AuthorizationServerConfigTest() {
		/*
		 * This constructor should not be modified. Any initialization code
		 * should be placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method accessTokenConverter().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see OAuth2AuthorizationServerConfig#accessTokenConverter()
	 *  
	 * 
	 */
	@Test
	public void testAccessTokenConverter0() throws Throwable {
		OAuth2AuthorizationServerConfig testedObject = new OAuth2AuthorizationServerConfig();
		JwtAccessTokenConverter result = testedObject.accessTokenConverter();
		assertNotNull(result); 
		assertNotNull(result.getKey()); 
		assertEquals(2, result.getKey().size()); 
		assertEquals(true, result.getKey().containsValue("SHA256withRSA")); 
		assertEquals(true, result.getKey().containsValue(
				"-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgIK2Wt4x2EtDl41C7vfpOsMquZMyOyteO2RsVeMLF/hXIeYvicKr0SQzVkodHEBCMiGXQDz5prijTq3RHPy2/5WJBCYq7yHgTLvspMy6sivXN7NdYE7I5pXo/KHk4nz+Fa6P3L8+L90E/3qwf6j3DKWnAgJFRY8AbSYXt1d5ELiIG1/gEqzC0fZmNhhfrBtxwWXrlpUDT0Kfvf0QVmPRxxCLXT+tEe1seWGEqeOLL5vXRLqmzZcBe1RZ9kQQm43+a9Qn5icSRnDfTAesQ3CrlAWJKl2kcWU1HwJqw+dZRSZ1X4kEXNMyzPdPBbGmU6MHdhpywI7SKZT7mX4BDnUKeQIDAQAB\n-----END PUBLIC KEY-----")); 
		assertEquals(true, result.isPublic()); 
		assertNotNull(result.getAccessTokenConverter()); 
		// No exception thrown
		
	}

	/**
	 * Test for method
	 * configure(org.springframework.security.oauth2.config.annotation.web.
	 * configurers.AuthorizationServerSecurityConfigurer).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see OAuth2AuthorizationServerConfig#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer)
	 *  
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testConfigure0() throws Throwable {
		OAuth2AuthorizationServerConfig testedObject = new OAuth2AuthorizationServerConfig();
		testedObject.configure((AuthorizationServerSecurityConfigurer) null);
	}

	/**
	 * Test for method
	 * configure(org.springframework.security.oauth2.config.annotation.
	 * configurers.ClientDetailsServiceConfigurer).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see OAuth2AuthorizationServerConfig#configure(org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer)
	 *  
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testConfigure1() throws Throwable {
		OAuth2AuthorizationServerConfig testedObject = new OAuth2AuthorizationServerConfig();
		ClientDetailsServiceBuilder builder = new ClientDetailsServiceBuilder();
		ClientDetailsServiceConfigurer clients = new ClientDetailsServiceConfigurer(builder);
		testedObject.configure(clients);

		// NullPointerException thrown
		// at
		// org.infy.idp.config.OAuth2AuthorizationServerConfig.configure(OAuth2AuthorizationServerConfig.java:92)
	}

	/**
	 * Test for method
	 * configure(org.springframework.security.oauth2.config.annotation.
	 * configurers.ClientDetailsServiceConfigurer).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see OAuth2AuthorizationServerConfig#configure(org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer)
	 *  
	 * 
	 */
	@Test(expected = IllegalStateException.class)
	public void testConfigure3() throws Throwable {
		OAuth2AuthorizationServerConfig testedObject = new OAuth2AuthorizationServerConfig();
		ClientDetailsServiceConfigurer clients = new ClientDetailsServiceConfigurer((ClientDetailsServiceBuilder) null);
		testedObject.configure(clients);
		// IllegalStateException thrown
		// at
		// org.springframework.security.config.annotation.SecurityConfigurerAdapter.getBuilder(SecurityConfigurerAdapter.java:68)
		// at
		// org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer.inMemory(ClientDetailsServiceConfigurer.java:43)
		// at
		// org.infy.idp.config.OAuth2AuthorizationServerConfig.configure(OAuth2AuthorizationServerConfig.java:92)
	}

	/**
	 * Test for method
	 * configure(org.springframework.security.oauth2.config.annotation.
	 * configurers.ClientDetailsServiceConfigurer).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see OAuth2AuthorizationServerConfig#configure(org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer)
	 *  
	 * 
	 */
	@Test(expected = Exception.class)
	public void testConfigure6() throws Throwable {
		OAuth2AuthorizationServerConfig testedObject = new OAuth2AuthorizationServerConfig();
		ClientDetailsServiceConfigurer clients = new ClientDetailsServiceConfigurer((ClientDetailsServiceBuilder) null);
		testedObject.configure(clients);

		// Exception thrown
		// at
		// org.infy.idp.config.OAuth2AuthorizationServerConfig.configure(OAuth2AuthorizationServerConfig.java:92)
	}


	/**
	 * Test for method
	 * configure(org.springframework.security.oauth2.config.annotation.web.
	 * configurers.AuthorizationServerEndpointsConfigurer).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see OAuth2AuthorizationServerConfig#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer)
	 *  
	 * 
	 */
	@Test
	public void testConfigure7() throws Throwable {
		OAuth2AuthorizationServerConfig testedObject = new OAuth2AuthorizationServerConfig();
		AuthorizationServerEndpointsConfigurer endpoints = new AuthorizationServerEndpointsConfigurer();
		testedObject.configure(endpoints);
		assertNotNull(endpoints.getTokenServices()); 
		assertNotNull(endpoints.getTokenEnhancer()); 
		assertNotNull(endpoints.getTokenStore()); 
		//assertNotNull(endpoints.getApprovalStore()); // Ignored by Jtest
		//assertNotNull(endpoints.getClientDetailsService()); 
		//assertNotNull(endpoints.getOAuth2RequestFactory()); 
		// No exception thrown
		
	
	}

	/**
	 * Test for method
	 * configure(org.springframework.security.oauth2.config.annotation.web.
	 * configurers.AuthorizationServerEndpointsConfigurer).
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see OAuth2AuthorizationServerConfig#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer)
	 *  
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testConfigure8() throws Throwable {
		OAuth2AuthorizationServerConfig testedObject = new OAuth2AuthorizationServerConfig();
		testedObject.configure((AuthorizationServerEndpointsConfigurer) null);

		// NullPointerException thrown
		// at
		// org.infy.idp.config.OAuth2AuthorizationServerConfig.configure(OAuth2AuthorizationServerConfig.java:112)
	}

	/**
	 * Test for method OAuth2AuthorizationServerConfig().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see OAuth2AuthorizationServerConfig#OAuth2AuthorizationServerConfig()
	 *  
	 * 
	 */
	@Test
	public void testOAuth2AuthorizationServerConfig0() throws Throwable {
		OAuth2AuthorizationServerConfig testedObject = new OAuth2AuthorizationServerConfig();
		// No exception thrown
		
	}

	/**
	 * Test for method tokenEnhancer().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see OAuth2AuthorizationServerConfig#tokenEnhancer()
	 *  
	 * 
	 */
	@Test
	public void testTokenEnhancer0() throws Throwable {
		OAuth2AuthorizationServerConfig testedObject = new OAuth2AuthorizationServerConfig();
		TokenEnhancer result = testedObject.tokenEnhancer();
		assertNotNull(result); 
		// No exception thrown
		
	}

	/**
	 * Test for method tokenServices().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see OAuth2AuthorizationServerConfig#tokenServices()
	 *  
	 * 
	 */
	@Test
	public void testTokenServices0() throws Throwable {
		OAuth2AuthorizationServerConfig testedObject = new OAuth2AuthorizationServerConfig();
		DefaultTokenServices result = testedObject.tokenServices();
		assertNotNull(result); 
		// No exception thrown
		
	}

	/**
	 * Test for method tokenStore().
	 * 
	 * @throws Throwable
	 *             Tests may throw any Throwable
	 *
	 * @see OAuth2AuthorizationServerConfig#tokenStore()
	 *  
	 * 
	 */
	@Test
	public void testTokenStore0() throws Throwable {
		OAuth2AuthorizationServerConfig testedObject = new OAuth2AuthorizationServerConfig();
		TokenStore result = testedObject.tokenStore();
		assertNotNull(result); 
		// No exception thrown
		
	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java OAuth2AuthorizationServerConfigTest
	 * 
	 * @param args
	 *            command line arguments are not needed
	 *  
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.config.OAuth2AuthorizationServerConfigTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 *  
	 */
	public Class getTestedClass() {
		return OAuth2AuthorizationServerConfig.class;
	}
}
