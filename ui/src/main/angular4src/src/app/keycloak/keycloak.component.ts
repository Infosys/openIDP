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
import { Router } from "@angular/router";
import { IdpdataService } from "../idpdata.service";
import { CookieService } from "ngx-cookie";
import { KeycloakService } from "./keycloak.service";
import { LoginKcService } from "../login-kc.service";
@Component({
  selector: "app-keycloak",
  templateUrl: "./keycloak.component.html",
})
export class KeycloakComponent implements OnInit {
  constructor(
    private idpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router,
    private keycloakService: KeycloakService,
    private _cookieService: CookieService,
    private loginkcService: LoginKcService
  ) {
    // alert("In KeycloakkkC");
    this.IdprestapiService.getData().then((response) => {
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

          // KC properties
          this.idpdataService.keycloakUrl = response.json().keycloakUrl;
          this.idpdataService.keycloakRealm = response.json().keycloakRealm;
          this.idpdataService.keycloakClientId = response.json().keycloakClientId;
          KeycloakService.init(
            this.idpdataService.keycloakUrl,
            this.idpdataService.keycloakRealm,
            this.idpdataService.keycloakClientId
          )
            .then(() => this.loginkcService.getData())
            .catch();
        }
      } catch (e) {
        //console.log(e);
        alert("Failed in getting the KC properties ");
      }
    });
  }

  ngOnInit() {}
}
