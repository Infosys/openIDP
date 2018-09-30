/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

/**
 * Response mock, to abstract a dao query result, or a remote service response
 * 
 * @author Infosys
 * 
 */
public class Response {

	private boolean ok;
	private String keycloacktoken;

	/**
	 * constructor
	 */
	private Response(boolean result) {
		this.ok = result;
	}

	private Response(boolean result, String token) {
		this.ok = result;
		this.keycloacktoken = token;
	}

	/**
	 * @return boolean
	 */

	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            as boolean
	 */

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * 
	 * @return String
	 */
	public String getKeycloacktoken() {
		return keycloacktoken;
	}

	/**
	 * 
	 * @param keycloacktoken
	 */
	public void setKeycloacktoken(String keycloacktoken) {
		this.keycloacktoken = keycloacktoken;
	}

	/**
	 * @return constructs a success response
	 */
	public static Response ok() {
		return new Response(true);
	}

	/**
	 * 
	 * @param token
	 * @return response
	 */
	public static Response ok(String token) {
		return new Response(true, token);
	}

	/**
	 * @return constructs a failure response
	 */
	public static Response fail() {
		return new Response(false);
	}
}
