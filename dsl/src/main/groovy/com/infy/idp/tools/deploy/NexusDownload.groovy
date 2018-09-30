/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.deploy

import com.infy.idp.creators.*
import com.infy.idp.customtools.NugetCT
import com.infy.idp.plugins.buildsteps.HttpRequest
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the Nexus download job
 *
 */

class NexusDownload {

    static String basepath

    /*
     * This method is used to create a free style nexus download job
     */

    public static void nexusDownloadJobCreation(context, jsonData, envVar) {

        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        def nugetPackaging = false

        if (null != jsonData.buildInfo.artifactToStage.nuspecFilePath && '' != jsonData.buildInfo.artifactToStage.nuspecFilePath) {
            nugetPackaging = true
        }
        addProperties(context)
        addSteps(context, jsonData, envVar, nugetPackaging)
        addWrappers(context, nugetPackaging)
    }

    /*
   * This method is used to add properties for parameterized job
   */

    private static void addProperties(context) {

        context.with {

            properties {

                PropertiesAdder.addGitLabConnection(delegate)
                PropertiesAdder.addRebuild(delegate)
                PropertiesAdder.addThrottleJobProperty(delegate)
            }
            configure {

                it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' {

                    PropertiesAdder.addStringParam(delegate, 'ARTIFACT_VERSION', 'NA', 'This parameter tells which artifacts has to be downloaded')
                    PropertiesAdder.addStringParam(delegate, 'ARTIFACT_ID', 'NA', 'This will tell in which folder it has to be downloaded')
                    PropertiesAdder.addStringParam(delegate, 'PIPELINE_NAME', basepath, 'This will tell what is the pipeline name')
                    PropertiesAdder.addStringParam(delegate, 'PIPELINE_BUILD_ID', 'NA', 'This will tell what is the pipeline name')
                    PropertiesAdder.addStringParam(delegate, 'IDP_WS', 'NA', 'CUSTOM Workspace')
                    PropertiesAdder.addStringParam(delegate, 'RELEASE_NO', 'NA', 'This parameter tells what is the release number')
                    PropertiesAdder.addNodeParam(delegate, 'SLAVE_NODE', ['ldaproleapp_Jmvn1_14Mr1_Slave'], '')
                    PropertiesAdder.addStringParam(delegate, 'NEXUS_URL', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'BRANCH_NEXUS', 'default', '')
                }
            }
        }
    }

    /*
     * This methos add steps or confiuration in nexus download job
     */

    private static void addSteps(context, jsonData, envVar, nugetPackaging) {

        context.with {
            concurrentBuild(true)
            steps {


                def credId = java.util.UUID.randomUUID().toString()
                AddCredentials.run(credId, jsonData.buildInfo.artifactToStage.artifactRepo.repoUsername, jsonData.buildInfo.artifactToStage.artifactRepo.repoPassword)
                if ((!jsonData.buildInfo.artifactToStage || !jsonData.buildInfo.artifactToStage.artifact) && !jsonData.code.technology.equalsIgnoreCase('dbDeployDelphix') && !jsonData.code.technology.equalsIgnoreCase('J2EE/Ant'))
                    return
                prepareNexusDownloadAntXml(jsonData, envVar, nugetPackaging)
                if (nugetPackaging) {
                    Ant.invokeAnt(delegate, 'createDir', basepath + '_nexus_download_build.xml')

                } else {
                    HttpRequest httpRequesst1 = new HttpRequest()
                    httpRequesst1.setRequestBody("")
                    httpRequesst1.setUrl('${NEXUS_URL}')
                    httpRequesst1.setOutputFile('${IDP_WS}/toBeUnZipped/' + jsonData.basicInfo.pipelineName + '.zip')
                    httpRequesst1.setHttpMode("GET")
                    httpRequesst1.setContentType("APPLICATION_ZIP")
                    httpRequesst1.setAcceptType("APPLICATION_ZIP")
                    httpRequesst1.setAuthentication(credId)
                    httpRequesst1.add(delegate, jsonData)

                    //(new ArtifactResolver()).add(delegate, jsonData)
                    //Ant.invokeAnt(delegate, 'unzipFile', basepath + '_nexus_download_build.xml')
                    Ant.invokeAnt(delegate, 'unzipFile', basepath + '_nexus_download_build.xml')
                }
            }
        }
        if (nugetPackaging) {
            NugetCT.downloadArtifacts(context, jsonData.basicInfo.buildServerOS, jsonData.buildInfo.artifactToStage.artifactRepo.repoURL, jsonData.buildInfo.artifactToStage.artifactRepo.repoName, jsonData.basicInfo.applicationName)
        }
    }

    /*
     * This method is used to preapre nexus download Xml
     */

    private static void prepareNexusDownloadAntXml(jsonData, envVar, nugetPackaging) {

        String fileContent = ReadFile.run('/ant_templates/nexus_download_ant.xml')
        if (!nugetPackaging)
            fileContent = fileContent.replace('$ARTIFACTID', jsonData.basicInfo.pipelineName)

        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_nexus_download_build.xml', fileContent)
    }

    /*
     * this method is used add the required wrappers
     */

    public static void addWrappers(context, nugetPackaging) {

        def toolList = 'ANT_HOME'
        if (nugetPackaging)
            toolList = toolList + ',NUGET_HOME'

        def copyPattern = basepath + '_nexus_download_build.xml'

        context.with {

            wrappers {

                (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(delegate, toolList)
                CopyToSlave.invokeCopyToSlave(delegate, copyPattern)
            }
        }
    }
}
