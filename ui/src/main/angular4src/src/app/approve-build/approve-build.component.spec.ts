/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
/* tslint:disable */
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule } from "@angular/forms";
import { ApproveBuildComponent } from "./approve-build.component";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";
import { ParentFormConnectComponent } from "../parent-form-connect/parent-form-connect.component";
import { TranslateModule , TranslateService, TranslateLoader, TranslateParser} from "ng2-translate";
import { NO_ERRORS_SCHEMA}  from "@angular/core";
import { IDPEncryption } from "../idpencryption.service";
import { IdpSubmitService } from "../idpsubmit.service";
import { ViewChild } from "@angular/core";

describe("ApproveBuildComponent", () => {
  let component: ApproveBuildComponent;
  let fixture: ComponentFixture<ApproveBuildComponent>;
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
 data= JSON.parse(JSON.stringify(this.template));

  }


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
      declarations: [ ApproveBuildComponent, ParentFormConnectComponent ],
      imports: [FormsModule, TranslateModule],
      providers: [{provide: IdprestapiService, useValue: idprestapiServiceStub},
                    {provide: IdpService, useValue: idpServiceStub},
                    {provide: IdpdataService, useValue: idpdataserviceStub},
          {provide: IDPEncryption, useValue: idpEncryptionStub},
          {provide: IdpSubmitService, useValue: idpSubmitServiceStub},
                {provide: Router, useValue: routerStub},
                TranslateService, TranslateLoader, TranslateParser
                ],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    router = TestBed.get(Router);
    idpService = TestBed.get(IdpService);
    idpdataService = TestBed.get(IdpdataService);
  idprestapiService = TestBed.get(IdprestapiService);
  idpsubmitService = TestBed.get(IdpSubmitService);
    idpEncryption = TestBed.get(IDPEncryption);
    fixture = TestBed.createComponent(ApproveBuildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

  });


  it("should create", () => {
    expect(component).toBeTruthy();
  });
  it("should run", () => {
    component.setBuildNum();
  });
  it("should run", () => {
    component.setDetails("detail");
  });
  it("should run", () => {
    component.submitAppr();
  });
});
