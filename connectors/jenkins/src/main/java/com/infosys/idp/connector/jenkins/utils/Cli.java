/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infosys.idp.connector.jenkins.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 
 * The class JenkinsCLI contains methods to interact to Jenkins
 * 
 */
@Component
public class Cli {

	@Autowired
	private ConfigurationManager configmanager;


	private static final Logger logger = LoggerFactory.getLogger(Cli.class);
	private String addProjectRole = "add-project-role";
	private String addAlmServer = "add-alm-server";
	private String copySlave = "copy-slave";
	private String addArtifcatoryRepo = "add-artifactory-repo";
	
	private static final String INITIALIZE_METHOD = "Method call initiated ";

	private File file;


	@PostConstruct
	public void downloadFile()  {
		
		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();

		String fromUrl = configmanager.getJenkinsurl() + "/jnlpJars/jenkins-cli.jar";
		URL url = null;
		file = new File("jenkins");
		try {
			url = new URL(fromUrl);
			FileUtils.copyURLToFile(url, file);
		} catch (IOException ex) {
			logger.error("Expection while downloading Jenkins-cli.jar {}", ex.getMessage());
		}

		
	}

	public List<String> getConnection() throws IOException {
		List<String> connectionList = new ArrayList<>();
		
		if(!file.exists()) {
			downloadFile();
		}

		connectionList.add("java");
		connectionList.add("-jar");
		connectionList.add(file.getAbsolutePath());
		connectionList.add("-s");
		connectionList.add(configmanager.getJenkinsurl());
		connectionList.add("-noCertificateCheck");
		connectionList.add("-auth");
		connectionList.add(configmanager.getJenkinsuserid() + ":" + configmanager.getJenkinspassword());
		return connectionList;
	}

	public int executeCommand(List<String> command) throws IOException, InterruptedException {

		String[] out = command.stream().toArray(String[]::new);
		ProcessBuilder pb = new ProcessBuilder(out);
		Process p = pb.start();
		return p.waitFor();
	}

	/**
	 * This method is used to add Role in role strategy plugin and also assigns
	 * users to specified Role in Jenkins
	 *
	 * @param Role        Name
	 * @param User        List (Comma separated)
	 * @param Role        Pattern
	 * @param Role        Type (PROJECT | GLOBAL | SLAVE)
	 * @param Permissions (DEVELOPER | PIPELINE_ADMIN | RELEASE_MANAGER | ENV_OWNER)
	 * 
	 * @return int, status
	 * @throws Exception
	 * 
	 * 
	 */
	public int createRole(String roleName, String usersList, String pattern, String roleType, String permissions)
			throws IOException , InterruptedException {

		int iStatus = 1;
		logger.info(INITIALIZE_METHOD);

		try {
			logger.info("inside createRole - JenkinsCLI");
			List<String> al = getConnection();
			al.add(addProjectRole);
			al.add(roleName);
			al.add(usersList);
			al.add(pattern);
			al.add(roleType);
			al.add(permissions);


			iStatus = executeCommand(al);

		} catch(IOException | InterruptedException ex) {
			logger.error("Error createRole - JenkinsCLI : {}" , ex.getMessage() , ex);
			throw ex; 
		}
		return iStatus;
	}


	/**
	 * This method is used to add ALM Server Name and ALM Server URL in Add ALM
	 * Sever details section to Global Configuration in Jenkins
	 *
	 * @param ALM Server Name
	 * @param ALM Server URL
	 * 
	 * @return int, status
	 * @throws Exception
	 */
	public String addALMConfig(String inputalmServerName, String almServerUrl) throws IOException , InterruptedException {

		int iStatus;
		logger.info("addALMConfig Method call initiated ");
		String almServerName = inputalmServerName;
		
		try {

			List<String> al = getConnection();
			al.add(addAlmServer);
			al.add(almServerName);
			al.add(almServerUrl);
			String[] out = al.stream().toArray(String[]::new);
			ProcessBuilder pb = new ProcessBuilder(out);
			Process p = pb.start();
			iStatus = p.waitFor();
			
			OutputStream os = p.getOutputStream();
			InputStream err = p.getErrorStream();
			
			String output = os.toString();
			String error = err.toString();

			logger.info("Add ALM Server Output Stream: " + output);
			logger.info("Add ALM Server Error Stream: " + error);

			if (iStatus == 0) {
				logger.info("Jenkins ALM Server Config Successful");
			} else {

				// Check for Same Server name in Jenkins Config
				if ((output.contains("ALM SERVER NAME") && output.contains("Please use a different name."))
						|| (error.contains("ALM SERVER NAME") && error.contains("Please use a different name."))) {

					String newName = almServerName + "1";
					logger.info("Existing ALM Server Name: " + almServerName);
					logger.info("Adding New ALM Server Name: " + newName);
					addALMConfig(newName, almServerUrl);

				}

				// Check for Same URL in Jenkins Config
				else if (output.contains("already exists with this URL")
						|| error.contains("already exists with this URL")) {
					String newName = null;
					if (output.contains(" SERVER ")) {
						newName = output.split(" SERVER ")[1];
						almServerName = newName;
					}
					if (error.contains(" SERVER ")) {
						newName = error.split(" SERVER ")[1];
						almServerName = newName;
					}
					if (newName != null) {
						newName = newName.split(" ")[0];
						logger.info("Existing ALM URL: " + newName);
						almServerName = newName;

					}

				}
			}
			
			logger.info("Status : {}", iStatus);

		} catch(IOException | InterruptedException ex) {
			logger.error(" {}" , ex.getMessage() , ex);
			throw ex;
		}

		return almServerName;
	}

