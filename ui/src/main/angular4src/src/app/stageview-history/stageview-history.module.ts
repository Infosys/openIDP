/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { StageviewHistoryComponent } from "./stageview-history.component";
import { stageviewHistoryRouter } from "./stageview-history.router";
import { TranslateModule,  TranslateLoader } from "@ngx-translate/core";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { SafePipeModule } from "../safe-pipe.module";


@NgModule({
  imports: [
    CommonModule,
    stageviewHistoryRouter,
    HttpModule,
    FormsModule,
    SafePipeModule,
    TranslateModule
  ],
  declarations: [StageviewHistoryComponent
  ]
})
export class StageviewHistoryModule { }
