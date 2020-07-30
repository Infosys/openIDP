/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.publishers

import com.infy.idp.creators.*
import com.infy.idp.interfaces.IPluginBase
import com.infy.idp.utils.*
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to discard old builds
 *
 */

class DiscardBuild implements IPluginBase {

    /*
     * This function implements add method of IPluginBase interface
     */

    public void add(Object context, IDPJob idpJobObj) {
        this.addOptions(context, this.performMapping(idpJobObj));
    }

    /*
     * This function implements performMapping method of IPluginBase interface
     */

    public HashMap<String, String> performMapping(IDPJob idpJobObj) {
        IDPJob job = idpJobObj

        HashMap<String, String> data = new HashMap<String, String>()

        data.put('daysToKeep', '30');
        data.put('intervalDaysToKeep', '30');
        data.put('numToKeep', '30');
        data.put('intervalNumToKeep', '30');

        data.put('discardSuccess', false);
        data.put('discardUnstable', false);
        data.put('discardFailure', false);
        data.put('discardNotBuilt', false);
        data.put('discardAborted', false);

        data.put('minLogFileSize', '-1');
        data.put('maxLogFileSize', '-1');

        data.put('regexp', '');

        return data
    }

    /*
    * This function implements addOptions method of IPluginBase interface
    */

    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            discardBuildPublisher {

                if (data.containsKey('daysToKeep')) daysToKeep(data.get('daysToKeep'))
                if (data.containsKey('intervalDaysToKeep')) intervalDaysToKeep(data.get('intervalDaysToKeep'))
                if (data.containsKey('numToKeep')) numToKeep(data.get('numToKeep'))
                if (data.containsKey('intervalNumToKeep')) intervalNumToKeep(data.get('intervalNumToKeep'))
                if (data.containsKey('discardSuccess')) discardSuccess(data.get('discardSuccess'))
                if (data.containsKey('discardUnstable')) discardUnstable(data.get('discardUnstable'))
                if (data.containsKey('discardFailure')) discardFailure(data.get('discardFailure'))
                if (data.containsKey('discardNotBuilt')) discardNotBuilt(data.get('discardNotBuilt'))
                if (data.containsKey('discardAborted')) discardAborted(data.get('discardAborted'))
                if (data.containsKey('minLogFileSize')) minLogFileSize(data.get('minLogFileSize'))
                if (data.containsKey('maxLogFileSize')) maxLogFileSize(data.get('maxLogFileSize'))
                if (data.containsKey('regexp')) regexp(data.get('regexp'))
            }
        }
    }
}
