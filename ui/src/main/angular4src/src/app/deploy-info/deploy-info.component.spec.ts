// /**
// *
// * Copyright 2018 Infosys Ltd.
// * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
// * https://opensource.org/licenses/MIT.”
// *
// **/
// // tslint:disable
// import { async, ComponentFixture, TestBed } from "@angular/core/testing";
// import { FormsModule } from "@angular/forms";
// import { DeployInfoComponent } from "./deploy-info.component";
// import { IdpService } from "../idp-service.service";
// import { IdpdataService } from "../idpdata.service";
// import { IdprestapiService } from "../idprestapi.service";
// import { Router, NavigationExtras } from "@angular/router";
// import { ParentFormConnectComponent } from "../parent-form-connect/parent-form-connect.component";
// import { TranslateModule , TranslateService, TranslateLoader, TranslateParser} from "ng2-translate";
// import { NO_ERRORS_SCHEMA}  from "@angular/core";
// import { IDPEncryption } from "../idpencryption.service";
// import { IdpSubmitService } from "../idpsubmit.service";

// describe("DeployInfoComponent", () => {
//   let component: DeployInfoComponent;
//   let fixture: ComponentFixture<DeployInfoComponent>;
//   let idpService: IdpService;
//   let idpdataService: IdpdataService;
//   let idprestapiService: IdprestapiService;
//   let idpEncryption: IDPEncryption;
//   let router: Router;
//   let idpsubmitService: IdpSubmitService;
//   class  IdpdataServiceStub {


//     constructor() {}
//   template: any = {
//     "grantAccess": {
//       "applicationName": "",
//       "developers": [],
//       "pipelineAdmins": [],
//       "releaseManager": [],
//       "environmentOwnerDetails": [{
//         "environmentName": "",
//         "environmentOwners": [],
//         "dbOwners": []
//       }],
//       "slaveDetails": [
//         {
//           "slaveName": "",
//           "buildServerOS": "",
//           "workspacePath": "",
//           "createNewSlave": "",
//           "labels": "",
//           "sshKeyPath": "",
//           "slaveUsage": "both"
//         }
//       ]
//     },
//     "basicInfo": {
//       "additionalMailRecipients": {
//         "applicationTeam": "",
//         "emailIds": ""
//       },
//       "applicationName": "mockApp",
//       "buildInterval": {
//         "buildInterval": "",
//         "buildIntervalValue": 0,
//         "pollSCM": "off"
//       },
//       "buildServerOS": "windows",
//       "engine": "",
//       "pipelineName": ""
//     },
//     "code": {
//       "category": "standard",
//       "technology": "",
//       "scm": [],
//       "buildScript":  [{"tool": ""}, {"tool": ""}, {}]
//     },
//     "buildInfo": {
//       "buildtool": "",
//       "castAnalysis": {},
//       "artifactToStage": {},
//       "modules": []
//     },
//     "deployInfo": {
//       "deployEnv": [{"deploySteps": []}]
//     },
//     "testInfo": {
//       "testEnv": []
//     },
//     "formStatus": {
//       "basicInfo": {
//         "appNameStatus": "0",
//         "formStatus": "0"
//       },
//       "codeInfo": "",
//       "buildInfo": {
//         "buildToolStatus": "0",
//         "formStatus": "0",
//         "ibmsiTypeStatus": "0"
//       },
//       "deployInfo": "",
//       "testInfo": "",
//       "operation": ""
//     },
//     "checkboxStatus": {
//       "basicInfo": {},
//       "codeInfo": {},
//       "buildInfo": {},
//       "deployInfo": {},
//       "testInfo": {},
//       "others": {}
//     },
//     "backUp": {
//       "deployInfo": {},
//       "testInfo": {}
//     },
//     "allFormStatus": {"deployInfo": {}},
//     "masterJson": {},
//   };
//   data: any =  JSON.parse(JSON.stringify(this.template));
//   releaseManagerTemplate: any  = {
//     "applicationName": "",
// "releasePipeline": [
//   {
//     "pipelineName": "",
//     "release": [
//       {
//         "actualEndDate": "",
//         "actualStartDate": "",
//         "additionalMailRecipients": {
//           "applicationTeam": "",
//           "emailIds": ""
//         },
//         "branchList": [
//           "na"
//         ],
//         "expectedEndDate": "",
//         "expectedStartDate": "",
//         "releaseNumber": "",
//         "remarks": "",
//         "status": "on",
//         "vstsReleaseName": ""
//       }
//     ]
//   }
// ]
// };

