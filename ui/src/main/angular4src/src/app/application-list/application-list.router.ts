/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Routes, RouterModule } from "@angular/router";
import {ApplicationListComponent} from "./application-list.component";

const APP_LIST_ROUTER: Routes = [
  {
    path: "",
    component: ApplicationListComponent
  }
];

export const applicationListRouter = RouterModule.forChild(APP_LIST_ROUTER );
