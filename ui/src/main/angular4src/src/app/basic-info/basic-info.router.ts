/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Routes, RouterModule } from "@angular/router";
import { BasicInfoComponent } from "./basic-info.component";

const BASIC_INFO_ROUTER: Routes = [
    {
        path: "",
        component: BasicInfoComponent,
        data: {
            'title': 'Create Pipeline'
        }
    }
];

export const basicInfoRouter = RouterModule.forChild(BASIC_INFO_ROUTER );
