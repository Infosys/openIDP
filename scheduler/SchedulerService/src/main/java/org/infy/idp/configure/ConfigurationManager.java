/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/



package org.infy.idp.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration manager class
 */
@ConfigurationProperties
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class ConfigurationManager {
	
	
	private String postgresqlusername;
	private String postgresqlpassword;
	private String postgresqldatabase;
	private String postgresqlschemaname;
	private String postgresqlinitialsize;
	private String url;
	private String jenkinsurl;
	

	private String jenkinsuserid;
	private String jenkinspassword;
	
	public ConfigurationManager(){
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPostgresqlusername() {
		return postgresqlusername;
	}
	public void setPostgresqlusername(String postgresqlusername) {
		this.postgresqlusername = postgresqlusername;
	}
	public String getPostgresqlpassword() {
		return postgresqlpassword;
	}
	public void setPostgresqlpassword(String postgresqlpassword) {
		this.postgresqlpassword = postgresqlpassword;
	}
	public String getPostgresqldatabase() {
		return postgresqldatabase;
	}
	public void setPostgresqldatabase(String postgresqldatabase) {
		this.postgresqldatabase = postgresqldatabase;
	}
	public String getPostgresqlschemaname() {
		return postgresqlschemaname;
	}
	public void setPostgresqlschemaname(String postgresqlschemaname) {
		this.postgresqlschemaname = postgresqlschemaname;
	}
	public String getPostgresqlinitialsize() {
		return postgresqlinitialsize;
	}
	public void setPostgresqlinitialsize(String postgresqlinitialsize) {
		this.postgresqlinitialsize = postgresqlinitialsize;
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


	
	
	
}
