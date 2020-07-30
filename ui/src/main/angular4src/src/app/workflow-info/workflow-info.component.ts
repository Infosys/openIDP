/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/

import {
  Component,
  AfterViewInit,
  ViewChild,
  DoCheck,
  Input,
  ComponentFactoryResolver,
  OnDestroy,
} from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { IdpService } from "../idp-service.service";
import { IdprestapiService } from "../idprestapi.service";
import { Router } from "@angular/router";
import { IdpdataService } from "../idpdata.service";
import { DataTable, DataTableResource } from "angular-2-data-table";

import { DynamicComponentDirective } from "../custom-directive/dynamicComponent.directive";
import { TriggerComponent } from "../triggerPipeline/triggerPipeline.component";

import { IDPEncryption } from "../idpencryption.service";
import {
  BsModalService,
  BsModalRef,
} from "../../../node_modules/ngx-bootstrap";

declare var jQuery: any;
@Component({
  selector: "app-workflow-info",
  templateUrl: "./workflow-info.component.html",
  styleUrls: [],
})
export class WorkflowInfoComponent implements AfterViewInit, OnDestroy {
  @ViewChild("modalforAlert") modalforAlert;
  @ViewChild("modalforAlertDataMiss") modalforAlertDataMiss;
  @ViewChild("modalformandatoryFieldsAlert") modalformandatoryFieldsAlert;
  @ViewChild("modalforconfirmAlert") modalforconfirmAlert;
  @ViewChild("modalforTrigger") modalforTrigger;
  @ViewChild("modalForTriggerDetails") modalForTriggerDetails;
  workflowData: any = this.Idpdata.workflowData;
  IDPWorkflowParamData: any = {};
  newOrEditWorkflow: string;

