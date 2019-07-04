/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TestInfoComponent } from "./test-info.component";
import { testInfoRouter } from "./test-info.router";
import { TranslateModule,  TranslateLoader } from "@ngx-translate/core";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import {IdpEncryptionModule } from "../idpEncryption.module";


@NgModule({
  imports: [
    CommonModule,
    testInfoRouter,
    HttpModule,
    FormsModule,
    IdpEncryptionModule,
    TranslateModule
  ],
  declarations: [TestInfoComponent]
})
export class TestInfoModule { }
