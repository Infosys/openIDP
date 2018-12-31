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
import { Router } from "@angular/router";
import { ViewChild } from "@angular/core";
import { IdpSubmitService } from "../idpsubmit.service";
@Component({
  selector: "app-create-application",
  templateUrl: "./create-application.component.html",
  styleUrls: ["./create-application.component.css"]
})

export class CreateApplicationComponent implements OnInit {
  @ViewChild("modalforAlert") button;
  @ViewChild("modalforDel") DelEnv;
  @ViewChild("modalforDelProv") DelEnvProv;
  @ViewChild("modalforreset") resetCodeInfo;
  @ViewChild("modalforslave") DelSlave;
  @ViewChild("modalforSuccessAlert") success;
  @ViewChild("modalforSlaveStageSelection") slaveStageAlert;
  /*constructor start*/
  flag4: any = true;
  appNames: any = [];
  flagNew: any = false;
  FlagEdit: any = false;
  flagp: any = false;
  flagpi: any = false;
  valueSubmit: any = "";
  valueSubmitting: any;
  index: any;
  indexToDisable: any;
  indexToDisForSlave: any;
  grantAccess: any = this.IdpdataService.data.grantAccess;
  tempObjectApp: any = this.IdpService.copy(this.grantAccess);
  addEditmsg: any;
  slaveIndex: any;
  envIndex: any;
  oslist: any = this.IdprestapiService.getIDPDropdownProperties().osList;
  messageEnvName: any = "";
  messageSlvName: any = "";
  message: any = "";
  grantloading: any;
  testPanelExpand = true;
  envProvPanelExpand = false;

  constructor(
    public IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdpSubmitService: IdpSubmitService,
    private IdprestapiService: IdprestapiService,
    private router: Router
  ) {

    this.IdpSubmitService.message = "";
    if (this.grantAccess.artifactToStage.artifactRepoName === undefined) {
        this.grantAccess.artifactToStage.artifactRepoName = "";
    }

    if (this.grantAccess.artifactToStage.artifactRepo === undefined) {
        this.grantAccess.artifactToStage.artifactRepo = {};
    }
    this.getSubscriptionPermission();

  }
  /*constructor end*/

  createNewApp() {
    this.flagNew = true;
    this.FlagEdit = false;
    this.valueSubmit = "Submit";
    this.valueSubmitting = "Submitting";
    this.flagp = false;
    this.flagpi = false;
    this.indexToDisable = null;
    this.indexToDisForSlave = null;
    this.grantAccess = {
        "applicationName": "",
        "developers": "",
        "pipelineAdmins": "",
        "releaseManager": "",
        "artifactToStage": { "artifactRepoName": "", "artifactRepo": {} },
        "sapApplication": "off",
        "applicationType": "",
        "environmentOwnerDetails": [{}],
        "environmentProvDetails": [],
        "slavesDetails": [{
        "slaveName": "",
        "buildServerOS": "",
        "workspacePath": "",
        "createNewSlave": "",
        "labels": "",
        "sshKeyPath": "",
        "slaveUsage": "both",
        "build": "on",
        "deploy": "off",
        "test": "off"
        }
        ]
    };
    this.tempObjectApp = this.IdpService.copy(this.grantAccess);
  }
  /* Application Type Support - General */
  setAppType(appType) {
    this.grantAccess.applicationType = "General";
    this.grantAccess.sapApplication = "off";
    this.grantAccess.environmentOwnerDetails = [{}];
  }

