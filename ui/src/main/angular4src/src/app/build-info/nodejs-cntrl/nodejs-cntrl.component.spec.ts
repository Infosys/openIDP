/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
// tslint:disable
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { NodejsCntrlComponent } from "./nodejs-cntrl.component";
import { FormsModule } from "@angular/forms";
import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { IdprestapiService } from "../../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";


describe("NodejsCntrlComponent", () => {
  let component: NodejsCntrlComponent;
  let fixture: ComponentFixture<NodejsCntrlComponent>;
  let idpService: IdpService;
  let idpdataService: IdpdataService;
  let idprestapiService: IdprestapiService;
  let router: Router;

  class  IdpdataServiceStub {


    constructor() {}
  template: any = {
    "grantAccess": {
        "applicationName": "",
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
        "applicationName": "",
        "buildInterval": {
        "buildInterval": "",
        "buildIntervalValue": 0,
        "pollSCM": "off"
        },
        "buildServerOS": "",
        "engine": "",
        "pipelineName": ""
    },
    "code": {
        "category": "",
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
        "deployEnv": []
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
    "masterJson": {}
  };
  data: any =  JSON.parse(JSON.stringify(this.template));


  language = "english";
  idpUserName = "";
  roles = [];
  access_token: any;
  permissions = [];
  createAppflag = false;
  createPipelineflag = false;
  copyPipelineflag = false;
  editPipelineflag = false;
  deletePipelineflag = false;
  test = false;
  devServerURL: any= "";
  IDPDashboardURL = "";
  IDPLink = "";
  geUrl = "";
  role = "";
  IDPDropdownProperties: any = {};
  showConfig: any;
  pa= true;
  continuecontrol: any;
  geFlag: any;
  p: any= false;
  ejbVal: any;
  warVal: any;
  jarVal: any;
  pipelineData: any;
  triggerJobData: any;
  application: any;
  freezeNavBars= false;
  osFlag: any;
  op: any;
  operation: any;
  initMain: any= false;
  RestApiDetails: any= false;
  buildInfoReset= false;
  compMove: any;
  unit: any;
  code: any;
  refreshBuild= false;
  }
  class  IdpServiceStub {

  }
  class  IdprestapiServiceStub {

  }

  class RouterStub {
    navigate(commands: any[], extras?: NavigationExtras) { }
  }

  const idpdataserviceStub: IdpdataServiceStub = new IdpdataServiceStub();
    const routerStub: RouterStub = new RouterStub();
    const idpServiceStub: IdpServiceStub = new IdpServiceStub();
    const idprestapiServiceStub: IdprestapiServiceStub = new IdprestapiServiceStub();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ NodejsCntrlComponent, ParentFormConnectComponent ],
        imports: [FormsModule],
        providers: [{provide: IdprestapiService, useValue: idprestapiServiceStub},
                      {provide: IdpService, useValue: idpServiceStub},
                      {provide: IdpdataService, useValue: idpdataserviceStub},
                    {provide: Router, useValue: routerStub}
                    ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    router = TestBed.get(Router);
    idpService = TestBed.get(IdpService);
      idpdataService = TestBed.get(IdpdataService);
      idprestapiService = TestBed.get(IdprestapiService);
    fixture = TestBed.createComponent(NodejsCntrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("code analysis off", () => {
    component.buildInfo.modules[0].codeAnalysis[0] = "off";
      component.buildInfo.modules[0].codeAnalysis[1] = "off";
    component.codeAnalysisCheck();
    expect(component.IdpdataService.pa).toBeFalsy();
  });

  it("code analysis on A ", () => {
    component.buildInfo.modules[0].codeAnalysis[0] = "on";
      component.buildInfo.modules[0].codeAnalysis[1] = "off";
    component.codeAnalysisCheck();
    expect(component.IdpdataService.pa).toBeTruthy();
  });
  it("code analysis on B", () => {
    component.buildInfo.modules[0].codeAnalysis[0] = "on";
      component.buildInfo.modules[0].codeAnalysis[1] = "on";
    component.codeAnalysisCheck();
    expect(component.IdpdataService.pa).toBeTruthy();
  });
  it("code analysis on C", () => {
    component.buildInfo.modules[0].codeAnalysis[0] = "off";
      component.buildInfo.modules[0].codeAnalysis[1] = "on";
    component.codeAnalysisCheck();
    expect(component.IdpdataService.pa).toBeTruthy();
  });
  it("code analysis on D", () => {
    component.tempObject.modules[0].codeAnalysis = "on";
    component.buildInfo.modules[0].codeAnalysis[0] = "off";
      component.buildInfo.modules[0].codeAnalysis[1] = "off";
    component.codeAnalysisCheckbox();
    expect(component.IdpdataService.pa).toBeFalsy();
  });
  it("code analysis on E", () => {
    component.tempObject.modules[0].codeAnalysis = "off";
    component.buildInfo.modules[0].codeAnalysis[0] = "off";
      component.buildInfo.modules[0].codeAnalysis[1] = "off";
    component.codeAnalysisCheckbox();
    expect(component.IdpdataService.pa).toBeTruthy();
  });
  it("code analysis on F", () => {
    component.tempObject.modules[0].codeAnalysis = "on";
    component.buildInfo.modules[0].codeAnalysis[0] = "off";
    component.buildInfo.modules[0].codeAnalysis[1] = "on";
    component.codeAnalysisCheckbox();
    expect(component.IdpdataService.pa).toBeTruthy();
  });

  it("ExcludeToDirectories", () => {
    component.tempObject.modules[0].codeAnalysis = "on";
    component.buildInfo.modules[0].codeAnalysis = ["off", "off"];
    component.checkCodeAOn();
    expect(component.tempObject.modules[0].excludeDirToScan).toEqual("off");
  });

  it("clearAll", () => {
    component.tempObject.modules[0].codeAnalysis = "on";
    component.buildInfo.modules[0].codeAnalysis = ["off", "off"];
    component.checkCodeAOn();
    expect(component.tempObject.modules[0].excludeDirToScan).toEqual("off");
  });

  xit("ExcludeToDirectories", () => {
    component.buildInfo.modules[0].npmProxyUserName = "";
    component.buildInfo.modules[0].npmProxyPassword = "";
    component.buildInfo.modules[0].npmProxy = "";
    component.checkCodeAOn();
    expect(component.clearAll()).toEqual("off");
  });

  xit("closeCompile", () => {
    component.buildInfo.modules[0].jsonPath = "";
    component.buildInfo.modules[0].codeFormatting = "off";
    component.tempObject.modules[0].checkRunApp = "off";
    component.buildInfo.modules[0].customScript = "";
    component.buildInfo.modules[0].unitTesting = "off";
    component.buildInfo.modules[0].unitTestProjectName = "";
    component.buildInfo.modules[0].codeCoverage = "off";
    component.buildInfo.modules[0].codeAnalysis.length = 1;
    component.buildInfo.modules[0].codeAnalysis[1] = "off";
    expect(component.closeCompile()).toEqual("off");
  });

  xit("closeCodeAnalysis", () => {
    component.buildInfo.modules[0].codeAnalysis = [];
    component.buildInfo.modules[0].excludeFolders = "";
    component.tempObject.modules[0].excludeDirToScan = "off";
    expect(component.closeCodeAnalysis()).toEqual("off");
  });

  xit("closeCheckRun", () => {
    component.buildInfo.modules[0].customScript = "";
    expect(component.closeCheckRun()).toEqual("off");
  });

  xit("closeUnitTest", () => {
    component.buildInfo.modules[0].unitTestProjectName = "";
    component.buildInfo.modules[0].codeCoverage = "off";
    expect(component.closeUnitTest()).toEqual("off");
  });

  xit("installGruntOn", () => {
    component.IdpdataService.osFlag === true;
    component.buildInfo.modules[0].installGrunt = "on";
    expect(component.installGruntOn()).toBeFalsy();
  });

});
