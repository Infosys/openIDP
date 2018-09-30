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
 * The class DBTableConstants to handle dbqueries
 *
 */
@Component
public class DBTableConstants {
	protected Logger logger = LoggerFactory.getLogger(DBTableConstants.class);
	private static String appCommitsPerEngineer="select lastmodifiedby as Developer,count(*) as commits_per_pipeline from scminfo group by appid,lastmodifiedby having appid in (select id from appinfo where application_name=?);";
	private static String updatedRelease="select count(distinct t2.tfs_workitem)from tpipeline_info as t1 inner join(\r\n" + 
			"select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id;";
	private static String pipelineExecutedForRelease="select count (distinct t1.pipeline_name )from tpipeline_info as t1 inner join( select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id;";
	private static String devTopViolatedRules="select  rulename,count(*) as count,category from codeanalysis  group by severity,rulename,appid,pipelineno,category having appid=(select id from appinfo where application_name=? and pipeline_name=?)and pipelineno=? order by count(*) desc limit 5;";
	private static String devTestResultInfo="select category,status,duration,message from testanalysis where appid=(select id from appinfo where application_name=? and pipeline_name=? ) and pipelineno=?;";
	private static String mtmTestSuites="select distinct trigger_entity::json ->> 'testPlanId' as \"testPlanId\" from ttrigger_history where (trigger_entity::json ->> 'testPlanId' !='null' and trigger_entity::json ->> 'testPlanId' !='' and trigger_entity::json->>'applicationName'=? and trigger_entity::json->>'releaseNumber'=? and trigger_entity::json->>'envSelected'=?);";
	private static String sparkArtifactsDetails="select version,trigger_entity::json ->> 'buildartifactNumber' as \"buildartifactNumber\",trigger_time,trigger_entity::json ->> 'userName' as \"userName\",trigger_entity::json ->> 'applicationName' as \"applicationName\",trigger_entity::json ->> 'pipelineName' as \"pipelineName\" from ttrigger_history where trigger_entity::json ->> 'applicationName'=? and trigger_entity::json->>'envSelected'=? and trigger_entity::json->>'pipelineName'=?;";

	private static String sparkEnvDetailsTypeNew="select trigger_entity::json ->> 'pipelineName' as \"pipelineName\",version,trigger_time from ttrigger_history t1 where (trigger_time=(select max(trigger_time) from ttrigger_history t2 where t1.trigger_entity::json ->> 'pipelineName'=t2.trigger_entity::json ->> 'pipelineName'  ) and trigger_entity::json->>'applicationName'=? and trigger_entity::json->>'envSelected'=?);";
	private static String storystatusboard = "select distinct t2.tfs_workitem as WIT ,t1.pipeline_name,t2.environment as Environment from tpipeline_info as t1 inner join(select release_number,pipeline_id,tfs_workitem,environment from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from  tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id;";
	private static String alm1StoryCount= "select count(distinct t2.tfs_workitem)from tpipeline_info as t1 inner join(select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id";
	private static String alm1PipelineCount=" select count (distinct t1.pipeline_name )from tpipeline_info as t1 inner join( select release_number,pipeline_id,tfs_workitem from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id";
	private static String jiraStoryCount= "select count(distinct t2.userstorystring)from tpipeline_info as t1 inner join(select release_number,pipeline_id,userstorystring from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id";
	private static String jiraPipelineCount=" select count (distinct t1.pipeline_name )from tpipeline_info as t1 inner join( select release_number,pipeline_id,userstorystring from ttrigger_history where release_number=? and pipeline_id in (select pipeline_id from tpipeline_info where application_id in (select application_id from tapplication_info where application_name=? ))) as t2 on t1.pipeline_id=t2.pipeline_id";
	
	private static String idpnoofapplications="Select count(distinct application_name) from appinfo";
	private static String idpnoofusers="SELECT  count(distinct user_id) FROM private.tuser_info";	
	private static String idpnoofpipelines="SELECT  count(id) FROM private.appinfo";
	private static String idpnoofbuilds="SELECT count(appid) FROM private.buildinfo where (stagename like '%Build%' OR stagename like 'Deploy%' OR stagename like 'Test%')";
	private static String idpnooftests="SELECT count(*) FROM private.testanalysis";
	private static String passbuilds="SELECT count(appid) FROM private.buildinfo where buildstatus='SUCCESS' AND (stagename like 'Build%' OR stagename like 'Deploy%' OR stagename like 'Test%')";
	private static String unstablebuilds="SELECT count(appid) FROM private.buildinfo where buildstatus='UNSTABLE' AND (stagename like 'Build%' OR stagename like 'Deploy%' OR stagename like 'Test%')";
	private static String failbuilds="SELECT count(appid) FROM private.buildinfo where buildstatus='FAILURE' AND (stagename like 'Build%' OR stagename like 'Deploy%' OR stagename like 'Test%')";
	private static String abortbuilds="SELECT count(appid) FROM private.buildinfo where buildstatus='ABORTED' AND (stagename like 'Build%' OR stagename like 'Deploy%' OR stagename like 'Test%')";
	private static String cidata="SELECT count(appid) FROM private.buildinfo where (stagename like 'Build%')";
	private static String cidatapass="SELECT count(appid) FROM private.buildinfo where buildstatus='SUCCESS' AND (stagename like 'Build%')";
	private static String cidatafail="SELECT count(appid) FROM private.buildinfo where buildstatus='FAILURE' AND (stagename like 'Build%')";
	private static String cddata="SELECT count(appid) FROM private.buildinfo where (stagename like 'Deploy%')";
	private static String cddatapass="SELECT count(appid) FROM private.buildinfo where buildstatus='SUCCESS' AND (stagename like 'Deploy%')";	
	private static String cddatafail="SELECT count(appid) FROM private.buildinfo where buildstatus='FAILURE' AND (stagename like 'Deploy%')";
	private static String ctdata="SELECT count(appid) FROM private.buildinfo where (stagename like 'Test%')";
	private static String ctdatapass="SELECT count(appid) FROM private.buildinfo where buildstatus='SUCCESS' AND (stagename like 'Test%')";
	private static String ctdatafail="SELECT count(appid) FROM private.buildinfo where buildstatus='FAILURE' AND (stagename like 'Test%')";
	
