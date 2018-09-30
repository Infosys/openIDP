/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.creators

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.*

/**
 *
 *  This class is created for base job creation
 *
 */

class BaseJobCreator{
	String jobName
	String jobDescription
	
	/*
	 *	This function is used for creating base job in jenkins
	 */

	Job create(DslFactory factory) {
		factory.job(jobName){
			it.description this.jobDescription
		}
	}

}


