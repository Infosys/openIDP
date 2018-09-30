package com.infosys.json;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pega {

	@SerializedName("pegaFileList")
	@Expose
	private List<String> pegaFileList;

	public List<String> getPegaFileList() {
		return pegaFileList;
	}

	public void setPegaFileList(List<String> pegaFileList) {
		this.pegaFileList = pegaFileList;
	}
	
	
}
