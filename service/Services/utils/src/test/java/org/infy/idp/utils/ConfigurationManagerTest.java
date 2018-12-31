/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ConfigurationManagerTest {
	@Test
	public void testConfigurationManagerValues() {
		ConfigurationManager testObj = new ConfigurationManager();
		testObj.setKafkahost("kafkahost");
		testObj.setKafkaport("kafkaport");
		testObj.setDriverClassName("driverClassName");
		testObj.setUrl("url");
		testObj.setPostgresqlusername("postgresqlusername");
		testObj.setPostgresqlpassword("postgresqlpassword");
		testObj.setPostgresqldatabase("postgresqldatabase");
		testObj.setPostgresqlschemaname("postgresqlschemaname");
		testObj.setPostgresqlinitialsize("postgresqlinitialsize");
		testObj.setMongocollection("mongocollection");
		testObj.setSonarurl("sonarurl");
		testObj.setOauthurl("oauthurl");
		testObj.setMongodbname("mongodbname");
		testObj.setMongoserver("mongoserver");
		testObj.setMongoport("mongoport");
		testObj.setEmaildomain("emaildomain");
		testObj.setEmailusername("emailusername");
		testObj.setEmailpassword("emailpassword");
		testObj.setEmailsmtphost("emailsmtphost");
		testObj.setEmailsmtpport("emailsmtpport");
		testObj.setEmailserver("emailserver");
		testObj.setEmailsenderid("emailsenderid");
		testObj.setDashboardurl("dashboardurl");
		testObj.setSonardashboardurl("sonardashboardurl");
		testObj.setSharePath("sharePath");
		testObj.setPipelineScriptPath("pipelineScriptPath");
		testObj.setIdplink("idplink");
		testObj.setJenkinsurl("jenkinsurl");
		testObj.setJenkinsstageviewurl("jenkinsstageviewurl");
		testObj.setJenkinsuserid("jenkinsuserid");
		testObj.setJenkinspassword("jenkinspassword");
		testObj.setSuccesspage("successpage");
		testObj.setSecuritypath("securitypath");
		testObj.setUrlgroupid("urlgroupid");
		testObj.setJobinfourl("jobinfourl");
		testObj.setGroupidservice("groupidservice");
		testObj.setGroupdetailsservice("groupdetailsservice");
		testObj.setNoOfBuildsToreview("noOfBuildsToreview");
		testObj.setServerURL("serverURL");
		testObj.setApiVersion("apiVersion");
		testObj.setProjectName("projectName");
		testObj.setAuthorizationToken("authorizationToken");
		testObj.setUserID("userID");
		testObj.setPassword("password");
		testObj.setProxyip("proxyip");
		testObj.setProxyport("proxyport");
		
		assertEquals("kafkahost",testObj.getKafkahost());
		assertEquals("kafkaport",testObj.getKafkaport());
		assertEquals("driverClassName",testObj.getDriverClassName());
		assertEquals("url",testObj.getUrl());
		assertEquals("postgresqlusername",testObj.getPostgresqlusername());
		assertEquals("postgresqlpassword",testObj.getPostgresqlpassword());
		assertEquals("postgresqldatabase",testObj.getPostgresqldatabase());
		assertEquals("postgresqlschemaname",testObj.getPostgresqlschemaname());
		assertEquals("postgresqlinitialsize",testObj.getPostgresqlinitialsize());
		assertEquals("mongocollection",testObj.getMongocollection());
		assertEquals("sonarurl",testObj.getSonarurl());
		assertEquals("oauthurl",testObj.getOauthurl());
		assertEquals("mongodbname",testObj.getMongodbname());
		assertEquals("mongoserver",testObj.getMongoserver());
		assertEquals("mongoport",testObj.getMongoport());
		assertEquals("emaildomain",testObj.getEmaildomain());
		assertEquals("emailusername",testObj.getEmailusername());
		assertEquals("emailpassword",testObj.getEmailpassword());
		assertEquals("emailsmtphost",testObj.getEmailsmtphost());
		assertEquals("emailsmtpport",testObj.getEmailsmtpport());
		assertEquals("emailserver",testObj.getEmailserver());
		assertEquals("emailsenderid",testObj.getEmailsenderid());
		assertEquals("dashboardurl",testObj.getDashboardurl());
		assertEquals("sonardashboardurl",testObj.getSonardashboardurl());
		assertEquals("sharePath",testObj.getSharePath());
		assertEquals("pipelineScriptPath",testObj.getPipelineScriptPath());
		assertEquals("idplink",testObj.getIdplink());
		assertEquals("jenkinsurl",testObj.getJenkinsurl());
		assertEquals("jenkinsstageviewurl",testObj.getJenkinsstageviewurl());
		assertEquals("jenkinsuserid",testObj.getJenkinsuserid());
		assertEquals("jenkinspassword",testObj.getJenkinspassword());
		assertEquals("successpage",testObj.getSuccesspage());
		assertEquals("securitypath",testObj.getSecuritypath());
		assertEquals("urlgroupid",testObj.getUrlgroupid());
		assertEquals("jobinfourl",testObj.getJobinfourl());
		assertEquals("groupidservice",testObj.getGroupidservice());
		assertEquals("groupdetailsservice",testObj.getGroupdetailsservice());
		assertEquals("noOfBuildsToreview",testObj.getNoOfBuildsToreview());
		assertEquals("serverURL",testObj.getServerURL());
		assertEquals("apiVersion",testObj.getApiVersion());
		assertEquals("projectName",testObj.getProjectName());
		assertEquals("authorizationToken",testObj.getAuthorizationToken());
		assertEquals("userID",testObj.getUserID());
		assertEquals("password",testObj.getPassword());
		assertEquals("proxyip",testObj.getProxyip());
		assertEquals("proxyport",testObj.getProxyport());
		


	}

	@Test
	public void testModuleNull() {
		ConfigurationManager testObj = new ConfigurationManager();
		assertNull(testObj.getKafkahost());
		assertNull(testObj.getKafkaport());
		assertNull(testObj.getDriverClassName());
		assertNull(testObj.getUrl());
		assertNull(testObj.getPostgresqlusername());
		assertNull(testObj.getPostgresqlpassword());
		assertNull(testObj.getPostgresqldatabase());
		assertNull(testObj.getPostgresqlschemaname());
		assertNull(testObj.getPostgresqlinitialsize());
		assertNull(testObj.getMongocollection());
		assertNull(testObj.getSonarurl());
		assertNull(testObj.getOauthurl());
		assertNull(testObj.getMongodbname());
		assertNull(testObj.getMongoserver());
		assertNull(testObj.getMongoport());
		assertNull(testObj.getEmaildomain());
		assertNull(testObj.getEmailusername());
		assertNull(testObj.getEmailpassword());
		assertNull(testObj.getEmailsmtphost());
		assertNull(testObj.getEmailsmtpport());
		assertNull(testObj.getEmailserver());
		assertNull(testObj.getEmailsenderid());
		assertNull(testObj.getDashboardurl());
		assertNull(testObj.getSonardashboardurl());
		assertNull(testObj.getSharePath());
		assertNull(testObj.getPipelineScriptPath());
		assertNull(testObj.getIdplink());
		assertNull(testObj.getJenkinsurl());
		assertNull(testObj.getJenkinsstageviewurl());
		assertNull(testObj.getJenkinsuserid());
		assertNull(testObj.getJenkinspassword());
		assertNull(testObj.getSuccesspage());
		assertNull(testObj.getSecuritypath());
		assertNull(testObj.getUrlgroupid());
		assertNull(testObj.getJobinfourl());
		assertNull(testObj.getGroupidservice());
		assertNull(testObj.getGroupdetailsservice());
		assertNull(testObj.getNoOfBuildsToreview());
		assertNull(testObj.getServerURL());
		assertNull(testObj.getApiVersion());
		assertNull(testObj.getProjectName());
		assertNull(testObj.getAuthorizationToken());
		assertNull(testObj.getUserID());
		assertNull(testObj.getPassword());
		assertNull(testObj.getProxyip());
		assertNull(testObj.getProxyport());


	}
}
