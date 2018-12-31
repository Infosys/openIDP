import { Component, OnInit, ViewChild, Input } from "@angular/core";
import { TranslateService } from "ng2-translate";
import { IdpService } from "../idp-service.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router } from "@angular/router";
import { IdpdataService } from "../idpdata.service";
import { DataTable, DataTableResource } from "angular-2-data-table";
import { ActivatedRoute } from "@angular/router";
import {
  TreeviewModule, TreeviewConfig, TreeviewItem, TreeviewHelper, DownlineTreeviewItem, TreeviewI18n, TreeviewComponent,
  TreeviewEventParser, OrderDownlineTreeviewEventParser
} from "ngx-treeview";
import * as _ from "lodash";
import { IDPEncryption } from "../idpencryption.service";
declare var jQuery: any;
@Component({
  selector: "app-trigger",
  templateUrl: "./triggerPipeline.component.html",
  styleUrls: ["./triggerPipeline.component.css"],
  providers: [
    { provide: TreeviewEventParser, useClass: OrderDownlineTreeviewEventParser }
  ]
})
export class TriggerComponent implements OnInit {
  @ViewChild(TreeviewComponent) treeviewComponent: TreeviewComponent;
  @ViewChild("modalforAlert") button;
  @ViewChild("modalforArtifactAlert") buttonArtifact;
  @ViewChild("modalforSubmitAlert") buttonSubmitArtifact;
  @ViewChild("modalforTrigger") triggerButton;
  @ViewChild("modalforAlertDBDep") dbButton;
  @ViewChild("modalforReqSCMAlert") triggerButtonRqdSCM;
  // save button both for schedule and workflow (master pipeline) feature
  @ViewChild("modalforSave") saveButton;
  @Input() workflowSequenceIndexI: number;
  @Input() workflowSequenceIndexJ: number;
  @Input() workflowSequenceIndexK: number;

  isParameterDisabled = false;
  data = { "applicationName": "", "pipelineName": "", "userName": "" };
  SAPEnvList: any = [];
  deployEnvList: any;
  error: any = "";
  devEnvList: any = [];
  userStoryArray: any = [];
  validateClick: any = "";
  valideStory: any = "";
  envJson: any = "";
  SAPEnv: any = "";
  TransReq: any = "";
  SapBuild: any;
  SapDeploy: any;
  SapTest: any;
  parameterloading: any = false;
  msg: any;
  IDPParamData: any = {};
  IDPDataSwitch: any = {};
  Env: any;
  Envbiz: any;
  SapSystemName: any = "";
  hideTransportRequest = false;
  import: any;
  ApproveDep: any;
  environment: any;
  SapsystemNames: any;
  transportRequests: any = [];
  transportRequest: any;
  unselectedTRs: any = [];
  selectedTR: any;
  showData: any;
  devTrue: any;
  build: any = {};
  allArtifactlist: any;
  buildSelect: any = "off";
  deploySelect: any = "off";
  testSelect: any = "off";
  reconcileSelect: any = "off";
  buildEnv: any = [];
  deployEnv: any = [];
  testEnv: any = [];
  allEnv: any = [];
  buildTestEnv: any = [];
  downgradeTransportRequest: any = "";
  objectData: any = [];
  objectTypeList: any = [];
  objectNameList: any = [];
  selectedItems: any = [];
  dropdownSettings: any = {};
  dbDeployRollbackTypeList: any = [];
  hideTransportRequests: any = false;
  branchList: any;
  testPlansList: any = [];
  branchTemp: any;
  buildIntervalData: any;
  tagTemp: any;
  tagList: any;
  branchOrTag: any;
  scmType: any;
  dropdownListTest: any = [];
  testSuitList: any = [];
  dropdownListDeploy: any = [];
  deployMultiselectSettings = {
    singleSelection: false,
    text: "Select Deploy Steps",
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All"
  };
  testMultiselectSettings = {
    singleSelection: false,
    text: "Select Test Steps",
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All"
  };
  virtualServiceMultiselectSettings = {
    singleSelection: false,
    text: "Select Virtual Services",
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All"
  };
  ibmRQMTestCaseMultiselectSettings = {
    singleSelection: false,
    text: "Select IBM RQM Test Case",
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All"
  };
  dbMultiselectSettings = {
    singleSelection: false,
    text: "Select DB operations",
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All"
  };
  testSuitId: any = [];
  deploySteps: any = [];
  testSteps: any = [];
  tempDeploySteps: any = [];
  tempTestSteps: any = [];
  deployArr: any = [];
  testArr: any = [];
  jobParamList: any;
  operation: any;
  disableflag: any;
  showJobParamFlag: any;
  testStepWithToolList: any = [];
  notFound: any = [];
  invalid: any = [];
  module: any = {};
  validWorkItems = false;
  slavestatus: any;
  testslavestatus: any;
  artifactDetails: any;
  packageContent: any;
  informatica: any;
  config = TreeviewConfig.create({
    hasAllCheckBox: false,
    hasFilter: false,
    hasCollapseExpand: false,
    decoupleChildFromParent: true,
    maxHeight: 400

  });
  siebel = {
    repoList: [],
    nonRepoList: []
  };
  pega = {
    pegaFileList: []
  };
  ant: any;
  dotNet: any;
  bigData: any;
  fetchedVirtualServicesList: any;
  tempVirtualServicesList: any;
  selectedVirtualServicesList: any;
  virtualServicesArr: any = [];
  dashboardUrl: String;
  fetchedIBMRQMTestCaseList: any;
  tempIBMRQMTestCaseList: any;
  selectedIBMRQMTestCaseList: any;
  ibmRQMTestCaseArr: any = [];
  changeReqestList: any;
  crListShow: any = [];
  artifacts: any;
  requiredSCM: any = false;
  branchListReqTemp: any = [];
  branchOrTagValue = "";
  disableSubmitBtn: boolean = false;
  tempTrArrayDeploy: any = [];
  tempTrArrayJira:  any = [];
  userstoryDropdownSettings: any = {};
  userStoryList: any;
  selectedUS: any;
  selectedUSData: any;

  ngOnInit() {
    this.getJobParamDetails();
  }

  constructor(
    public idpdataService: IdpdataService,
    private idprestapiService: IdprestapiService,
    private router: Router,
    public idpService: IdpService,
    private idpencryption: IDPEncryption
  ) {
    this.buildIntervalData = this.idpdataService.buildIntervalData;
    if (this.idpdataService.buildIntervalData === undefined) {
        this.idpdataService.buildIntervalData = [];
    }
    this.initialize();
  }

  populateDBDeployRollBackTypeList() {
    this.dbDeployRollbackTypeList = [{ 'name': 'byTag', 'value': 'byTag' },
    { 'name': 'byChangeSet', 'value': 'byChangeSet' },
    { 'name': 'byHours', 'value': 'byHours' },
    { 'name': 'byDate', 'value': 'byDate' }
    ];
  }

  changeDBDeployRollbackValues() {
    this.IDPParamData.deploy.dbDeployRollbackValue = "";
  }

  fetchReleaseBranches(data) {
    const releaseDetails = this.idpdataService.triggerJobData.releaseBranches;
    if (releaseDetails.hasOwnProperty(data)) {
        if (releaseDetails[data].length && releaseDetails[data].length !== 0) {
        this.branchList = releaseDetails[data];
        this.requiredSCM = true;
        this.branchListReqTemp = this.idpService.copy(this.branchList);
        } else {
        this.branchList = this.idpService.copy(this.branchTemp);
        this.requiredSCM = false;
        }
    }
  }

  addArtifact() {
    const temp = this.idpdataService.triggerJobData.artifactList;
    this.artifacts = [];
  this.allArtifactlist = [];
  if (this.IDPDataSwitch.deploySelected === "on" && this.IDPDataSwitch.buildSelected === "on") {
        const tempLatestArtifact = [{
        "artifactName": "",
        "artifactID": "", "groupId": "", "nexusURL": "", "repoName": "",
        "version": "", "buildModules": "", "enviromment": "", "userInfo": "",
        "timestamp": "", "downloadURL": ""
        }];
        tempLatestArtifact[0].artifactName = this.idpdataService.triggerJobData.applicationName
        + "_" + this.idpdataService.triggerJobData.pipelineName + "_latestArtifact";
        tempLatestArtifact[0].groupId = this.idpdataService.triggerJobData.applicationName;
        tempLatestArtifact[0].artifactID = this.idpdataService.triggerJobData.pipelineName;
        tempLatestArtifact[0].nexusURL = this.idpdataService.triggerJobData.nexusURL;
        tempLatestArtifact[0].repoName = this.idpdataService.triggerJobData.repoName;
        this.artifacts = tempLatestArtifact;
    this.allArtifactlist = tempLatestArtifact;
      }
  }

  clearWorkItemField() {
    this.notFound = [];
    this.invalid = [];
    this.validWorkItems = false;
  }

  validate() {
    const list = this.IDPParamData.tfsWorkItem.split(",");

    const mainList = [];
    let x = true;

    if (x) {
      for (let y = 0; y < list.length; y++) {
        if (y === list.length - 1) {
          x = false;
        }
        const data = list[y];
        this.idpdataService.loading = true;
        this.idprestapiService.getWorkItems(data)
          .then(response => {
            if (response) {
              this.idpdataService.loading = false;
              const fields = response.json().fields;
              const msg = response.json().message;

              if (fields) {
                const workType = fields["System.WorkItemType"];
                const obj = {"workItem": data, "status": workType };
                mainList.push(obj);
              } else if (msg && msg.indexOf("does not exist") !== -1) {
                const obj = { "workItem": data, "status": "not Found" };
                mainList.push(obj);
              }
              if (mainList.length === list.length) {
                this.checkValidity(mainList);
              }
            }
            this.idpdataService.loading = false;
          })
          .catch(error => {
            this.idpdataService.loading = false;
          });
      }
    }
  }

