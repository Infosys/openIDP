/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.triggerparameter;

import java.util.ArrayList;
import java.util.List;

import org.infy.entities.triggerinputs.DeployArtifact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store deploy details for trigger
 * 
 * @author Infosys
 *
 */
public class Deploy {

	@SerializedName("version")
	@Expose
	private String version;
	@SerializedName("artifactID")
	@Expose
	private String artifactID;
	@SerializedName("nexusId")
	@Expose
	private String nexusId;
	@SerializedName("deploymentType")
	@Expose
	private String deploymentType;
	@SerializedName("deployArtifact")
	@Expose
	private DeployArtifact deployArtifact;

	@SerializedName("dbDeployOperation")
	@Expose
	private String dbDeployOperation;

	@SerializedName("dbDeployRollbackType")
	@Expose
	private String dbDeployRollbackType;

	@SerializedName("dbDeployRollbackValue")
	@Expose
	private String dbDeployRollbackValue;

	@SerializedName("dbDeployFailSafe")
	@Expose
	private String dbDeployFailSafe;

	@SerializedName("deployStep")
	@Expose
	private ArrayList<String> deployStep;
	@SerializedName("dbDeployPipelineOwners")
	@Expose
	private String dbDeployPipelineOwners;
	@SerializedName("dbDeployPipelineList")
	@Expose
	private String dbDeployPipelineList;

	@SerializedName("subModule")
	@Expose
	private List<String> subModule = null;

	@SerializedName("deployModule")
	@Expose
	private List<String> deployModule;

	@SerializedName("manifestFileName")
	@Expose
	private String manifestFileName;
	@SerializedName("destEnvOwner")
	@Expose
	private String destEnvOwner;
	@SerializedName("sourceEnvName")
	@Expose
	private String sourceEnvName;
	@SerializedName("pairName")
	@Expose
	private String pairName;
	@SerializedName("allEnvOwner")
	@Expose
	private String allEnvOwner;
	@SerializedName("srcEnv")
	@Expose
	private String srcEnv;

	@SerializedName("password")
	@Expose
	private String password;

	@SerializedName("destLandscape")
	@Expose
	private String destLandscape;

	@SerializedName("deployOperationSelected")
	@Expose
	private String deployOperationSelected;

	public String getDeployOperationSelected() {
		return deployOperationSelected;
	}

	public void setDeployOperationSelected(String deployOperationSelected) {
		this.deployOperationSelected = deployOperationSelected;
	}

	public String getDestEnvOwner() {
		return destEnvOwner;
	}

	public void setDestEnvOwner(String destEnvOwner) {
		this.destEnvOwner = destEnvOwner;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSrcEnv() {
		return srcEnv;
	}

	public void setSrcEnv(String srcEnv) {
		this.srcEnv = srcEnv;
	}

	public String getAllEnvOwner() {
		return allEnvOwner;
	}

	public void setAllEnvOwner(String allEnvOwner) {
		this.allEnvOwner = allEnvOwner;
	}

	public String getDestLandscape() {
		return destLandscape;
	}

	public void setDestLandscape(String destLandscape) {
		this.destLandscape = destLandscape;
	}

	public String getPairName() {
		return pairName;
	}

	public void setPairName(String pairName) {
		this.pairName = pairName;
	}

	public String getSourceEnvName() {
		return sourceEnvName;
	}

	public void setSourceEnvName(String sourceEnvName) {
		this.sourceEnvName = sourceEnvName;
	}

	public String getManifestFileName() {
		return manifestFileName;
	}

	public void setManifestFileName(String manifestFileName) {
		this.manifestFileName = manifestFileName;
	}

	public List<String> getDeployModule() {
		return deployModule;
	}

	public void setDeployModule(List<String> deployModule) {
		this.deployModule = deployModule;
	}

	public ArrayList<String> getDeployStep() {
		return deployStep;
	}

	public void setDeployStep(ArrayList<String> deployStep) {
		this.deployStep = deployStep;
	}

	public DeployArtifact getDeployArtifact() {
		return deployArtifact;
	}

	public void setDeployArtifact(DeployArtifact deployArtifact) {
		this.deployArtifact = deployArtifact;
	}

	public String getDeploymentType() {
		return deploymentType;
	}

	public void setDeploymentType(String deploymentType) {
		this.deploymentType = deploymentType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getArtifactID() {
		return artifactID;
	}

	public void setArtifactID(String artifactID) {
		this.artifactID = artifactID;
	}

	public String getNexusId() {
		return nexusId;
	}

	public void setNexusId(String nexusId) {
		this.nexusId = nexusId;
	}

	public String getDbDeployOperation() {
		return dbDeployOperation;
	}

	public void setDbDeployOperation(String dbDeployOperation) {
		this.dbDeployOperation = dbDeployOperation;
	}

	public String getDbDeployRollbackType() {
		return dbDeployRollbackType;
	}

	public void setDbDeployRollbackType(String dbDeployRollbackType) {
		this.dbDeployRollbackType = dbDeployRollbackType;
	}

	public String getDbDeployRollbackValue() {
		return dbDeployRollbackValue;
	}

	public void setDbDeployRollbackValue(String dbDeployRollbackValue) {
		this.dbDeployRollbackValue = dbDeployRollbackValue;
	}

	public String getDbDeployFailSafe() {
		return dbDeployFailSafe;
	}

	public void setDbDeployFailSafe(String dbDeployFailSafe) {
		this.dbDeployFailSafe = dbDeployFailSafe;
	}

	public String getDbDeployPipelineOwners() {
		return dbDeployPipelineOwners;
	}

	public void setDbDeployPipelineOwners(String dbDeployPipelineOwners) {
		this.dbDeployPipelineOwners = dbDeployPipelineOwners;
	}

	public String getDbDeployPipelineList() {
		return dbDeployPipelineList;
	}

	public void setDbDeployPipelineList(String dbDeployPipelineList) {
		this.dbDeployPipelineList = dbDeployPipelineList;
	}

	public List<String> getSubModule() {
		return subModule;
	}

	public void setSubModule(List<String> subModule) {
		this.subModule = subModule;
	}

}