  listToFillFields: any = [];
  formStatusObject = this.Idpdata.data.formStatus;
  appPipeNamesAvailable = false;
  isReleaseAvailable = true;
  loader: any = "off";
  message: any;
  errorMessage: any;
  appNames: any = [];
  pipeNames: any = {};
  noPipelines = false;
  releaseNumberList: any = [];
  resetFlag = false;
  initialSequence: any;
  pipelinesSelection = [];
  extraMultiselectSettings: any = {
    enableSearchFilter: true,
    selectAllText: "Select All",
    unSelectAllText: "UnSelect All",
  };
  seqCollapseStatus: Array<any> = [];
  @ViewChild(DynamicComponentDirective)
  dynamicComponent: DynamicComponentDirective;
  modalforAlertRef: BsModalRef;
  modalforAlertDataMissRef: BsModalRef;
  modalformandatoryFieldsAlertRef: BsModalRef;
  modalforconfirmAlertRef: BsModalRef;
  modalforTriggerRef: BsModalRef;
  modalForTriggerDetailsRef: BsModalRef;
  constructor(
    public Idpdata: IdpdataService,
    private Idprestapi: IdprestapiService,
    private router: Router,
    private componentFactoryResolver: ComponentFactoryResolver,
    public IdpService: IdpService,
    private idpencryption: IDPEncryption,
    private modalService: BsModalService
  ) {
    ////console.log(this.Idpdata.triggerJobData);
    this.initialize();
  }
  ngAfterViewInit() {
    this.initNavigation();
    const filterString = "nonSAP";
    this.getApplicationNames(filterString);
  }
  ngOnDestroy() {}
  redirectToBasicInfo() {
    this.Idpdata.createWorkflowSequenceflag = false;
    this.router.navigate(["/createConfig/basicInfo"]);
  }
  redirectToShowConfiguration() {
    this.Idpdata.createWorkflowSequenceflag = false;
    this.router.navigate(["/previousConfig/showConfigurations"]);
  }
  initNavigation() {
    const url: string = this.router.url;
    if (
      url.includes("createConfig") &&
      !this.Idpdata.workflowTrigger &&
      this.Idpdata.data.formStatus.basicInfo.appNameStatus === "0"
    ) {
      this.modalforAlertRef = this.modalService.show(this.modalforAlert);
    } else if (
      url.includes("previousConfig") &&
      this.Idpdata.triggerJobData === undefined
    ) {
      this.redirectToShowConfiguration();
    }
  }
  initialize() {
    const url: string = this.router.url;
    if (url.includes("createConfig")) {
      this.Idpdata.workflowTrigger = false;
    } else if (url.includes("previousConfig")) {
      if (this.Idpdata.triggerJobData === undefined) {
        this.redirectToShowConfiguration();
      }
      this.Idpdata.workflowTrigger = true;
    }
    if (
      !this.Idpdata.workflowTrigger &&
      (this.Idpdata.createWorkflowSequenceflag === undefined ||
        !this.Idpdata.createWorkflowSequenceflag)
    ) {
      this.Idpdata.createWorkflowSequenceflag = true;
    }
    if (this.Idpdata.workflowTrigger) {
      this.IDPWorkflowParamData = {
        applicationName: "",
        artifactorySelected: "off",
        technology: "",
        subApplicationName: "",
        jobParam: [],
        build: {
          branchSelected: "",
          module: [],
        },
        deploy: null,
        envSelected: "",
        pipelineName: "",
        releaseNumber: "",
        jobBuildId: "",
        slaveName: "",
        testSlaveName: "",
        tfsWorkItem: "",
        branchOrTag: "",
        testPlanId: "",
        testSuitId: "",
        mtmStepName: "",
        repoDeployStatus: "",
        nonRepoDeployStatus: "",
        testSelected: "off",
        testStep: [],
        userName: this.Idpdata.idpUserName,
        gitTag: "",
        buildartifactNumber: "",
      };
      this.releaseNumberList = this.Idpdata.triggerJobData.hasOwnProperty(
        "releaseNumber"
      )
        ? this.Idpdata.triggerJobData.releaseNumber
        : [];
      this.IDPWorkflowParamData.applicationName = this.Idpdata.triggerJobData.hasOwnProperty(
        "applicationName"
      )
        ? this.Idpdata.triggerJobData.applicationName
        : "";
      this.IDPWorkflowParamData.pipelineName = this.Idpdata.triggerJobData.hasOwnProperty(
        "pipelineName"
      )
        ? this.Idpdata.triggerJobData.pipelineName
        : "";
      this.IDPWorkflowParamData.technology = this.Idpdata.triggerJobData.technology;
      this.Idpdata.triggerWorkflowJobData = this.Idpdata.triggerJobData;
      this.workflowData.workflowSequence = this.Idpdata.triggerWorkflowJobData.pipelines;
      // //console.log(
      //   "workflow sequence: " + this.Idpdata.triggerWorkflowJobData.pipelines
      // );
      this.workflowData.pipelines = this.Idpdata.triggerWorkflowJobData.pipelines;
      let i = 0;
      this.workflowData.workflowSequenceTemp = [];
      this.IDPWorkflowParamData.pipelineSelection = [];
      for (const pipe of this.workflowData.workflowSequence) {
        let j = 0;
        this.IDPWorkflowParamData.pipelineSelection.push({});
        this.IDPWorkflowParamData.pipelineSelection[i].appSelectionDetails = [];
        for (const appDetails of pipe.applicationDetails) {
          this.IDPWorkflowParamData.pipelineSelection[
            i
          ].appSelectionDetails.push({});
          this.IDPWorkflowParamData.pipelineSelection[i].appSelectionDetails[
            j
          ].pipelineSelectionDetails = [];
          for (const pipeDetails of appDetails.pipelineDetails) {
            this.IDPWorkflowParamData.pipelineSelection[i].appSelectionDetails[
              j
            ].pipelineSelectionDetails.push("on");
            this.workflowData.workflowSequence[i].applicationDetails[
              j
            ].applicationName = pipeDetails.applicationName;
            if (
              this.workflowData.workflowSequence[i].applicationDetails[j]
                .pipelineNameTrigger === undefined
            ) {
              this.workflowData.workflowSequence[i].applicationDetails[
                j
              ].pipelineNameTrigger = pipeDetails.pipelineName;
            } else {
              this.workflowData.workflowSequence[i].applicationDetails[
                j
              ].pipelineNameTrigger =
                this.workflowData.workflowSequence[i].applicationDetails[j]
                  .pipelineNameTrigger +
                ", " +
                pipeDetails.pipelineName;
            }
            this.workflowData.workflowSequenceTemp.push({});
            this.getPipelineList(pipeDetails.applicationName);
            this.workflowData.workflowSequenceTemp[i].applicationName =
              pipeDetails.applicationName;
            this.workflowData.workflowSequenceTemp[i].pipelineName =
              pipeDetails.pipelineName;
            this.workflowData.workflowSequenceTemp[
              i
            ].appPipeNamesDetails = true;
          }
          j++;
        }
        i++;
      }
    } else if (
      this.formStatusObject.operation === "copy" ||
      this.formStatusObject.operation === "edit"
    ) {
      this.newOrEditWorkflow = "old";
      // edit condition
      // //console.log("copy Edit:");

      if (this.resetFlag) {
        try {
          this.workflowData.workflowSequence = JSON.parse(
            JSON.stringify(this.initialSequence)
          );
        } catch (all) {}
        // this.workflowData.workflowSequence = this.initialSequence;
        this.resetFlag = false;
      } else {
        try {
          this.initialSequence = JSON.parse(
            JSON.stringify(this.workflowData.workflowSequence)
          );
        } catch (all) {}
        // this.initialSequence = this.workflowData.workflowSequence;
      }
      this.workflowData.workflowSequenceTemp = [];
      if (
        this.workflowData.workflowSequence !== undefined &&
        this.workflowData.workflowSequence.length > 0
      ) {
        let i = 0;
        let indexI = 0;
        for (const pipe of this.workflowData.workflowSequence) {
          this.workflowData.workflowSequenceTemp.push({
            expand: false,
            applicationDetails: [],
          });
          let indexJ = 0;
          for (const appDetails of pipe.applicationDetails) {
            this.workflowData.workflowSequenceTemp[
              indexI
            ].applicationDetails.push({});
            appDetails.allPipelineNames = [];
            const c = 0;
            for (const pipeDetails of appDetails.pipelineDetails) {
              appDetails.applicationName = pipeDetails.applicationName;
              this.getPipelineListCopyEdit(
                pipeDetails.applicationName,
                pipeDetails.pipelineName,
                indexI,
                indexJ
              );
              this.workflowData.workflowSequenceTemp[indexI].applicationDetails[
                indexJ
              ].applicationName = pipeDetails.applicationName;
              this.workflowData.workflowSequenceTemp[
                indexI
              ].appPipeNamesDetails = true;
              i++;
            }
            indexJ++;
          }
          indexI++;
        }
      }
    } else {
      this.newOrEditWorkflow = "new";
      if (this.workflowData.workflowSequence === undefined || this.resetFlag) {
        this.workflowData.workflowSequence = [];
        this.workflowData.workflowSequenceTemp = [];
        if (this.resetFlag) {
          this.resetFlag = false;
        }
      }
    }
  }
  getPipelineListCopyEdit(appName, pipeName, indexI, indexJ) {
    ////console.log("getting pipeline names");
    ////console.log(appName);

    this.Idprestapi.getPipelineListforWorkflow(appName).then((response) => {
      ////console.log(response);
      const resp = response.json();
      const errorMsg = resp.errorMessage;
      // //console.log("required" + JSON.stringify(resp));
      this.Idpdata.pipelineNames = [];
      const pipeNamesList = [];
      if (resp.resource !== "No Pipelines") {
        const pipData = JSON.parse(resp.resource);
        // //console.log(pipData);
        let c = 1;
        for (const i of pipData.names) {
          if (pipeName === i) {
            this.workflowData.workflowSequence[indexI].applicationDetails[
              indexJ
            ].allPipelineNames.push({ id: c, itemName: pipeName });
          }
          pipeNamesList.push({ id: c, itemName: i });
          c = c + 1;
        }
        this.pipeNames[appName] = pipeNamesList;
      } else {
        this.noPipelines = true;
      }
    });
  }
  getPipelineList(appName) {
    ////console.log("getting pipeline names");
    ////console.log(appName);

    this.Idprestapi.getPipelineListforWorkflow(appName).then((response) => {
      ////console.log(response);
      const resp = response.json();
      const errorMsg = resp.errorMessage;
      // //console.log("required" + JSON.stringify(resp));
      this.Idpdata.pipelineNames = [];
      const pipeNamesList = [];
      if (resp.resource !== "No Pipelines") {
        const pipData = JSON.parse(resp.resource);
        // //console.log(pipData);
        let c = 1;
        for (const i of pipData.names) {
          pipeNamesList.push({ id: c, itemName: i });
          c = c + 1;
        }
        this.pipeNames[appName] = pipeNamesList;
      } else {
        this.noPipelines = true;
      }
    });
  }

