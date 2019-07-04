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
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { ActiveReleaseComponent } from "./active-release/active-release.component";
import { AddReleaseComponent } from "./add-release/add-release.component";
import { ReleaseHistoryComponent } from "./release-history/release-history.component";
import { DateTimePickerModule } from "ng-pick-datetime";
import { TranslateModule } from "@ngx-translate/core";
import { TabsModule } from 'ngx-bootstrap';


@NgModule({
  imports: [
    CommonModule,
    addUpdateReleaseRouter,
    HttpModule,
  FormsModule,
  TranslateModule,
    TabsModule,
  DateTimePickerModule],
  declarations: [AddUpdateReleaseComponent,
            ActiveReleaseComponent,
                    AddReleaseComponent,
            ReleaseHistoryComponent
        ]
})
export class AddUpdateReleaseModule { }
