import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { AggLogsComponent } from "./agg-logs.component";

const routes: Routes = [
  {
    path: "",
    component: AggLogsComponent,
    data: {
      title: "Home / Insights / Aggregated Logs",
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AggLogsRoutingModule {}
