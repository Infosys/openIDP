/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.entities.licencekeymanagement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Infosys
 *
 */

public class OrganisationInfo {

	
	@SerializedName("orgId")
	@Expose
	private long orgId;
	

	@SerializedName("orgName")
	@Expose
	private String orgName;
	
	
	@SerializedName("orgAdmin")
	@Expose
	private String orgAdmin;
	
	
	@SerializedName("domain")
	@Expose
	private String domain;
	
	@SerializedName("userName")
	@Expose
	private String userName;
	
	@SerializedName("method")
	@Expose
	private String method;
	
	@SerializedName("mailBody")
	@Expose
	private String mailBody;
	
	@SerializedName("licenseServices")
	@Expose
	private String licenseServices;
	
	@SerializedName("licenseExpirydate")
	@Expose
	private String licenseExpiryDate;
	
	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName.toUpperCase();
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName.toUpperCase();
	}

	public String getOrgAdmin() {
		return orgAdmin;
	}

	public void setOrgAdmin(String orgAdmin) {
		this.orgAdmin = orgAdmin;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public String getLicenseServices() {
		return licenseServices;
	}

	public void setLicenseServices(String licenseServices) {
		this.licenseServices = licenseServices;
	}

	public String getLicenseExpiryDate() {
		return licenseExpiryDate;
	}

	public void setLicenseExpiryDate(String licenseExpiryDate) {
		this.licenseExpiryDate = licenseExpiryDate;
	}
	
	
}