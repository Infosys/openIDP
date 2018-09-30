/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.creators

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.jobs.WorkflowJob
import javaposse.jobdsl.dsl.*
/**
 *
 * This class includes the method for creating pipeline job
 *
 */

class BasePipelineCreator {
  String pipelineName

  /*
   * This function is used to create pipeline job in jenkins
   */
  WorkflowJob create(DslFactory factory) {
    factory.pipelineJob(pipelineName){
    }
  }
}
