import {
  NgModule,
  APP_INITIALIZER,
  CUSTOM_ELEMENTS_SCHEMA,
  NO_ERRORS_SCHEMA,
} from "@angular/core";
import { LocationStrategy, HashLocationStrategy } from "@angular/common";
import { DatePipe } from "@angular/common";
import { PerfectScrollbarModule } from "ngx-perfect-scrollbar";
import { PERFECT_SCROLLBAR_CONFIG } from "ngx-perfect-scrollbar";
import { PerfectScrollbarConfigInterface } from "ngx-perfect-scrollbar";
import { FormsModule } from "@angular/forms";
import { HttpModule, Http } from "@angular/http";
import { HttpClientModule } from "@angular/common/http";
// Import routing module
import { InsightsRouter } from "./insights-info.router";
// Import 3rd party components
import { BsDropdownModule } from "ngx-bootstrap/dropdown";
import { TabsModule } from "ngx-bootstrap/tabs";
import { ChartsModule } from "ng2-charts/ng2-charts";
import { RestApiService } from "./shared/rest-api.service";
import { DataApiService } from "./shared/data-api.service";
import { CapsLockDirective } from "./shared/caps-lock.directive";
const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true,
};

import { InsightsInfoComponent } from "./insights-info.component";

// Import containers
import { DefaultLayoutComponent } from "./containers";

import { P404Component } from "./views/error/404.component";
import { P500Component } from "./views/error/500.component";

// Import 3rd party modules
import { CookieService, CookieModule } from "ngx-cookie";
import { NgxSpinnerModule } from "ngx-spinner";

const APP_CONTAINERS = [DefaultLayoutComponent];

import {
  AppAsideModule,
  AppBreadcrumbModule,
  AppHeaderModule,
  AppFooterModule,
  AppSidebarModule,
} from "@coreui/angular";

@NgModule({
  imports: [
    InsightsRouter,
    AppAsideModule,
    AppBreadcrumbModule.forRoot(),
    AppFooterModule,
    AppHeaderModule,
    AppSidebarModule,
    PerfectScrollbarModule,
    BsDropdownModule.forRoot(),
    TabsModule.forRoot(),
    ChartsModule,
    FormsModule,
    HttpModule,
    HttpClientModule,
    CookieModule.forRoot(),
    NgxSpinnerModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
  declarations: [
    InsightsInfoComponent,
    ...APP_CONTAINERS,
    P404Component,
    P500Component,

    CapsLockDirective,
  ],
  providers: [
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy,
    },
    RestApiService,
    DatePipe,
  ],
})
export class InsightsInfoModule {}
