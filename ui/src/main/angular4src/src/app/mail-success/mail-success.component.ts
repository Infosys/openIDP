/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { IdpdataService } from "../idpdata.service";
import { IdpService } from "../idp-service.service";
import { IdprestapiService } from "../idprestapi.service";
import { CookieService } from "ngx-cookie";
import { IDPEncryption } from "../idpencryption.service";

@Component({
  selector: "app-mail-success",
  templateUrl: "./mail-success.component.html",
  styleUrls: [],
})
export class MailSuccessComponent implements OnInit {
  application: any;
  pipelineDetails: any;
  pipelineName: any;
  pipelineAdminName: any;
  message = "created";
  showPage = false;

  /* Constructor */
  constructor(
    private IdpdataService: IdpdataService,
    private IdprestapiService: IdprestapiService,
    private route: ActivatedRoute,
    private IdpService: IdpService,
    private _cookieService: CookieService,
    private idpencryption: IDPEncryption
  ) {
    this.route.params.subscribe((params) => {
      // console.log(params["appPipeName"]);
      // console.log(params["pipeLineName"]);
      const appName = params["appPipeName"];

      const pipeName = params["pipeLineName"];
      const appPipeData = {
        applicationName: appName,
        pipelineName: pipeName,
        userName: "",
      };
      //console.log(appName);
      this.init(appName, appPipeData);
    });
  }

  ngOnInit() {}

  init(appName, appPipeData) {
    if (this.IdpdataService.devServerURL !== "") {
      this.getAppDetails(appName, appPipeData);
    } else {
      this.IdprestapiService.getData().then((response) => {
        if (response) {
          this.IdpdataService.devServerURL = response.json().idpresturl;
          this.IdpdataService.IDPDashboardURL = response.json().idpdashboardurl;
          this.IdpdataService.IDPLink = response.json().IDPLink;
          this.IdpdataService.geUrl = response.json().geUrl;
          this.IdpdataService.geFlag = response.json().geFlag;

          if (this._cookieService.get("access_token")) {
            //console.log("details");
            if (this.IdpService.getDetails()) {
              // console.log("gerger");
              this.getAppDetails(appName, appPipeData);
            }
          }
        }
      });
    }
  }

  getPipelineData(data) {
    data.userName = this.IdpdataService.idpUserName;
    this.IdprestapiService.getPipelineDetails(data).then((response) => {
      //console.log(new Date().toUTCString(), "Pipeline details retrieved");
      try {
        const responseData = this.idpencryption.decryptAES(
          response.json().resource
        );
        // console.log(JSON.parse(responseData));
        const resp = JSON.parse(responseData);
        //console.log(resp);
        this.pipelineDetails = resp.pipelineJson;
        for (
          let i = 0;
          i < this.pipelineDetails.buildInfo.modules.length;
          i++
        ) {
          // console.log(this.pipelineDetails.buildInfo.modules[i]);
          let f = false;
          if (
            this.pipelineDetails.buildInfo.modules[i].codeAnalysis !== undefined
          ) {
            for (
              let x = 0;
              x < this.pipelineDetails.buildInfo.modules[i].codeAnalysis.length;
              x++
            ) {
              if (
                this.pipelineDetails.buildInfo.modules[i].codeAnalysis[x] !==
                  null &&
                this.pipelineDetails.buildInfo.modules[i].codeAnalysis[x] !==
                  "" &&
                this.pipelineDetails.buildInfo.modules[i].codeAnalysis[x] !==
                  "off"
              ) {
                f = true;
                break;
              }
            }
          }

          if (f === false) {
            this.pipelineDetails.buildInfo.modules[i].codeAnalysis = [];
          }
        }
        this.pipelineAdminName = this.IdpdataService.idpUserName;
        this.pipelineName = this.pipelineDetails.basicInfo.pipelineName;
        this.showPage = true;
        //console.log(this.showPage);
      } catch (e) {
        alert("Failed to get the PipeLine Details");
        //console.log(e);
      }
    });
  }

  /* Getting Application Details
   * for environment owners, steps added
   * to display in success page */
  getAppDetails(appName, appPipeData) {
    this.IdprestapiService.getApplicationDetails(appName).then((response) => {
      if (response) {
        const resp = response.json().resource;
        let parsed;
        try {
          parsed = JSON.parse(resp);
          // console.log(parsed);
          if (parsed) {
            this.IdpdataService.application = parsed.appJson;

            this.application = this.IdpdataService.application;
            this.getPipelineData(appPipeData);
          }
        } catch (e) {
          alert("Failed to get Application Details");
          //console.log(e);
        }
      }
    });
  }
}
