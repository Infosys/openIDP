/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.wrappers

import com.infy.idp.interfaces.IPluginBase
import com.infy.idp.utils.Constants
import com.infy.idp.utils.PropReader
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to add Xvfb option in jenkins job
 * This option allows to run parallel build in the jenkins
 *
 */

class Xvfb implements IPluginBase {

    /*
     * This function implements add method of IPluginBase interface
     */

    @Override
    public void add(context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj))
    }

    /*
     * This function implements performMapping method of IPluginBase interface
     */

    @Override
    public HashMap<String, String> performMapping(IDPJob dataObj) {

        HashMap<String, String> data = new HashMap<String, String>()
        data.put('xvfbInstallation', PropReader.readProperty(Constants.CONFIGFN, 'XVFB'))
        data.put('assignedLabels', '')
        data.put('autoDisplayName', false)
        data.put('displayName', '4')
        data.put('debug', true)
        data.put('displayNameOffset', 4)
        data.put('parallelBuild', false)
        data.put('screen', '1024x768x24')
        data.put('shutdownWithBuild', false)
        data.put('timeout', 0)
        return data
    }

    /*
    * This function implements addOptions method of IPluginBase interface
    */

    @Override
    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            if (data.containsKey('xvfbInstallation')) {

                xvfb(data.get('xvfbInstallation')) {

                    if (data.containsKey('assignedLabels')) assignedLabels(data.get('assignedLabels'))
                    if (data.containsKey('autoDisplayName')) autoDisplayName(data.get('autoDisplayName'))
                    //if(data.containsKey('displayName')) displayName(data.get('displayName'))
                    if (data.containsKey('debug')) debug(data.get('debug'))
                    if (data.containsKey('displayNameOffset')) displayNameOffset(data.get('displayNameOffset'))
                    if (data.containsKey('parallelBuild')) parallelBuild(data.get('parallelBuild'))
                    if (data.containsKey('screen')) screen(data.get('screen'))
                    if (data.containsKey('shutdownWithBuild')) shutdownWithBuild(data.get('shutdownWithBuild'))
                    if (data.containsKey('timeout')) timeout(data.get('timeout'))
                }
            }
        }
    }
}
