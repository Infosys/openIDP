/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { AddUpdateReleaseComponent } from "./add-update-release.component";
import { addUpdateReleaseRouter } from "./add-update-release.router";
import { TranslateModule, TranslateStaticLoader, TranslateLoader } from "ng2-translate";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { ActiveReleaseComponent } from "./active-release/active-release.component";
import { AddReleaseComponent } from "./add-release/add-release.component";
import { ReleaseHistoryComponent } from "./release-history/release-history.component";
import { DateTimePickerModule } from "ng-pick-datetime";

export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, "assets/i18n", ".json");
}

@NgModule({
  imports: [
    CommonModule,
    addUpdateReleaseRouter,
    TranslateModule.forRoot({
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [Http]
    }),
    HttpModule,
  FormsModule,
  DateTimePickerModule],
  declarations: [AddUpdateReleaseComponent,
            ActiveReleaseComponent,
                    AddReleaseComponent,
            ReleaseHistoryComponent
        ]
})
export class AddUpdateReleaseModule { }
