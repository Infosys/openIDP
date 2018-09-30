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
import {KeycloakService} from "./keycloak.service";
import {LoginKcService} from "../login-kc.service";
@Component({
  selector: "app-keycloak",
  templateUrl: "./keycloak.component.html"
})
export class KeycloakComponent implements OnInit {

  constructor(private IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router,
    private keycloakService: KeycloakService,
    private _cookieService: CookieService,
    private loginkcService: LoginKcService) {
// alert("In KeycloakkkC");
            this.IdprestapiService.getData()
        .then(response => {
        try {
        if (response) {
                // this.IdpdataService.devServerURL = response.json().idpresturl;
                // this.IdpdataService.devServerURL = "https://idplinv02:8889/idprest";
                 this.IdpdataService.devServerURL = "http://server411214d:8889/idprest";
                this.IdpdataService.subscriptionServerURL = response.json().idpsubscriptionurl;
                this.IdpdataService.IDPDashboardURL = response.json().idpdashboardurl;
                this.IdpdataService.IDPLink = response.json().IDPLink;
                this.IdpdataService.geUrl = response.json().geUrl;
                this.IdpdataService.geFlag = response.json().geFlag;
                this.IdpdataService.serverUrl = response.json().tfsServerUrl;
                this.IdpdataService.uName = response.json().uName;
                this.IdpdataService.pass = response.json().pass;
                console.log(this.IdpdataService.devServerURL);

                // KC properties
                this.IdpdataService.keycloakUrl = response.json().keycloakUrl;
                this.IdpdataService.keycloakRealm = response.json().keycloakRealm;
                this.IdpdataService.keycloakClientId = response.json().keycloakClientId;
                KeycloakService.init(this.IdpdataService.keycloakUrl, this.IdpdataService.keycloakRealm, this.IdpdataService.keycloakClientId)
    .then(() => this.loginkcService.getData())
    .catch(e => console.log(e));
            }
        } catch (e) {
             console.log(e);
            alert("Failed in getting the KC properties ");
        }
      });
}

  ngOnInit() {
  }

}
