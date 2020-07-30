/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Injectable } from "@angular/core";
import { Http, Response, RequestOptions, Headers } from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";

@Injectable()
export class StartupService {
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
}
