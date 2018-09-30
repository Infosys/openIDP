/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.build

import com.infy.idp.plugins.buildsteps.*
import com.infy.idp.utils.*
import jenkins.model.*
import org.infy.idp.entities.jobs.common.AntProperties

/**
 *
 * This class has the method to add various scripts
 *
 */

class RunScript {

    /*
     * This method is used to add prebuild option
     */

    public static void preBuild(context, runScript) {

        addWrappers(context)

        context.with {
            preBuildSteps { addSteps(delegate, runScript) }
        }
    }

    /*
    * This method is used to add postbuild option
    */

    public static void postBuild(context, runScript) {

        addWrappers(context)

        context.with {
            postBuildSteps { addSteps(delegate, runScript) }
        }
    }

    /*
    * This method is used to add postbuild module
    */

    public static void postBuildModule(context, runScript) {

        addWrappers(context)

        context.with {
            postBuildSteps { addSteps1(delegate, runScript) }
        }
    }

    /*
    * This method is used to add inbuild module
    */

    public static void inBuildModule(context, runScript) {

        addWrappers(context)

        context.with {
            steps { addSteps1(delegate, runScript) }
        }
    }

    /*
    * This method is used to add inbuild
    */

    public static void inBuild(context, runScript) {

        addWrappers(context)

        context.with {
            steps { addSteps(delegate, runScript) }
        }
    }

    /*
    * this method is used to configure conditional steps
    */

    private static void addSteps(context, runScript) {

        context.with {

            switch (runScript.tool) {

                case Constants.ANTSCRIPT:
                    def properties = ConvertArrtoString(runScript.antPropertiesArr);
                    //Ant.invokeAnt(context, runScript.targets, runScript.scriptFilePath)
                    Ant.invokeAnt(delegate, runScript.targets, runScript.scriptFilePath, properties, runScript.javaOptions)
                    break

                case Constants.SHELLSCRIPT:
                    shell('sh ' + runScript.scriptFilePath)
                    break

                case Constants.BATCHSCRIPT:
                    batchFile(runScript.scriptFilePath)
                    break

                case Constants.PSSCRIPT:
                    powerShell(runScript.scriptFilePath)
                    break

                case Constants.SSH_EXECUTION:
                    AddCredentials.run(runScript.host, runScript.userName, runScript.password)
                    SshBuilder ssh = new SshBuilder()
                    if (runScript.flattenFilePath != null && runScript.flattenFilePath == "off") {
                        ssh.flattenFilePath = "false"
                    }
                    addHost(runScript.host, runScript.host, runScript.userName)
                    ssh.invokeSshBuilder(delegate, runScript.host, runScript.userName, runScript.password, runScript.pathToFiles, runScript.destinationDir, runScript.script, runScript.sshKey, runScript.sshKeyPath)

                    break
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
     * This method is used to add steps for prebuild and post build
     */

    private static void addSteps1(context, runScript) {

        context.with {

            switch (runScript.tool) {

                case Constants.ANTSCRIPT:
                    def properties1 = ConvertArrtoString(runScript.antPropertiesArr);
                    Ant.invokeAnt(delegate, runScript.targets, runScript.scriptFilePath, properties1, runScript.javaOptions)
                    break

                case Constants.SHELL:
                    shell('sh ' + runScript.scriptFilePath)
                    break

                case Constants.BATCH:
                    batchFile(runScript.scriptFilePath)
                    break

                case Constants.PS:
                    powerShell(runScript.scriptFilePath)
                    break

                case Constants.SSH_EXECUTION:

                    AddCredentials.run(runScript.host, runScript.userName, runScript.password)
                    SshBuilder ssh = new SshBuilder()
                    if (runScript.flattenFilePath != null && runScript.flattenFilePath == "off") {
                        ssh.flattenFilePath = "false"
                    }
                    addHost(runScript.host, runScript.host, runScript.userName)
                    ssh.invokeSshBuilder(delegate, runScript.host, runScript.userName, runScript.password, runScript.pathToFiles, runScript.destinationDir, runScript.script, runScript.sshKey, runScript.sshPathToKey)

                    break
            }
        }
    }

    /*
    * this method is used to add required wrappers
    */

    private static String addWrappers(context) {

        context.with {

            configure {

                def tools = it / buildWrappers / 'hudson.plugins.toolenv.ToolEnvBuildWrapper' / vars
                (it / buildWrappers / 'hudson.plugins.toolenv.ToolEnvBuildWrapper' / vars).setValue(addAntTool(tools.value()))
            }
        }
    }

    /*
     * This method is used to add ant custom tool
     */

    private static String addAntTool(tools) {

        def retVal = tools.join(',')

        if (!retVal.contains('ANT_HOME'))
            retVal += (',ANT_HOME,')

        return retVal
    }

    /*
     * This method is used to convert array to string
     */

    private static Map<String, String> ConvertArrtoString(List<AntProperties> antPropArr) {
        Map<String, String> map = new HashMap<String, String>();

        if (null != antPropArr) {
            for (def i = 0; i < antPropArr.size(); i++) {
                map.put(antPropArr[i].antKey, antPropArr[i].antValue);
                // syntax=syntax+"'" +antPropArr[i].antKey + "' : '" +antPropArr[i].antValue+"',";
            }
        }
        return map;
    }
}
