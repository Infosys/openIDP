/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.wrappers

import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class has the method to configure artifactory download job in jenkin
 *
 */

class ArtifactoryArtifactDownloader {
    private String spacString;

    /*
     * this method adds the configuration for downloading artifacts from JFrog artifactory
     */

    public void add(Object context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj))
    }

    /*
     * This method performs mapping for setting various parameters
     */

    private HashMap<String, String> performMapping(IDPJob dataObj) {

        HashMap<String, String> data = new HashMap<String, String>()


        data.put('artifactoryURL', dataObj.buildInfo.artifactToStage.artifactRepo.repoURL)
        data.put('groupId', dataObj.basicInfo.applicationName)
        data.put('version', '${RELEASE_IDENTIFIER}-${SCM_BRANCH}-${PIPELINE_BUILD_ID}')
        data.put('repository', dataObj.buildInfo.artifactToStage.artifactRepo.repoName)



        data.put('artifactId', dataObj.basicInfo.pipelineName)
        data.put('type', 'zip')
        data.put('classifier', '')

        data.put('username', dataObj.buildInfo.artifactToStage.artifactRepo.repoUsername)
        data.put('password', dataObj.buildInfo.artifactToStage.artifactRepo.repoPassword)



        String artID = '${ARTIFACT_VERSION}'




        if (artID.toString().trim().replaceAll("[\n\r]", "").endsWith("-")) {
            artID = artID + '${PIPELINE_BUILD_ID}'
        }


        this.spacString = "	{\r\n" +
                "     \"files\": [\r\n" +
                "        {\r\n" +
                "          \"pattern\": \"" + dataObj.buildInfo.artifactToStage.artifactRepo.repoName + "/" + dataObj.basicInfo.pipelineName + "/" + artID + "/" + dataObj.basicInfo.pipelineName + ".zip\",\r\n" +
                "          \"flat\": \"true\",\r\n" +
                "          \"recursive\":\"false\",\r\n" +
                "          \"target\":\"toBeUnZipped\\\"\r\n" +
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
                        spec()
                    }

                    downloadSpec {
                        spec(this.spacString)
                    }

                }
            }
        }
    }


}
