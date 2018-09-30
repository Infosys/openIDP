package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransportRequests {
	
	@SerializedName("transportReqName")
	@Expose
	private String transportReqName;
	
	@SerializedName("srcSystem")
	@Expose
	private String srcSystem;
	
	@SerializedName("destSystem")
	@Expose
	private String destSystem;
	
	@SerializedName("user")
	@Expose
	private String user;

	public String getTransportReqName() {
		return transportReqName;
	}

	public void setTransportReqName(String transportReqName) {
		this.transportReqName = transportReqName;
	}

	public String getSrcSystem() {
		return srcSystem;
	}

	public void setSrcSystem(String srcSystem) {
		this.srcSystem = srcSystem;
	}

	public String getDestSystem() {
		return destSystem;
	}

	public void setDestSystem(String destSystem) {
		this.destSystem = destSystem;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
