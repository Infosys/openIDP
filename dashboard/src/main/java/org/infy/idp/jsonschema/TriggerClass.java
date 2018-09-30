/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.jsonschema;

public class TriggerClass {
private int triggerId;
private int pipelineId;
private String releaseNumber;
private String triggerTime;
private String version;
private String buildStatus;
private String deployStatus;
private String testStatus;
private String buildTriggered;
private String deployTriggered;
private String testTriggered;
private String tfsWorkitem;
private String environment;
private String userName;
private String artifactID;
private int count;
public int getTriggerId() {
	return triggerId;
}
public void setTriggerId(int triggerId) {
	this.triggerId = triggerId;
}
public int getPipelineId() {
	return pipelineId;
}
public void setPipelineId(int pipelineId) {
	this.pipelineId = pipelineId;
}
public String getReleaseNumber() {
	return releaseNumber;
}
public void setReleaseNumber(String releaseNumber) {
	this.releaseNumber = releaseNumber;
}
public String getTriggerTime() {
	return triggerTime;
}
public void setTriggerTime(String triggerTime) {
	this.triggerTime = triggerTime;
}
public String getVersion() {
	return version;
}
public void setVersion(String version) {
	this.version = version;
}
public String getBuildStatus() {
	return buildStatus;
}
public void setBuildStatus(String buildStatus) {
	this.buildStatus = buildStatus;
}
public String getDeployStatus() {
	return deployStatus;
}
public void setDeployStatus(String deployStatus) {
	this.deployStatus = deployStatus;
}
public String getTestStatus() {
	return testStatus;
}
public void setTestStatus(String testStatus) {
	this.testStatus = testStatus;
}
public String getBuildTriggered() {
	return buildTriggered;
}
public void setBuildTriggered(String buildTriggered) {
	this.buildTriggered = buildTriggered;
}
public String getDeployTriggered() {
	return deployTriggered;
}
public void setDeployTriggered(String deployTriggered) {
	this.deployTriggered = deployTriggered;
}
public String getTestTriggered() {
	return testTriggered;
}
public void setTestTriggered(String testTriggered) {
	this.testTriggered = testTriggered;
}
public String getTfsWorkitem() {
	return tfsWorkitem;
}
public void setTfsWorkitem(String tfsWorkitem) {
	this.tfsWorkitem = tfsWorkitem;
}
public String getEnvironment() {
	return environment;
}
public void setEnvironment(String environment) {
	this.environment = environment;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getArtifactID() {
	return artifactID;
}
public void setArtifactID(String artifactID) {
	this.artifactID = artifactID;
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}



}
