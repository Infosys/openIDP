/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.common;

/**
 * Entity to store notification detail
 * 
 * @author Infosys
 *
 */
public class Notification {
	private String date;
	private String status;
	private String pipelineName;

	public Notification() {
	}

	public Notification(String date, String status, String pipelineName) {
		this.date = date;
		this.status = status;
		this.pipelineName = pipelineName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}
}
