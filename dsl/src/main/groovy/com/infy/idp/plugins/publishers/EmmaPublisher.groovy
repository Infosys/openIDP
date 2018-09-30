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
 * This class implements IPLuginBase interface to publish reports of Emma
 *
 */

class EmmaPublisher implements IPluginBase {

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

        /*data.put('blockThreshold', 0..100)
        data.put('classThreshold', 0..100)
        data.put('conditionThreshold', 0..100)
        data.put('lineThreshold', 0..100)*/
        data.put('maxBlock', 80)
        data.put('maxClass', 100)
        data.put('maxCondition', 80)
        data.put('maxLine', 80)
        data.put('maxMethod', 70)
        data.put('methodThreshold', 0..100)
        data.put('minBlock', 0)
        data.put('minClass', 0)
        data.put('minCondition', 0)
        data.put('minLine', 0)
        data.put('minMethod', 0)

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            emma('**/Coverage.xml')
                    {
                        if (data.containsKey('blockThreshold')) blockThreshold(data.get('blockThreshold'))
                        if (data.containsKey('classThreshold')) classThreshold(data.get('classThreshold'))
                        if (data.containsKey('conditionThreshold')) conditionThreshold(data.get('conditionThreshold'))
                        if (data.containsKey('lineThreshold')) lineThreshold(data.get('lineThreshold'))
                        if (data.containsKey('maxBlock')) maxBlock(data.get('maxBlock'))
                        if (data.containsKey('maxClass')) maxClass(data.get('maxClass'))
                        if (data.containsKey('maxCondition')) maxCondition(data.get('maxCondition'))
                        if (data.containsKey('maxLine')) maxLine(data.get('maxLine'))
                        if (data.containsKey('maxMethod')) maxMethod(data.get('maxMethod'))
                        if (data.containsKey('methodThreshold')) methodThreshold(data.get('methodThreshold'))
                        if (data.containsKey('minBlock')) minBlock(data.get('minBlock'))
                        if (data.containsKey('minClass')) minClass(data.get('minClass'))
                        if (data.containsKey('minCondition')) minCondition(data.get('minCondition'))
                        if (data.containsKey('minLine')) minLine(data.get('minLine'))
                        if (data.containsKey('minMethod')) minMethod(data.get('minMethod'))
                    }
        }
    }
}
