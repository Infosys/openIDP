/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit, TemplateRef, ChangeDetectionStrategy, Input, OnChanges, SimpleChanges} from "@angular/core";
import { IdprestapiService } from "../../idprestapi.service";
import { IdpService } from "../../idp-service.service";
import { IdpdataService } from "../../idpdata.service";
import { NgForm } from "@angular/forms";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { Validators } from "@angular/forms";
import { ViewChild } from "@angular/core";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";


@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: "app-ant-ctrl",
  templateUrl: "./ant-ctrl.component.html",
  styleUrls: ["./ant-ctrl.component.css"]
})
export class AntCtrlComponent implements OnInit {

  @Input()
  public formName: string;

  tempsecurityAnalysislist: any = ["App Scan"];
  @Input() buildInfo: any = this.IdpdataService.data.buildInfo;
  @Input() tempObject: any = this.IdpdataService.data.checkboxStatus.buildInfo;
  formStatusObject: any = this.IdpdataService.data.formStatus;
  nameErrorMessage: any = "";
  javalist: any = [];
  ejbList: any = [];
  warList: any = [];
  tempCodeAnalysis: any = ["sonar", "pmd", "checkStyle", "findBugs"];
  checkBoxObject: any;
  distributionList: any = [];
  /* Constructor */
  constructor(
    private IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router
  ) {
    this.IdpdataService.ejbVal = "false";
    this.IdpdataService.warVal = "false";
    this.IdpdataService.jarVal = "false";
    if (this.formStatusObject.operation === "copy" || this.formStatusObject.operation === "edit") {
        this.checkCheckBox();
    }

    if (this.buildInfo.modules.length === 0) {
      this.addItem();
  }
    
    this.checkBoxObject = "on";
    this.javaOptionList();
    this.ejbOptionList();
    this.warOptionList();
  }
  setCheckBoxObject() {
    }
    /* Checks Code Analysis Status & sets flag */
  checkCodeAnalysisOn(i) {
    if ( this.tempObject.modules[i].codeAnalysis === "on" ) {
        if ( this.buildInfo.modules[i].codeAnalysis[0] === "off"
        && this.buildInfo.modules[i].codeAnalysis[1] === "off"
        && this.buildInfo.modules[i].codeAnalysis[2] === "off"
            && this.buildInfo.modules[i].codeAnalysis[3] === "off") {
        this.IdpdataService.pa = false;
        } else {
        this.IdpdataService.pa = true;
        }
    } else {
        this.IdpdataService.pa = true;
    }
  }

  /* Sets default values for code_cov, Packaging, unit Testing */
  setSomeValues1(i) {
    this.buildInfo.modules[i].javaMainClass = "";
    this.buildInfo.modules[i].ejbDescriptor = "";
    this.buildInfo.modules[i].warPackaging = "";
    this.buildInfo.modules[i].compile = "off";
    this.buildInfo.modules[i].unitTesting = "off";
    this.buildInfo.modules[i].codeCoverage = "off";
    this.buildInfo.modules[i].codeAnalysis[0] = "off";
    this.buildInfo.modules[i].codeAnalysis[1] = "off";
    this.buildInfo.modules[i].codeAnalysis[2] = "off";
    this.buildInfo.modules[i].codeAnalysis[3] = "off";
    this.tempObject.modules[i].chkjar = "off";
    this.tempObject.modules[i].WARPackaging = "off";
    return false;
  }

  /* Clear Code Analysis on unchecking */
  clearCodeAnalysis(i) {
    this.buildInfo.modules[i].codeAnalysis = [];
    return "off";
  }
  clearSonarqube() {
    console.log(this.buildInfo.modules[0]);
   this.buildInfo.modules[0].sonarUrl ="";
  this.buildInfo.modules[0].sonarUserName ="";
  this.buildInfo.modules[0].sonarPassword ="";
  this.buildInfo.modules[0].sonarProjectKey ="";
  this.buildInfo.modules[0].sonarProperties ="";

    return "off";
  }

