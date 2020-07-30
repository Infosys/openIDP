/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.buildsteps

/**
 *
 * This class implements IPLuginBase interface to configure job to run powershell scripts
 *
 */

class Powershell {

    /*
     * This function implements add method of IPluginBase interface
     */

    public void add(context, String cmd) {
        this.addOptions(context, this.performMapping(cmd));
    }

    /*
     * This function implements performMapping method of IPluginBase interface
     */

    public HashMap<String, String> performMapping(String cmd) {
        HashMap<String, String> data = new HashMap<String, String>()
        data.put("powerShell", cmd)
        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    public void addOptions(context, HashMap<String, String> data) {
        context.with {

            if (data.containsKey("powerShell")) powerShell(data.get("powerShell"));

        }
    }
}
