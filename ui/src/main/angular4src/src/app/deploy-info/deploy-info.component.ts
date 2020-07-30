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
import { Validators } from "@angular/forms";
import { FormControl, FormGroup } from "@angular/forms";
import { HttpClient } from "@angular/common/http";
import { Renderer } from "@angular/core";
import { ViewChild } from "@angular/core";
import { IDPEncryption } from "../idpencryption.service";
import { IdpSubmitService } from "../idpsubmit.service";
import { ParentFormConnectComponent } from "../parent-form-connect/parent-form-connect.component";
import { BsModalService } from "ngx-bootstrap/modal";
import { BsModalRef } from "ngx-bootstrap/modal/bs-modal-ref.service";
import * as _ from "lodash";

@Component({
  selector: "app-deploy-info",
  templateUrl: "./deploy-info.component.html",
  styleUrls: ["./deploy-info.component.css"],
})
export class DeployInfoComponent implements OnInit {
  env: any = {};
  envtoolList: any = [];
  scriptList: any = [];
  buildList: any = [];
  deploymentList: any = [];
  data: any = {};
  testInfo: any = this.IdpdataService.data.testInfo;
  deployInfo: any = this.IdpService.copy(this.IdpdataService.data.deployInfo);
  buildInfo: any = this.IdpdataService.data.buildInfo;
  code: any = this.IdpdataService.data.code;
  list: any = [];
  radio: any = "";
  runScriptFlag: any = "off";
  envProvFlag: any = "off";
  deployContainerFlag: any = "off";
  deployDBFlag: any = "off";
  deployDb: any = "off";
  tempObject: any = this.IdpdataService.data.checkboxStatus.deployInfo;
  p: boolean;
  dbList: any;
  dataJson: any;
  geFlag: boolean;
  containerList: any;
  deployToContainer: any = {};
  deployDatabase: any = {};
  formStatusObject = this.IdpdataService.data.formStatus;
  copyEditOperation = this.IdpdataService.copyEditOperation;
  ssoId: any;
  isDockerRegistry = this.IdpdataService.isDockerRegistry;
  ssoName: any;
  serverIndex: any;
  srfServerIndex: any;
  module: String;
  s: any;
  outerIndex: any;
  innerIndex: any;
  msg: any;
  insightsFlag = this.IdpdataService.insightsFlag;
  cloudDeployFlag = this.IdpdataService.cloudDeployFlag;
  loc: any;
  passphrasefrombuild: any;
  Artifactvalue: any;
  buildTool: any;
  importexporttype: any;
  administrator: any;
  password: any;
  fileName: "";
  pairName: "";
  srcEnvName: "";
  port: any;
  hostName: any;
  userName: any;
  sourceFolder: any;
  sourceRepo: any;
  targetFolder: any;
  targetFolderShared;
  artifactIndex: any;
  targetRepo: any;
  platform: any;
  sourceFolderShared: any;
  checkStepAdd = false;
  dbDeployRollbackTypeList: any;
  server: any;
  listToFillFields: any = [];
  index: any;
  indexI: any = -1;
  indexJ: any = -1;
  deployOperations = [
    { name: "EAR Deploy", value: "earDeploy" },
    { name: "Start Weblogic", value: "startWeb" },
    { name: "Stop Weblogic", value: "stopWeb" },
    { name: "Update DBC", value: "updateDBC" },
    { name: "XML Deploy", value: "xmlDeploy" },
  ];
  deployAemOperations = [
    { name: "Upload & Install", value: "packageUploadInstall" },
    { name: "Upload Only", value: "packageUploadNotInstall" },
    { name: "Rebuild", value: "packageRebuild" },
    { name: "Uninstall", value: "packageUninstall" },
    { name: "Delete", value: "packageDelete" },
  ];

  @ViewChild("modalforAlertDeploy") modalforAlertDeploy;
  @ViewChild("modalforDelDeployStep") modalforDelDeployStep;
  @ViewChild("modalforDelField") modalforDelField;
  @ViewChild("modalforDelAllField") modalforDelAllField;
  @ViewChild("modalforAlertDataMiss") modalforAlertDataMiss;
  @ViewChild("modalforDelAntProperties") modalforDelAntProperties;
  @ViewChild("modalforDotNet") modalforDotNet;
  @ViewChild("modalformandatoryFieldsDeployAlert")
  modalformandatoryFieldsDeployAlert;
  @ViewChild("modalforconfirmDeployAlert") modalforconfirmDeployAlert;
  @ViewChild("modalforMaximo") modalforMaximo;

  public rapidPage: any = {
    pageRows: [
      {
        sections: [
          {
            sectionRows: [
              { secRowColumns: [] },
              { secRowColumns: [{ colName: "users" }] },
              { secRowColumns: [{ colName: "sample" }] },
            ],
            width: 0,
          },
        ],
      },
    ],
    pageName: "DefaultPage",
    pageLayout: "DEFAULT_LAYOUT",
    editMode: true,
  };
  alertDeployModalRef: BsModalRef;
  missingDataModalRef: BsModalRef;
  dotnetAlertModalRef: BsModalRef;
  delDeployStepModalRef: BsModalRef;
  delFiledModalRef: BsModalRef;
  delAllFieldsModalRef: BsModalRef;
  delAntPropertyModalRef: BsModalRef;
  confirmSubmissionModalRef: BsModalRef;
  mandatoryMissingModalRef: BsModalRef;
  modalForMaximoRef: BsModalRef;
  deployStepsCollapseStatus: Array<Array<any>> = [];
  ngOnInit() {
    if (this.IdpdataService.data.formStatus.basicInfo.appNameStatus === "0") {
      this.TriggerAlert({
        msg: "Application Name",
        loc: "/createConfig/basicInfo",
      });
    } else {
      if (
        this.IdpdataService.data.formStatus.buildInfo.buildToolStatus === "0"
      ) {
        this.msg = "Technology Type";
        this.loc = "/createConfig/codeInfo";
        // this.IdpdataService.data.p=true;
        this.TriggerAlert({
          msg: "Technology Type",
          loc: "/createConfig/codeInfo",
        });
      }
    }
    if (
      this.buildInfo.buildtool === "maximo" &&
      this.IdpdataService.data.basicInfo.buildServerOS !== "windows"
    ) {
      this.modalForMaximoRef = this.modalService.show(this.modalforMaximo);
    }
    const applName = this.IdpdataService.data.basicInfo.applicationName;
  }

  /* Constructor */
  constructor(
    public IdpdataService: IdpdataService,
    private IdpService: IdpService,
    private IdpSubmitService: IdpSubmitService,
    private IdprestapiService: IdprestapiService,
    private idpencryption: IDPEncryption,
    private modalService: BsModalService,
    private httpClient: HttpClient,
    private router: Router
  ) {
    this.IdpdataService.data.deployInfo = this.deployInfo;
    this.IdpdataService.data.buildInfo = this.buildInfo;
    this.init();
    this.scriptList = [
      { name: "ANT Script", value: "ant" },
      { name: "Shell Script", value: "shellScript" },
      { name: "Batch Script", value: "batchScript" },
      { name: "Powershell Script", value: "powerShell" },
      { name: "SSH Execution", value: "sshExecution" },
    ];
    this.deploymentList = [{ name: "Mule Server", value: "muleServer" }];
    this.envtoolList = [{ name: "Ansible", value: "ansiblescript" }];
    if (
      this.buildInfo.buildtool === "maven" ||
      this.buildInfo.buildtool === "ant" ||
      this.buildInfo.buildtool === "java_gradle" ||
      this.buildInfo.buildtool === "catalog"
    ) {
      this.containerList = this.IdprestapiService.getIDPDropdownProperties().containerList;
    }
    if (this.buildInfo.buildtool === "general") {
      this.scriptList = [
        { name: "ANT Script", value: "ant" },
        { name: "Shell Script", value: "shellScript" },
        { name: "Batch Script", value: "batchScript" },
        { name: "Powershell Script", value: "powerShell" },
      ];
    }

    if (this.buildInfo.buildtool === "msBuild") {
      this.containerList = [
        {
          name: "IIS",
          value: "iis",
        },
      ];
    }
    if (this.buildInfo.buildtool === "iib") {
      this.buildList = [{ name: "IIB", value: "iib" }];
    }
    if (this.buildInfo.buildtool === "maximo") {
      this.containerList = [
        {
          name: "WebLogic",
          value: "weblogic",
        },
      ];
    }
    if (
      this.buildInfo !== undefined &&
      this.buildInfo !== "" &&
      this.buildInfo != null
    ) {
      if (this.buildInfo.buildtool === "reactjs") {
        this.server = {
          osserver: "",
        };
        this.server.osserver = this.IdpdataService.data.basicInfo.buildServerOS;
      }
    }
    if (
      this.formStatusObject.operation === "copy" ||
      this.formStatusObject.operation === "edit"
    ) {
      this.buildTool = this.buildInfo.buildtool;
      this.checkCheckBox();
    }
  }
  uploadForm = new FormGroup({
    file1: new FormControl(),
  });
  public fileUploader(event, envIndex, stepIndex) {
    const elem = event.target;
    if (elem.files.length > 0) {
      let reader = new FileReader();
      reader.onload = () => {
        // this 'text' is the content of the file
        var text = reader.result;
        this.deployInfo.deployEnv[envIndex].deploySteps[
          stepIndex
        ].cloudData = JSON.parse(text);
      };
      reader.readAsText(elem.files[0]);
    }

    // ...
  }

