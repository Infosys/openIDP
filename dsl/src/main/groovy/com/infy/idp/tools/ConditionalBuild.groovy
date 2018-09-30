/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools


import com.infy.idp.creators.*
import com.infy.idp.utils.*

/**
 *
 * This class has the method to add conditional build steps
 *
 */

class ConditionalBuild {

    /*
     * This method can be used to add conditional build plugin
     */

    public static void conditionalBuildPluginInvocation(context) {

        context.with {

            steps {

                conditionalBuilder {

                    runCondition {

                        expressionCondition {

                            // The regular expression used to match the label - Java regular expression syntax
                            expression('(.)*(;$MODULENAME;){1}(.)*')

                            // The label that will be tested by the regular expression
                            label('$MODULE_LIST')
                        }
                    }

                    // If the evaluation of a run condition fails, should the build fail, be marked unstable, run the build step ...
                    runner {
                        fail()
                    }
                }
            }
        }
    }
}
