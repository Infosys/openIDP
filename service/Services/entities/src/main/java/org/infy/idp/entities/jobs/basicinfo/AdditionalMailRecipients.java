/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.basicinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store mail recipients information
 * 
 * @author Infosys
 *
 */
public class AdditionalMailRecipients {

	@SerializedName("applicationTeam")
	@Expose
	private String applicationTeam;
	@SerializedName("emailIds")
	@Expose
	private String emailIds;

	public String getApplicationTeam() {
		return applicationTeam;
	}

	public void setApplicationTeam(String applicationTeam) {
		this.applicationTeam = applicationTeam;
	}

	public String getEmailIds() {
		return emailIds;
	}

	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}
}
