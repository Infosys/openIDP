/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Istanbul {
	@JsonProperty("className")
	private String className;
	@JsonProperty("classCoverage")
	private String classCoverage;
	@JsonProperty("lineCoverage")
	private String lineCoverage;
	@JsonProperty("branchcoverage")
	private String branchCoverage;

	

	@JsonProperty("methodCoverage")
	private String methodCoverage;

	public String getBranchCoverage() {
		return branchCoverage;
	}

	public void setBranchCoverage(String branchCoverage) {
		this.branchCoverage = branchCoverage;
	}
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassCoverage() {
		return classCoverage;
	}

	public void setClassCoverage(String classCoverage) {
		this.classCoverage = classCoverage;
	}

	public String getLineCoverage() {
		return lineCoverage;
	}

	public void setLineCoverage(String lineCoverage) {
		this.lineCoverage = lineCoverage;
	}

	public String getMethodCoverage() {
		return methodCoverage;
	}

	public void setMethodCoverage(String methodCoverage) {
		this.methodCoverage = methodCoverage;
	}
}
