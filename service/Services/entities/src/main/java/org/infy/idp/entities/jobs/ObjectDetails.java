/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import java.sql.Timestamp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**Entity to store details of SAP Objects
 * 
 * @author Infosys
 *
 */
public class ObjectDetails {

	@SerializedName("transportRequest")
	@Expose
	private String transportRequest;

	@SerializedName("objectName")
	@Expose
	private String objectName;

	@SerializedName("objectType")
	@Expose
	private String objectType;

	@SerializedName("timeStamp")
	@Expose
	private Timestamp timeStamp;

	@SerializedName("user")
	@Expose
	private String user;

	@SerializedName("task")
	@Expose
	private String task;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getTransportRequest() {
		return transportRequest;
	}

	public void setTransportRequest(String transportRequest) {
		this.transportRequest = transportRequest;
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

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
