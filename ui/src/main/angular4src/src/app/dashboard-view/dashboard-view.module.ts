import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { DashboardViewComponent } from "./dashboard-view.component";
import { dashboardViewRouter } from "./dashboard-view.router";
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { SafePipeModule } from "../safe-pipe.module";

@NgModule({
  imports: [
    CommonModule,
    dashboardViewRouter,
    TranslateModule,
    HttpModule,
    FormsModule,
    SafePipeModule,
  ],
  declarations: [DashboardViewComponent],
})
export class DashboardViewModule {}
