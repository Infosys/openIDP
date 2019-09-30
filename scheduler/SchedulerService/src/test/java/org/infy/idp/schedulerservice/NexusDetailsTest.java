/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.schedulerservice;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.sql.SQLException;

import java.util.List;

import org.infy.idp.configure.AppContext;
import org.infy.idp.configure.ConfigurationManager;
import org.infy.idp.configure.PostGreSqlDbContext;
import org.infy.idp.entities.DeployArtifact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class NexusDetailsTest {

	@Spy
	@Autowired
	private ConfigurationManager configmanager;

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@InjectMocks
	private ArtifactHistory artifactHistory;

	@InjectMocks
	private NexusDetails nexusDetails;

	@Before
	public void setup() {
		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = PostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(postGreSqlDbContext);

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPipelineInfo() {
		try {
			String depcryptedIDP = nexusDetails.getPipelineInfo("TCM", "TCM_CICD");
			assertNotNull(depcryptedIDP);
		} catch (NullPointerException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testGetNexus() {
		String idpData = "{\"basicInfo\":{\"applicationName\":\"CustomerPortal\",\"pipelineName\":\"DB_CICD\",\"buildServerOS\":\"windows\",\"masterSequence\":\"pipeline\",\"engine\":\"Jenkins Workflow\",\"buildInterval\":{\"pollSCM\":\"off\",\"buildInterval\":\"\",\"buildIntervalValue\":\"0\",\"buildAtSpecificInterval\":\"off\",\"event\":[{\"type\":\"--Select--\",\"hour\":\"00\",\"minute\":\"00\"}]},\"additionalMailRecipients\":{\"applicationTeam\":\"\",\"emailIds\":\"\"},\"pipelineStatus\":\"edit\",\"customTriggerInterval\":{\"interval\":[{\"time\":\"15\",\"type\":\"Day\",\"minute\":\"00\",\"details\":{\"applicationName\":\"CustomerPortal\",\"pipelineName\":\"DB_CICD\",\"jobBuildId\":\"\",\"releaseNumber\":\"R18.3\",\"buildartifactNumber\":\"\",\"gitTag\":\"\",\"jiraProjectKey\":\"\",\"userStoryString\":\"\",\"userName\":\"dummyuser\",\"slaveName\":\"java-agent-1\",\"testSlaveName\":\"\",\"envSelected\":\"\",\"build\":{\"branchSelected\":\"\",\"module\":[]},\"testSelected\":\"off\",\"artifactorySelected\":\"on\",\"emailed\":\"dummyuser@xyz.com,dummyuser@xyz.com,dummyuser@xyz.com\",\"testStep\":[],\"technology\":\"J2EE/Maven\",\"repoDeployStatus\":\"\",\"nonRepoDeployStatus\":\"\",\"branchOrTag\":\"\",\"subApplicationName\":\"\",\"testPlanId\":\"\",\"testSuitId\":\"\",\"mtmStepName\":\"\",\"tfsWorkItem\":\"\",\"ibmRQMTestSuiteId\":\"\",\"jobParam\":[]}},{\"week\":[\"Mon\"],\"time\":\"10\",\"type\":\"Week\",\"minute\":\"10\",\"details\":{\"applicationName\":\"CustomerPortal\",\"pipelineName\":\"DB_CICD\",\"jobBuildId\":\"\",\"releaseNumber\":\"R18.3\",\"buildartifactNumber\":\"\",\"gitTag\":\"\",\"jiraProjectKey\":\"\",\"userStoryString\":\"\",\"userName\":\"dummyuser\",\"slaveName\":\"java-agent-1\",\"testSlaveName\":\"\",\"envSelected\":\"DEV\",\"build\":{\"branchSelected\":\"\",\"module\":[]},\"deploy\":{\"version\":\"\",\"artifactID\":\"\",\"nexusId\":\"\",\"deployArtifact\":{\"version\":\"\",\"artifactID\":\"DB_CICD\",\"artifactName\":\"CustomerPortal_DB_CICD_latestArtifact\",\"timestamp\":\"\",\"groupId\":\"CustomerPortal\",\"userInfo\":\"\",\"downloadURL\":\"\",\"nexusURL\":\"dummyuser.ad.xyz.com:8081\",\"repoName\":\"idp_Nexus\"},\"dbDeployRollbackType\":\"\",\"deployStep\":[\"tomcat\"],\"subModule\":[],\"deployModule\":[]},\"testSelected\":\"off\",\"artifactorySelected\":\"on\",\"emailed\":\"dummyuser@xyz.com,dummyuser@xyz.com,dummyuser@xyz.com\",\"testStep\":[],\"technology\":\"J2EE/Maven\",\"rmAssemblies\":\"\",\"repoDeployStatus\":\"\",\"nonRepoDeployStatus\":\"\",\"branchOrTag\":\"\",\"subApplicationName\":\"\",\"testPlanId\":\"\",\"testSuitId\":\"\",\"mtmStepName\":\"\",\"tfsWorkItem\":\"\",\"ibmRQMTestSuiteId\":\"\",\"jobParam\":[]}},{\"date\":[\"1\"],\"time\":\"15\",\"type\":\"Month\",\"minute\":\"00\",\"details\":{\"applicationName\":\"CustomerPortal\",\"pipelineName\":\"DB_CICD\",\"jobBuildId\":\"\",\"releaseNumber\":\"R18.4\",\"buildartifactNumber\":\"\",\"gitTag\":\"\",\"jiraProjectKey\":\"\",\"userStoryString\":\"\",\"userName\":\"dummyuser\",\"slaveName\":\"java-agent-1\",\"testSlaveName\":\"\",\"envSelected\":\"\",\"build\":{\"branchSelected\":\"\",\"module\":[]},\"testSelected\":\"off\",\"artifactorySelected\":\"on\",\"emailed\":\"dummyuser@xyz.com,dummyuser@xyz.com,dummyuser@xyz.com\",\"testStep\":[],\"technology\":\"J2EE/Maven\",\"repoDeployStatus\":\"\",\"nonRepoDeployStatus\":\"\",\"branchOrTag\":\"\",\"subApplicationName\":\"\",\"testPlanId\":\"\",\"testSuitId\":\"\",\"mtmStepName\":\"\",\"tfsWorkItem\":\"\",\"ibmRQMTestSuiteId\":\"\",\"jobParam\":[]}}]},\"customPipelineAdmins\":[]},\"code\":{\"category\":\"Standard\",\"technology\":\"J2EE/Maven\",\"scm\":[{\"type\":\"git\",\"url\":\"https://dummyuser@bitbucket.org/dummyuser/tcm_maven.git\",\"userName\":\"dummyuserxyz.com\",\"password\":\"Infy8650@angular\",\"repositoryBrowser\":\"bitBucket\",\"browserUrl\":\"https://bitbucket.org\",\"projectName\":\"\",\"branch\":\"master\",\"projPath\":\"\",\"moduleName\":\"\",\"clearcaseType\":\"\",\"vobName\":\"\",\"snapshotViewName\":\"\",\"configSpec\":\"\",\"developmentStreamName\":\"\",\"buildConfiguration\":\"\",\"buildDefinition\":\"\",\"repositoryWorkspace\":\"\",\"projArea\":\"\",\"port\":\"\",\"version\":\"\",\"exclude\":\"\",\"proxy\":\"dummyuser\",\"proxyPort\":\"80\",\"appRepo\":\"on\",\"deployRepo\":\"on\",\"testRepo\":\"on\"}],\"jobParam\":[],\"buildScript\":[{\"tool\":\"\"},{\"tool\":\"\"},{}],\"subApplication\":\"\"},\"buildInfo\":{\"buildtool\":\"maven\",\"artifactToStage\":{\"artifact\":\"**/*.war\",\"artifactRepo\":{\"repoURL\":\"dummyuser.ad.xyz.com:8081\",\"repoName\":\"idp_Nexus\",\"repoUsername\":\"admin\",\"repoPassword\":\"dummy\"},\"artifactRepoName\":\"nexus\"},\"castAnalysis\":{},\"modules\":[{\"moduleName\":\"TCMTest\",\"relativePath\":\"tcm_maven/TCMTest/pom.xml\",\"codeAnalysis\":[\"sonar\",\"off\",\"off\",\"off\"],\"unitTesting\":\"off\",\"codeCoverage\":\"off\",\"args\":\"\",\"compile\":\"on\",\"clean\":\"on\",\"install\":\"on\",\"MVNOPTS\":\"\",\"interval\":\"\",\"sonarUrl\":\"http://dummyuser:8080/\",\"sonarUserName\":\"\",\"sonarPassword\":\"\"}],\"postBuildScript\":{\"dependentPipelineList\":[]},\"subModule\":[],\"sonarUrl\":\"http://dummyuser:8080/\",\"sonarUserName\":\"\",\"sonarPassword\":\"\"},\"deployInfo\":{\"deployEnv\":[{\"envName\":\"DEV\",\"envFlag\":\"on\",\"scriptType\":\"\",\"deploySteps\":[{\"stepName\":\"tomcat\",\"deployOS\":\"\",\"runScript\":{\"scriptType\":\"\",\"scriptFilePath\":\"\",\"targets\":\"\"},\"deployToContainer\":{\"containerName\":\"tomcat\",\"serverManagerURL\":\"http://dummyHost122:7979\",\"resourceToBeDeployed\":\"manualTomcat\",\"warPath\":\"**/*.war\",\"contextPath\":\"/TCMTest\",\"userName\":\"tomcatadmin\",\"password\":\"tomcatadmin\",\"targetCellName\":\"\",\"targetNodeName\":\"\",\"targetServerName\":\"\",\"hostName\":\"\",\"port\":\"\",\"updateDB\":\"\",\"rollbackStrategy\":\"\",\"narOS\":\"\",\"deployedFolder\":\"\",\"fileName\":\"\",\"dbDeployPipelineList\":[],\"pairName\":\"\",\"srcEnvName\":\"\"},\"deployDatabase\":{\"restorusername\":\"\",\"restorpassword\":\"\",\"dbusername\":\"\",\"dbpassword\":\"\",\"script\":\"\"},\"deploymentOption\":\"\",\"deployOperation\":\"\",\"proxy\":{\"username\":\"\",\"password\":\"\",\"host\":\"\",\"port\":\"\",\"enabled\":\"\"}}]},{\"envName\":\"QA\",\"envFlag\":\"on\",\"scriptType\":\"\",\"deploySteps\":[{\"stepName\":\"Deploy_Script\",\"deployOS\":\"\",\"runScript\":{\"scriptType\":\"batchScript\",\"scriptFilePath\":\"echo \\\"hi\\\"\",\"targets\":\"\",\"host\":\"\",\"userName\":\"\",\"password\":\"\",\"script\":\"\",\"pathToFiles\":\"\",\"destinationDir\":\"\",\"flattenFilePath\":\"off\"},\"deployToContainer\":{\"containerName\":\"\",\"userName\":\"\",\"password\":\"\",\"updateDB\":\"\",\"rollbackStrategy\":\"\",\"fileName\":\"\",\"dbDeployPipelineList\":[],\"pairName\":\"\",\"srcEnvName\":\"\"},\"deployDatabase\":{\"restorusername\":\"\",\"restorpassword\":\"\",\"dbusername\":\"\",\"dbpassword\":\"\",\"script\":\"\"},\"deploymentOption\":\"\",\"deployOperation\":\"\",\"proxy\":{\"username\":\"\",\"password\":\"\",\"host\":\"\",\"port\":\"\",\"enabled\":\"\"}}]},{\"envName\":\"PRE-PROD\",\"envFlag\":\"off\",\"scriptType\":\"\"},{\"envName\":\"PROD\",\"envFlag\":\"off\",\"scriptType\":\"\"}]},\"testInfo\":{\"testEnv\":[{\"envName\":\"DEV\",\"envFlag\":\"on\",\"testSteps\":[{\"stepName\":\"MTM\",\"runScript\":{\"scriptType\":\"\",\"reportType\":\"\"},\"test\":{\"testCategory\":\"functional\",\"testTypeName\":\"mtm\",\"projectName\":\"DevOps_Team\",\"frameWork\":\"\",\"testCase\":\"\",\"testPlan\":\"\",\"folderUrl\":\"\",\"userName\":\"\",\"password\":\"\",\"testSuiteName\":\"\",\"projectLocation\":\"\",\"serverUrl\":\"\",\"domain\":\"\",\"serviceName\":\"\",\"path\":\"sample\",\"authenticationCode\":\"\",\"timeout\":60,\"serverName\":\"\",\"browserName\":\"\",\"rootDir\":\"\",\"version\":\"\",\"externalFilePath\":\"\",\"parameters\":\"\",\"scriptPath\":\"\",\"targets\":\"\",\"arg\":\"\",\"buildDefId\":\"1\"}}]},{\"envName\":\"QA\",\"envFlag\":\"off\"},{\"envName\":\"PRE-PROD\",\"envFlag\":\"off\"},{\"envName\":\"PROD\",\"envFlag\":\"off\"}]}}";
		String appData = "{\"applicationName\":\"CustomerPortal\",\"pipelineAdmins\":\"dummyuser,dummyuser\",\"releaseManager\":\"dummyuser,dummyuser\",\"ibmRQMServerDetails\":{},\"slavesDetails\":[{\"deploy\":\"on\",\"labels\":\"Slave1\",\"slaveName\":\"Slave1\",\"buildServerOS\":\"windows\",\"build\":\"on\",\"workspacePath\":\"\",\"test\":\"on\",\"slaveUsage\":\"both\",\"createNewSlave\":\"\"},{\"deploy\":\"on\",\"labels\":\"Slave122\",\"slaveName\":\"Slave122\",\"buildServerOS\":\"windows\",\"build\":\"on\",\"workspacePath\":\"C:/CICD\",\"test\":\"on\",\"slaveUsage\":\"both\",\"createNewSlave\":\"on\"},{\"deploy\":\"on\",\"labels\":\"java-agent-1\",\"slaveName\":\"java-agent-1\",\"buildServerOS\":\"windows\",\"build\":\"on\",\"workspacePath\":\"C:/CICD\",\"test\":\"on\",\"slaveUsage\":\"both\",\"createNewSlave\":\"on\"},{\"deploy\":\"off\",\"labels\":\"test-agent-1\",\"slaveName\":\"test-agent-1\",\"buildServerOS\":\"windows\",\"build\":\"off\",\"workspacePath\":\"C:/CICD\",\"test\":\"on\",\"slaveUsage\":\"both\",\"createNewSlave\":\"on\"},{\"deploy\":\"on\",\"labels\":\"Android_Demo_slave\",\"slaveName\":\"Android_Demo_slave\",\"buildServerOS\":\"windows\",\"build\":\"on\",\"workspacePath\":\"D:/wrkspac_new\",\"test\":\"off\",\"slaveUsage\":\"both\",\"createNewSlave\":\"on\"}],\"virtualServiceServerDetails\":{},\"developers\":\"dummyuser\",\"sapApplication\":\"off\",\"environmentOwnerDetails\":[{\"qa\":\"dummyuser,dummyuser,dummyuser\",\"environmentName\":\"DEV\",\"environmentOwners\":\"dummyuser,dummyuser,dummyuser\",\"dBOwners\":\"\"},{\"qa\":\"dummyuser,dummyuser,dummyuser\",\"environmentName\":\"QA\",\"environmentOwners\":\"dummyuser,dummyuser\",\"dBOwners\":\"\"},{\"qa\":\"dummyuser,dummyuser,dummyuser\",\"environmentName\":\"PRE-PROD\",\"environmentOwners\":\"dummyuser,dummyuser\",\"dBOwners\":\"\"},{\"qa\":\"dummyuser,dummyuser,dummyuser\",\"environmentName\":\"PROD\",\"environmentOwners\":\"dummyuser,dummyuser\",\"dBOwners\":\"\"}],\"artifactToStage\":{\"artifactRepo\":{\"repoURL\":\"dummyuser@xyz.com:8081\",\"repoUsername\":\"admin\",\"repoPassword\":\"dummy\",\"repoName\":\"idp_Nexus\"},\"artifactRepoName\":\"nexus\"}}";

		List<DeployArtifact> arList = nexusDetails.getNexus(idpData, appData, "CustomerPortal", "DB_CICD");
		assertNotNull(arList);

	}

	@Test
	public void testGetApplication() {
		try {
			String app = nexusDetails.getApplication("TCM");
			assertNotNull(app);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testGetArtifact() {
		try {
			List<DeployArtifact> arList = nexusDetails.getArtifact("CustomerPortal", "Service_CICD", "R1.0.0", "DEV");
			assertNotNull(arList);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testGetPathCount() {
		try {
			int id = nexusDetails.getPathCount(2281);
			assertNotNull(id);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testGetPathEnvId() {
		try {
			List<Integer> pathId = nexusDetails.getParentEnvId(16, 2281);
			assertNotNull(pathId);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testGetArtifactList() {
		try {
			List<String> artifactList = nexusDetails.getArtifactList(2, 18, "approved");
			assertNotNull(artifactList);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

}
