/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BasicInfoComponent } from "./basic-info.component";
import { basicInfoRouter } from "./basic-info.router";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { TranslateModule } from "@ngx-translate/core";


@NgModule({
  imports: [
    CommonModule,
    basicInfoRouter,
    HttpModule,
    FormsModule,
    TranslateModule
  ],
  declarations: [BasicInfoComponent]
})
export class BasicInfoModule { }
