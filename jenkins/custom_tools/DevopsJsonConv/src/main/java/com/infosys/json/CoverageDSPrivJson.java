/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoverageDSPrivJson {
	@JsonProperty("moduleName")
	private String moduleName;
	@JsonProperty("lineCoverage")
	private String lineCoverage;
	@JsonProperty("blockCoverage")
	private String blockCoverage;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getLineCoverage() {
		return lineCoverage;
	}

	public void setLineCoverage(String lineCoverage) {
		this.lineCoverage = lineCoverage;
	}

	public String getBlockCoverage() {
		return blockCoverage;
	}

	public void setBlockCoverage(String blockCoverage) {
		this.blockCoverage = blockCoverage;
	}
}
