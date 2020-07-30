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
        data.put("userName", containerInfo.userName);
        data.put("password", containerInfo.password)
        data.put("serverManagerURL", containerInfo.serverManagerURL);
        return data
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
                            addTomcat(delegate, data)
                            break
                        case 'jBoss':
                            addJBoss(delegate, data)
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
                password(data.get('password'))
                userName(data.get('userName'))
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
                password(data.get('password'))
                userName(data.get('userName'))
            }
        }
    }
}
