/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.utils.*

/**
 *
 * This class includes the method for adding ReportFetchUtility customtool
 *
 */

class ReportFetchUtility{

	/*
    * This function is used to add the commands to run ReportFetchUtility customtool
    */

	public static void invokeTool(context, jsonData) {
		
		HashMap<String, String> data = performMapping(jsonData)
		def command
		if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
		command = """java -jar "${data.get('CUSTOM_TOOL_JAR')}" ${data.get('JSERVER')} ${data.get('USERNAME')} %JENKINS_PASSWORD% "${data.get('WORKSPACE_LOC')}" "${data.get('JOBNAME')}" "${data.get('APPNAME')}" pmd:pmd.xml cs:checkstyle-result.xml fb:findbugsXml.xml cob:coverage.xml pqm:pqm_report_pmd.txt test:junit.xml acc:report.json if:Fastest.json js:report-jshint-checkstyle.xml tng:testng-results.xml qlta:SummaryReport.xml rbt:output.xml tslint:result.txt ut:junit_ut.xml istanbul:istanbulcoverage.xml jcc:jacocoCoverage.xml put:pythontest.xml ${data.get('JMETER')}  sui:TEST-*.xml set:modReport_ecatt.txt sut:consoleUnit.txt sci:consoleSci.txt fxc:AnalysisResult.xml frt:fortifyxmlreport.xml Junitgo:JUnit_Test.xml alt:lintReport.xml orclanalysis:CodeAnalysisReports.xls oatstest:generalReport.xml trx:*.trx ptr:protractorxml.xml scala:scalastyle-output.xml pst:ParasoftSOATest.xml ser:Repo.csv ses:Static.csv cmx:ScanReport.xml"""
		} else {
		command = """java -jar "${data.get('CUSTOM_TOOL_JAR')}" ${data.get('JSERVER')} ${data.get('USERNAME')} \$JENKINS_PASSWORD  "${data.get('WORKSPACE_LOC')}"  "${data.get('JOBNAME')}" "${data.get('APPNAME')}"  pmd:pmd.xml cs:checkstyle-result.xml fb:findbugsXml.xml cob:coverage.xml pqm:pqm_report_pmd.txt test:junit.xml acc:report.json if:Fastest.json js:report-jshint-checkstyle.xml tng:testng-results.xml qlta:SummaryReport.xml rbt:output.xml tslint:result.txt ut:junit_ut.xml istanbul:istanbulcoverage.xml jcc:jacocoCoverage.xml put:pythontest.xml ${data.get('JMETER')}  sui:TEST-*.xml set:modReport_ecatt.txt sut:consoleUnit.txt sci:consoleSci.txt fxc:AnalysisResult.xml frt:fortifyxmlreport.xml Junitgo:JUnit_Test.xml alt:lintReport.xml orclanalysis:CodeAnalysisReports.xls oatstest:generalReport.xml trx:*.trx ptr:protractorxml.xml scala:scalastyle-output.xml pst:ParasoftSOATest.xml ser:Repo.csv ses:Static.csv cmx:ScanReport.xml"""
		}

		ExecuteCmdPostBuild.invokeCmd(context, command, jsonData.basicInfo.buildServerOS)
	}

	/*
	* This function is used to perform mapping for adding ReportFetchUtility customtool
	*/

	private static Map<String,String> performMapping(jsonData) {
		
		HashMap<String, String> data = new HashMap<String, String>()
		
		if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0 && jsonData.basicInfo.platform==null ) {
			data.put('CUSTOM_TOOL_JAR','%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'REPORTFECTHUTIL'))
			data.put('WORKSPACE_LOC','%IDP_WS%')
			data.put('JOBNAME','%JOB_NAME%')
			data.put('APPNAME','%JOB_BASE_NAME%')
		}
		else if(jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0 && jsonData.basicInfo.platform!=null && jsonData.basicInfo.platform=="ictp"){
			data.put('CUSTOM_TOOL_JAR','%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'REPORTFECTHUTIL_Test'))
			data.put('WORKSPACE_LOC','%IDP_WS%')
			data.put('JOBNAME','%JOB_NAME%')
			data.put('APPNAME','%JOB_BASE_NAME%')
			
		}
		else if(jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) != 0 && jsonData.basicInfo.platform!=null &&jsonData.basicInfo.platform=="ictp"){
			
			data.put('CUSTOM_TOOL_JAR','$IDP_WS/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'REPORTFECTHUTIL_Test'))
			data.put('WORKSPACE_LOC','$IDP_WS')
			data.put('JOBNAME','$JOB_NAME')
			data.put('APPNAME','$JOB_BASE_NAME')
			
		}
		else{
			data.put('CUSTOM_TOOL_JAR','$IDP_WS/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'REPORTFECTHUTIL'))
			data.put('WORKSPACE_LOC','$IDP_WS')
			data.put('JOBNAME','$JOB_NAME')
			data.put('APPNAME','$JOB_BASE_NAME')
		}
		
		data.put('JSERVER',PropReader.readProperty(Constants.CONFIGFN,'JENKINS_URL'))
		data.put('USERNAME',PropReader.readProperty(Constants.CONFIGFN,'JENKINS_USERNAME'))
		data.put('PASSWORD',PropReader.readProperty(Constants.CONFIGFN,'JENKINS_PASSWORD'))

		def jMeterReports = ''
		
		if(jsonData.testInfo && !jsonData.testInfo.toString().equals("{}") && jsonData.testInfo.testEnv && jsonData.testInfo.testEnv.size()!=0) {
			
			def testInfoArr = 	jsonData.testInfo.testEnv

			for(int i=0; i<testInfoArr.size(); i++) {

				def testStepsArr = testInfoArr.getAt(i).testSteps

				if (!testStepsArr || testStepsArr.size()==0)
					continue

				for(int j=0; j<testStepsArr.size(); j++) {
			
					if(testStepsArr.getAt(j).test && testStepsArr.getAt(j).test.testTypeName && testStepsArr.getAt(j).test.testTypeName == Constants.JMETER && testStepsArr.getAt(j).test.testPlan && testStepsArr.getAt(j).test.testPlan != '') {
						def temp = testStepsArr.getAt(j).test.testPlan
						temp = temp.replace('\\', '/')
						jMeterReports += ('jm:' + (temp.lastIndexOf('/')==-1 ? temp : temp.substring(temp.lastIndexOf('/')+1)) + '_result.jtl ')
					}
				}
			}			
		}
		
		data.put('JMETER', jMeterReports)
		return data
	}
}
