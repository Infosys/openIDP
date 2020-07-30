/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.buildsteps

import com.infy.idp.creators.*
import com.infy.idp.interfaces.IPluginBase
import com.infy.idp.utils.*
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to configure job for SonarAnalysis
 *
 */

class SonarRunner implements IPluginBase {

    private def project = ''
    private def properties = ''


    public def getProject() {
        return project;
    }

    public void setProject(def project) {
        this.project = project;
    }

    public def getProperties() {
        return properties;
    }

    public void setProperties(def properties) {
        this.properties = properties;
    }

    /*
     * This function implements add method of IPluginBase interface
     */

    public void add(Object context, IDPJob idpJobObj) {
        //index++
        this.addOptions(context, this.performMapping(idpJobObj));
    }

    /*
     * This function implements performMapping method of IPluginBase interface
     */

    public HashMap<String, String> performMapping(IDPJob idpJobObj) {

        HashMap<String, String> data = new HashMap<String, String>()
        IDPJob job = idpJobObj

        data.put('javaOpts', '')
        data.put('jdk', '')
        data.put('project', this.project)
        data.put('properties', this.properties)
        data.put('sonarScannerName', 'IDP_Sonar')
        data.put('task', '')

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            sonarRunnerBuilder {

                if (data.containsKey('javaOpts')) javaOpts(data.get('javaOpts'))
                if (data.containsKey('jdk')) jdk(data.get('jdk'))
                if (data.containsKey('project')) project(data.get('project'))
                if (data.containsKey('properties')) properties(data.get('properties'))
                if (data.containsKey('sonarScannerName')) sonarScannerName(data.get('sonarScannerName'))
                if (data.containsKey('task')) task(data.get('task'))
            }
        }
    }

}
