/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.deploy


import com.infy.idp.utils.ExecuteCmd
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the deployment job for IIS deployment
 *
 */

class DeployToIIS {

    /*
     * This method add the steps for deploying artifacts to IIS container
     */

    public static void add(context, jsonData, envIndex, stepIndex) {

        addSteps(context, jsonData, envIndex, stepIndex)
    }

    /*
     * This methos add steps or confiuration in deploy job
     */

    private static void addSteps(context, jsonData, envIndex, stepIndex) {

        def deployStep = jsonData.deployInfo.deployEnv[envIndex].deploySteps[stepIndex]
        def prjctPath = deployStep.deployToContainer.contextPath.replace('\\', '/')
        def prjctName = prjctPath.lastIndexOf('/') == -1 ? prjctPath : prjctPath.substring(prjctPath.lastIndexOf('/') + 1)
        def machineName = deployStep.deployToContainer.targetServerName
        def uName = deployStep.deployToContainer.userName
        def pwd = deployStep.deployToContainer.password

        context.with {

            steps {

                def cmd = '"' + prjctPath + '/obj/Debug/Package/' + prjctName + '.deploy.cmd" /Y /M:' + machineName + ' /U:' + uName + ' /P:' + pwd

                ExecuteCmd.invokeCmd(delegate, cmd, jsonData.basicInfo.buildServerOS)
            }
        }
    }
}
