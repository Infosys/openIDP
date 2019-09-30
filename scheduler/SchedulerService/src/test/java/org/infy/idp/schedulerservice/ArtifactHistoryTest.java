/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.schedulerservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.infy.idp.MixedModeFlywayPreparer;
import org.infy.idp.configure.ConfigurationManager;
import org.infy.idp.configure.PostGreSqlDbContext;

import org.json.JSONException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.jdbc.PgConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtifactHistoryTest {

	@Autowired
	private ConfigurationManager configmanager;

	@MockBean
	private PostGreSqlDbContext postGreSqlDbContext;

	@Autowired
	private ArtifactHistory artifactHistory;

	@ClassRule
	public static PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(MixedModeFlywayPreparer.forClasspathLocation("db" +
			"/migration"));

	private static DataSource ds;

	@BeforeClass
	public static void mockPostGresSql() throws SQLException {
		ds = db.getTestDatabase();
	}

	@Before
	public void setup() throws SQLException {
		when(postGreSqlDbContext.getConnection()).thenReturn(ds.getConnection());
	}

	@Test
	public void testGetJobJSON() {
		String json = artifactHistory.getJobJSON("DemoAppT_IDP_DotNet1", "builds");
		assertNotNull(json);
	}

	@Test
	public void testGetJobJSONJob() {
		String json = artifactHistory.getJobJSON("DemoAppT_IDP_DotNet1", "job");
		assertNotNull(json);
	}

	@Test
	public void testGetJobJSONBuilPipeline() {
		String json = artifactHistory.getJobJSON("DemoAppT_IDP_DotNet1", "builds_Pipeline");
		assertNotNull(json);
	}

	@Test
	public void testGetJobJSONLastBuild() {
		String json = artifactHistory.getJobJSON("DemoAppT_IDP_DotNet1", "lastBuild");
		assertNotNull(json);
	}

	@Test
	public void testGetJobJSONNextBuild() {
		String json = artifactHistory.getJobJSON("DemoAppT_IDP_DotNet1", "nextBuild");
		assertNotNull(json);
	}

	@Test
	public void testGetJobJSONNextBuildPipeline() {
		String json = artifactHistory.getJobJSON("DemoAppT_IDP_DotNet1", "nextBuild_Pipeline");
		assertNotNull(json);
	}

	@Test
	public void testGetJobJSONBuildable() {
		String json = artifactHistory.getJobJSON("DemoAppT_IDP_DotNet1", "buildable");
		assertNotNull(json);
	}

	@Test
	public void testGetJobJSONApprovalCheck() {
		String json = artifactHistory.getJobJSON("DemoAppT_IDP_DotNet1", "ApprovalCheck;0;1");
		assertNotNull(json);
	}

	@Test
	public void testGetJobJSONApprNext() {
		String json = artifactHistory.getJobJSON("DemoAppT_IDP_DotNet1", "apprNext;2");
		assertNotNull(json);
	}

	@Test
	public void testGetJobJSONGet() {
		String json = artifactHistory.getJobJSON("DemoAppT_IDP_DotNet1", "getJson;3;4");
		assertNotNull(json);
	}

	@Test
	public void testGetReleaseId() {

		try {
			int releaseId = artifactHistory.getReleaseId("CustomerPortal", "DB_CICD", "R18.3", "SUCCESS");
			assertNotNull(releaseId);
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	@Test
	public void testGetApplicationId() {
		Long appId = artifactHistory.getApplicationId("CustomerPortal");
		assertNotNull(appId);
	}

	@Test
	public void testGetEnvironmentId() {
		try {
			int envId = artifactHistory.getEnvironmentId("DEV", 7);
			assertNotNull(envId);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testInsertArtifacttoData() {

		try {
			artifactHistory.insertArtifacttoData(1, 2, "Artifact-default", "SUCCESS");
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testGetArtifactId() {
		try {
			int artifactId = artifactHistory.getArtifactId("TCM_TCM_CICD_1");
			assertNotNull(artifactId);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testInsertArtifactDetails() {
		try {
			artifactHistory.insertArtifactDetails(120, "SUCCESS", "seuuceesfully deployed", "DEV", "dummyuser");
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}


	@Test
	public void updateTriggerHistory() throws SQLException {
		artifactHistory.updateTriggerHistory(1, "artifact-101");
		ResultSet r = ((PgConnection) ds.getConnection()).execSQLQuery("select artifact_name from ttrigger_history " +
				"where trigger_id" +
				" = 1");
		assertTrue(r.next());
		assertEquals("artifact-101", r.getString(1));
	}

	@Test(expected = JSONException.class )
	public void testInsertArtifact() throws JSONException {
		String jsonInput = "{\"basicInfo\":{\"applicationName\":\"CustomerPortal\",\"pipelineName\":\"DB_CICD\",\"buildServerOS\":\"windows\",\"masterSequence\":\"pipeline\",\"engine\":\"Jenkins Workflow\",\"buildInterval\":{\"pollSCM\":\"off\",\"buildInterval\":\"\",\"buildIntervalValue\":\"0\",\"buildAtSpecificInterval\":\"off\",\"event\":[{\"type\":\"--Select--\",\"hour\":\"00\",\"minute\":\"00\"}]},\"additionalMailRecipients\":{\"applicationTeam\":\"\",\"emailIds\":\"\"},\"pipelineStatus\":\"edit\",\"customTriggerInterval\":{\"interval\":[{\"time\":\"15\",\"type\":\"Day\",\"minute\":\"00\",\"details\":{\"applicationName\":\"CustomerPortal\",\"pipelineName\":\"DB_CICD\",\"jobBuildId\":\"\",\"releaseNumber\":\"R18.3\",\"buildartifactNumber\":\"\",\"gitTag\":\"\",\"jiraProjectKey\":\"\",\"userStoryString\":\"\",\"userName\":\"dummyuser\",\"slaveName\":\"java-agent-1\",\"testSlaveName\":\"\",\"envSelected\":\"\",\"build\":{\"branchSelected\":\"\",\"module\":[]},\"testSelected\":\"off\",\"artifactorySelected\":\"on\",\"emailed\":\"dummyuser@xyz.com,dummyuser@xyz.com,dummyuser@xyz.com\",\"testStep\":[],\"technology\":\"J2EE/Maven\",\"repoDeployStatus\":\"\",\"nonRepoDeployStatus\":\"\",\"branchOrTag\":\"\",\"subApplicationName\":\"\",\"testPlanId\":\"\",\"testSuitId\":\"\",\"mtmStepName\":\"\",\"tfsWorkItem\":\"\",\"ibmRQMTestSuiteId\":\"\",\"jobParam\":[]}},{\"week\":[\"Mon\"],\"time\":\"10\",\"type\":\"Week\",\"minute\":\"10\",\"details\":{\"applicationName\":\"CustomerPortal\",\"pipelineName\":\"DB_CICD\",\"jobBuildId\":\"\",\"releaseNumber\":\"R18.3\",\"buildartifactNumber\":\"\",\"gitTag\":\"\",\"jiraProjectKey\":\"\",\"userStoryString\":\"\",\"userName\":\"dummyuser\",\"slaveName\":\"java-agent-1\",\"testSlaveName\":\"\",\"envSelected\":\"DEV\",\"build\":{\"branchSelected\":\"\",\"module\":[]},\"deploy\":{\"version\":\"\",\"artifactID\":\"\",\"nexusId\":\"\",\"deployArtifact\":{\"version\":\"\",\"artifactID\":\"DB_CICD\",\"artifactName\":\"CustomerPortal_DB_CICD_latestArtifact\",\"timestamp\":\"\",\"groupId\":\"CustomerPortal\",\"userInfo\":\"\",\"downloadURL\":\"\",\"nexusURL\":\"xyz.com:8081\",\"repoName\":\"idp_Nexus\"},\"dbDeployRollbackType\":\"\",\"deployStep\":[\"tomcat\"],\"subModule\":[],\"deployModule\":[]},\"testSelected\":\"off\",\"artifactorySelected\":\"on\",\"emailed\":\"dummyuser@xyz.com,dummyuser@xyz.com,dummyuser@xyz.com\",\"testStep\":[],\"technology\":\"J2EE/Maven\",\"rmAssemblies\":\"\",\"repoDeployStatus\":\"\",\"nonRepoDeployStatus\":\"\",\"branchOrTag\":\"\",\"subApplicationName\":\"\",\"testPlanId\":\"\",\"testSuitId\":\"\",\"mtmStepName\":\"\",\"tfsWorkItem\":\"\",\"ibmRQMTestSuiteId\":\"\",\"jobParam\":[]}},{\"date\":[\"1\"],\"time\":\"15\",\"type\":\"Month\",\"minute\":\"00\",\"details\":{\"applicationName\":\"CustomerPortal\",\"pipelineName\":\"DB_CICD\",\"jobBuildId\":\"\",\"releaseNumber\":\"R18.4\",\"buildartifactNumber\":\"\",\"gitTag\":\"\",\"jiraProjectKey\":\"\",\"userStoryString\":\"\",\"userName\":\"dummyuser\",\"slaveName\":\"java-agent-1\",\"testSlaveName\":\"\",\"envSelected\":\"\",\"build\":{\"branchSelected\":\"\",\"module\":[]},\"testSelected\":\"off\",\"artifactorySelected\":\"on\",\"emailed\":\"dummyuser@xyz.com,dummyuser@xyz.com,dummyuser@xyz.com\",\"testStep\":[],\"technology\":\"J2EE/Maven\",\"repoDeployStatus\":\"\",\"nonRepoDeployStatus\":\"\",\"branchOrTag\":\"\",\"subApplicationName\":\"\",\"testPlanId\":\"\",\"testSuitId\":\"\",\"mtmStepName\":\"\",\"tfsWorkItem\":\"\",\"ibmRQMTestSuiteId\":\"\",\"jobParam\":[]}}]},\"customPipelineAdmins\":[]},\"code\":{\"category\":\"Standard\",\"technology\":\"J2EE/Maven\",\"scm\":[{\"type\":\"git\",\"url\":\"https://dummyuser@bitbucket.org/dummyuser/tcm_maven.git\",\"userName\":\"jatin.bhatia03infosys.com\",\"password\":\"Infy8650@angular\",\"repositoryBrowser\":\"bitBucket\",\"browserUrl\":\"https://bitbucket.org\",\"projectName\":\"\",\"branch\":\"master\",\"projPath\":\"\",\"moduleName\":\"\",\"clearcaseType\":\"\",\"vobName\":\"\",\"snapshotViewName\":\"\",\"configSpec\":\"\",\"developmentStreamName\":\"\",\"buildConfiguration\":\"\",\"buildDefinition\":\"\",\"repositoryWorkspace\":\"\",\"projArea\":\"\",\"port\":\"\",\"version\":\"\",\"exclude\":\"\",\"proxy\":\"10.74.91.103\",\"proxyPort\":\"80\",\"appRepo\":\"on\",\"deployRepo\":\"on\",\"testRepo\":\"on\"}],\"jobParam\":[],\"buildScript\":[{\"tool\":\"\"},{\"tool\":\"\"},{}],\"subApplication\":\"\"},\"buildInfo\":{\"buildtool\":\"maven\",\"artifactToStage\":{\"artifact\":\"**/*.war\",\"artifactRepo\":{\"repoURL\":\"xyz.com:8081\",\"repoName\":\"idp_Nexus\",\"repoUsername\":\"admin\",\"repoPassword\":\"dummy\"},\"artifactRepoName\":\"nexus\"},\"castAnalysis\":{},\"modules\":[{\"moduleName\":\"TCMTest\",\"relativePath\":\"tcm_maven/TCMTest/pom.xml\",\"codeAnalysis\":[\"sonar\",\"off\",\"off\",\"off\"],\"unitTesting\":\"off\",\"codeCoverage\":\"off\",\"args\":\"\",\"compile\":\"on\",\"clean\":\"on\",\"install\":\"on\",\"MVNOPTS\":\"\",\"interval\":\"\",\"sonarUrl\":\"http://dummyuser:8080/\",\"sonarUserName\":\"\",\"sonarPassword\":\"\"}],\"postBuildScript\":{\"dependentPipelineList\":[]},\"subModule\":[],\"sonarUrl\":\"http://dummyuser:8080/\",\"sonarUserName\":\"\",\"sonarPassword\":\"\"},\"deployInfo\":{\"deployEnv\":[{\"envName\":\"DEV\",\"envFlag\":\"on\",\"scriptType\":\"\",\"deploySteps\":[{\"stepName\":\"tomcat\",\"deployOS\":\"\",\"runScript\":{\"scriptType\":\"\",\"scriptFilePath\":\"\",\"targets\":\"\"},\"deployToContainer\":{\"containerName\":\"tomcat\",\"serverManagerURL\":\"http://dummyHost122:7979\",\"resourceToBeDeployed\":\"manualTomcat\",\"warPath\":\"**/*.war\",\"contextPath\":\"/TCMTest\",\"userName\":\"tomcatadmin\",\"password\":\"tomcatadmin\",\"targetCellName\":\"\",\"targetNodeName\":\"\",\"targetServerName\":\"\",\"hostName\":\"\",\"port\":\"\",\"updateDB\":\"\",\"rollbackStrategy\":\"\",\"narOS\":\"\",\"deployedFolder\":\"\",\"fileName\":\"\",\"dbDeployPipelineList\":[],\"pairName\":\"\",\"srcEnvName\":\"\"},\"deployDatabase\":{\"restorusername\":\"\",\"restorpassword\":\"\",\"dbusername\":\"\",\"dbpassword\":\"\",\"script\":\"\"},\"deploymentOption\":\"\",\"deployOperation\":\"\",\"proxy\":{\"username\":\"\",\"password\":\"\",\"host\":\"\",\"port\":\"\",\"enabled\":\"\"}}]},{\"envName\":\"QA\",\"envFlag\":\"on\",\"scriptType\":\"\",\"deploySteps\":[{\"stepName\":\"Deploy_Script\",\"deployOS\":\"\",\"runScript\":{\"scriptType\":\"batchScript\",\"scriptFilePath\":\"echo \\\"hi\\\"\",\"targets\":\"\",\"host\":\"\",\"userName\":\"\",\"password\":\"\",\"script\":\"\",\"pathToFiles\":\"\",\"destinationDir\":\"\",\"flattenFilePath\":\"off\"},\"deployToContainer\":{\"containerName\":\"\",\"userName\":\"\",\"password\":\"\",\"updateDB\":\"\",\"rollbackStrategy\":\"\",\"fileName\":\"\",\"dbDeployPipelineList\":[],\"pairName\":\"\",\"srcEnvName\":\"\"},\"deployDatabase\":{\"restorusername\":\"\",\"restorpassword\":\"\",\"dbusername\":\"\",\"dbpassword\":\"\",\"script\":\"\"},\"deploymentOption\":\"\",\"deployOperation\":\"\",\"proxy\":{\"username\":\"\",\"password\":\"\",\"host\":\"\",\"port\":\"\",\"enabled\":\"\"}}]},{\"envName\":\"PRE-PROD\",\"envFlag\":\"off\",\"scriptType\":\"\"},{\"envName\":\"PROD\",\"envFlag\":\"off\",\"scriptType\":\"\"}]},\"testInfo\":{\"testEnv\":[{\"envName\":\"DEV\",\"envFlag\":\"on\",\"testSteps\":[{\"stepName\":\"MTM\",\"runScript\":{\"scriptType\":\"\",\"reportType\":\"\"},\"test\":{\"testCategory\":\"functional\",\"testTypeName\":\"mtm\",\"projectName\":\"DevOps_Team\",\"frameWork\":\"\",\"testCase\":\"\",\"testPlan\":\"\",\"folderUrl\":\"\",\"userName\":\"\",\"password\":\"\",\"testSuiteName\":\"\",\"projectLocation\":\"\",\"serverUrl\":\"\",\"domain\":\"\",\"serviceName\":\"\",\"path\":\"sample\",\"authenticationCode\":\"\",\"timeout\":60,\"serverName\":\"\",\"browserName\":\"\",\"rootDir\":\"\",\"version\":\"\",\"externalFilePath\":\"\",\"parameters\":\"\",\"scriptPath\":\"\",\"targets\":\"\",\"arg\":\"\",\"buildDefId\":\"1\"}}]},{\"envName\":\"QA\",\"envFlag\":\"off\"},{\"envName\":\"PRE-PROD\",\"envFlag\":\"off\"},{\"envName\":\"PROD\",\"envFlag\":\"off\"}]}}";
		String buildInput = "{\"nextBuildNumber\":\"5\"}";
		GsonBuilder gb = new GsonBuilder();

		Gson g = gb.create();
		org.json.JSONObject json = g.fromJson(jsonInput, org.json.JSONObject.class);

		Gson g1 = gb.create();
		net.sf.json.JSONObject build = g1.fromJson(buildInput, net.sf.json.JSONObject.class);
			artifactHistory.insertArtifact(json, build, "dummyuser");

	}
}
