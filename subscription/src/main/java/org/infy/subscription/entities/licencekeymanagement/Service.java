/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.entities.licencekeymanagement;

import java.sql.Date;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.Table;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Infosys
 *
 */

@Entity
@Table(name = "tservices")
public class Service {

	@EmbeddedId
	private ServiceIdentity serviceIdentity;
	
	@Column(name = "expiry_date")
	@SerializedName("expiryDate")
	@Expose
	private Date expiryDate;

	@Column(name = "org_name")
	@SerializedName("orgName")
	@Expose
	private String orgName;
	
	public String getOrgName() {
		return orgName.toUpperCase();
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName.toUpperCase();
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
