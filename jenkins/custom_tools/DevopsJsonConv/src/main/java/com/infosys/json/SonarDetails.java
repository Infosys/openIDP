/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SonarDetails {
	@SerializedName("sonarServer")
	@Expose
	private String sonarServer;
	@SerializedName("sonarPrjctKey")
	@Expose
	private String sonarPrjctKey;
	@SerializedName("sonarUserName")
	@Expose
	private String sonarUserName;
	@SerializedName("sonarPassword")
	@Expose
	private String sonarPassword;
	@SerializedName("bugs")
	@Expose
	private String bugs;
	@SerializedName("vulnerabilities")
	@Expose
	private String vulnerabilities;
	@SerializedName("codesmells")
	@Expose
	private String codesmells;
	@SerializedName("rateperhour")
	@Expose
	private String rateperhour;
	@SerializedName("technicaldebt")
	@Expose
	private String technicalDebt;
	@SerializedName("loc")
	@Expose
	private int loc;

	public String getTechnicalDebt() {
		return technicalDebt;
	}

	public void setTechnicalDebt(String technicalDebt) {
		this.technicalDebt = technicalDebt;
	}

	public String getSonarUserName() {
		return sonarUserName;
	}

	public void setSonarUserName(String sonarUserName) {
		this.sonarUserName = sonarUserName;
	}

	public String getSonarPassword() {
		return sonarPassword;
	}

	public void setSonarPassword(String sonarPassword) {
		this.sonarPassword = sonarPassword;
	}

	public int getLoc() {
		return loc;
	}

	public void setLoc(int loc) {
		this.loc = loc;
	}

	public String getBugs() {
		return bugs;
	}

	public void setBugs(String bugs) {
		this.bugs = bugs;
	}

	public String getVulnerabilities() {
		return vulnerabilities;
	}

	public void setVulnerabilities(String vulnerabilities) {
		this.vulnerabilities = vulnerabilities;
	}

	public String getCodesmells() {
		return codesmells;
	}

	public void setCodesmells(String codesmells) {
		this.codesmells = codesmells;
	}

	public String getRateperhour() {
		return rateperhour;
	}

	public void setRateperhour(String rateperhour) {
		this.rateperhour = rateperhour;
	}

	public void setSonarServer(String sonarServer) {
		this.sonarServer = sonarServer;
	}

	public void setSonarPrjctKey(String sonarPrjctKey) {
		this.sonarPrjctKey = sonarPrjctKey;
	}

	public String getSonarPrjctKey() {
		return sonarPrjctKey;
	}

	public String getSonarServer() {
		return sonarServer;
	}
}