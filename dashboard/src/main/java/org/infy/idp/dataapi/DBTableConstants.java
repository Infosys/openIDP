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
 * The class DBTableConstants to handle dbqueries
 *
 */
@Component
public class DBTableConstants {
	
	private HashMap<String, String> datamap = new HashMap();
	private List<String> idplist = new ArrayList();
	protected Logger logger = LoggerFactory.getLogger(DBTableConstants.class);
	public static final String DASHBOARD = "DASHBOARD";
	public static final String IDP = "IDP";

	DBTableConstants() {
		datamap.put("appCommitsPerEngineer",
				"select lastmodifiedby as Developer,count(*) as commits_per_pipeline from scminfo group by appid,lastmodifiedby having appid in (select id from appinfo where application_name=?);");
		datamap.put("updatedRelease", "select count(distinct t2.tfs_workitem)from tpipeline_info as t1 inner join(\r\n"
				+ "select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id;");
		datamap.put("pipelineExecutedForRelease",
				"select count (distinct t1.pipeline_name )from tpipeline_info as t1 inner join( select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id;");
		datamap.put("devTopViolatedRules",
				"select  rulename,count(*) as count,category from codeanalysis  group by severity,rulename,appid,pipelineno,category having appid=(select id from appinfo where application_name=? and pipeline_name=?)and pipelineno=? order by count(*) desc limit 5;");
		datamap.put("devTestResultInfo",
				"select category,status,duration,message from testanalysis where appid=(select id from appinfo where application_name=? and pipeline_name=? ) and pipelineno=?;");
		datamap.put("mtmTestSuites",
				"select distinct trigger_entity::json ->> 'testPlanId' as \"testPlanId\" from ttrigger_history where (trigger_entity::json ->> 'testPlanId' !='null' and trigger_entity::json ->> 'testPlanId' !='' and trigger_entity::json->>'applicationName'=? and trigger_entity::json->>'releaseNumber'=? and trigger_entity::json->>'envSelected'=?);");
		datamap.put("sparkArtifactsDetails",
				"select version,trigger_entity::json ->> 'buildartifactNumber' as \"buildartifactNumber\",trigger_time,trigger_entity::json ->> 'userName' as \"userName\",trigger_entity::json ->> 'applicationName' as \"applicationName\",trigger_entity::json ->> 'pipelineName' as \"pipelineName\" from ttrigger_history where trigger_entity::json ->> 'applicationName'=? and trigger_entity::json->>'envSelected'=? and trigger_entity::json->>'pipelineName'=?;");
		datamap.put("sparkEnvDetailsTypeNew",
				"select trigger_entity::json ->> 'pipelineName' as \"pipelineName\",version,trigger_time from ttrigger_history t1 where (trigger_time=(select max(trigger_time) from ttrigger_history t2 where t1.trigger_entity::json ->> 'pipelineName'=t2.trigger_entity::json ->> 'pipelineName'  ) and trigger_entity::json->>'applicationName'=? and trigger_entity::json->>'envSelected'=?);");
		datamap.put("storystatusboar",
				"select distinct t2.tfs_workitem as WIT ,t1.pipeline_name,t2.environment as Environment from tpipeline_info as t1 inner join(select release_number,pipeline_id,tfs_workitem,environment from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from  tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id;");
		datamap.put("alm1StoryCount",
				"select count(distinct t2.tfs_workitem)from tpipeline_info as t1 inner join(select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id");
		datamap.put("alm1PipelineCount",
				" select count (distinct t1.pipeline_name )from tpipeline_info as t1 inner join( select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id");
		datamap.put("jiraStoryCount",
				"select count(distinct t2.userstorystring)from tpipeline_info as t1 inner join(select release_number,pipeline_id,userstorystring from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id");
		datamap.put("jiraPipelineCount",
				" select count (distinct t1.pipeline_name )from tpipeline_info as t1 inner join( select release_number,pipeline_id,userstorystring from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id");
		datamap.put("idpnoofapplications", "Select count(distinct application_name) from appinfo");

		datamap.put("idpnoofusers", "SELECT  count(distinct user_id) FROM public.tuser_info");
		datamap.put("idpnoofpipelines", "SELECT  count(id) FROM public.appinfo");
		datamap.put("idpnoofbuilds",
				"SELECT count(appid) FROM public.buildinfo where (stagename like '%Build%' OR stagename like 'Deploy%' OR stagename like 'Test%')");
		datamap.put("idpnooftests", "SELECT count(*) FROM public.testanalysis");
		datamap.put("passbuilds",
				"SELECT count(appid) FROM public.buildinfo where buildstatus='SUCCESS' AND (stagename like 'Build%' OR stagename like 'Deploy%' OR stagename like 'Test%')");
		datamap.put("unstablebuilds",
				"SELECT count(appid) FROM public.buildinfo where buildstatus='UNSTABLE' AND (stagename like 'Build%' OR stagename like 'Deploy%' OR stagename like 'Test%')");
		datamap.put("failbuilds",
				"SELECT count(appid) FROM public.buildinfo where buildstatus='FAILURE' AND (stagename like 'Build%' OR stagename like 'Deploy%' OR stagename like 'Test%')");
		datamap.put("abortbuilds",
				"SELECT count(appid) FROM public.buildinfo where buildstatus='ABORTED' AND (stagename like 'Build%' OR stagename like 'Deploy%' OR stagename like 'Test%')");
		datamap.put("cidata", "SELECT count(appid) FROM public.buildinfo where (stagename like 'Build%')");
		datamap.put("cidatapass",
				"SELECT count(appid) FROM public.buildinfo where buildstatus='SUCCESS' AND (stagename like 'Build%')");
		datamap.put("cidatafail",
				"SELECT count(appid) FROM public.buildinfo where buildstatus='FAILURE' AND (stagename like 'Build%')");
		datamap.put("cddata", "SELECT count(appid) FROM public.buildinfo where (stagename like 'Deploy%')");
		datamap.put("cddatapass",
				"SELECT count(appid) FROM public.buildinfo where buildstatus='SUCCESS' AND (stagename like 'Deploy%')");
		datamap.put("cddatafail",
				"SELECT count(appid) FROM public.buildinfo where buildstatus='FAILURE' AND (stagename like 'Deploy%')");
		datamap.put("ctdata", "SELECT count(appid) FROM public.buildinfo where (stagename like 'Test%')");
		datamap.put("ctdatapass",
				"SELECT count(appid) FROM public.buildinfo where buildstatus='SUCCESS' AND (stagename like 'Test%')");
		datamap.put("ctdatafail",
				"SELECT count(appid) FROM public.buildinfo where buildstatus='FAILURE' AND (stagename like 'Test%')");
		datamap.put("applicationname",
				"Select count(distinct application_name) from appinfo");
		idplist.add("storystatusboard");
		idplist.add("updatedRelease");
		idplist.add("pipelineExecutedForRelease");
		idplist.add("sparkArtifactsDetails");
		idplist.add("sparkEnvDetailsTypeNew");
		idplist.add("alm1StoryCount");
		idplist.add("alm1PipelineCount");
		idplist.add("jiraPipelineCount");
		idplist.add("jiraStoryCount");
		idplist.add("sparkEnvDetailsTypeNew");
		idplist.add("applicationname");
		idplist.add("idpnoofusers");
		idplist.add("devTopViolatedRules");
		idplist.add("devTestResultInfo");
		
	}


	public List<String> getQuery(String queryName) {
		List<String> query = new ArrayList<>();
		if (datamap.containsKey(queryName)) {
			query.add(datamap.get(queryName));
			if (idplist.contains(queryName)) {
				query.add(IDP);
			} else {
				query.add(DASHBOARD);
			}
		}
		if (query.isEmpty()) {
			query.add(queryName);
			if (queryName.contains("tapplication_info") || queryName.contains("ttrigger_history")
					|| queryName.contains("tpipeline_info") || queryName.contains("icqa"))
				query.add(IDP);
			else
				query.add(DASHBOARD);
		}
		return query;
	}
}
