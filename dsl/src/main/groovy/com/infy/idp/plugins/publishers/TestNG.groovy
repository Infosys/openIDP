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
 * This class implements IPLuginBase interface to publish TestNG reports
 *
 */

class TestNG implements IPluginBase {

    private String pattern = ''

    public String updatePattern(pattern) {
        this.pattern += (pattern + ',')
    }

    public String setPattern(pattern) {
        this.pattern = pattern
    }

    public String getPattern() {
        return pattern;
    }

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
        data.put('escapeExceptionMessages', true)
        data.put('escapeTestDescription', true)
        data.put('markBuildAsFailureOnFailedConfiguration', true)
        data.put('markBuildAsUnstableOnSkippedTests', false)
        data.put('showFailedBuildsInTrendGraph', true)
        data.put('glob', this.pattern)
        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {
        context.with {
            archiveTestNG(data.get('glob')) {
                TestNG.addTestNG(delegate, data)
            }
        }
    }

    /*
     * This method is used to add TestNG reports option in jenkins job
     */

    private static void addTestNG(context, data) {
        context.with {
            if (data.containsKey('escapeExceptionMessages')) escapeExceptionMessages(data.get('escapeExceptionMessages').toBoolean());
            if (data.containsKey('escapeTestDescription')) escapeTestDescription(data.get('escapeTestDescription').toBoolean());
            if (data.containsKey('markBuildAsFailureOnFailedConfiguration')) markBuildAsFailureOnFailedConfiguration(data.get('markBuildAsFailureOnFailedConfiguration').toBoolean());
            if (data.containsKey('markBuildAsUnstableOnSkippedTests')) markBuildAsUnstableOnSkippedTests(data.get('markBuildAsUnstableOnSkippedTests').toBoolean());
            if (data.containsKey('showFailedBuildsInTrendGraph')) showFailedBuildsInTrendGraph(data.get('showFailedBuildsInTrendGraph').toBoolean());
        }
    }
}
