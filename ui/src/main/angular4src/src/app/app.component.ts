/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit } from "@angular/core";
import { IdprestapiService } from "./idprestapi.service";
import { IdpdataService } from "./idpdata.service";


@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent implements OnInit {
  title = "test";
  profile: any;
  constructor(private idprestapiService: IdprestapiService, private idpdataService: IdpdataService) { }
  getProfile() {
    this.idprestapiService.getData()
        .then(response => {
        try {
            if (response) {
            this.idpdataService.profile = response.json().profile;
            this.setProfile();
            }
        } catch (e) {
            alert("Failed to get the Properties Details");
        }
        });
  }
  setProfile() {
    this.profile = this.idpdataService.profile;
    if (this.profile === undefined || this.profile === "idp") {
        require("style-loader!./../styles.css");
    }
  }
  ngOnInit() {
    this.getProfile();
  }
}
