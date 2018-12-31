
package com.infosys.utilities.parasoftsoatest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

import com.infosys.utilities.parasoftsoatest.ExecutedTestsDetails.Total;
import com.infosys.utilities.parasoftsoatest.ExecutedTestsDetails.Total.Project;
import com.infosys.utilities.parasoftsoatest.ExecutedTestsDetails.Total.Project.TestSuite1;
import com.infosys.utilities.parasoftsoatest.ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2;
import com.infosys.utilities.parasoftsoatest.ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3;
import com.infosys.utilities.parasoftsoatest.ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3.TestSuite4;
import com.infosys.utilities.parasoftsoatest.ExecutedTestsDetails.Total.Project.TestSuite1.TestSuite2.TestSuite3.TestSuite4.TestSuite5;
import com.infosys.utilities.parasoftsoatest.ResultsSession.AssocUrls;
import com.infosys.utilities.parasoftsoatest.ResultsSession.Authors;
import com.infosys.utilities.parasoftsoatest.ResultsSession.Authors.Author;
import com.infosys.utilities.parasoftsoatest.ResultsSession.Exec;
import com.infosys.utilities.parasoftsoatest.ResultsSession.Exec.AssocUrls.Tag;
import com.infosys.utilities.parasoftsoatest.ResultsSession.FunctionalTests;
import com.infosys.utilities.parasoftsoatest.ResultsSession.FunctionalTests.Categories;
import com.infosys.utilities.parasoftsoatest.ResultsSession.FunctionalTests.Categories.Category;
import com.infosys.utilities.parasoftsoatest.ResultsSession.FunctionalTests.Categories.Category.SubCategory;
import com.infosys.utilities.parasoftsoatest.ResultsSession.FunctionalTests.FuncViols;
import com.infosys.utilities.parasoftsoatest.ResultsSession.FunctionalTests.FuncViols.FuncViol;
import com.infosys.utilities.parasoftsoatest.ResultsSession.FunctionalTests.Goals;
import com.infosys.utilities.parasoftsoatest.ResultsSession.FunctionalTests.Goals.Goal;
import com.infosys.utilities.parasoftsoatest.ResultsSession.Scope;
import com.infosys.utilities.parasoftsoatest.ResultsSession.Scope.ProjectInformations;
import com.infosys.utilities.parasoftsoatest.ResultsSession.Scope.ProjectInformations.ScopeProjectInfo;
import com.infosys.utilities.parasoftsoatest.ResultsSession.TestConfig;
import com.infosys.utilities.parasoftsoatest.ResultsSession.VersionInfos;
import com.infosys.utilities.parasoftsoatest.ResultsSession.VersionInfos.StorageInfo;

public class ResultsSessionTest {

	@Test
	public void testEquals() throws DatatypeConfigurationException, ParseException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		ResultsSession resultsSession=new ResultsSession();
		TestConfig testConfig=new TestConfig();
		Authors authors=new Authors();
		Author author=new Author();
		VersionInfos versionInfos=new VersionInfos();
		Exec exec=new Exec();
		Exec.AssocUrls assocUrls=new Exec.AssocUrls();
		ExecutedTestsDetails executedTestsDetails=new ExecutedTestsDetails();
		Total total=new Total();
		Project project=new Project();
		TestSuite1 testSuite1=new TestSuite1();
		TestSuite2 testSuite2=new TestSuite2();
		TestSuite3 testSuite3=new TestSuite3();
		TestSuite4 testSuite4=new TestSuite4();
		TestSuite5 testSuite5=new TestSuite5();
		TestSuite5.Test test=new TestSuite5.Test();
		Scope scope=new Scope();
		ProjectInformations projectInformations=new ProjectInformations();
		ScopeProjectInfo scopeProjectInfo=new ScopeProjectInfo();
		FunctionalTests functionalTests=new FunctionalTests();
		Goals goals=new Goals();
		FuncViols funcViols=new FuncViols();
		FuncViol funcViol=new FuncViol();
		Categories categories=new Categories();
		AssocUrls assocUrls1=new AssocUrls();
		
