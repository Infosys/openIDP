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
 * Entity to store selected pipeline details
 * 
 * @author Infosys
 *
 */
public class PipelineSelectionDetails {
	@SerializedName("pipelineSelectionDetails")
	@Expose
	private List<String> pipelineSelectionDetails;

	public List<String> getPipelineSelectionDetails() {
		return pipelineSelectionDetails;
	}

	public void setPipelineSelectionDetails(List<String> pipelineSelectionDetails) {
		this.pipelineSelectionDetails = pipelineSelectionDetails;
	}
}
