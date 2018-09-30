/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.deployinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store database deployment information
 * 
 * @author Infosys
 *
 */
public class DeployDatabase {

	@SerializedName("restorusername")
	@Expose
	private String restorusername;
	@SerializedName("restorpassword")
	@Expose
	private String restorpassword;
	@SerializedName("dbusername")
	@Expose
	private String dbusername;
	@SerializedName("dbpassword")
	@Expose
	private String dbpassword;
	@SerializedName("script")
	@Expose
	private String script;

	public String getRestorusername() {
		return restorusername;
	}

	public void setRestorusername(String restorusername) {
		this.restorusername = restorusername;
	}

	public String getRestorpassword() {
		return restorpassword;
	}

	public void setRestorpassword(String restorpassword) {
		this.restorpassword = restorpassword;
	}

	public String getDbusername() {
		return dbusername;
	}

	public void setDbusername(String dbusername) {
		this.dbusername = dbusername;
	}

	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

}
