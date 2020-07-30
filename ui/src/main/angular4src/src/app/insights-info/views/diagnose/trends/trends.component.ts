import { Component, OnInit, Input, ChangeDetectorRef } from "@angular/core";
import { PaginationInstance } from "ngx-pagination";
import { DomSanitizer, SafeResourceUrl } from "@angular/platform-browser";
import { DataApiService } from "../../../shared/data-api.service";
import { RestApiService } from "../../../shared/rest-api.service";
import { ResponseOptions } from "@angular/http/src/base_response_options";
import "chartjs-plugin-streaming";
import { ChartsModule } from "ng2-charts";
import { DatePipe } from "@angular/common";

@Component({
  selector: "app-trends",
  templateUrl: "./trends.component.html",
  styles: [
    `
      .table tr.active td {
        background-color: #dummy56 !important;
        color: white;
      }
    `,
  ],
})
export class TrendsComponent implements OnInit {
  selectedRow;
  notifications = [];
  selected_value = "";
  id: any;
  noti: any;
  activatedTabs = {};
  anamolyException = [];
  containerName = "";
  instanceName = "";
  period = "";
  release1 = "";
  release2 = "";
  testing = "";
  instanceList = [];
  selectedRange = {
    x: "",
    y: "",
  };
  elkIndex = "";
  elkHost = "";
  elkPort: any;

  periodList = [
    "Last30Min",
    "Last1Hr",
    "Last5Hr",
    "Last1Day",
    "Last1Week",
    "Last1Month",
  ];
  lineData = {
    data: [],
    label: "",
  };
  cpulineData = {
    data: [],
    label: "",
  };

  @Input() trendsAppName: string;
  @Input() containerList = [];

