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
@JsonPropertyOrder({ "id" })
public class Application {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("id")
	private String id;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Application() {
		// Empty Constructor
	}

	/**
	 * 
	 * @param applicationID
	 */
	public Application(String id) {
		this.id = id;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The applicationID
	 */
	@JsonProperty("id")
	public String getID() {
		return id;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param applicationID
	 *            The applicationID
	 */
	@JsonProperty("id")
	public void setID(String ID) {
		this.id = ID;
	}

}
