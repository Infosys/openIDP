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
 * Entity to store Jenkins request related information
 * 
 * @author Infosys
 *
 */
public class Performance {
	@SerializedName("Tool")
	@Expose
	private String tool;
	@SerializedName("Throughput")
	@Expose
	private int throughput;
	@SerializedName("Avg_response_time")
	@Expose
	private int avgrestime;

	@SerializedName("Status")
	@Expose
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

	public int getThroughput() {
		return throughput;
	}

	public void setThroughput(int throughput) {
		this.throughput = throughput;
	}

	public int getAvgrestime() {
		return avgrestime;
	}

	public void setAvgrestime(int avgrestime) {
		this.avgrestime = avgrestime;
	}
}
