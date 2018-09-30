/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.wrappers

import org.infy.idp.entities.jobs.IDPJob

//import com.infy.idp.interfaces.IPluginBase
/**
 *
 * This class has the method to configure artifactory upload job in jenkin
 *
 */

class ArtifactoryArtifactUploader {
    private String spacString;
    private String pattern_to_upload = '${IDP_WS}/toBeUploaded/'
    private String target_to_upload = ''

    /*
    * this method adds the configuration for uploading artifacts to JFrog artifactory
    */

    public void add(Object context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj))
    }

    /*
     * This method performs mapping for setting various parameters
     */

    private HashMap<String, String> performMapping(IDPJob dataObj) {

        this.target_to_upload = dataObj.buildInfo.artifactToStage.artifactRepo.repoName + "/" + dataObj.basicInfo.applicationName + "/" + dataObj.basicInfo.pipelineName + "/" + '${RELEASE_IDENTIFIER}-${SCM_BRANCH}-${PIPELINE_BUILD_ID}';
        HashMap<String, String> data = new HashMap<String, String>()


        data.put('artifactoryURL', dataObj.buildInfo.artifactToStage.artifactRepo.repoURL)
        data.put('groupId', dataObj.basicInfo.applicationName)
        data.put('version', '${RELEASE_IDENTIFIER}-${SCM_BRANCH}-${PIPELINE_BUILD_ID}')
        data.put('repository', dataObj.buildInfo.artifactToStage.artifactRepo.repoName)



        data.put('artifactId', dataObj.basicInfo.pipelineName)
        data.put('type', 'zip')
        data.put('classifier', '')
        data.put('file', '${IDP_WS}/toBeUploaded/' + dataObj.basicInfo.pipelineName + '.zip')
        data.put('username', dataObj.buildInfo.artifactToStage.artifactRepo.repoUsername)
        data.put('password', dataObj.buildInfo.artifactToStage.artifactRepo.repoPassword)

        this.spacString = "	{\r\n" +
                "     \"files\": [\r\n" +
                "        {\r\n" +
                "          \"pattern\": \"" + this.pattern_to_upload + dataObj.basicInfo.pipelineName + ".zip\",\r\n" +
                "          \"target\":\"" + dataObj.buildInfo.artifactToStage.artifactRepo.repoName + "/" + dataObj.basicInfo.pipelineName + "/" + data.get('version') + "/" + dataObj.basicInfo.pipelineName + '.zip' + "\"\r\n" +
                "        }\r\n" +
                "    ]\r\n" +
                "}"
        return data
    }

    /*
    * This method add options based on the mapping performed
    */

    private void addOptions(Object context, HashMap<String, String> data) {
        context.with {
            configure {
                def rep
                rep = it / buildWrappers
                rep << 'org.jfrog.hudson.generic.ArtifactoryGenericConfigurator'(plugin: 'artifactory@2.13.1') {


                    details {
                        artifactoryName(data.get('username') + "_" + data.get('repository'))
                        artifactoryUrl(data.get('artifactoryURL'))
                        stagingPlugin()
                        discardOldBuilds(false)
                        discardBuildArtifacts(false)
                    }
                    resolverDetails {
                        artifactoryName(data.get('username') + "_" + data.get('repository'))
                        artifactoryUrl(data.get('artifactoryURL'))
                        stagingPlugin()

                    }



                    deployerCredentialsConfig {
                        credentials {
                        }
                        overridingCredentials(false)
                        ignoreCredentialPluginDisabled(false)
                    }
                    resolverCredentialsConfig {
                        credentials {
                        }
                        overridingCredentials(false)
                        ignoreCredentialPluginDisabled(false)

                    }
                    useSpecs(true)
                    uploadSpec {
                        spec(this.spacString)
                    }
                    downloadSpec {
                        spec()
                    }

                }
            }
        }
    }
    

}
