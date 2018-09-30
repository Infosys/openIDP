
/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import java.util.ArrayList;


import com.fasterxml.jackson.annotation.JsonProperty;



public class SonarJson {
	@JsonProperty("issues")
	private ArrayList<SonarIssues> issues;
	@JsonProperty("p")
	private String p;
	@JsonProperty("total")
	private String total;
	public ArrayList<SonarIssues> getIssues() {
		return issues;
	}
	public void setIssues(ArrayList<SonarIssues> issues) {
		this.issues = issues;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPs() {
		return ps;
	}
	public void setPs(String ps) {
		this.ps = ps;
	}
	public SonarPaging getPaging() {
		return paging;
	}
	public void setPaging(SonarPaging paging) {
		this.paging = paging;
	}
	@JsonProperty("ps")
	private String ps;
	@JsonProperty("paging")
	private SonarPaging paging;
	

}
