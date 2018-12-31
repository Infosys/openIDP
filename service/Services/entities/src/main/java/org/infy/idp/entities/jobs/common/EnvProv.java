package org.infy.idp.entities.jobs.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnvProv {
	@SerializedName("scriptFilePath")
	@Expose
	private String scriptFilePath;
	@SerializedName("toolType")
	@Expose
	private String toolType;
	public String getScriptFilePath() {
		return scriptFilePath;
	}
	public void setScriptFilePath(String scriptFilePath) {
		this.scriptFilePath = scriptFilePath;
	}
	public String getToolType() {
		return toolType;
	}
	public void setToolType(String toolType) {
		this.toolType = toolType;
	}
	

}
