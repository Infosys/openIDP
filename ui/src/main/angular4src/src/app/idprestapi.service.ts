/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Injectable } from "@angular/core";
import { Headers, Http, RequestOptions } from "@angular/http";
import "rxjs/add/operator/toPromise";
import { IdpdataService } from "./idpdata.service";
import { CookieService } from "ngx-cookie";
import { Router } from "@angular/router";
import { environment } from "../environments/environment";
import { AdalService } from "adal-angular4";
import { StartupService } from "./startup.service";
import { KeycloakService } from "./keycloak/keycloak.service";
import { platformBrowserDynamic } from "@angular/platform-browser-dynamic";
import { OAuthService } from 'angular-oauth2-oidc';


@Injectable()
export class IdprestapiService {
  startupData: any;
  dropDownProperties: any = {
    "osList": [
        {
        "name": "Windows",
        "value": "windows"
        },
        {
        "name": "Linux",
        "value": "linux"
        },
        // {
        //   "name": "Mac",
        //   "value": "mac"
        // }
    ],
    "engineList": [
        {
        "name": "Jenkins Workflow",
        "value": "jenkins"
        },
        {
        "name": "IBM urbanCode",
        "value": "urbanCode"
        }
    ],
    "buildIntervallist": [
        {
        "name": "Minutes (30-59)",
        "value": "Minutes"
        },
        {
        "name": "Hours (0-23)",
        "value": "Hours"
        },
        {
        "name": "Day of the month (1-31)",
        "value": "DayOfMonth"
        },
        {
        "name": "Month (1-12)",
        "value": "Month"
        },
        {
        "name": "Day of week (0-7 , 0 and 7 being Sunday)",
        "value": "DayOfWeek"
        }
    ],
    "msBuildVersion": [
        {
        "name": "Default",
        "value": "(Default)"
        },
        {
        "name": "15.0",
        "value": "MSBUILD_15"
        },
        {
        "name": "14.0",
        "value": "MSBUILD_14"
        },
        {
        "name": "12.0",
        "value": "MSBUILD_12"
        },
        {
        "name": "4.0",
        "value": "MSBUILD_4"
        }
    ],
    "categoryList": [
        {
        "name": "Standard",
        "value": "Standard"
        }
      
    ],
    "SCMList": [
        {
        "name": "GIT",
        "value": "git"
        },
        {
        "name": "SVN",
        "value": "svn"
        },
        {
        "name": "MS TFS",
        "value": "tfs"
        },
      
        /* OSS Milestone 2

      */
        // {
        //   "name": "Rational ClearCase",
        //   "value": "clearcase"
        // }
    ],
    "repoList": [
        //  {
        //    "name": "GitBlit",
        //    "value": "gitBlit"
        //  },
        {
        "name": "GitHub",
        "value": "gitHub"
        },
        {
        "name": "GitLab",
        "value": "gitLab"
        },
        {
        "name": "BitBucket",
        "value": "bitBucket"
        }
    ],
    "buildConfList": [
        {
        "name": "Build Definition",
        "value": "buildDefinition"
        },
        {
        "name": "Repository Workspace",
        "value": "repositoryWorkspace"
        },
        {
        "name": "Stream",
        "value": "stream"
        }
    ],
    "codeScriptList": [
        {
        "name": "ANT Script",
        "value": "ant"
        },
        {
        "name": "Shell Script",
        "value": "shellScript"
        },
        {
        "name": "Batch Script",
        "value": "batchScript"
        }
    ],
    "runScriptList": [
        {
        "name": "ANT Script",
        "value": "ant"
        },
        {
        "name": "Shell Script",
        "value": "shellScript"
        },
        {
        "name": "Batch Script",
        "value": "batchScript"
        },
        {
        "name": "Powershell Script",
        "value": "powerShell"
        },
        {
        "name": "SSH Execution",
        "value": "sshExecution"
        }
    ],
    "containerList": [
        {
        "name": "Tomcat",
        "value": "tomcat"
        },
        {
        "name": "WebSphere",
        "value": "webSphere"
        }
    ],
    "databaseList": [
        {
        "name": "MySQL",
        "value": "mysql"
        },
        {
        "name": "PostgreSQL",
        "value": "postgres"
        },
        {
        "name": "Derby",
        "value": "derby"
        },
        {
        "name": "Hypersonic",
        "value": "hypersonic"
        },
        {
        "name": "H2",
        "value": "h2"
        },
        {
        "name": "Oracle",
        "value": "oracle"
        },
        {
        "name": "MsSQL",
        "value": "mssql"
        }
    ],
    "serviceList": [
        {
        "name": "IFast",
        "value": "iFast"
        }
    ],
    "runScriptTestPageList": []
  };

