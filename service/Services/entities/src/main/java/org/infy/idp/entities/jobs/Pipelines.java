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
 * Entity to store pipeline list
 * 
 * @author Infosys
 *
 */
public class Pipelines {
	@SerializedName("pipelines")
	@Expose
	private List<Pipeline> pips;

	public List<Pipeline> getPipelines() {
		return pips;
	}

	public void setPipelines(List<Pipeline> pipelines) {
		this.pips = pipelines;
	}

}
