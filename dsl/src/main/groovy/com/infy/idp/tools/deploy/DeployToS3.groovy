/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.deploy

import com.infy.idp.plugins.buildsteps.*

/**
 *
 * This class has the method to create the deployment job for S3 deployment
 *
 */
class DeployToS3 {

    /*
     * This method is used to add SSH server details
     */

    public static void addSSHServer(context, jsonData, envIndex, stepIndex, basepath) {
        context.with {

            steps {
                def deployJson = jsonData.deployInfo.deployEnv[envIndex].deploySteps[stepIndex]

                //Get git projName
                String gitRepoName = ExtractRepoName(jsonData.code)

                SshBuilder sshBuilder = new SshBuilder();


                def serverName = jsonData.buildInfo.modules.getAt(0).hostName + '_' + jsonData.buildInfo.modules.getAt(0).userName
                //Constructing remoteDir value
                String remoteDir = ''
                if (jsonData.buildInfo.modules.getAt(0).remoteDir) {
                    remoteDir += jsonData.buildInfo.modules.getAt(0).remoteDir + '/'
                }


                String command = 'cd ' + remoteDir + basepath + '-$PIPELINE_BUILD_ID/Uploaded_PyScripts\n'
                command += 'aws s3 cp ' + gitRepoName + ' s3://' + deployJson.s3location +
                        ' --sse AES256 --recursive'

                String key = jsonData.buildInfo.modules.getAt(0).privateKey

                //transferring files and  those files
                sshBuilder.invokeSshBuilder(delegate, serverName, jsonData.buildInfo.modules.getAt(0).userName, '', '', '', command, '', key)

            }
        }

    }

    /**
     * Get repo name from complete git url
     */
    private static String ExtractRepoName(code) {
        //Add code to ger GIT repo name
        def scmSection = code.scm.getAt(0);
        String repoName
        def repoNameList = scmSection.url.split("/")
        //						int arrSize = repoNameList.size()
        if (repoNameList.size() > 0 && repoNameList[-1].endsWith(".git")) {
            repoName = repoNameList[-1].split("\\.")[0]
        }

    }


}