// passwordEncryptionList: any = {
//   "code.scm": ["password", "PSpassword"],
//   "code.buildScript": ["password"],
//   "buildInfo": ["password", "artifactToStage.artifactRepo.repoPassword", "postBuildScript.password"],
//   "buildInfo.modules": ["npmProxyPassword", "password", "pegaPassword", "destPassword", "siebelPassword", "ipcPassword",
//    "servPass", "publishForms.password", "publishForms.dbPassword",
//   "workFlowPublish.password", "workFlowPublish.dbPassword", "proxy.password", "sourcePassword"],

//   "deployInfo.deployEnv.deploySteps": ["password", "ipcPassword", "dbPassword", "dbpasswordOTM",
//   "dbPasswordOTM", "dbOwnerPassword", "bizPassword", "formsDbPass", "databasePassword", "ddltmpPassword",
//   "datExportPassword", "workFlowDbPass", "deployPassword", "scalaPassword", "pigPassword", "hivePassword", "dbPwd", "staticPassword",
//   "srfPassword", "admPassword", "adminPassword", "dbOwnerPassword", "appsPass", "tomPwd",
//   "runScript.password", "deployToContainer.password", "deployToContainer.adminPassword",
//   "deployToContainer.sshPassword", "deployToContainer.dbOwnerPassword",
//   "deployToContainer.staticFiles.password", "deployDatabase.restorpassword",
//   "deployDatabase.dbpassword", "targetPassword", "proxy.password"],

//   "testInfo.testEnv.testSteps": ["runScript.password", "test.password"],

//   "virtualServiceServerDetails": ["password"]
// };



// propertySCM: any = {

// };
// pipelineListRm= "";
// releaseManagerData= JSON.parse(JSON.stringify(this.releaseManagerTemplate));
// language = "english";
// idpUserName = "";
// roles = [];
// azureadflag= false;
// expireTime: any;
// access_token: any;
// permissions = [];
// createAppflag = false;
// createOrganisationflag = false;
// createLicenseflag = false;
// createPipelineflag = false;
// copyPipelineflag = false;
// editPipelineflag = false;
// deletePipelineflag = false;
// test = false;
// loadReleasePage= false;
// devServerURL: any = "";
// subscriptionServerURL: any = "";
// IDPDashboardURL = "";
// IDPLink = "";
// geUrl = "";
// role = "";
// profile = "";
// pausedBuildsData: any= {};
// checkPausedBuilds: any= false;
// allFormStatus: any= {
//   "basicInfo": false,
//   "codeInfo": false,
//   "buildInfo": false,
//   "deployInfo": false,
//   "testInfo": false,
//   "workflowInfo": false
// };
// IDPDropdownProperties: any = {};
// showConfig: any;
// pa= true;
// continuecontrol: any;
// geFlag: any;
// p: any = false;
// ejbVal: any;
// warVal: any;
// jarVal: any;
// cloudDeployURL: any;
// pipelineData: any;
// triggerJobData: any;
// jobParamList: any;
// application: any;
// freezeNavBars= false;
// osFlag: any;
// op: any;
// operation: any;
// initMain: any = false;
// RestApiDetails: any = false;
// buildInfoReset = false;
// compMove: any;
// unit: any;
// uName: any= "";
// pass: any= "";
// code: any;
// serverUrl= "";


