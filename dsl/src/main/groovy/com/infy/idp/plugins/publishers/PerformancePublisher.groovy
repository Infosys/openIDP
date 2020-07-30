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
 * This class implements IPLuginBase interface to set the performance limits
 *
 */

class PerformancePublisher implements IPluginBase {

    private String pattern = ''

    public String setPattern(pattern) {
        this.pattern = pattern
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
        data.put('errorFailedThreshold', '0')
        data.put('errorUnstableThreshold', '100')
        data.put('errorUnstableResponseTimeThreshold', '')
        data.put('relativeFailedThresholdPositive', '0.0')
        data.put('relativeFailedThresholdNegative', '0.0')
        data.put('relativeUnstableThresholdPositive', '100.0')
        data.put('relativeUnstableThresholdNegative', '80.0')
        data.put('nthBuildNumber', '0')
        data.put('modePerformancePerTestCase', 'true')
        data.put('configType', 'ART')
        data.put('modeOfThreshold', 'true')
        data.put('failBuildIfNoResultFile', '')
        data.put('compareBuildPrevious', 'false')
        data.put('glob', this.pattern)
        data.put('pattern', 'timestamp,elapsed,responseCode,threadName,success,failureMessage,grpThreads,allThreads,URL,Latency,SampleCount,ErrorCount')
        data.put('delimiter', ',')
        data.put('skipFirstLine', 'true')
        data.put('modeThroughput', '')
        data.put('ignoreFailedBuilds', '')
        data.put('ignoreUnstableBuilds', '')
        data.put('modeEvaluation', '')
        data.put('persistConstraintLog', '')
        data.put('sourceDataFiles', '')
        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {
        context.with {
            performanceReport {
                PerformancePublisher.publishPerformance(delegate, data)
            }
        }
    }

    /*
     * This method is used to decide the threshold limit for various build in jenkins job
     */

    private static void publishPerformance(context, data) {
        context.with {
            if (data.containsKey('errorFailedThreshold')) errorFailedThreshold(data.get('errorFailedThreshold').toInteger());
            if (data.containsKey('errorUnstableThreshold')) errorUnstableThreshold(data.get('errorUnstableThreshold').toInteger());
            if (data.containsKey('errorUnstableResponseTimeThreshold')) errorUnstableResponseTimeThreshold(data.get('errorUnstableResponseTimeThreshold'));
            if (data.containsKey('relativeFailedThresholdPositive')) relativeFailedThresholdPositive(data.get('relativeFailedThresholdPositive').toDouble());
            if (data.containsKey('relativeFailedThresholdNegative')) relativeFailedThresholdNegative(data.get('relativeFailedThresholdNegative').toDouble());
            if (data.containsKey('relativeUnstableThresholdPositive')) relativeUnstableThresholdPositive(data.get('relativeUnstableThresholdPositive').toDouble());
            //if(data.containsKey('relativeUnstableThresholdNegative')) relativeUnstableThresholdNegative(data.get('relativeUnstableThresholdNegative').toDouble());
            if (data.containsKey('nthBuildNumber')) nthBuildNumber(data.get('nthBuildNumber').toInteger());
            if (data.containsKey('modePerformancePerTestCase')) modePerformancePerTestCase(data.get('modePerformancePerTestCase').toBoolean());
            if (data.containsKey('configType')) configType(data.get('configType'));
            if (data.containsKey('modeOfThreshold')) modeOfThreshold(data.get('modeOfThreshold').toBoolean());
            if (data.containsKey('failBuildIfNoResultFile')) failBuildIfNoResultFile(data.get('failBuildIfNoResultFile').toBoolean());
            if (data.containsKey('compareBuildPrevious')) compareBuildPrevious(data.get('compareBuildPrevious').toBoolean());
            if (data.containsKey('sourceDataFiles')) sourceDataFiles(data.get('sourceDataFiles'))
            if (data.containsKey('modeThroughput')) modeThroughput(data.get('modeThroughput').toBoolean());
            if (data.containsKey('ignoreFailedBuilds')) ignoreFailedBuilds(data.get('ignoreFailedBuilds').toBoolean());
            if (data.containsKey('ignoreUnstableBuilds')) ignoreUnstableBuilds(data.get('ignoreUnstableBuilds').toBoolean());
            if (data.containsKey('modeEvaluation')) modeEvaluation(data.get('modeEvaluation').toBoolean());
            if (data.containsKey('persistConstraintLog')) persistConstraintLog(data.get('persistConstraintLog').toBoolean());
        }
    }
}
