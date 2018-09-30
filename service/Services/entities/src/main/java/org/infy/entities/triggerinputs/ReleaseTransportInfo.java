/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store release details
 * 
 * @author Infosys
 *
 */
public class ReleaseTransportInfo {

	@SerializedName("releaseNumber")
	@Expose
	private String releaseNumber;
	@SerializedName("transportList")
	@Expose
	private List<String> transportList;

	public String getReleaseNumber() {
		return releaseNumber;
	}

	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	public List<String> getTransportList() {
		return transportList;
	}

	public void setTransportList(List<String> transportList) {
		this.transportList = transportList;
	}

}
