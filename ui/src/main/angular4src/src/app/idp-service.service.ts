/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Injectable } from "@angular/core";
import { IdprestapiService } from "./idprestapi.service";
import { IdpdataService } from "./idpdata.service";
import { Router } from "@angular/router";
import { CookieService } from "ngx-cookie";

@Injectable()
export class IdpService {
  private landingPageRoute: string = "/dashboard";
  constructor(
    private idprestapiService: IdprestapiService,
    private router: Router,
    private idpdataService: IdpdataService,
    private _cookieService: CookieService //  private ShowConfigurationsComponent:ShowConfigurationsComponent
  ) {
    // this.initMain();
  }

  getIDPDropdownProperties() {}

  initMain() {
    //   this.idpdataService.permissions=[];
    this.idprestapiService.getData().then((response) => {
      try {
        if (response) {
          this.idpdataService.devServerURL = response.json().idpresturl;
          this.idpdataService.subscriptionServerURL = response.json().idpsubscriptionurl;
          this.idpdataService.IDPDashboardURL = response.json().idpdashboardurl;
          this.idpdataService.IDPLink = response.json().IDPLink;
          this.idpdataService.geUrl = response.json().geUrl;
          this.idpdataService.geFlag = response.json().geFlag;
          this.idpdataService.serverUrl = response.json().tfsServerUrl;
          this.idpdataService.uName = response.json().uName;
          this.idpdataService.pass = response.json().pass;

          if (this._cookieService.get("access_token")) {
            //console.log("details");
            this.idpdataService.RestApiDetails = true;
            this.getDetails();
          }
        }
      } catch (e) {
        //console.log(e);
        alert("Failed in getting the properties ");
      }
    });
  }

  getValidatedLicense() {
    this.idprestapiService.getValidatedLicense().then((response) => {
      // //console.log(response);
      try {
        if (response) {
          const data = JSON.parse(response.json().status);
          this.idpdataService.flag = false;
          this.idpdataService.noAccessNavBars = true;
          for (const item of data.licenses) {
            if (item !== null) {
              this.idpdataService.flag = true;
              this.idpdataService.noAccessNavBars = false;
              this.idpdataService.noAccess = false;
              this.router.navigate([this.landingPageRoute]);
              return;
            }
          }

          if (!this.idpdataService.flag) {
            if (this.idpdataService.showService) {
              // user is Pipeline admin
              this.idpdataService.noAccessNavBars = true;
              this.idpdataService.noAccess = false;
              this.router.navigate([this.landingPageRoute]);
            } else {
              this.idpdataService.noAccessNavBars = true;
              this.idpdataService.noAccess = true;
              this.router.navigate([this.landingPageRoute]);
            }
          }
        }
      } catch (e) {
        //console.log(e);
        alert("Failed in getting the license details ");
      }
    });
  }
  getDetails() {
    this.idprestapiService.getUserName().then((response) => {
      try {
        if (response) {
          // //console.log(response);
          const userDetails = JSON.parse(response.json().resource);
          this.idpdataService.idpUserName = userDetails.user_id;
          this.idpdataService.organization = userDetails.orgName;
          // console.log(this.idpdataService.idpUserName);
          this.idpdataService.roles = userDetails.roles;
          this.idpdataService.permissions = userDetails.permissions;
          let permission = "";
          for (let i = 0; i < this.idpdataService.roles.length; i++) {
            if (
              this.idpdataService.role.indexOf(this.idpdataService.roles[i]) ===
              -1
            ) {
              this.idpdataService.role =
                this.idpdataService.role + this.idpdataService.roles[i] + " ";
            }
          }
          if (document.getElementById("idpUserName")) {
            document.getElementById(
              "idpUserName"
            ).title = this.idpdataService.role;
          }
          // console.log(this.idpdataService.permissions);
          //console.log(this.IdpdataService.role);
          if (this.idpdataService.role.indexOf("RELEASE_MANAGER") !== -1) {
            this.idpdataService.showRelease = true;
          }
          if (this.idpdataService.role.indexOf("IDP_ADMIN") !== -1) {
            this.idpdataService.showService = true;
          }
          for (let j = 0; j < this.idpdataService.permissions.length; j++) {
            permission = this.idpdataService.permissions[j];
            if (permission === "CREATE_APPLICATION") {
              this.idpdataService.createAppflag = true;
              //console.log(this.idpdataService.createAppflag);
            }
            if (permission === "CREATE_PIPELINE") {
              this.idpdataService.createPipelineflag = true;
            }
            if (permission === "COPY_PIPELINE") {
              this.idpdataService.copyPipelineflag = true;
            }
            if (permission === "EDIT_PIPELINE") {
              this.idpdataService.editPipelineflag = true;
            }
            if (permission === "DELETE_PIEPLINE") {
              this.idpdataService.deletePipelineflag = true;
            }
            if (permission === "APPROVE_BUILD") {
              this.idpdataService.approveBuildFlag = true;
            }
            if (permission === "APPROVE_DEPLOY") {
              this.idpdataService.approveDeployFlag = true;
            }

            if (permission === "CREATE_ORGANIZATION") {
              this.idpdataService.createOrganisationflag = true;
            }

            if (permission === "CREATE_LICENSE") {
              this.idpdataService.createLicenseflag = true;
            }
          }
        }
      } catch (e) {
        //console.log(e);
        alert("Failed in getting the user details ");
      }
    });
    return true;
  }
  copy(o) {
    let newO, i;

    if (typeof o !== "object") {
      return o;
    }
    if (!o) {
      return o;
    }

    if ("[object Array]" === Object.prototype.toString.apply(o)) {
      newO = [];
      for (i = 0; i < o.length; i += 1) {
        newO[i] = this.copy(o[i]);
      }
      return newO;
    }

    newO = {};
    for (i in o) {
      if (o.hasOwnProperty(i)) {
        newO[i] = this.copy(o[i]);
      }
    }
    return newO;
  }
}
