package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecurityTest {

	@SerializedName("checkmarx")
	@Expose
	private Checkmarx checkmarx;
	

	public Checkmarx getCheckmarx() {
		return checkmarx;
	}

	public void setCheckmarx(Checkmarx checkmarx) {
		this.checkmarx = checkmarx;
	}
}
