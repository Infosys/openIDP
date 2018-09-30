package com.infosys.json;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SonarDetailsLOCjson {
	@SerializedName("component")
	@Expose
	private SonarDetailsComponent component;

	public SonarDetailsComponent getComponent() {
		return component;
	}

	public void setComponent(SonarDetailsComponent component) {
		this.component = component;
	}
	
		
}
