/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils

/**
 *
 * This class has the method to for clearing the logs after certain days
 *
 */

class LogRotatorAdder {
    static void addLogRotator(context) {
        context.with {
            logRotator {
                artifactDaysToKeep(30)
                artifactNumToKeep(30)
                daysToKeep(30)
                numToKeep(30)
            }
        }
    }
}
