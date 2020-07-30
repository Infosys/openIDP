import {
  Component,
  OnInit,
  ViewChild,
  Input,
  Output,
  EventEmitter,
} from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { IdpService } from "../idp-service.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router } from "@angular/router";
import { IdpdataService } from "../idpdata.service";
import { DataTable, DataTableResource } from "angular-2-data-table";
import { ActivatedRoute } from "@angular/router";
import { TabsetComponent } from "ngx-bootstrap";
import * as _ from "lodash";
import {
  TreeviewModule,
  TreeviewConfig,
  TreeviewItem,
  TreeviewHelper,
  DownlineTreeviewItem,
  TreeviewI18n,
  TreeviewComponent,
  TreeviewEventParser,
  OrderDownlineTreeviewEventParser,
} from "ngx-treeview";
import { IDPEncryption } from "../idpencryption.service";
import { BsModalService } from "ngx-bootstrap/modal";
import { BsModalRef } from "ngx-bootstrap/modal/bs-modal-ref.service";
import { Item } from "angular2-multiselect-dropdown";

declare var jQuery: any;
@Component({
  selector: "app-trigger",
  templateUrl: "./triggerPipeline.component.html",
  styleUrls: ["./triggerPipeline.component.css"],
  providers: [
    {
      provide: TreeviewEventParser,
      useClass: OrderDownlineTreeviewEventParser,
    },
  ],
})
export class TriggerComponent implements OnInit {
  @ViewChild(TreeviewComponent) treeviewComponent: TreeviewComponent;
  @ViewChild("modalforAlert") modalforAlert;
  @ViewChild("modalforArtifactAlert") modalforArtifactAlert;
  @ViewChild("modalforSubmitAlert") modalforSubmitAlert;
  @ViewChild("modalforTrigger") modalforTrigger;
  @ViewChild("modalforAlertDBDep") modalforAlertDBDep;
  @ViewChild("modalforReqSCMAlert") modalforReqSCMAlert;
  @ViewChild("staticTabs") staticTabs: TabsetComponent;
  // save button both for schedule and workflow (master pipeline) feature
  @ViewChild("modalforSave") modalforSave;
  @Input() workflowSequenceIndexI: number;
  @Input() workflowSequenceIndexJ: number;
  @Input() workflowSequenceIndexK: number;
  @Output() onTriggerDetailsSaved = new EventEmitter<boolean>();
  isParameterDisabled = false;
  applicationName: any = "";
  pipelineName: any = "";
  data = { applicationName: "", pipelineName: "", userName: "" };
  paramData = { applicationName: "", pipelineName: "", userName: "" };
  approvalStages = [];

  SAPEnvList: any = [];
  crApprover: any = "";
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
  isCollapsed: boolean = true;
  scmType: any;
  dropdownListTest: any = [];
  testSuitList: any = [];
  dropdownListDeploy: any = [];
  rmsComponentName: any;
  rmsArtifacts: any = [];

  trialArr = [];
  deployMultiselectSettings = {
    singleSelection: false,
    text: "Select Deploy Steps",
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All",
  };
  testMultiselectSettings = {
    singleSelection: false,
    text: "Select Test Steps",
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All",
  };
  virtualServiceMultiselectSettings = {
    singleSelection: false,
    text: "Select Virtual Services",
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All",
  };
  ibmRQMTestCaseMultiselectSettings = {
    singleSelection: false,
    text: "Select IBM RQM Test Case",
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All",
  };
  dbMultiselectSettings = {
    singleSelection: false,
    text: "Select DB operations",
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All",
  };
  isBuild: any = false;
  isDeploy: any = false;
  testSuitId: any = [];
  resetSelect = "off";
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
  grantAccess: any = this.IdpdataService.data.grantAccess;
  isEnableTrack: string;
  config = TreeviewConfig.create({
    hasAllCheckBox: false,
    hasFilter: false,
    hasCollapseExpand: false,
    decoupleChildFromParent: true,
    maxHeight: 400,
  });
  siebel = {
    repoList: [],
    nonRepoList: [],
  };
  pega = {
    pegaFileList: [],
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
  rmsEnv: any;
  alertModalRef: BsModalRef;
  missingSCMBranchModalRef: BsModalRef;
  confirmSaveModalRef: BsModalRef;
  dbDepModalRef: BsModalRef;
  selectArtifactModalRef: BsModalRef;
  submitModalRef: BsModalRef;
  triggerModalRef: BsModalRef;
  disableSubmitBtn: boolean = false;
  tempTrArrayDeploy: any = [];
  tempTrArrayJira: any = [];
  userstoryDropdownSettings: any = {};
  changeDocumentDropdownSettings: any = {};
  userStoryList: any;
  selectedUS: any;
  selectedUSStr: any;
  selectedUSData: any;
  selectedCD: any;
  selectedCDData: any;
  cdListWithDescription: any = [];
  cdListWithStatus: any = [];
  changeRequestDetails = {
    buildCaCr: [],
    buildUtCr: [],
    buildCr: [],
    changeDocumentMapping: [
      {
        changeDocument: "",
        changeDocumentData: [
          {
            crNumber: "",
            crDescription: "",
            cdNumber: "",
            cdDescription: "",
            cdStatus: "",
            cdType: "",
            trNumber: "",
            trDescription: "",
            trStatus: "",
            track: "",
            trackDescription: "",
            subTrack: "",
            subTrackDescription: "",
          },
        ],
        developer: "",
        technicalReviewer: "",
        functionalReviewer: "",
        functionalTester: "",
        CC: "",
        PFA: "",
        DM: "",
      },
    ],
    crcdRelationList: [
      {
        changeRequest: "",
        changeDocumentList: [],
      },
    ],
    deployEnvCrList: [
      {
        environmentName: "",
        changeRequestList: [],
      },
    ],
    deployEnvCdList: [
      {
        environmentName: "",
        changeDocumentList: [],
      },
    ],
    listOfStatus: [],
    crAndDescription: [],
    crsBasedOnStatusOfCD: [
      {
        statusOfCD: "",
        crAndDescriptionList: [],
      },
    ],
    crsBasedOnTrackSubtrack: [
      {
        track: "",
        trackDescription: "",
        subTrack: "",
        subTrackDescription: "",
        crAndDescriptionList: [],
        trackRolesMapping: [],
      },
    ],
  };

  charmTriggerData = {
    changeRequest: "",
    changeDocument: [],
    sapTrackSelected: "",
    sapSubTrackSelected: "",
  };
  changeRequestList: any = [];
  changeRequestListTemp: any = [];
  changeDocumentList: any = [];
  changeDocumentListTemp: any = [];
  sapTrackListTemp: any = [];
  sapSubTrackListTemp: any = [];
  hideChangeDocument: boolean = false;
  hideChangeRequest: boolean = false;
  hideTrackDetails: boolean = false;
  isAllowed: boolean = false;
  showErrorForST: boolean = false;
  cdDetailsList: any = [];
  hideLandscape: any = "";
  changeDocumentListDescDropdown = [];
  rmsApprovedArtifact: any;
  showPermissionError: any = "";
  selectedLandscapeType: string = "";

  // [
  //   {
  //     "crNumber": "",
  //     "crDescription": "",
  //     "cdDescription": "",
  //     "cdStatus": "",
  //     "cdType": "",
  //     "trNumber": "",
  //     "trDescription": "",
  //     "trStatus": "",
  //     "cdNumber": ""
  //   }
  // ]

  ngOnInit() {
    this.idpdataService.devServerURL = this.idprestapiService.startupData.idpresturl;
    this.getapptype(this.idpdataService.appName);
    this.triggerDataInitialization();
    this.getJobParamDetails();
    if (this.IDPParamData.technology === "sapcharm") {
      this.getChangeRequest();
    }
  }

  constructor(
    public idpdataService: IdpdataService,
    private idprestapiService: IdprestapiService,
    private router: Router,
    private route: ActivatedRoute,
    public idpService: IdpService,
    private idpencryption: IDPEncryption,
    private modalService: BsModalService,
    private IdprestapiService: IdprestapiService,
    private IdpdataService: IdpdataService
  ) {
    this.route.queryParams.subscribe((params) => {
      if (params["applicationName"] && params["pipelineName"]) {
        this.idpdataService.appName = params["applicationName"];
        this.idpdataService.pipelineName = params["pipelineName"];
      }
    });

    this.buildIntervalData = this.idpdataService.buildIntervalData;
    if (this.idpdataService.buildIntervalData === undefined) {
      this.idpdataService.buildIntervalData = [];
    }
  }

  populateDBDeployRollBackTypeList() {
    this.dbDeployRollbackTypeList = [
      { name: "byTag", value: "byTag" },
      { name: "byChangeSet", value: "byChangeSet" },
      { name: "byHours", value: "byHours" },
      { name: "byDate", value: "byDate" },
    ];
  }

  getapptype(appName) {
    this.IdprestapiService.checkForApplicationType(appName).then((response) => {
      try {
        if (response) {
          if (
            response.json().errorMessage === null &&
            response.json().resource !== ""
          ) {
            if (response.json().resource === "true") {
              //alert("true")
              this.idpdataService.isSAPApplication = true;
            } else {
              this.idpdataService.isSAPApplication = false;
            }
          } else {
            alert("Failed to verify application Type");
          }
        }
      } catch (e) {
        alert("Failed during verifying the application type");
      }
      this.idpdataService.loading = false;
    });
  }

  changeDBDeployRollbackValues() {
    this.IDPParamData.deploy.dbDeployRollbackValue = "";
  }

  selectTab(tabId: number) {
    this.staticTabs.tabs[tabId].active = true;
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
    // SAP filter to fetch landscape names
    if (this.idpdataService.isSAPApplication) {
      this.setLandscapeNames();
    }
  }

  // Fetch userstory details from Jira as per the release number selected
  fetchUserStories(releaseNumber) {
    this.userStoryArray = [];
    // this.IDPParamData.userStories = [];
    this.selectedUS = [];
    this.userStoryList = [];
    this.selectedUSData = [];
    const data = {
      application_name: this.IDPParamData.applicationName,
      pipeline_name: this.IDPParamData.pipelineName,
      release: this.IDPDataSwitch.releaseNumber,
    };

    try {
      if (this.idpdataService.checkpollALM) {
        this.idpdataService.loading = true;
        this.idprestapiService.getUserStoriesSAP(data).then((response) => {
          if (response) {
            this.idpdataService.loading = false;
            if (
              response.json().resource !== null &&
              response.json().resource !== "[]" &&
              response.json().resource !== "{}"
            ) {
              if (
                JSON.parse(response.json().resource).UserStoryInfo !== undefined
              ) {
                const userStoryArray = JSON.parse(response.json().resource)
                  .UserStoryInfo;
                const temp = [];
                for (let i = 0; i < userStoryArray.length; i++) {
                  if (userStoryArray[i].Transport) {
                    if (userStoryArray[i].Transport.length !== 0) {
                      temp.push({
                        Userstory: userStoryArray[i].Userstory,
                        Transport: userStoryArray[i].Transport,
                      });
                    }
                  }
                }
                this.userStoryArray = temp;
              }
            }
            if (this.userStoryArray.length > 0) {
              for (let i = 0; i < this.userStoryArray.length; i++) {
                this.userStoryList.push({
                  id: i,
                  itemName: this.userStoryArray[i].Userstory,
                });
              }
            }
          }
        });
      }
    } catch (e) {
      alert("Failed in getting User Stories data");
    }
  }

  fetchSapSubTracks() {
    this.sapSubTrackListTemp = [];
    const trackList = this.changeRequestDetails.crsBasedOnTrackSubtrack;
    for (let i = 0; i < trackList.length; i++) {
      let localTrackList = trackList[i].track;
      if (this.charmTriggerData.sapTrackSelected === localTrackList) {
        this.sapSubTrackListTemp.push({
          val: trackList[i].subTrack,
          description:
            trackList[i].subTrack + " --> " + trackList[i].subTrackDescription,
        });
      }
    }
  }

  addArtifact() {
    const temp = this.idpdataService.triggerJobData.artifactList;
    this.artifacts = [];
    this.allArtifactlist = [];
    if (
      this.IDPDataSwitch.deploySelected === "on" &&
      this.IDPDataSwitch.buildSelected === "on"
    ) {
      const tempLatestArtifact = [
        {
          artifactName: "",
          artifactID: "",
          groupId: "",
          nexusURL: "",
          repoName: "",
          version: "",
          buildModules: "",
          enviromment: "",
          userInfo: "",
          timestamp: "",
          downloadURL: "",
        },
      ];
      tempLatestArtifact[0].artifactName =
        this.idpdataService.triggerJobData.applicationName +
        "_" +
        this.idpdataService.triggerJobData.pipelineName +
        "_latestArtifact";
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

        // console.log(list[y]);
        const data = list[y];
        this.idpdataService.loading = true;
        this.idprestapiService
          .getWorkItems(data)
          .then((response) => {
            if (response) {
              this.idpdataService.loading = false;
              const fields = response.json().fields;
              const msg = response.json().message;

              if (fields) {
                const workType = fields["System.WorkItemType"];
                const obj = { workItem: data, status: workType };
                mainList.push(obj);
              } else if (msg && msg.indexOf("does not exist") !== -1) {
                const obj = { workItem: data, status: "not Found" };
                mainList.push(obj);
              }
              if (mainList.length === list.length) {
                // console.log(mainList.length);
                this.checkValidity(mainList);
              }
            }
            this.idpdataService.loading = false;
          })
          .catch((error) => {
            this.idpdataService.loading = false;
            //console.log(error);
          });
      }
    }
  }

