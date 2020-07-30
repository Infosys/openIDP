/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import { IdpService } from "../idp-service.service";
import { IdprestapiService } from "../idprestapi.service";
import { IdpdataService } from "../idpdata.service";
import { Router, ActivatedRoute } from "@angular/router";
import { CookieService } from "ngx-cookie";
import { environment } from "../../environments/environment";
import { StartupService } from "../startup.service";
import { LoadingModule, ANIMATION_TYPES } from "ngx-loading";

@Component({
  selector: "app-idpheader",
  templateUrl: "./idpheader.component.html",
  styleUrls: ["./idpheader.component.css"],
})
export class IdpheaderComponent implements OnInit {
  hidePreviousConfig = false;
  hideCreateConfig = false;
  hideRelease = false;
  hideLogout = false;
  hideService = false;
  hideChangePassword = false;
  userRoles: any;
  isIDPAdmin = false;
  idpUserName: any = this.IdpdataService.idpUserName;
  role: any = this.IdpdataService.role;
  isAboutView: boolean = false;
  isDashboard: boolean = false;
  isNotification: boolean = false;

  constructor(
    private idpservice: IdpService,
    public IdpdataService: IdpdataService,
    private _cookieService: CookieService,
    private IdprestapiService: IdprestapiService,
    private router: Router,
    private route: ActivatedRoute,
    private startupService: StartupService
  ) {
    this.getRouteConfig();
  }
  azureFlag = false;
  keycloakFlag = false;

  getRouteConfig() {
    if (this.startupService.getData().authmode === "azureAd") {
      this.azureFlag = true;
    } else if (this.startupService.getData().authmode === "keycloak") {
      this.keycloakFlag = true;
    }
    const url: string = this.router.url;

    if (url.includes("createConfig")) {
      this.hideCreateConfig = true;
      this.hidePreviousConfig = false;
      this.hideRelease = false;
      this.hideService = false;
      this.isDashboard = false;
      this.isAboutView = false;

      if (url.includes("createapp")) {
        this.hideCreateConfig = false;
        this.IdpdataService.hideApp = true;
      } else {
        this.IdpdataService.hideApp = false;
      }
    } else if (
      url.includes("previousConfig") &&
      !url.includes("trigger") &&
      !url.includes("stageviewHistory") &&
      !url.includes("stageviewTrigger") &&
      !url.includes("release") &&
      !url.includes("approve") &&
      !url.includes("schedule") &&
      !url.includes("workflow") &&
      !url.includes("servicePortal") &&
      !url.includes("createLicense")
    ) {
      this.hidePreviousConfig = true;
      this.hideRelease = false;
      this.IdpdataService.hideApp = false;
      this.hideService = false;
      this.isAboutView = false;
      this.isDashboard = false;
      if (!url.includes("approve")) {
        this.IdpdataService.hideDashboard = false;
      }
    } else if (url.includes("success")) {
      this.hidePreviousConfig = false;
      this.hideRelease = false;
      this.IdpdataService.hideApp = false;
      this.hideService = false;
    } else if (
      url.includes("previousConfig") &&
      (url.includes("trigger") ||
        url.includes("stageviewHistory") ||
        url.includes("stageviewHistory") ||
        url.includes("approve") ||
        url.includes("schedule") ||
        url.includes("workflow"))
    ) {
      this.hidePreviousConfig = false;
      this.hideRelease = false;
      this.IdpdataService.hideApp = false;
      this.hideService = false;
      this.isAboutView = false;
      this.isDashboard = false;
      if (url.includes("approve")) {
        this.IdpdataService.hideDashboard = true;
      }
    } else if (url.includes("release")) {
      this.hidePreviousConfig = false;
      this.hideRelease = true;
      this.IdpdataService.hideApp = false;
      this.isAboutView = false;
      this.isDashboard = false;
    } else if (url.includes("servicePortal")) {
      this.hidePreviousConfig = false;
      this.hideRelease = false;
      this.hideCreateConfig = false;
      this.isDashboard = false;
      this.hideService = false;
      this.isAboutView = false;
    } else if (url.includes("createLicense")) {
      this.hidePreviousConfig = false;
      this.hideRelease = false;
      this.hideCreateConfig = false;
      this.hideService = false;
      this.isDashboard = false;
      this.isAboutView = false;
    }
    if (url.includes("dashboardView")) {
      this.isDashboard = true;
      this.hidePreviousConfig = false;
    }
    if (url.includes("notificationPage")) {
      this.isNotification = true;
      this.hidePreviousConfig = false;
    }
    if (url.includes("aboutView")) {
      this.isAboutView = true;
      this.hidePreviousConfig = false;
    }
  }
  ngOnInit() {}

  getCursor() {
    if (this.IdpdataService.freezeNavBars === true) {
      // console.log(2235);
      return "not-allowed";
    } else {
      return "pointer";
    }
  }

  getColor() {
    return "white";
  }

  getfont() {
    return "15px";
  }

  freezeHeaders() {
    if (
      this.IdpdataService.freezeNavBars ||
      this.IdpdataService.noAccessNavBars
    ) {
      this.hidePreviousConfig = true;
      this.hideCreateConfig = true;
      this.hideRelease = true;
      this.hideService = true;
      this.hideChangePassword = true;
      this.IdpdataService.hideApp = true;
    } else {
      this.getRouteConfig();
    }
  }

  AppLogout() {
    // console.log("logout successful");
  }
  /* Logout */
  logout() {
    this.IdprestapiService.logout().then((response) => {
      try {
        if (response != null) {
          //console.log(response);
        }
      } catch (e) {
        alert("Failed to Logout");
        //console.log(e);
      }
    });
    this._cookieService.removeAll();
  }
  changePasswordKC() {
    window.open(
      this.IdpdataService.keycloakUrl +
        "/realms/" +
        this.IdpdataService.keycloakRealm +
        "/account/password?redirect_uri=" +
        document.baseURI
    );
  }
}
