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

import org.infy.idp.jsonschema.SCMInfo;

public class EntityWrapper {
	private List<BuildDetail> buildinfo;
	private List<String> coverageinfo;
	private List<CoverageDetails> coveragedetails;
	private List<CodeAnalysis> codeAnalysisinfo;
	private List<TestCaseResult> testAnalysisinfo;
	private List<SCMInfo> scmInfoList;

	public EntityWrapper() {
		buildinfo = new ArrayList();
		coverageinfo = new ArrayList();
		coveragedetails = new ArrayList();
		codeAnalysisinfo = new ArrayList();
		testAnalysisinfo = new ArrayList();
		scmInfoList = new ArrayList();
	}

	public List<BuildDetail> getBuildinfo() {
		return buildinfo;
	}

	public void setBuildinfo(List<BuildDetail> buildinfo) {
		this.buildinfo = buildinfo;
	}

	public List<String> getCoverageinfo() {
		return coverageinfo;
	}

	public void setCoverageinfo(List<String> coverageinfo) {
		this.coverageinfo = coverageinfo;
	}

	public List<CoverageDetails> getCoveragedetails() {
		return coveragedetails;
	}

	public void setCoveragedetails(List<CoverageDetails> coveragedetails) {
		this.coveragedetails = coveragedetails;
	}

	public List<CodeAnalysis> getCodeAnalysisinfo() {
		return codeAnalysisinfo;
	}

	public void setCodeAnalysisinfo(List<CodeAnalysis> codeAnalysisinfo) {
		this.codeAnalysisinfo = codeAnalysisinfo;
	}

	public List<TestCaseResult> getTestAnalysisinfo() {
		return testAnalysisinfo;
	}

	public void setTestAnalysisinfo(List<TestCaseResult> testAnalysisinfo) {
		this.testAnalysisinfo = testAnalysisinfo;
	}

	public List<SCMInfo> getScmInfoList() {
		return scmInfoList;
	}

	public void setScmInfoList(List<SCMInfo> scmInfoList) {
		this.scmInfoList = scmInfoList;
	}

}
