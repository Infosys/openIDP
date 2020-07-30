/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.buildsteps

import com.infy.idp.interfaces.IPluginBase
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to configure job for MSTest option
 *
 */

class MSTestBuilder implements IPluginBase {

    private String cmdArgs = ''
    private String testFiles = ''
    private String testCat = ''
    private String testOpFile = ''
    private String msBuildVersion = '(Default)'


    public void setCmdArgs(String cmdArgs) {
        this.cmdArgs = cmdArgs;
    }


    public void setTestFiles(String testFiles) {
        this.testFiles = testFiles
    }


    public void setTestCat(String testCat) {
        this.testCat = testCat;
    }


    public void setTestOpFile(String testOpFile) {
        this.testOpFile = testOpFile
    }


    public void setMsBuildVersion(String msBuildVersion) {
        this.msBuildVersion = msBuildVersion;
    }

    /*
     * This function implements add method of IPluginBase interface
     */

    public void add(context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj))
    }


    /*
    * This function implements performMapping method of IPluginBase interface
    */

    public HashMap<String, String> performMapping(IDPJob jsonData) {
        IDPJob job = jsonData

        HashMap<String, String> data = new HashMap<String, String>()

        data.put('msTestName', this.msBuildVersion)
        data.put('testFiles', this.testFiles)
        data.put('categories', this.testCat)
        data.put('resultFile', this.testOpFile)
        data.put('cmdLineArgs', this.cmdArgs)
        data.put('continueOnFail', 'false')

        return data
    }


    /*
     * This function implements addOptions method of IPluginBase interface
     */

    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            msTestBuilder {

                MSTestBuilder.addMsTest(delegate, data)
            }
        }
    }

    /*
     * This method is used to add MSTest option in jenkins job
     */

    private static void addMsTest(context, data) {

        context.with {

            if (data.containsKey('msTestName')) msTestName(data.get('msTestName'))
            if (data.containsKey('testFiles')) testFiles(data.get('testFiles'))
            if (data.containsKey('categories')) categories(data.get('categories'))
            if (data.containsKey('resultFile')) resultFile(data.get('resultFile'))
            if (data.containsKey('cmdLineArgs')) cmdLineArgs(data.get('cmdLineArgs'))
            if (data.containsKey('continueOnFail')) continueOnFail(data.get('continueOnFail').toBoolean())
        }
    }
}
