/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/

import { Component, OnInit } from "@angular/core";
import { IdprestapiService } from "../idprestapi.service";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { Router } from "@angular/router";
import { CookieService } from "ngx-cookie";
import { IDPEncryption } from "../idpencryption.service";

@Component({
  selector: "app-basic-info",
  templateUrl: "./basic-info.component.html",
  styleUrls: ["./basic-info.component.css"]
})
export class BasicInfoComponent implements OnInit {
  temp = null;
  env = [];
  testInfo: any = this.Idpdata.data.testInfo;
  deployInfo: any = this.Idpdata.data.deployInfo;
  basicInfo: any = this.Idpdata.data.basicInfo;
  tempObject: any = this.Idpdata.data.checkboxStatus.basicInfo;
  formStatusObject: any = this.Idpdata.data.formStatus;
  operation: any;
  appNames: any = [];
  appNamesDropdown: any = [];
  groupIds = [];
  applicationName = "";
  pipelineName = "";
  data = {};
  userName = "";
  responseData: any;
  osList: any;
  engineList: any = this.Idpdata.IDPDropdownProperties.engineList;
  buildIntervallist: any = this.Idpdata.IDPDropdownProperties.buildIntervallist;
  pipelineJson: any;
  copyEditFlag: any;
  pipeLineNames: any;
  mess: string;
  nameFlag = false;
  pipelineAdminmsg: string;
  pipelineAdminFlag = false;
  sapWorkFlowSystem: any = [{ "name": "Jira", "value": "jira" }];
  typeOfInterval: any = "Select Interval Type";
  hourValue: any = [];
  minuteValue: any = [];
  weekValue: any = [];
  dateValue: any = [];
  intervalOptions: any = ["--Select--", "Day", "Week", "Month"];
  hourOptions: any = ["00", "01", "02", "03", "04",
    "05", "06", "07", "08", "09", "10", "11", "12",
    "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"];
  minuteOptions: any = ["0", "10", "20", "30", "40", "50"];
  weeklyOptions: any = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
  dateOptions: any = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
    "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
    "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"];
  sequenceList: any;
  customPipelineAdmins: any;
    lastEditedAdmin: any;
  customAdminMsgs: any;
  pipeLineAdminNames: any;
  isCustomadmin: any;
  isRmsApp: any;
  rmsAppName: any;
  services: String[]=['service1','service'];
  batchJobs: String[]=['Release1','Harmony'];
  applicat: String[]=['App1','App2', 'App3', 'App4','App5'];
  pipeLineNamesRms: any;
  rmsOperation:any;
  ngOnInit() {
  }

  /* Constructor */
  constructor(
    public Idpdata: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private idpencryption: IDPEncryption,
    private router: Router,
    private _cookieService: CookieService
  ) {
    this.constructorFunction();
  }

  // Constructor to initialize with empty json
  constructorFunction() {
    this.Idpdata.freezeNavBars = false;
    if (this.testInfo.testEnv === undefined) {
        this.testInfo.testEnv = [];
    }
    if (this.deployInfo.deployEnv === undefined) {
        this.deployInfo.deployEnv = [];
    }
    if (this.Idpdata.data.backUp.deployInfo.deployEnv === undefined) {
        this.Idpdata.data.backUp.deployInfo.deployEnv = [];
    }
    if (this.Idpdata.data.backUp.testInfo.testEnv === undefined) {
        this.Idpdata.data.backUp.testInfo.testEnv = [];
    }

    if(this.Idpdata.data.basicInfo.jiraProjectKey===undefined ||
         this.Idpdata.data.basicInfo.jiraProjectKey==="" ||
          this.Idpdata.data.basicInfo.jiraProjectKey=== null){
  
        this.basicInfo.JiraALM='off';
      }

    this.basicInfo.engine = "Jenkins Workflow";

    if (this.basicInfo.masterSequence !== undefined && this.basicInfo.masterSequence === "workflow") {
        this.Idpdata.createWorkflowSequenceflag = true;
      } else {
        this.basicInfo.masterSequence = "pipeline";
        this.Idpdata.createWorkflowSequenceflag = false;
      }
    this.init();

  }
  addBuildInterval() {
    this.basicInfo.buildInterval.event.push({ "type": "--Select--", "hour": "00", "minute": "00" });
  }
  deleteBuildInterval(i) {
    this.basicInfo.buildInterval.event.splice(i, 1);
  }

  clearProjectKey(){
    this.basicInfo.jiraProjectKey='';
    return 'off';
  }

  // Initializes server list, username by fetching from services
  init() {
    this.basicInfo.userName = this.Idpdata.idpUserName;
    const data = {
        "username": this.Idpdata.idpUserName
    };
        this.tempObject.isCustomadmin = false;
        //this.Idpdata.isRmsApp = false;
        this.Idpdata.PagePersmission.basic = false;
        this.Idpdata.PagePersmission.code = false;
        this.Idpdata.PagePersmission.build = false;
        this.Idpdata.PagePersmission.deploy = false;
        this.Idpdata.PagePersmission.test = false;
    this.operation = this.Idpdata.operation;
	this.rmsOperation = this.Idpdata.operation;
    // console.log(this.operation);

    this.selectedPipeType(this.basicInfo.pipelineType);
    if (this.Idpdata.devServerURL !== "") {
        this.getApplicationName(data);
    } else {
        this.IdprestapiService.getData()
        .then(response => {
            try {
            if (response) {
                this.Idpdata.devServerURL = "http://localhost:8889/idprest";
                
               // this.Idpdata.devServerURL = "http://localhost:8889/idprest";
                this.Idpdata.subscriptionServerURL = response.json().idpsubscriptionurl;
                this.Idpdata.IDPDashboardURL = response.json().idpdashboardurl;
                this.Idpdata.IDPLink = response.json().IDPLink;
                this.Idpdata.serverUrl = response.json().tfsServerUrl;
                this.Idpdata.uName = response.json().uName;
                this.Idpdata.pass = response.json().pass;
                if (this._cookieService.get("access_token")) {
                this.getApplicationName(data);
                this.IdpService.getDetails();
                }
            }
            } catch (e) {
            console.log(e);
            }
        });
    }

    this.groupIds = [{ "groupName": "abc", "groupId": "g1" }, { "groupName": "abfdhc", "groupId": "gfh1" }];
    this.osList = [{
        "name": "Windows",
        "value": "windows"
    },
    {
        "name": "Linux",
        "value": "linux"
    },
    ];
    this.buildIntervallist = [
        {
        "name": "Day of the month (1-31)",
        "value": "DayOfMonth"
        },
        {
        "name": "Month (1-12)",
        "value": "Month"
        },
        {
        "name": "Day of week (0-7 , 0 and 7 being Sunday)",
        "value": "DayOfWeek"
        }
    ];
    this.sequenceList = [{
        "name": "Pipeline Sequence",
        "value": "pipeline"
    },
    ];
  }

  // Populating list of configured applications and other details
  getApplicationDetails(appName) {
    this.IdprestapiService.getApplicationDetails(appName)
        .then(response => {
        if (response) {
            const resp = response.json().resource;
            let parsed;
            try {
            parsed = JSON.parse(resp);
            if (parsed) {
                this.Idpdata.application = parsed.appJson;
                this.Idpdata.application = this.idpencryption.doubleEncryptApplicationDetailsPassword(this.Idpdata.application);
                const application = this.Idpdata.application;
                if (!(application.artifactToStage === undefined
                || application.artifactToStage.artifactRepoName === "na"
                || application.artifactToStage.artifactRepoName === "")) {
                this.Idpdata.artifactVariable = true;
                this.Idpdata.artifactAppVariable = true;
				if(application.artifactToStage.artifactRepoName==="docker"){
					this.Idpdata.isDockerRegistry=true;
				}else{
					this.Idpdata.isDockerRegistry=false;
				}
                } else {
                this.Idpdata.artifactVariable = false;
                this.Idpdata.artifactAppVariable = false;
                this.Idpdata.isDockerRegistry = false;
                }
            }
            } catch (e) {
            alert("Failed to get Application Details");
            console.log(e);
            }
        }
        });
  }

  // Fetches Pipeline Names for the selected application
  getPipelineNames(appName) {
    this.IdprestapiService.getJobs(appName)
        .then(response => {
        try {
         // console.log('appName ' + appName);
          const pipetemp = JSON.parse(this.idpencryption.decryptAES(response.json().resource).toLowerCase());
          if (typeof pipetemp !== "string") {
            this.pipeLineNames = JSON.parse(this.idpencryption.decryptAES(response.json().resource));
            if (this.basicInfo.pipelineName !== undefined && this.basicInfo.pipelineName !== null && this.basicInfo.pipelineName !== "") {
                this.checkJobName(this.basicInfo.pipelineName);
            }
            }
        } catch (e) {
            console.log("Failed to get Pipeline Names");
            console.log(e);
        }
        });
  }
  // JOb creation with unique name for each application
  checkJobName(pipeName) {
    if (this.pipeLineNames) {
        for (let i = 0; i < this.pipeLineNames.pipelines.length; i++) {
        if (pipeName !== undefined && this.pipeLineNames.pipelines[i].pipelineName !== undefined
            && pipeName.toLowerCase() === this.pipeLineNames.pipelines[i].pipelineName.toLowerCase()
            && this.formStatusObject.operation !== "edit") {
            this.mess = "This name already exists.";
            this.nameFlag = true;
            break;
        } else if (pipeName !== this.pipeLineNames.pipelines[i].pipelineName) {
            this.mess = "";
            this.nameFlag = false;
        }
        }
    }
  }

  // Fetches application Names
  getApplicationName(data) {
        let oprMethod = "jobid1";
        if (this.Idpdata.operation === undefined || this.Idpdata.operation === "") {
            oprMethod = "create";
        }
        this.IdprestapiService.checkApplicationNames(data, oprMethod)
        .then(response => {
        try {
            console.log("string"+JSON.stringify(response))
            if (response) {
                
                console.log(response);
            const appDetails = JSON.parse(response.json().resource);
           // this.appNames = appDetails.applicationNames;
            this.appNames = appDetails.applicationNames;
            
            
            //Populate the names in a array of strings for dropdown
            this.appNamesDropdown=[];
            for(let app of this.appNames){
                this.appNamesDropdown.push(app);
               
                
            }
             console.log('fine');
			
			
            }
        } catch (e) {
            console.log(e);
            
            alert("Failed while getting applications names");
        }
        });
    if (typeof (Storage) !== "undefined") {
        console.log(typeof (Storage));
        this.operation = this.Idpdata.operation;
        if (this.operation === "copy" || this.operation === "edit") {
            // const data = localStorage.getItem("appName");
            // this.Idpdata.appName = data;
            // this.Idpdata.isRepoSelected = true;
            // this.IdprestapiService.checkForApplicationType(data).then(response => {
            //     try {
            //         if (response) {
            //             if (response.json().errorMessage === null && response.json().resource !== "") {
            //                 if (response.json().resource === "true") {
            //                     this.Idpdata.isSAPApplication = true;
            //                 } else {
            //                     this.Idpdata.isSAPApplication = false;
            //                 }
            //                 this.copyEditOperation();
            //                 this.Idpdata.operation = "off";
            //             } else {
            //                 alert("failed to verify application Type");
            //             }
            //         }
            //     } catch (e) {
            //         console.log(e);
            //         alert("Failed to verify application type");
            //     }
            // });
            const data = localStorage.getItem("appName");
            this.Idpdata.appName = data;
            this.copyEditOperation();

        } else {
        this.Idpdata.isRepoSelected = false;
        console.log(localStorage);
        localStorage.clear();
        this.operation = "off";
        this.Idpdata.operation = "off";
        }
    }
  }

  // Checks if build Interval is selected or not
  checkCheckBox() {
    if ((this.Idpdata.data.basicInfo.buildInterval.buildIntervalValue >= 0
        && this.Idpdata.data.basicInfo.buildInterval.buildInterval !== "")
        || (this.Idpdata.data.basicInfo.buildInterval.event !== undefined
        && this.Idpdata.data.basicInfo.buildInterval.event[0].type !== "--Select--")) {
        this.tempObject.buildIntervalCheck = "on";
    }
    if(this.Idpdata.data.basicInfo.jiraProjectKey!=='' &&
      this.Idpdata.data.basicInfo.jiraProjectKey!==undefined){
        this.tempObject.JiraALM='on';
      }
        if (this.basicInfo.customPipelineAdmins &&
            this.basicInfo.customPipelineAdmins.length > 0 &&
            this.basicInfo.customPipelineAdmins[0] !== undefined) {
            this.tempObject.checkCustomPipelineAdmin = "on";
        }
  }

  // On copy and edit, fetches all info and updates/ creates it
  copyEditOperation() {
    this.tempObject.copyEditFlag = true;
    this.applicationName = localStorage.getItem("appName");
    this.pipelineName = localStorage.getItem("pipeName");
    this.userName = this.Idpdata.idpUserName;
    this.data = {
        "applicationName": this.applicationName,
        "pipelineName": this.pipelineName,
        "userName": this.userName
    };
    // this.appNames = [];
    // this.appNames.push(this.applicationName);
    this.IdprestapiService.getPipelineDetails(this.data)
        .then(response => {
        console.log(new Date().toUTCString(), "Pipeline details retrieved");
        try {
            this.responseData = this.idpencryption.decryptAES(response.json().resource);
            const resp = JSON.parse(this.responseData);
            if (resp.pipelineJson.basicInfo.masterSequence === undefined ||
            resp.pipelineJson.basicInfo.masterSequence === "pipeline") {
            resp.pipelineJson = this.idpencryption.doubleEncryptPassword(resp.pipelineJson);
            resp.pipelineJson.basicInfo.masterSequence = "pipeline";
            }
            this.pipelineJson = resp.pipelineJson;
            this.Idpdata.data.basicInfo = this.pipelineJson.basicInfo;
            this.basicInfo = this.Idpdata.data.basicInfo;
            if (this.basicInfo.masterSequence === "pipeline" || this.basicInfo.masterSequence === "") {
            this.Idpdata.data.code = this.pipelineJson.code;
            this.Idpdata.data.buildInfo = this.pipelineJson.buildInfo;
            this.Idpdata.data.deployInfo = this.pipelineJson.deployInfo;
            this.Idpdata.data.testInfo = this.pipelineJson.testInfo;
            this.basicInfo.masterSequence = "pipeline";
            this.Idpdata.createWorkflowSequenceflag = false;
            } else {
                this.Idpdata.workflowData.workflowSequence = this.pipelineJson.pipelines;
                const workflowTemp = [];
                for (let i = 0; i < this.pipelineJson.pipelines.length; i++) {
                  workflowTemp.push({});
                }
                this.Idpdata.workflowData.workflowSequenceTemp = workflowTemp;
                this.Idpdata.createWorkflowSequenceflag = true;
              }
            this.Idpdata.data.formStatus.operation = this.operation;
            this.getPipelineNames(this.basicInfo.applicationName);
            if (this.basicInfo.masterSequence === undefined || this.basicInfo.masterSequence === "pipeline") {
            this.checkEnvNamesForCopyEdit(this.basicInfo.applicationName);
            }
            if (this.Idpdata.data.code.buildScript === undefined) {
            this.Idpdata.data.code.buildScript = [{}, {}];
            }

            if (this.basicInfo.jiraProjectKey === undefined  || this.basicInfo.jiraProjectKey === "") {
                this.tempObject.JiraALM = "off";
            }
            this.Idpdata.data.formStatus.basicInfo.appNameStatus = "1";
            if (this.Idpdata.data.buildInfo.buildtool !== undefined
            && this.Idpdata.data.buildInfo.buildtool !== null
            && this.Idpdata.data.buildInfo.buildtool !== "") {
            this.Idpdata.data.formStatus.buildInfo.buildToolStatus = "1";
            }
            this.formStatusObject.operation = this.operation;
            this.checkCheckBox();
            this.getApplicationDetails(this.applicationName);
            if (this.basicInfo.masterSequence === undefined || this.basicInfo.masterSequence === "pipeline") {
            this.Idpdata.allFormStatus = {
                "basicInfo": true,
                "codeInfo": true,
                "buildInfo": true,
                "deployInfo": true,
                "testInfo": true
            };
            } else {
                this.Idpdata.allFormStatus = {
                  "basicInfo": true,
                  "workflowInfo": true
                };

          }
            if (this.operation === "copy") {
            this.basicInfo.pipelineStatus = "copy";
            const pipelineName = this.basicInfo.pipelineName + "_Copy";
            this.basicInfo.pipelineName = pipelineName;
            } else if (this.operation === "edit") {
            this.basicInfo.pipelineStatus = "edit";
            this.formStatusObject.operation = this.operation;
            } else {
            this.Idpdata.data.formStatus.operation = "off";
            this.formStatusObject.operation = "off";
            }
            // for setting landscapeName
            if (this.Idpdata.isSAPApplication) {
                if (this.Idpdata.data.buildInfo.castAnalysis.landscapeName !== ""
                || this.Idpdata.data.buildInfo.castAnalysis.landscapeName !== "") {
                    this.Idpdata.landscapeName = this.Idpdata.data.buildInfo.castAnalysis.landscapeName;
                    this.getLandscapeNamesForSap();
                    this.getVariantsForSap();
                } else {
                    this.Idpdata.landscapeName = "";
                }
            }
            // Fetched Artifact details required in build info
            if (this.Idpdata.data.buildInfo.artifactToStage !== undefined
            && this.Idpdata.data.buildInfo.artifactToStage.artifact
            !== undefined && this.Idpdata.data.buildInfo.artifactToStage.artifact !== "") {
            this.Idpdata.artifactVariable = true;
            this.Idpdata.artifactAppVariable = true;
            } else {
            this.Idpdata.artifactVariable = false;
            this.Idpdata.artifactAppVariable = false;
            }
        } catch (e) {
            alert("Failed to get the PipeLine Details");
            console.log("Err msg" + e);
        }
        });
        // Fetches Pipeline List for edit/copy operation
    this.IdprestapiService.getPipelineList(this.applicationName).then(response => {
        try {
        console.log(response);
        const resp = response.json();
        const errorMsg = resp.errorMessage;
        this.Idpdata.data.pipelineList = [];
        const pipData = JSON.parse(this.idpencryption.decryptAES(resp.resource));
        let c = 1;
        for (const i of pipData.pipelines) {
            this.Idpdata.data.pipelineList.push({ "id": c, "itemName": i.pipelineName });
            c = c + 1;
        }
        } catch (e) {
        console.log(e);
        console.log("Failed to get PipelineList");
        }
    });
    this.IdprestapiService.getPipelinePermission(this.data)
        .then(response => {
                this.responseData = this.idpencryption.decryptAES(response.json().resource);
                const resp = JSON.parse(this.responseData);
                console.log("permission");
                console.log(resp);
                let permissionKey = [];
                permissionKey = resp.permissions;



                if (resp.permissions.length === 0) {
                    this.tempObject.isCustomadmin = false;
                    this.Idpdata.PagePersmission.basic = false;
                    this.Idpdata.PagePersmission.code = false;
                    this.Idpdata.PagePersmission.build = false;
                    this.Idpdata.PagePersmission.deploy = false;
                    this.Idpdata.PagePersmission.test = false;

                } else {
                    this.Idpdata.PagePersmission.basic = true;
                    this.Idpdata.PagePersmission.code = true;
                    this.Idpdata.PagePersmission.build = true;
                    this.Idpdata.PagePersmission.deploy = true;
                    this.Idpdata.PagePersmission.test = true;
                    this.tempObject.isCustomadmin = true;
                    for (let j = 0; j < resp.permissions.length; j++) {
                        if (resp.permissions[j] === ("EDIT_BASIC_INFO")) {

                            this.Idpdata.PagePersmission.basic = false;

                        }
                        if (resp.permissions[j] === ("EDIT_CODE_INFO")) {
                            this.Idpdata.PagePersmission.code = false;

                        }
                        if (resp.permissions[j] === ("EDIT_BUILD_INFO")) {
                            this.Idpdata.PagePersmission.build = false;

                        }
                        if (resp.permissions[j] === ("EDIT_DEPLOY_INFO")) {
                            this.Idpdata.PagePersmission.deploy = false;

                        }
                        if (resp.permissions[j] === ("EDIT_TEST_INFO")) {
                            this.Idpdata.PagePersmission.test = false;


                        }
                    }
                }
            });
  }

    // fetch variants for SAP application
    getVariantsForSap() {
        const data = {
            "landscape_name": this.Idpdata.landscapeName,
            "application_name": this.Idpdata.data.basicInfo.applicationName
        };

        if (data.landscape_name !== "") {

            this.IdprestapiService.getVariantNamesForSap(data).then(response => {
                try {
                    if (response) {
                        if (response.json().resource !== "{}" && response.json().resource !== null) {
                            const temp = response.json().resource;
                            this.Idpdata.VariantNameList = [];
                            if (JSON.parse(temp).names.length !== 0) {
                                this.Idpdata.VariantNameList = JSON.parse(temp).names;
                            } else {
                                alert("Variants are not available for application " + data.application_name);
                            }
                        } else {
                            alert("failed to get variant Names");
                        }
                    } else {
                        alert("failed to get variant Names");
                    }

                } catch (e) {
                    console.log(e);
                    alert("failed while getting varianst");
                }
            });
        }
    }

    // fetch SAP landcape names for the application
    getLandscapeNamesForSap() {
        const applicationName = this.Idpdata.data.basicInfo.applicationName;
        this.IdprestapiService.getLandscapesForSap(applicationName).then(response => {
            try {
                if (response) {
                    if (response.json().resource !== "{}" && response.json().resource !== null) {
                        const temp = response.json().resource;
                        const operation = this.Idpdata.operation;
                        if (operation === "copy" || operation === "edit") {
                            // for copy or edit pipeline
                            const landscapeName = this.Idpdata.landscapeName;
                            this.Idpdata.SAPEnvList = [];
                            const test = JSON.parse(temp).landscapes;
                            for (let i = 0; i < test.length; i++) {
                                this.Idpdata.SAPEnvList.push(test[i]);
                            }
                            this.Idpdata.landscapeName = landscapeName;
                        } else {
                            this.Idpdata.SAPEnvList = JSON.parse(temp).landscapes;
                        }
                    } else {
                        alert("failed to get landscapes Names");
                    }
                } else {
                    alert("failed to get landscapes Names");
                }
            } catch (e) {
                console.log(e);
                alert("failed while getting landscapes Names");
            }
        });
    }

	getAppmartDetails(){
      //Fetches details from appmart
	  if(this.Idpdata.isRmsApp === true){
		  //alert(this.applicationName)
		  if(this.basicInfo.applicationName === ""){
			  this.basicInfo.applicationName=this.applicationName;
		  }
		this.IdprestapiService.getRmsApplicationName(this.basicInfo.applicationName).then(response => {
			try {
                if (response) {
					this.rmsAppName= response.json().resource;
				}
			else {
                        alert("failed to get rms application name");
                    }
                
            } catch (e) {
                alert("failed to get rms application name");
            }
            
        });
		this.IdprestapiService.getAppmartDetails(this.Idpdata.idpUserName).then(response => {
			try {
                if (response) {
                    if (response.json().errorMessage === null && response.json().resource !== "") {
                        console.log(JSON.stringify(response.json().resource));
						const appmartdetails = JSON.parse(response.json().resource);
						this.Idpdata.dependencies= [];
						
						for(let obj of appmartdetails){
							console.log(obj);
							console.log(this.basicInfo.applicationName);
							if(obj.applicationName === this.rmsAppName){
								this.Idpdata.dependencies = obj.dependencies;
								this.selectedPipeType(this.basicInfo.pipelineType);
								break;
							}
						}
						
                    } else {
                        alert("failed to verify application Type");
                    }
                }
            } catch (e) {
                alert("failed during verifying the application type");
            }
            
        });
		}
  }
  // Gives the app selected by the user
  selectedApp1() {
    const data = this.basicInfo.applicationName;
    this.Idpdata.appName = data;
    this.Idpdata.loading = true;
    this.selectedApp();
    this.getApplicationDetails(data);


    }

 
  selectedPipeType(pipelineType){
      //Populate pipeline names for RMS based on type of pipeline chosen
if(pipelineType==='Application'){
	for(let obj of this.Idpdata.dependencies){
		if(obj.pipelineType === "App"){
		this.pipeLineNamesRms=obj.pipelineList;
		break;
		}
	}
}else if(pipelineType === 'Services'){
    for(let obj of this.Idpdata.dependencies){
		if(obj.pipelineType === "Service"){
		this.pipeLineNamesRms=obj.pipelineList;
		break;
		}
	}
}else if(pipelineType === 'BatchJob'){
    for(let obj of this.Idpdata.dependencies){
		if(obj.pipelineType === "Batch"){
		this.pipeLineNamesRms=obj.pipelineList;
		break;
		}
	}
}
  }
  // Fetches the pipeline details for the application selected by user
  selectedApp() {
    const applicationName = this.basicInfo.applicationName;
    this.Idpdata.data.checkboxStatus.buildInfo.dependentPipelineList = [];
    if (this.basicInfo.applicationName !== undefined &&
        this.basicInfo.applicationName !== ""
        && this.basicInfo.applicationName !== null) {
        this.Idpdata.data.formStatus.basicInfo.appNameStatus = "1";
    } else {
        this.Idpdata.data.formStatus.basicInfo.appNameStatus = "0";
    }
    if (this.basicInfo.applicationName) {
        console.log("getting pipeline names");
        this.getPipelineNames(this.basicInfo.applicationName);
            if (this.Idpdata.isSAPApplication) {
                if (this.Idpdata.setSAPdata()) {
                    this.testInfo = this.Idpdata.data.testInfo;
                    this.deployInfo = this.Idpdata.data.deployInfo;
                    this.basicInfo = this.Idpdata.data.basicInfo;
                    this.tempObject = this.Idpdata.data.checkboxStatus.basicInfo;
                    this.formStatusObject = this.Idpdata.data.formStatus;
                    this.basicInfo.applicationName = applicationName;
                    this.constructorFunction();
                    this.getPipelineNames(this.basicInfo.applicationName);
                }
            }
        this.getEnvironmentNames();
    }

  }

  // Fetches the env configured for application selected
  getEnvironmentNames() {
    this.IdprestapiService.getEnvironmentNames(this.basicInfo.applicationName)
        .then(response => {
        try {
            console.log(response);
            if (response.json().errorMessage === null && response.json().resource !== "{}") {
            this.temp = JSON.parse(response.json().resource);
            this.env = this.temp.envNames;
            this.testInfo.testEnv = [];
            this.deployInfo.deployEnv = [];
            this.Idpdata.data.backUp.deployInfo.deployEnv = [];
            this.Idpdata.data.backUp.testInfo.testEnv = [];
            this.Idpdata.data.deployInfo = this.deployInfo;
            this.Idpdata.data.testInfo = this.testInfo;
            if (this.env.length !== 0) {
                for (let j = 0; j < this.env.length; j++) {
                if (this.env[j]) {
                    this.testInfo.testEnv.push({
                    "envName": this.env[j],
                    "envFlag": "off",
                    });
                    this.Idpdata.data.backUp.testInfo.testEnv.push({
                    "envName": this.env[j],
                    "envFlag": "off",
                    });
                }
                }
                for (let i = 0; i < this.env.length; i++) {
                if (this.env[i]) {
                    this.deployInfo.deployEnv.push({
                    "envName": this.env[i],
                    "envFlag": "off"

                    });
                    this.Idpdata.data.backUp.deployInfo.deployEnv.push({
                    "envName": this.env[i],
                    "envFlag": "off"

                    });
                }
                }
                this.Idpdata.data.deployInfo = this.deployInfo;
                this.Idpdata.data.testInfo = this.testInfo;
                this.Idpdata.data.checkboxStatus.deployInfo = {};
                this.Idpdata.data.checkboxStatus.testInfo = {};
            }
            }
        } catch (e) {
            alert("failed to get Environments");
            console.log(e);
        }
        this.Idpdata.loading = false;
        });
  }

  changeNavigationLinks() {
    if (this.basicInfo.masterSequence !== undefined && this.basicInfo.masterSequence === "pipeline") {
        this.Idpdata.createWorkflowSequenceflag = false;
      } else if ( this.basicInfo.masterSequence !== undefined && this.basicInfo.masterSequence === "workflow") {
        this.Idpdata.createWorkflowSequenceflag = true;
      } else {
        this.Idpdata.createWorkflowSequenceflag = false;
      }

  }

  // Clears the data on resetting
  resetData() {
    const x = confirm("Are you sure to Reset ?");
    if (x) {
        const appName = this.basicInfo.applicationName;
        const pipelineName = this.basicInfo.pipelineName;
        const masterSeq = this.basicInfo.masterSequence;
        // If not edit or copy, reset everything
        if (this.Idpdata.data.formStatus.operation !== "edit"
        && this.Idpdata.data.formStatus.operation !== "copy") {
        this.basicInfo = {
            "additionalMailRecipients": {
            "applicationTeam": "",
            "emailIds": ""
            },
            "applicationName": "",
            "buildInterval": {
            "buildInterval": "",
            "buildIntervalValue": 0,
            "pollSCM": "off",
            "buildAtSpecificInterval": "off",
            "event": [{ "type": "--Select--", "hour": "00", "minute": "00" }]

            },
            "buildServerOS": "",
            "engine": "Jenkins Workflow",
            "pipelineName": "",
            "groupName": "",
            "jiraProjectKey": "",
            "groupId": "",
            "masterSequence": ""
        };
        this.Idpdata.data.formStatus.basicInfo.appNameStatus = "0";
        this.Idpdata.data.formStatus.basicInfo.groupIdStatus = "0";

        } else {
        if (this.Idpdata.data.formStatus.operation !== "edit") {
            this.basicInfo = {
            "additionalMailRecipients": {
                "applicationTeam": "",
                "emailIds": ""
            },
            "applicationName": appName,
            "buildInterval": {
                "buildInterval": "",
                "buildIntervalValue": 0,
                "pollSCM": "off",
                "buildAtSpecificInterval": "off",
                "event": [{ "type": "--Select--", "hour": "00", "minute": "00" }]
            },
            "buildServerOS": "",
            "engine": "Jenkins Workflow",
            "pipelineName": "",
            "groupName": "",
            "groupId": "",
            "masterSequence": masterSeq
            };

        } else { // If edit, retain the values
            const buildserver = this.basicInfo.buildServerOS;
            this.basicInfo = {
            "additionalMailRecipients": {
                "applicationTeam": "",
                "emailIds": ""
            },
            "applicationName": appName,
            "buildInterval": {
                "buildInterval": "",
                "buildIntervalValue": 0,
                "pollSCM": "off",
                "buildAtSpecificInterval": "off",
                "event": [{ "type": "--Select--", "hour": "00", "minute": "00" }]
            },
            "buildServerOS": buildserver,
            "engine": "Jenkins Workflow",
            "pipelineName": pipelineName,
            "groupName": "",
            "groupId": "",
            "masterSequence": masterSeq
            };

        }
        this.checkJobName(this.basicInfo.pipelineName);
        }
        this.Idpdata.data.basicInfo = this.basicInfo;

        this.Idpdata.data.checkboxStatus.basicInfo = {};
        this.tempObject = this.Idpdata.data.checkboxStatus.basicInfo;
    }
  }
  // On continue, save the basic Info details and navigate to codeInfo
  go() {
    if (this.nameFlag === false &&  this.pipelineAdminFlag === false) {
        this.Idpdata.data.basicInfo = this.basicInfo;
        this.Idpdata.data.masterJson["basicInfo"] = this.basicInfo;
        console.log(this.Idpdata.data);
        // window.location.href='#/code/';
        if (this.basicInfo.masterSequence !== "workflow") {
        this.router.navigate(["/createConfig/codeInfo"]);
        } else {
          this.router.navigate(["/createConfig/workflowInfo"]);
        }
      }
    }
    openCustomAdminField() {
        this.basicInfo.customPipelineAdmins = [];
        this.basicInfo.customAdminMsgs = [];
        this.addCustomAdmin();
        return "on";

    }

    closeCustomAdminField() {
        this.basicInfo.customPipelineAdmins = null;
        this.basicInfo.customAdminMsgs = null;
        return "off";

    }

    addCustomAdmin() {
        this.basicInfo.customPipelineAdmins.push({
            "editBasic": "n",
            "editCode": "n",
            "editBuild": "n",
            "editDeploy": "n",
            "editTest": "n"
        });
        this.basicInfo.customAdminMsgs.push({});
    }
    removeCustomAdmin(index) {
        // add condition
        const adminName = (this.basicInfo.customPipelineAdmins[index].adminName === undefined) ?
            "entry" : "'" + this.basicInfo.customPipelineAdmins[index].adminName + "'";
        const x = confirm("Are you sure to remove " + adminName + " as custom pipeline admin?");
        if (x) {
            this.basicInfo.customPipelineAdmins.splice(index, 1);
            this.basicInfo.customAdminMsgs.splice(index, 1);
        }
    }
  testfunction() {
    this.basicInfo.buildInterval.buildIntervalValue = "";
  }

  // Clear build interval values
  clearBuildInterval() {
    this.basicInfo.buildInterval.buildInterval = "";
    this.basicInfo.buildInterval.buildIntervalValue = "";
    this.basicInfo.buildInterval.pollSCM = "off";
    return "off";
  }

  clearPollALM() {
    this.basicInfo.buildInterval.almTool = "";
    this.basicInfo.buildInterval.projectKey = "";
    return "off";
  }
  ResetALMTool() {
    this.basicInfo.buildInterval.projectKey = "";
  }
  setFormStatus(data) {
    this.Idpdata.allFormStatus.basicInfo = data;
  }

  // Get Env names on copy/edit in deployInfo.deployEnv and testInfo.testEnv
  checkEnvNamesForCopyEdit(applicationName) {
    this.IdprestapiService.getEnvironmentNames(applicationName)
        .then(response => {
        try {

            if (response.json().errorMessage === null && response.json().resource !== "{}") {
            this.temp = JSON.parse(response.json().resource);
            this.env = this.temp.envNames;
            this.testInfo = this.Idpdata.data.testInfo;
            this.deployInfo = this.Idpdata.data.deployInfo;
            if (this.env.length !== 0) {
                if (this.testInfo.testEnv !== undefined && this.testInfo.testEnv.length !== 0) {
                for (let j = this.testInfo.testEnv.length; j < this.env.length; j++) {
                    this.Idpdata.data.testInfo.testEnv.push({
                    "envName": this.env[j],
                    "envFlag": "off",
                    });
                    this.Idpdata.data.backUp.testInfo.testEnv.push({
                    "envName": this.env[j],
                    "envFlag": "off",
                    });
                }
                }
                if (this.deployInfo.deployEnv !== undefined && this.deployInfo.deployEnv.length !== 0) {
                for (let i = this.deployInfo.deployEnv.length; i < this.env.length; i++) {
                    this.Idpdata.data.deployInfo.deployEnv.push({
                    "envName": this.env[i],
                    "envFlag": "off"

                    });
                    this.Idpdata.data.backUp.deployInfo.deployEnv.push({
                    "envName": this.env[i],
                    "envFlag": "off"
                    });
                }
                }
            }
            }
        } catch (e) {
            console.log(e);
            alert("failed to get Environments");
            console.log(e);
        }
        this.Idpdata.loading = false;
        });
  }

  // Fetches the pipeline Admins
  getPipelineAdminNames(appName, pipelineAdminName, j) {
    this.IdprestapiService.getPipelineAdmins(appName)
        .then(response => {
        try {
            const pipetemp = JSON.parse(this.idpencryption.decryptAES(response.json().resource).toLowerCase());
            if (typeof pipetemp !== "string") {
            this.pipeLineAdminNames = JSON.parse(this.idpencryption.decryptAES(response.json().resource));
            if (this.pipeLineAdminNames) {
                for (let i = 0; i < this.pipeLineAdminNames.length; i++) {
                if (pipelineAdminName !== undefined && this.pipeLineAdminNames[i] !== undefined
                    && pipelineAdminName.toLowerCase() === this.pipeLineAdminNames[i].toLowerCase()
                ) {
                    this.pipelineAdminFlag = true;
                    break;
                } else if (pipelineAdminName !== this.pipeLineAdminNames[i]) {
                    this.pipelineAdminFlag = false;
                }
                }
            }
            }
        } catch (e) {
            console.log(e);
        }
        });
  }
  // Checks if logged in user is pipeline admin and allows for pipeline creation
  checkPipelineAdminName(pipelineAdminName, j) {
        this.lastEditedAdmin = pipelineAdminName;
    this.getPipelineAdminNames(this.basicInfo.applicationName, pipelineAdminName, j);
  }
  isCPASelectedOnce(admin) {

    /**
    * Custom Pipeline Admin should have access to atleast one page.
    * return true  : if user selected atleast one page
    * return false : else
    */

    return ((admin.editBasic === "y") || (admin.editCode === "y") || (admin.editBuild === "y")
        || (admin.editDeploy === "y") || (admin.editTest === "y"));

  }

  isCPASelectedOnceAllUser(isFormValid: boolean) {

    /**
    * Function Check the form is valid and All the Users of Custom Pipeline Admins
    * Select one page.
    *
    * if isFormValid set False (or) CPA didnt select any page.
    *    return true
    * else : false
    */

    if (!isFormValid) {
        return false;
        }
        if (this.basicInfo !== undefined && this.basicInfo !== null) {
            if (this.basicInfo.customPipelineAdmins !== undefined && this.basicInfo.customPipelineAdmins !== null) {
              for (let i = 0; i < this.basicInfo.customPipelineAdmins.length; i++) {
               const isSelected: boolean = this.isCPASelectedOnce(this.basicInfo.customPipelineAdmins[i]);
                if (!isSelected) {
                  return false;
               } // if any one CPA didnt select any page.
              }
            }

    return true;
  }
}
}
