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
import { ModuleWithProviders, NgModule } from "@angular/core";
import { ReleaseConfigsComponent } from "./release-configs/release-configs.component";
import { ServicePortalComponent } from "./service-portal/service-portal.component";
import { KeycloakComponent } from "./keycloak/keycloak.component";
import { ManageEnvironmentComponent } from "./manage-environment/manage-environment.component";
import { CreateLicenseComponent } from "./create-license/create-license.component";
import { CreateOrganizationComponent } from "./create-organization/create-organization.component";
import { NotificationInfoComponent } from "./notification-info/notification-info.component";
import { DefaultLayoutComponent } from "./default-layout";
import { ApproveReleaseComponent } from "./approve-release/approve-release.component";
import { SsoComponent } from './sso/sso.component';

export const appRoutes: Routes = [
  { path: "", redirectTo: "/login", pathMatch: "full" },
  { path: "login", component: LoginComponent, canActivate: [AuthGuardService] },
  { path : "keycloak", component: KeycloakComponent },
  { path: 'sso' , component: SsoComponent},
  {
    path: '',
    component: DefaultLayoutComponent,
    data: {
      title: 'Home'
    },
    children: [
      { path: 'dashboardView', loadChildren: 'app/dashboard-view/dashboard-view.module#DashboardViewModule' },
      {
        path: 'dashboard',
        canActivate: [AuthGuardService],
        loadChildren: "./show-config/show-config.module#ShowConfigModule", data: { 'title': 'View Pipelines' }
      },
      { path: "manageEnvironment", component: ManageEnvironmentComponent, data: { 'title': 'Manage Environments' } },
      { path: "notificationPage", component: NotificationInfoComponent, data: { 'title': 'Notifications' } },
      { path: "aboutView", loadChildren: 'app/about-view/about-view.module#AboutViewModule', data: { 'title': 'About IDP' } },
      {
        path: "previousConfig",
        component: PreviousConfigComponent,
        canActivate: [AuthGuardService],
        children: [
          { path: "", redirectTo: "showConfigurations", pathMatch: "full" },
          { path: "showConfigurations", loadChildren: "./show-config/show-config.module#ShowConfigModule", data: { 'title': 'View Pipelines' } },
          { path: "stageviewTrigger", loadChildren: "./stageview-trigger/stageview-trigger.module#StageviewTriggerModule" },
          { path: "stageviewHistory", loadChildren: "./stageview-history/stageview-history.module#StageviewHistoryModule" },
        { path : "workflowInfo", loadChildren: "./workflow-info/workflow-info.module#WorkflowModule" },
        { path:"approve", loadChildren:"./approve-release/approve-release.module#ApproveReleaseModule"},
          { path: "aboutView", loadChildren: 'app/about-view/about-view.module#AboutViewModule' },
          { path: 'dashboardView', loadChildren: 'app/dashboard-view/dashboard-view.module#DashboardViewModule' },
          { path : "schedule", loadChildren: "./build-interval-cntrl/build-interval-cntrl.module#BuildIntervalModule" },
          { path: "approveBuild", loadChildren: "./approve-build/approve-build.module#ApproveBuildModule" },
          { path: "createLicense", component: CreateLicenseComponent },
          { path: "servicePortal", component: ServicePortalComponent },
          { path: "createorg", component: CreateOrganizationComponent },
          { path: "trigger", component: TriggerComponent },
          { path: "**", redirectTo: "showConfigurations", pathMatch: "full" },
        ]
      },
      {
        path: "releaseConfig", component: ReleaseConfigsComponent, canActivate: [AuthGuardService],
        children: [
          { path: "", redirectTo: "release", pathMatch: "full" },
          { path: "release", loadChildren: "./add-update-release/add-update-release.module#AddUpdateReleaseModule" },
          { path: "**", redirectTo: "release", pathMatch: "full" }
        ]
      },
      { path: "editApp", loadChildren: "./create-application/create-application.module#CreateApplicationModule" },
      { path: "applications", loadChildren: "./application-list/application-list.module#ApplicationListModule" },
        { path: "notificationPage", component: NotificationInfoComponent },
      { path: "createPipeline/success", component: SuccessComponent},
      {
        path: "createConfig", component: CreateConfigComponent, canActivate: [AuthGuardService],
        children: [
          { path: "", redirectTo: "basicInfo", pathMatch: "full" },
          { path: "basicInfo", loadChildren: "./basic-info/basic-info.module#BasicInfoModule" },
          { path: "codeInfo", loadChildren: "./code-info/code-info.module#CodeInfoModule" },
          { path: "buildInfo", loadChildren: "./build-info/build-info.module#BuildInfoModule" },
          { path: "testInfo", loadChildren: "./test-info/test-info.module#TestInfoModule" },
          { path: "deployInfo", loadChildren: "./deploy-info/deploy-info.module#DeployInfoModule" },
          { path: "workflowInfo", loadChildren: "./workflow-info/workflow-info.module#WorkflowModule" },
          { path: "**", redirectTo: "basicInfo", pathMatch: "full" },
        ]
      }
    ]
  },
  { path: "keycloak", component: KeycloakComponent },
  { path: "pipelines/configuration/:appPipeName/:pipeLineName", component: MailSuccessComponent, canActivate: [AuthGuardService] },

  { path: "**", redirectTo: "/dashboard", pathMatch: "full", canActivate: [AuthGuardService] }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
