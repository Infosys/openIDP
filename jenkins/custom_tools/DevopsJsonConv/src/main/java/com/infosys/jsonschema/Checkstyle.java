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
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "line", "message", "severity", "source", "column" })
public class Checkstyle {
	@SerializedName("critical")
	@Expose
	private Integer critical;
	@SerializedName("blocker")
	@Expose
	private Integer blocker;
	@SerializedName("major")
	@Expose
	private Integer major;
	@SerializedName("minor")
	@Expose
	private Integer minor;
	@SerializedName("info")	
	@Expose
	private Integer info;
	public Integer getCritical() 
	{
		return critical;
	}
	public void setCritical(Integer critical)
	{
		this.critical = critical;
	}
	public Integer getBlocker() {
		return blocker;
	}
	public void setBlocker(Integer blocker) {
		this.blocker = blocker;
	}
	public Integer getInfo() {
		return info;
	}
	public void setInfo(Integer info) {
		this.info = info;
	}
	public Integer getMajor() {
		return major;
	}
	public void setMajor(Integer major) {
		this.major = major;
	}
	public Integer getMinor() {
		return minor;
	}
	public void setMinor(Integer minor) {
		this.minor = minor;
	}
	
	
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
	@JsonProperty("line")
	private Object line = null;
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
	@JsonProperty("severity")
	@Expose
	private String severity;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("source")
	private String source;

	@JsonProperty("column")
	private String column;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Checkstyle() {
		// Empty Constructor
	}

	/**
	 * 
	 * @param message
	 * @param source
	 * @param line
	 * @param severity
	 */
	public Checkstyle(String id, long line, String message, String severity, String source, String column) {
		this.line = line;
		this.message = message;
		this.severity = severity;
		this.source = source;
		this.column = column;
		this.id = id;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The line
	 */
	@JsonProperty("line")
	public Object getLine() {
		return line;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param line
	 *            The line
	 */
	@JsonProperty("line")
	public void setLine(long line) {
		this.line = line;
	}
	

	@JsonProperty("column")
	public String getColumn() {
		return column;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param line
	 *            The line
	 */
	@JsonProperty("column")
	public void setColumn(String column) {
		this.column = column;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param line
	 *            The line
	 */
	@JsonProperty("id")
	public void setId(String column) {
		this.id = column;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The message
	 */
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param message
	 *            The message
	 */
	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The severity
	 */
	@JsonProperty("severity")
	public String getSeverity() {
		return severity;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param severity
	 *            The severity
	 */
	@JsonProperty("severity")
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The source
	 */
	@JsonProperty("source")
	public String getSource() {
		return source;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param source
	 *            The source
	 */
	@JsonProperty("source")
	public void setSource(String source) {
		this.source = source;
	}

}
