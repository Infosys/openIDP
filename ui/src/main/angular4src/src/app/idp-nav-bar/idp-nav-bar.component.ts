/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import { Renderer2 } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { NgClass } from "@angular/common";
import { IdpdataService } from "../idpdata.service";
import { IdprestapiService } from "../idprestapi.service";

@Component({
  selector: "app-idp-nav-bar",
  templateUrl: "./idp-nav-bar.component.html",
  styleUrls: ["./idp-nav-bar.component.scss"],
})
export class IdpNavBarComponent implements OnInit {
  urlChange: any;
  basicInfo: any;
  codeInfo: any;
  buildInfo: any;
  deployInfo: any;
  testInfo: any;
  workflowInfo: any;
  createApp: any;

  constructor(
    private renderer: Renderer2,
    private route: ActivatedRoute,
    private router: Router,
    private IdprestapiService: IdprestapiService,
    public IdpdataService: IdpdataService
  ) {
    this.basicInfo = true;
    this.router.events.subscribe((val: any) => {
      this.buildInfo = false;
      this.codeInfo = false;
      this.deployInfo = false;
      this.testInfo = false;
      this.createApp = false;
      // this.basicInfo = false;
      // OSS REMOVAL
      // this.workflowInfo = false;

      const url: string = this.router.url;
      // //console.log(url);
      if (url.includes("createConfig")) {
        if (url.includes("createapp")) {
          this.createApp = true;
          this.basicInfo = false;
        } else if (url.includes("basicInfo")) {
          this.createApp = false;
          this.basicInfo = true;
        }
        this.basicInfo = url.includes("basicInfo") ? true : false;
        this.buildInfo = url.includes("buildInfo") ? true : false;
        this.codeInfo = url.includes("codeInfo") ? true : false;
        this.deployInfo = url.includes("deployInfo") ? true : false;
        this.testInfo = url.includes("testInfo") ? true : false;
        this.workflowInfo = url.includes("workflowInfo") ? true : false;
      }
    });
  }

  getCursor() {
    if (this.IdpdataService.freezeNavBars) {
      return "not-allowed";
    }
  }

  ngOnInit() {
    if (this.IdpdataService.devServerURL === "") {
      this.getProperties();
    } else {
      this.getSubscriptionPermission();
    }
  }
  /* Get Subscription Properties */
  getProperties() {
    this.IdprestapiService.getData().then((response) => {
      try {
        if (response) {
          this.IdpdataService.devServerURL = response.json().idpresturl;
          this.IdpdataService.subscriptionServerURL = response.json().idpsubscriptionurl;
          this.getSubscriptionPermission();
        }
      } catch (e) {
        //console.log(e);
        alert("Failed in getting the properties ");
      }
    });
  }
  /* Get Subscription Details */
  getSubscriptionPermission() {
    this.IdprestapiService.getSubscriptionPermission().then((response) => {
      try {
        const status = JSON.parse(response.json().status);
        //console.log(status);
        if (status.names.indexOf("CREATE_TEST_SUBSCRIBED") !== -1) {
          this.IdpdataService.testSubscription = true;
        }
        if (status.names.indexOf("CREATE_DEPLOY_SUBSCRIBED") !== -1) {
          this.IdpdataService.deploySubscription = true;
          if (!this.IdpdataService.testSubscription === true) {
            this.IdpdataService.deploySubscriptionSubmit = true;
          }
        }
        if (status.names.indexOf("CREATE_BUILD_SUBSCRIBED") !== -1) {
          this.IdpdataService.buildSubscription = true;
          if (
            !(
              this.IdpdataService.deploySubscription === true ||
              this.IdpdataService.testSubscription === true
            )
          ) {
            this.IdpdataService.buildSubscriptionSubmit = true;
          }
        }
      } catch (e) {
        alert("Failed to get Subscription Details");
        //console.log(e);
      }
    });
  }
}
