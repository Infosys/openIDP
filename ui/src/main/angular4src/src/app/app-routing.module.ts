/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Routes, RouterModule } from "@angular/router";
import { LoginComponent } from "./login/login.component";
import { CreateConfigComponent } from "./create-config/create-config.component";
import { PreviousConfigComponent } from "./previous-config/previous-config.component";
import { AuthGuardService } from "./auth-guard.service";
import { SuccessComponent } from "./success/success.component";
import { TriggerComponent } from "./triggerPipeline/triggerPipeline.component";
import { MailSuccessComponent } from "./mail-success/mail-success.component";
import { ModuleWithProviders } from "@angular/core";
import {ReleaseConfigsComponent} from "./release-configs/release-configs.component";
import { ServicePortalComponent } from "./service-portal/service-portal.component";
import { KeycloakComponent } from "./keycloak/keycloak.component";
// import { ManageEnvironmentComponent } from "./manage-environment/manage-environment.component";
import { CreateLicenseComponent } from "./create-license/create-license.component";
import { CreateOrganizationComponent } from "./create-organization/create-organization.component";
import { NotificationInfoComponent } from "./notification-info/notification-info.component";
import { ApproveReleaseComponent } from "./approve-release/approve-release.component";

export const appRoutes: Routes = [
  // { path : "manageEnvironment", component: ManageEnvironmentComponent},
  { path : "notificationPage", component: NotificationInfoComponent},
  { path : "", redirectTo: "/login", pathMatch: "full" },
  { path : "login", component: LoginComponent, canActivate: [AuthGuardService] },
  { path : "keycloak", component: KeycloakComponent },
  { path : "createConfig", component : CreateConfigComponent, canActivate : [AuthGuardService],
    children : [
        { path : "", redirectTo : "basicInfo", pathMatch : "full" },
        { path : "createapp", loadChildren : "app/create-application/create-application.module#CreateApplicationModule" },
        { path : "basicInfo", loadChildren : "app/basic-info/basic-info.module#BasicInfoModule" },
        { path : "codeInfo", loadChildren : "app/code-info/code-info.module#CodeInfoModule" },
        { path : "buildInfo", loadChildren : "app/build-info/build-info.module#BuildInfoModule" },
        { path : "testInfo", loadChildren : "app/test-info/test-info.module#TestInfoModule" },
        { path : "deployInfo", loadChildren : "app/deploy-info/deploy-info.module#DeployInfoModule" },
        { path: "workflowInfo", loadChildren: "app/workflow-info/workflow-info.module#WorkflowModule" },
        { path : "**", redirectTo : "basicInfo", pathMatch : "full" },
    ]
  },
  { path : "previousConfig", component : PreviousConfigComponent, canActivate : [AuthGuardService],
    children : [
        { path : "", redirectTo : "showConfigurations", pathMatch : "full" },
        { path : "showConfigurations", loadChildren : "app/show-config/show-config.module#ShowConfigModule" },
        { path : "stageviewTrigger", loadChildren : "app/stageview-trigger/stageview-trigger.module#StageviewTriggerModule" },
        { path : "stageviewHistory", loadChildren : "app/stageview-history/stageview-history.module#StageviewHistoryModule" },
        { path : "trigger", component : TriggerComponent },
        { path : "workflowInfo", loadChildren: "app/workflow-info/workflow-info.module#WorkflowModule" },
        { path:"approve", loadChildren:"app/approve-release/approve-release.module#ApproveReleaseModule"},
        { path: "aboutView", loadChildren: 'app/about-view/about-view.module#AboutViewModule' },
        { path: 'dashboardView', loadChildren: 'app/dashboard-view/dashboard-view.module#DashboardViewModule' },
        { path : "schedule", loadChildren : "app/build-interval-cntrl/build-interval-cntrl.module#BuildIntervalModule"},
        { path : "approveBuild" , loadChildren : "app/approve-build/approve-build.module#ApproveBuildModule"},
        { path : "createLicense", component : CreateLicenseComponent },
    { path : "servicePortal", component : ServicePortalComponent },
    { path : "createorg", component : CreateOrganizationComponent },
      { path : "**", redirectTo : "showConfigurations", pathMatch : "full" },
    ]
  },
  {path  : "pipelines/configuration/:appPipeName/:pipeLineName", component: MailSuccessComponent, canActivate: [AuthGuardService]},
  {
    path: "releaseConfig", component: ReleaseConfigsComponent, canActivate: [AuthGuardService],
    children : [
        { path: "", redirectTo: "release", pathMatch: "full" },
        { path: "release", loadChildren: "app/add-update-release/add-update-release.module#AddUpdateReleaseModule"},
      { path: "**", redirectTo: "release", pathMatch: "full" }
    ]
  },
  { path: "success", component: SuccessComponent, canActivate: [AuthGuardService] },
  { path: "**", redirectTo: "/previousConfig", pathMatch: "full", canActivate: [AuthGuardService] }
];

export const AppRoutingModule: ModuleWithProviders = RouterModule.forRoot(appRoutes);
