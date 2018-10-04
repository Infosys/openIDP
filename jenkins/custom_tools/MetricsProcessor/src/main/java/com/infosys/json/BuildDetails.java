/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuildDetails {
	@JsonProperty("lastBuildId")
	private String lastBuildId;
	@JsonProperty("lastSuccessfulBuildId")
	private String lastSuccessfulBuildId;
	@JsonProperty("lastCompletedBuildId")
	private String lastCompletedBuildId;
	@JsonProperty("lastUnstableBuildId")
	private String lastUnstableBuildId;
	@JsonProperty("lastUnsuccessfulBuildId")
	private String lastUnsuccessfulBuildId;
	@JsonProperty("lastFailedBuildId")
	private String lastFailedBuildId;

	@JsonProperty("buildTime")
	private String buildTime;
	@JsonProperty("builtStatus")
	private String builtStatus;
	@JsonProperty("timestamp")
	private String timestamp;
	@JsonProperty("score")
	private String score;

	@JsonProperty("lastFailedBuildId")
	public String getLastFailedBuildId() {
		return lastFailedBuildId;
	}

	@JsonProperty("lastFailedBuildId")
	public void setLastFailedBuildId(String lastFailedBuildId) {
		this.lastFailedBuildId = lastFailedBuildId;
	}

	@JsonProperty("timestamp")
	public String getTimestamp() {
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@JsonProperty("buildTime")
	public String getBuildTime() {
		return buildTime;
	}

	@JsonProperty("buildTime")
	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	@JsonProperty("builtStatus")
	public String getBuiltStatus() {
		return builtStatus;
	}

	@JsonProperty("builtStatus")
	public void setBuiltStatus(String builtStatus) {
		this.builtStatus = builtStatus;
	}

	@JsonProperty("lastBuildId")
	public String getLastBuildId() {
		return lastBuildId;
	}

	@JsonProperty("lastBuildId")
	public void setLastBuildId(String lastBuildId) {
		this.lastBuildId = lastBuildId;
	}

	@JsonProperty("lastSuccessfulBuildId")
	public String getLastSuccessfulBuildId() {
		return lastSuccessfulBuildId;
	}

	@JsonProperty("lastSuccessfulBuildId")
	public void setLastSuccessfulBuildId(String lastSuccessfulBuildId) {
		this.lastSuccessfulBuildId = lastSuccessfulBuildId;
	}

	@JsonProperty("lastCompletedBuildId")
	public String getLastCompletedBuildId() {
		return lastCompletedBuildId;
	}

	@JsonProperty("lastCompletedBuildId")
	public void setLastCompletedBuildId(String lastCompletedBuildId) {
		this.lastCompletedBuildId = lastCompletedBuildId;
	}

	@JsonProperty("lastUnstableBuildId")
	public String getLastUnstableBuildId() {
		return lastUnstableBuildId;
	}

	@JsonProperty("lastUnstableBuildId")
	public void setLastUnstableBuildId(String lastUnstableBuildId) {
		this.lastUnstableBuildId = lastUnstableBuildId;
	}

	@JsonProperty("lastUnsuccessfulBuildId")
	public String getLastUnsuccessfulBuildId() {
		return lastUnsuccessfulBuildId;
	}

	@JsonProperty("lastUnsuccessfulBuildId")
	public void setLastUnsuccessfulBuildId(String lastUnsuccessfulBuildId) {
		this.lastUnsuccessfulBuildId = lastUnsuccessfulBuildId;
	}

	@JsonProperty("score")
	public String getScore() {
		return score;
	}

	@JsonProperty("score")
	public void setScore(String score) {
		this.score = score;
	}
}
