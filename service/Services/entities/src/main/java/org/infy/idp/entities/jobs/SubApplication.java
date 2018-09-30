/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubApplication {
	@SerializedName("subApplication")
	@Expose
	private List<String> subApps;

	public List<String> getSubApps() {
		return subApps;
	}

	public void setSubApps(List<String> subApps) {
		this.subApps = subApps;
	}

}
