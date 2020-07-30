import { Component, OnInit } from "@angular/core";
import { IdprestapiService } from "../idprestapi.service";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
@Component({
  selector: "app-approve-release",
  templateUrl: "./approve-release.component.html",
  styleUrls: [],
})
export class ApproveReleaseComponent implements OnInit {
  constructor(
    public idpdataService: IdpdataService,
    private idpService: IdpService,
    private idprestapiService: IdprestapiService
  ) {
    if (this.data === undefined) {
      this.data = {
        applicationName: this.idpdataService.appName,
        approvedArtifact: [],
        environmentName: "",
        importedArtifact: [],
        pipelineName: this.idpdataService.pipelineName,
        releaseNumber: "",
      };

      this.outputData = {
        applicationName: this.idpdataService.appName,
        approvedArtifact: [],
        environmentName: "",
        importedArtifact: [],
        pipelineName: this.idpdataService.pipelineName,
        releaseNumber: "",
      };
    }
    this.getReleaseAndEnvironment();
  }

  ngOnInit() {}
  data: any;
  update: boolean;
  approve = "";
  envList = [];
  release = [];
  releaseList = [];
  approvedList = [];
  importedList = [];
  disapproveList = ["Bangalore", "Chennai"];
  extraMultiselectSettings = {
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All",
  };

  arraylist = [];
  importedarrayList = [];
  approvearrayList = [];
  importedSelected: any = [];
  approveSelected: any = [];
  outputDisapproveRemark: any;
  outputApproveRemark: any;
  displayimportedSelected: any = [];
  displayapproveSelected: any = [];
  outputData: any;

  getReleaseAndEnvironment() {
    this.idpdataService.loading = true;
    this.idprestapiService.getReleasesApprovePortal().then((response) => {
      let resp = response.json();
      //alert(resp);
      //console.log(resp);
      let errorMsg = resp.errorMessage;
      // console.log("required"+JSON.stringify(resp));
      this.release = JSON.parse(resp.resource).releasePipeline[0].release;
      this.envList = JSON.parse(resp.resource).accessEnvironmentList;
      for (var i = 0; i < this.release.length; i++) {
        //push into release list to show in dropdown
        this.releaseList.push(this.release[i].releaseNumber);
      }

      //console.log(this.release);
      this.idpdataService.loading = false;
    });
  }
  getReleaseNamesApprovePortal() {}

  getArtifactsApprovePortal() {
    // console.log("In artifacts!!");
    if (this.data.environmentName !== "") {
      this.idpdataService.loading = true;
      this.data.importedArtifact = [];
      this.data.approvedArtifact = [];
      this.idprestapiService
        .getArtifactsApprovePortal(this.data)
        .then((response) => {
          let resp = response.json();
          //alert(resp);
          //console.log(resp);
          let errorMsg = resp.errorMessage;
          // console.log("required"+JSON.stringify(resp));
          //console.log(resp.resource);
          this.importedarrayList = JSON.parse(resp.resource).importedArtifact;
          this.approvearrayList = JSON.parse(resp.resource).approvedArtifact;

          var temp = JSON.parse(resp.resource).approvedArtifact;
          this.approvedList = [];
          //multiselect box dosent take simple array of strings as input, hence convert it to array of objects
          for (var i = 0; i < temp.length; i++) {
            this.approvedList.push({ id: i, itemName: temp[i].artifactName });
          }
          temp = JSON.parse(resp.resource).importedArtifact;
          this.importedList = [];
          for (var i = 0; i < temp.length; i++) {
            this.importedList.push({ id: i, itemName: temp[i].artifactName });
          }
          // console.log(this.importedList);
          // //console.log(this.release);
          this.idpdataService.loading = false;
        });
    }
  }

