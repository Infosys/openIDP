/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.releasemanagerinfo;

import java.util.List;

import org.infy.idp.entities.jobs.basicinfo.AdditionalMailRecipients;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store release detail
 * 
 * @author Infosys
 *
 */
public class Release {
	@SerializedName("releaseNumber")
	@Expose
	private String releaseNumber;

	@SerializedName("vstsReleaseName")
	@Expose
	private String vstsReleaseName;
	@SerializedName("expectedStartDate")
	@Expose
	private String expectedStartDate;
	@SerializedName("expectedEndDate")
	@Expose
	private String expectedEndDate;
	@SerializedName("actualStartDate")
	@Expose
	private String actualStartDate;
	@SerializedName("actualEndDate")
	@Expose
	private String actualEndDate;
	@SerializedName("remarks")
	@Expose
	private String remarks;
	@SerializedName("historyRemarks")
	@Expose
	private String historyRemarks;
	@SerializedName("branchList")
	@Expose
	private List<String> branchList;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("additionalMailRecipients")
	@Expose
	private AdditionalMailRecipients additionalMailRecipients;
	@SerializedName("envPathList")
	@Expose
	private List<PathSequence> envPathList;

	public String getHistoryRemarks() {
		return historyRemarks;
	}

	public void setHistoryRemarks(String historyRemarks) {
		this.historyRemarks = historyRemarks;
	}

	public String getReleaseNumber() {
		return releaseNumber;
	}

	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	public String getVstsReleaseName() {
		return vstsReleaseName;
	}

	public void setVstsReleaseName(String vstsReleaseName) {
		this.vstsReleaseName = vstsReleaseName;
	}

	public String getExpectedStartDate() {
		return expectedStartDate;
	}

	public void setExpectedStartDate(String expectedStartDate) {
		this.expectedStartDate = expectedStartDate;
	}

	public String getExpectedEndDate() {
		return expectedEndDate;
	}

	public void setExpectedEndDate(String expectedEndDate) {
		this.expectedEndDate = expectedEndDate;
	}

	public String getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public String getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<String> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<String> branchList) {
		this.branchList = branchList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AdditionalMailRecipients getAdditionalMailRecipients() {
		return additionalMailRecipients;
	}

	public void setAdditionalMailRecipients(AdditionalMailRecipients additionalMailRecipients) {
		this.additionalMailRecipients = additionalMailRecipients;
	}

	public List<PathSequence> getEnvPathList() {
		return envPathList;
	}

	public void setEnvPathList(List<PathSequence> envPathList) {
		this.envPathList = envPathList;
	}

}
