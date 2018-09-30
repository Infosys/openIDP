/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.models;

import java.sql.Date;

/**
 * Entity to store transition details
 * 
 * @author Infosys
 *
 */
public class TransitionStatus {

	/** The id. */
	private int id;

	/** The track. */
	private String track;

	/** The start date. */
	private Date startDate;

	/** The end date. */
	private Date endDate;

	/** The status. */
	private String status;

	/** The remarks. */
	private String remarks;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the track.
	 *
	 * @return the track
	 */
	public String getTrack() {
		return track;
	}

	/**
	 * Sets the track.
	 *
	 * @param track the new track
	 */
	public void setTrack(String track) {
		this.track = track;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the remarks.
	 *
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Sets the remarks.
	 *
	 * @param remarks the new remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Update query.
	 *
	 * @return the string
	 */
	public String updateQuery() {
		String query = "Update ttransitionstatus SET startdate = '" + this.getStartDate() + "', enddate='"
				+ this.getEndDate() + "', Remarks='" + this.getRemarks() + "', Status='" + this.getStatus()
				+ "' where id=" + this.getId() + ";";
		return query;
	}

}