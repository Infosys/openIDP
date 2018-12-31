import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule } from "@angular/forms";
import { WorkflowInfoComponent } from "./workflow-info.component";
import { TranslateModule, TranslateStaticLoader, TranslateLoader } from "ng2-translate";
import { SortablejsModule } from "angular-sortablejs";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown/angular2-multiselect-dropdown";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";
import { IDPEncryption } from "../idpencryption.service";
import { ParentFormConnectComponent } from "../parent-form-connect/parent-form-connect.component";
import { workflowRouter } from "./workflow-info.router";
import { HttpModule, XHRBackend, ResponseOptions, Response, RequestMethod, Headers } from "@angular/http";
import {
  MockBackend,
  MockConnection
} from "@angular/http/testing";
import { HttpHeaders } from "@angular/common/http";

const mockResponse = {
  resource: JSON.stringify({"status": "SUCCESS"})
};
describe("WorkflowInfoComponent", () => {
  /*let component: WorkflowInfoComponent;
  let fixture: ComponentFixture<WorkflowInfoComponent>;
  let Idpdata: IdpdataService;
  let Idprestapi: IdprestapiService;
  let router: Router;
  let IdpService: IdpService;
  let idpencryption: IDPEncryption;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [WorkflowInfoComponent],
      imports: [FormsModule,
        TranslateModule.forRoot(),
        SortablejsModule,
        AngularMultiSelectModule],
      providers: [
        forwardRef(() => IdpService),
      })
      .compileComponents();

    // TestBed.configureTestingModule({
    //   declarations: [TestInfoComponent, ParentFormConnectComponent],
    //   imports: [FormsModule],
    //   providers: [{ provide: IdprestapiService, useValue: idprestapiServiceStub },
    //   { provide: IdpService, useValue: idpServiceStub },
    //   { provide: IdpdataService, useValue: idpdataserviceStub },
    //   { provide: Router, useValue: routerStub }
    //   ]
    // })
    //   .compileComponents();
  }));*/

  let component: WorkflowInfoComponent;
  let fixture: ComponentFixture<WorkflowInfoComponent>;
  let idpService: IdpService;
  let idpdataService: IdpdataService;
  let Idprestapi: IdprestapiService;
  let router: Router;
  let idpencryption: IDPEncryption;
  class IdpdataServiceStub {

    // constructor() { }
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
    workflowData: any = [{ "workflowSequence": "" }];
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
    initMain: false;
    RestApiDetails: false;
    buildInfoReset = false;
    compMove: any;
    unit: any;
    code: any;
    refreshBuild = false;
    extraMultiselectSettings: any = {
      enableSearchFilter: true,
      selectAllText: "Select All",
      unSelectAllText: "UnSelect All"
    };
  }
  class IdpServiceStub {
  }
  class IdprestapiServiceStub {

    getFilteredApplicationNames(name) {
      return Promise.resolve(new Response(
        new ResponseOptions({ body: mockResponse})
      ));
    }

    getPipelineListforWorkflow() {
      return Promise.resolve(new Response(
        new ResponseOptions({ body: mockResponse})
      ));
    }
  }

  class RouterStub {
    url = "server411214d:4200";
    navigate(commands: any[], extras?: NavigationExtras) {
    }
  }
  class WorkflowStub {
  }
  const idpdataserviceStub: IdpdataServiceStub = new IdpdataServiceStub();
  const routerStub: RouterStub = new RouterStub();
  const idpServiceStub: IdpServiceStub = new IdpServiceStub();
  const idprestapiServiceStub: IdprestapiServiceStub = new IdprestapiServiceStub();
  const workflowStub: WorkflowStub = new WorkflowStub();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [WorkflowInfoComponent, ParentFormConnectComponent],
      imports: [FormsModule,
        TranslateModule.forRoot(),
        SortablejsModule,
        AngularMultiSelectModule],
      providers: [{ provide: IdprestapiService, useValue: idprestapiServiceStub },
      { provide: IdpService, useValue: idpServiceStub },
      { provide: IdpdataService, useValue: idpdataserviceStub },
      { provide: Router, useValue: routerStub },
      { provide: IDPEncryption },
      { provide: workflowRouter, useClass: WorkflowStub }
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkflowInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /*it("should create", () => {
    expect(component).toBeTruthy();
  });*/

  it("should return application names", () => {
    component.getApplicationNames("application");
  });

  it("redirect to basic info", () => {
    component.redirectToBasicInfo();
  });

  it("redirect to show configuration", () => {
    component. redirectToShowConfiguration();
  });

  it("test init navigation", () => {
    component.initNavigation();
  });

  it("initialize component", () => {
    component.initialize();
  });

  it("get pipelinelist copy edit", () => {
    component.getPipelineListCopyEdit("customerPortal", "service_cicd", 0 , 0);
  });

});
