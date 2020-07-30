/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.buildsteps

import com.infy.idp.interfaces.IPluginBase
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to configure job HPRun from file builder
 *
 */

class HPRunFromFileBuilder implements IPluginBase {

    private String testScriptPath;
    private Integer timeout;

    public String getTestScriptPath() {
        return testScriptPath;
    }

    public void setTestScriptPath(String testScriptPath) {
        this.testScriptPath = '$IDP_WS/' + testScriptPath;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = (Integer) timeout;
    }

    /*
     * This function implements add method of IPluginBase interface
     */

    @Override
    public void add(Object context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj));
    }

    /*
    * This function implements performMapping method of IPluginBase interface
    */

    @Override
    public HashMap<String, String> performMapping(IDPJob dataObj) {
        HashMap<String, String> data = new HashMap<String, String>()
        data.put("fsTests", this.testScriptPath)
        data.put("controllerPollingInterval", "30")
        data.put("fsAutActions", "")
        data.put("fsDeviceId", "")
        data.put("fsDevicesMetrics", "")
        data.put("fsExtraApps", "")
        data.put("fsInstrumented", "")
        data.put("fsJobId", "")
        data.put("fsLaunchAppName", "")
        data.put("fsManufacturerAndModel", "")
        data.put("fsOs", "")
        data.put("fsPassword", "")
        data.put("fsTargetLab", "")
        data.put("fsTimeout", this.timeout)
        data.put("fsUserName", "")
        data.put("ignoreErrorStrings", "")
        data.put("mcServerName", "")
        data.put("perScenarioTimeOut", "10")
        data.put("fsUseAuthentication", false)
        data.put("fsProxyAddress", "")
        data.put("fsProxyUserName", "")
        data.put("fsProxyPassword", "")
        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(Object context, HashMap<String, String> data) {
        context.with {
            runFromFileBuilder {
                if (data.containsKey("fsTests"))
                    fsTests(data.get("fsTests"))
                if (data.containsKey("fsTimeout"))
                    fsTimeout(data.get("fsTimeout").toString());

                this.addLoadRunnerSettings(delegate, data);
                this.addMobileCenterOptions(delegate, data);

                useSSL(false)
            }
        }
    }

    /*
     * This method is used to add load runner settings in jenkins job
     */

    private void addLoadRunnerSettings(context, data) {
        context.with {
            if (data.containsKey("controllerPollingInterval")) controllerPollingInterval(data.get("controllerPollingInterval"));
            if (data.containsKey("perScenarioTimeOut")) perScenarioTimeOut(data.get("perScenarioTimeOut"));
            if (data.containsKey("ignoreErrorStrings")) ignoreErrorStrings(data.get("ignoreErrorStrings"));
        }
    }

    /*
     * This method is used to add mobile center option in jenkins job
     */

    private void addMobileCenterOptions(context, data) {
        context.with {
            if (data.containsKey("mcServerName")) mcServerName(data.get("mcServerName"));
            if (data.containsKey("fsUserName")) fsUserName(data.get("fsUserName"));
            if (data.containsKey("fsPassword")) fsPassword(data.get("fsPassword"));
            this.addDeviceInformation(delegate, data);
            this.addTestDefinitions(delegate, data);
            //Not supported for current version
            //if(data.containsKey("fsProxyAddress")) this.addProxyInformation(delegate,data);

        }
    }

    /*
     * This method is used to add device information in jenkins job
     */

    private void addDeviceInformation(context, data) {
        context.with {
            if (data.containsKey("fsOs")) fsOs(data.get("fsOs"));
            if (data.containsKey("fsDeviceId")) fsDeviceId(data.get("fsDeviceId"));
            if (data.containsKey("fsManufacturerAndModel")) fsManufacturerAndModel(data.get("fsManufacturerAndModel"));
            if (data.containsKey("fsTargetLab")) fsTargetLab(data.get("fsTargetLab"));
            if (data.containsKey("fsLaunchAppName")) fsLaunchAppName(data.get("fsLaunchAppName"));
            if (data.containsKey("fsExtraApps")) fsExtraApps(data.get("fsExtraApps"));
        }
    }

    /*
     * This method is used to add test definitions in jenkins job
     */

    private void addTestDefinitions(context, data) {
        context.with {
            if (data.containsKey("fsJobId")) fsJobId(data.get("fsJobId"));
            if (data.containsKey("fsInstrumented")) fsInstrumented(data.get("fsInstrumented"));
            if (data.containsKey("fsAutActions")) fsAutActions(data.get("fsAutActions"));
            if (data.containsKey("fsDevicesMetrics")) fsDevicesMetrics(data.get("fsDevicesMetrics"));
        }
    }


}
