import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ApproveReleaseComponent } from "./approve-release.component";
import { approveReleaseRouter } from "./approve-release.router";
import { TranslateModule } from "@ngx-translate/core";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";

@NgModule({
  imports: [
    CommonModule,
	approveReleaseRouter,
	TranslateModule,
	HttpModule,
	FormsModule,
	AngularMultiSelectModule
  ],
  declarations: [ApproveReleaseComponent]
})
export class ApproveReleaseModule { }
