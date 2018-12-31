/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit, Input} from "@angular/core";
import { IdprestapiService } from "../../idprestapi.service";
import { IdpdataService } from "../../idpdata.service";
import { Router } from "@angular/router";
import { ViewChild } from "@angular/core";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";

@Component({
  selector: "app-build-info-sub",
  templateUrl: "./build-info-sub.component.html",
  styleUrls: ["./build-info-sub.component.css"]
})
export class BuildInfoSubComponent implements OnInit {
  @ViewChild("modalforDelAntProperties")DelAntProp;


  @Input()
  public formName: string;

  constructor(

    public IdpdataService: IdpdataService,
    private IdprestapiService: IdprestapiService,
    private router: Router
  ) {
    console.log(  this.checkBoxObject.artifact);

    this.IdpdataService.showConfig = false;
    this.IdpdataService.pa = true;
    this.IdpdataService.continuecontrol = true;
    if (this.formStatusObject.operation === "copy" || this.formStatusObject.operation === "edit") {
        this.checkCheckBox();
    }

    if (this.codeInfo.buildScript[2] !== undefined && this.codeInfo.buildScript[2].type === "xml") {
        this.buildInfo.artifactToStage = {};

    }
    if (this.buildInfo.artifactToStage.artifactRepoName === undefined) {
        this.buildInfo.artifactToStage.artifactRepoName = "";
    }

    if (this.buildInfo.artifactToStage.artifactRepo === undefined ) {
        this.buildInfo.artifactToStage.artifactRepo = {};
    }
    this.checkCheckBox();
  }

  buildInfo: any = this.IdpdataService.data.buildInfo;
  codeInfo: any = this.IdpdataService.data.code;
  tempejb: any = this.IdpdataService.data.checkboxStatus.buildInfo;
  checkBoxObject: any = this.IdpdataService.data.checkboxStatus.others;
  formStatusObject: any = this.IdpdataService.data.formStatus;
  buildtool: any = this.buildInfo.buildtool;
  index: any;
  // artifactVariable:any=false;
  // artifactAppVariable:any=false;
  // appName=this.IdpdataService.data.basicInfo.applicationName;


  getApplicationDetails(appName) {
    this.IdprestapiService.getApplicationDetails(appName)
        .then(response => {
            if (response) {
            const resp = response.json().resource;
            let parsed;
            try {
                parsed = JSON.parse(resp);
                if (parsed) {
                    this.IdpdataService.application = parsed.appJson;
                    console.log(this.IdpdataService.application);
                    console.log(this.IdpdataService.data);
                    const application = this.IdpdataService.application;
                    if (!(application.artifactToStage === undefined ||
                        application.artifactToStage.artifactRepoName === "na" || application.artifactToStage.artifactRepoName === "" )) {
                        this.IdpdataService.artifactVariable = true;
                        this.IdpdataService.artifactAppVariable = true;
                        console.log("getApplicationDetails : this.IdpdataService.artifactVariable ===" +
                        this.IdpdataService.artifactVariable);
                        console.log("getApplicationDetails : this.IdpdataService.artifactAppVariable ===" +
                        this.IdpdataService.artifactAppVariable);
                        // console.log(this.IdpdataService.artifactVariable+"bbbbbbbbbbbb"+this.IdpdataService.artifactAppVariable);
                    }


                }

            } catch (e) {
                alert("Failed to get Application Details");
                console.log(e);
                }

            }



        });

  }

detailsApplication() {
    this.getApplicationDetails(this.IdpdataService.data.basicInfo.applicationName);
}

  openPostBuild() {
    this.buildInfo.postBuildScript = {"tool": ""};

    return "on";
  }

  clearBuild() {
    // console.log(this.buildInfo.postBuildScript.tool);
    this.buildInfo.postBuildScript.scriptFilePath = "";
    this.buildInfo.postBuildScript.targets = "";
  }
  clearArtifact() {
    this.buildInfo.artifactToStage.artifactRepoName = "";
    this.buildInfo.artifactToStage.artifactRepo = {};
    this.buildInfo.artifactToStage.artifact = "";
    this.checkBoxObject.nugetPackage = "off";
    this.checkBoxObject.flattenFileStructure = "off";
    this.IdpdataService.artifactVariable = false;
    return "off";
  }

  clearArtifactOnSelect() {
    this.buildInfo.artifactToStage.artifactRepo = {};
    this.buildInfo.artifactToStage.artifact = "";
    this.checkBoxObject.nugetPackage = "off";
  }

  naArtifact() {
    if (this.buildInfo.artifactToStage.artifactRepoName !== "na") {
        this.IdpdataService.artifactVariable = true;
    }
    if (this.buildInfo.artifactToStage.artifactRepoName === "na") {
        this.IdpdataService.artifactVariable = false;
        this.clearArtifactOnSelect();
    }
  }

  clearRunScripts() {
    this.clearBuild();
    this.buildInfo.postBuildScript.tool = "";
        this.checkBoxObject.transferFilesFlag = "off";
        this.buildInfo.postBuildScript.host = "";
        this.buildInfo.postBuildScript.userName = "";
        this.buildInfo.postBuildScript.password = "";
        this.buildInfo.postBuildScript.sshKey = "";
        this.buildInfo.postBuildScript.sshPathToKey = "";
            this.buildInfo.postBuildScript.script = "";
            this.clearTransferFilesFlag();
    return "off";
  }

  clearPostbuild() {
    this.clearBuild();
    this.buildInfo.postBuildScript.tool = "";
    this.buildInfo.postBuildScript.archiveLogs = "";
    this.checkBoxObject.postBuildRunScript = "off";
   this.checkBoxObject.transferFilesFlag = "off";
        this.buildInfo.postBuildScript.host = "";
        this.buildInfo.postBuildScript.userName = "";
        this.buildInfo.postBuildScript.password = "";
        this.buildInfo.postBuildScript.sshKey = "";
        this.buildInfo.postBuildScript.sshKey = "";
            this.buildInfo.postBuildScript.script = "";
            this.clearTransferFilesFlag();
    return "off";
  }


