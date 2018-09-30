/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.applicationinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store application json
 * 
 * @author Infosys
 *
 */
public class Application {
	@SerializedName("applicationName")
	@Expose
	private String applicationName;
	@SerializedName("appJson")
	@Expose
	private ApplicationInfo appJson;

	public ApplicationInfo getAppJson() {
		return appJson;
	}

	public void setAppJson(ApplicationInfo appJson) {
		this.appJson = appJson;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationNames) {
		this.applicationName = applicationNames;
	}

}
