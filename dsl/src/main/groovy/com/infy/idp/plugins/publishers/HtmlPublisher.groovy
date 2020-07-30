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
 * This class implements IPLuginBase interface to publish HTML reports
 *
 */

class HtmlPublisher implements IPluginBase {


    private reportDir = []
    private reportNames = []
    private reportFile = []

    public String updateReportDir(reportDir) {
        this.reportDir.add(reportDir)
    }

    public String updateReportName(reportName) {
        this.reportNames.add(reportName)
    }

    public String updateReportFile(reportFile) {
        this.reportFile.add(reportFile)
    }

    /*
     * This function implements add method of IPluginBase interface
     */

    public void add(Object context, IDPJob idpJobObj) {
        this.addOptions(context, this.performMapping(idpJobObj));
    }

    /*
    * This function implements performMapping method of IPluginBase interface
    */

    public HashMap<String, String> performMapping(IDPJob idpJobObj) {

        HashMap<String, String> data = new HashMap<String, String>()

        data.put('reportDir', reportDir)
        data.put('allowMissing', false)
        data.put('alwaysLinkToLastBuild', false)
        data.put('keepAll', false)
        data.put('reportFiles', reportFile)
        data.put('reportName', 'HTML Report')

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    public void addOptions(context, HashMap<String, String> data) {

        def newData = data
        context.with {

            publishHtml {

                // Adds a report to publish.
                for (int i = 0; i < reportDir.size(); i++) {

                    report(reportDir[i]) {

                        // If set, allows report to be missing.
                        allowMissing(false)

                        // Publishes the link on project level even if build failed.
                        alwaysLinkToLastBuild(false)

                        // If set, archives reports for all successful builds.
                        keepAll(false)

                        // Sets the path to the HTML report directory relative to the workspace.
                        reportFiles(reportFile[i])

                        // Sets a title for the report.
                        if (reportNames[i] == null) {
                            reportName('HTML Report')
                        } else {
                            reportName(reportNames[i])
                        }
                    }
                }


            }
        }
    }


}
