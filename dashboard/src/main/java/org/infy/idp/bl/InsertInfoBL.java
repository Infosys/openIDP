/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.bl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.infy.idp.dataapi.BuildInfoDL;
import org.infy.idp.dataapi.CodeAnalysisDL;
import org.infy.idp.dataapi.CodeCoverageDL;
import org.infy.idp.dataapi.Executor;
import org.infy.idp.dataapi.FileNetExportAnalysisDL;
import org.infy.idp.dataapi.FileNetImportAnalysisDL;
import org.infy.idp.dataapi.IDPHistoryInfoDL;
import org.infy.idp.dataapi.PipelineDL;
import org.infy.idp.dataapi.SCMInfoDL;
import org.infy.idp.dataapi.TestAnalysisDL;
import org.infy.idp.entities.BuildDetail;
import org.infy.idp.entities.BuildInfoDetails;
import org.infy.idp.entities.CodeAnalysis;
import org.infy.idp.entities.CoverageDetails;
import org.infy.idp.entities.EntityWrapper;
import org.infy.idp.entities.FileNet;
import org.infy.idp.entities.FileNetExportClassDefinitionType;
import org.infy.idp.entities.FileNetExportDocumentType;
import org.infy.idp.entities.FileNetExportFolderType;
import org.infy.idp.entities.FileNetExportOtherType;
import org.infy.idp.entities.FileNetExportPropertyType;
import org.infy.idp.entities.JsonClass;
import org.infy.idp.entities.TestCaseResult;
import org.infy.idp.jsonschema.SCMInfo;
import org.infy.idp.utils.ConfigurationManager;
import org.infy.idp.utils.jira.DetailsForJira;
import org.infy.idp.utils.vsts.DetailsForVSTSFetcher;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 *The class InsertInfoBL provides method to update all data in postgres DB 
 */
@Component
public class InsertInfoBL {
	@Autowired
	private ConfigurationManager configurationManager;
	
	@Autowired
	private BuildInfoDL buildinfoDL;
	@Autowired
	private PipelineDL appinfo;
	@Autowired
	private IDPHistoryInfoDL historydl;
	@Autowired
	private CodeCoverageDL coveragedl;
	@Autowired
	private CodeAnalysisDL codeAnalysisDL;
	@Autowired
	private TestAnalysisDL testAnalysisdl;
	@Autowired
	private SCMInfoDL scminfodl;
	@Autowired
	private DetailsForVSTSFetcher detailsForVSTSFetcher;
	@Autowired
	private DetailsForJira detailsForJira;
	@Autowired
	private FileNetExportAnalysisDL fileNetExportAnalysisDL;
	@Autowired
	private FileNetImportAnalysisDL fileNetImportAnalysisDL;
	@Autowired
	private Executor executor;

	private static String propertyTemplateException="propertyTemplate exception";
	
	/** The //logger */
	protected Logger logger;

	InsertInfoBL() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	
	/**
	 * Inserts ICQA data in postgres
	 * 
	 * @param appid
	 * @param classname
	 * @param mi
	 * @param cp
	 * @param dp
	 */
	 
	public void dataInfo(int appid,String classname,String mi,String cbo,String cr,String doth,String loc,String locom1,String mnol,String mnop,String noa,String noam,String nocon,String noo,String noom,String norm,String rfc,String cc,String wmpc1,String cp,String dp){
		 try {
			 executor.insertICQAdetails(appid, classname,cbo,cr,doth,loc,locom1,mnol,mnop,noa,noam,nocon,noo,noom,norm,rfc,cc,wmpc1, mi, cp, dp);
	    } catch (SQLException | IOException e) {
	    	logger.error(e.getMessage(),e);
	    }
	}
	
