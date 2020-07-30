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
import { ViewChild } from "@angular/core";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";

@Component({
  selector: "app-go-cntrl",
  templateUrl: "./go-cntrl.component.html",
  styleUrls: []
})
export class GoCntrlComponent implements OnInit {
  tempCodeAnalysis = ["checkStyle", "sonar"];
  tempObject = this.IdpdataService.data.checkboxStatus.buildInfo;
  formStatusObject = this.IdpdataService.data.formStatus;
  buildInfo: any = this.IdpdataService.data.buildInfo;
  module: any;


  @Input()
  public formName: string;

  constructor(private IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router) {

    if (this.buildInfo.modules.length === 0) {
        this.module = {
        "projectPath": "",
        "codeAnalysis": [],
        "build": "",
        "moduleName": "",
        "relativePath": ""

        };
        this.buildInfo.modules.push(this.module);
    }

    this.checkCheckBox();
    this.IdpdataService.data.buildInfo = this.buildInfo;
    this.IdpdataService.data.checkboxStatus.buildInfo = this.tempObject;
  }
  ngOnInit() {
  }

  /**
  *
  * Code Analysis Check
  *
  **/
  codeAnalysisCheckbox() {
    if (this.tempObject.modules[0].codeAnalysis === "on") {
        this.buildInfo.modules[0].codeAnalysis = [null, null];
    }
  }

  /**
  *
  * Check of sonar, code analysis tool
  *
  **/
  check() {
    if (this.buildInfo.modules[0].codeAnalysis[1] === "sonar") {
        this.buildInfo.modules[0].codeAnalysis[0] = "checkStyle";
    } else if (this.buildInfo.modules[0].codeAnalysis[1] === "off"
        || this.buildInfo.modules[0].codeAnalysis[1] == null) {
        this.buildInfo.modules[0].codeAnalysis[0] = "off";
    }
  }

  /**
  *
  * Checks for Go Module, Compile, code analysis and proxy url,
  *if defined, push it to json
  **/
  checkCheckBox() {
    if (this.tempObject.modules === undefined) {
        this.tempObject.modules = [];
    }
    for (let i = 0; i < this.buildInfo.modules.length; i++) {
        let codeAnalysis = "off";
        let setProxy = "off";
        if (this.buildInfo.modules[i].codeAnalysis.length !== 0) {
        codeAnalysis = "on";
        }
        if (this.buildInfo.modules[i].npmProxy !== "") {
        setProxy = "on";
        }
        this.tempObject.modules.push({
        "codeAnalysis": codeAnalysis,
        "compile": setProxy
        });
    }
  }
  paFalse() {
    this.IdpdataService.pa = false;
    return false;
  }

  paTrue() {
    this.IdpdataService.pa = true;
    return false;
  }
  codeAnalysisEmpty() {
    this.buildInfo.modules[0].codeAnalysis = [];
    this.IdpdataService.pa = true;
    return "off";
  }

  codeCoverageOffUnitTestDirEmpty() {
    this.buildInfo.modules[0].unitTestDir = "";
    this.buildInfo.modules[0].codeCoverage = "off";
    return "off";
  }

  /**
  *
  *Clears proxy, on reset
  *
  **/
  clearProxyDetails() {
    this.buildInfo.modules[0].npmProxy = "";
    this.buildInfo.modules[0].npmProxyUserName = "";
    this.buildInfo.modules[0].npmProxyPassword = "";
    return "off";
  }
  /**
  *
  * Set "" or off, if code coverage is unselected or on reset
  *
  **/
  codeCoverageOff() {
    this.buildInfo.modules[0].unitTestDir = "";
    this.buildInfo.modules[0].codeCoverage = "off";
    this.buildInfo.modules[0].unitTesting = "off";
    return "off";
  }
  clearSonarqube() {
    this.buildInfo.modules[0].sonarUrl ="";
    this.buildInfo.modules[0].sonarUserName ="";
    this.buildInfo.modules[0].sonarPassword ="";
    this.buildInfo.modules[0].sonarProjectKey ="";
    this.buildInfo.modules[0].sonarProperties ="";
  
  
      return "off";
    }
}
