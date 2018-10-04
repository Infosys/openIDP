/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.code;

import java.util.List;

import org.infy.idp.entities.jobs.common.RunScript;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store code or project repository details
 * 
 * @author Infosys
 *
 */
public class Code {

	@SerializedName("category")
	@Expose
	private String category;
	@SerializedName("technology")
	@Expose
	private String technology;
	@SerializedName("scm")
	@Expose
	private List<Scm> scm = null;

	@SerializedName("jobParam")
	@Expose
	private List<JobParam> jobParam = null;

	@SerializedName("buildScript")
	@Expose
	private List<RunScript> buildScript = null;

	@SerializedName("subApplication")
	@Expose
	private String subApplication;

	@SerializedName("port")
	@Expose
	private String port = null;

	@SerializedName("username")
	@Expose
	private String username = null;

	@SerializedName("password")
	@Expose
	private String password = null;

	@SerializedName("connType")
	@Expose
	private String connType = null;

	@SerializedName("serverName")
	@Expose
	private String serverName = null;
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConnType() {
		return connType;
	}

	public void setConnType(String connType) {
		this.connType = connType;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	

	public String getSubApplication() {
		return subApplication;
	}

	public void setSubApplication(String subApplication) {
		this.subApplication = subApplication;
	}

	public List<RunScript> getBuildScript() {
		return buildScript;
	}

	public void setBuildScript(List<RunScript> buildScript) {
		this.buildScript = buildScript;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public List<Scm> getScm() {
		return scm;
	}

	public void setScm(List<Scm> scm) {
		this.scm = scm;
	}

	public List<JobParam> getJobParam() {
		return jobParam;
	}

	public void setJobParam(List<JobParam> jobParam) {
		this.jobParam = jobParam;
	}

}
