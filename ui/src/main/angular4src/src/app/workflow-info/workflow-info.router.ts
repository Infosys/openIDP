/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/

import { Routes, RouterModule } from "@angular/router";
import { WorkflowInfoComponent } from "./workflow-info.component";
import { TriggerComponent } from "../triggerPipeline/triggerPipeline.component";
import {NgModule} from "../../../node_modules/@angular/core";


const WORKFLOW_ROUTER: Routes = [
    {
        path: "",
        component: WorkflowInfoComponent
    },
];
@NgModule({
  imports: [RouterModule.forChild(WORKFLOW_ROUTER)],
  exports: [RouterModule]
})
export class WorkflowInfoRouter {}
