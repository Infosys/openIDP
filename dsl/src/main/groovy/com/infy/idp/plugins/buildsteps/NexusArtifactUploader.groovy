/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.buildsteps

import com.infy.idp.utils.AddCredentials
import com.infy.idp.utils.Constants
import com.infy.idp.utils.PropReader
import org.infy.idp.entities.jobs.IDPJob

//import com.infy.idp.interfaces.IPluginBase
/**
 *
 * This class implements IPLuginBase interface to configure job for nexus upload
 *
 */

class NexusArtifactUploader {

    /*
     * This function implements add method of IPluginBase interface
     */

    public void add(Object context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj))
    }

    /*
    * This function implements performMapping method of IPluginBase interface
    */

    private HashMap<String, String> performMapping(IDPJob dataObj) {

        HashMap<String, String> data = new HashMap<String, String>()

        data.put('nexusVersion', PropReader.readProperty(Constants.DSLPROPSFN, 'NEXUSVERSION'))
        data.put('protocol', PropReader.readProperty(Constants.DSLPROPSFN, 'NEXUSURL').toString().split('://')[0])
        data.put('nexusUrl', dataObj.buildInfo.artifactToStage.artifactRepo.repoURL)
        data.put('groupId', dataObj.basicInfo.applicationName)
        data.put('version', '${RELEASE_IDENTIFIER}-${SCM_BRANCH}-${PIPELINE_BUILD_ID}')
        data.put('repository', dataObj.buildInfo.artifactToStage.artifactRepo.repoName)

        def credId = java.util.UUID.randomUUID().toString()
        AddCredentials.run(credId, dataObj.buildInfo.artifactToStage.artifactRepo.repoUsername, dataObj.buildInfo.artifactToStage.artifactRepo.repoPassword)
        data.put('credentialsId', credId)

        data.put('artifactId', dataObj.basicInfo.pipelineName)
        data.put('type', 'zip')
        data.put('classifier', '')
        data.put('file', '${IDP_WS}/toBeUploaded/' + dataObj.basicInfo.pipelineName + '.zip')

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    private void addOptions(Object context, HashMap<String, String> data) {
        context.with {
            configure {

                def rep

                if (it.name() == 'project') {
                    rep = it / builders
                } else {
                    rep = it / postbuilders
                }

                rep << 'sp.sd.nexusartifactuploader.NexusArtifactUploader'(plugin: 'nexus-artifact-uploader@2.9') {
                    if (data.containsKey('nexusVersion')) nexusVersion(data.get('nexusVersion'))
                    if (data.containsKey('protocol')) protocol(data.get('protocol'))
                    if (data.containsKey('nexusUrl')) nexusUrl(data.get('nexusUrl'))
                    if (data.containsKey('groupId')) groupId(data.get('groupId'))
                    if (data.containsKey('version')) version(data.get('version'))
                    if (data.containsKey('repository')) repository(data.get('repository'))
                    if (data.containsKey('credentialsId')) credentialsId(data.get('credentialsId'))
                    artifacts {
                        this.addArtifcat(delegate, data)
                    }
                }
            }
        }
    }

    /*
     * This method is used to configure job for uploading artifacts on nexus
     */

    private void addArtifcat(context, data) {
        context.with {
            'sp.sd.nexusartifactuploader.Artifact' {
                if (data.containsKey('artifactId')) artifactId(data.get('artifactId'))
                if (data.containsKey('type')) type(data.get('type'))
                if (data.containsKey('classifier')) classifier(data.get('classifier'))
                if (data.containsKey('file')) file(data.get('file'))
            }
        }
    }
}
