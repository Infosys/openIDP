/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.code;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store job parameter details
 * 
 * @author Infosys
 *
 */
public class JobParam {

	@SerializedName("jobType")
	@Expose
	private String jobType;
	@SerializedName("jobParamName")
	@Expose
	private String jobParamName;
	@SerializedName("jobParamValue")
	@Expose
	private String jobParamValue;
	@SerializedName("jobParamSatic")
	@Expose
	private Boolean jobParamSatic;

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobParamName() {
		return jobParamName;
	}

	public void setJobParamName(String jobParamName) {
		this.jobParamName = jobParamName;
	}

	public String getJobParamValue() {
		return jobParamValue;
	}

	public void setJobParamValue(String jobParamValue) {
		this.jobParamValue = jobParamValue;
	}

	public Boolean getJobParamSatic() {
		return jobParamSatic;
	}

	public void setJobParamSatic(Boolean jobParamSatic) {
		this.jobParamSatic = jobParamSatic;
	}
}
