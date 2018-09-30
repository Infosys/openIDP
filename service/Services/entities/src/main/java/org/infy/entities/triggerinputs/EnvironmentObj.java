/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.triggerinputs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store environment details
 * 
 * @author Infosys
 *
 */
public class EnvironmentObj {
	@SerializedName("EnvName")
	@Expose
	private String envName;
	@SerializedName("bizCheck")
	@Expose
	private String bizCheck;

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public String getBizCheck() {
		return bizCheck;
	}

	public void setBizCheck(String bizCheck) {
		this.bizCheck = bizCheck;
	}

}
