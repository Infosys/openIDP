/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import { IdprestapiService } from "../idprestapi.service";
import { IdpdataService } from "../idpdata.service";
import { Router } from "@angular/router";
import { ViewChild } from "@angular/core";
import { IdpService } from "../idp-service.service";
import { IDPEncryption } from "../idpencryption.service";
import { constants } from "os";
import { BsModalService } from "ngx-bootstrap/modal";
import { BsModalRef } from "ngx-bootstrap/modal/bs-modal-ref.service";

@Component({
  selector: "app-test-info",
  templateUrl: "./test-info.component.html",
  styleUrls: [],
})
export class TestInfoComponent implements OnInit {
  @ViewChild("modalforRedirect") modalforRedirect;
  @ViewChild("modalforDelTestStep") modalforDelTestStep;
  confirmAlert;
  @ViewChild("modalforAlertDataMiss") modalforAlertDataMiss;
  @ViewChild("modalformandatoryFieldsAlert") modalformandatoryFieldsAlert;
  @ViewChild("modalforMandatorySAPStepswithJira")
  modalforMandatorySAPStepswithJira;
  @ViewChild("modalForConfirmSubmission") modalForConfirmSubmission;
  @ViewChild("modalforDelAntProperties") modalforDelAntProperties;
  @ViewChild("modalforDotNet") dotNetButton;
  @ViewChild("mandatorySCMAlert") mandatorySCMAlert;
  redirectModalRef: BsModalRef;
  dataMissingModalRef: BsModalRef;
  delTestStepModalRef: BsModalRef;
  mandatoryFieldsModalRef: BsModalRef;
  confirmSubmissionModalRef: BsModalRef;
  deleteAntPropModalRef: BsModalRef;
  mandatorySCMModalRef: BsModalRef;
  mandatorySAPStepsModalRef: BsModalRef;

  /*constructor start*/
  constructor(
    public IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router,
    private idpencryption: IDPEncryption,
    private modalService: BsModalService
  ) {
    if (
      this.formStatusObject.operation === "copy" ||
      this.formStatusObject.operation === "edit"
    ) {
      this.checkCheckBox();
    }
  }
  /*constructor end*/
  buildInfo: any = this.IdpdataService.data.buildInfo;
  testInfo: any = this.IdpdataService.data.testInfo;
  tempObjecttest: any = this.IdpdataService.data.checkboxStatus.testInfo;
  formStatusObject = this.IdpdataService.data.formStatus;
  env: any = [];
  applicationDetails: any = this.IdpdataService.application;

  // keys for Test Tool Option
  testCategory: any = [];
  listToFillFields: any = [];
  testTypeName: any = [];
  testframeworkList: any = [
    { name: "JUnit", value: "jUnit" },
    { name: "TestNG", value: "testNG" },
  ];
  caDevTestType: any = [
    { name: "Suite", value: "suite" },
    { name: "Test Case", value: "testCase" },
  ];
  testbrowserList: any = [
    { name: "Google Chrome", value: "chrome" },
    { name: "Internet Explorer", value: "ie" },
    { name: "Firefox", value: "firefox" },
  ];
  msBuildVersion: any = [
    { name: "Default", value: "(Default)" },
    { name: "15.0", value: "MSBUILD_15" },
    { name: "14.0", value: "MSBUILD_14" },
    { name: "12.0", value: "MSBUILD_12" },
    { name: "4.0", value: "MSBUILD_4" },
  ];

  // keys for Run Script Option
  shellScript: any = [];
  batchScript: any = [];
  antScript: any = [];
  iTafBrowserList: any = [
    { name: "Chrome", value: "Chrome" },
    { name: "Mozilla", value: "Mozilla" },
    { name: "Internet Explorer", value: "Internet Explorer" },
    { name: "Sauce Labs Desktop Browser", value: "Sauce Labs Desktop Browser" },
    { name: "Chrome Mobile (android)", value: "Chrome Mobile (android)" },
    { name: "Safari Mobile (iOS)", value: "Safari Mobile (iOS)" },
    { name: "Native App (android)", value: "Native App (android)" },
    {
      name: "Sauce Labs Mobile Browser (android)",
      value: "Sauce Labs Mobile Browser (android)",
    },
    {
      name: "Sauce Labs Mobile Browser (iOS)",
      value: "Sauce Labs Mobile Browser (iOS)",
    },
    {
      name: "Sauce Labs Native App (android)",
      value: "Sauce Labs Native App (android)",
    },
    {
      name: "Sauce Labs Native App (iOS)",
      value: "Sauce Labs Native App (iOS)",
    },
  ];

  testScriptList: any = [
    { name: "ANT Script", value: "ant" },
    { name: "Shell Script", value: "shellScript" },
    { name: "Batch Script", value: "batchScript" },
    { name: "Powershell Script", value: "powerShell" },
    { name: "SSH Execution", value: "sshExecution" },
  ];
  scriptReportType: any = [
    { name: "Single View", value: "singleView" },
    { name: "Serenity", value: "serenity" },
    { name: "Others", value: "others" },
  ];

  // keys for Iterating through Test Steps
  index: any;
  outerIndex: any;
  innerIndex: any;
  object: any;

