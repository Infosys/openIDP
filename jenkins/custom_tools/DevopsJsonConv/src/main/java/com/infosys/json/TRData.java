/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
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
