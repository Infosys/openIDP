/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils

import com.infy.idp.creators.*

import static com.infy.idp.utils.Constants.*

/**
 *
 * This class has the method to Execute commands based on OS
 *
 */

class ExecuteCmd {

    /*
     * This method is used to add batch or shell commands in jenkins job
     */

    public static void invokeCmd(context, cmd, buildServerOS, errorLevel = '9999999') {

        context.with {

            if (buildServerOS.toString().compareToIgnoreCase(Constants.WINDOWSOS) == 0) {

                batchFile {
                    command(cmd)

                    if (errorLevel == '1') unstableReturn(1)
                }

            } else {

                shell {
                    command(cmd)

                    if (errorLevel == '1') unstableReturn(1)
                }
            }
        }
    }

}
