/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store job parameter details
 * 
 * @author Infosys
 *
 */
public class Migration {

	@SerializedName("hostName")
	@Expose
	private String hostName;
	@SerializedName("srcPath")
	@Expose
	private String srcPath;
	@SerializedName("projName")
	@Expose
	private String projName;
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("password")
	@Expose
	private String password;
	@SerializedName("envFile")
	@Expose
	private String envFile;
	@SerializedName("dbNameOfIndex")
	@Expose
	private String dbNameOfIndex;
	@SerializedName("dbPassword")
	@Expose
	private String dbPassword;
	@SerializedName("ftpLocation")
	@Expose
	private String ftpLocation;
	@SerializedName("fmxLocation")
	@Expose
	private String fmxLocation;
	@SerializedName("ctlName")
	@Expose
	private String ctlName;
	@SerializedName("propertyFile")
	@Expose
	private String propertyFile;

	@SerializedName("fileName")
	@Expose
	private String fileName;
	@SerializedName("rootPath")
	@Expose
	private String rootPath;
	@SerializedName("envPath")
	@Expose
	private String envPath;
	@SerializedName("appPassword")
	@Expose
	private String appPassword;
	@SerializedName("uploadOrDownload")
	@Expose
	private String uploadOrDownload;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getEnvPath() {
		return envPath;
	}

	public void setEnvPath(String envPath) {
		this.envPath = envPath;
	}

	public String getAppPassword() {
		return appPassword;
	}

	public void setAppPassword(String appPassword) {
		this.appPassword = appPassword;
	}

	public String getUploadOrDownload() {
		return uploadOrDownload;
	}

	public void setUploadOrDownload(String uploadOrDownload) {
		this.uploadOrDownload = uploadOrDownload;
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

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
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

	public String getEnvFile() {
		return envFile;
	}

	public void setEnvFile(String envFile) {
		this.envFile = envFile;
	}

	public String getDbNameOfIndex() {
		return dbNameOfIndex;
	}

	public void setDbNameOfIndex(String dbNameOfIndex) {
		this.dbNameOfIndex = dbNameOfIndex;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getFtpLocation() {
		return ftpLocation;
	}

	public void setFtpLocation(String ftpLocation) {
		this.ftpLocation = ftpLocation;
	}

	public String getFmxLocation() {
		return fmxLocation;
	}

	public void setFmxLocation(String fmxLocation) {
		this.fmxLocation = fmxLocation;
	}

	public String getCtlName() {
		return ctlName;
	}

	public void setCtlName(String ctlName) {
		this.ctlName = ctlName;
	}

	public String getPropertyFile() {
		return propertyFile;
	}

	public void setPropertyFile(String propertyFile) {
		this.propertyFile = propertyFile;
	}

}
