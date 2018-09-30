import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BuildIntervalCntrlComponent } from "./build-interval-cntrl.component";
import { buildScheduleRouter } from "./build-interval-cntrl.router";
import { TranslateModule, TranslateStaticLoader, TranslateLoader } from "ng2-translate";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown/angular2-multiselect-dropdown";
import { DateTimePickerModule } from "ng-pick-datetime";

import { TreeviewModule } from "ngx-treeview";
import { TriggerModule } from "../triggerPipeline/triggerPipeline.module";


export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, "assets/i18n", ".json");
}

@NgModule({
    declarations: [
    BuildIntervalCntrlComponent
    ],
  imports: [
    CommonModule,
    TranslateModule.forRoot({
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [Http]
    }),
    HttpModule,
    FormsModule,
    AngularMultiSelectModule,
    DateTimePickerModule,
    TreeviewModule.forRoot(),
    TriggerModule,
    buildScheduleRouter
  ],
  bootstrap: [BuildIntervalCntrlComponent]
})
export class BuildIntervalModule { }
