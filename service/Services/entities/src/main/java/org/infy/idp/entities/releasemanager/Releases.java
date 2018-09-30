/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.releasemanager;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Releases {
	@SerializedName("pipelineName")
	@Expose
	private String pipelineName;
	@SerializedName("release")
	@Expose
	private List<Release> release = null;

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public List<Release> getRelease() {
		return release;
	}

	public void setRelease(List<Release> release) {
		this.release = release;
	}

}