  checkValidity(data) {
    for (let i = 0; i < data.length; i++) {
      if (data[i].status === "not Found") {
        this.notFound.push(data[i]);
      } else if (
        data[i].status !== "Product Backlog Item" &&
        data[i].status !== "Task"
      ) {
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
    this.idprestapiService
      .getUserStories(this.IDPParamData.jiraProjectKey)
      .then((response) => {
        if (response) {
          this.idpdataService.loading = false;
          this.valideStory = "true";
          if (response.json().resource !== null) {
            const userStories = JSON.parse(response.json().resource)
              .userStoryInfo;
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
    if (
      this.IDPDataSwitch.subModules !== undefined &&
      this.IDPDataSwitch.subModules.length !== undefined
    ) {
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
    if (
      this.idpdataService.triggerJobData.artifactList === undefined ||
      this.idpdataService.triggerJobData.artifactList.length === 0 ||
      this.idpdataService.triggerJobData.artifactList === null
    ) {
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
        alert(
          "You can not deploy this application now. Please trigger it at planned time."
        );
        const currentTime = new Date();
        const hours = currentTime.getHours();
        const minutes = currentTime.getMinutes();
        const timeZoneOffset = hours + ":" + minutes;
        this.idprestapiService
          .getAvailableSlot(
            this.IDPParamData.applicationName,
            this.IDPDataSwitch.releaseNumber,
            timeZoneOffset
          )
          .then((response) => {
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
          this.Env = tempEnv;
        }

        if (this.Env.length === 0) {
          alert(
            "You can not deploy this application now. Please trigger it at planned time"
          );
          const currentTime = new Date();
          const hours = currentTime.getHours();
          const minutes = currentTime.getMinutes();
          const timeZoneOffset = hours + ":" + minutes;
          this.idprestapiService
            .getAvailableSlot(
              this.IDPParamData.applicationName,
              this.IDPDataSwitch.releaseNumber,
              timeZoneOffset
            )
            .then((response) => {
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
        if (
          this.IDPDataSwitch.testSelected === "on" &&
          this.IDPDataSwitch.deploySelected === "off"
        ) {
          this.Env = this.IDPDataSwitch.testEnv;
        }
        return;
      }
    }
  }
  getEnvNames() {
    this.idprestapiService
      .getEnvNames(
        this.IDPParamData.applicationName,
        this.IDPDataSwitch.releaseNumber
      )
      .then((response) => {
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
  checkDBOperation() {}

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
          this.IDPParamData.deploy.subModule.push(
            this.IDPDataSwitch.subModules[i].moduleName
          );
        }
      }
    }
  }
  checkEnvToPopulate() {
    if (
      this.IDPDataSwitch.testSelected === "on" &&
      this.IDPDataSwitch.deploySelected === "on"
    ) {
      this.Env = this.IDPDataSwitch.deployTestEnv;
      this.getEnvNames();
      this.Envbiz = this.IDPDataSwitch.deploytestbizEnv;
    } else if (
      this.IDPDataSwitch.testSelected === "on" &&
      this.IDPDataSwitch.deploySelected === "off"
    ) {
      this.Env = this.IDPDataSwitch.testEnv;
    } else if (
      this.IDPDataSwitch.buildSelected === "on" &&
      this.IDPDataSwitch.deploySelected === "on"
    ) {
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

  triggerDataInitialization() {
    this.IdpdataService.isSAPApplication = false;
    this.paramData.applicationName = this.idpdataService.appName;
    this.paramData.pipelineName = this.idpdataService.pipelineName;
    this.idprestapiService.getUserName().then((response) => {
      try {
        if (response) {
          const result = response.json().resource;
          // console.log(JSON.parse(result));
          this.idpdataService.idpUserName = JSON.parse(result).user_id;
          // //console.log(response);
          this.paramData.userName = this.idpdataService.idpUserName;
          // console.log("data", this.paramData);

          this.IdprestapiService.triggerJob(this.paramData).then((response) => {
            if (response) {
              const result = response.json().resource;
              if (result !== "{}" && result !== null) {
                this.IdpdataService.triggerJobData = JSON.parse(result);
                const temp = JSON.parse(result);
                const checkInBuild =
                  this.IdpdataService.triggerJobData.hasOwnProperty("build") &&
                  this.IdpdataService.triggerJobData.build.approveBuild !==
                    undefined &&
                  this.IdpdataService.triggerJobData.build.approveBuild !==
                    null &&
                  this.IdpdataService.triggerJobData.build.approveBuild
                    .length !== 0;
                const checkInDeploy =
                  this.IdpdataService.triggerJobData.hasOwnProperty("deploy") &&
                  this.IdpdataService.triggerJobData.deploy
                    .workEnvApprovalList !== undefined &&
                  this.IdpdataService.triggerJobData.deploy
                    .workEnvApprovalList !== null &&
                  Object.keys(
                    this.IdpdataService.triggerJobData.deploy
                      .workEnvApprovalList
                  ).length !== 0 &&
                  this.IdpdataService.triggerJobData.deploy.workEnvApprovalList
                    .constructor === Object;
                if (checkInBuild || checkInDeploy) {
                  this.IdpdataService.checkPausedBuilds = true;
                  if (checkInBuild) {
                    this.isBuild = true;
                  }
                  if (checkInDeploy) {
                    this.isDeploy = true;
                  }
                } else {
                  this.IdpdataService.checkPausedBuilds = false;
                }
                if (this.IdpdataService.triggerJobData.applicationName) {
                  const applicationName = this.IdpdataService.triggerJobData
                    .applicationName;
                }
                if (
                  this.IdpdataService.triggerJobData.releaseNumber !== null &&
                  this.IdpdataService.triggerJobData.releaseNumber.length !== 0
                ) {
                  if (this.IdpdataService.checkPausedBuilds === true) {
                    if (
                      this.IdpdataService.triggerJobData.roles.indexOf(
                        "RELEASE_MANAGER"
                      ) !== -1 &&
                      (this.isBuild === true || this.isDeploy === true)
                    ) {
                      const forBuild =
                        this.isBuild && this.IdpdataService.approveBuildFlag;
                      const forDeploy =
                        this.isDeploy && this.IdpdataService.approveDeployFlag;
                      if (forBuild && forDeploy) {
                        this.router.navigate(["/previousConfig/approveBuild"]);
                      }

                      if (forBuild) {
                        if (this.IdpdataService.approveDeployFlag !== true) {
                          alert(
                            "You do no thave permission to approve build for Deploy Stage"
                          );
                        }
                        this.router.navigate(["/previousConfig/approveBuild"]);
                      }
                      if (forDeploy) {
                        if (this.IdpdataService.approveBuildFlag !== true) {
                          alert(
                            "You do no thave permission to approve build for Build Stage"
                          );
                        }
                        this.router.navigate(["/previousConfig/approveBuild"]);
                      }
                    } else {
                      alert(
                        "A build is waiting approval. Kindly contact your release manager."
                      );
                    }
                  }
                } else if (
                  this.IdpdataService.triggerJobData.roles.indexOf(
                    "RELEASE_MANAGER"
                  ) !== -1
                ) {
                  alert(
                    "No active releases for this pipeline. Please add releases."
                  );
                  this.router.navigate(["/releaseConfig/release"]);
                } else {
                  alert(
                    "No active releases for this pipeline. Please contact the release manager."
                  );
                }
              } else {
                alert("Failed  to get the Trigger Job Details");
              }
              this.initialize();
            }
          });
        }
      } catch (e) {
        alert("Failed to get trigger details");
        // //console.log(e);
      }
    });
  }

  initialize() {
    if (!this.idpdataService.isSAPApplication) {
      this.dbDeployRollbackTypeList = [
        { name: "byTag", value: "byTag" },
        { name: "byChangeSet", value: "byChangeSet" },
        { name: "byHours", value: "byHours" },
        { name: "byDate", value: "byDate" },
      ];

      this.IDPParamData = {
        applicationName: "",
        artifactorySelected: "off",
        technology: this.idpdataService.triggerJobData.technology,
        subApplicationName: "",
        ibmRQMTestSuiteId: "",
        schedule: false,
        dashBoardLink: this.idpdataService.IDPDashboardURL,
        pairNames: [],
        jobParam: [],
        jiraProjectKey: "",
        build: {
          branchSelected: "",
          module: [],
          checkmarxTag: this.idpdataService.triggerJobData.build.checkmarxTag,
          checkmarxIncremental: "",
          runCheckmarxInput: "",
        },
        deploy: {
          deployArtifact: {},
          artifactID: "",
          nexusId: "",
          version: "",
          artifacts: {},
          deployStep: [],
          dbDeployRollbackType: "",
          deployModule: [],
          subModule: [],
        },
        env: [],
        charmRequests: [],
        envSelected: "",
        approvalSelected: "",
        pipelineName: "",
        releaseNumber: "",
        jobBuildId: "",
        userTeam: "",
        slaveName: "",
        testSlaveName: "",
        branchOrTag: "",
        testPlanId: "",
        testSuitId: "",
        mtmStepName: "",
        rebase: {
          sourceEnvSelected: "",
          transportObjectType: "",
          transportObject: "",
          targetEnvSelected: "",
          bugFixTR: "",
        },
        repoDeployStatus: "",
        nonRepoDeployStatus: "",
        testSelected: "off",
        testStep: [],
        userName: this.idpdataService.idpUserName,
        gitTag: "",
        scanTag: this.idpdataService.triggerJobData.build.scanTag,
        buildartifactNumber: "",
      };
      this.IDPDataSwitch = {
        test: false,
        build: false,
        deploy: false,
        nexusURL: "",
        repoName: "",
        releaseManager: true,
        buildSelected: "off",
        deploySelected: "off",
        testSelected: "off",
        reconcileSelected: "off",
        sifImport: "off",
        srfCompile: "off",
        srfCompileType: "",
        repoDeployStatus: "",
        nonRepoDeployStatus: "",
        slaves: [],
        appSlaves: [],
        modules: [],
        subModules: [],
        artifacts: [],
        buildBranch: [],
        jobParm: [],
        buildenv: [],
        testEnv: [],
        buildDeployEnv: [],
        deployEnv: [],
        gitTagEnable: "",
        checkmarxTag: "",
        sshAndDependent: "",
        relaseList: [],
        releaseNumberList: [],
        releaseNumber: "",
      };
      try {
        if (this.idpdataService.triggerJobData) {
          if (this.idpdataService.triggerJobData.hasOwnProperty("build")) {
            this.IDPDataSwitch.build = true;
            this.IDPDataSwitch.modules = this.idpdataService.triggerJobData.build.hasOwnProperty(
              "modules"
            )
              ? this.idpdataService.triggerJobData.build.modules
              : [];
            this.IDPDataSwitch.subModules = this.idpdataService.triggerJobData.build.hasOwnProperty(
              "subModules"
            )
              ? this.idpdataService.triggerJobData.build.subModules
              : [];
            for (let i = 0; i < this.IDPDataSwitch.modules.length; i++) {
              if (this.IDPDataSwitch.modules[i].defaultModule === "on") {
                const modName = this.IDPDataSwitch.modules[i].moduleName;
                this.IDPParamData.build.module.push(modName);
              }
            }
            this.IDPDataSwitch.buildBranch = this.idpdataService.triggerJobData.build.hasOwnProperty(
              "gitBranches"
            )
              ? this.idpdataService.triggerJobData.build.gitBranches
              : [];
            this.IDPDataSwitch.gitTagEnable = this.idpdataService.triggerJobData.build.hasOwnProperty(
              "gitTag"
            )
              ? this.idpdataService.triggerJobData.build.gitTag
              : [];
            this.IDPDataSwitch.checkmarxTag = this.idpdataService.triggerJobData.build.hasOwnProperty(
              "checkmarxTag"
            )
              ? this.idpdataService.triggerJobData.build.checkmarxTag
              : "off";
            this.IDPDataSwitch.sshAndDependent = this.idpdataService.triggerJobData.hasOwnProperty(
              "sshAndDependent"
            )
              ? this.idpdataService.triggerJobData.sshAndDependent
              : [];
            this.IDPDataSwitch.relaseList = this.idpdataService.triggerJobData.hasOwnProperty(
              "relaseList"
            )
              ? this.idpdataService.triggerJobData.relaseList
              : [];
            this.IDPDataSwitch.releaseNumberList = this.idpdataService.triggerJobData.hasOwnProperty(
              "releaseNumber"
            )
              ? this.idpdataService.triggerJobData.releaseNumber
              : [];
          }
          this.branchList = this.idpdataService.triggerJobData.hasOwnProperty(
            "branchList"
          )
            ? this.idpdataService.triggerJobData.branchList
            : [];
          this.tagList = this.idpdataService.triggerJobData.hasOwnProperty(
            "tagList"
          )
            ? this.idpdataService.triggerJobData.tagList
            : [];
          this.branchTemp = this.idpService.copy(this.branchList);
          this.tagTemp = this.idpService.copy(this.tagList);

          this.scmType = this.idpdataService.triggerJobData.hasOwnProperty(
            "scmType"
          )
            ? this.idpdataService.triggerJobData.scmType
            : "";

          if (this.idpdataService.triggerJobData.hasOwnProperty("test")) {
            this.IDPDataSwitch.test = true;
            this.IDPDataSwitch.testEnv = this.idpdataService.triggerJobData.test.hasOwnProperty(
              "testEnv"
            )
              ? this.idpdataService.triggerJobData.test.testEnv
              : [];
          }
          if (
            this.idpdataService.triggerJobData.hasOwnProperty(
              "rmsComponentName"
            )
          ) {
            this.rmsComponentName = this.idpdataService.triggerJobData.rmsComponentName;
          } else {
            this.rmsComponentName = "";
          }
          if (this.idpdataService.triggerJobData.hasOwnProperty("deploy")) {
            this.IDPDataSwitch.deploy = true;
            this.IDPDataSwitch.buildDeployEnv = this.idpdataService.triggerJobData.hasOwnProperty(
              "buildDeployEnv"
            )
              ? this.idpdataService.triggerJobData.buildDeployEnv
              : [];
            this.IDPDataSwitch.deployEnv = this.idpdataService.triggerJobData.deploy.hasOwnProperty(
              "deployEnv"
            )
              ? this.idpdataService.triggerJobData.deploy.deployEnv
              : [];
            this.IDPDataSwitch.deploybizEnv = this.idpdataService.triggerJobData.deploy.hasOwnProperty(
              "bizobj"
            )
              ? this.idpdataService.triggerJobData.deploy.bizobj
              : [];
            this.IDPParamData.env = this.IDPDataSwitch.deployEnv;
            this.rmsEnv = this.idpdataService.triggerJobData.deploy.hasOwnProperty(
              "deployEnvRms"
            )
              ? this.idpdataService.triggerJobData.deploy.deployEnvRms
              : [];
          }
          if (
            this.idpdataService.triggerJobData.hasOwnProperty("deployTestEnv")
          ) {
            this.IDPDataSwitch.deploy = true;
            this.IDPDataSwitch.test = true;
            this.IDPDataSwitch.deployTestEnv = this.idpdataService.triggerJobData.deployTestEnv.hasOwnProperty(
              "Env"
            )
              ? this.idpdataService.triggerJobData.deployTestEnv.Env
              : [];
            this.IDPDataSwitch.deploytestbizEnv = this.idpdataService.triggerJobData.deployTestEnv.hasOwnProperty(
              "envObj"
            )
              ? this.idpdataService.triggerJobData.deployTestEnv.envObj
              : [];
          } else {
            this.IDPDataSwitch.deployTestEnv = [];
          }
        }
        this.IDPDataSwitch.slaves = this.idpdataService.triggerJobData.hasOwnProperty(
          "slaves"
        )
          ? this.idpdataService.triggerJobData.slaves
          : [];
        this.IDPDataSwitch.appSlaves = this.idpdataService.triggerJobData.hasOwnProperty(
          "appSlaves"
        )
          ? this.idpdataService.triggerJobData.appSlaves
          : [];
        this.IDPParamData.applicationName = this.idpdataService.triggerJobData.hasOwnProperty(
          "applicationName"
        )
          ? this.idpdataService.triggerJobData.applicationName
          : "";
        this.IDPParamData.jiraProjectKey = this.idpdataService.triggerJobData.hasOwnProperty(
          "jiraProjectKey"
        )
          ? this.idpdataService.triggerJobData.jiraProjectKey
          : "";
        this.IDPParamData.pipelineName = this.idpdataService.triggerJobData.hasOwnProperty(
          "pipelineName"
        )
          ? this.idpdataService.triggerJobData.pipelineName
          : "";
        this.IDPDataSwitch.releaseNumberList = this.idpdataService.triggerJobData.hasOwnProperty(
          "releaseNumber"
        )
          ? this.idpdataService.triggerJobData.releaseNumber
          : "";
        this.IDPParamData.jobBuildId = this.idpdataService.triggerJobData.hasOwnProperty(
          "jobBuildId"
        )
          ? this.idpdataService.triggerJobData.jobBuildId
          : "";
        this.IDPDataSwitch.nexusURL = this.idpdataService.triggerJobData.hasOwnProperty(
          "nexusURL"
        )
          ? this.idpdataService.triggerJobData.nexusURL
          : "";
        this.IDPDataSwitch.repoName = this.idpdataService.triggerJobData.hasOwnProperty(
          "repoName"
        )
          ? this.idpdataService.triggerJobData.repoName
          : "";
        this.IDPDataSwitch.jobBuildId = this.IDPParamData.jobBuildId;
        this.IDPParamData.subApplicationName = this.idpdataService.triggerJobData.hasOwnProperty(
          "subApplicationName"
        )
          ? this.idpdataService.triggerJobData.subApplicationName
          : "";
        if (this.IDPDataSwitch.repoName !== "na") {
          this.IDPParamData.artifactorySelected = "on";
        }
        if (
          this.IDPParamData.releaseNumber !== null &&
          this.IDPParamData.releaseNumber !== "" &&
          this.IDPParamData.releaseNumber !== undefined
        ) {
          this.IDPDataSwitch.releaseNumber = this.IDPParamData.releaseNumber;
        } else {
          this.IDPDataSwitch.releaseNumber = "";
        }
      } catch (e) {
        //console.log(e);
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
        classes: "myclass custom-class",
      };
      this.changeDocumentDropdownSettings = {
        singleSelection: false,
        text: "Select change document(s)",
        selectAllText: "Select All",
        unSelectAllText: "UnSelect All",
        enableSearchFilter: true,
        classes: "myclass custom-class",
      };
      this.dropdownSettings = {
        singleSelection: false,
        text: "Select Transport Requests",
        selectAllText: "Select All",
        unSelectAllText: "UnSelect All",
        enableSearchFilter: true,
        classes: "myclass custom-class",
      };
      this.IDPParamData = {
        applicationName: this.paramData.applicationName,
        pipelineName: "pipName",
        releaseNumber: "",
        ibmRQMTestSuiteId: "",
        userName: this.idpdataService.idpUserName,
        landscapesDetails: [],
        subApplicationName: "",
        userTeam: "",
        slaveName: "",
        testSlaveName: "",
        castSlaveName: "",
        reconcileSlaveName: "",
        envSelected: "",
        approvalSelected: "",
        systemName: "",
        SapsystemNames: "",
        instance: "",
        client: "",
        charmRequests: [],
        sapUserName: "",
        branchOrTag: "",
        password: "",
        technology: this.idpdataService.triggerJobData.technology,
        language: "",
        transportRequest: [],
        copyTR: false,
        userStories: [],
        userStoryMapping: [],
        jobParam: [],
        testPlanId: "",
        testSuitId: "",
        mtmStepName: "",
        rebase: {
          sourceEnvSelected: "",
          transportObjectType: "",
          transportObject: "",
          targetEnvSelected: "",
          bugFixTR: "",
        },
        resetSelected: "",
        build: {
          branchSelected: "",
          codeAnalysis: "",
          unitTest: "",
          cast: "",
          currentDate: "",
          oldVersion: "",
          newVersion: "",
          module: [],
        },
        deploy: {
          version: "",
          artifactID: "",
          nexusId: "",
          deploymentType: "",
          deployOperationSelected: "",
          deployStep: [],
          subModule: [],
        },
        testSelected: "",
        testStep: [],
      };
      this.IDPDataSwitch = {
        test: false,
        build: false,
        deploy: false,
        releaseManager: true,
        buildSelected: "off",
        deploySelected: "off",
        testSelected: "off",
        charmConfigType: "master",
        reconcileSelected: "off",
        slaves: [],
        modules: [],
        buildBranch: [],
        buildenv: [],
        testEnv: [],
        deployEnv: [],
        releaseNumberList: [],
        releaseNumber: "",
      };
      if (this.IDPParamData.technology === "sapcharm") this.getChangeRequest();
      try {
        if (this.idpdataService.triggerJobData) {
          if (this.idpdataService.triggerJobData.hasOwnProperty("build")) {
            this.IDPDataSwitch.build = true;
            if (
              this.idpdataService.triggerJobData.build.hasOwnProperty(
                "buildEnv"
              )
            ) {
              if (
                this.idpdataService.triggerJobData.build.buildEnv.length !== 0
              ) {
                this.buildEnv = this.idpdataService.triggerJobData.build.buildEnv;
              } else {
                this.buildEnv = [];
              }
            }
            if (
              this.idpdataService.triggerJobData.build.hasOwnProperty(
                "codeAnalysis"
              )
            ) {
              if (this.idpdataService.triggerJobData.build.codeAnalysis) {
                this.build.codeAnalysis = this.idpdataService.triggerJobData.build.codeAnalysis;
              }
            }
            if (
              this.idpdataService.triggerJobData.build.hasOwnProperty("cast")
            ) {
              if (this.idpdataService.triggerJobData.build.cast) {
                this.build.cast = this.idpdataService.triggerJobData.build.cast;
              }
            }
            if (
              this.idpdataService.triggerJobData.build.hasOwnProperty(
                "unitTesting"
              )
            ) {
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
            if (
              this.idpdataService.triggerJobData.test.hasOwnProperty("testEnv")
            ) {
              if (
                this.idpdataService.triggerJobData.test.testEnv.length !== 0
              ) {
                this.testEnv = this.idpdataService.triggerJobData.test.testEnv;
              } else {
                this.testEnv = [];
              }
            }
          }
          if (
            this.idpdataService.triggerJobData.hasOwnProperty("systemNames")
          ) {
            if (this.idpdataService.triggerJobData.systemNames) {
              this.SapsystemNames = this.idpdataService.triggerJobData.systemNames;
            }
          }
          if (this.idpdataService.triggerJobData.hasOwnProperty("deploy")) {
            this.IDPDataSwitch.deploy = true;
            this.IDPDataSwitch.deployEnv = this.idpdataService.triggerJobData.deploy.hasOwnProperty(
              "deployEnv"
            )
              ? this.idpdataService.triggerJobData.deploy.deployEnv
              : [];
            if (
              this.idpdataService.triggerJobData.deploy.hasOwnProperty(
                "deployEnv"
              )
            ) {
              if (
                this.idpdataService.triggerJobData.deploy.deployEnv.length !== 0
              ) {
                this.deployEnv = this.idpdataService.triggerJobData.deploy.deployEnv;
              } else {
                this.deployEnv = [];
              }
            }
          }
          this.IDPDataSwitch.slaves = this.idpdataService.triggerJobData.hasOwnProperty(
            "slaves"
          )
            ? this.idpdataService.triggerJobData.slaves
            : [];
          this.IDPParamData.applicationName = this.idpdataService.triggerJobData.hasOwnProperty(
            "applicationName"
          )
            ? this.idpdataService.triggerJobData.applicationName
            : "";
          this.IDPParamData.pipelineName = this.idpdataService.triggerJobData.hasOwnProperty(
            "pipelineName"
          )
            ? this.idpdataService.triggerJobData.pipelineName
            : "";
          this.IDPDataSwitch.releaseNumberList = this.idpdataService.triggerJobData.hasOwnProperty(
            "releaseNumber"
          )
            ? this.idpdataService.triggerJobData.releaseNumber
            : "";
          this.IDPParamData.jobBuildId = this.idpdataService.triggerJobData.hasOwnProperty(
            "jobBuildId"
          )
            ? this.idpdataService.triggerJobData.jobBuildId
            : "";
          this.IDPDataSwitch.jobBuildId = this.IDPParamData.jobBuildId;
          this.branchList = this.idpdataService.triggerJobData.hasOwnProperty(
            "branchList"
          )
            ? this.idpdataService.triggerJobData.branchList
            : [];
          this.tagList = this.idpdataService.triggerJobData.hasOwnProperty(
            "tagList"
          )
            ? this.idpdataService.triggerJobData.tagList
            : [];
          this.branchTemp = this.idpService.copy(this.branchList);
          this.tagTemp = this.idpService.copy(this.tagList);
          this.scmType = this.idpdataService.triggerJobData.hasOwnProperty(
            "scmType"
          )
            ? this.idpdataService.triggerJobData.scmType
            : "";
          if (
            this.IDPParamData.releaseNumber !== null &&
            this.IDPParamData.releaseNumber !== "" &&
            this.IDPParamData.releaseNumber !== undefined
          ) {
            this.IDPDataSwitch.releaseNumber = this.IDPParamData.releaseNumber;
          } else {
            this.IDPDataSwitch.releaseNumber = "";
          }
        }
        {
          this.idprestapiService
            .getApplicationInfo(this.IDPParamData.applicationName)
            .then((response) => {
              if (response) {
                const app = JSON.parse(response.json().resource);
                this.isEnableTrack = app.enableSapTrack;
                if (this.idpdataService.isSAPApplication) {
                  this.IDPParamData.landscapesDetails =
                    app.environmentOwnerDetails;
                  const details = this.IDPParamData.landscapesDetails;
                  for (let i = 0; i < details.length; i++) {
                    if (
                      "DEV" === details[i].landscapeType ||
                      "HOTFIX" === details[i].landscapeType
                    ) {
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
        //console.log(e);
        alert("Failed , Trigger job data is not proper");
      }
    }
  }

  toggleSelectionSiebel(module) {
    var idx = this.IDPParamData.build.module.indexOf(module);
    if (idx > -1) {
      this.IDPParamData.build.module.splice(idx, 1);
      if (module == "Full_Compile") {
        this.IDPDataSwitch.srfcompile = "off";
      }
      if (module == "SIFSelected") {
        this.IDPDataSwitch.sifImport = "off";
      }
    } else {
      this.IDPParamData.build.module.push(module);
      if (module == "Full_Compile") {
        this.IDPDataSwitch.srfcompile = "on";
      }
      if (module == "SIFSelected") {
        this.IDPDataSwitch.sifImport = "on";
      }
    }

    // console.log(this.IDPParamData.build.module);
  }

  closeBuild() {
    this.IDPParamData.build.module = [];
    this.IDPParamData.build.checkmarxIncremental = "off";
    this.IDPParamData.build.runCheckmarxInput = "";
    this.IDPDataSwitch.buildSelected = "off";
    this.IDPParamData.slaveName = "";
    this.slavestatus = "";
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
    if (this.IDPDataSwitch.buildSelected === "off") {
      this.IDPParamData.slaveName = "";
      this.slavestatus = "";
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
    if (
      this.IDPDataSwitch.deploySelected !== "on" &&
      this.IDPDataSwitch.testSelected !== "on"
    ) {
      this.IDPParamData.envSelected = "";
    }
  }
  //siebel
  repoDeployTrue() {
    this.IDPParamData.repoDeployStatus = "true";
    return "on";
  }

  repoDeployFalse() {
    this.IDPParamData.repoDeployStatus = "false";
    return "off";
  }

  nonRepoDeployTrue() {
    this.IDPParamData.nonRepoDeployStatus = "true";
    this.IDPParamData.technology = "siebel";
    return "on";
  }

  nonRepoDeployFalse() {
    this.IDPParamData.nonRepoDeployStatus = "false";
    this.IDPParamData.technology = "siebel";
    return "off";
  }
  deployOptionAlert() {
    alert("Please select atleast one deploy option");
  }
  siebelBuildOption() {
    this.IDPParamData.build.module[0] = [];
    if (this.IDPDataSwitch.sifImport == "on") {
      this.IDPParamData.build.module[0] += ";SIFSelected;";
      if (this.IDPDataSwitch.srfCompile == "on")
        this.IDPParamData.build.module[0] += "SRFSelected;";
      if (this.IDPDataSwitch.srfCompileType == "fullCompile")
        this.IDPParamData.build.module[0] += "Full_Compile;";
      if (this.IDPDataSwitch.srfCompileType == "incCompile")
        this.IDPParamData.build.module[0] += "Incremental_Compile;";
    } else {
      if (this.IDPDataSwitch.srfCompile == "on")
        this.IDPParamData.build.module[0] += ";SRFSelected;";
      if (this.IDPDataSwitch.srfCompileType == "fullCompile")
        this.IDPParamData.build.module[0] += "Full_Compile;";
      if (this.IDPDataSwitch.srfCompileType == "incCompile")
        this.IDPParamData.build.module[0] += "Incremental_Compile;";
    }
    this.IDPParamData.technology = "siebel";
    return true;
  }

  triggerAlert() {
    this.alertModalRef = this.modalService.show(this.modalforAlert);
  }

  triggerAlertDBDep() {
    this.dbDepModalRef = this.modalService.show(this.modalforAlertDBDep);
  }

  triggerAlertArtifact() {
    this.selectArtifactModalRef = this.modalService.show(
      this.modalforArtifactAlert
    );
  }

  triggerSubmitArtifactAlert() {
    this.submitModalRef = this.modalService.show(this.modalforSubmitAlert);
  }

  getLandscapeType() {
    if (this.IDPDataSwitch.deploySelected === "on") {
      this.hideTransportRequest = false;
      this.selectedItems = [];
      this.selectedTR = [];
      const details = this.IDPParamData.landscapesDetails;
      this.devTrue = false;
      this.IDPParamData.deploy.deployOperationSelected = "";
      let landscapeType = "";
      for (let i = 0; i < details.length; i++) {
        if (this.IDPParamData.envSelected === details[i].environmentName) {
          landscapeType = details[i].landscapeType;
          break;
        }
      }
      if (landscapeType === "DEV") {
        this.devTrue = true;
      } else {
        this.getTransportRequest();
      }
    } else {
      this.getTransportRequest();
    }
  }
  // Fetch transport request for SAP
  getTransportRequest() {
    this.transportRequests = [];
    this.selectedItems = [];
    this.selectedTR = [];
    this.showData = false;
    this.IDPParamData.copyTR = false;
    let data;
    if (this.buildSelect === "on" && this.deploySelect === "on") {
      // BD, BDT fetch tr from dev even when QA or prod selected
      data = {
        application_name: this.IDPParamData.applicationName,
        landscapeName: this.buildEnv,
        deployOperation: this.IDPParamData.deploy.deployOperationSelected,
      };
    } else {
      data = {
        application_name: this.IDPParamData.applicationName,
        landscapeName: this.IDPParamData.envSelected,
        deployOperation: this.IDPParamData.deploy.deployOperationSelected,
      };
    }

    //console.log(data);

    this.idpdataService.loading = true;
    this.hideTransportRequests = false;
    try {
      if (data.application_name !== "" && data.landscapeName !== "") {
        this.idpdataService.loading = true;
        this.idprestapiService.getTransportRequest(data).then((response) => {
          this.idpdataService.loading = false;
          if (response) {
            if (
              response.json().resource !== null &&
              response.json().resource !== "[]" &&
              response.json().resource !== "{}"
            ) {
              const tData = JSON.parse(response.json().resource);
              const transportData = tData.transportRequests;
              this.showData = tData.show;
              this.unselectedTRs = transportData;
              this.selectedTR = [];
              if (transportData.length !== 0) {
                this.IDPParamData.transportRequest = [];
                this.transportRequests = [];

                let c = 1;
                let transportList = null;
                if (
                  this.IDPDataSwitch.deploySelected === "on" &&
                  this.IDPParamData.deploy.deployOperationSelected !==
                    "release" &&
                  !this.idpdataService.checkpollALM
                ) {
                  transportList = null;

                  // Filtering transport based on release number
                  if (
                    this.IDPDataSwitch.releaseNumber !== "" &&
                    this.idpdataService.triggerJobData.releaseTransportInfo
                      .length !== 0
                  ) {
                    for (const relTransportInfo of this.idpdataService
                      .triggerJobData.releaseTransportInfo) {
                      if (
                        relTransportInfo.releaseNumber ===
                        this.IDPDataSwitch.releaseNumber
                      ) {
                        transportList = relTransportInfo.transportList;
                        break;
                      }
                    }
                  }

                  if (transportList != null && transportList.length > 0) {
                    for (const i of transportData) {
                      if (transportList.includes(i.transportReqName)) {
                        this.transportRequests.push({
                          id: c,
                          itemName: i.transportReqName,
                        });
                        c = c + 1;
                      }
                    }
                  }
                } else {
                  for (const i of transportData) {
                    this.transportRequests.push({
                      id: c,
                      itemName: i.transportReqName,
                    });
                    c = c + 1;
                  }
                }

                if (this.idpdataService.checkpollALM) {
                  this.tempTrArrayDeploy = [];
                  const tempTrList = [];

                  for (const i of this.transportRequests) {
                    tempTrList.push(i.itemName);
                  }

                  this.transportRequests = [];
                  this.tempTrArrayDeploy = Array.from(new Set(tempTrList));
                  let c = 1;
                  if (
                    this.tempTrArrayDeploy !== null &&
                    this.tempTrArrayDeploy.length > 0
                  ) {
                    for (let i = 0; i < this.tempTrArrayDeploy.length; i++) {
                      if (
                        this.tempTrArrayJira.includes(this.tempTrArrayDeploy[i])
                      ) {
                        this.transportRequests.push({
                          id: c,
                          itemName: this.tempTrArrayDeploy[i],
                        });
                        c = c + 1;
                      }
                    }
                  }
                }
              } else {
                alert(
                  "No Transport Request available for SAP System : " +
                    data.landscapeName
                );
              }
            } else {
              alert(
                "Failed to get Transport Request for  SAP System : " +
                  data.landscapeName
              );
            }
          } else {
            alert(
              "Failed to get Transport Request for  SAP System : " +
                data.landscapeName
            );
          }
          this.idpdataService.loading = false;
        });
      } else {
        this.hideTransportRequest = false;
      }
    } catch (e) {
      alert("Failed in getting Transport Request");
    }
  }

  clearLandscapeAndUserStory() {
    if (
      this.IDPDataSwitch.buildSelected === "on" &&
      this.IDPParamData.build.cast !== "on" &&
      this.IDPParamData.build.codeAnalysis !== "on" &&
      this.IDPParamData.build.unitTest !== "on"
    ) {
      this.IDPParamData.envSelected = "";
      this.selectedUS = [];
      this.hideTransportRequest = true;
      this.selectedItems = [];
    }
  }

  // Checks conditions to show Landscape dropdown
  checkCheckBoxesOn() {
    if (
      this.IDPDataSwitch.buildSelected === "on" &&
      (this.IDPParamData.build.cast === "on" ||
        this.IDPParamData.build.codeAnalysis === "on" ||
        this.IDPParamData.build.unitTest === "on")
    ) {
      return true;
    } else if (
      this.IDPDataSwitch.buildSelected !== "on" &&
      (this.IDPDataSwitch.deploySelected === "on" ||
        this.IDPDataSwitch.testSelected === "on")
    ) {
      return true;
    } else {
      return false;
    }
  }

  setReconcileSelected(selected) {
    if (selected) {
      this.IDPDataSwitch.reconcileSelected = "on";
      this.reconcileSelect = "on";
      this.setbuildLandScapeNames(false);
      this.setdeployLandScapeNames(false);
      this.settestLandScapeNames(false);
    } else {
      this.IDPDataSwitch.reconcileSelected = "off";
      this.reconcileSelect = "off";
      this.IDPParamData.reconcileSlaveName = "";
      this.reconcilationChange();
    }
  }

  setbuildLandScapeNames(selected) {
    if (selected) {
      this.IDPDataSwitch.buildSelected = "on";
      this.buildSelect = "on";
      this.setReconcileSelected(false);
    } else {
      this.IDPDataSwitch.buildSelected = "off";
      this.buildSelect = "off";
      this.IDPParamData.slaveName = "";
      this.IDPParamData.build.codeAnalysis = "off";
      this.IDPParamData.build.cast = "off";
      this.IDPParamData.build.unitTest = "off";
      if (this.idpdataService.checkpollALM) {
        this.selectedUS = [];
        this.hideTransportRequest = false;
      }
    }
    this.setLandscapeNames();
  }

  setCharmResetLandScapeNames(selected) {
    if (selected) {
      this.IDPDataSwitch.resetSelected = "on";
      this.resetSelect = "on";
    } else {
      this.IDPDataSwitch.resetSelected = "off";
      this.resetSelect = "off";
      this.IDPParamData.slaveName = "";
      this.IDPParamData.build.codeAnalysis = "off";
      this.IDPParamData.build.unitTest = "off";
    }
    this.setCharmLandscapeNames();
  }
  setCharmbuildLandScapeNames(selected) {
    if (selected) {
      this.IDPDataSwitch.buildSelected = "on";
      this.buildSelect = "on";
    } else {
      this.IDPDataSwitch.buildSelected = "off";
      this.buildSelect = "off";
      this.IDPParamData.slaveName = "";
      this.IDPParamData.build.codeAnalysis = "off";
      this.IDPParamData.build.unitTest = "off";
    }
    this.setCharmLandscapeNames();
  }

  setCharmDeployLandScapeNames(selected) {
    if (selected) {
      this.IDPDataSwitch.deploySelected = "on";
      this.deploySelect = "on";
    } else {
      this.IDPDataSwitch.deploySelected = "off";
      this.deploySelect = "off";
      this.IDPParamData.slaveName = "";
      this.IDPParamData.deploy.deployOperationSelected = "";
    }
    this.IDPParamData.build.codeAnalysis = "off";
    this.IDPParamData.build.unitTest = "off";
    if (this.idpdataService.checkpollALM) {
      this.selectedUS = [];
    }
    this.setCharmLandscapeNames();
  }

  setCharmTestLandScapeNames(selected) {
    if (selected) {
      this.IDPDataSwitch.testSelected = "on";
      this.testSelect = "on";
    } else {
      this.IDPDataSwitch.testSelected = "off";
      this.testSelect = "off";
      this.closeTest();
    }
    this.setCharmLandscapeNames();
  }

  setdeployLandScapeNames(selected) {
    if (selected) {
      this.IDPDataSwitch.deploySelected = "on";
      this.deploySelect = "on";
      this.setReconcileSelected(false);
    } else {
      this.IDPDataSwitch.deploySelected = "off";
      this.deploySelect = "off";
      this.IDPParamData.slaveName = "";
      this.IDPParamData.deploy.deployOperationSelected = "";
    }
    if (this.idpdataService.checkpollALM) {
      this.selectedUS = [];
    }
    this.setLandscapeNames();
  }

  settestLandScapeNames(selected) {
    if (selected) {
      this.IDPDataSwitch.testSelected = "on";
      this.testSelect = "on";
      this.setReconcileSelected(false);
    } else {
      this.IDPDataSwitch.testSelected = "off";
      this.testSelect = "off";
      this.closeTest();
    }
    this.setLandscapeNames();
  }

  getChangeRequest() {
    try {
      this.idpdataService.loading = true;
      this.idprestapiService
        .getChangeRequestDetails(
          this.IDPParamData.applicationName,
          this.IDPDataSwitch.charmConfigType
        )
        .then((response) => {
          if (response) {
            if (
              response.json().resource !== null &&
              response.json().resource !== "[]" &&
              response.json().resource !== "{}"
            ) {
              const tData = JSON.parse(response.json().resource);
              this.changeRequestDetails = tData;
              if (this.IDPDataSwitch.charmConfigType === "master") {
                this.approvalStages = [
                  "Successfully Tested",
                  "Authorized for Production",
                  "Imported into Production",
                  "Confirmed",
                  "Completed",
                ];
              } else if (this.IDPDataSwitch.charmConfigType === "workflow") {
                this.approvalStages = [
                  "Successfully Tested",
                  "Ready for Production Import",
                  "PFA Approved",
                  "DM Approved",
                  "Imported into Production",
                  "Confirmed",
                  "Completed",
                ];
              }
            } else {
              alert("Failed to get Change Request for  SAP System : ");
            }
          } else {
            alert("Failed to get Change Request for  SAP System : ");
          }
          this.idpdataService.loading = false;
        });
    } catch (e) {
      alert("Failed in getting Change Request");
    }
  }

  fetchChangeRequest() {
    this.changeRequestListTemp = [];
    this.changeRequestList = [];
    this.showPermissionError = "";

    this.hideChangeRequest = false;

    if (this.IDPDataSwitch.buildSelected === "on") {
      if (
        this.IDPParamData.build.codeAnalysis === "on" &&
        this.IDPParamData.build.unitTest === "on"
      ) {
        this.changeRequestList = this.changeRequestDetails.buildCr;
      } else if (this.IDPParamData.build.codeAnalysis === "on") {
        this.changeRequestList = this.changeRequestDetails.buildCaCr;
      } else if (this.IDPParamData.build.unitTest === "on") {
        this.changeRequestList = this.changeRequestDetails.buildUtCr;
      }
    }

    if (this.IDPDataSwitch.deploySelected === "on") {
      let envCrList = this.changeRequestDetails.deployEnvCrList;

      for (let i = 0; i < envCrList.length; i++) {
        if (this.IDPParamData.envSelected === envCrList[i].environmentName) {
          let localChangeRequestList = envCrList[i].changeRequestList;
          for (let i = 0; i < localChangeRequestList.length; i++) {
            if (!this.changeRequestList.includes(localChangeRequestList[i])) {
              this.changeRequestList.push(localChangeRequestList[i]);
            }
          }
          break;
        }
      }
    }

    if (this.IDPDataSwitch.testSelected === "on") {
      let crStatusList = this.changeRequestDetails.crsBasedOnStatusOfCD.filter(
        (x) => x.statusOfCD === "Unit Testing Completed"
      );

      for (let i = 0; i < crStatusList.length; i++) {
        let localChangeRequestList = crStatusList[i].crAndDescriptionList;
        for (let i = 0; i < localChangeRequestList.length; i++) {
          if (!this.changeRequestList.includes(localChangeRequestList[i])) {
            this.changeRequestList.push(localChangeRequestList[i]);
          }
        }
        break;
      }
    }

    if (this.isEnableTrack === "on") {
      this.changeRequestListTemp = [];
      let changeRequestArr = [];
      let approvalStageArr = [];

      if (
        this.charmTriggerData.sapTrackSelected !== undefined &&
        this.charmTriggerData.sapTrackSelected !== null &&
        this.charmTriggerData.sapSubTrackSelected !== undefined &&
        this.charmTriggerData.sapSubTrackSelected != null
      ) {
        let trackCrList = this.changeRequestDetails.crsBasedOnTrackSubtrack;
        if (this.IDPParamData.approvalSelected) {
          for (
            let k = 0;
            k < this.changeRequestDetails.crsBasedOnStatusOfCD.length;
            k++
          ) {
            let currentStatus = this.changeRequestDetails.crsBasedOnStatusOfCD[
              k
            ];
            if (
              currentStatus.statusOfCD === this.IDPParamData.approvalSelected
            ) {
              approvalStageArr.push(currentStatus.crAndDescriptionList);
            }
          }
        }
        for (let i = 0; i < trackCrList.length; i++) {
          if (
            this.charmTriggerData.sapSubTrackSelected ===
            trackCrList[i].subTrack
          ) {
            let localChangeRequestList = trackCrList[i].crAndDescriptionList;
            for (let i = 0; i < localChangeRequestList.length; i++) {
              if (!this.changeRequestList.includes(localChangeRequestList[i])) {
                changeRequestArr.push(localChangeRequestList[i]);
              }
            }
            break;
          }
        }
        for (let j = 0; j < changeRequestArr.length; j++) {
          if (this.IDPParamData.approvalSelected) {
            if (approvalStageArr.length > 0) {
              let cr = approvalStageArr[0].filter(
                (x) => changeRequestArr[j].crNumber === x.crNumber
              );
              if (cr !== null && cr.length > 0) {
                this.showPermissionError = "";
                this.changeRequestListTemp.push({
                  val: changeRequestArr[j].crNumber,
                  description:
                    changeRequestArr[j].crNumber +
                    " --> " +
                    changeRequestArr[j].crDescription,
                  role: changeRequestArr[j].crApprover,
                });
              } else if (cr == null || cr.length == 0) {
                if (this.changeRequestListTemp.length === 0) {
                  this.showPermissionError =
                    "No data available for this request.";
                }
              }
            } else {
              this.showPermissionError = "No data available for this request.";
            }
          } else {
            this.changeRequestListTemp.push({
              val: changeRequestArr[j].crNumber,
              description:
                changeRequestArr[j].crNumber +
                " --> " +
                changeRequestArr[j].crDescription,
              role: changeRequestArr[j].crApprover,
            });
          }
        }
      }
    } else {
      this.changeRequestListTemp = [];
      for (let k = 0; k < this.changeRequestList.length; k++) {
        let cr = this.changeRequestDetails.crAndDescription.filter(
          (x) => this.changeRequestList[k] === x.crNumber
        );
        if (cr == null || cr.length == 0) {
          this.changeRequestListTemp.push({
            val: this.changeRequestList[k],
            description: this.changeRequestList[k],
          });
        } else {
          if (cr[0].crDescription) {
            this.changeRequestListTemp.push({
              val: this.changeRequestList[k],
              description:
                this.changeRequestList[k] + " --> " + cr[0].crDescription,
            });
          } else {
            this.changeRequestListTemp.push({
              val: this.changeRequestList[k],
              description: this.changeRequestList[k],
            });
          }
        }
      }
    }
  }

  updateCRRole() {
    let crRole, currentCR;
    for (
      let k = 0;
      k < this.changeRequestDetails.crsBasedOnTrackSubtrack.length;
      k++
    ) {
      currentCR = this.changeRequestDetails.crsBasedOnTrackSubtrack[k];
      let cr = currentCR.crAndDescriptionList.filter(
        (x) => this.charmTriggerData.changeRequest === x.crNumber
      );
      if (cr !== null && cr.length !== 0) {
        crRole = cr[0].crApprover;
      }
    }
    this.crApprover = crRole;
  }

  checkIfTrackEnabled() {
    if (this.isEnableTrack === "on") {
      let currentLandscape = this.IDPParamData.landscapesDetails.filter(
        (x) => this.IDPParamData.envSelected === x.environmentName
      );
      this.selectedLandscapeType = currentLandscape[0].landscapeType;
      this.IDPParamData.approvalSelected = "";
      this.clearCrData();
      this.fetchSteps();
    } else {
      this.clearCrData();
      this.fetchChangeRequest();
      this.fetchSteps();
    }
  }

  trackEnabledForUT() {
    this.isEnableTrack === "on" ? this.clearCrData() : this.clearCrData();
    this.fetchChangeRequest();
  }

  filterTrack(itemList) {
    let mySet: Set<string> = new Set<string>();
    let result = [];
    if (itemList !== undefined) {
      for (let i = 0; i < itemList.length; i++) {
        if (!mySet.has(itemList[i].track)) {
          result.push(itemList[i]);
        }
        mySet.add(itemList[i].track);
      }
    }
    return result;
  }

  fetchChangeDocument() {
    this.changeDocumentListDescDropdown = [];
    this.hideChangeDocument = false;
    let changeRequest: String = this.charmTriggerData.changeRequest;
    this.changeDocumentList = [];
    this.selectedCDData = [];
    this.selectedCD = [];
    this.transportRequests = [];
    this.cdDetailsList = [];
    this.changeDocumentListTemp = [];
    this.cdListWithStatus = [];
    //Prepare CD list with description

    for (let cd of this.changeRequestDetails.changeDocumentMapping) {
      this.cdListWithDescription.push({
        cdNum: cd.changeDocument,
        cdDescription: cd.changeDocumentData[0].cdDescription,
      });
    }
    for (let cd of this.changeRequestDetails.changeDocumentMapping) {
      this.cdListWithStatus.push({
        cdNum: cd.changeDocument,
        cdStatus: cd.changeDocumentData[0].cdStatus,
        cdDescription: cd.changeDocumentData[0].cdDescription,
      });
    }
    for (
      let i = 0;
      i < this.changeRequestDetails.crcdRelationList.length;
      i++
    ) {
      let cdmap = this.changeRequestDetails.crcdRelationList[i];
      if (this.IDPDataSwitch.testSelected === "on") {
        for (let k = 0; k < this.cdListWithStatus.length; k++) {
          if (
            changeRequest === cdmap.changeRequest &&
            cdmap.changeDocumentList.includes(this.cdListWithStatus[k].cdNum) &&
            this.cdListWithStatus[k].cdStatus === "Ready for QA"
          ) {
            this.changeDocumentListDescDropdown.push({
              id: this.cdListWithStatus[k].cdNum,
              itemName:
                this.cdListWithStatus[k].cdNum +
                " --> " +
                this.cdListWithStatus[k].cdDescription,
            });
          }
        }
      }

      if (changeRequest === cdmap.changeRequest) {
        for (let j = 0; j < cdmap.changeDocumentList.length; j++) {
          //Don't add all the CD's; first filter based on status

          if (this.IDPDataSwitch.deploySelected === "on") {
            for (let cdList of this.changeRequestDetails.deployEnvCdList) {
              if (cdList.environmentName === this.IDPParamData.envSelected) {
                for (let cd of cdList.changeDocumentList) {
                  if (cdmap.changeDocumentList.includes(cd)) {
                    if (!this.changeDocumentListTemp.includes(cd)) {
                      this.changeDocumentListTemp.push(cd);
                    }
                  }
                }
              }
            }
          }

          if (this.IDPDataSwitch.buildSelected === "on") {
            //B
            if (
              this.IDPParamData.build.unitTest === "on" &&
              this.IDPParamData.build.codeAnalysis === "on"
            ) {
              //UT & CA
              let status = this.idpdataService.buildCAUT.split(",");
              let currentCDstatus = this.cdListWithStatus.filter((obj) => {
                //console.log(obj.cdNum);
                //console.log(cdmap.changeDocumentList[j]);
                return obj.cdNum === cdmap.changeDocumentList[j];
              });
              // console.log(currentCDstatus);
              // console.log(status.includes(currentCDstatus[0].cdStatus));
              if (status.includes(currentCDstatus[0].cdStatus)) {
                if (
                  !this.changeDocumentListTemp.includes(
                    cdmap.changeDocumentList[j]
                  )
                )
                  this.changeDocumentListTemp.push(cdmap.changeDocumentList[j]);
                //this.changeDocumentList.push({"id": j, "itemName": cdmap.changeDocumentList[j]});
              }
              //console.log(status);
            } else if (this.IDPParamData.build.unitTest === "on") {
              //only UT
              let status = this.idpdataService.buildUT.split(",");
              let currentCDstatus = this.cdListWithStatus.filter((obj) => {
                return obj.cdNum === cdmap.changeDocumentList[j];
              });
              if (status.includes(currentCDstatus[0].cdStatus)) {
                if (
                  !this.changeDocumentListTemp.includes(
                    cdmap.changeDocumentList[j]
                  )
                )
                  this.changeDocumentListTemp.push(cdmap.changeDocumentList[j]);
              }
              //console.log(status);
            } else if (this.IDPParamData.build.codeAnalysis === "on") {
              //only CA
              let status = this.idpdataService.buildCA.split(",");
              let currentCDstatus = this.cdListWithStatus.filter((obj) => {
                return obj.cdNum === cdmap.changeDocumentList[j];
              });
              if (status.includes(currentCDstatus[0].cdStatus)) {
                if (
                  !this.changeDocumentListTemp.includes(
                    cdmap.changeDocumentList[j]
                  )
                )
                  this.changeDocumentListTemp.push(cdmap.changeDocumentList[j]);
              }
              //console.log(status);
            }
          }

          //this.changeDocumentList.push({"id": j, "itemName": cdmap.changeDocumentList[j]});
          if (this.IDPDataSwitch.testSelected !== "on") {
            for (let k = 0; k < this.cdListWithDescription.length; k++) {
              if (
                cdmap.changeDocumentList[j] ===
                this.cdListWithDescription[k].cdNum
              ) {
                this.changeDocumentListDescDropdown.push({
                  id: this.cdListWithDescription[k].cdNum,
                  itemName:
                    this.cdListWithDescription[k].cdNum +
                    " --> " +
                    this.cdListWithDescription[k].cdDescription,
                });
              }
            }
          }
          // break;
        }
      }
    }

    for (let k = 0; k < this.changeDocumentListTemp.length; k++) {
      this.changeDocumentList.push({
        id: k,
        itemName: this.changeDocumentListTemp[k],
      });
    }
  }

  setLandscapeNames() {
    this.idpdataService.SAPEnvList = [];
    this.IDPParamData.envSelected = "";
    this.devTrue = false;
    this.showData = false;
    this.transportRequest = "";
    this.hideTransportRequests = false;
    this.hideTransportRequest = false;
    if (this.IDPParamData.envSelected === "") {
      this.hideTransportRequest = false;
    }
    /* if (this.reconcileSelect === "on") {
      this.idpdataService.SAPEnvList = this.allEnv;
    } else if (this.deploySelect === "on" && this.buildSelect === "on") {// B || B+D
      this.idpdataService.SAPEnvList = this.deployEnv;
	  
	  
    } else if (this.deploySelect === "on" && this.buildSelect !== "on" && this.testSelect !== "on") {// D
      this.idpdataService.SAPEnvList = this.allEnv;
	  
    } else if (this.testSelect === "on" || (this.deploySelect === "on" && this.testSelect === "on")) {// T || D+T
      this.idpdataService.SAPEnvList = this.testEnv;
	  
    } else if (this.buildSelect === "on" && this.testSelect === "on") {// B +T
      //this.idpdataService.SAPEnvList = this.buildTestEnv;
	  this.idpdataService.SAPEnvList = this.buildEnv;
	  
    } else if (this.buildSelect === "on" && this.testSelect === "on" && this.deploySelect === "on") { // B +T+D
      this.idpdataService.SAPEnvList = this.buildTestEnv;
	  
    }  else {
      this.hideTransportRequests = false;
      this.hideTransportRequest = false;
    } */
    this.hideLandscape = false;
    if (this.reconcileSelect === "on") {
      this.idpdataService.SAPEnvList = this.allEnv;
    } else if (this.testSelect === "on") {
      // BDT || DT || T || BT

      this.idpdataService.SAPEnvList = this.testEnv;

      if (this.deploySelect !== "on" && this.buildSelect === "on") {
        //BT
        this.hideLandscape = true;
        // console.log(this.buildEnv);
        // if(!this.idpdataService.checkpollALM){
        this.hideTransportRequest = true;
        this.IDPParamData.envSelected = this.buildEnv;

        //}
        // this.getTransportRequest();
      }
    } else if (this.deploySelect === "on") {
      // D, BD
      this.idpdataService.SAPEnvList = this.deployEnv;
    } else if (this.buildSelect === "on") {
      // B
      //alert("in build")
      // console.log(this.buildEnv);

      this.IDPParamData.envSelected = this.buildEnv;
      this.hideLandscape = true;
      //this.getTransportRequest();
      // if (this.idpdataService.checkpollALM) {
      // }
    }
  }

  showTransportReq() {
    this.hideTransportRequest = false;
  }

  setCharmLandscapeNames() {
    this.idpdataService.SAPEnvList = [];
    this.IDPParamData.envSelected = "";
    this.devTrue = false;
    this.showData = false;
    this.transportRequest = "";

    if (this.IDPParamData.envSelected === "") {
      this.hideTransportRequest = false;
    }
    if (
      this.buildSelect === "on" ||
      (this.deploySelect === "on" && this.buildSelect === "on")
    ) {
      // B || B+D
      this.idpdataService.SAPEnvList = this.buildEnv;
    } else if (
      this.deploySelect === "on" &&
      this.buildSelect !== "on" &&
      this.testSelect !== "on"
    ) {
      // D
      this.idpdataService.SAPEnvList = this.allEnv;
      this.hideTransportRequest = false;
    } else if (
      this.testSelect === "on" ||
      (this.deploySelect === "on" && this.testSelect === "on")
    ) {
      // T || D+T
      this.idpdataService.SAPEnvList = this.testEnv;
      this.hideTransportRequest = false;
      if (this.deploySelect !== "on") {
        this.hideTransportRequests = true;
      } else {
        this.hideTransportRequests = false;
      }
    } else if (this.buildSelect === "on" && this.testSelect === "on") {
      // B +T
      this.idpdataService.SAPEnvList = this.buildTestEnv;
    } else if (
      this.buildSelect === "on" &&
      this.testSelect === "on" &&
      this.deploySelect === "on"
    ) {
      // B +T+D
      this.idpdataService.SAPEnvList = this.buildTestEnv;
    } else if (this.resetSelect === "on") {
      //R
      this.idpdataService.SAPEnvList = this.buildEnv;
      this.hideTransportRequest = false;
    } else if (
      this.testSelect === "on" &&
      this.buildSelect === "off" &&
      this.deploySelect === "off"
    ) {
      this.hideTransportRequests = true;
    } else {
      this.hideTransportRequests = false;
      this.hideTransportRequest = false;
    }
  }

  reconcilationChange() {
    this.IDPParamData.rebase.sourceEnvSelected = "";
    this.IDPParamData.rebase.transportObjectType = "";
    this.IDPParamData.rebase.transportObject = "";
    this.IDPParamData.rebase.targetEnvSelected = "";
    this.IDPParamData.rebase.bugFixTR = "";
  }

  getObjects() {
    this.IDPParamData.rebase.transportObjectType = "";
    this.IDPParamData.rebase.transportObject = "";
    this.IDPParamData.rebase.targetEnvSelected = "";
    this.IDPParamData.rebase.bugFixTR = "";
    this.idpdataService.loading = true;
    this.idprestapiService
      .getTRObjects(
        this.IDPParamData.applicationName,
        this.IDPParamData.rebase.sourceEnvSelected
      )
      .then((response) => {
        if (response) {
          this.idpdataService.loading = false;
          if (
            response.json().resource !== null &&
            response.json().resource !== "[]" &&
            response.json().resource !== "{}"
          ) {
            this.objectData = JSON.parse(response.json().resource);
            if (this.objectData.length !== 0) {
              const tempObjList = [];
              for (let i = 0; i < this.objectData.length; i++) {
                tempObjList.push(this.objectData[i].objectType);
              }
              this.objectTypeList = Array.from(new Set(tempObjList));
            }
          }
        }
      });
  }
  getObjectNames() {
    this.IDPParamData.rebase.transportObject = "";
    this.IDPParamData.rebase.targetEnvSelected = "";
    this.IDPParamData.rebase.bugFixTR = "";
    const tempObjectName = [];
    for (let i = 0; i < this.objectData.length; i++) {
      if (
        this.objectData[i].objectType ===
        this.IDPParamData.rebase.transportObjectType
      ) {
        tempObjectName.push(this.objectData[i].objectName);
      }
    }
    this.objectNameList = Array.from(new Set(tempObjectName));
  }

  getTRforObject() {
    this.IDPParamData.rebase.targetEnvSelected = "";
    this.IDPParamData.rebase.bugFixTR = "";

    for (let i = 0; i < this.objectData.length; i++) {
      if (
        this.objectData[i].objectType ===
          this.IDPParamData.rebase.transportObjectType &&
        this.objectData[i].objectName ===
          this.IDPParamData.rebase.transportObject
      ) {
        this.IDPParamData.rebase.bugFixTR = this.objectData[i].transportRequest;
      }
    }
  }

  intersectionValues() {
    const finalData = this.buildEnv.filter((x) => this.testEnv.indexOf(x) > -1);
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

  getTransportRequestForUserStories(operation, userStory) {
    this.idpdataService.loading = true;
    const temp1 = [];
    let temp2 = "";
    const temp = this.userStoryArray;
    let count;
    if (operation === "onDeselectAll") {
      this.selectedItems = [];
      this.transportRequests = [];
    } else if (operation === "onSelectAll") {
      this.selectedItems = [];
      this.transportRequests = [];
      for (let j = 0; j < temp.length; j++) {
        for (let i = 0; i < temp[j].Transport.length; i++) {
          temp1.push(temp[j].Transport[i]);
        }
      }
    } else if (operation === "onSelect") {
      this.selectedItems = [];
      for (let j = 0; j < temp.length; j++) {
        if (userStory === temp[j].Userstory) {
          for (let i = 0; i < temp[j].Transport.length; i++) {
            temp1.push(temp[j].Transport[i]);
          }
        }
      }
    } else if (operation === "onDeselect") {
      this.selectedItems = [];
      let index;
      for (let j = 0; j < temp.length; j++) {
        if (userStory === temp[j].Userstory) {
          temp2 = temp[j].Transport[0];
          count = temp[j].Transport.length;
          break;
        }
      }
      for (let i = 0; i < this.transportRequests.length; i++) {
        if (temp2 === this.transportRequests[i].itemName) {
          index = i;
          break;
        }
      }
      this.transportRequests.splice(index, count);
    }

    this.hideTransportRequest = false;
    if (this.deploySelect === "on") {
      this.IDPParamData.envSelected = "";
      this.devTrue = false;
      this.showData = false;

      this.IDPParamData.deploy.deployOperationSelected = "";
    }
    this.transportRequests = [];
    this.selectedTR = [];
    if (temp1.length > 0) {
      for (const element of temp1) {
        this.tempTrArrayJira.push(element);
      }
    } else {
      if (temp2.length > 0) {
        let index;
        for (let i = 0; i < this.tempTrArrayJira.length; i++) {
          if (temp2 === this.tempTrArrayJira[i].itemName) {
            index = i;
            break;
          }
        }
        this.tempTrArrayJira.splice(index, count);
      } else {
        this.tempTrArrayJira = [];
      }
    }

    this.idpdataService.loading = true;

    if (this.buildSelect === "on" && this.deploySelect !== "on") {
      this.getTransportRequest();
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
    this.showPermissionError = "";
    if (
      this.requiredSCM === true &&
      (this.IDPParamData.branchOrTagValue === undefined ||
        this.IDPParamData.branchOrTagValue === "")
    ) {
      this.missingSCMBranchModalRef = this.modalService.show(
        this.modalforReqSCMAlert
      );
    } else if (
      (this.IDPDataSwitch.buildSelected === "off" ||
        this.IDPDataSwitch.buildSelected === null ||
        this.IDPDataSwitch.buildSelected === undefined) &&
      (this.IDPDataSwitch.testSelected === "off" ||
        this.IDPDataSwitch.testSelected === null ||
        this.IDPDataSwitch.testSelected === undefined) &&
      (this.IDPDataSwitch.deploySelected === "off" ||
        this.IDPDataSwitch.deploySelected === null ||
        this.IDPDataSwitch.deploySelected === undefined) &&
      (this.IDPDataSwitch.resetSelected === "off" ||
        this.IDPDataSwitch.resetSelected === null ||
        this.IDPDataSwitch.resetSelected === undefined)
    ) {
      this.idpdataService.loading = false;
      this.triggerAlert();
    } else {
      if (this.IDPParamData.technology === "sapcharm") {
        this.checkPermissions();
        this.validateApprovalStage();
      }
      if (!this.showPermissionError) {
        if (this.IDPParamData.build.runCheckmarxInput === "") {
          this.IDPParamData.build.checkmarxIncremental = "NA";
        }
        if (
          this.idpdataService.schedulePage === true ||
          this.idpdataService.createWorkflowSequenceflag
        ) {
          this.confirmSaveModalRef = this.modalService.show(this.modalforSave);
        } else {
          this.triggerModalRef = this.modalService.show(this.modalforTrigger);
        }
      }
    }
  }
  saveData(modalRef) {
    modalRef.hide();
    this.onTriggerDetailsSaved.emit(true);
    this.idpdataService.loading = true;
    this.IDPParamData.releaseNumber = this.IDPDataSwitch.releaseNumber;
    if (this.IDPDataSwitch.repoName === "na") {
      this.IDPParamData.deploy.deployArtifact = {};
    } else {
      if (this.IDPParamData.deploy !== null) {
        this.IDPParamData.deploy.deployArtifact = this.IDPParamData.deploy.artifacts;
      }
    }

    // console.log(this.IDPParamData.deploy.deployArtifact);

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
    if (
      ((this.IDPDataSwitch.buildSelected === "off" ||
        this.IDPDataSwitch.buildSelected === null ||
        this.IDPDataSwitch.buildSelected === undefined) &&
        (this.IDPDataSwitch.testSelected === "off" ||
          this.IDPDataSwitch.testSelected === null ||
          this.IDPDataSwitch.testSelected === undefined) &&
        (this.IDPDataSwitch.deploySelected === "off" ||
          this.IDPDataSwitch.deploySelected === null ||
          this.IDPDataSwitch.deploySelected === undefined)) ||
      (this.IDPParamData.deploy.update !== "on" &&
        this.IDPParamData.deploy.rollback !== "on" &&
        this.IDPParamData.deploy.misc !== "on" &&
        this.idpdataService.triggerJobData.technology === "dbDeployDelphix")
    ) {
      this.idpdataService.loading = false;
      if (
        this.IDPParamData.deploy.update !== "on" &&
        this.IDPParamData.deploy.rollback !== "on" &&
        this.IDPParamData.deploy.misc !== "on" &&
        this.idpdataService.triggerJobData.technology === "dbDeployDelphix"
      ) {
        this.triggerAlertDBDep();
      } else {
        this.triggerAlert();
      }
      // this.parameterloading = false;
    } else {
      if (
        this.tempDeploySteps.length !== 0 &&
        this.tempDeploySteps.length !== undefined
      ) {
        for (let i = 0; i < this.deployArr.length; i++) {
          const step = this.deployArr[i];
          if (this.deployArr[i] !== null) {
            this.IDPParamData.deploy.deployStep.push(step);
            // console.log(this.IDPParamData.deploy.deployStep);
          }
        }
      }
      if (
        this.tempTestSteps.length !== 0 &&
        this.tempTestSteps.length !== undefined
      ) {
        for (let i = 0; i < this.testArr.length; i++) {
          const step = this.testArr[i];
          if (this.testArr[i] !== null) {
            this.IDPParamData.testStep.push(step);
            // console.log(this.IDPParamData.testStep);
          }
        }
      }
      const requestData = this.IDPParamData;
      //console.log(requestData);

      if (this.idpdataService.isSAPApplication) {
        if (
          this.IDPDataSwitch.buildSelected === "on" &&
          this.idpdataService.checkpollALM
        ) {
          if (this.buildEnv[0]) {
            requestData.envSelected = this.buildEnv[0];
            // console.log(this.buildEnv[0]);
          }
        }
      }
      if (
        this.IDPDataSwitch.buildSelected === "off" ||
        this.IDPDataSwitch.buildSelected === null ||
        this.IDPDataSwitch.buildSelected === undefined
      ) {
        requestData.build = null;
      }
      if (
        this.IDPDataSwitch.deploySelected !== "on" ||
        this.IDPDataSwitch.deploySelected === null ||
        this.IDPDataSwitch.deploySelected === undefined
      ) {
        requestData.deploy = null;
      }
      if (
        this.IDPDataSwitch.resetSelected !== "on" ||
        this.IDPDataSwitch.resetSelected === null ||
        this.IDPDataSwitch.resetSelected === undefined
      ) {
        requestData.resetStatus = null;
        requestData.resetSelected = "off";
      } else {
        requestData.resetSelected = "on";
      }
      if (
        this.IDPDataSwitch.testSelected === "off" ||
        this.IDPDataSwitch.testSelected === null ||
        this.IDPDataSwitch.testSelected === undefined
      ) {
        if (this.idpdataService.isSAPApplication) {
          // requestData.testSelected = 'off';
        } else {
          requestData.testSelected = "off";
          // requestData.test = null;
        }
      } else if (this.IDPDataSwitch.testSelected === "on") {
        requestData.testSelected = "on";
      }

      if (this.IDPDataSwitch.resetSelected === "on") {
        requestData.resetSelected = "on";
      } else {
        requestData.resetSelected = "off";
      }

      if (
        this.IDPDataSwitch.reconcileSelected === undefined ||
        this.IDPDataSwitch.reconcileSelected === null ||
        this.IDPDataSwitch.reconcileSelected === "off"
      ) {
        requestData.rebase = null;
        // console.log("rebase: " + requestData.rebase);
      }

      if (
        this.IDPDataSwitch.deploySelected === "off" &&
        this.IDPDataSwitch.testSelected === "off" &&
        !this.idpdataService.isSAPApplication
      ) {
        requestData.envSelected = "";
      }
      requestData.jobParam = [];
      //console.log(requestData);

      // console.log(JSON.stringify(requestData));
      this.idpdataService.buildIntervalData[
        this.idpdataService.index
      ].details = requestData;
      this.idpdataService.statusCheck[this.idpdataService.index] = "off";
      // console.log(this.idpdataService.buildIntervalData);
      this.idpdataService.loading = false;
    }

    // console.log(this.idpdataService.triggerJobData);
    this.initialize();
  }

  triggerData(modalRef) {
    modalRef.hide();
    this.idpdataService.loading = true;
    const requestData = this.getRequestData();
    if (requestData === undefined) {
      // console.log("Request data undefined: " + requestData);
    } else {
      this.idprestapiService.triggerJobs(requestData).then((response) => {
        try {
          if (response) {
            const err = response.json().errorMessage;
            if (
              err === null &&
              response.json().resource.toLowerCase() === "success"
            ) {
              this.idpdataService.loading = false;
              this.msg = "success";
              this.disableSubmitBtn = true;
              setTimeout(() => {
                this.router.navigate(["/previousConfig/stageviewTrigger"]);
              }, 7000);
              this.redirectTo();
            } else {
              this.disableSubmitBtn = false;
              this.idpdataService.loading = false;
              this.msg = "error";
              setTimeout(() => {
                this.router.navigate(["/previousConfig"]);
              }, 7000);
            }
          }
        } catch (e) {
          //console.log(e);
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
    let resetUncheck = false;
    this.IDPParamData.releaseNumber = this.IDPDataSwitch.releaseNumber;
    this.IDPParamData.resetSelected =
      this.IDPDataSwitch.resetSelected === "on" ? "on" : "off";
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
        } else {
          for (const i of this.selectedUS) {
            this.IDPParamData.userStories.push(i.itemName);
          }
        }
      }
      for (const i of this.selectedItems) {
        this.IDPParamData.transportRequest.push(i.itemName);
      }
      if (this.idpdataService.checkpollALM) {
        for (const data of this.userStoryArray) {
          const userStoryMapsTRs = {
            userstory: "",
            transportRequests: [],
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
      if (this.IDPParamData.technology === "sapcharm") {
        this.IDPParamData.transportRequest = this.transportRequests;
        /*	let onlyCDNosList = [];
		for (let cd of this.selectedCDData ){
			onlyCDNosList.push(cd.split(",")[0]);
		}
        this.IDPParamData.changeDocumentList = onlyCDNosList;*/
        this.IDPParamData.changeDocumentList = this.selectedCDData;
        this.IDPParamData.charmConfigType = this.IDPDataSwitch.charmConfigType;
        this.IDPParamData.changeDocumentDetialsList = this.changeRequestDetails.changeDocumentMapping;
      }
    }
    if (
      this.IDPDataSwitch.buildSelected === "off" ||
      this.IDPDataSwitch.buildSelected === null ||
      this.IDPDataSwitch.buildSelected === undefined
    ) {
      buildUncheck = true;
    }
    if (
      this.IDPDataSwitch.testSelected === "off" ||
      this.IDPDataSwitch.testSelected === null ||
      this.IDPDataSwitch.testSelected === undefined
    ) {
      testUncheck = true;
    }

    if (
      this.IDPDataSwitch.deploySelected === "off" ||
      this.IDPDataSwitch.deploySelected === null ||
      this.IDPDataSwitch.deploySelected === undefined
    ) {
      deployUncheck = true;
    }

    if (
      this.IDPDataSwitch.reconcileSelected === "off" ||
      this.IDPDataSwitch.reconcileSelected === null ||
      this.IDPDataSwitch.reconcileSelected === undefined
    ) {
      reconcileUncheck = true;
    }
    if (
      this.IDPDataSwitch.resetSelected === "off" ||
      this.IDPDataSwitch.resetSelected === null ||
      this.IDPDataSwitch.resetSelected === undefined
    ) {
      resetUncheck = true;
    }

    if (
      (!this.idpdataService.isSAPApplication &&
        buildUncheck &&
        testUncheck &&
        deployUncheck) ||
      (this.idpdataService.isSAPApplication &&
        buildUncheck &&
        testUncheck &&
        deployUncheck &&
        reconcileUncheck &&
        resetUncheck) ||
      (this.idpdataService.triggerJobData.technology === "dbDeployDelphix" &&
        this.IDPParamData.deployDB === "on" &&
        this.IDPParamData.dbOperations !== undefined &&
        this.IDPParamData.dbOperations.length === 0)
    ) {
      this.idpdataService.loading = false;
      if (
        this.idpdataService.triggerJobData.technology === "dbDeployDelphix" &&
        this.IDPParamData.deployDB === "on" &&
        this.IDPParamData.dbOperations !== undefined &&
        this.IDPParamData.dbOperations.length === 0
      ) {
        this.triggerAlertDBDep();
      } else {
        this.triggerAlert();
      }
    } else {
      if (
        this.tempDeploySteps.length !== 0 &&
        this.tempDeploySteps.length !== undefined
      ) {
        for (let i = 0; i < this.deployArr.length; i++) {
          const step = this.deployArr[i];
          if (this.deployArr[i] !== null) {
            // console.log(this.deployArr[i]);
            this.IDPParamData.deploy.deployStep.push(step);
          }
        }
      }
      if (
        this.tempTestSteps.length !== 0 &&
        this.tempTestSteps.length !== undefined
      ) {
        for (let i = 0; i < this.testArr.length; i++) {
          const step = this.testArr[i];
          if (this.testArr[i] !== null) {
            this.IDPParamData.testStep.push(step);
          }
        }
      }

      if (
        this.IDPParamData.dbOperations !== undefined &&
        this.IDPParamData.dbOperations.length !== 0
      ) {
        const list = this.idpService.copy(this.IDPParamData.dbOperations);
        this.IDPParamData.dbOperations = "";
        for (let i = 0; i < list.length; i++) {
          this.IDPParamData.dbOperations += list[i] + ";";
        }
      } else if (this.IDPParamData.dbOperations !== undefined) {
        this.IDPParamData.dbOperations = "";
      }
      if (
        this.virtualServicesArr !== undefined &&
        this.virtualServicesArr.length > 0
      ) {
        this.IDPParamData.virtualServicesList = this.virtualServicesArr;
      }
      requestData = this.IDPParamData;

      if (this.idpdataService.isSAPApplication) {
        if (
          this.IDPDataSwitch.buildSelected === "on" &&
          this.IDPDataSwitch.deploySelected !== "on" &&
          (this.idpdataService.checkpollALM ||
            this.IDPParamData.technology === "sapcharm")
        ) {
          if (this.buildEnv[0]) {
            requestData.envSelected = this.buildEnv[0];
          }
        }
      }
      if (
        this.IDPDataSwitch.buildSelected === "off" ||
        this.IDPDataSwitch.buildSelected === null ||
        this.IDPDataSwitch.buildSelected === undefined
      ) {
        requestData.build = null;
      }
      if (
        this.IDPDataSwitch.deploySelected !== "on" ||
        this.IDPDataSwitch.deploySelected === null ||
        this.IDPDataSwitch.deploySelected === undefined
      ) {
        requestData.deploy = null;
      }

      if (
        this.IDPDataSwitch.resetSelected !== "on" ||
        this.IDPDataSwitch.resetSelected === null ||
        this.IDPDataSwitch.resetSelected === undefined
      ) {
        requestData.resetStatus = null;
      }

      if (
        this.IDPDataSwitch.testSelected === "off" ||
        this.IDPDataSwitch.testSelected === null ||
        this.IDPDataSwitch.testSelected === undefined
      ) {
        if (!this.idpdataService.isSAPApplication) {
          requestData.testSelected = "off";
        }
      } else if (this.IDPDataSwitch.testSelected === "on") {
        requestData.testSelected = "on";
      }
      if (
        this.IDPDataSwitch.reconcileSelected === undefined ||
        this.IDPDataSwitch.reconcileSelected === null ||
        this.IDPDataSwitch.reconcileSelected === "off"
      ) {
        requestData.rebase = null;
      }
      if (
        this.IDPDataSwitch.deploySelected === "off" &&
        this.IDPDataSwitch.testSelected === "off" &&
        !this.idpdataService.isSAPApplication
      ) {
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
      if (
        this.IDPParamData.build !== undefined &&
        this.IDPParamData.build !== null &&
        this.IDPParamData.build.checkmarxIncremental !== undefined
      ) {
        requestData.build.checkmarxIncremental = this.IDPParamData.build.checkmarxIncremental;
      }
      if (
        this.IDPParamData.build !== undefined &&
        this.IDPParamData.build !== null &&
        this.IDPParamData.build.checkmarxTag !== undefined &&
        this.IDPParamData.build.checkmarxTag === "on" &&
        this.IDPParamData.build.checkmarxIncremental === ""
      ) {
        requestData.build.checkmarxIncremental = "off";
      }
      if (
        this.IDPParamData.build !== undefined &&
        this.IDPParamData.build !== null &&
        this.IDPParamData.build.runCheckmarxInput === ""
      ) {
        requestData.build.runCheckmarxInput = "off";
      }
      if (
        this.idpdataService.triggerJobData.build != null &&
        this.idpdataService.triggerJobData.build.checkMarxDetails !==
          undefined &&
        this.idpdataService.triggerJobData.build.checkMarxDetails != null
      ) {
        requestData.build.checkMarxDetails = this.idpdataService.triggerJobData.build.checkMarxDetails;
      }
    }
    return requestData;
  }

  checkPermissions() {
    this.showPermissionError = "";
    if (
      this.idpdataService.idpUserName.toLowerCase() ===
      this.crApprover.toLowerCase()
    ) {
      // checking CR Approver permissions
      if (this.IDPDataSwitch.charmConfigType === "master") {
        if (this.IDPParamData.envSelected === "DEV") {
          this.showPermissionError = "CR Approver cannot be developer.";
        }
      } else if (
        this.IDPParamData.build.unitTest === "on" ||
        this.IDPParamData.build.codeAnalysis === "on"
      ) {
        this.showPermissionError = "CR Approver cannot be developer.";
      } else if (this.IDPParamData.envSelected === "PROD") {
        this.showPermissionError = "CR Approver cannot be CC.";
      }
    }
    if (this.showPermissionError == "") {
      // checking CD related permissions
      for (
        let k = 0;
        k < this.changeRequestDetails.changeDocumentMapping.length;
        k++
      ) {
        let currentCD = this.changeRequestDetails.changeDocumentMapping[k];

        if (this.selectedCDData.indexOf(currentCD.changeDocument) !== -1) {
          if (
            this.idpdataService.idpUserName.toLowerCase() ===
            currentCD.developer.toLowerCase()
          ) {
            if (
              this.IDPParamData.envSelected === "QA" ||
              this.IDPParamData.envSelected === "PROD"
            ) {
              this.showPermissionError =
                "Developer/Technical Reviewer cannot be Functional Reviewer.";
            }
          } else if (
            this.idpdataService.idpUserName.toLowerCase() ===
            currentCD.functionalReviewer.toLowerCase()
          ) {
            if (
              this.IDPParamData.build.unitTest === "on" ||
              this.IDPParamData.build.codeAnalysis === "on"
            ) {
              this.showPermissionError =
                "Functional Reviewer cannot be Developer/Technical Reviewer.";
            }
          } else if (
            this.idpdataService.idpUserName.toLowerCase() ===
            currentCD.technicalReviewer.toLowerCase()
          ) {
            if (
              this.IDPParamData.envSelected === "QA" ||
              this.IDPParamData.envSelected === "PROD"
            ) {
              this.showPermissionError =
                "Technical Reviewer cannot be Functional Reviewer/CC.";
            } else if (
              this.IDPParamData.envSelected &&
              this.IDPDataSwitch.testSelected === "on"
            ) {
              this.showPermissionError =
                "Technical Reviewer cannot be Functional Tester.";
            }
          } else if (
            this.idpdataService.idpUserName.toLowerCase() ===
            currentCD.CC.toLowerCase()
          ) {
            if (
              this.IDPParamData.build.unitTest === "on" ||
              this.IDPParamData.build.codeAnalysis === "on"
            ) {
              this.showPermissionError =
                "CC cannot be Developer/Technical Reviewer.";
            }
          } else if (
            this.IDPParamData.approvalSelected === "Successfully Tested" ||
            this.IDPParamData.approvalSelected === "DM Approved"
          ) {
            let trackSelected = this.changeRequestDetails.crsBasedOnTrackSubtrack.filter(
              (x) => this.charmTriggerData.sapTrackSelected === x.track
            );
            if (trackSelected[0].trackRolesMapping.includes("CC")) {
              if (
                this.idpdataService.idpUserName.toLowerCase() ===
                currentCD.developer.toLowerCase()
              ) {
                this.showPermissionError =
                  "CC cannot be Developer/Technical Reviewer.";
              }
            }
          }
        }
      }
    }
  }

  validateApprovalStage() {
    this.isAllowed = false;
    if (!this.showPermissionError) {
      let currentApproverStage = this.IDPParamData.approvalSelected;
      let trackSelected = this.changeRequestDetails.crsBasedOnTrackSubtrack.filter(
        (x) => this.charmTriggerData.sapTrackSelected === x.track
      );

      switch (currentApproverStage) {
        case "Successfully Tested": {
          if (trackSelected[0].trackRolesMapping.includes("CC")) {
            this.isAllowed = true;
          } else {
            this.showPermissionError =
              "You are not authorized for this request.";
          }
          break;
        }
        case "Authorized for Production": {
          if (trackSelected[0].trackRolesMapping.includes("DM")) {
            this.isAllowed = true;
          } else {
            this.showPermissionError =
              "You are not authorized for this request.";
          }
          break;
        }
        case "Ready for Production Import": {
          if (trackSelected[0].trackRolesMapping.includes("PF")) {
            this.isAllowed = true;
          } else {
            this.showPermissionError =
              "You are not authorized for this request.";
          }
          break;
        }
        case "PFA Approved": {
          if (trackSelected[0].trackRolesMapping.includes("DM")) {
            this.isAllowed = true;
          } else {
            this.showPermissionError =
              "You are not authorized for this request.";
          }
          break;
        }
        case "DM Approved": {
          if (trackSelected[0].trackRolesMapping.includes("CC")) {
            this.isAllowed = true;
          } else {
            this.showPermissionError =
              "You are not authorized for this request.";
          }
          break;
        }
      }
    }
  }

  saveWorkflowData(modalRef) {
    modalRef.hide();
    this.onTriggerDetailsSaved.emit(true);
    this.idpdataService.loading = true;
    const requestData = this.getRequestData();

    if (requestData === undefined) {
      // console.log("Request data undefined: " + requestData);
    } else {
      //console.log(requestData);

      // console.log(JSON.stringify(requestData));
      if (this.workflowSequenceIndexI !== undefined) {
        // console.log("workflowSequenceIndex: " + this.workflowSequenceIndexI);
        this.idpdataService.workflowData.workflowSequence[
          this.workflowSequenceIndexI
        ].applicationDetails[this.workflowSequenceIndexJ].pipelineDetails[
          this.workflowSequenceIndexK
        ] = requestData;
        this.idpdataService.workflowData.workflowSequenceTemp[
          this.workflowSequenceIndexI
        ].IDPDataSwitch = this.IDPDataSwitch;
      } else {
        console.log(
          "Unable to update as workflowSequenceIndex is: " +
            this.workflowSequenceIndexI
        );
      }
      
    }
    this.idpdataService.loading = false;
  }

  redirectTo() {}

  getSlaveStatus(slave) {
    this.idprestapiService.getSlaveStatus(slave).then((response) => {
      if (response) {
        if (
          response.json().resource !== null &&
          response.json().resource !== "[]" &&
          response.json().resource !== "{}"
        ) {
          if (response.json().resource !== null) {
            this.slavestatus = response.json().resource;
          } else {
            this.slavestatus = "Not Found";
          }
        }
      } else {
        this.slavestatus = "";
      }
    });
  }

  getTestSlaveStatus(slave) {
    this.idprestapiService.getSlaveStatus(slave).then((response) => {
      if (response) {
        if (
          response.json().resource !== null &&
          response.json().resource !== "[]" &&
          response.json().resource !== "{}"
        ) {
          if (response.json().resource !== null) {
            this.testslavestatus = response.json().resource;
          } else {
            this.testslavestatus = "Not Found";
          }
        }
      } else {
        this.testslavestatus = "";
      }
    });
  }

  optionalGetArtifactsRm() {
    if (
      this.rmsEnv === undefined ||
      this.rmsEnv.indexOf(this.IDPParamData.envSelected) === -1
    ) {
      this.getArtifactsRm();
    }
  }
  getArtifactsRm() {
    if (this.IDPDataSwitch.repoName !== "na") {
      if (
        this.IDPDataSwitch.deploySelected === "on" &&
        (this.IDPDataSwitch.buildSelected !== "on" ||
          this.IDPParamData.technology === "dbDeployDelphix")
      ) {
        if (this.IDPParamData.envSelected !== "") {
          const data = {
            applicationName: this.IDPParamData.applicationName,
            artifactList: this.idpdataService.triggerJobData.artifactList,
            environmentName: this.IDPParamData.envSelected,
            pipelineName: this.IDPParamData.pipelineName,
            releaseNumber: this.IDPDataSwitch.releaseNumber,
          };
          this.idprestapiService.getArtifactsRm(data).then((response) => {
            if (response) {
              if (
                response.json().resource !== null &&
                response.json().resource !== "[]" &&
                response.json().resource !== "{}"
              ) {
                this.artifacts = JSON.parse(
                  response.json().resource
                ).artifactList;
                this.allArtifactlist = JSON.parse(
                  response.json().resource
                ).artifactList;
                const tempLatestArtifact = [
                  {
                    artifactName: "",
                    artifactID: "",
                    groupId: "",
                    nexusURL: "",
                    repoName: "",
                    version: "",
                    buildModules: "",
                    enviromment: "",
                    userInfo: "",
                    timestamp: "",
                    downloadURL: "",
                  },
                ];

                this.filterArtifacts();
                if (this.rmsEnv.indexOf(this.IDPParamData.envSelected) !== -1) {
                  this.filterArtifactsRms();
                }
                const artifactSize = this.artifacts.length;
                tempLatestArtifact[0].artifactName =
                  this.idpdataService.triggerJobData.applicationName +
                  "_" +
                  this.idpdataService.triggerJobData.pipelineName +
                  "_latestArtifact";
                //alert("version" + this.artifacts[artifactSize-1].version);
                tempLatestArtifact[0].groupId = this.idpdataService.triggerJobData.applicationName;
                tempLatestArtifact[0].artifactID = this.idpdataService.triggerJobData.pipelineName;
                tempLatestArtifact[0].nexusURL = this.idpdataService.triggerJobData.nexusURL;
                tempLatestArtifact[0].repoName = this.idpdataService.triggerJobData.repoName;
                tempLatestArtifact[0].downloadURL =
                  "http://" +
                  tempLatestArtifact[0].nexusURL +
                  "/repository/" +
                  tempLatestArtifact[0].repoName +
                  "/" +
                  this.IDPParamData.applicationName +
                  "/" +
                  this.IDPParamData.pipelineName +
                  "/" +
                  this.artifacts[artifactSize - 1].version +
                  "/" +
                  this.IDPParamData.pipelineName +
                  "-" +
                  this.artifacts[artifactSize - 1].version +
                  ".zip";
                if (this.rmsEnv.indexOf(this.IDPParamData.envSelected) === -1)
                  this.artifacts.push(tempLatestArtifact[0]);
              }
            }
            this.idpdataService.loading = false;
          });
        }
      }
    }
  }

  filterArtifactsRms() {
    this.rmsArtifacts = [];
    for (let i = 0; i < this.artifacts.length; i++) {
      if (
        _.includes(this.artifacts[i].artifactName, this.rmsApprovedArtifact) !==
        false
      ) {
        this.rmsArtifacts.push(this.artifacts[i]);
      }
    }
    if (this.rmsArtifacts.length === 0) {
      alert(
        "Please select both build and deploy as no approved artifacts are present"
      );
    }
    this.artifacts = this.rmsArtifacts;
  }

  filterArtifacts() {
    this.artifacts = [];
    for (let i = 0; i < this.allArtifactlist.length; i++) {
      if (
        _.includes(
          this.allArtifactlist[i].version,
          this.IDPDataSwitch.releaseNumber
        ) !== false
      ) {
        this.artifacts.push(this.allArtifactlist[i]);
      }
    }
    if (this.artifacts.length === 0) {
      alert("Please select both build and deploy");
    }
  }

  /*calling rest service for getting jobparam details*/
  getJobParamDetails() {
    this.idprestapiService.getJobParamList().then((response) => {
      if (response) {
        const resp = response.json().resource;
        let parsed;
        try {
          if (
            response.json().resource !== "{}" &&
            response.json().resource !== null
          ) {
            parsed = response.json().resource;
            this.idpdataService.triggerJobData.jobParamList = JSON.parse(
              parsed
            );
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
          //alert("Failed to get JobparamDetails Details");
          //console.log(e);
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
          if (
            branchFrom[x].indexOf(this.IDPParamData.branchOrTagValue) !== -1
          ) {
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
          if (
            this.tagTemp[x].indexOf(this.IDPParamData.branchOrTagValue) !== -1
          ) {
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
      application_name: this.IDPParamData.applicationName,
      pipeline_name: this.IDPParamData.pipelineName,
      env_name: this.IDPParamData.envSelected,
    };
    let resp;
    this.idprestapiService.fetchTriggerSteps(data).then((response) => {
      try {
        if (response) {
          const err = response.json().errorMessage;
          if (
            err === null &&
            response.json().status.toLowerCase() === "success"
          ) {
            resp = JSON.parse(response.json().resource);
            if (resp.deploySteps !== undefined) {
              for (let i = 0; i < resp.deploySteps.length; i++) {
                const step = {
                  id: i,
                  itemName: resp.deploySteps[i],
                };
                this.dropdownListDeploy.push(step);
                console.log(this.dropdownListDeploy);
                this.deployArr[i] = null;
              }
            }
            if (resp.testSteps !== undefined) {
              for (let j = 0; j < resp.testSteps.length; j++) {
                const step = {
                  id: j,
                  itemName: resp.testSteps[j].stepName,
                };
                this.testStepWithToolList.push(resp.testSteps[j]);
                this.dropdownListTest.push(step);
                this.testArr[j] = null;
              }
            }
          }
        }
      } catch (e) {
        //console.log(e);
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
            this.IDPParamData.mtmStepName = this.testStepWithToolList[
              i
            ].stepName;
            break;
          }
        }
      }
    }

    if (flagTestPlan) {
      this.idpdataService.loading = true;
      this.idprestapiService
        .getTestPlanList(
          this.IDPParamData.applicationName,
          this.IDPParamData.pipelineName
        )
        .then((response) => {
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
    this.idprestapiService
      .getTestSuitList(
        planId,
        this.IDPParamData.applicationName,
        this.IDPParamData.pipelineName
      )
      .then((response) => {
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
      applicationName: this.IDPParamData.applicationName,
      pipelineName: this.IDPParamData.pipelineName,
      envName: this.IDPParamData.envSelected,
    };
    this.idprestapiService
      .getEnvironmentPairs(this.envJson)
      .then((response) => {
        if (response) {
          if (response.json().resource !== null) {
            this.IDPParamData.pairNames = JSON.parse(
              response.json().resource
            ).names;
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
          children: [],
        };
      }
      tempJson[parentKey].children.push(suiteObj);
    }
    let finalList;
    finalList = JSON.parse(
      JSON.stringify(tempJson["root"].children)
        .split('"testSuitName":')
        .join('"text":')
    );
    finalList = JSON.parse(
      JSON.stringify(finalList).split('"testSuitId":').join('"value":')
    );
    finalList = JSON.parse(
      JSON.stringify(finalList).replace('"testSuitParent":"na",', "")
    );
    const result = this.createTreeView({
      text: "na",
      value: "na",
      children: finalList,
    });
    this.testSuitList = result.children;
  }

  onSelectedChange(downlineItems: DownlineTreeviewItem[]) {}

  addItem(item) {
    if (item.srcElement.checked) {
      if (this.testSuitId.indexOf(item.srcElement.name) === -1) {
        this.testSuitId.push(item.srcElement.name);
      }
      return true;
    } else {
      if (this.testSuitId.indexOf(item.srcElement.name) !== -1) {
        this.testSuitId.splice(
          this.testSuitId.indexOf(item.srcElement.name),
          1
        );
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
      const x = {
        value: data.value,
        text: data.text,
        children: [],
        checked: false,
        collapsed: true,
      };
      return new TreeviewItem(x, true);
    } else {
      const d = {
        text: data.text,
        value: data.value,
        children: data.children,
        checked: false,
        collapsed: true,
      };
      for (let i = 0; i < data.children.length; i++) {
        data.children[i] = this.createTreeView(data.children[i]);
        d.children[i] = data.children[i];
      }
      return new TreeviewItem(d, true);
    }
  }

  // <----- Deploy Steps Multi-select Dropdown functions---->

  onItemSelectDepSteps(item: any) {
    this.deployArr.push(item.itemName);
  }
  trOnItemSelect(item: any) {
    this.trialArr[item.id] = item.itemName;
  }

  trOnItemSelectDe(item: any) {
    const i = this.trialArr.indexOf(item.itemName);
    if (i !== -1) {
      this.trialArr[i] = null;
    }
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
    this.testArr.push(item.itemName);
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
        this.IDPParamData.dbOperations.splice(
          this.IDPParamData.dbOperations.indexOf(event.target.name),
          1
        );
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
      applicationName: this.IDPParamData.applicationName,
      pipelineName: this.IDPParamData.pipelineName,
      userName: this.idpdataService.idpUserName,
    };
    this.idpdataService.loading = true;
    this.idprestapiService.getPipelineDetails(data).then((response) => {
      //console.log(new Date().toUTCString(), "Pipeline details retrieved");
      try {
        const responseData = this.idpencryption.decryptAES(
          response.json().resource
        );
        let resp = JSON.parse(responseData);
        resp = this.idpencryption.doubleEncryptPassword(resp.pipelineJson);
        this.idpdataService.buildIntervalData =
          resp.basicInfo.customTriggerInterval.interval;
      } catch (e) {
        //console.log("Failed to get the Build Interval Schedule Details");
        //console.log(e);
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
    this.idpdataService.buildIntervalData.push({
      type: "",
      minute: "",
      time: "",
      details: {},
    });
  }

  getArtifactLatestDetails(artifacts) {
    // console.log(artifacts.artifactName);
    const dashboardURL: String = this.idpdataService.IDPDashboardURL;
    // console.log("dashboard url: " + dashboardURL);
    const hostName: String = dashboardURL.split(":")[1].substr(2);
    this.dashboardUrl =
      "https://" +
      hostName +
      ":3000/dashboard/db/artifact-view?orgId=1&var-Application=" +
      this.IDPParamData.applicationName;
    this.dashboardUrl =
      this.dashboardUrl + "&var-Pipeline=" + this.IDPParamData.pipelineName;
    this.dashboardUrl =
      this.dashboardUrl + "&var-ReleaseNo=" + this.IDPDataSwitch.releaseNumber;
    this.dashboardUrl =
      this.dashboardUrl + "&var-ArtifactId=" + artifacts.artifactName;
    this.idprestapiService
      .getArtifactLatestDetails(artifacts.artifactName)
      .then((response) => {
        if (response) {
          if (
            response.json().resource !== null &&
            response.json().resource !== "[]" &&
            response.json().resource !== "{}"
          ) {
            this.artifactDetails = JSON.parse(
              response.json().resource
            ).artifactDetails;
            this.packageContent = JSON.parse(
              response.json().resource
            ).packageContent;
            for (const artifactvalue of this.artifactDetails) {
            }
            if (
              this.packageContent !== undefined &&
              this.packageContent.ant !== undefined &&
              this.packageContent.ant.moduleName !== undefined
            ) {
              this.ant = this.packageContent.ant.moduleName;
            }
            if (
              this.packageContent !== undefined &&
              this.packageContent.dotNet !== undefined &&
              this.packageContent.dotNet.moduleName !== undefined
            ) {
              this.dotNet = this.packageContent.dotNet.moduleName;
            }
            if (
              this.packageContent !== undefined &&
              this.packageContent.bigData !== undefined &&
              this.packageContent.bigData.moduleName !== undefined
            ) {
              this.bigData = this.packageContent.bigData.moduleName;
            }
            if (
              this.packageContent !== undefined &&
              this.packageContent.pega !== undefined
            ) {
              this.pega = this.packageContent.pega;
            }
            if (
              this.packageContent !== undefined &&
              this.packageContent.siebel !== undefined
            ) {
              this.siebel = this.packageContent.siebel;
              console.log(" Siebel DataContent " + this.siebel);
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

  clearArtifactSelection(){
    this.IDPParamData.deploy.artifacts = "";
  }


  isLatestArtifact() {
    return (this.IDPParamData.deploy.artifacts.artifactName as String).endsWith(
      "_latestArtifact"
    );
  }

  onItemSelectUserStory(item: any) {
    for (let j = 0; j < this.userStoryArray.length; j++) {
      if (item.itemName === this.userStoryArray[j].Userstory) {
        this.selectedUSData.push(this.userStoryArray[j]);
        break;
      }
    }
    console.log(this.selectedUSData);
    this.getTransportRequestForUserStories("onSelect", item.itemName);
  }
  OnItemDeSelectUserStory(item: any) {
    let index;
    for (let j = 0; j < this.selectedUSData.length; j++) {
      if (item.itemName === this.selectedUSData[j].Userstory) {
        index = j;
        break;
      }
    }
    this.selectedUSData.splice(index, 1);
    this.getTransportRequestForUserStories("onDeselect", item.itemName);
  }
  onSelectAllUserStory(items: any) {
    this.selectedUSData = this.userStoryArray;
    this.getTransportRequestForUserStories("onSelectAll", "");
  }
  onDeSelectAllUserStory(items: any) {
    this.selectedUSData = [];
    this.getTransportRequestForUserStories("onDeselectAll", "");
  }
  rmsToken() {
    if (this.IDPDataSwitch.repoName !== "na") {
      if (
        this.IDPDataSwitch.deploySelected === "on" &&
        (this.IDPDataSwitch.buildSelected !== "on" ||
          this.IDPParamData.technology === "dbDeployDelphix")
      ) {
        if (this.IDPParamData.envSelected !== "") {
          if (this.rmsEnv.indexOf(this.IDPParamData.envSelected) !== -1) {
            if (this.rmsComponentName === "") {
              alert("No Rms Component Name present. Please edit the pipeline.");
            }
            this.idprestapiService
              .getRmsToken(this.rmsComponentName)
              .then((response) => {
                try {
                  alert("Successfully retrieved RMS token!!");
                  console.log(response.json().resource);
                  const str = response.json().resource;

                  this.rmsApprovedArtifact = str.substr(1).slice(0, -1);
                  //alert("after token call")
                  this.getArtifactsRm();
                } catch (e) {
                  console.log("Failed to get Rms token");
                  //console.log(e);
                }
              });
          }
        }
      }
    }
  }

  OnItemSelectChangeDocument(item: any) {
    this.selectedCDData.push(item.itemName.split(" -->")[0]);
    this.getCharmTransportData();
  }
  OnItemDeSelectChangeDocument(item: any) {
    let index;
    for (let j = 0; j < this.selectedCDData.length; j++) {
      if (item.itemName.split(" -->")[0] === this.selectedCDData[j]) {
        index = j;
        break;
      }
    }
    this.selectedCDData.splice(index, 1);
    this.getCharmTransportData();
  }
  onSelectAllChangeDocument(items: any) {
    for (let cd of this.changeDocumentList) {
      this.selectedCDData.push(cd.itemName);
      console.log(this.selectedCDData);
    }
    this.getCharmTransportData();
  }
  onDeSelectAllChangeDocument(items: any) {
    this.selectedCDData = [];
    console.log(this.selectedCDData);
    this.getCharmTransportData();
  }

  getCharmTransportData() {
    let cdDetails = this.changeRequestDetails.changeDocumentMapping;
    this.transportRequests = [];
    this.cdDetailsList = [];

    for (let i = 0; i < cdDetails.length; i++) {
      for (let j = 0; j < this.selectedCDData.length; j++) {
        console.log(" selected Cd data :" + this.selectedCDData[j]);
        // console.log(" change Document : " + cdDetails[i].changeDocument);
        if (this.selectedCDData[j] === cdDetails[i].changeDocument) {
          console.log("true inside func");
          let cdDataList = cdDetails[i].changeDocumentData;
          for (let k = 0; k < cdDataList.length; k++) {
            // console.log(cdDetails[i].changeDocumentData[k].trNumber);
            this.cdDetailsList.push(cdDetails[i].changeDocumentData[k]);
            if (cdDetails[i].changeDocumentData[k].trStatus !== "Released") {
              this.transportRequests.push(
                cdDetails[i].changeDocumentData[k].trNumber
              );
            }
          }
        }
      }
    }
  }

  trackDisplayedName(trackInfo) {
    if (trackInfo.track) {
      return trackInfo.track + "-->" + trackInfo.trackDescription;
    }
  }

  clearCrData() {
    // this.hideChangeDocument=true;
    // this.hideChangeRequest=true;
    this.changeRequestList = [];
    this.changeRequestListTemp = [];
    this.changeDocumentList = [];
    this.sapSubTrackListTemp = [];
    this.IDPParamData.resetSlave = "";
    this.showPermissionError = "";
    // this.IDPParamData.resetStatus.fromState = "";
    this.selectedCDData = [];
    this.selectedCD = [];
    this.charmTriggerData.changeRequest = "";
    this.charmTriggerData.sapTrackSelected = "";
    this.charmTriggerData.sapSubTrackSelected = "";
    this.transportRequests = [];
    this.cdDetailsList = [];
    this.showErrorForST = false;
    // this.IDPParamData.approvalSelected = "";
    // this.IDPParamData.slaveName = "";
    // this.slavestatus = "";
  }
  clearCrCd() {
    this.changeRequestList = [];
    this.changeRequestListTemp = [];
    this.changeDocumentList = [];
    this.changeDocumentListTemp = [];
    this.changeDocumentListDescDropdown = [];
    this.selectedCDData = [];
    this.selectedCD = [];
    this.charmTriggerData.changeRequest = "";
    this.transportRequests = [];
    this.cdDetailsList = [];
    this.showPermissionError = "";
    this.showErrorForST = false;
  }

  validateForST() {
    if (
      this.IDPParamData.approvalSelected === "Successfully Tested" &&
      this.selectedLandscapeType === "PROD"
    ) {
      this.showErrorForST = true;
    }
  }

  clearCharmConfigType() {
    this.clearCrCd();
    this.IDPDataSwitch.buildSelected = "off";
    this.IDPDataSwitch.deploySelected = "off";
    this.IDPDataSwitch.testSelected = "off";
    this.showPermissionError = "";
    this.IDPParamData.slaveName = "";
    this.slavestatus = "";
    this.IDPParamData.approvalSelected = "";
    this.selectedLandscapeType = "";
    if (this.IDPParamData.technology === "sapcharm") this.getChangeRequest();
  }
  updateIncrementalScan() {
    if (this.IDPParamData.build.runCheckmarxInput === "on") {
      if (this.IDPParamData.build.checkmarxIncremental === "on") {
        this.IDPParamData.build.checkmarxIncremental = "on";
      } else {
        this.IDPParamData.build.checkmarxIncremental = "off";
      }
    } else {
      this.IDPParamData.build.checkmarxIncremental = "NA";
    }
  }
}
