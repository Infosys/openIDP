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
 * Entity to store server details
 * 
 * @author Infosys
 *
 */

public class Server {

	@SerializedName("hostName")
	@Expose
	private String hostName;
	@SerializedName("srcPath")
	@Expose
	private String srcPath;
	@SerializedName("destinationPath")
	@Expose
	private String destinationPath;
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("password")
	@Expose
	private String password;
	@SerializedName("serverName")
	@Expose
	private String serverName;
	@SerializedName("hostUserName")
	@Expose
	private String hostUserName;
	@SerializedName("hostPassword")
	@Expose
	private String hostPassword;
	@SerializedName("dbOwner")
	@Expose
	private String dbOwner;
	@SerializedName("repo")
	@Expose
	private String repo;
	@SerializedName("datFilePath")
	@Expose
	private String datFilePath;
	@SerializedName("dbNameOfIndex")
	@Expose
	private String dbNameOfIndex;
	@SerializedName("dbName")
	@Expose
	private String dbName;
	@SerializedName("host")
	@Expose
	private String host;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getHostUserName() {
		return hostUserName;
	}

	public void setHostUserName(String hostUserName) {
		this.hostUserName = hostUserName;
	}

	public String getHostPassword() {
		return hostPassword;
	}

	public void setHostPassword(String hostPassword) {
		this.hostPassword = hostPassword;
	}

	public String getDbOwner() {
		return dbOwner;
	}

	public void setDbOwner(String dbOwner) {
		this.dbOwner = dbOwner;
	}

	public String getRepo() {
		return repo;
	}

	public void setRepo(String repo) {
		this.repo = repo;
	}

	public String getDatFilePath() {
		return datFilePath;
	}

	public void setDatFilePath(String datFilePath) {
		this.datFilePath = datFilePath;
	}

	public String getDbNameOfIndex() {
		return dbNameOfIndex;
	}

	public void setDbNameOfIndex(String dbNameOfIndex) {
		this.dbNameOfIndex = dbNameOfIndex;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

}
