import { Component, OnInit } from "@angular/core";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-dashboard-view",
  templateUrl: "./dashboard-view.component.html",
  styleUrls: ["./dashboard-view.component.css"],
})
export class DashboardViewComponent implements OnInit {
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

  dashboardUrl: any = "";

  getUrl() {
    // console.log("dashboard :   " + this.dashboardUrl);
    return this.dashboardUrl;
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
          this.dashboardUrl =
            this.IdpdataService.IDPDashboardURL + "?theme=light&kiosk=tv";
        }
      } catch (e) {
        alert("failed to get properties details");
        //console.log(e);
      }
    });
  }
}
