/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.�
*
**/
/* tslint:disable */
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule } from "@angular/forms";
import { BuildIntervalCntrlComponent } from "./build-interval-cntrl.component";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";
import { ParentFormConnectComponent } from "../parent-form-connect/parent-form-connect.component";
import { TranslateModule , TranslateService, TranslateLoader, TranslateParser} from "@ngx-translate/core";
import { NO_ERRORS_SCHEMA}  from "@angular/core";
import { IDPEncryption } from "../idpencryption.service";
import { IdpSubmitService } from "../idpsubmit.service";
import { ViewChild } from "@angular/core";
import {  HttpModule,  XHRBackend,  ResponseOptions,  Response,  RequestMethod} from "@angular/http";
import {
    MockBackend,
    MockConnection
  } from "@angular/http/testing";
describe("BuildIntervalCntrlComponent", () => {
  let component: BuildIntervalCntrlComponent;
  let fixture: ComponentFixture<BuildIntervalCntrlComponent>;
  let idpService: IdpService;
  let idpdataService: IdpdataService;
  let idprestapiService: IdprestapiService;
  let idpEncryption: IDPEncryption;
  let router: Router;
  let idpsubmitService: IdpSubmitService;
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
        "applicationTeam": "",
        "emailIds": ""
      },
      "applicationName": "TestMaven",
      "buildInterval": {
        "buildInterval": "",
        "buildIntervalValue": 0,
        "pollSCM": "off"
      },
      "buildServerOS": "windows",
      "engine": "",
      "pipelineName": "pipName"
    },
    "code": {
      "category": "standard",
      "technology": "",
      "scm": [],
      "buildScript":  [{"tool": "", "antPropertiesArr": []}, {"tool": "", "antPropertiesArr": []}, {}]
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
triggerJobData= {"applicationName": "TestMaven", "pipelineName": "pipName", "idpUserName": "user"};
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
 data: any =  JSON.parse(JSON.stringify(this.template));

  }

  const mockResponsePipDetail = {
    resource: "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"
  };
  class  IdpServiceStub {
     copy(o) {
    let newO, i;

    if (typeof o !== "object") {
      return o;
    }
    if (!o) {
      return o;
    }

    if ("[object Array]" === Object.prototype.toString.apply(o)) {
      newO = [];
      for (i = 0; i < o.length; i += 1) {
        newO[i] = this.copy(o[i]);
      }
      return newO;
    }

    newO = {};
    for (i in o) {
      if (o.hasOwnProperty(i)) {
        newO[i] = this.copy(o[i]);
      }
    }
    return newO;
  }
  }
  class  IdprestapiServiceStub {
    getIDPDropdownProperties() {

      const msBuildVersionObj = {
        msBuildVersion: ""
      };

      return msBuildVersionObj;

    }
    getPipelineDetails() {
      return Promise.resolve(new Response(
        new ResponseOptions({body: mockResponsePipDetail, "status": 1})
  ));
    }
  }
class  IDPEncryptionStub {
  }
  class IdpSubmitServiceStub {}
  class RouterStub {
    navigate(commands: any[], extras?: NavigationExtras) { }
  }
    const idpdataserviceStub: IdpdataServiceStub = new IdpdataServiceStub();
    const routerStub: RouterStub = new RouterStub();
    const idpServiceStub: IdpServiceStub = new IdpServiceStub();
    const idprestapiServiceStub: IdprestapiServiceStub = new IdprestapiServiceStub();
  const idpEncryptionStub: IDPEncryptionStub = new IDPEncryptionStub();
  const idpSubmitServiceStub: IdpSubmitServiceStub = new IdpSubmitServiceStub();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BuildIntervalCntrlComponent, ParentFormConnectComponent ],
      imports: [FormsModule, TranslateModule],
      providers: [{provide: IdprestapiService, useValue: idprestapiServiceStub},
                    {provide: IdpService, useValue: idpServiceStub},
                    {provide: IdpdataService, useValue: idpdataserviceStub},
          {provide: IDPEncryption, useValue: idpEncryptionStub},
          {provide: IdpSubmitService, useValue: idpSubmitServiceStub},
          {
            provide: XHRBackend,
            useClass: MockBackend
          },
                {provide: Router, useValue: routerStub},
                TranslateService, TranslateLoader, TranslateParser
                ],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  }));

  beforeEach(() => {
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 1000000;
    router = TestBed.get(Router);
    idpService = TestBed.get(IdpService);
    idpdataService = TestBed.get(IdpdataService);
  idprestapiService = TestBed.get(IdprestapiService);
  idpsubmitService = TestBed.get(IdpSubmitService);
    idpEncryption = TestBed.get(IDPEncryption);
    fixture = TestBed.createComponent(BuildIntervalCntrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

  });

  it("should create", () => {
    component.IdpdataService.triggerJobData = {"applicationName": "TestMaven", "pipelineName": "pipName", "idpUserName": "user"};
    expect(component).toBeTruthy();
  });
  it("should create", () => {
    component.submit();
  });
  it("should create", () => {
    component.totalSubmit();
  });
  it("should create", () => {
    component.checkDetailsSingle(0);
  });
  it("should create", () => {
    component.checkDetailsFilled();
  });
  it("should create", () => {
    component.setIndex(0);
  });
  it("should create", () => {
    component.deleteDetails(0);
  });
  it("should create", () => {
    component.deleteBuildInterval(0);
  });

});
