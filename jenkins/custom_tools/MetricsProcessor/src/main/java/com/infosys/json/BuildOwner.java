/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuildOwner {

	@JsonProperty("id")
	private List<String> id;

	@JsonProperty("id")
	public List<String> getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(List<String> id) {
		this.id = id;
	}

	public BuildOwner() {
		super();
	}

}
