/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/


package org.infy.idp.entities;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * class DeployArtifact
 *
 * 
 */
public class DeployArtifact {
	
	@SerializedName("version")
    @Expose
    private String version;
    @SerializedName("artifactID")
    @Expose
    private String artifactID;
    @SerializedName("artifactName")
    @Expose
    private String artifactName;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("groupId")
    @Expose
    private String groupId;
    @SerializedName("userInfo")
    @Expose
    private String userInfo;
    @SerializedName("downloadURL")
    @Expose
    private String downloadURL;
    @SerializedName("buildModulesList")
    @Expose
    private List<String> buildModulesList;
    @SerializedName("environment")
    @Expose
    private String environment;
    @SerializedName("nexusURL")
    @Expose
    private String nexusURL;
   

	@SerializedName("repoName")
    @Expose
    private String repoName;
	

	public List<String> getBuildModulesList() {
		return buildModulesList;
	}

	public void setBuildModulesList(List<String> buildModulesList) {
		this.buildModulesList = buildModulesList;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getnexusURL() {
		return nexusURL;
	}

	public void setnexusURL(String nexusURL) {
		this.nexusURL = nexusURL;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

   

}

