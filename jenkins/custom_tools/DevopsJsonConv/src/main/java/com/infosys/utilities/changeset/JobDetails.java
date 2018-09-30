/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.changeset;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "number","url","lastSuccessfulBuildId","lastCompletedBuildId","lastUnstableBuildId","lastUnsuccessfulBuildId","lastFailedBuildId", "score"})
@XmlRootElement(name = "lastBuild")
public class JobDetails {
	private String number;
	private String url;
	//private String lastBuildId;
	private String lastSuccessfulBuildId;
	private String lastCompletedBuildId;
	private String lastUnstableBuildId;
	private String lastUnsuccessfulBuildId;
	private String lastFailedBuildId;
		
/*	public String getLastBuildId() {
		return lastBuildId;
	}
	public void setLastBuildId(String lastBuildId) {
		this.lastBuildId = lastBuildId;
	}*/
	public String getLastFailedBuildId() {
		return lastFailedBuildId;
	}
	public void setLastFailedBuildId(String lastFailedBuildId) {
		this.lastFailedBuildId = lastFailedBuildId;
	}
	private String score;
	

	public String getLastSuccessfulBuildId() {
		return lastSuccessfulBuildId;
	}
	
	public void setLastSuccessfulBuildId(String lastSuccessfulBuildId) {
		this.lastSuccessfulBuildId = lastSuccessfulBuildId;
	}
	
	public String getLastCompletedBuildId() {
		return lastCompletedBuildId;
	}
	
	public void setLastCompletedBuildId(String lastCompletedBuildId) {
		this.lastCompletedBuildId = lastCompletedBuildId;
	}
	
	public String getLastUnstableBuildId() {
		return lastUnstableBuildId;
	}
	
	public void setLastUnstableBuildId(String lastUnstableBuildId) {
		this.lastUnstableBuildId = lastUnstableBuildId;
	}
	
	public String getLastUnsuccessfulBuildId() {
		return lastUnsuccessfulBuildId;
	}
	
	public void setLastUnsuccessfulBuildId(String lastUnsuccessfulBuildId) {
		this.lastUnsuccessfulBuildId = lastUnsuccessfulBuildId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getScore() {
		return score;
	}
	
	public void setScore(String score) {
		this.score = score;
	}
}