  constructor(
    private http: Http,
    private idpdataService: IdpdataService,
    private _cookieService: CookieService,
    private router: Router,
    private adalSvc: AdalService,
    private startupService: StartupService,
    private keycloakService: KeycloakService,
    private oauthService: OAuthService

  ) {
    this.startupData = this.startupService.getData();
    this.idpdataService.authmode = this.startupData.authmode;
    this.idpdataService.keycloakUrl = this.startupData.keycloakUrl;
    this.idpdataService.keycloakRealm = this.startupData.keycloakRealm;
    this.idpdataService.keycloakClientId = this.startupData.keycloakClientId;
    this.idpdataService.cloudDeployURL = this.startupData.clouddeployurl;
    this.idpdataService.insightsFlag=this.startupData.insightsFlag;
     this.idpdataService.cloudDeployFlag=this.startupData.cloudDeployFlag;

    if (this.idpdataService.authmode === "azureAd") {
        this.idpdataService.adalConfig.clientId = this.startupData.clientId;
        this.idpdataService.adalConfig.tenate = this.startupData.tenate;
        this.idpdataService.adalConfig.postLogoutRedirectUri = this.startupData.postLogoutRedirectUri;
        this.idpdataService.adalConfig.endpoints[this.startupData.endpoints] = this.startupData.endpoints;
        this.adalSvc.init(JSON.parse(JSON.stringify(this.idpdataService.adalConfig)));
        this.adalSvc.handleWindowCallback();
    }
  }
  getNotification(): Promise<any> {
    const url = this.idpdataService.devServerURL + "/userService/notification";
    const headers = new Headers();
    let cookie;
    headers.append("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });

    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(Error => console.log(Error));


  }

  getUserName(): Promise<any> {
    const url = this.idpdataService.devServerURL + "/userService/jobid1/getUserRolesPermissions";
    const headers = new Headers();
    let cookie;
    headers.append("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    const data = "";
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(Error => console.log(Error));

  }

  getWorkItems(workitem): Promise <any> {
    const url = this.idpdataService.serverUrl + "/_apis/wit/Workitems/" + workitem + "?api-version=2.2";
    const headers = new Headers();
    headers.append("Authorization", "Basic " + this.base64EncodeDecode(this.idpdataService.uName, this.idpdataService.pass));
    const options = new RequestOptions({ headers: headers });

    return this.http
    .get(url, options)
    .toPromise()
    .then(response => response)
    .catch(error => error);

  }

  getUserStories(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/trigger/pipelines/stories/" + data;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
      cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    console.log(url);
    return this.http
          .get(url, options)
          .toPromise()
          .then(response => response)
          .catch(error => console.log(error));

  }

  updatePlan(data1, data2, data3, envData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/updateRelease/" + data1 + "/" + data2 + "/" + data3;
    let cookie;
    const headers = new Headers();
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    console.log(envData);
    return this.http
        .post(url, envData, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }
  getReleaseNumber(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/" + data + "/getReleaseInfo";
    let cookie;
    const headers = new Headers();
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    console.log(data);
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  getEnvNames(data, data1): Promise<any> {
    const url = this.idpdataService.devServerURL + "/releaseService/release/" + data1 + "/" + data;
    const headers = new Headers();
    let cookie;
    console.log(data, data1);
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);

    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }
  obtainAccessToken(params: any): Promise<any> {
    const url = "idp-oauth/oauth/token";
    const headers = new Headers();
    headers.append("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
    if (this.idpdataService.authmode === "keycloak") {
        console.log(this.idpdataService.keycloakToken);
        headers.append("accessflow", this.idpdataService.keycloakToken);
    }
    const options = new RequestOptions({ headers: headers });
    const temp = "client_id=" + params.client_id + "&grant_type=" + params.grant_type + "&username=" +
        params.username + "&password=" + encodeURIComponent(params.password);
    return this.http
        .post(url, temp, options)
        .toPromise()
        .then(response => response)
        .catch(error => error);

  }


 getData(): Promise<any> {
  
        let headers = new Headers({'Authorization': 'Basic '+btoa("idpadmin:idpadmin@123")});
        let options = new RequestOptions({ headers: headers });
       
        return this.http
            .get("properties",options)
            .toPromise()
            .then(response => response)
            .catch(error => console.log(error));
      }
	  
  logout() {
    if (this.idpdataService.authmode === "azureAd") {
        this.adalSvc.init(JSON.parse(JSON.stringify(this.idpdataService.adalConfig)));
        this.adalSvc.handleWindowCallback();
        this.adalSvc.logOut();
    }
    if (this.idpdataService.authmode === "keycloak") {
        this._cookieService.removeAll();
        this.idpdataService.keycloakToken = "";
        console.log("inside logout");
        this.oauthService.logOut();

    }
    const url = "idp-oauth/oauth/token";
    return this.http
        .delete(url)
        .toPromise()
  }
  checkApplicationNamesRms(data,  operation = "jobid1"): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/IDP/" + operation + "/getRmsApplication";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  checkApplicationNames(data,  operation = "jobid1"): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/IDP/" + operation + "/getApplication";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }
  getOrganizationWiseApplicationNames(): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/IDP/org/applications/names ";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  getTimeZone(): Promise<any> {
    const url = this.idpdataService.devServerURL + "/releaseService/serverTimeZone";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  getAvailableSlot(data1, data2, data3): Promise<any> {
    const url = this.idpdataService.devServerURL + "/releaseService/release/availableSlot/" + data2 + "/" + data1 + "/" + data3;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));


  }

  getPipelineDetails(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/IDP/jobid1/getPipelineDetails";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }
  checkForApplicationType(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/sapJobService/pipelines/sapApplication/" + data;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    console.log(url);
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  getEnvSlots(application_name, env): Promise<any> {

    const url = this.idpdataService.devServerURL + "/releaseService/existingEnvSlots/" + application_name + "/" + env; let cookie;
    const headers = new Headers();
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(Error => console.log(Error));

  }

  getIdpBuildInfo():Promise<any>{
    return this.http.get("idpBuildInfo").toPromise().then(response=>{
      return response.json();
    }).catch(error=>{
      console.log(error);
      })
  }
  getExistingSlot(application_name, release_number, env): Promise<any> {
    const url = this.idpdataService.devServerURL + "/releaseService/existingSlots/" + release_number + "/" + application_name + "/" + env;
    let cookie;
    const headers = new Headers();
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(Error => console.log(Error));
  }

  emailToEnvOwner(envOwner): Promise<any> {

    const url = this.idpdataService.devServerURL + "/releaseService/environment/mail";
    let cookie;
    const headers = new Headers();
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, envOwner, options)
        .toPromise()
        .then(response => response)
        .catch(Error => console.log(Error));



  }

  getPipelineEnv(data, data1): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/environment/pipelines/" + data1 + "/" + data;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);

    const options = new RequestOptions({ headers: headers });
    console.log(url);
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }
  getEnvironmentPairs(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/pairName";
    const headers = new Headers();
    let cookie;
    headers.append("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(Error => console.log(Error));

  }


  getEnvironmentNames(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/jobid1/getEnvironmentNames";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    console.log(data);
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }


getLandscapesForSap(data): Promise<any> {

        const url = this.idpdataService.devServerURL + "/sapJobService/pipelines/landscapes/" + data;
        const headers = new Headers();
        let cookie;
        if (this._cookieService.get("access_token")) {
          cookie = this._cookieService.get("access_token");
        }
        headers.append("Authorization", "Bearer " + cookie);
        const options = new RequestOptions({ headers: headers });
        return this.http
              .get(url, options)
              .toPromise()
              .then(response => response)
              .catch(error => console.log(error));

  }


  getVariantNamesForSap(data): Promise<any> {

    const url = this.idpdataService.devServerURL + "/sapJobService/pipelines/variant/" + data.application_name + "/" + data.landscape_name;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
      cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
          .get(url, options)
          .toPromise()
          .then(response => response)
          .catch(error => console.log(error));
  }


  getTransportRequest(data): Promise<any> {

    const url = this.idpdataService.devServerURL + "/sapJobService/pipelines/transportrequest/"
    + data.application_name + "?landscapeName=" + data.landscapeName + "&&deployOperation=" + data.deployOperation;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
      cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Accept", "*/*");
    const options = new RequestOptions({ headers: headers });
    return this.http
          .get(url, options)
          .toPromise()
          .then(response => response)
          .catch(error => console.log(error));

  }

  getChangeRequestDetails(appName , charmFlowType): Promise<any> {

    const url = this.idpdataService.devServerURL + "/sapJobService/changerequest/" + appName + "/" + charmFlowType;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
      cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Accept", "*/*");
    const options = new RequestOptions({ headers: headers });
    return this.http
          .get(url, options)
          .toPromise()
          .then(response => response)
          .catch(error => console.log(error));

  }


  getUserStoriesSAP(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/sapJobService/pipelines/userstories/"
    + data.application_name + "/" + data.pipeline_name + "/" + data.release;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
      cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
          .get(url, options)
          .toPromise()
          .then(response => response)
          .catch(error => console.log(error));
  }

  getIDPDropdownProperties() {
    const techStackJson = this.dropDownProperties;
    return techStackJson;
  }
  checkAvailableJobs(): Promise<any> {
    console.log(this.idpdataService.devServerURL);
    const url = this.idpdataService.devServerURL + "/jobService/checkAvailableJobsToTrigger";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = "IDP";
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  triggerJob(request): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/jobid1/TriggerInputs";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    const data = request;
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));


  }


