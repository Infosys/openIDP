import { Routes, RouterModule } from "@angular/router";
import { BuildIntervalCntrlComponent } from "./build-interval-cntrl.component";

const BUILD_INTERVAL_ROUTER: Routes = [
    {
        path: "",
        component: BuildIntervalCntrlComponent
    }
];

export const buildScheduleRouter = RouterModule.forChild(BUILD_INTERVAL_ROUTER);
