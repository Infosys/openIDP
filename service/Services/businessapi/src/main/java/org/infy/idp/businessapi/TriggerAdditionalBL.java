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

import org.infy.entities.triggerinputs.Steps;
import org.infy.entities.triggerinputs.TestSteps;
import org.infy.idp.dataapi.services.JobAdditionalDetailsDL;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.code.Scm;
import org.infy.idp.entities.jobs.deployinfo.DeployEnv;
import org.infy.idp.entities.jobs.deployinfo.DeployStep;
import org.infy.idp.entities.jobs.testinfo.TestEnv;
import org.infy.idp.entities.jobs.testinfo.TestStep;
import org.infy.idp.entities.models.GitHubBrachModel;
import org.infy.idp.utils.BitBucketBranchTagFetcher;
import org.infy.idp.utils.GitHubBranchTagFetcher;
import org.infy.idp.utils.TFSBranchFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import net.sf.json.JSONException;
@Component
public class TriggerAdditionalBL {
	private static final Logger logger = LoggerFactory.getLogger(TriggerAdditionalBL.class);
	@Autowired
	private JobAdditionalDetailsDL jobAddInfoDL;
	@Autowired
	private GitHubBranchTagFetcher gitHubBranchTagFetcher;
	@Autowired
	private BitBucketBranchTagFetcher bitBucketBranchTagFetcher;
	@Autowired
	private TFSBranchFetcher tFSBranchFetcher;
	TriggerAdditionalBL() {
	}

	public ArrayList<String> getDeployStep(List<DeployStep> list) {
		ArrayList<String> arrayList = new ArrayList<>();
		for (DeployStep tempDeploy : list) {
			arrayList.add(tempDeploy.getStepName());
		}
		return arrayList;
	}
	public String gitSelected(List<Scm> scm) {

		String flag = "off";
		if (scm == null)
			return flag;

		for (Scm scmSection : scm) {
			if ("git".equalsIgnoreCase(scmSection.getType())) {
				flag = "on";
				return flag;
			}
		}

		return flag;
	}

	public ArrayList<TestSteps> getTestStep(List<TestStep> list) {
		ArrayList<TestSteps> arrayList = new ArrayList<>();
		for (TestStep tempDeploy : list) {
			TestSteps testSteps = new TestSteps();
			testSteps.setStepName(tempDeploy.getStepName());
			if (tempDeploy.getTest() != null && tempDeploy.getTest().getTestTypeName() != null
					&& !tempDeploy.getTest().getTestTypeName().equals("")) {
				testSteps.setToolName(tempDeploy.getTest().getTestTypeName());
			} else {
				testSteps.setToolName("na");
			}
			arrayList.add(testSteps);
		}
		return arrayList;
	}
	public List<ArrayList<String>> gitHubBranchesTagsFetcher(GitHubBrachModel branchModel) {
		List<ArrayList<String>> list = null;
		try {
			list = new ArrayList<ArrayList<String>>();
			list = gitHubBranchTagFetcher.getBranchList(branchModel);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	public List<ArrayList<String>> bitBucketbranchesTagsFetcher(String repoUrl, String username, String pwd,
			String projectUrl, String proxy, String port) {
		List<ArrayList<String>> list = null;
		try {
			list = new ArrayList<ArrayList<String>>();
			list = bitBucketBranchTagFetcher.getBranchList(repoUrl, username, pwd, projectUrl, proxy, port);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	public ArrayList<String> branchFetcher(String repoUrl, String projPath, String username, String pwd) {
		ArrayList<String> list = null;
		list = new ArrayList<>();
		try {
			list = tFSBranchFetcher.getAllBranches(repoUrl, projPath, "1.0", username, pwd);
			if (list == null) {
				list = new ArrayList<>();
			}
		} catch (JSONException | NullPointerException e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	public Steps fecthTriggerSteps(String appName, String pipelineName, String envName) {
		Steps steps = new Steps();
		List<DeployStep> deploySteps = null;
		List<TestStep> testSteps = null;
		try {
			IDPJob idp = jobAddInfoDL.getPipelineInfo(appName, pipelineName);
			List<DeployEnv> deployEnvs = idp.getDeployInfo().getDeployEnv();
			List<TestEnv> testEnvs = idp.getTestInfo().getTestEnv();
			int deployEnvSize = deployEnvs.size();
			for (int i = 0; i < deployEnvSize; i++) {
				if (deployEnvs.get(i).getEnvName().equalsIgnoreCase(envName)
						&& (null != deployEnvs.get(i).getDeploySteps()
								&& !deployEnvs.get(i).getDeploySteps().isEmpty())) {
					deploySteps = deployEnvs.get(i).getDeploySteps();
					steps.setDeploySteps(getDeployStep(deploySteps));
				}
			}
			for (int i = 0; i < testEnvs.size(); i++) {
				if (testEnvs.get(i).getEnvName().equalsIgnoreCase(envName)
						&& (null != testEnvs.get(i).getTestSteps() && !testEnvs.get(i).getTestSteps().isEmpty())) {
					testSteps = testEnvs.get(i).getTestSteps();
					steps.setTestSteps(getTestStep(testSteps));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return steps;
	}

	

	public List<String> getPairNames(IDPJob idp, String envName) {
		logger.info("inside get trigger details");
		Gson gson = new Gson();
		List<String> pairnames = new ArrayList<>();
		logger.info("idp json " + gson.toJson(idp));
		List<DeployEnv> deployEnvs = idp.getDeployInfo().getDeployEnv();
		for (DeployEnv deployEnv : deployEnvs) {
			logger.info("deployenv : " + gson.toJson(deployEnv));
			if (envName.equalsIgnoreCase(deployEnv.getEnvName())) {
				int depStepsSize = deployEnv.getDeploySteps().size();
				for (int i = 0; i < depStepsSize; i++) {
					pairnames.add(deployEnv.getDeploySteps().get(i).getDeployToContainer().getPairName());
					logger.info("added pairname : "
							+ deployEnv.getDeploySteps().get(i).getDeployToContainer().getPairName());
				}
				break;
			}
		}
		return pairnames;
	}
}
