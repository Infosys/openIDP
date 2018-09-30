/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule } from "@angular/forms";
import { DeployInfoComponent } from "./deploy-info.component";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";
import { ParentFormConnectComponent } from "../parent-form-connect/parent-form-connect.component";
import { TranslateModule , TranslateService, TranslateLoader, TranslateParser} from "ng2-translate";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import { IDPEncryption } from "../idpencryption.service";

describe("DeployInfoComponent", () => {
  let component: DeployInfoComponent;
  let fixture: ComponentFixture<DeployInfoComponent>;
  let idpService: IdpService;
  let idpdataService: IdpdataService;
  let idprestapiService: IdprestapiService;
  let idpEncryption: IDPEncryption;
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
  }
class  IDPEncryptionStub {
  }
  class RouterStub {
    navigate(commands: any[], extras?: NavigationExtras) { }
  }
    const idpdataserviceStub: IdpdataServiceStub = new IdpdataServiceStub();
    const routerStub: RouterStub = new RouterStub();
    const idpServiceStub: IdpServiceStub = new IdpServiceStub();
    const idprestapiServiceStub: IdprestapiServiceStub = new IdprestapiServiceStub();
    const idpEncryptionStub: IDPEncryptionStub = new IDPEncryptionStub();
  beforeEach(async(() => {
    TestBed.configureTestingModule({
        declarations: [ DeployInfoComponent, ParentFormConnectComponent ],

      imports: [FormsModule, TranslateModule],
      providers: [{provide: IdprestapiService, useValue: idprestapiServiceStub},
                    {provide: IdpService, useValue: idpServiceStub},
                    {provide: IdpdataService, useValue: idpdataserviceStub},
                    {provide: IDPEncryption, useValue: idpEncryptionStub},
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
    idpEncryption = TestBed.get(IDPEncryption);
    fixture = TestBed.createComponent(DeployInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it("should create", () => {
    expect(component.deployInfo.deployEnv[0].deploySteps[0].deployToContainer.fileName).toBe("");
  });

  it("checkStep", () => {
    expect(component.deployInfo.checkStep(0)).toBeTruthy();
  });

  it("clearStep", () => {
    expect(component.deployInfo.clearStep(0)).toBeFalsy();
  });

});
