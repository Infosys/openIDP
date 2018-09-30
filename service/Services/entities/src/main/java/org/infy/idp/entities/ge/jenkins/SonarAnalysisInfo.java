/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.ge.jenkins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Entity to store Sonar analysis related details
 * 
 * @author Infosys
 *
 */
public class SonarAnalysisInfo {
	@SerializedName("MajorIssue")
	@Expose
	private int majorIssue;
	@SerializedName("MinorIssue")
	@Expose
	private int minorIssue;
	@SerializedName("LinesOfCode")
	@Expose
	private int linesOfCode;
	@SerializedName("status")
	@Expose
	private String status;

	public int getMajorIssue() {
		return majorIssue;
	}

	public void setMajorIssue(int majorIssue) {
		this.majorIssue = majorIssue;
	}

	public int getMinorIssue() {
		return minorIssue;
	}

	public void setMinorIssue(int minorIssue) {
		this.minorIssue = minorIssue;
	}

	public int getLinesOfCode() {
		return linesOfCode;
	}

	public void setLinesOfCode(int linesOfCode) {
		this.linesOfCode = linesOfCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
