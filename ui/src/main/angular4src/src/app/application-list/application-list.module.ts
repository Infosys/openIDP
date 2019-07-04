/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { applicationListRouter } from "./application-list.router";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { TranslateModule } from "@ngx-translate/core";
import {ApplicationListComponent} from "./application-list.component";
import {NgxPaginationModule} from 'ngx-pagination';
import {AppListFilterPipe} from "./app-list-filter.pipe";


@NgModule({
  imports: [
    CommonModule,
    applicationListRouter,
    HttpModule,
    FormsModule,
    TranslateModule,
    NgxPaginationModule
  ],
  declarations: [ApplicationListComponent,AppListFilterPipe]
})
export class ApplicationListModule { }
