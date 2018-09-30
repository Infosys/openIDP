/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * This class is create for custom authentication
 * 
 * @author Infosys
 *
 */
public class CustomAuthentication extends UsernamePasswordAuthenticationToken implements Authentication {

	/**
	* 
	*/
	private static final long serialVersionUID = -1052238536718645685L;

	public CustomAuthentication(Object principal, Object credentials, String keycloaktoken) {
		super(principal, credentials);
		this.keycloaktoken = keycloaktoken;
	}

	public CustomAuthentication(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, String keycloaktoken) {
		super(principal, credentials, authorities);
		this.keycloaktoken = keycloaktoken;
	}

	private String keycloaktoken;

	public String getKeyCloakToken() {
		return keycloaktoken;
	}
}
