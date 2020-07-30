import { Injectable } from "@angular/core";
import { OAuthService } from "angular-oauth2-oidc";
import { IdprestapiService } from ".././idprestapi.service";
import { Router } from "@angular/router";
import { IdpdataService } from ".././idpdata.service";
import { CookieService } from "ngx-cookie";

@Injectable()
export class SsoService {
  constructor(
    private oauthService: OAuthService,
    private IdpdataService: IdpdataService,
    private _cookieService: CookieService,
    private IdprestapiService: IdprestapiService,
    private router: Router
  ) {}

  getAuthorizedFromIDPServer(): Promise<{ statusCode; message }> {
    let promise = new Promise<{ statusCode; message }>((resolve, reject) => {
      let email: String = this.oauthService.getIdentityClaims()["email"];
      let name: String = email.split("@")[0];

      // get the keycloak token
      let accessToken = this.oauthService.getAccessToken();
      if (accessToken !== null) {
        //console.log(accessToken);
        this.IdpdataService.keycloakToken = accessToken;
        //Pass KC token in the header to OAUTH
        var params = {
          grant_type: "password",
          username: name,
          password: "dummy",
          client_id: "idpclient",
        };

        this.IdprestapiService.obtainAccessToken(params).then((response) => {
          if (response.status >= 500) {
            // console.error(
            //   "error while obtaining access token from oauth server",
            //   response
            // );
            reject({
              statusCode: response.status,
              message:
                "Looks like there was an issue during authorisation. Please try again . Contact IDP support team if issue persists.",
            });
          } else if (response.status >= 400 && response.status < 500) {
            console.error(
              "error while obtaining access token from oauth server",
              response
            );
            let error = response.json().error;
            if (error === "invalid_grant") {
              let userName = this.oauthService.getIdentityClaims()["name"];
              reject({
                statusCode: response.status,
                message: ` "${userName}" is not authorized to access IDP. Please contact your IDP administrator to get access. `,
              });
            } else {
              reject({
                statusCode: response.status,
                message: error,
              });
            }
          } else {
            try {
              if (response) {
                if (!response.json().error) {
                  let expireDate = new Date(
                    new Date().getTime() + 1000 * response.json().expires_in
                  );
                  this.IdpdataService.expireTime = response.json().expires_in;
                  this.IdpdataService.organization = response.json().organization;
                  this._cookieService.put(
                    "access_token",
                    response.json().access_token,
                    { expires: expireDate }
                  );
                  var expiretime = 1000 * response.json().expires_in;
                  this.callUserName();

                  setTimeout(() => {
                    alert("Your session has expired. Please login again");
                    window.location.href = "/idpapp/login";
                  }, expiretime);
                }
              } else {
                console.error(
                  "error while obtaining access token from oauth server",
                  response
                );
                reject({
                  statusCode: response.status,
                  message:
                    "Failed to login. Please contact your IDP support team",
                });
              }
            } catch (e) {
              console.error(
                "error while obtaining access token from oauth server",
                e
              );
              // console.error("oauth response", response);
              reject({
                statusCode: response.status,
                message:
                  "Failed to login. Please contact your IDP administrator",
              });
            }
          }
        });
      }
    });
    return promise;
  }

  callUserName() {
    //get user details
    this.getUserName();
  }

  getUserName() {
    this.IdprestapiService.getUserName().then((response) => {
      try {
        if (response) {
          ////console.log(response);
          var userDetails = JSON.parse(response.json().resource);
          this.IdpdataService.idpUserName = userDetails.user_id;
          //console.log(this.IdpdataService.idpUserName);
          this.IdpdataService.roles = userDetails.roles;
          this.IdpdataService.permissions = userDetails.permissions;
          let permission = "";
          for (var i = 0; i < this.IdpdataService.roles.length; i++) {
            if (
              this.IdpdataService.role.indexOf(this.IdpdataService.roles[i]) ===
              -1
            ) {
              this.IdpdataService.role =
                this.IdpdataService.role + this.IdpdataService.roles[i] + " ";
            }

            //this.IdpdataService.role = this.IdpdataService.role + this.IdpdataService.roles[i] + ' ';
          }
          //////console.log(this.IdpdataService.role);
          if (document.getElementById("idpUserName")) {
            document.getElementById(
              "idpUserName"
            ).title = this.IdpdataService.role;
          }
          //console.log(this.IdpdataService.permissions);
          ////console.log(this.IdpdataService.role);
          if (this.IdpdataService.role.indexOf("RELEASE_MANAGER") !== -1) {
            this.IdpdataService.showRelease = true;
          }
          if (this.IdpdataService.role.indexOf("IDP_ADMIN") !== -1) {
            this.IdpdataService.showService = true;
          }
          for (var j = 0; j < this.IdpdataService.permissions.length; j++) {
            permission = this.IdpdataService.permissions[j];
            if (permission === "CREATE_APPLICATION") {
              this.IdpdataService.createAppflag = true;
              //console.log(this.IdpdataService.createAppflag);
            }

            if (permission === "CREATE_LICENSE") {
              this.IdpdataService.createLicenseflag = true;
            }

            if (permission === "CREATE_PIPELINE") {
              this.IdpdataService.createPipelineflag = true;
            }
            if (permission === "COPY_PIPELINE") {
              this.IdpdataService.copyPipelineflag = true;
              //$scope.copyPipelineflag=true;
            }
            if (permission === "EDIT_PIPELINE") {
              this.IdpdataService.editPipelineflag = true;
              //$scope.editPipelineflag=true;
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
