
package com.infosys.utilities.ntrxunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.infosys.utilities.ntrxunit.TestRun.ResultSummary;
import com.infosys.utilities.ntrxunit.TestRun.ResultSummary.Counters;
import com.infosys.utilities.ntrxunit.TestRun.ResultSummary.ResultFiles;
import com.infosys.utilities.ntrxunit.TestRun.ResultSummary.ResultFiles.ResultFile;
import com.infosys.utilities.ntrxunit.TestRun.ResultSummary.RunInfos;
import com.infosys.utilities.ntrxunit.TestRun.ResultSummary.RunInfos.RunInfo;
import com.infosys.utilities.ntrxunit.TestRun.Results;
import com.infosys.utilities.ntrxunit.TestRun.TestDefinitions;
import com.infosys.utilities.ntrxunit.TestRun.TestEntries;
import com.infosys.utilities.ntrxunit.TestRun.TestLists;
import com.infosys.utilities.ntrxunit.TestRun.Times;
import com.infosys.utilities.ntrxunit.TestSettings.Deployment;
import com.infosys.utilities.ntrxunit.TestSettings.Execution;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.AgentRule;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.AgentRule.DataCollectors;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.AgentRule.DataCollectors.DataCollector;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage.Regular;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.AgentRule.DataCollectors.DataCollector.Configuration.CodeCoverage.Regular.CodeCoverageItem;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.TestTypeSpecific;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig.AssemblyResolution;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.TestTypeSpecific.UnitTestRunConfig.AssemblyResolution.TestDirectory;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration.Browser;
import com.infosys.utilities.ntrxunit.TestSettings.Execution.TestTypeSpecific.WebTestRunConfiguration.Browser.Headers;

public class TestRunTest {

