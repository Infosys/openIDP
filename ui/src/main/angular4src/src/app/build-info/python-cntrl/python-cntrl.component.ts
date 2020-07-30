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
  selector: "app-python-cntrl",
  templateUrl: "./python-cntrl.component.html",
  styleUrls: []
})
export class PythonCntrlComponent implements OnInit {
        tempCodeAnalysis = ["sonarqube"];
        buildInfo: any = this.IdpdataService.data.buildInfo;
        tempObject: any = this.IdpdataService.data.checkboxStatus.buildInfo;
        formStatusObject: any = this.IdpdataService.data.formStatus;
        module: any;
  @Input()
  public formName: string;

  constructor(public IdpdataService: IdpdataService,
    public IdpService: IdpService,
    public IdprestapiService: IdprestapiService,
    public router: Router) {


        if (this.buildInfo.modules.length === 0) {
            // this.buildInfo = this.IdpService.copy(this.IdpdataService.data.buildInfo);
            // this.buildInfo.modules = [];
            this.tempObject.modules = [{"codeAnalysis": ""}];
            this.module = {
                "moduleName" : "",
                "codeAnalysis" : [],
                "unitTesting" : "off",
                "unitTestFolderName" : "",
                "unitTestReportFolder" : "",
                "targetHostName" : "",
                "targetUserName" : "",
                "targetPassword" : "",
                "targetRemotedir" : ""
            };

            this.buildInfo.modules.push(this.module);
        }

        if (this.formStatusObject.operation === "copy" || this.formStatusObject.operation === "edit" ) {
        this.checkCheckBox();
        }


        this.IdpdataService.data.buildInfo = this.buildInfo;
        this.IdpdataService.data.checkboxStatus.buildInfo = this.tempObject;

    }

    unitTestOn() {
        this.IdpdataService.unitTest = true;
        return "on";
    }
    unitTestOff() {
        this.IdpdataService.unitTest = false;
        return "off";
    }

  ngOnInit() {
  }

        codeAnalysisCheckbox() {
            if (this.tempObject.modules[0].codeAnalysis === "on") {
                this.buildInfo.modules[0].codeAnalysis = [null];
            }
        }



        checkCheckBox() {
            if (this.tempObject.modules === undefined) {
        this.tempObject.modules = [{"codeAnalysis": ""}];
    }
            if (this.buildInfo.modules[0].codeAnalysis.length !== 0) {
                if (this.tempObject.modules === undefined) {
                    this.tempObject.modules = [];
                    this.tempObject.modules.push({});
                }
                this.tempObject.modules[0].codeAnalysis = "on";
        }
        if (this.buildInfo.modules[0].unitTesting === "on") {
            this.IdpdataService.unitTest = true;
        }
        }



codeAnalysisSonar() {
  this.buildInfo.modules[0].codeAnalysis[0] = "sonar";
  return false;
}

paFalse() {
  this.IdpdataService.pa = false;
  return false;
}

paTrue() {
  this.IdpdataService.pa = true;
  return false;
}

codeAnalysisEmptyPaTrue() {
  this.buildInfo.modules[0].codeAnalysis = [];
  this.IdpdataService.pa = true;
  return false;
}

clearSonarqube() {
    this.buildInfo.modules[0].sonarUrl ="";
    this.buildInfo.modules[0].sonarUserName ="";
    this.buildInfo.modules[0].sonarPassword ="";
    this.buildInfo.modules[0].sonarProjectKey ="";
    this.buildInfo.modules[0].sonarProperties ="";
  
      return "off";
    }


unitTestingOn() {
  this.buildInfo.modules[0].unitTesting = "on";
  return false;
}

continuecontrolFalse() {
  this.IdpdataService.continuecontrol = false;
  return false;
}

continuecontrolTrue() {
this.IdpdataService.continuecontrol = true;
return false;
}

unitTestingOff() {
  this.buildInfo.modules[0].unitTestDir = "";
  this.buildInfo.modules[0].report = "";
  this.IdpdataService.unitTest = false;
  for (const dep of this.IdpdataService.data.deployInfo.deployEnv) {
    if (dep !== undefined  && dep.deploySteps !== undefined) {
        for (const step of dep.deploySteps) {
        step.s3location = "";
        }
    }
  }
  if (this.IdpdataService.data.checkboxStatus !== undefined && this.IdpdataService.data.checkboxStatus.deployInfo !== undefined
    && this.IdpdataService.data.checkboxStatus.deployInfo.deployEnv !== undefined) {
    for (const dep of this.IdpdataService.data.checkboxStatus.deployInfo.deployEnv) {
        if (dep !== undefined && dep.deploySteps !== undefined) {
        for (const step of dep.deploySteps) {
            step.s3Loc = "";
        }
        }


    }
  }

  return false;

}

targetEnvironmentUnitTestingOff() {
  this.buildInfo.modules[0].hostName = "";
  this.buildInfo.modules[0].userName = "";
  this.buildInfo.modules[0].privateKey = "";
  this.buildInfo.modules[0].remoteDir = "";
  return false;
}
}
