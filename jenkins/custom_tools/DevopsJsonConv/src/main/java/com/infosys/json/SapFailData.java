package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SapFailData {

	
	@SerializedName("cdNumber")
	@Expose
	private String cdNumber;
	
	@SerializedName("cdFromStatus")
	@Expose
	private String cdFromStatus;
	
	@SerializedName("cdToStatus")
	@Expose
	private String cdToStatus;
	
	@SerializedName("logs")
	@Expose
	private String logs;
	
	@SerializedName("status")
	@Expose
	private String status;

	public String getCdNumber() {
		return cdNumber;
	}

	public void setCdNumber(String cdNumber) {
		this.cdNumber = cdNumber;
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
