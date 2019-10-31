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

public class ServiceTest {
	@SerializedName("ifastTestReport")
	@Expose
	private IFastReport ifastTestReport;

	public IFastReport getIfastTestReport() {
		return ifastTestReport;
	}

	public void setIfastTestReport(IFastReport ifastTestReport) {
		this.ifastTestReport = ifastTestReport;
	}
}
