/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { DeployInfoComponent } from "./deploy-info.component";
import { deployInfoRouter } from "./deploy-info.router";
import { TranslateModule, TranslateStaticLoader, TranslateLoader } from "ng2-translate";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown/angular2-multiselect-dropdown";
import { DateTimePickerModule } from "ng-pick-datetime";
import {IdpEncryptionModule } from "../idpEncryption.module";
import {NgJsonEditorModule} from 'ang-jsoneditor';


export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, "assets/i18n", ".json");
}


@NgModule({
  imports: [
    CommonModule,
    deployInfoRouter,
    NgJsonEditorModule,
    TranslateModule.forRoot({
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [Http]
    }),
    HttpModule,
    FormsModule,
    AngularMultiSelectModule,
    DateTimePickerModule,
    IdpEncryptionModule
  ],
  declarations: [DeployInfoComponent]
})

export class DeployInfoModule { }