	/**
	 * Method to process pipeline's json object and update relevant data in postgres
	 * 
	 * @param jsonobj
	 * @param applicationName
	 * @param pipeliname
	 * @param pipleineno
	 */
	public void processInfo(JsonClass jsonobj, String applicationName, String pipeliname, int pipleineno) {

		try {

			if(jsonobj.getLog()!=null)
			{

				logger.info(jsonobj.getLog());
			}
			else logger.info("logs not present");
			
			EntityWrapper entitywrapper = createBuildObject(jsonobj);
			
			String stage = (entitywrapper.getBuildinfo().get(0).getStageName()
					.replace(applicationName + "_" + pipeliname + "_", ""));
			
			historydl.insertHistoryInfo(applicationName, pipeliname, stage, Integer.toString(pipleineno),
					entitywrapper.getBuildinfo().get(0).getBuiltStatus());
			
			
			
			int appid = appinfo.insertappdetails(applicationName, pipeliname);
			insertBuildInfo(applicationName, appid, entitywrapper.getBuildinfo().get(0), pipeliname, pipleineno);
			if (entitywrapper.getCoveragedetails() != null && !entitywrapper.getCoveragedetails().isEmpty()) {
				logger.info("Build Stage hence pushing coverage");
				insertCoverageInfo(entitywrapper.getCoveragedetails(), appid,
						Integer.parseInt(entitywrapper.getBuildinfo().get(0).getLastBuildId()), pipleineno);

			}
			//
			if (entitywrapper.getScmInfoList() != null && !entitywrapper.getScmInfoList().isEmpty()) {
				insertSCMInfo(appid, pipleineno, entitywrapper.getScmInfoList());
				logger.info("SCMInfo details populated");
			}
			//
			if (entitywrapper.getCodeAnalysisinfo() != null && !entitywrapper.getCodeAnalysisinfo().isEmpty()) {
				insertCodeAnalysisDetails(appid, pipleineno,
						Integer.parseInt(entitywrapper.getBuildinfo().get(0).getLastBuildId()),
						entitywrapper.getCodeAnalysisinfo());
			}
			if (entitywrapper.getTestAnalysisinfo() != null && !entitywrapper.getTestAnalysisinfo().isEmpty()) {
				insertTestAnalysisInfo(appid, pipleineno,
						Integer.parseInt(entitywrapper.getBuildinfo().get(0).getLastBuildId()),
						entitywrapper.getTestAnalysisinfo());
			}

			logger.info("Data pushing completed!!");
		} catch (Exception e) {
			logger.error("processInfo exception", e);
		}

		logger.info("Json is uploaded into DataBase");

	}

	/**
	 * 
	 * 
	 * @param dir1
	 * @param child
	 * @throws FileNotFoundException
	 * @throws JsonIOException
	 * @throws JsonSyntaxException
	 */
	public EntityWrapper createBuildObject(JsonClass json) {
		EntityWrapper entitywrapper = new EntityWrapper();
		if (json != null) {
			entitywrapper.setBuildinfo(buildDetails(json));
			entitywrapper.setCoveragedetails(coverageDetails(json));
			entitywrapper.setCodeAnalysisinfo(codeAnalysis(json));

			entitywrapper.setTestAnalysisinfo(testAnalysis(json));
			if (json.getScmInfo() != null) {
				entitywrapper.setScmInfoList(scmInfo(json));
			}
			entitywrapper.setCoveragedetails(computeCodeCoverage(json));

		}
		return entitywrapper;
	}

	/**
	 * Returns CoverageDetails data for EntityWrapper
	 * 
	 * @param json
	 * @return
	 */
	public List<CoverageDetails> computeCodeCoverage(JsonClass json) {
		List<CoverageDetails> coverageDetails = new ArrayList<>();
		try {
			if (json.getCoverageDetailsList() != null) {

				for (CoverageDetails i : json.getCoverageDetailsList()) {
					coverageDetails.add(i);
				}
			}
		} catch (Exception e) {
			logger.error("computeCodeCoverage exception", e);
		}

		return coverageDetails;
	}

