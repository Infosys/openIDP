/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.triggers

import com.infy.idp.creators.*
import com.infy.idp.utils.*

/**
 *
 * This class has the method to trigger pipeline based on the status change in JIRA story
 *
 */

class JiraChangeLogTrigger {

    /*
     * This method is used to trigger job on change in jira story status
     */

    public static void invokeJiraChangeLogTrigger(context, jQLFilter) {

        context.with {

            triggers {

                jiraChangelogTrigger {

                    changelogMatchers {}

                    // A build will only be triggered if the updated issues matches this JQL filter.
                    jqlFilter(jQLFilter)

                    // Maps JIRA issue attributes as Jenkins parameters.
                    parameterMappings {}
                }
            }
        }
    }
}
