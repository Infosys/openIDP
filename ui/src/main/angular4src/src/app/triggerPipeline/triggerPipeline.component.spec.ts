/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
//tslint:disable
// import { async, ComponentFixture, TestBed } from "@angular/core/testing";
// import { FormsModule } from "@angular/forms";
// import { TriggerComponent } from "./triggerPipeline.component";
// import { IdpService } from "../idp-service.service";
// import { IdpdataService } from "../idpdata.service";
// import { IdprestapiService } from "../idprestapi.service";
// import { Router, NavigationExtras } from "@angular/router";
// import { ParentFormConnectComponent } from "../parent-form-connect/parent-form-connect.component";


// describe("TriggerComponent", () => {
//   let component: TriggerComponent;
//   let fixture: ComponentFixture<TriggerComponent>;

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
//         declarations: [TriggerComponent, ParentFormConnectComponent],
//         imports: [FormsModule],
//         providers: [{ provide: IdprestapiService, useValue: idprestapiServiceStub },
//         { provide: IdpService, useValue: idpServiceStub },
//         { provide: IdpdataService, useValue: idpdataserviceStub },
//         { provide: Router, useValue: routerStub }
//         ]
//     })
//         .compileComponents();
//   }));

//   beforeEach(() => {
//     router = TestBed.get(Router);
//     idpService = TestBed.get(IdpService);
//     idpdataService = TestBed.get(IdpdataService);
//     idprestapiService = TestBed.get(IdprestapiService);
//     fixture = TestBed.createComponent(TriggerComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });

//   xit("should be created", () => {
//     expect(component).toBeTruthy();
//   });

//   xit("setArtifact1", () => {
//     expect(component.IDPDataSwitch.buildSelected.toBe("on"));
//     expect(component.IDPParamData.deploy.artifacts.toBe(""));
//     expect(component.IDPParamData.deploy.deployModule.toBe("Module1"));
//     expect(component.IDPParamData.deploy.deployModule.toBe([]));
//     expect(component.setArtifact1()).toEqual("on");
//   });

//   xit("setArtifact1", () => {
//     expect(component.IDPDataSwitch.deploySelected.toEqual("on"));
//     expect(component.IDPParamData.deploy.artifacts.toEqual(""));
//     component.addArtifact();
//     component.checkDBOperation();
//     expect(component.IDPDataSwitch.subModules.toBe("hello"));
//     expect(component.IDPDataSwitch.subModules.length.toBe(1));
//     expect(component.IDPParamData.deploy.deployModule.toBe([]));
//     expect(component.IDPDataSwitch.subModules[0].defaultModule.toEqual("on"));
//     expect(component.IDPParamData.deploy.subModule.push("hello"));
//     expect(component.setArtifact2()).toEqual("on");
//   });

// });
