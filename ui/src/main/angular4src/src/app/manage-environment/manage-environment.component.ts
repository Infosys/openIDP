/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit, NgModule } from "@angular/core";
import { Router } from "@angular/router";
import { IdprestapiService } from "../idprestapi.service";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpModule, Http } from "@angular/http";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule } from "@angular/forms";
import { DateTimePickerModule } from "ng-pick-datetime";
import { CookieService } from "ngx-cookie";

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
    ReactiveFormsModule,
    FormsModule,
    DateTimePickerModule,
  ],
})
@Component({
  selector: "app-manage-environment",
  templateUrl: "./manage-environment.component.html",
  styleUrls: ["./manage-environment.component.css"],
})
export class ManageEnvironmentComponent implements OnInit {
  pipelineNames: any;
  appNames: any;
  intervalOptions: any = ["Day", "Week", "Month"];
  weeklyOptions: any = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
  dateOptions: any = [
    "01",
    "02",
    "03",
    "04",
    "05",
    "06",
    "07",
    "08",
    "09",
    "10",
    "11",
    "12",
    "13",
    "14",
    "15",
    "16",
    "17",
    "18",
    "19",
    "20",
    "21",
    "22",
    "23",
    "24",
    "25",
    "26",
    "27",
    "28",
    "29",
    "30",
    "31",
  ];
  envPlannigData: any;
  envData: any = [];
  temp: any;
  envSlots: any = [];
  timeZone: any;
  slotsCardCollapseStatus: Array<any> = [];
  public constructor(
    private IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private _cookieService: CookieService,
    private router: Router
  ) {
    this.envPlannigData = {
      applicationName: "",
      envNames: [],
      env: "",
      startTime: "",
      endTime: "",
      releaseNo: "",
      releaseNumbers: "",
      releaseNumber: "",
      type: "",
      week: [],
      date: [],
    };
    this.callforRest();
  }

  callforRest() {
    this.IdprestapiService.getData().then((response) => {
      try {
        if (response) {
          this.IdpdataService.devServerURL = response.json().idpresturl;
          this.IdpdataService.IDPDashboardURL = response.json().idpdashboardurl;
          this.IdpdataService.IDPLink = response.json().IDPLink;
          this.IdpdataService.geUrl = response.json().geUrl;
          this.IdpdataService.geFlag = response.json().geFlag;
          this.IdpdataService.serverUrl = response.json().tfsServerUrl;
          this.IdpdataService.uName = response.json().uName;
          this.IdpdataService.pass = response.json().pass;

          if (this._cookieService.get("access_token")) {
            //console.log("details");
          }
          this.getApplicationName();
          this.getTimeZone();
        }
      } catch (e) {
        alert("failed to get properties details");
        //console.log(e);
      }
    });
  }

  ngOnInit() {}

  addJob() {
    this.envData.push({
      startTime: "",
      endTime: "",
      type: "",
      week: [],
      date: [],
    });
  }

  /* To fetch environment */
  getEnvSlots() {
    this.IdprestapiService.getEnvSlots(
      this.envPlannigData.applicationName,
      this.envPlannigData.env
    ).then((response) => {
      if (response) {
        if (response.status === 200) {
          this.envSlots = JSON.parse(response.json().resource).slot;
          this.temp = this.envData.week;
        } else if (response.text().status === "No previous slots") {
          // console.log("Application was not previously planned");
        } else {
          alert("failed to get the existing slots");
        }
      }
    });
  }

  getTimeZone() {
    this.IdprestapiService.getTimeZone().then((response) => {
      if (response) {
        this.timeZone = JSON.parse(response.text()).status;
      } else {
        alert("could not get time zone");
      }
    });
  }

  getExistingSlots() {
    this.IdprestapiService.getExistingSlot(
      this.envPlannigData.applicationName,
      this.envPlannigData.releaseNumber,
      this.envPlannigData.env
    ).then((response) => {
      if (response) {
        if (response.status === 200) {
          this.envData = JSON.parse(response.json().resource).slot;
          this.temp = this.envData.week;
        } else if (response.text().status === "No previous slots") {
          // console.log("Application was not previously planned");
        } else {
          alert("failed to get the existing slots");
        }
      }
    });
  }

  deleteBuildInterval(i) {
    const x = confirm("Are you sure you want to remove these details?");
    if (x) {
      this.envData.splice(i, 1);
    }
  }

  /* Fetches Application Details */
  getApplicationName() {
    this.IdprestapiService.getApplicationNameForReleaseManager(
      this.IdpdataService.idpUserName
    ).then((response) => {
      if (response) {
        const appDetails = JSON.parse(response.json().resource);
        //console.log("appdetails" + JSON.stringify(appDetails));
        this.appNames = appDetails.names;
      }
    });
    const currentTime = new Date();
    const hours = currentTime.getHours();
    const minutes = currentTime.getMinutes();
  }