  checkValidity(data) {
    for (let i = 0; i < data.length; i++) {
      if (data[i].status === "not Found") {
        this.notFound.push(data[i]);
      } else if (data[i].status !== "Product Backlog Item" && data[i].status !== "Task") {
        this.invalid.push(data[i]);
      }
    }

    if (this.invalid.length === 0 && this.notFound.length === 0) {
      this.validWorkItems = true;
    }
  }

  clearUserStory() {
    this.validateClick = "false";
    this.valideStory = "false";
  }

  validateStories() {
    this.idpdataService.loading = true;
    this.idprestapiService.getUserStories(this.IDPParamData.jiraProjectKey).then(response => {
      if (response) {
        this.idpdataService.loading = false;
        this.valideStory = "true";
        if (response.json().resource !== null) {
          const userStories = JSON.parse(response.json().resource).userStoryInfo;
          const stories = this.IDPParamData.userStoryString.split(",");
          let count = 0;
          for (const i of stories) {
            if (userStories.indexOf(i) === -1) {
                count += 1;
                this.validateClick = "false";
            }
          }
          if (count === 0) {
            this.validateClick = "true";
          }

        } else {
          alert("No user stories available for this project");
          this.validateClick = "false";
        }

      } else {
        this.idpdataService.loading = false;
        alert("Failed to get User Stories for the project");
        this.validateClick = "false";
      }
    });
}

  setArtifact1() {
    this.IDPDataSwitch.buildSelected = "on";
    this.IDPParamData.deploy.artifacts = "";
    if (this.IDPParamData.deploy.deployModule !== undefined) {
        this.IDPParamData.deploy.deployModule = [];
    }
    this.addArtifact();
    return "on";
  }
  setArtifact2() {
    this.IDPDataSwitch.deploySelected = "on";
    this.IDPParamData.deploy.artifacts = "";
    this.addArtifact();
    this.checkDBOperation();
    if (this.IDPDataSwitch.subModules !== undefined
        && this.IDPDataSwitch.subModules.length !== undefined) {
        for (let i = 0; i < this.IDPDataSwitch.subModules.length; i++) {
        if (this.IDPDataSwitch.subModules[i].defaultModule === "on") {
            const modName = this.IDPDataSwitch.subModules[i].moduleName;
            this.IDPParamData.deploy.subModule.push(modName);
        }
        }
    }
    return "on";
  }

  alertforNoArtifact() {
    if (this.idpdataService.triggerJobData.artifactList === undefined
        || this.idpdataService.triggerJobData.artifactList.length === 0
        || this.idpdataService.triggerJobData.artifactList === null) {
        this.triggerAlertArtifact();
    }
  }
  clearTriggerArtifact() {
    this.IDPParamData.deploy.artifacts = [];
  }
  populateEnv() {
    if (this.error === "SUCCESS") {
        if (this.deployEnvList.length === 0) {
        this.Env = [];
        this.disableflag = true;
        this.IDPDataSwitch.deploySelected = "off";
        alert("You can not deploy this application now. Please trigger it at planned time.");
        const currentTime = new Date();
        const hours = currentTime.getHours();
        const minutes = currentTime.getMinutes();
        const timeZoneOffset = hours + ":" + minutes;
        this.idprestapiService.getAvailableSlot(this.IDPParamData.applicationName,
            this.IDPDataSwitch.releaseNumber, timeZoneOffset).then(response => {
            if (response) {
                if (response.json().resource !== null) {
                alert(response.json().resource);
                } else {
                alert("No slots available for the day");
                }
            } else {
                alert("Could not suggest next available slot");
            }
            });
        if (this.IDPDataSwitch.testSelected === "on") {
            this.Env = this.IDPDataSwitch.testEnv;
        }
        return;
        } else {
        const tempEnv = [];
        for (const i of this.Env) {
            if (this.deployEnvList.indexOf(i) !== -1) {
            tempEnv.push(i);
            }
        }
        this.Env = tempEnv;
        if (this.Env.length === 0) {
            alert("You can not deploy this application now. Please trigger it at planned time");
            const currentTime = new Date();
            const hours = currentTime.getHours();
            const minutes = currentTime.getMinutes();
            const timeZoneOffset = hours + ":" + minutes;
            this.idprestapiService.getAvailableSlot(this.IDPParamData.applicationName,
            this.IDPDataSwitch.releaseNumber, timeZoneOffset).then(response => {
                if (response) {
                if (response.json().resource !== null) {
                    alert(response.json().resource);
                } else {
                    alert("No slots available for the day");
                }
                } else {
                alert("Couild not suggest next available slot");
                }
            });
            this.disableflag = true;
            this.IDPDataSwitch.deploySelected = "off";
        }
        if (this.IDPDataSwitch.testSelected === "on"
            && this.IDPDataSwitch.deploySelected === "off") {
            this.Env = this.IDPDataSwitch.testEnv;
        }
        return;
        }
    }
  }
  getEnvNames() {
    this.idprestapiService.getEnvNames(this.IDPParamData.applicationName, this.IDPDataSwitch.releaseNumber).then(response => {
        if (response) {
        if (JSON.parse(response.text()).status !== "Env was not planned") {
            if (response.status === 200) {
            this.deployEnvList = JSON.parse(response.json().resource).names;
            this.error = "SUCCESS";
            this.populateEnv();
            } else {
            this.error = "Could not fetch";
            }
        } else {
            this.error = "Env was not planned";
        }
        }
    });
  }
  checkDBOperation() {
  }

  toggleSelection(module) {
    const idx = this.IDPParamData.build.module.indexOf(module);
    if (idx > -1) {
        this.IDPParamData.build.module.splice(idx, 1);
    } else {
        this.IDPParamData.build.module.push(module);
    }
  }

  toggleSelection1(subModule) {
    const idx = this.IDPParamData.deploy.subModule.indexOf(subModule);
    if (idx > -1) {
        this.IDPParamData.deploy.subModule.splice(idx, 1);
    } else {
        this.IDPParamData.deploy.subModule.push(subModule);
    }
  }
  toggleSelectionDeploy(module) {
    const idx = this.IDPParamData.deploy.deployModule.indexOf(module);
    if (idx > -1) {
        this.IDPParamData.deploy.deployModule.splice(idx, 1);
    } else {
        this.IDPParamData.deploy.deployModule.push(module);
    }
  }

  toggleSelection2() {
    alert("in toggle2");
    if (this.IDPDataSwitch.subModules) {
        alert("in if");
        for (let i = 0; i < this.IDPDataSwitch.subModules.length; i++) {
        alert(this.IDPDataSwitch.subModules[i].moduleName);
        if (this.IDPDataSwitch.subModules[i].defaultModule === "on") {
            this.IDPParamData.deploy.subModule.push(this.IDPDataSwitch.subModules[i].moduleName);
        }
        }
    }
  }
  checkEnvToPopulate() {
    if (this.IDPDataSwitch.testSelected === "on"
        && this.IDPDataSwitch.deploySelected === "on") {
        this.Env = this.IDPDataSwitch.deployTestEnv;
        this.getEnvNames();
        this.Envbiz = this.IDPDataSwitch.deploytestbizEnv;
    } else if (this.IDPDataSwitch.testSelected === "on"
        && this.IDPDataSwitch.deploySelected === "off") {
        this.Env = this.IDPDataSwitch.testEnv;
    } else if (this.IDPDataSwitch.buildSelected === "on"
        && this.IDPDataSwitch.deploySelected === "on") {
        const temp = this.IDPDataSwitch.buildDeployEnv;
        this.Env = [];
        for (const obj of temp) {
        if (obj.releaseNumber === this.IDPDataSwitch.releaseNumber) {
            this.Env = obj.Env;
            this.getEnvNames();
            break;
        }
        }
    } else {
        this.Env = this.IDPDataSwitch.deployEnv;
        this.getEnvNames();
        this.Envbiz = this.IDPDataSwitch.deploybizEnv;
    }
  }
  checkrm() {
    if (this.Envbiz !== undefined) {
        for (let i = 0; i < this.Envbiz.length; i++) {
        if (this.Envbiz[i].EnvName === this.IDPParamData.envSelected) {
            if (this.Envbiz[i].bizCheck === "on") {
            return true;
            } else {
            return false;
            }
        }
        }
    }
    return false;
  }
  checkrmcheck() {
    this.IDPParamData.removePrevAssem = "off";
    this.IDPParamData.rmAssemblies = "";
    this.clearArtifact();
    if (this.IDPParamData.envSelected !== undefined) {
        this.fetchSteps();
    }
  }

