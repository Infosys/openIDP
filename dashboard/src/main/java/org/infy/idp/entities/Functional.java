/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Functional {
	
	@SerializedName("accleratest")
	@Expose
	private Accleratest accleratest;
	
	@SerializedName("itops")
	@Expose
	private Itops itops;
	
	

	@SerializedName("selenium")
	@Expose
	private Selenium selenium;
	
	
	@SerializedName("qualitia")
	@Expose
	private Qualitia qualitia;
	

	public Qualitia getQualitia() {
		return qualitia;
	}

	public void setQualitia(Qualitia qualitia) {
		this.qualitia = qualitia;
	}

	public Accleratest getAccleratest() {
		return accleratest;
	}

	public void setAccleratest(Accleratest accleratest) {
		this.accleratest = accleratest;
	}

	public Itops getItops() {
		return itops;
	}

	public void setItops(Itops itops) {
		this.itops = itops;
	}
	public Selenium getSelenium() {
		return selenium;
	}

	public void setSelenium(Selenium selenium) {
		this.selenium = selenium;
	}
	
	
}
