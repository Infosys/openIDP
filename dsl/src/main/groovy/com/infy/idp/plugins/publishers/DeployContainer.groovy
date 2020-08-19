/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.publishers

import com.infy.idp.interfaces.IPluginBase
import org.infy.idp.entities.jobs.IDPJob
import com.infy.idp.utils.AddCredentials

/**
 *
 * This class implements IPLuginBase interface to add options for tomcat and Jboss
 *
 */

class DeployContainer implements IPluginBase {

    def envIndex;
    def stepIndex;

    /*
     * This function implements add method of IPluginBase interface
     */

    @Override
    public void add(context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj));
    }

    /*
    * This function implements performMapping method of IPluginBase interface
    */

    @Override
    public HashMap<String, String> performMapping(IDPJob dataObj) {
        HashMap<String, String> data = new HashMap<String, String>();
        def containerInfo = dataObj.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].deployToContainer;
        if (containerInfo.warPath && containerInfo.warPath != '') {
            data.put("war", containerInfo.warPath);
        } else {
            data.put("war", '**/' + containerInfo.resourceToBeDeployed);
        }
        data.put("contextPath", containerInfo.contextPath);
        data.put("onFailure", false);
        data.put("containerName", containerInfo.containerName);

        def credId = ""
        if (containerInfo.passwordManager == 'on') {
            def str = containerInfo.passwordManagerId
            str = str.replaceAll("\$", "")
            str = str.replaceAll("/", "")
            credId = str
        } else {
            credId = java.util.UUID.randomUUID().toString()
        }

        AddCredentials.run(credId, containerInfo.userName, containerInfo.password)
        data.put("credentials", credId);
        data.put("serverManagerURL", containerInfo.serverManagerURL);
        return data;
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {
        context.with {
            deployPublisher {
                if (data.containsKey('war')) war(data.get('war'));
                if (data.containsKey('contextPath')) contextPath(data.get('contextPath'));
                if (data.containsKey('onFailure')) onFailure(data.get('onFailure'));
                adapters {

                    switch (data.get('containerName')) {
                        case 'tomcat':
                            this.addTomcat(delegate, data)
                            break
                        case 'jBoss':
                            this.addJBoss(delegate, data)
                            break
                    }
                }
            }
        }
    }

    /*
     * This method is used to configure tomcat in deploy job of jenkins
     */

    private void addTomcat(context, data) {
        context.with {
            tomcat7xAdapter {
                url(data.get('serverManagerURL'))
                credentialsId(data.get('credentials'))
            }
        }
    }

    /*
     * This method is used to configure JBoss in deploy job of jenkins
     */

    private void addJBoss(context, data) {
        context.with {
            jBoss7xAdapter {
                url(data.get('serverManagerURL'))
                credentialsId(data.get('credentials'))
            }
        }
    }
}