import { NgModule } from "@angular/core";

import { DiagnoseComponent } from "./diagnose.component";
import { DiagnoseRoutingModule } from "./diagnose-routing.module";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
// Tabs Component
import { TabsModule } from "ngx-bootstrap/tabs";
// Chartjs Component
import { ChartsModule } from "ng2-charts";
// Modal
import { ModalModule } from "ngx-bootstrap/modal";
import { TrendsComponent } from "./trends/trends.component";
import { NgxSpinnerModule } from "ngx-spinner";
import { NgxPaginationModule } from "ngx-pagination";

@NgModule({
  imports: [
    CommonModule,
    DiagnoseRoutingModule,
    FormsModule,
    TabsModule,
    ChartsModule,
    NgxPaginationModule,
    NgxSpinnerModule,
    ModalModule.forRoot(),
  ],
  declarations: [DiagnoseComponent, TrendsComponent],
})
export class DiagnoseModule {}
