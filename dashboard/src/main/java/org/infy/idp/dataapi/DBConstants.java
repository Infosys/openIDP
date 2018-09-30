/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

/**
 * 
 * The class DBContants to handle dbqueries
 *
 */
@Component
public class DBConstants {
	
	private static String pipelineIdForApplication="select count(id),max(created_at) from appinfo where application_name=?;";
	private static String totalJobsForApplication="select count(appid),max(created_at) from buildinfo where appid in (select id from appinfo where application_name=?);";
	private static String totalSuccessfulJobsForApplication="select count(appid),max(created_at) from buildinfo where buildstatus='SUCCESS' and appid in (select id from appinfo where application_name=?);";
	private static String totalFailedJobsForApplication="select count(appid),max(created_at) from buildinfo where buildstatus='FAILURE' and appid in (select id from appinfo where application_name=?);";
	private static String totalSCMCheckoutsForApplication="select count(appid),max(created_at) from buildinfo where stagename='SCM' and appid in (select id from appinfo where application_name=?);";
	private static String scmDurationTrendForApplication="select buildtime,created_at from buildinfo where stagename='SCM'  and appid in (select id from appinfo where application_name=?);";
	private static String failedCheckoutsForApplication="select count(appid),max(created_at) from buildinfo where stagename='SCM' and appid in (select id from appinfo where application_name=?) and buildstatus='FAILURE';";
	private static String successfulCheckoutsForApplication="select count(appid),max(created_at) from buildinfo where stagename='SCM' and appid in (select id from appinfo where application_name=?) and buildstatus='SUCCESS';";
	private static String totalBuildsForApplication="select count(appid),max(created_at) from buildinfo where stagename='Build' and appid in (select id from appinfo where application_name=?);";
	private static String buildDurationTrendForApplication="select buildtime,created_at,buildtime from buildinfo where stagename='Build'  and appid in (select id from appinfo where application_name=?);";
	private static String totalFailedBuildsForApplication="select count(appid),max(created_at) from buildinfo where stagename='Build' and appid in (select id from appinfo where application_name=?) and buildstatus='FAILURE';";
	private static String totalSuccessfulBuildsForApplication="select count(appid),max(created_at) from buildinfo where stagename='Build' and appid in (select id from appinfo where application_name=?) and buildstatus='SUCCESS';";
	private static String totalTestsForApplication="select count(appid),max(created_at) from buildinfo where stagename like '%Test%' and appid in (select id from appinfo where application_name=?);";
	private static String testDurationTrendForApplication="select buildtime,created_at from buildinfo where stagename like '%Test%'  and appid in (select id from appinfo where application_name=? order by appid desc);";
	private static String totalFailedTestsForApplication="select count(appid),max(created_at) from buildinfo where stagename like '%Test%' and appid in (select id from appinfo where application_name=?) and buildstatus='FAILURE';";
	private static String totalSuccessfulTestsForApplication="select count(appid),max(created_at) from buildinfo where stagename like '%Test%' and appid in (select id from appinfo where application_name=?) and buildstatus!='FAILURE';";
	private static String totalDeploysForApplication="select count(appid),max(created_at) from buildinfo where stagename like '%Deploy%' and appid in (select id from appinfo where application_name=?);";
	private static String deployDurationTrendForApplication="select buildtime,created_at,buildtime from buildinfo where stagename like '%Deploy%'  and appid in (select id from appinfo where application_name=?);";
	private static String totalFailedDeploysForApplication="select count(appid),max(created_at) from buildinfo where stagename like '%Deploy%' and appid in (select id from appinfo where application_name=?) and buildstatus='FAILURE';";
	private static String totalSuccessfulDeploysForApplication="select count(appid),max(created_at) from buildinfo where stagename like '%Deploy%' and appid in (select id from appinfo where application_name=?) and buildstatus='SUCCESS';";
	private static String totalDeploysForApplicationInAWeek="SELECT count(*),max(created_at) from buildinfo where  DATE_PART('day',CURRENT_TIMESTAMP::timestamp-created_at::timestamp )<=7 and appid in (select id from appinfo where application_name=?) and stagename like '%Deploy%';";
	private static String applicationScore="select round(avg(score),2),max(created_at) from buildinfo where appid in (select id from appinfo where application_name =?);";
	private static String updatedRelease="select count(distinct t2.tfs_workitem)from tpipeline_info as t1 inner join( select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id;";
	private static String pipelineExecutedForRelease="select count (distinct t1.pipeline_name )from tpipeline_info as t1 inner join( select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id;";
	private static String expectedStartDate = "select  expected_start_date ,current_timestamp from trelease_info where release_number =? and application_id=(select application_id from tapplication_info where application_name=?) and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?);";
	private static String expectedEndDate = "select expected_end_date,current_timestamp from trelease_info where release_number =? and application_id=(select application_id from tapplication_info where application_name=?) and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?);";
	private static String lastExecutedOn ="Select trigger_time ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time Limit 1;";
	private static String buildSelectedForPipeline ="Select build_triggered ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time Limit 1;";
	private static String buildStatus ="Select deploy_status ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time  Limit 1;";
	private static String deploySelected ="Select deploy_triggered ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time  Limit 1;";
	private static String deployStatus ="Select deploy_status ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time;";
	private static String testSelected ="Select test_triggered ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time;";
	private static String testStatus ="Select test_status ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time;";
	private static String devSCMStatus ="select buildstatus,created_at from buildinfo where appid=(select id from appinfo where application_name=? and pipeline_name =?) and pipelineno=? and stagename like 'SCM';";
	private static String devBuildStatus ="select buildstatus,created_at from buildinfo where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like '%Build%';";
	private static String devDeployStatus ="select buildstatus,created_at from buildinfo where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like 'Deploy%';";
	private static String devTestStatus ="select buildstatus,created_at from buildinfo where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like 'Test%';";
	private static String devNoOfCommit ="select  count(*),max(created_at)from scminfo where appid=(select id from appinfo where application_name=? and pipeline_name=?)and pipelineno=?;";
	private static String devSCMDuration ="SELECT buildtime,created_at FROM buildinfo WHERE appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like 'SCM';";
	private static String devBuildDuration ="SELECT buildtime,created_at FROM buildinfo WHERE appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like '%Build%';";
	private static String devTestDuration ="SELECT sum(buildtime),max(created_at) FROM buildinfo WHERE appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like 'Test%';";
	private static String devDeployDuration ="SELECT sum(buildtime),max(created_at) FROM buildinfo WHERE appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like 'Deploy%';";
	private static String devTotalDuration ="select sum(buildtime),max(created_at)from buildinfo group by appid,pipelineno having appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=?;";
	private static String devZeroCoverageClasses ="select count(classname) , max(created_at) from codecoverage where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and linecoverage=0;";
	private static String dev50CoverageClasses ="select count(classname) ,max(created_at) from codecoverage where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and linecoverage>0 and linecoverage<=50;";
	private static String dev100CoverageClasses ="select count(classname) ,max(created_at) from codecoverage where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and linecoverage>50 and linecoverage<=100;";
	private static String devHighSeverity ="select count(rulename),max(created_at) from codeanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and severity='high';";
	private static String devMediumSeverity ="select count(rulename),max(created_at) from codeanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and severity='medium';";
	private static String devlowSeverity ="select count(rulename),max(created_at) from codeanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and severity='low';";
	private static String devTotalTests ="select count(*),max(created_at) from testanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=?;";
	private static String devFailedTests ="select count(*),max(created_at) from testanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and( status like '%Fail%' or status like '%FAILURE%' or status like '%fail%');";
	private static String devPassedTests ="select count(*),max(created_at) from testanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and (status='SUCCESS' or status='Passed' or status ='passed');";
	private static String mtmTotalTestPlans ="select distinct trigger_entity::json ->> 'testPlanId' from ttrigger_history where (trigger_entity::json ->> 'testPlanId'!='null' and trigger_entity::json ->> 'testPlanId'!='');";
	private static String mtmTotalTestSuites ="select distinct trigger_entity::json ->> 'testSuitId' from ttrigger_history where (trigger_entity::json ->> 'testSuitId'!='null' and trigger_entity::json ->> 'testSuitId'!='');";
	private static String mtmTotalTestCases ="select distinct trigger_entity::json ->> 'testPlanId' as \"testPlanId\" from ttrigger_history where (trigger_entity::json ->> 'testPlanId' !='null' and trigger_entity::json ->> 'testPlanId' !='' and trigger_entity::json->>'applicationName'=? and trigger_entity::json->>'releaseNumber'=? and trigger_entity::json->>'envSelected'=?);";
	//private static String mtmTestCaseSuccessRatio ="select distinct trigger_entity::json ->> 'testPlanId' as \"testPlanId\" from ttrigger_history where (trigger_entity::json ->> 'testPlanId' !='null' and trigger_entity::json ->> 'testPlanId' !='' and trigger_entity::json->>'applicationName'=? and trigger_entity::json->>'releaseNumber'=? and trigger_entity::json->>'envSelected'=?);";
	private static String mtmTestRunid ="select distinct trigger_entity::json ->> 'testPlanId' as \"testPlanId\" from ttrigger_history where (trigger_entity::json ->> 'testPlanId' !='null' and trigger_entity::json ->> 'testPlanId' !='' and trigger_entity::json->>'applicationName'=? and trigger_entity::json->>'releaseNumber'=? and trigger_entity::json->>'envSelected'=?);";
	private static String mtmTestRuntime ="select distinct trigger_entity::json ->> 'testPlanId' as \"testPlanId\" from ttrigger_history where (trigger_entity::json ->> 'testPlanId' !='null' and trigger_entity::json ->> 'testPlanId' !='' and trigger_entity::json->>'applicationName'=? and trigger_entity::json->>'releaseNumber'=? and trigger_entity::json->>'envSelected'=?;";
	
	
	
