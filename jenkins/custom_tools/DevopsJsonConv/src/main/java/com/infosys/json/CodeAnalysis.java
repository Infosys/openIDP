/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CodeAnalysis {
	@JsonProperty("id")
	private String id;
	@JsonProperty("severity")
	private String severity;
	@JsonProperty("message")
	private String message;
	@JsonProperty("line")
	private String line;
	@JsonProperty("ruleName")
	private String ruleName;
	@JsonProperty("category")
	private String category;
	@JsonProperty("recommendation")
	private String recommendation;
	@JsonProperty("className")
	private String className;
	@JsonProperty("type")
	private String type;
	@JsonProperty("project")
	private String project;
	

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public CodeAnalysis() {
		super();
		this.id = "none";
		this.severity = "low";
		this.message = "Some rule voilated";
		this.line = "0";
		this.ruleName = "default";
		this.recommendation = "default";
		this.className = "default";
		this.project="default";
		this.type="default";
	}

	public CodeAnalysis(String id, String severity, String message) {
		super();
		this.id = id;
		this.severity = severity;
		this.message = message;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String application) {
		this.id = application;
	}

	@JsonProperty("severity")
	public String getSeverity() {
		return severity;
	}

	@JsonProperty("severity")
	public void setSeverity(String codeCoverage) {
		this.severity = codeCoverage;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String testsuites) {
		this.message = testsuites;
	}

	@JsonProperty("line")
	public String getLine() {
		return line;
	}

	@JsonProperty("line")
	public void setLine(String testsuites) {
		this.line = testsuites;
	}

	@JsonProperty("ruleName")
	public String getruleName() {
		return ruleName;
	}

	@JsonProperty("ruleName")
	public void setruleName(String testsuites) {
		this.ruleName = testsuites;
	}

	@JsonProperty("recommendation")
	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	@JsonProperty("category")
	public String getcategory() {
		return category;
	}

	@JsonProperty("category")
	public void setcategory(String testsuites) {
		this.category = testsuites;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("project")
	public String getProject() {
		return project;
	}

	@JsonProperty("project")
	public void setProject(String project) {
		this.project = project;
	}
	
}
