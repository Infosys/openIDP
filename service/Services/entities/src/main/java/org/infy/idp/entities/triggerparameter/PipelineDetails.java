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
 * Entity to store artifact list for deploy 
 * 
 * @author Infosys
 *
 */
public class PipelineDetails {
	@SerializedName("pipelineDetails")
	@Expose
	private List<TriggerDetails> pipelineDetails;

	public List<TriggerDetails> getPipelineDetails() {
		return pipelineDetails;
	}

	public void setPipelineDetails(List<TriggerDetails> pipelineDetails) {
		this.pipelineDetails = pipelineDetails;
	}
}
