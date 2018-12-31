/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit, TemplateRef, Input } from "@angular/core";
import { IdprestapiService } from "../../idprestapi.service";
import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { NgForm } from "@angular/forms";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { Validators } from "@angular/forms";
import { ViewChild, ElementRef } from "@angular/core";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";

@Component({
  selector: "app-maven-cntrl",
  templateUrl: "./maven-cntrl.component.html",
  styleUrls: ["./maven-cntrl.component.css"]
})
export class MavenCntrlComponent implements OnInit {
  @Input()
  public formName: string;

    @ViewChild("setUncheckedCodeCoverage") public checkbox: ElementRef;

  tempCodeAnalysis: any = ["sonar", "pmd", "checkStyle", "findBugs"];
  tempsecurityAnalysislist: any = ["Checkmarx"];
  tempObject: any = this.IdpdataService.data.checkboxStatus.buildInfo;
  formStatusObject: any = this.IdpdataService.data.formStatus;
  buildInfo: any = this.IdpdataService.data.buildInfo;
  module: any;
  distributionList: any = [];
  constructor(
    public IdpdataService: IdpdataService,
    public   IdpService: IdpService,
    public   IdprestapiService: IdprestapiService,
    public router: Router
  ) {
    if (this.buildInfo.modules.length === 0) {
        this.module = {
        "moduleName": "",
        "relativePath": "",
        "clean": "off",
        "install": "off",
        "args": "",
        "MVNOPTS": "",
        "codeAnalysis": [],
        "ossDistributionType": "",
        "paffFilePath": "",
        "securityServerMachine": "",
        "unitTesting": "off",
        "codeCoverage": "off",
        "interval": ""
        } ;
        this.tempObject.modules = [{"codeAnalysis": ""}];
        this.tempObject.modules[0].codeAnalysis = "off";
        this.buildInfo.modules.push(this.module);
    }
    if (this.formStatusObject.operation === "copy"
        || this.formStatusObject.operation === "edit") {
        this.checkCheckBox();
    }
  }
  ngOnInit() {
    this.distributionList = [{
        "name": "Internal",
        "value": "Internal"
    },
    {
        "name": "Hosted Service (Infosys Infrastructure) ",
        "value": "Hosted Service"
    },
    {
        "name": "Bundling",
        "value": "Bundling"
    },
    {
        "name": "Dynamic Library",
        "value": "Dynamic Library"
    },
    {
        "name": "Deliverable Application",
        "value": "Deliverable Application"
    }];
  }
SetcodeCoverage() {
  this.buildInfo.modules[0].codeCoverage = "off";
  this.checkbox.nativeElement.checked = false;
  return false;
}
  setcodeAnalysisPaTrue() {
    this.buildInfo.modules[0].codeAnalysis = [];
    this.IdpdataService.pa = true;
    return false;
  }
  setpaFalse() {
    this.IdpdataService.pa = false;
    return false;
  }
  setpaTrue() {
    this.IdpdataService.pa = true;
    return false;
  }
  setcodeAnalysis() {
    this.buildInfo.modules[0].codeAnalysis[3] = "off";
  }
  setContinuecontrolFalse() {
    this.IdpdataService.continuecontrol = false;
    return false;
  }
  setContinuecontrolTrue() {
    this.IdpdataService.continuecontrol = true;
    return false;
  }
  codeAnalysisCheckbox(checked) {
    if (checked) {
        this.tempObject.modules[0].codeAnalysis = "on";
    } else {
        this.tempObject.modules[0].codeAnalysis = "off";
    }
    if (this.tempObject.modules[0].codeAnalysis === "off") {
        this.buildInfo.modules[0].codeAnalysis = [];
    } else {
        this.buildInfo.modules[0].codeAnalysis = ["off", "off", "off", "off", "off"];
    }
  }
  checkCodeAnalysisOn() {
    if (this.tempObject.modules[0].codeAnalysis === "on") {
        if (this.buildInfo.modules[0].codeAnalysis[0] === "off"
        && this.buildInfo.modules[0].codeAnalysis[0] === "off"
        && this.buildInfo.modules[0].codeAnalysis[2] === "off"
        && this.buildInfo.modules[0].codeAnalysis[3] === "off"
        && this.buildInfo.modules[0].codeAnalysis[4] === "off") {
            this.IdpdataService.pa = false;
        } else {
        this.IdpdataService.pa = true;
        }
    } else {
        this.IdpdataService.pa = true;
    }
  }
  openSubModule() {
    this.buildInfo.subModule =
    [{"moduleName": "",
            "defaultModule": ""}];
        return "on";
  }
  clearSubModule() {
      this.buildInfo.subModule = [];
      return "off";
  }
  addSubModule() {
        this.buildInfo.subModule.push({"moduleName": "",
                                    "defaultModule": ""});
  }
  deleteSubModule(i) {
         this.buildInfo.subModule.splice(i, 1);
  }
  checkCheckBox() {
    if (this.tempObject.modules === undefined) {
        this.tempObject.modules = [];
    }
    for (let i = 0; i < this.buildInfo.modules.length; i++) {
        let codeAnalysis = "off";
        let securityAnalysis = "off";
        let schdulePeriodicFullScan = "off";
        let ossCheck = "off";
        if (this.buildInfo.modules[i].interval !== ""
        && this.buildInfo.modules[i].interval !== undefined
        && this.buildInfo.modules[i].interval !== null) {
        schdulePeriodicFullScan = "on";
        }
        if (this.buildInfo.modules[i].interval === ""
        || this.buildInfo.modules[i].interval === undefined
        || this.buildInfo.modules[i].interval === null) {
        schdulePeriodicFullScan = "off";
        }
        if (this.buildInfo.modules[i].codeAnalysis.length !== 0) {
        codeAnalysis = "on";
        }
        if (this.buildInfo.modules[i].pafFilePath !== ""
        && this.buildInfo.modules[i].pafFilePath !== undefined) {
        securityAnalysis = "on";
        }
        if (this.buildInfo.modules[i].ossMailRecipients !== ""
        && this.buildInfo.modules[i].ossMailRecipients !== undefined) {
        ossCheck = "on";
        }
        this.tempObject.modules.push({
        "codeAnalysis": codeAnalysis,
        "securityAnalysis": securityAnalysis,
        "schdulePeriodicFullScan": schdulePeriodicFullScan,
        "ossCheck": ossCheck
        });
    }
    if (this.buildInfo.subModule === undefined) {
        this.buildInfo.subModule = [];
    }
    for (let i = 0; i < this.buildInfo.subModule.length; i++) {
            this.tempObject.subModuleAction = "on";
    }
  }

  /* Initializing OSS inputs on checking and unchecking */
  ossCompliance(index,checked){
    if (checked) {
      this.tempObject.modules[index].ossCheck = "on";
    } 
    else {
      this.tempObject.modules[index].ossCheck = "off";
      this.buildInfo.modules[index].ossMailRecipients = "";
      this.buildInfo.modules[index].ossDistributionType = "";
      this.buildInfo.modules[index].ossAnalysisType = "";
    }
  }
}
