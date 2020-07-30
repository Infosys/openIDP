import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";
import { JavaGradleCntrlComponent } from "./java-gradle-cntrl.component";
import { FormsModule } from "@angular/forms";
import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { IdprestapiService } from "../../idprestapi.service";
import { Router, NavigationExtras } from "@angular/router";
import {
  TranslateModule,
  TranslateStaticLoader,
  TranslateLoader,
} from "ng2-translate";
import { HttpModule, Http } from "@angular/http";

describe("JavaGradleCntrlComponent", () => {
  let component: JavaGradleCntrlComponent;
  let fixture: ComponentFixture<JavaGradleCntrlComponent>;
  let idpService: IdpService;
  let idpdataService: IdpdataService;
  let idprestapiService: IdprestapiService;
  let router: Router;

  class IdpdataServiceStub {
    constructor() {}
    template: any = {
      grantAccess: {
        applicationName: "",
        developers: [],
        pipelineAdmins: [],
        releaseManager: [],
        environmentOwnerDetails: [
          {
            environmentName: "",
            environmentOwners: [],
            dbOwners: [],
          },
        ],
        slaveDetails: [
          {
            slaveName: "",
            buildServerOS: "",
            workspacePath: "",
            createNewSlave: "",
            labels: "",
            sshKeyPath: "",
            slaveUsage: "both",
          },
        ],
      },
      basicInfo: {
        additionalMailRecipients: {
          applicationTeam: "",
          emailIds: "",
        },
        applicationName: "",
        buildInterval: {
          buildInterval: "",
          buildIntervalValue: 0,
          pollSCM: "off",
        },
        buildServerOS: "",
        engine: "",
        pipelineName: "",
      },
      code: {
        category: "",
        technology: "",
        scm: [],
        buildScript: [{ tool: "" }, { tool: "" }, {}],
      },
      buildInfo: {
        buildtool: "",
        castAnalysis: {},
        artifactToStage: {},
        modules: [],
      },
      deployInfo: {
        deployEnv: [],
      },
      testInfo: {
        testEnv: [],
      },
      formStatus: {
        basicInfo: {
          appNameStatus: "0",
          formStatus: "0",
        },
        codeInfo: "",
        buildInfo: {
          buildToolStatus: "0",
          formStatus: "0",
          ibmsiTypeStatus: "0",
        },
        deployInfo: "",
        testInfo: "",
        operation: "",
      },
      checkboxStatus: {
        basicInfo: {},
        codeInfo: {},
        buildInfo: {},
        deployInfo: {},
        testInfo: {},
        others: {},
      },
      backUp: {
        deployInfo: {},
        testInfo: {},
      },
      masterJson: {},
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
    pa: boolean = true;
    continuecontrol: any;
    geFlag: any;
    p: any = false;
    ejbVal: any;
    warVal: any;
    jarVal: any;
    pipelineData: any;
    triggerJobData: any;
    application: any;
    freezeNavBars: boolean = false;
    osFlag: any;
    op: any;
    operation: any;
    initMain: any = false;
    RestApiDetails: any = false;
    buildInfoReset = false;
    compMove: any;
    unit: any;
    code: any;
    refreshBuild: boolean = false;
  }
  class IdpServiceStub {}
  class IdprestapiServiceStub {}

  class RouterStub {
    navigate(commands: any[], extras?: NavigationExtras) {}
  }

  let idpdataserviceStub: IdpdataServiceStub = new IdpdataServiceStub();
  let routerStub: RouterStub = new RouterStub();
  let idpServiceStub: IdpServiceStub = new IdpServiceStub();
  let idprestapiServiceStub: IdprestapiServiceStub = new IdprestapiServiceStub();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [JavaGradleCntrlComponent, ParentFormConnectComponent],
      imports: [
        FormsModule,
        TranslateModule.forRoot({
          provide: TranslateLoader,
          useFactory: (http: Http) =>
            new TranslateStaticLoader(http, "public/assets/i18n", ".json"),
          deps: [Http],
        }),
      ],
      providers: [
        { provide: IdprestapiService, useValue: idprestapiServiceStub },
        { provide: IdpService, useValue: idpServiceStub },
        { provide: IdpdataService, useValue: idpdataserviceStub },
        { provide: Router, useValue: routerStub },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    router = TestBed.get(Router);
    idpService = TestBed.get(IdpService);
    idpdataService = TestBed.get(IdpdataService);
    idprestapiService = TestBed.get(IdprestapiService);
    fixture = TestBed.createComponent(JavaGradleCntrlComponent);
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

    component.codeAnalysisCheck();
    expect(component.IdpdataService.pa).toBeFalsy();
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

    component.codeAnalysisCheckbox();
    expect(component.IdpdataService.pa).toBeFalsy();
  });
  it("checkCheckBox", () => {
    component.tempObject.modules = undefined;

    component.checkCheckBox();
    expect(component.tempObject.modules).toBe([]);
  });
  it("unitTestInputOff", () => {
    var a = component.unitTestInputOff();
    expect(component.buildInfo.modules[0].args).toBe("");
    expect(component.buildInfo.modules[0].codeCoverage).toBe("off");
    expect(a).toBe("off");
  });
  it("clearCodeCoverage", () => {
    var a = component.clearCodeCoverage();
    expect(component.buildInfo.modules[0].args).toBe("");

    expect(a).toBe("off");
  });
  it("codeAnalysisOn", () => {
    var a = component.codeAnalysisOn();
    expect(component.buildInfo.modules[0].codeAnalysis).toBe([
      "androidLint",
      "off",
    ]);
    expect(a).toBe("on");
  });
  it("codeAnalysisOff", () => {
    var a = component.codeAnalysisOff();
    component.buildInfo.modules[0].args = "";
    expect(component.buildInfo.modules[0].codeAnalysis).toBe([
      "androidLint",
      "off",
    ]);
    expect(a).toBe("off");
  });
});
