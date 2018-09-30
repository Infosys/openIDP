/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.basicinfo;

import java.util.List;

import org.infy.idp.entities.triggerparameter.TriggerParameters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store build interval detail
 * 
 * @author Infosys
 *
 */
public class Interval {

	@SerializedName("date")
	@Expose
	private List<String> date;
	@SerializedName("week")
	@Expose
	private List<String> week;
	@SerializedName("time")
	@Expose
	private String time;
	@SerializedName("hour")
	@Expose
	private String hour;

	@SerializedName("type")
	@Expose
	private String type;

	@SerializedName("minute")
	@Expose
	private String minute;
	@SerializedName("details")
	@Expose
	private TriggerParameters details = null;

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public TriggerParameters getDetails() {
		return details;
	}

	public List<String> getDate() {
		return date;
	}

	public void setDate(List<String> date) {
		this.date = date;
	}

	public List<String> getWeek() {
		return week;
	}

	public void setWeek(List<String> week) {
		this.week = week;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public void setDetails(TriggerParameters details) {
		this.details = details;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
