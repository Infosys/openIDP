package com.infosys.json;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CsvUpload {

	@SerializedName("appid")
	@Expose
	private String appid;
	@SerializedName("pipelinename")
	@Expose
	private String pipelinename;
	@SerializedName("applicationName")
	@Expose
	private String applicationName;
	@SerializedName("environment")
	@Expose
	private String environment;
	@SerializedName("ack_status")
	@Expose
	private String ack_status;
	@SerializedName("filename")
	@Expose
	private String filename;
	@SerializedName("pipelineno")
	@Expose
	private Integer pipelineno;
	public Integer getPipelineno()
	{
		return pipelineno;
	}
	public void setPipelineno(Integer pipelineno) {
		this.pipelineno = pipelineno;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPipelinename() {
		return pipelinename;
	}
	public void setPipelinename(String pipelinename) {
		this.pipelinename = pipelinename;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getAck_status() {
		return ack_status;
	}
	public void setAck_status(String ack_status) {
		this.ack_status = ack_status;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}		
	}


