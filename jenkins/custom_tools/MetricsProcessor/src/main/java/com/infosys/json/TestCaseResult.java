/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "testSuiteName", "status", "message", "category", "startTime", "duration" })
public class TestCaseResult {
	@JsonProperty("id")
	private String id;
	@JsonProperty("className")
	private String className;
	@JsonProperty("message")
	private String message;
	@JsonProperty("testSuiteName")
	private String testSuiteName;
	@JsonProperty("category")
	private String category;
	@JsonProperty("status")
	private String status;
	@JsonProperty("startTime")
	private String startTime;
	@JsonProperty("duration")
	private String duration;
	@JsonProperty("testCaseName")
	private String testCaseName;
	@JsonProperty("testToolName")
	private String testToolName;
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}


	public TestCaseResult() {
		super();
		this.id = "none";
		this.message = "Test Case Passed";
		this.testSuiteName = "none";
		this.status = "passed";
		this.duration = "none";
		this.startTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
	}

	public TestCaseResult(String id, String message, String testSuiteName, String category, String startTime,
			String duration) {
		super();
		this.id = id;
		this.message = message;
		this.testSuiteName = testSuiteName;
		this.category = category;
		this.startTime = startTime;
		this.duration = duration;
	}

	
	@JsonProperty("testSuiteName")
	public String getTestSuiteName() {
		return testSuiteName;
	}

	@JsonProperty("testSuiteName")
	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	@JsonProperty("testCaseName")
	public String getTestCaseName() {
		return testCaseName;
	}

	@JsonProperty("testCaseName")
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	@JsonProperty("testToolName")
	public String getTestToolName() {
		return testToolName;
	}

	@JsonProperty("testToolName")
	public void setTestToolName(String testToolName) {
		this.testToolName = testToolName;
	}

	@JsonProperty("category")
	public String getCategory() {
		return category;
	}

	@JsonProperty("category")
	public void setCategory(String hostname) {
		this.category = hostname;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String classname) {
		this.id = classname;
	}

	@JsonProperty("testSuiteName")
	public String gettestSuiteName() {
		return testSuiteName;
	}

	@JsonProperty("testSuiteName")
	public void settestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String status) {
		this.message = status;
	}

	@JsonProperty("startTime")
	public String getStartTime() {
		return startTime;
	}

	@JsonProperty("startTime")
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@JsonProperty("duration")
	public String getDuration() {
		return duration;
	}

	@JsonProperty("duration")
	public void setDuration(String duration) {
		this.duration = duration;
	}
}