  loader: any = "off";
  message: any;
  errorMessage: any;
  msg: any;
  loc: any;
  indexI: any = -1;
  indexJ: any = -1;

  TriggerAlert(msg, loc) {
    this.redirectModalRef = this.modalService.show(this.modalforRedirect);
    this.redirectModalRef.content = { msg, loc };
  }

  redirectToSync(modalRef) {
    modalRef.hide();
    if (modalRef.content.loc) {
      this.router.navigate([modalRef.content.loc]);
    }
  }

  /* Checks for duplication of test Step name */
  checkStepName(i, j) {
    // console.log(this.testInfo);
    // console.log(this.testInfo.testEnv[i].testSteps[j].stepName);
    for (let x = 0; x < this.testInfo.testEnv.length; x++) {
      if (
        this.testInfo.testEnv[x] !== undefined &&
        this.testInfo.testEnv[x].testSteps !== undefined
      ) {
        for (let y = 0; y < this.testInfo.testEnv[x].testSteps.length; y++) {
          if (this.tempObjecttest.testEnv[i].testSteps[j] === undefined) {
            this.tempObjecttest.testEnv[i].testSteps[j] = {};
          }
          if (
            j !== y &&
            this.testInfo.testEnv[x].testSteps[y].stepName !== undefined &&
            this.testInfo.testEnv[i].testSteps[j].stepName !== undefined &&
            this.testInfo.testEnv[x].testSteps[y].stepName ===
              this.testInfo.testEnv[i].testSteps[j].stepName
          ) {
            this.tempObjecttest.testEnv[i].testSteps[j].msgStep =
              "Step Name must be unique.";
            break;
          } else {
            this.tempObjecttest.testEnv[i].testSteps[j].msgStep = "";
          }
        }
      }
    }
  }

  /* Addition of test step*/
  addTestStep(index) {
    if (this.testInfo.testEnv[index].testSteps === undefined) {
      this.testInfo.testEnv[index].testSteps = [];
    }
    if (this.tempObjecttest.testEnv === undefined) {
      this.tempObjecttest.testEnv = [];
    }
    if (this.tempObjecttest.testEnv[index] === undefined) {
      this.tempObjecttest.testEnv[index] = {};
    }
    if (this.tempObjecttest.testEnv[index].testSteps === undefined) {
      this.tempObjecttest.testEnv[index].testSteps = [];
    }
    this.testInfo.testEnv[index].testSteps.push({
      test: {
        testCategory: "",
        testTypeName: "",
        frameWork: "",
        browserName: "",
        version: "",
      },
      runScript: {
        scriptType: "",
        reportType: "",
      },
    });
    this.tempObjecttest.testEnv[index].testSteps.push({
      test: {
        testCategory: "",
        testTypeName: "",
        frameWork: "",
        browserName: "",
        version: "",
      },
      runScript: {
        scriptType: "",
        reportType: "",
      },
    });
  }

  removeTestStep(outerIndex, innerIndex) {
    this.delTestStepModalRef = this.modalService.show(this.modalforDelTestStep);
    this.delTestStepModalRef.content = { innerIndex, outerIndex };
  }

  confirmDeleteTestStep(modalRef) {
    this.testInfo.testEnv[modalRef.content.outerIndex].testSteps.splice(
      modalRef.content.innerIndex,
      1
    );
    this.tempObjecttest.testEnv[modalRef.content.outerIndex].testSteps.splice(
      modalRef.content.innerIndex,
      1
    );
    modalRef.hide();
  }

