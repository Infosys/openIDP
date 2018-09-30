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
 * Entity to store Deploy test environment details
 * 
 * @author Infosys
 *
 */
public class DeployTestEnv {

	@SerializedName("Env")
	@Expose
	private List<String> env = null;
	@SerializedName("envObj")
	@Expose
	private List<EnvironmentObj> envObj = null;

	public List<EnvironmentObj> getEnvObj() {
		return envObj;
	}

	public void setEnvObj(List<EnvironmentObj> envObj) {
		this.envObj = envObj;
	}

	public List<String> getEnv() {
		return env;
	}

	public void setEnv(List<String> env) {
		this.env = env;
	}

}
