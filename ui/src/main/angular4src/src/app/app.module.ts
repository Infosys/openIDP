/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { BrowserModule } from "@angular/platform-browser";
import { NgModule, APP_INITIALIZER, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from "@angular/core";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CookieModule, CookieService } from "ngx-cookie";
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { HttpModule, Http } from "@angular/http";
import { DateTimePickerModule } from "ng-pick-datetime";
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { AppComponent } from "./app.component";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import { SsoComponent } from './sso/sso.component';
import { SsoService } from './sso/sso.service';
import { OAuthModule } from 'angular-oauth2-oidc';

import { FormsModule } from "@angular/forms";
import { LoadingModule, ANIMATION_TYPES } from "ngx-loading";
import { AppRoutingModule } from "./app-routing.module";
import { LoginComponent } from "./login/login.component";
import { IdpService } from "./idp-service.service";
import { IdpSubmitService } from "./idpsubmit.service";
import { IdprestapiService } from "./idprestapi.service";
import { IdpdataService } from "./idpdata.service";
import { IdpheaderComponent } from "./idpheader/idpheader.component";
import { CreateConfigComponent } from "./create-config/create-config.component";
import { IdpNavBarComponent } from "./idp-nav-bar/idp-nav-bar.component";
import { PreviousConfigComponent } from "./previous-config/previous-config.component";
import { KeycloakComponent } from "./keycloak/keycloak.component";
import { AuthGuardService } from "./auth-guard.service";
import { Ng2TableModule } from "ng2-table/ng2-table";
import { SuccessComponent } from "./success/success.component";
import { PaginationModule } from "ngx-bootstrap/pagination";
import { TriggerServiceComponent } from "./trigger-service/trigger-service.component";
import { MailSuccessComponent } from "./mail-success/mail-success.component";
import { CommonModule } from "@angular/common";
import { IDPEncryption } from "./idpencryption.service";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";
import { ServicePortalComponent } from "./service-portal/service-portal.component";
import { TriggerModule } from "./triggerPipeline/triggerPipeline.module";
import { SortablejsModule } from "angular-sortablejs";
import { AdalService, AdalGuard, AdalInterceptor } from 'adal-angular4';
import { StartupService } from "./startup.service";
import { ReleaseConfigsComponent } from "./release-configs/release-configs.component";
import { LoginKcService } from "./login-kc.service";
import { SubscriptionService } from "./subscription.service";
import { KeycloakService } from "./keycloak/keycloak.service";
import { RequestOptions, XHRBackend } from "@angular/http";
import { KeycloakHttp, keycloakHttpFactory } from "./keycloak/keycloak.http";
import { ManageEnvironmentComponent } from "./manage-environment/manage-environment.component";
import { CreateLicenseComponent } from "./create-license/create-license.component";
import { CreateOrganizationComponent } from "./create-organization/create-organization.component";
import { NotificationInfoComponent } from "./notification-info/notification-info.component";
// import { DynamicComponentDirective } from "./custom-directive/dynamicComponent.directive";
// import { WorkflowInfoComponent } from "./workflow-info/workflow-info.component";
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import {NgxPaginationModule} from 'ngx-pagination';
import { MomentModule } from 'ngx-moment';

import {
    AppAsideModule,
    AppBreadcrumbModule,
    AppHeaderModule,
    AppFooterModule,
    AppSidebarModule,
} from '@coreui/angular';

export function createTranslateLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, "assets/i18n/", ".json");
}
const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
    suppressScrollX: true
};
import { DefaultLayoutComponent } from './default-layout';
import { WidgetsModule } from "./widgets/widgets.module";
import {BuildIntervalModule} from "./build-interval-cntrl/build-interval-cntrl.module";
import {NgxSpinnerModule} from "ngx-spinner";

export function startupServiceFactory(startupService: StartupService): Function {
    return () => startupService.load();
}
const APP_CONTAINERS = [
    DefaultLayoutComponent
];
@NgModule({
    declarations: [
        AppComponent,
        ...APP_CONTAINERS,
        LoginComponent,
        KeycloakComponent,
        IdpheaderComponent,
        CreateConfigComponent,
        IdpNavBarComponent,
        PreviousConfigComponent,
        SuccessComponent,
        TriggerServiceComponent,
        MailSuccessComponent,
        ReleaseConfigsComponent,
        ServicePortalComponent,
        ManageEnvironmentComponent,
        CreateLicenseComponent,
        NotificationInfoComponent,
        CreateOrganizationComponent,
        SsoComponent,
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        DateTimePickerModule,
        AppAsideModule,
        AppBreadcrumbModule.forRoot(),
        AppFooterModule,
        AppHeaderModule,
        AppSidebarModule,
        PerfectScrollbarModule,
        CookieModule.forRoot(),
        OAuthModule.forRoot(),
        HttpClientModule,
        HttpModule,
        Ng2TableModule,
        AngularMultiSelectModule,
        FormsModule,
        CommonModule,
        MomentModule,
        PaginationModule.forRoot(),
        TranslateModule.forRoot(
            {
                loader: {
                    provide: TranslateLoader,
                    useFactory: (createTranslateLoader),
                    deps: [HttpClient]
                }
            }),
        FormsModule,
        CommonModule,
        NgxPaginationModule,
        BsDropdownModule.forRoot(),
        CollapseModule.forRoot(),
        ModalModule.forRoot(),
        TabsModule.forRoot(),

        LoadingModule.forRoot({
            animationType: ANIMATION_TYPES.rectangleBounce
        }),
        AppRoutingModule,
        TriggerModule,
        SortablejsModule.forRoot({ animation: 150 }),
        WidgetsModule,
        NgxSpinnerModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    providers: [IdpService, IdpSubmitService, SubscriptionService, IdprestapiService, IdpdataService,
        AuthGuardService, CookieService, IDPEncryption, AdalService,
        AdalGuard,
        // { provide: HTTP_INTERCEPTORS, useClass: AdalInterceptor, multi: true },

        StartupService,
        {
            provide: APP_INITIALIZER,
            useFactory: startupServiceFactory,
            deps: [StartupService],
            multi: true
        },
        LoginKcService,
        {
            provide: KeycloakHttp,
            useFactory: keycloakHttpFactory,
            deps: [XHRBackend, RequestOptions, KeycloakService]
        },
        KeycloakService,
        SsoService],
    bootstrap: [AppComponent]
})
export class AppModule { }