  initialize() {
    if (!this.idpdataService.isSAPApplication) {
        this.dbDeployRollbackTypeList = [{ 'name': 'byTag', 'value': 'byTag' },
        { 'name': 'byChangeSet', 'value': 'byChangeSet' },
        { 'name': 'byHours', 'value': 'byHours' },
        { 'name': 'byDate', 'value': 'byDate' }
        ];

        this.IDPParamData = {
        "applicationName": "",
        "artifactorySelected": "off",
        "technology": this.idpdataService.triggerJobData.technology,
        "subApplicationName": "",
        "ibmRQMTestSuiteId": "",
        "schedule": false,
        "dashBoardLink": this.idpdataService.IDPDashboardURL,
        "pairNames": [],
        "jobParam": [],
        "jiraProjectKey": "",
        "build": {
            "branchSelected": "",
            "module": []
        },
        "deploy": {
            "deployArtifact": {},
            "artifactID": "",
            "nexusId": "",
            "version": "",
            "artifacts": {},
            "deployStep": [],
            "dbDeployRollbackType": "",
            "deployModule": [],
            "subModule": []
        },
        "env": [],
        "charmRequests": [],
        "envSelected": "",
        "pipelineName": "",
        "releaseNumber": "",
        "jobBuildId": "",
        "slaveName": "",
        "testSlaveName": "",
        "branchOrTag": "",
        "testPlanId": "",
        "testSuitId": "",
        "mtmStepName": "",
        "rebase":
        {
            "sourceEnvSelected": "",
            "transportObjectType": "",
            "transportObject": "",
            "targetEnvSelected": "",
            "bugFixTR": ""
        },
        "repoDeployStatus": "",
        "nonRepoDeployStatus": "",
        "testSelected": "off",
        "testStep": [],
        "userName": this.idpdataService.idpUserName,
        "gitTag": "",
        "buildartifactNumber": "",
        };
        this.IDPDataSwitch = {
        "test": false,
        "build": false,
        "deploy": false,
        "nexusURL": "",
        "repoName": "",
        "releaseManager": true,
        "buildSelected": "off",
        "deploySelected": "off",
        "testSelected": "off",
        "reconcileSelected": "off",
        "sifImport": "off",
        "srfCompile": "off",
        "srfCompileType": "",
        "repoDeployStatus": "",
        "nonRepoDeployStatus": "",
        "slaves": [],
        "appSlaves": [],
        "modules": [],
        "subModules": [],
        "artifacts": [],
        "buildBranch": [],
        "jobParm": [],
        "buildenv": [],
        "testEnv": [],
        "buildDeployEnv": [],
        "deployEnv": [],
        "gitTagEnable": "",
        "sshAndDependent": "",
        "relaseList": [],
        "releaseNumberList": [],
        "releaseNumber": ""
        };
        try {
        if (this.idpdataService.triggerJobData) {
            if (this.idpdataService.triggerJobData.hasOwnProperty("build")) {
            this.IDPDataSwitch.build = true;
            this.IDPDataSwitch.modules = this.idpdataService.triggerJobData.build.hasOwnProperty("modules")
                ? this.idpdataService.triggerJobData.build.modules : [];
            this.IDPDataSwitch.subModules = this.idpdataService.triggerJobData.build.hasOwnProperty("subModules")
                ? this.idpdataService.triggerJobData.build.subModules : [];
            for (let i = 0; i < this.IDPDataSwitch.modules.length; i++) {
                if (this.IDPDataSwitch.modules[i].defaultModule === "on") {
                const modName = this.IDPDataSwitch.modules[i].moduleName;
                this.IDPParamData.build.module.push(modName);
                }
            }
            this.IDPDataSwitch.buildBranch = this.idpdataService.triggerJobData.build.hasOwnProperty("gitBranches")
                ? this.idpdataService.triggerJobData.build.gitBranches : [];
            this.IDPDataSwitch.gitTagEnable = this.idpdataService.triggerJobData.build.hasOwnProperty("gitTag")
                ? this.idpdataService.triggerJobData.build.gitTag : [];
            this.IDPDataSwitch.sshAndDependent = this.idpdataService.triggerJobData.hasOwnProperty("sshAndDependent")
                ? this.idpdataService.triggerJobData.sshAndDependent : [];
            this.IDPDataSwitch.relaseList = this.idpdataService.triggerJobData.hasOwnProperty("relaseList")
                ? this.idpdataService.triggerJobData.relaseList : [];
            this.IDPDataSwitch.releaseNumberList = this.idpdataService.triggerJobData.hasOwnProperty("releaseNumber")
                ? this.idpdataService.triggerJobData.releaseNumber : [];
            }
            this.branchList = this.idpdataService.triggerJobData.hasOwnProperty("branchList")
            ? this.idpdataService.triggerJobData.branchList : [];
            this.tagList = this.idpdataService.triggerJobData.hasOwnProperty("tagList")
            ? this.idpdataService.triggerJobData.tagList : [];
            this.branchTemp = this.idpService.copy(this.branchList);
            this.tagTemp = this.idpService.copy(this.tagList);

            this.scmType = this.idpdataService.triggerJobData.hasOwnProperty("scmType") ? this.idpdataService.triggerJobData.scmType : "";

            if (this.idpdataService.triggerJobData.hasOwnProperty("test")) {
            this.IDPDataSwitch.test = true;
            this.IDPDataSwitch.testEnv = this.idpdataService.triggerJobData.test.hasOwnProperty("testEnv")
                ? this.idpdataService.triggerJobData.test.testEnv : [];
            }
            if (this.idpdataService.triggerJobData.hasOwnProperty("deploy")) {
            this.IDPDataSwitch.deploy = true;
            this.IDPDataSwitch.buildDeployEnv = this.idpdataService.triggerJobData.hasOwnProperty("buildDeployEnv")
                ? this.idpdataService.triggerJobData.buildDeployEnv : [];
            this.IDPDataSwitch.deployEnv = this.idpdataService.triggerJobData.deploy.hasOwnProperty("deployEnv")
                ? this.idpdataService.triggerJobData.deploy.deployEnv : [];
            this.IDPDataSwitch.deploybizEnv = this.idpdataService.triggerJobData.deploy.hasOwnProperty("bizobj")
                ? this.idpdataService.triggerJobData.deploy.bizobj : [];
            this.IDPParamData.env = this.IDPDataSwitch.deployEnv;
            }
            if (this.idpdataService.triggerJobData.hasOwnProperty("deployTestEnv")) {
            this.IDPDataSwitch.deploy = true;
            this.IDPDataSwitch.test = true;
            this.IDPDataSwitch.deployTestEnv = this.idpdataService.triggerJobData.deployTestEnv.hasOwnProperty("Env")
                ? this.idpdataService.triggerJobData.deployTestEnv.Env : [];
            this.IDPDataSwitch.deploytestbizEnv = this.idpdataService.triggerJobData.deployTestEnv.hasOwnProperty("envObj")
                ? this.idpdataService.triggerJobData.deployTestEnv.envObj : [];
            } else {
            this.IDPDataSwitch.deployTestEnv = [];
            }
        }
        this.IDPDataSwitch.slaves = this.idpdataService.triggerJobData.hasOwnProperty("slaves")
            ? this.idpdataService.triggerJobData.slaves : [];
        this.IDPDataSwitch.appSlaves = this.idpdataService.triggerJobData.hasOwnProperty("appSlaves")
            ? this.idpdataService.triggerJobData.appSlaves : [];
        this.IDPParamData.applicationName = this.idpdataService.triggerJobData.hasOwnProperty("applicationName")
            ? this.idpdataService.triggerJobData.applicationName : "";
            this.IDPParamData.jiraProjectKey = this.idpdataService.triggerJobData.hasOwnProperty("jiraProjectKey")
             ? this.idpdataService.triggerJobData.jiraProjectKey : "";
        this.IDPParamData.pipelineName = this.idpdataService.triggerJobData.hasOwnProperty("pipelineName")
            ? this.idpdataService.triggerJobData.pipelineName : "";
        this.IDPDataSwitch.releaseNumberList = this.idpdataService.triggerJobData.hasOwnProperty("releaseNumber")
            ? this.idpdataService.triggerJobData.releaseNumber : "";
        this.IDPParamData.jobBuildId = this.idpdataService.triggerJobData.hasOwnProperty("jobBuildId")
            ? this.idpdataService.triggerJobData.jobBuildId : "";
        this.IDPDataSwitch.nexusURL = this.idpdataService.triggerJobData.hasOwnProperty("nexusURL")
            ? this.idpdataService.triggerJobData.nexusURL : "";
        this.IDPDataSwitch.repoName = this.idpdataService.triggerJobData.hasOwnProperty("repoName")
            ? this.idpdataService.triggerJobData.repoName : "";
        this.IDPDataSwitch.jobBuildId = this.IDPParamData.jobBuildId;
        this.IDPParamData.subApplicationName = this.idpdataService.triggerJobData.hasOwnProperty("subApplicationName")
            ? this.idpdataService.triggerJobData.subApplicationName : "";
        if (this.IDPDataSwitch.repoName !== "na") {
            this.IDPParamData.artifactorySelected = "on";
        }
        if (this.IDPParamData.releaseNumber !== null
            && this.IDPParamData.releaseNumber !== ""
            && this.IDPParamData.releaseNumber !== undefined) {
            this.IDPDataSwitch.releaseNumber = this.IDPParamData.releaseNumber;
        } else {
            this.IDPDataSwitch.releaseNumber = "";
        }
        } catch (e) {
        console.log(e);
        alert("Error in Trigger job details");
        this.router.navigate(["/previousConfig"]);
        }
    } else {
      this.userstoryDropdownSettings = {
        singleSelection: false,
        text: "Select User story(s)",
        selectAllText: "Select All",
        unSelectAllText: "UnSelect All",
        enableSearchFilter: true,
        classes: "myclass custom-class"
      };
        this.dropdownSettings = {
        singleSelection: false,
        text: "Select Transport Requests",
        selectAllText: "Select All",
        unSelectAllText: "UnSelect All",
        enableSearchFilter: true,
        classes: "myclass custom-class"
        };
        this.IDPParamData = {
        "applicationName": "appName",
        "pipelineName": "pipName",
        "releaseNumber": "",
        "ibmRQMTestSuiteId": "",
        "userName": this.idpdataService.idpUserName,
        "landscapesDetails": [],
        "subApplicationName": "",
        "slaveName": "",
        "testSlaveName": "",
        "castSlaveName": "",
        "reconcileSlaveName": "",
        "envSelected": "",
        "systemName": "",
        "SapsystemNames": "",
        "instance": "",
        "client": "",
        "charmRequests": [],
        "sapUserName": "",
        "branchOrTag": "",
        "password": "",
        "technology": this.idpdataService.triggerJobData.technology,
        "language": "",
        "transportRequest": [],
        "copyTR": false,
        "userStories": [],
        "userStoryMapping": [],
        "jobParam": [],
        "testPlanId": "",
        "testSuitId": "",
        "mtmStepName": "",
        "rebase":
        {
            "sourceEnvSelected": "",
            "transportObjectType": "",
            "transportObject": "",
            "targetEnvSelected": "",
            "bugFixTR": ""
        },
        "build":
        {
            "branchSelected": "",
            "codeAnalysis": "",
            "unitTest": "",
            "cast": "",
            "currentDate": "",
            "oldVersion": "",
            "newVersion": "",
            "module":
            [
            ]
        },
        "deploy":
        {
            "version": "",
            "artifactID": "",
            "nexusId": "",
            "deploymentType": "",
            "deployOperationSelected": "",
            "deployStep": [],
            "subModule": []
        },
        "testSelected": "",
        "testStep": []
        };
        this.IDPDataSwitch = {
        "test": false,
        "build": false,
        "deploy": false,
        "releaseManager": true,
        "buildSelected": "off",
        "deploySelected": "off",
        "testSelected": "off",
        "reconcileSelected": "off",
        "slaves": [],
        "modules": [],
        "buildBranch": [],
        "buildenv": [],
        "testEnv": [],
        "deployEnv": [],
        "releaseNumberList": [],
        "releaseNumber": ""
        };
        try {
        if (this.idpdataService.triggerJobData) {
            if (this.idpdataService.triggerJobData.hasOwnProperty("build")) {
            this.IDPDataSwitch.build = true;
            if (this.idpdataService.triggerJobData.build.hasOwnProperty("buildEnv")) {
                if (this.idpdataService.triggerJobData.build.buildEnv.length !== 0) {
                this.buildEnv = this.idpdataService.triggerJobData.build.buildEnv;
                } else {
                this.buildEnv = [];
                }
            }
            if (this.idpdataService.triggerJobData.build.hasOwnProperty("codeAnalysis")) {
                if (this.idpdataService.triggerJobData.build.codeAnalysis) {
                this.build.codeAnalysis = this.idpdataService.triggerJobData.build.codeAnalysis;
                }
            }
            if (this.idpdataService.triggerJobData.build.hasOwnProperty("cast")) {
              if (this.idpdataService.triggerJobData.build.cast) {
                this.build.cast = this.idpdataService.triggerJobData.build.cast;
              }
            }
            if (this.idpdataService.triggerJobData.build.hasOwnProperty("unitTesting")) {
                if (this.idpdataService.triggerJobData.build.unitTesting) {
                this.build.unitTest = this.idpdataService.triggerJobData.build.unitTesting;
                }
            }
            }
            if (this.idpdataService.triggerJobData.hasOwnProperty("test")) {
            if (this.idpdataService.triggerJobData.test === "on") {
                this.IDPDataSwitch.test = true;
            } else {
                this.IDPDataSwitch.test = false;
            }
            if (this.idpdataService.triggerJobData.test.hasOwnProperty("testEnv")) {
                if (this.idpdataService.triggerJobData.test.testEnv.length !== 0) {
                this.testEnv = this.idpdataService.triggerJobData.test.testEnv;
                } else {
                this.testEnv = [];
                }
            }
            }
            if (this.idpdataService.triggerJobData.hasOwnProperty("systemNames")) {
            if (this.idpdataService.triggerJobData.systemNames) {
                this.SapsystemNames = this.idpdataService.triggerJobData.systemNames;
            }
            }
            if (this.idpdataService.triggerJobData.hasOwnProperty("deploy")) {
            this.IDPDataSwitch.deploy = true;
            this.IDPDataSwitch.deployEnv = this.idpdataService.triggerJobData.deploy.hasOwnProperty("deployEnv")
                ? this.idpdataService.triggerJobData.deploy.deployEnv : [];
            if (this.idpdataService.triggerJobData.deploy.hasOwnProperty("deployEnv")) {
                if (this.idpdataService.triggerJobData.deploy.deployEnv.length !== 0) {
                this.deployEnv = this.idpdataService.triggerJobData.deploy.deployEnv;
                } else {
                this.deployEnv = [];
                }
            }
            }
            this.IDPDataSwitch.slaves = this.idpdataService.triggerJobData.hasOwnProperty("slaves")
            ? this.idpdataService.triggerJobData.slaves : [];
            this.IDPParamData.applicationName = this.idpdataService.triggerJobData.hasOwnProperty("applicationName")
            ? this.idpdataService.triggerJobData.applicationName : "";
            this.IDPParamData.pipelineName = this.idpdataService.triggerJobData.hasOwnProperty("pipelineName")
            ? this.idpdataService.triggerJobData.pipelineName : "";
            this.IDPDataSwitch.releaseNumberList = this.idpdataService.triggerJobData.hasOwnProperty("releaseNumber")
            ? this.idpdataService.triggerJobData.releaseNumber : "";
            this.IDPParamData.jobBuildId = this.idpdataService.triggerJobData.hasOwnProperty("jobBuildId")
            ? this.idpdataService.triggerJobData.jobBuildId : "";
            this.IDPDataSwitch.jobBuildId = this.IDPParamData.jobBuildId;
            this.branchList = this.idpdataService.triggerJobData.hasOwnProperty("branchList")
            ? this.idpdataService.triggerJobData.branchList : [];
            this.tagList = this.idpdataService.triggerJobData.hasOwnProperty("tagList") ? this.idpdataService.triggerJobData.tagList : [];
            this.branchTemp = this.idpService.copy(this.branchList);
            this.tagTemp = this.idpService.copy(this.tagList);
            this.scmType = this.idpdataService.triggerJobData.hasOwnProperty("scmType") ? this.idpdataService.triggerJobData.scmType : "";
            if (this.IDPParamData.releaseNumber !== null
            && this.IDPParamData.releaseNumber !== ""
            && this.IDPParamData.releaseNumber !== undefined) {
            this.IDPDataSwitch.releaseNumber = this.IDPParamData.releaseNumber;
            } else {
            this.IDPDataSwitch.releaseNumber = "";
            }
        }
        {
            this.idprestapiService.getApplicationInfo(this.IDPParamData.applicationName).then(response => {
            if (response) {
                const app = JSON.parse(response.json().resource);
                    if (this.idpdataService.isSAPApplication) {
                        this.IDPParamData.landscapesDetails = app.environmentOwnerDetails;
                        const details = this.IDPParamData.landscapesDetails;
                        for (let i = 0; i < details.length; i++) {
                            if ("DEV" === details[i].landscapeType || "HOTFIX" === details[i].landscapeType) {
                                this.devEnvList.push(details[i].environmentName);
                            }
                        }
                    }
                }
            });
        }
        this.environment = this.idpdataService.SAPEnvList;
        this.intersectionValues();
        this.removeDuplicates();
        } catch (e) {
        console.log(e);
        alert("Failed , Trigger job data is not proper");
        }
    }
  }

