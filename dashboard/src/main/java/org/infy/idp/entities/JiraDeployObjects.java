package org.infy.idp.entities;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JiraDeployObjects {



	@SerializedName("applicationName")
	@Expose
	private String applicationName;
	@SerializedName("landscapeName")
	@Expose
	private String landscapeName;
	@SerializedName("pipelineName")
	@Expose
	private String pipelineName;
	@SerializedName("releaseName")
	@Expose
	private String releaseName;
	@SerializedName("userStoryName")
	@Expose
	private String userStoryName;
	@SerializedName("userStoryNameList")
    @Expose
    private List<String> userStoryNameList;
	
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getLandscapeName() {
		return landscapeName;
	}
	public void setLandscapeName(String landscapeName) {
		this.landscapeName = landscapeName;
	}
	public String getPipelineName() {
		return pipelineName;
	}
	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public String getUserStoryName() {
		return userStoryName;
	}
	public void setUserStoryName(String userStoryName) {
		this.userStoryName = userStoryName;
	}
	public List<String> getUserStoryNameList() {
		return userStoryNameList;
	}
	public void setUserStoryNameList(List<String> userStoryNameList) {
		this.userStoryNameList = userStoryNameList;
	}
	
	
	

}