  togglePanel(i) {
    if (
      this.workflowData.workflowSequenceTemp[i].expand === undefined ||
      !this.workflowData.workflowSequenceTemp[i].expand
    ) {
      this.workflowData.workflowSequenceTemp[i].expand = true;
    } else {
      this.workflowData.workflowSequenceTemp[i].expand = false;
    }
  }
  addSequence() {
    this.workflowData.workflowSequence.push({
      applicationDetails: [],
      allPipelineNames: [],
    });
    this.workflowData.workflowSequenceTemp.push({
      expand: false,
      applicationDetails: [],
    });
  }
  removeSequence(index) {
    const x = confirm(
      "Are you sure you want to delete the Step No_" + (index + 1) + "?"
    );
    if (x) {
      this.workflowData.workflowSequence.splice(index, 1);
      this.workflowData.workflowSequenceTemp.splice(index, 1);
    }
  }
  addApplication(i) {
    this.workflowData.workflowSequence[i].applicationDetails.push({
      applicationName: "",
      pipelineDetails: [],
      allPipelineNames: [],
    });
    this.workflowData.workflowSequenceTemp[i].applicationDetails.push({
      applicationName: "",
      pipelineDetails: [],
    });
  }
  removeApplication(i, j) {
    const x = confirm(
      "Are you sure you want to remove application: " +
        this.workflowData.workflowSequence[i].applicationDetails[j]
          .applicationName
    );
    if (x) {
      this.workflowData.workflowSequence[i].applicationDetails.splice(j, 1);
    }
  }
  getApplicationNames(data) {
    this.Idprestapi.getFilteredApplicationNames(data).then((response) => {
      try {
        //console.log("Response: " + response);
        if (response) {
          const appDetails = JSON.parse(response.json().resource);
          this.appNames = appDetails.applicationNames;
          // //console.log("Application list " + data + ": " + this.appNames);
        }
      } catch (e) {
        ////console.log(e);
        alert("Failed while getting applications names");
      }
    });
  }

