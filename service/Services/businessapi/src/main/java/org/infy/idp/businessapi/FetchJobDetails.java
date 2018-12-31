/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.businessapi;

import java.util.ArrayList;
import java.util.List;

import org.infy.entities.triggerinputs.EnvironmentObj;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.EnvironmentOwnerDetail;
import org.infy.idp.entities.jobs.deployinfo.DeployEnv;
import org.infy.idp.entities.jobs.testinfo.TestEnv;
import org.infy.idp.utils.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides methods for fetching information and artifacts related to pipeline
 * or application
 * 
 * @author Infosys
 */
@Component
public class FetchJobDetails {
	private static final Logger logger = LoggerFactory.getLogger(FetchJobDetails.class);

	@Autowired
	private ConfigurationManager configurationManager;

	FetchJobDetails() {

	}

	/**
	 * Returns all environments for app
	 * 
	 * @param app
	 * @param userName
	 * @param userEnv
	 * @return
	 */
	public List<String> getEnvironments(ApplicationInfo app, List<String> userEnv) {
		List<String> env = new ArrayList<>();
		List<EnvironmentOwnerDetail> envDetails = app.getEnvironmentOwnerDetails();
		int envDetailsSize = envDetails.size();
		for (int i = 0; i < envDetailsSize; i++) {

			env.add(envDetails.get(i).getEnvironmentName());

		}
		env = intersection(env, userEnv);
		return env;
	}

	/**
	 * Returns environment names where specified user has access
	 * 
	 * @param app
	 * @param userName
	 * @return List<String>
	 */
	public List<String> getUserEnvironment(ApplicationInfo app, String userName) {
		List<String> env = new ArrayList<>();
		String userStr;
		List<EnvironmentOwnerDetail> envDetails = app.getEnvironmentOwnerDetails();
		int envDetailsSize = envDetails.size();
		for (int i = 0; i < envDetailsSize; i++) {
			userStr = envDetails.get(i).getEnvironmentOwners().toLowerCase();

			if (userStr.contains(userName)) {

				env.add(envDetails.get(i).getEnvironmentName());
			}
		}
		return env;
	}

	/**
	 * Returns QA environment users
	 * 
	 * @param app
	 * @param userName
	 * @return List<String>
	 */

	public List<String> getQAUserEnvironment(ApplicationInfo app, String userName) {
		List<String> env = new ArrayList<>();
		String userStr;
		List<EnvironmentOwnerDetail> envDetails = app.getEnvironmentOwnerDetails();
		int envDetailsSize = envDetails.size();
		for (int i = 0; i < envDetailsSize; i++) {
			if (null == envDetails.get(i).getQa() || "".equals(envDetails.get(i).getQa())) {
				continue;
			}
			userStr = envDetails.get(i).getQa().toLowerCase();
			logger.info("getQAUserEnv: env fetched");
			if (userStr.contains(userName)) {
				env.add(envDetails.get(i).getEnvironmentName());
			}
		}
		return env;
	}

/**
	 * 
	 * @param app
	 * @param idp
	 * @param userName
	 * @param userEnvs
	 * @return List<String>
	 */
	public List<String> getEnvironmentsForTest(ApplicationInfo app, IDPJob idp, String userName,
			List<String> userEnvs) {

		List<String> envFromIDP = getEnvironmentsTest(app, idp, userEnvs, userName);

		List<String> env = new ArrayList<>();
		List<String> testEnv;
		List<EnvironmentOwnerDetail> envDetails = app.getEnvironmentOwnerDetails();
		int envDetailsSize = envDetails.size();
		for (int i = 0; i < envDetailsSize; i++) {
			env.add(envDetails.get(i).getEnvironmentName());
		}
		env = intersection(env, envFromIDP);
		testEnv = getQAUserEnvironment(app, userName);
		env = union(env, testEnv);
		return env;
	}
	
	/**
	 * 
	 * @param app
	 * @param userName
	 * @return List<String>
	 */
	public List<String> getEnvironmentsforPipelineCreation(ApplicationInfo app) {
		List<String> env = new ArrayList<>();
		List<EnvironmentOwnerDetail> envDetails = app.getEnvironmentOwnerDetails();
		for (int i = 0; i < envDetails.size(); i++) {
			env.add(envDetails.get(i).getEnvironmentName());
		}
		return env;
	}

