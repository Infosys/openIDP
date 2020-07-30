import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

// Import Containers
import { DefaultLayoutComponent } from "./containers";

import { P404Component } from "./views/error/404.component";
import { P500Component } from "./views/error/500.component";

export const routes: Routes = [
  {
    path: "",
    redirectTo: "home/insights",
  },
  {
    path: "404",
    component: P404Component,
    data: {
      title: "Page 404",
    },
  },
  {
    path: "500",
    component: P500Component,
    data: {
      title: "Page 500",
    },
  },

  {
    path: "home",
    component: DefaultLayoutComponent,
    data: {
      title: "",
    },
    children: [
      {
        path: "",
        redirectTo: "measure",
      },

      {
        path: "insights",
        loadChildren: "./views/insights/insights.module#InsightsModule",
      },

      {
        path: "diagnose",
        loadChildren: "./views/diagnose/diagnose.module#DiagnoseModule",
      },
      {
        path: "agg-logs",
        loadChildren: "./views/insights/agg-logs/agg-logs.module#AggLogsModule",
      },
    ],
  },
];

export const InsightsRouter = RouterModule.forChild(routes);