	/**
	 * Returns SCMInfo data for EntityWrapper
	 * 
	 * @param json
	 * @return
	 */
	public List<SCMInfo> scmInfo(JsonClass json) {
		List<SCMInfo> scminfoList = new ArrayList<>();
		try {
			if (json.getScmInfo() != null) {
				for (SCMInfo scmInfo : json.getScmInfo()) {
					scminfoList.add(scmInfo);
				}
			}
		} catch (Exception e) {
			logger.error("scmInfo exception", e);
		}
		return scminfoList;

	}

	/**
	 * Returns TestCaseResults data for EntityWrapper
	 * 
	 * @param json
	 * @return
	 */
	public List<TestCaseResult> testAnalysis(JsonClass json) {
		List<TestCaseResult> testAnalysisinfo = new ArrayList();
		try {
			if (json.getTestCaseResult() != null) {
				for (TestCaseResult testCase : json.getTestCaseResult()) {
					testAnalysisinfo.add(testCase);
				}
			}
		} catch (Exception e) {
			logger.error("testAnalysis exception", e);
		}
		return testAnalysisinfo;

	}
	/**
	 * Returns FileNetExportPropertyType 
	 * 
	 * @param json
	 * @return
	 */
	public List<FileNetExportPropertyType> parsePropertyTemplate(JsonClass json) {
		List<FileNetExportPropertyType> propertyTemplateList = new ArrayList<>();
		try {
			if (json.getPropetyTemplates() != null) {
				for (FileNetExportPropertyType propertyTemplateObj : json.getPropetyTemplates()) {
					propertyTemplateList.add(propertyTemplateObj);
				}
			}
			
		} catch (Exception e) {
			logger.error(propertyTemplateException, e);
		}
		return propertyTemplateList;

	}
	/**
	 * 
	 * @param json
	 * @return
	 */
	public List<FileNetExportClassDefinitionType> parseClasssDefinition(JsonClass json) {
		List<FileNetExportClassDefinitionType> classDefinationList = new ArrayList<>();
		try {
			if (json.getclassDefinitions() != null) {
				for (FileNetExportClassDefinitionType classDefinationObj : json.getclassDefinitions()) {
					classDefinationList.add(classDefinationObj);
				}
			}
			logger.info("classDefinationList List lengthh" +classDefinationList.size());
			
		} catch (Exception e) {
			logger.error("classDefinationList exception", e);
		}
		return classDefinationList;

	}
	/**
	 * 
	 * @param json
	 * @return
	 */
	public List<FileNetExportFolderType> parsefolder(JsonClass json) {
		List<FileNetExportFolderType> folderList = new ArrayList<>();
		try {
			if (json.getfolders() != null) {
				for (FileNetExportFolderType folderObj : json.getfolders()) {
					folderList.add(folderObj);
				}
			}
			
		} catch (Exception e) {
			logger.error(propertyTemplateException, e);
		}
		return folderList;

	}
	/**
	 * 
	 * @param json
	 * @return
	 */
	public List<FileNetExportDocumentType> parseDocumentType(JsonClass json) {
		List<FileNetExportDocumentType> parseDocumentTypenList = new ArrayList<>();
		try {
			if (json.getdocuments() != null) {
				for (FileNetExportDocumentType classDefinationObj : json.getdocuments()) {
					parseDocumentTypenList.add(classDefinationObj);
				}
			}
			
		} catch (Exception e) {
			logger.error(propertyTemplateException, e);
		}
		return parseDocumentTypenList;

	}
	
	public List<FileNetExportOtherType> parseOthers(JsonClass json) {
		List<FileNetExportOtherType> otherTypeList = new ArrayList<>();
		try {
			if (json.getothers() != null) {
				for (FileNetExportOtherType otherObj : json.getothers()) {
					otherTypeList.add(otherObj);
				}
			}
			
		} catch (Exception e) {
			logger.error(propertyTemplateException, e);
		}
		return otherTypeList;

	}
	/**
	 * Returns CodeAnalysis data for entitywrapper
	 * 
	 * @param json
	 * @return
	 */

