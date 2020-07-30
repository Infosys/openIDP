/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit, ViewChild } from "@angular/core";
import { IdprestapiService } from "../idprestapi.service";
import { IdpdataService } from "../idpdata.service";
import { Router } from "@angular/router";
import { TabsetComponent } from "ngx-bootstrap";
@Component({
  selector: "app-add-update-release",
  templateUrl: "./add-update-release.component.html",
  styleUrls: ["./add-update-release.component.css"],
})
export class AddUpdateReleaseComponent implements OnInit {
  @ViewChild("releaseTabs") staticTabs: TabsetComponent;

  appNames: any;
  activeRelease = true;
  addRelease = false;
  releaseHistory = false;
  releaseManagerData: any;
  /* Constructor */
  constructor(
    private Idpdata: IdpdataService,
    private Idprestapi: IdprestapiService,
    private router: Router
  ) {
    this.Idpdata.workflowData = [];
    this.Idpdata.workflowDataTemp = [];
    this.getApplicationName();
    this.Idpdata.releaseManagerData = JSON.parse(
      JSON.stringify(this.Idpdata.releaseManagerTemplate)
    );
    this.releaseManagerData = this.Idpdata.releaseManagerData;
  }

  ngOnInit() {}
  /* Gets Applicaion Name */
  getApplicationName() {
    this.Idprestapi.getApplicationNameForReleaseManager(
      this.Idpdata.idpUserName
    ).then((response) => {
      if (response) {
        const appDetails = JSON.parse(response.json().resource);
        this.appNames = appDetails.names;
      }
    });
  }

  getPipelineNames() {
    if (this.releaseManagerData.applicationName !== "") {
      this.Idpdata.releaseUpdateSuccess = false;
      this.Idpdata.activeReleasePipelineName = "";
      this.Idpdata.releasePipelineName = "";
      this.Idpdata.noPipelines = false;
      this.Idpdata.releaseAddSuccess = false;
      this.Idprestapi.getPipelineNames(
        this.releaseManagerData.applicationName
      ).then((response) => {
        const resp = response.json();
        this.Idpdata.pipelineNames = [];
        if (resp.resource !== "No Pipelines") {
          const pipData = JSON.parse(resp.resource);
          for (const i of pipData.names) {
            this.Idpdata.pipelineNames.push(i);
          }
        } else {
          this.Idpdata.noPipelines = true;
        }
      });
      return true;
    }
  }

  addSuccessFalse() {
    this.Idpdata.releaseAddSuccess = false;
    this.pipelinesEmpty();
  }
  updateSuccessFalse() {
    this.pipelinesEmpty();
    this.Idpdata.releaseUpdateSuccess = false;
  }
  pipelinesEmpty() {
    this.Idpdata.activeReleasePipelineName = "";
    this.Idpdata.releasePipelineName = "";
  }
  getRouteConfig() {
    this.router.events.subscribe((val: any) => {
      const url = val.url;
      if (url.includes("activeRelease")) {
        this.activeRelease = true;
        this.addRelease = false;
        this.releaseHistory = false;
      } else if (url.includes("addRelease")) {
        this.addRelease = true;
        this.releaseHistory = false;
        this.activeRelease = false;
      } else if (url.includes("releaseHistory")) {
        this.releaseHistory = true;
        this.addRelease = false;
        this.activeRelease = false;
      }
    });
  }
}
