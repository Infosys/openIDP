/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.creators.*
import com.infy.idp.utils.*

/**
 *
 * This class includes the method for adding Pmd customtool
 *
 */

class Pmd {

	/*
    * This function is used to add the commands to run Pmd
    */

	public static void invokeTool(context, relativePath, os) {	
		
		relativePath = relativePath.replace('\\', '/')
		def moduleDir 
		if(relativePath.contains('/')){
			moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
		}
		else{
			moduleDir = relativePath
		}
		
		String pmdReportDir = '%IDP_WS%/' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN,Constants.PMDREPORTNAME)
		
		StringBuilder sb = new StringBuilder()

		sb.append('"%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.PMDCTNM) + '/bin/pmd" -shortnames -l java ')
		sb.append('-R "' + Constants.PMDRULES + '" ')
		sb.append('-f "' + Constants.PMDREPORTFORMAT + '" ')
		sb.append('-r "' + pmdReportDir + '/PMD_Report.xml" ')
		sb.append('-d "%IDP_WS%/' + moduleDir +  '" || exit 0')


		ExecuteCmd.invokeCmd(context, 'mkdir "' + pmdReportDir + '" || exit 0', os)
		ExecuteCmd.invokeCmd(context, sb.toString(), os)
	}
}
