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
 * This class implements IPLuginBase interface to publish JMeter reports
 *
 */

class JmeterPublisher implements IPluginBase {

    private jmeterReportFile


    public java.lang.Object getJmeterReportFile() {
        return jmeterReportFile;
    }

    public void setJmeterReportFile(java.lang.Object jmeterReportFile) {
        this.jmeterReportFile = jmeterReportFile;
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
        data.put("errorFailedThreshold", 80);
        data.put("errorUnstableThreshold", 50);
        data.put("sourceDataFiles", "");
        data.put("relativeFailedThresholdPositive", 0);
        data.put("relativeFailedThresholdNegative", 0);
        data.put("relativeUnstableThresholdPositive", 0);
        data.put("relativeUnstableThresholdNegative", 0);
        data.put("nthBuildNumber", 0);
        data.put("modePerformancePerTestCase", "true");
        data.put("configType", "ART");
        data.put("modeOfThreshold", "false")
        data.put("compareBuildPrevious", "true");
        data.put("glob", this.jmeterReportFile);
        data.put("modeThroughput", "true")
        data.put("errorUnstableResponseTimeThreshold", "0");
        data.put("failBuildIfNoResultFile", "true");

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {
        context.with {
            performanceReport
                    {
                        if (data.containsKey("sourceDataFiles")) sourceDataFiles(data.get("sourceDataFiles"))
                        if (data.containsKey("errorFailedThreshold")) errorFailedThreshold(data.get("errorFailedThreshold"))
                        if (data.containsKey("errorUnstableThreshold")) errorUnstableThreshold(data.get("errorUnstableThreshold"))
                        if (data.containsKey("errorUnstableResponseTimeThreshold")) errorUnstableResponseTimeThreshold(data.get("errorUnstableResponseTimeThreshold"))
                        if (data.containsKey("relativeFailedThresholdPositive")) relativeFailedThresholdPositive(data.get("relativeFailedThresholdPositive"))
                        if (data.containsKey("relativeFailedThresholdNegative")) relativeFailedThresholdNegative(data.get("relativeFailedThresholdNegative"))
                        if (data.containsKey("relativeUnstableThresholdPositive")) relativeUnstableThresholdPositive(data.get("relativeUnstableThresholdPositive"))
                        if (data.containsKey("relativeUnstableThresholdNegative")) relativeUnstableThresholdNegative(data.get("relativeUnstableThresholdNegative"))
                        if (data.containsKey("nthBuildNumber")) nthBuildNumber(data.get("nthBuildNumber"))
                        if (data.containsKey("modePerformancePerTestCase")) modePerformancePerTestCase(data.get("modePerformancePerTestCase").toBoolean())
                        if (data.containsKey("configType")) configType(data.get("configType"))
                        if (data.containsKey("modeOfThreshold")) modeOfThreshold(data.get("modeOfThreshold").toBoolean())
                        if (data.containsKey("failBuildIfNoResultFile")) failBuildIfNoResultFile(data.get("failBuildIfNoResultFile").toBoolean())
                        if (data.containsKey("compareBuildPrevious")) compareBuildPrevious(data.get("compareBuildPrevious").toBoolean())
                        parsers {
                            jMeterParser {
                                if (data.containsKey("glob")) glob(data.get("glob"))
                            }
                        }
                        if (data.containsKey("modeThroughput")) modeThroughput(data.get("modeThroughput").toBoolean())
                        if (data.containsKey("ignoreFailedBuilds")) ignoreFailedBuilds(data.get("ignoreFailedBuilds"))
                        if (data.containsKey("ignoreUnstableBuilds")) ignoreUnstableBuilds(data.get("ignoreUnstableBuilds"))
                        if (data.containsKey("modeEvaluation")) modeEvaluation(data.get("modeEvaluation"))
                        if (data.containsKey("persistConstraintLog")) persistConstraintLog(data.get("persistConstraintLog"))

                    }
        }
    }
}
