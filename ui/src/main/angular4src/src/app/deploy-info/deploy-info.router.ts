/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Routes, RouterModule } from "@angular/router";
import { DeployInfoComponent } from "./deploy-info.component";

const DEPLOY_INFO_ROUTER: Routes = [
    {
        path: "",
        component: DeployInfoComponent
    }
];

export const deployInfoRouter = RouterModule.forChild(DEPLOY_INFO_ROUTER );
