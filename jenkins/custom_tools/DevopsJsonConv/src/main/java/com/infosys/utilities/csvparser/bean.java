/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.csvparser;

public class Bean {
	// @CsvBindByPosition(position=0)
	private String packageName;
	// @CsvBindByPosition(position=15)
	private String CC;
	// @CsvBindByPosition(position=17)
	private String MI;
	// @CsvBindByPosition(position=18)
	private String CP;
	// @CsvBindByPosition(position=19)
	private String DP;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCC() {
		return CC;
	}

	public void setCC(String cc) {
		CC = cc;
	}

	public String getMI() {
		return MI;
	}

	public void setMI(String mI) {
		MI = mI;
	}

	public String getCP() {
		return CP;
	}

	public void setCP(String cP) {
		CP = cP;
	}

	public String getDP() {
		return DP;
	}

	public void setDP(String dP) {
		DP = dP;
	}
}
