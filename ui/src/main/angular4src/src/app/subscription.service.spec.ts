/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
// tslint:disable
import { async, TestBed, inject } from "@angular/core/testing";
import { NO_ERRORS_SCHEMA } from "@angular/core";
import { IdprestapiService } from "./idprestapi.service";
import { MockBackend, MockConnection } from "@angular/http/testing";
import {
  HttpModule,
  XHRBackend,
  ResponseOptions,
  Response,
  RequestMethod,
} from "@angular/http";
import "rxjs/add/operator/toPromise";
import { IdpdataService } from "./idpdata.service";
import { CookieService } from "ngx-cookie";
import { Router, NavigationExtras } from "@angular/router";
import { environment } from "../environments/environment";
import { Adal4Service } from "adal-angular4";
import { StartupService } from "./startup.service";
import { KeycloakService } from "./keycloak/keycloak.service";
import { platformBrowserDynamic } from "@angular/platform-browser-dynamic";
import {
  HttpClientTestingModule,
  HttpTestingController,
} from "@angular/common/http/testing";
import {
  HttpClient,
  HttpResponse,
  HttpErrorResponse,
} from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { SubscriptionService } from "./subscription.service";
describe("Subscription Service", () => {
  let httpTestingController: HttpTestingController;
  let idprestapiService: IdprestapiService;
  let subscriptionService: SubscriptionService;
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
        postBuildScript: {},
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

  class RouterStub {
    navigate(commands: any[], extras?: NavigationExtras) {}
  }

  class StartUpServiceStub {
    authmode = "ldap";
    keycloakUrl = "abc";
    keycloakRealm = "aa";
    keycloakClientId = "";
    clouddeployurl = "";
    getData() {
      return "data";
    }
  }
  const idpdataserviceStub: IdpdataServiceStub = new IdpdataServiceStub();
  const routerStub: RouterStub = new RouterStub();
  const startupStub: StartUpServiceStub = new StartUpServiceStub();

  beforeEach(async(() => {
    const cookieServiceSpy = jasmine.createSpyObj(["get"]);
    const stubValue = true;
    const stub = { id: 1, name: "A" };

    const adalServiceSpy = jasmine.createSpyObj(["get"]);

    const keycloakServiceSpy = jasmine.createSpyObj(["get"]);

    cookieServiceSpy.get.and.returnValue(stubValue);
    //console.log(Observable.of(stub));
    adalServiceSpy.get.and.returnValue(stubValue);

    keycloakServiceSpy.get.and.returnValue(stubValue);
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, HttpModule],
      providers: [
        { provide: StartupService, useValue: startupStub },
        { provide: CookieService, useValue: cookieServiceSpy },
        { provide: IdpdataService, useValue: idpdataserviceStub },
        { provide: Router, useValue: routerStub },
        { provide: Adal4Service, useValue: adalServiceSpy },
        { provide: KeycloakService, useValue: keycloakServiceSpy },

        IdprestapiService,
        SubscriptionService,
      ],
      schemas: [NO_ERRORS_SCHEMA],
    });

    httpTestingController = TestBed.get(HttpTestingController);
    idprestapiService = TestBed.get(IdprestapiService);
  }));

  beforeEach(() => {});

  it("should be created", inject(
    [SubscriptionService],
    (service: SubscriptionService) => {
      expect(service).toBeTruthy();
    }
  ));
  it("should be created", inject(
    [SubscriptionService],
    (service: SubscriptionService) => {
      service.addLicense("license");
    }
  ));
  it("should be created", inject(
    [SubscriptionService],
    (service: SubscriptionService) => {
      service.getActiveServices();
    }
  ));
  it("should be created", inject(
    [SubscriptionService],
    (service: SubscriptionService) => {
      service.getAllSubscriptions();
    }
  ));
  it("should be created", inject(
    [SubscriptionService],
    (service: SubscriptionService) => {
      service.validateLicense("key");
    }
  ));
});
