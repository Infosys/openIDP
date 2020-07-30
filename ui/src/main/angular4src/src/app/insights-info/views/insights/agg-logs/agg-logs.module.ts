import { NgModule } from "@angular/core";
import { AggLogsComponent } from "./agg-logs.component";
import { AggLogsRoutingModule } from "./agg-logs-routing.module";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { NgxPaginationModule } from "ngx-pagination";

@NgModule({
  imports: [
    AggLogsRoutingModule,
    FormsModule,
    CommonModule,
    NgxPaginationModule,
  ],
  declarations: [AggLogsComponent],
})
export class AggLogsModule {}
