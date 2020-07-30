/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import {
  OAuthService,
  AuthConfig,
  NullValidationHandler,
} from "angular-oauth2-oidc";
import { SsoService } from "./sso.service";
import { CookieService } from "ngx-cookie";
import { Router } from "@angular/router";
import { IdprestapiService } from "../idprestapi.service";

@Component({
  selector: "app-sso",
  templateUrl: "./sso.component.html",
  styleUrls: ["./sso.component.css"],
})
export class SsoComponent implements OnInit {
  error: any;

  constructor(
    private oauthService: OAuthService,
    private ssoService: SsoService,
    private router: Router,
    private _cookieService: CookieService,
    private idpRest: IdprestapiService
  ) {}

  ngOnInit() {
    // console.log("inside sso");
    this.oauthService.loadDiscoveryDocumentAndTryLogin().then(() => {
      //alert("inside load");
      if (!this.oauthService.hasValidIdToken()) {
        this._cookieService.removeAll();
        sessionStorage.clear();

        this.oauthService.initImplicitFlow();
      } else {
        // console.log(this.oauthService.getAccessToken());
        //console.log(this.oauthService.hasValidIdToken());
        // console.log(this.oauthService.getIdentityClaims()["name"]);

        if (!this._cookieService.get("access_token")) {
          this.ssoService.getAuthorizedFromIDPServer().catch((error) => {
            // console.log("auth error " + error);
            this.error = error;
          });
          this.router.navigate(["/dashboard"]);
        } else {
          this.router.navigate(["/dashboard"]);
        }
      }
    });
  }

  logout() {
    this.idpRest.logout();
  }
}
