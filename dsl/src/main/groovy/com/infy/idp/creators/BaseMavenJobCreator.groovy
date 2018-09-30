/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.creators

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.jobs.MavenJob

/**
 *
 * This class includes the method for creating base maven job
 *
 */

class BaseMavenJobCreator {
  String jobName
  String jobDescription
  
  /*
   * This function is used to create base maven job in jenkins
   */

  MavenJob create(DslFactory factory) {
    factory.mavenJob(jobName){
      description(jobDescription)
    }
  }

}
