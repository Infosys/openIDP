/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.applicationinfo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReleaseNumber {

	/**
	 * Entity to store release no list
	 * 
	 * @author Infosys
	 *
	 */
	@SerializedName("releaseNumbers")
	@Expose
	private List<String> releaseNumbers;

	public List<String> getReleaseNumbers() {
		return releaseNumbers;
	}

	public void setReleaseNumbers(List<String> releaseNumbers) {
		this.releaseNumbers = releaseNumbers;
	}

}
