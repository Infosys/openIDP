/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.ngnjson;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store Stage information of Next-Gen Json
 * 
 * @author Infosys
 *
 */
public class Stage {

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("tasklist")
	@Expose
	private List<Task> tasklist =  null;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the tasklist
	 */
	public List<Task> getTasklist() {
		return tasklist;
	}

	/**
	 * @param tasklist the tasklist to set
	 */
	public void setTasklist(List<Task> tasklist) {
		this.tasklist = tasklist;
	}



}
