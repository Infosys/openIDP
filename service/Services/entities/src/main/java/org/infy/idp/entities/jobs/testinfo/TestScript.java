/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.testinfo;

import java.util.List;

import org.infy.idp.entities.jobs.common.AntProperties;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store test script details
 * 
 * @author Infosys
 *
 */
public class TestScript {

	@SerializedName("scriptType")
	@Expose
	private String scriptType;
	@SerializedName("pathOfFile")
	@Expose
	private String pathOfFile;
	@SerializedName("targets")
	@Expose
	private String targets;
	@SerializedName("host")
	@Expose
	private String host;
	@SerializedName("userName")
	@Expose
	private String userName;
	@SerializedName("password")
	@Expose
	private String password;
	@SerializedName("script")
	@Expose
	private String script;
	@SerializedName("pathToFiles")
	@Expose
	private String pathToFiles;
	@SerializedName("destinationDir")
	@Expose
	private String destinationDir;
	@SerializedName("flatternFilePath")
	@Expose
	private String flatternFilePath;
	@SerializedName("reportType")
	@Expose
	private String reportType;
	@SerializedName("pathOfReports")
	@Expose
	private String pathOfReports;
	@SerializedName("javaOptions")
	@Expose
	private String javaOptions;
	@SerializedName("antPropertiesArr")
	@Expose
	private List<AntProperties> antPropertiesArr;

	@SerializedName("sshKey")
	@Expose
	private String sshKey;
	@SerializedName("sshPathToKey")
	@Expose
	private String sshPathToKey;

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getPathOfReports() {
		return pathOfReports;
	}

	public void setPathOfReports(String pathOfReports) {
		this.pathOfReports = pathOfReports;
	}

	public String getScriptType() {
		return scriptType;
	}

	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}

	public String getPathOfFile() {
		return pathOfFile;
	}

	public void setPathOfFile(String pathOfFile) {
		this.pathOfFile = pathOfFile;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getPathToFiles() {
		return pathToFiles;
	}

	public void setPathToFiles(String pathToFiles) {
		this.pathToFiles = pathToFiles;
	}

	public String getDestinationDir() {
		return destinationDir;
	}

	public void setDestinationDir(String destinationDir) {
		this.destinationDir = destinationDir;
	}

	public String getFlatternFilePath() {
		return flatternFilePath;
	}

	public void setFlatternFilePath(String flatternFilePath) {
		this.flatternFilePath = flatternFilePath;
	}

	public String getJavaOptions() {
		return javaOptions;
	}

	public void setJavaOptions(String javaOptions) {
		this.javaOptions = javaOptions;
	}

	public List<AntProperties> getAntPropertiesArr() {
		return antPropertiesArr;
	}

	public void setAntPropertiesArr(List<AntProperties> antPropertiesArr) {
		this.antPropertiesArr = antPropertiesArr;
	}

	public String getSshKey() {
		return sshKey;
	}

	public void setSshKey(String sshKey) {
		this.sshKey = sshKey;
	}

	public String getSshPathToKey() {
		return sshPathToKey;
	}

	public void setSshPathToKey(String sshPathToKey) {
		this.sshPathToKey = sshPathToKey;

	}

}
