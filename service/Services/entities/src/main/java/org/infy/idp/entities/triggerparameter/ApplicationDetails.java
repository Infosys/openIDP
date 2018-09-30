/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.triggerparameter;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store pipeline list of application
 * 
 * @author Infosys
 *
 */
public class ApplicationDetails {

	@SerializedName("applicationDetails")
	@Expose
	private List<PipelineDetails> applicationDetails;

	public List<PipelineDetails> getApplicationDetails() {
		return applicationDetails;
	}

	public void setApplicationDetails(List<PipelineDetails> applicationDetails) {
		this.applicationDetails = applicationDetails;
	}
}
