package com.infosys.json;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class ApplicationInfo
 * */

public class ApplicationInfo {
    @SerializedName("applicationName")
    @Expose
    private String applicationName;
    @SerializedName("developers")
    @Expose
    private String developers;
    @SerializedName("pipelineAdmins")
    @Expose
    private String pipelineAdmins;
    @SerializedName("releaseManager")
    @Expose
    private String releaseManager;
    @SerializedName("environmentOwnerDetails")
    @Expose
    private List<EnvironmentOwnerDetail> environmentOwnerDetails = null;
    @SerializedName("slavesDetails")
    @Expose
    private List<SlavesDetail> slavesDetails = null;
    @SerializedName("sapApplication")
    @Expose
    private String sapApplication;
    @SerializedName("artifactToStage")
    @Expose
    private ArtifactToStage artifactToStage;
    @SerializedName("virtualServiceServerDetails")
    @Expose
    private VirtualServiceServerDetails virtualServiceServerDetails;
    
    
	public VirtualServiceServerDetails getVirtualServiceServerDetails() {
		return virtualServiceServerDetails;
	}
	public void setVirtualServiceServerDetails(VirtualServiceServerDetails virtualServiceServerDetails) {
		this.virtualServiceServerDetails = virtualServiceServerDetails;
	}
	public ArtifactToStage getArtifactToStage() {
		return artifactToStage;
	}
	public void setArtifactToStage(ArtifactToStage artifactToStage) {
		this.artifactToStage = artifactToStage;
	}
	public String getSapApplication() {
		return sapApplication;
	}
	public void setSapApplication(String sapApplication) {
		this.sapApplication = sapApplication;
	}
	public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    public String getDevelopers() {
        return developers;
    }
    public void setDevelopers(String developers) {
        this.developers = developers;
    }
    public String getPipelineAdmins() {
        return pipelineAdmins;
    }
    public void setPipelineAdmins(String pipelineAdmins) {
        this.pipelineAdmins = pipelineAdmins;
    }
    public String getReleaseManager() {
        return releaseManager;
    }
    public void setReleaseManager(String releaseManager) {
        this.releaseManager = releaseManager;
    }
    public List<EnvironmentOwnerDetail> getEnvironmentOwnerDetails() {
        return environmentOwnerDetails;
    }
    public void setEnvironmentOwnerDetails(List<EnvironmentOwnerDetail> environmentOwnerDetails) {
        this.environmentOwnerDetails = environmentOwnerDetails;
    }
    public List<SlavesDetail> getSlavesDetails() {
        return slavesDetails;
    }
    public void setSlavesDetails(List<SlavesDetail> slavesDetails) {
        this.slavesDetails = slavesDetails;
    }
}
