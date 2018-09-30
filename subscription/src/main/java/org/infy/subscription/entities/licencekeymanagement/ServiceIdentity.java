/**
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.”
*
**/
package org.infy.subscription.entities.licencekeymanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceIdentity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6301235306295610195L;

	@Column(name = "license_id", insertable = true, updatable = true)
	@SerializedName("licenseId")
	@Expose
	@NotNull
	private long licenseId;

	@Column(name = "service_name")
	@SerializedName("serviceName")
	@Expose
	@NotNull
	private String serviceName;

	public long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(long licenseId) {
		this.licenseId = licenseId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
