/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infosys.idp.connector.jenkins.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ConfigurationManager entity for Kafka configuration
 * 
 * @author Infosys
 *
 */
@ConfigurationProperties
@Component
public class ConfigurationManager {
	private String jenkinsurl;
	private String jenkinsstageviewurl;
	private String jenkinsuserid;
	private String jenkinspassword;
	private String sharePath;
	private String pipelineScriptPath;
	
	public String getJenkinsstageviewurl() {
		return jenkinsstageviewurl;
	}

	public void setJenkinsstageviewurl(String jenkinsstageviewurl) {
		this.jenkinsstageviewurl = jenkinsstageviewurl;
	}


	public String getJenkinsurl() {
		return jenkinsurl;
	}

	public void setJenkinsurl(String jenkinsurl) {
		this.jenkinsurl = jenkinsurl;
	}

	public String getJenkinsuserid() {
		return jenkinsuserid;
	}

	public void setJenkinsuserid(String jenkinsuserid) {
		this.jenkinsuserid = jenkinsuserid;
	}

	public String getJenkinspassword() {
		return jenkinspassword;
	}

	public void setJenkinspassword(String jenkinspassword) {
		this.jenkinspassword = jenkinspassword;
	}
	
	public String getSharePath() {
		return sharePath;
	}

	public void setSharePath(String sharePath) {
		this.sharePath = sharePath;
	}

	public String getPipelineScriptPath() {
		return pipelineScriptPath;
	}

	public void setPipelineScriptPath(String pipelineScriptPath) {
		this.pipelineScriptPath = pipelineScriptPath;
	}
}