import { Routes, RouterModule } from "@angular/router";
import { AboutViewComponent } from "./about-view.component";

const ABOUT_VIEW_ROUTER: Routes = [
  {
    path: "",
    component: AboutViewComponent,
  },
];

export const aboutViewRouter = RouterModule.forChild(ABOUT_VIEW_ROUTER);
