/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Routes, RouterModule } from "@angular/router";
import { StageviewHistoryComponent } from "./stageview-history.component";

const STAGEVIEW_HISTORY_ROUTER: Routes = [
    {
        path: "",
        component: StageviewHistoryComponent
    }
];

export const stageviewHistoryRouter = RouterModule.forChild(STAGEVIEW_HISTORY_ROUTER);
