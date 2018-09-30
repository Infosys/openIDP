/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.entities.models.org;

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
@Table(name = "torganization_info")
public class OrgInfo {

	@SequenceGenerator(name = "torganization_info_org_id_seq", sequenceName = "torganization_info_org_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "torganization_info_org_id_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "org_id", insertable = false, updatable = true)
	@SerializedName("orgId")
	@Expose
	@Id
	private long orgId;

	@Column(name = "org_name")
	@SerializedName("orgName")
	@Expose
	private String orgName;

	@Column(name = "org_admin")
	@SerializedName("orgAdmin")
	@Expose
	private String orgAdmin;

	@Column(name = "domain")
	@SerializedName("domain")
	@Expose
	private String domain;

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

}