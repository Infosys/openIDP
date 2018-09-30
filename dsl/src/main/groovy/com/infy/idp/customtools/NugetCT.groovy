/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.utils.Constants;
import com.infy.idp.utils.ExecuteCmd
import com.infy.idp.utils.PropReader;

/**
 *
 * This class includes the method for adding IDPDashBoard customtool
 *
 */

class NugetCT {

	/*
	 * This function is used to upload artifacts on Nuget repository
	 */
	public static String uploadArtifacts(context,os,specFile,NexusRepo,nexusKey,repoName){
		String specPath="."
		if (specFile.contains('/') && specFile.contains('.nuspec'))
			specPath= specFile.substring(0, specFile.lastIndexOf('/'))
		String idpWS = (os == Constants.WINDOWSOS) ? '%IDP_WS%/' : '$IDP_WS/'
		
		String packCmd
		packCmd=idpWS+"/..//../" + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'NUGET')+" pack \""+specFile+"\" -outputdirectory \""+specPath+"\"";
		
		if(!NexusRepo.startsWith("http"))
			NexusRepo="http://"+NexusRepo
	
		String pushCmd

		pushCmd=idpWS+"/..//../" + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'NUGET')+" push \""+specPath+"\\*.nupkg\" "+nexusKey+" -SOURCE "+NexusRepo+"/repository/"+repoName+"/"

		context.with{
			steps{
					ExecuteCmd.invokeCmd(delegate, packCmd, os)
					ExecuteCmd.invokeCmd(delegate, pushCmd, os)
			}
		}
	}

	/*
	 * This function is used to download artifacts from Nuget repository
	 */

	public static String downloadArtifacts(context, os, NexusRepo, repoName, appName){
		String idpWS = (os == Constants.WINDOWSOS) ? '%IDP_WS%/' : '$IDP_WS/'
		if(!NexusRepo.startsWith("http"))
		NexusRepo="http://"+NexusRepo
		
		String cmd

		cmd=idpWS+"/..//../" + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'NUGET')+" install "+appName+".%ARTIFACT_ID%.%PIPELINE_NAME% -version %ARTIFACT_VERSION% -outputdirectory toBeDeployed -source "+NexusRepo+"/repository/"+repoName+"/"

		
		context.with{
			steps{
					ExecuteCmd.invokeCmd(delegate, cmd, os)
			}
		}
	}
}
