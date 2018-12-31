/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
/*tslint:disable */
/*import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule } from "@angular/forms";
import { AngularCntrlComponent } from "./angular-cntrl.component";
import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { IdprestapiService } from "../../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";


describe("AngularCntrlComponent", () => {
  let component: AngularCntrlComponent;
  let fixture: ComponentFixture<AngularCntrlComponent>;

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
    initMain = false;
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
        declarations: [AngularCntrlComponent, ParentFormConnectComponent],

        imports: [FormsModule],
        providers: [{ provide: IdprestapiService, useValue: idprestapiServiceStub },
        { provide: IdpService, useValue: idpServiceStub },
        { provide: IdpdataService, useValue: idpdataserviceStub },
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
    fixture = TestBed.createComponent(AngularCntrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("code analysis off", () => {
    component.buildInfo.modules[0].codeAnalysis[0] = "off";
    component.buildInfo.modules[0].codeAnalysis[1] = "off";
    component.codeAnalysisCheck();
    expect(component.IdpdataService.pa).toBeFalsy();
  });

  it("code analysis on", () => {
    component.buildInfo.modules[0].codeAnalysis[0] = "on";
    component.buildInfo.modules[0].codeAnalysis[1] = "off";
    component.codeAnalysisCheck();
    expect(component.IdpdataService.pa).toBeTruthy();
  });
  it("jsonPathEmpty", () => {
    component.buildInfo.modules.push({ jsonPath: "" });
    const a = component.jsonPathEmpty();
    expect(component.buildInfo.modules[0].jsonPath).toBe("");
    expect(a).toBeFalsy();
  });
  it("compileOff", () => {
    component.buildInfo.modules.push({ codeAnalysis: "" });
    component.tempObject.modules.push({ jsonPath: "" });
    const a = component.compileOff();
    expect(component.buildInfo.modules[0].codeAnalysis[1]).toBe("off");
    expect(component.tempObject.modules[0].build).toBe("off");
    expect(component.tempObject.modules[0].codeAnalysis).toBe("off");
    expect(component.buildInfo.modules[0].noViolations).toBe("off");
    expect(component.buildInfo.modules[0].allUnitTestPass).toBe("off");
    expect(a).toBeFalsy();
  });
  it("buildOn", () => {
    component.tempObject.modules.push({ build: "" });
    const a = component.buildOn();
    expect(component.tempObject.modules[0].build).toBe("on");
    expect(a).toBeFalsy();
  });
  it("compMoveTrue", () => {
    const a = component.compMoveTrue();
    expect(component.IdpdataService.compMove).toBe(true);
    expect(a).toBeFalsy();
  });
  it("compMoveFalse", () => {
    const a = component.compMoveFalse();
    expect(component.IdpdataService.compMove).toBe(false);
    expect(a).toBeFalsy();
  });
  it("buildValueEmpty", () => {
    component.buildInfo.modules.push({ buildValue: "" });
    const a = component.buildValueEmpty();
    expect(component.buildInfo.modules[0].buildValue).toBe("");
    expect(a).toBe("off");
  });
  it("noViolationsOff", () => {
    component.buildInfo.modules.push({ noViolations: "" });
    const a = component.noViolationsOff();
    expect(component.buildInfo.modules[0].noViolations).toBe("off");
    expect(a).toBeFalsy();
  });
  it("codeAnalysisOff", () => {
    component.buildInfo.modules.push({ noViolations: "" });
    const a = component.codeAnalysisOff();
    expect(component.buildInfo.modules[0].noViolations).toBe("off");
    expect(component.IdpdataService.pa).toBe(true);
    expect(a).toBeFalsy();
  });
  it("paTrue", () => {
    const a = component.paTrue();
    expect(component.IdpdataService.pa).toBe(true);
    expect(a).toBeFalsy();
  });

  it("paFalse", () => {
    const a = component.paFalse();
    expect(component.IdpdataService.pa).toBe(false);
    expect(a).toBeFalsy();
  });
  it("unitTestingOff", () => {
    const a = component.unitTestingOff();
    expect(component.buildInfo.modules[0].unitTesting).toBe("off");
    expect(a).toBeFalsy();
  });
  it("unitTestToolOff", () => {
    const a = component.unitTestToolOff();
    expect(component.buildInfo.modules[0].unitTestTool[0]).toBe("off");
    expect(component.buildInfo.modules[0].codeCoverage).toBe("off");
    expect(component.buildInfo.modules[0].codeCoverageTool[0]).toBe("off");
    expect(a).toBe("off");
  });
  it("allUnitTestPassOff", () => {
    const a = component.allUnitTestPassOff();
    expect(component.buildInfo.modules[0].allUnitTestPass).toBe("off");
    expect(a).toBeFalsy();
  });
  it("unitTestProjectNameEmpty", () => {
    const a = component.unitTestProjectNameEmpty();
    expect(component.buildInfo.modules[0].unitTestProjectName).toBe("");
    expect(a).toBe("off");
  });
  it("unitTrue", () => {
    const a = component.unitTrue();
    expect(component.IdpdataService.unit).toBe(true);
    expect(a).toBeFalsy();
  });
  it("unitFalse", () => {
    const a = component.unitFalse();
    expect(component.IdpdataService.unit).toBe(false);
    expect(a).toBeFalsy();
  });
  it("codeCoverageToolOff", () => {
    const a = component.codeCoverageToolOff();
    expect(component.buildInfo.modules[0].codeCoverageTool[0]).toBe("off");
    expect(a).toBe("off");
  });
  it("codeTrue", () => {
    const a = component.codeTrue();
    expect(component.IdpdataService.code).toBe(true);
    expect(a).toBeFalsy();
  });
  it("codeFalse", () => {
    const a = component.codeFalse();
    expect(component.IdpdataService.code).toBe(false);
    expect(a).toBeFalsy();
  });
  it("codeCoverageOff", () => {
    const a = component.codeCoverageOff();
    expect(component.buildInfo.modules[0].codeCoverage).toBe("off");
    expect(component.buildInfo.modules[0].codeCoverageTool[0]).toBe("off");
    expect(a).toBeFalsy();
  });
  it("clearAll", () => {
    const a = component.clearAll();
    expect(component.tempObject.modules[0].build).toBe("off");
    expect(component.buildInfo.modules[0].buildValue).toBe("");
    expect(component.tempObject.modules[0].codeAnalysis).toBe("off");
    expect(component.buildInfo.modules[0].codeAnalysis[0]).toBe("off");
    expect(component.buildInfo.modules[0].noViolations).toBe("off");
    expect(component.buildInfo.modules[0].unitTesting).toBe("off");
    expect(component.buildInfo.modules[0].unitTestTool[0]).toBe("off");
    expect(component.buildInfo.modules[0].codeCoverage).toBe("off");
    expect(component.buildInfo.modules[0].codeCoverageTool[0]).toBe("off");
    expect(component.buildInfo.modules[0].jsonPath).toBe("");
    expect(component.buildInfo.modules[0].npmProxyUserName).toBe("");
    expect(component.buildInfo.modules[0].npmProxyPassword).toBe("");
    expect(component.buildInfo.modules[0].npmProxy).toBe("");

    expect(a).toBe("off");
  });
  it("codeAnalysisCheckbox", () => {
    component.tempObject.modules[0].codeAnalysis = "on";
    component.buildInfo.modules[0].codeAnalysis[0] = "off";
    component.codeAnalysisCheckbox();
    expect(component.IdpdataService.pa).toBeFalsy();
  });
  it("checkCompile", () => {
    component.tempObject.modules[0].build = "on";
    component.buildInfo.modules[0].buildValue = "";
    component.checkCompile();
    expect(component.IdpdataService.compMove).toBeFalsy();
  });
  it("checkCheckBox", () => {
    component.tempObject.modules = undefined;

    component.checkCheckBox();
    expect(component.tempObject.modules).toBe([]);
  });
});
*/