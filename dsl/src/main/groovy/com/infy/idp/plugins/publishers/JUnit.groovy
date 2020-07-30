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
 * This class implements IPLuginBase interface to publish JUnit resultss
 *
 */

class JUnit implements IPluginBase {

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
        HashMap<String, String> data = new HashMap<String, String>()
        data.put("testResults", "\$JUNITREPORT");
        data.put("allowEmptyResults", "false");
        data.put("healthScaleFactor", "1");
        data.put("keepLongStdio", "false");
        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {
        context.with {
            junit
                    {
                        if (data.containsKey("testResults")) testResults(data.get("testResults"))
                        if (data.containsKey("allowEmptyResults")) allowEmptyResults(data.get("allowEmptyResults").toBoolean())
                        if (data.containsKey("healthScaleFactor")) healthScaleFactor(data.get("healthScaleFactor").toDouble())
                        if (data.containsKey("keepLongStdio")) keepLongStdio(data.get("keepLongStdio").toBoolean())
                        testDataPublishers {

                        }
                    }
        }
    }
}
