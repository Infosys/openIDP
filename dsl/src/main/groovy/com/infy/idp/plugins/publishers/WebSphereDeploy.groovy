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
 * This class implements IPLuginBase interface to configure WebSphere deploy option in jenkins job
 *
 */

class WebSphereDeploy implements IPluginBase {

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

        def containerInfo = dataObj.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].deployToContainer;

        HashMap<String, String> data = new HashMap<String, String>()
        data.put("ipAddress", containerInfo.ipOrDNS);
        data.put("connectorType", "SOAP");
        data.put("port", containerInfo.port);
        data.put("username", "");
        data.put("password", "");
        data.put("clientKeyFile", "");
        data.put("clientTrustFile", "");
        data.put("artifacts", containerInfo.wARPath);
        data.put("node", containerInfo.targetNodeName);
        data.put("cell", containerInfo.targetCellName);
        data.put("server", containerInfo.targetServerName);
        data.put("cluster", "");
        data.put("clientKeyPassword", "");
        data.put("clientTrustPassword", "");
        data.put("earLevel", "6");
        data.put("deploymentTimeout", "");
        data.put("operations", "");
        data.put("precompile", "true");
        data.put("reloading", "true");
        data.put("verbose", "");
        data.put("classLoaderPolicy", "")

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {
        context.with {
            webSphereDeployerPlugin
                    {
                        if (data.containsKey("ipAddress")) ipAddress(data.get("ipAddress"))
                        if (data.containsKey("connectorType")) connectorType(data.get("connectorType"))
                        if (data.containsKey("port")) port(data.get("port").toString())
                        if (data.containsKey("username")) username(data.get("username"))
                        if (data.containsKey("password")) password(data.get("password"))
                        if (data.containsKey("clientKeyFile")) clientKeyFile(data.get("clientKeyFile"))
                        if (data.containsKey("clientTrustFile")) clientTrustFile(data.get("clientTrustFile"))
                        if (data.containsKey("artifacts")) artifacts(data.get("artifacts"))
                        if (data.containsKey("node")) node(data.get("node"))
                        if (data.containsKey("cell")) cell(data.get("cell"))
                        if (data.containsKey("server")) server(data.get("server"))
                        if (data.containsKey("cluster")) cluster(data.get("cluster"))
                        if (data.containsKey("clientKeyPassword")) clientKeyPassword(data.get("clientKeyPassword"))
                        if (data.containsKey("clientTrustPassword")) clientTrustPassword(data.get("clientTrustPassword"))
                        if (data.containsKey("earLevel")) earLevel(data.get("earLevel"))
                        if (data.containsKey("deploymentTimeout")) deploymentTimeout(data.get("deploymentTimeout"))
                        if (data.containsKey("operations")) operations(data.get("operations"))
                        if (data.containsKey("precompile")) precompile(data.get("precompile").toBoolean())
                        if (data.containsKey("reloading")) reloading(data.get("reloading").toBoolean())
                        if (data.containsKey("verbose")) verbose(data.get("verbose").toBoolean())
                        if (data.containsKey("classLoaderPolicy")) classLoaderPolicy(data.get("classLoaderPolicy"))
                    }
        }
    }
}
