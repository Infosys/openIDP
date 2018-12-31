/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.applicationinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store env owner details
 * 
 * @author Infosys
 *
 */
public class EnvironmentOwnerDetail {
	@SerializedName("environmentName")
	@Expose
	private String environmentName;
	@SerializedName("environmentOwners")
	@Expose
	private String environmentOwners;
	@SerializedName("qa")
	@Expose
	private String qa;
    
	@SerializedName("landscapeType")
    @Expose
    private String landscapeType;
	@SerializedName("hostName")
	@Expose
	private String hostName;
	@SerializedName("instanceNumber")
	@Expose
	private String instanceNumber;

	@SerializedName("systemId")
	@Expose
	private String systemId;

	@SerializedName("client")
	@Expose
	private String client;

	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("password")
	@Expose
	private String password;
	@SerializedName("language")
	@Expose
	private String language;
	@SerializedName("dBOwners")
	@Expose
	private String dBOwners;

	@SerializedName("ServerName")
	@Expose
	private String ServerName;
	@SerializedName("port")
	@Expose
	private String port;
	@SerializedName("connType")
	@Expose
	private String connType;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getServerName() {
		return ServerName;
	}

	public void setServerName(String serverName) {
		ServerName = serverName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getConnType() {
		return connType;
	}

	public void setConnType(String connType) {
		this.connType = connType;
	}

	public String getQa() {
		return qa;
	}

	public void setQa(String qa) {
		this.qa = qa;
	}
    public String getLandscapeType() {
		return landscapeType;
	}
	public void setLandscapeType(String landscapeType) {
		this.landscapeType = landscapeType;
	}
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getInstanceNumber() {
		return instanceNumber;
	}

	public void setInstanceNumber(String instanceNumber) {
		this.instanceNumber = instanceNumber;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public String getEnvironmentOwners() {
		return environmentOwners;
	}

	public void setEnvironmentOwners(String environmentOwners) {
		this.environmentOwners = environmentOwners;
	}

	public String getdBOwners() {
		return dBOwners;
	}

	public void setdBOwners(String dBOwners) {
		this.dBOwners = dBOwners;
	}

}