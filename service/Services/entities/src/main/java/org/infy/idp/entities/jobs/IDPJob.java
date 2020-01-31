/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import java.util.List;


import org.infy.idp.entities.jobs.basicinfo.BasicInfo;
import org.infy.idp.entities.jobs.buildinfo.BuildInfo;
import org.infy.idp.entities.jobs.code.Code;
import org.infy.idp.entities.jobs.deployinfo.DeployInfo;
import org.infy.idp.entities.jobs.testinfo.TestInfo;
import org.infy.idp.entities.triggerparameter.ApplicationDetails;
import org.infy.idp.entities.models.Ngnjson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store complete IDP job details
 * 
 * @author Infosys
 *
 */
public class IDPJob {
	@SerializedName("basicInfo")
	@Expose
	private BasicInfo basicInfo;
	@SerializedName("code")
	@Expose
	private Code code;
	@SerializedName("buildInfo")
	@Expose
	private BuildInfo buildInfo;
	@SerializedName("deployInfo")
	@Expose
	private DeployInfo deployInfo;
	@SerializedName("testInfo")
	@Expose
	private TestInfo testInfo;
	@SerializedName("errorCode")
	@Expose
	private String errorCode;
    @SerializedName("ngnJson")
	@Expose
	private Ngnjson ngnJson;
	@SerializedName("ssoName")
	@Expose
	private String ssoName;
	@SerializedName("ssoId")
	@Expose
	private String ssoId;

	@SerializedName("pipelines")
	@Expose
	private List<ApplicationDetails> pipelines;

	public String getSsoName() {
		return ssoName;
	}

	public void setSsoName(String ssoName) {
		this.ssoName = ssoName;
	}

	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	public List<ApplicationDetails> getPipelines() {
		return pipelines;
	}

	public void setPipelines(List<ApplicationDetails> pipelines) {
		this.pipelines = pipelines;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public BasicInfo getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public BuildInfo getBuildInfo() {
		return buildInfo;
	}

	public void setBuildInfo(BuildInfo buildInfo) {
		this.buildInfo = buildInfo;
	}

	public DeployInfo getDeployInfo() {
		return deployInfo;
	}

	public void setDeployInfo(DeployInfo deployInfo) {
		this.deployInfo = deployInfo;
	}

	public TestInfo getTestInfo() {
		return testInfo;
	}

	public void setTestInfo(TestInfo testInfo) {
		this.testInfo = testInfo;
	}
	public Ngnjson getNgnJson() {
		return ngnJson;
	}
	public void setNgnJson(Ngnjson ngnJson) {
		this.ngnJson = ngnJson;
	}

}
