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

/**
 * Entity to store application list
 * 
 * @author Infosys
 *
 */
public class AppNames {
	@SerializedName("applicationNames")
	@Expose
	private List<String> applicationNames;

	public List<String> getApplicationNames() {
		return applicationNames;
	}

	public void setApplicationNames(List<String> applicationNames) {
		this.applicationNames = applicationNames;
	}
}