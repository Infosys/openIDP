import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { TriggerComponent } from "./triggerPipeline.component";

const routes: Routes = [
  {
    path: "",
    component: TriggerComponent,
    data: {
      title: "Trigger",
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TriggerPipelineRoutingModule {}
