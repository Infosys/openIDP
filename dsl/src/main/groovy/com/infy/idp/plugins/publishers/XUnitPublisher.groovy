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
 * This class implements IPLuginBase interface to publish EUnit reports
 *
 */

class XUnitPublisher implements IPluginBase {


    private String pattern = ''
    private Boolean skipNoTestFiles = false
    private Boolean failIfNotNew = true
    private Boolean deleteOutputFiles = true
    private Boolean stopProcessingIfError = true
    private String unstableThresholdFailed = ''
    private String unstableNewThresholdFailed = ''
    private String failureThresholdFailed = ''
    private String failureNewThresholdFailed = ''
    private String unstableThresholdSkipped = ''
    private String unstableNewThresholdSkipped = ''
    private String failureThresholdSkipped = ''
    private String failureNewThresholdSkipped = ''
    private String thresholdMode = '1'
    private String testTimeMargin = '3000'


    public Boolean getSkipNoTestFiles() {
        return skipNoTestFiles;
    }

    public void setSkipNoTestFiles(Boolean skipNoTestFiles) {
        this.skipNoTestFiles = skipNoTestFiles
    }

    public Boolean getFailIfNotNew() {
        return failIfNotNew;
    }

    public void setFailIfNotNew(Boolean failIfNotNew) {
        this.failIfNotNew = failIfNotNew;
    }

    public Boolean getDeleteOutputFiles() {
        return deleteOutputFiles;
    }

    public void setDeleteOutputFiles(Boolean deleteOutputFiles) {
        this.deleteOutputFiles = deleteOutputFiles
    }

    public Boolean getStopProcessingIfError() {
        return stopProcessingIfError;
    }

    public void setStopProcessingIfError(Boolean stopProcessingIfError) {
        this.stopProcessingIfError = stopProcessingIfError;
    }

    public String getUnstableThresholdFailed() {
        return unstableThresholdFailed;
    }

    public void setUnstableThresholdFailed(String unstableThresholdFailed) {
        this.unstableThresholdFailed = unstableThresholdFailed;
    }

    public String getUnstableNewThresholdFailed() {
        return unstableNewThresholdFailed;
    }

    public void setUnstableNewThresholdFailed(String unstableNewThresholdFailed) {
        this.unstableNewThresholdFailed = unstableNewThresholdFailed;
    }

    public String getFailureThresholdFailed() {
        return failureThresholdFailed;
    }

    public void setFailureThresholdFailed(String failureThresholdFailed) {
        this.failureThresholdFailed = failureThresholdFailed;
    }

    public String getFailureNewThresholdFailed() {
        return failureNewThresholdFailed;
    }

    public void setFailureNewThresholdFailed(String failureNewThresholdFailed) {
        this.failureNewThresholdFailed = failureNewThresholdFailed
    }

    public String getUnstableThresholdSkipped() {
        return unstableThresholdSkipped;
    }

    public void setUnstableThresholdSkipped(String unstableThresholdSkipped) {
        this.unstableThresholdSkipped = unstableThresholdSkipped;
    }

    public String getUnstableNewThresholdSkipped() {
        return unstableNewThresholdSkipped
    }

    public void setUnstableNewThresholdSkipped(String unstableNewThresholdSkipped) {
        this.unstableNewThresholdSkipped = unstableNewThresholdSkipped
    }

    public String getFailureThresholdSkipped() {
        return failureThresholdSkipped
    }

    public void setFailureThresholdSkipped(String failureThresholdSkipped) {
        this.failureThresholdSkipped = failureThresholdSkipped
    }

    public String getFailureNewThresholdSkipped() {
        return failureNewThresholdSkipped;
    }

    public void setFailureNewThresholdSkipped(String failureNewThresholdSkipped) {
        this.failureNewThresholdSkipped = failureNewThresholdSkipped;
    }

    public String getThresholdMode() {
        return thresholdMode;
    }

