/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils

class WrapperAdder {

	static void addWrapper(context,jobType){
		context.with{
			wrappers {
				if(jobType == "maven_build"){
					addCopyToSlaveBuildWrapper(delegate,',ldaproleapp_Jmvn1_14Mr1/jobs/ldaproleapp_Jmvn1_14Mr1_BUILD/jobs/ldaproleapp_Jmvn1_14Mr1/build.xml,')
					addToolEnvBuildWrapper(delegate,'ANT_HOME,TG_MAVEN_POM_EDITOR_HOME')
				}
				else if(jobType == "maven_upload"){
					addbuildTimeoutWrapper(delegate)
					addToolEnvBuildWrapper(delegate,'ANT_HOME,TG_NEXUS_ARTIFACT_INFO_UPLOAD_HOME')
					addCopyToSlaveBuildWrapper(delegate, 'ldaproleapp_Jmvn1_14Mr1/jobs/ldaproleapp_Jmvn1_14Mr1_BUILD/jobs/ldaproleapp_Jmvn1_14Mr1_Repo_Upload/IDP_StaggingZip.xml')
				}
			}
		}
	}

	private static void addCopyToSlaveBuildWrapper(context,includesList){
		context.with{
			copyToSlaveBuildWrapper {
				includes(includesList)
				excludes('')
				flatten(true)
				includeAntExcludes(false)
				relativeTo('somewhereElse')
				hudsonHomeRelative(false)
			}
		}
	}

	private static void addToolEnvBuildWrapper(context,varList){
		context.with {
			toolEnvBuildWrapper { vars(varList) }
		}
	}

	private static void addbuildTimeoutWrapper(context){
		context.with {
			buildTimeoutWrapper {
				strategy {
					absoluteTimeOutStrategy { timeoutMinutes('180') }
				}
				operationList { abortOperation() }
				timeoutEnvVar('')
			}
		}
	}
}
