/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.utils.*
import com.infy.idp.tools.build.*
import com.infy.idp.plugins.wrappers.BuildEnv
/**
 *
 * This class includes the method for adding DevOpsJsonConversion customtool
 *
 */

class DevopsJsonConversion {

    /*
     * This function is used to add the DevOpsJsonConversion customtool for each job
     */

    public static void invokeTool(context, jsonData, stageName, prefixID) {
        HashMap<String, String> data = performMapping(jsonData, stageName, prefixID)
        def command
        def sonarUrl = "";
        def sonarUserName = "";
        def sonarPassword = "";
        if (null != jsonData.buildInfo && null != jsonData.buildInfo.sonarUrl) {
            sonarUrl = jsonData.buildInfo.sonarUrl;
        }
        if (null != jsonData.buildInfo && null != jsonData.buildInfo.sonarUserName) {
            sonarUserName = jsonData.buildInfo.sonarUserName
            sonarPassword = jsonData.buildInfo.sonarPassword

        }

		if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
        command = """java -jar "${data.get('CUSTOM_TOOL_JAR')}" "${data.get('REPORTS_PATH')}" "${
            data.get('APP_NAME')
        }" "${data.get('SONAR_KEY')}" ${data.get('SONAR_URL')} ${data.get('PREFIX_FOR_ID')} "${
            data.get('PIPELINE_NAME')
        }" "${data.get('GROUP_ID')}" "${data.get('GROUP_NAME')}" ${data.get('BUILD_ID')} "${
            data.get('BUILD_TOOL')
        }" "${data.get('CAST_WAR_FILE_NAME')}" "${data.get('DASHBOARD_SERVICE_URL')}" "${
            data.get('DASHBOARD_SERVICE_UNAME')
            }" %DASHBOARD_SERVICE_PWD% "${data.get('JENKINS_HOSTNAME')}" "${
            data.get('JENKINS_USERNAME')
            }" %JENKINS_PASSWORD% """
			}
        else {
			command = """java -jar "${data.get('CUSTOM_TOOL_JAR')}" "${data.get('REPORTS_PATH')}" "${
            data.get('APP_NAME')
        }" "${data.get('SONAR_KEY')}" ${data.get('SONAR_URL')} ${data.get('PREFIX_FOR_ID')} "${
            data.get('PIPELINE_NAME')
        }" "${data.get('GROUP_ID')}" "${data.get('GROUP_NAME')}" ${data.get('BUILD_ID')} "${
            data.get('BUILD_TOOL')
        }" "${data.get('CAST_WAR_FILE_NAME')}" "${data.get('DASHBOARD_SERVICE_URL')}" "${
            data.get('DASHBOARD_SERVICE_UNAME')
            }" \$DASHBOARD_SERVICE_PWD "${data.get('JENKINS_HOSTNAME')}" "${
            data.get('JENKINS_USERNAME')
            }" \$JENKINS_PASSWORD """
			}
        if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
            command += '%ARTIFACT_NAME% ';
        } else {
            command += '$ARTIFACT_NAME ';
        }
        command += "\"" + jsonData.code.technology + "\"" + ' ';
        command += stageName + ' ';
		if (stageName.toLowerCase().equals('rebase') && jsonData.code.technology.toString().equalsIgnoreCase("sapnoncharm")) {
			if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
				command += ' %TARGET_LANDSCAPE% %TRANS_REQ% %TRANSPORT_OBJECT% %TRANSPORT_OBJECT_TYPE%';
			} else {
				command += ' $TARGET_LANDSCAPE $TRANS_REQ $TRANSPORT_OBJECT $TRANSPORT_OBJECT_TYPE';
			}
		}
		
		if (stageName.toLowerCase().equals('deploy') && jsonData.code.technology.toString().equalsIgnoreCase("sapnoncharm")) {

			String databasePort = PropReader.readProperty(Constants.CONFIGFN, 'IDP_DATABASE_PORT')
			String databasePwd = PropReader.readProperty(Constants.CONFIGFN, 'IDP_DATABASE_PASSWORD')

			if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
				command += ' %trigger_id%';
			} else {
				command += ' $trigger_id';
			}

			command += ' ' + databasePort + ' ' + databasePwd;
		}

        ExecuteCmdPostBuild.invokeCmd(context, command, jsonData.basicInfo.buildServerOS)
    }

    /*
     * This function is used to perform mapping specific to DevOpsJsonConversion customtool
     */

    private static Map<String, String> performMapping(jsonData, stageName, prefixID) {

        HashMap<String, String> data = new HashMap<String, String>()

        if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {

            data.put('CUSTOM_TOOL_JAR', '%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, 'DEVOPSJSONCONV'))
            data.put('REPORTS_PATH', '%IDP_WS%/IDP_DevopsJSON_Integration/Jenkins_Reports')

        } else {

            data.put('CUSTOM_TOOL_JAR', '$IDP_WS/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, 'DEVOPSJSONCONV'))
            data.put('REPORTS_PATH', '$IDP_WS/IDP_DevopsJSON_Integration/Jenkins_Reports')
        }

        data.put('APP_NAME', jsonData.basicInfo.applicationName)
        data.put('DASHBOARD_SERVICE_URL', PropReader.readProperty(Constants.CONFIGFN, 'IDP_DASHBOARD_SERVICEURL'))
        data.put('DASHBOARD_SERVICE_UNAME', PropReader.readProperty(Constants.CONFIGFN, 'IDP_DASHBOARD_SERVICEUNAME'))
        data.put('DASHBOARD_SERVICE_PWD', PropReader.readProperty(Constants.CONFIGFN, 'IDP_DASHBOARD_SERVICEPWD'))
        data.put('JENKINS_HOSTNAME', PropReader.readProperty(Constants.CONFIGFN, 'JENKINS_HOSTNAME'))
        data.put('JENKINS_USERNAME', PropReader.readProperty(Constants.CONFIGFN, 'JENKINS_USERNAME'))
        data.put('JENKINS_PASSWORD', PropReader.readProperty(Constants.CONFIGFN, 'JENKINS_PASSWORD'))
        def sonarKey = 'NA'
        def sonarServer = 'NA'
        def sonarPassword = 'NA'
        def sonarUserName = 'NA'

        if (jsonData.buildInfo && jsonData.buildInfo.modules && stageName == Constants.BUILD) {

            def modulesArr = jsonData.buildInfo.modules

            for (mod in modulesArr) {

                if (mod.codeAnalysis && mod.codeAnalysis.contains(Constants.SONAR)) {

                    if (jsonData.code.technology == Constants.JAVAMAVENTECH) {
                        sonarKey = jsonData.basicInfo.applicationName + "_" + jsonData.basicInfo.pipelineName + ":" + jsonData.basicInfo.applicationName + "_" + jsonData.basicInfo.pipelineName
                    } else {
                        sonarKey = jsonData.basicInfo.applicationName + "_" + jsonData.basicInfo.pipelineName
                    }

                    if (null != jsonData.buildInfo && null != jsonData.buildInfo.sonarUrl) {
                        sonarServer = jsonData.buildInfo.sonarUrl;
                    } else {
                        sonarServer = PropReader.readProperty(Constants.CONFIGFN, Constants.SONARURL);
                    }

                    if (null != jsonData.buildInfo && null != jsonData.buildInfo.sonarUserName) {
                        sonarUserName = jsonData.buildInfo.sonarUserName;
                        sonarPassword = jsonData.buildInfo.sonarPassword;
                    } else {
                        sonarUserName = PropReader.readProperty(Constants.CONFIGFN, Constants.SONARUSERNAME);
                        sonarPassword = PropReader.readProperty(Constants.CONFIGFN, Constants.SONARPASSWORD);
                    }
                    break
                }
            }
        }

        data.put('SONAR_KEY', sonarKey)
        data.put('SONAR_URL', sonarServer)
        data.put('SONAR_USER', sonarUserName)
        data.put('SONAR_PASSWORD', sonarPassword)
        data.put('PREFIX_FOR_ID', prefixID)
        data.put('PIPELINE_NAME', jsonData.basicInfo.pipelineName)

        def groupId = 'NA'
        def groupName = 'NA'

        if (jsonData.basicInfo.groupId)
            groupId = jsonData.basicInfo.groupId

        if (jsonData.basicInfo.groupName)
            groupName = jsonData.basicInfo.groupName

        data.put('GROUP_ID', groupId)
        data.put('GROUP_NAME', groupName)

        (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) ? data.put('BUILD_ID', '%PIPELINE_BUILD_ID%') : data.put('BUILD_ID', '$PIPELINE_BUILD_ID')
        (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) ? data.put('JOB_BUILD_ID', '%JOB_BUILD_ID%') : data.put('JOB_BUILD_ID', '$JOB_BUILD_ID')

        def ssoId = 'NA'
        if (jsonData.ssoId)
            ssoId = jsonData.ssoId

        data.put('SSO_ID', jsonData.ssoId)
        def buildTool = 'NA'
        if (jsonData.buildInfo && jsonData.buildInfo.buildtool) {
            buildTool = jsonData.buildInfo.buildtool
        }
        data.put('BUILD_TOOL', buildTool)
	
        return data
    }
}
