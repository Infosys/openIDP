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


const WORKFLOW_ROUTER: Routes = [
    {
        path: "",
        component: WorkflowInfoComponent
    },
];

export const workflowRouter = RouterModule.forChild(WORKFLOW_ROUTER);
