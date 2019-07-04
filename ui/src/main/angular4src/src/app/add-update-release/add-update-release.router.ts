/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Routes, RouterModule } from "@angular/router";
import { AddUpdateReleaseComponent } from "./add-update-release.component";

const ADD_UPDATE_ROUTER: Routes = [
    { path: "",
        component: AddUpdateReleaseComponent,
        data: {
            'title': 'Manage Releases'
        }
    }
];

export const addUpdateReleaseRouter = RouterModule.forChild(ADD_UPDATE_ROUTER );
