/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store ant project properties details
 * 
 * @author Infosys
 *
 */
public class AntProperties {
	@SerializedName("antKey")
	@Expose
	private String antKey;
	@SerializedName("antValue")
	@Expose
	private String antValue;

	public String getAntKey() {
		return antKey;
	}

	public void setAntKey(String antKey) {
		this.antKey = antKey;
	}

	public String getAntValue() {
		return antValue;
	}

	public void setAntValue(String antValue) {
		this.antValue = antValue;
	}

}