    public void setThresholdMode(String thresholdMode) {
        this.thresholdMode = thresholdMode;
    }

    public String getTestTimeMargin() {
        return testTimeMargin;
    }

    public void setTestTimeMargin(String testTimeMargin) {
        this.testTimeMargin = testTimeMargin;
    }

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

    public void add(Object context, IDPJob idpJobObj) {
        this.addOptions(context, this.performMapping(idpJobObj))
    }

    /*
     * This function implements performMapping method of IPluginBase interface
     */

    public HashMap<String, String> performMapping(IDPJob idpJobObj) {
        IDPJob job = idpJobObj
        HashMap<String, String> data = new HashMap<String, String>()

        data.put('pattern', getPattern())
        data.put('skipNoTestFiles', getSkipNoTestFiles())
        data.put('failIfNotNew', getFailIfNotNew())
        data.put('deleteOutputFiles', getDeleteOutputFiles())
        data.put('stopProcessingIfError', getStopProcessingIfError())

        data.put('unstableThresholdFailed', getUnstableThresholdFailed())
        data.put('unstableNewThresholdFailed', getUnstableNewThresholdFailed())
        data.put('failureThresholdFailed', getFailureThresholdFailed())
        data.put('failureNewThresholdFailed', getFailureNewThresholdFailed())

        data.put('unstableThresholdSkipped', getUnstableThresholdSkipped())
        data.put('unstableNewThresholdSkipped', getUnstableNewThresholdSkipped())
        data.put('failureThresholdSkipped', getFailureThresholdSkipped())
        data.put('failureNewThresholdSkipped', getFailureNewThresholdSkipped())

        data.put('thresholdMode', getThresholdMode())
        data.put('testTimeMargin', getTestTimeMargin())

        return data
    }

    /*
    * This function implements addOptions method of IPluginBase interface
    */

    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            xUnitPublisher {

                tools {

                    jUnitType {

                        if (data.containsKey('pattern')) pattern(data.get('pattern'))
                        if (data.containsKey('skipNoTestFiles')) skipNoTestFiles(data.get('skipNoTestFiles').toBoolean())
                        if (data.containsKey('failIfNotNew')) failIfNotNew(data.get('failIfNotNew').toBoolean())
                        if (data.containsKey('deleteOutputFiles')) deleteOutputFiles(data.get('deleteOutputFiles').toBoolean())
                        if (data.containsKey('stopProcessingIfError')) stopProcessingIfError(data.get('stopProcessingIfError').toBoolean())
                    }
                }

                thresholds {

                    failedThreshold {

                        if (data.containsKey('unstableThresholdFailed')) unstableThreshold(data.get('unstableThresholdFailed'))
                        if (data.containsKey('unstableNewThresholdFailed')) unstableNewThreshold(data.get('unstableNewThresholdFailed'))
                        if (data.containsKey('failureThresholdFailed')) failureThreshold(data.get('failureThresholdFailed'))
                        if (data.containsKey('failureNewThresholdFailed')) failureNewThreshold(data.get('failureNewThresholdFailed'))
                    }

                    skippedThreshold {

                        if (data.containsKey('unstableThresholdSkipped')) unstableThreshold(data.get('unstableThresholdSkipped'))
                        if (data.containsKey('unstableNewThresholdSkipped')) unstableNewThreshold(data.get('unstableNewThresholdSkipped'))
                        if (data.containsKey('failureThresholdSkipped')) failureThreshold(data.get('failureThresholdSkipped'))
                        if (data.containsKey('failureNewThresholdSkipped')) failureNewThreshold(data.get('failureNewThresholdSkipped'))
                    }
                }

                if (data.containsKey('thresholdMode')) thresholdMode(Integer.parseInt(data.get('thresholdMode')))
                if (data.containsKey('testTimeMargin')) testTimeMargin(data.get('testTimeMargin'))
            }
        }
    }
}
