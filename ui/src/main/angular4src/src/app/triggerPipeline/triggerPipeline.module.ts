import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TriggerComponent } from "./triggerPipeline.component";
import { TranslateModule,  TranslateLoader } from "@ngx-translate/core";
import { HttpClientModule, HttpClient } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";
import { DateTimePickerModule } from "ng-pick-datetime";
// moved up to app.modules.ts
import { SlaveLabelFilterModule } from "../custom-filter-pipes/slaveFilterPipe.module";

import { TreeviewModule } from "ngx-treeview";
import { TabsModule, CollapseModule } from "ngx-bootstrap";
import { TriggerPipelineRoutingModule } from "./triggerPipeline.routing.module";


@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    TriggerPipelineRoutingModule,
    AngularMultiSelectModule,
    DateTimePickerModule,
    TreeviewModule.forRoot(),
    SlaveLabelFilterModule,
    TranslateModule,
    TabsModule,
    CollapseModule
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
