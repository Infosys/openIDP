/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericInfo {

	@JsonProperty("checkstyleURL")
	private String checkstyleURL;
	@JsonProperty("pmdURL")
	private String pmdURL;
	@JsonProperty("findbugsURL")
	private String findbugsURL;
	@JsonProperty("junitURL")
	private String junitURL;

	public GenericInfo() {
		super();
		this.checkstyleURL = "";
		this.pmdURL = "";
		this.findbugsURL = "";
		this.junitURL = "";
	}	
	
	@JsonProperty("checkstyleURL")
	public String getCheckstyleURL() {
		return checkstyleURL;
	}

	@JsonProperty("checkstyleURL")
	public void setCheckstyleURL(String checkstyleURL) {
		this.checkstyleURL = checkstyleURL;
	}

	@JsonProperty("pmdURL")
	public String getPmdURL() {
		return pmdURL;
	}

	@JsonProperty("pmdURL")
	public void setPmdURL(String pmdURL) {
		this.pmdURL = pmdURL;
	}

	@JsonProperty("findbugsURL")
	public String getFindbugsURL() {
		return findbugsURL;
	}

	@JsonProperty("findbugsURL")
	public void setFindbugsURL(String findbugsURL) {
		this.findbugsURL = findbugsURL;
	}

	@JsonProperty("junitURL")
	public String getJunitURL() {
		return junitURL;
	}

	@JsonProperty("junitURL")
	public void setJunitURL(String junitURL) {
		this.junitURL = junitURL;
	}
}