	public List<CodeAnalysis> codeAnalysis(JsonClass json) {
		List<CodeAnalysis> codeAnalysisinfo = new ArrayList<>();
		try {
			for (CodeAnalysis codeAnalysis : json.getCodeAnalysis()) {
				codeAnalysisinfo.add(codeAnalysis);
			}
		} catch (Exception e) {
			logger.error("codeAnalysis exception", e);
		}
		return codeAnalysisinfo;
	}

	/**
	 * Returns CoverageDetails data for entitywrapper
	 * 
	 * @param json
	 * @return
	 */
	public List<CoverageDetails> coverageDetails(JsonClass json) {
		List<CoverageDetails> coveragedetails = new ArrayList<>();
		try {

			for (CoverageDetails c : json.getCoverageDetailsList()) {
				coveragedetails.add(c);
			}
		} catch (Exception e) {
			logger.info("coverage Details", e);
		}
		return coveragedetails;
	}

	/**
	 * Returns BuildDetail data for entitywrapper
	 * 
	 * @param json
	 * @return
	 */
	public List<BuildDetail> buildDetails(JsonClass json) {
		List<BuildDetail> buildinfo = new ArrayList<>();
		try {
			if (json.getBuildDetails() != null) {
				for (BuildDetail buildDetail : json.getBuildDetails()) {
					buildinfo.add(buildDetail);
				}
			}
		} catch (Exception e) {
			logger.error("buildDetails exception", e);
		}
		return buildinfo;
	}

	/**
	 * Method to update buildinfo details in database
	 * 
	 * @param appName
	 * @param appid
	 * @param buildDetail
	 * @param pipeline
	 * @param pID
	 */
	public void insertBuildInfo(String appName, int appid, BuildDetail buildDetail, String pipeline, int pID) {
		String lastBuildId = StringUtils.isBlank(buildDetail.getLastBuildId()) ? "0" : buildDetail.getLastBuildId();
		String lastSuccessfulBuildID = StringUtils.isBlank(buildDetail.getLastSuccessfulBuildId()) ? "0"
				: buildDetail.getLastSuccessfulBuildId();
		String lastCompleteBuildId = StringUtils.isBlank(buildDetail.getLastCompletedBuildId()) ? "0"
				: buildDetail.getLastCompletedBuildId();
		String lastUnstableBuildId = StringUtils.isBlank(buildDetail.getLastUnstableBuildId()) ? "0"
				: buildDetail.getLastUnstableBuildId();
		String lastUnsuccessfulBuildId = StringUtils.isBlank(buildDetail.getLastUnsuccessfulBuildId()) ? "0"
				: buildDetail.getLastUnsuccessfulBuildId();
		String lastFailedBuildId = StringUtils.isBlank(buildDetail.getLastFailedBuildId()) ? "0"
				: buildDetail.getLastFailedBuildId();

		String stageName = (buildDetail.getStageName().replace(appName + "_" + pipeline + "_", ""));

		int temp;
		if (StringUtils.isBlank(buildDetail.getScore())) {

			temp = 0;
		} else {
			temp = Integer.parseInt(buildDetail.getScore());
		}
		try {
			
			BuildInfoDetails buildInfoDetails=new BuildInfoDetails();
			buildInfoDetails.setAppid(appid);
			buildInfoDetails.setBuildtime(Double.parseDouble(buildDetail.getBuildTime()));
			buildInfoDetails.setBuildstatus(buildDetail.getBuiltStatus());
			buildInfoDetails.setBuildid(Integer.parseInt(lastBuildId));
			buildInfoDetails.setLastcompletebuildid(Integer.parseInt(lastCompleteBuildId));
			buildInfoDetails.setLastsuccessfulbuildid(Integer.parseInt(lastSuccessfulBuildID));
			buildInfoDetails.setLastunstablebuildid(Integer.parseInt(lastUnstableBuildId));
			buildInfoDetails.setLastunsuccessfulbuildid(Integer.parseInt(lastUnsuccessfulBuildId));
			buildInfoDetails.setLastfailedbuildid(Integer.parseInt(lastFailedBuildId));
			buildInfoDetails.setPipelinebuildno(pID);
			buildInfoDetails.setScore(Double.valueOf(temp));
			buildInfoDetails.setStagename(stageName);
			
			
			buildinfoDL.insertbuilddetails(buildInfoDetails);
		} catch (Exception e) {
			logger.error("Exception in insertbuilddetails", e);

		}

		logger.info("Build deails populated");
	}

