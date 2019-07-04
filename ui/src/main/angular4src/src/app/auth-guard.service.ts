/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Injectable } from "@angular/core";
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { CookieService } from "ngx-cookie";
import { IdpdataService } from "./idpdata.service";
import { AdalService } from "adal-angular4";
import { IdprestapiService } from "./idprestapi.service";
import { KeycloakService } from "./keycloak/keycloak.service";
@Injectable()
export class AuthGuardService implements CanActivate {
    private landingPageRoute = "/previousConfig";

  constructor(
    private router: Router,
    private _cookieService: CookieService,
    private restApiService: IdprestapiService,
    private adalSvc: AdalService,
    private idpdataService: IdpdataService,
    private keycloakService: KeycloakService
  ) {

  }

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
        if (this.idpdataService.authmode === "azureAd" && !this.adalSvc.userInfo.authenticated) {
            this.adalSvc.login();
            return false;
        }

        if (this.idpdataService.authmode === "azureAd"
        && this.adalSvc.userInfo.userName !== "" && !this.authUser(this.adalSvc.userInfo.userName)) {
            console.log("Authentication failure");
            return false;
        }

        if (this.idpdataService.authmode === "keycloak") {
            if (this.keycloakService.checkLoggedIn() === false) {
            this.router.navigate(["/keycloak"]);
            }
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
        if (this._cookieService.get("access_token")) {
        // logged in so return true

        return true;
        } else if (this.idpdataService.authmode === "keycloak"
        && this.keycloakService.checkLoggedIn()) {
        return true;
        }
        // not logged in so redirect to login page with the return url
        this.router.navigate(["/login"]);
       // console.log("redirect To Login");
        return false;

    }
  }
  authUser(username) {

    const loginData = {
        grant_type: "password",
        username: username,
        password: "random",
        client_id: "idpclient"
    };

    this.restApiService.obtainAccessToken(loginData)
        .then(response => {
        if (response) {
            if (!response.json().error) {
            const expireDate = new Date(new Date().getTime() + (1000 * response.json().expires_in));
            this._cookieService.put("access_token", response.json().access_token, { "expires": expireDate });
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
