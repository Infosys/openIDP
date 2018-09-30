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
  selector: "app-general-cntrl",
  templateUrl: "./general-cntrl.component.html",
  styleUrls: ["./general-cntrl.component.css"]
})
export class GeneralCntrlComponent implements OnInit {
    @Input()
  public formName: string;
  @Input() buildInfo: any = this.IdpdataService.data.buildInfo;
  @Input() tempObject: any = this.IdpdataService.data.checkboxStatus.buildInfo;
  formStatusObject: any = this.IdpdataService.data.formStatus;
  nameErrorMessage: any = "";
  checkBoxObject: any;

/**
*
* Constructor for General Tech Component
*
**/
  constructor(private IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router) {
    if (this.buildInfo.modules.length === 0) {
        this.addItem();
    }
    if (this.formStatusObject.operation === "copy"
        || this.formStatusObject.operation === "edit") {
        this.checkCheckBox();
    }
    this.checkBoxObject = "on";
  }
/**
*
* Instance Variables initialization
*
**/
    shellScript: any = [];
  batchScript: any = [];
  antScript: any = [];
  ScriptList: any = [
    { "name": "ANT Script", "value": "ant" },
    { "name": "Shell Script", "value": "shellScript" },
    { "name": "Batch Script", "value": "batchScript" },
    { "name": "Powershell Script", "value": "powerShell" }];
  addItem() {
    this.buildInfo.modules.push({
        "runScript": {
        "scriptType": "",
        "reportType": ""
        },
      "abortScript": {
        "scriptType": "",
        "reportType": ""
        }
    });
    if (this.tempObject.modules === undefined) {
        this.tempObject.modules = [];
    }
    this.tempObject.modules.push({
        "runScript": {
        "scriptType": "",
        "reportType": ""
        },
      "abortScript": {
        "scriptType": "",
        "reportType": ""
        }
    });
  }
/**
*
* On creation of each module, sets "" value for the inputs
*
**/
  changeRunScript(i) {
    this.buildInfo.modules[i].runScript.scriptFilePath = "";
    this.buildInfo.modules[i].runScript.targets = "";
    this.buildInfo.modules[i].runScript.host = "";
    this.buildInfo.modules[i].runScript.userName = "";
    this.buildInfo.modules[i].runScript.password = "";
    this.buildInfo.modules[i].runScript.script = "";
    this.buildInfo.modules[i].runScript.pathToFiles = "";
    this.buildInfo.modules[i].runScript.destinationDir = "";
    this.buildInfo.modules[i].runScript.flattenFilePath = "off";
  }
  clearApproval(i) {
      this.buildInfo.modules[i].timeout = "";
      this.tempObject.modules[i].runScriptAbortCheckBox = this.clearRunScriptOnAbort(i);
      return "off";
  }
  clearRunScriptOnAbort(i) {
      this.buildInfo.modules[i].abortScript.scriptType = "";
      this.changeAbortRunScript(i);
      return "off";
  }
/**
*
* On creation of each module, sets "" value for the SSH inputs
*
**/
  changeAbortRunScript(i) {
    this.buildInfo.modules[i].abortScript.scriptFilePath = "";
    this.buildInfo.modules[i].abortScript.targets = "";
    this.buildInfo.modules[i].abortScript.host = "";
    this.buildInfo.modules[i].abortScript.userName = "";
    this.buildInfo.modules[i].abortScript.password = "";
    this.buildInfo.modules[i].abortScript.script = "";
    this.buildInfo.modules[i].abortScript.pathToFiles = "";
    this.buildInfo.modules[i].abortScript.destinationDir = "";
    this.buildInfo.modules[i].abortScript.flattenFilePath = "off";
  }
  checkCheckBox() {
    if (this.tempObject.modules === undefined) {
        this.tempObject.modules = [];
    }
    for (let i = 0; i < this.buildInfo.modules.length; i++) {
        let approvalCheckBox = "off";
        let runScriptAbortCheckBox = "off";
        if (this.buildInfo.modules[i].timeout !== undefined
            && this.buildInfo.modules[i].timeout !== null
            && this.buildInfo.modules[i].timeout !== "") {
        approvalCheckBox = "on";
        }
        if (this.buildInfo.modules[i].abortScript !== undefined
            && this.buildInfo.modules[i].abortScript.scriptType !== undefined
            && this.buildInfo.modules[i].abortScript.scriptType !== null
            && this.buildInfo.modules[i].abortScript.scriptType !== "") {
        runScriptAbortCheckBox = "on";
        }
        this.tempObject.modules.push({
        "approvalCheckBox": approvalCheckBox,
        "runScriptAbortCheckBox": runScriptAbortCheckBox
        });
    }
  }
/**
*
* On removal of each module, changes the index value
*
**/
  deleteItem(index) {
    const x = confirm("Are you sure to remove the Module ?");
    if (x) {
        this.buildInfo.modules.splice(index, 1);
        this.tempObject.modules.splice(index, 1);
    }
  }
/**
*
* Checks for duplication of module name
*
**/
  checkModuleName(index) {
    for (let i = 0; i < this.buildInfo.modules.length; i++) {
        if (this.buildInfo.modules[i].moduleName !== undefined
            && this.buildInfo.modules[index].moduleName !== undefined
            && this.buildInfo.modules[i].moduleName.toLowerCase() === this.buildInfo.modules[index].moduleName.toLowerCase()
            && i !== index) {
            this.nameErrorMessage = "Module name must be unique.";
            break;
        } else {
        this.nameErrorMessage = "";
        }
    }
  }
  ngOnInit() {
  }
}