	private static String dashboard="DASHBOARD";
	private static String idp="IDP";
	
	public List<String> getQuery(String queryName)
	{
		List<String> query = new ArrayList<>();
		switch (queryName) {
		case "appCommitsPerEngineer":
			logger.info("appCommitsPerEngineer");
			query.add(appCommitsPerEngineer);
			query.add(dashboard);
			break;			
		case "storystatusboard":
			logger.info("storystatusboard");
			query.add(storystatusboard);
			query.add(idp);
			break;
		case "updatedRelease":
			logger.info("updatedRelease");
			query.add(updatedRelease);
			query.add(idp);
			break;
		case "pipelineExecutedForRelease":
			logger.info("pipelineExecutedForRelease");
			query.add(pipelineExecutedForRelease);
			query.add(idp);
			break;
		case "devTopViolatedRules":
			logger.info("devTopViolatedRules");
			query.add(devTopViolatedRules);
			query.add(dashboard);
			break;
		case "devTestResultInfo":
			logger.info("devTestResultInfo");
			query.add(devTestResultInfo);
			query.add(dashboard);
			break;
		case "mtmTestSuites":
			logger.info("mtmTestSuites");
			query.add(mtmTestSuites);
			query.add(dashboard);
			break;
		case "sparkArtifactsDetails":
			logger.info("sparkArtifactsDetails");
			query.add(sparkArtifactsDetails);
			query.add(idp);
			break;
		
		case "sparkEnvDetailsTypeNew":
			logger.info("sparkEnvDetailsTypeNew");
			query.add(sparkEnvDetailsTypeNew);
			query.add(idp);
			break;
		case "alm1StoryCount":
			logger.info("alm1StoryCount");
			query.add(alm1StoryCount);
			query.add(idp);
			break;
		case "alm1PipelineCount":
			logger.info("alm1PipelineCount");
			query.add(alm1PipelineCount);
			query.add(idp);
			break;
		case "jiraStoryCount":
			logger.info("jiraStoryCount");
			query.add(jiraStoryCount);
			query.add(idp);
			break;
		case "jiraPipelineCount":
			logger.info("jiraPipelineCount");
			query.add(jiraPipelineCount);
			query.add(idp);
			break;
		case "idpnoofapplications":
			logger.info("idpnoofapplications");
			query.add(idpnoofapplications);
			query.add(dashboard);
			break;
		case "idpnoofusers":
			logger.info("idpnoofusers");
			query.add(idpnoofusers);
			query.add(idp);
			break;
		case "idpnoofpipelines":
			logger.info("idpnoofpipelines");
			query.add(idpnoofpipelines);
			query.add(dashboard);
			break;
		case "idpnoofbuilds":
			logger.info("idpnoofbuilds");
			query.add(idpnoofbuilds);
			query.add(dashboard);
			break;
		case "idpnooftests":
			logger.info("idpnooftests");
			query.add(idpnooftests);
			query.add(dashboard);
			break;
		case "passbuilds":
			logger.info("passbuilds");
			query.add(passbuilds);
			query.add(dashboard);
			break;
		case "unstablebuilds":
			logger.info("unstablebuilds");
			query.add(unstablebuilds);
			query.add(dashboard);
			break;
		case "failbuilds":
			logger.info("failbuilds");
			query.add(failbuilds);
			query.add(dashboard);
			break;
		case "abortbuilds":
			logger.info("abortbuilds");
			query.add(abortbuilds);
			query.add(dashboard);
			break;
		case "cidata":
			logger.info("cidata");
			query.add(cidata);
			query.add(dashboard);
			break;
		case "cidatapass":
			logger.info("cidatapass");
			query.add(cidatapass);
			query.add(dashboard);
			break;
		case "cidatafail":
			logger.info("cidatafail");
			query.add(cidatafail);
			query.add(dashboard);
			break;
		case "cddata":
			logger.info("cddata");
			query.add(cddata);
			query.add(dashboard);
			break;
		case "cddatapass":
			logger.info("cddatapass");
			query.add(cddatapass);
			query.add(dashboard);
			break;
		case "cddatafail":
			logger.info("cddatafail");
			query.add(cddatafail);
			query.add(dashboard);
			break;
		case "ctdata":
			logger.info("ctdata");
			query.add(ctdata);
			query.add(dashboard);
			break;
		case "ctdatapass":
			logger.info("ctdatapass");
			query.add(ctdatapass);
			query.add(dashboard);
			break;
		case "ctdatafail":
			logger.info("ctdatafail");
			query.add(ctdatafail);
			query.add(ctdatafail);
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
