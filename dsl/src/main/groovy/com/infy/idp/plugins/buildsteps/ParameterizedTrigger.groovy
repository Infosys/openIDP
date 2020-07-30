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
 * This class implements IPLuginBase interface to configure job for parameterized trigger
 *
 */

class ParameterizedTrigger implements IPluginBase {

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
        data.put("projects", "");
        data.put("buildStepFailureThreshold", "");
        data.put("unstableThreshold", "");
        data.put("failureThreshold", "");

        return data
    }

    /*
    * This function implements addOptions method of IPluginBase interface
    */

    @Override
    public void addOptions(Object context, HashMap<String, String> data) {
        context.with {
            triggerBuilder {
                configs {
                    blockableBuildTriggerConfig {
                        if (data.containsKey("projects")) projects(data.get("projects"));
                        block {
                            if (data.containsKey("buildStepFailureThreshold")) buildStepFailureThreshold(data.get("buildStepFailureThreshold"));
                            if (data.containsKey("unstableThreshold")) unstableThreshold(data.get("unstableThreshold"));
                            if (data.containsKey("failureThreshold")) failureThreshold(data.get("failureThreshold"));
                        }
                    }
                }
            }
        }
    }
}
