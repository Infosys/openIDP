import { Component, OnInit, ChangeDetectorRef } from "@angular/core";
import { DataApiService } from "../../shared/data-api.service";
import { RestApiService } from "../../shared/rest-api.service";
import { PaginationInstance } from "ngx-pagination";
import "chartjs-plugin-streaming";
import { DatePipe } from "@angular/common";
import { ChartsModule } from "ng2-charts";

import { BaseChartDirective } from "ng2-charts";

@Component({
  selector: "app-insights",
  templateUrl: "./insights.component.html",
  styleUrls: ["./insights.component.scss"],
})
export class InsightsComponent implements OnInit {
  appList = [];
  demoList = [];
  timeMinute = 0;
  timeHour = 0;
  pipelineList = { ShoppingCart: ["ShoppingCart"] };
  insightsApplicationName = "";
  insightsPipelineName = "";
  anomalyDataResponse = false;
  backTimeHour = 0;
  backTimeMinute = 0;
  appName = "";
  activatedTabs = {};
  applicationList = [];
  exceptionCount = {
    totalException: 0,
    newException: 0,
    oldException: 0,
  };
  exceptionRange = {
    applicationName: "",
    sourceException: 0,
    targetException: 0,
  };

  public p = 1;
  public filter = "";
  public maxSize = 7;
  public directionLinks = true;
  public config: PaginationInstance = {
    itemsPerPage: 5,
    currentPage: 1,
  };
  public labels: any = {
    previousLabel: "Previous",
    nextLabel: "Next",
    screenReaderPaginationLabel: "Pagination",
    screenReaderPageLabel: "page",
    screenReaderCurrentLabel: "You're                 on page",
  };
  anamolyException = [];
  ExceptionHighpref = [];
  Exceptionlowpref = [];

  chartColors = {
    red: "rgb(255, 99, 132)",
    orange: "rgb(255, 159, 64)",
    yellow: "rgb(255, 205, 86)",
    green: "rgb(75, 192, 192)",
    blue: "rgb(54, 162, 235)",
    purple: "rgb(153, 102, 255)",
    grey: "rgb(201, 203, 207)",
  };
  datasets = [
    {
      label: "Today",
      backgroundColor: this.chartColors.red,
      borderColor: this.chartColors.red,
      fill: false,
      lineTension: 0,
      borderDash: [8, 4],
      type: "line",
      data: [],
      pointRadius: 5,
      pointHoverRadius: 15,
      pointHitRadius: 10,
      pointHoverBackgroundColor: this.chartColors.red,
    },
    {
      label: "Yesterday",
      backgroundColor: this.chartColors.blue,
      borderColor: this.chartColors.blue,
      fill: false,
      cubicInterpolationMode: "monotone",
      data: [],
    },
  ];

  options = {
    animation: {
      duration: 0, // general animation time
    },
    responsiveAnimationDuration: 0, // animation duration after a resize
    scales: {
      xAxes: [
        {
          type: "realtime", // x axis will auto-scroll from right to left
          scaleLabel: {
            display: true,
            labelString: "Time",
          },
        },
      ],
      yAxes: [
        {
          type: "linear",
          display: true,
          scaleLabel: {
            display: true,
            labelString: "Value",
          },
        },
      ],
    },
    tooltips: {
      mode: "nearest",
      intersect: false,
    },
    hover: {
      mode: "nearest",
      intersect: false,
      animationDuration: 0, // duration of animations when hovering an item
    },
    plugins: {
      streaming: {
        // enabled by default
        duration: 180000, // data in the past 20000 ms will be displayed
        refresh: 10000, // onRefresh callback will be called every 1000 ms
        delay: 10000, // delay of 1000 ms, so upcoming values are known before plotting a line
        frameRate: 20, // chart is drawn n times every second
        pause: false, // chart is not paused
      },
    },
  };
  loginData = {
    username: "",
  };
  checkinDetails = [];

  constructor(
    private restService: RestApiService,
    private dataService: DataApiService,
    private changeDetectorRef: ChangeDetectorRef,
    public datepipe: DatePipe
  ) {}

  ngOnInit() {
    this.dataService.anamolyException = [];
    this.anamolyException = [];
    this.activatedTabs = this.dataService.activatedTabs;
    //this.getUserAccessApplication();
    this.getApplicationAndPipelineList();

    // this.getCheckinDetails(this.loginData.username);
    if (this.appName) {
      this.getExceptionCountDetails();
      this.getExceptionDetails();
    }

    setInterval(() => {
      this.getExceptionrangeDetails();
    }, 10000);
  }

  onPageChange(number: number) {
    this.config.currentPage = number;
  }

