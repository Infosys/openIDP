/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities.models;

import java.util.List;

import org.infy.idp.entities.jobs.ngnjson.Stage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store All Stages information of Next-Gen Json
 * 
 * @author Infosys
 *
 */
public class Ngnjson {	
	@SerializedName("stages")
	@Expose
    private List<Stage> stages =  null;

	/**
	 * @return the stages
	 */
	public List<Stage> getStages() {
		return stages;
	}

	/**
	 * @param stages the stages to set
	 */
	public void setStages(List<Stage> stages) {
		this.stages = stages;
	} 
    
}
