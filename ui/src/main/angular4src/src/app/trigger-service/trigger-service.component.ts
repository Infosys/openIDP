/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit } from "@angular/core";
import { ShowConfigurationsComponent } from "../show-config/show-config.component";
@Component({
  selector: "app-trigger-service",
  templateUrl: "./trigger-service.component.html",
  styleUrls: []
})
export class TriggerServiceComponent implements OnInit {

  constructor(
    private showconfig: ShowConfigurationsComponent


  ) {
   }

   callAllRestApi() {
        this.showconfig.getPipelineData();

   }





  ngOnInit(



  ) {
  }

}
