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


@Component({
    selector: "app-active-release",
    templateUrl: "./active-release.component.html",
    styleUrls: ["./active-release.component.css"]
})
export class ActiveReleaseComponent implements OnInit {
    many = [];
    many2: String[][];
    release = [];
    envList = [];
    names = [];

    str = "";
    scmType = "";

    constructor(public Idpdata: IdpdataService,

        private Idprestapi: IdprestapiService,
    ) {
        this.many2 = [];
        this.Idpdata.pipelineNames = "";

    }

    ngOnInit() {
    }

    clearEnd(i) {
        this.release[i].actualEndDate = "";
    }
    /* Clears start and end date of release */
    clearStart(i) {
        this.release[i].actualStartDate = "";
        this.release[i].actualEndDate = "";
    }
    /* Shows acive releases for a pipeline */
    showReleases() {
        this.Idpdata.loading = true;
        if (this.Idpdata.activeReleasePipelineName !== "" && this.Idpdata.activeReleasePipelineName !== undefined) {
            this.Idprestapi.getActiveReleases(this.Idpdata.activeReleasePipelineName).then(response => {
                this.Idpdata.loading = false;
                const resp = response.json();
                this.release = JSON.parse(resp.resource).releasePipeline[0].release;
                this.envList = JSON.parse(resp.resource).environmentList;
            });
        } else {
            this.Idpdata.loading = false;
        }
    }

    /* Updates release wih the provided date */
    update() {
        const x = confirm("Are you sure you want to update the Release Information?");
        if (x) {
            let stat = "";
            for (const rel of this.release) {

                if (rel.actualEndDate !== undefined && rel.actualEndDate !== "") {
                    rel.status = "on";
                    stat = rel.status;
                }
            }
            const releaseManagerData = {
                "applicationName": this.Idpdata.releaseManagerData.applicationName,
                "releasePipeline": [
                    {
                        "pipelineName": this.Idpdata.activeReleasePipelineName,
                        "release": this.release
                    }]
            };
            this.Idpdata.loading = true;
            if (stat === "on") {
                this.Idprestapi.updateReleases(releaseManagerData).then(response => {
                    const resp = response.json();
                    if (resp.resource === "Successfully Updated") {
                        this.Idpdata.releaseUpdateSuccess = true;
                        this.Idpdata.releasePipelineName = "";
                    } else {
                        alert("Update Failed!!!");
                    }
                    this.Idpdata.loading = false;
                });
            }
        }
    }
}
