import { Component, OnInit, ChangeDetectorRef } from "@angular/core";
import { PaginationInstance } from "ngx-pagination";
import { RestApiService } from "../../../shared/rest-api.service";
import { DataApiService } from "../../../shared/data-api.service";

@Component({
  selector: "app-agg-logs",
  templateUrl: "./agg-logs.component.html",
  styleUrls: ["./agg-logs.component.scss"],
})
export class AggLogsComponent implements OnInit {
  appName = "IDPApp";
  activatedTabs = {};
  applicationList = [];
  appList = [];
  demoList = [];
  public p = 1;
  public filter = "";
  public maxSize = 7;
  public directionLinks = true;
  public config: PaginationInstance = {
    itemsPerPage: 10,
    currentPage: 1,
  };
  public labels: any = {
    previousLabel: "Previous",
    nextLabel: "Next",
    screenReaderPaginationLabel: "Pagination",
    screenReaderPageLabel: "page",
    screenReaderCurrentLabel: "You're on page",
  };
  aggregateLogs = [];
  constructor(
    private restService: RestApiService,
    private dataService: DataApiService,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.activatedTabs = this.dataService.activatedTabs;
    this.getUserAccessApplication();
    this.appName = this.dataService.appName;
    if (this.appName) {
      this.getExceptionDetails();
    }
  }
  onPageChange(number: number) {
    //console.log('change to page', number);
    this.config.currentPage = number;
    this.p = number;
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
    //console.log("Inside getApp")
    for (let c of this.appList) {
      this.demoList.push(c.applicationName);
      //console.log("********************")
    }
    //console.log(this.demoList);
    this.restService.getApplist(this.demoList).then((res) => {
      //console.log(res)
      if (res) {
        if (res["status"] === 200) {
          //console.log(res.json())
          this.applicationList = res.json();
        }
      }
    });
  }

  getExceptionDetails() {
    let exceptionDetails = [];
    if (this.appName) {
      this.dataService.appName = this.appName;
      this.restService.getAggregatedLogsDetails(this.appName).then((res) => {
        if (res) {
          if (res["status"] === 200) {
            exceptionDetails = JSON.parse(res.json()["resource"]);
            if (exceptionDetails) {
              if (exceptionDetails.length > 0) {
                this.aggregateLogs = exceptionDetails;
              } else {
                this.aggregateLogs = [];
              }
            }
            this.changeDetectorRef.detectChanges();
          }
        }
      });
    }
  }
}
