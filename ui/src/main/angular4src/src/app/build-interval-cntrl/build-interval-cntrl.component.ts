import { Component, OnInit, ViewChild } from "@angular/core";
import { TranslateService } from "ng2-translate";
import { IdpService } from "../idp-service.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router } from "@angular/router";
import { IdpdataService } from "../idpdata.service";
import { DataTable, DataTableResource } from "angular-2-data-table";
import { ActivatedRoute } from "@angular/router";
import { IDPEncryption } from "../idpencryption.service";
declare var jQuery: any;
@Component({
  selector: "app-build-interval-cntrl",
  templateUrl: "./build-interval-cntrl.component.html",
  styleUrls: ["./build-interval-cntrl.component.css"]
})
export class BuildIntervalCntrlComponent implements OnInit {
  intervalOptions: any = ["Day", "Week", "Month"];
  hourOptions: any = ["00", "01", "02", "03", "04", "05", "06", "07",
  "08", "09", "10", "11", "12", "13", "14", "15", "16", "17",
  "18", "19", "20", "21", "22", "23", "24"];
  minuteOptions: any = ["00", "10", "20", "30", "40", "50"];
  weeklyOptions: any = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
  dateOptions: any = ["1", "2", "3", "4", "5", "6", "7", "8",
  "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
  "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"];
  interval: any;
  statusCheck: any = ["", ""];
  dropdownListTest: any = [];
  dropdownListDeploy: any = [];
  initialNotZero = false;
@ViewChild("modalforTotalSubmit") submitButton;
    ngOnInit() {
  }

  constructor(
  public IdpdataService: IdpdataService,
  private IdprestapiService: IdprestapiService,
  private router: Router,
  private idpencryption: IDPEncryption,
  public IdpService: IdpService) {
    const data = {
        "applicationName": this.IdpdataService.triggerJobData.applicationName,
        "pipelineName": this.IdpdataService.pipelineName,
        "userName": this.IdpdataService.idpUserName
    };
    this.IdpdataService.loading = true;
    this.IdpdataService.buildIntervalData = [];
   this.IdprestapiService.getPipelineDetails(data)
        .then(response => {
        console.log(new Date().toUTCString(), "Pipeline details retrieved");
        try {
            const responseData = this.idpencryption.decryptAES(response.json().resource);
            let resp = JSON.parse(responseData);
            resp = this.idpencryption.doubleEncryptPassword(resp.pipelineJson);
          this.IdpdataService.buildIntervalData = resp.basicInfo.customTriggerInterval.interval;
          if( this.IdpdataService.buildIntervalData!=null &&  this.IdpdataService.buildIntervalData.length!==0){
            this.initialNotZero  = true;
          }
        } catch (e) {
            console.log("Failed to get the Build Interval Schedule Details");
            console.log(e);
        }
        });
      this.IdpdataService.loading = false;
  }
  submit() {
    const x = this.checkDetailsFilled();
    if (x) {
        this.submitButton.nativeElement.click();
    } else {
        alert("Details not added for all jobs!!");
    }
    }
    totalSubmit() {
    this.IdpdataService.loading = true;
    for (const i in this.IdpdataService.buildIntervalData) {
        this.IdpdataService.buildIntervalData[i].details.schedule = true;
    }
     const data = {"interval": this.IdpdataService.buildIntervalData};
        this.IdprestapiService.buildIntervalTriggerJobs(data,this.IdpdataService.triggerJobData.applicationName,this.IdpdataService.pipelineName,this.IdpdataService.idpUserName).then(response => {
            try {
            if (response) {
            const err = response.json().errorMessage;
            if (err === null && response.json().resource.toLowerCase() === "success") {
                this.IdpdataService.loading = false;
                alert("Successfully submitted details!!");
              this.IdpdataService.schedulePage = false;
              setTimeout(() => { location.reload(); }, 5);
            } else {
                this.IdpdataService.loading = false;
              alert("Error! Couldn\"t submit. Please try again.");
            }
            }
        } catch (e) {
            console.log(e);
            alert("Failed while submitting ");
            }
        });
    }

  redirectTo() {

  }

setIndex(i) {
    this.IdpdataService.index = i;
}
  checkDetailsSingle(i ) {
      console.log(this.IdpdataService.buildIntervalData);
      if (this.IdpdataService.buildIntervalData[i] !== undefined) {
      if (Object.getOwnPropertyNames(this.IdpdataService.buildIntervalData[i].details).length === 0) {
          return false;
    } else {
          return true;
      }
      }
  }
  checkDetailsFilled() {
     if( !this.initialNotZero && this.IdpdataService.buildIntervalData!==undefined && this.IdpdataService.buildIntervalData!=null && this.IdpdataService.buildIntervalData.length === 0){
        console.log('len : '+this.IdpdataService.buildIntervalData.length);
        alert('No job is scheduled !')
        return false;
     }
      for (const interval of this.IdpdataService.buildIntervalData) {
          console.log(interval.details);
          if (Object.getOwnPropertyNames(interval.details).length === 0) {
              return false;
          }
      }
      return true;
  }
  deleteDetails(i) {
      const x = confirm("Are you sure you want to remove these details?");
      if (x) {
      this.IdpdataService.buildIntervalData[i].details = {};
      }
  }
  deleteBuildInterval(i) {
      const x = confirm("Are you sure you want to remove these details?");
      if (x) {
      this.IdpdataService.buildIntervalData.splice(i, 1);
      }
  }
  addJob() {
    this.IdpdataService.buildIntervalData.push({"type": "", "minute": "", "time": "", "details": {}});

  }
  closeModal(id) {
      jQuery("#" + id).modal("hide");
  }
}
