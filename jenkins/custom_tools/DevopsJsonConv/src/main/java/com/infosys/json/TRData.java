package com.infosys.json;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TRData {
	
	@SerializedName("transportRequests")
    @Expose
    private List<TransportRequests> transportRequests;

	@SerializedName("show")
	@Expose
	private Boolean show;

	public List<TransportRequests> getTransportRequests() {
		return transportRequests;
	}

	public void setTransportRequests(List<TransportRequests> transportRequests) {
		this.transportRequests = transportRequests;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}
	
	
}


