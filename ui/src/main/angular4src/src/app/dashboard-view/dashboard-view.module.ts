/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardViewComponent } from './dashboard-view.component';
import { dashboardViewRouter } from './dashboard-view.router';
import { TranslateModule, TranslateStaticLoader, TranslateLoader } from "ng2-translate";
import { HttpModule, Http } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { SafePipeModule } from '../safe-pipe.module';

export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, 'assets/i18n', '.json');
}

@NgModule({
  imports: [
    CommonModule,
    dashboardViewRouter,
	TranslateModule.forRoot({
      provide: TranslateLoader,
      useFactory: (createTranslateLoader),
      deps: [Http]
    }),
	HttpModule,
	FormsModule,
	SafePipeModule
  ],
  declarations: [DashboardViewComponent
				 
				 ]
})
export class DashboardViewModule { }
