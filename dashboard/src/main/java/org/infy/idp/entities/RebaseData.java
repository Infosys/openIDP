/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RebaseData {
	
	@SerializedName("sprintTR")
	@Expose
	private String sprintTR;
	
	@SerializedName("dummyTR")
	@Expose
	private String dummyTR;
	
	@SerializedName("bugTR")
	@Expose
	private String bugTR;

	@SerializedName("objectName")
	@Expose
	private String objectName;
	
	@SerializedName("objectType")
	@Expose
	private String objectType;
	
	public String getSprintTR() {
		return sprintTR;
	}

	public void setSprintTR(String sprintTR) {
		this.sprintTR = sprintTR;
	}

	public String getDummyTR() {
		return dummyTR;
	}

	public void setDummyTR(String dummyTR) {
		this.dummyTR = dummyTR;
	}

	public String getBugTR() {
		return bugTR;
	}

	public void setBugTR(String bugTR) {
		this.bugTR = bugTR;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	
}

