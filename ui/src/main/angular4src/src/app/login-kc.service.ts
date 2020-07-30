/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Injectable } from "@angular/core";
import { IdprestapiService } from "./idprestapi.service";
import { Router } from "@angular/router";
import { IdpdataService } from "./idpdata.service";
import { CookieService } from "ngx-cookie";
import { KeycloakService } from "./keycloak/keycloak.service";

// to perform all the service calls that are performed after normal login

@Injectable()
export class LoginKcService {
  private _startupData: any;
  private landingPageRoute: string = "/dashboard";
  constructor(
    private IdpdataService: IdpdataService,
    private _cookieService: CookieService,
    private IdprestapiService: IdprestapiService,
    private router: Router,
    private keycloakService: KeycloakService
  ) {}

  getData() {
    const name = KeycloakService.getUsername();
    // get the keycloak token
    this.keycloakService.getToken().then((response) => {
      //console.log(response);
      this.IdpdataService.keycloakToken = response;
      // Pass KC token in the header to OAUTH
      const params = {
        grant_type: "password",
        username: name,
        password: "dummy",
        client_id: "idpclient",
      };
      this.IdprestapiService.obtainAccessToken(params).then((response) => {
        try {
          if (response) {
            if (!response.json().error) {
              const expireDate = new Date(
                new Date().getTime() + 1000 * response.json().expires_in
              );
              this.IdpdataService.expireTime = response.json().expires_in;
              this.IdpdataService.organization = response.json().organization;
              // console.log(response.json().expires_in);
              this._cookieService.put(
                "access_token",
                response.json().access_token,
                { expires: expireDate }
              );
              const expiretime = 1000 * response.json().expires_in;
              setTimeout(function () {
                alert("Your session has expired. Please login again");
                window.location.href = "/idpapp/login";
              }, expiretime);
            }
            this.callUserName();
            this.callValidatedLicense();
          } else {
            // console.log("No token");
          }
        } catch (e) {
          alert("failed to login");
          //console.log(e);
        }
      });
    });
  }

  callUserName() {
    // get user details
    this.getUserName();
  }
  callValidatedLicense() {
    // get license details
    this.getValidatedLicense();
  }
  getValidatedLicense() {
    this.IdprestapiService.getValidatedLicense().then((response) => {
      try {
        if (response) {
          const data = JSON.parse(response.json().status);
          this.IdpdataService.flag = false;
          this.IdpdataService.noAccessNavBars = true;
          for (const item of data.licenses) {
            if (item !== null) {
              this.IdpdataService.flag = true;
              this.IdpdataService.noAccessNavBars = false;
              this.IdpdataService.noAccess = false;
              this.router.navigate([this.landingPageRoute]);
              return;
            }
          }
          if (!this.IdpdataService.flag) {
            if (this.IdpdataService.showService) {
              // user is Pipeline admin
              this.IdpdataService.noAccessNavBars = true;
              this.IdpdataService.noAccess = false;
              this.router.navigate([this.landingPageRoute]);
            } else {
              this.IdpdataService.noAccessNavBars = true;
              this.IdpdataService.noAccess = true;
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
  getUserName() {
    this.IdprestapiService.getUserName().then((response) => {
      try {
        if (response) {
          // //console.log(response);
          const userDetails = JSON.parse(response.json().resource);
          this.IdpdataService.idpUserName = userDetails.user_id;
          // console.log(this.IdpdataService.idpUserName);
          this.IdpdataService.roles = userDetails.roles;
          this.IdpdataService.permissions = userDetails.permissions;
          let permission = "";
          for (let i = 0; i < this.IdpdataService.roles.length; i++) {
            if (
              this.IdpdataService.role.indexOf(this.IdpdataService.roles[i]) ===
              -1
            ) {
              this.IdpdataService.role =
                this.IdpdataService.role + this.IdpdataService.roles[i] + " ";
            }
          }
          if (document.getElementById("idpUserName")) {
            document.getElementById(
              "idpUserName"
            ).title = this.IdpdataService.role;
          }
          if (this.IdpdataService.role.indexOf("RELEASE_MANAGER") !== -1) {
            this.IdpdataService.showRelease = true;
          }
          if (this.IdpdataService.role.indexOf("IDP_ADMIN") !== -1) {
            this.IdpdataService.showService = true;
          }
          for (let j = 0; j < this.IdpdataService.permissions.length; j++) {
            permission = this.IdpdataService.permissions[j];
            if (permission === "CREATE_APPLICATION") {
              this.IdpdataService.createAppflag = true;
              // console.log(this.IdpdataService.createAppflag);
            }
            if (permission === "CREATE_LICENSE") {
              this.IdpdataService.createLicenseflag = true;
            }
            if (permission === "CREATE_PIPELINE") {
              this.IdpdataService.createPipelineflag = true;
            }
            if (permission === "COPY_PIPELINE") {
              this.IdpdataService.copyPipelineflag = true;
            }
            if (permission === "EDIT_PIPELINE") {
              this.IdpdataService.editPipelineflag = true;
            }
            if (permission === "DELETE_PIEPLINE") {
              this.IdpdataService.deletePipelineflag = true;
            }
            if (permission === "APPROVE_BUILD") {
              this.IdpdataService.approveBuildFlag = true;
            }
            if (permission === "APPROVE_DEPLOY") {
              this.IdpdataService.approveDeployFlag = true;
            }
            if (permission === "CREATE_ORGANIZATION") {
              this.IdpdataService.createOrganisationflag = true;
            }
          }
        }
      } catch (e) {
        //console.log(e);
        alert("Failed in getting the user details ");
      }
    });
  }
}
