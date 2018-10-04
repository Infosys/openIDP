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

public class UnitTesting {
	@SerializedName("jUnit")
	@Expose
	private JUnit jUnit;

	public JUnit getjUnit() {
		return jUnit;
	}

	public void setjUnit(JUnit jUnit) {
		this.jUnit = jUnit;
	}
}
