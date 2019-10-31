/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infosys.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.infosys.jsonschema.CodeMetric;
import com.infosys.jsonschema.SCMInfo;
import com.infosys.jsonschema.VersionInfo;



@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "groupId", "groupName", "pipelineName", "sonarDetails", "performanceTest", "serviceTest",
		"functionalTest", "securityTest", "codeQuality", "codeCoverage", "application", "buildId", "buildDetails",
		"buildOwners", "ruleSet", "codeMetric", "testCaseResult", "codeAnalysis", "versionInfo", "scmInfo" })
public class JsonClass {

	/**
	 * 
	 */

	// added later
	@JsonProperty("sonarDetails")
	private SonarDetails sonarDetails;
	
	@JsonProperty("pipelineName")
	private String pipelineName;

	@JsonProperty("groupId")
	private String groupId;
	
	@JsonProperty("groupName")
	private String groupName;

	@JsonProperty("ssoId")
	private String ssoId;
	
	@JsonProperty("log")
	private String log;
	
	private String BaseURL;
	
	@JsonProperty("reports")
	private Reports Reports;
	
/*	@JsonProperty("unitTesting")
	private UnitTesting unitTesting;*/
	
	@JsonProperty("functionalTest")
	private Functional functionalTest;

	@JsonProperty("serviceTest")
	private ServiceTest serviceTest;
	
	@JsonProperty("performanceTest")
	private Performance performanceTest;
	
	@JsonProperty("securityTest")
	private SecurityTest securityTest;
	
	@JsonProperty("codeQuality")
	private CodeQuality codeQuality;
	
	@JsonProperty("codeCoverage")
	private Codecoverage codeCoverage;
	
	@JsonProperty("application")
	private String application;

	@JsonProperty("buildId")
	private String buildId;
	
	@JsonProperty("jobBuildId")
	private String jobBuildId;

	@JsonProperty("buildDetails")
	private List<BuildDetail> buildDetails;
	
	@JsonProperty("buildOwners")
	private List<BuildOwner> buildOwners = null;

	@JsonProperty("ruleSet")
	private List<RuleSet> ruleSet;

	@JsonProperty("codeMetric")
	private List<CodeMetric> codeMetric = null;

	@JsonProperty("testCaseResult")
	private List<TestCaseResult> testCaseResult = null;

	@JsonProperty("codeAnalysis")
	private List<CodeAnalysis> codeAnalysis = new ArrayList<>();

	@JsonProperty("CoverageDetails")
	private List <CoverageDetails> coverageDetails;
	
	@JsonProperty("versionInfo")
	private List<VersionInfo> versionInfo = null;
	
	@JsonProperty("scmInfo")
	private List<SCMInfo> scmInfo = null;

	@JsonProperty("fileNet")
	private  FileNet fileNet;
	
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
	public void setBaseURL(String baseURL) {
		BaseURL = baseURL;
	}
	public String getBaseURL() {
		return BaseURL;
	}
	
		
/*	@JsonProperty("unitTesting")
	public UnitTesting getUnitTesting() {
		return unitTesting;
	}
	
	@JsonProperty("unitTesting")
	public void setUnitTesting(UnitTesting unitTesting) {
		this.unitTesting = unitTesting;
	}*/
	
	@JsonProperty("groupId")
	public String getGroupId() {
		return groupId;
	}
	
	@JsonProperty("groupId")
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@JsonProperty("groupName")
	public String getGroupName() {
		return groupName;
	}
	
	@JsonProperty("groupName")
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@JsonProperty("ssoId")
	public String getSsoId() {
		return ssoId;
	}
	
