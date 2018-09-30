package com.infosys.json;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SoapUIReport {
	
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("tests")
	@Expose
	private String tests;
	@SerializedName("failures")
	@Expose
	private String failures;
	@SerializedName("errors")
	@Expose
	private String errors;
	@SerializedName("time")
	@Expose
	private String time;
	@SerializedName("testcase")
	@Expose
	private List<SoapUITestcase> testcase;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTests() {
		return tests;
	}
	public void setTests(String tests) {
		this.tests = tests;
	}
	public String getFailures() {
		return failures;
	}
	public void setFailures(String failures) {
		this.failures = failures;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<SoapUITestcase> getTestcase() {
		return testcase;
	}
	public void setTestcase(List<SoapUITestcase> testcase) {
		this.testcase = testcase;
	}
	
	
}
