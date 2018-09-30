/**
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.”
*
**/
package org.infy.subscription.exception;

public class InvalidKeyException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5503143068764336022L;

	/**
	 * Constructor 
	 * @param message
	 */
	public InvalidKeyException(String message)
	{
		super(message);
	}

}
