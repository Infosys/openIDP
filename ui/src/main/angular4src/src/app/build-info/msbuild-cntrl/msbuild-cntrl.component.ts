/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit, TemplateRef } from "@angular/core";
import { IdprestapiService } from "../../idprestapi.service";
import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { NgForm } from "@angular/forms";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { Validators } from "@angular/forms";
import { ViewChild, Input } from "@angular/core";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";

@Component({
  selector: "app-msbuild-cntrl",
  templateUrl: "./msbuild-cntrl.component.html",
  styleUrls: []
})
export class MsbuildCntrlComponent implements OnInit {

  @Input()
  public formName: string;
  tempsecurityAnalysislist: any = ["Checkmarx"];
  buildInfo: any = this.IdpdataService.data.buildInfo;
  tempObject: any = this.IdpdataService.data.checkboxStatus.buildInfo;
  formStatusObject: any = this.IdpdataService.data.formStatus;
  msBuildVersion: any = this.IdprestapiService.getIDPDropdownProperties().msBuildVersion;
  tempCodeAnalysis: any;
  distributionList: any = [];
  solutions:Array<number> = [];
  /* CONSTRUCTOR */
  constructor(
    private IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router
  ) {
    if (this.formStatusObject.operation === "copy"
        || this.formStatusObject.operation === "edit") {
        this.checkCheckBox();
    }
    if (this.buildInfo.modules.length === 0) {
        this.addItem();
    }
    this.tempCodeAnalysis = ["sonar", "fxcop"];
  }
  /* Clear UnitTest on unchecking or reseting */
  clearUnitTest(key) {
    this.buildInfo.modules[key].unitTestProjectName = "";
    this.buildInfo.modules[key].unitTestCategory = "";
    this.buildInfo.modules[key].codeCoverage = "off";
    this.buildInfo.modules[key].testSettingFilePath = "";
    return "off";
  }
  /* Clears Code Coverage on unchecking or reetting */
  clearCodeCoverage(key) {
    this.buildInfo.modules[key].testSettingFilePath = "";
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
  setpaFalse() {
    this.IdpdataService.pa = false;
    return false;
  }
  setpaTrue() {
    this.IdpdataService.pa = true;
    return false;
  }
  /* Setting Code Analysis */
  setpaCodeAnalysis(i) {
    this.buildInfo.modules[i].codeAnalysis = [];
    this.IdpdataService.pa = true;
    return false;
  }
  setSomeValues1() {
    this.tempObject.modules[0].Checkmarx = "";
    this.tempObject.modules[0].excludeFolder = "off";
    this.tempObject.modules[0].schdulePeriodicFullScan = "off";
    this.buildInfo.modules[0].incrementalScan = "off";
    return false;
  }
  setSomeValues2() {
    this.tempObject.modules[0].schdulePeriodicFullScan = "off";
    return false;
  }
  setBuildinfoExc() {
    this.buildInfo.modules[0].exclude = "";
    return false;
  }
  setBuildinfoInt() {
    this.buildInfo.modules[0].interval = "";
    return false;
  }
  setUSPassSM(i) {
    this.buildInfo.modules[i].userName = "";
    this.buildInfo.modules[i].password = "";
    this.buildInfo.modules[i].serverMachine = "";
    return false;
  }
  /* Iniializing Unit testing values */
  setunitTest(i) {
  this.buildInfo.modules[i].unitTestProjectName = "";
  this.buildInfo.modules[i].unitTestCategory = "";
  this.buildInfo.modules[i].codeCoverage = "off";
  return false;
}
settestSettingFilePath(i) {
    this.buildInfo.modules[i].testSettingFilePath = "";
    return false;
}

/* Setting code Analysis on checking the checkbox */
setcodeAnalysis(i) {
  this.tempObject.modules[i].codeAnalysis = "on";
  return false;
}
setTempObjectCheckMarx() {
  this.tempObject.modules[0].Checkmarx = "";
  return false;
}
dbObject(index, checked) {
  if (checked) {
    this.tempObject.modules[index].dbObject = "on";
  } else {
    this.tempObject.modules[index].dbObject = "off";
    this.buildInfo.modules[index].projPath = "";
    this.buildInfo.modules[index].srcSchName = "";
    this.buildInfo.modules[index].tarSchName = "";
  }
}
/* Initializing nunit inputs on checking and unchecking */
nUnit(index, checked) {
  if (checked) {
    this.tempObject.modules[index].nUnit = "on";
  } else {
    this.tempObject.modules[index].nUnit = "off";
    this.buildInfo.modules[index].nUnitprojPath = "";
  }
}

/* Setting and unsetting code analysis
  * for particulr module (index)
  */
  codeAnalysisCheckbox(index, checked) {
    if (checked) {
        this.tempObject.modules[index].codeAnalysis = "on";
    } else {
        this.tempObject.modules[index].codeAnalysis = "off";
    }
    if (this.tempObject.modules[index].codeAnalysis === "on") {
        this.buildInfo.modules[index].codeAnalysis = ["off", "off"];
    } else {
        this.buildInfo.modules[index].codeAnalysis = [];
    }
  }
  /* Adding all the inputs
   * on adding a module
   */
  addItem() {
    this.buildInfo.modules.push({
        codeAnalysis: [],
        version: "",
        ossDistributionType: ""
    });
    if (this.tempObject.modules === undefined) {
        this.tempObject.modules = [];
    }
    this.tempObject.modules.push({});
  }
  obfuscationDotnetChecked(index) {
    if (this.buildInfo.modules[index].renaming === null) {
        this.buildInfo.modules[index].renaming = "On";
    }
    if (this.buildInfo.modules[index].controlFlow === null) {
        this.buildInfo.modules[index].controlFlow = "On";
    }
    if (this.buildInfo.modules[index].encryption === null) {
        this.buildInfo.modules[index].encryption = "On";
    }
  }

  /* On deleing the added Solution */
  deleteItem(index) {
    const x = confirm("Are you sure to remove the Solution ?");
    if (x) {
        this.buildInfo.modules.splice(index, 1);
        this.tempObject.modules.splice(index, 1);
    }
  }
  checkCheckBox() {
    if (this.tempObject.modules === undefined) {
        this.tempObject.modules = [];
    }
    for (let i = 0; i < this.buildInfo.modules.length; i++) {
        let codeAnalysis = "off";
        let securityAnalysis = "off";
        let ossCheck = "off";
        let dbObject = "off";
        let nUnit = "off";

        if (this.buildInfo.modules[i].codeAnalysis.length !== 0) {
        codeAnalysis = "on";
        }
        if (this.buildInfo.modules[i].userName !== ""
        && this.buildInfo.modules[i].userName !== undefined) {
        securityAnalysis = "on";
        }
        if (this.buildInfo.modules[i].projPath !== ""
        && this.buildInfo.modules[i].projPath !== undefined) {
        dbObject = "on";
        }
        if (this.buildInfo.modules[i].ossMailRecipients !== ""
        && this.buildInfo.modules[i].ossMailRecipients !== undefined) {
        ossCheck = "on";
        }
        if (this.buildInfo.modules[i].nUnitprojPath !== ""
        && this.buildInfo.modules[i].nUnitprojPath !== undefined) {
        nUnit = "on";
        }
        this.tempObject.modules.push({
        "codeAnalysis": codeAnalysis,
        "securityAnalysis": securityAnalysis,
        "ossCheck" : ossCheck,
        "dbObject": dbObject,
        "nUnit": nUnit
        });
    }
  }
  ngOnInit() {
    this.distributionList = [{
        "name": "Internal",
        "value": "Internal"
    },
    {
        "name": "Hosted Service (Infosys Infrastructure)",
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