  /* Subscription Permission
   * CI (Only Build), CI-CD or CI-CD-CT
   */
  getSubscriptionPermission() {
    this.IdprestapiService.getSubscriptionPermission().then(response => {
        try {
        const status = JSON.parse(response.json().status);
        if (status.names.indexOf("CREATE_TEST_SUBSCRIBED") !== -1) {
            this.IdpdataService.testSubscription = true;
        }
        if (status.names.indexOf("CREATE_DEPLOY_SUBSCRIBED") !== -1) {
            this.IdpdataService.deploySubscription = true;
            if (!this.IdpdataService.testSubscription === true) {
            this.IdpdataService.deploySubscriptionSubmit = true;
            }
        }
        if (status.names.indexOf("CREATE_BUILD_SUBSCRIBED") !== -1) {
            this.IdpdataService.buildSubscription = true;
            if (!(this.IdpdataService.deploySubscription === true || this.IdpdataService.testSubscription === true)) {
            this.IdpdataService.buildSubscriptionSubmit = true;
            }
        }
        } catch (e) {
        console.log(e);
        }
    });
  }
  setSAPgrantAccessDetails() {
      const applicationName = this.grantAccess.applicationName;
      this.grantAccess.applicationType = "SAP";
      this.grantAccess.sapApplication = "on";
      this.grantAccess.environmentOwnerDetails = [
        {
            "client": "",
            "dBOwners": "",
            "environmentName": "",
            "environmentOwners": "",
            "hostName": "",
            "instanceNumber": "",
            "systemId": "",
            "landscapeType": "",
            "language": "",
            "password": "",
            "userName": ""
          }
      ];
      this.tempObjectApp = this.IdpService.copy(this.grantAccess);
  }
  /* Getting app Information
   * on edit of Application
   */
  getAppInfo() {
    this.FlagEdit = true;
    this.flagNew = false;
    this.appNames = [];
    this.grantAccess.applicationName = "";
    this.IdprestapiService.getOrganizationWiseApplicationNames()
        .then(response => {
        try {
            if (response) {
            const appDetails = JSON.parse(response.json().resource);
            const appArray = appDetails.applications;
            for (const item of appArray) {
                this.appNames.push(item.applicationName);
            }
            }
        } catch (e) {
            alert("Failed while getting applications names");
        }
        });
  }
  /* Adding Env owner details
   * while creating application
   */
  addEnvOwner() {
    if (this.grantAccess.sapApplication === "on") {
        this.grantAccess.environmentOwnerDetails.push(
            {
                "client": "",
                "dBOwners": "",
                "environmentName": "",
                "environmentOwners": "",
                "hostName": "",
                "instanceNumber": "",
                "systemId": "",
                "landscapeType": "",
                "language": "",
                "password": "",
                "userName": ""
            }
        );
    } else {
        this.grantAccess.environmentOwnerDetails.push({});
    }
        this.tempObjectApp.environmentOwnerDetails.push({
        "messageEnvName": ""
    });
  }
  /* Removal of Env owner details
   * while creating application
   */
  removeEnvOwner(envindex) {
    this.envIndex = envindex;
    this.DelEnv.nativeElement.click();
  }
  /* Removal of Env owner details,
   * getting confirmation, before removing
   */
  removeEnvOwnerConfirm() {
    this.grantAccess.environmentOwnerDetails.splice(this.envIndex, 1);
    this.tempObjectApp.environmentOwnerDetails.splice(this.envIndex, 1);
  }
  /* Adding Env Provisioning details for Ansible
   * while creating application
   */
  addEnvProv() {
    this.grantAccess.environmentProvDetails.push({});
    this.tempObjectApp.environmentProvDetails.push({
        "messageEnvName": ""
    });
  }
  removeEnvProv(envindex) {
    this.envIndex = envindex;
    this.DelEnvProv.nativeElement.click();
  }
  removeEnvProvConfirm() {
    this.grantAccess.environmentProvDetails.splice(this.envIndex, 1);
    this.tempObjectApp.environmentProvDetails.splice(this.envIndex, 1);
  }
  /* Adding Slave details
   * while creating application
   */
  addSlave() {
    this.grantAccess.slavesDetails.push(
        {
        "slaveName": "",
        "buildServerOS": "",
        "workspacePath": "",
        "createNewSlave": "",
        "labels": "",
        "sshKeyPath": "",
        "slaveUsage": "both",
        "build": "on",
        "deploy": "off",
        "test": "off"
        }
    );
    this.tempObjectApp.slavesDetails.push(
        {
        "slaveName": "",
        "buildServerOS": "",
        "workspacePath": "",
        "createNewSlave": "",
        "labels": "",
        "sshKeyPath": "",
        "slaveUsage": "both",
        "messageSlvName": "",
        "build": "on",
        "deploy": "off",
        "test": "off"
        }
    );
  }
  /* Removal of slave
   *
   */
  removeSlave(index) {
    this.slaveIndex = index;
    this.DelSlave.nativeElement.click();
  }
  removeSlaveConfirm() {
    this.grantAccess.slavesDetails.splice(this.slaveIndex, 1);
    this.tempObjectApp.slavesDetails.splice(this.slaveIndex, 1);
  }
  /* Checking for duplication of Env names
   * while creating application
   */
  checkEnvName(index) {
    for (let i = 0; i < this.grantAccess.environmentOwnerDetails.length; i++) {
        if (i !== index && this.grantAccess.environmentOwnerDetails[i].environmentName !== undefined &&
        this.grantAccess.environmentOwnerDetails[index].environmentName !== undefined &&
        this.grantAccess.environmentOwnerDetails[i].environmentName.toLowerCase() ===
        this.grantAccess.environmentOwnerDetails[index].environmentName.toLowerCase()) {
        this.tempObjectApp.environmentOwnerDetails[index].messageEnvName = "Environment name should be unique.";
        break;
        } else {
        this.tempObjectApp.environmentOwnerDetails[index].messageEnvName = "";
        }
    }
  }
  /* Checking for duplication of Env Provisioning names
     * while creating application
     */
  checkEnvProvName(index) {
    for (let i = 0; i < this.grantAccess.environmentProvDetails.length; i++) {
        if (i !== index && this.grantAccess.environmentProvDetails[i].environmentProvName !== undefined &&
        this.grantAccess.environmentProvDetails[index].environmentProvName !== undefined &&
        this.grantAccess.environmentProvDetails[i].environmentProvName.toLowerCase()
        === this.grantAccess.environmentProvDetails[index].environmentProvName.toLowerCase()) {
        this.tempObjectApp.environmentProvDetails[index].messageEnvName = "Environment Prov name should be unique.";
        break;
        } else {
        this.tempObjectApp.environmentProvDetails[index].messageEnvName = "";
        }
    }
  }
  /* Checking for duplication of Slave names
   * while creating application
   */
  checkSlaveName(index) {
    for (let i = 0; i < this.grantAccess.slavesDetails.length; i++) {
        if (i !== index && this.grantAccess.slavesDetails[i].slaveName !== undefined &&
        this.grantAccess.slavesDetails[index].slaveName !== undefined &&
        this.grantAccess.slavesDetails[i].slaveName.toLowerCase() === this.grantAccess.slavesDetails[index].slaveName.toLowerCase()) {
        this.tempObjectApp.slavesDetails[index].messageSlvName = "Slave name should be unique.";
        break;
        } else {
        this.tempObjectApp.slavesDetails[index].messageSlvName = "";
        }
    }
  }
  /* To check if atleast a stage is selected in slave
   * while creating application
   */
  checkSlaveStage() {
    for (let i = 0; i < this.grantAccess.slavesDetails.length; i++) {
        if ((this.grantAccess.slavesDetails[i].build === undefined || this.grantAccess.slavesDetails[i].build === "off") &&
        (this.grantAccess.slavesDetails[i].deploy === undefined || this.grantAccess.slavesDetails[i].deploy === "off") &&
        (this.grantAccess.slavesDetails[i].test === undefined || this.grantAccess.slavesDetails[i].test === "off")) {
        this.tempObjectApp.slavesDetails[i].messageSlvStageSelect =
            "Atleast one Slave usage stage (Build/Deploy/Test) has to be checked.";
        } else {
        this.tempObjectApp.slavesDetails[i].messageSlvStageSelect = "";
        }
    }
  }
  checkSlaveStageOnGo() {
    let retFlag = true;
    this.checkSlaveStage();
    for (let i = 0; i < this.grantAccess.slavesDetails.length; i++) {
        if (this.tempObjectApp.slavesDetails[i].messageSlvStageSelect !== undefined
        && this.tempObjectApp.slavesDetails[i].messageSlvStageSelect.indexOf
            ("Atleast one Slave usage stage (Build/Deploy/Test) has to be checked.") !== -1) {
        retFlag = false;
        break;
        }
    }
    return retFlag;
  }
  /* Gets Application Info
   * while editing application
   */
  getApplicationDetailedInfo(data) {
    this.flagp = true;
    const data1 = data;
    this.valueSubmit = "Edit";
    this.valueSubmitting = "Editing";
    this.IdprestapiService.getApplicationInfo(data1)
        .then(response => {
        try {
            const responseData = JSON.parse(response.json().resource);
            this.grantAccess = responseData;
            if (!(this.grantAccess.hasOwnProperty("sapApplication"))) {
                this.grantAccess.sapApplication = "off";
            }
            if (this.FlagEdit && this.grantAccess.environmentProvDetails !== undefined
            && this.grantAccess.environmentProvDetails.length >= 0) {
            this.envProvPanelExpand = true;
            }
            if ((this.FlagEdit && this.grantAccess.artifactToStage === undefined)
            || (this.FlagEdit && this.grantAccess.artifactToStage === {})) {
            this.grantAccess.artifactToStage = {};
            this.grantAccess.artifactToStage.artifactRepoName = "na";
            }
            this.tempObjectApp = this.IdpService.copy(responseData);
            this.indexToDisable = this.grantAccess.environmentOwnerDetails.length;
            if (this.grantAccess.slavesDetails) {
            this.indexToDisForSlave = this.grantAccess.slavesDetails.length;
            }
            if (this.grantAccess.sapApplication === "on") {
                this.grantAccess.applicationType = "SAP";
            } else {
                this.grantAccess.applicationType = "General";
            }
            this.checkCheckBox();
        } catch (e) {
            alert("Failed to get Application Info");
            console.log(e);
        }
        });
  }
  checkCheckBox() {
    for (let i = 0; i < this.grantAccess.slavesDetails.length; i++) {
        if (this.grantAccess.slavesDetails[i].workspacePath !== undefined
        && this.grantAccess.slavesDetails[i].workspacePath !== null
        && this.grantAccess.slavesDetails[i].workspacePath !== "") {
        this.grantAccess.slavesDetails[i].createNewSlave = "on";
        }
    }
  }
  /* Gets Application Info, and alerts if
   * application name already exist
   * while creating application
   */
  getAllApplications() {
    this.IdprestapiService.getExistingAppNames().then(response => {
        try {
        const appDetails = JSON.parse(response.json().resource);
        let sameAppFlag = false;
        for (let i = 0; i < appDetails.applications.length; i++) {
            const app = appDetails.applications[i].applicationName;
            if (app !== undefined && this.grantAccess.applicationName !== undefined
            && app.toLowerCase() === this.grantAccess.applicationName.toLowerCase()) {
            sameAppFlag = true;
            }
        }
        if (sameAppFlag) {
            if (document.getElementById("appname").innerHTML !== null) {
            document.getElementById("appname").innerHTML = "Application already exists.";
            }
        } else {
            if (document.getElementById("appname").innerHTML !== null) {
            document.getElementById("appname").innerHTML = "";
            }
        }
        } catch (e) {
        alert("Failed to get Existing Apps");
        console.log(e);
        }
    });
  }
  /* Sends all application data provided by user
   * while creating application
   */
  go() {
    this.grantloading = true;
    if (this.FlagEdit === true) {
        // check slave stage check box
        if (this.checkSlaveStageOnGo()) {
        this.IdprestapiService.editApplicationDetails(this.grantAccess).then(response => {
            try {
            this.grantloading = false;
            if (response.status === -1
                && response.statusText.toLowerCase() === "illegalstateexception") {
                this.message = "Backend server is down. Please try after sometime.";
            } else {
                if (response.status === 200 && response.statusText === "OK") {
                this.grantloading = false;
                this.addEditmsg = "edited";
                const actiondata = {
                    "applicationName": this.grantAccess.applicationName,
                    "method": "edit",
                    "userName": this.IdpdataService.idpUserName
                };
                this.IdprestapiService.sendAppMail(actiondata);
                this.successAdd();
                } else {
                document.getElementById("dbresponse").innerHTML = "Some error occured.";
                }
            }
            } catch (e) {
            alert("Failed to do Edit Application Details");
            console.log(e);
            }
        });
        } else {
        this.slaveStageAlert.nativeElement.click();
        this.grantloading = false;
        }
    } else {
        if (this.flagNew === true) {
        if (this.checkSlaveStageOnGo()) {
            this.IdprestapiService.createApplication(this.grantAccess).then(response => {
            try {
                this.grantloading = false;
                if (response.status === -1
                && response.statusText.toLowerCase() === "illegalstateexception") {
                response.message = "Backend server is down. Please try after sometime.";
                } else {
                if (response.status === 200 && response.statusText === "OK") {
                    this.grantloading = false;
                    const actiondata = {
                    "applicationName": this.grantAccess.applicationName,
                    "method": "create",
                    "userName": this.IdpdataService.idpUserName
                    };
                    this.IdprestapiService.sendAppMail(actiondata);
                    this.successAdd();
                    this.addEditmsg = "added";
                    this.IdprestapiService.getUserName().then(response => {
                    try {
                        if (response) {
                        const userDetails = JSON.parse(response.json().resource);
                        this.IdpdataService.idpUserName = userDetails.user_id;
                        this.IdpdataService.permissions = userDetails.permissions;
                        let permission;
                        for (let i = 0; i < this.IdpdataService.permissions.length; i++) {
                            permission = this.IdpdataService.permissions[i];
                            if (permission === "CREATE_APPLICATION") {
                            this.IdpdataService.createAppflag = true;
                            }
                            if (permission === "CREATE_PIPELINE") {
                            this.IdpdataService.createPipelineflag = true;
                            }
                            if (permission === "CREATE_ORGANIZATION") {
                            this.IdpdataService.createOrganisationflag = true;
                            }
                            if (permission === "CREATE_LICENSE") {
                            this.IdpdataService.createLicenseflag = true;
                            }
                        }
                        }
                    } catch (e) {
                        console.log(e);
                        alert("Failed to get User Permissions");
                    }
                    });
                } else {
                    document.getElementById("dbresponse").innerHTML = "Some error occured.";
                }
                }
            } catch (e) {
                alert("Failed  in creating application");
                console.log(e);
            }
            });
        } else {
            this.slaveStageAlert.nativeElement.click();
            this.grantloading = false;
        }
        }
    }
  }

  toggleTestPanel() {
    this.testPanelExpand = !this.testPanelExpand;
  }
  toggleEnvProvPanel() {
    this.envProvPanelExpand = !this.envProvPanelExpand;
  }
  reload() {
    this.FlagEdit = false;
    this.flagNew = false;
    this.flagp = false;
  }
  successAdd() {
    console.log("successAdd");
    this.success.nativeElement.click();
  }
  ngOnInit() {
    if (this.IdpdataService.data.formStatus.basicInfo.appNameStatus === "0") {
    }
  }
  clearWorkspacePath(slvIndex) {
    this.grantAccess.slavesDetails[slvIndex].workspacePath = "";
    return "off";
  }
  redirectToBasicInfo() {
  }
}