  toggleSelectionSiebel(module) {

    var idx = this.IDPParamData.build.module.indexOf(module);
    if (idx > -1) {
      this.IDPParamData.build.module.splice(idx, 1);
      if (module == 'Full_Compile') {
        this.IDPDataSwitch.srfcompile = 'off';
      }
      if (module == 'SIFSelected') {
        this.IDPDataSwitch.sifImport = 'off';
      }
    }
    else {
      this.IDPParamData.build.module.push(module);
      if (module == 'Full_Compile') {
        this.IDPDataSwitch.srfcompile = 'on';
      }
      if (module == 'SIFSelected') {
        this.IDPDataSwitch.sifImport = 'on';
      }
    }

    console.log(this.IDPParamData.build.module)
  }

  closeBuild() {
    this.IDPParamData.build.module = [];
    this.IDPDataSwitch.buildSelected = "off";
    this.IDPParamData.slaveName = "";
    this.slavestatus="";
    if (this.IDPParamData.deploy.artifacts === "") {
        this.IDPDataSwitch.deploySelected = "off";
    }
    this.IDPParamData.deploy.artifacts = "";
    this.addArtifact();
    return "off";
  }
  closeDeploy() {
    this.IDPDataSwitch.deploySelected = "off";
    this.IDPParamData.deploy.artifacts = "";
    if(this.IDPDataSwitch.buildSelected === "off"){
    this.IDPParamData.slaveName="";
    this.slavestatus="";
    }
    this.tempDeploySteps = [];
    this.deployArr = [];
    this.IDPParamData.deploy.deployStep = [];
    this.IDPParamData.deploy.subModule = [];
    this.operation = [];
    
    this.addArtifact();
    this.closeDBDeploy();
    return "off";
  }
  closeTest() {
    this.IDPDataSwitch.testSelected = "off";
    this.tempTestSteps = [];
    this.testArr = [];
    this.IDPParamData.testStep = [];
    this.fetchedIBMRQMTestCaseList = [];
    this.fetchedVirtualServicesList = [];
    return "off";
  }
  closeDBDeploy() {
    this.IDPParamData.deployDB = "off";
    this.IDPParamData.restoreDB = "off";
    this.IDPParamData.deploy.subApplication = "";
    this.IDPParamData.dbOperations = [];
  }
  checkEnv() {
    if (this.IDPDataSwitch.deploySelected !== "on"
        && this.IDPDataSwitch.testSelected !== "on") {
        this.IDPParamData.envSelected = "";
    }
  }
  //siebel
  repoDeployTrue() {
    this.IDPParamData.repoDeployStatus = 'true';
    return "on";
  }