  setHour() {
    this.backTimeHour = this.timeHour;
    //console.log(this.backTimeHour);
    //console.log("consoel.g" + this.timeHour);
  }
  setMinute() {
    this.backTimeMinute = this.timeMinute;
  }
  getExceptionrangeDetails() {
    this.appName =
      this.insightsApplicationName.replace(/[_-]/g, "") +
      this.insightsPipelineName.replace(/[_-]/g, "");
    if (
      this.insightsApplicationName === "" &&
      this.insightsPipelineName === ""
    ) {
      return;
    }
    //console.log("app name " + this.appName);
    let currentdate = new Date();
    //console.log("horu" + this.backTimeHour);

    currentdate.setMinutes(currentdate.getMinutes() - 5 - this.backTimeMinute);
    // currentdate.setHours(currentdate.getHours()-this.backTimeHour);

    let currdate = this.datepipe.transform(currentdate, "yyyy-MM-dd HH:mm:ss");

    let ydaydate = new Date();
    ydaydate.setMinutes(currentdate.getMinutes());
    // ydaydate.setHours(currentdate.getHours());
    ydaydate.setDate(currentdate.getDate() - 1);

    let ydaydat = this.datepipe.transform(ydaydate, "yyyy-MM-dd HH:mm:ss");

    const params = {
      applicationName: this.appName,
      sourcePeriod: currdate,
      targetPeriod: ydaydat,
    };

    this.restService.getExceptionrangeDetails(params).then((res) => {
      if (res) {
        if (res["status"] === 200) {
          this.exceptionRange = JSON.parse(res.text());

          this.datasets[0].data.push({
            x: Date.now(),
            y: this.exceptionRange.sourceException,
          });
          this.datasets[1].data.push({
            x: Date.now(),
            y: this.exceptionRange.targetException,
          });
        }
      }
    });
  }

  getApplicationAndPipelineList() {
    this.restService.getApplicationListFromIdp().then((res) => {
      if (res) {
        if (res["status"] === 200) {
          this.pipelineList = JSON.parse(res.json()["resource"]);
          for (const key in this.pipelineList) {
            var arr = this.pipelineList[key];
            arr = arr.sort();
            var newarr = [arr[0]];
            for (var i = 1; i < arr.length; i++) {
              if (arr[i] != arr[i - 1]) newarr.push(arr[i]);
            }
            this.pipelineList[key] = newarr;
            this.applicationList.push(key);
          }
        }
      }
    });
  }
  getExceptionCountDetails() {
    this.appName =
      this.insightsApplicationName.replace(/[_-]/g, "") +
      this.insightsPipelineName.replace(/[_-]/g, "");

    const params = {
      applicationName: this.appName,
      period: "Last1month",
    };
    //console.log(params);
    //We get the data which has exception name,class name etc
    this.restService.getExceptionCountDetails(params).then((res) => {
      if (res) {
        if (res["status"] === 200) {
          this.exceptionCount = JSON.parse(res.text());
          this.dataService.exceptionCount = this.exceptionCount;
          this.changeDetectorRef.detectChanges();
        }
      }
    });
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
    //console.log(this.demoList);
    this.restService.getApplist(this.demoList).then((res) => {
      //console.log(res);
      if (res) {
        if (res["status"] === 200) {
          //console.log(res.json());
          this.applicationList = res.json();
        }
      }
    });
  }

  getExceptionDetails() {
    this.anomalyDataResponse = false;
    this.anamolyException = [];
    this.appName =
      this.insightsApplicationName.replace(/[_-]/g, "") +
      this.insightsPipelineName.replace(/[_-]/g, "");
    let exceptionDetails = [];
    //console.log("inside 283");
    const params = {
      applicationName: this.appName,
      period: "Last1month",
    };
    let pref = 0;
    if (this.appName) {
      this.dataService.appName = this.appName;
      this.getExceptionCountDetails();
      this.restService.getExceptionDetails(params).then((res) => {
        //console.log("inside this" + res);

        if (res) {
          if (res["status"] === 200) {
            exceptionDetails = JSON.parse(res.text())["resource"];
            if (exceptionDetails) {
              if (exceptionDetails.length > 0) {
                this.anamolyException = exceptionDetails;
              } else {
                this.anamolyException = [];
              }
            } else {
              this.anamolyException = [];
            }
            if (exceptionDetails === null) {
              this.anamolyException = [];
            }

            for (let exceptionObj of this.anamolyException) {
              pref = 0;
              if (exceptionObj[5] == -1) {
                exceptionObj.push(exceptionObj[4] + "x Increase");
              } else {
                exceptionObj.push(exceptionObj[4] + "x Decrease");
              }

              for (let val of this.checkinDetails) {
                //console.log("classname" + exceptionObj[1]);
                if (
                  exceptionObj[1].toLowerCase() === val.toLowerCase() &&
                  pref === 0
                ) {
                  this.ExceptionHighpref.push(exceptionObj);
                  pref = 1;
                  break;
                }
              }
              if (pref === 0) {
                this.Exceptionlowpref.push(exceptionObj);
                //console.log("Does not match");
              }
            }
            // this.anamolyException = this.ExceptionHighpref.concat(this.Exceptionlowpref);
            //  this.dataService.anamolyException = this.anamolyException;
            //console.log("anamolyException:" + this.anamolyException);
            this.changeDetectorRef.detectChanges();
          }
        } else {
          this.anamolyException = [];
        }
        this.anomalyDataResponse = true;
      });
    }
    //console.log(this.anamolyException);
  }

  getCheckinDetails(checkin) {
    checkin = this.loginData.username;
    checkin = checkin + "@domain.com";
    ////console.log('see now' + checkin)
    const params = {
      committername: checkin,
    };

    this.restService.getCommitterCheckinDetails(params).then((res) => {
      if (res) {
        if (res["status"] === 200) {
          this.checkinDetails = res.json().res;
          //console.log("success checkins are:");
          //console.log(this.checkinDetails);
        }
      }
    });
  }
}
