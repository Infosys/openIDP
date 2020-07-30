/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import { allServices } from "../services";
import { toolsList } from "../toolsList";
import { CookieService } from "ngx-cookie";
import { SubscriptionService } from "../subscription.service";
import { IdpdataService } from "../idpdata.service";
import { IdprestapiService } from "../idprestapi.service";
@Component({
  selector: "app-service-portal",
  templateUrl: "./service-portal.component.html",
  styleUrls: ["./service-portal.component.css"],
})
export class ServicePortalComponent implements OnInit {
  token: string;
  services = allServices;
  tools = toolsList;
  haveLicense: boolean;
  haveServices: boolean;
  activeServices: string[];
  activeServicesInfo: string[];
  licenseKey = "";
  licenseStatus: string;
  isLoading = false;
  constructor(
    private _cookieService: CookieService,
    public IdpdataService: IdpdataService,
    private IdprestapiService: IdprestapiService,
    private subscriptionService: SubscriptionService
  ) {}
  allSubscriptions: any[] = new Array();
  currentServiceList: String[] = new Array();
  ngOnInit() {
    this.Initialize();
  }
  Initialize() {
    this.activeServices = [];
    this.activeServicesInfo = [];
    // this.token=this._cookieService.get("access_token");
    // console.log(this.services);
    this.IdprestapiService.getData().then((response) => {
      try {
        if (response) {
          this.IdpdataService.devServerURL = response.json().idpresturl;
          this.IdpdataService.subscriptionServerURL = response.json().idpsubscriptionurl;
          // this.IdpdataService.subscriptionServerURL=  "http://localhost:8090/subscription";

          this.IdprestapiService.getUserName().then((response) => {
            if (response) {
              //console.log(response);
              const userDetails = JSON.parse(response.json().resource);
              this.IdpdataService.idpUserName = userDetails.user_id;
              // console.log(this.IdpdataService.idpUserName);
              this.IdpdataService.roles = userDetails.roles;
              this.IdpdataService.permissions = userDetails.permissions;
              let permission = "";
              for (let i = 0; i < this.IdpdataService.roles.length; i++) {
                if (
                  this.IdpdataService.role.indexOf(
                    this.IdpdataService.roles[i]
                  ) === -1
                ) {
                  this.IdpdataService.role =
                    this.IdpdataService.role +
                    this.IdpdataService.roles[i] +
                    " ";
                }

                // this.IdpdataService.role = this.IdpdataService.role + this.IdpdataService.roles[i] + " ";
              }
              // //console.log(this.IdpdataService.role);
              if (document.getElementById("idpUserName")) {
                document.getElementById(
                  "idpUserName"
                ).title = this.IdpdataService.role;
              }
              //console.log(this.IdpdataService.permissions);
              //console.log(this.IdpdataService.role);
              if (this.IdpdataService.role.indexOf("RELEASE_MANAGER") !== -1) {
                this.IdpdataService.showRelease = true;
              }
              if (this.IdpdataService.role.indexOf("IDP_ADMIN") !== -1) {
                this.IdpdataService.showService = true;
              }
              for (let j = 0; j < this.IdpdataService.permissions.length; j++) {
                permission = this.IdpdataService.permissions[j];
                if (permission === "CREATE_APPLICATION") {
                  this.IdpdataService.createAppflag = true;
                  //console.log(this.IdpdataService.createAppflag);
                }
                if (permission === "CREATE_PIPELINE") {
                  this.IdpdataService.createPipelineflag = true;
                }
                if (permission === "COPY_PIPELINE") {
                  this.IdpdataService.copyPipelineflag = true;
                  // $scope.copyPipelineflag=true;
                }
                if (permission === "EDIT_PIPELINE") {
                  this.IdpdataService.editPipelineflag = true;
                  // $scope.editPipelineflag=true;
                }
                if (permission === "DELETE_PIEPLINE") {
                  this.IdpdataService.deletePipelineflag = true;
                }

                if (permission === "APPROVE_BUILD") {
                  this.IdpdataService.approveBuildFlag = true;
                }
                if (permission === "APPROVE_DEPLOY") {
                  this.IdpdataService.approveDeployFlag = true;
                }
              }
            }
          });
          this.subscriptionService.getActiveServices().then((response) => {
            const data = JSON.parse(response.json().status).service;

            data.forEach((value, index) => {
              // console.log(value.serviceIdentity.serviceName);
              this.activeServices.push(value.serviceIdentity.serviceName);
              let activeServiceInfo = "";
              if (value.serviceIdentity.serviceName === "CI") {
                activeServiceInfo = "Continuous Integration of Different tools";
              } else if (value.serviceIdentity.serviceName === "CD") {
                activeServiceInfo =
                  "Continuous Deployment to various servers and containers";
              } else if (value.serviceIdentity.serviceName === "CT") {
                activeServiceInfo =
                  "Continuous Testing of tools for Functional , performance testing etc.";
              }
              this.activeServicesInfo.push(activeServiceInfo);
            });
            if (this.activeServices.length === 0) {
              this.haveServices = false;
              this.IdpdataService.noAccessNavBars = true;
            } else {
              this.haveServices = true;
              this.IdpdataService.noAccessNavBars = false;
            }
            // console.log(this.activeServices);
          });

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
      } catch (e) {
        alert("failed to get properties details");
        //console.log(e);
      }
    });
  }
  isActive(currentService: String): boolean {
    let flag = false;

    if (this.activeServices) {
      this.activeServices.forEach((value, index) => {
        if (value === currentService) {
          flag = true;
        }
      });
    }
    return flag;
  }

  applyForLicense() {
    let isKeyValid = false;
    if (this.licenseKey.length > 0) {
      this.isLoading = true;
      this.subscriptionService
        .validateLicense(this.licenseKey)
        .then((response) => {
          // //console.log(response);

          const data = response;
          if (data.status === "SUCCESS") {
            isKeyValid = true;
          }
        });

      if ((isKeyValid = true)) {
        this.subscriptionService
          .addLicense(this.licenseKey)
          .then((response) => {
            // console.log(response.json().resource);
            this.isLoading = false;
            if (response.json().resource === "SUCCESS") {
              alert("License successfully applied");
              this.licenseKey = "";
              // window.location.reload();
              document.getElementById("cancelLicenseModal").click();
              this.Initialize();
            } else {
              alert("Given license key is invalid");
            }
          });
      }
    } else {
      alert("License Key can not be blank");
      return;
    }
  }

  getLicenseDetails(subIndex: number) {
    this.currentServiceList = this.allSubscriptions[subIndex].serviceList;
    document.getElementById("detailsButton").click();
  }
  getstatus(expirydate: string) {
    const oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
    const checkDate: Date = new Date(expirydate);
    const currentDate: Date = new Date();
    const diffDays = Math.round(
      Math.abs((checkDate.getTime() - currentDate.getTime()) / oneDay)
    );

    // console.log(diffDays);
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

  GetKeys(o) {
    return Object.keys(o);
  }
  userHaveLicences() {
    if (this.allSubscriptions.length === 0) {
      this.haveLicense = false;
    } else {
      this.haveLicense = true;
    }
    return this.haveLicense;
  }
}