  createCloudApplication(requestData): Promise<any> {
    const url = this.idpdataService.cloudDeployURL + "/deploymentService/application/createApplication";
    console.log(url);
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }
  createLoadBalancer(requestData): Promise<any> {
    const url = "http://localhost:8092/deployment/deploymentService/application/createApplication";
    console.log(url);
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }
  createCloudPipeline(requestData): Promise<any> {

    const url = this.idpdataService.cloudDeployURL + "/deploymentService/pipeline/createPipeline";
    console.log(url);
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  copyeditCloudPipeline(appName, pipName): Promise<any> {

    const url = this.idpdataService.cloudDeployURL + "/deployment/deploymentService/pipeline/" +
        appName + "/" + pipName + "/getPipelineInfo";
    console.log(url);
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Authorization", "Bearer " + cookie);
    const data = "";
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }



  submit(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/IDP/jobid1/submitJob";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  triggerJobs(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/jobid1/triggerJobs";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  getApplicationDetails(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/jobid1/getApplicationDetails";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  deletePipeline(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/jobid1/deletePipeline";

    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  getExistingApps(): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/IDP/jobid1/getExistingApps";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = "";
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }




  getApplicationInfo(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/jobid1/getAppInfo";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }
  // get filtered application names : "nonSAP" as of now
  getFilteredApplicationNames(filterString): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/IDP/jobid1/filter/Application/" + filterString;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = "requestData";
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  editApplicationDetails(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/jobid1/updateAppinfo";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  createApplication(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/jobid1/createApplication";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  getJobs(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/IDP/jobid1/getJobs";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  getStageViewUrl(): Promise<any> {

    console.log("focuuus" + this.idpdataService.appName);
    console.log("focuuus" + this.idpdataService.pipelineName);

    const url = this.idpdataService.devServerURL + "/jobService/stageview/url/" +
        this.idpdataService.appName + "/" + this.idpdataService.pipelineName;
    console.log("Stage view URl" + url);
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Content-type", "application/json");
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }
  getPipelineList(responseData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/jobid1/getDependencyJobs";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Content-type", "application/json");
    const data = responseData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  sendAppMail(responseData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/applications/mail";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Content-type", "application/json");
    const data = responseData;
    console.log(data);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  sendOrgMail(responseData): Promise<any> {
    const url = this.idpdataService.subscriptionServerURL + "/org/registrationServices/mail";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Content-type", "application/json");
    const data = responseData;
    console.log(data);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  sendLicenseMail(responseData): Promise<any> {
    const url = this.idpdataService.subscriptionServerURL + "/org/registrationServices/license/mail";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Content-type", "application/json");
    const data = responseData;
    console.log(data);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  sendPipeMail(responseData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/jobid1/jobCreationSuccessMail";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Content-type", "application/json");
    const data = responseData;
    console.log(data);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }
  getPipelineNames(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/pipelines/list/" +
        this.idpdataService.releaseManagerData.applicationName + "/" + "release";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }
  getPipelineListforWorkflow(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/pipelines/list/" + requestData + "/workflow";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);

    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }
  getExistingAppNames(): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/IDP/applications/names";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  checkSubApplication(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/getSubApplication?appName=" + data;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);

    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  getActiveReleases(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/releaseService/release/info/" +
        this.idpdataService.releaseManagerData.applicationName + "/" + requestData + "/on";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  getHistoryReleases(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/releaseService/release/info/" +
        this.idpdataService.releaseManagerData.applicationName + "/" + requestData + "/off";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  getApplicationNameForReleaseManager(responseData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/applicationService/IDP/applications/release/list ";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Content-type", "application/json");
    const data = responseData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  postReleaseData(responseData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/releaseService/release/insert/" +
        this.idpdataService.releaseManagerData.applicationName + "/" + this.idpdataService.pipelineListRm;
    const headers = new Headers();
    let cookie;
    console.log(responseData);
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Content-type", "application/json");
    const data = responseData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  getArtifactsApprovePortal(data): Promise < any > {
    var url= this.idpdataService.devServerURL + '/releaseService/getApprovedArtifact';
      let headers = new Headers();
      let cookie;
      if(this._cookieService.get('access_token')) {
        cookie = this._cookieService.get('access_token');
      }
                 headers.append('Content-type', 'application/json');
      headers.append('Authorization', 'Bearer ' + cookie);
      //let data = requestData;
      let options = new RequestOptions({ headers: headers });
      return this.http
    .post(url, data, options)
    .toPromise()
    .then(response => { return response; })
    .catch(error => console.log(error));
}

  updateReleases(responseData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/releaseService/release/update";
    const headers = new Headers();
    let cookie;
    console.log(responseData);
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Content-type", "application/json");
    const data = responseData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));


  }

  checkReleaseNo(): Promise<any> {

    const url = this.idpdataService.devServerURL + "/releaseService/release/number/" +
        this.idpdataService.releaseManagerData.applicationName + "/" + this.idpdataService.pipelineListRm;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  getArtifactsRm(data): Promise<any> {

    const url = this.idpdataService.devServerURL + "/releaseService/getArtifactDetails";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  // Release info service
  getReleasesApprovePortal(): Promise<any> {
    const url = this.idpdataService.devServerURL + "/releaseService/release/info/" +
        this.idpdataService.appName + "/" + this.idpdataService.pipelineName + "/on";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }


  // submit button clicked on approve portal
  updateArtifacts(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/releaseService/update/artifact";
    const headers = new Headers();
    let cookie;
    console.log(data);
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Content-type", "application/json");

    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  fetchTriggerSteps(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/trigger/steps/" + data.application_name + "/" +
        data.pipeline_name + "/" + data.env_name;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);

    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  /* Service call to fect list of Jobparam */
  getJobParamList(): Promise<any> {

    const url = this.idpdataService.devServerURL + "/jobService/getJobParmDetails/" +
        this.idpdataService.appName + "/" + this.idpdataService.pipelineName;

    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);

    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));


  }

  getTestPlanList(appName, pipeName): Promise<any> {

    const url = this.idpdataService.devServerURL + "/jobService/testPlan/" + appName + "/" + pipeName;

    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);

    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));


  }

  getTestSuitList(id, appName, pipeName): Promise<any> {
    console.log(appName);
    console.log(pipeName);

    const url = this.idpdataService.devServerURL + "/jobService/testPlan/" + id + "/" + appName + "/" + pipeName;

    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);

    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));


  }

  base64EncodeDecode(uname, pass) {
    const string_new = uname + ":" + pass;

    // Encode the String
    const encodedString = btoa(string_new);
    console.log(encodedString);

    // Decode the String
    // const decodedString = atob(encodedString);
    return encodedString;
  }

  buildIntervalTriggerJobs(data,applicationName,pipelineName,userName): Promise<any> {

    const url = this.idpdataService.devServerURL + "/jobService/trigger/"+applicationName+"/"+pipelineName+"/"+userName+"/triggerInterval";
    const headers = new Headers();
    let cookie;
    console.log(data);
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Authorization", "Bearer " + cookie);
    headers.append("Content-type", "application/json");

    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  getSlaveStatus(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/trigger/slavestatus/" + data;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    console.log(url);
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  getArtifactLatestDetails(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/releaseService/latest/artifact/details/" + data;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    console.log(url);
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  getPipelineAdmins(appName): Promise<any> {
    const url = this.idpdataService.devServerURL + "/trigger/pipelineAdmins?applicationName=" + appName;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  getPipelinePermission(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/pipelinepermission?applicationName=" +
        data.applicationName + "&pipelineName=" +
        data.pipelineName + "&userName=" + data.userName;
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  approveJobs(requestData): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/jobid1/approveJobs";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }
  getSubscriptionPermission(): Promise<any> {
    const url = this.idpdataService.devServerURL + "/subscriptionService/subscriptions";
    const data = {};
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);

    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  getValidatedLicense(): Promise<any> {

    const url = this.idpdataService.subscriptionServerURL + "/licenseService/license/active";
    const data = {};
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);


    const options = new RequestOptions({ headers: headers });
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

    getTRObjects(appName, landscapeName): Promise<any> {
        const url = this.idpdataService.devServerURL + "/sapJobService/rebase/" + appName + "/" + landscapeName;
        const headers = new Headers();
        let cookie;
        if (this._cookieService.get("access_token")) {
            cookie = this._cookieService.get("access_token");
        }
        headers.append("Content-type", "application/json");
        headers.append("Authorization", "Bearer " + cookie);
        const options = new RequestOptions({ headers: headers });
        return this.http
            .get(url, options)
            .toPromise()
            .then(response => response)
            .catch(error => console.log(error));
    }


  createLicense(data): Promise<any> {

    const url = this.idpdataService.subscriptionServerURL + "/licenseService/license/create";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }

    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);


    const options = new RequestOptions({ headers: headers });
    return this.http
        .put(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }


  createOrganization(requestData): Promise<any> {
    const url = this.idpdataService.subscriptionServerURL + "/org/registrationServices/register";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .put(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }


  getExistingOrgNames(): Promise<any> {
    const url = this.idpdataService.subscriptionServerURL + "/org/registrationServices/organizations";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);

    const options = new RequestOptions({ headers: headers });
    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));

  }

  editOrganizationDetails(requestData): Promise<any> {
    const url = this.idpdataService.subscriptionServerURL + "/org/registrationServices/update";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Content-type", "application/json");
    headers.append("Authorization", "Bearer " + cookie);
    const data = requestData;
    const options = new RequestOptions({ headers: headers });
    return this.http
        .put(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(error => console.log(error));
  }

  getCheckmarxData(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/getCheckMarxData";
    const headers = new Headers();
    let cookie;
    headers.append("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    const params =    {   "password": data.checkmarxPwd,
                        "url": data.checkmarxUrl,
                        "username": data.checkmarxUname
                    }

    return this.http
        .post(url, params, options)
        .toPromise()
        .then(response => response)
        .catch(Error => console.log(Error));

  }
  
  getAppmartDetails(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/getAppmartDetails";
    const headers = new Headers();
    let cookie;
    headers.append("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    

    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(Error => console.log(Error));

  }
  
  getRmsToken(data): Promise<any> {
    const url = this.idpdataService.devServerURL + "/jobService/getRmsToken";
    const headers = new Headers();
    let cookie;
    headers.append("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    

    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => response)
        .catch(Error => console.log(Error));

  }
  
  getRmsApplicationName(appname): Promise<any> {
	 
    const url = this.idpdataService.devServerURL + "/jobService/getRmsApplicationName/"+appname;
    const headers = new Headers();
    let cookie;
    headers.append("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    

    return this.http
        .get(url, options)
        .toPromise()
        .then(response => response)
        .catch(Error => console.log(Error));

  }

}