  refreshSaveDetails(i, j) {
    if (
      this.workflowData.workflowSequenceTemp[i].applicationDetails[j]
        .applicationName !== undefined &&
      this.workflowData.workflowSequenceTemp[i].applicationDetails[j]
        .applicationName !== ""
    ) {
      let mesg = "Do you want to reset the pipeline name ";
      mesg =
        mesg +
        this.workflowData.workflowSequence[i].applicationDetails[j]
          .applicationName;
      const con = confirm(mesg);
      if (con) {
        const applicationName = this.workflowData.workflowSequence[i]
          .applicationDetails[j].applicationName;
        this.workflowData.workflowSequence[i].applicationDetails[j] = {
          applicationName: applicationName,
          pipelineDetails: [],
          allPipelineNames: [],
          expand: true,
        };
        this.workflowData.workflowSequenceTemp[i].applicationDetails[j] = {
          applicationName: applicationName,
          pipelineDetails: [],
          allPipelineNames: [],
          expand: true,
        };
      } else {
        this.workflowData.workflowSequence[i].applicationDetails[
          j
        ].applicationName = this.workflowData.workflowSequenceTemp[
          i
        ].applicationDetails[j].applicationName;
      }
    } else {
      this.workflowData.workflowSequenceTemp[i].applicationDetails[
        j
      ].applicationName = this.workflowData.workflowSequence[
        i
      ].applicationDetails[j].applicationName;
    }
  }
  fetchPipelineTriggerDetails(reqData, i, j, k): Promise<any> {
    this.appPipeNamesAvailable = false;
    return this.Idprestapi.triggerJob(reqData).then((response) => {
      try {
        if (response) {
          const result = response.json().resource;
          // //console.log(result);
          if (result !== "{}" && result !== null) {
            this.Idpdata.triggerJobData = JSON.parse(result);
            if (
              this.Idpdata.triggerJobData.releaseNumber !== null &&
              this.Idpdata.triggerJobData.releaseNumber.length !== 0
            ) {
              this.Idpdata.appName = reqData.applicationName;
              this.Idpdata.pipelineName = reqData.pipelineName;
              this.getComponentToLoad(i, j, k);
            } else if (
              this.Idpdata.triggerJobData.roles.indexOf("RELEASE_MANAGER") ===
              -1
            ) {
              this.isReleaseAvailable = false;
              alert(
                "No active releases for this pipeline. Please add releases before adding in workflow sequence"
              );
            } else {
              this.isReleaseAvailable = false;
              alert(
                "No active releases for this pipeline. Please contact the release manager."
              );
            }
          } else {
            this.isReleaseAvailable = false;
            alert("Failed  to get the Trigger Job Details");
          }
        }
      } catch (e) {
        alert("Failed to get trigger details");
        ////console.log(e);
      }
    });
  }

