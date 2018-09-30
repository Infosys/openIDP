import { Routes, RouterModule } from "@angular/router";
import {SlaveLabelfilterPipe} from "./slaveFilterPipe";


const SLAVE_FILTER_ROUTER: Routes = [
    {
        path: "",
        component: SlaveLabelfilterPipe
    }
];

export const slaveFilterRouter = RouterModule.forChild(SLAVE_FILTER_ROUTER );