	@Test
	public void testEquals()
	{
		TestRun testRun=new TestRun();
		TestSettings testSettings=new TestSettings();
		Deployment deployment=new Deployment();
		Execution execution=new Execution();
		TestTypeSpecific testTypeSpecific=new TestTypeSpecific();
		UnitTestRunConfig unitTestRunConfig=new UnitTestRunConfig();
		AssemblyResolution assemblyResolution=new AssemblyResolution();
		TestDirectory testDirectory=new TestDirectory();
		WebTestRunConfiguration webTestRunConfiguration=new WebTestRunConfiguration();
		Browser browser=new Browser();
		Headers headers=new Headers();
		AgentRule agentRule=new AgentRule();
		DataCollectors dataCollectors=new DataCollectors();
		DataCollector dataCollector=new DataCollector();
		Configuration configuration=new Configuration();
		CodeCoverage codeCoverage=new CodeCoverage();
		Regular regular=new Regular();
		CodeCoverageItem codeCoverageItem=new CodeCoverageItem();
		Times times=new Times();
		ResultSummary resultSummary=new ResultSummary();
		Counters counters=new Counters();
		RunInfos runInfos=new RunInfos();
		RunInfo runInfo=new RunInfo();
		ResultFiles resultFiles=new ResultFiles();
		ResultFile resultFile=new ResultFile();
		TestDefinitions testDefinitions=new TestDefinitions();
		TestLists testLists=new TestLists();
		TestEntries testEntries=new TestEntries();
		Results results=new Results();
		
		testDirectory.setUseLoadContext("value");
		
		assemblyResolution.setTestDirectory(testDirectory);
		
		unitTestRunConfig.setAssemblyResolution(assemblyResolution);
		unitTestRunConfig.setTestTypeId("value");
		
		
		
		browser.setHeaders(headers);
		browser.setMaxConnections(1);
		browser.setName("value");
		
		webTestRunConfiguration.setBrowser(browser);
		webTestRunConfiguration.setTestTypeId("value");
		
		testTypeSpecific.setUnitTestRunConfig(unitTestRunConfig);
		testTypeSpecific.setWebTestRunConfiguration(webTestRunConfiguration);
		
		deployment.setRunDeploymentRoot("value");
		deployment.setUseDefaultDeploymentRoot("value");
		deployment.setUserDeploymentRoot("value");
		
		codeCoverageItem.setBinaryFile("value");
		codeCoverageItem.setInstrumentInPlace("value");
		codeCoverageItem.setPdbFile("value");
		
		regular.setCodeCoverageItem(codeCoverageItem);
		
		codeCoverage.setRegular(regular);
		
		configuration.setCodeCoverage(codeCoverage);
		
		dataCollector.setAssemblyQualifiedName("value");
		dataCollector.setConfiguration(configuration);
		dataCollector.setFriendlyName("value");
		dataCollector.setUri("value");
		
		dataCollectors.setDataCollector(dataCollector);
		
		agentRule.setDataCollectors(dataCollectors);
		agentRule.setName("value");
		
		execution.setAgentRule(agentRule);
		execution.setTestTypeSpecific(testTypeSpecific);
		
		testSettings.setDeployment(deployment);
		testSettings.setDescription("value");
		testSettings.setExecution(execution);
		testSettings.setId("value");
		testSettings.setName("value");
		testSettings.setProperties("value");
		
		times.setCreation("value");
		times.setFinish("value");
		times.setQueuing("value");
		times.setStart("value");
		
		counters.setAborted(1);
		counters.setCompleted(1);
		counters.setDisconnected(1);
		counters.setError(1);
		counters.setExecuted(1);
		counters.setFailed(1);
		counters.setInconclusive(1);
		counters.setInProgress(1);
		counters.setNotExecuted(1);
		counters.setNotRunnable(1);
		counters.setPassed(1);
		counters.setPassedButRunAborted(1);
		counters.setPending(1);
		counters.setTimeout(1);
		counters.setTotal(1);
		counters.setWarning(1);
		
		runInfo.setComputerName("value");
		runInfo.setOutcome("value");
		runInfo.setText("value");
		runInfo.setTimestamp("value");
		
		runInfos.setRunInfo(runInfo);
		
		resultFile.setPath("value");
		
		resultFiles.setResultFile(resultFile);
		
		resultSummary.setCounters(counters);
		resultSummary.setOutcome("value");
		resultSummary.setResultFiles(resultFiles);
		resultSummary.setRunInfos(runInfos);
		
		
		
		testRun.setId("value");
		testRun.setName("value");
		testRun.setResults(results);
		testRun.setResultSummary(resultSummary);
		testRun.setRunUser("value");
		testRun.setTestDefinitions(testDefinitions);
		testRun.setTestEntries(testEntries);
		testRun.setTestLists(testLists);
		testRun.setTestSettings(testSettings);
		testRun.setTimes(times);
		
		assertEquals("value",testRun.getId());
		assertEquals("value",testRun.getName());
		assertEquals("value",testRun.getRunUser());
		assertEquals(0,testRun.getResults().getUnitTestResult().size());
		assertEquals("value",testRun.getResultSummary().getOutcome());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getAborted());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getCompleted());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getDisconnected());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getError());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getExecuted());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getFailed());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getInconclusive());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getInProgress());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getNotExecuted());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getNotRunnable());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getPassed());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getPassedButRunAborted());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getPending());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getTimeout());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getTotal());
		assertEquals((Integer)1,testRun.getResultSummary().getCounters().getWarning());
		assertEquals("value",testRun.getResultSummary().getResultFiles().getResultFile().getPath());
		assertEquals("value",testRun.getResultSummary().getRunInfos().getRunInfo().getComputerName());
		assertEquals("value",testRun.getResultSummary().getRunInfos().getRunInfo().getOutcome());
		assertEquals("value",testRun.getResultSummary().getRunInfos().getRunInfo().getText());
		assertEquals("value",testRun.getResultSummary().getRunInfos().getRunInfo().getTimestamp());
		assertEquals(0,testRun.getTestDefinitions().getUnitTest().size());
		assertEquals(0,testRun.getTestEntries().getTestEntry().size());
		assertEquals(0,testRun.getTestLists().getTestList().size());
		assertEquals("value",testRun.getTestSettings().getId());
		assertEquals("value",testRun.getTestSettings().getName());
		assertEquals("value",testRun.getTestSettings().getProperties());
		assertEquals("value",testRun.getTestSettings().getDescription());
		assertEquals("value",testRun.getTestSettings().getDeployment().getRunDeploymentRoot());
		assertEquals("value",testRun.getTestSettings().getDeployment().getUseDefaultDeploymentRoot());
		assertEquals("value",testRun.getTestSettings().getDeployment().getUserDeploymentRoot());
		assertEquals("value",testRun.getTestSettings().getExecution().getAgentRule().getName());
		assertEquals("value",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getAssemblyQualifiedName());
		assertEquals("value",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getFriendlyName());
		assertEquals("value",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getUri());
		assertEquals("value",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getConfiguration().getCodeCoverage().getRegular().getCodeCoverageItem().getBinaryFile());
		assertEquals("value",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getConfiguration().getCodeCoverage().getRegular().getCodeCoverageItem().getInstrumentInPlace());
		assertEquals("value",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getConfiguration().getCodeCoverage().getRegular().getCodeCoverageItem().getPdbFile());
		assertEquals("value",testRun.getTestSettings().getExecution().getTestTypeSpecific().getUnitTestRunConfig().getTestTypeId());
		assertEquals("value",testRun.getTestSettings().getExecution().getTestTypeSpecific().getUnitTestRunConfig().getAssemblyResolution().getTestDirectory().getUseLoadContext());
		assertEquals("value",testRun.getTestSettings().getExecution().getTestTypeSpecific().getWebTestRunConfiguration().getTestTypeId());
		assertEquals((Integer)1,testRun.getTestSettings().getExecution().getTestTypeSpecific().getWebTestRunConfiguration().getBrowser().getMaxConnections());
		assertEquals("value",testRun.getTestSettings().getExecution().getTestTypeSpecific().getWebTestRunConfiguration().getBrowser().getName());
		assertEquals(0,testRun.getTestSettings().getExecution().getTestTypeSpecific().getWebTestRunConfiguration().getBrowser().getHeaders().getHeader().size());
		assertEquals("value",testRun.getTimes().getCreation());
		assertEquals("value",testRun.getTimes().getFinish() );
		assertEquals("value",testRun.getTimes().getQueuing());
		assertEquals("value",testRun.getTimes().getStart());
		
		
	}
	
	@Test
	public void testNotEquals()
	{
		TestRun testRun=new TestRun();
		TestSettings testSettings=new TestSettings();
		Deployment deployment=new Deployment();
		Execution execution=new Execution();
		TestTypeSpecific testTypeSpecific=new TestTypeSpecific();
		UnitTestRunConfig unitTestRunConfig=new UnitTestRunConfig();
		AssemblyResolution assemblyResolution=new AssemblyResolution();
		TestDirectory testDirectory=new TestDirectory();
		WebTestRunConfiguration webTestRunConfiguration=new WebTestRunConfiguration();
		Browser browser=new Browser();
		Headers headers=new Headers();
		AgentRule agentRule=new AgentRule();
		DataCollectors dataCollectors=new DataCollectors();
		DataCollector dataCollector=new DataCollector();
		Configuration configuration=new Configuration();
		CodeCoverage codeCoverage=new CodeCoverage();
		Regular regular=new Regular();
		CodeCoverageItem codeCoverageItem=new CodeCoverageItem();
		Times times=new Times();
		ResultSummary resultSummary=new ResultSummary();
		Counters counters=new Counters();
		RunInfos runInfos=new RunInfos();
		RunInfo runInfo=new RunInfo();
		ResultFiles resultFiles=new ResultFiles();
		ResultFile resultFile=new ResultFile();
		TestDefinitions testDefinitions=new TestDefinitions();
		TestLists testLists=new TestLists();
		TestEntries testEntries=new TestEntries();
		Results results=new Results();
		
		testDirectory.setUseLoadContext("value");
		
		assemblyResolution.setTestDirectory(testDirectory);
		
		unitTestRunConfig.setAssemblyResolution(assemblyResolution);
		unitTestRunConfig.setTestTypeId("value");
		
		
		
		browser.setHeaders(headers);
		browser.setMaxConnections(1);
		browser.setName("value");
		
		webTestRunConfiguration.setBrowser(browser);
		webTestRunConfiguration.setTestTypeId("value");
		
		testTypeSpecific.setUnitTestRunConfig(unitTestRunConfig);
		testTypeSpecific.setWebTestRunConfiguration(webTestRunConfiguration);
		
		deployment.setRunDeploymentRoot("value");
		deployment.setUseDefaultDeploymentRoot("value");
		deployment.setUserDeploymentRoot("value");
		
		codeCoverageItem.setBinaryFile("value");
		codeCoverageItem.setInstrumentInPlace("value");
		codeCoverageItem.setPdbFile("value");
		
		regular.setCodeCoverageItem(codeCoverageItem);
		
		codeCoverage.setRegular(regular);
		
		configuration.setCodeCoverage(codeCoverage);
		
		dataCollector.setAssemblyQualifiedName("value");
		dataCollector.setConfiguration(configuration);
		dataCollector.setFriendlyName("value");
		dataCollector.setUri("value");
		
		dataCollectors.setDataCollector(dataCollector);
		
		agentRule.setDataCollectors(dataCollectors);
		agentRule.setName("value");
		
		execution.setAgentRule(agentRule);
		execution.setTestTypeSpecific(testTypeSpecific);
		
		testSettings.setDeployment(deployment);
		testSettings.setDescription("value");
		testSettings.setExecution(execution);
		testSettings.setId("value");
		testSettings.setName("value");
		testSettings.setProperties("value");
		
		times.setCreation("value");
		times.setFinish("value");
		times.setQueuing("value");
		times.setStart("value");
		
		counters.setAborted(1);
		counters.setCompleted(1);
		counters.setDisconnected(1);
		counters.setError(1);
		counters.setExecuted(1);
		counters.setFailed(1);
		counters.setInconclusive(1);
		counters.setInProgress(1);
		counters.setNotExecuted(1);
		counters.setNotRunnable(1);
		counters.setPassed(1);
		counters.setPassedButRunAborted(1);
		counters.setPending(1);
		counters.setTimeout(1);
		counters.setTotal(1);
		counters.setWarning(1);
		
		runInfo.setComputerName("value");
		runInfo.setOutcome("value");
		runInfo.setText("value");
		runInfo.setTimestamp("value");
		
		runInfos.setRunInfo(runInfo);
		
		resultFile.setPath("value");
		
		resultFiles.setResultFile(resultFile);
		
		resultSummary.setCounters(counters);
		resultSummary.setOutcome("value");
		resultSummary.setResultFiles(resultFiles);
		resultSummary.setRunInfos(runInfos);
		
		
		
		testRun.setId("value");
		testRun.setName("value");
		testRun.setResults(results);
		testRun.setResultSummary(resultSummary);
		testRun.setRunUser("value");
		testRun.setTestDefinitions(testDefinitions);
		testRun.setTestEntries(testEntries);
		testRun.setTestLists(testLists);
		testRun.setTestSettings(testSettings);
		testRun.setTimes(times);
		
		assertNotEquals("value2",testRun.getId());
		assertNotEquals("value2",testRun.getName());
		assertNotEquals("value2",testRun.getRunUser());
		assertNotEquals(2,testRun.getResults().getUnitTestResult().size());
		assertNotEquals("value2",testRun.getResultSummary().getOutcome());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getAborted());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getCompleted());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getDisconnected());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getError());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getExecuted());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getFailed());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getInconclusive());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getInProgress());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getNotExecuted());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getNotRunnable());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getPassed());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getPassedButRunAborted());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getPending());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getTimeout());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getTotal());
		assertNotEquals((Integer)2,testRun.getResultSummary().getCounters().getWarning());
		assertNotEquals("value2",testRun.getResultSummary().getResultFiles().getResultFile().getPath());
		assertNotEquals("value2",testRun.getResultSummary().getRunInfos().getRunInfo().getComputerName());
		assertNotEquals("value2",testRun.getResultSummary().getRunInfos().getRunInfo().getOutcome());
		assertNotEquals("value2",testRun.getResultSummary().getRunInfos().getRunInfo().getText());
		assertNotEquals("value2",testRun.getResultSummary().getRunInfos().getRunInfo().getTimestamp());
		assertNotEquals(2,testRun.getTestDefinitions().getUnitTest().size());
		assertNotEquals(2,testRun.getTestEntries().getTestEntry().size());
		assertNotEquals(2,testRun.getTestLists().getTestList().size());
		assertNotEquals("value2",testRun.getTestSettings().getId());
		assertNotEquals("value2",testRun.getTestSettings().getName());
		assertNotEquals("value2",testRun.getTestSettings().getProperties());
		assertNotEquals("value2",testRun.getTestSettings().getDescription());
		assertNotEquals("value2",testRun.getTestSettings().getDeployment().getRunDeploymentRoot());
		assertNotEquals("value2",testRun.getTestSettings().getDeployment().getUseDefaultDeploymentRoot());
		assertNotEquals("value2",testRun.getTestSettings().getDeployment().getUserDeploymentRoot());
		assertNotEquals("value2",testRun.getTestSettings().getExecution().getAgentRule().getName());
		assertNotEquals("value2",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getAssemblyQualifiedName());
		assertNotEquals("value2",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getFriendlyName());
		assertNotEquals("value2",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getUri());
		assertNotEquals("value2",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getConfiguration().getCodeCoverage().getRegular().getCodeCoverageItem().getBinaryFile());
		assertNotEquals("value2",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getConfiguration().getCodeCoverage().getRegular().getCodeCoverageItem().getInstrumentInPlace());
		assertNotEquals("value2",testRun.getTestSettings().getExecution().getAgentRule().getDataCollectors().getDataCollector().getConfiguration().getCodeCoverage().getRegular().getCodeCoverageItem().getPdbFile());
		assertNotEquals("value2",testRun.getTestSettings().getExecution().getTestTypeSpecific().getUnitTestRunConfig().getTestTypeId());
		assertNotEquals("value2",testRun.getTestSettings().getExecution().getTestTypeSpecific().getUnitTestRunConfig().getAssemblyResolution().getTestDirectory().getUseLoadContext());
		assertNotEquals("value2",testRun.getTestSettings().getExecution().getTestTypeSpecific().getWebTestRunConfiguration().getTestTypeId());
		assertNotEquals((Integer)2,testRun.getTestSettings().getExecution().getTestTypeSpecific().getWebTestRunConfiguration().getBrowser().getMaxConnections());
		assertNotEquals("value2",testRun.getTestSettings().getExecution().getTestTypeSpecific().getWebTestRunConfiguration().getBrowser().getName());
		assertNotEquals(2,testRun.getTestSettings().getExecution().getTestTypeSpecific().getWebTestRunConfiguration().getBrowser().getHeaders().getHeader().size());
		assertNotEquals("value2",testRun.getTimes().getCreation());
		assertNotEquals("value2",testRun.getTimes().getFinish() );
		assertNotEquals("value2",testRun.getTimes().getQueuing());
		assertNotEquals("value2",testRun.getTimes().getStart());
		
		
	}
}
