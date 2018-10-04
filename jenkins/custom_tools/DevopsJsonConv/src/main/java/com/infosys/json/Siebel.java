/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Siebel {
	@SerializedName("repoList")
	@Expose
	private List<String> repoList;
	@SerializedName("nonRepoList")
	@Expose
	private List<String> nonRepoList;

	public List<String> getRepoList() {
		return repoList;
	}

	public void setRepoList(List<String> repoList) {
		this.repoList = repoList;
	}

	public List<String> getNonRepoList() {
		return nonRepoList;
	}

	public void setNonRepoList(List<String> nonRepoList) {
		this.nonRepoList = nonRepoList;
	}
}
