/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;

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
	
	public CodeAnalysis() {
		super();
		this.id = "none";
		this.severity = "low";
		this.message = "Some rule voilated";
		this.line = "0";
		this.ruleName = "default";
		this.recommendation = "default";
	}
	public CodeAnalysis(String id, String severity, String message) {
		super();
		this.id = id;
		this.severity = severity;
		this.message = message;
	}
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	

	

	/**
	 * 
	 * @return The application
	 */
	@JsonProperty("id")
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param application
	 *            The Application
	 */
	@JsonProperty("id")
	public void setId(String application) {
		this.id = application;
	}

	/**
	 * 
	 * @return The codeCoverage
	 */
	@JsonProperty("severity")
	public String getSeverity() {
		return severity;
	}

	/**
	 * 
	 * @param codeCoverage
	 *            The CodeCoverage
	 */
	@JsonProperty("severity")
	public void setSeverity(String codeCoverage) {
		this.severity = codeCoverage;
	}

	/**
	 * 
	 * @return The testResults
	 */
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param testResults
	 *            The TestResults
	 */
	@JsonProperty("message")
	public void setMessage(String testsuites) {
		this.message = testsuites;
	}

	@JsonProperty("line")
	public String getLine() {
		return line;
	}

	/**
	 * 
	 * @param testResults
	 *            The TestResults
	 */
	@JsonProperty("line")
	public void setLine(String testsuites) {
		this.line = testsuites;
	}

	@JsonProperty("ruleName")
	public String getruleName() {
		return ruleName;
	}

	/**
	 * 
	 * @param testResults
	 *            The TestResults
	 */
	@JsonProperty("ruleName")
	public void setruleName(String testsuites) {
		this.ruleName = testsuites;
	}

	/**
	 * 
	 * @param testResults
	 *            The TestResults
	 */
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

	/**
	 * 
	 * @param testResults
	 *            The TestResults
	 */
	@JsonProperty("category")
	public void setcategory(String testsuites) {
		this.category = testsuites;
	}
}
