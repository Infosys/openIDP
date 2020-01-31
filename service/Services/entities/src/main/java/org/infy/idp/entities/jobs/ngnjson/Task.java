/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.ngnjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store Task information of Next-Gen Json
 * 
 * @author Infosys
 *
 */
public class Task {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("options")
	@Expose
	private Options options;
	
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
	 * @return the options
	 */
	public Options getOptions() {
		return options;
	}
	/**
	 * @param options the options to set
	 */
	public void setOptions(Options options) {
		this.options = options;
	}


}
