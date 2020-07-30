import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { InsightsComponent } from "./insights.component";

const routes: Routes = [
  {
    path: "",
    component: InsightsComponent,
    data: {
      title: "Home / Insights / Anomaly Detection",
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class InsightsRoutingModule {}
