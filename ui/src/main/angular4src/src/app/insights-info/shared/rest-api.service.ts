import { Injectable, OnInit } from "@angular/core";
import { Headers, Http, RequestOptions } from "@angular/http";
import { CookieService } from "ngx-cookie";
import { Router } from "@angular/router";
import {
  HttpEvent,
  HttpClient,
  HttpRequest,
  HttpHeaders,
} from "@angular/common/http";
import { DataApiService } from "./data-api.service";
import { StartupService } from "../../startup.service";
@Injectable({
  providedIn: "root",
})
export class RestApiService {
  startupData: any;
  constructor(
    private http: Http,
    private httpClient: HttpClient,
    private router: Router,
    private _cookieService: CookieService,
    private dataApiService: DataApiService,
    private startupService: StartupService
  ) {
    this.startupData = this.startupService.getData();
    this.notifyURL = this.startupData.notifyURL;
    this.restUrl = this.startupData.insightsresturl;
    this.flaskUrl = this.startupData.flaskUrl;
    this.flaskurl = this.startupData.flaskurl;
    this.flaskCheckinURL = this.startupData.flaskCheckinURL;
    this.mlurl = this.startupData.mlurl;
    this.idpRestUrl = this.startupData.idpresturl;
  }
  notifyURL: any;
  restUrl: any;
  flaskUrl: any;
  flaskurl: any;
  flaskCheckinURL: any;
  mlurl: any;
  idpRestUrl: any;

  getData(): Promise<any> {
    return this.http
      .get("properties")
      .toPromise()
      .then((response) => response)
      .catch((error) => console.log(error));
  }

  getUserAccessApplication() {
    const url =
      this.idpRestUrl + "applicationService/IDP/org/applications/names";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
      cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    const data = "";
    return this.http
      .get(url, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }

  getExceptionrangeDetails(params) {
    const url =
      this.restUrl + "/urlservice/urlservice/insights/exception/rangecount";
    const headers = new Headers();
    const options = new RequestOptions({ headers: headers });
    const data = params;
    return this.http
      .post(url, data, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }

  getApplist(params) {
    const url = this.flaskCheckinURL + "/appinsights";
    const headers = new Headers();
    const options = new RequestOptions({ headers: headers });
    const applist = { applist: params };
    return this.http
      .post(url, applist, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }

  getExceptionCountDetails(params) {
    const url = this.restUrl + "/urlservice/insights/exception/count";
    const headers = new Headers();
    const options = new RequestOptions({ headers: headers });
    const data = params;
    return this.http
      .post(url, data, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }

  getCommitterCheckinDetails(params) {
    const url = this.flaskCheckinURL;
    const headers = new Headers();
    headers.append("Content-type", "application/json");
    const data = params;
    const options = new RequestOptions({ headers: headers });
    return this.http
      .post(url, data)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }

  getELKdetails(params) {
    const url = "http://dummyuser:5010/appserver/" + params;
    const headers = new Headers();
    // console.log("url" + url);
    const options = new RequestOptions({ headers: headers });
    return this.http
      .post(url, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }

  getDiagnosisUrl(params) {
    const url = this.restUrl + "urlservice/diagnosis/" + params;
    const headers = new Headers();
    let cookie;
    const options = new RequestOptions({ headers: headers });
    return this.http
      .get(url, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }

  getCriticalDetails(params) {
    const url = this.mlurl + "correlate";
    const headers = new Headers();
    const options = new RequestOptions({ headers: headers });
    const data = params;
    return this.http
      .post(url, data, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }
  getExceptionDetails(params) {
    const url = this.restUrl + "/urlservice/insights/anomaly/data";
    const headers = new Headers();
    const options = new RequestOptions({ headers: headers });
    const data = params;
    return this.http
      .post(url, data, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }

  getAggregatedLogsDetails(params) {
    const url = this.restUrl + "urlservice/insights/aggregated/logs/" + params;
    const headers = new Headers();
    const options = new RequestOptions({ headers: headers });
    return this.http
      .get(url, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }

  getServerUtilizationStats(params) {
    const url = this.restUrl + "diagnosis/container/utilization";
    const headers = new Headers();
    const options = new RequestOptions({ headers: headers });
    const data = params;
    return this.http
      .post(url, data, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }

  getContainerList(params) {
    const url = this.restUrl + "diagnosis/containerlist/" + params;
    const headers = new Headers();
    const options = new RequestOptions({ headers: headers });
    return this.http
      .get(url, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }

  getNotifications(params) {
    let data1;
    const url = this.notifyURL;
    const headers = new Headers();
    const appName = { appName: params };
    headers.append("Content-type", "application/json");
    const options = new RequestOptions({ headers: headers });
    data1 = this.http
      .post(url, appName, options)
      .toPromise()
      .then((response) => response)
      .catch();
    return data1;
  }
  getApplicationListFromIdp() {
    const url = this.idpRestUrl + "/jobService/IDP/appInsights";
    const headers = new Headers();
    let cookie;
    if (this._cookieService.get("access_token")) {
      cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    const data = "";
    return this.http
      .get(url, options)
      .toPromise()
      .then((response) => response)
      .catch((error) => error);
  }
}
