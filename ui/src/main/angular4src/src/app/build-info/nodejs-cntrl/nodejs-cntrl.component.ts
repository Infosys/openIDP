/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit, Input } from "@angular/core";
import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { IdprestapiService } from "../../idprestapi.service";
import { Router } from "@angular/router";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";

@Component({
  selector: "app-nodejs-cntrl",
  templateUrl: "./nodejs-cntrl.component.html",
  styleUrls: [],
})
export class NodejsCntrlComponent implements OnInit {
  tempsecurityAnalysislist = ["Checkmarx"];
  // Temporary Object for checkboxes
  tempObject = this.IdpdataService.data.checkboxStatus.buildInfo;
  tempCodeAnalysis = ["sonar", "checkStyle"];

  module: any;
  buildInfo: any = this.IdpdataService.data.buildInfo;
  formStatusObject = this.IdpdataService.data.formStatus;
  @Input()
  public formName: string;

  constructor(
    public IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router
  ) {
    if (this.IdpdataService.data.basicInfo.buildServerOS === "linux") {
      this.IdpdataService.osFlag = true;
    }

    if (this.IdpdataService.data.basicInfo.buildServerOS === "windows") {
      this.IdpdataService.osFlag = false;
    }

    // if(this.IdpdataService.data.buildInfo.modules.length !== 0){
    // 	if(this.IdpdataService.osFlag==true || this.IdpdataService.data.basicInfo.buildServerOS==="linux"){
    //     	this.buildInfo.modules[0].installGrunt="on";
    //
    //     }
    //
    //     if(this.IdpdataService.osFlag==false && this.IdpdataService.op !== "edit" && this.IdpdataService.op !== "copy"){
    //     	this.buildInfo.modules[0].installGrunt="off";
    //
    //     }
    // }

    // this.buildInfo = this.IdpdataService.data.buildInfo;
    if (this.buildInfo.modules.length === 0) {
      this.buildInfo.modules = [];
      this.tempObject.modules = [];
      this.module = {
        codeFormatting: "off",
        relativePath: "",
        customScript: "",
        unitTestProjectName: "",
        unitTesting: "off",
        codeCoverage: "off",
        codeAnalysis: [],
      };
      this.buildInfo.modules.push(this.module);
      this.tempObject.modules.push({});
      if (this.IdpdataService.osFlag === true) {
        this.buildInfo.modules[0].installGrunt = "on";
        // this.tempObject.modules[0].installGrunt="on";
      }

      if (this.IdpdataService.osFlag === false) {
        this.buildInfo.modules[0].installGrunt = "off";
        // this.tempObject.modules[0].installGrunt="off";
      }

      // console.log(this.tempObject);
    }

    if (
      this.formStatusObject.operation === "copy" ||
      this.formStatusObject.operation === "edit"
    ) {
      this.checkCheckBox();
    }

    this.IdpdataService.data.buildInfo = this.buildInfo;
    this.IdpdataService.data.checkboxStatus.buildInfo = this.tempObject;
  }

  clearAll() {
    this.buildInfo.modules[0].npmProxyUserName = "";
    this.buildInfo.modules[0].npmProxyPassword = "";
    this.buildInfo.modules[0].npmProxy = "";
    return "off";
  }

  ngOnInit() {}

  codeAnalysisCheckbox() {
    if (this.tempObject.modules[0].codeAnalysis === "on") {
      if (
        this.buildInfo.modules[0].codeAnalysis[0] === "off" &&
        this.buildInfo.modules[0].codeAnalysis[1] === "off"
      ) {
        this.IdpdataService.pa = false;
      } else {
        this.IdpdataService.pa = true;
      }
    } else if (this.tempObject.modules[0].codeAnalysis === "off") {
      // this.buildInfo.modules[0].codeAnalysis = ["off","off"];
      // console.log(this.buildInfo.modules[0].codeAnalysis);
      this.IdpdataService.pa = true;
    }
  }

  codeAnalysisCheck() {
    if (
      this.buildInfo.modules[0].codeAnalysis[0] === "off" &&
      this.buildInfo.modules[0].codeAnalysis[1] === "off"
    ) {
      this.IdpdataService.pa = false;
    } else {
      this.IdpdataService.pa = true;
    }
  }

  closeCompile() {
    this.buildInfo.modules[0].jsonPath = "";
    this.buildInfo.modules[0].codeFormatting = "off";
    this.tempObject.modules[0].checkRunApp = "off";
    this.buildInfo.modules[0].customScript = "";
    this.buildInfo.modules[0].unitTesting = "off";
    this.buildInfo.modules[0].unitTestProjectName = "";
    this.buildInfo.modules[0].codeCoverage = "off";
    if (this.buildInfo.modules[0].codeAnalysis.length !== 0) {
      this.buildInfo.modules[0].codeAnalysis[1] = "off";
    }
    return "off";
  }

  closeCodeAnalysis() {
    this.buildInfo.modules[0].codeAnalysis = [];
    this.buildInfo.modules[0].excludeFolders = "";
    this.tempObject.modules[0].excludeDirToScan = "off";
    return "off";
  }

