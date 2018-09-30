/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.getjob;

import java.util.List;

import org.infy.idp.entities.ge.jenkins.Performance;
import org.infy.idp.entities.ge.jenkins.SonarAnalysisInfo;
import org.infy.idp.entities.ge.jenkins.TestAnalysis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store response of jenkins
 * 
 * @author Infosys
 *
 */

public class GetResponse {
	@SerializedName("JobName")
	@Expose
	private String mainJob;
	@SerializedName("DashboardLink")
	@Expose
	private String dashboard;
	@SerializedName("PipelineStatus")
	@Expose
	private String pipelineStatus;
	@SerializedName("SonarAnalysis")
	@Expose
	private SonarAnalysisInfo sonar;
	@SerializedName("TestResult")
	@Expose
	private List<TestAnalysis> functionalTest;
	@SerializedName("PerformanceTest")
	@Expose
	private List<Performance> performanceTest;

	public List<Performance> getPerformanceTest() {
		return performanceTest;
	}

	public void setPerformanceTest(List<Performance> performanceTest) {
		this.performanceTest = performanceTest;
	}

	public String getMainJob() {
		return mainJob;
	}

	public void setMainJob(String mainJob) {
		this.mainJob = mainJob;
	}

	public SonarAnalysisInfo getSonar() {
		return sonar;
	}

	public void setSonar(SonarAnalysisInfo sonar) {
		this.sonar = sonar;
	}

	public List<TestAnalysis> getFunctionalTest() {
		return functionalTest;
	}

	public void setFunctionalTest(List<TestAnalysis> functionalTest) {
		this.functionalTest = functionalTest;
	}

	public String getDashboard() {
		return dashboard;
	}

	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}

	public String getPipelineStatus() {
		return pipelineStatus;
	}

	public void setPipelineStatus(String pipelineStatus) {
		this.pipelineStatus = pipelineStatus;
	}

}
