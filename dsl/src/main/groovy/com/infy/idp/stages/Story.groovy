/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.stages

import com.infy.idp.creators.*
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.tools.NiaIntegStage
import com.infy.idp.utils.Constants
import com.infy.idp.utils.PropertiesAdder

class Story {
    String basepath


    void run(factory, jsonData) {
        storyJobCreation(factory, jsonData)
    }

    void storyJobCreation(factory, jsonData) {
        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

        new BaseJobCreator(

                jobName: basepath + '/' + basepath + Constants.STORYJOBNAMEPOSTFIX,
                jobDescription: ''

        ).create(factory).with {

            //Standard IDP Settings
            configure { it / canRoam(false) }
            concurrentBuild(true)
            this.addProperties(delegate, jsonData)
            this.addWrappers(delegate, jsonData)

            //Optional settings of job
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) customWorkspace('$IDP_WS')
            NiaIntegStage.run(delegate, jsonData, Constants.STROY);
        }
    }

    public void addWrappers(context, jsonData) {

        def toolList = ''

        context.with {

            wrappers {
                (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(delegate, toolList)


            }
        }
    }

    void addProperties(context, jsonData) {

        context.with {

            properties {

                PropertiesAdder.addBuildDiscarder(delegate)
            }

            configure {

                it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' {

                    //Adding tigger id and scm branch
                    PropertiesAdder.addStringParam(delegate, 'Trigger_ID', 'NA', 'This is the Trigger_ID for Story Job')
                    PropertiesAdder.addStringParam(delegate, 'SCM_Branch', 'NA', 'This is SCM branch used for Story Job')

                    //
                    PropertiesAdder.addStringParam(delegate, 'IDP_WS', 'NA', 'CUSTOM Workspace')
                    PropertiesAdder.addStringParam(delegate, 'MODULE_LIST', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'RELEASE_IDENTIFIER', 'NA', '')
                    //Adding pipeline build id
                    PropertiesAdder.addStringParam(delegate, 'PIPELINE_BUILD_ID', 'NA', '')
                    //Adding job build  id
                    PropertiesAdder.addStringParam(delegate, 'JOB_BUILD_ID', 'NA', '')
                    PropertiesAdder.addNodeParam(delegate, 'SLAVE_NODE', ['ldaproleapp_Jmvn1_14Mr1_Slave'], '')
                    PropertiesAdder.addStringParam(delegate, 'BUILD_LABEL', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'ARTIFACT_NAME', 'NA', '')
                    if (jsonData.buildInfo.artifactToStage && jsonData.buildInfo.artifactToStage.artifact) {
                        PropertiesAdder.addStringParam(delegate, 'ARTIFACT_VERSION', 'NA', 'This parameter tells which artifacts has to be downloaded')
                        PropertiesAdder.addStringParam(delegate, 'ARTIFACT_ID', 'NA', 'This will tell in which folder it has to be downloaded')
                        PropertiesAdder.addStringParam(delegate, 'RELEASE_IDENTIFIER', 'NA', 'This will tell in which folder it has to be downloaded')
                        PropertiesAdder.addStringParam(delegate, 'SCM_BRANCH', 'NA', 'This will tell in which folder it has to be downloaded')
                    }
                    if (jsonData.code.technology.toString().equalsIgnoreCase(Constants.SAP)) {
                        PropertiesAdder.addBooleanParam(delegate, 'SCI', 'NA', 'This parameter tells whether SCI is selected or not')
                        PropertiesAdder.addBooleanParam(delegate, 'ABAP', 'NA', 'This parameter tells which ABAP has been selected or not')
                        PropertiesAdder.addStringParam(delegate, 'APP_SERVER', 'NA', 'This parameter tells the application server name')
                        PropertiesAdder.addStringParam(delegate, 'INSTANCE', 'NA', 'This parameter tells the instance name')
                        PropertiesAdder.addStringParam(delegate, 'CLIENT', 'NA', 'This parameter tells the client name')
                        PropertiesAdder.addStringParam(delegate, 'SYSTEM_ID', 'NA', 'This parameter tells the SID')
                        PropertiesAdder.addStringParam(delegate, 'SAP_USERID', 'NA', 'This parameter tells the SAP User Name')
                        PropertiesAdder.addStringParam(delegate, 'SAP_PASSWORD', 'NA', 'This parameter tells the SAP password')
                        PropertiesAdder.addStringParam(delegate, 'LANGUAGE', 'NA', 'This parameter tells which ')
                        PropertiesAdder.addStringParam(delegate, 'TRANS_REQ', 'NA', 'This parameter tells which artifacts has to be downloaded')
                    }
                    if (jsonData.code.technology.toString().equalsIgnoreCase(Constants.SIEBELTECH)) {

                        PropertiesAdder.addStringParam(delegate, 'USER_INFO', 'NA', 'This parameter tells about the user info who triggered the build')
                        PropertiesAdder.addStringParam(delegate, 'TOOLS_USERNAME', jsonData.buildInfo.modules[0].siebelUserName, 'This parameter container tool username')
                        PropertiesAdder.addPasswordParam(delegate, 'TOOLS_PASSWORD', jsonData.buildInfo.modules[0].siebelPassword, 'This parameter contains Tools Password')
                        PropertiesAdder.addStringParam(delegate, 'TOOLS_ROOT', jsonData.buildInfo.modules[0].toolsRoot, 'This parameter contains Siebel tools root path')
                        PropertiesAdder.addStringParam(delegate, 'TOOLS_CFG', jsonData.buildInfo.modules[0].toolsCfg, 'This parameter contains Siebel tools CFG file path')


                    }
                }
            }
        }
    }
}
