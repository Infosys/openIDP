package com.infosys.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CodeCoverageDetails {
	@JsonProperty("branchCoverage")
	private String branchCoverage;
	@JsonProperty("classCoverage")
	private String classCoverage;
	@JsonProperty("complexityScore")
	private String complexityScore;
	@JsonProperty("instructionCoverage")
	private String instructionCoverage;
	@JsonProperty("lineCoverage")
	private String lineCoverage;
	@JsonProperty("methodCoverage")
	private String methodCoverage;
	
	@JsonProperty("branchCoverage")
	public String getBranchCoverage() {
		return branchCoverage;
	}
	@JsonProperty("branchCoverage")
	public void setBranchCoverage(String branchCoverage) {
		this.branchCoverage = branchCoverage;
	}
	@JsonProperty("classCoverage")
	public String getClassCoverage() {
		return classCoverage;
	}
	@JsonProperty("classCoverage")
	public void setClassCoverage(String classCoverage) {
		this.classCoverage = classCoverage;
	}
	@JsonProperty("complexityScore")
	public String getComplexityScore() {
		return complexityScore;
	}
	@JsonProperty("complexityScore")
	public void setComplexityScore(String complexityScore) {
		this.complexityScore = complexityScore;
	}
	@JsonProperty("instructionCoverage")
	public String getInstructionCoverage() {
		return instructionCoverage;
	}
	@JsonProperty("instructionCoverage")
	public void setInstructionCoverage(String instructionCoverage) {
		this.instructionCoverage = instructionCoverage;
	}
	@JsonProperty("lineCoverage")
	public String getLineCoverage() {
		return lineCoverage;
	}
	@JsonProperty("lineCoverage")
	public void setLineCoverage(String lineCoverage) {
		this.lineCoverage = lineCoverage;
	}
	@JsonProperty("methodCoverage")
	public String getMethodCoverage() {
		return methodCoverage;
	}
	@JsonProperty("methodCoverage")
	public void setMethodCoverage(String methodCoverage) {
		this.methodCoverage = methodCoverage;
	}
}
