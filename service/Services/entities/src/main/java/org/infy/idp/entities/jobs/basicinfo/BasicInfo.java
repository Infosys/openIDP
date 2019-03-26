/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.basicinfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store pipeline basic info
 * 
 * @author Infosys
 *
 */
public class BasicInfo {

	@SerializedName("applicationName")
	@Expose
	private String applicationName;
	@SerializedName("pipelineName")
	@Expose
	private String pipelineName;

	@SerializedName("masterSequence")
	@Expose
	private String masterSequence;

	@SerializedName("buildServerOS")
	@Expose
	private String buildServerOS;
	@SerializedName("engine")
	@Expose
	private String engine;
	@SerializedName("buildInterval")
	@Expose
	private BuildInterval buildInterval;
	@SerializedName("additionalMailRecipients")
	@Expose
	private AdditionalMailRecipients additionalMailRecipients;

	@SerializedName("groupId")
	@Expose
	private String groupId;
	@SerializedName("groupName")
	@Expose
	private String groupName;

	@SerializedName("platform")
	@Expose
	private String platform;

	@SerializedName("userName")
	@Expose
	private String userName;

	@SerializedName("pipelineStatus")
	@Expose
	private String pipelineStatus;
	@SerializedName("customTriggerInterval")
	@Expose
	private TriggerInterval customTriggerInterval = null;
	

	public String getMasterSequence() {
		return masterSequence;
	}

	public void setMasterSequence(String masterSequence) {
		this.masterSequence = masterSequence;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public TriggerInterval getCustomTriggerInterval() {
		return customTriggerInterval;
	}

	public void setCustomTriggerInterval(TriggerInterval customTriggerInterval) {
		this.customTriggerInterval = customTriggerInterval;
	}

	public String getPipelineStatus() {
		return pipelineStatus;
	}

	public void setPipelineStatus(String pipelineStatus) {
		this.pipelineStatus = pipelineStatus;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public String getBuildServerOS() {
		return buildServerOS;
	}

	public void setBuildServerOS(String buildServerOS) {
		this.buildServerOS = buildServerOS;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public BuildInterval getBuildInterval() {
		return buildInterval;
	}

	public void setBuildInterval(BuildInterval buildInterval) {
		this.buildInterval = buildInterval;
	}

	public AdditionalMailRecipients getAdditionalMailRecipients() {
		return additionalMailRecipients;
	}

	public void setAdditionalMailRecipients(AdditionalMailRecipients additionalMailRecipients) {
		this.additionalMailRecipients = additionalMailRecipients;
	}
}
