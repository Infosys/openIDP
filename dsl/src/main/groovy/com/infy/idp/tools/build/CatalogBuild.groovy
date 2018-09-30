/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.build

import com.infy.idp.creators.*
import com.infy.idp.customtools.*
import com.infy.idp.plugins.buildsteps.*
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.wrappers.*
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the catalog build job
 *
 */

class CatalogBuild {

    /*
     * This method is used to create the pipeline script for truggering the pipeline
     */

    public static void createPipelineScript(basepath, jsonData, envVar) {
        String idpWS = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS)) ? '%IDP_WS%/' : '$IDP_WS/'

        def modulesArr = jsonData.buildInfo.modules
        def script = ''
        for (int i = 0; i < modulesArr.size(); i++) {


            if (modulesArr.getAt(i).runScript && modulesArr.getAt(i).runScript.scriptFilePath && modulesArr.getAt(i).runScript.scriptFilePath != null) {
                def command = ''
                def os = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS)) ? 'bat' : 'sh'
                switch (modulesArr.getAt(i).runScript.scriptType.toString()) {

                    case Constants.ANTSCRIPT:
                        command = os + '\'ant -f ' + idpWS + modulesArr.getAt(i).runScript.scriptFilePath + ' ' + modulesArr.getAt(i).runScript.targets + '\' \n '
                        break
                    case Constants.BATCHSCRIPT:
                        command = 'bat \'' + idpWS + modulesArr.getAt(i).runScript.scriptFilePath + '\' \n '
                        break
                    case Constants.SHELLSCRIPT:
                        command = 'sh \'sh ' + idpWS + modulesArr.getAt(i).runScript.scriptFilePath + '\' \n '
                        break
                    case Constants.PSSCRIPT:
                        command = 'bat \'' + ' powershell "' + idpWS + modulesArr.getAt(i).runScript.scriptFilePath + '" \' \n '
                        break
                }
                script += 'if(MODULE_LIST.contains(\';' + modulesArr.getAt(i).moduleName + ';\')) {'
                script += 'stage(\'' + modulesArr.getAt(i).moduleName + '\'){ \n' + '\t node(SLAVE_NODE) { \n \t' + command + '}\n'
            }


            if (modulesArr.getAt(i).timeout && modulesArr.getAt(i).timeout != null) {
                script += '\t try { \n' + '\t \t timeout(time: ' + modulesArr.getAt(i).timeout + ', unit: \'MINUTES\'){ \n' +

                        '\t \t \t userInput= input( \n' + '\t \t \t id: \'IDPApproval\', message: \'APPROVE THE BUILD?\', parameters: [ \n' +
                        '\t \t \t [$class: \'TextParameterDefinition\', defaultValue: \'NO COMMENTS\', description: \'USER INFO WITH COMMENTS\', name: \'APPR_INFO\']]) \n' + '\t \t \t echo \'Approved By: \'+userInput \n' + '\t \t \t echo \'APPROVAL IS DONE EXECUTION IS PROCEEDING \' \n ' + '\t }}catch(Exception e) { \n' + '\t \t timeout(time: 2, unit: \'MINUTES\'){ \n' + '\t \t \t userInput = input( \n' + 'id: \'IDPDefault\', message: \'APPROVE THE BUILD?\', parameters: [ \n' +
                        '\t \t \t [$class: \'TextParameterDefinition\', defaultValue: \'NO COMMENTS\', description:' +
                        '\'USER INFO WITH COMMENTS\', name: \'APPR_INFO\']]) \n' + '\t \t \t echo \'Rejected By: \'+userInput \n' +
                        '\t \t \t echo \'Build is rejected, Hence aborting the build\' \n '


                if (modulesArr.getAt(i).abortScript && !modulesArr.getAt(i).abortScript.equals('{}') && modulesArr.getAt(i).abortScript.scriptFilePath) {
                    def command = ''
                    def os = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS)) ? 'bat' : 'sh'
                    switch (modulesArr.getAt(i).abortScript.scriptType.toString()) {

                        case Constants.ANTSCRIPT:
                            command = os + '\'ant -f ' + idpWS + modulesArr.getAt(i).abortScript.scriptFilePath + ' ' + modulesArr.getAt(i).abortScript.targets + '\' \n '
                            break
                        case Constants.BATCHSCRIPT:
                            command = 'bat \'' + idpWS + modulesArr.getAt(i).abortScript.scriptFilePath + '\' \n '
                            break
                        case Constants.SHELLSCRIPT:
                            command = 'sh \'sh ' + idpWS + modulesArr.getAt(i).abortScript.scriptFilePath + '\' \n '
                            break
                        case Constants.PSSCRIPT:
                            command = 'bat \'' + ' powershell "' + idpWS + modulesArr.getAt(i).abortScript.scriptFilePath + '" \' \n '
                            break
                    }

                    script += 'node(SLAVE_NODE) { \n' + command + '\n } \n throw new Exception(\"FAILURE\") \n } \n '
                } else {
                    script += ' \n throw new Exception(\"FAILURE\") \n } \n  '
                }
                script += '}\n'
            }



            script += '}\n'
            script += '}\n' + 'else { echo \'Module not selected for execution\' } \n'

        }
        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_pipeline_script.groovy', script)


    }


}
	


