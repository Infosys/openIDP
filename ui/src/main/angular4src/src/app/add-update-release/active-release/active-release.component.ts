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
  styleUrls: ["./active-release.component.css"],
})
export class ActiveReleaseComponent implements OnInit {
  many = [];
  many2: String[][];
  envName = [];
  release = [];
  envList = [];
  names = [];
  scmBranches = [];
  str = "";
  scmType = "";
  maxEndDate: any;

  constructor(
    public Idpdata: IdpdataService,

    private Idprestapi: IdprestapiService
  ) {
    this.maxEndDate = new Date();
    this.many2 = [];
    this.Idpdata.pipelineNames = "";
  }

  ngOnInit() {}

  clearEnd(i) {
    this.release[i].actualEndDate = "";
  }
  /* Clears start and end date of release */

  /* Shows acive releases for a pipeline */
  showReleases() {
    this.Idpdata.loading = true;
    if (
      this.Idpdata.activeReleasePipelineName !== "" &&
      this.Idpdata.activeReleasePipelineName !== undefined
    ) {
      this.Idprestapi.getActiveReleases(
        this.Idpdata.activeReleasePipelineName
      ).then((response) => {
        this.Idpdata.loading = false;
        const resp = response.json();
        let errorMsg = resp.errorMessage;
        this.release = JSON.parse(resp.resource).releasePipeline[0].release;
        this.envList = JSON.parse(resp.resource).environmentList;
        this.scmBranches = JSON.parse(
          resp.resource
        ).releasePipeline[0].scmBranches;
        this.scmType = JSON.parse(resp.resource).releasePipeline[0].scmType;
        for (var i = 0; i < this.release.length; i++) {
          this.many2[i] = [];
          if (!this.release[i].hasOwnProperty("branchList")) {
            this.release[i].branchList = [];
          }
        }
      });
    } else {
      this.Idpdata.loading = false;
    }
  }

  addEnv(index) {
    // console.log(index);
    //console.log(this.many2[index]);
    if (this.many2[index].length !== 0) {
      if (this.release[index].envPathList === undefined) {
        this.release[index].envPathList = [];
      }
      if (this.many2[index].length === 1) {
        alert("Please select more than one environment to add a sequence!!");
      } else if (this.checkDuplicate(index)) {
        alert("Please select a ENV only once!!");
      } else {
        this.release[index].envPathList.push({ pathList: this.many2[index] });
      }
      this.many2[index] = [];
    }
  }

  clear(index) {
    this.many2[index] = [];
  }

  checkDuplicate(index) {
    for (var i = 0; i <= this.many2[index].length; i++) {
      for (var j = i; j <= this.many2[index].length; j++) {
        if (i != j && this.many2[index][i] == this.many2[index][j]) {
          return true;
        }
      }
    }
    return false;
  }

  removeEnvSequence(i, j) {
    var x = confirm(
      "Are you sure you want to delete this Environment sequence?"
    );
    if (x) {
      this.release[i].envPathList.splice(j, 1);
    }
  }

  addEnvName(index) {
    for (let name of this.envName[index]) {
      this.many2[index].push(name);
    }
    this.envName[index] = [];
  }

  stringConvert(name) {
    //var str="";
    // console.log(name);
    this.str = "";
    for (var env of name) {
      if (this.str !== "") {
        this.str = this.str + " -> " + env;
      } else {
        this.str = env;
      }
    }

    return true;
  }

  clearStart(i) {
    //console.log("clear");
    this.release[i].actualStartDate = "";
    this.release[i].actualEndDate = "";
  }

  /* Updates release wih the provided date */
  update() {
    const x = confirm(
      "Are you sure you want to update the Release Information?"
    );
    if (x) {
      let stat = "";
      for (const rel of this.release) {
        if (rel.actualEndDate !== undefined && rel.actualEndDate !== "") {
          rel.status = "on";
          stat = rel.status;
        }
      }
      const releaseManagerData = {
        applicationName: this.Idpdata.releaseManagerData.applicationName,
        releasePipeline: [
          {
            pipelineName: this.Idpdata.activeReleasePipelineName,
            release: this.release,
          },
        ],
      };

      this.Idpdata.loading = true;
      // if (stat === "on") {
      this.Idprestapi.updateReleases(releaseManagerData).then((response) => {
        const resp = response.json();
        if (resp.resource === "Successfully Updated") {
          this.Idpdata.releaseUpdateSuccess = true;
          this.Idpdata.releasePipelineName = "";
          this.showReleases();
        } else {
          alert("Update Failed!!!");
        }
        this.Idpdata.loading = false;
      });
      // }
    }
  }
}
