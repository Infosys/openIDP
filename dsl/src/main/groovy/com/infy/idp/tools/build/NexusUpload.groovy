/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.build

import com.infy.idp.customtools.NugetCT
import com.infy.idp.plugins.buildsteps.NexusArtifactUploader
import com.infy.idp.utils.Ant
import com.infy.idp.utils.Constants
import com.infy.idp.utils.fs.ReadFile
import com.infy.idp.utils.fs.WriteFile

/**
 *
 * This class has the method to create the Nexus upload job
 *
 */
class NexusUpload {

    private static String basepath

    /*
     * This method is used to create a free style nexus job
     */

    public static String freeStyleNexus(context, jsonData, envVar,buildID) {
        def nugetPackaging = false
        if (null != jsonData.buildInfo.artifactToStage.nuspecFilePath && '' != jsonData.buildInfo.artifactToStage.nuspecFilePath)
            nugetPackaging = true

        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

        prepareAppScanAntXml(jsonData, envVar, nugetPackaging)
        addWrappers(context, nugetPackaging)

        if (nugetPackaging) {
            context.with {
                steps {
                    Ant.invokeAnt(delegate, 'copyArtifacts', basepath + '_nexus_upload_build.xml')
                }
            }
            NugetCT.uploadArtifacts(context, jsonData.basicInfo.buildServerOS, jsonData.buildInfo.artifactToStage.nuspecFilePath, jsonData.buildInfo.artifactToStage.artifactRepo.repoURL, jsonData.buildInfo.artifactToStage.nexusAPIKey, jsonData.buildInfo.artifactToStage.artifactRepo.repoName)
        } else {
            context.with {
                concurrentBuild(true)
                steps {

                    Ant.invokeAnt(delegate, 'zipFile', basepath + '_nexus_upload_build.xml')
                }

                (new NexusArtifactUploader()).add(context, jsonData)
            }
        }
    }

    /*
     * This method is used to create nexus upload job for maven and ant
     */

    public static String mavenNexus(context, jsonData, envVar) {

        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

        prepareAppScanAntXml(jsonData, envVar)
        addWrappers(context)

        context.with {

            postBuildSteps('UNSTABLE') {

                Ant.invokeAnt(delegate, 'zipFile', basepath + '_nexus_upload_build.xml', '')
            }

            (new NexusArtifactUploader()).add(context, jsonData)
        }
    }

    /*
     * this method is used add the required wrappers
     */

    private static String addWrappers(context, nugetPackaging) {

        context.with {

            configure {

                def tools = it / buildWrappers / 'hudson.plugins.toolenv.ToolEnvBuildWrapper' / vars
                (it / buildWrappers / 'hudson.plugins.toolenv.ToolEnvBuildWrapper' / vars).setValue(addAntTool(tools.value(), nugetPackaging))

                def copyFiles = it / buildWrappers / 'com.michelin.cio.hudson.plugins.copytoslave.CopyToSlaveBuildWrapper' / includes
                (it / buildWrappers / 'com.michelin.cio.hudson.plugins.copytoslave.CopyToSlaveBuildWrapper' / includes).setValue(updateCopyFilePattern(copyFiles.value()))
            }
        }
    }

    /*
     * This method is used to add the Ant customtool
     */

    private static String addAntTool(tools, nugetPackaging) {

        def retVal = tools.join(',')

        if (!retVal.contains('ANT_HOME'))
            retVal += (',ANT_HOME,')

        if (nugetPackaging)
            if (null != retVal)
                retVal = retVal + 'NUGET_HOME,'
            else {
                retVal = "NUGET_HOME,"
            }
        return retVal
    }

    /*
    * This method is used to update the name of file to be copied from jenkins master to slave workspace
    */

    private static String updateCopyFilePattern(pattern) {

        def retVal
        retVal = (pattern.join(',') + ',' + basepath + '_nexus_upload_build.xml,')
        return retVal
    }

    /*
     * This method is used to preapre AppScan ant Xml for Ant
     */

    private static String prepareAppScanAntXml(jsonData, envVar, nugetPackaging) {
        def nuspecFilePath = jsonData.buildInfo.artifactToStage.nuspecFilePath
        String fileContent = ReadFile.run('/ant_templates/nexus_upload_ant.xml')

        if (jsonData.buildInfo.artifactToStage.flattenFileStructure == "on") {
            fileContent = fileContent.replace('$FLATTENINFO', 'true')
        } else {
            fileContent = fileContent.replace('$FLATTENINFO', 'false')
        }

        def artifactPattern = jsonData.buildInfo.artifactToStage.artifact
        if (jsonData.code.technology.toString() == Constants.SALESFORCE) {
            artifactPattern += ', retrieve*.zip'
        }
        fileContent = fileContent.replace('$ARTIFACTSTOUPLOAD', artifactPattern)
        fileContent = fileContent.replace('$ARTIFACTID', jsonData.basicInfo.pipelineName)

        if (nugetPackaging) {
            fileContent = fileContent.replace('$NUSPECFILEPATH', nuspecFilePath)
        }

        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_nexus_upload_build.xml', fileContent)
    }
}
