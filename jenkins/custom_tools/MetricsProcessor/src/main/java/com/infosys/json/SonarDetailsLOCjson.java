/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SonarDetailsLOCjson {
	@SerializedName("component")
	@Expose
	private SonarDetailsComponent component;

	public SonarDetailsComponent getComponent() {
		return component;
	}

	public void setComponent(SonarDetailsComponent component) {
		this.component = component;
	}
	
		
}
