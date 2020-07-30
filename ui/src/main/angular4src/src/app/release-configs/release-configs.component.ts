/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { ChangeDetectorRef } from "@angular/core";
import { IdprestapiService } from "../idprestapi.service";
import { CookieService } from "ngx-cookie";

@Component({
  selector: "app-release-configs",
  templateUrl: "./release-configs.component.html",
  styleUrls: [],
})
export class ReleaseConfigsComponent implements OnInit {
  constructor(
    private IdpService: IdpService,
    public IdpdataService: IdpdataService,
    private cdr: ChangeDetectorRef,
    private IdprestapiService: IdprestapiService,
    private _cookieService: CookieService
  ) {
    this.callforRest();
  }

  ngOnInit() {}

  /* Fetching java rest to get details */
  callforRest() {
    this.IdprestapiService.getData().then((response) => {
      try {
        if (response) {
          this.IdpdataService.devServerURL = response.json().idpresturl;
          this.IdpdataService.IDPDashboardURL = response.json().idpdashboardurl;
          this.IdpdataService.IDPLink = response.json().IDPLink;
          this.IdpdataService.geUrl = response.json().geUrl;
          this.IdpdataService.geFlag = response.json().geFlag;
          this.IdpdataService.serverUrl = response.json().tfsServerUrl;
          this.IdpdataService.uName = response.json().uName;
          this.IdpdataService.pass = response.json().pass;

          if (this._cookieService.get("access_token")) {
            //console.log("details");
            this.IdpdataService.loadReleasePage = true;
            this.IdpService.getDetails();
          }
        }
      } catch (e) {
        alert("failed to get properties details");
        //console.log(e);
      }
    });
  }
}
