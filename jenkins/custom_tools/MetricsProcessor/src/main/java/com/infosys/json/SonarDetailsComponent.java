/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SonarDetailsComponent {
	@SerializedName("measures")
	@Expose	
	private List<SonarDetailsLocmeasures> measures;

	public List<SonarDetailsLocmeasures> getMeasures() {
		return measures;
	}

	public void setMeasures(List<SonarDetailsLocmeasures> measures) {
		this.measures = measures;
	}

	
}
