/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.stages

import com.infy.idp.creators.*
//import com.infy.idp.customtools.GeGitUtil
import com.infy.idp.plugins.scm.*
import com.infy.idp.plugins.wrappers.BuildNameSetter
import com.infy.idp.plugins.wrappers.PreBuildCleanup
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import com.infy.idp.utils.Constants
import javaposse.jobdsl.dsl.DslFactory
import com.infy.idp.plugins.wrappers.BuildEnv

/**
 *
 * This class has the method to create the SCM Deploy job
 *
 */

class Scm_Deploy {

    String basepath

    /*
    * This method is used to call other child jobs to create a SCM Deploy job with all the configuration
    */

    void run(DslFactory factory, jsonData) {
        def scmFlag = true
        if (jsonData.code.scm.size() > 0) {
			if(jsonData.code.technology.equalsIgnoreCase("SapNonCharm")){
				def data = jsonData.code.scm.get(0);
				if(!(data.size()>0))
					scmFlag=false
			}
            if (scmFlag) {
                basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
                baseJobCreationForScmDeploy(factory, jsonData)
            }
        }
    }

    /*
     * this method is used to create the SCM deploy job in jenkins
     */

    private void baseJobCreationForScmDeploy(factory, jsonData) {

        String basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

        new BaseJobCreator(

                jobName: basepath + '/' + basepath + Constants.SCM_DEPLOY_JOBNAMEPOSTFIX,
                jobDescription: ''

        ).create(factory).with {

            //Standard IDP Settings
            configure { it / canRoam(false) }
            concurrentBuild(true)

            if (jsonData.buildInfo && jsonData.buildInfo.buildtool != '' && !'SapNonCharm'.equalsIgnoreCase(jsonData.buildInfo.buildtool)) {

                if (jsonData.code.scm.getAt(0).proxy && jsonData.code.scm.getAt(0).proxy != '') {
                    def proxy = jsonData.code.scm.getAt(0).proxy
                    def port = jsonData.code.scm.getAt(0).proxyPort
                    def abc = proxy + ":" + port
                    environmentVariables {
                        propertiesFile('$IDP_WS/CustomJobParm.properties')
                        env('http_proxy', abc)
                        env('https_proxy', abc)

                        keepBuildVariables(true)
                    }
                } else {

                    environmentVariables {
                        propertiesFile('$IDP_WS/CustomJobParm.properties')

                        keepBuildVariables(true)
                    }
                }

            }
            this.addProperties(delegate, jsonData)
            this.invokeMultiScm(delegate, jsonData)
            this.addWrappers(delegate, jsonData)

            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) customWorkspace('$IDP_WS')
            NiaIntegStage.run(delegate, jsonData, Constants.SCM);
        }
    }

    /*
     * This method is used to add the required wrappers
     */

    public void addWrappers(context, jsonData) {

        def toolList = ''

        context.with {

            wrappers {
                BuildNameSetter buildname1 = new BuildNameSetter()
                buildname1.setTemplate('${BUILD_LABEL}' + '_' + '${build_number}')
                buildname1.add(delegate, jsonData)
                (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(delegate, toolList)

                PreBuildCleanup preBuild = new PreBuildCleanup()
                preBuild.setIncludePattern("toBe**/**.*")
                preBuild.setExcludePattern("")
                preBuild.setDeleteDirectories(false)
                preBuild.setCleanupParameter("true")
                preBuild.setDeleteCommand("")
                preBuild.add(delegate, jsonData)
				BuildEnv env = new BuildEnv()
				env.add(delegate, jsonData)
            }
        }
    }

    /*
     * This method is used to add steps in SCM deploy job
     */

    public void addSteps(context, jsonData) {

        context.with {

            steps {

                GeGitUtil.invokeTool(delegate, jsonData)
            }
        }
    }

    /*
     * This method allows to add multiple SCM options
     */

    private void invokeMultiScm(context, jsonData) {
        context.with {
            def deplyScmlist = getSCMList(jsonData.code.scm);

            def scmlistVal = deplyScmlist.values();
            List<Scm> scmlistValues = new ArrayList<>();
            for (def x = 0; x < scmlistVal.size(); x++) {
                scmlistValues.add(scmlistVal.getAt(x));
            }

            def list = scmTypeExists(scmlistValues);




            multiscm {
                if (list.contains(Constants.GITSCM)) {
                    GitScm gitSCM = new GitScm()
                    gitSCM.setScmMap(deplyScmlist)
                    gitSCM.add(delegate, jsonData)
                }

                if (list.contains(Constants.SVNSCM)) {
                    SvnScm svnSCM = new SvnScm()
                    svnSCM.setScmMap(deplyScmlist)
                    svnSCM.add(delegate, jsonData)
                }
            }
            if (list.contains(Constants.TFSSCM)) {
                TfsScm tfs = new TfsScm()
                tfs.setScmMap(deplyScmlist)
                tfs.add(delegate, jsonData)

            }

            if (list.contains(Constants.RTCSCM)) {
                RtcScm rtc = new RtcScm()
                rtc.setScmMap(deplyScmlist)
                rtc.add(delegate, jsonData)

            }
            /*if (list.contains(Constants.IBMRTCSCM)) {
                IbmRtcScm ibmrtc = new IbmRtcScm()
                ibmrtc.setScmMap(deplyScmlist)
                ibmrtc.add(delegate, jsonData)

            }*/
        }
    }

    /*
     * this method prepares the Hasmap of SCM options
     */

    private HashMap<Integer, Scm> getSCMList(scmArr) {

        HashMap<Integer, Scm> scmMap = new HashMap<Integer, Scm>()

        for (int i = 0; i < scmArr.size(); i++) {
            if (null != scmArr.getAt(i).deployRepo && scmArr.getAt(i).deployRepo.equalsIgnoreCase("on")) {
                scmMap.put(new Integer(i), scmArr.getAt(i))
            }


        }
        return scmMap
    }

    /*
     * This method prepares the list of SCM options
     */

    private ArrayList<String> scmTypeExists(scmArr) {
        def list = []
        for (int i = 0; i < scmArr.size(); i++) {
            list.add(scmArr.getAt(i).type)

        }
        return list


    }

    /*
     * This method add each SCM option in the jenkins job
     */

    private void addSCM(context, jsonData) {
        context.with {
            def scmArr = jsonData.code.scm
            for (int i = 0; i < jsonData.code.scm.size(); i++) {

                if (null != scmArr.getAt(i).deployRepo && scmArr.getAt(i).deployRepo.equalsIgnoreCase("on")) {
                    if (jsonData.code.scm.getAt(i).type == Constants.GITSCM) {
                        PropertiesAdder.addStringParam(delegate, 'SCM_BRANCH_' + i, jsonData.code.scm.getAt(i).branch, '')
                    } else if (jsonData.code.scm.getAt(i).type == Constants.TFSSCM) {
                        PropertiesAdder.addStringParam(delegate, 'SCM_BRANCH_' + i, jsonData.code.scm.getAt(i).projPath, '')
                    } else {
                        PropertiesAdder.addStringParam(delegate, 'SCM_BRANCH_' + i, 'NA', '')
                    }

                }

            }
        }
    }

    /*
     * this method is used to add string or password parameters in jenkins job
     */

    private void addProperties(context, jsonData) {
        context.with {
            properties {
                PropertiesAdder.addGitLabConnection(delegate)
                PropertiesAdder.addRebuild(delegate)
                PropertiesAdder.addThrottleJobProperty(delegate)

            }
            configure {
                it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' {
                    PropertiesAdder.addStringParam(delegate, 'IDP_WS', 'NA', 'CUSTOM Workspace')
                    //Adding pipeline build id
                    PropertiesAdder.addStringParam(delegate, 'PIPELINE_BUILD_ID', 'NA', '')
                    //Adding job build  id
                    PropertiesAdder.addStringParam(delegate, 'JOB_BUILD_ID', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'BUILD_LABEL', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'ARTIFACT_NAME', 'NA', '')
                    PropertiesAdder.addNodeParam(delegate, 'SLAVE_NODE', ['ldaproleapp_Jmvn1_14Mr1_Slave'], '')

                    if (jsonData.code && jsonData.code.scm && jsonData.code.scm.size() > 0) {
                        addSCM(delegate, jsonData)

                    }


                }
            }
        }
    }
}