  repoDeployFalse() {
    this.IDPParamData.repoDeployStatus = 'false';
    return "off";
  }

  nonRepoDeployTrue() {

    this.IDPParamData.nonRepoDeployStatus = 'true';
    this.IDPParamData.technology = 'siebel';
    return "on";
  }

  nonRepoDeployFalse() {
    this.IDPParamData.nonRepoDeployStatus = 'false';
    this.IDPParamData.technology = 'siebel';
    return "off";
  }
  deployOptionAlert() {
    alert("Please select atleast one deploy option");
  }
  siebelBuildOption() {

    this.IDPParamData.build.module[0] = []
    if (this.IDPDataSwitch.sifImport == 'on') {
      this.IDPParamData.build.module[0] += ';SIFSelected;';
      if (this.IDPDataSwitch.srfCompile == 'on')
        this.IDPParamData.build.module[0] += 'SRFSelected;';
      if (this.IDPDataSwitch.srfCompileType == 'fullCompile')
        this.IDPParamData.build.module[0] += 'Full_Compile;';
      if (this.IDPDataSwitch.srfCompileType == 'incCompile')
        this.IDPParamData.build.module[0] += 'Incremental_Compile;';
    } else {
      if (this.IDPDataSwitch.srfCompile == 'on')
        this.IDPParamData.build.module[0] += ';SRFSelected;';
      if (this.IDPDataSwitch.srfCompileType == 'fullCompile')
        this.IDPParamData.build.module[0] += 'Full_Compile;';
      if (this.IDPDataSwitch.srfCompileType == 'incCompile')
        this.IDPParamData.build.module[0] += 'Incremental_Compile;';
    }
    this.IDPParamData.technology = 'siebel';
    return true;
  }

  triggerAlert() {
    this.button.nativeElement.click();
  }

  triggerAlertDBDep() {
    this.dbButton.nativeElement.click();
  }

  triggerAlertArtifact() {
    this.buttonArtifact.nativeElement.click();
  }

  triggerSubmitArtifactAlert() {
    this.buttonSubmitArtifact.nativeElement.click();
  }

  // Checks conditions to show Landscape dropdown
  checkCheckBoxesOn() {
      if (this.IDPDataSwitch.buildSelected === "on" && (this.IDPParamData.build.cast === "on"
      || this.IDPParamData.build.codeAnalysis === "on" || this.IDPParamData.build.unitTest === "on")) {
          return true;
      } else if (this.IDPDataSwitch.buildSelected !== "on"
      && (this.IDPDataSwitch.deploySelected === "on" || this.IDPDataSwitch.testSelected === "on")) {
          return true;
      } else {
          return false;
      }
  }

  intersectionValues() {
    const finalData = this.buildEnv.filter(x => this.testEnv.indexOf(x) > -1);
    if (finalData !== undefined) {
        this.buildTestEnv = finalData;
    } else {
        this.buildTestEnv = [];
    }
  }
  removeDuplicates() {
    const temp = this.deployEnv.concat(this.buildEnv.concat(this.testEnv));
    const len = temp.length;
    const obj = {};
    for (let i = 0; i < len; i++) {
        obj[temp[i]] = 0;
    }
    for (const i in obj) {
        if (obj.hasOwnProperty(i)) {
        this.allEnv.push(i);
        }
    }
  }

  onItemSelect(item: any) {
    for (let j = 0; j < this.unselectedTRs.length; j++) {
        if (item.itemName === this.unselectedTRs[j].transportReqName) {
        this.selectedTR.push(this.unselectedTRs[j]);
        break;
        }
    }
  }
  OnItemDeSelect(item: any) {
    let index;
    for (let j = 0; j < this.unselectedTRs.length; j++) {
        if (item.itemName === this.selectedTR[j].transportReqName) {
        index = j;
        break;
        }
    }
    this.selectedTR.splice(index, 1);
  }
  onSelectAll(items: any) {
    this.selectedTR = this.unselectedTRs;
  }
  onDeSelectAll(items: any) {
    this.selectedTR = [];
  }
  trigger() {
    if (this.requiredSCM === true
        && (this.IDPParamData.branchOrTagValue === undefined || this.IDPParamData.branchOrTagValue === "")) {
        this.triggerButtonRqdSCM.nativeElement.click();
    } else if (((this.IDPDataSwitch.buildSelected === "off"
    || this.IDPDataSwitch.buildSelected === null
    || this.IDPDataSwitch.buildSelected === undefined) &&
    (this.IDPDataSwitch.testSelected === "off"
    || this.IDPDataSwitch.testSelected === null
    || this.IDPDataSwitch.testSelected === undefined) &&
    (this.IDPDataSwitch.deploySelected === "off"
    || this.IDPDataSwitch.deploySelected === null
    || this.IDPDataSwitch.deploySelected === undefined))) {
    this.idpdataService.loading = false;
    this.triggerAlert();
    } else {
        if (this.idpdataService.schedulePage === true || this.idpdataService.createWorkflowSequenceflag) {
        this.saveButton.nativeElement.click();
        } else {
        this.triggerButton.nativeElement.click();
        }
    }
  }
  saveData() {
    jQuery("#saveAlertForthis").modal("hide");

    this.idpdataService.loading = true;
    this.IDPParamData.releaseNumber = this.IDPDataSwitch.releaseNumber;
    if (this.IDPDataSwitch.repoName === "na") {
      this.IDPParamData.deploy.deployArtifact = {};
    } else {
      if (this.IDPParamData.deploy !== null) {
        this.IDPParamData.deploy.deployArtifact = this.IDPParamData.deploy.artifacts;
      }
    }



    console.log(this.IDPParamData.deploy.deployArtifact);

    if (this.idpdataService.isSAPApplication) {
      if (this.idpdataService.checkpollALM) {
        if (this.IDPParamData.userStories === undefined) {
          this.IDPParamData.useStories = [];
        }
        for (const i of this.selectedUS) {
          this.IDPParamData.userStories.push(i.itemName);
        }
      }
      for (const i of this.selectedItems) {
        this.IDPParamData.transportRequest.push(i.itemName);
      }
      if (this.idpdataService.checkpollALM) {
        const userStoryMapsTRs = new Map<String, any>();
        for (const data of this.userStoryArray) {
          if (this.IDPParamData.userStories.indexOf(data.Userstory) !== -1) {
            const tempTRs = [];
            for (const tr of data.transportRequest) {
              if (this.IDPParamData.transportRequest.includes(tr)) {
                tempTRs.push(tr);
              }
            }
            if (tempTRs.length > 0) {
              userStoryMapsTRs.set(data.Userstory, tempTRs);
            }
          }
        }
        this.IDPParamData.userStoryMapping = userStoryMapsTRs;
      }
    }
    if (((this.IDPDataSwitch.buildSelected === "off"
     || this.IDPDataSwitch.buildSelected === null
      || this.IDPDataSwitch.buildSelected === undefined) &&
      (this.IDPDataSwitch.testSelected === "off"
       || this.IDPDataSwitch.testSelected === null
        || this.IDPDataSwitch.testSelected === undefined)
      && (this.IDPDataSwitch.deploySelected === "off"
       || this.IDPDataSwitch.deploySelected === null || this.IDPDataSwitch.deploySelected === undefined)) ||
      (this.IDPParamData.deploy.update !== "on" && this.IDPParamData.deploy.rollback !== "on" &&
        this.IDPParamData.deploy.misc !== "on" && this.idpdataService.triggerJobData.technology === "dbDeployDelphix")) {
      this.idpdataService.loading = false;
      if (this.IDPParamData.deploy.update !== "on" && this.IDPParamData.deploy.rollback !== "on" &&
        this.IDPParamData.deploy.misc !== "on" && this.idpdataService.triggerJobData.technology === "dbDeployDelphix") {
        this.triggerAlertDBDep();
      } else {
        this.triggerAlert();
      }
      // this.parameterloading = false;
    } else {
      if (this.tempDeploySteps.length !== 0 && this.tempDeploySteps.length !== undefined) {
        for (let i = 0; i < this.deployArr.length; i++) {
          const step = this.deployArr[i];
          if (this.deployArr[i] !== null) {
            this.IDPParamData.deploy.deployStep.push(step);
            console.log(this.IDPParamData.deploy.deployStep);
          }
        }
      }
      if (this.tempTestSteps.length !== 0 && this.tempTestSteps.length !== undefined) {
        for (let i = 0; i < this.testArr.length; i++) {
          const step = this.testArr[i];
          if (this.testArr[i] !== null) {
            this.IDPParamData.testStep.push(step);
            console.log(this.IDPParamData.testStep);
          }
        }
      }
      const requestData = this.IDPParamData;
      console.log(requestData);

      if (this.idpdataService.isSAPApplication) {
        if (this.IDPDataSwitch.buildSelected === "on" && this.idpdataService.checkpollALM) {
          if (this.buildEnv[0]) {
            requestData.envSelected = this.buildEnv[0];
            console.log(this.buildEnv[0]);
          }
        }
      }
      if (this.IDPDataSwitch.buildSelected === "off"
       || this.IDPDataSwitch.buildSelected === null ||
        this.IDPDataSwitch.buildSelected === undefined) {
        requestData.build = null;
      }
      if (this.IDPDataSwitch.deploySelected !== "on"
       || this.IDPDataSwitch.deploySelected === null || this.IDPDataSwitch.deploySelected === undefined) {
        requestData.deploy = null;
      }
      if (this.IDPDataSwitch.testSelected === "off"
       || this.IDPDataSwitch.testSelected === null || this.IDPDataSwitch.testSelected === undefined) {
        if (this.idpdataService.isSAPApplication) {
          // requestData.testSelected = 'off';
        } else {
          requestData.testSelected = "off";
          // requestData.test = null;
        }
      } else if (this.IDPDataSwitch.testSelected === "on") {
        requestData.testSelected = "on";
      }

      if (this.IDPDataSwitch.reconcileSelected === undefined
         || this.IDPDataSwitch.reconcileSelected === null || this.IDPDataSwitch.reconcileSelected === "off") {
        requestData.rebase = null;
        console.log("rebase: " + requestData.rebase);
      }

      if (this.IDPDataSwitch.deploySelected === "off"
       && this.IDPDataSwitch.testSelected === "off" && (!this.idpdataService.isSAPApplication)) {
        requestData.envSelected = "";
      }
      requestData.jobParam = [];
      console.log(requestData);


      // console.log(JSON.stringify(requestData));
      this.idpdataService.buildIntervalData[this.idpdataService.index].details = requestData;
      this.idpdataService.statusCheck[this.idpdataService.index] = "off";
      console.log(this.idpdataService.buildIntervalData);
      this.idpdataService.loading = false;
      console.log(this.idpdataService.triggerJobData);
      jQuery("#addDetails").modal("hide");

    }

    console.log(this.idpdataService.triggerJobData);
    this.initialize();
  }

