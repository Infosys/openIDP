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
@JsonPropertyOrder({ "id", "message", "begincolumn", "endcolumn", "beginline", "endline", "externalInfoUrl", "priority",
		"rule", "ruleset" })
public class Pmd {
	@JsonProperty("begincolumn")
	private long begincolumn;
	@JsonProperty("beginline")
	private long beginline;
	@JsonProperty("id")
	private String id;
	@JsonProperty("message")
	private String message;
	@JsonProperty("endcolumn")
	private long endcolumn;
	@JsonProperty("endline")
	private long endline;
	@JsonProperty("externalInfoUrl")
	private String externalInfoUrl;
	@JsonProperty("priority")
	private long priority;
	@JsonProperty("rule")
	private String rule;
	@JsonProperty("ruleset")
	private String ruleset;

	

	

	@JsonProperty("begincolumn")
	public long getBegincolumn() {
		return begincolumn;
	}

	@JsonProperty("begincolumn")
	public void setBegincolumn(long begincolumn) {
		this.begincolumn = begincolumn;
	}

	public Pmd withBegincolumn(long begincolumn) {
		this.begincolumn = begincolumn;
		return this;
	}

	@JsonProperty("beginline")
	public long getBeginline() {
		return beginline;
	}

	@JsonProperty("beginline")
	public void setBeginline(long beginline) {
		this.beginline = beginline;
	}

	public Pmd withBeginline(long beginline) {
		this.beginline = beginline;
		return this;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String _class) {
		this.id = _class;
	}

	public Pmd withId(String _class) {
		this.id = _class;
		return this;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String content) {
		this.message = content;
	}

	public Pmd withMessage(String content) {
		this.message = content;
		return this;
	}

	@JsonProperty("endcolumn")
	public long getEndcolumn() {
		return endcolumn;
	}

	@JsonProperty("endcolumn")
	public void setEndcolumn(long endcolumn) {
		this.endcolumn = endcolumn;
	}

	public Pmd withEndcolumn(long endcolumn) {
		this.endcolumn = endcolumn;
		return this;
	}

	@JsonProperty("endline")
	public long getEndline() {
		return endline;
	}

	@JsonProperty("endline")
	public void setEndline(long endline) {
		this.endline = endline;
	}

	public Pmd withEndline(long endline) {
		this.endline = endline;
		return this;
	}

	@JsonProperty("externalInfoUrl")
	public String getExternalInfoUrl() {
		return externalInfoUrl;
	}

	@JsonProperty("externalInfoUrl")
	public void setExternalInfoUrl(String externalInfoUrl) {
		this.externalInfoUrl = externalInfoUrl;
	}

	public Pmd withExternalInfoUrl(String externalInfoUrl) {
		this.externalInfoUrl = externalInfoUrl;
		return this;
	}

	@JsonProperty("priority")
	public long getPriority() {
		return priority;
	}

	@JsonProperty("priority")
	public void setPriority(long priority) {
		this.priority = priority;
	}

	public Pmd withPriority(long priority) {
		this.priority = priority;
		return this;
	}

	@JsonProperty("rule")
	public String getRule() {
		return rule;
	}

	@JsonProperty("rule")
	public void setRule(String rule) {
		this.rule = rule;
	}

	public Pmd withRule(String rule) {
		this.rule = rule;
		return this;
	}

	@JsonProperty("ruleset")
	public String getRuleset() {
		return ruleset;
	}

	@JsonProperty("ruleset")
	public void setRuleset(String ruleset) {
		this.ruleset = ruleset;
	}

	public Pmd withRuleset(String ruleset) {
		this.ruleset = ruleset;
		return this;
	}
}
