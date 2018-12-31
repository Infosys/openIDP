/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import hudson.cli.CLI;

/**
 * 
 * The class JenkinsCLI contains methods to interact to Jenkins
 * 
 */
@Component
@SuppressWarnings({"PMD.MissingStaticMethodInNonInstantiatableClass","unused"})
public class JenkinsCLI {

	@Autowired
	private ConfigurationManager configmanager;

	@Autowired
	private ResourceLoader resourceLoader;

	private static final Logger logger = LoggerFactory.getLogger(JenkinsCLI.class);
	private String addProjectRole = "add-project-role";
	private String saveJob = "save-job";
	private String addNexusRepo = "add-nexus-repo";
	private String addAlmServer = "add-alm-server";
	private String copySlave = "copy-slave";
	private String createUser = "create-user";
	private String whoAmI = "who-am-i";
	private String addArtifcatoryRepo = "add-artifactory-repo";

	private CLI cli;

	private JenkinsCLI() {

	}

	@PostConstruct
	private void init() {
		try {
			initMethod();

		} catch (MalformedURLException e) {

			logger.error(e.getMessage(), e);

		} catch (IOException e) {

			logger.error(e.getMessage(), e);

		} catch (InterruptedException e) {

			logger.error(e.getMessage(), e);

		} catch (GeneralSecurityException e) {

			logger.error(e.getMessage(), e);

		}

	}

