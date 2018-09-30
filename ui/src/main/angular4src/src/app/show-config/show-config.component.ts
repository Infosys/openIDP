/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit, NgModule, ViewChild } from "@angular/core";
import { TranslateService } from "ng2-translate";
import { IdpService } from "../idp-service.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router } from "@angular/router";
import { IdpdataService } from "../idpdata.service";
import { CookieService } from "ngx-cookie";
import { NgTableComponent, NgTableFilteringDirective, NgTablePagingDirective, NgTableSortingDirective } from "ng2-table/ng2-table";
@Component({
  selector: "app-show-config",
  templateUrl: "./show-config.component.html",
  styleUrls: ["./show-config.component.css"]
})

export class ShowConfigurationsComponent implements OnInit {
  @ViewChild ("modalforAlertconfig") button;
  @ViewChild ("modalforDelete") buttonDel;
  @ViewChild ("modalforDeleteAlert") msgAlertDel;
  reqData: any;
  schedule: any;
  data: any;
  isBuild: any = false;
  isDeploy: any = false;
  public rows: Array<any> = [];
  public columns: Array<any> = [
    { title: "Application Name", name: "applicationName" },
    {
        title: "Pipeline Name",
        name: "pipelineName"
    },
    { title: "Creation Date", name: "creationDate" },
    { title: "Trigger Pipeline", name: "trigger", sort: false },
    { title: "Schedule Job", name: "schedule", sort: false },
    { title: "Copy Pipeline", name: "copy", sort: false },
    { title: "Edit Pipeline", name: "edit", sort: false },
    { title: "Delete Pipeline", name: "delete", sort: false },
  ];
  public page = 1;
  public itemsPerPage = 10;
  public maxSize = 5;
  public numPages = 1;
  public length = 0;
  public config: any = {
    paging: true,
    sorting: { columns: this.columns },
    filtering: { filterString: "" },
    className: ["table-striped", "table-bordered"]
  };
  public constructor(
    private IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private router: Router,
    private _cookieService: CookieService
  ) {
      this.IdpdataService.schedulePage = false;
    this.IdpdataService.data = JSON.parse(JSON.stringify(this.IdpdataService.template));
    this.IdpdataService.operation = "";
    // workflow remove
    this.IdpdataService.workflowData = [];
    this.IdpdataService.workflowDataTemp = [];
    this.callforRest();
  }
  callforRest() {
    this.IdprestapiService.getData()
        .then(response => {
        try {
            if (response) {
                this.IdpdataService.devServerURL = response.json().idpresturl;
            // this.IdpdataService.devServerURL = "http://server401189d:8889/idprest";
            // this.IdpdataService.devServerURL = "https://idplinv02:8889/idprest";
            // this.IdpdataService.devServerURL = "http://server411214d:8889/idprest";
             // this.IdpdataService.subscriptionServerURL = response.json().idpsubscriptionurl;
            this.IdpdataService.IDPDashboardURL = response.json().idpdashboardurl;
            this.IdpdataService.IDPLink = response.json().IDPLink;
            this.IdpdataService.geUrl = response.json().geUrl;
            this.IdpdataService.geFlag = response.json().geFlag;
            this.IdpdataService.serverUrl = response.json().tfsServerUrl;
            this.IdpdataService.uName = response.json().uName;
            this.IdpdataService.pass = response.json().pass;
            if (this._cookieService.get("access_token")) {
                this.getPipelineData();
                this.IdpService.getDetails();
            }
            }
        } catch (e) {
            alert("failed to get properties details");
            console.log(e);
        }
        });
  }
  public ngOnInit(): void {

  }
  public changePage(page: any, data: Array<any> = this.data): Array<any> {
    const start = (page.page - 1) * page.itemsPerPage;
    const end = page.itemsPerPage > -1 ? (start + page.itemsPerPage) : data.length;
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
        item[config.filtering.columnName].toLowerCase().match(this.config.filtering.filterString.toLowerCase()));
    }

    const tempArray: Array<any> = [];
    filteredData.forEach((item: any) => {
        let flag = false;
        this.columns.forEach((column: any) => {
        if (item[column.name] !== undefined) {
            if (item[column.name].toString().toLowerCase().match(this.config.filtering.filterString.toLowerCase())) {
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

  public onChangeTable(config: any, page: any = { page: this.page, itemsPerPage: this.itemsPerPage }): any {
    if (config.filtering) {
        Object.assign(this.config.filtering, config.filtering);
    }

    if (config.sorting) {
        Object.assign(this.config.sorting, config.sorting);
    }

    const filteredData = this.changeFilter(this.data, this.config);
    const sortedData = this.changeSort(filteredData, this.config);
    this.rows = page && config.paging ? this.changePage(page, sortedData) : sortedData;
    this.length = sortedData.length;
  }

  public onCellClick(data: any): any {
    this.IdpdataService.operation = "";
const indexStart = data.row.pipelineName.indexOf(">") + 1;
    const indexEnd = data.row.pipelineName.indexOf("</");
    const pName = data.row.pipelineName.substring(indexStart, indexEnd);
    console.log(pName);
    if (data.column === "pipelineName") {
        this.reqData = { "applicationName": data.row.applicationName, "pipelineName": pName, "userName": this.IdpdataService.idpUserName };
        this.IdpdataService.pipelineName = this.reqData.pipelineName;
      this.IdpdataService.appName = this.reqData.applicationName;
      this.stageView();
    }
    if (data.column === "trigger") {
        let trigger = true;
        for (let i = 0 ; i < this.IdpdataService.pipelineData.length; i++) {
        if (data.row.pipelineName === this.IdpdataService.pipelineData[i].pipelineName) {
            trigger = this.IdpdataService.pipelineData[i].triggerFlag;
            break;
        }
        }
        this.reqData = { "applicationName": data.row.applicationName, "pipelineName": pName, "userName": this.IdpdataService.idpUserName };
     this.IdpdataService.pipelineName = this.reqData.pipelineName;
    this.IdpdataService.appName = this.reqData.applicationName;
    if (data.column === "trigger" && trigger) {
        this.trigger();
    }
    }
    if (data.column === "schedule") {
        this.IdpdataService.schedulePage = true;
      this.reqData = { "applicationName": data.row.applicationName, "pipelineName": pName, "userName": this.IdpdataService.idpUserName };
     this.IdpdataService.pipelineName = this.reqData.pipelineName;
      this.IdpdataService.appName = this.reqData.applicationName;
        this.trigger();
    }
    if (data.column === "copy" || data.column === "edit") {
        let copy = true;
        let edit = true;
        for (let i = 0 ; i < this.IdpdataService.pipelineData.length; i++) {
        if (data.row.pipelineName === this.IdpdataService.pipelineData[i].pipelineName) {
            copy = this.IdpdataService.pipelineData[i].copyFlag;
            edit = this.IdpdataService.pipelineData[i].editFlag;
            break;
        }
        }
        this.reqData = { "applicationName": data.row.applicationName, "pipelineName": pName, "userName": this.IdpdataService.idpUserName };
        if (data.column === "copy" && copy) {
        this.copyEdit(data.column);
        } else if (data.column === "edit" && edit) {
        this.copyEdit(data.column);
        }
    }
    if (data.column === "delete") {
        let delete1 = true ;
        for (let i = 0 ; i < this.IdpdataService.pipelineData.length; i++) {
        if (data.row.pipelineName === this.IdpdataService.pipelineData[i].pipelineName) {
            delete1 = this.IdpdataService.pipelineData[i].deleteFlag;
            break;
        }
        }
        this.reqData = { "applicationName": data.row.applicationName, "pipelineName": pName, "userName": this.IdpdataService.idpUserName };
        if (data.column === "delete" && delete1) {
        this.deleteAlert();
        }
    }
  }
stageView() {
        this.router.navigate(["/previousConfig/stageviewHistory"]);
    }

  trigger() {
    this.checkApplicationType();
    this.trigger1();
  }
  trigger1() {
    this.IdprestapiService.triggerJob(this.reqData)
        .then(response => {
        try {
            if (response) {
            const result = response.json().resource;
            if (result !== "{}" && result !== null) {
                this.IdpdataService.triggerJobData = JSON.parse(result);
                const temp = JSON.parse(result);
                const checkInBuild = this.IdpdataService.triggerJobData.hasOwnProperty("build")
                && this.IdpdataService.triggerJobData.build.approveBuild !== undefined
                && this.IdpdataService.triggerJobData.build.approveBuild !== null
                    && this.IdpdataService.triggerJobData.build.approveBuild.length !== 0;
                const checkInDeploy = this.IdpdataService.triggerJobData.hasOwnProperty("deploy")
                && this.IdpdataService.triggerJobData.deploy.workEnvApprovalList !== undefined
                && this.IdpdataService.triggerJobData.deploy.workEnvApprovalList !== null
                    && Object.keys(this.IdpdataService.triggerJobData.deploy.workEnvApprovalList).length !== 0
                    && this.IdpdataService.triggerJobData.deploy.workEnvApprovalList.constructor === Object;
                if (checkInBuild || checkInDeploy) {
                this.IdpdataService.checkPausedBuilds = true;
                if (checkInBuild) {
                    this.isBuild = true;
                }
                if (checkInDeploy) {
                this.isDeploy = true;
                }
            } else {
                            this.IdpdataService.checkPausedBuilds = false;
                    }
                if (this.IdpdataService.triggerJobData.applicationName) {
                const applicationName = this.IdpdataService.triggerJobData.applicationName;
                }
              if (this.IdpdataService.triggerJobData.releaseNumber !== null && this.IdpdataService.triggerJobData.releaseNumber.length !== 0) {
            if (this.IdpdataService.checkPausedBuilds === true) {
                if (this.IdpdataService.triggerJobData.roles.indexOf("RELEASE_MANAGER") !== -1
                && (this.isBuild === true || this.isDeploy === true)) {
                const forBuild = this.isBuild && this.IdpdataService.approveBuildFlag;
                const forDeploy = this.isDeploy && this.IdpdataService.approveDeployFlag;
                if (forBuild && forDeploy) {
                    this.router.navigate(["/previousConfig/approveBuild"]);
                }

                if (forBuild) {
                    if ((this.IdpdataService.approveDeployFlag !== true)) {
                    alert("You do no thave permission to approve build for Deploy Stage");
                    }
                    this.router.navigate(["/previousConfig/approveBuild"]);
                }
                if (forDeploy) {
                    if ((this.IdpdataService.approveBuildFlag !== true)) {
                    alert("You do no thave permission to approve build for Build Stage");
                    }
                    this.router.navigate(["/previousConfig/approveBuild"]);
                }
                } else {
                alert("A build is waiting approval. Kindly contact your release manager.");
                }
            } else {
            const x = confirm("Please ensure slave is launched");
            if (x) {
                if (this.IdpdataService.schedulePage === true) {
                this.router.navigate(["/previousConfig/schedule"]);
                } else {
                this.router.navigate(["/previousConfig/trigger"]);
                }
            }
            }
              } else if (this.IdpdataService.triggerJobData.roles.indexOf("RELEASE_MANAGER") !== -1) {
                  alert("No active releases for this pipeline. Please add releases.");
                  this.router.navigate(["/releaseConfig/release"]);
              } else {
                  alert("No active releases for this pipeline. Please contact the release manager.");
              }
        } else {
                alert("Failed  to get the Trigger Job Details");
            }
            }
        } catch (e) {
            alert("Failed to get trigger details");
            console.log(e);
        }
        });
  }

  copyEdit(operation) {
    this.IdpdataService.operation = operation;
    localStorage.setItem("appName", this.reqData.applicationName);
    localStorage.setItem("pipeName", this.reqData.pipelineName);
    const data = this.reqData.applicationName;
    this.checkApplicationType();
    this.router.navigate(["createConfig/basicInfo"]);
  }
  checkApplicationType() {
    const data = this.reqData.applicationName;
    this.IdpdataService.isSAPApplication = false;
  }
  TriggerAlert() {
    this.button.nativeElement.click();
  }
  deleteAlert() {
    this.buttonDel.nativeElement.click();
  }

  delete() {
    this.IdprestapiService.deletePipeline(this.reqData)
        .then(response => {
        try {
        if (response) {
            this.msgAlert();
            this.getPipelineData();
        }
        } catch (e) {
        alert("failed to delete the pipeline ");
        console.log(e);
        }
        });
  }

  msgAlert() {
    this.msgAlertDel.nativeElement.click();
  }

  getPipelineData() {
    console.log(this.IdpdataService.devServerURL);
    this.IdprestapiService.checkAvailableJobs()
        .then(response => {
        try {
            if (response) {
            const data = response.json().resource;
            if (data !== "No pipelines to trigger") {
                this.IdpdataService.pipelineData = [];
                const maindata = JSON.parse(data).pipelineDetails;
                if (maindata && maindata.length !== 0) {
                for (let i = 0; i < maindata.length; i++) {
                this.IdpdataService.pipelineData.push(maindata[i]);
                      const tempPipeName = maindata[i].pipelineName;
                const permissions = maindata[i].permissions;
                const triggerPipeline = (permissions.indexOf("BUILD") === -1
                    && permissions.indexOf("DEPLOY") === -1 && permissions.indexOf("TEST") === -1) ? false : true;
                const copyPipeline = (permissions.indexOf("COPY_PIPELINE") === -1) ? false : true ;
                const editPipeline = (permissions.indexOf("EDIT_PIPELINE") === -1) ? false : true ;
                const deletePipeline = (permissions.indexOf("DELETE_PIPELINE") === -1) ? false : true ;
                const approveRelease = (this.IdpdataService.role.indexOf("ENVIRONMENT_OWNER") === -1 ||
                    maindata[i].buildTool === "SapNonCharm" || maindata[i].buildTool === "workflow") ? false : true ;
                      this.IdpdataService.pipelineData[i].pipelineName = "<a style=\"cursor:pointer\">" + tempPipeName + "</a>";
                this.IdpdataService.pipelineData[i].trigger = "<div class=\"text-center field-tip\"><input TYPE=\"image\"" +
                    "src=\"assets/images/build_now.png\"" +
                    "id=\"TBN\" name=\"TBN\"  /></div>";
                this.IdpdataService.pipelineData[i].copyFlag = copyPipeline;
                this.IdpdataService.pipelineData[i].editFlag = editPipeline;
                this.IdpdataService.pipelineData[i].deleteFlag = deletePipeline;
                this.IdpdataService.pipelineData[i].approveFlag = approveRelease;
                this.IdpdataService.pipelineData[i].triggerFlag = triggerPipeline;
                if (copyPipeline) {
                    this.IdpdataService.pipelineData[i].copy = "<div class=\"text-center \"><input TYPE=\"image\"" +
                    "src=\"assets/images/copy_job.png\" id=\"TBN\" name=\"TBN\"  /></div>";
                } else {
                this.IdpdataService.pipelineData[i].copy = "<div class=\"text-center field-tip\"" +
                    "style=\"cursor:not-allowed\"><input style=\"cursor:not-allowed;opacity:0.4\" TYPE=\"image\"" +
                    "readonly [disabled]=\"true\" src=\"assets/images/copy_job.png\" id=\"TBN\" name=\"TBN\"  />" +
                    "<span class=\"tip-content text-center\" style=\"z-index: 1;cursor:not-allowed;width:80%;\">" +
                    "Copy pipeline is not available </span></div>";
                }

                if (editPipeline) {
                    this.IdpdataService.pipelineData[i].edit = "<div class=\"text-center \"><input TYPE=\"image\"" +
                    "src=\"assets/images/edit.png\" id=\"TBN\" name=\"TBN\"  /></div>";
                } else {
                this.IdpdataService.pipelineData[i].edit = "<div class=\"text-center field-tip\"" +
                    "style=\"cursor:not-allowed\"><input TYPE=\"image\" style=\"cursor:not-allowed;opacity:0.4\"" +
                    "readonly [disabled]=\"true\"  src=\"assets/images/edit.png\" id=\"TBN\" name=\"TBN\"  /><span class=\"" +
                    "tip-content text-center\" style=\"z-index: 1;cursor:not-allowed;width:80%;\">" +
                    "Edit pipeline is not available </span></div>";
                }
                if (deletePipeline) {
                    this.IdpdataService.pipelineData[i].delete = "<div class=\"text-center \"><input TYPE=\"image\"" +
                    "src=\"assets/images/removeBtn.png\" id=\"TBN\" name=\"TBN\"  /></div>";
                } else {
                    this.IdpdataService.pipelineData[i].delete = "<div class=\"text-center field-tip\"" +
                    "style=\"cursor:not-allowed\"><input TYPE=\"image\" style=\"cursor:not-allowed;opacity:0.4\"" +
                    "readonly [disabled]=\"true\" src=\"assets/images/removeBtn.png\" id=\"TBN\" name=\"TBN\"  />" +
                    "<span  class=\"tip-content text-center\" style=\"z-index: 1;cursor:not-allowed;width:70%;\">" +
                        "Delete pipeline is not available </span></div>";
                }

                if (approveRelease) {
                    this.IdpdataService.pipelineData[i].approve = "<div class=\"text-center \"><input TYPE=\"image\"" +
                    "src=\"assets/images/images.png\" id=\"TBN\" name=\"TBN\"  /></div>";
                } else {
                    this.IdpdataService.pipelineData[i].approve = "<div class=\"text-center field-tip\" style=\"cursor:not-allowed\">" +
                    "<input TYPE=\"image\" style=\"cursor:not-allowed;opacity:0.4\"" +
                    "readonly [disabled]=\"true\" src=\"assets/images/images.png\" id=\"TBN\" name=\"TBN\"  />" +
                    "<span  class=\"tip-content text-center\" style=\"z-index: 1;cursor:not-allowed;width:70%;\">" +
                    "Approve artifacts is not available </span></div>";
                }
                if (triggerPipeline) {
                this.IdpdataService.pipelineData[i].trigger = "<div class=\"text-center field-tip\"><input TYPE=\"image\"" +
                    "src=\"assets/images/build_now.png\" id=\"TBN\" name=\"TBN\"  /></div>";
                } else {
                    this.IdpdataService.pipelineData[i].trigger = "<div class=\"text-center field-tip\" style=\"cursor:not-allowed\" >" +
                    "<input TYPE=\"image\" style=\"cursor:not-allowed;opacity:0.4\" readonly [disabled]=\"true\"" +
                    "src=\"assets/images/build_now.png\" id=\"TBN\" name=\"TBN\"  />" +
                        "<span  class=\"tip-content text-center\" style=\"z-index: 1;cursor:not-allowed;width:70%;\">" +
                        "Trigger is not allowed for this user.</span></div>";
                }


        this.IdpdataService.pipelineData[i].schedule = "<div class=\"text-center \"><input TYPE=\"image\"" +
        "src=\"assets/images/schedule.png\" id=\"TBN\" name=\"TBN\"  /></div>";
                }
                this.data = this.IdpdataService.pipelineData;
                this.length = this.data.length;
                this.onChangeTable(this.config);
            }
            } else {
                alert("No pipelines available");
            }
            }
        } catch (e) {
            alert("failed to get the pipeline Details");
            console.log(e);
        }
        });
  }
  noAccessCheck() {
      if (this.IdpdataService.noAccess) {
          return true;
      } else {
          return false;
      }
  }
  accessCheck() {
  if (!this.IdpdataService.noAccess) {
      return true;
  } else {
      return false;
  }
  }
}
