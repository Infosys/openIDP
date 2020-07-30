/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit, ViewChild } from "@angular/core";
import { IdpService } from "../idp-service.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router } from "@angular/router";
import { IdpdataService } from "../idpdata.service";
@Component({
    selector: "app-approve-build",
    templateUrl: "./approve-build.component.html",
    styleUrls: []
})
export class ApproveBuildComponent implements OnInit {
    IDPApproveData: any;
    msg: any;
    buildDeploy = false;
    buildBuild: any = false;
    builds: any = [];
    buildDeployArr: any = [];
    buildBuildArr: any = [];
    ngOnInit() { }
    constructor(
        public idpdataService: IdpdataService,
        private idprestapiService: IdprestapiService,
        private router: Router,
        public idpService: IdpService) {

        this.IDPApproveData = {
            "applicationName": "",
            "pipelineName": "",
            "buildNo": "",
            "releaseNo": "",
            "userName": "",
            "jobType": "",
            "workEnv": "",
            "moduleList": [],
            "deployStep": [],
            "releaseIdentifier": "",
            "obj": ""

        };

        try {
            if (this.idpdataService.triggerJobData) {
                this.IDPApproveData.applicationName = this.idpdataService.triggerJobData.hasOwnProperty("applicationName") ?
                    this.idpdataService.triggerJobData.applicationName : "";
                this.IDPApproveData.pipelineName = this.idpdataService.triggerJobData.hasOwnProperty("pipelineName") ?
                    this.idpdataService.triggerJobData.pipelineName : "";
                this.IDPApproveData.buildNo = this.idpdataService.triggerJobData.build.ApprBuildNO;
                this.IDPApproveData.userName = this.idpdataService.triggerJobData.build.userInfo;
                this.IDPApproveData.jobType = this.idpdataService.triggerJobData.jobType;
                this.IDPApproveData.moduleList = this.idpdataService.triggerJobData.build.moduleList;

                this.IDPApproveData.deployStep = this.idpdataService.triggerJobData.deploy.deployStep;
                this.IDPApproveData.releaseIdentifier = this.idpdataService.triggerJobData.build.releaseIdentifier;
                if (this.idpdataService.triggerJobData.hasOwnProperty("build")) {
                    const build = this.idpdataService.triggerJobData.build;
                    if (build.approveBuild !== undefined && build.approveBuild.length !== 0) {
                        this.buildBuildArr = build.approveBuild;
                        this.buildBuild = true;
                    }
                }

                if (this.idpdataService.triggerJobData.hasOwnProperty("deploy")) {
                    const deploy = this.idpdataService.triggerJobData.deploy;
                    this.IDPApproveData.workEnv = this.idpdataService.triggerJobData.deploy.workEnv;
                    if (Object.keys(this.idpdataService.triggerJobData.deploy.workEnvApprovalList).length !== 0 &&
                        this.idpdataService.triggerJobData.deploy.workEnvApprovalList.constructor === Object &&
                        deploy.workEnvApprovalList !== undefined && deploy.workEnvApprovalList !== "") {
                        for (const k in deploy.workEnvApprovalList) {
                            if (deploy.workEnvApprovalList.hasOwnProperty(k)) {
                                for (let p = 0; p < deploy.workEnvApprovalList[k].length; p++) {
                                    const ob = deploy.workEnvApprovalList[k][p];
                                    ob.env = k;
                                    this.buildDeployArr.push(deploy.workEnvApprovalList[k][p]);
                                }
                            }
                        }
                        this.buildDeploy = true;
                     }
                    }
                    if (this.buildBuild && this.buildDeploy) {
                    this.builds = [];
                    this.IDPApproveData.jobType = "";
                } else if (this.buildBuild) {
                    this.builds = this.buildBuildArr;
                    this.IDPApproveData.jobType = "build";
                } else {
                    this.builds = this.buildDeployArr;
                    this.IDPApproveData.jobType = "deploy";
                }

                this.IDPApproveData.buildNo = "";
            }

        } catch (e) {
            console.error("error:",e);
         }
    }

    setBuildNum() {
        if (this.IDPApproveData.jobType === "build") {
            this.builds = this.buildBuildArr;
        } else if (this.IDPApproveData.jobType === "deploy") {
            this.builds = this.buildDeployArr;
        }
    }

    submitAppr() {
        this.idpdataService.loading = true;
        const data = {
            "applicationName": this.IDPApproveData.applicationName,
            "pipelineName": this.IDPApproveData.pipelineName,
            "jobType": this.IDPApproveData.jobType,
            "apprInput": this.IDPApproveData.approve,
            "apprComment": this.IDPApproveData.apprvComments,
            "apprBuildNo": this.IDPApproveData.buildNo,
            "envName": this.IDPApproveData.workEnv,
            "userName": this.IDPApproveData.userName,
            "apprStepName": ""
        };
        this.idprestapiService.approveJobs(data)
            .then(response => {
                try {
                    if (response) {
                        const err = response.json().errorMessage;

                        if (err === null && response.json().resource.toLowerCase() === "success") {
                            this.idpdataService.loading = false;
                            this.msg = "success";
                            setTimeout(() => { this.router.navigate(["/previousConfig/showConfigurations"]); }, 7000);

                        } else {
                            this.idpdataService.loading = false;
                            this.msg = "error";
                            setTimeout(() => { this.router.navigate(["/previousConfig/showConfigurations"]); }, 7000);
                        }
                    }
                } catch (e) {
                    alert("Failed while approving build.");
                }
            });
    }
    setDetails(temp) {
        this.IDPApproveData.buildNo = temp.ApprBuildNO;
        this.IDPApproveData.userName = temp.userInfo;
        if (this.IDPApproveData.jobType === "deploy") {
            this.IDPApproveData.workEnv = temp.env;
        }
    }

}
