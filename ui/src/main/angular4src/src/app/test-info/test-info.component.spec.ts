// /**
// *
// * Copyright 2018 Infosys Ltd.
// * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
// * https://opensource.org/licenses/MIT.?
// *
// **/
// tslint:disable
// import { async, ComponentFixture, TestBed } from "@angular/core/testing";
// import { FormsModule } from "@angular/forms";
// import { TestInfoComponent } from "./test-info.component";
// import { IdpService } from "../idp-service.service";
// import { IdpdataService } from "../idpdata.service";
// import { IdprestapiService } from "../idprestapi.service";
// import { Router, NavigationExtras } from "@angular/router";
// import { ParentFormConnectComponent } from "../parent-form-connect/parent-form-connect.component";


// describe("TriggerComponent", () => {
//   let component: TestInfoComponent;
//   let fixture: ComponentFixture<TestInfoComponent>;
//   let idpService: IdpService;
//   let idpdataService: IdpdataService;
//   let idprestapiService: IdprestapiService;
//   let router: Router;
//   class IdpdataServiceStub {
//     constructor() { }
//     template: any = {
//         "grantAccess": {
//         "applicationName": "",
//         "developers": [],
//         "pipelineAdmins": [],
//         "releaseManager": [],
//         "environmentOwnerDetails": [{
//             "environmentName": "",
//             "environmentOwners": [],
//             "dbOwners": []
//         }],
//         "slaveDetails": [
//             {
//             "slaveName": "",
//             "buildServerOS": "",
//             "workspacePath": "",
//             "createNewSlave": "",
//             "labels": "",
//             "sshKeyPath": "",
//             "slaveUsage": "both"
//             }
//         ]
//         },
//         "basicInfo": {
//         "additionalMailRecipients": {
//             "applicationTeam": "",
//             "emailIds": ""
//         },
//         "applicationName": "",
//         "buildInterval": {
//             "buildInterval": "",
//             "buildIntervalValue": 0,
//             "pollSCM": "off"
//         },
//         "buildServerOS": "",
//         "engine": "",
//         "pipelineName": ""
//         },
//         "code": {
//         "category": "",
//         "technology": "",
//         "scm": [],
//         "buildScript": [{ "tool": "" }, { "tool": "" }, {}]
//         },
//         "buildInfo": {
//         "buildtool": "",
//         "castAnalysis": {},
//         "artifactToStage": {},
//         "modules": []
//         },
//         "deployInfo": {
//         "deployEnv": []
//         },
//         "testInfo": {
//         "testEnv": []
//         },
//         "formStatus": {
//         "basicInfo": {
//             "appNameStatus": "0",
//             "formStatus": "0"
//         },
//         "codeInfo": "",
//         "buildInfo": {
//             "buildToolStatus": "0",
//             "formStatus": "0",
//             "ibmsiTypeStatus": "0"
//         },
//         "deployInfo": "",
//         "testInfo": "",
//         "operation": ""
//         },
//         "checkboxStatus": {
//         "basicInfo": {},
//         "codeInfo": {},
//         "buildInfo": {},
//         "deployInfo": {},
//         "testInfo": {},
//         "others": {}
//         },
//         "backUp": {
//         "deployInfo": {},
//         "testInfo": {}
//         },
//         "masterJson": {}
//     };
//     data: any = JSON.parse(JSON.stringify(this.template));
//     language = "english";
//     idpUserName = "";
//     roles = [];
//     access_token: any;
//     permissions = [];
//     createAppflag = false;
//     createPipelineflag = false;
//     copyPipelineflag = false;
//     editPipelineflag = false;
//     deletePipelineflag = false;
//     test = false;
//     devServerURL: any = "";
//     IDPDashboardURL = "";
//     IDPLink = "";
//     geUrl = "";
//     role = "";
//     IDPDropdownProperties: any = {};
//     showConfig: any;
//     pa = true;
//     continuecontrol: any;
//     geFlag: any;
//     p: any = false;
//     ejbVal: any;
//     warVal: any;
//     jarVal: any;
//     pipelineData: any;
//     triggerJobData: any;
//     application: any;
//     freezeNavBars = false;
//     osFlag: any;
//     op: any;
//     operation: any;
//     initMain: false;
//     RestApiDetails: false;
//     buildInfoReset = false;
//     compMove: any;
//     unit: any;
//     code: any;
//     refreshBuild = false;
//   }
//   class IdpServiceStub {
//   }
//   class IdprestapiServiceStub {
//   }

//   class RouterStub {
//     navigate(commands: any[], extras?: NavigationExtras) { }
//   }
//   const idpdataserviceStub: IdpdataServiceStub = new IdpdataServiceStub();
//   const routerStub: RouterStub = new RouterStub();
//   const idpServiceStub: IdpServiceStub = new IdpServiceStub();
//   const idprestapiServiceStub: IdprestapiServiceStub = new IdprestapiServiceStub();

