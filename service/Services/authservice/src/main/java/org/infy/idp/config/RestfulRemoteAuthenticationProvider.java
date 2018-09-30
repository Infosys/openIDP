/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This class indicates a class can process a specific auth
 * 
 * @author Infosys
 */
@Component
public class RestfulRemoteAuthenticationProvider implements AuthenticationProvider {
	private final Logger logger = LoggerFactory.getLogger(RestfulRemoteAuthenticationProvider.class);

	@Autowired
	private UserService userService;

	/**
	 * Method authenticate
	 * 
	 * @param authentication as Authentication
	 * 
	 * @return Authentication
	 * 
	 */

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.debug("Authenticating using AuthenticationProvider: " + authentication);
		Response response;
		// here goes username/password authentication
		if (null == authentication.getPrincipal() || "".equals(authentication.getPrincipal())
				|| null == authentication.getCredentials() || "".equals(authentication.getCredentials())) {
			response = Response.fail();
		} else {
			response = userService.authenticate(String.valueOf(authentication.getPrincipal()),
					String.valueOf(authentication.getCredentials()));
		}
		if (response.isOk()) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			return new CustomAuthentication(authentication.getPrincipal(), authentication.getCredentials(), authorities,
					response.getKeycloacktoken());
		} else {
			throw new BadCredentialsException("Authentication failed.");
		}
	}

	/**
	 * Method supports
	 * 
	 * @param authentication as Class
	 * 
	 * @return boolean
	 * 
	 */

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
