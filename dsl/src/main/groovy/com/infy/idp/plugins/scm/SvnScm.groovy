/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.scm

import com.infy.idp.creators.*
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import org.infy.idp.entities.jobs.IDPJob
import org.infy.idp.entities.jobs.code.Scm

/**
 *
 * This class implements IPLuginBase interface to add SVNSCM option in jenkins job
 *
 */

class SvnScm {

    private indexToMap = -1

    private HashMap<Integer, Scm> scmMap;


    public HashMap<Integer, Scm> getScmMap() {
        return scmMap;
    }

    public void setScmMap(HashMap<Integer, Scm> scmMap) {
        this.scmMap = scmMap;
    }

    /*
     * This function implements add method of IPluginBase interface
     */

    public void add(Object context, IDPJob idpJobObj) {


        def scmArr = null;
        if (idpJobObj.code != null && idpJobObj.code.scm != null) {
            scmArr = idpJobObj.code.scm
        } else {
            scmArr = idpJobObj.tesAsset.scm
        }



        for (Map.Entry<Integer, Scm> entry : this.scmMap.entrySet()) {
            int key = entry.getKey();
            if (scmArr.getAt(key).type == Constants.SVNSCM) {
                indexToMap = key
                this.addOptions(context, this.performMapping(idpJobObj));
            }


        }


    }

    /*
     * This function implements performMapping method of IPluginBase interface
     */

    public HashMap<String, String> performMapping(IDPJob idpJobObj) {

        HashMap<String, String> data = new HashMap<String, String>()


        def scmSection = null;
        if (idpJobObj.code != null && idpJobObj.code.scm != null) {
            scmSection = idpJobObj.code.scm.getAt(indexToMap)
        } else {
            scmSection = idpJobObj.tesAsset.scm.getAt(indexToMap)
        }
        data.put('remote', scmSection.url)

        def credId = java.util.UUID.randomUUID().toString()
        AddCredentials.run(credId, scmSection.userName, scmSection.password)
        data.put('credentialsId', credId)

        if (scmSection.url) {
            String repoName
            scmSection.url.replace("\\", "/")
            def repoNameList = scmSection.url.split("/")

            if (repoNameList.size() > 0) {
                repoName = repoNameList[-1]
            }
            data.put('local', repoName)
        }

        data.put('depthOption', 'infinity')
        data.put('ignoreExternalsOption', 'false')

        data.put('spaceName', '')
        data.put('url', '')
        data.put('excludedRegions', '')
        data.put('excludedUsers', '')

        data.put('excludedRevprop', '')
        data.put('excludedCommitMessages', '')
        data.put('includedRegions', '')
        data.put('ignoreDirPropChanges', 'false')
        data.put('filterChangelog', false)
        data.put('realm', '')
        data.put('credentialsIdAdditionals', '')

        return data
    }

    /*
    * This function implements addOptions method of IPluginBase interface
    */

    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            subversionSCM {

                locations {
                    moduleLocation {
                        if (data.containsKey('remote')) remote(data.get('remote'))
                        if (data.containsKey('credentialsId')) credentialsId(data.get('credentialsId'))
                        if (data.containsKey('local')) local(data.get('local'))
                        if (data.containsKey('depthOption')) depthOption(data.get('depthOption'))
                        if (data.containsKey('ignoreExternalsOption')) ignoreExternalsOption(data.get('ignoreExternalsOption').toBoolean())
                    }
                }
                workspaceUpdater {
                    checkoutUpdater()
                    updateUpdater()
                    updateWithCleanUpdater()
                    updateWithRevertUpdater()
                }

                browser {}

                if (data.containsKey('excludedRegions')) excludedRegions(data.get('excludedRegions'))
                if (data.containsKey('excludedUsers')) excludedUsers(data.get('excludedUsers'))
                if (data.containsKey('excludedRevprop')) excludedRevprop(data.get('excludedRevprop'))
                if (data.containsKey('excludedCommitMessages')) excludedCommitMessages(data.get('excludedCommitMessages'))
                if (data.containsKey('includedRegions')) includedRegions(data.get('includedRegions'))
                if (data.containsKey('ignoreDirPropChanges')) ignoreDirPropChanges(data.get('ignoreDirPropChanges').toBoolean())
                if (data.containsKey('filterChangelog')) filterChangelog(data.get('filterChangelog'))

            }
        }
    }
}
