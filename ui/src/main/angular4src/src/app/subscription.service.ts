/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Injectable } from "@angular/core";
import { CookieService } from "ngx-cookie";
import { Headers, Http, RequestOptions } from "@angular/http";
import "rxjs/add/operator/toPromise";
import { IdpdataService } from "./idpdata.service";
@Injectable()
export class SubscriptionService {
  subscriptionUrl: String = "https://dummyuser:8090/subscription";
  constructor(private http: Http,
    private IdpdataService: IdpdataService,
    private _cookieService: CookieService
    ) {
        // IdpdataService.subscriptionServerURL=this.subscriptionUrl;
    }
  getActiveServices(): Promise<any> {
    const url = this.IdpdataService.subscriptionServerURL + "/licenseService/license/service/active";
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
        .then(response => {
            return response; })
        .catch(Error => console.log(Error));
  }
  getAllSubscriptions(): Promise<any> {
    const url = this.IdpdataService.subscriptionServerURL + "/licenseService/license/active";
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
        .then(response => {
            return response; })
        .catch(Error => console.log(Error));
  }
  validateLicense(licenseKey: String): Promise<any> {
    const url = this.IdpdataService.subscriptionServerURL + "/licenseService/license/validate";
    const headers = new Headers();
    let cookie;
    headers.append("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    const data = licenseKey;
    return this.http
        .post(url, data, options)
        .toPromise()
        .then(response => {
            return response; })
        .catch(Error => console.log(Error));
  }

  addLicense(licenseKey: String): Promise<any> {
    const url = this.IdpdataService.subscriptionServerURL + "/licenseService/license/add";
    const headers = new Headers();
    let cookie;
    headers.append("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers.append("Authorization", "Bearer " + cookie);
    const options = new RequestOptions({ headers: headers });
    const data = licenseKey;
    return this.http
        .put(url, data, options)
        .toPromise()
        .then(response => {
            return response; })
        .catch(Error => console.log(Error));
  }
}
