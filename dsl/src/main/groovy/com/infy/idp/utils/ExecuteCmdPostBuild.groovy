/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils

import static com.infy.idp.utils.Constants.*

/**
 *
 * This class has the method to Execute commands in post build based on OS
 *
 */

class ExecuteCmdPostBuild {

    /*
     * This method is used to add batch or shell commands in jenkins job
     */

    public static void invokeCmd(context, cmd, buildServerOS) {
        context.with {
            configure {
                it / publishers << 'org.jenkinsci.plugins.postbuildscript.PostBuildScript'(plugin: 'postbuildscript@0.17') {
                    buildSteps {
                        addBuildSteps(delegate, cmd, buildServerOS)
                    }
                    markBuildUnstable(true)
                }
            }
        }
    }

    /*
     * This method is used to add post build steps
     */

    public static void addBuildSteps(context, cmd, buildServerOS) {
        context.with {
            if (buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
                'hudson.tasks.BatchFile' {
                    command(cmd)
                }
            } else {
                'hudson.tasks.Shell' {
                    command(cmd)
                }
            }
        }
    }
}
