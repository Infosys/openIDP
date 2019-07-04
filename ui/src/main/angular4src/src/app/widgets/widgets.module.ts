import { NgModule,NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TriggerPipelineComponent } from './trigger-pipeline/trigger-pipeline.component';
import { CopyPipelineComponent } from './copy-pipeline/copy-pipeline.component';
import { EditPipelineComponent } from './edit-pipeline/edit-pipeline.component';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
@NgModule({
  imports: [
    CommonModule,
    TooltipModule.forRoot()
  ],
  declarations: [TriggerPipelineComponent, CopyPipelineComponent, EditPipelineComponent],
  exports:[TriggerPipelineComponent,CopyPipelineComponent,EditPipelineComponent],
  schemas: [NO_ERRORS_SCHEMA]
})
export class WidgetsModule { }
