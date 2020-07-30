/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.deploy

import com.infy.idp.plugins.publishers.*
import com.infy.idp.utils.*
import org.infy.idp.entities.jobs.IDPJob

//import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
//import com.infy.idp.interfaces.IPluginBase;
/**
 *
 * This class has the method to create the WebSphere Deploy job
 *
 */
class WebSphereDeploy {

    def envIndex;
    def stepIndex;

    /*
     * This method is used to add conditional steps
     */

    public void add(context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj));
    }

    /*
     * This method is used to perform mapping for weblogin deploy
     */

    public HashMap<String, String> performMapping(IDPJob dataObj) {

        def containerInfo = dataObj.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].deployToContainer;


        def earNameList = containerInfo.warPath.split("/")
        def earName = ''
//	def ws = dataObj.basicInfo.buildServerOS == Constants.WINDOWSOS? '%IDP_WS%/' : '$IDP_WS/'
        def ws = dataObj.basicInfo.applicationName + '_' + dataObj.basicInfo.pipelineName;
        if (earNameList.size() > 0) {
            if (earNameList[-1].contains(".")) {
                earName = earNameList[-1].split("\\.")[0]
            }
        }

        HashMap<String, String> data = new HashMap<String, String>()
        data.put("host", containerInfo.ipOrDNS);
        data.put("conntype", "SOAP");
        data.put("port", containerInfo.port);
        data.put("user", containerInfo.userName);
        data.put("password", containerInfo.password)
        data.put("earname", containerInfo.warPath);
        data.put("earFilePath", ws);
        data.put("appname", earName);
        data.put("node", containerInfo.targetNodeName);
        data.put("cell", containerInfo.targetCellName);
        data.put("server", containerInfo.targetServerName);
        //////////////////////////////////////////
        // Need to set property//////////////////
        ////////////////////////////////

        return data
    }

    public void addOptions(context, HashMap<String, String> data) {

        context.with {
            addSteps(delegate, data)
        }

    }

    private static void addSteps(context, HashMap<String, String> data) {

        context.with {
            steps {

                Ant.invokeAnt(delegate, ['deployApp'], 'IDP_SCRIPTS/secureWebsphereRemoteDeploy.xml', data)
            }
        }
    }
}

