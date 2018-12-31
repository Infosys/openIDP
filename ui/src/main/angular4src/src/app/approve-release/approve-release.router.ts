import { Routes, RouterModule } from "@angular/router";
import { ApproveReleaseComponent } from "./approve-release.component";

const APPROVE_RELEASE_ROUTER: Routes = [
    { 
        path: '',
        component: ApproveReleaseComponent
    }
];

export const approveReleaseRouter = RouterModule.forChild(APPROVE_RELEASE_ROUTER );