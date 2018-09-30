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
 * Entity to store environment provider details
 * 
 * @author Infosys
 *
 */
public class EnvironmentProvDetails {

	@SerializedName("environmentProvName")
	@Expose
	private String environmentProvName;
	@SerializedName("serverName")
	@Expose
	private String serverName;
	@SerializedName("toolName")
	@Expose
	private String toolName;

	@SerializedName("ansUserName")
	@Expose
	private String ansUserName;
	@SerializedName("ansPassword")
	@Expose
	private String ansPassword;
	@SerializedName("playPath")
	@Expose
	private String playPath;
	@SerializedName("playName")
	@Expose
	private String playName;
	@SerializedName("invName")
	@Expose
	private String invName;

	public String getEnvironmentProvName() {
		return environmentProvName;
	}

	public void setEnvironmentProvName(String environmentProvName) {
		this.environmentProvName = environmentProvName;
	}

	public String getAnsUserName() {
		return ansUserName;
	}

	public void setAnsUserName(String ansUserName) {
		this.ansUserName = ansUserName;
	}

	public String getAnsPassword() {
		return ansPassword;
	}

	public void setAnsPassword(String ansPassword) {
		this.ansPassword = ansPassword;
	}

	public String getPlayPath() {
		return playPath;
	}

	public void setPlayPath(String playPath) {
		this.playPath = playPath;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public String getInvName() {
		return invName;
	}

	public void setInvName(String invName) {
		this.invName = invName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
}
