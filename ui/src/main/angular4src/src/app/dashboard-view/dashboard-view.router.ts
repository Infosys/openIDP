import { Routes, RouterModule } from "@angular/router";
import { DashboardViewComponent } from "./dashboard-view.component";

const DASHBOARD_VIEW_ROUTER: Routes = [
  {
    path: "",
    component: DashboardViewComponent,
    data: {
      title: "Dashboard",
    },
  },
];

export const dashboardViewRouter = RouterModule.forChild(DASHBOARD_VIEW_ROUTER);
