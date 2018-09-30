/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.common;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store script information
 * 
 * @author Infosys
 *
 */

public class RunScript {
	@SerializedName("scriptType")
	@Expose
	private String scriptType;
	@SerializedName("sshKey")
	@Expose
	private String sshKey;
	@SerializedName("sshPathToKey")
	@Expose
	private String sshPathToKey;

	@SerializedName("dependentPipelineList")
	@Expose
	private List<String> dependentPipelineList;

	@SerializedName("tool")
	@Expose
	private String tool;
	@SerializedName("scriptFilePath")
	@Expose
	private String scriptFilePath;
	@SerializedName("targets")
	@Expose
	private String targets;
	@SerializedName("archiveLogs")
	@Expose
	private String archiveLogs;
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
	@SerializedName("flattenFilePath")
	@Expose
	private String flattenFilePath;

	@SerializedName("javaOptions")
	@Expose
	private String javaOptions;
	@SerializedName("antPropertiesArr")
	@Expose
	private List<AntProperties> antPropertiesArr;

	@SerializedName("port")
	@Expose
	private Integer port;
	@SerializedName("type")
	@Expose
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScriptType() {
		return scriptType;
	}

	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}

	public String getFlattenFilePath() {
		return flattenFilePath;
	}

	public void setFlattenFilePath(String flattenFilePath) {
		this.flattenFilePath = flattenFilePath;
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

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
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

	public String getArchiveLogs() {
		return archiveLogs;
	}

	public void setArchiveLogs(String archiveLogs) {
		this.archiveLogs = archiveLogs;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

	public String getScriptFilePath() {
		return scriptFilePath;
	}

	public void setScriptFilePath(String scriptFilePath) {
		this.scriptFilePath = scriptFilePath;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public List<String> getDependentPipelineList() {
		return dependentPipelineList;
	}

	public void setDependentPipelineList(List<String> dependentPipelineList) {
		this.dependentPipelineList = dependentPipelineList;
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
}
