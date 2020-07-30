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
import { WorkflowInfoRouter } from "./workflow-info.router";
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { HttpModule, Http } from "@angular/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";
import { DateTimePickerModule } from "ng-pick-datetime";
import { SlaveLabelFilterModule } from "../custom-filter-pipes/slaveFilterPipe.module";
import { TreeviewModule } from "ngx-treeview";
import { TriggerModule } from "../triggerPipeline/triggerPipeline.module";
import { DynamicComponentDirective } from "../custom-directive/dynamicComponent.directive";
import { SortablejsModule } from "angular-sortablejs";
import { CollapseModule } from "ngx-bootstrap/collapse";
import { ModalModule } from "ngx-bootstrap/modal";

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    FormsModule,
    CollapseModule,
    ModalModule,
    ReactiveFormsModule,
    AngularMultiSelectModule,
    DateTimePickerModule,
    SortablejsModule,
    TreeviewModule.forRoot(),
    WorkflowInfoRouter,
    SlaveLabelFilterModule,
    TriggerModule,
    TranslateModule,
  ],
  declarations: [WorkflowInfoComponent, DynamicComponentDirective],
  bootstrap: [WorkflowInfoComponent],
})
export class WorkflowModule {}
