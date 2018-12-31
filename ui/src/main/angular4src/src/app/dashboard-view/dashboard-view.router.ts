/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Routes, RouterModule } from '@angular/router';
import { DashboardViewComponent } from './dashboard-view.component';

const DASHBOARD_VIEW_ROUTER: Routes = [
    { 
        path: '',
        component: DashboardViewComponent
    }
];

export const dashboardViewRouter = RouterModule.forChild(DASHBOARD_VIEW_ROUTER );