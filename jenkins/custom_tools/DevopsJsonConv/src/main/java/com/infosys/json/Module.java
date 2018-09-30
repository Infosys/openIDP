package com.infosys.json;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Module {
	@SerializedName("moduleName")
	@Expose
	private List<String> moduleName;

	public List<String> getModuleName() {
		return moduleName;
	}

	public void setModuleName(List<String> moduleName) {
		this.moduleName = moduleName;
	}
}
