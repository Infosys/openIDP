/**
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.”
*
**/
package org.infy.subscription.entities.licencekeymanagement;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class License implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6385500988046238790L;

	/**
	 * expiry date
	 */
	@SerializedName("expirydate")
	@Expose
	public Date expirydate;
	
	/**
	 * Signature
	 */
	@SerializedName("signature")
	@Expose
	public String signature;
	
	/**
	 * Organization
	 */
	@SerializedName("organiztion")
	@Expose
	public String organization;
	
	/**
	 * emailid
	 */
	@SerializedName("emailid")
	@Expose
	public String emailid;
	
	/**
	 * Service List
	 */
	@SerializedName("serviceList")
	@Expose
	private List<ServiceType> serviceList;
	
	/**
	 * License Type
	 */
	@SerializedName("license_type")
	@Expose
	private String licenseType;

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}


	public Date getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}

	public List<ServiceType> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ServiceType> serviceList) {
		this.serviceList = serviceList;
	} 
	
	public Map<String, String> getProperty(){
		HashMap<String,String> properties = new HashMap<String,String>();
		properties.put("expirydate",expirydate.toString());
		properties.put("organiztion",organization);
		properties.put("emailid",emailid);
		properties.put("licenseType",licenseType);
		properties.put("serviceList",serviceList.toString());
		return properties;
	}
}
