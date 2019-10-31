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
import { IDPEncryption } from "../../idpencryption.service";
import { ParentFormConnectComponent } from "../../parent-form-connect/parent-form-connect.component";
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

@Component({
  selector: "app-build-info-sub",
  templateUrl: "./build-info-sub.component.html",
  styleUrls: ["./build-info-sub.component.css"]
})
export class BuildInfoSubComponent implements OnInit {
  @ViewChild("modalforDelAntProperties")DelAntProp;

  javaVersionsForFortify: string[] = ['1.5','1.6','1.7','1.8','1.9']

  dotNetVersionsForFortify: string[] = ['3.5','4.0','4.5','4.6','4.7','4.8'] 
  
  @Input()
  public formName: string;
    deleteAntPropModalRef: BsModalRef;
  responseData: any;
  public showCheckmarx: boolean = false;
//   presetNames: any = [];
  presetList: any = [];
  teamList: any = [];

  constructor(

    public IdpdataService: IdpdataService,
    private IdprestapiService: IdprestapiService,
    private idpencryption: IDPEncryption,
    private router: Router,
    private modalService:BsModalService
  ) {
    this.IdpdataService.showConfig = false;
    this.IdpdataService.pa = true;
    this.IdpdataService.continuecontrol = true;

    if(this.IdpdataService.application && this.IdpdataService.application.checkMarxDetails && this.IdpdataService.application.checkMarxDetails.checkmarxUrl &&  this.IdpdataService.application.checkMarxDetails.checkmarxUrl!==''){
        this.showCheckmarx = true;
        if ( this.buildInfo.securityAnalysisTool === "checkmarx") {
            this.getCheckmarxDetails();
        }
    }
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
  //  this.buildInfo.artifactToStage.artifactRepo.nexusProtocol = "HTTP";
    
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

  /* checkmarx method */
  getCheckmarxDetails() {
    this.IdprestapiService.getCheckmarxData(this.IdpdataService.application.checkMarxDetails).then(response => {
      try {

        const dropdownNames = JSON.parse(response.json().resource);

        //Populate the preset list
        for(let pname of dropdownNames.preset){
            this.presetList.push(pname.name);
        }
        //Populate the team list
        for(let team of dropdownNames.teams){
            let name = team.fullName.replace(/\\\\/g,'\\');
            name = name.substring(1);
            this.teamList.push(name);
        }
        if(this.teamList == null || this.teamList.length ==0 ){
            alert("Could not fetch team names!")
        }
    } catch (e) {
        this.showCheckmarx = false;
        alert("Could not fetch team names!")
        console.log(e);
      }
    });
  }

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
openSADropdown() {

    this.buildInfo.checkmarxAnalysis = {"scanTag": "off",
                                        "excludeFiles": "",
                                        "sastHigh": "",
                                        "sastMedium": "",
                                        "sastLow": "",
                                        "enableOSA": "",
                                        "excludeFileso": "",
                                        "team": "",
                                        "preset": ""
                                        };
    this.buildInfo.securityAnalysisTool="";
}
addFortifyDetails(){
    if(this.buildInfo.securityAnalysisTool === 'fortify'){
        this.buildInfo.fortifyDetails = {
            "javaVersion" : "",
            "dotNetVersion" : "",
            "excludeList" : ""
        };
    }
  }

  clearFortifyDetails(){
    if(this.buildInfo.securityAnalysisTool !== 'fortify'){
        this.buildInfo.fortifyDetails = {
            "javaVersion" : "",
            "dotNetVersion" : "",
            "excludeList" : ""
        };
    }
  }

  closeSADropdown() {

    this.buildInfo.checkmarxAnalysis = {"scanTag": "off",
                                        "tool": "",
                                        "excludeFiles": "",
                                        "sastHigh": "",
                                        "sastMedium": "",
                                        "sastLow": "",
                                        "enableOSA": "off",
                                        "excludeFileso": "",
                                        "team": "",
                                        "preset": ""
                                        };
    this.buildInfo.securityAnalysisTool=null;
  }

  

  openOSACheck() {
    this.buildInfo.checkmarxAnalysis.enableOSA = {
                                        //"enableOSA": "on",
                                        "excludeFileso": ""
                                    };

    return "on";
  }

  clearBuild() {
    // console.log(this.buildInfo.postBuildScript.tool);
    this.buildInfo.postBuildScript.scriptFilePath = "";
    this.buildInfo.postBuildScript.targets = "";
  }

  clearAnalysis() {
    // console.log(this.buildInfo.postBuildScript.tool);
    this.buildInfo.checkmarxAnalysis.excludeFiles = "";
    this.buildInfo.checkmarxAnalysis.targets = "";
  }

  clearOSAAnalysis() {
    // console.log(this.buildInfo.postBuildScript.tool);
    this.buildInfo.checkmarxAnalysis.excludeFileso = "";
    this.buildInfo.checkmarxAnalysis.targets = "";
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
    this.buildInfo.artifactToStage.artifact = "na";
    this.checkBoxObject.nugetPackage = "off";
    this.clearOrSetArtifactoryDetails()
  }

  clearOrSetArtifactoryDetails(){
    this.buildInfo.artifactToStage.artifactRepo.repoURL="",
    this.buildInfo.artifactToStage.artifactRepo.repoName="",
    this.buildInfo.artifactToStage.artifactRepo.repoUsername="",
    this.buildInfo.artifactToStage.artifactRepo.repoPassword="",
    this.buildInfo.artifactToStage.artifactRepo.dockerFilePathDR="",
    this.buildInfo.artifactToStage.artifactRepo.dockerRegistryUrlDR="",
    this.buildInfo.artifactToStage.artifactRepo.repoNameDR="",
    this.buildInfo.artifactToStage.artifactRepo.userNameDR="",
    this.buildInfo.artifactToStage.artifactRepo.passwordDR=""
}

  naArtifact() {
    if (this.buildInfo.artifactToStage.artifactRepoName !== "na") {
        this.IdpdataService.artifactVariable = true;
    }
    if (this.buildInfo.artifactToStage.artifactRepoName === "na") {
        this.IdpdataService.artifactVariable = false;
        this.clearArtifactOnSelect();
    }
    this.clearOrSetArtifactoryDetails()

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
  clearCheck() {
    this.clearAnalysis();
    this.buildInfo.checkmarxAnalysis.tools= "";
    this.buildInfo.checkmarxAnalysis.excludeFiles = "";
    this.buildInfo.checkmarxAnalysis.excludeFileso = "";
    this.buildInfo.checkmarxAnalysis.enableOSA = "off";
    this.buildInfo.checkmarxAnalysis.sastHigh = "";
    this.buildInfo.checkmarxAnalysis.sastMedium = "";
    this.buildInfo.checkmarxAnalysis.sastLow = "";
    this.buildInfo.checkmarxAnalysis.scanTag = "off";
    return "off";
  }

  clearOSACheck() {
    this.buildInfo.checkmarxAnalysis.excludeFileso = "";
    //this.buildInfo.checkMarxAction.enableOSA = "off";
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
        "checkmarxAnalysis": {},
        "securityAnalysisTool": "",
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
        this.deleteAntPropModalRef = this.modalService.show(this.DelAntProp.nativeElement);
        this.deleteAntPropModalRef.content = {id:index};
    }
    deleteAntPropConfirm() {
        this.buildInfo.postBuildScript.antPropertiesArr.splice(this.index, 1);
    }
  checkCheckBox() {
      if (this.buildInfo.checkmarxAnalysis === undefined){
        this.buildInfo.checkmarxAnalysis = {};
        this.buildInfo.checkmarxAnalysis.scanTag = "off";
      }
      if ((this.buildInfo.checkmarxAnalysis.excludeFiles !== undefined &&
        this.buildInfo.checkmarxAnalysis.excludeFiles !== null &&
        this.buildInfo.checkmarxAnalysis.excludeFiles !== "") || (this.buildInfo.checkmarxAnalysis.excludeFileso !== undefined &&
        this.buildInfo.checkmarxAnalysis.excludeFileso !== null &&
        this.buildInfo.checkmarxAnalysis.excludeFileso !== "") || (this.buildInfo.checkmarxAnalysis.sastHigh !== undefined &&
        this.buildInfo.checkmarxAnalysis.sastHigh !== null &&
        this.buildInfo.checkmarxAnalysis.sastHigh !== "") || (this.buildInfo.checkmarxAnalysis.sastMedium !== undefined &&
        this.buildInfo.checkmarxAnalysis.sastMedium !== null &&
        this.buildInfo.checkmarxAnalysis.sastMedium !== "") || (this.buildInfo.checkmarxAnalysis.sastLow !== undefined &&
        this.buildInfo.checkmarxAnalysis.sastLow !== null &&
        this.buildInfo.checkmarxAnalysis.sastLow !== "")) {
        this.buildInfo.checkmarxAnalysis.checkmarxAnalysis = "on";
        // console.log(this.buildInfo.checkmarxAnalysis.scanTag);
        this.buildInfo.checkmarxAnalysis.scanTag = "on";
        // this.checkBoxObject.checkMarxAction = "on";
    }
    if ((this.buildInfo.checkmarxAnalysis.excludeFileso !== undefined &&
        this.buildInfo.checkmarxAnalysis.excludeFileso !== null &&
        this.buildInfo.checkmarxAnalysis.excludeFileso !== "")){
        if(this.buildInfo.checkmarxAnalysis.enableOSA === "on"){
            this.buildInfo.checkmarxAnalysis.enableOSA = "on";
        } else {
            this.buildInfo.checkmarxAnalysis.enableOSA = "off";
        }
        }
    if (this.buildInfo.postBuildScript === undefined) {
        this.buildInfo.postBuildScript = {};

    }
    if ((this.buildInfo.postBuildScript.tool !== undefined &&
        this.buildInfo.postBuildScript.tool !== null &&
        this.buildInfo.postBuildScript.tool !== "") || (this.buildInfo.postBuildScript.archiveLogs !== undefined &&
        this.buildInfo.postBuildScript.archiveLogs !== null &&
        this.buildInfo.postBuildScript.archiveLogs !== "")) {
        this.checkBoxObject.postBuildAction = "on";
        // this.checkBoxObject.checkMarxAction = "on";
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
	//select checkbox for docker
	if(this.buildInfo.artifactToStage.artifactRepo !== undefined && this.buildInfo.artifactToStage.artifactRepo.dockerRegistryUrlDR !== undefined && this.buildInfo.artifactToStage.artifactRepo.dockerRegistryUrlDR !== ""){
		this.checkBoxObject.artifact = "on";
		this.buildInfo.artifactToStage.artifactRepoName = "docker";
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
			if(this.IdpdataService.isDockerRegistry===false)
				return true;
        }
    }

    return false;
  }
  checkTechnologyAndPackage() {
      if ((this.buildInfo.buildtool ==='bigData') || (this.buildInfo.buildtool ==='mainframe') || (this.buildInfo.buildtool ==='general') || (this.codeInfo.category === 'Package')) {
        return false;
      } else {
        return true;
      }
  }
}
