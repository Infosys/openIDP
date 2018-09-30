/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils.fs

import com.infy.idp.utils.*

/**
 *
 * This class has the method to rename file
 *
 */
class Rename {

    /*
     * This method prepares the command to rename a file
     */

    public static void run(context, os, loc) {

        def cmd

        if (os == Constants.WINDOWSOS) {
            cmd = 'rename ' + loc + ' || exit 0'
        } else {
            cmd = 'mv ' + loc
        }

        ExecuteCmd.invokeCmd(context, cmd, os)
    }
}
