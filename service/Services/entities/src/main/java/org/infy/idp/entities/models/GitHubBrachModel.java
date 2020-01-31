/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.models;
/**
 * Entity to store GitHub branch detail
 * 
 * @author Infosys
 *
 */
public class GitHubBrachModel {
	private String repoUrl; 
	private String username;
	private String pwd;
	private String projectUrl;
	private String proxy;
	private String port;

	public GitHubBrachModel(String repoUrl, String username, String pwd, String projectUrl, String proxy, String port) {
		this.repoUrl = repoUrl;
		this.username = username;
		this.pwd = pwd;
		this.projectUrl = projectUrl;
		this.proxy = proxy;
		this.port = port;
	}

	public GitHubBrachModel() {}
	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getProjectUrl() {
		return projectUrl;
	}

	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
