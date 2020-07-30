/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import { IdprestapiService } from "../idprestapi.service";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { Router } from "@angular/router";
import { ViewChild } from "@angular/core";
import { IdpSubmitService } from "../idpsubmit.service";

@Component({
  selector: "app-create-organization",
  templateUrl: "./create-organization.component.html",
  styleUrls: [],
})
export class CreateOrganizationComponent implements OnInit {
  @ViewChild("modalforSuccessAlert") success;
  flagNew: any = false;
  flagEdit: any = false;
  valueSubmit: any = "";
  valueSubmitting: any;
  indexToDisable: any;
  grantAccess: any = this.IdpdataService.data.grantAccess;
  tempObjectApp: any = this.IdpService.copy(this.grantAccess);
  addEditmsg: any;
  message: any = "";
  grantloading: any;
  orgNames: any = [];
  orgList: any = [];
  flagp: any = false;

  constructor(
    public IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdpSubmitService: IdpSubmitService,
    private IdprestapiService: IdprestapiService,
    private router: Router
  ) {}

  ngOnInit() {}

  /* Initializing values for new Org */
  createNewOrg() {
    this.flagp = false;
    this.flagNew = true;
    this.flagEdit = false;
    this.valueSubmit = "Submit";
    this.valueSubmitting = "Submitting";
    this.indexToDisable = null;
    this.grantAccess = {
      domain: "",
      orgAdmin: "",
      orgId: 0,
      orgName: "",
    };
    this.tempObjectApp = this.IdpService.copy(this.grantAccess);
  }

  /* To Get all Org List */
  getAllOrganizations() {
    this.IdprestapiService.getExistingOrgNames().then((response) => {
      try {
        const orgDetails = response.json().resource;
        let sameOrgFlag = false;
        for (let i = 0; i < orgDetails.length; i++) {
          const org = orgDetails[i].orgName;
          if (
            org !== undefined &&
            this.grantAccess.orgName !== undefined &&
            org.toLowerCase() === this.grantAccess.orgName.toLowerCase()
          ) {
            sameOrgFlag = true;
          }
        }
        if (sameOrgFlag) {
          if (document.getElementById("orgname").innerHTML !== null) {
            document.getElementById("orgname").innerHTML =
              "Organization already exists.";
          }
        } else {
          if (document.getElementById("orgname").innerHTML !== null) {
            document.getElementById("orgname").innerHTML = "";
          }
        }
      } catch (e) {
        alert("Failed to get Existing Apps");
        //console.log(e);
      }
    });
  }

  /* To create new Organiozation */
  go() {
    this.grantloading = true;
    if (this.flagEdit === true) {
      //console.log(JSON.stringify(this.grantAccess));
      this.IdprestapiService.editOrganizationDetails(this.grantAccess).then(
        (response) => {
          try {
            this.grantloading = false;
            if (
              response.status === -1 &&
              response.statusText.toLowerCase() === "illegalstateexception"
            ) {
              this.message =
                "Backend server is down. Please try after sometime.";
            } else {
              if (response.status === 200 && response.statusText === "OK") {
                this.grantloading = false;
                this.addEditmsg = "edited";
                const actiondata = {
                  applicationName: this.grantAccess.orgName,
                  method: "create",
                  userName: this.grantAccess.orgAdmin,
                  orgName: this.grantAccess.orgName,
                  orgAdmin: this.grantAccess.orgAdmin,
                  domain: this.grantAccess.domain,
                };
                this.IdprestapiService.sendOrgMail(actiondata);
                this.successAdd();
                // window.location.reload();
              } else {
                document.getElementById("dbresponse").innerHTML =
                  "Some error occured.";
              }
            }
          } catch (e) {
            alert("Failed to do Edit Organization Details");
            //console.log(e);
          }
        }
      );
    } else {
      // check slave stage check box checkSlaveStageOnGo()
      if (this.flagNew === true) {
        //console.log(JSON.stringify(this.grantAccess));
        this.IdprestapiService.createOrganization(this.grantAccess).then(
          (response) => {
            try {
              //console.log(response.status);
              this.grantloading = false;
              if (
                response.status === -1 &&
                response.statusText.toLowerCase() === "illegalstateexception"
              ) {
                response.message =
                  "Backend server is down. Please try after sometime.";
              } else {
                if (response.status === 200 && response.statusText === "OK") {
                  this.grantloading = false;
                  const actiondata = {
                    applicationName: this.grantAccess.orgName,
                    method: "create",
                    userName: this.grantAccess.orgAdmin,
                    orgName: this.grantAccess.orgName,
                    orgAdmin: this.grantAccess.orgAdmin,
                    domain: this.grantAccess.domain,
                  };
                  this.IdprestapiService.sendOrgMail(actiondata);
                  this.successAdd();
                  this.addEditmsg = "added";
                } else {
                  document.getElementById("dbresponse").innerHTML =
                    "Some error occured.";
                }
              }
            } catch (e) {
              alert("Failed  in creating application");
              this.grantloading = false;
              //console.log(e);
            }
          }
        );
      }
    }
  }

  successAdd() {
    // console.log("successAdd");
    this.success.nativeElement.click();
  }

  /* Fetching Org Data */
  getOrgInfo() {
    this.flagEdit = true;
    this.flagNew = false;
    this.orgNames = [];
    this.grantAccess.orgName = "";
    this.IdprestapiService.getExistingOrgNames().then((response) => {
      try {
        const orgDetails = response.json().resource;
        this.orgList = orgDetails;
        for (let i = 0; i < orgDetails.length; i++) {
          this.orgNames.push(orgDetails[i].orgName);
        }
      } catch (e) {
        alert("Failed to get Existing Apps");
        //console.log(e);
      }
    });
  }

  getOrganizationDetailedInfo(data) {
    this.flagp = true;
    this.valueSubmit = "Edit";
    this.valueSubmitting = "Editing";
    // console.log(this.valueSubmit);
    for (let i = 0; i < this.orgList.length; i++) {
      const org = this.orgList[i].orgName;
      if (
        org !== undefined &&
        this.grantAccess.orgName !== undefined &&
        org.toLowerCase() === this.grantAccess.orgName.toLowerCase()
      ) {
        this.grantAccess.orgAdmin = this.orgList[i].orgAdmin;
        this.grantAccess.domain = this.orgList[i].domain;
      }
    }
  }

  reload() {
    this.flagEdit = false;
    this.flagNew = false;
    this.flagp = false;
  }
}