  // Modal
  public myModal;
  // lineChart
  public lineChartData: Array<any> = [
    // {data: [65, 59, 80, 81, 56, 55, 40], label: 'IDPLINV01'}
  ];
  public lineCPUChardData: Array<any> = [];
  public lineChartLabels: Array<any> = []; // = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
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
      label: "Memory Usage(in MBs)",
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
  ];
  CPUdatasets = [
    {
      label: "CPU Usage(in %)",
      backgroundColor: this.chartColors.blue,
      borderColor: this.chartColors.blue,
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
  ];
  options = {
    // responsive: true,
    // maintainAspectRatio: false,
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
        duration: 360000, // data in the past 20000 ms will be displayed
        refresh: 10000, // onRefresh callback will be called every 1000 ms
        delay: 10000, // delay of 1000 ms, so upcoming values are known before plotting a line
        frameRate: 20, // chart is drawn n times every second
        pause: false, // chart is not paused
      },
    },
  };

  public config: PaginationInstance = {
    // id: 'advanced',
    itemsPerPage: 5,
    currentPage: 1,
  };
  public labels: any = {
    previousLabel: "Previous",
    nextLabel: "Next",
    screenReaderPaginationLabel: "Pagination",
    screenReaderPageLabel: "page",
    screenReaderCurrentLabel: `You're on page`,
  };
  public lineChartColours: Array<any> = [
    {
      // grey
      backgroundColor: "rgba(148,159,177,0.2)",
      borderColor: "rgba(148,159,177,1)",
      pointBackgroundColor: "rgba(148,159,177,1)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(148,159,177,0.8)",
      pointRadius: 0,
      fill: false,
    },
  ];
  public lineChartLegend = true;
  public lineChartType = "line";

  constructor(
    private dataService: DataApiService,
    private restService: RestApiService,
    private changeDetectorRef: ChangeDetectorRef,
    public datepipe: DatePipe
  ) {}

  ngOnInit() {
    this.activatedTabs = this.dataService.activatedTabs;
    this.getELKdetails();

    this.id = setInterval(() => {
      this.getServerUtilizationStats("30Sec");
    }, 3000);
    this.noti = setInterval(() => {
      this.getNotifications();
    }, 200000);
    this.getNotifications();
  }

  ngOnDestroy() {
    if (this.id) {
      clearInterval(this.id);
    }
    if (this.noti) {
      clearInterval(this.noti);
    }
  }
  onPageChange(number: number) {
    this.config.currentPage = number;
  }

  getServerUtilizationStats(periodtime) {
    this.clearStats();

    const params = {
      containerName: this.containerName,
      //"containerName" : this.diagnosecomp.selected_value,
      // "indexName": "idplinv04stats", //this is the name it had earlier
      indexName: this.elkIndex,
      instanceName: this.instanceName,
      period: periodtime,
      elkPort: this.elkPort,
      elkserver: this.elkHost,
    };

    let responseData: any;

    this.restService.getServerUtilizationStats(params).then((res) => {
      if (res) {
        if (res["status"] === 200) {
          responseData = JSON.parse(res.json()["resource"]);

          this.dataService.serverUtilization = responseData;
          //console.log(this.dataService.serverUtilization);
          this.lineData.label = "MEMORY USAGE";
          for (
            let i = 0;
            i <
            this.dataService.serverUtilization.memoryData.processingTime.length;
            i++
          ) {
            this.datasets[0].data.push({
              x: this.dataService.serverUtilization.memoryData.processingTime[
                i
              ],
              y: this.dataService.serverUtilization.memoryData.memoryUsage[i],
            });
            this.CPUdatasets[0].data.push({
              x: this.dataService.serverUtilization.memoryData.processingTime[
                i
              ],
              y: this.dataService.serverUtilization.cpuData.cpuAverage[i],
            });
          }

          this.cpulineData.label = "CPU USAGE";
          this.changeDetectorRef.detectChanges();
        }
      }
    });
    //this.getCriticalLogs();
  }
  setClickedRow(index) {
    this.selectedRow = index;
    let name, name1, time;
    name = this.notifications[index];
    name1 = name.split(":")[0];
    time = name.split(",")[1];
    this.selected_value = name1;
    let ydaydate = new Date(time);
    let selectdate = new Date(time);
    //console.log(ydaydate);
    ydaydate.setMinutes(ydaydate.getMinutes() - 60);
    //console.log(ydaydate);
    selectdate.setMinutes(selectdate.getMinutes() + 60);
    let ydaydat = this.datepipe.transform(ydaydate, "yyyy-MM-ddTHH:mm:ssZ");
    let selecteddate = this.datepipe.transform(
      selectdate,
      "yyyy-MM-ddTHH:mm:ssZ"
    );
    this.getCriticalLogs(selecteddate, ydaydat);
  }

  getELKdetails() {
    let responseData;
    this.notifications = [];
    this.selectedRow = -1;
    //console.log("getELKDetails initialized");
    // this.spinner.show();

    this.restService.getELKdetails("ShoppingCart").then((res) => {
      if (res) {
        if (res["status"] === 200) {
          responseData = res.json();
          for (let key in responseData) {
            //console.log("key: " + key + ",  value: " + responseData[key]);
          }
          //console.log("ElkDetails: " + responseData["ElkIndex"]);
          this.elkIndex = responseData["ElkIndex"];
          this.elkHost = responseData["elkHost"];
          this.elkPort = responseData["ElkPort"];
        }
      }
    });

    // this.spinner.hide();
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
              responseData[i][1] + ": CPU/Mem spike at " + responseData[i][2]
            );
          }
        }
      }
    });

    // this.spinner.hide();
  }
  removeData() {
    this.datasets = [
      {
        label: "Memory Usage(in MBs)",
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
    ];
    this.CPUdatasets = [
      {
        label: "CPU Usage(in %)",
        backgroundColor: this.chartColors.blue,
        borderColor: this.chartColors.blue,
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
    ];
  }

  public chartClicked(e: any): void {
    ////console.log(e);
    if (e.active.length > 0) {
      const chart = e.active[0]._chart;
      const activePoints = chart.getElementAtEvent(e.event);
      if (activePoints.length > 0) {
        // get the internal index of slice in pie chart
        const clickedElementIndex = activePoints[0]._index;
        const label = chart.data.labels[clickedElementIndex];

        // get value by index
        const value = chart.data.datasets[0].data[clickedElementIndex];
        //console.log("x value " + value.x);
        //console.log("y value " + value.y);
        let ydaydate = new Date(value.x);
        ydaydate.setMinutes(ydaydate.getMinutes() - 60);
        let ydaydat = this.datepipe.transform(ydaydate, "yyyy-MM-ddTHH:mm:ssZ");
        let selecteddate = this.datepipe.transform(
          new Date(value.x),
          "yyyy-MM-ddTHH:mm:ssZ"
        );
        this.getCriticalLogs(selecteddate, ydaydat);
      }
    }
  }
  clearStats() {
    this.lineCPUChardData = [];
    this.lineChartData = [];
  }

  changeContainerName() {
    //console.log(" App Name : " + this.trendsAppName);

    if (this.containerName == "") {
      this.instanceList = [];
      this.instanceName = "";
      this.period = "";
    }
    this.datasets[0].data = [];
    this.CPUdatasets[0].data = [];
    this.changeDetectorRef.detectChanges();
    //let localInstanceList : any;
    for (let i = 0; i < this.containerList.length; i++) {
      if (this.containerList[i]["containerName"] === this.containerName) {
        this.instanceList = this.containerList[i]["instanceList"];
      }
    }
    //console.log(this.instanceList);
    if (this.instanceList.length > 0) {
      this.instanceName = this.instanceList[0]["instanceNumber"];
      this.period = this.periodList[0];
    }
    this.removeData();
    this.getServerUtilizationStats(this.period);
  }
  changeContainerName1() {
    this.containerName = this.selected_value;

    //console.log(" App Name : " + this.trendsAppName);

    if (this.containerName == "") {
      this.instanceList = [];
      this.instanceName = "";
      this.period = "";
    }
    this.datasets[0].data = [];
    this.CPUdatasets[0].data = [];
    this.changeDetectorRef.detectChanges();
    //let localInstanceList : any;
    for (let i = 0; i < this.containerList.length; i++) {
      if (this.containerList[i]["containerName"] === this.containerName) {
        this.instanceList = this.containerList[i]["instanceList"];
      }
    }
    //console.log(this.instanceList);
    if (this.instanceList.length > 0) {
      this.instanceName = this.instanceList[0]["instanceNumber"];
      this.period = this.periodList[0];
    }
    this.removeData();
    this.getServerUtilizationStats(this.period);
  }

  getCriticalLogs(selecteddate, ydaydat) {
    let exceptionDetails = [];
    const params = {
      AppName: this.trendsAppName,
      StartTime: ydaydat,
      EndTime: selecteddate,
      ContainerName: this.containerName,
    };
    if (this.trendsAppName) {
      this.dataService.appName = this.trendsAppName;
      //console.log("getCriticalDetails invoking");
      this.restService.getCriticalDetails(params).then((res) => {
        if (res) {
          // if (true) {
          exceptionDetails = JSON.parse(res["_body"]);

          if (exceptionDetails) {
            if (exceptionDetails.length > 0) {
              //console.log(exceptionDetails);
              this.anamolyException = exceptionDetails;
            } else {
              this.anamolyException = [];
            }
          } else {
            this.anamolyException = [];
          }
          this.dataService.anamolyException = this.anamolyException;
          //console.log(this.anamolyException);
          this.changeDetectorRef.detectChanges();
          // }
        }
      });
    }
  }
}
