/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
// tslint:disable
import { GoCntrlComponent } from "./go-cntrl.component";
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";
import { FormsModule } from "@angular/forms";

import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { IdprestapiService } from "../../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";
import { TranslateModule, TranslateService, TranslateLoader, TranslateParser } from "ng2-translate";



describe("GoCntrlComponent", () => {
  let component: GoCntrlComponent;
  let fixture: ComponentFixture<GoCntrlComponent>;

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
        declarations: [GoCntrlComponent, ParentFormConnectComponent],
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
    fixture = TestBed.createComponent(GoCntrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("paFalse()", () => {
    const a = component.paFalse();
    expect(idpdataService.pa).toBeFalsy();
    expect(a).toBeFalsy();
  });

  it("paTrue() ", () => {
    const a = component.paTrue();
    expect(idpdataService.pa).toBeTruthy();
    expect(a).toBeFalsy();
  });


  it("codeAnalysisEmpty()", () => {
    const a = component.codeAnalysisEmpty();
    expect(idpdataService.pa).toBeTruthy();
    expect(a).toBe("off");
  });


  it(" codeCoverageOffUnitTestDirEmpty() ", () => {
    const a = component.codeCoverageOffUnitTestDirEmpty();
    expect(component.buildInfo.modules[0].unitTestDir).toBe("");
    expect(component.buildInfo.modules[0].codeCoverage).toBe("off");
    expect(a).toBe("off");
  });

  it("clearProxyDetails() ", () => {
    const a = component.codeCoverageOffUnitTestDirEmpty();
    expect(component.buildInfo.modules[0].npmProxy.toBe(""));
    expect(component.buildInfo.modules[0].npmProxyUserName.toBe(""));
    expect(component.buildInfo.modules[0].npmProxyPassword.toBe(""));
    expect(a).toBe("off");
  });

  it("codeCoverageOff() ", () => {
    const a = component.codeCoverageOff();
    expect(component.buildInfo.modules[0].unitTestDir).toBe("");
    expect(component.buildInfo.modules[0].codeCoverage).toBe("off");
    expect(component.buildInfo.modules[0].unitTesting).toBe("off");
    expect(a).toBe("off");
  });

  it("check() ", () => {
    const a = component.codeCoverageOff();
    expect(component.buildInfo.modules[0].codeAnalysis[1].toBe("sonar"));
    expect(component.buildInfo.modules[0].codeAnalysis[0].toBe("checkStyle"));
  });


});