  setFormStatus(data) {
    this.IdpdataService.allFormStatus.deployInfo = data;
  }

  get rapidPageValue() {
    return JSON.stringify(this.rapidPage, null, 2);
  }
  set rapidPageValue(v) {
    try {
      this.rapidPage = JSON.parse(v);
    } catch (e) {
      //console.log("error occored while you were typing the JSON");
    }
  }

  TriggerAlert(content) {
    this.alertDeployModalRef = this.modalService.show(this.modalforAlertDeploy);
    this.alertDeployModalRef.content = content;
  }

  redirectTo(modalRef) {
    modalRef.hide();
    if (modalRef.content.loc) {
      this.router.navigate([modalRef.content.loc]);
    }
  }

  redirectToBasicInfo(modalRef) {
    modalRef.hide();
    this.router.navigate(["/createConfig/basicInfo"]);
  }

  init() {
    this.deployInfo.deployEnv.forEach(() => {
      this.deployStepsCollapseStatus.push([]);
    });
    this.dbList = [
      { name: "MySQL", value: "MySQL" },
      { name: "PostgreSQL", value: "PostgresSQL" },
      { name: "Derby", value: "Derby" },
      { name: "Hypersonic", value: "Hypersonic" },
      { name: "H2", value: "H2" },
      { name: "Oracle", value: "oracle" },
      { name: "MsSQL", value: "mssql" },
    ];
    this.dbDeployRollbackTypeList = [
      { name: "byTag", value: "byTag" },
      { name: "byChangeSet", value: "byChangeSet" },
      { name: "byHours", value: "byHours" },
      { name: "byDate", value: "byDate" },
    ];
  }

  checkStep(envIndex) {
    return "on";
  }

  clearStep(envIndex) {
    return "off";
  }

