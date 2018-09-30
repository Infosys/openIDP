/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.ge.jenkins;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Entity to store Sonar API details
 * 
 * @author Infosys
 *
 */
public class SonarAPI {
	@SerializedName("ComponentKeys")
	@Expose
	private List<String> componentKeys;

	public List<String> getComponentKeys() {
		return componentKeys;
	}

	public void setComponentKeys(List<String> componentKeys) {
		this.componentKeys = componentKeys;
	}
}
