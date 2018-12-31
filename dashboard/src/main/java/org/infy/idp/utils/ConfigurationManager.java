
/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration manager class
 */
@ConfigurationProperties
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
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
	private String idpurl;
	private String idppostgresqlusername;
	private String idppostgresqlpassword;
	private String idppostgresqldatabase;
	private String idppostgresqlschemaname;
	private String idppostgresqlinitialsize;
	private String batchSize;
	private String clientid;
	private String skipAuthentication;
	private String appsecret;
	private String appversion;
	private String ldapurl;
	private String ldapBase;
	private Integer accessTokenValidity;
	private Integer refreshTokenValidity;
	private String jenkinsURL;
	private String jenkinsID;
	private String jenkinsPassword;
	
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
	
	public String getJenkinsURL() {
		return jenkinsURL;
	}

	public void setJenkinsURL(String jenkinsURL) {
		this.jenkinsURL = jenkinsURL;
	}

	public String getJenkinsID() {
		return jenkinsID;
	}

	public void setJenkinsID(String jenkinsID) {
		this.jenkinsID = jenkinsID;
	}

	public String getJenkinsPassword() {
		return jenkinsPassword;
	}

	public void setJenkinsPassword(String jenkinsPassword) {
		this.jenkinsPassword = jenkinsPassword;
	}



	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getSkipAuthentication() {
		return skipAuthentication;
	}

	public void setSkipAuthentication(String skipAuthentication) {
		this.skipAuthentication = skipAuthentication;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getLdapurl() {
		return ldapurl;
	}

	public void setLdapurl(String ldapurl) {
		this.ldapurl = ldapurl;
	}

	public String getLdapBase() {
		return ldapBase;
	}

	public void setLdapBase(String ldapBase) {
		this.ldapBase = ldapBase;
	}

	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
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

	public String getIdpurl() {
		return idpurl;
	}

	public void setIdpurl(String idpurl) {
		this.idpurl = idpurl;
	}

	public String getIdppostgresqlusername() {
		return idppostgresqlusername;
	}

	public void setIdppostgresqlusername(String idppostgresqlusername) {
		this.idppostgresqlusername = idppostgresqlusername;
	}

	public String getIdppostgresqlpassword() {
		return idppostgresqlpassword;
	}

	public void setIdppostgresqlpassword(String idppostgresqlpassword) {
		this.idppostgresqlpassword = idppostgresqlpassword;
	}

	public String getIdppostgresqldatabase() {
		return idppostgresqldatabase;
	}

	public void setIdppostgresqldatabase(String idppostgresqldatabase) {
		this.idppostgresqldatabase = idppostgresqldatabase;
	}

	public String getIdppostgresqlinitialsize() {
		return idppostgresqlinitialsize;
	}

	public void setIdppostgresqlinitialsize(String idppostgresqlinitialsize) {
		this.idppostgresqlinitialsize = idppostgresqlinitialsize;
	}

	public String getIdppostgresqlschemaname() {
		return idppostgresqlschemaname;
	}

	public void setIdppostgresqlschemaname(String idppostgresqlschemaname) {
		this.idppostgresqlschemaname = idppostgresqlschemaname;
	}

	public String getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(String batchSize) {
		this.batchSize = batchSize;
	}



}