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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Infosys
 *
 */
@Entity
@Table(name = "tlicense_key_management")
public class LicenseKey {

	@Column(name = "license_key", insertable = true, updatable = true)
	@SerializedName("licenseKey")
	@Expose
	private byte[] license;

	@Column(name = "date", insertable = false, updatable = false)
	@SerializedName("date")
	@Expose
	private Date date;

	@Column(name = "expiry_date")
	@SerializedName("expiryDate")
	@Expose
	private Date expiryDate;
	
	@SequenceGenerator(name="tlicense_key_management_license_id_seq",
            sequenceName="tlicense_key_management_license_id_seq",
            allocationSize=1)
	@GeneratedValue(generator="tlicense_key_management_license_id_seq",strategy=GenerationType.SEQUENCE)
	@Column(name = "license_id", insertable = false, updatable = false)
	@SerializedName("licenseId")
	@Expose
	@Id
	private long licenseId;
	
	
	@Column(name = "license_type")
	@SerializedName("licenseType")
	@Expose
	private String licenseType;
	
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

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(long licenseId) {
		this.licenseId = licenseId;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}


	public byte[] getLicenseKey() {
		return license;
	}

	public void setLicenseKey(String licenseKey) {
		this.license = licenseKey.getBytes();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	

}