// authorization= "";
// unitTest: any= false;
// artifactVariable: any= false;
// artifactAppVariable: any= false;
// public loading =  false;
// refreshBuild= false;
//  showRelease= false;
// isSAPApplication= false;
// checkpollALM: boolean ;
// SAPScmCheck: any ;
// authmode= "ldap";
// adalConfig: any=
//   {"clientId": "",
//   "tenate": "",
//   "postLogoutRedirectUri": "",
//   "endpoints": {},
// };
// pipelineName= "";
// appName= "";
// pipelineNames: any;
// releaseAddSuccess: boolean;
// releaseUpdateSuccess: boolean;
// releasePipelineName: "";
// activeReleasePipelineName: "";
// noPipelines: boolean;
// hideDashboard: boolean;
// sapNewFlag= false;
//  buildSubscription= false;
// deploySubscription= false;
// testSubscription= false;
// buildSubscriptionSubmit= false;
// deploySubscriptionSubmit= false;
// noAccess: boolean;
// showService: boolean;
// flag: boolean;
// noAccessNavBars: boolean;
// scheduleJob: any;
// schedulePage: any= false;
// index: any;
// buildIntervalData: any= [];
// statusCheck: any= [];
// workflowData: any = [];
// workflowDataTemp: any = [];
// createWorkflowSequenceflag= false;
// workflowTrigger= false;
// triggerWorkflowJobData: any;
// approveBuildFlag: any= false;
// approveDeployFlag: any= false;
// keycloakToken: any;
// organization: any;
// keycloakUrl: any;
// keycloakRealm: any;
// keycloakClientId: any;

// hideApp= false;

// PagePersmission: any= {
//   "basic" : false,
//   "code" : false,
//   "build" : false,
//   "deploy": false,
//   "test": false
//  };
//   }
//   class  IdpServiceStub {
//      copy(o) {
//     let newO, i;

//     if (typeof o !== "object") {
//       return o;
//     }
//     if (!o) {
//       return o;
//     }

//     if ("[object Array]" === Object.prototype.toString.apply(o)) {
//       newO = [];
//       for (i = 0; i < o.length; i += 1) {
//         newO[i] = this.copy(o[i]);
//       }
//       return newO;
//     }

//     newO = {};
//     for (i in o) {
//       if (o.hasOwnProperty(i)) {
//         newO[i] = this.copy(o[i]);
//       }
//     }
//     return newO;
//   }
//   }
//   class  IdprestapiServiceStub {
//     getIDPDropdownProperties() {

//       const msBuildVersionObj = {
//         msBuildVersion: ""
//       };

//       return msBuildVersionObj;

//     }
//   }
// class  IDPEncryptionStub {
//   }
//   class IdpSubmitServiceStub {}
//   class RouterStub {
//     navigate(commands: any[], extras?: NavigationExtras) { }
//   }
//     const idpdataserviceStub: IdpdataServiceStub = new IdpdataServiceStub();
//     const routerStub: RouterStub = new RouterStub();
//     const idpServiceStub: IdpServiceStub = new IdpServiceStub();
//     const idprestapiServiceStub: IdprestapiServiceStub = new IdprestapiServiceStub();
//   const idpEncryptionStub: IDPEncryptionStub = new IDPEncryptionStub();
//   const idpSubmitServiceStub: IdpSubmitServiceStub = new IdpSubmitServiceStub();

//   beforeEach(async(() => {
//     TestBed.configureTestingModule({
//       declarations: [ DeployInfoComponent, ParentFormConnectComponent ],

//       imports: [FormsModule, TranslateModule],
//       providers: [{provide: IdprestapiService, useValue: idprestapiServiceStub},
//                     {provide: IdpService, useValue: idpServiceStub},
//                     {provide: IdpdataService, useValue: idpdataserviceStub},
//           {provide: IDPEncryption, useValue: idpEncryptionStub},
//           {provide: IdpSubmitService, useValue: idpSubmitServiceStub},
//                 {provide: Router, useValue: routerStub},
//                 TranslateService, TranslateLoader, TranslateParser
//                 ],
//       schemas: [NO_ERRORS_SCHEMA]
//     })
//     .compileComponents();
//   }));

//   beforeEach(() => {
//         jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
//     router = TestBed.get(Router);
//     idpService = TestBed.get(IdpService);
//     idpdataService = TestBed.get(IdpdataService);
//   idprestapiService = TestBed.get(IdprestapiService);
//   idpsubmitService = TestBed.get(IdpSubmitService);
//     idpEncryption = TestBed.get(IDPEncryption);
//     fixture = TestBed.createComponent(DeployInfoComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });

//  it("should create", () => {
//     expect(component).toBeTruthy();
//   });

