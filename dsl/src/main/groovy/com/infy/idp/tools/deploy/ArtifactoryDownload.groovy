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
import com.infy.idp.plugins.wrappers.ArtifactoryArtifactDownloader
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the artifactory download job
 *
 */

class ArtifactoryDownload {
    static String basepath

    /*
     * This method is used to create Jfrog artifactory download job
     */

    public static void artifactoryDownloadJobCreation(context, jsonData, envVar) {

        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        def nugetPackaging = false



        addProperties(context)
        addSteps(context, jsonData, envVar, nugetPackaging)
        addWrappers(context, nugetPackaging, jsonData)
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
     * This method is used to add conditional steps
     */

    private static void addSteps(context, jsonData, envVar, nugetPackaging) {

        context.with {
            concurrentBuild(true)
            steps {
                //String nexusUrl='${NEXUS_URL}'
                //String artID = nexusUrl.split('/')[nexusUrl.split('/').size()-2]
                if ((!jsonData.buildInfo.artifactToStage || !jsonData.buildInfo.artifactToStage.artifact) && !jsonData.code.technology.equalsIgnoreCase('dbDeployDelphix') && !jsonData.code.technology.equalsIgnoreCase('J2EE/Ant'))
                    return
                prepareNexusDownloadAntXml(jsonData, envVar, nugetPackaging)
                if (nugetPackaging) {
                    Ant.invokeAnt(delegate, 'createDir', basepath + '_nexus_download_build.xml')

                } else {
                    // TO_DO Write For Arthifactory

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
     * This method is used to prepare nexus download ant xml
     */

    private static void prepareNexusDownloadAntXml(jsonData, envVar, nugetPackaging) {
        //String artID = '${NEXUS_URL}'.split('/')['${NEXUS_URL}'.split('/').size()-2]
        String fileContent = ReadFile.run('/ant_templates/nexus_download_ant.xml')
        if (!nugetPackaging)
            fileContent = fileContent.replace('$ARTIFACTID', jsonData.basicInfo.pipelineName)

        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_nexus_download_build.xml', fileContent)
    }

    /*
     * This method is used to add required wrappers
     */

    public static void addWrappers(context, nugetPackaging, jsonData) {

        def toolList = 'ANT_HOME'
        if (nugetPackaging)
            toolList = toolList + ',NUGET_HOME'

        def copyPattern = basepath + '_nexus_download_build.xml'

        context.with {

            wrappers {

                (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(delegate, toolList)
                CopyToSlave.invokeCopyToSlave(delegate, copyPattern)
            }
            (new ArtifactoryArtifactDownloader()).add(delegate, jsonData)
        }
    }

}
