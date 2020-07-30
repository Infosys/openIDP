/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Injectable } from "@angular/core";
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from "@angular/router";
import { CookieService } from "ngx-cookie";
import { IdpdataService } from "./idpdata.service";
import { AdalService } from "adal-angular4";
import { IdprestapiService } from "./idprestapi.service";
import { KeycloakService } from "./keycloak/keycloak.service";
import { SsoService } from "./sso/sso.service";
import { OAuthService } from "angular-oauth2-oidc";
@Injectable()
export class AuthGuardService implements CanActivate {
  private landingPageRoute = "/dashboard";

  constructor(
    private router: Router,
    private _cookieService: CookieService,
    private restApiService: IdprestapiService,
    private adalSvc: AdalService,
    private idpdataService: IdpdataService,
    private ssoService: SsoService,
    private oauthService: OAuthService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const urlChangeLogin = state.url.includes("login");

    const urlChangeSuccess = state.url.includes("success");
    const urlChangeTrigger = state.url.includes("trigger");
    const urlChangeStageviewTrigger = state.url.includes("stageviewTrigger");
    const urlChangeStageviewHistory = state.url.includes("stageviewHistory");
    const urlChangeRelease = state.url.includes("release");

    const urlChangeApprove = state.url.includes("approve");
    const urlChangeSchedule = state.url.includes("schedule");
    const urlValue = state.url;

    if (urlChangeLogin) {
      if (!this._cookieService.get("access_token")) {
        if (
          this.idpdataService.authmode === "azureAd" &&
          !this.adalSvc.userInfo.authenticated
        ) {
          this.adalSvc.login();
          return false;
        }

        if (
          this.idpdataService.authmode === "azureAd" &&
          this.adalSvc.userInfo.userName !== "" &&
          !this.authUser(this.adalSvc.userInfo.userName)
        ) {
          // console.log("Authentication failure");
          return false;
        }
        // console.log("auth mode he " + this.idpdataService.authmode);
        if (this.idpdataService.authmode === "keycloak") {
          if (
            this.oauthService.hasValidIdToken() &&
            this._cookieService.get("access_token")
          ) {
            //alert("loggedin");
            return true;
          }
          //alert("not login");
          this.router.navigate(["/sso"]);
          return false;
        }
      }
      if (this._cookieService.get("access_token")) {
        // logged in so return true
        this.router.navigate([this.landingPageRoute]);
        return false;
      }
      // not logged in so redirect to login page with the return url
      return true;
    } else if (urlChangeSuccess) {
      if (this._cookieService.get("access_token")) {
        // logged in so return true
        if (this.idpdataService.data.masterJson.basicInfo === undefined) {
          this.router.navigate([this.landingPageRoute]);
          return false;
        } else {
          return true;
        }
      }
      this.router.navigate(["/login"]);
      // not logged in so redirect to login page with the return url
      return false;
    } else if (urlChangeTrigger) {
      if (this._cookieService.get("access_token")) {
        // logged in so return true
        //this.router.navigate([this.landingPageRoute]);
        return true;
      }
      this.router.navigate(["/login"]);
      // not logged in so redirect to login page with the return url
      return false;
    } else if (urlChangeStageviewTrigger) {
      if (this._cookieService.get("access_token")) {
        // logged in so return true
        this.router.navigate([this.landingPageRoute]);
        return false;
      }
      this.router.navigate(["/login"]);
      // not logged in so redirect to login page with the return url
      return false;
    } else if (urlChangeStageviewHistory) {
      if (this._cookieService.get("access_token")) {
        // logged in so return true //console.log("redirectTo config");
        this.router.navigate([this.landingPageRoute]);
        return false;
      }
      this.router.navigate(["/login"]);
      // not logged in so redirect to login page with the return url
      return false;
    } else if (urlChangeApprove) {
      if (this._cookieService.get("access_token")) {
        // logged in so return true //console.log("redirectTo config");
        this.router.navigate([this.landingPageRoute]);
        return false;
      }
      this.router.navigate(["/login"]);
      // not logged in so redirect to login page with the return url
      return false;
    } else if (urlChangeSchedule) {
      if (this._cookieService.get("access_token")) {
        // logged in so return true //console.log("redirectTo config");
        this.router.navigate([this.landingPageRoute]);
        return false;
      }
      this.router.navigate(["/login"]);
      // not logged in so redirect to login page with the return url
      return false;
    } else if (urlChangeRelease) {
      if (this._cookieService.get("access_token")) {
        return true;
      }
      this.router.navigate(["/login"]);
      // not logged in so redirect to login page with the return url
      return false;
    } else if (urlValue !== "/") {
      //alert("stage1")
      //console.log("auth mode"+this.idpdataService.authmode)
      if (this.idpdataService.authmode == "keycloak") {
        this.oauthService.loadDiscoveryDocumentAndTryLogin().then(() => {
          //  console.log("Inside load_document \n Valid id token : " + this.oauthService.hasValidIdToken() + "\n" +
          //  " Valid Access Token : " + this.oauthService.hasValidAccessToken());
        });

        if (
          this.oauthService.hasValidAccessToken() &&
          this._cookieService.get("access_token")
        ) {
          return true;
        } else {
          this.router.navigate(["/sso"]);
          //console.log("redirect To Login");
          return false;
        }
      } else if (this._cookieService.get("access_token")) {
        // logged in so return true

        return true;
      }
      // not logged in so redirect to login page with the return url
      this.router.navigate(["/sso"]);
      return false;
    }
  }
  authUser(username) {
    const loginData = {
      grant_type: "password",
      username: username,
      password: "random",
      client_id: "idpclient",
    };

    this.restApiService.obtainAccessToken(loginData).then((response) => {
      if (response) {
        if (!response.json().error) {
          const expireDate = new Date(
            new Date().getTime() + 1000 * response.json().expires_in
          );
          this._cookieService.put(
            "access_token",
            response.json().access_token,
            { expires: expireDate }
          );
          this.router.navigate([this.landingPageRoute]);
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    });
    return false;
  }
}
