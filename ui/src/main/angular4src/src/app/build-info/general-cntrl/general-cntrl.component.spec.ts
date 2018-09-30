/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { GeneralCntrlComponent } from "./general-cntrl.component";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";
import { FormsModule } from "@angular/forms";
import { TranslateModule , TranslateService, TranslateLoader, TranslateParser} from "ng2-translate";
import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { IdprestapiService } from "../../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";

describe("GeneralCntrlComponent", () => {
  let component: GeneralCntrlComponent;
  let fixture: ComponentFixture<GeneralCntrlComponent>;
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
        declarations: [ GeneralCntrlComponent, ParentFormConnectComponent ],
      imports: [FormsModule, TranslateModule],
      providers: [{provide: IdprestapiService, useValue: idprestapiServiceStub},
                    {provide: IdpService, useValue: idpServiceStub},
                    {provide: IdpdataService, useValue: idpdataserviceStub},
                    IdpdataService, TranslateService, TranslateLoader, TranslateParser,
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
    fixture = TestBed.createComponent(GeneralCntrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("addItem", () => {
      component.tempObject.modules === undefined;
      const a = component.addItem();
    expect(component.tempObject.modules).toBe([]);
  });
  it("changeRunScript", () => {
      component.changeRunScript(0);
      expect(component.buildInfo.modules[0].runScript.scriptFilePath).toBe("");
      expect(component.buildInfo.modules[0].runScript.targets).toBe("");
      expect(component.buildInfo.modules[0].runScript.host).toBe("");
      expect(component.buildInfo.modules[0].runScript.userName).toBe("");
      expect(component.buildInfo.modules[0].runScript.password).toBe("");
      expect(component.buildInfo.modules[0].runScript.script).toBe("");
      expect(component.buildInfo.modules[0].runScript.pathToFiles).toBe("");
      expect(component.buildInfo.modules[0].runScript.destinationDir).toBe("");
      expect(component.buildInfo.modules[0].runScript.flattenFilePath).toBe("off");


  });

  it("changeAbortRunScript", () => {
      component.changeAbortRunScript(0);
      expect(component.buildInfo.modules[0].abortScript.scriptFilePath).toBe("");
      expect(component.buildInfo.modules[0].abortScript.targets).toBe("");
      expect(component.buildInfo.modules[0].abortScript.host).toBe("");
      expect(component.buildInfo.modules[0].abortScript.userName).toBe("");
      expect(component.buildInfo.modules[0].abortScript.password).toBe("");
      expect(component.buildInfo.modules[0].abortScript.script).toBe("");
      expect(component.buildInfo.modules[0].abortScript.pathToFiles).toBe("");
      expect(component.buildInfo.modules[0].abortScript.destinationDir).toBe("");
      expect(component.buildInfo.modules[0].abortScript.flattenFilePath).toBe("off");


  });

});