//   it("should create new", () => {
//     component.deployInfo.deployEnv[0].deploySteps[0] = {"deployToContainer": {"fileName": ""}};
//     expect(component.deployInfo.deployEnv[0].deploySteps[0].deployToContainer.fileName).toBe("");
//   });

//   it("checkStep", () => {
//     console.log("component");
//    expect(component.checkStep(0)).toBeTruthy();
//   });

//   it("clearStep", () => {
//     expect(component.clearStep(0)).toBe("off");
//   });

//   it("checkStepName", () => {
//     expect(component.deployInfo.checkStepName(0, 0).toBe("Step Name must be unique."));
//   });

//   it("should run #setFormStatus()", async(() => {
//     const result = component.setFormStatus({});
//   }));

//   it("should run #TriggerAlert()", async(() => {
//     const result = component.TriggerAlert();
//   }));

//   it("should run #redirectTo()", async(() => {
//     const result = component.redirectTo();
//   }));

//   it("should run #redirectToBasicInfo()", async(() => {
//     const result = component.redirectToBasicInfo();
//   }));

//   it("should run #init()", async(() => {
//     const result = component.init();
//   }));

//   it("should run #checkStep()", async(() => {
//     const result = component.checkStep(0);
//   }));

//   it("should run #clearStep()", async(() => {
//     const result = component.clearStep(0);
//   }));

//   it("should run #checkDeployOperation()", async(() => {
//     const result = component.checkDeployOperation(0, 0);
//   }));

//   it("should run #checkStepName()", async(() => {
//     const result = component.checkStepName(0, 0);
//   }));

//   it("should run #optionList()", async(() => {
//     const result = component.optionList();
//   }));

//   it("should run #clearTempObject()", async(() => {
//     const result = component.clearTempObject(0);
//   }));

//   it("should run #clearObjFlag()", async(() => {
//     const result = component.clearObjFlag(0, 0);
//   }));

//   it("should run #clearIntFlag()", async(() => {
//     const result = component.clearIntFlag(0, 0);
//   }));

//   it("should run #tomcatRestartoff()", async(() => {
//     const result = component.tomcatRestartoff(0, 0);
//   }));

//   it("should run #tomcatRestarton()", async(() => {
//     const result = component.tomcatRestarton(0, 0);
//   }));

//   it("should run #addDeployStep()", async(() => {
//     const result = component.addDeployStep(0);
//   }));

//   it("should run #removeDeployStep()", async(() => {
//     const result = component.removeDeployStep(0, 0);
//   }));

//   it("should run #clearcfg()", async(() => {
//     const result = component.clearcfg(0, 0);
//   }));

//   it("should run #clearconfig()", async(() => {
//     const result = component.clearconfig(0, 0, 0);
//   }));

//   it("should run #inscfgMain()", async(() => {
//     const result = component.inscfgMain(0, 0);
//   }));

//   it("should run #insconfig()", async(() => {
//     const result = component.insconfig(0, 0, 0);
//   }));

//   it("should run #deleteItemCofirm()", async(() => {
//     const result = component.deleteItemCofirm();
//   }));

//   it("should run #checkCheckBox()", async(() => {
//     const result = component.checkCheckBox();
//   }));

//   it("should run #resetData()", async(() => {
//     //const result = component.resetData();
//   }));

//   it("should run #validatePage()", async(() => {
//     const result = component.validatePage();
//   }));

//   it("should run #go()", async(() => {
//     const result = component.go();
//   }));

//   it("should run #clearDeploySteps()", async(() => {
//     const result = component.clearDeploySteps(0);
//   }));

//   it("should run #clearScriptType()", async(() => {
//     const result = component.clearScriptType(0);
//   }));

//   it("should run #changeRunScript()", async(() => {
//     const result = component.changeRunScript(0, 0);
//   }));

//   it("should run #clearApproval()", async(() => {
//     const result = component.clearApproval(0, 0);
//   }));

//   it("should run #clearRunScriptOnAbort()", async(() => {
//     const result = component.clearRunScriptOnAbort(0, 0);
//   }));

//   it("should run #changeDeploymentOption()", async(() => {
//     const result = component.changeDeploymentOption(0, 0);
//   }));

//   it("should run #changeAbortRunScript()", async(() => {
//     const result = component.changeAbortRunScript(0, 0);
//   }));

