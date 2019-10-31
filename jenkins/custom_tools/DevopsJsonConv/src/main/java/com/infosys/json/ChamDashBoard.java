package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChamDashBoard {
	
	
	@SerializedName("crNumber")
	@Expose
	private String crNumber;
	
	@SerializedName("crDescription")
	@Expose
	private String crDescription;
	
	@SerializedName("crStatus")
	@Expose
	private String crStatus;
	
	@SerializedName("cdNumber")
	@Expose
	private String cdNumber;
	
	@SerializedName("cdType")
	@Expose
	private String cdType;
	
	@SerializedName("cdDescription")
	@Expose
	private String cdDescription;
	
	@SerializedName("cdFromStatus")
	@Expose
	private String cdFromStatus;
	
	@SerializedName("cdToStatus")
	@Expose
	private String cdToStatus;
	
	@SerializedName("trNumber")
	@Expose
	private String trNumber;
	
	@SerializedName("trDescription")
	@Expose
	private String trDescription;
	
	@SerializedName("trStatus")
	@Expose
	private String trStatus;
	
	@SerializedName("logs")
	@Expose
	private String logs;
	
	@SerializedName("status")
	@Expose
	private String status;
	
	
	

	public String getCrNumber() {
		return crNumber;
	}

	public void setCrNumber(String crNumber) {
		this.crNumber = crNumber;
	}

	public String getCrDescription() {
		return crDescription;
	}

	public void setCrDescription(String crDescription) {
		this.crDescription = crDescription;
	}

	public String getCrStatus() {
		return crStatus;
	}

	public void setCrStatus(String crStatus) {
		this.crStatus = crStatus;
	}

	public String getCdNumber() {
		return cdNumber;
	}

	public void setCdNumber(String cdNumber) {
		this.cdNumber = cdNumber;
	}

	public String getCdType() {
		return cdType;
	}

	public void setCdType(String cdType) {
		this.cdType = cdType;
	}

	public String getCdDescription() {
		return cdDescription;
	}

	public void setCdDescription(String cdDescription) {
		this.cdDescription = cdDescription;
	}

	public String getCdFromStatus() {
		return cdFromStatus;
	}

	public void setCdFromStatus(String cdFromStatus) {
		this.cdFromStatus = cdFromStatus;
	}

	public String getCdToStatus() {
		return cdToStatus;
	}

	public void setCdToStatus(String cdToStatus) {
		this.cdToStatus = cdToStatus;
	}

	public String getTrNumber() {
		return trNumber;
	}

	public void setTrNumber(String trNumber) {
		this.trNumber = trNumber;
	}

	public String getTrDescription() {
		return trDescription;
	}

	public void setTrDescription(String trDescription) {
		this.trDescription = trDescription;
	}

	public String getTrStatus() {
		return trStatus;
	}

	public void setTrStatus(String trStatus) {
		this.trStatus = trStatus;
	}

	
	
	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
	
	

}
