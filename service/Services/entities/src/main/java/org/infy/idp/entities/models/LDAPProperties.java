/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.models;

/**
 * Entity to store LDAP properties
 * 
 * @author Infosys
 *
 */
public class LDAPProperties {
	private String LDAPBase;
	private String LDAPUrl;
	private String LDAPSuffix;

	public String getLDAPBase() {
		return LDAPBase;
	}

	public void setLDAPBase(String lDAPBase) {
		LDAPBase = lDAPBase;
	}

	public String getLDAPUrl() {
		return LDAPUrl;
	}

	public void setLDAPUrl(String lDAPUrl) {
		LDAPUrl = lDAPUrl;
	}

	public String getLDAPSuffix() {
		return LDAPSuffix;
	}

	public void setLDAPSuffix(String lDAPSuffix) {
		LDAPSuffix = lDAPSuffix;
	}

}
