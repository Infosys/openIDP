/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Istanbul {
@JsonProperty("lineCoverage")
private String lineCoverage;

public String getLineCoverage() {
	return lineCoverage;
}

public void setLineCoverage(String lineCoverage) {
	this.lineCoverage = lineCoverage;
}


}
