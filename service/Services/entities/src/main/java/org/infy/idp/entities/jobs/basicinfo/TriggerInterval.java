/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.basicinfo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store trigger interval list detail
 * 
 * @author Infosys
 *
 */
public class TriggerInterval {

	@SerializedName("interval")
	@Expose
	private List<Interval> interval = null;

	public List<Interval> getInterval() {
		return interval;
	}

	public void setInterval(List<Interval> interval) {
		this.interval = interval;
	}

}