		funcViol.setAuth("value");
		funcViol.setCat(1);
		funcViol.setConfig(1);
		funcViol.setFatal("value");
		funcViol.setHash(1);
		funcViol.setLang("value");
		funcViol.setLn(1);
		funcViol.setLocFile("value");
		funcViol.setLocType("value");
		funcViol.setMsg("value");
		funcViol.setRequestTrafficTime(1);
		funcViol.setResponseTrafficTime(1);
		funcViol.setSev(1);
		funcViol.setTaskType("value");
		funcViol.setTestCaseId("value");
		funcViol.setTool("value");
		funcViol.setUrgent("value");
		funcViol.setViolationDetails("value");
		
		funcViols.setFuncViol(funcViol);
		
		functionalTests.setCategories(categories);
		functionalTests.setFuncViols(funcViols);
		functionalTests.setGoals(goals);
		functionalTests.setOwnerId("value");
		functionalTests.setTime("value");
		
		scopeProjectInfo.setFltFiles(1);
		scopeProjectInfo.setFltLns(1);
		scopeProjectInfo.setProject("value");
		scopeProjectInfo.setTotFiles(1);
		scopeProjectInfo.setTotLns(1);
		
		projectInformations.setScopeProjectInfo(scopeProjectInfo);
		
		scope.setProjectInformations(projectInformations);
		
		test.setAuth("value");
		test.setAuthChange("value");
		test.setAuthFail("value");
		test.setChange(1);
		test.setChangePass(1);
		test.setChangeTotal(1);
		test.setFail(1);
		test.setId("value");
		test.setName("value");
		test.setPass(1);
		test.setTool("value");
		test.setTotal(1);
		
		testSuite5.setAuthChange("value");
		testSuite5.setAuthFail("value");
		testSuite5.setChange(1);
		testSuite5.setChangePass(1);
		testSuite5.setChangeTotal(1);
		testSuite5.setFail(1);
		testSuite5.setId("value");
		testSuite5.setName("value");
		testSuite5.setPass(1);
		testSuite5.setTest(test);
		testSuite5.setTotal(1);
		testSuite5.setAuth("value");
		
		testSuite4.setAuthChange("value");
		testSuite4.setAuthFail("value");
		testSuite4.setChange(1);
		testSuite4.setChangePass(1);
		testSuite4.setChangeTotal(1);
		testSuite4.setFail(1);
		testSuite4.setId("value");
		testSuite4.setName("value");
		testSuite4.setPass(1);
		testSuite4.setTestSuite(testSuite5);
		testSuite4.setTotal(1);
		testSuite4.setAuth("value");
		
		testSuite3.setAuthChange("value");
		testSuite3.setAuthFail("value");
		testSuite3.setChange(1);
		testSuite3.setChangePass(1);
		testSuite3.setChangeTotal(1);
		testSuite3.setFail(1);
		testSuite3.setId("value");
		testSuite3.setName("value");
		testSuite3.setPass(1);
		testSuite3.setTestSuite(testSuite4);
		testSuite3.setTotal(1);
		testSuite3.setAuth("value");
		
		testSuite2.setAuthChange("value");
		testSuite2.setAuthFail("value");
		testSuite2.setChange(1);
		testSuite2.setChangePass(1);
		testSuite2.setChangeTotal(1);
		testSuite2.setFail(1);
		testSuite2.setId("value");
		testSuite2.setName("value");
		testSuite2.setPass(1);
		testSuite2.setTestSuite(testSuite3);
		testSuite2.setTotal(1);
		testSuite2.setAuth("value");
		testSuite2.setRoot("value");
		
		testSuite1.setAuthChange("value");
		testSuite1.setAuthFail("value");
		testSuite1.setChange(1);
		testSuite1.setChangePass(1);
		testSuite1.setChangeTotal(1);
		testSuite1.setFail(1);
		testSuite1.setId("value");
		testSuite1.setName("value");
		testSuite1.setPass(1);
		testSuite1.setTestSuite(testSuite2);
		testSuite1.setTotal(1);
		
		project.setAuthChange("value");
		project.setAuthFail("value");
		project.setChange(1);
		project.setChangePass(1);
		project.setChangeTotal(1);
		project.setFail(1);
		project.setName("value");
		project.setPass(1);
		project.setTestSuite(testSuite1);
		project.setTotal(1);
		
