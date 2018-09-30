/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Routes, RouterModule } from "@angular/router";
import { ShowConfigurationsComponent } from "./show-config.component";

const SHOW_CONFIG_ROUTER: Routes = [
    {
        path: "",
        component: ShowConfigurationsComponent
    }
];

export const showConfigRouter = RouterModule.forChild(SHOW_CONFIG_ROUTER );
