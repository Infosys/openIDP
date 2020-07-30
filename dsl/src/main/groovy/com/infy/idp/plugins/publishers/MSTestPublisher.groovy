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
 * This class implements IPLuginBase interface to publish MSTest reports
 *
 */

class MSTestPublisher implements IPluginBase {

    private String pattern = ''

    public void setPattern(String pattern) {
        this.pattern = pattern
    }

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

        data.put('testResultsFile', this.pattern)
        //data.put('buildTime', '0')
        data.put('failOnError', false)
        data.put('keepLongStdio', false)

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            msTestPublisher {

                if (data.containsKey('testResultsFile')) testResultsFile(data.get('testResultsFile'))
                //if(data.containsKey('buildTime')) buildTime(data.get('buildTime'))
                if (data.containsKey('failOnError')) failOnError(data.get('failOnError').toBoolean())
                if (data.containsKey('keepLongStdio')) keepLongStdio(data.get('keepLongStdio').toBoolean())
            }
        }
    }
}
