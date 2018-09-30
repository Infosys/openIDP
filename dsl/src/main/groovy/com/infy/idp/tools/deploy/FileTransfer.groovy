/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.deploy

import com.infy.idp.plugins.buildsteps.Powershell
import com.infy.idp.utils.*
import com.infy.idp.utils.Constants
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the deployment job for file transfer deployment
 *
 */
class FileTransfer {

    static String basepath

    /*
    * This method add the steps for deployment using file transfer
    */

    public static void add(context, jsonData, envIndex, stepIndex, basepath) {

        this.basepath = basepath
        addProperties(context, jsonData, envIndex, stepIndex)
        addSteps(context, jsonData, envIndex, stepIndex)

    }

    /*
   * This method is used to add properties for parameterized job
   */

    private static void addProperties(context, jsonData, envIndex, stepIndex) {
        context.with {
            configure {

                it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' << {

                    PropertiesAdder.addPasswordParam(delegate, 'pass', jsonData.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].password, 'Machine Password')

                }
            }
        }
    }

    /*
    * This methos add steps or confiuration in deploy job
    */

    private static void addSteps(context, jsonData, envIndex, stepIndex) {
        def deployArr = jsonData.deployInfo.deployEnv[envIndex].deploySteps[stepIndex]
        String cmd
        context.with {

            steps {
                if (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS && deployArr.deployOS == Constants.WINDOWSOS) {
                    cmd = "\$secpasswd = ConvertTo-SecureString \$env:pass -AsPlainText -Force\n"
                    cmd += "\$mycreds = New-Object System.Management.Automation.PSCredential (\"" + deployArr.userName + "\",\$secpasswd)\n"
                    cmd += "\$b= New-PSSession -ComputerName " + deployArr.hostName + " -Credential \$mycreds\n"
                    cmd += "\$destination=\"" + deployArr.staticDestinationPath + "\"\n"
                    cmd += "if(Invoke-Command {-not (Test-Path \$using:destination)} -session \$b)\n"
                    cmd += "{\nInvoke-Command -Session \$b -ScriptBlock {New-Item -Path " + deployArr.staticDestinationPath + " -ItemType DIR}\n}\n"
                    cmd += "Copy-Item -Recurse -Path " + deployArr.pathToFiles + " -Destination " + deployArr.staticDestinationPath + " -ToSession \$b -force"
                    Powershell pws = new Powershell()
                    pws.add(delegate, cmd)
                }
            }
        }
    }
}
