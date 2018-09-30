/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class loads user specific data for auth
 * @author Infosys
 */
@Service
public class HybridUserDetailsService implements UserDetailsService {

	

	@Autowired
	private UserService userService;

	/**
	 * loadUserByUsername Method
	 *
	 * @param username as String
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null || username.isEmpty()) {
			throw new UsernameNotFoundException("Username is empty");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Object clientPrincipal = authentication.getPrincipal();

		if (clientPrincipal instanceof User) {

			UserDetails user = loadUserDetails(username);
			if (user != null)
				return user;

		}

		throw new UsernameNotFoundException("Unauthorized client_id or username not found: " + username);
	}

	/**
	 * loadUserDetails Method
	 *
	 * @param username as String
	 * 
	 */
	private UserDetails loadUserDetails(String username) {
		Response response = userService.loadUser(username);
		if (response.isOk()) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			return new User(username, "", authorities);
		}
		return null;
	}

}