  updateArtifacts() {
    // console.log(this.outputData.importedArtifact);
    // console.log(this.outputData.approvedArtifact);
    if (
      (this.outputData.importedArtifact.length !== 0 &&
        this.outputData.importedArtifact !== undefined) ||
      (this.outputData.approvedArtifact.length !== 0 &&
        this.outputData.approvedArtifact !== undefined)
    ) {
      // this.data.applicationName=this.IdpdataService.appName;
      // this.data.pipelineName=this.IdpdataService.pipelineName;
      // var temp = this.data.importedArtifact;
      // this.data.importedArtifact = [];
      // this.update=false;
      // if(temp!== undefined){
      // for(var i = 0; i < temp.length; i++){
      // 	this.data.importedArtifact.push(temp[i].itemName);
      // }
      // }
      // temp = this.data.approvedArtifact;
      // this.data.approvedArtifact = [];
      // if(temp!== undefined){
      // for(var i = 0; i < temp.length; i++){
      // 	this.data.approvedArtifact.push(temp[i].itemName);
      // }
      // }
      this.outputData.environmentName = this.data.environmentName;
      this.outputData.releaseNumber = this.data.releaseNumber;
      this.idpdataService.loading = true;
      this.idprestapiService
        .updateArtifacts(this.outputData)
        .then((response) => {
          let resp = response.json();
          //alert(resp);
          //console.log(resp);
          let errorMsg = resp.errorMessage;
          // console.log("required"+JSON.stringify(resp));

          if (resp.resource === "Updated Successfully!!!") {
            this.update = true;
          } else {
            alert("Update unsuccessful!! Please update again.");
          }

          //console.log(this.release);
          this.idpdataService.loading = false;
        });
    } else {
      alert("Please Select artifacts!!");
    }
    this.displayapproveSelected = this.approveSelected;
    this.displayimportedSelected = this.importedSelected;
    this.importedSelected = [];
    this.outputData.importedArtifact = [];
    // this.outputApproveRemark;
    this.approveSelected = [];
    this.outputData.approvedArtifact = [];
    // this.outputDisapproveRemark;
  }

  updateArtifactRemark() {
    this.outputData.environmentName = this.data.environmentName;
    this.outputData.releaseNumber = this.data.releaseNumber;
  }

  updateFalse() {
    this.update = false;
    this.data = {
      applicationName: this.idpdataService.appName,
      approvedArtifact: [],
      environmentName: "",
      importedArtifact: [],
      pipelineName: this.idpdataService.pipelineName,
      releaseNumber: "",
    };
    this.approve = "";
  }

  clearData() {
    this.data.approvedArtifact = [];
    this.data.importedArtifact = [];
    this.data.environmentName = "";
    this.approve = "";
    this.importedList = [];
    this.approvedList = [];
  }
  onItemSelect(item: any) {
    //console.log(item);

    var temp = this.importedarrayList;
    for (var i = 0; i < temp.length; i++) {
      if (temp[i].artifactName == item.itemName)
        this.importedSelected.push(temp[i]);
    }

    let artifactJson = {
      artifactName: "",
      artifactDetails: [
        {
          status: "",
          remark: "",
        },
      ],
    };

    artifactJson.artifactName = item.itemName;
    artifactJson.artifactDetails[0].status = "approved";
    this.outputData.importedArtifact.push(artifactJson);
    //this.outputApproveRemark.push('');

    //this.importedSelected.push(item.itemName);
    //this.buildInfo.postBuildScript.dependentPipelineList.push(item.itemName);
    //console.log(this.selectedItems2);
  }
  OnItemDeSelect(item: any) {
    //console.log(item);
    var temp = this.importedSelected;
    for (var i = 0; i < temp.length; i++) {
      if (temp[i].artifactName == item.itemName) {
        this.importedSelected.splice(i, 1);
        break;
      }
    }

    for (var i = 0; i < this.outputData.importedArtifact.length; i++) {
      if (this.outputData.importedArtifact[i].artifactName === item.itemName) {
        //console.log("Deteleted artifact:  " + this.outputData.approvedArtifact[i].artifactName);
        this.outputData.importedArtifact.splice(i, 1);
        //this.outputApproveRemark.splice(i,1);
        break;
      }
    }

    /* var i = this.buildInfo.postBuildScript.dependentPipelineList.indexOf(item.itemName);
    if (i !== -1) {
      this.buildInfo.postBuildScript.dependentPipelineList.splice(i, 1);
      console.log("Item Found");
    } */
  }
  onSelectAll(items: any) {
    //console.log(items);
    this.importedSelected = [];
    this.outputData.importedArtifact = [];
    //this.outputApproveRemark = [];
    var temp = this.importedarrayList;
    for (var i = 0; i < temp.length; i++) {
      this.importedSelected.push(temp[i]);
    }

    for (var item of items) {
      let artifactJson = {
        artifactName: "",
        artifactDetails: [
          {
            status: "",
            remark: "",
          },
        ],
      };

      artifactJson.artifactName = item.itemName;
      artifactJson.artifactDetails[0].status = "approved";
      this.outputData.importedArtifact.push(artifactJson);
      //this.outputApproveRemark.push('');
    }

    /* for (var item of items) {
       this.buildInfo.postBuildScript.dependentPipelineList.push(item.itemName);
     } */
  }
  onDeSelectAll(items: any) {
    //console.log(items);
    this.importedSelected = [];
    this.outputData.importedArtifact = [];
    //this.outputApproveRemark = [];
    //this.buildInfo.postBuildScript.dependentPipelineList = [];
  }

