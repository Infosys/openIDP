/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import { IdprestapiService } from "../../idprestapi.service";
import { IdpdataService } from "../../idpdata.service";

@Component({
  selector: "app-release-history",
  templateUrl: "./release-history.component.html",
  styleUrls: ["./release-history.component.css"],
})
export class ReleaseHistoryComponent implements OnInit {
  str = "";
  pipelineName = "";
  release = [];
  closedRel = [];
  /* Constructor */
  constructor(
    public idpdata: IdpdataService,
    private idprestapi: IdprestapiService
  ) {
    this.idpdata.releasePipelineName = "";
    this.showReleases();
  }

  ngOnInit() {
    this.showReleases();
  }
  /* Show Release in Release history */
  showReleases() {
    this.release = [];
    if (this.idpdata.releasePipelineName !== "") {
      this.idprestapi
        .getHistoryReleases(this.idpdata.releasePipelineName)
        .then((response) => {
          const resp = response.json();
          // //console.log(resp);
          const errorMsg = resp.errorMessage;
          this.closedRel = JSON.parse(resp.resource).releasePipeline[0].release;
          // console.log(this.closedRel);
          this.closedRel.forEach((value, index) => {
            if (
              value.actualStartDate !== undefined &&
              value.actualStartDate !== ""
            ) {
              this.release.push(value);
            }
          });
        });
    }
  }

  update() {}
}
