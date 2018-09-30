package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileNetImport {

	@SerializedName("triggerId")
	@Expose
	private Integer triggerId;

	@SerializedName("source")
	@Expose
	private String source;

	@SerializedName("destination")
	@Expose
	private String destination;

	public Integer getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(Integer triggerId) {
		this.triggerId = triggerId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "FileNetImport [triggerId=" + triggerId + ", source=" + source + ", destination=" + destination + "]";
	}

	

}