	/**
	 * This method is used to store the credentials in Jenkins: used by GIT and SVN
	 *
	 * @param scope          - credentials scope
	 * @param credId         - credentials unique Id
	 * @param userName       - user name to persist
	 * @param pass           - password to persist
	 * @param description    - description
	 * @param configFileName -
	 * 
	 * @return int, status
	 * @throws IOException , InterruptedException 
	 */
	public int persistCredentials(String scope, String credId, String userName, String pass, String description,
			String configFileName) throws IOException , InterruptedException {

		int iStatus = 1;
		logger.info(INITIALIZE_METHOD);

		try {

			List<String> al = getConnection();
			al.add("write-to-credentials-xml");
			al.add(scope);
			al.add(credId);
			al.add(description);
			al.add(userName);
			al.add(pass);

			iStatus = executeCommand(al);
		} catch(IOException | InterruptedException ex) {
			logger.error(" {}" , ex.getMessage() , ex);
			throw ex;
		}
		return iStatus;
	}

	/**
	 * This method is used create a slave for a job in Jenkins
	 *
	 * @param slaveName
	 * @param workspace
	 * @param slaveLabel
	 * 
	 * @return int, status
	 * @throws Exception
	 */
	public int copySlave(String slaveName, String workspace, String slaveLabel, String sshKeyPath) throws InterruptedException , IOException {

		int iStatus = 1;

		try {
			List<String> al = getConnection();
			al.add(copySlave);
			al.add(slaveName);
			al.add(workspace);
			al.add(slaveLabel);
			al.add(sshKeyPath);

			iStatus = executeCommand(al);
			logger.info("slave creation status: " + (iStatus == 0 ? "Success-" + iStatus : "Failure-" + iStatus));

		}catch(IOException | InterruptedException ex) {
			logger.error("Error while creating Slave : {}" ,ex.getMessage(), ex);
			throw ex;
		} 
		return iStatus;
	}

	

	/**
	 * This method is used to disable job in Jenkins
	 * 
	 *
	 * @param JobName
	 * @param JobConfigFile
	 * 
	 * @return int, status
	 * @throws Exception
	 */
	public int disableJob(String jobName, String jobConfigFile) throws InterruptedException, IOException {

		int iStatus = 1;
		logger.info(INITIALIZE_METHOD);

		try {
			List<String> al = getConnection();
			al.add("disable-job");
			al.add(jobName);

			iStatus = executeCommand(al);

		} catch(IOException | InterruptedException ex) {
			logger.error("Disale JOb:  {}" , ex.getMessage() , ex);
			throw ex;
		}
		return iStatus;
	}

	

	/**
	 * Method to add global Artifactory configuration
	 * 
	 * @param repoUrl
	 * @param user
	 * @param pwd
	 * @param repoName
	 * @return
	 * @throws Exception
	 */
	public int addArtifactoryRepoGlobConf(String repoUrl, String user, String pwd, String repoName) throws IOException , InterruptedException {

		int iStatus = 1;
		logger.info(INITIALIZE_METHOD);


		try {

			List<String> al = getConnection();
			al.add(addArtifcatoryRepo);
			// Artifactory SERVER URL
			al.add(repoUrl);
			// Artifactory User Name
			al.add(user);
			// Artifactory Password
			al.add(pwd);
			// Artifactory Repository ID
			al.add(repoName);

			iStatus = executeCommand(al);

		} catch(IOException | InterruptedException ex) {
			logger.error("Error while add artifactory Repo :  {}" , ex.getMessage() , ex);
			throw ex;
		}
		return iStatus;
	}

}
