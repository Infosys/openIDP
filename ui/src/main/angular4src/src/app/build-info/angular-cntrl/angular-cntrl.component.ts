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
import { ChangeDetectorRef, ChangeDetectionStrategy } from "@angular/core";
import { ViewChild, ElementRef } from "@angular/core";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";

@Component({
  selector: "app-angular-cntrl",
  templateUrl: "./angular-cntrl.component.html",
  styleUrls: [],
})
export class AngularCntrlComponent implements OnInit {
  @Input()
  public formName: string;

  constructor(
    public IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router
  ) {
    if (this.buildInfo.modules.length === 0) {
      this.tempObject.modules = [{ codeAnalysis: "" }];
      this.module = {
        relativePath: "",
        jsonPath: "",
        moduleName: "",
        npmProxy: "",
        buildValue: "",
        unitTesting: "off",
        codeCoverage: "off",
        codeAnalysis: [],
        unitTestTool: [],
        codeCoverageTool: [],
      };

      this.buildInfo.modules.push(this.module);
      // console.log(this.buildInfo.modules[0].codeAnalysis);
    }
    this.checkCheckBox();
    this.IdpdataService.data.buildInfo = this.buildInfo;
    this.IdpdataService.data.checkboxStatus.buildInfo = this.tempObject;
  }

  ngOnInit() {}

  // Temporary Object for checkboxes
  tempObject = this.IdpdataService.data.checkboxStatus.buildInfo;
  formStatusObject = this.IdpdataService.data.formStatus;
  buildInfo: any = this.IdpdataService.data.buildInfo;
  module: any;

  codeAnalysisCheckbox() {
    if (this.tempObject.modules[0].codeAnalysis === "on") {
      if (
        (this.buildInfo.modules[0].codeAnalysis[0] === "off" ||
          this.buildInfo.modules[0].codeAnalysis[0] === undefined) &&
        (this.buildInfo.modules[0].codeAnalysis[1] === "off" ||
          this.buildInfo.modules[0].codeAnalysis[1] === undefined)
      ) {
        this.IdpdataService.pa = false;
      } else {
        this.IdpdataService.pa = true;
      }
    } else if (this.tempObject.modules[0].codeAnalysis === "off") {
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

  checkCompile() {
    // console.log(this.tempObject.modules[0].build);
    if (this.tempObject.modules[0].build === "on") {
      if (
        this.buildInfo.modules[0].buildValue == null ||
        this.buildInfo.modules[0].buildValue === undefined ||
        this.buildInfo.modules[0].buildValue === ""
      ) {
        this.IdpdataService.compMove = false;
      } else {
        this.IdpdataService.compMove = true;
      }
    }
    if (this.tempObject.modules[0].build === "off") {
      this.IdpdataService.compMove = true;
    }
  }

  checkCheckBox() {
    if (this.tempObject.modules === undefined) {
      this.tempObject.modules = [];
    }

    for (let i = 0; i < this.buildInfo.modules.length; i++) {
      let codeAnalysis = "off";
      let compile = "off";
      let build = "off";
      if (
        this.buildInfo.modules[i].jsonPath !== null &&
        this.buildInfo.modules[i].jsonPath !== undefined &&
        this.buildInfo.modules[i].jsonPath !== ""
      ) {
        compile = "on";
      }
      if (
        this.buildInfo.modules[i].angularBuildCommand !== null &&
        this.buildInfo.modules[i].angularBuildCommand !== undefined &&
        this.buildInfo.modules[i].angularBuildCommand !== ""
      ) {
        build = "on";
      }

      if (this.buildInfo.modules[i].codeAnalysis.length !== 0) {
        codeAnalysis = "on";
      }

      this.tempObject.modules.push({
        codeAnalysis: codeAnalysis,
        compile: compile,
        build: build,
      });
    }
  }

  jsonPathEmpty() {
    this.buildInfo.modules[0].jsonPath = "";
    return false;
  }

  compileOff() {
    this.buildInfo.modules[0].codeAnalysis[1] = "off";
    this.tempObject.modules[0].build = "off";
    this.tempObject.modules[0].codeAnalysis = "off";
    this.buildInfo.modules[0].noViolations = "off";
    this.buildInfo.modules[0].allUnitTestPass = "off";
    return false;
  }

  buildOn() {
    this.tempObject.modules[0].build = "on";
    return false;
  }

  compMoveTrue() {
    this.IdpdataService.compMove = true;
    return false;
  }

  compMoveFalse() {
    this.IdpdataService.compMove = false;
    return false;
  }

  buildValueEmpty() {
    this.buildInfo.modules[0].angularBuildCommand = "";
    return "off";
  }

  noViolationsOff() {
    this.buildInfo.modules[0].noViolations = "off";
    return false;
  }

  codeAnalysisOff() {
    this.buildInfo.modules[0].codeAnalysis = [];
    this.buildInfo.modules[0].noViolations = "off";
    this.IdpdataService.pa = true;
    return false;
  }

  paTrue() {
    this.IdpdataService.pa = true;
    return false;
  }
  paFalse() {
    this.IdpdataService.pa = false;
    return false;
  }

  unitTestingOff() {
    this.buildInfo.modules[0].unitTesting = "off";
    return false;
  }

  unitTestToolOff() {
    this.buildInfo.modules[0].unitTestTool[0] = "off";
    this.buildInfo.modules[0].codeCoverage = "off";
    this.buildInfo.modules[0].codeCoverageTool[0] = "off";
    return "off";
  }

  allUnitTestPassOff() {
    this.buildInfo.modules[0].allUnitTestPass = "off";
    return false;
  }

  unitTestProjectNameEmpty() {
    this.buildInfo.modules[0].unitTestProjectName = "";
    return "off";
  }

  unitTrue() {
    this.IdpdataService.unit = true;
    return false;
  }

  unitFalse() {
    this.IdpdataService.unit = false;
    return false;
  }

  codeCoverageToolOff() {
    this.buildInfo.modules[0].codeCoverageTool[0] = "off";
    return "off";
  }

  codeTrue() {
    this.IdpdataService.code = true;
    return false;
  }

  codeFalse() {
    this.IdpdataService.code = false;
    return false;
  }

  codeCoverageOff() {
    this.buildInfo.modules[0].codeCoverage = "off";
    this.buildInfo.modules[0].codeCoverageTool[0] = "off";
    return false;
  }

  clearAll() {
    this.tempObject.modules[0].build = "off";
    this.buildInfo.modules[0].buildValue = "";
    this.tempObject.modules[0].codeAnalysis = "off";
    this.buildInfo.modules[0].codeAnalysis[0] = "off";
    this.buildInfo.modules[0].noViolations = "off";
    this.buildInfo.modules[0].unitTesting = "off";
    this.buildInfo.modules[0].unitTestTool[0] = "off";
    this.buildInfo.modules[0].codeCoverage = "off";
    this.buildInfo.modules[0].codeCoverageTool[0] = "off";
    this.buildInfo.modules[0].jsonPath = "";
    this.buildInfo.modules[0].npmProxyUserName = "";
    this.buildInfo.modules[0].npmProxyPassword = "";
    this.buildInfo.modules[0].npmProxy = "";
    return "off";
  }

  clearSonarqube() {
    this.buildInfo.modules[0].sonarUrl = "";
    this.buildInfo.modules[0].sonarUserName = "";
    this.buildInfo.modules[0].sonarPassword = "";
    this.buildInfo.modules[0].sonarProjectKey = "";

    return "off";
  }

  setBuildInfoValue(value) {
    // console.log(value);
  }
}
