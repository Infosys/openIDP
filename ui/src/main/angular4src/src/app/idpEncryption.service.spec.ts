/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
// tslint:disable
import { async, ComponentFixture, TestBed, inject } from "@angular/core/testing";
import { FormsModule } from "@angular/forms";
import { IdpService } from "./idp-service.service";
import { IdpdataService } from "./idpdata.service";
import { IdprestapiService } from "./idprestapi.service";
import { Router, NavigationExtras, ActivatedRoute} from "@angular/router";
import {  HttpModule,  XHRBackend,  ResponseOptions,  Response,  RequestMethod} from "@angular/http";
import { TranslateModule , TranslateService, TranslateLoader, TranslateParser} from "ng2-translate";
import { NO_ERRORS_SCHEMA}  from "@angular/core";
import { IDPEncryption } from "./idpencryption.service";
import { IdpSubmitService } from "./idpsubmit.service";
import { CookieService } from "ngx-cookie";
import { Observable } from "rxjs/Rx";
import {SubscriptionService} from "./subscription.service";
import * as CryptoJS from "crypto-js";

import {
  MockBackend,
  MockConnection
} from "@angular/http/testing";
import { Subscription } from "rxjs/Subscription";



describe("Service:IDPEncryption", () => {
  let service;
  let idpService: IdpService;
  let idpdataService: IdpdataService;
  let idprestapiService: IdprestapiService;
  let idpEncryption: IDPEncryption;
  let router: Router;
  let idpsubmitService: IdpSubmitService;
  let subscriptionService: SubscriptionService;
  let cookieService: CookieService;
  class  IdpdataServiceStub {


    constructor() {}
  template: any = {
    "grantAccess": {
      "applicationName": "TestMaven",
      "developers": [],
      "pipelineAdmins": [],
      "releaseManager": [],
      "environmentOwnerDetails": [{
        "environmentName": "",
        "environmentOwners": [],
        "dbOwners": []
      }],
      "slaveDetails": [
        {
          "slaveName": "",
          "buildServerOS": "",
          "workspacePath": "",
          "createNewSlave": "",
          "labels": "",
          "sshKeyPath": "",
          "slaveUsage": "both"
        }
      ]
    },
    "basicInfo": {
      "additionalMailRecipients": {
        "applicationTeam": "TestMaven",
        "emailIds": ""
      },
      "applicationName": "TestMaven",
      "buildInterval": {
        "buildInterval": "",
        "buildIntervalValue": 0,
        "pollSCM": "off",
        "event": [{"type": "--Select--"}]
      },
      "buildServerOS": "windows",
      "engine": "",
      "pipelineName": ""
    },
    "code": {
      "category": "standard",
      "technology": "",
      "scm": [],
      "buildScript":  [{"tool": ""}, {"tool": ""}, {}]
    },
    "buildInfo": {
      "buildtool": "",
      "castAnalysis": {},
      "artifactToStage": {},
      "modules": []
    },
    "deployInfo": {
      "deployEnv": [{"deploySteps": []}]
    },
    "testInfo": {
      "testEnv": []
    },
    "formStatus": {
      "basicInfo": {
        "appNameStatus": "0",
        "formStatus": "0"
      },
      "codeInfo": "",
      "buildInfo": {
        "buildToolStatus": "0",
        "formStatus": "0",
        "ibmsiTypeStatus": "0"
      },
      "deployInfo": "",
      "testInfo": "",
      "operation": ""
    },
    "checkboxStatus": {
      "basicInfo": {},
      "codeInfo": {},
      "buildInfo": {},
      "deployInfo": {},
      "testInfo": {},
      "others": {}
    },
    "backUp": {
      "deployInfo": {},
      "testInfo": {}
    },
    "allFormStatus": {"deployInfo": {}},
    "masterJson": {},
  };
  data: any =  JSON.parse(JSON.stringify(this.template));
  releaseManagerTemplate: any  = {
    "applicationName": "",
"releasePipeline": [
  {
    "pipelineName": "",
    "release": [
      {
        "actualEndDate": "",
        "actualStartDate": "",
        "additionalMailRecipients": {
          "applicationTeam": "",
          "emailIds": ""
        },
        "branchList": [
          "na"
        ],
        "expectedEndDate": "",
        "expectedStartDate": "",
        "releaseNumber": "",
        "remarks": "",
        "status": "on",
        "vstsReleaseName": ""
      }
    ]
  }
]
};

passwordEncryptionList: any = {
  "code.scm": ["password", "PSpassword"],
  "code.buildScript": ["password"],
  "buildInfo": ["password", "artifactToStage.artifactRepo.repoPassword", "postBuildScript.password"],
  "buildInfo.modules": ["npmProxyPassword", "password", "pegaPassword", "destPassword", "siebelPassword", "ipcPassword",
   "servPass", "publishForms.password", "publishForms.dbPassword",
  "workFlowPublish.password", "workFlowPublish.dbPassword", "proxy.password", "sourcePassword"],

  "deployInfo.deployEnv.deploySteps": ["password", "ipcPassword", "dbPassword", "dbpasswordOTM",
  "dbPasswordOTM", "dbOwnerPassword", "bizPassword", "formsDbPass", "databasePassword", "ddltmpPassword",
  "datExportPassword", "workFlowDbPass", "deployPassword", "scalaPassword", "pigPassword", "hivePassword", "dbPwd", "staticPassword",
  "srfPassword", "admPassword", "adminPassword", "dbOwnerPassword", "appsPass", "tomPwd",
  "runScript.password", "deployToContainer.password", "deployToContainer.adminPassword",
  "deployToContainer.sshPassword", "deployToContainer.dbOwnerPassword",
  "deployToContainer.staticFiles.password", "deployDatabase.restorpassword",
  "deployDatabase.dbpassword", "targetPassword", "proxy.password"],

  "testInfo.testEnv.testSteps": ["runScript.password", "test.password"],

  "virtualServiceServerDetails": ["password"]
};




propertySCM: any = {

};
pipelineListRm= "";
releaseManagerData= JSON.parse(JSON.stringify(this.releaseManagerTemplate));
language = "english";
idpUserName = "";
roles = [];
azureadflag= false;
expireTime: any;
access_token: any;
permissions = [];
createAppflag = false;
createOrganisationflag = false;
createLicenseflag = false;
createPipelineflag = false;
copyPipelineflag = false;
editPipelineflag = false;
deletePipelineflag = false;
test = false;
loadReleasePage= false;
devServerURL: any = "";
subscriptionServerURL: any = "";
IDPDashboardURL = "";
IDPLink = "";
geUrl = "";
role = "";
profile = "";
pausedBuildsData: any= {};
checkPausedBuilds: any= false;
allFormStatus: any= {
  "basicInfo": false,
  "codeInfo": false,
  "buildInfo": false,
  "deployInfo": false,
  "testInfo": false,
  "workflowInfo": false
};
IDPDropdownProperties: any = {};
showConfig: any;
pa= true;
continuecontrol: any;
geFlag: any;
p: any = false;
ejbVal: any;
warVal: any;
jarVal: any;
cloudDeployURL: any;
pipelineData: any;
triggerJobData: any;
jobParamList: any;
application: any;
freezeNavBars= false;
osFlag: any;
op: any;
operation: any;
initMain: any = false;
RestApiDetails: any = false;
buildInfoReset = false;
compMove: any;
unit: any;
uName: any= "";
pass: any= "";
code: any;
serverUrl= "";


authorization= "";
unitTest: any= false;
artifactVariable: any= false;
artifactAppVariable: any= false;
public loading =  false;
refreshBuild= false;
 showRelease= false;
isSAPApplication= false;
checkpollALM: boolean ;
SAPScmCheck: any ;
authmode= "ldap";
adalConfig: any=
  {"clientId": "",
  "tenate": "",
  "postLogoutRedirectUri": "",
  "endpoints": {},
};
pipelineName= "";
appName= "";
pipelineNames: any;
releaseAddSuccess: boolean;
releaseUpdateSuccess: boolean;
releasePipelineName: "";
activeReleasePipelineName: "";
noPipelines: boolean;
hideDashboard: boolean;
sapNewFlag= false;
 buildSubscription= false;
deploySubscription= false;
testSubscription= false;
buildSubscriptionSubmit= false;
deploySubscriptionSubmit= false;
noAccess: boolean;
showService: boolean;
flag: boolean;
noAccessNavBars: boolean;
scheduleJob: any;
schedulePage: any= false;
index: any;
buildIntervalData: any= [];
statusCheck: any= [];
workflowData: any = [];
workflowDataTemp: any = [];
createWorkflowSequenceflag= false;
workflowTrigger= false;
triggerWorkflowJobData: any;
approveBuildFlag: any= false;
approveDeployFlag: any= false;
keycloakToken: any;
organization: any;
keycloakUrl: any;
keycloakRealm: any;
keycloakClientId: any;

hideApp= false;

PagePersmission: any= {
  "basic" : false,
  "code" : false,
  "build" : false,
  "deploy": false,
  "test": false
 };

  }



  class  IdprestapiServiceStub {
    getIDPDropdownProperties() {

      const msBuildVersionObj = {
        msBuildVersion: ""
      };

      return msBuildVersionObj;

    }

  }
  //spyOn(idprestapiServiceStub, 'getData').and.returnValue(Promise.resolve(promisedData));



const encryptdecryptdata = {"basicInfo": {"applicationName": "5thFebApp_JMeter", "pipelineName": "fggfgfgf", "masterSequence": "pipeline", "buildServerOS": "windows", "engine": "Jenkins Workflow", "buildInterval": {"pollSCM": "off", "buildInterval": "", "buildIntervalValue": "0", "buildAtSpecificInterval": "off", "event": [{"type": "--Select--", "hour": "00", "minute": "00"}]}, "additionalMailRecipients": {"applicationTeam": "", "emailIds": ""}, "userName": "userName", "pipelineStatus": "create"}, "code": {"category": "Standard", "technology": "dotNetCsharp", "scm": [{"type": "svn", "url": "http://hkgkgjk", "userName": "jkgjkgjk", "password": "gjkgjk", "repositoryBrowser": "", "browserUrl": "", "projectName": "", "branch": "", "projPath": "", "moduleName": "", "clearcaseType": "", "vobName": "", "snapshotViewName": "", "configSpec": "", "developmentStreamName": "", "buildConfiguration": "", "buildDefinition": "", "repositoryWorkspace": "", "projArea": "", "port": "", "version": "", "exclude": "", "proxy": "", "proxyPort": "", "appRepo": "on", "deployRepo": "", "testRepo": ""}], "jobParam": [], "buildScript": [{"tool": ""}, {"tool": ""}, {}], "subApplication": ""}, "buildInfo": {"buildtool": "msBuild", "artifactToStage": {"artifactRepo": {}, "artifactRepoName": "na"}, "castAnalysis": {}, "modules": [{"version": "(Default)", "moduleName": "ererer", "relativePath": "eererer", "codeAnalysis": [], "args": "ererererer", "ossDistributionType": ""}], "postBuildScript": {"dependentPipelineList": []}}, "deployInfo": {"deployEnv": [{"envName": "dev", "envFlag": "on", "scriptType": "", "deploySteps": [{"stepName": "fgfggf", "deployOS": "", "runScript": {"scriptType": "ant", "scriptFilePath": "gfgf.xml", "targets": "gfgfg", "host": "", "userName": "", "password": "", "script": "", "pathToFiles": "", "destinationDir": "", "flattenFilePath": "off"}, "deployToContainer": {"containerName": "iis", "serverManagerURL": "", "resourceToBeDeployed": "", "warPath": "", "contextPath": "\\fdfsdfdf", "userName": "fddfdf", "password": "dffd", "targetCellName": "", "targetNodeName": "", "targetServerName": "dfdf", "hostName": "", "port": "", "updateDB": "", "rollbackStrategy": "", "narOS": "", "deployedFolder": "", "fileName": "", "pairName": "", "srcEnvName": ""}, "deployDatabase": {"restorusername": "", "restorpassword": "", "dbusername": "", "dbpassword": "", "script": ""}, "abortScript": {"scriptType": ""}, "deploymentOption": "", "deployOperation": "", "proxy": {"username": "", "password": "", "host": "", "port": "", "enabled": ""}}]}]}, "testInfo": {"testEnv": [{"envName": "dev", "envFlag": "on", "testSteps": [{"stepName": "dfdffd", "runScript": {"scriptType": "", "reportType": ""}, "test": {"testCategory": "functional", "testTypeName": "rft", "projectName": "fddfsdfs", "frameWork": "", "testCase": "fddffd", "testPlan": "", "folderUrl": "", "userName": "", "password": "", "testSuiteName": "", "projectLocation": "", "serverUrl": "", "domain": "", "serviceName": "", "path": "", "authenticationCode": "", "timeout": 60, "serverName": "", "iteration": 2, "browserName": "", "rootDir": "", "version": "", "externalFilePath": "", "parameters": "", "scriptPath": "", "targets": "", "arg": "", "buildDefId": ""}}]}]}};
class SubscriptionServiceStub {

}
  class IdpSubmitServiceStub {}
  class RouterStub {
    navigate(commands: any[], extras?: NavigationExtras) { }
  }
  const fakeActivatedRoute = {
    snapshot: { data: {} }
  } as ActivatedRoute;

  class CookieServiceStub {}
    const idpdataserviceStub: IdpdataServiceStub = new IdpdataServiceStub();
    const routerStub: RouterStub = new RouterStub();
    const idprestapiServiceStub: IdprestapiServiceStub = new IdprestapiServiceStub();
  const idpSubmitServiceStub: IdpSubmitServiceStub = new IdpSubmitServiceStub();
  const cookieServiceStub: CookieServiceStub = new CookieServiceStub();
  const subscriptionServiceStub: SubscriptionServiceStub = new SubscriptionServiceStub();
    beforeEach(async(() => {
    TestBed.configureTestingModule({

      imports: [FormsModule, TranslateModule],
      providers: [{provide: IdprestapiService, useValue: idprestapiServiceStub},
                    {provide: IdpdataService, useValue: idpdataserviceStub},
          {provide: IdpSubmitService, useValue: idpSubmitServiceStub},
          {provide: SubscriptionService, useValue: subscriptionServiceStub},
          {provide: CookieService, useValue: CookieServiceStub},
          {
            provide: XHRBackend,
            useClass: MockBackend
          },
          IdpService, IDPEncryption,

                {provide: Router, useValue: routerStub},
                {provide: ActivatedRoute, useValue: { "params": Observable.from([{ "id": 1 }]) }},
                TranslateService, TranslateLoader, TranslateParser
                ],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  }));

  beforeEach(() => {
  service = new IDPEncryption(idpdataService);
    router = TestBed.get(Router);
    idpService = TestBed.get(IdpService);
    idpdataService = TestBed.get(IdpdataService);
  idprestapiService = TestBed.get(IdprestapiService);


    idpsubmitService = TestBed.get(IdpSubmitService);
    idpEncryption = TestBed.get(IDPEncryption);
  });
  it("should be created", inject([IdpService], (service: IdpService) => {
    expect(service).toBeTruthy();
  }));
   it("should be encrypt", () => {
    const result = idpEncryption.encryptAES("demo");
   });
  it("should decrypt", () => {
    const data = "0bWRVHtBODrmpi8/RsFVO/XpFgjoa/sie71x3S2ewRrTfgwVB5UxnPB4xVZKLw6p";

     const result = idpEncryption.decryptAES(data);
   });
   it("should doubledecrypt", () => {
     const result = idpEncryption.doubleDecryptPassword(encryptdecryptdata);
   });
   it("should doubledecrypt", () => {
    const result = idpEncryption.doubleEncryptPassword(encryptdecryptdata);
  });

});
