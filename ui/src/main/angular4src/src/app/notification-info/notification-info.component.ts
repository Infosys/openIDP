/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import { Ng2TableModule } from "ng2-table/ng2-table";
import { PaginationModule } from "ngx-bootstrap/pagination";
import { NgModule } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { DataTableResource } from "angular-2-data-table";
import { IdprestapiService } from "../idprestapi.service";
import { IdpdataService } from "../idpdata.service";
import { IdpService } from "../idp-service.service";
import { CookieService } from "ngx-cookie";

@Component({
  selector: "app-notification-info",
  templateUrl: "./notification-info.component.html",
  styleUrls: ["./notification-info.component.css"],
})
@NgModule({
  imports: [Ng2TableModule, PaginationModule.forRoot()],
})
export class NotificationInfoComponent implements OnInit {
  public page = 1;
  public itemsPerPage = 10;
  public maxSize = 5;
  public numPages = 1;
  public length = 0;

  data: any;
  public rows: Array<any> = [];
  public columns: Array<any> = [
    { title: "Pipeline Name", name: "pipelineName", sort: false },
    { title: "Status", name: "status", sort: false },
    { title: "Creation Date", name: "date", sort: false },
  ];
  public config: any = {
    paging: true,
    sorting: { columns: this.columns },
    filtering: { filterString: "" },
    className: ["table-striped", "table-bordered"],
  };

  ngOnInit() {
    this.getNotification();
  }
  constructor(
    private IdpService: IdpService,
    private IdpdataService: IdpdataService,
    private IdprestapiService: IdprestapiService,
    private _cookieService: CookieService,
    private route: ActivatedRoute
  ) {
    this.IdpService.initMain();
    this.callforRest();
  }
  callforRest() {
    this.IdprestapiService.getData().then((response) => {
      try {
        if (response) {
          this.IdpdataService.devServerURL = response.json().idpresturl;
          this.IdpdataService.subscriptionServerURL = response.json().idpsubscriptionurl;
          this.IdpdataService.IDPDashboardURL = response.json().idpdashboardurl;
          this.IdpdataService.IDPLink = response.json().IDPLink;
          this.IdpdataService.geUrl = response.json().geUrl;
          this.IdpdataService.geFlag = response.json().geFlag;
          this.IdpdataService.serverUrl = response.json().tfsServerUrl;
          this.IdpdataService.uName = response.json().uName;
          this.IdpdataService.pass = response.json().pass;

          if (this._cookieService.get("access_token")) {
            //console.log("details");
            this.getNotification();
          }
        }
      } catch (e) {
        alert("failed to get properties details");
        //console.log(e);
      }
    });
  }

  /* Getting notification for jobs created */
  getNotification() {
    this.IdprestapiService.getNotification().then((response) => {
      try {
        if (response) {
          // alert("getNotif")
          const temp = response.json().resource;
          const notification = JSON.parse(temp).notificationList;
          // console.log(notification);
          this.data = notification;
          this.length = this.data.length;
          this.maxSize = this.data.length;
          this.onChangeTable(this.config);
          // console.log(this.data);
        }
      } catch (e) {
        alert("Failed to get notifications!");
        //console.log(e);
      }
    });
  }

  public changePage(page: any, data: Array<any> = this.data): Array<any> {
    const start = (page.page - 1) * page.itemsPerPage;
    const end =
      page.itemsPerPage > -1 ? start + page.itemsPerPage : data.length;
    return data.slice(start, end);
  }
  public changeSort(data: any, config: any): any {
    if (!config.sorting) {
      return data;
    }
    const columns = this.config.sorting.columns || [];
    let columnName: string = void 0;
    let sort: string = void 0;
    for (let i = 0; i < columns.length; i++) {
      if (columns[i].sort !== "" && columns[i].sort !== false) {
        columnName = columns[i].name;
        sort = columns[i].sort;
      }
    }
    if (!columnName) {
      return data;
    }
    // simple sorting
    return data.sort((previous: any, current: any) => {
      if (previous[columnName] > current[columnName]) {
        return sort === "desc" ? -1 : 1;
      } else if (previous[columnName] < current[columnName]) {
        return sort === "asc" ? -1 : 1;
      }
      return 0;
    });
  }
  public changeFilter(data: any, config: any): any {
    let filteredData: Array<any> = data;
    this.columns.forEach((column: any) => {
      if (column.filtering) {
        filteredData = filteredData.filter((item: any) => {
          return item[column.name].match(column.filtering.filterString);
        });
      }
    });
    if (!config.filtering) {
      return filteredData;
    }
    if (config.filtering.columnName) {
      return filteredData.filter((item: any) =>
        item[config.filtering.columnName]
          .toLowerCase()
          .match(this.config.filtering.filterString.toLowerCase())
      );
    }
    const tempArray: Array<any> = [];
    filteredData.forEach((item: any) => {
      let flag = false;
      this.columns.forEach((column: any) => {
        if (item[column.name] !== undefined) {
          if (
            item[column.name]
              .toString()
              .toLowerCase()
              .match(this.config.filtering.filterString.toLowerCase())
          ) {
            flag = true;
          }
        }
      });
      if (flag) {
        tempArray.push(item);
      }
    });
    filteredData = tempArray;
    return filteredData;
  }
  public onChangeTable(
    config: any,
    page: any = { page: this.page, itemsPerPage: this.itemsPerPage }
  ): any {
    if (config.filtering) {
      Object.assign(this.config.filtering, config.filtering);
    }

    if (config.sorting) {
      Object.assign(this.config.sorting, config.sorting);
    }

    const filteredData = this.changeFilter(this.data, this.config);
    const sortedData = this.changeSort(filteredData, this.config);
    this.rows =
      page && config.paging ? this.changePage(page, sortedData) : sortedData;
    this.length = sortedData.length;
  }

  public onCellClick(data: any): any {}
}
