/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoverageDetails {
	@JsonProperty("className")
	private String className;
	@JsonProperty("lineCoverage")
	private String lineCoverage;
	@JsonProperty("branchCoverage")
	private String branchCoverage;
	@JsonProperty("category")
	private String category;
	@JsonProperty("package")
	private String pckage;
	@JsonProperty("missedLineNumbers")
	private String missedLineNumbers;

	public String getPckage() {
		return pckage;
	}

	public void setPckage(String pckage) {
		this.pckage = pckage;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getLineCoverage() {
		return lineCoverage;
	}

	public void setLineCoverage(String lineCoverage) {
		this.lineCoverage = lineCoverage;
	}

	public String getBranchCoverage() {
		return branchCoverage;
	}

	public void setBranchCoverage(String branchCoverage) {
		this.branchCoverage = branchCoverage;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public CoverageDetails createCoverageDetails() {
		return new CoverageDetails();
	}

	public String getMissedLineNumbers() {
		return missedLineNumbers;
	}

	public void setMissedLineNumbers(String missedLineNumbers) {
		this.missedLineNumbers = missedLineNumbers;
	}
	
	
}
