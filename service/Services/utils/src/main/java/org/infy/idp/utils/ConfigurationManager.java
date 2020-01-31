/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import java.util.Map;

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

	private String kafkahost;
	private String kafkaport;
	private String driverClassName;
	private String url;
	private String postgresqlusername;
	private String postgresqlpassword;
	private String postgresqldatabase;
	private String postgresqlschemaname;
	private String postgresqlinitialsize;
	private String mongocollection;
	private String sonarurl;
	private String oauthurl;
	private String mongodbname;
	private String mongoserver;
	private String mongoport;
	private String emaildomain;
	private String emailusername;
	private String emailpassword;
	private String emailsmtphost;
	private String emailsmtpport;
	private String emailserver;
	private String emailsenderid;
    private String orchestratorUrl;
	private String dashboardurl;
	private String sonardashboardurl;
	private String sharePath;
	private String pipelineScriptPath;
	private String idplink;
	private String jenkinsurl;
	private String jenkinsstageviewurl;

	private String jenkinsuserid;
	private String jenkinspassword;
	private String successpage;
	private String securitypath;
	private String urlgroupid;

	private String jobinfourl;

	private String groupidservice;
	private String groupdetailsservice;

	private String noOfBuildsToreview;

	private String serverURL;
	private String apiVersion;
	private String projectName;
	private String authorizationToken;
	private String userID;
	private String password;
	private String proxyip;
	private String proxyport;
	

	private Map<String, String> validation;

	public String getEmailserver() {
		return emailserver;
	}

	public void setEmailserver(String emailserver) {
		this.emailserver = emailserver;
	}

	public String getEmailsenderid() {
		return emailsenderid;
	}

	public void setEmailsenderid(String emailsenderid) {
		this.emailsenderid = emailsenderid;
	}

	public String getProxyip() {
		return proxyip;
	}

	public void setProxyip(String proxyip) {
		this.proxyip = proxyip;
	}

	public String getProxyport() {
		return proxyport;
	}

	public void setProxyport(String proxyport) {
		this.proxyport = proxyport;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public String getNoOfBuildsToreview() {
		return noOfBuildsToreview;
	}

	public void setNoOfBuildsToreview(String noOfBuildsToreview) {
		this.noOfBuildsToreview = noOfBuildsToreview;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAuthorizationToken() {
		return authorizationToken;
	}

	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJenkinsstageviewurl() {
		return jenkinsstageviewurl;
	}

	public void setJenkinsstageviewurl(String jenkinsstageviewurl) {
		this.jenkinsstageviewurl = jenkinsstageviewurl;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
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

	public String getOauthurl() {
		return oauthurl;
	}

	public void setOauthurl(String oauthurl) {
		this.oauthurl = oauthurl;
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

	public String getSecuritypath() {
		return securitypath;
	}

	public void setSecuritypath(String securitypath) {
		this.securitypath = securitypath;
	}

	public String getUrlgroupid() {
		return urlgroupid;
	}

	public void setUrlgroupid(String urlgroupid) {
		this.urlgroupid = urlgroupid;
	}

	public String getJobinfourl() {
		return jobinfourl;
	}

	public void setJobinfourl(String jobinfourl) {
		this.jobinfourl = jobinfourl;
	}

	public String getGroupidservice() {
		return groupidservice;
	}

	public void setGroupidservice(String groupidservice) {
		this.groupidservice = groupidservice;
	}

	public String getGroupdetailsservice() {
		return groupdetailsservice;
	}

	public void setGroupdetailsservice(String groupdetailsservice) {
		this.groupdetailsservice = groupdetailsservice;
	}

	public Map<String, String> getValidation() {
		return validation;
	}

	public void setValidation(Map<String, String> validation) {
		this.validation = validation;
	}

	public String getMongocollection() {
		return mongocollection;
	}

	public void setMongocollection(String mongocollection) {
		this.mongocollection = mongocollection;
	}

	public String getSonarurl() {
		return sonarurl;
	}

	public void setSonarurl(String sonarurl) {
		this.sonarurl = sonarurl;
	}

	public String getEmaildomain() {
		return emaildomain;
	}

	public void setEmaildomain(String emaildomain) {
		this.emaildomain = emaildomain;
	}

	public String getMongodbname() {
		return mongodbname;
	}

	public void setMongodbname(String mongodbname) {
		this.mongodbname = mongodbname;
	}

	public String getMongoserver() {
		return mongoserver;
	}

	public void setMongoserver(String mongoserver) {
		this.mongoserver = mongoserver;
	}

	public String getMongoport() {
		return mongoport;
	}

	public void setMongoport(String mongoport) {
		this.mongoport = mongoport;
	}

	public String getEmailusername() {
		return emailusername;
	}

	public void setEmailusername(String emailusername) {
		this.emailusername = emailusername;
	}

	public String getEmailpassword() {
		return emailpassword;
	}

	public void setEmailpassword(String emailpassword) {
		this.emailpassword = emailpassword;
	}

	public String getEmailsmtphost() {
		return emailsmtphost;
	}

	public void setEmailsmtphost(String emailsmtphost) {
		this.emailsmtphost = emailsmtphost;
	}

	public String getEmailsmtpport() {
		return emailsmtpport;
	}

	public void setEmailsmtpport(String emailsmtpport) {
		this.emailsmtpport = emailsmtpport;
	}

	public String getDashboardurl() {
		return dashboardurl;
	}

	public void setDashboardurl(String dashboardurl) {
		this.dashboardurl = dashboardurl;
	}
    public String getOrchestratorUrl() {
		return orchestratorUrl;
	}

	public void setOrchestratorUrl(String orchestratorUrl) {
		this.orchestratorUrl = orchestratorUrl;
	}
	public String getSonardashboardurl() {
		return sonardashboardurl;
	}

	public void setSonardashboardurl(String sonardashboardurl) {
		this.sonardashboardurl = sonardashboardurl;
	}

	public String getIdplink() {
		return idplink;
	}

	public void setIdplink(String idplink) {
		this.idplink = idplink;
	}

	public String getSuccesspage() {
		return successpage;
	}

	public void setSuccesspage(String successpage) {
		this.successpage = successpage;
	}

	public String getKafkahost() {
		return kafkahost;
	}

	public void setKafkahost(String kafkahost) {
		this.kafkahost = kafkahost;
	}

	public String getKafkaport() {
		return kafkaport;
	}

	public void setKafkaport(String kafkaport) {
		this.kafkaport = kafkaport;
	}
}