  clearTransferFilesFlag() {
        this.buildInfo.postBuildScript.pathToFiles = "";
        this.buildInfo.postBuildScript.destinationDir = "";
        this.buildInfo.postBuildScript.flattenFilePath = "";
    // this.tempObjectcode.buildScript[0].flattenFilePath="off";
    return "off";
  }

  resetData() {
    const x = confirm("Are you sure to Reset ?");
    if (x) {
        this.buildInfo = {
        "buildtool": this.buildtool,
        "castAnalysis": {},
        "artifactToStage": {},
        "modules": []
        };
    }
    this.IdpdataService.data.buildInfo = this.buildInfo;
    this.IdpdataService.data.checkboxStatus.others = {};
    this.IdpdataService.data.checkboxStatus.buildInfo = {};
  }

   openAntPropertiesField() {
    this.buildInfo.postBuildScript.antPropertiesArr = [];
    this.addAntProperties();
    return "on";
  }
  addAntProperties() {
    this.buildInfo.postBuildScript.antPropertiesArr.push({});
    }
   clearAntPropertisField() {
    this.buildInfo.postBuildScript.antPropertiesArr = [];
    return false;
    }
    deleteAntProp(index) {
        this.index = index;
        this.DelAntProp.nativeElement.click();
    }
    deleteAntPropConfirm() {
        this.buildInfo.postBuildScript.antPropertiesArr.splice(this.index, 1);
    }
  checkCheckBox() {
    if (this.buildInfo.postBuildScript === undefined) {
        this.buildInfo.postBuildScript = {};

    }
    if ((this.buildInfo.postBuildScript.tool !== undefined &&
        this.buildInfo.postBuildScript.tool !== null &&
        this.buildInfo.postBuildScript.tool !== "") || (this.buildInfo.postBuildScript.archiveLogs !== undefined &&
        this.buildInfo.postBuildScript.archiveLogs !== null &&
        this.buildInfo.postBuildScript.archiveLogs !== "")) {
        this.checkBoxObject.postBuildAction = "on";
    }
    if (this.buildInfo.postBuildScript.tool !== undefined &&
        this.buildInfo.postBuildScript.tool !== null &&
        this.buildInfo.postBuildScript.tool !== "") {
        this.checkBoxObject.postBuildRunScript = "on";
    }
        // edit and copy to check the check box

    if (this.buildInfo.artifactToStage.artifactRepoName !== undefined &&
        this.buildInfo.artifactToStage.artifactRepoName !== null &&
        this.buildInfo.artifactToStage.artifact !== undefined &&
        this.buildInfo.artifactToStage.artifact.length > 0 &&
        this.buildInfo.artifactToStage.artifactRepoName !== "") {
        this.checkBoxObject.artifact = "on";
        } else {
        this.checkBoxObject.artifact = "off";
        this.buildInfo.artifactToStage.artifactRepoName = "";
    }

        if (this.buildInfo.artifactToStage.artifact !== undefined ||
        this.buildInfo.artifactToStage.artifact !== null ||
        this.buildInfo.artifactToStage.artifact !== "") {
            if (this.buildInfo.artifactToStage.flattenFileStructure === "on") {
            this.checkBoxObject.flattenFileStructure = "on";
        } else {
        this.checkBoxObject.flattenFileStructure = "off";
        }

        }

        if (this.buildInfo.artifactToStage.nuspecFilePath !== undefined && this.buildInfo.artifactToStage.nuspecFilePath !== null &&
            this.buildInfo.artifactToStage.nuspecFilePath !== ""
            ) {
            this.checkBoxObject.nugetPackage = "on";
            } else {
            this.checkBoxObject.nugetPackage = "off";
            }


            if (this.buildInfo.postBuildScript &&
                this.buildInfo.postBuildScript.antPropertiesArr &&
                this.buildInfo.postBuildScript.antPropertiesArr[0] &&
                this.buildInfo.postBuildScript.antPropertiesArr[0].antKey !== undefined &&
                this.buildInfo.postBuildScript.antPropertiesArr[0] &&
                this.buildInfo.postBuildScript.antPropertiesArr[0].antValue !== undefined) {
            this.buildInfo.postBuildScript.antProperty1 = "on";
            }
            if (this.buildInfo.postBuildScript && this.buildInfo.postBuildScript.javaOptions !== undefined ) {
            this.buildInfo.postBuildScript.antJavaOption1 = "on";
            }


            if (this.buildInfo.postBuildScript &&  this.buildInfo.postBuildScript.pathToFiles !== "" &&
            this.buildInfo.postBuildScript.pathToFiles !== undefined ) {
                this.checkBoxObject.transferFilesFlag = "on";
            }
  }

  ngOnInit() {

  }


  clearNugetDetails() {
    this.buildInfo.artifactToStage.nuspecFilePath = "";
    this.buildInfo.artifactToStage.nexusAPIKey = "";
    return "off";
  }


  getArtifactPatterStatus() {

    /**
     * This function will return true
     *  if artifact Pattern is requried
     *  else false.
     */
    // If nexus got override return true else false.
    if (this.checkBoxObject.artifact === "on") {
        return (this.buildInfo.artifactToStage.artifactRepoName === "nexus" || this.buildInfo.artifactToStage.artifactRepoName === "jfrog");
    } else if (this.checkBoxObject.artifact === "off") {
        if (this.IdpdataService.artifactAppVariable) {
        return true;
        }
    }

    return false;
  }
}
