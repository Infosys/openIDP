/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Routes, RouterModule } from "@angular/router";
import { ApproveBuildComponent } from "./approve-build.component";

const APPROVE_BUILD_ROUTER: Routes = [
    {
        path: "",
        component: ApproveBuildComponent
    }
];

export const approvebuildRouter = RouterModule.forChild(APPROVE_BUILD_ROUTER);
