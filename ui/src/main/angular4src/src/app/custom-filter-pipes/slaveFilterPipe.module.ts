import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import {SlaveLabelfilterPipe} from "./slaveFilterPipe";

@NgModule({
  imports: [
    ],
  declarations: [SlaveLabelfilterPipe],
  exports: [SlaveLabelfilterPipe],
})
export class SlaveLabelFilterModule { }