	/**
	 * 
	 * @param idp
	 * @param envInDeploy
	 * @return List<EnvironmentObj>
	 */
	public List<EnvironmentObj> getbizObj(IDPJob idp, List<String> envInDeploy) {
		List<EnvironmentObj> ebj = new ArrayList<>();
		List<DeployEnv> envs = idp.getDeployInfo().getDeployEnv();
		boolean biz = false;
		int envsSize = envs.size();
		for (int i = 0; i < envsSize; i++) {
			if (envInDeploy.contains(envs.get(i).getEnvName()) && envs.get(i).getDeploySteps() != null
					&& !envs.get(i).getDeploySteps().isEmpty()) {
				for (int k = 0; k < envs.get(i).getDeploySteps().size(); k++) {
					if (envs.get(i).getDeploySteps().get(k).getBizserviceAccount() != null
							&& envs.get(i).getDeploySteps().get(k).getBizserviceAccount().length() > 0) {
						biz = true;
						break;
					}
				}
			}

			if (biz) {
				EnvironmentObj evobj = new EnvironmentObj();
				evobj.setEnvName(envs.get(i).getEnvName());
				evobj.setBizCheck("on");
				ebj.add(evobj);
				biz = false;

			}
		}

		return ebj;
	}

	/**
	 * Returns environment list for deploy for user accessed environments
	 * 
	 * @param idp
	 * @param userEnvs
	 * @return List<String>
	 */
	public List<String> getEnvironmentsDeploy(IDPJob idp, List<String> userEnvs) {
		List<String> env = new ArrayList<>();
		if (idp.getDeployInfo() == null || idp.getDeployInfo().getDeployEnv() == null
				|| idp.getDeployInfo().getDeployEnv().isEmpty())
			return env;
		List<DeployEnv> envs = idp.getDeployInfo().getDeployEnv();
		int envSize = envs.size();
		for (int i = 0; i < envSize; i++) {
			if (envs.get(i).getDeploySteps() != null && !envs.get(i).getDeploySteps().isEmpty())
				env.add(envs.get(i).getEnvName());
		}
		env = intersection(userEnvs, env);
		return env;
	}

	/**
	 * Get environment for test
	 * 
	 * @param app
	 * @param idp
	 * @param userEnvs
	 * @param userName
	 * @return List<String>
	 */

	public List<String> getEnvironmentsTest(ApplicationInfo app, IDPJob idp, List<String> userEnvs, String userName) {
		List<String> env = new ArrayList<>();
		List<String> testEnv;
		if (idp.getTestInfo() == null || idp.getTestInfo().getTestEnv() == null
				|| idp.getTestInfo().getTestEnv().isEmpty())
			return env;
		List<TestEnv> envs = idp.getTestInfo().getTestEnv();
		int envSize = envs.size();
		for (int i = 0; i < envSize; i++) {
			if (null != envs.get(i).getTestSteps() && !envs.get(i).getTestSteps().isEmpty()) {
				env.add(envs.get(i).getEnvName());
			}
		}
		testEnv = getQAUserEnvironment(app, userName);
		testEnv = union(userEnvs, testEnv);
		env = intersection(testEnv, env);

		return env;
	}

	/**
	 * Returns common environment for Deployment and Testing
	 * 
	 * @param app
	 * @param idp
	 * @param userEnvs
	 * @param userName
	 * @return List<String>
	 */
	public List<String> getEnvironmentsDeployTest(ApplicationInfo app, IDPJob idp, List<String> userEnvs,
			String userName) {
		List<String> envDeploy = getEnvironmentsDeploy(idp, userEnvs);
		List<String> envTest = getEnvironmentsTest(app, idp, userEnvs, userName);

		return intersection(envDeploy, envTest);
	}

	/**
	 * Returns email recipients for the specified app
	 * 
	 * @param app
	 * @return List<String>
	 */

