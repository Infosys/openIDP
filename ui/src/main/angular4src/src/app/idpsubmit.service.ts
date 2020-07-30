/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Injectable } from "@angular/core";
import { IdprestapiService } from "./idprestapi.service";
import { IdpdataService } from "./idpdata.service";
import { Router } from "@angular/router";
import { IDPEncryption } from "./idpencryption.service";

@Injectable()
export class IdpSubmitService {
  constructor(
    private IdprestapiService: IdprestapiService,
    private router: Router,
    private IdpdataService: IdpdataService,
    private idpencryption: IDPEncryption
  ) {}

  buildInfo: any = this.IdpdataService.data.buildInfo;
  testInfo: any = this.IdpdataService.data.testInfo;
  tempObjecttest: any = this.IdpdataService.data.checkboxStatus.testInfo;
  formStatusObject = this.IdpdataService.data.formStatus;
  env: any = [];

  loader: any = "off";
  message: any;
  errorMessage: any;
  msg: any;
  loc: any;
  submitData() {
    this.loader = "on";
    this.IdpdataService.freezeNavBars = true;
    this.IdpdataService.data.testInfo = this.testInfo;
    this.IdpdataService.data.masterJson[
      "basicInfo"
    ] = this.IdpdataService.data.basicInfo;
    this.IdpdataService.data.masterJson[
      "buildInfo"
    ] = this.IdpdataService.data.buildInfo;
    this.IdpdataService.data.masterJson["code"] = this.IdpdataService.data.code;
    this.IdpdataService.data.masterJson[
      "deployInfo"
    ] = this.IdpdataService.data.deployInfo;
    this.IdpdataService.data.masterJson[
      "testInfo"
    ] = this.IdpdataService.data.testInfo;
    this.IdpdataService.data.checkboxStatus.testInfo = this.tempObjecttest;
    let data = this.idpencryption.doubleDecryptPassword(
      this.IdpdataService.data.masterJson
    );
    data = this.idpencryption.encryptAES(JSON.stringify(data));
    this.IdprestapiService.submit(data).then((response) => {
      try {
        const resp = response.json();
        const errorMsg = resp.errorMessage;
        this.loader = "off";
        if (errorMsg === null && resp.resource.toLowerCase() === "success") {
          this.message = "success";
          if (this.formStatusObject.operation !== "edit") {
            const actiondata = {
              applicationName: this.IdpdataService.data.masterJson.basicInfo
                .applicationName,
              method: "create",
              pipelineName: this.IdpdataService.data.masterJson.basicInfo
                .pipelineName,
              userName: this.IdpdataService.idpUserName,
            };
            this.IdprestapiService.sendPipeMail(actiondata);
          } else {
            const actiondata = {
              applicationName: this.IdpdataService.data.masterJson.basicInfo
                .applicationName,
              method: "edit",
              pipelineName: this.IdpdataService.data.masterJson.basicInfo
                .pipelineName,
              userName: this.IdpdataService.idpUserName,
            };
            this.IdprestapiService.sendPipeMail(actiondata);
          }
          this.getAppDetails();
        } else {
          this.IdpdataService.freezeNavBars = false;
          this.message = "error";
          this.errorMessage = errorMsg;
        }
      } catch (e) {
        alert("Failed while submiting the trigger job");
        //console.log(e);
      }
    });
  }
  getAppDetails() {
    this.IdprestapiService.getApplicationDetails(
      this.IdpdataService.data.masterJson.basicInfo.applicationName
    ).then((response) => {
      if (response) {
        const resp = response.json().resource;
        let parsed;
        try {
          parsed = JSON.parse(resp);
          if (parsed) {
            this.IdpdataService.application = parsed.appJson;
            this.redirectTo();
          }
        } catch (e) {
          //console.log(e);
          alert("Failed while getting  the pipeline details");
          this.redirectTo();
        }
      }
    });
  }

  redirectTo() {
    setTimeout(() => {
      this.router.navigate(["/success"]);
    }, 3000);
  }
}
