/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
// tslint:disable
import { MsbuildCntrlComponent } from "./msbuild-cntrl.component";
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";
import { FormsModule } from "@angular/forms";

import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { IdprestapiService } from "../../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";
import { TranslateModule, TranslateService, TranslateLoader, TranslateParser } from "ng2-translate";



describe("MsbuildCntrlComponent", () => {
  let component: MsbuildCntrlComponent;
  let fixture: ComponentFixture<MsbuildCntrlComponent>;

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

    getIDPDropdownProperties() {

        const msBuildVersionObj = {
        msBuildVersion: ""
        };

        return msBuildVersionObj;

    }
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
        declarations: [MsbuildCntrlComponent, ParentFormConnectComponent],
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
    fixture = TestBed.createComponent(MsbuildCntrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it("setpaFalse()", () => {
    const a = component.setpaFalse();
    expect(a).toBeFalsy();
  });

  it("setpaTrue()", () => {
    const a = component.setpaTrue();
    expect(a).toBeFalsy();
  });

  it("setpaCodeAnalysis(i)", () => {
    const a = component.setpaCodeAnalysis(0);
    expect(a).toBeFalsy();
  });


  it("setSomeValues1()", () => {
    const a = component.setSomeValues1();
    expect(component.tempObject.modules[0].Checkmarx).toBe("");
    expect(component.tempObject.modules[0].excludeFolder).toBe("off");
    expect(component.tempObject.modules[0].schdulePeriodicFullScan).toBe("off");
    expect(a).toBeFalsy();
  });


  it("setSomeValues2()", () => {
    const a = component.setSomeValues2();
    expect(a).toBeFalsy();
  });

  it("setBuildinfoExc()", () => {
    const a = component.setBuildinfoExc();
    expect(a).toBeFalsy();
  });

  it("setBuildinfoExc()", () => {
    const a = component.setBuildinfoExc();
    expect(component.buildInfo.modules[0].exclude).toBe("");
    expect(a).toBeFalsy();
  });

  it("setBuildinfoInt()", () => {
    const a = component.setBuildinfoInt();
    expect(component.tempObject.modules[0].interval).toBe(undefined);
    expect(a).toBeFalsy();
  });

  it("setUSPassSM(i)", () => {
    const a = component.setUSPassSM(0);
    expect(a).toBeFalsy();
  });


  it("setunitTest(i)", () => {
    const a = component.setunitTest(0);
    expect(component.tempObject.modules[0].unitTestCategory).toBe(undefined);
    expect(a).toBeFalsy();
  });

  it("settestSettingFilePath(i)", () => {
    const a = component.settestSettingFilePath(0);
    expect(a).toBeFalsy();
  });


  it("setcodeAnalysis(i)", () => {
    const a = component.setcodeAnalysis(0);
    expect(component.tempObject.modules[0].codeAnalysis).toBe("on");
    expect(a).toBeFalsy();
  });


  it("setTempObjectCheckMarx()", () => {
    const a = component.setTempObjectCheckMarx();
    expect(component.tempObject.modules[0].Checkmarx).toBe("");
    expect(a).toBeFalsy();
  });


  it("addItem()", () => {
    component.tempObject.modules = undefined;
    const a = component.addItem();
  });

  it("obfuscationDotnetChecked(index)", () => {
    component.buildInfo.modules[0].renaming = null;
    const a = component.obfuscationDotnetChecked(0);
    expect(component.buildInfo.modules[0].renaming).toBe("On");
    component.buildInfo.modules[0].controlFlow = null;
    const a1 = component.obfuscationDotnetChecked(0);
    expect(component.buildInfo.modules[0].controlFlow).toBe("On");
    component.buildInfo.modules[0].encryption = null;
    const a2 = component.obfuscationDotnetChecked(0);
    expect(component.buildInfo.modules[0].encryption).toBe("On");
  });

  it(" checkCheckBox()", () => {
    component.buildInfo.modules[0].codeAnalysis.length = 3;
    const a = component.checkCheckBox();
  });
});