	/**
	 * Method to update coverage details in database
	 * 
	 * @param coverageDetailsList
	 * @param appid
	 * @param buildid
	 * @param pid
	 */
	public void insertCoverageInfo(List<CoverageDetails> coverageDetailsList, int appid, int buildid, int pid) {
		try {
			logger.info("Writing Consolidated code coverage");
			coveragedl.insertcoveragedetails(coverageDetailsList, appid, buildid, pid);
			logger.info("Consolidated coverage   populated");
		} catch (Exception e) {
			logger.error("Error in pushing data to Consolidated Coverage", e);
		}

	}
	
	/**
	 * Method to update FileNetExport details in database
	 * 
	 * @param triggerId
	 * @param fileNet
	 */
	public void insertFileNetExportInfo(Integer triggerId, FileNet fileNet) {
		try {
			logger.info("Writing Consolidated filenet export report");
			fileNetExportAnalysisDL.insertFileNetExportAnalysisDetails(triggerId, fileNet);
			logger.info("Filenet export report populated");
		} catch (Exception e) {
			logger.error("Error in pushing data to Filenet export report", e);
		}

	}

	/**
	 * Method to update FileNetImport details in database
	 * 
	 * @param fileNet
	 */
	public void insertFileNetImportInfo(FileNet fileNet) {
		try {
			logger.info("Writing Consolidated filenet export report");
			fileNetImportAnalysisDL.insertFileNetImportAnalysisDetails(fileNet);
			logger.info("Filenet export report populated");
		} catch (Exception e) {
			logger.error("Error in pushing data to Filenet export report", e);
		}

	}
	
	/**
	 * Method to update CodeAnalysis details in database
	 * 
	 * @param appid
	 * @param pipelineno
	 * @param buildid
	 * @param codeAnalysisList
	 */
	public void insertCodeAnalysisDetails(int appid, int pipelineno, int buildid, List<CodeAnalysis> codeAnalysisList) {
		try {
			codeAnalysisDL.insertCodeAnalysisDetails(appid, pipelineno, buildid, codeAnalysisList);

		} catch (Exception e) {
			logger.error("unable to push recored", e);
		}
	}

	/**
	 * Method to update SCM details in database
	 * 
	 * @param appid
	 * @param pipleineno
	 * @param scmInfoList
	 */
	public void insertSCMInfo(int appid, int pipleineno, List<SCMInfo> scmInfoList) {
		try {
			logger.info("Writing SCM info");
			scminfodl.insertSCMInfo(appid, pipleineno, scmInfoList);
			logger.info("SCM info written");
		} catch (Exception e) {
			logger.info("Exception in writing SCM info: ", e);
		}
	}

	/**
	 * Method to update TestAnalysis details in database
	 * 
	 * @param appid
	 * @param pipelineno
	 * @param buildid
	 * @param testcaseList
	 */
	public void insertTestAnalysisInfo(int appid, int pipelineno, int buildid, List<TestCaseResult> testcaseList) {
		try {
			logger.info("Writing test info....");
			testAnalysisdl.insertTestAnalysis(appid, pipelineno, buildid, testcaseList);

			logger.info("Test info written");
		} catch (Exception e) {
			logger.error("Error in pushing data to TestAnalysisInfo", e);
		}
	}
	
