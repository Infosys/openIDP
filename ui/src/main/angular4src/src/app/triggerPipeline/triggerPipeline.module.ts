import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TriggerComponent } from "./triggerPipeline.component";
import { TranslateModule, TranslateStaticLoader, TranslateLoader } from "ng2-translate";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown/angular2-multiselect-dropdown";
import { DateTimePickerModule } from "ng-pick-datetime";
// moved up to app.modules.ts
import { SlaveLabelFilterModule } from "../custom-filter-pipes/slaveFilterPipe.module";

import { TreeviewModule } from "ngx-treeview";



export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, "assets/i18n", ".json");
}

@NgModule({
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
    SlaveLabelFilterModule
  ],
  declarations: [
    TriggerComponent,
    // SlaveLabelfilterPipe
    ],
    exports: [
    TriggerComponent
  ]
})
export class TriggerModule { }
