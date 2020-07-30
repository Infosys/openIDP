import { NgModule } from "@angular/core";

import { InsightsComponent } from "./insights.component";
import { InsightsRoutingModule } from "./insights-routing.module";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { NgxPaginationModule } from "ngx-pagination";
import { ChartsModule } from "ng2-charts";

@NgModule({
  imports: [
    InsightsRoutingModule,
    FormsModule,
    CommonModule,
    NgxPaginationModule,
    ChartsModule,
  ],
  declarations: [InsightsComponent],
})
export class InsightsModule {}
