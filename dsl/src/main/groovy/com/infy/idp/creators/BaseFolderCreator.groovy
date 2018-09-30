/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.creators

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Folder
import javaposse.jobdsl.dsl.*

/**
 *	This class is used to create base folder for each job in Jenkins
 *
 *
 */
class BaseFolderCreator {
	String folderName

	/*
	 *	Create method is used for creating base folder
	 */
	Folder create(DslFactory factory) {
		factory.folder(folderName){
		}
	}
}

