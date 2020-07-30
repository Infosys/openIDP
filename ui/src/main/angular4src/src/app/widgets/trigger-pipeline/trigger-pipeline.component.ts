import { Component, OnInit, Input } from "@angular/core";
import { Router } from "@angular/router";

@Component({
  selector: "trigger-pipeline",
  templateUrl: "./trigger-pipeline.component.html",
  styleUrls: [],
})
export class TriggerPipelineComponent implements OnInit {
  @Input() applicationName: string;
  @Input() pipelineName: string;

  constructor(private router: Router) {}

  ngOnInit() {}

  navigateToTriggerPage() {
    this.router.navigate(["/previousConfig/trigger"], {
      queryParams: {
        applicationName: this.applicationName,
        pipelineName: this.pipelineName,
      },
    });
  }
}