  /* Default Code Analysis, security Analysis status */
  createCodeAnalysis(i) {
    this.buildInfo.modules[i].codeAnalysis = ["off" , "off" , "off" , "off"];
    return "on";
  }
  setContinuecontrolFalse() {
    this.IdpdataService.continuecontrol = false;
    return false;
  }
  setContinuecontrolTrue() {
    this.IdpdataService.continuecontrol = true;
    return false;
  }
  /* Setting custom build xml,compile for each module if selected */
  setcustomBuildXml(i) {
    this.tempObject.modules[i].customBuildXml = "on";
    return false;
  }
  setcodeAnalysisthree(i) {
    this.buildInfo.modules[i].codeAnalysis[3] = "off";
    this.tempObject.modules[i].WARPackaging = "off";
    return false;
  }
  setCompileOff(i) {
    this.setWarPack(i);
    return "off";
  }
  setCustomBuildOn(i) {
    this.buildInfo.modules[i].javaMainClass = "";
    this.buildInfo.modules[i].ejbDescriptor = "";
    this.buildInfo.modules[i].warPackaging = "";
    this.buildInfo.modules[i].compile = "off";
    this.buildInfo.modules[i].unitTesting = "off";
    this.buildInfo.modules[i].codeCoverage = "off";
    this.buildInfo.modules[i].codeAnalysis[1] = "off";
    this.buildInfo.modules[i].codeAnalysis[2] = "off";
    this.buildInfo.modules[i].codeAnalysis[3] = "off";
    this.tempObject.modules[i].chkjar = "off";
    this.tempObject.modules[i].WARPackaging = "off";
    return "on";
  }
  setCustomBuildOff(i) {
    this.buildInfo.modules[i].customBuildXml = "";
    this.buildInfo.modules[i].args = "";
    return "off";
  }
  setWarPack(i) {
    this.tempObject.modules[i].WARPackaging = "off";
    this.buildInfo.modules[i].warPackaging = "";
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
  setargsCustomBuild(i) {
    this.buildInfo.modules[i].args = "";
    this.buildInfo.modules[i].customBuildXml = "";
    return false;
  }
  setpaCodeAnalysis(i) {
    this.buildInfo.modules[i].codeAnalysis = [];
    this.IdpdataService.pa = true;
    return false;
  }
  setunitTestcodeCoverage(i) {
    this.buildInfo.modules[i].unitTestDir = "";
    this.buildInfo.modules[i].codeCoverage = "off";
    return false;
  }
  setejbejbVal(i) {
    this.IdpdataService.ejbVal = false;
    this.buildInfo.modules[i].ejbDescriptor = "";
    return false;
  }
  setjavaMain(i) {
    this.IdpdataService.jarVal = false;
    this.buildInfo.modules[i].javaMainClass = "";
    return false;
  }
  setejbValTrue() {
    this.IdpdataService.ejbVal = true;
    return false;
  }
  setejbValFalse() {
    this.IdpdataService.ejbVal = false;
    return false;
  }
  setejbejbVal2(i) {
    this.buildInfo.modules[i].ejbDescriptor = "";
    this.buildInfo.modules[i].javaMainClass = "";
    this.IdpdataService.ejbVal = false;
    this.IdpdataService.jarVal = false;
  }

  /* Default Values for
  Module, War Packaging, web modules, ejb modules */
  setwarValTrue() {
    this.IdpdataService.warVal = true;
    return false;
  }
  setwarValFalse() {
    this.IdpdataService.warVal = false;
    return false;
  }
  setmodulewarVal(i) {
    this.buildInfo.modules[i].warPackaging = "";
    this.IdpdataService.warVal = false;
    return false;
  }
  setJavaModules() {
    this.buildInfo.javaModules = "";
    return false;
  }
  setEjbModules() {
    this.buildInfo.ejbModules = "";
    return false;
  }
  setwebModules() {
    this.buildInfo.webModules = "";
    return false;
  }
  setwebejbjavaModules() {
    this.buildInfo.ejbModules = "";
    this.buildInfo.webModules = "";
    this.buildInfo.javaModules = "";
    return false;
  }

  /* Checks input required for EAr packaging */
  checkForEarPack() {
    if (( this.tempObject.javalist === undefined || this.tempObject.javalist.length === 0)
        && (this.tempObject.ejbList === undefined || this.tempObject.ejbList.length === 0)
        && (this.tempObject.warList === undefined || this.tempObject.warList.length === 0) ) {
        return true;
        } else {
        return false;
        }
  }
  checkForEarPackJava() {
    if ( !( this.tempObject.javalist === undefined || this.tempObject.javalist.length === 0) ) {
        return true;
        } else {
        return false;
        }
  }
  /* Sets true if inputs for EJB packaging is available */
  checkForEarPackEJB() {
    if ( !( this.tempObject.ejbList === undefined || this.tempObject.ejbList.length === 0) ) {
        return true;
        } else {
        return false;
        }
  }

  checkForEarPackWAR() {
    if (!(this.tempObject.warList === undefined || this.tempObject.warList.length === 0) ) {
        return true;
        } else {
        return false;
        }
  }

  /* Addition of each Ant Module */
  addItem() {
    this.buildInfo.modules.push({
        codeAnalysis: [],
        ossDistributionType: ""
    });
    if (this.tempObject.modules === undefined) {
        this.tempObject.modules = [];
    }
    this.tempObject.modules.push({});
  }
  onClick(key) {
    if (this.buildInfo.modules[key].compile === "off"
        || this.buildInfo.modules[key].compile === undefined
        || this.buildInfo.modules[key].compile === null) {
        this.buildInfo.modules[key].codeAnalysis[3].disabled = true;
    } else {
        this.buildInfo.modules[key].codeAnalysis[3].disabled = false;
    }
  }

  /* Checks for unique module name */
  checkModuleName(index) {
    for (let i = 0; i < this.buildInfo.modules.length; i++) {
        if (this.buildInfo.modules[i].moduleName !== undefined
            && this.buildInfo.modules[index].moduleName !== undefined
            && this.buildInfo.modules[i].moduleName.toLowerCase() === this.buildInfo.modules[index].moduleName.toLowerCase() && i !== index) {
        this.nameErrorMessage = "Module name must be unique.";
        break;
        } else {
        this.nameErrorMessage = "";
        }
    }
  }

  /* Checks for mainclass for each Ant module */
  javaOptionList() {
    this.javalist = [];
    for (let i = 0; i < this.buildInfo.modules.length; i++) {
        if (this.buildInfo.modules[i].moduleName !== undefined &&
            this.buildInfo.modules[i].moduleName !== null &&
            this.buildInfo.modules[i].moduleName !== "" &&
            this.buildInfo.modules[i].javaMainClass !== undefined &&
            this.buildInfo.modules[i].javaMainClass !== "" &&
            this.buildInfo.modules[i].javaMainClass !== null) {
        this.javalist.push(this.buildInfo.modules[i].moduleName);
        }
    }
    this.tempObject.javalist = this.javalist;
    return true;
  }

  /* Checks for EJB descr for each module,
    if EJB is enabled, and pushes the values to service */
  ejbOptionList() {
    this.ejbList = [];
    for (let i = 0; i < this.buildInfo.modules.length; i++) {
        if ( this.buildInfo.modules[i].moduleName !== undefined &&
            this.buildInfo.modules[i].moduleName !== null &&
            this.buildInfo.modules[i].moduleName !== "" &&
            this.buildInfo.modules[i].ejbDescriptor !== undefined &&
            this.buildInfo.modules[i].ejbDescriptor !== null &&
            this.buildInfo.modules[i].ejbDescriptor !== "") {
                this.ejbList.push(this.buildInfo.modules[i].moduleName);
        }
    }
    this.tempObject.ejbList = this.ejbList;
    return true;
  }

  /* Checks for war packaging for each module,
    if enabled, it pushes the value of each module to service */
  warOptionList() {
    this.warList = [];
    for (let i = 0; i < this.buildInfo.modules.length; i++) {
        if ( this.buildInfo.modules[i].moduleName !== undefined &&
        this.buildInfo.modules[i].moduleName !== null &&
        this.buildInfo.modules[i].moduleName !== "" &&
        this.buildInfo.modules[i].warPackaging !== undefined &&
        this.buildInfo.modules[i].warPackaging !== null &&
        this.buildInfo.modules[i].warPackaging !== "" ) {
        this.warList.push(this.buildInfo.modules[i].moduleName);
        }
    }
    this.tempObject.warList = this.warList;
    return true;
  }
  codeAnalysisCheckbox(index) {
    if (this.tempObject.modules[index].codeAnalysis === "on") {
        this.buildInfo.modules[index].codeAnalysis = [null, null, null, null];
    }
  }

  /* Removal of ANT module */
  deleteItem(index) {
    const x = confirm("Are you sure to remove the Module ?");
    if (x) {
        this.buildInfo.modules.splice(index, 1);
        this.tempObject.modules.splice(index, 1);
    }
  }

  /* Checks for status of each checkbbox,
    including compile, packaging, code Analysis, modules,.. and gets the status */
  checkCheckBox() {
    if (this.tempObject.modules === undefined) {
        this.tempObject.modules = [];
    }
    for (let i = 0; i < this.buildInfo.modules.length; i++) {
        let customBuildXml = "off";
        let codeAnalysis = "off";
        let securityAnalysis = "off";
        const JARPackaging = "off";
        let WARPackaging = "off";
        let checkjar = "off";
        let secAnal = "";
        const schdulePeriodicFullScan = "off";
        let ear = "off";
        let ossCheck = "off";
        if (this.buildInfo.modules[i].customBuildXml !== ""
        && this.buildInfo.modules[i].customBuildXml !== undefined) {
        customBuildXml = "on";
        }
        if (this.buildInfo.modules[i].codeAnalysis.length !== 0) {
        codeAnalysis = "on";
        }
        if (this.buildInfo.modules[i].pafFilePath !== ""
        && this.buildInfo.modules[i].pafFilePath !== undefined) {
        securityAnalysis = "on";
        }

        if (this.buildInfo.modules[i].warPackaging !== ""
        && this.buildInfo.modules[i].warPackaging !== undefined) {
        WARPackaging = "on";
        }
        if (this.buildInfo.modules[i].javaMainClass !== undefined
            && this.buildInfo.modules[i].javaMainClass !== "" ) {
        checkjar = "on";
        } else if (this.buildInfo.modules[i].ejbDescriptor !== undefined
            && this.buildInfo.modules[i].ejbDescriptor !== "") {
        checkjar = "on";
        }

        if ((this.buildInfo.javaModules !== undefined
            && this.buildInfo.javaModules !== null
            && this.buildInfo.javaModules !== "") ||
        (this.buildInfo.ejbModules !== undefined
            && this.buildInfo.ejbModules !== null
            && this.buildInfo.ejbModules !== "") ||
            (this.buildInfo.webModules !== undefined
                && this.buildInfo.webModules !== null
                && this.buildInfo.webModules !== "")) {
                ear = "on";
        }
        if (this.buildInfo.modules[i].pafFilePath !== undefined
            && this.buildInfo.modules[i].pafFilePath !== "") {
        securityAnalysis = "on";
            secAnal = "App Scan";
        }
        if (this.buildInfo.modules[i].ossMailRecipients !== ""
        && this.buildInfo.modules[i].ossMailRecipients !== undefined) {
        ossCheck = "on";
        }
        this.tempObject.modules.push({ // Pushes the status/value of each field
        "customBuildXml": customBuildXml,
        "codeAnalysis": codeAnalysis,
        "securityAnalysis": securityAnalysis,
        "WARPackaging": WARPackaging,
        "chkjar": checkjar,
        "secAnal": secAnal,
        "schdulePeriodicFullScan": schdulePeriodicFullScan,
        "ossCheck": ossCheck
        });
        this.tempObject.ear = ear;
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
