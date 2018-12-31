/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/

import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { WorkflowInfoComponent } from "./workflow-info.component";
import { workflowRouter } from "./workflow-info.router";
import { TranslateModule, TranslateStaticLoader, TranslateLoader } from "ng2-translate";
import { HttpModule, Http } from "@angular/http";
import { FormsModule , ReactiveFormsModule} from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown/angular2-multiselect-dropdown";
import { DateTimePickerModule } from "ng-pick-datetime";
import { SlaveLabelFilterModule } from "../custom-filter-pipes/slaveFilterPipe.module";
import { TreeviewModule } from "ngx-treeview";
import { TriggerModule } from "../triggerPipeline/triggerPipeline.module";
import { DynamicComponentDirective } from "../custom-directive/dynamicComponent.directive";
import { SortablejsModule } from "angular-sortablejs";


export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, "assets/i18n", ".json");
}

@NgModule({
  imports: [
    CommonModule,
    workflowRouter,
    TranslateModule.forRoot({
      provide: TranslateLoader,
      useFactory: (createTranslateLoader),
      deps: [Http]
    }),
    HttpModule,
    FormsModule,
    ReactiveFormsModule,
    AngularMultiSelectModule,
    DateTimePickerModule,
    SortablejsModule,
    TreeviewModule.forRoot(),
    SlaveLabelFilterModule,
    TriggerModule,
    workflowRouter
  ],
  declarations: [
    WorkflowInfoComponent,
    DynamicComponentDirective,
  ],
  bootstrap: [WorkflowInfoComponent,
  ]
})
export class WorkflowModule { }
