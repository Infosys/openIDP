/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.�
 *
 **/
/* tslint:disable */
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

describe("IdprestapiService", () => {
  let httpTestingController: HttpTestingController;
  let idprestapiService: IdprestapiService;
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
      ],
      schemas: [NO_ERRORS_SCHEMA],
    });

    httpTestingController = TestBed.get(HttpTestingController);
    idprestapiService = TestBed.get(IdprestapiService);
  }));

  beforeEach(() => {});

  it("#getValue should return stubbed value from a spy", () => {
    // create `getValue` spy on an object representing the ValueService

    /* const { idprestService, stubValue, stub, cookieServiceSpy, httpServiceSpy, adalServiceSpy, startupStub, keycloakServiceSpy } = setup(); */
    let response;

    idprestapiService.getNotification().then((res) => {
      response = res;
    });

    /*  expect(response)
      .toBe(stub, "service returned stub value");

    expect(httpServiceSpy.getValue.calls.count())
      .toBe(1, "spy method was called once");
    expect(httpServiceSpy.getValue.calls.mostRecent().returnValue)
      .toBe(stubPromiseValue); */
  });

  /* it("#getValue should return stubbed value from a spy", () => {
	  idprestService.getUserName();
	  idprestService.updatePlan("data1", "data2", "data3", "envData");
	  idprestService.getReleaseNumber("data1");
	  idprestService.getEnvNames("data1", "d2");
	  idprestService.obtainAccessToken("data1");
	  idprestService.getData();
	  idprestService.logout();
	  idprestService.checkApplicationNames("data1");
	  idprestService.getOrganizationWiseApplicationNames();
	  idprestService.getAvailableSlot("d1", "d2", "d3");
	  idprestService.getPipelineDetails("data");
	  idprestService.checkForApplicationType("data");
	  idprestService.getEnvSlots("d1","d2");
	  idprestService.getExistingSlot("d1","d2", "d3");
	  idprestService.emailToEnvOwner("d1");
	  idprestService.getPipelineEnv("d1","d2");
	  idprestService.getEnvironmentPairs("d1","d2");

  }); */

  it("should run #getNotification()", async(() => {
    const result = idprestapiService.getNotification();
  }));

  it("should run #getUserName()", async(() => {
    const result = idprestapiService.getUserName();
  }));

  it("should run #updatePlan()", async(() => {
    let data1, data2, data3, envData;
    const result = idprestapiService.updatePlan(data1, data2, data3, envData);
  }));

  it("should run #getReleaseNumber()", async(() => {
    let data;
    const result = idprestapiService.getReleaseNumber(data);
  }));

  it("should run #getEnvNames()", async(() => {
    let data;
    let data1;
    const result = idprestapiService.getEnvNames(data, data1);
  }));

  it("should run #obtainAccessToken()", async(() => {
    let params;
    const result = idprestapiService.obtainAccessToken(params);
  }));

  it("should run #getData()", async(() => {
    const result = idprestapiService.getData();
  }));

  it("should run #logout()", async(() => {
    const result = idprestapiService.logout();
  }));

  it("should run #checkApplicationNames()", async(() => {
    const result = idprestapiService.checkApplicationNames({});
  }));

  it("should run #getOrganizationWiseApplicationNames()", async(() => {
    const result = idprestapiService.getOrganizationWiseApplicationNames();
  }));

  it("should run #getTimeZone()", async(() => {
    const result = idprestapiService.getTimeZone();
  }));

  it("should run #getAvailableSlot()", async(() => {
    let data1;
    let data2;
    let data3;
    const result = idprestapiService.getAvailableSlot(data1, data2, data3);
  }));

  it("should run #getPipelineDetails()", async(() => {
    let data;
    const result = idprestapiService.getPipelineDetails(data);
  }));

  it("should run #checkForApplicationType()", async(() => {
    let data;
    const result = idprestapiService.checkForApplicationType(data);
  }));

  it("should run #getEnvSlots()", async(() => {
    const result = idprestapiService.getEnvSlots("application_name", "env");
  }));

  it("should run #getExistingSlot()", async(() => {
    let application_name;
    let release_number;
    let env;

    const result = idprestapiService.getExistingSlot(
      application_name,
      release_number,
      env
    );
  }));

  it("should run #emailToEnvOwner()", async(() => {
    let envOwner;
    const result = idprestapiService.emailToEnvOwner(envOwner);
  }));

  it("should run #getPipelineEnv()", async(() => {
    let data;
    let data1;
    const result = idprestapiService.getPipelineEnv(data, data1);
  }));

  it("should run #getEnvironmentPairs()", async(() => {
    let data;
    const result = idprestapiService.getEnvironmentPairs(data);
  }));

  it("should run #getEnvironmentNames()", async(() => {
    let data;
    const result = idprestapiService.getEnvironmentNames(data);
  }));

  it("should run #getIDPDropdownProperties()", async(() => {
    const result = idprestapiService.getIDPDropdownProperties();
  }));

  it("should run #checkAvailableJobs()", async(() => {
    const result = idprestapiService.checkAvailableJobs();
  }));

  it("should run #triggerJob()", async(() => {
    let request;

    const result = idprestapiService.triggerJob(request);
  }));

  it("should run #createCloudApplication()", async(() => {
    let requestData;
    const result = idprestapiService.createCloudApplication(requestData);
  }));

  it("should run #createLoadBalancer()", async(() => {
    let requestData;
    const result = idprestapiService.createLoadBalancer(requestData);
  }));

  it("should run #createCloudPipeline()", async(() => {
    let requestData;
    const result = idprestapiService.createCloudPipeline(requestData);
  }));

  it("should run #copyeditCloudPipeline()", async(() => {
    let appName;
    let pipName;
    const result = idprestapiService.copyeditCloudPipeline(appName, pipName);
  }));

  it("should run #submit()", async(() => {
    const result = idprestapiService.submit({});
  }));

  it("should run #triggerJobs()", async(() => {
    let requestData;
    const result = idprestapiService.triggerJobs({});
  }));

  it("should run #getApplicationDetails()", async(() => {
    const requestData = {};
    const result = idprestapiService.getApplicationDetails(requestData);
  }));

  it("should run #deletePipeline()", async(() => {
    const requestData = {};
    const result = idprestapiService.deletePipeline(requestData);
  }));

  it("should run #getExistingApps()", async(() => {
    const result = idprestapiService.getExistingApps();
  }));

  it("should run #getApplicationInfo()", async(() => {
    const requestData = {};
    const result = idprestapiService.getApplicationInfo(requestData);
  }));

  it("should run #getFilteredApplicationNames()", async(() => {
    const filterString = "";
    const result = idprestapiService.getFilteredApplicationNames(filterString);
  }));

  it("should run #editApplicationDetails()", async(() => {
    const requestData = {};

    const result = idprestapiService.editApplicationDetails(requestData);
  }));

  it("should run #createApplication()", async(() => {
    const requestData = {};
    const result = idprestapiService.createApplication(requestData);
  }));

  it("should run #getJobs()", async(() => {
    const requestData = {};
    const result = idprestapiService.getJobs(requestData);
  }));

  it("should run #getStageViewUrl()", async(() => {
    const result = idprestapiService.getStageViewUrl();
  }));

  it("should run #getPipelineList()", async(() => {
    const responseData = {};
    const result = idprestapiService.getPipelineList(responseData);
  }));

  it("should run #sendAppMail()", async(() => {
    const responseData = {};
    const result = idprestapiService.sendAppMail(responseData);
  }));

  it("should run #sendOrgMail()", async(() => {
    const responseData = {};
    const result = idprestapiService.sendOrgMail(responseData);
  }));

  it("should run #sendLicenseMail()", async(() => {
    const responseData = {};
    const result = idprestapiService.sendLicenseMail(responseData);
  }));

  it("should run #sendPipeMail()", async(() => {
    const responseData = {};
    const result = idprestapiService.sendPipeMail(responseData);
  }));

  it("should run #getPipelineNames()", async(() => {
    const requestData = {};
    const result = idprestapiService.getPipelineNames(requestData);
  }));

  it("should run #getPipelineListforWorkflow()", async(() => {
    const requestData = {};
    const result = idprestapiService.getPipelineListforWorkflow(requestData);
  }));

  it("should run #getExistingAppNames()", async(() => {
    const result = idprestapiService.getExistingAppNames();
  }));

  it("should run #checkSubApplication()", async(() => {
    const data = {};
    const result = idprestapiService.checkSubApplication(data);
  }));

  it("should run #getActiveReleases()", async(() => {
    const requestData = {};
    const result = idprestapiService.getActiveReleases(requestData);
  }));

  it("should run #getHistoryReleases()", async(() => {
    const requestData = {};
    const result = idprestapiService.getHistoryReleases(requestData);
  }));

  it("should run #getApplicationNameForReleaseManager()", async(() => {
    const responseData = {};
    const result = idprestapiService.getApplicationNameForReleaseManager(
      responseData
    );
  }));

  it("should run #postReleaseData()", async(() => {
    const responseData = {};
    const result = idprestapiService.postReleaseData(responseData);
  }));

  it("should run #updateReleases()", async(() => {
    const responseData = {};
    const result = idprestapiService.updateReleases(responseData);
  }));

  it("should run #checkReleaseNo()", async(() => {
    const result = idprestapiService.checkReleaseNo();
  }));

  it("should run #getArtifactsRm()", async(() => {
    const data = {};
    const result = idprestapiService.getArtifactsRm(data);
  }));

  it("should run #getReleasesApprovePortal()", async(() => {
    const result = idprestapiService.getReleasesApprovePortal();
  }));

  it("should run #updateArtifacts()", async(() => {
    const data = {};
    const result = idprestapiService.updateArtifacts(data);
  }));

  it("should run #fetchTriggerSteps()", async(() => {
    const data = {};
    const result = idprestapiService.fetchTriggerSteps(data);
  }));

  it("should run #getJobParamList()", async(() => {
    const result = idprestapiService.getJobParamList();
  }));

  it("should run #getTestPlanList()", async(() => {
    const result = idprestapiService.getTestPlanList("appName", "pipeName");
  }));

  it("should run #getTestSuitList()", async(() => {
    const result = idprestapiService.getTestSuitList(
      "id",
      "appName",
      "pipeName"
    );
  }));

  it("should run #base64EncodeDecode()", async(() => {
    const result = idprestapiService.base64EncodeDecode("uname", "pass");
  }));

  it("should run #buildIntervalTriggerJobs()", async(() => {
    const data = {};
    const result = idprestapiService.buildIntervalTriggerJobs(data);
  }));

  it("should run #getSlaveStatus()", async(() => {
    const data = {};
    const result = idprestapiService.getSlaveStatus(data);
  }));

  it("should run #getArtifactLatestDetails()", async(() => {
    const data = {};
    const result = idprestapiService.getArtifactLatestDetails(data);
  }));

  it("should run #getPipelineAdmins()", async(() => {
    const appName = "appName";
    const result = idprestapiService.getPipelineAdmins(appName);
  }));

  it("should run #getPipelinePermission()", async(() => {
    const data = {};

    const result = idprestapiService.getPipelinePermission(data);
  }));

  it("should run #approveJobs()", async(() => {
    const requestData = {};
    const result = idprestapiService.approveJobs(requestData);
  }));

  it("should run #getSubscriptionPermission()", async(() => {
    const result = idprestapiService.getSubscriptionPermission();
  }));

  it("should run #getValidatedLicense()", async(() => {
    const result = idprestapiService.getValidatedLicense();
  }));

  it("should run #createLicense()", async(() => {
    const data = {};
    const result = idprestapiService.createLicense(data);
  }));

  it("should run #createOrganization()", async(() => {
    const requestData = {};
    const result = idprestapiService.createOrganization(requestData);
  }));

  it("should run #getExistingOrgNames()", async(() => {
    const result = idprestapiService.getExistingOrgNames();
  }));

  it("should run #editOrganizationDetails()", async(() => {
    const result = idprestapiService.editOrganizationDetails({});
  }));
});
