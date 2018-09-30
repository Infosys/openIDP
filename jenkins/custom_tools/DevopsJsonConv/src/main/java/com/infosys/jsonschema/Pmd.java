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

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("begincolumn")
	private long begincolumn;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("beginline")
	private long beginline;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("id")
	private String id;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("message")
	private String message;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("endcolumn")
	private long endcolumn;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("endline")
	private long endline;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("externalInfoUrl")
	private String externalInfoUrl;

	@JsonProperty("priority")
	private long priority;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("rule")
	private String rule;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("ruleset")
	private String ruleset;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Pmd() {
		// Empty Constructor
	}

	/**
	 * 
	 * @param endcolumn
	 * @param content
	 * @param _class
	 * @param ruleset
	 * @param priority
	 * @param _package
	 * @param endline
	 * @param rule
	 * @param begincolumn
	 * @param beginline
	 * @param externalInfoUrl
	 */
	public Pmd(long begincolumn, long beginline, String _class, String content, long endcolumn, long endline,
			String externalInfoUrl, long priority, String rule, String ruleset) {
		this.begincolumn = begincolumn;
		this.beginline = beginline;
		this.id = _class;
		this.message = content;
		this.endcolumn = endcolumn;
		this.endline = endline;
		this.externalInfoUrl = externalInfoUrl;
		this.priority = priority;
		this.rule = rule;
		this.ruleset = ruleset;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The begincolumn
	 */
	@JsonProperty("begincolumn")
	public long getBegincolumn() {
		return begincolumn;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param begincolumn
	 *            The begincolumn
	 */
	@JsonProperty("begincolumn")
	public void setBegincolumn(long begincolumn) {
		this.begincolumn = begincolumn;
	}

	public Pmd withBegincolumn(long begincolumn) {
		this.begincolumn = begincolumn;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The beginline
	 */
	@JsonProperty("beginline")
	public long getBeginline() {
		return beginline;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param beginline
	 *            The beginline
	 */
	@JsonProperty("beginline")
	public void setBeginline(long beginline) {
		this.beginline = beginline;
	}

	public Pmd withBeginline(long beginline) {
		this.beginline = beginline;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The _class
	 */
	@JsonProperty("id")
	public String getId() {
		return id;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param _class
	 *            The class
	 */
	@JsonProperty("id")
	public void setId(String _class) {
		this.id = _class;
	}

	public Pmd withId(String _class) {
		this.id = _class;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The content
	 */
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param content
	 *            The content
	 */
	@JsonProperty("message")
	public void setMessage(String content) {
		this.message = content;
	}

	public Pmd withMessage(String content) {
		this.message = content;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The endcolumn
	 */
	@JsonProperty("endcolumn")
	public long getEndcolumn() {
		return endcolumn;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param endcolumn
	 *            The endcolumn
	 */
	@JsonProperty("endcolumn")
	public void setEndcolumn(long endcolumn) {
		this.endcolumn = endcolumn;
	}

	public Pmd withEndcolumn(long endcolumn) {
		this.endcolumn = endcolumn;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The endline
	 */
	@JsonProperty("endline")
	public long getEndline() {
		return endline;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param endline
	 *            The endline
	 */
	@JsonProperty("endline")
	public void setEndline(long endline) {
		this.endline = endline;
	}

	public Pmd withEndline(long endline) {
		this.endline = endline;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The externalInfoUrl
	 */
	@JsonProperty("externalInfoUrl")
	public String getExternalInfoUrl() {
		return externalInfoUrl;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param externalInfoUrl
	 *            The externalInfoUrl
	 */
	@JsonProperty("externalInfoUrl")
	public void setExternalInfoUrl(String externalInfoUrl) {
		this.externalInfoUrl = externalInfoUrl;
	}

	public Pmd withExternalInfoUrl(String externalInfoUrl) {
		this.externalInfoUrl = externalInfoUrl;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The priority
	 */
	@JsonProperty("priority")
	public long getPriority() {
		return priority;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param priority
	 *            The priority
	 */
	@JsonProperty("priority")
	public void setPriority(long priority) {
		this.priority = priority;
	}

	public Pmd withPriority(long priority) {
		this.priority = priority;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The rule
	 */
	@JsonProperty("rule")
	public String getRule() {
		return rule;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param rule
	 *            The rule
	 */
	@JsonProperty("rule")
	public void setRule(String rule) {
		this.rule = rule;
	}

	public Pmd withRule(String rule) {
		this.rule = rule;
		return this;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The ruleset
	 */
	@JsonProperty("ruleset")
	public String getRuleset() {
		return ruleset;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param ruleset
	 *            The ruleset
	 */
	@JsonProperty("ruleset")
	public void setRuleset(String ruleset) {
		this.ruleset = ruleset;
	}

	public Pmd withRuleset(String ruleset) {
		this.ruleset = ruleset;
		return this;
	}

}
