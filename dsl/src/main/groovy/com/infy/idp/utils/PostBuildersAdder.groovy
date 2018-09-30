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
 * This class has the method for postbuild adders
 *
 */

class PostBuildersAdder {

    static void addPostBuilders(context) {
        context.with {
            postBuildSteps('UNSTABLE') {
            }
        }
    }
}
