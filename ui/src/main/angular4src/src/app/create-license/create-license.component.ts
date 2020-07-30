/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import { IdprestapiService } from "../idprestapi.service";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { SubscriptionService } from "../subscription.service";

@Component({
  selector: "app-create-license",
  templateUrl: "./create-license.component.html",
  styleUrls: ["./create-license.component.css"],
})
export class CreateLicenseComponent implements OnInit {
  allSubscriptions: any[] = new Array();
  haveLicense: boolean;
  licenseStatus: string;
  data: any;
  availableServiceList: any = [
    { id: 1, itemName: "CI" },
    { id: 2, itemName: "CD" },
    { id: 3, itemName: "CT" },
  ];
  availableServices: any = ["CI", "CD", "CT"];
  selectedServiceList: any = [];
  selectedServices: any = [];
  licenseKey: string;

  constructor(
    public IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdprestapiService: IdprestapiService,
    private subscriptionService: SubscriptionService
  ) {
    if (this.data === undefined) {
      this.data = {
        licenseType: "",
        emailid: "",
        expirydate: "",
        organization: IdpdataService.organization,
        serviceList: [],
        signature: "string",
      };
    }
  }

  ngOnInit() {
    this.initialize();
  }
  initialize() {
    this.subscriptionService.getAllSubscriptions().then((response) => {
      const data = JSON.parse(response.json().status).licenses;
      this.allSubscriptions = new Array();
      data.forEach((value, index) => {
        if (value != null) {
          this.allSubscriptions.push(value);
        }
      });
    });
  }

  userHaveLicences() {
    if (this.allSubscriptions.length === 0) {
      this.haveLicense = false;
    } else {
      this.haveLicense = true;
    }
    return this.haveLicense;
  }

  getstatus(expirydate: string) {
    const oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
    const checkDate: Date = new Date(expirydate);
    const currentDate: Date = new Date();
    const diffDays = Math.round(
      Math.abs((checkDate.getTime() - currentDate.getTime()) / oneDay)
    );
    if (diffDays >= 0) {
      if (diffDays <= 1) {
        this.licenseStatus = "About to Expire";
      } else {
        this.licenseStatus = "Active";
      }
    } else {
      this.licenseStatus = "Expired";
    }
    return this.licenseStatus;
    // else if((new Date())-checkDate.getDate)
  }

  clearExpiryDate() {
    //coconsole.log("clear");
    this.data.expirydate = "";
  }

  onItemSelect(item: any) {
    for (let j = 0; j < this.availableServices.length; j++) {
      if (item.itemName === this.availableServices[j]) {
        this.selectedServiceList.push(this.availableServices[j]);
        break;
      }
    }
  }
  OnItemDeSelect(item: any) {
    let index;
    for (let j = 0; j < this.availableServices.length; j++) {
      if (item.itemName === this.selectedServiceList[j]) {
        index = j;
        break;
      }
    }
    this.selectedServiceList.splice(index, 1);
  }
  onSelectAll(items: any) {
    this.selectedServiceList = this.availableServices;
  }
  onDeSelectAll(items: any) {
    this.selectedServiceList = [];
  }

  submit() {
    if (this.checkDate()) {
      const x = confirm("Are you sure you want to create the License ?");
      if (x) {
        for (const i of this.selectedServices) {
          this.data.serviceList.push(i.itemName);
        }
        // console.log(this.data.serviceList);
        this.IdpdataService.loading = true;
        this.IdprestapiService.createLicense(this.data).then((response) => {
          //console.log(response);
          const resp = response.json();
          const errorMsg = resp.errorMessage;
          if (resp.resource !== "") {
            this.data = {
              licenseType: "",
              emailid: "",
              expirydate: "",
              organization: "",
              serviceList: [],
              signature: "string",
            };
            this.selectedServices = [];
            this.selectedServiceList = [];
            this.licenseKey = resp.resource;
            this.subscriptionService
              .addLicense(this.licenseKey)
              .then((response) => {
                // console.log(response.json().resource);
                // console.log("ServiceList1" + this.selectedServices);
                // console.log("ServiceList1" + this.selectedServices);
                // console.log("ServiceList1" + this.selectedServices);
                if (response.json().resource === "SUCCESS") {
                  alert("License successfully created");
                  this.licenseKey = "";
                  const actiondata = {
                    method: "create",
                    userName: this.IdpdataService.idpUserName,
                    orgName: this.IdpdataService.organization,
                    licenseExpiryDate: this.data.expirydate,
                    licenseServices: this.data.serviceList,
                  };
                  // this.IdprestapiService.sendLicenseMail(actiondata);

                  this.initialize();
                } else {
                  alert("Problem creating License...");
                }
              });
          } else {
            alert("Could nott create License...");
          }
          // console.log("required"+JSON.stringify(resp));
          this.IdpdataService.loading = false;
        });
      }
    }
  }

  checkDate() {
    //console.log("in checkDate");

    const licenseExpDate = new Date(this.data.expirydate);
    const currDate = new Date();
    if (licenseExpDate <= currDate) {
      alert("License expiry date cannot be less than current date.");
      return false;
    } else {
      return true;
    }
  }
}
