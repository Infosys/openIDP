package org.infy.idp.entities.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fortify {
	@SerializedName("javaVersion")
	@Expose
	private String javaVersion;
	
	@SerializedName("dotNetVersion")
	@Expose
	private String dotNetVersion;
	
	@SerializedName("excludeList")
	@Expose
	private String excludeList;
	
	public String getJavaVersion() {
		return javaVersion;
	}
	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}
	public String getDotNetVersion() {
		return dotNetVersion;
	}
	public void setDotNetVersion(String dotNetVersion) {
		this.dotNetVersion = dotNetVersion;
	}
	public String getExcludeList() {
		return excludeList;
	}
	public void setExcludeList(String excludeList) {
		this.excludeList = excludeList;
	}
	

}