  getComponentToLoad(i, j, k) {
    this.appPipeNamesAvailable = false;
    // //console.log("Change In Index(" + i + ") : " + this.dynamicComponent);
    let componentFactory;
    let viewContainerRef;
    let componentRef;
    componentFactory = this.componentFactoryResolver.resolveComponentFactory(
      TriggerComponent
    );
    viewContainerRef = this.dynamicComponent.viewContainerRef;
    viewContainerRef.clear();
    componentRef = viewContainerRef.createComponent(componentFactory);
    this.workflowData.workflowSequenceTemp[i].componentInstance = <
      TriggerComponent
    >componentRef.instance;
    componentRef.instance.workflowSequenceIndexI = i;
    componentRef.instance.workflowSequenceIndexJ = j;
    componentRef.instance.workflowSequenceIndexK = k;
    componentRef.instance.workflowSequence = this.workflowData.workflowSequence;
    componentRef.instance.getJobParamDetails();
    componentRef.instance.onTriggerDetailsSaved.subscribe((event) => {
      this.modalForTriggerDetails.hide();
    });
    this.appPipeNamesAvailable = true;
  }

  launchTriggerDetailsModal(i, j, k) {
    if (
      this.workflowData.workflowSequence[i].applicationDetails[j]
        .pipelineDetails[k].releaseNumber !== undefined
    ) {
      alert(
        "You are going to update the saved details; if you fill details and save it?"
      );
    }
    this.Idpdata.index = i;
    this.getTriggerDetails(i, j, k);
  }
  getTriggerDetails(i, j, k) {
    this.Idpdata.appName = this.workflowData.workflowSequence[
      i
    ].applicationDetails[j].applicationName;
    this.Idpdata.pipelineName = this.workflowData.workflowSequence[
      i
    ].applicationDetails[j].pipelineDetails[k].pipelineName;
    const reqData = {
      applicationName: this.workflowData.workflowSequence[i].applicationDetails[
        j
      ].applicationName,
      pipelineName: this.workflowData.workflowSequence[i].applicationDetails[j]
        .pipelineDetails[k].pipelineName,
      userName: this.Idpdata.idpUserName,
    };
    this.fetchPipelineTriggerDetails(reqData, i, j, k).then(() => {
      if (this.isReleaseAvailable) {
        this.modalForTriggerDetails.show();
      }
      this.isReleaseAvailable = true;
    });
  }

