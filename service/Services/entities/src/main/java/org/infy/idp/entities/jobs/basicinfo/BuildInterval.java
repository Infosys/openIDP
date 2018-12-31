/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.basicinfo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store build interval information
 * 
 * @author Infosys
 *
 */
public class BuildInterval {
	@SerializedName("pollSCM")
	@Expose
	private String pollSCM;
	@SerializedName("buildInterval")
	@Expose
	private String buildIntrvl;
	@SerializedName("buildIntervalValue")
	@Expose
	private String buildIntervalValue;

	@SerializedName("projectKey")
	@Expose
	private String projectKey;
	@SerializedName("buildAtSpecificInterval")
	@Expose
	private String buildAtSpecificInterval;

	@SerializedName("event")
	@Expose
	private List<Event> event;

	@SerializedName("appServer")
	@Expose
	private String appServer;

	@SerializedName("systemNumber")
	@Expose
	private String systemNumber;

	@SerializedName("clientID")
	@Expose
	private String clientID;

	@SerializedName("solmanuserName")
	@Expose
	private String solmanuserName;

	@SerializedName("solmanpassword")
	@Expose
	private String solmanpassword;

	@SerializedName("lang")
	@Expose
	private String lang;

	//Add VSTS ALM
//	@SerializedName("userStory")
//	@Expose
//	private String userStory;
//
//	@SerializedName("pollALM")
//	@Expose
//	private String pollALM;
//	
//	@SerializedName("almTool")
//	@Expose
//	private String almTool;

	
	
	
//	public String getUserStory() {
//		return userStory;
//	}
//
//	public void setUserStory(String userStory) {
//		this.userStory = userStory;
//	}
//
//	public String getPollALM() {
//		return pollALM;
//	}
//
//	public void setPollALM(String pollALM) {
//		this.pollALM = pollALM;
//	}
//
//	public String getAlmTool() {
//		return almTool;
//	}
//
//	public void setAlmTool(String almTool) {
//		this.almTool = almTool;
//	}

	public String getBuildAtSpecificInterval() {
		return buildAtSpecificInterval;
	}

	public void setBuildAtSpecificInterval(String buildAtSpecificInterval) {
		this.buildAtSpecificInterval = buildAtSpecificInterval;
	}

	public List<Event> getEvent() {
		return event;
	}

	public void setEvent(List<Event> event) {
		this.event = event;
	}

	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	public String getPollSCM() {
		return pollSCM;
	}

	public void setPollSCM(String pollSCM) {
		this.pollSCM = pollSCM;
	}

	public String getBuildInterval() {
		return buildIntrvl;
	}

	public void setBuildInterval(String buildInterval) {
		this.buildIntrvl = buildInterval;
	}

	public String getBuildIntervalValue() {
		return buildIntervalValue;
	}

	public void setBuildIntervalValue(String buildIntervalValue) {
		this.buildIntervalValue = buildIntervalValue;
	}

	public String getBuildIntrvl() {
		return buildIntrvl;
	}

	public void setBuildIntrvl(String buildIntrvl) {
		this.buildIntrvl = buildIntrvl;
	}

	public String getAppServer() {
		return appServer;
	}

	public void setAppServer(String appServer) {
		this.appServer = appServer;
	}

	public String getSystemNumber() {
		return systemNumber;
	}

	public void setSystemNumber(String systemNumber) {
		this.systemNumber = systemNumber;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getSolmanuserName() {
		return solmanuserName;
	}

	public void setSolmanuserName(String solmanuserName) {
		this.solmanuserName = solmanuserName;
	}

	public String getSolmanpassword() {
		return solmanpassword;
	}

	public void setSolmanpassword(String solmanpassword) {
		this.solmanpassword = solmanpassword;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
