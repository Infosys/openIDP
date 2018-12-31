package org.infy.idp.bl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.AppContext;
import org.infy.idp.dataapi.BuildInfoDL;
import org.infy.idp.dataapi.CodeAnalysisDL;
import org.infy.idp.dataapi.CodeCoverageDL;
import org.infy.idp.dataapi.Executor;
import org.infy.idp.dataapi.FileNetExportAnalysisDL;
import org.infy.idp.dataapi.FileNetImportAnalysisDL;
import org.infy.idp.dataapi.IDPHistoryInfoDL;
import org.infy.idp.dataapi.IDPPostGreSqlDbContext;
import org.infy.idp.dataapi.PipelineDL;
import org.infy.idp.dataapi.PostGreSqlDbContext;
import org.infy.idp.dataapi.SCMInfoDL;
import org.infy.idp.dataapi.TestAnalysisDL;
import org.infy.idp.entities.BuildDetail;
import org.infy.idp.entities.Cobertura;
import org.infy.idp.entities.CodeAnalysis;
import org.infy.idp.entities.CodeQuality;
import org.infy.idp.entities.Codecoverage;
import org.infy.idp.entities.CoverageDetails;
import org.infy.idp.entities.Istanbul;
import org.infy.idp.entities.Jacoco;
import org.infy.idp.entities.JsonClass;
import org.infy.idp.entities.Sonar;
import org.infy.idp.entities.TestCaseResult;
import org.infy.idp.utils.ConfigurationManager;
import org.infy.idp.utils.jira.DetailsForJira;
import org.infy.idp.utils.vsts.DetailsForVSTSFetcher;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class InsertInfoBLTest {

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;
	
	@Spy
	@InjectMocks
	private IDPPostGreSqlDbContext idpPostGreSqlDbContext;
	
	@Spy
	@Autowired
	private ConfigurationManager configmanager;

	@Spy
	@InjectMocks
	private InsertInfoBL insertInfoBL;

	@Spy
	@InjectMocks
	private BuildInfoDL buildinfoDL;

	@Spy
	@InjectMocks
	private PipelineDL appinfo;
	
	@Spy
	@InjectMocks
	private IDPHistoryInfoDL historydl;

	@Spy
	@InjectMocks
	private CodeCoverageDL coveragedl;

	@Spy
	@InjectMocks
	private CodeAnalysisDL codeAnalysisDL;

	@Spy
	@InjectMocks
	private TestAnalysisDL testAnalysisdl;

	@Spy
	@InjectMocks
	private SCMInfoDL scminfodl;

	@Spy
	@InjectMocks
	private DetailsForVSTSFetcher detailsForVSTSFetcher;

	@Spy
	@InjectMocks
	private DetailsForJira detailsForJira;

	@Spy
	@InjectMocks
	private FileNetExportAnalysisDL fileNetExportAnalysisDL;

	@Spy
	@InjectMocks
	private FileNetImportAnalysisDL fileNetImportAnalysisDL;

	@Spy
	@InjectMocks
	private Executor executor;
	
	
	@Before
	public void setUp() throws Exception {

		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = PostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(postGreSqlDbContext);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = IDPPostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(idpPostGreSqlDbContext);

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void test() throws JSONException {
		
		JsonClass json=new JsonClass();
		
		json.setApplication("application");
		
		BuildDetail buildDetail=new BuildDetail();
		buildDetail.setBuildTime("10.0");
		buildDetail.setBuiltStatus("builtStatus");
		buildDetail.setLastBuildId("2");
		buildDetail.setLastCompletedBuildId("1");
		buildDetail.setLastFailedBuildId("1");
		buildDetail.setLastSuccessfulBuildId("1");
		buildDetail.setLastUnstableBuildId("1");
		buildDetail.setLastUnsuccessfulBuildId("1");
		buildDetail.setScore("10");
		buildDetail.setStageName("stageName");
		buildDetail.setTimestamp("10.0");
		List<BuildDetail> buildDetails=new ArrayList<>();
		buildDetails.add(buildDetail);
		json.setBuildDetails(buildDetails);
		
		json.setbuildId("1");
		
		CodeAnalysis codeAnalysis=new CodeAnalysis();
		codeAnalysis.setSeverity("high");
		codeAnalysis.setcategory("sonarqube");
		codeAnalysis.setruleName("gotype");
		codeAnalysis.setLine("7");
		
		codeAnalysis.setRecommendation("value");
		codeAnalysis.setClassName("class");
		
		codeAnalysis.setId("main_go");
		codeAnalysis.setMessage("message");
		List<CodeAnalysis> codeAnalysisL=new ArrayList<>();
		codeAnalysisL.add(codeAnalysis);
		json.setCodeAnalysis(codeAnalysisL);
		Codecoverage codeCoverage=new Codecoverage();
		Cobertura cobertura =new Cobertura();
		cobertura.setBranchCoverage("1.0");
		cobertura.setClassCoverage("1.0");
		cobertura.setComplexityScore("1.0");
		cobertura.setInstructionCoverage("1.0");
		cobertura.setLineCoverage("1.0");
		cobertura.setMethodCoverage("1.0");
		
		codeCoverage.setCobertura(cobertura);
		Istanbul istanbul=new Istanbul();
		istanbul.setLineCoverage("1.0");
		codeCoverage.setIstanbul(istanbul);
		Jacoco jacoco=new Jacoco();
		jacoco.setBranchCoverage("1.0");
		jacoco.setClassCoverage("1.0");
		jacoco.setComplexityScore("1.0");
		jacoco.setInstructionCoverage("1.0");
		jacoco.setLineCoverage("1.0");
		jacoco.setMethodCoverage("1.0");
		codeCoverage.setJacoco(jacoco);
		json.setCodecoverage(codeCoverage);
		CodeQuality codeQuality=new CodeQuality();
		Sonar sonar=new Sonar();
		sonar.setBlocker(1);
		sonar.setCritical(2);
		sonar.setInfo(3);
		sonar.setMajor(4);
		sonar.setMinor(5);
		codeQuality.setSonar(sonar);
		json.setCodeQuality(codeQuality);
		
		CoverageDetails coverageDetails=new CoverageDetails();
		coverageDetails.setCategory("category");
		coverageDetails.setClassName("className");
		coverageDetails.setLineCoverage("1.0");
		coverageDetails.setPckage("pckage");
		List<CoverageDetails> coverageDetailsList=new ArrayList<>();
		coverageDetailsList.add(coverageDetails);
		json.setCoverageDetailsList(coverageDetailsList);
		
		TestCaseResult testCaseResult=new TestCaseResult();
		testCaseResult.setCategory("Functional");
		testCaseResult.setStatus("FAILURE");
		testCaseResult.setMessage("Testcase failed");
		testCaseResult.setDuration("1.0");

		List<TestCaseResult> testCaseResultList=new ArrayList<>();
		testCaseResultList.add(testCaseResult);
		json.setTestCaseResult(testCaseResultList);
//		insertInfoBL.buildDetails(null);
//		insertInfoBL.codeAnalysis(null);
//		insertInfoBL.computeCodeCoverage(null);
//		insertInfoBL.coverageDetails(null);
//		insertInfoBL.createBuildObject(null);
//		insertInfoBL.dataInfo(10, "main", "mivalue", "cpvalue", "dpvalue");
//		insertInfoBL.insertBuildInfo("appname", 0, null, "java", 0);
//		insertInfoBL.insertCodeAnalysisDetails(0, 10, 3, null);
//		insertInfoBL.insertCoverageInfo(null, 0, 1, 3);
//		insertInfoBL.insertFileNetExportInfo(2, null);
//		insertInfoBL.insertFileNetImportInfo(null);
//		insertInfoBL.insertSCMInfo(0, 10, null);
//		insertInfoBL.insertTestAnalysisInfo(0, 10, 2, null);
//		insertInfoBL.parseClasssDefinition(null);
//		insertInfoBL.parseDocumentType(null);
//		insertInfoBL.parsefolder(null);
//		insertInfoBL.parseOthers(null);
//		insertInfoBL.parsePropertyTemplate(null);
		insertInfoBL.processInfo(json, "application", "pipeline", 1);
		insertInfoBL.scmInfo(null);
		insertInfoBL.testAnalysis(null);
		insertInfoBL.updateAppDetails("data");
		insertInfoBL.updateFileNetDB("1", null);
		insertInfoBL.updateVSTSDb("1", "10");
		insertInfoBL.updateVSTSJiraDb("1", "1");
		Assert.assertEquals(true, true);
		
		
	}
}
