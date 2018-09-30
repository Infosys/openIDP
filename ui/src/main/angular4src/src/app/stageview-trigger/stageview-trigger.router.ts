/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Routes, RouterModule } from "@angular/router";
import { StageviewTriggerComponent } from "./stageview-trigger.component";

const STAGEVIEW_TRIGGER_ROUTER: Routes = [
    {
        path: "",
        component: StageviewTriggerComponent
    }
];

export const stageviewTriggerRouter = RouterModule.forChild(STAGEVIEW_TRIGGER_ROUTER );