  /* Fetches Pipeline Details */
  getpipelines() {
    this.IdprestapiService.getPipelineEnv(
      this.envPlannigData.applicationName,
      this.envPlannigData.releaseNumber
    ).then((response) => {
      if (response) {
        if (response.status === 200) {
          this.pipelineNames = JSON.parse(response.json().resource).envNames;
        }
      } else {
        alert("Failed to get the Pipelines Affected");
      }
    });
  }

  updatePlan() {
    const x = confirm("Are you sure you want to add this plan?");
    if (x) {
      // console.log("whole object rinting" + this.envData);
      if (this.envPlannigData.releaseNumber !== "") {
        const send = {
          envData: this.envData,
        };
        // console.log("printing send " + send);
        this.IdprestapiService.updatePlan(
          this.envPlannigData.releaseNumber,
          this.envPlannigData.applicationName,
          this.envPlannigData.env,
          send
        ).then((response) => {
          // console.log("logging response " + response.text());
          if (response) {
            // console.log(
            //   "logging json in update plan " +
            //     JSON.parse(response.json().resource)
            // );
            if (response.status === 200) {
              alert("Plan for the Environment added succesfully.");
              this.envPlannigData.env = "";
              this.envSlots = [];
              this.envPlannigData.releaseNumber = "";
              this.pipelineNames = "";
              this.IdprestapiService.getUserName().then((response) => {
                if (response) {
                  const userDetails = JSON.parse(response.json().resource);
                  const username = userDetails.user_id;
                  const table = this.maketable();
                  const envOwner = {
                    applicationName: this.envPlannigData.applicationName,
                    envName: this.envPlannigData.env,
                    mailBody:
                      "Dear " +
                      username +
                      "," +
                      "<br> <br>Environment has been planned for <br> <br> &nbsp; &nbsp; &nbsp; &nbsp; Application Name: " +
                      this.envPlannigData.applicationName +
                      " <br> &nbsp; &nbsp; &nbsp; &nbsp; Release Number: " +
                      this.envPlannigData.releaseNumber +
                      " <br> &nbsp; &nbsp; &nbsp; &nbsp; Environment Name: " +
                      this.envPlannigData.env +
                      "<br><br>" +
                      table +
                      "<br><br> Regards, <br> IDP Team",
                    userName: username,
                  };
                  this.IdprestapiService.emailToEnvOwner(envOwner).then(
                    (response) => {
                      if (response) {
                        if (response.status === 200) {
                          // console.log("Email Sent Succesfully");
                        }
                      }
                    }
                  );
                }
              });
            } else {
              alert("Plan could not be added");
            }
          }
        });
      } else {
        alert("Please feel the required fields");
      }
    }
  }

  maketable() {
    if (this.envData.length !== 0) {
      const style =
        "<html><head><style> table {font-family: arial," +
        "sans-serif;border-collapse: collapse; width: 100%;}td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}" +
        "tr:nth-child(even){background-color: grey;}</style></head>";
      let table =
        style +
        "<table><tr><th>Slot Type</th><th>Start Time</th><th>End Time</th></tr>";
      for (const i of this.envData) {
        table =
          table +
          "<tr><td>" +
          i.type +
          "</td><td>" +
          i.startTime +
          "</td><td>" +
          i.endTime +
          "</td></tr>";
      }
      table = table + "</table>";
      return table;
    } else {
      const table =
        "&nbsp; &nbsp; &nbsp; &nbsp; You can deploy in this environment 24*7.";
      return table;
    }
  }

  getReleaseNumber() {
    this.IdprestapiService.getReleaseNumber(
      this.envPlannigData.applicationName
    ).then((response) => {
      if (response) {
        if (response.json().resource !== "No Release for this application") {
          this.envPlannigData.releaseNumbers = JSON.parse(
            response.json().resource
          ).releaseNumbers;
          const temp = [];
          for (const i of this.envPlannigData.releaseNumbers) {
            if (temp.indexOf(i) === -1) {
              temp.push(i);
            }
          }
          this.envPlannigData.releaseNumbers = temp;
        } else {
          alert("No Active Release for this Application");
        }
      } else {
        alert("No response for this application, something went wrong");
      }
    });
  }

  /* Fetches Environment names */
  getEnvironmentNames() {
    this.IdprestapiService.getEnvironmentNames(
      this.envPlannigData.applicationName
    ).then((response) => {
      if (response) {
        if (response.json().resource !== null) {
          this.envPlannigData.envNames = JSON.parse(
            response.json().resource
          ).envNames;
        } else {
          alert(
            "There are no Environments to be planned for this application."
          );
        }
      } else {
        alert("No response for this application, something went wrong");
      }
    });
  }
}
