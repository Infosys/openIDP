import { Component, OnInit, Input } from "@angular/core";
import { IdpdataService } from "../../idpdata.service";
import { Router } from "@angular/router";
import { IdprestapiService } from "../../idprestapi.service";

@Component({
  selector: "copy-pipeline",
  templateUrl: "./copy-pipeline.component.html",
  styleUrls: [],
})
export class CopyPipelineComponent implements OnInit {
  @Input() appName: string;
  @Input() pipeName: string;

  constructor(
    private idpdataService: IdpdataService,
    private router: Router,
    private IdprestapiService: IdprestapiService
  ) {}

  ngOnInit() {}
  navigateToCopyEdit() {
    this.idpdataService.operation = "copy";
    localStorage.setItem("appName", this.appName);
    localStorage.setItem("pipeName", this.pipeName);
    const data = this.appName;
    this.checkApplicationType();
    this.router.navigate(["createConfig/basicInfo"]);
  }
  checkApplicationType() {
    const data = this.appName;
    this.IdprestapiService.checkForApplicationType(data).then((response) => {
      try {
        if (response) {
          if (
            response.json().errorMessage === null &&
            response.json().resource !== ""
          ) {
            if (response.json().resource === "true") {
              this.idpdataService.isSAPApplication = true;
            } else {
              this.idpdataService.isSAPApplication = false;
            }
          } else {
            alert("Failed to verify application Type");
          }
        }
      } catch (e) {
        alert("Failed during verifying the application type");
      }
      this.idpdataService.loading = false;
    });
  }
}
