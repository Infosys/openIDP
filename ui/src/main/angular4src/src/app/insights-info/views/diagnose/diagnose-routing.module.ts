import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { DiagnoseComponent } from "./diagnose.component";

const routes: Routes = [
  {
    path: "",
    component: DiagnoseComponent,
    data: {
      title: "Home / Diagnose",
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DiagnoseRoutingModule {}
