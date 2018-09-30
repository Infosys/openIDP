/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import org.infy.idp.config.authbl.LoginBL;
import org.infy.idp.config.entities.LoginRequest;
import org.infy.idp.config.utils.ConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class UserService is for user login
 * @author Infosys
**/
@Service
public class UserService {

	@Autowired
	private ConfigurationManager configManager;
	@Autowired
	private LoginBL loginBL;

	/**
	 * authenticates from data source
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	public Response authenticate(String username, String password) {
		// here goes your actual username && password verification logic
		password = password.trim();
		username = username.trim();
		if ("Y".equals(configManager.getSkipAuthentication())) {
			return Response.ok();
		}
		if (null == username || null == password || "".equalsIgnoreCase(username) || "".equalsIgnoreCase(password)) {
			return Response.fail();
		}
		String response = loginBL.performLogin(new LoginRequest(username, password));
		if (response.equals("false")) {
			return Response.fail();
		} else {
			return Response.ok(response);
		}
	}

	/**
	 * method loadUser
	 *
	 * @param username the String
	 * 
	 * @return
	 */
	public Response loadUser(String username) {

		return Response.ok();
	}

}
