/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Routes, RouterModule } from "@angular/router";
import { BuildInfoComponent } from "./build-info.component";

const BUILD_INFO_ROUTER: Routes = [
    {
        path: "",
        component: BuildInfoComponent
    }
];

export const buildInfoRouter = RouterModule.forChild(BUILD_INFO_ROUTER );
