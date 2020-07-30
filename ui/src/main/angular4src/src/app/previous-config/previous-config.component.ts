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

@Component({
  selector: "app-previous-config",
  templateUrl: "./previous-config.component.html",
  styleUrls: [],
})
export class PreviousConfigComponent implements OnInit {
  constructor(
    private IdpService: IdpService,
    public IdpdataService: IdpdataService
  ) {
    // this.IdpService.initMain();
    // console.log(this.IdpdataService.IDPDashboardURL);
  }

  ngOnInit() {}
}
