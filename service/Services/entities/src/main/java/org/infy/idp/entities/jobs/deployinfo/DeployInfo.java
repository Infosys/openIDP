/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.deployinfo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store deploy environment list
 * 
 * @author Infosys
 *
 */
public class DeployInfo {
	@SerializedName("deployEnv")
	@Expose
	private List<DeployEnv> deployEnv = null;

	public List<DeployEnv> getDeployEnv() {
		return deployEnv;
	}

	public void setDeployEnv(List<DeployEnv> deployEnv) {
		this.deployEnv = deployEnv;
	}
}