	@JsonProperty("ssoId")
	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}
	@JsonProperty("codeCoverage")
	public Codecoverage getCodecoverage() {
		return codeCoverage;
	}


	@JsonProperty("codeCoverage")
	public void setCodecoverage(Codecoverage codeCoverage) {
		this.codeCoverage = codeCoverage;
	}



	@JsonProperty("codeQuality")
	public CodeQuality getCodeQuality() {
		return codeQuality;
	}
	
	
	
	@JsonProperty("codeQuality")
	public void setCodeQuality(CodeQuality codeQuality) {
		this.codeQuality = codeQuality;
	}
	@JsonProperty("SecurityTest")
	public SecurityTest getSecurityTest() {
		return securityTest;
	}
	@JsonProperty("SecurityTest")
	public void setSecurityTest(SecurityTest securityTest) {
		this.securityTest = securityTest;
	}
	@JsonProperty("functionalTest")
	public Functional getFunctionalTest() {
		return functionalTest;
	}
	@JsonProperty("functionalTest")
	public void setFunctionalTest(Functional functionalTest) {
		this.functionalTest = functionalTest;
	}

	

	public List<CoverageDetails> getCoverageDetails() {
		return coverageDetails;
	}
	public void setCoverageDetails() {
		if (this.coverageDetails==null) this.coverageDetails=new ArrayList();
		
	}
	public void addCoverageDetails(CoverageDetails c)
	{
		this.coverageDetails.add(c);
	}
	
	@JsonProperty("sonarDetails")
	public SonarDetails getSonarDetails() {
		return sonarDetails;
	}
	@JsonProperty("sonarDetails")
	public void setSonarDetails(SonarDetails sonarDetails) {
		this.sonarDetails = sonarDetails;
	}
	
	

	@JsonProperty("fileNet")
	public FileNet getFileNet() {
		return fileNet;
	}


	@JsonProperty("fileNet")
	public void setFileNet(FileNet fileNet) {
		this.fileNet = fileNet;
	}
	public JsonClass() {
		super();
		this.application = "none";
		this.buildId = "0";
	}

	public JsonClass(String application, List<CodeMetric> codeMetric, List<TestCaseResult> testCaseResult,
			List<CodeAnalysis> codeAnalysis, List<RuleSet> ruleSet, List<VersionInfo> versionInfo) {
		super();
		this.application = application;
		this.codeMetric = codeMetric;
		this.testCaseResult = testCaseResult;
		this.codeAnalysis = codeAnalysis;
		this.ruleSet = ruleSet;
		this.versionInfo = versionInfo;
	}
	
	
	@JsonProperty("buildDetails")
	public List<BuildDetail> getBuildDetails() {
		return buildDetails;
	}
	@JsonProperty("buildDetails")
	public void setBuildDetails(List<BuildDetail> buildDetails) {
		this.buildDetails = buildDetails;
	}

	@JsonProperty("buildOwners")
	public List<BuildOwner> getBuildOwners() {
		return buildOwners;
	}

	@JsonProperty("buildOwners")
	public void setBuildOwners(List<BuildOwner> buildOwners) {
		this.buildOwners = buildOwners;
	}

	@JsonProperty("application")
	public String getApplication() {
		return application;
	}

	@JsonProperty("buildId")
	public void setbuildId(String application) {
		this.buildId = application;
	}

	@JsonProperty("buildId")
	public String getbuildId() {
		return buildId;
	}
	
	@JsonProperty("jobBuildId")
	public String getJobBuildId() {
		return jobBuildId;
	}
	
	@JsonProperty("jobBuildId")
	public void setJobBuildId(String jobBuildId) {
		this.jobBuildId = jobBuildId;
	}
	@JsonProperty("application")
	public void setApplication(String application) {
		this.application = application;
	}

	@JsonProperty("codeMetric")
	public List<CodeMetric> getCodeMetric() {
		return codeMetric;
	}

	@JsonProperty("codeMetric")
	public void setCodeMetric(List<CodeMetric> codeMetric) {
		this.codeMetric = codeMetric;
	}

	@JsonProperty("testCaseResult")
	public List<TestCaseResult> getTestCaseResult() {
		return testCaseResult;
	}

	@JsonProperty("testCaseResult")
	public void setTestCaseResult(List<TestCaseResult> testCaseResult) {
		this.testCaseResult = testCaseResult;
	}

	@JsonProperty("codeAnalysis")
	public List<CodeAnalysis> getCodeAnalysis() {
		return codeAnalysis;
	}

	@JsonProperty("codeAnalysis")
	public void setCodeAnalysis(List<CodeAnalysis> CodeAnalysis) {
		this.codeAnalysis = CodeAnalysis;
	}

	@JsonProperty("ruleSet")
	public List<RuleSet> getRuleSet() {
		return ruleSet;
	}

	@JsonProperty("ruleSet")
	public void setRuleSet(List<RuleSet> ruleSet) {
		this.ruleSet = ruleSet;
	}

	@JsonProperty("versionInfo")
	public List<VersionInfo> getVersionInfo() {
		return versionInfo;
	}

	@JsonProperty("versionInfo")
	public void setVersionInfo(List<VersionInfo> versionInfo) {
		this.versionInfo = versionInfo;
	}

	@JsonProperty("scmInfo")	
	public List<SCMInfo> getScmInfo() {
		return scmInfo;
	}

	@JsonProperty("scmInfo")
	public void setScmInfo(List<SCMInfo> scmInfo) {
		this.scmInfo = scmInfo;
	}
	
	@JsonProperty("performanceTest")
	public Performance getPerformanceTest() {
		return performanceTest;
	}

	@JsonProperty("performanceTest")
	public void setPerformanceTest(Performance performanceTest) {
		this.performanceTest = performanceTest;
	}
	
	@JsonProperty("pipelineName")
	public String getPipelineName() {
		return pipelineName;
	}
	@JsonProperty("pipelineName")
	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	
	@JsonProperty("serviceTest")
	public ServiceTest getServiceTest() {
		return serviceTest;
	}

	@JsonProperty("serviceTest")
	public void setServiceTest(ServiceTest serviceTest) {
		this.serviceTest = serviceTest;
	}
	
		@JsonProperty("reports")
	public Reports getReports() {
		return this.Reports;
	}
	@JsonProperty("reports")
	public void setReports(Reports reports) {
		this.Reports = reports;
	}
	@Override
	public String toString() {
		return "JsonClass [sonarDetails=" + sonarDetails + ", pipelineName=" + pipelineName + ", groupId=" + groupId
				+ ", groupName=" + groupName + ", ssoId=" + ssoId + ", log=" + log + ", BaseURL=" + BaseURL
				+ ", Reports=" + Reports + ", functionalTest=" + functionalTest + ", serviceTest=" + serviceTest
				+ ", performanceTest=" + performanceTest + ", securityTest=" + securityTest + ", codeQuality="
				+ codeQuality + ", codeCoverage=" + codeCoverage + ", application=" + application + ", buildId="
				+ buildId + ", jobBuildId=" + jobBuildId + ", buildDetails=" + buildDetails + ", buildOwners="
				+ buildOwners + ", ruleSet=" + ruleSet + ", codeMetric=" + codeMetric + ", testCaseResult="
				+ testCaseResult + ", codeAnalysis=" + codeAnalysis + ", coverageDetails=" + coverageDetails
				+ ", versionInfo=" + versionInfo + ", scmInfo=" + scmInfo + ", fileNet=" + fileNet + "]";
	}
	
	
	
}