		total.setAuthChange("value");
		total.setAuthFail("value");
		total.setChange(1);
		total.setChangePass(1);
		total.setChangeTotal(1);
		total.setFail(1);
		total.setName("value");
		total.setPass(1);
		total.setProject(project);
		total.setTotal(1);
		
		executedTestsDetails.setTotal(total);
		executedTestsDetails.setType("value");
		
		exec.setAssoc("value");
		exec.setAssocUrls(assocUrls);
		
		author.setId("value");
		author.setName("value");
		
		authors.setAuthor(author);
		
		testConfig.setMachine("value");
		testConfig.setName("value");
		testConfig.setPseudoUrl("value");
		testConfig.setUser("value");
		
		resultsSession.setAssocUrls(assocUrls1);
		resultsSession.setAuthors(authors);
		resultsSession.setBuildId("value");
		resultsSession.setDate("value");
		resultsSession.setExec(exec);
		resultsSession.setExecutedTestsDetails(executedTestsDetails);
		resultsSession.setFunctionalTests(functionalTests);
		resultsSession.setMachine("value");
		resultsSession.setProject("value");
		resultsSession.setScope(scope);
		resultsSession.setTag("value");
		resultsSession.setTestConfig(testConfig);
		resultsSession.setTime(xmlGregCal1);
		resultsSession.setToolName("value");
		resultsSession.setToolVer("value");
		resultsSession.setUser("value");
		resultsSession.setVersionInfos(versionInfos);
		
