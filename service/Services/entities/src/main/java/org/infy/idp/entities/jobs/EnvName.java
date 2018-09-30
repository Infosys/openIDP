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
 * Entity to store environment name list
 * 
 * @author Infosys
 *
 */
public class EnvName {
	@SerializedName("envNames")
	@Expose
	private List<String> envNames;

	public List<String> getEnvNames() {
		return envNames;
	}

	public void setEnvNames(List<String> envNames) {
		this.envNames = envNames;
	}
}