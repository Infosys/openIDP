/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.businessapi;

import javax.mail.PasswordAuthentication;

/**
 * Provides email authenticator
 * 
 * @author Infosys
 */
public class Authenticator extends javax.mail.Authenticator {

	private PasswordAuthentication authentication;

	/**
	 * Constructor authenticator with the specified username and password
	 * 
	 * @param username
	 * @param password
	 */
	public Authenticator(String username, String password) {

		authentication = new PasswordAuthentication(username, password);
	}

	/**
	 * @return PasswordAuthentication
	 */

	protected PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
}