  onItemSelectDisapprove(item: any) {
    //console.log(item);

    var temp = this.approvearrayList;
    for (var i = 0; i < temp.length; i++) {
      if (temp[i].artifactName == item.itemName)
        this.approveSelected.push(temp[i]);
    }

    let artifactJson = {
      artifactName: "",
      artifactDetails: [
        {
          status: "",
          remark: "",
        },
      ],
    };

    artifactJson.artifactName = item.itemName;
    artifactJson.artifactDetails[0].status = "disapproved";
    this.outputData.approvedArtifact.push(artifactJson);
    //this.outputDisapproveRemark.push('');
    //console.log(artifactJson);

    //this.importedSelected.push(item.itemName);
    //this.buildInfo.postBuildScript.dependentPipelineList.push(item.itemName);
    //console.log(this.selectedItems2);
  }
  OnItemDeSelectDisapprove(item: any) {
    //console.log(item);
    var temp = this.approveSelected;
    for (var i = 0; i < temp.length; i++) {
      if (temp[i].artifactName == item.itemName) {
        this.approveSelected.splice(i, 1);
        break;
      }
    }
    //console.log("Before deleting: " + this.outputData.approvedArtifact.length );
    for (var i = 0; i < this.outputData.approvedArtifact.length; i++) {
      if (this.outputData.approvedArtifact[i].artifactName === item.itemName) {
        //console.log("Deteleted artifact:  " + this.outputData.approvedArtifact[i].artifactName);
        this.outputData.approvedArtifact.splice(i, 1);
        //this.outputDisapproveRemark.splice(i,1);
        break;
      }
    }
    //console.log("After deleting: " + this.outputData.approvedArtifact.length );
    /* var i = this.buildInfo.postBuildScript.dependentPipelineList.indexOf(item.itemName);
    if (i !== -1) {
      this.buildInfo.postBuildScript.dependentPipelineList.splice(i, 1);
      console.log("Item Found");
    } */
  }
  onSelectAllDisapprove(items: any) {
    //console.log(items);

    this.approveSelected = [];
    this.outputData.approvedArtifact = [];
    //this.outputDisapproveRemark = [];
    var temp = this.approvearrayList;
    for (var i = 0; i < temp.length; i++) {
      this.approveSelected.push(temp[i]);
    }

    for (var item of items) {
      let artifactJson = {
        artifactName: "",
        artifactDetails: [
          {
            status: "",
            remark: "",
          },
        ],
      };

      artifactJson.artifactName = item.itemName;
      artifactJson.artifactDetails[0].status = "disapproved";
      this.outputData.approvedArtifact.push(artifactJson);
      //this.outputDisapproveRemark.push('');
    }

    /* for (var item of items) {
       this.buildInfo.postBuildScript.dependentPipelineList.push(item.itemName);
     } */
  }
  onDeSelectAllDisapprove(items: any) {
    //console.log(items);
    this.approveSelected = [];
    this.outputData.approvedArtifact = [];
    //this.outputDisapproveRemark = [];
    //this.buildInfo.postBuildScript.dependentPipelineList = [];
  }

  insertDisapproveRemark(remark: any) {
    for (
      var count = 0;
      count < this.outputData.approvedArtifact.length;
      count++
    ) {
      this.outputData.approvedArtifact[
        count
      ].artifactDetails[0].remark = remark;
    }
  }

  insertApproveRemark(remark: any) {
    for (
      var count = 0;
      count < this.outputData.importedArtifact.length;
      count++
    ) {
      this.outputData.importedArtifact[
        count
      ].artifactDetails[0].remark = remark;
    }
  }

  clearArtifact() {
    this.approveSelected = [];
    this.outputData.approvedArtifact = [];
    this.importedSelected = [];
    this.outputData.importedArtifact = [];
    this.data.importedArtifact = [];
    this.data.approvedArtifact = [];
  }
}
