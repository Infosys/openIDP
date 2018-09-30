/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.subscription;

import java.sql.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store organization service details
 * 
 * @author Infosys
 *
 */
public class Service {

	@SerializedName("serviceIdentity")
	@Expose
	private ServiceIdentity serviceIdentity;

	@SerializedName("expiryDate")
	@Expose
	private Date expiryDate;

	@SerializedName("orgName")
	@Expose
	private String orgName;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public ServiceIdentity getServiceIdentity() {
		return serviceIdentity;
	}

	public void setServiceIdentity(ServiceIdentity serviceIdentity) {
		this.serviceIdentity = serviceIdentity;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
