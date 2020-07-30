import { Component, OnInit } from "@angular/core";

import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-about-view",
  templateUrl: "./about-view.component.html",
  styleUrls: [],
})
export class AboutViewComponent implements OnInit {
  constructor(
    private IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router
  ) {
    this.IdpService.initMain();
    this.callforRest();
  }

  ngOnInit() {
    this.IdpService.initMain();
    this.callforRest();
    //this.getUrl();
  }

  githubUrl: string = "https://github.com/infosys/openIDP/wiki";

  getUrl() {
    // console.log("github :   " + this.githubUrl);
    return this.githubUrl;
  }
  callforRest() {
    this.IdprestapiService.getData().then((response) => {
      try {
        if (response) {
          this.IdpdataService.devServerURL = response.json().idpresturl;
          this.IdpdataService.subscriptionServerURL = response.json().idpsubscriptionurl;
          this.IdpdataService.IDPDashboardURL = response.json().idpdashboardurl;
          this.IdpdataService.IDPLink = response.json().IDPLink;
          this.IdpdataService.geUrl = response.json().geUrl;
          this.IdpdataService.geFlag = response.json().geFlag;
          this.IdpdataService.serverUrl = response.json().tfsServerUrl;
          this.IdpdataService.uName = response.json().uName;
          this.IdpdataService.pass = response.json().pass;
        }
      } catch (e) {
        alert("failed to get properties details");
        //console.log(e);
      }
    });
  }
}
