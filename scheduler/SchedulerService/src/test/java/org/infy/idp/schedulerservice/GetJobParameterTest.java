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

import org.infy.idp.configure.AppContext;
import org.infy.idp.configure.ConfigurationManager;
import org.infy.idp.configure.PostGreSqlDbContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class GetJobParameterTest {

	@Spy
	@Autowired
	private ConfigurationManager configmanager;

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@InjectMocks
	private GetJobParameter getJobParameter;

	@Mock
	private ArtifactHistory artifactHistory;

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
	public void testGetJobParameter() {
		try {
			getJobParameter.getJobParameter(
					"{\"applicationName\":\"TCM\",\"pipelineName\":\"TCM_CICD\",\"releaseNumber\":\"R1.0.0\"}", "8");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPipelineId() {
		try {
			Long id = getJobParameter.getPipelineId("TCM", "TCM_CICD");
			assertNotNull(id);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testInsertTriggerhistory() {
		String jsonInput = "{\"applicationName\":\"CustomerPortal\",\"artifactorySelected\":\"on\",\"branchOrTag\":\"\",\"build\":{\"branchSelected\":\"\",\"module\":[]},\"buildartifactNumber\":\"\",\"emailed\":\"userName@domain.com,shivam.bhagat@domain.com,kruti.vyas@domain.com\",\"envSelected\":\"\",\"gitTag\":\"\",\"ibmRQMTestSuiteId\":\"\",\"jiraProjectKey\":\"\",\"jobBuildId\":\"\",\"jobParam\":[],\"mtmStepName\":\"\",\"nonRepoDeployStatus\":\"\",\"pipelineName\":\"DB_CICD\",\"releaseNumber\":\"R18.3\",\"repoDeployStatus\":\"\",\"slaveName\":\"java-agent-1\",\"subApplicationName\":\"\",\"technology\":\"J2EE/Maven\",\"testPlanId\":\"\",\"testSelected\":\"off\",\"testSlaveName\":\"\",\"testStep\":[],\"testSuitId\":\"\",\"tfsWorkItem\":\"\",\"userName\":\"userName\",\"userStoryString\":\"\"}";

		org.json.JSONObject json;
		try {
			json = new JSONObject(jsonInput);
			Integer id = getJobParameter.insertTriggerHistory(json, "1.0.0");
			assertNotNull(id);
		} catch (JSONException e1) {

			e1.printStackTrace();
		}

	}
}
