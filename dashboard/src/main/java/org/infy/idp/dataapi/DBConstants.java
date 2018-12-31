/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * The class DBContants to handle dbqueries
 *
 */
@Component
public class DBConstants {
	private HashMap<String, String> datamap = new HashMap();
	private List<String> idpList = new ArrayList();
	protected Logger logger = LoggerFactory.getLogger(DBConstants.class);
	public static final String DASHBOARD = "DASHBOARD";
	public static final String IDP = "IDP";

	DBConstants() {
		datamap.put("pipelineIdForApplication",
				"select count(id),max(created_at) from appinfo where application_name=?;");
		datamap.put("totalJobsForApplication",
				"select count(appid),max(created_at) from buildinfo where appid in (select id from appinfo where application_name=?);");
		datamap.put("totalSuccessfulJobsForApplication",
				"select count(appid),max(created_at) from buildinfo where buildstatus='SUCCESS' and appid in (select id from appinfo where application_name=?);");
		datamap.put("totalFailedJobsForApplication",
				"select count(appid),max(created_at) from buildinfo where buildstatus='FAILURE' and appid in (select id from appinfo where application_name=?);");
		datamap.put("totalSCMCheckoutsForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename='SCM' and appid in (select id from appinfo where application_name=?);");
		datamap.put("scmDurationTrendForApplication",
				"select buildtime,created_at from buildinfo where stagename='SCM'  and appid in (select id from appinfo where application_name=?);");
		datamap.put("failedCheckoutsForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename='SCM' and appid in (select id from appinfo where application_name=?) and buildstatus='FAILURE';");
		datamap.put("successfulCheckoutsForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename='SCM' and appid in (select id from appinfo where application_name=?) and buildstatus='SUCCESS';");
		datamap.put("totalBuildsForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename='Build' and appid in (select id from appinfo where application_name=?);");
		datamap.put("buildDurationTrendForApplication",
				"select buildtime,created_at,buildtime from buildinfo where stagename='Build'  and appid in (select id from appinfo where application_name=?);");
		datamap.put("totalFailedBuildsForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename='Build' and appid in (select id from appinfo where application_name=?) and buildstatus='FAILURE';");
		datamap.put("totalSuccessfulBuildsForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename='Build' and appid in (select id from appinfo where application_name=?) and buildstatus='SUCCESS';");
		datamap.put("totalTestsForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename like '%Test%' and appid in (select id from appinfo where application_name=?);");
		datamap.put("testDurationTrendForApplication",
				"select buildtime,created_at from buildinfo where stagename like '%Test%'  and appid in (select id from appinfo where application_name=? order by appid desc);");
		datamap.put("totalFailedTestsForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename like '%Test%' and appid in (select id from appinfo where application_name=?) and buildstatus='FAILURE';");
		datamap.put("totalSuccessfulTestsForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename like '%Test%' and appid in (select id from appinfo where application_name=?) and buildstatus!='FAILURE';");
		datamap.put("totalDeploysForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename like '%Deploy%' and appid in (select id from appinfo where application_name=?);");
		datamap.put("deployDurationTrendForApplication",
				"select buildtime,created_at,buildtime from buildinfo where stagename like '%Deploy%'  and appid in (select id from appinfo where application_name=?);");
		datamap.put("totalFailedDeploysForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename like '%Deploy%' and appid in (select id from appinfo where application_name=?) and buildstatus='FAILURE';");
		datamap.put("totalSuccessfulDeploysForApplication",
				"select count(appid),max(created_at) from buildinfo where stagename like '%Deploy%' and appid in (select id from appinfo where application_name=?) and buildstatus='SUCCESS';");
		datamap.put("totalDeploysForApplicationInAWeek",
				"SELECT count(*),max(created_at) from buildinfo where  DATE_PART('day',CURRENT_TIMESTAMP::timestamp-created_at::timestamp )<=7 and appid in (select id from appinfo where application_name=?) and stagename like '%Deploy%';");
		datamap.put("applicationScore",
				"select round(avg(score),2),max(created_at) from buildinfo where appid in (select id from appinfo where application_name =?);");
		datamap.put("updatedRelease",
				"select count(distinct t2.tfs_workitem)from tpipeline_info as t1 inner join( select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id;");
		datamap.put("pipelineExecutedForRelease",
				"select count (distinct t1.pipeline_name )from tpipeline_info as t1 inner join( select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id;");
		datamap.put("expectedStartDate",
				"select  expected_start_date ,current_timestamp from trelease_info where release_number =? and application_id=(select application_id from tapplication_info where application_name=?) and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?);");
		datamap.put("expectedEndDate",
				"select expected_end_date,current_timestamp from trelease_info where release_number =? and application_id=(select application_id from tapplication_info where application_name=?) and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?);");
		datamap.put("lastExecutedOn",
				"Select trigger_time ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time Limit 1;");
		datamap.put("buildSelectedForPipeline",
				"Select build_triggered ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time Limit 1;");
		datamap.put("buildStatus",
				"Select deploy_status ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time  Limit 1;");
		datamap.put("deploySelected",
				"Select deploy_triggered ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time  Limit 1;");
		datamap.put("deployStatus",
				"Select deploy_status ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time;");
		datamap.put("testSelected",
				"Select test_triggered ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time;");
		datamap.put("testStatus",
				"Select test_status ,current_timestamp from ttrigger_history where release_number=? and pipeline_id=(select pipeline_id from tpipeline_info where pipeline_name=?) and tfs_workitem=? order by trigger_time;");
		datamap.put("devSCMStatus",
				"select buildstatus,created_at from buildinfo where appid=(select id from appinfo where application_name=? and pipeline_name =?) and pipelineno=? and stagename like 'SCM';");
		datamap.put("devBuildStatus",
				"select buildstatus,created_at from buildinfo where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like '%Build%';");
		datamap.put("devDeployStatus",
				"select buildstatus,created_at from buildinfo where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like 'Deploy%';");
		datamap.put("devTestStatus",
				"select buildstatus,created_at from buildinfo where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like 'Test%';");
		datamap.put("devNoOfCommit",
				"select  count(*),max(created_at)from scminfo where appid=(select id from appinfo where application_name=? and pipeline_name=?)and pipelineno=?;");
		datamap.put("devSCMDuration",
				"SELECT buildtime,created_at FROM buildinfo WHERE appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like 'SCM';");
		datamap.put("devBuildDuration",
				"SELECT buildtime,created_at FROM buildinfo WHERE appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like '%Build%';");
		datamap.put("devTestDuration",
				"SELECT sum(buildtime),max(created_at) FROM buildinfo WHERE appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like 'Test%';");
		datamap.put("devDeployDuration",
				"SELECT sum(buildtime),max(created_at) FROM buildinfo WHERE appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and stagename like 'Deploy%';");
		datamap.put("devTotalDuration",
				"select sum(buildtime),max(created_at)from buildinfo group by appid,pipelineno having appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=?;");
		datamap.put("devZeroCoverageClasses",
				"select count(classname) , max(created_at) from codecoverage where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and linecoverage=0;");
		datamap.put("dev50CoverageClasses",
				"select count(classname) ,max(created_at) from codecoverage where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and linecoverage>0 and linecoverage<=50;");
		datamap.put("dev100CoverageClasses",
				"select count(classname) ,max(created_at) from codecoverage where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and linecoverage>50 and linecoverage<=100;");
		datamap.put("devHighSeverity",
				"select count(rulename),max(created_at) from codeanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and severity='high';");
		datamap.put("devMediumSeverity",
				"select count(rulename),max(created_at) from codeanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and severity='medium';");
		datamap.put("devlowSeverity",
				"select count(rulename),max(created_at) from codeanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and severity='low';");
		datamap.put("devTotalTests",
				"select count(*),max(created_at) from testanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=?;");
		datamap.put("devFailedTests",
				"select count(*),max(created_at) from testanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and( status like '%Fail%' or status like '%FAILURE%' or status like '%fail%');");
		datamap.put("devPassedTests",
				"select count(*),max(created_at) from testanalysis where appid=(select id from appinfo where application_name=? and pipeline_name =?)  and pipelineno=? and (status='SUCCESS' or status='Passed' or status ='passed');");
		datamap.put("mtmTotalTestPlans",
				"select distinct trigger_entity::json ->> 'testPlanId' from ttrigger_history where (trigger_entity::json ->> 'testPlanId'!='null' and trigger_entity::json ->> 'testPlanId'!='');");
		datamap.put("mtmTotalTestSuites",
				"select distinct trigger_entity::json ->> 'testSuitId' from ttrigger_history where (trigger_entity::json ->> 'testSuitId'!='null' and trigger_entity::json ->> 'testSuitId'!='');");
		datamap.put("mtmTotalTestCases",
				"select distinct trigger_entity::json ->> 'testPlanId' as \"testPlanId\" from ttrigger_history where (trigger_entity::json ->> 'testPlanId' !='null' and trigger_entity::json ->> 'testPlanId' !='' and trigger_entity::json->>'applicationName'=? and trigger_entity::json->>'releaseNumber'=? and trigger_entity::json->>'envSelected'=?);");
		datamap.put("mtmTestRunid",
				"select distinct trigger_entity::json ->> 'testPlanId' as \"testPlanId\" from ttrigger_history where (trigger_entity::json ->> 'testPlanId' !='null' and trigger_entity::json ->> 'testPlanId' !='' and trigger_entity::json->>'applicationName'=? and trigger_entity::json->>'releaseNumber'=? and trigger_entity::json->>'envSelected'=?);");
		datamap.put("mtmTestRuntime",
				"select distinct trigger_entity::json ->> 'testPlanId' as \"testPlanId\" from ttrigger_history where (trigger_entity::json ->> 'testPlanId' !='null' and trigger_entity::json ->> 'testPlanId' !='' and trigger_entity::json->>'applicationName'=? and trigger_entity::json->>'releaseNumber'=? and trigger_entity::json->>'envSelected'=?;");
		
		
		idpList.add("updatedRelease");
		idpList.add("expectedStartDate");
		idpList.add("expectedEndDate");
		idpList.add("lastExecutedOn");
		idpList.add("buildSelectedForPipeline");
		idpList.add("testSelected");
		idpList.add("testStatus");
		idpList.add("buildStatus");
		idpList.add("deploySelected");
		idpList.add("deployStatus");
			
	}



	public List<String> getQuery(String queryName) {
		List<String> query = new ArrayList<>();
		if (datamap.containsKey(queryName)) {
			query.add(datamap.get(queryName));
			if (idpList.contains(queryName)) {
				query.add(IDP);
			} else {
				query.add(DASHBOARD);
			}
		}
		if (query.isEmpty()) {
			query.add(queryName);
			if (queryName.contains("tapplication_info") || queryName.contains("ttrigger_history")
					|| queryName.contains("tpipeline_info"))
				query.add(IDP);
			else
				query.add(DASHBOARD);
		}
		return query;
	}
}