  triggerData() {
    jQuery("#triggerAlertForthis").modal("hide");
    this.idpdataService.loading = true;
    const requestData = this.getRequestData();
    if (requestData === undefined) {
    } else {
        this.idprestapiService.triggerJobs(requestData)
        .then(response => {
            try {
            if (response) {
                const err = response.json().errorMessage;
                if (err === null && response.json().resource.toLowerCase() === "success") {
                this.idpdataService.loading = false;
                this.msg = "success";
                this.disableSubmitBtn = true;
                setTimeout(() => { this.router.navigate(["/previousConfig/stageviewTrigger"]); }, 7000);
                this.redirectTo();
                } else {
                this.disableSubmitBtn = false;
                this.idpdataService.loading = false;
                this.msg = "error";
                setTimeout(() => { this.router.navigate(["/previousConfig"]); }, 7000);
                }
            }
            } catch (e) {
            console.log(e);
            alert("Failed while triggering ");
            }
        });
    }
  }

  getRequestData() {
    let buildUncheck = false;
    let deployUncheck = false;
    let testUncheck = false;
    let reconcileUncheck = false;
    this.IDPParamData.releaseNumber = this.IDPDataSwitch.releaseNumber;
    let requestData;
    if (this.IDPDataSwitch.repoName === "na") {
        this.IDPParamData.deploy.deployArtifact = {};
    } else {
        this.IDPParamData.deploy.deployArtifact = this.IDPParamData.deploy.artifacts;
    }
    if (this.idpdataService.isSAPApplication) {
      if (this.idpdataService.checkpollALM) {
        if (this.IDPParamData.userStories === undefined) {
          this.IDPParamData.userStories = [];
        }
        for (const i of this.selectedUS) {
          this.IDPParamData.userStories.push(i.itemName);
        }
      }
      for (const i of this.selectedItems) {
        this.IDPParamData.transportRequest.push(i.itemName);
      }
    if (this.idpdataService.checkpollALM) {
        for (const data of this.userStoryArray) {
            const userStoryMapsTRs = {
            userstory : "",
            transportRequests : []
          };
          if (this.IDPParamData.userStories.indexOf(data.Userstory) !== -1) {
            const tempTRs = [];
            for (const tr of data.Transport) {
              if (this.IDPParamData.transportRequest.includes(tr)) {
                tempTRs.push(tr);
              }
            }
            if (tempTRs.length > 0) {
              userStoryMapsTRs.userstory = data.Userstory;
              userStoryMapsTRs.transportRequests = tempTRs;
              this.IDPParamData.userStoryMapping.push(userStoryMapsTRs);
            }
          }
        }
      }
    }
    if (this.IDPDataSwitch.buildSelected === "off"
        || this.IDPDataSwitch.buildSelected === null
        || this.IDPDataSwitch.buildSelected === undefined) {
        buildUncheck = true;
    }
    if (this.IDPDataSwitch.testSelected === "off"
        || this.IDPDataSwitch.testSelected === null
        || this.IDPDataSwitch.testSelected === undefined) {
        testUncheck = true;
    }

    if (this.IDPDataSwitch.deploySelected === "off"
        || this.IDPDataSwitch.deploySelected === null
        || this.IDPDataSwitch.deploySelected === undefined) {
        deployUncheck = true;
    }

    if (this.IDPDataSwitch.reconcileSelected === "off"
        || this.IDPDataSwitch.reconcileSelected === null
        || this.IDPDataSwitch.reconcileSelected === undefined) {
        reconcileUncheck = true;
    }

    if ((!this.idpdataService.isSAPApplication && buildUncheck && testUncheck && deployUncheck) ||
        (this.idpdataService.isSAPApplication && buildUncheck && testUncheck && deployUncheck && reconcileUncheck) ||
        (this.idpdataService.triggerJobData.technology === "dbDeployDelphix"
        && this.IDPParamData.deployDB === "on"
        && this.IDPParamData.dbOperations !== undefined
        && this.IDPParamData.dbOperations.length === 0)) {
        this.idpdataService.loading = false;
        if (this.idpdataService.triggerJobData.technology === "dbDeployDelphix"
        && this.IDPParamData.deployDB === "on"
        && this.IDPParamData.dbOperations !== undefined
        && this.IDPParamData.dbOperations.length === 0) {
        this.triggerAlertDBDep();
        } else {
        this.triggerAlert();
        }
    } else {
        if (this.tempDeploySteps.length !== 0 && this.tempDeploySteps.length !== undefined) {
        for (let i = 0; i < this.deployArr.length; i++) {
            const step = this.deployArr[i];
            if (this.deployArr[i] !== null) {
            this.IDPParamData.deploy.deployStep.push(step);
            }
        }
        }
        if (this.tempTestSteps.length !== 0 && this.tempTestSteps.length !== undefined) {
        for (let i = 0; i < this.testArr.length; i++) {
            const step = this.testArr[i];
            if (this.testArr[i] !== null) {
            this.IDPParamData.testStep.push(step);
            }
        }
        }

        if (this.IDPParamData.dbOperations !== undefined && this.IDPParamData.dbOperations.length !== 0) {
        const list = this.idpService.copy(this.IDPParamData.dbOperations);
        this.IDPParamData.dbOperations = "";
        for (let i = 0; i < list.length; i++) {
            this.IDPParamData.dbOperations += list[i] + ";";
        }
        } else if (this.IDPParamData.dbOperations !== undefined) {
        this.IDPParamData.dbOperations = "";
        }
        if (this.virtualServicesArr !== undefined && this.virtualServicesArr.length > 0) {
        this.IDPParamData.virtualServicesList = this.virtualServicesArr;
        }
        requestData = this.IDPParamData;

        if (this.idpdataService.isSAPApplication) {
            if (this.IDPDataSwitch.buildSelected === "on" && this.idpdataService.checkpollALM) {
                if (this.buildEnv[0]) {
                    requestData.envSelected = this.buildEnv[0];
                }
            }
        }
        if (this.IDPDataSwitch.buildSelected === "off"
        || this.IDPDataSwitch.buildSelected === null
        || this.IDPDataSwitch.buildSelected === undefined) {
        requestData.build = null;
        }
        if (this.IDPDataSwitch.deploySelected !== "on"
        || this.IDPDataSwitch.deploySelected === null
        || this.IDPDataSwitch.deploySelected === undefined) {
        requestData.deploy = null;
        }
        if (this.IDPDataSwitch.testSelected === "off"
        || this.IDPDataSwitch.testSelected === null
        || this.IDPDataSwitch.testSelected === undefined) {
            if (!this.idpdataService.isSAPApplication) {
                requestData.testSelected = "off";
            }
        } else if (this.IDPDataSwitch.testSelected === "on") {
            requestData.testSelected = "on";
        }
        if (this.IDPDataSwitch.reconcileSelected === undefined
            || this.IDPDataSwitch.reconcileSelected === null
            || this.IDPDataSwitch.reconcileSelected === "off") {
            requestData.rebase = null;
        }
        if (this.IDPDataSwitch.deploySelected === "off"
        && this.IDPDataSwitch.testSelected === "off"
        && (!this.idpdataService.isSAPApplication)) {
        requestData.envSelected = "";
        }
        let ids = "";
        for (let x = 0; x < this.testSuitId.length; x++) {
        ids += this.testSuitId[x];
        if (x !== this.testSuitId.length - 1) {
            ids += ",";
        }
        }
        requestData.testSuitId = ids;
    }
    return requestData;
  }

  saveWorkflowData() {
    jQuery("#saveAlertForthis").modal("hide");
    this.idpdataService.loading = true;
    const requestData = this.getRequestData();



    if (requestData === undefined) {
      console.log("Request data undefined: " + requestData);
    } else {
      console.log(requestData);

      console.log(JSON.stringify(requestData));
      if (this.workflowSequenceIndexI !== undefined) {
        console.log("workflowSequenceIndex: " + this.workflowSequenceIndexI);
        this.idpdataService.workflowData.workflowSequence[this.workflowSequenceIndexI].
        applicationDetails[this.workflowSequenceIndexJ].pipelineDetails[this.workflowSequenceIndexK] = requestData;
        this.idpdataService.workflowData.workflowSequenceTemp[this.workflowSequenceIndexI].IDPDataSwitch = this.IDPDataSwitch;
      } else {
        console.log("Unable to update as workflowSequenceIndex is: " + this.workflowSequenceIndexI);
      }
    }
    this.idpdataService.loading = false;
    jQuery("#addPipelineDetails").modal("hide");
  }

