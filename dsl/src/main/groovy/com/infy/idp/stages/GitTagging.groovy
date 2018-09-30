/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.stages

import com.infy.idp.creators.*
import com.infy.idp.plugins.publishers.GitPublisher
import com.infy.idp.plugins.scm.*
import com.infy.idp.plugins.wrappers.BuildNameSetter
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import com.infy.idp.utils.Constants

/**
 *
 * This class has the method to create the gittag job
 *
 */

class GitTagging {

    String basepath

    /*
     * This method is used to call other child jobs to create a GitTag job with all the configuration
     */

    void run(factory, jsonData) {
        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        baseJobCreation(factory, jsonData)
    }

    /*
     * this method is used to create the GitTag job in jenkins
     */

    private void baseJobCreation(factory, jsonData) {

        String basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

        new BaseJobCreator(

                jobName: basepath + '/' + basepath + Constants.GITTAGJOBNAMEPOSTFIX,
                jobDescription: ''

        ).create(factory).with {

            //Standard IDP Settings
            configure { it / canRoam(false) }
            concurrentBuild(true)
            if (jsonData.code.scm.getAt(0).proxy && jsonData.code.scm.getAt(0).proxy != '') {
                def proxy = jsonData.code.scm.getAt(0).proxy
                def port = jsonData.code.scm.getAt(0).proxyPort
                def abc = proxy + ":" + port
                environmentVariables {
                    propertiesFile('$IDP_WS/CustomJobParm.properties')
                    env('http_proxy', abc)
                    env('https_proxy', abc)

                    keepBuildVariables(true)
                }
            } else {

                environmentVariables {
                    propertiesFile('$IDP_WS/CustomJobParm.properties')

                    keepBuildVariables(true)
                }
            }
            this.addScm(delegate, jsonData)
            this.addProperties(delegate)
            this.addPublishers(delegate, jsonData)
            this.addWrappers(delegate, jsonData)
            // DiscardBuild.invokeDiscardBuildPublisher(delegate)
            //addSteps(delegate, jsonData)

            //Optional settings of job
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) customWorkspace('$IDP_WS')
            //NiaIntegStage.run(delegate,jsonData, Constants.SCM);
        }
    }

    /*
     * This method is used to extract checkout name
     */

    private String extractCheckoutFolder(url) {

        String repoNameWithGit = url.lastIndexOf('/') == -1 ? url : url.substring(url.lastIndexOf('/') + 1)
        return repoNameWithGit.endsWith('.git') ? repoNameWithGit.substring(0, repoNameWithGit.length() - '.git'.length()) : repoNameWithGit
    }

    /*
     * This method is used to add the required wrappers
     */

    public void addWrappers(context, jsonData) {
        context.with {
            wrappers {
                BuildNameSetter buildname1 = new BuildNameSetter()
                buildname1.setTemplate('${BUILD_LABEL}' + '_' + '${build_number}')
                buildname1.add(delegate, jsonData)
            }
        }
    }

    /*
     * This method is used to add SCM option to push the code in the repository
     */

    private void addScm(context, jsonData) {

        context.with {

            def scmArr = jsonData.code.scm

            for (def scmObj : scmArr) {
                if (scmObj.type == Constants.GITSCM) {
                    this.addGitSCM(delegate, scmObj)
                    break
                }
            }
        }
    }

    /*
     * This method is used to configure addGitSCM option
     */

    private void addGitSCM(context, scmObj) {
        context.with {
            scm {

                git {

                    branch(scmObj.branch)

                    remote {

                        def credId = java.util.UUID.randomUUID().toString()
                        AddCredentials.run(credId, scmObj.userName, scmObj.password)

                        // Sets credentials for authentication with the remote repository.
                        credentials(credId)

                        // Sets a name for the remote.
                        name(extractCheckoutFolder(scmObj.url))

                        // Sets the remote URL.
                        url(scmObj.url)
                    }

                    browser {

                        // Use GitLab as repository browser.
                        if (scmObj.repositoryBrowser.toLowerCase() == Constants.GITLABREPOBROWSER) gitLab(scmObj.browserUrl, '1.0')

                        // Use Gitblit as repository browser.
                        if (scmObj.repositoryBrowser.toLowerCase() == Constants.GITBLITREPOBROWSER) gitblit(scmObj.browserUrl, scmObj.projectName)
                    }

                    extensions {

                        // Specifies a local directory (relative to the workspace root) where the Git repository will be checked out.
                        relativeTargetDirectory(extractCheckoutFolder(scmObj.url))

                        // Specifies behaviors for cloning repositories.
                        cloneOptions {

                            // Honor refspec on initial clone.
                            honorRefspec(false)

                            // Specify a folder containing a repository that will be used by Git as a reference during clone operations.
                            reference('')

                            // Perform shallow clone, so that Git will not download history of the project.
                            shallow(false)

                            // Specify a timeout (in minutes) for clone and fetch operations.
                            timeout(60)
                        }

                        if (scmObj.exclude) {
                            this.addGitSparseCheckout(delegate, scmObj)
                        }
                    }
                }
            }
        }
    }

    /*
     * This method is used to add gitSparsecheckout option in jenkins job
     */

    private void addGitSparseCheckout(context, scmObj) {
        context.with {

            sparseCheckoutPaths {

                sparseCheckoutPaths {

                    def sparseCheckoutList = scmObj.exclude.split(",")

                    for (int i = 0; i < sparseCheckoutList.size(); i++) {

                        sparseCheckoutPath {

                            path(sparseCheckoutList[i].trim())
                        }
                    }
                }
            }
        }
    }

    /*
     * this method is used to add the required publishers
     */

    public void addPublishers(context, jsonData) {

        context.with {

            publishers {

                def scmArr = jsonData.code.scm

                for (def scmObj : scmArr) {

                    if (scmObj.type == Constants.GITSCM) {

                        GitPublisher gitPublisher = new GitPublisher()
                        gitPublisher.setTargetRepo(extractCheckoutFolder(scmObj.url.toString()))
                        //String pipId = (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) ? '%PIPELINE_BUILD_ID%' : '$PIPELINE_BUILD_ID'
                        gitPublisher.setTag(basepath + '_' + '${PIPELINE_BUILD_ID}')
                        gitPublisher.add(delegate, jsonData)
                        break
                    }
                }
            }
        }
    }

    /*
     * this method is used to add string or password parameters in jenkins job
     */

    private void addProperties(context) {

        context.with {

            properties {

                PropertiesAdder.addGitLabConnection(delegate)
                PropertiesAdder.addRebuild(delegate)
                PropertiesAdder.addThrottleJobProperty(delegate)
            }

            configure {

                it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' {
                    PropertiesAdder.addStringParam(delegate, 'IDP_WS', 'NA', 'CUSTOM Workspace')
                    //Adding pipeline build id
                    PropertiesAdder.addStringParam(delegate, 'PIPELINE_BUILD_ID', 'NA', '')
                    //Adding job build  id
                    PropertiesAdder.addStringParam(delegate, 'JOB_BUILD_ID', 'NA', '')
                    PropertiesAdder.addNodeParam(delegate, 'SLAVE_NODE', ['ldaproleapp_Jmvn1_14Mr1_Slave'], '')
                    PropertiesAdder.addStringParam(delegate, 'BUILD_LABEL', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'ARTIFACT_NAME', 'NA', '')
                }
            }
        }
    }
}
