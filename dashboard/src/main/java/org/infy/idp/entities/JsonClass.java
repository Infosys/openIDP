/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import org.infy.idp.jsonschema.CodeMetric;
import org.infy.idp.jsonschema.SCMInfo;
import org.infy.idp.jsonschema.VersionInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"functionalTest","securityTest","codeQuality","codeCoverage","application", "buildId","buildDetails","buildOwners", "ruleSet", "codeMetric",
	"testCaseResult", "codeAnalysis", "versionInfo", "scmInfo","propertyTemplates","classDefinitions","others" })
public class JsonClass {

	/**
	 * 
	 */

	@JsonProperty("functionalTest")
	private Functional functionalTest;
	
	@JsonProperty("sonarDetails")
	private SonarDetails sonarDetails;
	
	public SonarDetails getSonarDetails() {
		return sonarDetails;
	}
	
	

	public void setSonarDetails(SonarDetails sonarDetails) {
		this.sonarDetails = sonarDetails;
	}

	@JsonProperty("securityTest")
	private SecurityTest securityTest;
	
	@JsonProperty("codeQuality")
	private CodeQuality codeQuality;
	
	@JsonProperty("codeCoverage")
	private Codecoverage codeCoverage;
	
	@JsonProperty("log")
	private String log;

	@JsonProperty("propertyTemplates")
	private List<FileNetExportPropertyType> propetyTemplates;
	
	
	
	
	
	@JsonProperty("folders")
	private List<FileNetExportFolderType> folders;
	
	@JsonProperty("documents")
	private List<FileNetExportDocumentType> documents;
	
	@JsonProperty("choiceLists")
	private List<FileNetExportChoiceListType> choiceLists;
	
	
	
	
	
	
	@JsonProperty("propertyTemplates")
	public List<FileNetExportPropertyType> getPropetyTemplates() {
		return propetyTemplates;
	}

	@JsonProperty("propertyTemplates")
	public void setPropetyTemplates(List<FileNetExportPropertyType> propetyTemplates) {
		this.propetyTemplates = propetyTemplates;
	}

	@JsonProperty("classDefinitions")
	private List<FileNetExportClassDefinitionType> classDefinitions;
	
	@JsonProperty("others")
	private List<FileNetExportOtherType> others;
	
	@JsonProperty("folders")
	public List<FileNetExportFolderType> getfolders() {
		return folders;
	}

	@JsonProperty("folders")
	public void setfolders(List<FileNetExportFolderType> folders) {
		this.folders = folders;
	}

	@JsonProperty("documents")
	public List<FileNetExportDocumentType> getdocuments() {
		return documents;
	}
	
	@JsonProperty("documents")
	public void setdocuments(List<FileNetExportDocumentType> documents) {
		this.documents = documents;
	}

	@JsonProperty("choiceLists")
	public List<FileNetExportChoiceListType> getchoiceLists() {
		return choiceLists;
	}

	@JsonProperty("choiceLists")
	public void setchoiceLists(List<FileNetExportChoiceListType> choiceLists) {
		this.choiceLists = choiceLists;
	}

	@JsonProperty("classDefinitions")
	public List<FileNetExportClassDefinitionType> getclassDefinitions() {
		return classDefinitions;
	}
	
	@JsonProperty("classDefinitions")
	public void setclassDefinitions(List<FileNetExportClassDefinitionType> classDefinitions) {
		this.classDefinitions = classDefinitions;
	}

	@JsonProperty("others")
	public List<FileNetExportOtherType> getothers() {
		return others;
	}

	@JsonProperty("others")
	public void setothers(List<FileNetExportOtherType> others) {
		this.others = others;
	}
	public String getLog() {
		return log;
	}



	public void setLog(String log) {
		this.log = log;
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

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}
	@JsonProperty("functionalTest")
	public void setFunctionalTest(Functional functionalTest) {
		this.functionalTest = functionalTest;
	}

	@JsonProperty("application")
	private String application;
	@JsonProperty("fileNet")
	private FileNet fileNet;
	@JsonProperty("buildId")
	private String buildId;

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

	@JsonProperty("coverageDetails")
	private List<CoverageDetails> coverageDetails=new ArrayList<>();
	@JsonProperty("versionInfo")
	private List<VersionInfo> versionInfo = null;

	@JsonProperty("scmInfo")
	private List<SCMInfo> scmInfo = null;
	
	

	

	public JsonClass() {
		super();
		this.application = "none";
		this.buildId = "0";
	}

	public JsonClass(String application, List<CodeMetric> codeMetric, List<TestCaseResult> testCaseResult,
			List<CodeAnalysis> codeAnalysis, List<RuleSet> ruleSet, List<VersionInfo> versionInfo,
				List<FileNetExportPropertyType> propertyTemplatesList,List<FileNetExportClassDefinitionType> classDefinitions,List<FileNetExportFolderType> folders,List<FileNetExportChoiceListType> choiceLists,
				List<FileNetExportOtherType> others) {
		super();
		this.application = application;
		this.codeMetric = codeMetric;
		this.testCaseResult = testCaseResult;
		this.codeAnalysis = codeAnalysis;
		this.ruleSet = ruleSet;
		this.versionInfo = versionInfo;
		this.propetyTemplates = propertyTemplatesList;
		this.classDefinitions=classDefinitions;
		this.folders=folders;
		this.choiceLists = choiceLists;
		this.others=others;
	
		
	}
	public List<CoverageDetails> getCoverageDetailsList() {
		return coverageDetails;
	}


	public void setCoverageDetailsList(List<CoverageDetails> coverageDetailsList) {
		this.coverageDetails = coverageDetailsList;
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
	@JsonProperty("fileNet")
	public FileNet getFileNet() {
		return fileNet;
	}

	@JsonProperty("fileNet")
	public void setFileNet(FileNet fileNet) {
		this.fileNet = fileNet;
	}
}