	public List<String> getEmailRecipients(ApplicationInfo app) {
		String userString;
		List<String> emails = getPipelineAdmins(app);
		userString = app.getReleaseManager();
		emails = splitUsers(userString, emails);
		userString = app.getDevelopers();
		emails = splitUsers(userString, emails);
		List<EnvironmentOwnerDetail> envOwnerDetails = app.getEnvironmentOwnerDetails();

		int envOwnerDetailsSize = envOwnerDetails.size();
		for (int i = 0; i < envOwnerDetailsSize; i++) {
			userString = envOwnerDetails.get(i).getEnvironmentOwners();
			emails = splitUsers(userString, emails);
			if (null != envOwnerDetails.get(i).getQa() || "".equals(envOwnerDetails.get(i).getQa())) {
				userString = envOwnerDetails.get(i).getQa();
				emails = splitUsers(userString, emails);
			}
			if (null != envOwnerDetails.get(i).getdBOwners() || "".equals(envOwnerDetails.get(i).getdBOwners())) {
				userString = envOwnerDetails.get(i).getdBOwners();
				emails = splitUsers(userString, emails);
			}
		}

		return emails;
	}

	/**
	 * Returns pipeline admin names
	 * 
	 * @param app
	 * @return List<String>
	 */
	public List<String> getPipelineAdmins(ApplicationInfo app) {
		List<String> emails = new ArrayList<>();
		String userString = app.getPipelineAdmins();
		emails = splitUsers(userString, emails);
		return emails;
	}

	/**
	 * adds the users which are not available in the specified list
	 * 
	 * @param userString
	 * @param emails
	 * @return List<String>
	 */
	public List<String> splitUsers(String userString, List<String> emails) {
		String[] users = userString.split(",");
		for (int i = 0; i < users.length; i++) {
			if (!emails.contains(users[i])) {
				emails.add(users[i]);
			}
		}
		return emails;
	}
	/**
	 * Fetching all environments except build Env
	 * 
	 * @param idp
	 * @param userName
	 * @param userEnvs
	 * @param buildEnv
	 * @return the required list
	 */
	public List<String> getEnvExceptBuildEnv(IDPJob idp, List<String> userEnvs,
			List<String> buildEnv) {

		List<String> env = getEnvironmentsDeploy(idp, userEnvs);
		env = subtraction(env, buildEnv);
		return env;
	}

	/**
	 * Return env of list1 - env of list2
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public List<String> subtraction(List<String> list1, List<String> list2) {
		list1.removeAll(list2);
		return list1;
	}

	/**
	 * Return env of list1 intersection env of list2
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public List<String> intersection(List<String> list1, List<String> list2) {

		if (list1.isEmpty()) {
			return list1;
		}
		if (list2.isEmpty()) {
			return list2;
		}
		List<String> finalList = new ArrayList<>();
		int listSize = list1.size();
		for (int i = 0; i < listSize; i++) {
			if (list2.contains(list1.get(i))) {
				finalList.add(list1.get(i));
			}
		}
		return finalList;
	}

	/**
	 * 
	 * Return env of list1 U env of list2
	 * 
	 * @param list1
	 * @param list2
	 * @return List<String>
	 */

	public List<String> union(List<String> list1, List<String> list2) {

		if (list1.isEmpty()) {
			return list2;
		}
		if (list2.isEmpty()) {
			return list1;
		}
		List<String> finalList = list2;
		int list1Size = list1.size();
		for (int i = 0; i < list1Size; i++) {
			if (!finalList.contains(list1.get(i))) {
				finalList.add(list1.get(i));
			}
		}
		return finalList;
	}

	/**
	 * Return sonar details of specified job
	 * 
	 * @param idp
	 * @return IDPJob
	 */
	public IDPJob getSonarInfo(IDPJob idp) {

		if (null != idp.getBuildInfo() && null != idp.getBuildInfo().getModules()
				&& (!idp.getBuildInfo().getModules().isEmpty())
				&& null != idp.getBuildInfo().getModules().get(0).getCodeAnalysis()
				&& idp.getBuildInfo().getModules().get(0).getCodeAnalysis().contains("sonar")) {
			if (null == idp.getBuildInfo().getModules().get(0).getSonarUrl()
					|| idp.getBuildInfo().getModules().get(0).getSonarUrl().isEmpty()) {
				idp.getBuildInfo().setSonarUrl(configurationManager.getSonarurl());
			} else {
				idp.getBuildInfo().setSonarUrl(idp.getBuildInfo().getModules().get(0).getSonarUrl());
				idp.getBuildInfo().setSonarUserName(idp.getBuildInfo().getModules().get(0).getSonarUserName());
				idp.getBuildInfo().setSonarPassword(idp.getBuildInfo().getModules().get(0).getSonarPassword());
			}

		}

		return idp;

	}

}
