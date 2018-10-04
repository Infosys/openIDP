/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.jsonschema;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "packageName", "cyclomaticComplexity", "maintainabilityIndex", "changePronenessIndex",
		"defectPronenessIndex" })
public class CSVInfo {
	@JsonProperty("packageName")
	private String name;
	@JsonProperty("cyclomaticComplexity")
	private String cc;
	@JsonProperty("maintainabilityIndex")
	private String mi;
	@JsonProperty("changePronenessIndex")
	private String cp;
	@JsonProperty("defectPronenessIndex")
	private String dp;

	public CSVInfo() {
		super();
	}

	public CSVInfo(String name, String cc, String mi, String cp, String dp) {
		super();
		this.name = name;
		this.cc = cc;
		this.mi = mi;
		this.cp = cp;
		this.dp = dp;
	}

	@JsonProperty("packageName")
	public String getName() {
		return name;
	}

	@JsonProperty("packageName")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("cyclomaticComplexity")
	public String getCc() {
		return cc;
	}

	@JsonProperty("cyclomaticComplexity")
	public void setCc(String cc) {
		this.cc = cc;
	}

	@JsonProperty("maintainabilityIndex")
	public String getMi() {
		return mi;
	}

	@JsonProperty("maintainabilityIndex")
	public void setMi(String mi) {
		this.mi = mi;
	}

	@JsonProperty("changePronenessIndex")
	public String getCp() {
		return cp;
	}

	@JsonProperty("changePronenessIndex")
	public void setCp(String cp) {
		this.cp = cp;
	}

	@JsonProperty("defectPronenessIndex")
	public String getDp() {
		return dp;
	}

	@JsonProperty("defectPronenessIndex")
	public void setDp(String dp) {
		this.dp = dp;
	}
}