  checkCategory(outerIndex, innerIndex, category) {
    this.innerIndex = innerIndex;
    this.outerIndex = outerIndex;

    /* Functional Testing Options */
    if (category === "functional") {
      if (this.buildInfo.buildtool === "iOS(Swift)") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "Monkey Talk IOS", value: "monkeyTalk" },
          { name: "iTAF", value: "iTAF" },
          { name: "Selenium", value: "selenium" },
          { name: "RFT", value: "rft" },
          { name: "SAHI", value: "sahi" },
          { name: "HP UFT", value: "hpUft" },
          { name: "Cucumber", value: "cucumber" },
          //  {'name':'HP UFT','value':'hpUft'}
        ];
      } else if (this.buildInfo.buildtool === "oracleEBS") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "OATS", value: "oats" },
          { name: "iTAF", value: "iTAF" },
          { name: "Selenium", value: "selenium" },
          { name: "RFT", value: "rft" },
          { name: "SAHI", value: "sahi" },
          { name: "HP UFT", value: "hpUft" },
          { name: "Cucumber", value: "cucumber" },
          //  {'name':'HP UFT','value':'hpUft'}
        ];
      } else if (this.buildInfo.buildtool === "tibco") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "Selenium", value: "selenium" },
          { name: "RFT", value: "rft" },
          //{ 'name': 'Soap UI', 'value': 'soapUI' },
          { name: "SAHI", value: "sahi" },
          { name: "HP UFT", value: "hpUft" },
          { name: "Cucumber", value: "cucumber" },
          //  {'name':'HP UFT','value':'hpUft'}
        ];
      } else if (this.buildInfo.buildtool === "msBuild") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          // { "name": "SAHI", "value": "sahi" }, { "name": "HP UFT", "value": "hpUft" }
          { name: "SAHI", value: "sahi" },

          { name: "Selenium", value: "selenium" },

          { name: "RFT", value: "rft" },
        ];
      } else if (this.buildInfo.buildtool === "maven") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "Selenium", value: "selenium" },

          // { "name": "RFT", "value": "rft" },
          { name: "RFT", value: "rft" },

          // {"name": "Microsoft Test Manager", "value": "mtm"},
          { name: "SAHI", value: "sahi" },
        ];
        // {"name": "HP UFT", "value": "hpUft"},
        // {"name": "HP ALM", "value": "hpAlm"}];
      } else if (this.buildInfo.buildtool === "dotNetCsharp") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "Selenium", value: "selenium" },
          { name: "iTAF", value: "iTAF" },
          { name: "RFT", value: "rft" },
          { name: "Protractor", value: "protractor" },
          { name: "Cucumber", value: "cucumber" },
          { name: "SAHI", value: "sahi" },
        ];

        // {"name": "HP UFT", "value": "hpUft"}, {"name": "HP ALM", "value": "hpAlm"}];
      } else if (this.buildInfo.buildtool === "angular") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "Selenium", value: "selenium" },
          { name: "RFT", value: "rft" },
        ];
      } else if (this.buildInfo.buildtool === "nodeJs") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "Selenium", value: "selenium" },
          { name: "RFT", value: "rft" },
          { name: "SAHI", value: "sahi" },
          // { "name": "HP UFT", "value": "hpUft" },
        ];
      } else if (this.buildInfo.buildtool === "ant") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "Selenium", value: "selenium" },
          { name: "RFT", value: "rft" },
          { name: "SAHI", value: "sahi" },
        ];
        // {"name": "Microsoft Test Manager", "value": "mtm"}];
        // {"name": "HP UFT", "value": "hpUft"},
        // {"name": "HP ALM", "value": "hpAlm"}];
      } else if (this.buildInfo.buildtool === "gradle") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "Monkey Talk", value: "androidmonkeyTalk" },
          { name: "iTAF", value: "iTAF" },
          { name: "Protractor", value: "protractor" },
          { name: "Cucumber", value: "cucumber" },
          { name: "RFT", value: "rft" },
          { name: "Protractor", value: "protractor" },
        ];
      } else if (this.buildInfo.buildtool === "maximo") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "Selenium", value: "selenium" },
          { name: "Cucumber", value: "cucumber" },
          { name: "RFT", value: "rft" },
          { name: "iTAF", value: "iTAF" },
          // { 'name': 'Soap UI', 'value': 'soapUI' },
          { name: "SAHI", value: "sahi" },
          //  {'name':'HP UFT','value':'hpUft'}
        ];
      } else {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "Selenium", value: "selenium" },
          { name: "RFT", value: "rft" },
          { name: "SAHI", value: "sahi" },
        ];
      }
      if (this.IdpdataService.isSAPApplication) {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "eCATT", value: "eCATT" },
          { name: "HP UFT", value: "hpUft" },
          { name: "Cucumber", value: "cucumber" },
        ];
      }
      this.tempObjecttest.testEnv[outerIndex].testSteps[
        innerIndex
      ].testTypeName.push({ name: "HP UFT", value: "hpUft" });
    }
    if (category === "performance") {
      if (
        this.buildInfo.buildtool === "ant" ||
        this.buildInfo.buildtool === "maven" ||
        this.buildInfo.buildtool === "msBuild" ||
        this.buildInfo.buildtool === "nodeJs"
      ) {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [{ name: "JMeter", value: "jMeter" }];
      } else if (this.IdpdataService.isSAPApplication) {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [{ name: "RPT", value: "rpt" }];
      } else if (this.buildInfo.buildtool === "SAPNonCharm") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [{ name: "RPT", value: "rpt" }];
      } else
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "JMeter", value: "jMeter" },
          { name: "RPT", value: "rpt" },
        ];
    }

    if (category === "security") {
      this.tempObjecttest.testEnv[outerIndex].testSteps[
        innerIndex
      ].testTypeName = [{ name: "IBM AppScan Enterprise", value: "appscan" }];
    }
    if (category === "service") {
      this.tempObjecttest.testEnv[outerIndex].testSteps[
        innerIndex
      ].testTypeName = [];
      if (this.buildInfo.buildtool === "msBuild") {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [{ name: "Soap UI", value: "soapUI" }];
      } else {
        this.tempObjecttest.testEnv[outerIndex].testSteps[
          innerIndex
        ].testTypeName = [
          { name: "Soap UI", value: "soapUI" },
          { name: "Parasoft SOA", value: "parasoftsoa" },
        ];
      }
    }
  }

  changeRunScript(outerIndex, innerIndex) {
    this.testInfo.testEnv[outerIndex].testSteps[
      innerIndex
    ].runScript.pathOfFile = "";
    this.testInfo.testEnv[outerIndex].testSteps[innerIndex].runScript.targets =
      "";
    this.testInfo.testEnv[outerIndex].testSteps[innerIndex].runScript.host = "";
    this.testInfo.testEnv[outerIndex].testSteps[innerIndex].runScript.userName =
      "";
    this.testInfo.testEnv[outerIndex].testSteps[innerIndex].runScript.password =
      "";
    this.testInfo.testEnv[outerIndex].testSteps[innerIndex].runScript.script =
      "";
    this.testInfo.testEnv[outerIndex].testSteps[
      innerIndex
    ].runScript.pathToFiles = "";
    this.testInfo.testEnv[outerIndex].testSteps[
      innerIndex
    ].runScript.destinationDir = "";
    this.tempObjecttest.testEnv[outerIndex].testSteps[
      innerIndex
    ].transferFilesFlag = "off";
    this.tempObjecttest.testEnv[outerIndex].testSteps[
      innerIndex
    ].runScript.flattenFilePath = "off";
    this.testInfo.testEnv[outerIndex].testSteps[
      innerIndex
    ].runScript.flattenFilePath = "off";
  }

  cleartransferFilesFlag(outerIndex, innerIndex) {
    this.testInfo.testEnv[outerIndex].testSteps[
      innerIndex
    ].runScript.pathToFiles = "";
    this.testInfo.testEnv[outerIndex].testSteps[
      innerIndex
    ].runScript.destinationDir = "";
    this.testInfo.testEnv[outerIndex].testSteps[
      innerIndex
    ].runScript.flattenFilePath = "off";
  }

  /* resets Data by default empty values
   * on resetting
   */
  resetData() {
    const x = confirm("Are you sure to Reset ?");
    if (x) {
      const val = this.testInfo.testEnv;
      this.testInfo = {
        testEnv: [],
      };
      for (let i = 0; i < val.length; i++) {
        this.testInfo.testEnv.push({
          envName: val[i].envName,
          envFlag: "off",
        });
      }
      this.IdpdataService.data.testInfo = this.testInfo;
      this.IdpdataService.data.checkboxStatus.testInfo = {};
      this.tempObjecttest = this.IdpdataService.data.checkboxStatus.testInfo;
    }
  }

  /* Validation of Test page
   * for test steps selected
   */
  validatePage() {
    let f = true;
    for (let i = 0; i < this.testInfo.testEnv.length; i++) {
      if (
        this.testInfo.testEnv[i].envFlag === "on" &&
        (this.testInfo.testEnv[i].testSteps === undefined ||
          (this.testInfo.testEnv[i].testSteps !== undefined &&
            this.testInfo.testEnv[i].testSteps.length === 0))
      ) {
        f = false;
        break;
      } else if (
        this.testInfo.testEnv[i].envFlag === "on" &&
        this.testInfo.testEnv[i].testSteps !== undefined &&
        this.testInfo.testEnv[i].testSteps.length !== 0
      ) {
        for (let j = 0; j < this.testInfo.testEnv[i].testSteps.length; j++) {
          if (
            (this.tempObjecttest.testEnv[i].testSteps[j].runScriptFlag ===
              undefined ||
              this.tempObjecttest.testEnv[i].testSteps[j].runScriptFlag ===
                "off") &&
            (this.tempObjecttest.testEnv[i].testSteps[j].testToolFlag ===
              undefined ||
              this.tempObjecttest.testEnv[i].testSteps[j].testToolFlag ===
                "off")
          ) {
            f = false;
            break;
          }
        }
      } else if (this.testInfo.testEnv[i].envFlag === "off") {
        continue;
      } else {
        continue;
      }
    }
    if (f === true) {
      //   console.log(f);
      return true;
    } else {
      //   console.log(f);
      return false;
    }
  }
  setClouddata(appName, pipelineName, data) {
    let cloudDeploymentCount = 0;
    const cloudData = {
      appName: appName,
      pipelineName: pipelineName,
      env: [],
    };
    for (let i = 0; i < data.length; i++) {
      cloudData.env.push({ name: data[i].envName, step: [] });
      if (data[i].deploySteps !== undefined) {
        for (let j = 0; j < data[i].deploySteps.length; j++) {
          if (
            data[i].deploySteps[j].cloudDeploymentFlag !== undefined &&
            data[i].deploySteps[j].cloudDeploymentFlag === "on"
          ) {
            const pipeline_new =
              appName +
              "_" +
              pipelineName +
              "_" +
              data[i].envName +
              "_step" +
              j;
            const pipeline_id = this.idpencryption.encryptAES(pipeline_new);
            let id_new = "cfe00b10-f0f3-41c0-8edb-";
            id_new = id_new + pipeline_id.substr(pipeline_id.length - 8);
            data[i].deploySteps[j].cloudData.id = id_new;
            data[i].deploySteps[j].cloudData.application = appName;
            const pipelineJson = JSON.stringify(
              data[i].deploySteps[j].cloudData
            );
            const encryptedPipelineString = this.idpencryption.encryptAES(
              pipelineJson
            );
            cloudData.env[i].step.push({
              index: j + 1,
              pipelineInfo: encryptedPipelineString,
            });
            cloudDeploymentCount++;
          }
        }
      }
    }
    return [cloudData, cloudDeploymentCount];
  }
  submitData(modalRef) {
    this.loader = "on";
    this.IdpdataService.freezeNavBars = true;
    this.IdpdataService.data.testInfo = this.testInfo;
    this.IdpdataService.data.masterJson[
      "basicInfo"
    ] = this.IdpdataService.data.basicInfo;
    this.IdpdataService.data.masterJson[
      "buildInfo"
    ] = this.IdpdataService.data.buildInfo;
    this.IdpdataService.data.masterJson["code"] = this.IdpdataService.data.code;
    this.IdpdataService.data.masterJson[
      "deployInfo"
    ] = this.IdpdataService.data.deployInfo;
    this.IdpdataService.data.masterJson[
      "testInfo"
    ] = this.IdpdataService.data.testInfo;
    this.IdpdataService.data.checkboxStatus.testInfo = this.tempObjecttest;
    const applicationName = this.IdpdataService.data.basicInfo.applicationName;
    const pipelineName = this.IdpdataService.data.basicInfo.pipelineName;
    var count = this.setClouddata(
      applicationName,
      pipelineName,
      this.IdpdataService.data.deployInfo.deployEnv
    );
    var cloudErrorMsg = "failure";
    if (count[1] > 0) {
      var applicationData = {
        job: [
          {
            type: "createApplication",
            application: {
              cloudProviders: "",
              instancePort: 80,
              name: applicationName,
              dataSources: {
                enabled: ["canaryConfigs"],
                disabled: [],
              },
              email: "userName@domain.com",
            },
            user: "[anonymous]",
          },
        ],
        application: applicationName,
        description: "Create Application: applicationName",
      };

      this.IdprestapiService.createCloudApplication(applicationData).then(
        (response) => {
          try {
            let resp = response.json();
            let errorMsg = resp.errorMessage;

            //console.log(resp);
            this.loader = "off";
            //  this.IdpdataService.freezeNavBars=false;
            if (errorMsg === null && resp.status.toLowerCase() === "success") {
              this.IdprestapiService.createCloudPipeline(count[0]).then(
                (response) => {
                  try {
                    let resp = response.json();
                    let errorMsg = resp.errorMessage;

                    //console.log(resp);
                    this.loader = "off";
                    //  this.IdpdataService.freezeNavBars=false;
                    if (
                      errorMsg === null &&
                      resp.status.toLowerCase() === "success"
                    ) {
                      cloudErrorMsg = "success";
                    }
                  } catch (e) {
                    alert("Failed while submiting the trigger job");
                    //console.log(e);
                  }
                  //callPipeline(count[0])
                }
              );
              //   console.log(this.formStatusObject.operation);
            }
          } catch (e) {
            alert("Failed while submiting the trigger job");
            //console.log(e);
          }
        }
      );
    }

    let data = this.idpencryption.doubleDecryptPassword(
      this.IdpdataService.data.masterJson
    );
    data = this.idpencryption.encryptAES(JSON.stringify(data));
    this.IdprestapiService.submit(data).then((response) => {
      modalRef.hide();
      try {
        const resp = response.json();
        const errorMsg = resp.errorMessage;
        this.loader = "off";
        if (errorMsg === null && resp.resource.toLowerCase() === "success") {
          this.message = "success";
          if (this.formStatusObject.operation !== "edit") {
            const actiondata = {
              applicationName: this.IdpdataService.data.masterJson.basicInfo
                .applicationName,
              method: "create",
              pipelineName: this.IdpdataService.data.masterJson.basicInfo
                .pipelineName,
              userName: this.IdpdataService.idpUserName,
            };
            this.IdprestapiService.sendPipeMail(actiondata);
          } else {
            const actiondata = {
              applicationName: this.IdpdataService.data.masterJson.basicInfo
                .applicationName,
              method: "edit",
              pipelineName: this.IdpdataService.data.masterJson.basicInfo
                .pipelineName,
              userName: this.IdpdataService.idpUserName,
            };
            this.IdprestapiService.sendPipeMail(actiondata);
          }
          this.getAppDetails();
        } else {
          this.IdpdataService.freezeNavBars = false;
          this.message = "error";
          this.errorMessage = errorMsg;
        }
      } catch (e) {
        alert("Failed while submiting the trigger job");
        //console.log(e);
      }
    });
  }

  go() {
    if (this.validatePage()) {
      if (
        this.IdpdataService.isSAPApplication &&
        this.buildInfo.buildtool === "SapNonCharm" &&
        this.IdpdataService.data.checkboxStatus.buildInfo.codeAnalysisCheck ===
          "on" &&
        this.IdpdataService.data.checkboxStatus.buildInfo
          .SAPCodeInspectorCheck !== "on"
      ) {
        this.IdpdataService.allFormStatus.buildInfo = false;
      }
      if (
        this.IdpdataService.allFormStatus.basicInfo &&
        this.IdpdataService.allFormStatus.codeInfo &&
        this.IdpdataService.allFormStatus.buildInfo &&
        this.IdpdataService.allFormStatus.deployInfo &&
        this.IdpdataService.allFormStatus.testInfo
      ) {
        let scmCheckBreak = false;
        let operationCheckBreak = false;
        if (
          this.IdpdataService.isSAPApplication &&
          this.buildInfo.buildtool === "SapNonCharm"
        ) {
          const testEnv = this.testInfo.testEnv;
          for (let i = 0; i < testEnv.length; i++) {
            if (testEnv[i].envFlag === "on") {
              const testStep = testEnv[i].testSteps;
              for (let j = 0; j < testStep.length; j++) {
                if (
                  testStep[j].test !== undefined &&
                  (testStep[j].test.testTypeName === "rpt" ||
                    testStep[j].test.testTypeName === "hpUft")
                ) {
                  if (
                    this.IdpdataService.SAPScmCheck === undefined ||
                    this.IdpdataService.SAPScmCheck === "off" ||
                    this.IdpdataService.SAPScmCheck === ""
                  ) {
                    scmCheckBreak = true;
                    break;
                  }
                }
              }
            }
          }
          if (
            this.IdpdataService.data.checkboxStatus.buildInfo
              .RaiseJiraBugonfailureCheck === "on" &&
            this.IdpdataService.data.checkboxStatus.buildInfo
              .codeAnalysisCheck !== "on" &&
            this.IdpdataService.data.checkboxStatus.buildInfo
              .castAnalysisCheck !== "on" &&
            this.IdpdataService.data.buildInfo.modules[0].unitTesting !== "on"
          ) {
            operationCheckBreak = true;
          }
        }
        if (scmCheckBreak) {
          this.mandatorySCMModalRef = this.modalService.show(
            this.mandatorySCMAlert
          );
        } else if (operationCheckBreak) {
          this.mandatorySAPStepsModalRef = this.modalService.show(
            this.modalforMandatorySAPStepswithJira
          );
        } else {
          this.confirmSubmissionModalRef = this.modalService.show(
            this.modalForConfirmSubmission
          );
        }
      } else {
        let listToFillFields = [];
        if (
          !this.IdpdataService.allFormStatus.basicInfo &&
          this.listToFillFields.indexOf("BasicInfo") === -1
        ) {
          listToFillFields.push("BasicInfo");
        }
        if (
          !this.IdpdataService.allFormStatus.codeInfo &&
          this.listToFillFields.indexOf("CodeInfo") === -1
        ) {
          listToFillFields.push("CodeInfo");
        }
        if (
          !this.IdpdataService.allFormStatus.buildInfo &&
          this.listToFillFields.indexOf("BuildInfo") === -1
        ) {
          listToFillFields.push("BuildInfo");
        }
        if (
          !this.IdpdataService.allFormStatus.deployInfo &&
          this.listToFillFields.indexOf("DeployInfo") === -1
        ) {
          listToFillFields.push("DeployInfo");
        }
        if (
          !this.IdpdataService.allFormStatus.testInfo &&
          this.listToFillFields.indexOf("TestInfo") === -1
        ) {
          listToFillFields.push("TestInfo");
        }
        this.mandatoryFieldsModalRef = this.modalService.show(
          this.modalformandatoryFieldsAlert
        );
        this.mandatoryFieldsModalRef.content = { listToFillFields };
      }
    } else {
      this.dataMissingModalRef = this.modalService.show(
        this.modalforAlertDataMiss
      );
    }
  }
  ngOnInit() {
    if (this.IdpdataService.data.formStatus.basicInfo.appNameStatus === "0") {
      this.TriggerAlert("Application Name", "/createConfig/basicInfo");
    } else if (
      this.IdpdataService.data.formStatus.buildInfo.buildToolStatus === "0"
    ) {
      this.TriggerAlert("Technology Type", "/createConfig/codeInfo");
    }

    if (this.buildInfo.buildtool === "SapNonCharm") {
      this.testCategory = [
        { name: "Functional", value: "functional" },
        { name: "Performance", value: "performance" },
      ];
    } else if (
      this.buildInfo.buildtool === "maven" ||
      this.buildInfo.buildtool === "ant" ||
      this.buildInfo.buildtool === "msBuild"
    ) {
      this.testCategory = [
        { name: "Functional", value: "functional" },
        { name: "Performance", value: "performance" },

        { name: "Service", value: "service" },
      ];
    } else if (this.buildInfo.buildtool === "nodeJs") {
      this.testCategory = [
        { name: "Functional", value: "functional" },
        { name: "Performance", value: "performance" },

        { name: "Service", value: "service" },
      ];
    } else if (this.buildInfo.buildtool === "angular") {
      this.testCategory = [
        { name: "Functional", value: "functional" },
        { name: "Performance", value: "performance" },

        { name: "Service", value: "service" },
      ];
    } else if (this.buildInfo.buildtool === "mainframe") {
      this.testCategory = [
        { name: "Functional", value: "functional" },
        { name: "Performance", value: "performance" },

        { name: "Service", value: "service" },
      ];
    } else if (this.buildInfo.buildtool === "tibco") {
      this.testCategory = [
        { name: "Functional", value: "functional" },
        { name: "Performance", value: "performance" },

        { name: "Service", value: "service" },
      ];
    } else if (this.buildInfo.buildtool === "maximo") {
      this.testCategory = [
        { name: "Functional", value: "functional" },

        { name: "Service", value: "service" },
      ];
    } else if (this.buildInfo.buildtool === "php") {
      this.testCategory = [
        { name: "Functional", value: "functional" },
        { name: "Performance", value: "performance" },
        { name: "Security", value: "security" },
        { name: "Service", value: "service" },
      ];
    } else if (this.buildInfo.buildtool === "mssql") {
      this.testCategory = [
        { name: "Functional", value: "functional" },
        { name: "Performance", value: "performance" },

        { name: "Service", value: "service" },
      ];
    } else if (this.buildInfo.buildtool === "tibco") {
      this.testCategory = [
        { name: "Performance", value: "performance" },
        { name: "Service", value: "service" },
      ];
    } else if (this.buildInfo.buildtool === "gradle") {
      this.testCategory = [{ name: "Functional", value: "functional" }];
    } else {
      this.testCategory = [
        { name: "Functional", value: "functional" },
        { name: "Performance", value: "performance" },
        { name: "Service", value: "service" },
      ];
    }
    window.scroll(0, 0);
  }
  redirectToBasicInfo() {
    this.router.navigate(["/createConfig/basicInfo"]);
  }

  // Start Clear Functions for Checkboxes
  clearStep(index) {
    this.testInfo.testEnv[index].testSteps = [];
    if (
      this.tempObjecttest.testEnv !== undefined &&
      this.tempObjecttest.testEnv[index] !== undefined
    ) {
      this.tempObjecttest.testEnv[index].testSteps = [];
    }
    return "off";
  }
  clearRunScript(innerIndex, outerIndex) {
    this.testInfo.testEnv[innerIndex].testSteps[outerIndex].runScript = {
      scriptType: "",
      reportType: "",
    };
    this.tempObjecttest.testEnv[innerIndex].testSteps[outerIndex].runScript = {
      scriptType: "",
      reportType: "",
    };
    return "off";
  }
  clearTest(innerIndex, outerIndex) {
    this.testInfo.testEnv[innerIndex].testSteps[outerIndex].test = {
      testCategory: "",
      testTypeName: "",
      frameWork: "",
      browserName: "",
      version: "",
    };
    this.tempObjecttest.testEnv[innerIndex].testSteps[outerIndex].test = {
      testCategory: "",
      testTypeName: "",
      frameWork: "",
      browserName: "",
      version: "",
    };
    return "off";
  }
  clearLibraryFlag(innerIndex, outerIndex) {
    this.testInfo.testEnv[innerIndex].testSteps[
      outerIndex
    ].test.externalFilePath = "";
    return "off";
  }
  cleardataPool(innerIndex, outerIndex) {
    this.testInfo.testEnv[innerIndex].testSteps[outerIndex].test.iteration = "";
    this.testInfo.testEnv[innerIndex].testSteps[outerIndex].test.fullIteration =
      "";
    return "off";
  }
  clearSharedProject(innerIndex, outerIndex) {
    this.testInfo.testEnv[innerIndex].testSteps[outerIndex].test.userName = "";
    return "off";
  }
  getAppDetails() {
    this.IdprestapiService.getApplicationDetails(
      this.IdpdataService.data.masterJson.basicInfo.applicationName
    ).then((response) => {
      if (response) {
        const resp = response.json().resource;
        let parsed;
        try {
          parsed = JSON.parse(resp);
          if (parsed) {
            this.IdpdataService.application = parsed.appJson;
            this.redirectTo();
          }
        } catch (e) {
          //console.log(e);
          alert("Failed while getting  the pipeline details");
          this.redirectTo();
        }
      }
    });
  }

  redirectTo() {
    setTimeout(() => {
      this.router.navigate(["/createPipeline/success"]);
    }, 3000);
  }
  // End Clear Functions for Checkboxes

  // Start Check Checkbox function
  checkCheckBox() {
    if (this.tempObjecttest.testEnv === undefined) {
      this.tempObjecttest.testEnv = [];
    }

    for (let i = 0; i < this.testInfo.testEnv.length; i++) {
      const envFlag = "off";
      if (this.tempObjecttest.testEnv[i] === undefined) {
        this.tempObjecttest.testEnv[i] = {};
      }
      if (this.tempObjecttest.testEnv[i].testSteps === undefined) {
        this.tempObjecttest.testEnv[i].testSteps = [];
      }
      if (this.testInfo.testEnv[i].testSteps !== undefined) {
        if (this.testInfo.testEnv[i].testSteps.length !== 0) {
          this.testInfo.testEnv[i].envFlag = "on";
        }
        for (let j = 0; j < this.testInfo.testEnv[i].testSteps.length; j++) {
          const runScriptFlag = "off";
          const testToolFlag = "off";
          if (
            this.testInfo.testEnv[i].testSteps[j].runScript !== "" &&
            this.testInfo.testEnv[i].testSteps[j].runScript !== undefined
          ) {
            if (
              this.testInfo.testEnv[i].testSteps[j].runScript.scriptType !==
                "" &&
              this.testInfo.testEnv[i].testSteps[j].runScript.scriptType !==
                undefined
            ) {
              if (this.tempObjecttest.testEnv[i].testSteps[j] === undefined) {
                this.tempObjecttest.testEnv[i].testSteps[j] = {};
              }
              this.tempObjecttest.testEnv[i].testSteps[j].runScriptFlag = "on";
              if (
                this.testInfo.testEnv[i].testSteps[j].runScript.scriptType ===
                "ant"
              ) {
                if (
                  this.testInfo.testEnv[i].testSteps[j].runScript
                    .antPropertiesArr &&
                  this.testInfo.testEnv[i].testSteps[j].runScript
                    .antPropertiesArr[0].antKey !== undefined &&
                  this.testInfo.testEnv[i].testSteps[j].runScript
                    .antPropertiesArr[0].antValue !== undefined
                ) {
                  this.testInfo.testEnv[i].testSteps[j].runScript.antProperty1 =
                    "on";
                }
                if (
                  this.testInfo.testEnv[i].testSteps[j].runScript
                    .javaOptions !== undefined
                ) {
                  this.testInfo.testEnv[i].testSteps[
                    j
                  ].runScript.antJavaOption1 = "on";
                }
              }
            }
          }
          if (
            this.testInfo.testEnv[i].testSteps[j].test.testCategory !== "" &&
            this.testInfo.testEnv[i].testSteps[j].test.testCategory !==
              undefined
          ) {
            if (
              this.testInfo.testEnv[i].testSteps[j].test.testCategory !== "" &&
              this.testInfo.testEnv[i].testSteps[j].test.testCategory !==
                undefined
            ) {
              if (this.tempObjecttest.testEnv[i].testSteps[j] === undefined) {
                this.tempObjecttest.testEnv[i].testSteps[j] = {};
              }
              this.tempObjecttest.testEnv[i].testSteps[j].testToolFlag = "on";
              this.checkCategory(
                i,
                j,
                this.testInfo.testEnv[i].testSteps[j].test.testCategory
              );
            }
          }
          if (
            this.testInfo.testEnv[i].testSteps[j].runScript.pathToFiles !==
              "" &&
            this.testInfo.testEnv[i].testSteps[j].runScript.pathToFiles !==
              undefined
          ) {
            if (
              this.tempObjecttest.testEnv[i].testSteps[j].runScript ===
              undefined
            ) {
              this.tempObjecttest.testEnv[i].testSteps[j].runScript = {};
            }
            this.tempObjecttest.testEnv[i].testSteps[j].transferFilesFlag =
              "on";
            if (
              this.testInfo.testEnv[i].testSteps[j].runScript
                .flattenFilePath === "on"
            ) {
              this.tempObjecttest.testEnv[i].testSteps[
                j
              ].runScript.flattenFilePath = "on";
            }
          }
          this.IdpdataService.data.checkboxStatus.testInfo = this.tempObjecttest;
        }
      } else {
        continue;
      }
    }
  }

  clearTool(i, j) {
    this.testInfo.testEnv[i].testSteps[j].test.testTypeName = "";
    this.clearAllValues(i, j);
  }
  clearAllValues(envIndex, index) {
    this.testInfo.testEnv[envIndex].testSteps[index].test.folderUrl = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.projectName = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.frameWork = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.rootDir = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.testSuiteName = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.externalFilePath = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.scriptPath = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.targets = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.timeout = 60;
    this.testInfo.testEnv[envIndex].testSteps[index].test.serverName = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.userName = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.password = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.domain = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.testCase = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.fullIiteration =
      "off";
    this.testInfo.testEnv[envIndex].testSteps[index].test.iteration = 2;
    this.testInfo.testEnv[envIndex].testSteps[index].test.path = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.serverUrl = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.browserName = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.testPlan = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.version = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.folderUrl = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.projectLocation = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.parameters = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.version = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.arg = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.serviceName = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.authenticationCode =
      "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.sharedProject = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.ownerId = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.buildDefId = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.arg = "";

    this.testInfo.testEnv[envIndex].testSteps[index].test.iTafUser = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.iTafPassword = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.iTafAgent = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.iTafProject = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.iTafBrowser = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.iTafTestSuite = "";
    this.testInfo.testEnv[envIndex].testSteps[index].test.iTafUrl = "";
  }
  // End Check Checkbox function

  setFormStatus(data) {
    this.IdpdataService.allFormStatus.testInfo = data;
  }
  // Antproperties Srart here
  openAntPropertiesField(i, j) {
    this.testInfo.testEnv[i].testSteps[j].runScript.antPropertiesArr = [];
    this.addAntProperties(i, j);
    return "on";
  }
  addAntProperties(i, j) {
    this.testInfo.testEnv[i].testSteps[j].runScript.antPropertiesArr.push({});
  }
  clearAntPropertisField(i, j) {
    this.testInfo.testEnv[i].testSteps[j].runScript.antPropertiesArr = [];
    return false;
  }
  deleteAntProp(index, i, j) {
    this.deleteAntPropModalRef = this.modalService.show(
      this.modalforDelAntProperties
    );
    this.deleteAntPropModalRef.content = { index: index, indexI: i, indexJ: j };
  }
  deleteAntPropConfirm(modalRef) {
    this.testInfo.testEnv[modalRef.content.indexI].testSteps[
      modalRef.content.indexJ
    ].runScript.antPropertiesArr.splice(modalRef.content.index, 1);
    modalRef.hide();
  }
}
