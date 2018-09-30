package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtifactRepo {
	@SerializedName("repoURL")
    @Expose
    private String repoURL;
	@SerializedName("repoName")
    @Expose
    private String repoName;
	@SerializedName("repoUsername")
    @Expose
    private String repoUsername;
	@SerializedName("repoPassword")
    @Expose
    private String repoPassword;
	public String getRepoURL() {
		return repoURL;
	}
	public void setRepoURL(String repoURL) {
		this.repoURL = repoURL;
	}
	public String getRepoName() {
		return repoName;
	}
	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}
	public String getRepoUsername() {
		return repoUsername;
	}
	public void setRepoUsername(String repoUsername) {
		this.repoUsername = repoUsername;
	}
	public String getRepoPassword() {
		return repoPassword;
	}
	public void setRepoPassword(String repoPassword) {
		this.repoPassword = repoPassword;
	}
	
}