//   it("should run #addRunScript()", async(() => {
//     const result = component.addRunScript(0, 0);
//   }));

//   it("should run #clearRunScript()", async(() => {
//     const result = component.clearRunScript(0, 0);
//   }));

//   it("should run #cleartransferFilesFlag()", async(() => {
//     const result = component.cleartransferFilesFlag(0, 0);
//   }));

//   it("should run #removeDeploySteps()", async(() => {
//     const result = component.removeDeploySteps(0);
//   }));

//   it("should run #clearContainerValues()", async(() => {
//     const result = component.clearContainerValues(0, 0);
//   }));

//   it("should run #changeContainerValues()", async(() => {
//     const result = component.changeContainerValues(0, 0);
//   }));

//   it("should run #clearSpringBoot()", async(() => {
//     const result = component.clearSpringBoot(0, 0);
//   }));

//   it("should run #setServerIndex()", async(() => {
//     const result = component.setServerIndex(0);
//   }));

//   it("should run #checkVal()", async(() => {
//     const result = component.checkVal(0, 0);
//   }));

//   it("should run #dockerContainerOff()", async(() => {
//     const result = component.dockerContainerOff(0, 0);
//   }));

//   it("should run #addArtifactField()", async(() => {
//     const result = component.addArtifactField(0, 0);
//   }));

//   it("should run #clearS3Values()", async(() => {
//     const result = component.clearS3Values(0, 0);
//   }));

//   it("should run #checkDis()", async(() => {
//     const result = component.checkDis(0);
//   }));

//   it("should run #addField()", async(() => {
//     const result = component.addField(0, 0);
//   }));

//   it("should run #removeField()", async(() => {
//     const result = component.removeField(0, 0, 0);
//   }));

//   it("should run #confirmRemoveField()", async(() => {
//     const result = component.confirmRemoveField();
//   }));

//   it("should run #removeAllFields()", async(() => {
//     const result = component.removeAllFields(0, 0, 0);
//   }));

//   it("should run #confirmRemoveAllFields()", async(() => {
//     const result = component.confirmRemoveAllFields();
//   }));

//   it("should run #trackById()", async(() => {
//     const result = component.trackById(0, 0);
//   }));

//   it("should run #clearWeblogic()", async(() => {
//     const result = component.clearWeblogic(0, 0);
//   }));

//   it("should run #enableWebLogic()", async(() => {
//     const result = component.enableWebLogic(0, 0);
//   }));

//   it("should run #openAntPropertiesField()", async(() => {
//     const result = component.openAntPropertiesField(0, 0);
//   }));

//   it("should run #addAntProperties()", async(() => {
//     const result = component.addAntProperties(0, 0);
//   }));

//   it("should run #clearAntPropertisField()", async(() => {
//     const result = component.clearAntPropertisField(0, 0);
//   }));

//   it("should run #deleteAntProp()", async(() => {
//     const result = component.deleteAntProp(0, 0, 0);
//   }));

//   it("should run #deleteAntPropConfirm()", async(() => {
//     const result = component.deleteAntPropConfirm();
//   }));

//   it("should run #resetProxyFields()", async(() => {
//     const result = component.resetProxyFields(0, 0);
//   }));

//   it("should run #submitData()", async(() => {
//     const result = component.submitData();
//   }));

//   it("should run #checkMsgSuccess()", async(() => {
//     const result = component.checkMsgSuccess();
//   }));

//   it("should run #checkMsgError()", async(() => {
//     const result = component.checkMsgError();
//   }));

//   it("should run #freezeNavBarsCheck()", async(() => {
//     const result = component.freezeNavBarsCheck();
//   }));

//   it("should run #deploySubscriptionNotSubmitCheck()", async(() => {
//     const result = component.deploySubscriptionNotSubmitCheck();
//   }));

//   it("should run #deploySubscriptionSubmitCheck()", async(() => {
//     const result = component.deploySubscriptionSubmitCheck();
//   }));

//   it("should run #checkLoaderOn()", async(() => {
//     const result = component.checkLoaderOn();
//   }));

//   it("should run #checkLoaderOff()", async(() => {
//     const result = component.checkLoaderOff();
//   }));
// });