  /* To check if Deploy step is added or not */
  checkDeployOperation(i, j) {
    if (
      this.deployInfo.deployEnv[i].deploySteps[j].deployOperation === undefined
    ) {
      this.deployInfo.deployEnv[i].deploySteps[j].deployOperation = "";
    }
    this.deployInfo.deployEnv[i].deploySteps[j].deployFuntion = "";
  }
  checkOracleDeployOperation(i, j) {
    if (this.deployInfo.deployEnv[i].deploySteps[j].deployTech === undefined)
      this.deployInfo.deployEnv[i].deploySteps[j].deployTech = "";
    //this.deployInfo.deployEnv[i].deploySteps[j].deployFuntion="";
  }
  /* Checks for duplication of step name
   * and alerts
   */
  checkStepName(i, j) {
    for (let x = 0; x < this.deployInfo.deployEnv.length; x++) {
      if (
        this.deployInfo.deployEnv[x] !== undefined &&
        this.deployInfo.deployEnv[x].deploySteps !== undefined
      ) {
        for (
          let y = 0;
          y < this.deployInfo.deployEnv[x].deploySteps.length;
          y++
        ) {
          if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
            this.tempObject.deployEnv[i].deploySteps[j] = {};
          }
          if (
            j !== y &&
            this.deployInfo.deployEnv[x].deploySteps[y].stepName !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].stepName !==
              undefined &&
            this.deployInfo.deployEnv[x].deploySteps[y].stepName ===
              this.deployInfo.deployEnv[i].deploySteps[j].stepName
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].msgStep =
              "Step Name must be unique.";
            break;
          } else {
            this.tempObject.deployEnv[i].deploySteps[j].msgStep = "";
          }
        }
      }
    }
  }

  printaa() {
    // if(this.env.runScript.scriptType===''){
    // //console.log( this.env);
    // }
  }

  /* Options for Java packaging */
  optionList() {
    if (this.tempObject.warList === undefined) {
      this.tempObject.warList = [];
    }
    for (let i = 0; i < this.buildInfo.modules.length; i++) {
      if (
        this.buildInfo.modules[i].moduleName !== undefined &&
        this.buildInfo.modules[i].moduleName != null &&
        this.buildInfo.modules[i].moduleName !== "" &&
        this.buildInfo.modules[i].warPackaging !== "" &&
        this.buildInfo.modules[i].warPackaging !== null &&
        this.buildInfo.modules[i].warPackaging !== undefined
      ) {
        if (
          this.tempObject.warList.indexOf(
            this.buildInfo.modules[i].moduleName
          ) === -1
        ) {
          this.tempObject.warList.push(this.buildInfo.modules[i].moduleName);
        }
      }
    }
    return true;
  }
  clearTempObject(envIndex) {
    if (this.deployInfo.deployEnv[envIndex].envFlag === "off") {
      if (
        this.tempObject.deployEnv !== undefined &&
        this.tempObject.deployEnv[envIndex] !== undefined
      ) {
        this.tempObject.deployEnv[envIndex].deploySteps = [];
      }
    }
  }
  clearObjFlag(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].dbName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbServName = "";
    this.tempObject.deployEnv[i].deploySteps[j].deployObjFlag = "off";
    this.clearIntFlag(i, j);
    return "off";
  }
  clearIntFlag(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].dbUName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbPwd = "";
    this.tempObject.deployEnv[i].deploySteps[j].isInternetFlag = "off";
    return "off";
  }

  tomcatRestartoff(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].tomUname = "";
    this.deployInfo.deployEnv[i].deploySteps[j].tomPwd = "";
    this.deployInfo.deployEnv[i].deploySteps[j].tomHost = "";
    this.deployInfo.deployEnv[i].deploySteps[j].tomPort = "";
    this.deployInfo.deployEnv[i].deploySteps[j].appName = "";
    return "off";
  }
  tomcatRestarton(i, j) {
    this.tempObject.deployEnv[i].deploySteps[j].tomcatRestart = "on";
    return "on";
  }
  /* Addition of deploy step */
  addDeployStep(key) {
    if (this.deployInfo.deployEnv === undefined) {
      this.deployInfo.deployEnv = [];
      //console.log("chck");
    }
    if (this.tempObject.deployEnv === undefined) {
      this.tempObject.deployEnv = [];
    }
    if (this.deployInfo.deployEnv[key] === undefined) {
      this.deployInfo.deployEnv[key] = {};
    }
    if (this.tempObject.deployEnv[key] === undefined) {
      this.tempObject.deployEnv[key] = {};
    }
    if (this.deployInfo.deployEnv[key].deploySteps === undefined) {
      this.deployInfo.deployEnv[key].deploySteps = [];
    }
    if (this.tempObject.deployEnv[key].deploySteps === undefined) {
      this.tempObject.deployEnv[key].deploySteps = [];
    }

    if (this.tempObject.deployEnv === undefined) {
      this.tempObject.deployEnv = [];
    }
    this.deployToContainer = {
      containerName: "",
      updateDB: "",
      rollbackStrategy: "",
      fileName: "",
      pairName: "",
      srcEnvName: "",
      userName: "",
      password: "",
      aemPort: "",
      aemProxy: "",
      deployAemOperations: "",
      groupName: "",
      contextPath: "",
      proxyun: "",
      proxypw: "",
      appName: "",
    };
    this.deployDatabase = {
      restorusername: "",
      restorpassword: "",
      dbusername: "",
      dbpassword: "",
      script: "",
    };
    this.deployInfo.deployEnv[key].deploySteps.push({
      deployToContainer: this.deployToContainer,
      deployDatabase: this.deployDatabase,
      insightsFlag: "off",
      insightsKafkaUrl: "",
      insightsKafkaTopic: "",
      deploymentOption: "",
      runScript: {
        scriptType: "",
        scriptFilePath: "",
        targets: "",
      },
      buildScript: {
        buildType: "",
        targets: "",
        buildFilePath: "",
      },
      cloudDeployment: {},
      proxy: {
        username: "",
        password: "",
        host: "",
        enabled: "",
        port: "",
      },
      deployOS: "",
      abortScript: { scriptType: "" },
      platform: "",
      deployOperation: "",
      envProv: {
        toolType: "",
        scriptFilePath: "",
      },
    });

    this.tempObject.deployEnv[key].deploySteps.push({});
  }

  /* Removal of deploy step */
  removeDeployStep(outerIndex, innerIndex) {
    this.delDeployStepModalRef = this.modalService.show(
      this.modalforDelDeployStep
    );
    this.delDeployStepModalRef.content = {
      innerIndex: innerIndex,
      outerIndex: outerIndex,
    };
  }

  /* Clears environment config */
  clearcfg(i, j) {
    this.tempObject.deployEnv[i].deploySteps[j].envconfigNameCheckBox = [];
    this.deployInfo.deployEnv[i].deploySteps[j].environmentProvDetails = [];
    return "off";
  }
  clearconfig(i, j, k) {
    const idx = this.deployInfo.deployEnv[i].deploySteps[
      j
    ].environmentProvDetails.indexOf(
      this.IdpdataService.application.environmentProvDetails[k]
    );
    if (idx > -1) {
      this.deployInfo.deployEnv[i].deploySteps[j].environmentProvDetails.splice(
        idx,
        1
      );
    } else {
      this.deployInfo.deployEnv[i].deploySteps[j].environmentProvDetails.push(
        this.IdpdataService.application.environmentProvDetails[k]
      );
    }
    //console.log(
    //   this.deployInfo.deployEnv[i].deploySteps[j].environmentProvDetails
    // );

    return "off";
  }

  inscfgMain(i, j) {
    this.tempObject.deployEnv[i].deploySteps[j].envconfigNameCheckBox = [];
    this.deployInfo.deployEnv[i].deploySteps[j].environmentProvDetails = [];
    return "on";
  }

  insconfig(i, j, k) {
    const idx = this.deployInfo.deployEnv[i].deploySteps[
      j
    ].environmentProvDetails.indexOf(
      this.IdpdataService.application.environmentProvDetails[k]
    );
    if (idx > -1) {
      this.deployInfo.deployEnv[i].deploySteps[j].environmentProvDetails.splice(
        idx,
        1
      );
    } else {
      this.deployInfo.deployEnv[i].deploySteps[j].environmentProvDetails.push(
        this.IdpdataService.application.environmentProvDetails[k]
      );
    }
    //console.log(
    //   this.deployInfo.deployEnv[i].deploySteps[j].environmentProvDetails
    // );

    return "on";
  }
  confirmDeleteDeployStep(modelRef) {
    this.deployInfo.deployEnv[modelRef.content.outerIndex].deploySteps.splice(
      modelRef.content.innerIndex,
      1
    );
    this.tempObject.deployEnv[modelRef.content.outerIndex].deploySteps.splice(
      modelRef.content.innerIndex,
      1
    );
    modelRef.hide();
  }
  checkCheckBox() {
    if (
      this.IdpdataService.data.buildInfo !== undefined &&
      this.IdpdataService.data.buildInfo.modules !== undefined &&
      this.IdpdataService.data.buildInfo.modules[0] !== undefined &&
      this.IdpdataService.data.buildInfo.modules[0].unitTesting === "on"
    ) {
      this.IdpdataService.unitTest = true;
    }

    if (this.tempObject.deployEnv === undefined) {
      this.tempObject.deployEnv = [];
    }
    if (this.tempObject.deployEnv.deploySteps === undefined) {
      this.tempObject.deployEnv.push({});
      this.tempObject.deployEnv.deploySteps = [];
    }
    for (let i = 0; i < this.deployInfo.deployEnv.length; i++) {
      const envFlag = "off";
      if (this.tempObject.deployEnv[i] === undefined) {
        this.tempObject.deployEnv[i] = {};
      }

      if (this.tempObject.deployEnv[i].deploySteps === undefined) {
        this.tempObject.deployEnv[i].deploySteps = [];
      }
      if (this.deployInfo.deployEnv[i].deploySteps !== undefined) {
        if (this.deployInfo.deployEnv[i].deploySteps.length !== 0) {
          this.deployInfo.deployEnv[i].envFlag = "on";
        }
        for (
          let j = 0;
          j < this.deployInfo.deployEnv[i].deploySteps.length;
          j++
        ) {
          const runScriptFlag = "off";
          const deployContainerFlag = "off";
          if (
            this.deployInfo.deployEnv[i].deploySteps[j].s3location !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].s3location !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].s3Loc = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].s3Loc = "off";
          }
          const deployDBFlag = "off";
          if (
            this.deployInfo.deployEnv[i].deploySteps[j].runScript !== "" &&
            this.deployInfo.deployEnv[i].deploySteps[j].runScript !== undefined
          ) {
            if (
              this.deployInfo.deployEnv[i].deploySteps[j].runScript
                .scriptType !== "" &&
              this.deployInfo.deployEnv[i].deploySteps[j].runScript
                .scriptType !== undefined
            ) {
              if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
                ////console.log(this.tempObject.deployEnv[i].deploySteps[j]);
                this.tempObject.deployEnv[i].deploySteps[j] = {};
              }
              ////console.log(this.tempObject.deployEnv[i].deploySteps[j]);
              this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag = "on";
              /* setting SSH on, if inputs are provided */
              if (
                this.deployInfo.deployEnv[i].deploySteps[j].runScript
                  .scriptType === "sshExecution"
              ) {
                if (
                  this.deployInfo.deployEnv[i].deploySteps[j].runScript
                    .pathToFiles !== "" &&
                  this.deployInfo.deployEnv[i].deploySteps[j].runScript
                    .pathToFiles !== undefined
                ) {
                  this.tempObject.deployEnv[i].deploySteps[
                    j
                  ].transferFilesFlag = "on";
                }
              }

              /* Setting Ant execution on, if ant targets, xml file are provided */
              if (
                this.deployInfo.deployEnv[i].deploySteps[j].runScript
                  .scriptType === "ant"
              ) {
                if (
                  this.deployInfo.deployEnv[i].deploySteps[j].runScript &&
                  this.deployInfo.deployEnv[i].deploySteps[j].runScript
                    .antPropertiesArr &&
                  this.deployInfo.deployEnv[i].deploySteps[j].runScript
                    .antPropertiesArr[0] &&
                  this.deployInfo.deployEnv[i].deploySteps[j].runScript
                    .antPropertiesArr[0].antKey !== undefined &&
                  this.deployInfo.deployEnv[i].deploySteps[j].runScript
                    .antPropertiesArr[0].antValue !== undefined
                ) {
                  this.deployInfo.deployEnv[i].deploySteps[
                    j
                  ].runScript.antProperty1 = "on";
                }
                if (
                  this.deployInfo.deployEnv[i].deploySteps[j].runScript &&
                  this.deployInfo.deployEnv[i].deploySteps[j].runScript
                    .javaOptions !== undefined
                ) {
                  this.deployInfo.deployEnv[i].deploySteps[
                    j
                  ].runScript.antJavaOption1 = "on";
                }
              }
            } else {
              if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
                this.tempObject.deployEnv[i].deploySteps[j] = {};
              }
              this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag = "off";
            }
          } else {
            this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag = "off";
          }
          //env provision start
          if (
            this.buildInfo.buildtool !== "SapNonCharm" ||
            this.buildInfo.buildtool !== "sapcharm"
          ) {
            if (
              this.deployInfo.deployEnv[i].deploySteps[j].envProv ===
                undefined ||
              this.deployInfo.deployEnv[i].deploySteps[j].envProv == null ||
              this.deployInfo.deployEnv[i].deploySteps[j].envProv.toolType ===
                undefined ||
              this.deployInfo.deployEnv[i].deploySteps[j].envProv.toolType ===
                ""
            ) {
              this.tempObject.deployEnv[i].deploySteps[j].envProvFlag = "off";
              this.deployInfo.deployEnv[i].deploySteps[j].envProv = {
                toolType: "",
                scriptFilePath: "",
              };
            } else {
              this.tempObject.deployEnv[i].deploySteps[j].envProvFlag = "on";
            }
          } else {
            //this.deployInfo.deployEnv[i].deploySteps[j].envProv = {toolType:"",scriptFilePath:""};
            this.tempObject.deployEnv[i].deploySteps[j].envProvFlag = "off";
          }
          //envprovison end

          if (
            this.buildInfo.buildtool === "reactjs" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .hostName !== ""
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].folderCopyFlag = "on";
          }
          if (
            this.buildInfo.buildtool === "bigData" &&
            ((this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .pigUsername !== "" &&
              this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
                .pigUsername !== undefined) ||
              (this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
                .hiveServerName !== "" &&
                this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
                  .hiveServerName !== undefined) ||
              (this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
                .scalaServerName !== "" &&
                this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
                  .scalaServerName !== undefined))
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].bigDataFlag = "on";
          }
          if (
            this.buildInfo.buildtool === "bigData" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .pigUsername !== "" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .pigUsername !== undefined
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].pig = "on";
            if (
              this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
                .pigScr !== "" &&
              this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
                .pigScr !== undefined
            ) {
              this.tempObject.deployEnv[i].deploySteps[j].pigExec = "on";
            }
          }
          if (
            this.buildInfo.buildtool === "bigData" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .hiveServerName !== "" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .hiveServerName !== undefined
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].hive = "on";
            if (
              this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
                .hiveScr !== "" &&
              this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
                .hiveScr !== undefined
            ) {
              this.tempObject.deployEnv[i].deploySteps[j].hiveExec = "on";
            }
          }
          if (
            this.buildInfo.buildtool === "bigData" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .scalaServerName !== "" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .scalaServerName !== undefined
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].scala = "on";
            if (
              this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
                .scalaUip !== "" &&
              this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
                .scalaUip !== undefined
            ) {
              this.tempObject.deployEnv[i].deploySteps[j].scalaExec = "on";
            }
          }
          // tibco deployment changes
          if (
            this.buildInfo.buildtool === "tibco" &&
            this.deployInfo.deployEnv[i].deploySteps[j].pathToFiles !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].pathToFiles !== null &&
            this.deployInfo.deployEnv[i].deploySteps[j].pathToFiles !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].tibcoDeploy = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].tibcoDeploy = "off";
          }
          // ios deployment changes
          if (
            this.buildInfo.buildtool === "iOS(Swift)" &&
            this.deployInfo.deployEnv[i].deploySteps[j].iosDataPath !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].iosDataPath !== null &&
            this.deployInfo.deployEnv[i].deploySteps[j].iosDataPath !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].iosDeploy = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].iosDeploy = "off";
          }
          // pega deployment changes
          if (
            this.buildInfo.buildtool === "pega" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .pegaDBDataPort !== "" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .pegaDBDataPort !== undefined
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].dataSchema = "on";
          }
          if (
            this.buildInfo.buildtool === "pega" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .tomUname !== "" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .tomUname !== undefined
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].tomcatRestart = "on";
          }

          //siebel

          if (
            this.buildTool === "siebel" &&
            this.deployInfo.deployEnv[i].deploySteps[j].deployUserName !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].deployUserName !==
              null &&
            this.deployInfo.deployEnv[i].deploySteps[j].deployUserName !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].automationScript = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].automationScript =
              "off";
          }

          //oracle deployment steps
          if (
            this.buildInfo.buildtool === "oracleEBS" &&
            this.deployInfo.deployEnv[i].deploySteps[j].sqlFileName !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].sqlFileName !== null &&
            this.deployInfo.deployEnv[i].deploySteps[j].sqlFileName !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.deployInfo.deployEnv[i].deploySteps[j].deployTech =
              "ebsoracle";
            this.tempObject.deployEnv[i].deploySteps[j].sqlFilesPackages = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].sqlFilesPackages =
              "off";
          }

          if (
            this.buildInfo.buildtool === "oracleEBS" &&
            this.deployInfo.deployEnv[i].deploySteps[j].sqlFolder !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].sqlFolder !== null &&
            this.deployInfo.deployEnv[i].deploySteps[j].sqlFolder !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.deployInfo.deployEnv[i].deploySteps[j].deployTech =
              "otmoracle";
            this.tempObject.deployEnv[i].deploySteps[j].sqlFilesPackagesOTM =
              "on";
            //this.tempObject.deployEnv[i].deploySteps[j].deployTech="otmoracle";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].sqlFilesPackagesOTM =
              "off";
          }

          if (
            this.buildInfo.buildtool === "oracleEBS" &&
            this.deployInfo.deployEnv[i].deploySteps[j].reportName !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].reportName !== null &&
            this.deployInfo.deployEnv[i].deploySteps[j].reportName !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.deployInfo.deployEnv[i].deploySteps[j].deployTech =
              "ebsoracle";
            this.tempObject.deployEnv[i].deploySteps[j].report = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].report = "off";
          }

          if (
            this.buildInfo.buildtool === "oracleEBS" &&
            this.deployInfo.deployEnv[i].deploySteps[j].oafFolderName !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].oafFolderName !==
              null &&
            this.deployInfo.deployEnv[i].deploySteps[j].oafFolderName !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.deployInfo.deployEnv[i].deploySteps[j].deployTech =
              "ebsoracle";
            this.tempObject.deployEnv[i].deploySteps[j].oafObject = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].oafObject = "off";
          }

          if (
            this.buildInfo.buildtool === "oracleEBS" &&
            this.deployInfo.deployEnv[i].deploySteps[j].formName !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].formName !== null &&
            this.deployInfo.deployEnv[i].deploySteps[j].formName !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.deployInfo.deployEnv[i].deploySteps[j].deployTech =
              "ebsoracle";
            this.tempObject.deployEnv[i].deploySteps[j].forms = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].forms = "off";
          }

          if (
            this.buildInfo.buildtool === "oracleEBS" &&
            this.deployInfo.deployEnv[i].deploySteps[j].hostCtlfile !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].hostCtlfile !== null &&
            this.deployInfo.deployEnv[i].deploySteps[j].hostCtlfile !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.deployInfo.deployEnv[i].deploySteps[j].deployTech =
              "ebsoracle";
            this.tempObject.deployEnv[i].deploySteps[j].hostCtl = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].hostCtl = "off";
          }

          if (
            this.buildInfo.buildtool === "oracleEBS" &&
            this.deployInfo.deployEnv[i].deploySteps[j].oaMediaFile !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].oaMediaFile !== null &&
            this.deployInfo.deployEnv[i].deploySteps[j].oaMediaFile !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.deployInfo.deployEnv[i].deploySteps[j].deployTech =
              "ebsoracle";
            this.tempObject.deployEnv[i].deploySteps[j].oaMedia = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].oaMedia = "off";
          }

          if (
            this.buildInfo.buildtool === "oracleEBS" &&
            this.deployInfo.deployEnv[i].deploySteps[j].workFlowName !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].workFlowName !== null &&
            this.deployInfo.deployEnv[i].deploySteps[j].workFlowName !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.deployInfo.deployEnv[i].deploySteps[j].deployTech =
              "ebsoracle";
            this.tempObject.deployEnv[i].deploySteps[j].workFlow = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].workFlow = "off";
          }

          if (
            this.buildInfo.buildtool === "oracleEBS" &&
            this.deployInfo.deployEnv[i].deploySteps[j].customTopPath !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].customTopPath !==
              null &&
            this.deployInfo.deployEnv[i].deploySteps[j].customTopPath !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.deployInfo.deployEnv[i].deploySteps[j].deployTech =
              "ebsoracle";
            this.tempObject.deployEnv[i].deploySteps[j].aolScripts = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] == undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].aolScripts = "off";
          }

          if (
            this.deployInfo.deployEnv[i].deploySteps[j]
              .environmentProvDetails !== undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].environmentProvDetails
              .length > 0
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].envconfigAbortCheckBox =
              "on";
            this.tempObject.deployEnv[i].deploySteps[
              j
            ].envconfigNameCheckBox = [];
            for (
              let s = 0;
              s <
              this.deployInfo.deployEnv[i].deploySteps[j].environmentProvDetails
                .length;
              s++
            ) {
              for (
                let f = 0;
                f <
                this.IdpdataService.application.environmentProvDetails.length;
                f++
              ) {
                if (
                  this.IdpdataService.application.environmentProvDetails[f]
                    .environmentProvName ===
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails[s].environmentProvName
                ) {
                  this.tempObject.deployEnv[i].deploySteps[
                    j
                  ].envconfigNameCheckBox[f] = "on";
                  this.deployInfo.deployEnv[i].deploySteps[
                    j
                  ].environmentProvDetails[
                    s
                  ] = this.IdpdataService.application.environmentProvDetails[f];
                  break;
                }
              }
            }
          }
          if (
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .cloudDeploymentFlag !== undefined &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .cloudDeploymentFlag === "on"
          ) {
            // //console.log(this.IdpdataService.data.deployInfo);
            const deployData1 = this.IdpdataService.data.basicInfo.deployData1;
            // //console.log(this.IdpdataService.data.deployInfo.deployEnv[i]);
            if (deployData1 !== undefined) {
              const deployData = JSON.parse(deployData1);
              for (let l = 0; l < deployData.env.length; l++) {
                if (
                  deployData.env[l].name ===
                  this.IdpdataService.data.deployInfo.deployEnv[i].envName
                ) {
                  for (let k = 0; k < deployData.env[l].step.length; k++) {
                    if (deployData.env[l].step[k].index - 1 === j) {
                      this.IdpdataService.data.deployInfo.deployEnv[
                        i
                      ].deploySteps[j].dataRight = JSON.parse(
                        this.idpencryption.decryptAES(
                          deployData.env[l].step[k].pipelineInfo
                        )
                      );
                      //console.log(
                      //   this.IdpdataService.data.deployInfo.deployEnv[i]
                      //     .deploySteps[j].dataRight +
                      //     "  " +
                      //     this.idpencryption.decryptAES(
                      //       deployData.env[l].step[k].pipelineInfo
                      //     )
                      // );
                    }
                  }
                }
              }
            }
          }
          /* MSBUILD Deployment */
          if (
            this.buildInfo.buildtool === "msBuild" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .appPackName !== "" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .appPackName !== undefined
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].deployFabFlag = "on";
          }
          if (
            this.buildInfo.buildtool === "msBuild" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .dbServName !== "" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .dbServName !== undefined
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].deployObjFlag = "on";
          }
          if (
            this.buildInfo.buildtool === "msBuild" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .dbUName !== "" &&
            this.IdpdataService.data.deployInfo.deployEnv[i].deploySteps[j]
              .dbUName !== undefined
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].isInternetFlag = "on";
          }

          if (
            this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer !==
              "" &&
            this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer !==
              undefined
          ) {
            if (
              this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                .containerName !== "" &&
              this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                .containerName !== undefined
            ) {
              this.tempObject.deployEnv[i].deploySteps[j].deployContainerFlag =
                "on";
            } else {
              this.tempObject.deployEnv[i].deploySteps[j].deployContainerFlag =
                "off";
            }
          } else {
            this.tempObject.deployEnv[i].deploySteps[j].deployContainerFlag =
              "off";
          }
          if (
            (this.buildTool === "ant" || this.buildTool === "maven") &&
            this.deployInfo.deployEnv[i].deploySteps[j].pathToFiles !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].pathToFiles !== null &&
            this.deployInfo.deployEnv[i].deploySteps[j].pathToFiles !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].scriptType = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].scriptType = "off";
          }
          if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
            this.tempObject.deployEnv[i].deploySteps[j] = {};
          }

          if (
            this.deployInfo.deployEnv[i].deploySteps[j].dockerFilePath !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].dockerFilePath !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              ////console.log(this.tempObject.deployEnv[i].deploySteps[j]);
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }

            this.tempObject.deployEnv[i].deploySteps[j].dockerContainerFlag =
              "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              ////console.log(this.tempObject.deployEnv[i].deploySteps[j]);
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].dockerContainerFlag =
              "off";
          }

          if (
            this.deployInfo.deployEnv[i].deploySteps[j].dockerComposePath !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].dockerComposePath !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              ////console.log(this.tempObject.deployEnv[i].deploySteps[j]);
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }

            this.tempObject.deployEnv[i].deploySteps[j].dockerRegistryFlag =
              "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              ////console.log(this.tempObject.deployEnv[i].deploySteps[j]);
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].dockerRegistryFlag =
              "off";
          }
          if (
            this.deployInfo.deployEnv[i].deploySteps[j].bizScriptPath !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].bizScriptPath !== ""
          ) {
            this.tempObject.deployEnv[i].deploySteps[j].deployBizFlag = "on";
          }

          if (
            this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer !==
              "" &&
            this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer !==
              undefined
          ) {
            if (
              this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                .updateDB !== "" &&
              this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                .updateDB !== undefined
            ) {
              if (
                this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                  .rollbackStrategy !== "" &&
                this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                  .rollbackStrategy !== undefined
              ) {
                this.tempObject.deployEnv[i].deploySteps[j].rollbackOnFailFlag =
                  "on";
              } else {
                this.tempObject.deployEnv[i].deploySteps[j].rollbackOnFailFlag =
                  "off";
              }
              this.tempObject.deployEnv[i].deploySteps[j].dbDeployFlag = "on";
            } else {
              this.tempObject.deployEnv[i].deploySteps[j].dbDeployFlag = "off";
            }
          } else {
            this.tempObject.deployEnv[i].deploySteps[j].dbDeployFlag = "off";
          }

          // dbDeployDBOwners
          if (
            this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer !==
              "" &&
            this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer !==
              undefined
          ) {
            if (
              this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                .dbDeployPipelineList !== undefined &&
              this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                .dbDeployPipelineList !== "" &&
              this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                .dbDeployDBOwners !== undefined &&
              this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                .dbDeployDBOwners !== ""
            ) {
              this.tempObject.deployEnv[i].deploySteps[j].dbDeployPipelineFlag =
                "on";
              if (
                this.tempObject.deployEnv[i].deploySteps[j]
                  .dependentDBDeployPipelineList === undefined
              ) {
                this.tempObject.deployEnv[i].deploySteps[
                  j
                ].dependentDBDeployPipelineList = [];
              }
              if (
                this.tempObject.deployEnv[i].deploySteps[j]
                  .dependentDBDeployPipelineList.length === 0
              ) {
                for (const name of this.deployInfo.deployEnv[i].deploySteps[j]
                  .deployToContainer.dbDeployPipelineList) {
                  let itemIndex;
                  itemIndex = this.IdpdataService.data.DBDeploypipelineList.map(
                    function (e) {
                      return e.itemName;
                    }
                  ).indexOf(name);
                  if (itemIndex !== undefined) {
                    this.tempObject.deployEnv[i].deploySteps[
                      j
                    ].dependentDBDeployPipelineList.push(
                      this.IdpdataService.data.DBDeploypipelineList[itemIndex]
                    );
                  }
                }
              }
            } else {
              this.tempObject.deployEnv[i].deploySteps[j].dbDeployPipelineFlag =
                "off";
              this.deployInfo.deployEnv[i].deploySteps[
                j
              ].deployToContainer.dbDeployPipelineList = [];
            }
          } else {
            this.tempObject.deployEnv[i].deploySteps[j].dbDeployPipelineFlag =
              "off";
            this.deployInfo.deployEnv[i].deploySteps[
              j
            ].deployToContainer.dbDeployPipelineList = [];
          }
          if (
            this.deployInfo.deployEnv[i].deploySteps[j].deploymentOption !==
              undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].deploymentOption !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }

            this.tempObject.deployEnv[i].deploySteps[j].muleESBDeployFlag =
              "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              ////console.log(this.tempObject.deployEnv[i].deploySteps[j]);
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.deployInfo.deployEnv[i].deploySteps[j].deploymentOption = "";
            this.tempObject.deployEnv[i].deploySteps[j].muleESBDeployFlag =
              "off";
          }

          if (
            this.deployInfo.deployEnv[i].deploySteps[j].timeout !== undefined &&
            this.deployInfo.deployEnv[i].deploySteps[j].timeout !== null &&
            this.deployInfo.deployEnv[i].deploySteps[j].timeout !== ""
          ) {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              ////console.log(this.tempObject.deployEnv[i].deploySteps[j]);
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].approvalCheckBox = "on";
          } else {
            if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
              ////console.log(this.tempObject.deployEnv[i].deploySteps[j]);
              this.tempObject.deployEnv[i].deploySteps[j] = {};
            }
            this.tempObject.deployEnv[i].deploySteps[j].approvalCheckBox =
              "off";
          }

          if (
            this.buildInfo.buildtool !== "gradle" &&
            this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer !==
              "" &&
            this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer !==
              undefined
          ) {
            if (
              this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                .containerName !== "" &&
              this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                .containerName !== undefined
            ) {
              this.tempObject.deployEnv[i].deploySteps[j].deployContainerFlag =
                "on";
            } else {
              ////console.log("bcause here as well");
              this.tempObject.deployEnv[i].deploySteps[j].deployContainerFlag =
                "off";
            }
          } else {
            ////console.log("bcause here as well");
            this.tempObject.deployEnv[i].deploySteps[j].deployContainerFlag =
              "off";
          }

          // general Deploy Steps
          if (
            this.deployInfo.deployEnv[i].deploySteps[j].abortScript !== "" &&
            this.deployInfo.deployEnv[i].deploySteps[j].abortScript !==
              undefined
          ) {
            if (
              this.deployInfo.deployEnv[i].deploySteps[j].abortScript
                .scriptType !== "" &&
              this.deployInfo.deployEnv[i].deploySteps[j].abortScript
                .scriptType !== undefined
            ) {
              if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
                this.tempObject.deployEnv[i].deploySteps[j] = {};
              }

              this.tempObject.deployEnv[i].deploySteps[
                j
              ].runScriptAbortCheckBox = "on";
              if (
                this.deployInfo.deployEnv[i].deploySteps[j].abortScript
                  .scriptType === "ant"
              ) {
                if (
                  this.deployInfo.deployEnv[i].deploySteps[j].abortScript &&
                  this.deployInfo.deployEnv[i].deploySteps[j].abortScript
                    .antPropertiesArr &&
                  this.deployInfo.deployEnv[i].deploySteps[j].abortScript
                    .antPropertiesArr[0].antKey !== undefined &&
                  this.deployInfo.deployEnv[i].deploySteps[j].abortScript
                    .antPropertiesArr[0].antValue !== undefined
                ) {
                  this.deployInfo.deployEnv[i].deploySteps[
                    j
                  ].abortScript.antProperty1 = "on";
                }
                if (
                  this.deployInfo.deployEnv[i].deploySteps[j].abortScript &&
                  this.deployInfo.deployEnv[i].deploySteps[j].abortScript
                    .javaOptions !== undefined
                ) {
                  this.deployInfo.deployEnv[i].deploySteps[
                    j
                  ].abortScript.antJavaOption1 = "on";
                }
              }
            } else {
              if (this.tempObject.deployEnv[i].deploySteps[j] === undefined) {
                this.tempObject.deployEnv[i].deploySteps[j] = {};
              }
              this.tempObject.deployEnv[i].deploySteps[
                j
              ].runScriptAbortCheckBox = "off";
            }
          } else {
            this.tempObject.deployEnv[i].deploySteps[j].runScriptAbortCheckBox =
              "off";
          }
          this.IdpdataService.data.checkboxStatus.deployInfo = this.tempObject;
        }
      } else {
        continue;
      }
    }
  }

  addCloudDeployment(outerIndex, innerIndex) {
    if (
      this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex]
        .cloudDeployment === undefined
    )
      this.tempObject.deployEnv[outerIndex].deploySteps[
        innerIndex
      ].cloudDeployment = "";
    //this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].dataright=this.dataEditorLeft;

    return "on";
  }
  clearCloudDeployment(outerIndex, innerIndex) {
    return "off";
  }

  /* Resets data */
  resetData() {
    const x = confirm("Are you sure to Reset ?");
    if (x) {
      const val = this.deployInfo.deployEnv;
      this.deployInfo = {
        deployEnv: [],
      };
      for (let i = 0; i < val.length; i++) {
        this.deployInfo.deployEnv.push({
          envName: val[i].envName,
          envFlag: "off",
        });
      }
      this.IdpdataService.data.deployInfo = this.deployInfo;
      this.IdpdataService.data.checkboxStatus.deployInfo = {};
    }
  }

  /* validation of deploy Steps
   * for each tech stack
   */
  validatePage() {
    let f = true;

    for (let i = 0; i < this.deployInfo.deployEnv.length; i++) {
      if (
        this.deployInfo.deployEnv[i].envFlag === "on" &&
        this.deployInfo.deployEnv[i].deploySteps === undefined
      ) {
        f = false;
        break;
      } else if (
        this.deployInfo.deployEnv[i].envFlag === "on" &&
        this.deployInfo.deployEnv[i].deploySteps !== undefined &&
        this.deployInfo.deployEnv[i].deploySteps.length !== 0
      ) {
        for (
          let j = 0;
          j < this.deployInfo.deployEnv[i].deploySteps.length;
          j++
        ) {
          switch (this.buildInfo.buildtool) {
            /* Validation of Ant Deploy Steps */
            case "ant": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .deployContainerFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .deployContainerFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].scriptType ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].scriptType ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dbDeployPipelineFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dbDeployPipelineFlag === "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }
            /* Validation of Maven Deploy Steps */
            case "maven": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .deployContainerFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .deployContainerFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].scriptType ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].scriptType ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dockerContainerFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dockerContainerFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dockerRegistryFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dockerRegistryFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dbDeployPipelineFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dbDeployPipelineFlag === "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }
            /* validation of Angular Deploy Steps */
            case "angular": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dbDeployPipelineFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dbDeployPipelineFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dockerRegistryFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dockerRegistryFlag === "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }
            /* Validation of .Net Deploy step */
            case "msBuild": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .deployContainerFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .deployContainerFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dockerRegistryFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dockerRegistryFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].deployBizFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].deployBizFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dbDeployPipelineFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dbDeployPipelineFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].deployFabFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].deployFabFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].deployObjFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].deployObjFlag ===
                    "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }
            /* Validation of NodeJS Deploy Step */
            case "nodeJs": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dbDeployPipelineFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dbDeployPipelineFlag === "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }
            /* validation of Python Deploy step */
            case "python": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].s3Loc ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].s3Loc ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dbDeployPipelineFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dbDeployPipelineFlag === "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            /* validation of Go Deploy step */
            case "go": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            /* validation of MainFrame Deploy step */
            case "mainframe": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            /* validation of MsSql Deploy step */
            case "mssql": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dbDeployPipelineFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dbDeployPipelineFlag === "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            /* validation of FileNet Deploy step */
            case "fileNet": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            /* validation of PHP Deploy step */
            case "php": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            /* validation of Siebel Deploy step */
            case "siebel": {
              if (
                (this.deployInfo.deployEnv[i].deploySteps[j].deployOS ===
                  undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j].deployOS !=
                    "windows") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }
            /* validation of IIB Deploy step */
            case "iib": {
              if (
                this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  "off"
              ) {
                f = false;
                break;
              }
              break;
            }
            /* validation of Pega Deploy step */
            case "pega": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].dataSchema ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].dataSchema ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].tomcatRestart ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].tomcatRestart ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            /* validation of Oracle Deploy step */
            case "oracleEBS": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .sqlFilesPackages === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .sqlFilesPackages === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .sqlFilesPackagesOTM === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .sqlFilesPackagesOTM === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].forms ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].forms ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].report ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].report ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].oafObject ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].oafObject ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].hostCtl ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].hostCtl ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].oaMedia ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].oaMedia ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].workFlow ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].workFlow ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].aolScripts ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].aolScripts ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dbDeployPipelineFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dbDeployPipelineFlag === "off") &&
                this.buildInfo.buildtool !== "ibmsi" &&
                this.buildInfo.buildtool !== "informatica" &&
                this.buildInfo.buildtool !== "general" &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            /* validation of MuleESB Deploy step */
            case "muleESB": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .muleESBDeployFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .muleESBDeployFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dbDeployPipelineFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dbDeployPipelineFlag === "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            /* validation of iOS Deploy step */
            // case "iOS(Objective C)": {
            //     if ((this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag === undefined ||
            //     this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag === "off")) {
            //         f = false;
            //         break;
            //         }
            //         break;
            //     }

            /* validation of Android Deploy step */
            case "Android(Ant based)": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }
            /* validation of iOS Deploy step */
            case "iOS(Swift)": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].iosDeploy ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].iosDeploy ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            /* validation of Maximo Deploy step */
            case "maximo": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                this.tempObject.deployEnv[i].deployOperation === "earDeploy" &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .deployContainerFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .deployContainerFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }
            /* validation of BigData Deploy step */
            case "bigData": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].bigDataFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].bigDataFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].pig ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].pig === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].scala ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].scala ===
                    "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.tempObject.deployEnv[i].deploySteps[j].hive ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].hive === "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }

              //if((this.tempObject.deployEnv[i].deploySteps[j].bigDataFlag!=undefined && this.tempObject.deployEnv[i].deploySteps[j].bigDataFlag!='off' )
              // && (this.tempObject.deployEnv[i].deploySteps[j].pig!=undefined && this.tempObject.deployEnv[i].deploySteps[j].pig!='off') && (this.tempObject.deployEnv[i].deploySteps[j].pigExec!=undefined && this.tempObject.deployEnv[i].deploySteps[j].pigExec!='off') && ( this.deployInfo.deployEnv[i].deploySteps[j].pigLocalMac===undefined || this.deployInfo.deployEnv[i].deploySteps[j].pigLocalMac==='off') && (this.deployInfo.deployEnv[i].deploySteps[j].pigMapRed===undefined || this.deployInfo.deployEnv[i].deploySteps[j].pigMapRed==='off')){
              // f=false;
              // //console.log("Inside if condition of pig");
              //break;
              //}
              ////console.log("outside if condition of pig");
              break;
            }

            /* validation of Tibco Deploy step */
            case "tibco": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].tibcoDeploy ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].tibcoDeploy ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dbDeployPipelineFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dbDeployPipelineFlag === "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            case "dbDeploy": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].dbDeployFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].dbDeployFlag ===
                    "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }
            case "gradle": {
              if (
                (this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                  undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j].runScriptFlag ===
                    "off") &&
                this.deployInfo.deployEnv[i].deploySteps[j]
                  .deployToContainer !== undefined &&
                (this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                  .containerName === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer
                    .containerName === "") &&
                (this.tempObject.deployEnv[i].deploySteps[j]
                  .dbDeployPipelineFlag === undefined ||
                  this.tempObject.deployEnv[i].deploySteps[j]
                    .dbDeployPipelineFlag === "off") &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .environmentProvDetails === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .environmentProvDetails.length <= 0) &&
                (this.deployInfo.deployEnv[i].deploySteps[j]
                  .cloudDeploymentFlag === undefined ||
                  this.deployInfo.deployEnv[i].deploySteps[j]
                    .cloudDeploymentFlag === "off") &&
                (this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                  "off" ||
                  this.tempObject.deployEnv[i].deploySteps[j].envProvFlag ===
                    undefined)
              ) {
                f = false;
                break;
              }
              break;
            }

            // default: {
            // }
          }
        }
      } else {
        // //console.log(i);
        continue;
      }
    }

    if (f === true) {
      //console.log(f);
      return true;
    } else {
      //console.log(f);
      return false;
    }
  }
  /* Submitting Deploy job
   * With the details provided
   */
  go() {
    if (this.validatePage()) {
      this.IdpdataService.data.deployInfo = this.deployInfo;
      this.IdpdataService.data.masterJson["deployInfo"] = this.deployInfo;
      ////console.log(this.IdpdataService.data);
      if (this.IdpdataService.testSubscription === true) {
        this.router.navigate(["/createConfig/testInfo"]);
      } else {
        if (
          this.IdpdataService.allFormStatus.basicInfo &&
          this.IdpdataService.allFormStatus.codeInfo &&
          this.IdpdataService.allFormStatus.buildInfo &&
          this.IdpdataService.allFormStatus.deployInfo
        ) {
          this.confirmSubmissionModalRef = this.modalService.show(
            this.modalforconfirmDeployAlert
          );
        } else {
          let listToFillFields = [];
          if (
            !this.IdpdataService.allFormStatus.basicInfo &&
            this.listToFillFields.indexOf("BasicInfo") === -1
          ) {
            listToFillFields.push("BasicInfo");
          }
          if (
            !this.IdpdataService.allFormStatus.codeInfo &&
            this.listToFillFields.indexOf("CodeInfo") === -1
          ) {
            listToFillFields.push("CodeInfo");
          }
          if (
            !this.IdpdataService.allFormStatus.buildInfo &&
            this.listToFillFields.indexOf("BuildInfo") === -1
          ) {
            listToFillFields.push("BuildInfo");
          }
          if (
            !this.IdpdataService.allFormStatus.deployInfo &&
            this.listToFillFields.indexOf("DeployInfo") === -1
          ) {
            listToFillFields.push("DeployInfo");
          }
          this.mandatoryMissingModalRef = this.modalService.show(
            this.modalformandatoryFieldsDeployAlert
          );
          this.mandatoryMissingModalRef.content = {
            listToFillFields: listToFillFields,
          };
        }
      }
      return true;
    } else {
      this.missingDataModalRef = this.modalService.show(
        this.modalforAlertDataMiss
      );
    }
  }
  clearDeploySteps(envIndex) {
    this.deployInfo.deployEnv[envIndex].deploySteps = [];
    //console.log(this.tempObject);
    if (
      this.tempObject !== undefined &&
      this.tempObject.deployEnv !== undefined &&
      this.tempObject.deployEnv[envIndex] !== undefined
    ) {
      this.tempObject.deployEnv[envIndex].deploySteps = [];
    }
    return false;
  }

  clearScriptType(envIndex) {
    this.deployInfo.deployEnv[envIndex].scriptType = "";
    return false;
  }
  clearFabFlag(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].appPackName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].pubProfName = "";
    return "off";
  }

  changeRunScript(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.scriptFilePath = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.targets = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.host = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.userName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.password = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.script = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.pathToFiles = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.destinationDir = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.flattenFilePath = "off";
    this.tempObject.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].transferFilesFlag = "off";
  }

  clearApproval(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].timeout = "";
    this.tempObject.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScriptAbortCheckBox = this.clearRunScriptOnAbort(
      outerIndex,
      innerIndex
    );
    return "off";
  }

  clearRunScriptOnAbort(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].abortScript.scriptType = "";
    this.changeAbortRunScript(outerIndex, innerIndex);
    return "off";
  }
  /*addition of EnvironmentProvisioning */
  addEnvironmentProvisioning(outerIndex, innerIndex) {
    if (
      this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex].envProv ===
      undefined
    ) {
      this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex].envProv =
        "";
    }
    return "on";
  }
  /*clear Environment Provisioning*/
  clearEnvironmentProvisioning(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].envProv = {
      toolType: "",
      scriptFilePath: "",
    };
    // this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].envProv.toolType="";
    // this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].envProv.scriptFilePath="";
  }
  /*onchange of environment toolfor env provisioning*/
  changeenvProv(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].envProv.scriptFilePath = "";
  }

  changeDeploymentOption(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].muleConsoleURL = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].userName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].password = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].muleServerGroup = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].hostName = "";
  }

  changeAbortRunScript(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].abortScript.scriptFilePath = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].abortScript.targets = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].abortScript.host = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].abortScript.userName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].abortScript.password = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].abortScript.script = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].abortScript.pathToFiles = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].abortScript.destinationDir = "";
    this.tempObject.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].transferFilesFlag = "off";
  }

  /* addition of Run script */
  addRunScript(outerIndex, innerIndex) {
    if (
      this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex]
        .runScript === undefined
    ) {
      this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex].runScript =
        "";
    }
    return "on";
  }
  //   Addition of IIB Build
  addBuildScript(outerIndex, innerIndex) {
    if (
      this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex]
        .buildScript === undefined
    ) {
      this.tempObject.deployEnv[outerIndex].deploySteps[
        innerIndex
      ].buildScript = "";
    }
    return "on";
  }
  /* Clearing the values of runscript */
  clearRunScript(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.scriptType = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.scriptFilePath = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.targets = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.host = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.userName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.password = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.script = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.pathToFiles = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.destinationDir = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.sshKey = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.sshPathToKey = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.flattenFilePath = "off";
    this.tempObject.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].transferFilesFlag = "off";
    //this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].deployOperation = ""
    //this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex].deployContainerFlag = "off";
    return "off";
  }
  /* Clearing the values of IIB build */
  clearBuildScript(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.buildType = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.buildFilePath = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.targets = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.host = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.userName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.password = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.script = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.pathToFiles = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.destinationDir = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.sshKey = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.sshPathToKey = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].buildScript.flattenFilePath = "off";
    this.tempObject.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].transferFilesFlag = "off";
    return "off";
  }

  cleartransferFilesFlag(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.pathToFiles = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.destinationDir = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].runScript.flattenFilePath = "off";
    return "off";
  }

  /* Deletion of Deploy step */
  removeDeploySteps(envIndex) {
    this.deployInfo.deployEnv[envIndex].deploySteps = [];
    this.tempObject.deployEnv[envIndex].deploySteps = [];
    return false;
  }

  /* Clearing the container values */
  clearContainerValues(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.containerName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.serverManagerURL = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.warPath = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.resourceToBeDeployed = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.userName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.password = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.narOS = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.hostName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.resourceToBeDeployed = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.port = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.targetCellName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.targetServerName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.ipOrDns = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.targetServerName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.targetNodeName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.deployedFolder = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.contextPath = "";
    return "off";
  }

  changeContainerValues(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.webSphereIp = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.serverManagerURL = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.warPath = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.resourceToBeDeployed = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.userName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.password = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.narOS = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.hostName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.resourceToBeDeployed = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.port = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.targetCellName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.targetServerName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.ipOrDns = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.targetServerName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.targetNodeName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.contextPath = "";
    if (
      this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex]
        .deployToContainer.containerName === "nifi"
    ) {
      this.deployInfo.deployEnv[outerIndex].deploySteps[
        innerIndex
      ].deployToContainer.deployedFolder = "lib";
    } else {
      this.deployInfo.deployEnv[outerIndex].deploySteps[
        innerIndex
      ].deployToContainer.deployedFolder = "";
    }
  }

  changeDBDeployRollbackValues(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.tagName = "";
  }
  changeEARfile(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.warPath = "";
  }

  clearDatabaseDeployment(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.updateDB = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.serverManagerURL = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.userName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.password = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.tagDB = "off";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.rollbackStrategy = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].deployToContainer.tagName = "";
  }

  openDatabaseDeployment(outerIndex, innerIndex) {
    this.tempObject.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].rollbackOnFailFlag = "on";
    return "on";
  }

  clearSpringBoot(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].pathToFiles =
      "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].port = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].parameters =
      "";
    return "off";
  }
  clearSiebelScriptlValues(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].deployUserName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployPassword = "";
    this.deployInfo.deployEnv[i].deploySteps[j].adminUserName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].adminPassword = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbOwner = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbOwnerPassword = "";

    return "off";
  }
  setServerIndex(stepIndex) {
    this.serverIndex = stepIndex;
    return false;
  }

  checkVal(envIndex, stepIndex) {}

  dockerContainerOff(envIndex, stepIndex) {
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].dockerFilePath =
      "";
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].tagName = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].dockerPort = 80;
    this.deployInfo.deployEnv[envIndex].deploySteps[
      stepIndex
    ].applicationPort = 80;
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].userName = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].password = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].repoUrl = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].pullFromRepo =
      "off";
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].pushToRepo =
      "off";
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].artifact = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[
      stepIndex
    ].artifactsToBeDeployed = [];

    return false;
  }

  dockerRegistryOff(envIndex, stepIndex) {
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].repoNameDR = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].tagNameDR = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].userNameDR = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].passwordDR = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[
      stepIndex
    ].dockerRegistryUrlDR = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[
      stepIndex
    ].dockerFilePathDR = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].dockerPortDR =
      "";
    this.deployInfo.deployEnv[envIndex].deploySteps[
      stepIndex
    ].applicationPortDR = "";
    this.deployInfo.deployEnv[envIndex].deploySteps[
      stepIndex
    ].dockerComposePath = "";

    return false;
  }

  muleESBDeployOff(envIndex, stepIndex) {
    // this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].muleConsoleURL = '';
    // this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].userName = '';
    // this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].password = '';
    // this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].muleServerGroup = '';
    this.deployInfo.deployEnv[envIndex].deploySteps[
      stepIndex
    ].deploymentOption = "";

    return false;
  }

  addArtifactField(key1, key2) {
    if (
      this.tempObject.deployEnv[key1].deploySteps[key2].dockerContainerFlag ===
      "on"
    ) {
      // //console.log("here");
      this.deployInfo.deployEnv[key1].deploySteps[
        key2
      ].artifactsToBeDeployed = [];
      this.deployInfo.deployEnv[key1].deploySteps[
        key2
      ].artifactsToBeDeployed.push("");
    }
  }

  clearS3Values(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].s3location = "";
    return "off";
  }
  clearDeployOnEmulator(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.fileName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.avdName = "";
    this.deployInfo.deployEnv[i].deploySteps[
      j
    ].deployToContainer.launcherActivity = "";
    return "";
  }

  clearDeployOnHockeyApp(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.token = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.fileName = "";
    return "";
  }

  cleariosDeployValues(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].iosDataPath = "";
  }

  clearOracleOTMSqlValues(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].sqlFolder = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbuserNameOTM = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbpasswordOTM = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbhostNameOTM = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbPort = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbSid = "";
    return "off";
  }

  clearOracleSqlValues(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].sqlFileName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbuserName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbpassword = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbhostName = "";
    return "off";
  }
  clearOracleReportValues(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].reportName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].reportPath = "";
    return "off";
  }
  clearOracleFormsValues(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].formName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].formFTPPath = "";
    this.deployInfo.deployEnv[i].deploySteps[j].formsBasePath = "";
    this.deployInfo.deployEnv[i].deploySteps[j].formsEnvFile = "";
    this.deployInfo.deployEnv[i].deploySteps[j].formsDbPass = "";
    return "off";
  }
  clearOracleCtlValues(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].hostCtlfile = "";
    this.deployInfo.deployEnv[i].deploySteps[j].reportPathCtl = "";
    return "off";
  }
  clearOracleOaValues(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].oaMediaFile = "";
    this.deployInfo.deployEnv[i].deploySteps[j].reportPathOa = "";
    return "off";
  }
  clearOracleoafObjectValues(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].oafFolderName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].dbPassword = "";
    return "off";
  }
  clearOracleWfValues(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].workFlowName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].workFlowFTPPath = "";
    this.deployInfo.deployEnv[i].deploySteps[j].workFlowBasePath = "";
    this.deployInfo.deployEnv[i].deploySteps[j].workFlowEnvFile = "";
    this.deployInfo.deployEnv[i].deploySteps[j].workFlowDbPass = "";
    return "off";
  }
  clearOracleAolValues(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].customTopPath = "";
    this.deployInfo.deployEnv[i].deploySteps[j].aolBasePath = "";
    this.deployInfo.deployEnv[i].deploySteps[j].aolEnvFile = "";
    this.deployInfo.deployEnv[i].deploySteps[j].loadInFile = "";
    this.deployInfo.deployEnv[i].deploySteps[j].upDownFile = "";
    this.deployInfo.deployEnv[i].deploySteps[j].appsPass = "";
    return "off";
  }

  checkDis(envIndex) {
    let f = false;
    for (
      let j = 0;
      j < this.tempObject.deployEnv[envIndex].deploySteps.length;
      j++
    ) {
      if (
        this.tempObject.deployEnv[envIndex].deploySteps[j].tibcoDeploy === "on"
      ) {
        f = true;
        break;
      }
    }
    return f;
  }

  addField(key1, key2) {
    if (
      this.deployInfo.deployEnv[key1].deploySteps[key2]
        .artifactsToBeDeployed === undefined ||
      this.deployInfo.deployEnv[key1].deploySteps[key2].artifactsToBeDeployed ==
        null
    ) {
      this.deployInfo.deployEnv[key1].deploySteps[
        key2
      ].artifactsToBeDeployed = [];
    }
    this.deployInfo.deployEnv[key1].deploySteps[
      key2
    ].artifactsToBeDeployed.push("");
  }

  removeField(key1, key2, index) {
    this.innerIndex = key2;
    this.outerIndex = key1;
    this.artifactIndex = index;
    this.delFiledModalRef = this.modalService.show(this.modalforDelField);
    this.delFiledModalRef.content = {
      innerIndex: key1,
      outerIndex: key1,
      artifactIndex: index,
    };
  }

  confirmRemoveField(modelRef) {
    this.deployInfo.deployEnv[modelRef.content.outerIndex].deploySteps[
      modelRef.content.innerIndex
    ].artifactsToBeDeployed.splice(modelRef.content.artifactIndex, 1);
    modelRef.hide();
  }

  removeAllFields(key1, key2, index) {
    this.delAllFieldsModalRef = this.modalService.show(
      this.modalforDelAllField
    );
    this.delAllFieldsModalRef.content = {
      innerIndex: key2,
      outerIndex: key1,
      artifactIndex: index,
    };
  }

  confirmRemoveAllFields(modalRef) {
    this.deployInfo.deployEnv[modalRef.content.outerIndex].deploySteps[
      modalRef.content.innerIndex
    ].artifactsToBeDeployed = [];
  }

  trackById(index, field) {
    return field.id;
  }

  clearbigDataFlag(outerIndex, innerIndex) {
    this.scalaOff(outerIndex, innerIndex);
    this.pigOff(outerIndex, innerIndex);
    this.hiveOff(outerIndex, innerIndex);
  }

  scalaOff(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].scalaServerName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].scalaUsername = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].scalaPassword = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].scalaDir = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].scalaUip = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].scalaJfn = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].scalaMmn = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].scalaCfn = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].scalaOf = "";
    this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex].scalaExec =
      "off";
    this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex].scala = "off";
    return "off";
  }

  pigOff(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].pigServerName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].pigUsername =
      "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].pigPassword =
      "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].pigDir = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].pigScr = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].pigLocalMac =
      "off";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].pigMapRed =
      "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].pigdf = "";
    this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex].pig = "off";
    this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex].pigExec =
      "off";
    return "off";
  }

  hiveOff(outerIndex, innerIndex) {
    this.deployInfo.deployEnv[outerIndex].deploySteps[
      innerIndex
    ].hiveServerName = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].hiveUsername =
      "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].hivePassword =
      "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].hiveDir = "";
    this.deployInfo.deployEnv[outerIndex].deploySteps[innerIndex].hiveScr = "";
    this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex].hive = "off";
    this.tempObject.deployEnv[outerIndex].deploySteps[innerIndex].hiveExec =
      "off";
    return "off";
  }

  cleartibcoDeploy(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].path = "";
    this.deployInfo.deployEnv[i].deploySteps[j].pathToFiles = "";
    return "off";
  }
  // reset fields for pega
  dataSchemaoff(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].pegaDBdataUname = "";
    this.deployInfo.deployEnv[i].deploySteps[j].pegaDBName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].pegaDBHost = "";
    this.deployInfo.deployEnv[i].deploySteps[j].pegaDBDataPort = "";
    this.deployInfo.deployEnv[i].deploySteps[j].pegaSqlFile = "";
    return "off";
  }
  dataSchemaon(i, j) {
    this.tempObject.deployEnv[i].deploySteps[j].dataSchema = "on";

    return "on";
  }

  /* clearing Weblogic Details */
  clearWeblogic(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.hostName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.port = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.userName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.password = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.contextPath =
      "";
    this.deployInfo.deployEnv[i].deploySteps[
      j
    ].deployToContainer.resourceToBeDeployed = "";
    this.deployInfo.deployEnv[i].deploySteps[
      j
    ].deployToContainer.targetServerName = "";
    this.deployInfo.deployEnv[i].deploySteps[
      j
    ].deployToContainer.targetServerName = "";
    this.deployInfo.deployEnv[i].deploySteps[
      j
    ].deployToContainer.targetServerName = "";
    this.tempObject.deployEnv[i].deploySteps[j].deployContainerFlag = "off";
    this.tempObject.deployEnv[i].deploySteps[j].maximoDisable = false;
  }
  enableWebLogic(i, j) {
    if (
      this.deployInfo.deployEnv[i].deploySteps[j].deployOperation ===
      "earDeploy"
    ) {
      if (this.tempObject.deployEnv === undefined) {
        this.tempObject.deployEnv = [{}];
      }
      if (this.tempObject.deployEnv[i] === undefined) {
        this.tempObject.deployEnv[i] = { deploySteps: [{}] };
      }
      this.tempObject.deployEnv[i].deploySteps[j].deployContainerFlag = "on";
      this.tempObject.deployEnv[i].deploySteps[j].maximoDisable = true;
    }
  }

  /* clearing AEM Details */
  clearDeployAem(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.hostName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.aemPort = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.userName = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.password = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.contextPath =
      "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.packageName =
      "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.groupName =
      "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.aemProxy = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.proxyun = "";
    this.deployInfo.deployEnv[i].deploySteps[j].deployToContainer.proxypw = "";
  }
  enableDeployAem(i, j) {
    if (
      this.deployInfo.deployEnv[i].deploySteps[j].deployOperation ===
      "deployAemOperations"
    ) {
      if (this.tempObject.deployEnv === undefined) {
        this.tempObject.deployEnv = [{}];
      }
      if (this.tempObject.deployEnv[i] === undefined) {
        this.tempObject.deployEnv[i] = { deploySteps: [{}] };
      } else {
        this.tempObject.deployEnv[i][j] = {
          deploySteps: [this.env.deployToContainer.deployAemOperations],
        };
      }
      this.tempObject.deployEnv[i].deploySteps[j].deployContainerFlag = "on";
      this.tempObject.deployEnv[i].deploySteps[j].maximoDisable = true;
    }
  }

  /* Ant java Options */
  openAntPropertiesField(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].runScript.antPropertiesArr = [];
    this.addAntProperties(i, j);
    //console.log("inside 1");
    return "on";
  }
  /* Addfing Ant properties for each deploy step */
  addAntProperties(i, j) {
    // //console.log("qwerty");
    this.deployInfo.deployEnv[i].deploySteps[j].runScript.antPropertiesArr.push(
      {}
    );
    //console.log(
    //   this.deployInfo.deployEnv[i].deploySteps[j].runScript.antPropertiesArr
    // );
  }
  clearAntPropertisField(i, j) {
    this.deployInfo.deployEnv[i].deploySteps[j].runScript.antPropertiesArr = [];
    // //console.log("inside 1");
    return false;
  }
  /* Removal of Ant module */
  deleteAntProp(index, i, j) {
    this.delAntPropertyModalRef = this.modalService.show(
      this.modalforDelAntProperties
    );
    this.delAntPropertyModalRef.content = {
      indexI: i,
      indexJ: j,
      index: index,
    };
  }

  deleteAntPropConfirm(modalRef) {
    this.deployInfo.deployEnv[modalRef.content.indexI].deploySteps[
      modalRef.content.indexJ
    ].runScript.antPropertiesArr.splice(modalRef.content.index, 1);
    modalRef.hide();
  }

  resetProxyFields(envIndex, stepIndex) {
    this.deployInfo.deployEnv[envIndex].deploySteps[stepIndex].proxy = {
      username: "",
      password: "",
      host: "",
      port: "",
    };
    return "false";
  }

  submitData(modalRef) {
    this.IdpSubmitService.submitData();
    modalRef.hide();
  }

  checkMsgSuccess() {
    if (this.IdpSubmitService.message === "success") {
      return true;
    } else {
      return false;
    }
  }

  checkMsgError() {
    if (this.IdpSubmitService.message === "error") {
      return true;
    } else {
      return false;
    }
  }

  freezeNavBarsCheck() {
    if (this.IdpdataService.freezeNavBars === true) {
      return true;
    } else {
      return false;
    }
  }
  deploySubscriptionNotSubmitCheck() {
    if (this.IdpdataService.deploySubscriptionSubmit !== true) {
      return true;
    } else {
      return false;
    }
  }

  deploySubscriptionSubmitCheck() {
    if (this.IdpdataService.deploySubscriptionSubmit === true) {
      return true;
    } else {
      return false;
    }
  }

  checkLoaderOn() {
    if (this.IdpSubmitService.loader === "on") {
      return true;
    } else {
      return false;
    }
  }

  checkLoaderOff() {
    if (this.IdpSubmitService.loader === "off") {
      return true;
    } else {
      return false;
    }
  }
}
