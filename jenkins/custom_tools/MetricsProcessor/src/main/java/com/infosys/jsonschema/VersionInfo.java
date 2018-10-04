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
@JsonPropertyOrder({ "lastModified", "commitMessage", "id", "latestFileVersion", "lastModifiedBy", "commitId" })
public class VersionInfo {
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
	@JsonProperty("commitId")
	private String commitId;

	public VersionInfo(String lastModified, String commitMessage, String id) {
		super();
		this.lastModified = lastModified;
		this.commitMessage = commitMessage;
		this.id = id;
	}

	public VersionInfo() {
		super();
		this.lastModified = "Wed Jan 01 00:00:00 IST 1897";
		this.commitMessage = "File Checked In";
		this.id = "none";
		this.latestFileVersion = "0";
		this.lastModifiedBy = "none";
		this.commitId = "none";
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
	public String getid() {
		return id;
	}

	@JsonProperty("id")
	public void setid(String id) {
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

	@JsonProperty("commitId")
	public String getCommitId() {
		return commitId;
	}

	@JsonProperty("commitId")
	public void setCommitId(String id) {
		this.commitId = id;
	}
}