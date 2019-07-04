/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
// tslint:disable
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";
import { PythonCntrlComponent } from "./python-cntrl.component";
import { FormsModule } from "@angular/forms";

import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { IdprestapiService } from "../../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";
import { TranslateModule, TranslateService, TranslateLoader, TranslateParser } from "@ngx-translate/core";



describe("PythonCntrlComponent", () => {
  let component: PythonCntrlComponent;
  let fixture: ComponentFixture<PythonCntrlComponent>;

  let idpService: IdpService;
  let idpdataService: IdpdataService;
  let idprestapiService: IdprestapiService;
  let router: Router;
  class IdpdataServiceStub {


    constructor() { }
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
        "buildScript": [{ "tool": "" }, { "tool": "" }, {}]
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
    data: any = JSON.parse(JSON.stringify(this.template));


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
    devServerURL: any = "";
    IDPDashboardURL = "";
    IDPLink = "";
    geUrl = "";
    role = "";
    IDPDropdownProperties: any = {};
    showConfig: any;
    pa = true;
    continuecontrol: any;
    geFlag: any;
    p: any = false;
    ejbVal: any;
    warVal: any;
    jarVal: any;
    pipelineData: any;
    triggerJobData: any;
    application: any;
    freezeNavBars = false;
    osFlag: any;
    op: any;
    operation: any;
    initMain: any = false;
    RestApiDetails: any = false;
    buildInfoReset = false;
    compMove: any;
    unit: any;
    code: any;
    refreshBuild = false;
  }
  class IdpServiceStub {

  }
  class IdprestapiServiceStub {

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
        declarations: [PythonCntrlComponent, ParentFormConnectComponent],
        imports: [FormsModule, TranslateModule],
        providers: [{ provide: IdprestapiService, useValue: idprestapiServiceStub },
        { provide: IdpService, useValue: idpServiceStub },
        { provide: IdpdataService, useValue: idpdataserviceStub },
        IdpdataService, TranslateService, TranslateLoader, TranslateParser,
        { provide: Router, useValue: routerStub }
        ]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    router = TestBed.get(Router);
    idpService = TestBed.get(IdpService);
    idpdataService = TestBed.get(IdpdataService);
    idprestapiService = TestBed.get(IdprestapiService);
    fixture = TestBed.createComponent(PythonCntrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("codeAnalysisSonar()", () => {
    component.buildInfo.modules[0].codeAnalysis[0] = "sonar";
    const a = component.codeAnalysisSonar();
    expect(a).toBeFalsy();
  });

  it("codeAnalysisEmptyPaTrue() ", () => {
    const a = component.codeAnalysisEmptyPaTrue();
    expect(component.IdpdataService.pa).toBeTruthy();
    expect(a).toBeFalsy();
  });

  it("codeAnalysisEmptyPaTrue() ", () => {
    const a = component.codeAnalysisEmptyPaTrue();
    expect(component.IdpdataService.pa).toBeTruthy();
    expect(a).toBeFalsy();
  });

  it("unitTestingOn()", () => {
    const a = component.unitTestingOn();
    expect(component.buildInfo.modules[0].unitTesting).toBe("on");
    expect(a).toBeFalsy();
  });

  it("continuecontrolFalse() ", () => {
    const a = component.continuecontrolFalse();
    expect(component.IdpdataService.continuecontrol).toBeFalsy();
    expect(a).toBeFalsy();
  });

  it("continuecontrolTrue() ", () => {
    const a = component.continuecontrolTrue();
    component.IdpdataService.data.checkboxStatus = "on";
    component.IdpdataService.data.checkboxStatus.deployInfo = "on";
    component.IdpdataService.data.checkboxStatus.deployInfo.deployEnv = "on";
    expect(component.IdpdataService.continuecontrol).toBeTruthy();
    expect(a).toBeFalsy();
  });

  it("unitTestingOff() ", () => {
   const a = component.unitTestingOff();
    expect(a).toBeFalsy();
  });


  it("targetEnvironmentUnitTestingOff() ", () => {
    const a = component.targetEnvironmentUnitTestingOff();
    expect(component.buildInfo.modules[0].hostName).toBe("");
    expect(component.buildInfo.modules[0].userName).toBe("");
    expect(component.buildInfo.modules[0].privateKey).toBe("");
    expect(component.buildInfo.modules[0].remoteDir).toBe("");
    expect(a).toBeFalsy();
  });

  it("unitTestOn() ", () => {
    component.IdpdataService.unitTest = true;
    const a = component.unitTestOn();
        expect(a).toEqual("on");
   });


});
