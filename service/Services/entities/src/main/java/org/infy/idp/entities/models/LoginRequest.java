/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.models;

/**
 * Entity to store login request information
 * 
 * @author Infosys
 *
 */
public class LoginRequest {

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/**
	 * Constructor
	 * 
	 * @param username the String
	 * @param password the String
	 */
	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Constructor
	 */
	public LoginRequest() {

	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}