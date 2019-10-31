/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.jsonschema;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "lastModified", "commitMessage", "id", "latestFileVersion", "lastModifiedBy", "remoteUrl",
		"getAffectedPath" })
public class SCMInfo {
	@JsonProperty("lastModified")
	private String lastModified;
	@JsonProperty("commitMessage")
	private String commitMessage;


	@JsonProperty("id")
	private String id;
	@JsonProperty("latestFileVersion")
	private String latestFileVersion;
	@JsonProperty("lastModifiedBy")
	private String lastModifiedBy;
	@JsonProperty("remoteUrl")
	private String remoteUrl;
	@JsonProperty("getAffectedPath")
	private String getAffectedPath;
	
	@JsonProperty("scmUrl")
	private String scmUrl;

	public String getScmUrl() {
		return scmUrl;
	}

	public void setScmUrl(String scmUrl) {
		this.scmUrl = scmUrl;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
	public String getGetAffectedPath() {
		return getAffectedPath;
	}

	public void setGetAffectedPath(String getAffectedPath) {
		this.getAffectedPath = getAffectedPath;
	}

	public SCMInfo() {
		super();
		this.lastModified = "Wed Jan 01 00:00:00 IST 1897";
		this.commitMessage = "File(s) Checked In";
		this.id = "none";
		this.latestFileVersion = "0";
		this.lastModifiedBy = "none";
		this.remoteUrl = "none";
		this.getAffectedPath = "none";
	}

	@JsonProperty("lastModified")
	public String getLastModified() {
		return lastModified;
	}

	@JsonProperty("lastModified")
	public void setLastModified(String string) {
		this.lastModified = string;
	}

	@JsonProperty("commitMessage")
	public String getCommitMessage() {
		return commitMessage;
	}

	@JsonProperty("commitMessage")
	public void setCommitMessage(String commitMessage) {
		this.commitMessage = commitMessage;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("latestFileVersion")
	public String getLatestFileVersion() {
		return latestFileVersion;
	}

	@JsonProperty("latestFileVersion")
	public void setLatestFileVersion(String id) {
		this.latestFileVersion = id;
	}

	@JsonProperty("lastModifiedBy")
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	@JsonProperty("lastModifiedBy")
	public void setLastModifiedBy(String id) {
		this.lastModifiedBy = id;
	}
}
