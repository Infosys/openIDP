/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.stages

import com.infy.idp.creators.*
import com.infy.idp.plugins.scm.*
import com.infy.idp.tools.*
import com.infy.idp.tools.deploy.NexusDownload
import com.infy.idp.utils.*
import com.infy.idp.utils.Constants
import javaposse.jobdsl.dsl.DslFactory


/**
 *
 * This class has the method to create the artifact download job in jenkins
 *
 */

class ArtifactDownload {

    String basepath

    /*
     * This method is used to call other child method in order to create and configure artifact download job
     */

    void run(DslFactory factory, jsonData, envVar) {

        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        if (jsonData.buildInfo) {
            baseJobCreation(factory, jsonData, envVar)
        }
    }

    /*
     * this method is used to create a base job for artifact download
     */

    private void baseJobCreation(factory, jsonData, envVar) {

        new BaseJobCreator(

                jobName: basepath + '/' + basepath + Constants.ARTIFACTDOWNLOADJOBNAMEPOSTFIX,
                jobDescription: ''

        ).create(factory).with {

            //Standard IDP Settings
            configure { it / canRoam(false) }

            environmentVariables {
                propertiesFile('$IDP_WS/CustomJobParm.properties')
                keepBuildVariables(true)
            }
            concurrentBuild(true)

            //Optional settings of job
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) customWorkspace('$IDP_WS')

            invokeArtifactDownloader(delegate, jsonData, envVar)
        }
    }

    /*
     * This method call the nexus download job creation method
     */

    private void invokeArtifactDownloader(context, jsonData, envVar) {

        NexusDownload.nexusDownloadJobCreation(context, jsonData, envVar)
    }
}
