/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.triggerparameter;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store build details for trigger
 * 
 * @author Infosys
 *
 */
public class Build {

	@SerializedName("branchSelected")
	@Expose
	private String branchSelected;

	@SerializedName("module")
	@Expose
	private List<String> module = null;

	@SerializedName("oldVersion")
	@Expose
	private String oldVersion;
	@SerializedName("codeAnalysis")
	@Expose
	private String codeAnalysis;
	@SerializedName("unitTest")
	@Expose
	private String unitTest;
	@SerializedName("cast")
	@Expose
	private String cast;
	@SerializedName("currentDate")
	@Expose
	private String currentDate;
	@SerializedName("newVersion")
	@Expose
	private String newVersion;

	@SerializedName("manifestFileName")
	@Expose
	private String manifestFileName;
	@SerializedName("srcEnv")
	@Expose
	private String srcEnv;

	public String getSrcEnv() {
		return srcEnv;
	}

	public void setSrcEnv(String srcEnv) {
		this.srcEnv = srcEnv;
	}

	public String getManifestFileName() {
		return manifestFileName;
	}

	public void setManifestFileName(String manifestFileName) {
		this.manifestFileName = manifestFileName;
	}

	public String getBranchSelected() {
		return branchSelected;
	}

	public void setBranchSelected(String branchSelected) {
		this.branchSelected = branchSelected;
	}

	public List<String> getModule() {
		return module;
	}

	public void setModule(List<String> module) {
		this.module = module;
	}

	public String getOldVersion() {
		return oldVersion;
	}

	public void setOldVersion(String oldVersion) {
		this.oldVersion = oldVersion;
	}

	public String getCodeAnalysis() {
		return codeAnalysis;
	}

	public void setCodeAnalysis(String codeAnalysis) {
		this.codeAnalysis = codeAnalysis;
	}

	public String getUnitTest() {
		return unitTest;
	}

	public void setUnitTest(String unitTest) {
		this.unitTest = unitTest;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}

}
