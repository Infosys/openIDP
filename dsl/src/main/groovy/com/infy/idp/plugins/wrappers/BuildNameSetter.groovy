/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.wrappers

import com.infy.idp.interfaces.IPluginBase
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class has the method set the build name of each job
 *
 */

class BuildNameSetter implements IPluginBase {

    public String template = ""

    public String getTemplate() {
        return template
    }

    public void setTemplate(String template) {
        this.template = template
    }

    /*
    * This function implements add method of IPluginBase interface
    */

    @Override
    public void add(context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj));
    }

    /*
     * This method performs mapping for setting various parameters
     */

    @Override
    public HashMap<String, String> performMapping(IDPJob dataObj) {
        HashMap<String, String> data = new HashMap<String, String>()
        data.put("template", this.template);
        data.put("runAtStart", true);
        data.put("runAtEnd", false);
        return data
    }

    /*
     * This method add options based on the mapping performed
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {
        context.with {
            buildNameSetter {
                BuildNameSetter.addGeneralBuildSettings(delegate, data)
            }
        }
    }

    /*
     * This method is used to add general settings for setting build job name
     */

    private static void addGeneralBuildSettings(context, data) {
        context.with {
            if (data.containsKey("template")) template(data.get("template"));
            if (data.containsKey("runAtStart")) runAtStart(data.get("runAtStart").toBoolean());
            if (data.containsKey("runAtEnd")) runAtEnd(data.get("runAtEnd").toBoolean());


        }
    }


}
