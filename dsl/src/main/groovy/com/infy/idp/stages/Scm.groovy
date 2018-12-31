/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.stages

import com.infy.idp.creators.*
import com.infy.idp.plugins.buildsteps.Powershell
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
 * This class has the method to create the SCM job with multiple SCMS
 *
 */

class Scm {

    String basepath

    /*
     * This method is used to call other child jobs to create a SCM job with all the configuration
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
                baseJobCreation(factory, jsonData)
            }
        }
    }

    /*
    * this method is used to create the SCM job in jenkins
    */

    private void baseJobCreation(factory, jsonData) {

        String basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

        new BaseJobCreator(

                jobName: basepath + '/' + basepath + Constants.SCMJOBNAMEPOSTFIX,
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
            //this.EnvironmentVariables.add(delegate,jsonData)
            this.addProperties(delegate, jsonData)
            this.invokeMultiScm(delegate, jsonData)
            this.addWrappers(delegate, jsonData)
            // DiscardBuild.invokeDiscardBuildPublisher(delegate)
            addSteps(delegate, jsonData)

            //Optional settings of job
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
                preBuild.setIncludePattern("retrieve*.zip")
                preBuild.setExcludePattern("")
                preBuild.setDeleteDirectories(false)
                preBuild.setCleanupParameter("")
                preBuild.setDeleteCommand("")
                preBuild.add(delegate, jsonData)
				BuildEnv env = new BuildEnv();
				env.add(delegate, jsonData);
            }
        }
    }

    /*
     * This method is used to add steps in jenkins job
     * Steps can be to run bat script,shell script, etc
     */

    public void addSteps(context, jsonData) {

        context.with {

            steps {
                if (jsonData.buildInfo.buildtool == 'siebel') {
                    Powershell pw = new Powershell();
                    def cmd1 = 'New-Item IDP -ItemType Directory -Force \n AutomationScripts/idp_siebel_delta_script.ps1 -SourcePath $env:IDP_WS/Repo -DestinationPath $env:IDP_WS/IDP -JenkinsURL $env:JOB_URL -JenkinsBuildNumber $env:BUILD_NUMBER -ObjectType REPO -UserName idpadmin01 -Password Automation@123 -LogFileName $env:IDP_WS/IDP/log1.log'
                    pw.add(delegate, cmd1.toString())
                    def cmd2 = 'AutomationScripts/idp_siebel_delta_script.ps1 -SourcePath $env:IDP_WS/NonRepo -DestinationPath $env:IDP_WS/IDP -JenkinsURL $env:JOB_URL -JenkinsBuildNumber $env:BUILD_NUMBER -ObjectType NONREPO -UserName idpadmin01 -Password Automation@123 -LogFileName $env:IDP_WS/IDP/log1.log'
                    pw.add(delegate, cmd2.toString())
                    def cmd3 = '$IDP_StaticPath = $env:IDP_WS+\'/IDP_Static\'\n' +
                            'if((Test-Path $IDP_StaticPath))\n' +
                            '{\n' +
                            'Remove-Item IDP_Static -Recurse\n' +
                            '}\n' +
                            'New-Item IDP_Static -ItemType Directory\n' +
                            'copy-item -Path IDP/$env:BUILD_NUMBER\\** -Destination IDP_Static -Recurse -Force\n' +
                            'New-Item IDP_Static\\SRF -ItemType Directory -Force\n'
                    //'copy-item -Path D:/SRF/siebel_sia.srf -Destination IDP_Static\\SRF -Force'
                    pw.add(delegate, cmd3.toString())

                }
                //GeGitUtil.invokeTool(delegate, jsonData)
            }
        }
    }

    /*
     * This method allows to create multiple SCM steps in SCM job
     */

    private void invokeMultiScm(context, jsonData) {
        context.with {

            def scmlist = getSCMList(jsonData.code.scm);
            def scmlistVal = scmlist.values();

            List<Scm> scmlistValues = new ArrayList<>();
            for (def x = 0; x < scmlistVal.size(); x++) {
                scmlistValues.add(scmlistVal.getAt(x));
            }

//			List<Scm> scmlistValues = Arrays.asList(scmlistVal);

            def list = scmTypeExists(scmlistValues);

            multiscm {
                if (list.contains(Constants.GITSCM)) {
                    GitScm gitSCM = new GitScm()
                    gitSCM.setScmMap(scmlist)
                    gitSCM.add(delegate, jsonData)
                }

                if (list.contains(Constants.SVNSCM)) {
                    SvnScm svnSCM = new SvnScm()
                    svnSCM.setScmMap(scmlist)
                    svnSCM.add(delegate, jsonData)
                }

            }
            if (list.contains(Constants.TFSSCM)) {
                TfsScm tfs = new TfsScm()
                tfs.setScmMap(scmlist)
                tfs.add(delegate, jsonData)

            }

            if (list.contains(Constants.RTCSCM)) {
                RtcScm rtc = new RtcScm()
                rtc.setScmMap(scmlist)
                rtc.add(delegate, jsonData)

            }
            /*if (list.contains(Constants.IBMRTCSCM)) {
                IbmRtcScm ibmrtc = new IbmRtcScm()
                ibmrtc.setScmMap(scmlist)
                ibmrtc.add(delegate, jsonData)

            }*/

        }
    }

    /*
     * this method prepares the SCM array for adding multiple SCMS in the job
     */

    private ArrayList<String> scmTypeExists(scmArr) {
        def list = []
        for (int i = 0; i < scmArr.size(); i++) {
            list.add(scmArr.getAt(i).type)
            //if (scmArr.getAt(i).type == type)
            //	return true
        }
        return list
    }

    /*
     * this method is used to get the list of SCMs from SCM array
     */

    private HashMap<Integer, Scm> getSCMList(scmArr) {
//		def Applist = []
        HashMap<Integer, Scm> scmMap = new HashMap<Integer, Scm>()

        for (int i = 0; i < scmArr.size(); i++) {
            if (null != scmArr.getAt(i).appRepo && scmArr.getAt(i).appRepo.equalsIgnoreCase("on")) {
                scmMap.put(new Integer(i), scmArr.getAt(i))
            }


        }
        return scmMap
    }

    /*
     * This method adds each SCM option in jenkins
     */

    private void addSCM(context, jsonData) {
        context.with {
            def scmArr = jsonData.code.scm
            for (int i = 0; i < jsonData.code.scm.size(); i++) {


                if (null != scmArr.getAt(i).appRepo && scmArr.getAt(i).appRepo.equalsIgnoreCase("on")) {
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