		assertEquals(0,resultsSession.getAssocUrls().getTag().size());
		assertEquals("value",resultsSession.getAuthors().getAuthor().getId());
		assertEquals("value",resultsSession.getAuthors().getAuthor().getName());
		assertEquals("value",resultsSession.getBuildId());
		assertEquals("value",resultsSession.getDate());
		assertEquals("value",resultsSession.getExec().getAssoc());
		assertEquals(0,resultsSession.getExec().getAssocUrls().getTag().size());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getType());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getAuthChange());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getAuthFail());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getChange());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getChangePass());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getChangeTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getFail());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getName());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getPass());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getAuthChange());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getAuthFail());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getChange());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getChangePass());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getChangeTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getFail());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getName());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getPass());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getAuthChange());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getAuthFail());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getChange());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getChangePass());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getChangeTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getFail());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getId());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getName());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getPass());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getAuth());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getAuthChange());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getAuthFail());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getChange());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getChangePass());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getChangeTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getFail());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getId());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getName());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getPass());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getRoot());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getAuth());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getAuthChange());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getAuthFail());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getChange());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getChangePass());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getChangeTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getFail());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getId());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getName());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getPass());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuth());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuthChange());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuthFail());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChange());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChangePass());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChangeTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getFail());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getId());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getName());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getPass());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuth());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuthChange());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuthFail());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChange());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChangePass());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChangeTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getFail());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getId());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getName());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getPass());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getAuth());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getAuthChange());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getAuthFail());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getChange());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getChangePass());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getChangeTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getFail());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getId());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getName());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getPass());
		assertEquals("value",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getTool());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTotal());
		assertEquals((Integer) 1,resultsSession.getExecutedTestsDetails().getTotal().getTotal());
		assertEquals(0,resultsSession.getFunctionalTests().getCategories().getCategory().size());
		assertEquals("value",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getAuth());
		assertEquals((Integer) 1,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getCat());
		assertEquals((Integer) 1,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getConfig());
		assertEquals("value",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getFatal());
		assertEquals((Integer) 1,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getHash());
		assertEquals("value",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getLang());
		assertEquals((Integer) 1,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getLn());
		assertEquals("value",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getLocFile());
		assertEquals("value",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getLocType());
		assertEquals("value",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getMsg());
		assertEquals((Integer) 1,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getRequestTrafficTime());
		assertEquals((Integer) 1,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getResponseTrafficTime());
		assertEquals((Integer) 1,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getSev());
		assertEquals("value",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getTaskType());
		assertEquals("value",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getTestCaseId());
		assertEquals("value",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getTool());
		assertEquals("value",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getUrgent());
		assertEquals("value",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getViolationDetails());
		assertEquals(0,resultsSession.getFunctionalTests().getGoals().getGoal().size());
		assertEquals("value",resultsSession.getFunctionalTests().getOwnerId());
		assertEquals("value",resultsSession.getFunctionalTests().getTime());
		assertEquals("value",resultsSession.getMachine());
		assertEquals("value",resultsSession.getProject());
		assertEquals((Integer) 1,resultsSession.getScope().getProjectInformations().getScopeProjectInfo().getFltFiles());
		assertEquals((Integer) 1,resultsSession.getScope().getProjectInformations().getScopeProjectInfo().getFltLns());
		assertEquals("value",resultsSession.getScope().getProjectInformations().getScopeProjectInfo().getProject());
		assertEquals((Integer) 1,resultsSession.getScope().getProjectInformations().getScopeProjectInfo().getTotFiles());
		assertEquals((Integer) 1,resultsSession.getScope().getProjectInformations().getScopeProjectInfo().getTotLns());
		assertEquals("value",resultsSession.getTag());
		assertEquals("value",resultsSession.getTestConfig().getMachine());
		assertEquals("value",resultsSession.getTestConfig().getName());
		assertEquals("value",resultsSession.getTestConfig().getPseudoUrl());
		assertEquals("value",resultsSession.getTestConfig().getUser());
		assertEquals((Integer) 24,(Integer)resultsSession.getTime().getDay());
		assertEquals("value",resultsSession.getToolName());
		assertEquals("value",resultsSession.getToolVer());
		assertEquals("value",resultsSession.getUser());
		assertEquals(0,resultsSession.getVersionInfos().getStorageInfo().size());
		
		
		StorageInfo storageInfo=new StorageInfo();
		
		storageInfo.setOwnerId("value");
		storageInfo.setResultId("value");
		storageInfo.setVer(1);
		
		assertEquals("value",storageInfo.getOwnerId());
		assertEquals("value",storageInfo.getResultId());
		assertEquals((Integer) 1,storageInfo.getVer());
		
		Tag tag=new Tag();
		
		tag.setDisp("value");
		tag.setName("value");
		
		assertEquals("value",tag.getDisp());
		assertEquals("value",tag.getName());
		
		Goal goal=new Goal();
		
		goal.setMode(1);
		goal.setName("value");
		goal.setType(1);
		
		assertEquals((Integer) 1,goal.getMode());
		assertEquals("value",goal.getName());
		assertEquals((Integer) 1,goal.getType());
		
		Category category=new Category();
		
		category.setDesc("value");
		category.setId(1);
		category.setLabel("value");
		category.setLabel0("value");
		category.setLabel1("value");
		category.setName("value");
		
		assertEquals("value",category.getDesc());
		assertEquals((Integer) 1,category.getId());
		assertEquals("value",category.getLabel());
		assertEquals("value",category.getLabel0());
		assertEquals("value",category.getLabel1());
		assertEquals("value",category.getName());
		assertEquals(0,category.getSubCategory().size());
		
		SubCategory subCategory=new SubCategory();
		
		subCategory.setId(1);
		subCategory.setName("value");
		subCategory.setShort("value");
		
		assertEquals((Integer) 1,subCategory.getId());
		assertEquals("value",subCategory.getName());
		assertEquals("value",subCategory.getShort());
		
		AssocUrls.Tag tag1=new AssocUrls.Tag();
		tag1.setDisp("value");
		tag1.setName("value");
		
		assertEquals("value",tag1.getName());
		assertEquals("value",tag1.getDisp());
		
	}
	
	@Test
	public void testNotEquals() throws DatatypeConfigurationException, ParseException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse("2014-04-24 11:15:00");
		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(date);

		XMLGregorianCalendar xmlGregCal1 =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal1);
		
		ResultsSession resultsSession=new ResultsSession();
		TestConfig testConfig=new TestConfig();
		Authors authors=new Authors();
		Author author=new Author();
		VersionInfos versionInfos=new VersionInfos();
		Exec exec=new Exec();
		Exec.AssocUrls assocUrls=new Exec.AssocUrls();
		ExecutedTestsDetails executedTestsDetails=new ExecutedTestsDetails();
		Total total=new Total();
		Project project=new Project();
		TestSuite1 testSuite1=new TestSuite1();
		TestSuite2 testSuite2=new TestSuite2();
		TestSuite3 testSuite3=new TestSuite3();
		TestSuite4 testSuite4=new TestSuite4();
		TestSuite5 testSuite5=new TestSuite5();
		TestSuite5.Test test=new TestSuite5.Test();
		Scope scope=new Scope();
		ProjectInformations projectInformations=new ProjectInformations();
		ScopeProjectInfo scopeProjectInfo=new ScopeProjectInfo();
		FunctionalTests functionalTests=new FunctionalTests();
		Goals goals=new Goals();
		FuncViols funcViols=new FuncViols();
		FuncViol funcViol=new FuncViol();
		Categories categories=new Categories();
		AssocUrls assocUrls1=new AssocUrls();
		
		funcViol.setAuth("value");
		funcViol.setCat(1);
		funcViol.setConfig(1);
		funcViol.setFatal("value");
		funcViol.setHash(1);
		funcViol.setLang("value");
		funcViol.setLn(1);
		funcViol.setLocFile("value");
		funcViol.setLocType("value");
		funcViol.setMsg("value");
		funcViol.setRequestTrafficTime(1);
		funcViol.setResponseTrafficTime(1);
		funcViol.setSev(1);
		funcViol.setTaskType("value");
		funcViol.setTestCaseId("value");
		funcViol.setTool("value");
		funcViol.setUrgent("value");
		funcViol.setViolationDetails("value");
		
		funcViols.setFuncViol(funcViol);
		
		functionalTests.setCategories(categories);
		functionalTests.setFuncViols(funcViols);
		functionalTests.setGoals(goals);
		functionalTests.setOwnerId("value");
		functionalTests.setTime("value");
		
		scopeProjectInfo.setFltFiles(1);
		scopeProjectInfo.setFltLns(1);
		scopeProjectInfo.setProject("value");
		scopeProjectInfo.setTotFiles(1);
		scopeProjectInfo.setTotLns(1);
		
		projectInformations.setScopeProjectInfo(scopeProjectInfo);
		
		scope.setProjectInformations(projectInformations);
		
		test.setAuth("value");
		test.setAuthChange("value");
		test.setAuthFail("value");
		test.setChange(1);
		test.setChangePass(1);
		test.setChangeTotal(1);
		test.setFail(1);
		test.setId("value");
		test.setName("value");
		test.setPass(1);
		test.setTool("value");
		test.setTotal(1);
		
		testSuite5.setAuthChange("value");
		testSuite5.setAuthFail("value");
		testSuite5.setChange(1);
		testSuite5.setChangePass(1);
		testSuite5.setChangeTotal(1);
		testSuite5.setFail(1);
		testSuite5.setId("value");
		testSuite5.setName("value");
		testSuite5.setPass(1);
		testSuite5.setTest(test);
		testSuite5.setTotal(1);
		testSuite5.setAuth("value");
		
		testSuite4.setAuthChange("value");
		testSuite4.setAuthFail("value");
		testSuite4.setChange(1);
		testSuite4.setChangePass(1);
		testSuite4.setChangeTotal(1);
		testSuite4.setFail(1);
		testSuite4.setId("value");
		testSuite4.setName("value");
		testSuite4.setPass(1);
		testSuite4.setTestSuite(testSuite5);
		testSuite4.setTotal(1);
		testSuite4.setAuth("value");
		
		testSuite3.setAuthChange("value");
		testSuite3.setAuthFail("value");
		testSuite3.setChange(1);
		testSuite3.setChangePass(1);
		testSuite3.setChangeTotal(1);
		testSuite3.setFail(1);
		testSuite3.setId("value");
		testSuite3.setName("value");
		testSuite3.setPass(1);
		testSuite3.setTestSuite(testSuite4);
		testSuite3.setTotal(1);
		testSuite3.setAuth("value");
		
		testSuite2.setAuthChange("value");
		testSuite2.setAuthFail("value");
		testSuite2.setChange(1);
		testSuite2.setChangePass(1);
		testSuite2.setChangeTotal(1);
		testSuite2.setFail(1);
		testSuite2.setId("value");
		testSuite2.setName("value");
		testSuite2.setPass(1);
		testSuite2.setTestSuite(testSuite3);
		testSuite2.setTotal(1);
		testSuite2.setAuth("value");
		testSuite2.setRoot("value");
		
		testSuite1.setAuthChange("value");
		testSuite1.setAuthFail("value");
		testSuite1.setChange(1);
		testSuite1.setChangePass(1);
		testSuite1.setChangeTotal(1);
		testSuite1.setFail(1);
		testSuite1.setId("value");
		testSuite1.setName("value");
		testSuite1.setPass(1);
		testSuite1.setTestSuite(testSuite2);
		testSuite1.setTotal(1);
		
		project.setAuthChange("value");
		project.setAuthFail("value");
		project.setChange(1);
		project.setChangePass(1);
		project.setChangeTotal(1);
		project.setFail(1);
		project.setName("value");
		project.setPass(1);
		project.setTestSuite(testSuite1);
		project.setTotal(1);
		
		total.setAuthChange("value");
		total.setAuthFail("value");
		total.setChange(1);
		total.setChangePass(1);
		total.setChangeTotal(1);
		total.setFail(1);
		total.setName("value");
		total.setPass(1);
		total.setProject(project);
		total.setTotal(1);
		
		executedTestsDetails.setTotal(total);
		executedTestsDetails.setType("value");
		
		exec.setAssoc("value");
		exec.setAssocUrls(assocUrls);
		
		author.setId("value");
		author.setName("value");
		
		authors.setAuthor(author);
		
		testConfig.setMachine("value");
		testConfig.setName("value");
		testConfig.setPseudoUrl("value");
		testConfig.setUser("value");
		
		resultsSession.setAssocUrls(assocUrls1);
		resultsSession.setAuthors(authors);
		resultsSession.setBuildId("value");
		resultsSession.setDate("value");
		resultsSession.setExec(exec);
		resultsSession.setExecutedTestsDetails(executedTestsDetails);
		resultsSession.setFunctionalTests(functionalTests);
		resultsSession.setMachine("value");
		resultsSession.setProject("value");
		resultsSession.setScope(scope);
		resultsSession.setTag("value");
		resultsSession.setTestConfig(testConfig);
		resultsSession.setTime(xmlGregCal1);
		resultsSession.setToolName("value");
		resultsSession.setToolVer("value");
		resultsSession.setUser("value");
		resultsSession.setVersionInfos(versionInfos);
		
		assertNotEquals(1,resultsSession.getAssocUrls().getTag().size());
		assertNotEquals("value1",resultsSession.getAuthors().getAuthor().getId());
		assertNotEquals("value1",resultsSession.getAuthors().getAuthor().getName());
		assertNotEquals("value1",resultsSession.getBuildId());
		assertNotEquals("value1",resultsSession.getDate());
		assertNotEquals("value1",resultsSession.getExec().getAssoc());
		assertNotEquals(1,resultsSession.getExec().getAssocUrls().getTag().size());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getType());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getAuthChange());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getAuthFail());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getChange());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getChangePass());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getChangeTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getFail());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getName());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getPass());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getAuthChange());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getAuthFail());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getChange());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getChangePass());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getChangeTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getFail());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getName());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getPass());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getAuthChange());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getAuthFail());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getChange());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getChangePass());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getChangeTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getFail());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getId());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getName());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getPass());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getAuth());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getAuthChange());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getAuthFail());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getChange());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getChangePass());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getChangeTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getFail());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getId());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getName());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getPass());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getRoot());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getAuth());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getAuthChange());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getAuthFail());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getChange());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getChangePass());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getChangeTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getFail());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getId());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getName());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getPass());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuth());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuthChange());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuthFail());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChange());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChangePass());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChangeTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getFail());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getId());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getName());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getPass());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuth());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuthChange());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getAuthFail());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChange());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChangePass());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getChangeTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getFail());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getId());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getName());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getPass());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getAuth());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getAuthChange());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getAuthFail());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getChange());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getChangePass());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getChangeTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getFail());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getId());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getName());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getPass());
		assertNotEquals("value1",resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getTool());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTest().getTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTestSuite().getTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTestSuite().getTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTestSuite().getTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTestSuite().getTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getProject().getTotal());
		assertNotEquals((Integer) 2,resultsSession.getExecutedTestsDetails().getTotal().getTotal());
		assertNotEquals(1,resultsSession.getFunctionalTests().getCategories().getCategory().size());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getAuth());
		assertNotEquals((Integer) 2,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getCat());
		assertNotEquals((Integer) 2,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getConfig());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getFatal());
		assertNotEquals((Integer) 2,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getHash());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getLang());
		assertNotEquals((Integer) 2,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getLn());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getLocFile());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getLocType());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getMsg());
		assertNotEquals((Integer) 2,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getRequestTrafficTime());
		assertNotEquals((Integer) 2,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getResponseTrafficTime());
		assertNotEquals((Integer) 2,resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getSev());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getTaskType());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getTestCaseId());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getTool());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getUrgent());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getFuncViols().getFuncViol().getViolationDetails());
		assertNotEquals(1,resultsSession.getFunctionalTests().getGoals().getGoal().size());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getOwnerId());
		assertNotEquals("value1",resultsSession.getFunctionalTests().getTime());
		assertNotEquals("value1",resultsSession.getMachine());
		assertNotEquals("value1",resultsSession.getProject());
		assertNotEquals((Integer) 2,resultsSession.getScope().getProjectInformations().getScopeProjectInfo().getFltFiles());
		assertNotEquals((Integer) 2,resultsSession.getScope().getProjectInformations().getScopeProjectInfo().getFltLns());
		assertNotEquals("value1",resultsSession.getScope().getProjectInformations().getScopeProjectInfo().getProject());
		assertNotEquals((Integer) 2,resultsSession.getScope().getProjectInformations().getScopeProjectInfo().getTotFiles());
		assertNotEquals((Integer) 2,resultsSession.getScope().getProjectInformations().getScopeProjectInfo().getTotLns());
		assertNotEquals("value1",resultsSession.getTag());
		assertNotEquals("value1",resultsSession.getTestConfig().getMachine());
		assertNotEquals("value1",resultsSession.getTestConfig().getName());
		assertNotEquals("value1",resultsSession.getTestConfig().getPseudoUrl());
		assertNotEquals("value1",resultsSession.getTestConfig().getUser());
		assertNotEquals((Integer) 25,(Integer)resultsSession.getTime().getDay());
		assertNotEquals("value1",resultsSession.getToolName());
		assertNotEquals("value1",resultsSession.getToolVer());
		assertNotEquals("value1",resultsSession.getUser());
		assertNotEquals(1,resultsSession.getVersionInfos().getStorageInfo().size());
		
		
		StorageInfo storageInfo=new StorageInfo();
		
		storageInfo.setOwnerId("value");
		storageInfo.setResultId("value");
		storageInfo.setVer(1);
		
		assertNotEquals("value1",storageInfo.getOwnerId());
		assertNotEquals("value1",storageInfo.getResultId());
		assertNotEquals((Integer) 2,storageInfo.getVer());
		
		Tag tag=new Tag();
		
		tag.setDisp("value");
		tag.setName("value");
		
		assertNotEquals("value1",tag.getDisp());
		assertNotEquals("value1",tag.getName());
		
		Goal goal=new Goal();
		
		goal.setMode(1);
		goal.setName("value");
		goal.setType(1);
		
		assertNotEquals((Integer) 2,goal.getMode());
		assertNotEquals("value1",goal.getName());
		assertNotEquals((Integer) 2,goal.getType());
		
		Category category=new Category();
		
		category.setDesc("value");
		category.setId(1);
		category.setLabel("value");
		category.setLabel0("value");
		category.setLabel1("value");
		category.setName("value");
		
		assertNotEquals("value1",category.getDesc());
		assertNotEquals((Integer) 2,category.getId());
		assertNotEquals("value1",category.getLabel());
		assertNotEquals("value1",category.getLabel0());
		assertNotEquals("value1",category.getLabel1());
		assertNotEquals("value1",category.getName());
		assertNotEquals(1,category.getSubCategory().size());
		
		SubCategory subCategory=new SubCategory();
		
		subCategory.setId(1);
		subCategory.setName("value");
		subCategory.setShort("value");
		
		assertNotEquals((Integer) 2,subCategory.getId());
		assertNotEquals("value1",subCategory.getName());
		assertNotEquals("value1",subCategory.getShort());
		
		AssocUrls.Tag tag1=new AssocUrls.Tag();
		tag1.setDisp("value");
		tag1.setName("value");
		
		assertNotEquals("value1",tag1.getName());
		assertNotEquals("value1",tag1.getDisp());
		
	}
}