  redirectTo() {

  }

  getSlaveStatus(slave) {
    this.idprestapiService.getSlaveStatus(slave).then(response => {
        if (response) {
        if (response.json().resource !== null
            && response.json().resource !== "[]"
            && response.json().resource !== "{}") {
            if (response.json().resource !== null) {
            this.slavestatus = response.json().resource;
            } else {
            this.slavestatus = "Not Found";
            }
        }
        }
    });
  }

  getTestSlaveStatus(slave) {
    this.idprestapiService.getSlaveStatus(slave).then(response => {
        if (response) {
        if (response.json().resource !== null
            && response.json().resource !== "[]"
            && response.json().resource !== "{}") {
            if (response.json().resource !== null) {
            this.testslavestatus = response.json().resource;
            } else {
            this.testslavestatus = "Not Found";
            }
        }

        }
    });
  }

  getArtifactsRm() {
    if (this.IDPDataSwitch.repoName !== "na") {
        if (this.IDPDataSwitch.deploySelected === "on"
        && (this.IDPDataSwitch.buildSelected !== "on"
            || this.IDPParamData.technology === "dbDeployDelphix")) {
        if (this.IDPParamData.envSelected !== "") {
            const data = {
            "applicationName": this.IDPParamData.applicationName,
            "artifactList": this.idpdataService.triggerJobData.artifactList,
            "environmentName": this.IDPParamData.envSelected,
            "pipelineName": this.IDPParamData.pipelineName,
            "releaseNumber": this.IDPDataSwitch.releaseNumber
            };
            this.idprestapiService.getArtifactsRm(data).then(response => {
            if (response) {
                if (response.json().resource !== null
                && response.json().resource !== "[]"
                && response.json().resource !== "{}") {
                this.artifacts = JSON.parse(response.json().resource).artifactList;
				this.allArtifactlist = JSON.parse(response.json().resource).artifactList;
                const tempLatestArtifact = [{
                    "artifactName": "",
                    "artifactID": "", "groupId": "", "nexusURL": "",
                    "repoName": "", "version": "", "buildModules": "",
                    "enviromment": "", "userInfo": "", "timestamp": "", "downloadURL": ""
                }];
				                                
				this.filterArtifacts();
				const artifactSize=this.artifacts.length;
                tempLatestArtifact[0].artifactName = this.idpdataService.triggerJobData.applicationName + "_" +
                    this.idpdataService.triggerJobData.pipelineName + "_latestArtifact";
				alert("version" + this.artifacts[artifactSize-1].version); 
                tempLatestArtifact[0].groupId = this.idpdataService.triggerJobData.applicationName;
                tempLatestArtifact[0].artifactID = this.idpdataService.triggerJobData.pipelineName;
                tempLatestArtifact[0].nexusURL = this.idpdataService.triggerJobData.nexusURL;
                tempLatestArtifact[0].repoName = this.idpdataService.triggerJobData.repoName;
				tempLatestArtifact[0].downloadURL="http://"+tempLatestArtifact[0].nexusURL + "/repository/" + tempLatestArtifact[0].repoName+"/"+this.IDPParamData.applicationName+"/"+this.IDPParamData.pipelineName+"/"+this.artifacts[artifactSize-1].version+"/"+this.IDPParamData.pipelineName+"-"+this.artifacts[artifactSize-1].version+".zip";
				this.artifacts.push(tempLatestArtifact[0]);
				}
            }
            this.idpdataService.loading = false;
            });
			
        }
        }
    }
  }
  
  filterArtifacts() {
      this.artifacts = [];
      for (let i = 0; i < this.allArtifactlist.length; i++) {
        if (_.includes(this.allArtifactlist[i].version, this.IDPDataSwitch.releaseNumber) !== false) {
            this.artifacts.push(this.allArtifactlist[i]);
        }

      }
      if (this.artifacts.length === 0) {
            alert("Please select both build and deploy");
      }
  }

  /*calling rest service for getting jobparam details*/
  getJobParamDetails() {
    this.idprestapiService.getJobParamList()
        .then(response => {
        if (response) {
            const resp = response.json().resource;
            let parsed;
            try {
            if (response.json().resource !== "{}" && response.json().resource !== null) {
                parsed = response.json().resource;
                this.idpdataService.triggerJobData.jobParamList = JSON.parse(parsed);
                this.jobParamList = this.idpdataService.triggerJobData.jobParamList;
                this.IDPParamData.jobParam = this.jobParamList;
                let checkStatic = 0;
                for (let x = 0; x < this.jobParamList.length; x++) {
                if (this.jobParamList[x].jobParamSatic === true) {
                    checkStatic++;
                }
                }
                if (checkStatic === this.jobParamList.length) {
                this.showJobParamFlag = "off";
                } else {
                this.showJobParamFlag = "on";
                }
            } else {
                console.log("Failed to get JobparamDetails Details");
            }
            } catch (e) {
            alert("Failed to get JobparamDetails Details");
            console.log(e);
            }
        }
        });
  }

  setValue(val) {
    this.IDPParamData.branchOrTagValue = val;
    this.branchList = this.idpService.copy(this.branchTemp);
    this.tagList = this.idpService.copy(this.tagTemp);
    this.fetchReleaseBranches(this.IDPDataSwitch.releaseNumber);
  }

  setBranchTag(data) {
    this.branchOrTag = data;
    this.IDPParamData.branchOrTag = data;
    this.IDPParamData.branchOrTagValue = "";
    this.branchList = this.idpService.copy(this.branchTemp);
    this.tagList = this.idpService.copy(this.tagTemp);
    this.fetchReleaseBranches(this.IDPDataSwitch.releaseNumber);
  }
  setBranchOrTag() {
    this.IDPParamData.branchOrTag = "branch";
    this.IDPParamData.branchOrTagValue = "";
    this.branchList = this.idpService.copy(this.branchTemp);
    this.tagList = this.idpService.copy(this.tagTemp);
    this.fetchReleaseBranches(this.IDPDataSwitch.releaseNumber);
  }

  searchValue() {
    const list = [];
    if (this.branchOrTag === "branch") {
        this.tagList = this.idpService.copy(this.tagTemp);
        if (this.IDPParamData.branchOrTagValue !== "") {
        let branchFrom = this.idpService.copy(this.branchTemp);
        if (this.requiredSCM) {
            branchFrom = this.idpService.copy(this.branchListReqTemp);
        }
        for (let x = 0; x < branchFrom.length; x++) {
            if (branchFrom[x].indexOf(this.IDPParamData.branchOrTagValue) !== -1) {
            list.push(branchFrom[x]);
            }
        }
        this.branchList = this.idpService.copy(list);
        } else {
        this.branchList = this.idpService.copy(this.branchTemp);
        this.fetchReleaseBranches(this.IDPDataSwitch.releaseNumber);
        }
    } else if (this.branchOrTag === "tag") {
        this.branchList = this.idpService.copy(this.branchTemp);
        this.fetchReleaseBranches(this.IDPDataSwitch.releaseNumber);
        if (this.IDPParamData.branchOrTagValue !== "") {
        for (let x = 0; x < this.tagTemp.length; x++) {
            if (this.tagTemp[x].indexOf(this.IDPParamData.branchOrTagValue) !== -1) {
            list.push(this.tagTemp[x]);
            }
        }
        this.tagList = this.idpService.copy(list);
        } else {
        this.tagList = this.idpService.copy(this.tagTemp);
        }
    }
  }
  fetchSteps() {
    this.dropdownListDeploy = [];
    this.dropdownListTest = [];
    this.testStepWithToolList = [];
    this.tempTestSteps = [];
    this.testArr = [];
    const data = {
        "application_name": this.IDPParamData.applicationName,
        "pipeline_name": this.IDPParamData.pipelineName,
        "env_name": this.IDPParamData.envSelected
    };
    let resp;
    this.idprestapiService.fetchTriggerSteps(data)
        .then(response => {
        try {
            if (response) {
            const err = response.json().errorMessage;
            if (err === null && response.json().status.toLowerCase() === "success") {
                resp = JSON.parse(response.json().resource);
                if (resp.deploySteps !== undefined) {
                for (let i = 0; i < resp.deploySteps.length; i++) {
                    const step = {
                    "id": i,
                    "itemName": resp.deploySteps[i]
                    };
                    this.dropdownListDeploy.push(step);
                    this.deployArr[i] = null;
                }
                }
                if (resp.testSteps !== undefined) {
                for (let j = 0; j < resp.testSteps.length; j++) {
                    const step = {
                    "id": j,
                    "itemName": resp.testSteps[j].stepName
                    };
                    this.testStepWithToolList.push(resp.testSteps[j]);
                    this.dropdownListTest.push(step);
                    this.testArr[j] = null;
                }
                }
            }
            }
        } catch (e) {
            console.log(e);
        }
        });
  }
  getTestPlan() {
    let flagTestPlan = false;
    for (let j = 0; j < this.testArr.length; j++) {
        for (let i = 0; i < this.testStepWithToolList.length; i++) {
        if (this.testArr[j] === this.testStepWithToolList[i].stepName) {
            if (this.testStepWithToolList[i].toolName === "mtm") {
            flagTestPlan = true;
            this.IDPParamData.mtmStepName = this.testStepWithToolList[i].stepName;
            break;
            }
        }
        }
    }

    if (flagTestPlan) {
        this.idpdataService.loading = true;
        this.idprestapiService.getTestPlanList(this.IDPParamData.applicationName, this.IDPParamData.pipelineName)
        .then(response => {
            if (response) {
            this.idpdataService.loading = false;
            const respResource = JSON.parse(response.json().resource);
            this.testPlansList = respResource;
            }
        });
    }
  }
  getTestSuits(planId) {
    this.idpdataService.loading = true;
    this.testSuitList = [];
    this.testSuitId = [];
    this.idprestapiService.getTestSuitList(planId, this.IDPParamData.applicationName,
        this.IDPParamData.pipelineName)
        .then(response => {
        if (response) {
            this.idpdataService.loading = false;
            const respResource = JSON.parse(response.json().resource);
            const testSuitList = respResource;
            this.addTestSuitesChildren(testSuitList);
        }
        });
  }

