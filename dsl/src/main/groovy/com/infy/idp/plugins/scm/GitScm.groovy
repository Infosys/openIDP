/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.scm

import com.infy.idp.creators.*
import com.infy.idp.interfaces.IPluginBase
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import org.infy.idp.entities.jobs.IDPJob
import org.infy.idp.entities.jobs.code.Scm

/**
 *
 * This class implements IPLuginBase interface to add GitSCM option in jenkins job
 *
 */

class GitScm implements IPluginBase {

    private indexToMap = -1


    private HashMap<Integer, Scm> scmMap;


    public HashMap<Integer, Scm> getScmMap() {
        return scmMap;
    }

    public void setScmMap(HashMap<Integer, Scm> scmMap) {
        this.scmMap = scmMap;
    }

    /*
     * This function implements add method of IPluginBase interface
     */

    public void add(Object context, IDPJob idpJobObj) {
        def scmArr = null
        //SCM for IDP
        if (idpJobObj.code != null) {
            scmArr = idpJobObj.code.scm
            for (Map.Entry<Integer, Scm> entry : this.scmMap.entrySet()) {
                int key = entry.getKey();
                if (scmArr.getAt(key).type == Constants.GITSCM) {
                    indexToMap = key
                    this.addOptions(context, this.performMapping(idpJobObj));
                }


            }
        } else {
            scmArr = idpJobObj.testAsset.scm
            for (int i = 0; i < scmArr.size(); i++) {
                if (scmArr.getAt(i).type == Constants.GITSCM) {
                    indexToMap = i;
                    this.addOptions(context, this.performMapping(idpJobObj));
                }
            }


        }

    }

    /*
    * This function implements performMapping method of IPluginBase interface
    */

    public HashMap<String, String> performMapping(IDPJob idpJobObj) {

        HashMap<String, String> data = new HashMap<String, String>()
        def scmSection = null
        if (idpJobObj.code != null) {
            scmSection = idpJobObj.code.scm.getAt(indexToMap)
            data.put('branch', '$SCM_BRANCH_' + indexToMap);

        } else {
            scmSection = idpJobObj.testAsset.scm.getAt(indexToMap)
            data.put('branch', scmSection.branch)
        }


        data.put('repoBrowser', scmSection.repositoryBrowser)
        data.put('repoBrowserUrl', scmSection.browserUrl)

        data.put('browserVersion', '1.0')

        if (scmSection.projectName)
            data.put('browserProjectName', scmSection.projectName)

        def credId = java.util.UUID.randomUUID().toString()
        AddCredentials.run(credId, scmSection.userName, scmSection.password)
        data.put('credentials', credId)

        data.put('name', '')
        data.put('refspec', '')
        data.put('url', scmSection.url)

        if (scmSection.url) {
            String repoName
            scmSection.url.replace("\\", "/")
            def repoNameList = scmSection.url.split("/")

            if (repoNameList.size() > 0) {
                if (repoNameList[-1].contains(".")) {
                    repoName = repoNameList[-1].split("\\.")[0]
                }
            }
            data.put('relativeTargetDirectory', repoName)
        }

        if (scmSection.exclude)
            data.put('sparseCheckout', scmSection.exclude)

        data.put('honorRefspec', false)
        data.put('reference', '')
        data.put('shallow', false)
        data.put('timeout', 60)

        return data
    }

    /*
    * This function implements addOptions method of IPluginBase interface
    */

    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            git {

                // Specify the branches to examine for changes and to build.
                if (data.containsKey('branch')) branch(data.get('branch'))

                this.addRemote(delegate, data)
                this.addBrowser(delegate, data)
                this.addExtensions(delegate, data)
            }
        }
    }

    /*
     * This method is used to add details of remote repository
     */

    private void addRemote(context, data) {

        context.with {

            // Adds a remote.
            remote {

                // Sets credentials for authentication with the remote repository.
                if (data.containsKey('credentials')) credentials(data.get('credentials'))

                // Sets a name for the remote.
                if (data.containsKey('name')) name(data.get('name'))

                // Sets a refspec for the remote.
                if (data.containsKey('refspec')) refspec(data.get('refspec'))

                // Sets the remote URL.
                if (data.containsKey('url')) url(data.get('url'))
            }
        }
    }

    /*
     * This method is used to configure repository browsers like github, gitlab, etc
     */

    private void addBrowser(context, data) {

        if (data.containsKey('repoBrowser') && ((data.get('repoBrowser').toString().toLowerCase() == Constants.GITLABREPOBROWSER) || (data.get('repoBrowser').toString().toLowerCase() == Constants.GITBLITREPOBROWSER))) {

            context.with {

                // Adds a repository browser for browsing the details of changes in an external system.
                browser {

                    // Use GitLab as repository browser.
                    if (data.containsKey('repoBrowser') && data.get('repoBrowser').toString().toLowerCase() == Constants.GITLABREPOBROWSER) gitLab(data.get('repoBrowserUrl'), data.get('browserVersion'))

                    // Use Gitblit as repository browser.
                    if (data.containsKey('repoBrowser') && data.get('repoBrowser').toString().toLowerCase() == Constants.GITBLITREPOBROWSER) gitblit(data.get('repoBrowserUrl'), data.get('browserProjectName'))
                }
            }
        }
    }

    /*
     * This method is used to configure options before checkout in jenkins job
     */

    private void addExtensions(context, data) {

        context.with {

            if (data.containsKey('repoBrowser')) {

                // Adds additional behaviors.
                extensions {

                    // Clean up the workspace before every checkout by deleting all untracked files and directories, including those which are specified in .gitignore.
                    cleanBeforeCheckout()

                    // Specifies a local directory (relative to the workspace root) where the Git repository will be checked out.
                    if (data.containsKey('relativeTargetDirectory')) relativeTargetDirectory(data.get('relativeTargetDirectory'))

                    // Specifies behaviors for cloning repositories.
                    cloneOptions {

                        // Honor refspec on initial clone.
                        if (data.containsKey('honorRefspec')) honorRefspec(data.get('honorRefspec'))

                        // Specify a folder containing a repository that will be used by Git as a reference during clone operations.
                        if (data.containsKey('reference')) reference(data.get('reference'))

                        // Perform shallow clone, so that Git will not download history of the project.
                        if (data.containsKey('shallow')) shallow(data.get('shallow'))

                        // Specify a timeout (in minutes) for clone and fetch operations.
                        if (data.containsKey('timeout')) timeout(data.get('timeout'))
                    }

                    if (data.containsKey('sparseCheckout')) {
                        /*sparseCheckoutPaths {
                            sparseCheckoutPaths {

                                def sparseCheckoutList = data.get('sparseCheckout').split(",")
                                for (int i = 0; i < sparseCheckoutList.size(); i++) {
                                    sparseCheckoutPath {
                                        path(sparseCheckoutList[i].trim())
                                    }
                                }
                            }
                        }*/
                        this.addSparseCheckout(delegate, data)
                    }
                }
            }
        }
    }

    /*
     * This method is used to configure Sparse Checkout in jenkins job
     */

    private void addSparseCheckout(context, data) {
        context.with {
            sparseCheckoutPaths {
                sparseCheckoutPaths {

                    def sparseCheckoutList = data.get('sparseCheckout').split(",")
                    for (int i = 0; i < sparseCheckoutList.size(); i++) {
                        sparseCheckoutPath {
                            path(sparseCheckoutList[i].trim())
                        }
                    }
                }
            }
        }
    }
}
