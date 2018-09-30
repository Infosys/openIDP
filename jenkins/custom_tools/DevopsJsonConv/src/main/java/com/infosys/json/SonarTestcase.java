package com.infosys.json;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SonarTestcase {
	
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("time")
	@Expose
	private String time;
	@SerializedName("failures")
	@Expose
	private List<SoapUIFailure> failures;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<SoapUIFailure> getFailures() {
		return failures;
	}
	public void setFailures(List<SoapUIFailure> failures) {
		this.failures = failures;
	}
	
	
	
}
