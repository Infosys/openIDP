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
 * Entity to store selected app details
 * 
 * @author Infosys
 *
 */
public class AppSelectionDetails {

	@SerializedName("appSelectionDetails")
	@Expose
	private List<PipelineSelectionDetails> appSelectionDetails;

	public List<PipelineSelectionDetails> getAppSelectionDetails() {
		return appSelectionDetails;
	}

	public void setAppSelectionDetails(List<PipelineSelectionDetails> appSelectionDetails) {
		this.appSelectionDetails = appSelectionDetails;
	}

}
