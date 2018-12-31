import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ApproveReleaseComponent } from "./approve-release.component";
import { approveReleaseRouter } from "./approve-release.router";
import { TranslateModule, TranslateStaticLoader, TranslateLoader } from "ng2-translate";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown/angular2-multiselect-dropdown";

export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, "assets/i18n", ".json");
}

@NgModule({
  imports: [
    CommonModule,
	approveReleaseRouter,
	TranslateModule.forRoot({
      provide: TranslateLoader,
      useFactory: (createTranslateLoader),
      deps: [Http]
    }),
	HttpModule,
	FormsModule,
	AngularMultiSelectModule
  ],
  declarations: [ApproveReleaseComponent]
})
export class ApproveReleaseModule { }
