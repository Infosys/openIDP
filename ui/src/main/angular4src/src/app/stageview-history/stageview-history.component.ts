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
import { IdprestapiService } from "../idprestapi.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-stageview-history",
  templateUrl: "./stageview-history.component.html",
  styleUrls: [],
})
export class StageviewHistoryComponent implements OnInit {
  jobUrl: any;
  constructor(
    private IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router
  ) {
    this.getUrl();
  }

  ngOnInit() {}

  getUrl() {
    this.IdprestapiService.getStageViewUrl().then((response) => {
      const resp = response.json();
      const errorMsg = resp.errorMessage;
      /* console.log("required"+JSON.stringify(resp)); */
      //console.log(resp.resource);
      this.jobUrl = resp.resource;
    });
    return true;
  }
}