  getEnvPair() {
    this.envJson = {
        "applicationName": this.IDPParamData.applicationName,
        "pipelineName": this.IDPParamData.pipelineName,
        "envName": this.IDPParamData.envSelected
    };
    this.idprestapiService.getEnvironmentPairs(this.envJson).then(response => {
        if (response) {
        if (response.json().resource !== null) {
            this.IDPParamData.pairNames = JSON.parse(response.json().resource).names;
        }
        }
    });
  }
  addTestSuitesChildren(array) {
    const tempJson = {};
    for (let i = 0; i < array.length; i++) {
        const suiteObj = array[i];
        suiteObj.children = [];
        tempJson[suiteObj.testSuitId] = suiteObj;
        let parentKey;
        if (suiteObj.testSuitParent === "na") {
        parentKey = "root";
        } else {
        parentKey = suiteObj.testSuitParent;
        }
        if (parentKey !== "root" && parentKey !== "na") {
        const parentKeyJson = JSON.parse(suiteObj.testSuitParent);
        parentKey = parentKeyJson.id;
        }
        if (!tempJson[parentKey]) {
        tempJson[parentKey] = {
            children: []
        };
        }
        tempJson[parentKey].children.push(suiteObj);
    }
    let finalList;
    finalList = JSON.parse(JSON.stringify(tempJson["root"].children).split("\"testSuitName\":").join("\"text\":"));
    finalList = JSON.parse(JSON.stringify(finalList).split("\"testSuitId\":").join("\"value\":"));
    finalList = JSON.parse(JSON.stringify(finalList).replace("\"testSuitParent\":\"na\",", ""));
    const result = this.createTreeView({ "text": "na", "value": "na", "children": finalList });
    this.testSuitList = result.children;
  }

  onSelectedChange(downlineItems: DownlineTreeviewItem[]) {

  }

  addItem(item) {
    if (item.srcElement.checked) {
        if (this.testSuitId.indexOf(item.srcElement.name) === -1) {
        this.testSuitId.push(item.srcElement.name);
        }
        return true;
    } else {
        if (this.testSuitId.indexOf(item.srcElement.name) !== -1) {
        this.testSuitId.splice(this.testSuitId.indexOf(item.srcElement.name), 1);
        }
        return false;
    }
  }

  checkChecked(checked) {
    if (checked) {
        return true;
    } else {
        return false;
    }
  }
  createTreeView(data) {
    if (data.children.length === 0) {
        const x = { "value": data.value, "text": data.text, "children": [], "checked": false, "collapsed": true };
        return new TreeviewItem(x, true);
    } else {
        const d = { "text": data.text, "value": data.value, "children": data.children, "checked": false, "collapsed": true };
        for (let i = 0; i < data.children.length; i++) {
        data.children[i] = this.createTreeView(data.children[i]);
        d.children[i] = data.children[i];
        }
        return new TreeviewItem(d, true);
    }
  }

  // <----- Deploy Steps Multi-select Dropdown functions---->

  onItemSelectDepSteps(item: any) {
    this.deployArr[item.id] = item.itemName;
  }
  onItemDeSelectDepSteps(item: any) {
    const i = this.deployArr.indexOf(item.itemName);
    if (i !== -1) {
        this.deployArr[i] = null;
    }
  }
  onSelectAllDepSteps(items: any) {
    for (const item of items) {
        this.deployArr[item.id] = item.itemName;
    }
  }
  onDeSelectAllDepSteps(items: any) {
    this.deployArr = [];
  }
  // <----- Test Steps Multi-select Dropdown functions---->
  onItemSelectTestSteps(item: any) {
    this.testArr[item.id] = item.itemName;

  }
  onItemDeSelectTestSteps(item: any) {
    const i = this.testArr.indexOf(item.itemName);
    if (i !== -1) {
        this.testArr[i] = null;
    }
    for (let k = 0; k < this.testStepWithToolList.length; k++) {
        if (item.itemName === this.testStepWithToolList[k].stepName) {
        if (this.testStepWithToolList[k].toolName === "mtm") {
            this.IDPParamData.mtmStepName = "";
            this.testPlansList = [];
            this.testSuitList = [];
            this.testSuitId = [];
            this.IDPParamData.testPlanId = "";
            break;
        }
        }
    }
  }
  onSelectAllTestSteps(items: any) {
    for (const item of items) {
        this.testArr[item.id] = item.itemName;
    }
  }
  onDeSelectAllTestSteps(items: any) {
    this.testArr = [];
    this.IDPParamData.mtmStepName = "";
    this.testPlansList = [];
    this.testSuitList = [];
    this.testSuitId = [];
    this.IDPParamData.testPlanId = "";
  }

  updateSelectedOperation(event) {
    if (event.target.checked) {
        if (this.IDPParamData.dbOperations.indexOf(event.target.name) < 0) {
        this.IDPParamData.dbOperations.push(event.target.name);
        }
    } else {
        if (this.IDPParamData.dbOperations.indexOf(event.target.name) > -1) {
        this.IDPParamData.dbOperations.splice(this.IDPParamData.dbOperations.indexOf(event.target.name), 1);
        }
    }
  }
  unCheckCast() {
    this.IDPParamData.build.cast = "off";
    this.IDPParamData.castSlaveName = "";
    this.IDPParamData.build.oldVersion = "";
    this.IDPParamData.build.newVersion = "";
  }
  scheduleJobOn() {
    const data = {
        "applicationName": this.IDPParamData.applicationName,
        "pipelineName": this.IDPParamData.pipelineName,
        "userName": this.idpdataService.idpUserName
    };
    this.idpdataService.loading = true;
    this.idprestapiService.getPipelineDetails(data)
        .then(response => {
        console.log(new Date().toUTCString(), "Pipeline details retrieved");
        try {
            const responseData = this.idpencryption.decryptAES(response.json().resource);
            let resp = JSON.parse(responseData);
            resp = this.idpencryption.doubleEncryptPassword(resp.pipelineJson);
            this.idpdataService.buildIntervalData = resp.basicInfo.customTriggerInterval.interval;
        } catch (e) {
            console.log("Failed to get the Build Interval Schedule Details");
            console.log(e);
        }
        });
    this.idpdataService.loading = false;
    return "on";
  }
  scheduleJobOff() {
    this.idpdataService.schedulePage = 0;
    return "off";
  }

  addJob() {
    this.idpdataService.buildIntervalData.push({ "type": "", "minute": "", "time": "", "details": {} });
  }

  closeModal(id) {
    jQuery("#" + id).modal("hide");
  }

  getArtifactLatestDetails(artifacts) {
    console.log(artifacts.artifactName);
    const dashboardURL: String = this.idpdataService.IDPDashboardURL;
    const hostName: String = dashboardURL.split(":")[1].substr(2);
    this.dashboardUrl = "https://" + hostName +
        ":3000/dashboard/db/artifact-view?orgId=1&var-Application=" + this.IDPParamData.applicationName;
    this.dashboardUrl = this.dashboardUrl + "&var-Pipeline=" + this.IDPParamData.pipelineName;
    this.dashboardUrl = this.dashboardUrl + "&var-ReleaseNo=" + this.IDPDataSwitch.releaseNumber;
    this.dashboardUrl = this.dashboardUrl + "&var-ArtifactId=" + artifacts.artifactName;
    this.idprestapiService.getArtifactLatestDetails(artifacts.artifactName).then(response => {
        if (response) {
        if (response.json().resource !== null && response.json().resource !== "[]"
            && response.json().resource !== "{}") {
            this.artifactDetails = JSON.parse(response.json().resource).artifactDetails;
            this.packageContent = JSON.parse(response.json().resource).packageContent;
            for (const artifactvalue of this.artifactDetails) {

            }
            if (this.packageContent !== undefined && this.packageContent.ant !== undefined
            && this.packageContent.ant.moduleName !== undefined) {
            this.ant = this.packageContent.ant.moduleName;
            }
            if (this.packageContent !== undefined && this.packageContent.dotNet !== undefined
            && this.packageContent.dotNet.moduleName !== undefined) {
            this.dotNet = this.packageContent.dotNet.moduleName;
            }
            if (this.packageContent !== undefined && this.packageContent.bigData !== undefined
                && this.packageContent.bigData.moduleName !== undefined) {
                this.bigData = this.packageContent.bigData.moduleName;
            }
            if(this.packageContent !== undefined && this.packageContent.pega !== undefined){
                this.pega = this.packageContent.pega;
            }
			      if(this.packageContent !== undefined && this.packageContent.siebel !== undefined){
              this.siebel = this.packageContent.siebel;
            }
        }

        }
    });

  }
  clearArtifact() {
    this.artifactDetails = [];
    this.informatica = [];
    this.dotNet = [];
    this.bigData = [];
    this.ant = [];
    this.siebel.repoList = [];
    this.siebel.nonRepoList = [];
    this.pega.pegaFileList = [];
  }

  isLatestArtifact() {
    return (this.IDPParamData.deploy.artifacts.artifactName as String).endsWith("_latestArtifact");
  }

}