  deleteSequenceDetails(i, j) {
    const appName = this.workflowData.workflowSequence[i].applicationName;
    const pipeName = this.workflowData.workflowSequence[i].pipelineDetails[j]
      .pipelineName;
    const x = confirm("Are you sure you want to remove these details?");
    if (x) {
      this.Idpdata.workflowData.workflowSequence[i].pipelineDetails[j] = {
        applicationName: appName,
        pipelineName: pipeName,
      };
    }
  }

  validate() {
    let f = true;
    // //console.log(f);
    for (const appArray of this.workflowData.workflowSequence) {
      for (const pipeArray of appArray.applicationDetails) {
        if (pipeArray.pipelineDetails.length === 0) {
          f = false;
          break;
        }
        for (const pipe of pipeArray.pipelineDetails) {
          if (
            pipe.applicationName !== undefined &&
            pipe.pipelineName !== undefined &&
            pipe.releaseNumber !== undefined
          ) {
            continue;
          } else {
            f = false;
            break;
          }
        }
      }
    }
    if (f) {
      // //console.log(f);
      return f;
    } else {
      // //console.log(f);
      return f;
    }
  }
  setFormStatus(data) {
    this.Idpdata.allFormStatus.workflowInfo = data;
  }

  submit() {
    // //console.log("below line ");
    // //console.log(this.workflowData);
    // //console.log(JSON.stringify(this.workflowData));
    if (this.Idpdata.workflowTrigger) {
      this.modalforTriggerRef = this.modalService.show(this.modalforTrigger);
    } else if (this.validate()) {
      if (
        this.Idpdata.allFormStatus.basicInfo &&
        this.Idpdata.allFormStatus.workflowInfo
      ) {
        this.modalforconfirmAlertRef = this.modalService.show(
          this.modalforconfirmAlert
        );
      } else {
        if (
          !this.Idpdata.allFormStatus.basicInfo &&
          this.listToFillFields.indexOf("BasicInfo") === -1
        ) {
          this.listToFillFields.push("BasicInfo");
        }
        if (
          !this.Idpdata.allFormStatus.workflowInfo &&
          this.listToFillFields.indexOf("workflowInfo") === -1
        ) {
          this.listToFillFields.push("workflowInfo");
        }

        this.modalformandatoryFieldsAlertRef = this.modalService.show(
          this.modalformandatoryFieldsAlert
        );
      }
    } else {
      this.modalforAlertDataMissRef = this.modalService.show(
        this.modalforAlertDataMiss
      );
    }
  }

  submitData(modalforconfirmAlertRef) {
    modalforconfirmAlertRef.hide();
    this.loader = "on";
    this.Idpdata.freezeNavBars = true;
    this.Idpdata.data.masterJson["basicInfo"] = this.Idpdata.data.basicInfo;
    this.Idpdata.data.masterJson[
      "pipelines"
    ] = this.workflowData.workflowSequence;

    let data = this.Idpdata.data.masterJson;
    // ////console.log(data);
    data = this.idpencryption.encryptAES(JSON.stringify(data));
    this.Idprestapi.submit(data).then((response) => {
      try {
        const resp = response.json();
        const errorMsg = resp.errorMessage;
        ////console.log(resp);
        this.loader = "off";
        if (errorMsg === null && resp.resource.toLowerCase() === "success") {
          this.message = "success";
          //console.log(this.formStatusObject.operation);
          if (this.formStatusObject.operation !== "edit") {
            const actiondata = {
              applicationName: this.Idpdata.data.masterJson.basicInfo
                .applicationName,
              method: "create",
              pipelineName: this.Idpdata.data.masterJson.basicInfo.pipelineName,
              userName: this.Idpdata.idpUserName,
            };
            this.Idprestapi.sendPipeMail(actiondata);
          } else {
            const actiondata = {
              applicationName: this.Idpdata.data.masterJson.basicInfo
                .applicationName,
              method: "edit",
              pipelineName: this.Idpdata.data.masterJson.basicInfo.pipelineName,
              userName: this.Idpdata.idpUserName,
            };
            this.Idprestapi.sendPipeMail(actiondata);
          }
          this.redirectTo();
        } else {
          this.Idpdata.freezeNavBars = false;
          this.message = "error";
          this.errorMessage = errorMsg;
        }
      } catch (e) {
        alert("Failed while submiting the trigger job");
        ////console.log(e);
      }
    });
  }

