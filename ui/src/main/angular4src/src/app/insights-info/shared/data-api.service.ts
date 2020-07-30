import { Injectable } from "@angular/core";
import { Http, Response, RequestOptions, Headers } from "@angular/http";
// import {Observable} from 'rxjs';
import { catchError } from "rxjs/operators";
import { Observable, Subject, throwError } from "rxjs";
// import { map } from 'rxjs/operators';
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";

@Injectable({
  providedIn: "root",
})
export class DataApiService {
  private _startupData: any;
  constructor(private http: Http) {}

  // This is the method you want to call at bootstrap
  // Important: It should return a Promise
  load(): Promise<any> {
    this._startupData = null;
    let headers = new Headers({
      Authorization: "Basic " + btoa("idpadmin:idpadmin@123"),
    });
    let options = new RequestOptions({ headers: headers });
    return this.http
      .get("properties", options)
      .map((res: Response) => res.json())
      .toPromise()
      .then((data: any) => (this._startupData = data))
      .catch((err: any) => Promise.resolve());
  }
  getData(): any {
    return this._startupData;
  }

  // constructor() { }
  /* To fetch from properties file */
  restUrl: string;
  /* Logged in user data and subscription data */
  subscriptionData = {
    services: [],
    orgAdmin: "",
    orgName: "",
  };
  /* To activate tabs based on subscribed services */
  activatedTabs = {
    Diagnose: false,
    Insights: false,
  };
  /* For Application Name and Application Drop-down */
  appName = "";
  appNameMenuFlag = true;
  /* For Admin Tab. For Sample Json, CREATE/EDIT Application */
  appJson = {
    applicationname: "string",
    datasource: {
      elasticsearch: {
        index: "string",
        instance: "string",
        password: "string",
        port: "string",
        server: "string",
        userid: "string",
      },
      git: {
        branch: "string",
        password: "string",
        url: "string",
        userid: "string",
      },
      jenkins: {
        dashboardServicePassword: "string",
        dashboardServicePort: "string",
        dashboardServiceSuffix: "string",
        dashboardServiceUser: "string",
        hostServerIp: "string",
        jenkinsPassword: "string",
        jenkinsPort: "string",
        jenkinsSuffix: "string",
        jenkinsUser: "string",
        sonalUrl: "string",
        sonarKey: "string",
        url: "string",
        workspaceLocation: "string",
      },
      jira: {
        password: "string",
        projectkey: "string",
        releaseno: "string",
        url: "string",
        userid: "string",
      },
      kibana: {
        index: "string",
        port: "string",
        server: "string",
      },
    },
    usecasename: "string",
    userlist: [
      {
        role: "string",
        userid: "string",
      },
    ],
  };
  /* Insights Tab */
  anamolyException = [];
  exceptionCount = {
    totalException: 0,
    newException: 0,
    oldException: 0,
  };

  serverUtilization = {
    instanceNumber: "",
    memoryData: {
      memoryUsage: [],
      memoryLimit: [],
      memoryPercentage: [],
      memoryAverage: [],
      processingTime: [],
    },
    cpuData: {
      cpuPercentage: [],
      cpuAverage: [],
      processingTime: [],
    },
  };
}
