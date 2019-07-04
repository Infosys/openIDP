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
import { AntCtrlComponent } from "./ant-ctrl.component";
import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { IdprestapiService } from "../../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";
import { TranslateModule, TranslateService, TranslateLoader, TranslateParser } from "@ngx-translate/core";

describe("AntCtrlComponent", () => {
  let component: AntCtrlComponent;
  let fixture: ComponentFixture<AntCtrlComponent>;

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
    RestApiDetails = false;
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
        declarations: [AntCtrlComponent, ParentFormConnectComponent],

        imports: [FormsModule, TranslateModule],
        providers: [{ provide: IdprestapiService, useValue: idprestapiServiceStub },
        { provide: IdpService, useValue: idpServiceStub },
        { provide: IdpdataService, useValue: idpdataserviceStub },
        { provide: Router, useValue: routerStub },
        TranslateService, TranslateLoader, TranslateParser
        ]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    router = TestBed.get(Router);
    idpService = TestBed.get(IdpService);
    idpdataService = TestBed.get(IdpdataService);
    idprestapiService = TestBed.get(IdprestapiService);
    fixture = TestBed.createComponent(AntCtrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it("code analysis on", () => {
    component.tempObject.modules[0].codeAnalysis = "on";
    component.buildInfo.modules[0].codeAnalysis[0] = "off";
    component.buildInfo.modules[0].codeAnalysis[1] = "off";
    component.buildInfo.modules[0].codeAnalysis[2] = "off";
    component.buildInfo.modules[0].codeAnalysis[3] = "off";
    expect(component.checkCodeAnalysisOn(0)).toBeFalsy();
  });

  it("code analysis on1", () => {
    component.tempObject.modules[0].codeAnalysis = "on";
    component.buildInfo.modules[0].codeAnalysis[0] = "on";
    component.buildInfo.modules[0].codeAnalysis[1] = "off";
    component.buildInfo.modules[0].codeAnalysis[2] = "off";
    component.buildInfo.modules[0].codeAnalysis[3] = "off";
    expect(component.checkCodeAnalysisOn(0)).toBeTruthy();
  });

  it("default values set", () => {
    expect(component.buildInfo.modules[0].javaMainClass.toBe(""));
    expect(component.buildInfo.modules[0].ejbDescriptor.toBe(""));
    expect(component.buildInfo.modules[0].warPackaging.toBe(""));
    expect(component.buildInfo.modules[0].compile.toBe("off"));
    expect(component.buildInfo.modules[0].unitTesting.toBe("off"));
    expect(component.buildInfo.modules[0].codeCoverage.toBe("off"));
    expect(component.buildInfo.modules[0].codeAnalysis[0].toBe("on"));
    expect(component.buildInfo.modules[0].codeAnalysis[1].toBe("off"));
    expect(component.buildInfo.modules[0].codeAnalysis[2].toBe("off"));
    expect(component.buildInfo.modules[0].codeAnalysis[3].toBe("off"));
    expect(component.buildInfo.modules[0].chkjar.toBe("off"));
    expect(component.buildInfo.modules[0].WARPackaging.toBe("off"));
    expect(component.setSomeValues1(0)).toBeFalsy();
  });

  it("code analysis clear", () => {
    expect(component.buildInfo.modules[0].codeAnalysis.toBe([]));
    expect(component.clearCodeAnalysis(0)).toBeFalsy();
  });

  it("code analysis clear", () => {
    expect(component.buildInfo.modules[0].codeAnalysis.toBe(["off" , "off" , "off" , "off"]));
    expect(component. createCodeAnalysis(0)).toBeTruthy();
  });

  it("set customBuild", () => {
    expect(component.tempObject.modules[0].customBuildXml.toBe("on"));
    expect(component.setcustomBuildXml(0)).toBeFalsy();
  });

  it("code analysis three", () => {
    expect(component.buildInfo.modules[0].codeAnalysis[3].toBe("off"));
    expect(component.tempObject.modules[0].WARPackaging.toBe("off"));
    expect(component.setcodeAnalysisthree(0)).toBeFalsy();
  });

  it("compile off", () => {
    expect(component.setWarPack(0));
    expect(component.setCompileOff(0)).toBe("off");
  });

  it("customBuild on", () => {
    expect(component.buildInfo.modules[0].javaMainClass.toBe(""));
    expect(component.buildInfo.modules[0].ejbDescriptor.toBe(""));
    expect(component.buildInfo.modules[0].warPackaging.toBe(""));
    expect(component.buildInfo.modules[0].compile.toBe("off"));
    expect(component.buildInfo.modules[0].unitTesting.toBe("off"));
    expect(component.buildInfo.modules[0].codeCoverage.toBe("off"));
    expect(component.buildInfo.modules[0].codeAnalysis[1].toBe("off"));
    expect(component.buildInfo.modules[0].codeAnalysis[2].toBe("off"));
    expect(component.buildInfo.modules[0].codeAnalysis[3].toBe("off"));
    expect(component.tempObject.modules[0].chkjar.toBe("off"));
    expect(component.tempObject.modules[0].WARPackaging.toBe("off"));
    expect(component.setCustomBuildOn(0)).toBeTruthy();
  });

  it("customBuild off", () => {
    expect(component.buildInfo.modules[0].customBuildXml.toBe(""));
    expect(component.buildInfo.modules[0].args.toBe(""));
    expect(component.setCustomBuildOff(0)).toBeFalsy();
  });

  it("war pack set", () => {
    expect(component.tempObject.modules[0].WARPackaging.toBe("off"));
    expect(component.buildInfo.modules[0].warPackaging.toBe(""));
    expect(component.setWarPack(0)).toBeFalsy();
  });

  it("checkForEarPackWAR", () => {
    component.tempObject.warList = undefined;
    expect(component.checkForEarPackWAR()).toBeFalsy();
  });

  it("checkForEarPackWAR", () => {
    component.tempObject.warList = ["abc"];
    expect(component.checkForEarPackWAR()).toBeTruthy();
  });

  it("setContinuecontrolFalse()", () => {
    const a = component.setContinuecontrolFalse();
    expect(a).toBeFalsy();
  });

  it("setContinuecontrolTrue()", () => {
    const a = component.setContinuecontrolTrue();
    expect(a).toBeFalsy();
  });

  it("setpaFalse()", () => {
    const a = component.setpaFalse();
    expect(a).toBeFalsy();
  });

  it("setpaTrue()", () => {
    const a = component.setpaTrue();
    expect(a).toBeFalsy();
  });

  it("setejbValFalse()", () => {
    const a = component.setejbValFalse();
    expect(a).toBeFalsy();
  });

  it("setejbValTrue()", () => {
    const a = component.setejbValTrue();
    expect(a).toBeFalsy();
  });

  it("setwarValFalse()", () => {
    const a = component.setwarValFalse();
    expect(a).toBeFalsy();
  });

  it("setwarValTrue()", () => {
    const a = component.setwarValTrue();
    expect(a).toBeFalsy();
  });

  it("setJavaModules()", () => {
    const a = component.setJavaModules();
    expect(component.buildInfo.javaModules).toBe("");
    expect(a).toBeFalsy();
  });

  it("setEjbModules()", () => {
    const a = component.setEjbModules();
    expect(component.buildInfo.ejbModules).toBe("");
    expect(a).toBeFalsy();
  });

  it("setwebModules()", () => {
    const a = component.setwebModules();
    expect(component.buildInfo.webModules).toBe("");
    expect(a).toBeFalsy();
  });

  it("setwebejbjavaModules()", () => {
    const a = component.setwebejbjavaModules();
    expect(component.buildInfo.ejbModules).toBe("");
    expect(component.buildInfo.webModules).toBe("");
    expect(component.buildInfo.javaModules).toBe("");
    expect(a).toBeFalsy();
  });

  it("checkForEarPackJava", () => {
    component.tempObject.javalist = undefined;
    const a = component.checkForEarPackJava();
    expect(a).toBeFalsy();
  });

  it("javaOptionList", () => {
    expect(component.tempObject.javalist.toBe([]));
    expect(component.buildInfo.modules[0].javaMainClass.toBe("hello"));
    const a = component.javaOptionList();
    expect(a).toBeFalsy();
  });
  it("setSomeValues", () => {
    const a = component.setSomeValues1(0);
    expect(a).toBeTruthy();
  });
  it("setCustomBuild", () => {
    const a = component.setCustomBuildOn(0);
    expect(a).toBeTruthy();
  });
  it("setCustomeBuildOFf", () => {
    const a = component.setCustomBuildOff(0);
    expect(a).toBeTruthy();
  });
  it("checkCheckBox", () => {
    const a = component.checkCheckBox();
    expect(a).toBeTruthy();
  });
  it("setSomeValues", () => {
    const a = component.setSomeValues1(0);
    expect(a).toBeTruthy();
  });

});