	public void initMethod() throws MalformedURLException, IOException, InterruptedException, GeneralSecurityException {
		try {
			URL url = new URL(configmanager.getJenkinsurl());
			cli = new CLI(url);
			Resource resource = resourceLoader.getResource("classpath:tg_admin");

			cli.authenticate(CLI.loadKey(IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8.name())));

		} catch (MalformedURLException e) {

			logger.error(e.getMessage(), e);
			throw e;
		} catch (IOException e) {

			logger.error(e.getMessage(), e);
			throw e;
		} catch (InterruptedException e) {

			logger.error(e.getMessage(), e);
			throw e;
		} catch (GeneralSecurityException e) {

			logger.error(e.getMessage(), e);

		}
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
			throws IOException {

		int iStatus = 1;
		logger.info("Method call initiated ");

		InputStream is = null;
		OutputStream os = null;

		try {
			logger.info("inside createRole - JenkinsCLI");
			List<String> al = new ArrayList<String>();
			al.add(addProjectRole);
			al.add(roleName);
			al.add(usersList);
			al.add(pattern);
			al.add(roleType);
			al.add(permissions);

			is = null;
			os = new PipedOutputStream();
			if (0 != whoAmI()) {
				initMethod();
			}

			iStatus = cli.execute(al, is, os, os);
			logger.debug(
					"Method execution with status: " + (iStatus == 0 ? "Success-" + iStatus : "Failure-" + iStatus));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {

			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);

				}
			}

		}
		return iStatus;
	}

	/**
	 * This method is used to save a configured job in Jenkins
	 * 
	 *
	 * @param JobName
	 * @param JobConfigFile
	 * 
	 * @return int, status
	 * @throws Exception
	 */
	public int saveJob(String jobName, String jobConfigFile) throws FileNotFoundException, IOException {

		int iStatus = 1;
		logger.info("Method call initiated ");

		InputStream is = null;
		OutputStream os = null;

		try {
			if (0 != whoAmI()) {
				initMethod();
			}

			List<String> al = new ArrayList<String>();
			al.add(saveJob);
			al.add(jobName);
			is = new FileInputStream(jobConfigFile);

			os = new PipedOutputStream();

			iStatus = cli.execute(al, is, os, os);
			logger.debug(
					"Method execution with status: " + (iStatus == 0 ? "Success-" + iStatus : "Failure-" + iStatus));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (GeneralSecurityException | InterruptedException e) {
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {

			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}

		}
		return iStatus;
	}

	/**
	 * Method to add global Nexus configuration
	 * 
	 * @param repoUrl
	 * @param user
	 * @param pwd
	 * @param repoName
	 * @return
	 * @throws Exception
	 */
	public int addNexusRepoGlobConf(String repoUrl, String user, String pwd, String repoName) throws IOException {

		int iStatus = 1;
		logger.info("Method call initiated ");

		InputStream is = null;
		OutputStream os = null;
		OutputStream err = null;
		try {
			if (0 != whoAmI()) {
				initMethod();
			}
			List<String> al = new ArrayList<>();
			al.add(addNexusRepo);
			// NEXUS SERVER URL
			al.add(repoUrl);
			// NEXUS User Name
			al.add(user);
			// NEXUS Password
			al.add(pwd);
			// NEXUS Repository ID
			al.add(repoName);

			is = null;

			os = new ByteArrayOutputStream();
			err = new ByteArrayOutputStream();

			iStatus = cli.execute(al, is, os, err);
			logger.debug(
					"Method execution with status: " + (iStatus == 0 ? "Success-" + iStatus : "Failure-" + iStatus));

		} catch (GeneralSecurityException | InterruptedException e) {
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (err != null) {
				err.close();
			}
			if (os != null) {
				os.close();
			}

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
	/*public String addALMConfig(String inputalmServerName, String almServerUrl) throws IOException {

		int iStatus = 1;
		logger.info("addALMConfig Method call initiated ");
		String almServerName = inputalmServerName;
		InputStream is = null;
		OutputStream os = null;
		OutputStream err = null;

		try {
			if (0 != whoAmI()) {
				initMethod();
			}
			List<String> al = new ArrayList<String>();
			al.add(addAlmServer);
			al.add(almServerName);
			al.add(almServerUrl);
			is = null;

			is = null;

			os = new ByteArrayOutputStream();
			err = new ByteArrayOutputStream();

			iStatus = cli.execute(al, is, os, err);

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

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {

			if (err != null) {
				err.close();
			}
			if (os != null) {
				os.close();
			}

		}

		return almServerName;
	}*/

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
	 */
	public int persistCredentials(String scope, String credId, String userName, String pass, String description,
			String configFileName) {

		int iStatus = 1;
		logger.info("Method call initiated ");

		InputStream is = null;
		OutputStream os = null;

		try {
			if (0 != whoAmI()) {
				initMethod();
			}
			List<String> al = new ArrayList<String>();
			al.add("write-to-credentials-xml");
			al.add(scope);
			al.add(credId);
			al.add(description);
			al.add(userName);
			al.add(pass);

			is = null;

			os = new PipedOutputStream();

			iStatus = cli.execute(al, is, os, os);

			logger.debug(
					"Method execution with status: " + (iStatus == 0 ? "Success-" + iStatus : "Failure-" + iStatus));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {

				if (os != null) {
					os.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

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
	public int copySlave(String slaveName, String workspace, String slaveLabel, String sshKeyPath) throws IOException {

		int iStatus = 1;

		InputStream is = null;
		OutputStream os = null;

		try {
			if (0 != whoAmI()) {
				initMethod();
			}
			List<String> al = new ArrayList<String>();
			al.add(copySlave);
			al.add(slaveName);
			al.add(workspace);
			al.add(slaveLabel);
			al.add(sshKeyPath);

			is = null;

			os = new PipedOutputStream();

			iStatus = cli.execute(al, is, os, os);
			logger.info("slave creation status: " + (iStatus == 0 ? "Success-" + iStatus : "Failure-" + iStatus));

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

		} finally {

			if (os != null) {
				os.close();
			}

		}
		return iStatus;
	}

	/**
	 * This method is used to create users in Jenkins
	 *
	 * @param userName
	 * @param password
	 * @param fullName
	 * @param email
	 * 
	 * @return int, status
	 * @throws Exception
	 */
	public int createUser(String userName, String password, String fullName, String email) throws IOException {

		int iStatus = 1;
		logger.info("Method call initiated ");

		InputStream is = null;
		OutputStream os = null;

		try {
			if (0 != whoAmI()) {
				initMethod();
			}
			List<String> al = new ArrayList<String>();
			al.add(createUser);

			al.add(userName);
			al.add(password);
			al.add(fullName);
			al.add(email);

			is = null;

			os = new PipedOutputStream();
			iStatus = cli.execute(al, is, os, os);

			logger.info(
					"Method execution with status: " + (iStatus == 0 ? "Success-" + iStatus : "Failure-" + iStatus));
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

		} finally {

			if (os != null) {
				os.close();
			}

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
	public int disableJob(String jobName, String jobConfigFile) throws FileNotFoundException, IOException {

		int iStatus = 1;
		logger.info("Method call initiated ");

		InputStream is = null;
		OutputStream os = null;

		try {
			if (0 != whoAmI()) {
				initMethod();
			}
			List<String> al = new ArrayList<String>();
			al.add("disable-job");
			al.add(jobName);
			is = null;

			is = new FileInputStream(jobConfigFile);

			os = new PipedOutputStream();

			iStatus = cli.execute(al, is, os, os);
			logger.debug(
					"Method execution with status: " + (iStatus == 0 ? "Success-" + iStatus : "Failure-" + iStatus));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {

			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}

		}
		return iStatus;
	}

	/**
	 * Add ssh host server in Jenkins global configuration
	 * 
	 * @param hostName the String host name
	 * @param userName the String user name
	 * @return the integer iStatus
	 * @throws Exception
	 */
	public int createSSHServer(String hostName, String userName) throws Exception {

		int iStatus = 1;
		logger.info("Method call initiated ");

		OutputStream os = null;

		try {
			if (0 != whoAmI()) {
				initMethod();
			}
			List<String> al = new ArrayList<String>();
			al.add("addhost");
			al.add(hostName + "_" + userName);
			al.add(hostName);
			al.add(".");
			al.add(userName);

			os = new PipedOutputStream();

			iStatus = cli.execute(al, null, os, os);
			logger.debug(
					"Method execution with status: " + (iStatus == 0 ? "Success-" + iStatus : "Failure-" + iStatus));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {

			if (os != null) {
				os.close();
			}

		}
		return iStatus;
	}

	/**
	 * who-am-i
	 * 
	 * @param hostName the String host name
	 * @param userName the String user name
	 * @return the integer iStatus
	 * @throws Exception
	 */
	public int whoAmI() {

		int iStatus = 1;
		logger.info("Method call initiated ");

		OutputStream os = null;

		try {
			List<String> al = new ArrayList<String>();
			al.add(whoAmI);

			os = new PipedOutputStream();

			iStatus = cli.execute(al, null, os, os);
			logger.info("who am I ?" + os.toString());
			logger.debug(
					"Method execution with status: " + (iStatus == 0 ? "Success-" + iStatus : "Failure-" + iStatus));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			iStatus = -1;
		} finally {
			try {

				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}

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
	public int addArtifactoryRepoGlobConf(String repoUrl, String user, String pwd, String repoName) throws IOException {

		int iStatus = 1;
		logger.info("Method call initiated ");

		InputStream is = null;
		OutputStream os = null;
		OutputStream err = null;
		try {
			if (0 != whoAmI()) {
				initMethod();
			}
			List<String> al = new ArrayList<>();
			al.add(addArtifcatoryRepo);
			// Artifactory SERVER URL
			al.add(repoUrl);
			// Artifactory User Name
			al.add(user);
			// Artifactory Password
			al.add(pwd);
			// Artifactory Repository ID
			al.add(repoName);

			is = null;

			os = new ByteArrayOutputStream();
			err = new ByteArrayOutputStream();

			iStatus = cli.execute(al, is, os, err);
			logger.debug(
					"Method execution with status: " + (iStatus == 0 ? "Success-" + iStatus : "Failure-" + iStatus));

		} catch (GeneralSecurityException | InterruptedException e) {
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (err != null) {
				err.close();
			}
			if (os != null) {
				os.close();
			}

		}
		return iStatus;
	}

}