  OpenCodeAnalysys() {
    this.buildInfo.modules[0].codeAnalysis = ["off", "off"];
    return "on";
  }

  excludeDirToScanOff() {
    this.buildInfo.modules[0].excludeFolders = "";
    return "off";
  }

  checkCodeAOn() {
    if (this.buildInfo.modules[0].codeAnalysis.length !== 0) {
      if (
        this.buildInfo.modules[0].codeAnalysis[0] === "off" &&
        this.buildInfo.modules[0].codeAnalysis[1] === "off"
      ) {
        this.buildInfo.modules[0].excludeFolders = "";
        this.tempObject.modules[0].excludeDirToScan = "off";
      }
    }
  }

  closeCheckRun() {
    this.buildInfo.modules[0].customScript = "";
    return "off";
  }

  closeUnitTest() {
    this.buildInfo.modules[0].unitTestProjectName = "";
    this.buildInfo.modules[0].codeCoverage = "off";
    return "off";
  }

  /* codeAnalysisCheckNewZero(event){

      if(event != false){

          this.buildInfo.modules[0].codeAnalysis[0]="sonar";

      }
      else{
        this.buildInfo.modules[0].codeAnalysis[0]="off";
      }
          if(this.buildInfo.modules[0].codeAnalysis[0]==="off" && this.buildInfo.modules[0].codeAnalysis[1]==="off"){
            this.pa=false;
          }
          else{
            this.pa=true;
          }
      }

  codeAnalysisCheckNewOne(event){

      if(event != false){

          this.buildInfo.modules[0].codeAnalysis[1]="checkStyle";

      }
      else{
        this.buildInfo.modules[0].codeAnalysis[1]="off";
      }
          if(this.buildInfo.modules[0].codeAnalysis[0]==="off" && this.buildInfo.modules[0].codeAnalysis[1]==="off"){
            this.pa=false;
          }
          else{
            this.pa=true;
          }
      }
       */

  checkCheckBox() {
    if (this.tempObject.modules === undefined) {
      this.tempObject.modules = [];
    }

    // console.log(this.buildInfo.modules[0].interval);

    for (let i = 0; i < this.buildInfo.modules.length; i++) {
      let codeAnalysis = "off";
      let securityAnalysis = "off";
      let install = "off";
      let compile = "off";
      let checkRunApp = "off";
      let incrementalScan = "off";
      let schdulePeriodicFullScan = "off";
      let excludeDirToScan = "off";
      let depInstall = "off";
      let installGrunt = "off";

      if (
        this.buildInfo.modules[i].interval !== "" &&
        this.buildInfo.modules[i].interval !== undefined
      ) {
        incrementalScan = "on";
        schdulePeriodicFullScan = "on";
      }

      if (
        this.buildInfo.modules[i].relativePath !== null &&
        this.buildInfo.modules[i].relativePath !== undefined &&
        this.buildInfo.modules[i].relativePath !== ""
      ) {
        install = "on";
      }

      if (
        this.buildInfo.modules[i].jsonPath !== null &&
        this.buildInfo.modules[i].jsonPath !== undefined &&
        this.buildInfo.modules[i].jsonPath !== ""
      ) {
        compile = "on";
      }

      if (
        this.buildInfo.modules[i].customScript !== null &&
        this.buildInfo.modules[i].customScript !== undefined &&
        this.buildInfo.modules[i].customScript !== ""
      ) {
        checkRunApp = "on";
      }

      if (this.buildInfo.modules[i].codeAnalysis.length !== 0) {
        codeAnalysis = "on";
      }
      if (
        this.buildInfo.modules[i].pafFilePath !== "" &&
        this.buildInfo.modules[i].pafFilePath !== undefined
      ) {
        securityAnalysis = "on";
      }

      if (
        this.buildInfo.modules[i].excludeFolders !== undefined &&
        this.buildInfo.modules[i].excludeFolders !== ""
      ) {
        excludeDirToScan = "on";
      }

      if (
        this.buildInfo.modules[i].npmProxy !== undefined &&
        this.buildInfo.modules[i].npmProxy !== ""
      ) {
        depInstall = "on";
      }

      if (
        this.buildInfo.modules[i].installGrunt !== undefined &&
        this.buildInfo.modules[i].installGrunt !== ""
      ) {
        installGrunt = "on";
      }

      this.tempObject.modules.push({
        codeAnalysis: codeAnalysis,
        securityAnalysis: securityAnalysis,
        install: install,
        compile: compile,
        checkRunApp: checkRunApp,
        incrementalScan: incrementalScan,
        schdulePeriodicFullScan: schdulePeriodicFullScan,
        excludeDirToScan: excludeDirToScan,
        depInstall: depInstall,
        installGrunt: installGrunt,
      });
    }
  }

  installGruntOn() {
    if (this.IdpdataService.osFlag === true) {
      this.buildInfo.modules[0].installGrunt = "on";
    }
    // else{
    //   this.buildInfo.modules[0].installGrunt = "off";
    // }

    return false;
  }
}
