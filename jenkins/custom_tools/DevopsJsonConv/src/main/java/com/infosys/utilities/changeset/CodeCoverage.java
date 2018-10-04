/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.changeset;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "branchCoverage", "classCoverage", "complexityScore", "instructionCoverage",
		"lineCoverage", "methodCoverage" })
@XmlRootElement(name = "previousResult")
public class CodeCoverage {
	private String branchCoverage;
	private String classCoverage;
	private String complexityScore;
	private String instructionCoverage;
	private String lineCoverage;
	private String methodCoverage;

	public String getBranchCoverage() {
		return branchCoverage;
	}

	public void setBranchCoverage(String branchCoverage) {
		this.branchCoverage = branchCoverage;
	}

	public String getClassCoverage() {
		return classCoverage;
	}

	public void setClassCoverage(String classCoverage) {
		this.classCoverage = classCoverage;
	}

	public String getComplexityScore() {
		return complexityScore;
	}

	public void setComplexityScore(String complexityScore) {
		this.complexityScore = complexityScore;
	}

	public String getInstructionCoverage() {
		return instructionCoverage;
	}

	public void setInstructionCoverage(String instructionCoverage) {
		this.instructionCoverage = instructionCoverage;
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
