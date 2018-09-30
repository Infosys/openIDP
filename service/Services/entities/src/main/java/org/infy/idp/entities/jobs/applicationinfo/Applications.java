/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.applicationinfo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store application list
 * 
 * @author Infosys
 *
 */
public class Applications {
	@SerializedName("applications")
	@Expose
	private List<Application> apps;

	public List<Application> getApplications() {
		return apps;
	}

	public void setApplications(List<Application> applications) {
		this.apps = applications;
	}

}