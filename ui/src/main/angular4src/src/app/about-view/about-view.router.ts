/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Routes, RouterModule } from '@angular/router';
import { AboutViewComponent } from './about-view.component';

const ABOUT_VIEW_ROUTER: Routes = [
    { 
        path: '',
        component: AboutViewComponent
    }
];

export const aboutViewRouter = RouterModule.forChild(ABOUT_VIEW_ROUTER );