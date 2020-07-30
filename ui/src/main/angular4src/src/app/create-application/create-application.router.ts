/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Routes, RouterModule } from "@angular/router";
import { CreateApplicationComponent } from "./create-application.component";

const CREATE_APP_ROUTER: Routes = [
  {
    path: "",
    component: CreateApplicationComponent,
    data: {
      title: "Manage Applications",
    },
  },
];

export const createAppRouter = RouterModule.forChild(CREATE_APP_ROUTER);