	/**
	 * Method to update VSTSJira details in database
	 * 
	 * @param triggerId
	 * @param buildNum
	 */
	public void updateVSTSJiraDb(String triggerId, String buildNum) {
		updateVSTSDb(triggerId,buildNum);
		logger.info("VSTS rocmkingggg");
		updateJira(triggerId);
		
	}
	
	private void updateJira(String triggerIdIn) {
		logger.info("in update jira");
		Integer triggerId  = Integer.parseInt(triggerIdIn);
		
		String resultVal=detailsForJira.getFromDatabase(triggerId);
		if(resultVal.indexOf(':')!=-1)
		{
			logger.info("inside jira if");
			String[] resultSplit=resultVal.split(":");
			if(resultSplit[0]!=null && resultSplit[1]!=null)
			{
				String jenkinsUrl=configurationManager.getJenkinsURL();
				if(jenkinsUrl.indexOf("//")!=-1) {
					logger.info(jenkinsUrl+ "jenkinsUrl");
					String[] splitWith=jenkinsUrl.split(":");
				    String apiFixUrl= splitWith[0]+":"+splitWith[1]+":8889/";
					String url=apiFixUrl+"idprest/trigger/jiraUpdate/"+resultSplit[0]+"/"+resultSplit[1];
					logger.info(url+ "apiurl");
					URL urlCall;
					try {
						logger.info(url+ "apiurl inside tryapii " );
					
						urlCall = new URL(url);
						HttpURLConnection connection = (HttpURLConnection) urlCall.openConnection();
						logger.info("connection" );
						connection.setRequestMethod("GET");
						connection.setDoOutput(false);
						connection.getInputStream();
						
						
					} 
					
					catch (IOException e) {
						
						logger.error(e.getMessage());
						
						logger.error(ExceptionUtils.getFullStackTrace(e));
					}
					

					
				}

			}
			
		}
	}
	
	/**
	 * Method to update VSTS details in database
	 * 
	 * @param triggerId
	 * @param buildNum
	 */

	public void updateVSTSDb(String triggerId, String buildNum){
		try{			
			
				
			int stat = detailsForVSTSFetcher.createVSTSUpdateList(triggerId,buildNum);
			
			if(stat == 1) {
				
				logger.info("Success in CreateVSTS");	
			}
			else {
				logger.info("No VSTS record in list to update");
			}
			
			
			
			
		}catch(Exception e){

			
			logger.error("Error in updating values in VSTS Db", e.getMessage());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
	}
	
	/**
	 * Method to update FileNet details in database
	 * 
	 * @param triggerId
	 * @param jsonObj
	 */
	public void updateFileNetDB(String triggerId, JsonClass jsonObj) {
		
		FileNet fileNet =jsonObj.getFileNet();
		try {

			if(jsonObj.getLog()!=null)
			{
				logger.info(jsonObj.getLog());
			}
			
			fileNetExportAnalysisDL.insertFileNetExportAnalysisDetails(new Integer(triggerId), fileNet);
			fileNetImportAnalysisDL.insertFileNetImportAnalysisDetails( fileNet);
		}catch (Exception e) {
			
			logger.error("Error in updating values in filenet Db", e.getMessage());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	/**
	 * Method to update Application details in database
	 * 
	 * @param data
	 * @throws JSONException
	 */
	public void updateAppDetails(String data) throws JSONException{
		String[] dataArray = data.split(";");
		int appId = Integer.parseInt(dataArray[0]);
		String appInfo = dataArray[1];
		JSONObject applicationInfoJson=new JSONObject(appInfo);
		String appName =  applicationInfoJson.getString("applicationName");
		 try {
			 executor.insertAppDetails(appId, appName, appInfo);
	    } catch (SQLException | IOException e) {
	    	logger.error(e.getMessage(),e);
	    }
		
	}
}
