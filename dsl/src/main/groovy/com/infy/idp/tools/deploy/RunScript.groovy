/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.deploy

import com.infy.idp.plugins.buildsteps.*
import com.infy.idp.utils.*
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import jenkins.model.*
import org.infy.idp.entities.jobs.common.AntProperties

/**
 *
 * This class has the method to add various scripts
 *
 */
class RunScript {

    /*
    * this method is used to configure conditional steps
    */

    public static void add(context, runScript) {
        switch (runScript.scriptType) {
            case 'ant':
                context.with {
                    steps {
                        def properties = ConvertArrtoString(runScript.antPropertiesArr);
                        Ant.invokeAnt(delegate, runScript.targets, runScript.scriptFilePath, properties, runScript.javaOptions)
                    }
                }
                break
            case 'shellScript':
                context.with {
                    steps {
                        ExecuteCmd.invokeCmd(delegate, 'sh ' + runScript.scriptFilePath, 'unix')
                    }
                }
                break
            case 'batchScript':
                context.with {
                    steps {
                        ExecuteCmd.invokeCmd(delegate, runScript.scriptFilePath, 'windows')
                    }
                }
                break
            case 'powerShell':
                context.with {
                    steps {
                        new Powershell().add(delegate, runScript.scriptFilePath)
                    }
                }
                break
            case 'sshExecution':
                context.with {
                    steps {
                        AddCredentials.run(runScript.host, runScript.userName, runScript.password)
                        SshBuilder ssh = new SshBuilder()
                        if (runScript.flattenFilePath != null && runScript.flattenFilePath == "off") {
                            ssh.flattenFilePath = "false"
                        }
                        addHost(runScript.host, runScript.host, runScript.userName)
                        ssh.invokeSshBuilder(delegate, runScript.host, runScript.userName, runScript.password, runScript.pathToFiles, runScript.destinationDir, runScript.script, runScript.sshKey, runScript.sshKeyPath)
                    }
                }
                break
        }
		if(runScript.scriptType == 'ant'){
		def toolList = 'ANT_HOME'
			context.with {

            wrappers {
                (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(delegate, toolList)
            }
        }
		}
    }

    /*
     * This method is used to add host details
     */

    private static void addHost(name, hostname, username) {
        def inst = Jenkins.getInstance()
        def publish_ssh = inst.getDescriptor("jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin")
        publish_ssh.removeHostConfiguration(name)
        def configuration = publish_ssh.class.classLoader.loadClass("jenkins.plugins.publish_over_ssh.BapSshHostConfiguration").newInstance(name, hostname, username, '', '/', 22, 300000, false, '', '', false)
        publish_ssh.addHostConfiguration(configuration)
        publish_ssh.save()
    }

    /*
     * This method is used to convert array to string
     */

    private static Map<String, String> ConvertArrtoString(List<AntProperties> antPropArr) {

        Map<String, String> map = new HashMap<String, String>();

        if (null != antPropArr) {
            for (def i = 0; i < antPropArr.size(); i++) {

                map.put(antPropArr[i].antKey, antPropArr[i].antValue);


            }
        }


        return map;
    }
}
