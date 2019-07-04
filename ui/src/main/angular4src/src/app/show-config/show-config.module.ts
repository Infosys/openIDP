/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from "@angular/core";
import {CommonModule, DatePipe} from "@angular/common";
import { ShowConfigurationsComponent } from "./show-config.component";
import { showConfigRouter } from "./show-config.router";
import { TranslateModule,  TranslateLoader } from "@ngx-translate/core";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { Ng2TableModule } from "ng2-table/ng2-table";
import { PaginationModule } from "ngx-bootstrap/pagination";


@NgModule({
  imports: [
    CommonModule,
    showConfigRouter,
    HttpModule,
    FormsModule,
    TranslateModule,
    Ng2TableModule,
    PaginationModule.forRoot()
  ],
  declarations: [ShowConfigurationsComponent],
  providers:[DatePipe]
})
export class ShowConfigModule { }