	private static String dashboard="DASHBOARD";
	private static String idp="IDP";
	
	
	
	protected Logger logger = LoggerFactory.getLogger(DBConstants.class);
	
	public List<String> getQuery(String queryName)
	{
		List<String> query = new ArrayList<>();
		switch (queryName) {
		case "pipelineIdForApplication":
			logger.info("pipelineIdForApplication");
			query.add(pipelineIdForApplication);
			query.add(dashboard);
			break;
			
		case "totalJobsForApplication":
			logger.info("totalJobsForApplication");
			query.add(totalJobsForApplication);
			query.add(dashboard);
			break;
		case "totalSuccessfulJobsForApplication":
			logger.info("totalSuccessfulJobsForApplication");
			query.add(totalSuccessfulJobsForApplication);
			query.add(dashboard);
			break;
		case "totalFailedJobsForApplication":
			logger.info("totalFailedJobsForApplication");
			query.add(totalFailedJobsForApplication);
			query.add(dashboard);
			break;
		case "totalSCMCheckoutsForApplication":
			logger.info("totalSCMCheckoutsForApplication");
			query.add(totalSCMCheckoutsForApplication);
			query.add(dashboard);
			break;
		case "SCMDurationTrendForApplication":
			logger.info("SCMDurationTrendForApplication");
			query.add(scmDurationTrendForApplication);
			query.add(dashboard);
			break;
		case "failedCheckoutsForApplication":
			logger.info("failedCheckoutsForApplication");
			query.add(failedCheckoutsForApplication);
			query.add(dashboard);
			break;
		case "successfulCheckoutsForApplication":
			logger.info("successfulCheckoutsForApplication");
			query.add(successfulCheckoutsForApplication);
			query.add(dashboard);
			break;
		case "totalBuildsForApplication":
			logger.info("totalBuildsForApplication");
			query.add(totalBuildsForApplication);
			query.add(dashboard);
			break;
		case "buildDurationTrendForApplication":
			logger.info("buildDurationTrendForApplication");
			query.add(buildDurationTrendForApplication);
			query.add(dashboard);
			break;
		case "totalFailedBuildsForApplication":
			logger.info("totalFailedBuildsForApplication");
			query.add(totalFailedBuildsForApplication);
			query.add(dashboard);
			break;
		case "totalSuccessfulBuildsForApplication":
			logger.info("totalSuccessfulBuildsForApplication");
			query.add(totalSuccessfulBuildsForApplication);
			query.add(dashboard);
			break;
		case "totalTestsForApplication":
			logger.info("totalTestsForApplication");
			query.add(totalTestsForApplication);
			query.add(dashboard);
			break;
		case "testDurationTrendForApplication":
			logger.info("testDurationTrendForApplication");
			query.add(testDurationTrendForApplication);
			query.add(dashboard);
			break;
		case "totalFailedTestsForApplication":
			logger.info("totalFailedTestsForApplication");
			query.add(totalFailedTestsForApplication);
			query.add(dashboard);
			break;
		case "totalSuccessfulTestsForApplication":
			logger.info("totalSuccessfulTestsForApplication");
			query.add(totalSuccessfulTestsForApplication);
			query.add(dashboard);
			break;
		case "totalDeploysForApplication":
			logger.info("totalDeploysForApplication");
			query.add(totalDeploysForApplication);
			query.add(dashboard);
			break;
		case "deployDurationTrendForApplication":
			logger.info("deployDurationTrendForApplication");
			query.add(deployDurationTrendForApplication);
			query.add(dashboard);
			break;
		case "totalFailedDeploysForApplication":
			logger.info("totalFailedDeploysForApplication");
			query.add(totalFailedDeploysForApplication);
			query.add(dashboard);
			break;
		case "totalSuccessfulDeploysForApplication":
			logger.info("totalSuccessfulDeploysForApplication");
			query.add(totalSuccessfulDeploysForApplication);
			query.add(dashboard);
			break;
		case "totalDeploysForApplicationInAWeek":
			logger.info("totalDeploysForApplicationInAWeek");
			query.add(totalDeploysForApplicationInAWeek);
			query.add(dashboard);		
			break;			
		case "applicationScore":
			logger.info("applicationScore");
			query.add(applicationScore);
			query.add(dashboard);
			break;
		case "updatedRelease":
			logger.info("updatedRelease");
			query.add(updatedRelease);
			query.add(idp);
			break;
		case "pipelineExecutedForRelease":
			logger.info("pipelineExecutedForRelease");
			query.add(pipelineExecutedForRelease);
			query.add(dashboard);		
			break;			
		case "expectedStartDate":
			logger.info("expectedStartDate");
			query.add(expectedStartDate);
			query.add(idp);
			break;
		case "expectedEndDate":
			logger.info("expectedEndDate");
			query.add(expectedEndDate);
			query.add(idp);
			break;
		case "lastExecutedOn":
			logger.info("lastExecutedOn");
			query.add(lastExecutedOn);
			query.add(idp);	
			break;
		case "buildSelectedForPipeline":
			logger.info("buildSelectedForPipeline");
			query.add(buildSelectedForPipeline);
			query.add(idp);	
			break;
		case "testSelected":
			logger.info("testSelected");
			query.add(testSelected);
			query.add(idp);
			break;
		case "testStatus":
			logger.info("testStatus");
			query.add(testStatus);
			query.add(idp);
			break;
		case "buildStatus":
			logger.info("buildStatus");
			query.add(buildStatus);
			query.add(idp);
			break;			
		case "deploySelected":
			logger.info("deploySelected");
			query.add(deploySelected);
			query.add(idp);	
			break;
		case "deployStatus":
			logger.info("deployStatus");
			query.add(deployStatus);
			query.add(idp);
			break;			
		case "devSCMStatus":
			logger.info("devSCMStatus");
			query.add(devSCMStatus);
			query.add(dashboard);
			break;
		case "devBuildStatus":
			logger.info("devBuildStatus");
			query.add(devBuildStatus);
			query.add(dashboard);
			break;
		case "devDeployStatus":
			logger.info("devDeployStatus");
			query.add(devDeployStatus);
			query.add(dashboard);
			break;
		case "devTestStatus":
			logger.info("devTestStatus");
			query.add(devTestStatus);
			query.add(dashboard);
			break;
		case "devNoOfCommit":
			logger.info("devNoOfCommit");
			query.add(devNoOfCommit);
			query.add(dashboard);
			break;
		case "devSCMDuration":
			logger.info("devSCMDuration");
			query.add(devSCMDuration);
			query.add(dashboard);
			break;
		case "devBuildDuration":
			logger.info("devBuildDuration");
			query.add(devBuildDuration);
			query.add(dashboard);
			break;
		case "devTestDuration":
			logger.info("devTestDuration");
			query.add(devTestDuration);
			query.add(dashboard);
			break;
		case "devDeployDuration":
			logger.info("devDeployDuration");
			query.add(devDeployDuration);
			query.add(dashboard);
			break;
		case "devTotalDuration":
			logger.info("devTotalDuration");
			query.add(devTotalDuration);
			query.add(dashboard);
			break;
		case "devZeroCoverageClasses":
			logger.info("devZeroCoverageClasses");
			query.add(devZeroCoverageClasses);
			query.add(dashboard);
			break;
		case "dev50CoverageClasses":
			logger.info("dev50CoverageClasses");
			query.add(dev50CoverageClasses);
			query.add(dashboard);
			break;
		case "dev100CoverageClasses":
			logger.info("dev100CoverageClasses");
			query.add(dev100CoverageClasses);
			query.add(dashboard);
			break;
		case "devHighSeverity":
			logger.info("devHighSeverity");
			query.add(devHighSeverity);
			query.add(dashboard);
			break;
		case "devMediumSeverity":
			logger.info("devMediumSeverity");
			query.add(devMediumSeverity);
			query.add(dashboard);
			break;
		case "devlowSeverity":
			logger.info("devlowSeverity");
			query.add(devlowSeverity);
			query.add(dashboard);
			break;
		case "devTotalTests":
			logger.info("devTotalTests");
			query.add(devTotalTests);
			query.add(dashboard);
			break;
		case "devFailedTests":
			logger.info("devFailedTests");
			query.add(devFailedTests);
			query.add(dashboard);
			break;
		case "devPassedTests":
			logger.info("devPassedTests");
			query.add(devPassedTests);
			query.add(dashboard);
			break;
		case "mtmTotalTestPlans":
			logger.info("mtmTotalTestPlans");
			query.add(mtmTotalTestPlans);
			query.add(dashboard);
			break;
		case "mtmTotalTestSuites":
			logger.info("mtmTotalTestSuites");
			query.add(mtmTotalTestSuites);
			query.add(dashboard);
			break;
		case "mtmTotalTestCases":
			logger.info("mtmTotalTestCases");
			query.add(mtmTotalTestCases);
			query.add(dashboard);
			break;
//		case "mtmTestCaseSuccessRatio":
//			logger.info("mtmTestCaseSuccessRatio");
//			query.add(mtmTestCaseSuccessRatio);
//			query.add(dashboard);
//			break;
		case "mtmTestRunid":
			logger.info("mtmTestRunid");
			query.add(mtmTestRunid);
			query.add(dashboard);
			break;
		case "mtmTestRuntime":
			logger.info("mtmTestRuntime");
			query.add(mtmTestRuntime);
			query.add(dashboard);
			break;
		default:			

			break;
		}
		if(query.isEmpty()){
			query.add(queryName);
			if(queryName.contains("tapplication_info") || queryName.contains("ttrigger_history") || queryName.contains("tpipeline_info"))query.add(idp);
			else query.add(dashboard);
		}
		return query;
	}
	

}
