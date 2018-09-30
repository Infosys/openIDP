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

	

}
