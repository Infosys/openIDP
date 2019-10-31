package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JmeterViewResults {
//	timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,URL,Latency,IdleTime,Connect

	@SerializedName("timeStamp")
	@Expose
	private String timeStamp;
	
	@SerializedName("elapsed")
	@Expose
	private String elapsed;
	
	@SerializedName("label")
	@Expose
	private String label;
	
	@SerializedName("responseCode")
	@Expose
	private String responseCode;
	
	@SerializedName("responseMessage")
	@Expose
	private String responseMessage;
	
	@SerializedName("threadName")
	@Expose
	private String threadName;
	
	@SerializedName("dataType")
	@Expose
	private String dataType;
	
	@SerializedName("success")
	@Expose
	private String success;
	
	@SerializedName("failureMessage")
	@Expose
	private String failureMessage;
	
	@SerializedName("bytes")
	@Expose
	private String bytes;
	
	@SerializedName("sentBytes")
	@Expose
	private String sentBytes;
	
	@SerializedName("grpThreads")
	@Expose
	private String grpThreads;
	
	@SerializedName("allThreads")
	@Expose
	private String allThreads;
	
	@SerializedName("URL")
	@Expose
	private String URL;
	
	@SerializedName("latency")
	@Expose
	private String latency;
	
	@SerializedName("IdleTime")
	@Expose
	private String IdleTime;
	
	@SerializedName("connect")
	@Expose
	private String connect;
	

	
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getElapsed() {
		return elapsed;
	}

	public void setElapsed(String elapsed) {
		this.elapsed = elapsed;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public String getBytes() {
		return bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}

	public String getSentBytes() {
		return sentBytes;
	}

	public void setSentBytes(String sentBytes) {
		this.sentBytes = sentBytes;
	}

	public String getGrpThreads() {
		return grpThreads;
	}

	public void setGrpThreads(String grpThreads) {
		this.grpThreads = grpThreads;
	}

	public String getAllThreads() {
		return allThreads;
	}

	public void setAllThreads(String allThreads) {
		this.allThreads = allThreads;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	

	public String getLatency() {
		return latency;
	}

	public void setLatency(String latency) {
		this.latency = latency;
	}

	public String getConnect() {
		return connect;
	}

	public void setConnect(String connect) {
		this.connect = connect;
	}

	public String getIdleTime() {
		return IdleTime;
	}

	public void setIdleTime(String idleTime) {
		IdleTime = idleTime;
	}

	

	
}
