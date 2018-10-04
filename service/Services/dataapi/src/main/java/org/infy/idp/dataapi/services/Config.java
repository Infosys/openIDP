/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.dataapi.services;

/**
 * @author Infosys
 *
 */
public class Config {
	public static final int IDP_APPLICATION = 1;
	
	private Config() {

	}

	/**
	 * @param platformName
	 * @return respective integer value of platform name
	 */
	public static int platformType(String platformName) {
		if (platformName != null && platformName.equalsIgnoreCase("IDP")) {
			return IDP_APPLICATION;
		}
		return 1;
	}
}
