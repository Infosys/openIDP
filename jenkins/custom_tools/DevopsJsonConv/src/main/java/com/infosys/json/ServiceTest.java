package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceTest {

	@SerializedName("iFast")
	@Expose
	private IFast iFast;

	public IFast getiFast() {
		return iFast;
	}

	public void setiFast(IFast iFast) {
		this.iFast = iFast;
	}	
}