//   beforeEach(async(() => {
//     TestBed.configureTestingModule({
//       declarations: [TestInfoComponent, ParentFormConnectComponent],
//       imports: [FormsModule],
//       providers: [{ provide: IdprestapiService, useValue: idprestapiServiceStub },
//       { provide: IdpService, useValue: idpServiceStub },
//       { provide: IdpdataService, useValue: idpdataserviceStub },
//       { provide: Router, useValue: routerStub }
//       ]
//     })
//       .compileComponents();
//   }));

//   beforeEach(() => {
//     router = TestBed.get(Router);
//     idpService = TestBed.get(IdpService);
//     idpdataService = TestBed.get(IdpdataService);
//     idprestapiService = TestBed.get(IdprestapiService);
//     fixture = TestBed.createComponent(TestInfoComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });

//   xit("should be created", () => {
//     expect(component).toBeTruthy();
//   });

//   it("clearRunScript", () => {
//     expect(component.testInfo.testEnv[0].testSteps[0].runScript = {
//         "scriptType": "",
//         "reportType": ""
//       });
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].runScript = {
//         "scriptType": "",
//       "reportType": ""
//       });
//     expect(component.clearRunScript(0, 0)).toEqual("off");
//   });

//   it("clearTest", () => {
//     expect(component.testInfo.testEnv[0].testSteps[0].test = {
//         "testCategory": "",
//         "testTypeName": "",
//         "frameWork": "",
//         "browserName": "",
//         "version": ""
//       });
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].test = {
//         "testCategory": "",
//         "testTypeName": "",
//         "frameWork": "",
//         "browserName": "",
//         "version": ""
//       });
//     expect(component.clearTest(0, 0)).toEqual("off");
//   });

//   it("clearLibraryFlag", () => {
//     expect(component.testInfo.testEnv[0].testSteps[0].test.externalFilePath.toEqual(""));
//     expect(component.clearLibraryFlag(0, 0)).toEqual("off");
//   });

//   it("cleardataPool", () => {
//     expect(component.testInfo.testEnv[0].testSteps[0].test.iteration.toEqual(""));
//     expect(component.testInfo.testEnv[0].testSteps[0].test.fullIteration.toEqual(""));
//     expect(component.cleardataPool(0, 0)).toEqual("off");
//   });

//   it("openAntPropertiesField", () => {
//     expect(component.testInfo.testEnv[0].testSteps[0].runScript.antPropertiesArr.toEqual([]));
//     expect(component.addAntProperties(0, 0));
//     expect(component.openAntPropertiesField(0, 0)).toEqual("on");
//   });

//   it("clearAntPropertisField", () => {
//     expect(component.testInfo.testEnv[0].testSteps[0].runScript.antPropertiesArr.toEqual([]));
//     expect(component.clearAntPropertisField(0, 0)).toBeFalsy();
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("msBuild"));
//     component.checkCategory(0, 0, "functional");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "SAHI", "value": "sahi" },
//       { "name": "Selenium", "value": "selenium" }
//     ]));
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("maven"));
//     component.checkCategory(0, 0, "functional");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "SAHI", "value": "sahi" },
//       { "name": "Selenium", "value": "selenium" }
//     ]));
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("maven"));
//     component.checkCategory(0, 0, "functional");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "SAHI", "value": "sahi" },
//       { "name": "Selenium", "value": "selenium" }
//     ]));
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("ant"));
//     component.checkCategory(0, 0, "functional");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "SAHI", "value": "sahi" },
//       { "name": "Selenium", "value": "selenium" }
//     ]));
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("angular"));
//     component.checkCategory(0, 0, "functional");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "Selenium", "value": "selenium" }
//     ]));
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("nodejs"));
//     component.checkCategory(0, 0, "functional");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "SAHI", "value": "sahi" },
//       { "name": "Selenium", "value": "selenium" },
//       { "name": "HP UFT", "value": "hpUft" },
//       { "name": "HP ALM", "value": "hpAlm" }
//     ]));
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("Python"));
//     component.checkCategory(0, 0, "functional");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "SAHI", "value": "sahi" },
//       { "name": "Selenium", "value": "selenium" },
//         { "name": "RFT", "value": "rft" },
//          { "name": "Microsoft Test Manager", "value": "mtm" }
//     ]));
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("msBuild"));
//     component.checkCategory(0, 0, "performance");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "JMeter", "value": "jMeter" }
//     ]));
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("ant"));
//     component.checkCategory(0, 0, "performance");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "JMeter", "value": "jMeter" }
//     ]));
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("maven"));
//     component.checkCategory(0, 0, "performance");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "JMeter", "value": "jMeter" }
//     ]));
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("nodeJs"));
//     component.checkCategory(0, 0, "performance");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "JMeter", "value": "jMeter" }
//     ]));
//   });

//   it("checkCategory", () => {
//     expect(component.buildInfo.buildtool.toEqual("Python"));
//     component.checkCategory(0, 0, "performance");
//     expect(component.tempObjecttest.testEnv[0].testSteps[0].testTypeName.toEqual([
//       { "name": "JMeter", "value": "jMeter" },
//        { "name": "RPT", "value": "rpt" }

//     ]));
//   });

// });
