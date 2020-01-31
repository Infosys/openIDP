/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.buildinfo;

import java.util.List;

import org.infy.idp.entities.jobs.common.RunScript;
import org.infy.idp.entities.models.Fortify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store build information of pipeline
 * 
 * @author Infosys
 *
 */
public class BuildInfo {

	@SerializedName("buildtool")
	@Expose
	private String buildtool;
	
	@SerializedName("securityAnalysisTool")
	@Expose
	private String securityAnalysisTool;



	@SerializedName("artifactToStage")
	@Expose
	private ArtifactToStage artifactToStage;
	@SerializedName("fortifyDetails")
	@Expose
	private Fortify fortifyDetails;


	//    @SerializedName("castAnalysis")
//    @Expose
//    private CastAnalysis castAnalysis;
	@SerializedName("modules")
	@Expose
	private List<Module> modules = null;
	@SerializedName("postBuildScript")
	@Expose
	private RunScript postBuildScript = null;

	@SerializedName("nexusType")
	@Expose
	private String nexusType;
	@SerializedName("nexusUrl")
	@Expose
	private String nexusUrl;
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("javaModules")
	@Expose
	private String javaModules;

	@SerializedName("ejbModules")
	@Expose
	private String ejbModules;
	@SerializedName("password")
	@Expose
	private String password;
	@SerializedName("webModules")
	@Expose
	private String webModules;
	@SerializedName("subModule")
	@Expose
	private List<SubModule> subModule = null;

	@SerializedName("sonarUrl")
	@Expose
	private String sonarUrl;

	@SerializedName("sonarUserName")
	@Expose
	private String sonarUserName;

	@SerializedName("sonarPassword")
	@Expose
	private String sonarPassword;
	
	@SerializedName("sonarProjectKey")
	@Expose
	private String sonarProjectKey;
	
	@SerializedName("sonarProperties")
	@Expose
	private String sonarProperties;

	public String getSonarProperties() {
		return sonarProperties;
	}

	public void setSonarProperties(String sonarProperties) {
		this.sonarProperties = sonarProperties;
	}

	public String getSonarProjectKey() {
		return sonarProjectKey;
	}

	public void setSonarProjectKey(String sonarProjectKey) {
		this.sonarProjectKey = sonarProjectKey;
	}

	public String getSecurityAnalysisTool() {
		return securityAnalysisTool;
	}

	public void setSecurityAnalysisTool(String securityAnalysisTool) {
		this.securityAnalysisTool = securityAnalysisTool;
	}
	
	// Add Jira ALM
//    @SerializedName("jiraProjKey")
//	@Expose
//	private String jiraProjKey;
//
//	@SerializedName("jiraAssigneeName")
//	@Expose
//	private String jiraAssigneeName;

	
//	public String getJiraProjKey() {
//		return jiraProjKey;
//	}
//
//	public void setJiraProjKey(String jiraProjKey) {
//		this.jiraProjKey = jiraProjKey;
//	}
//
//	public String getJiraAssigneeName() {
//		return jiraAssigneeName;
//	}
//
//	public void setJiraAssigneeName(String jiraAssigneeName) {
//		this.jiraAssigneeName = jiraAssigneeName;
//	}
    public Fortify getFortifyDetails() {
		return fortifyDetails;
	}

	public void setFortifyDetails(Fortify fortifyDetails) {
		this.fortifyDetails = fortifyDetails;
	}

	public String getSonarUserName() {
		return sonarUserName;
	}

	public void setSonarUserName(String sonarUserName) {
		this.sonarUserName = sonarUserName;
	}

	public String getSonarPassword() {
		return sonarPassword;
	}

	public void setSonarPassword(String sonarPassword) {
		this.sonarPassword = sonarPassword;
	}

	public String getSonarUrl() {
		return sonarUrl;
	}

	public void setSonarUrl(String sonarUrl) {
		this.sonarUrl = sonarUrl;
	}

	public List<SubModule> getSubModule() {
		return subModule;
	}

	public void setSubModule(List<SubModule> subModule) {
		this.subModule = subModule;
	}

	public String getJavaModules() {
		return javaModules;
	}

	public void setJavaModules(String javaModules) {
		this.javaModules = javaModules;
	}

	public String getEjbModules() {
		return ejbModules;
	}

	public void setEjbModules(String ejbModules) {
		this.ejbModules = ejbModules;
	}

	public String getWebModules() {
		return webModules;
	}

	public void setWebModules(String webModules) {
		this.webModules = webModules;
	}

	public String getNexusType() {
		return nexusType;
	}

	public void setNexusType(String nexusType) {
		this.nexusType = nexusType;
	}

	public String getNexusUrl() {
		return nexusUrl;
	}

	public void setNexusUrl(String nexusUrl) {
		this.nexusUrl = nexusUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RunScript getPostBuildScript() {
		return postBuildScript;
	}

	public void setPostBuildScript(RunScript postBuildScript) {
		this.postBuildScript = postBuildScript;
	}

	public String getBuildtool() {
		return buildtool;
	}

	public void setBuildtool(String buildtool) {
		this.buildtool = buildtool;
	}

	public ArtifactToStage getArtifactToStage() {
		return artifactToStage;
	}

	public void setArtifactToStage(ArtifactToStage artifactToStage) {
		this.artifactToStage = artifactToStage;
	}

//    public CastAnalysis getCastAnalysis() {
//        return castAnalysis;
//    }
//
//    public void setCastAnalysis(CastAnalysis castAnalysis) {
//        this.castAnalysis = castAnalysis;
//    }
	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
}
