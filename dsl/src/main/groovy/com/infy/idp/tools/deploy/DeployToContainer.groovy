/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.deploy

import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.utils.*

/**
 *
 * This class has the method to create the Deploy job for deploying in container
 *
 */

class DeployToContainer {
    public static void add(context, jsonData, envIndex, stepIndex) {

        def deployToContainer = jsonData.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].deployToContainer;
        context.with {
            addWrappers(delegate, deployToContainer.containerName)
//			publishers {
            switch (deployToContainer.containerName) {
                case 'tomcat':
                    publishers {
                        def dcObj = new DeployContainer()
                        dcObj.envIndex = envIndex
                        dcObj.stepIndex = stepIndex
                        dcObj.add(delegate, jsonData)
                    }
                    break
                case 'jBoss':
                    publishers {
                        def dcObj = new DeployContainer()
                        dcObj.envIndex = envIndex
                        dcObj.stepIndex = stepIndex
                        dcObj.add(delegate, jsonData)
                    }
                    break
                case 'webSphere':
                    def webObj = new WebSphereDeploy()
                    webObj.envIndex = envIndex
                    webObj.stepIndex = stepIndex
                    webObj.add(delegate, jsonData)
                    break
                case 'wildFly':

                    break
                case 'weblogic':
                    def webLogic = new WeblogicDeploy()
                    webLogic.envIndex = envIndex
                    webLogic.stepIndex = stepIndex
                    WeblogicDeploy.buildServer = jsonData.basicInfo.buildServerOS;
                    webLogic.add(delegate, jsonData)
                    break
                /*case 'nifi':
                    publishers {
                        PublishOverSSH pbObj = new PublishOverSSH();
                        pbObj.envIndex = envIndex;
                        pbObj.stepIndex = stepIndex;
                        pbObj.add(delegate, jsonData);
                    }
                    break*/
            }
//			}
        }
    }

    /*
     * This method is used to add required wrappers
     */

    public static void addWrappers(context, containerName) {
        def toolList = ''
        def copyPattern = ''

        if (containerName.equalsIgnoreCase('webSphere')) {
            toolList += 'ANT_HOME,WASCLIENT_HOME'
            copyPattern += 'IDP_SCRIPTS/secureWebsphereRemoteDeploy.xml'
        }
        if (containerName.equalsIgnoreCase('weblogic')) {
            toolList += 'ANT_HOME,WL_CLIENT_HOME'
            copyPattern += 'IDP_SCRIPTS/weblogic.xml,IDP_SCRIPTS/checkWeblogicServer.bat'
        }

        context.with {

            wrappers {

                (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(delegate, toolList)

                if (copyPattern != '')
                    CopyToSlave.invokeCopyToSlave(delegate, copyPattern)
            }
        }
    }
}
