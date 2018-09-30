/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit} from "@angular/core";
import { TranslateService } from "ng2-translate";
import { IdpService } from "../idp-service.service";
import {IdprestapiService} from "../idprestapi.service";
import { Router } from "@angular/router";
import { IdpdataService} from "../idpdata.service";
import {  ChangeDetectorRef  } from "@angular/core";


@Component({
  selector: "app-create-config",
  templateUrl: "./create-config.component.html",
  styleUrls: ["./create-config.component.css"]
})
export class CreateConfigComponent implements OnInit {


  constructor(
    private cdr: ChangeDetectorRef,
    private translate: TranslateService,
    private IdpService: IdpService,
    public IdpdataService: IdpdataService,
    private router: Router
  ) {
    // console.log(this.IdpdataService.language);
    this.IdpService.initMain();
    // console.log(this.IdpdataService.createAppflag);
  }

  ngOnInit() {
  }

  ngAfterContentChecked () {
    this.cdr.detectChanges();
    this.updateConfigShow();
    // console.log('fewfwf ' + this.IdpdataService.hideApp);
  }

  updateConfigShow() {
    return this.IdpdataService.hideApp;
  }

}
