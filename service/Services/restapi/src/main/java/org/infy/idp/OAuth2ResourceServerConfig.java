/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 
 * The class OAuth2ResourceServerConfig contains methods to configure the access
 * rules for secure resources
 * 
 * @author Infosys
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private CustomAccessTokenConverter customAccessTokenConverter;

	//

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and().authorizeRequests()
				.anyRequest().permitAll()
				.antMatchers("/", "/lib/*", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/images/*",
						"/css/*", "/swagger-ui.js", "/swagger-ui.min.js", "/api-docs", "/fonts/*", "/api-docs/*",
						"/api-docs/default/*", "/o2c.html", "index.html", "/webjars/**", "/hystrix/**")
				.permitAll().and().authorizeRequests().antMatchers(HttpMethod.GET, "/applicationService/**")
				.access("#oauth2.hasScope('read')").antMatchers(HttpMethod.POST, "/applicationService/**")
				.access("#oauth2.hasScope('write')").antMatchers(HttpMethod.GET, "/jobService/**")
				.access("#oauth2.hasScope('read')").antMatchers(HttpMethod.POST, "/jobService/**")
				.access("#oauth2.hasScope('write')").antMatchers(HttpMethod.GET, "/userService/**")
				.access("#oauth2.hasScope('read')").antMatchers(HttpMethod.POST, "/userService/**")
				.access("#oauth2.hasScope('write')");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(final ResourceServerSecurityConfigurer config) {
		config.tokenServices(tokenServices());
	}

	/**
	 * 
	 * @return tokenstore bean
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	/**
	 * 
	 * @return JwtAccessTokenConverter
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setAccessTokenConverter(customAccessTokenConverter);
		final Resource resource = new ClassPathResource("public.txt");
		String publicKey = null;
		try {
			publicKey = IOUtils.toString(resource.getInputStream());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		converter.setVerifierKey(publicKey);
		return converter;
	}

	/**
	 * 
	 * method tokenServices
	 *
	 * @return defaultTokenServices the DefaultTokenServices
	 */
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}

}
