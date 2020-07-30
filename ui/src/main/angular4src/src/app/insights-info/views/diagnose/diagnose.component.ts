import { Component, OnInit, ViewChild } from "@angular/core";
import { DataApiService } from "../../shared/data-api.service";
import { RestApiService } from "../../shared/rest-api.service";
import { DomSanitizer, SafeResourceUrl } from "@angular/platform-browser";
import { NgxSpinnerService } from "ngx-spinner";
import { PaginationInstance } from "ngx-pagination";
import { THIS_EXPR } from "@angular/compiler/src/output/output_ast";
import { TrendsComponent } from "../diagnose/trends/trends.component";
import { directiveDef } from "@angular/core/src/view";
@Component({
  selector: "app-diagnose",
  templateUrl: "./diagnose.component.html",
  styleUrls: ["./diagnose.component.scss"],
})
export class DiagnoseComponent implements OnInit {
  //@ViewChild(TrendsComponent) child: TrendsComponent;
  elements: any[] = [];
  activatedTabs = {};
  appName = "";
  message = "";
  //setClickedRow:Function;
  selectedRow;
  diagnoseUrl: SafeResourceUrl;
  invalidUrl: boolean;
  applicationList = [];
  containerList = [];
  notifications = [];
  selected_value = "";
  appList = [];
  demoList = [];
  constructor(
    private dataService: DataApiService,
    private restService: RestApiService,
    private sanitizer: DomSanitizer,
    private spinner: NgxSpinnerService //private trendscomp: TrendsComponent
  ) {
    //{
    //   this.setClickedRow = function(index){
    //     this.selectedRow = index;
    //     }
  }

  ngOnInit() {
    this.activatedTabs = this.dataService.activatedTabs;
    this.getUserAccessApplication();
    this.appName = this.dataService.appName;
    this.getNotifications();
    if (this.appName) {
      this.getDiagnosisUrl();
      this.getContainerList();
    }
  }

  setClickedRow(index) {
    this.selectedRow = index;
    let name;
    name = this.notifications[index];
    name = name.split(":")[0];
    this.selected_value = name;
  }
  transform(url) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  getSelectedValue() {
    return this.selected_value;
  }

  getUserAccessApplication() {
    this.restService.getUserAccessApplication().then((res) => {
      if (res) {
        if (res["status"] === 200) {
          this.appList = JSON.parse(res.json()["resource"])["applications"];
        }
      }
      this.getAppList();
    });
  }
  getAppList() {
    for (let c of this.appList) {
      this.demoList.push(c.applicationName);
    }

    this.restService.getApplist(this.demoList).then((res) => {
      if (res) {
        if (res["status"] === 200) {
          this.applicationList = res.json();
        }
      }
    });
  }

  getDiagnosisUrl() {
    if (this.appName) {
      this.dataService.appName = this.appName;
      this.restService.getDiagnosisUrl(this.appName).then((res) => {
        if (res) {
          if (res["status"] === 200) {
            const url = res.json()["resource"];
            if (url.indexOf("http") > -1) {
              this.diagnoseUrl = this.transform(url);
              this.invalidUrl = false;
            } else {
              this.diagnoseUrl = undefined;
              this.invalidUrl = true;
            }
          }
        }
      });
    }
  }

  getContainerList() {
    let responseData;

    this.restService.getContainerList(this.appName).then((res) => {
      if (res) {
        if (res["status"] === 200) {
          responseData = JSON.parse(res.json()["resource"]);
          this.containerList = responseData["containerList"];
        }
      }
    });
  }

  getNotifications() {
    let responseData;
    this.notifications = [];
    this.selectedRow = -1;

    // this.spinner.show();
    this.restService.getNotifications("ShoppingCart").then((res) => {
      if (res) {
        if (res["status"] === 200) {
          responseData = res.json().res;
          for (let i = 0; i < responseData.length; i++) {
            this.selected_value = responseData[i][0];
            this.notifications.push(
              responseData[i][0] + ": CPU/Mem spike at " + responseData[i][1]
            );
          }
        }
      }
    });
  }
}
