import { Routes, RouterModule } from "@angular/router";
import { BuildIntervalCntrlComponent } from "./build-interval-cntrl.component";
import {NgModule} from "../../../node_modules/@angular/core";

const BUILD_INTERVAL_ROUTER: Routes = [
    {
        path: "",
        component: BuildIntervalCntrlComponent
    }
];

@NgModule({
  imports: [RouterModule.forChild(BUILD_INTERVAL_ROUTER)],
  exports: [RouterModule]
})
export class BuildScheduleRouter {}
