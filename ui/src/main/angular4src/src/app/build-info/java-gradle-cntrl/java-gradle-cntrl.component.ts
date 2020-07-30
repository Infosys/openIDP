import { Component, OnInit, Input } from "@angular/core";
import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { IdprestapiService } from "../../idprestapi.service";
import { Router } from "@angular/router";
import { ChangeDetectorRef, ChangeDetectionStrategy } from "@angular/core";
import { ViewChild, ElementRef } from "@angular/core";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";

@Component({
  selector: "app-java-gradle-cntrl",
  templateUrl: "./java-gradle-cntrl.component.html",
  styleUrls: [],
})
export class JavaGradleCntrlComponent implements OnInit {
  @Input()
  public formName: string;

  tempObject = this.IdpdataService.data.checkboxStatus.buildInfo;
  formStatusObject = this.IdpdataService.data.formStatus;
  buildInfo: any = this.IdpdataService.data.buildInfo;
  module: any;

  constructor(
    public IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router
  ) {
    if (this.buildInfo.modules.length === 0) {
      this.tempObject.modules = [{ codeAnalysis: "off" }];

      this.module = {
        moduleName: "",
        codeAnalysis: [],
      };
      // console.log(this.buildInfo.modules);
      this.buildInfo.modules.push(this.module);
      // this.codeAnalysisOn();
      // console.log(this.buildInfo.modules[0].codeAnalysis);
    }

    if (
      this.formStatusObject.operation === "copy" ||
      this.formStatusObject.operation === "edit"
    ) {
      this.checkCheckBox();
    }
  }

  checkCheckBox() {
    if (this.tempObject.modules === undefined) {
      this.tempObject.modules = [];
    }

    for (var i = 0; i < this.buildInfo.modules.length; i++) {
      var codeAnalysis = "off";

      if (this.buildInfo.modules[i].codeAnalysis.length !== 0) {
        codeAnalysis = "on";
      }

      this.tempObject.modules.push({
        codeAnalysis: codeAnalysis,
      });
    }
  }

  codeAnalysisCheckbox() {
    // console.log(this.tempObject.modules[0].codeAnalysis);
    if (this.tempObject.modules[0].codeAnalysis === "on") {
      if (
        (this.buildInfo.modules[0].codeAnalysis[0] == undefined ||
          this.buildInfo.modules[0].codeAnalysis[0] == "off") &&
        (this.buildInfo.modules[0].codeAnalysis[1] == undefined ||
          this.buildInfo.modules[0].codeAnalysis[1] == "off")
      ) {
        // if ((this.buildInfo.modules[0].codeAnalysis[0] == undefined || this.buildInfo.modules[0].codeAnalysis[0] == 'off' )) {
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
      // if (this.buildInfo.modules[0].codeAnalysis[0] === 'off' ) {
      this.IdpdataService.pa = false;
    } else {
      this.IdpdataService.pa = true;
    }
  }

  unitTestInputOff() {
    this.buildInfo.modules[0].args = "";
    this.buildInfo.modules[0].codeCoverage = "off";
    return "off";
  }

  clearCodeCoverage() {
    this.buildInfo.modules[0].args = "";
    return "off";
  }

  clearSonarqube() {
    this.buildInfo.modules[0].sonarUrl = "";
    this.buildInfo.modules[0].sonarUserName = "";
    this.buildInfo.modules[0].sonarPassword = "";
    this.buildInfo.modules[0].sonarProjectKey = "";

    return "off";
  }

  codeAnalysisOn() {
    this.buildInfo.modules[0].codeAnalysis = ["sonar", "on"];
    return "on";
  }

  codeAnalysisOff() {
    this.buildInfo.modules[0].codeAnalysis = [];
    return "off";
  }

  ngOnInit() {}
}
