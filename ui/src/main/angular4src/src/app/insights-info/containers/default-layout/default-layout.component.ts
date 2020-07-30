import { Component, Input, OnInit } from "@angular/core";
import { navItems } from "./../../_nav";
import { Router, RouterStateSnapshot, ActivatedRoute } from "@angular/router";
import { RestApiService } from "../../shared/rest-api.service";
import { CookieService } from "ngx-cookie";
import { DataApiService } from "../../shared/data-api.service";

@Component({
  selector: "app-dashboard",
  templateUrl: "./default-layout.component.html",
  styleUrls: ["./default-layout.component.css"],
})
export class DefaultLayoutComponent implements OnInit {
  public navItems = navItems;
  public sidebarMinimized = true;
  private changes: MutationObserver;
  public element: HTMLElement = document.body;
  disableFlag = true;
  appName = "";
  useCase = "";
  dropdownFlag = false;
  appNameArr = [];
  activatedTabs = {};
  subcriptionData = {
    orgName: "",
  };
  applicationList = [];
  appNameMenuFlag = true;
  tabs = {
    diagnose: false,
    insights: false,
    "agg-logs": false,
  };

  constructor(
    private router: Router,
    private restService: RestApiService,
    private _cookieService: CookieService,
    private dataService: DataApiService
  ) {
    this.changes = new MutationObserver((mutations) => {
      this.sidebarMinimized = document.body.classList.contains(
        "sidebar-minimized"
      );
    });

    this.changes.observe(<Element>this.element, {
      attributes: true,
    });

    this.router.events.subscribe((val) => {
      const url = this.router.url;
      this.tabs.diagnose = url.includes("diagnose") ? true : false;
      this.tabs.insights = url.includes("insights") ? true : false;
      this.tabs["agg-logs"] = url.includes("agg-logs") ? true : false;
    });
  }

  ngOnInit() {
    this.activatedTabs = this.dataService.activatedTabs;
    this.subcriptionData = this.dataService.subscriptionData;
    this.appNameMenuFlag = this.dataService.appNameMenuFlag;
  }
  /* Based on the services subscribed, show/hide the corresponding tabs */
  setActiveTabs() {
    try {
      const services = this._cookieService.get("services");
      if (services) {
        this.dataService.subscriptionData.services = services.trim().split(" ");
        this.dataService.subscriptionData.services.forEach((element) => {
          if (element === "DG") {
            this.dataService.activatedTabs["Diagnose"] = true;
          } else if (element === "IS") {
            this.dataService.activatedTabs["Insights"] = true;
          }
        });
      } else {
        alert(
          "Contact Org Admin: " + this.dataService.subscriptionData.orgAdmin
        );
      }
    } catch (error) {
      // console.error(error);
    }
  }

  getUserAccessApplication() {
    this.restService.getUserAccessApplication().then((res) => {
      if (res) {
        if (res["status"] === 200) {
          this.applicationList = JSON.parse(res.json()["resource"])[
            "applicationList"
          ];
        }
      }
    });
  }

  checkRoute($event) {
    // console.log($event);
    this.appNameMenuFlag = $event;
  }
  fireEvent(event) {
    // console.log(event);
  }
}
