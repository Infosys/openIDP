/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.buildinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store artifact repository details for build
 * 
 * @author Infosys
 *
 */
public class ArtifactRepo {
	@SerializedName("repoURL")
	@Expose
	private String repoURL;
	@SerializedName("repoName")
	@Expose
	private String repoName;
	@SerializedName("repoUsername")
	@Expose
	private String repoUsername;
	@SerializedName("repoPassword")
	@Expose
	private String repoPassword;
	@SerializedName("repoNameDR")
	@Expose
	private String repoNameDR;
	@SerializedName("passwordDR")
	@Expose
	private String passwordDR;
	@SerializedName("dockerFilePathDR")
	@Expose
	private String dockerFilePathDR;
	@SerializedName("dockerRegistryUrlDR")
	@Expose
	private String dockerRegistryUrlDR;
	@SerializedName("userNameDR")
	@Expose
	private String userNameDR;
	@SerializedName("passwordManager")
	@Expose
	private String passwordManager;
	@SerializedName("passwordManagerId")
	@Expose
	private String passwordManagerId;
	public String getRepoNameDR() {
		return repoNameDR;
	}

	public void setRepoNameDR(String repoNameDR) {
		this.repoNameDR = repoNameDR;
	}

	public String getPasswordDR() {
		return passwordDR;
	}

	public void setPasswordDR(String passwordDR) {
		this.passwordDR = passwordDR;
	}

	public String getDockerFilePathDR() {
		return dockerFilePathDR;
	}

	public void setDockerFilePathDR(String dockerFilePathDR) {
		this.dockerFilePathDR = dockerFilePathDR;
	}

	public String getDockerRegistryUrlDR() {
		return dockerRegistryUrlDR;
	}

	public void setDockerRegistryUrlDR(String dockerRegistryUrlDR) {
		this.dockerRegistryUrlDR = dockerRegistryUrlDR;
	}

	public String getUserNameDR() {
		return userNameDR;
	}

	public void setUserNameDR(String userNameDR) {
		this.userNameDR = userNameDR;
	}

	public String getPasswordManager() {
		return passwordManager;
	}

	public void setPasswordManager(String passwordManager) {
		this.passwordManager = passwordManager;
	}

	public String getPasswordManagerId() {
		return passwordManagerId;
	}

	public void setPasswordManagerId(String passwordManagerId) {
		this.passwordManagerId = passwordManagerId;
	}

	public String getRepoURL() {
		return repoURL;
	}

	public void setRepoURL(String repoURL) {
		this.repoURL = repoURL;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	public String getRepoUsername() {
		return repoUsername;
	}

	public void setRepoUsername(String repoUsername) {
		this.repoUsername = repoUsername;
	}

	public String getRepoPassword() {
		return repoPassword;
	}

	public void setRepoPassword(String repoPassword) {
		this.repoPassword = repoPassword;
	}

}
