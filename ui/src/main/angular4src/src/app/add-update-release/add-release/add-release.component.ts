/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit } from "@angular/core";
import { IdprestapiService } from "../../idprestapi.service";
import { IdpdataService } from "../../idpdata.service";
import { ViewChild } from "@angular/core";

declare const $: any;

@Component({
    selector: "app-add-release",
    templateUrl: "./add-release.component.html",
    styleUrls: []
})
export class AddReleaseComponent implements OnInit {

    @ViewChild("date1") date1;
    @ViewChild("date") date;
    min: Date = new Date();
    releaseManagerData: any;
    invalidReleaseNo: boolean;
    invalidReleaseNoPipeline = "";
    pipelineList = [];
    addSuccess: boolean;
    /* Constructor Start */
    constructor(
        public Idpdata: IdpdataService, private Idprestapi: IdprestapiService) {
        if (this.releaseManagerData === undefined) {
            this.releaseManagerData = {
                "additionalMailRecipients": {
                    "applicationTeam": "",
                    "emailIds": ""
                },
                "expectedEndDate": "",
                "expectedStartDate": "",
                "releaseNumber": "",
                "remarks": "",
                "status": "on",
                "vstsReleaseName": ""
            };
        }
    }
    /* Clears End Date */
    clearEnd() {
        this.releaseManagerData.expectedEndDate = "";
    }
    /* Clears start date */
    clearStart() {
        this.releaseManagerData.expectedStartDate = "";
    }

    /* Add release details after adding in release page */
    submit() {
        if (this.checkDate()) {
            if (!this.invalidReleaseNo) {
                const x = confirm("Are you sure you want to add this Release ?");
                if (x) {
                    this.Idpdata.pipelineListRm = "";
                    for (const name of this.pipelineList) {
                        if (this.Idpdata.pipelineListRm !== "") {
                            this.Idpdata.pipelineListRm = this.Idpdata.pipelineListRm + "," + name;
                        } else {
                            this.Idpdata.pipelineListRm = name;
                        }
                    }
                    this.Idpdata.loading = true;
                    this.Idprestapi.postReleaseData(this.releaseManagerData).then(response => {

                        const resp = response.json();
                        const errorMsg = resp.errorMessage;
                        if (resp.resource === "Successfully inserted") {
                            this.Idpdata.releaseAddSuccess = true;

                            this.releaseManagerData = {
                                "additionalMailRecipients": {
                                    "applicationTeam": "",
                                    "emailIds": ""
                                },
                                "expectedEndDate": "",
                                "expectedStartDate": "",
                                "releaseNumber": "",
                                "remarks": "",
                                "status": "on",
                                "vstsReleaseName": ""
                            };
                            this.pipelineList = [];
                            this.Idpdata.activeReleasePipelineName = "";
                        } else {
                            alert("Could not add the new release");
                        }
                        this.Idpdata.loading = false;
                    });
                }
            } else {
                alert("Release Number conflict: Please enter another release number.");
            }
        }
    }

    invalidFalse() {
        this.invalidReleaseNo = false;
        this.invalidReleaseNoPipeline = "";
    }
    /* Checks for Release Number duplication */
    checkReleaseNo() {
        if (this.releaseManagerData.releaseNumber !== "") {
            this.Idpdata.pipelineListRm = "";
            for (const name of this.pipelineList) {
                if (this.Idpdata.pipelineListRm !== "") {
                    this.Idpdata.pipelineListRm = this.Idpdata.pipelineListRm + "," + name;
                } else {
                    this.Idpdata.pipelineListRm = name;
                }
            }
            if (this.pipelineList.length !== 0) {
                this.Idprestapi.checkReleaseNo().then(response => {
                    const resp = response.json();
                    const errorMsg = resp.errorMessage;
                    const releasePipeline = JSON.parse(resp.resource).releasePipeline;
                    this.invalidReleaseNo = false;
                    for (const rp of releasePipeline) {
                        for (const release of rp.release) {
                            if (this.releaseManagerData.releaseNumber === release.releaseNumber) {
                                this.invalidReleaseNo = true;
                                if (this.invalidReleaseNoPipeline !== "") {
                                    this.invalidReleaseNoPipeline = this.invalidReleaseNoPipeline + ", " + rp.pipelineName;
                                } else {
                                    this.invalidReleaseNoPipeline = rp.pipelineName;
                                }
                            }
                        }
                    }
                });
            }
        }
    }
    /* Date Vlidaion for start and end date */
    checkDate() {
        if (this.releaseManagerData.expectedStartDate === "" || this.releaseManagerData.expectedStartDate === undefined) {
            alert("Please select Expected Start date.");
            return false;
        }
        if (this.releaseManagerData.expectedEndDate === "" || this.releaseManagerData.expectedEndDate === undefined) {
            alert("Please select Expected End date.");
            return false;
        }
        const startDate = new Date(this.releaseManagerData.expectedStartDate);
        const endDate = new Date(this.releaseManagerData.expectedEndDate);
        if (startDate > endDate) {
            alert("Start Date cannot be greater than End Date.");
            return false;
        } else {
            return true;
        }
    }

    /* Initialization of Date */
    ngOnInit() {
        const todayDate = new Date();
        // $(document).ready(function () {
        //     const date_input = $("input[name=\"date\"]");
        //     const container = $(".bootstrap-iso form").length > 0 ? $(".bootstrap-iso form").parent() : "body";
        //     const options = {
        //         format: "yyyy-mm-dd",
        //         container: container,
        //         todayHighlight: true,
        //         autoclose: true,
        //         todayBtn: "linked",
        //         clearBtn: true,
        //     };
        //     date_input.datepicker(options);
        // });
        // $(document).ready(function () {
        //     const date_input = $("input[name=\"date1\"]");
        //     const container = $(".bootstrap-iso form").length > 0 ? $(".bootstrap-iso form").parent() : "body";
        //     const options = {
        //         startDate: "today",
        //         todayBtn: "linked",
        //         clearBtn: true,
        //         autoclose: true,
        //         todayHighlight: true,
        //         format: "yyyy-mm-dd",
        //         container: container
        //     };
        //     date_input.datepicker(options);
        // });
    }
}
