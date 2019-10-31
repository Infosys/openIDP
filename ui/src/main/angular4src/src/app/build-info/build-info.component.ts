/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit, ChangeDetectorRef, ChangeDetectionStrategy } from "@angular/core";
import { IdprestapiService } from "../idprestapi.service";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { Router } from "@angular/router";
import { ViewChild, ElementRef } from "@angular/core";
import { ParentFormConnectComponent } from "../parent-form-connect/parent-form-connect.component";
import { IdpSubmitService } from "../idpsubmit.service";
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';


@Component({
  selector: "app-build-info",
  templateUrl: "./build-info.component.html",
  styleUrls: ["./build-info.component.css"]
})
export class BuildInfoComponent implements OnInit {
  @ViewChild("modalforAlert") private modalforAlert: ElementRef;
  @ViewChild("modalforDotNet") dotNetButton;
  @ViewChild("modalformandatoryFieldsBuildAlert") mandatoryFieldsAlert;
  @ViewChild("modalforconfirmBuildAlert") confirmationAlert;
  private alertModalRef: BsModalRef;
  private  confirmAlertModalRef: BsModalRef;
  private mandatoryModalRef: BsModalRef;

  /*constructor start*/
  constructor(
    private ref: ChangeDetectorRef,
    public IdpdataService: IdpdataService,
    private IdpSubmitService: IdpSubmitService,
    private router: Router,
    private IdpService: IdpService,
    private modalService: BsModalService
  ) {
    this.IdpSubmitService.message = "";
  }
  buildInfo = this.IdpdataService.data.buildInfo;
  buildtool = this.buildInfo.buildtool;
  msg: any;
  loc: any;
  listToFillFields: any = [];
  TriggerAlert(config) {
      this.alertModalRef = this.modalService.show(this.modalforAlert);
      this.alertModalRef.content = config;
  }

  redirectTo(loc) {
    this.alertModalRef.hide();
    if (loc) {
        this.router.navigate([loc]);
    }
  }

    go() {

        if (this.IdpdataService.isSAPApplication
            && this.IdpdataService.data.checkboxStatus.buildInfo.RaiseJiraBugonfailureCheck === "on"
            && this.IdpdataService.data.checkboxStatus.buildInfo.codeAnalysisCheck !== "on"
            && this.IdpdataService.data.checkboxStatus.buildInfo.castAnalysisCheck !== "on"
            && this.IdpdataService.data.buildInfo.modules[0].unitTesting !== "on") {
            alert("Atleast one operation should be selected along with \"Raise Jira Bug on failure\"");
            return;
        } else if (this.IdpdataService.isSAPApplication
            && this.IdpdataService.data.checkboxStatus.buildInfo.codeAnalysisCheck === "on"
            && this.IdpdataService.data.checkboxStatus.buildInfo.SAPCodeInspectorCheck !== "on") {
            alert("Please select code inspection to continue.");
        } else {
            if (this.IdpdataService.pa) {
                if (this.IdpdataService.isSAPApplication) {
                    this.buildInfo = this.IdpdataService.SapBuildTemp;
                }
               

                this.IdpdataService.data.buildInfo = this.buildInfo;
                this.IdpdataService.data.masterJson["buildInfo"] = this.buildInfo;
                console.log(this.IdpdataService.data);
                if (this.IdpdataService.buildSubscriptionSubmit !== true) {
                    this.router.navigate(["/createConfig/deployInfo"]);
                } else if (this.IdpdataService.buildSubscriptionSubmit === true) {
                    if (this.IdpdataService.allFormStatus.basicInfo &&
                        this.IdpdataService.allFormStatus.codeInfo &&
                        this.IdpdataService.allFormStatus.buildInfo) {
            this.confirmAlertModalRef = this.modalService.show(this.confirmationAlert);
                    } else {
                        if (!this.IdpdataService.allFormStatus.basicInfo && this.listToFillFields.indexOf("BasicInfo") === -1) {
                            this.listToFillFields.push("BasicInfo");
                        }
                        if (!this.IdpdataService.allFormStatus.codeInfo && this.listToFillFields.indexOf("CodeInfo") === -1) {
                            this.listToFillFields.push("CodeInfo");
                        }
                        if (!this.IdpdataService.allFormStatus.buildInfo && this.listToFillFields.indexOf("BuildInfo") === -1) {
                            this.listToFillFields.push("BuildInfo");
                        }
            this.mandatoryModalRef = this.modalService.show(this.mandatoryFieldsAlert);
                    }

                }
            } else {
                alert("Please select atleast one tool for code analysis.");
            }
        }
    }

  // ngOnInit starts
  ngOnInit() {
    if (this.IdpdataService.data.formStatus.basicInfo.appNameStatus === "0") {
        this.TriggerAlert({loc:"/createConfig/basicInfo",msg:"Application Name"});
    } else if (this.IdpdataService.data.formStatus.buildInfo.buildToolStatus === "0") {
        this.TriggerAlert({loc:"/createConfig/codeInfo",msg:"Technology Type"});
    }
    if (this.buildInfo.buildtool === "msBuild" && this.IdpdataService.data.basicInfo.buildServerOS !== "windows") {
        this.dotNetButton.nativeElement.click();

    }
    window.scroll(0,0);
  }


  setFormStatus(data) {
    this.IdpdataService.allFormStatus.buildInfo = data;
  }
  // ngOnInit ends
  resetData() {
    const buildtool = this.buildInfo.buildtool;
    const x = confirm("Are you sure to Reset ?");
    if (x) {
        this.buildInfo = {
        "buildtool": buildtool,
        "castAnalysis": {},
        "artifactToStage": {},
        "modules": [],
        "dependentPipelineList": [],
        "securityAnalysisTool":"",
        };
    }

    this.IdpdataService.data.buildInfo = this.buildInfo;
    this.IdpdataService.data.checkboxStatus.others = {};
    this.IdpdataService.data.checkboxStatus.buildInfo = {};
    console.log(this.IdpdataService.data.buildInfo);
    console.log(this.IdpdataService.data.buildInfo);
    this.router.navigateByUrl("/Dummy", { skipLocationChange: true })
        .then(() => this.router.navigate(["/createConfig/buildInfo"]));
  }
  redirectToBasicInfo() {
    this.router.navigate(["/createConfig/basicInfo"]);
  }

  buildSubscriptionNotSubmitCheck() {
    if (this.IdpdataService.buildSubscriptionSubmit !== true) {
        return true;
    } else {
        return false;
    }
  }

  buildSubscriptionSubmitCheck() {
    if (this.IdpdataService.buildSubscriptionSubmit === true) {
        return true;
    } else {
        return false;
    }
  }

  checkLoaderOn() {
    if (this.IdpSubmitService.loader === "on") {
        return true;
    } else {
        return false;
    }
  }

  checkLoaderOff() {
    if (this.IdpSubmitService.loader === "off") {
        return true;
    } else {
        return false;
    }
  }

  checkMsgSuccess() {
    if (this.IdpSubmitService.message === "success") {
        return true;
    } else {
        return false;
    }
  }

  checkMsgError() {
    if (this.IdpSubmitService.message === "error") {
        return true;
    } else {
        return false;
    }
  }

  submitData() {
    this.IdpSubmitService.submitData();
  }
}
