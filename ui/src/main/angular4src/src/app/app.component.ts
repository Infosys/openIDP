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
import {TranslateService} from '@ngx-translate/core';


@Component({
  selector: "body",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent implements OnInit {
  title = "test";
  profile: any;
  constructor(private idprestapiService: IdprestapiService, private idpdataService: IdpdataService,
              translate:TranslateService) {
    // this language will be used as a fallback when a translation isn't found in the current language
    translate.setDefaultLang('en');

    // the lang to use, if the lang isn't available, it will use the current loader to get them
    translate.use('en');
  }
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