  getAppDetails() {
    this.Idprestapi.getApplicationDetails(
      this.Idpdata.data.masterJson.basicInfo.applicationName
    ).then((response) => {
      if (response) {
        const resp = response.json().resource;
        let parsed;
        try {
          parsed = JSON.parse(resp);
          if (parsed) {
            this.Idpdata.application = parsed.appJson;
            // //console.log(this.Idpdata.application);
            // //console.log(this.Idpdata.data);
            this.redirectTo();
          }
        } catch (e) {
          ////console.log(e);
          alert("Failed while getting  the pipeline details");
          this.redirectTo();
        }
      }
    });
  }
  redirectTo() {
    setTimeout(() => {
      this.router.navigate(["/createPipeline/success"]);
    }, 3000);
  }
  resetData() {
    const x = confirm("Are you sure to reset workflow details ?");
    if (x) {
      this.resetFlag = true;
      this.initialize();
      // //console.log("Reset initialize again");
    }
  }

  triggerData(modalRef) {
    modalRef.hide();
    this.loader = "on";
    const requestData = this.IDPWorkflowParamData;
    ////console.log(requestData);
    requestData.build = null;
    requestData.deploy = null;
    requestData.testSelected = "off";
    requestData.envSelected = "";
    this.Idprestapi.triggerJobs(requestData).then((response) => {
      try {
        if (response) {
          const err = response.json().errorMessage;
          if (
            err === null &&
            response.json().resource.toLowerCase() === "success"
          ) {
            this.loader = "off";
            this.message = "success";
            setTimeout(() => {
              this.router.navigate(["/previousConfig/stageviewTrigger"]);
            }, 7000);
          } else {
            this.loader = "off";
            this.message = "error";
            setTimeout(() => {
              this.router.navigate(["/previousConfig"]);
            }, 7000);
          }
        }
      } catch (e) {
        ////console.log(e);
        alert("Failed while triggering ");
      }
    });
  }

  onItemSelect(item: any, i, j) {
    // ////console.log(item);
    if (
      !this.workflowData.workflowSequence[i].applicationDetails[j]
        .pipelineDetails
    ) {
      this.workflowData.workflowSequence[i].applicationDetails[
        j
      ].pipelineDetails = [];
    }
    this.workflowData.workflowSequence[i].applicationDetails[
      j
    ].pipelineDetails.push({ pipelineName: item.itemName });
  }
  OnItemDeSelect(item: any, i, j) {
    const index = this.workflowData.workflowSequence[i].applicationDetails[
      j
    ].pipelineDetails.findIndex(
      (pipeline) => pipeline.pipelineName === item.itemName
    );
    this.workflowData.workflowSequence[i].applicationDetails[
      j
    ].pipelineDetails.splice(index, 1);
  }
  onSelectAll(items: any, i, j) {
    if (
      !this.workflowData.workflowSequence[i].applicationDetails[j]
        .pipelineDetails
    ) {
      this.workflowData.workflowSequence[i].applicationDetails[
        j
      ].pipelineDetails = [];
    }
    for (const item of items) {
      const index = this.workflowData.workflowSequence[i].applicationDetails[
        j
      ].pipelineDetails.findIndex(
        (pipeline) => pipeline.pipelineName === item.itemName
      );
      if (index === -1) {
        this.workflowData.workflowSequence[i].applicationDetails[
          j
        ].pipelineDetails.push({ pipelineName: item.itemName });
      }
    }
  }
  onDeSelectAll(items: any, i, j) {
    const x = confirm(
      "Are you sure you want to reset all the pipeline names for " +
        this.workflowData.workflowSequence[i].applicationDetails[j]
          .applicationName +
        " ?"
    );
    if (x) {
      this.workflowData.workflowSequence[i].applicationDetails[
        j
      ].pipelineDetails = [];
    }
  }
}
