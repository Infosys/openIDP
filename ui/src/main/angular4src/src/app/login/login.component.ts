/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Component, OnInit } from "@angular/core";
import { CookieService } from "ngx-cookie";
import { TranslateService } from "@ngx-translate/core";
import { IdprestapiService } from "../idprestapi.service";
import { IdpService } from "../idp-service.service";
import { IdpdataService } from "../idpdata.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {
  loginData = {
    grant_type: "password",
    username: "",
    password: "",
    client_id: "idpclient",
  };
  backgroundImage = ""; // "/../assets/images/Login_page_BG2.png";
  username = "";
  password = "";
  pipelineData: any;
  ErrorMsg: any;
  isLoading: boolean;
  // idpBuildInfo:any = {}

  /* Constructor */
  constructor(
    public translate: TranslateService,
    private restApiService: IdprestapiService,
    private idpservice: IdpService,
    private router: Router,
    private IdpdataService: IdpdataService,
    private _cookieService: CookieService
  ) {
    translate.addLangs(["english", "french", "spanish"]);
    translate.setDefaultLang("english");
    const browserLang = translate.getBrowserLang();
    const language = browserLang.match(/english|french/)
      ? browserLang
      : "english";
    this.IdpdataService.language = language;
    translate.use(language);
  }

  ngOnInit() {
    // this.restApiService.getIdpBuildInfo().then((response)=>{
    //   this.idpBuildInfo = response || {};
    // });
    this.idpservice.initMain();
  }

  getDetails() {
    this.idpservice.getDetails();
  }
  getValidatedLicense() {
    this.idpservice.getValidatedLicense();
  }
  getUrl() {
    return "url(assets/images/Login_page_BG.png)";
  }

  setLanguage(data: any) {
    this.IdpdataService.language = data;
  }

  /* Authentication of user while logging in */
  authenticateUser(form: any) {
    this.ErrorMsg = "";
    const username = form.value.username;
    const password = form.value.password;
    if (username !== "" && password !== "") {
      this.loginData = {
        grant_type: "password",
        username: username,
        password: password,
        client_id: "idpclient",
      };
      this.isLoading = true;
      this.ErrorMsg = null;
      this.obtainAccessToken(this.loginData)
        .then(() => {
          this.isLoading = false;
        })
        .catch(() => {
          this.isLoading = false;
        });
    } else {
      this.ErrorMsg = "username or password should not be empty";
    }
  }

  obtainAccessToken(params: any): Promise<any> {
    return this.restApiService.obtainAccessToken(params).then((response) => {
      try {
        if (response) {
          if (!response.json().error) {
            const expireDate = new Date(
              new Date().getTime() + 1000 * response.json().expires_in
            );
            // console.log();
            this.IdpdataService.expireTime = response.json().expires_in;
            this.IdpdataService.organization = response.json().organization;
            // console.log(response.json().expires_in);
            this._cookieService.put(
              "access_token",
              response.json().access_token,
              { expires: expireDate }
            );
            // console.log(this.router);
            const expiretime = 1000 * response.json().expires_in;
            // console.log(response.json().expires_in);
            setTimeout(function () {
              alert("Your session has expired. Please login again");
              window.location.href = "/idpapp/login";
            }, expiretime);
            this.getDetails();
            this.getValidatedLicense();
          } else {
            this.ErrorMsg = "username or password is incorrect";
          }
        } else {
          // console.log("No token");
        }
      } catch (e) {
        alert("failed to login");
        //console.log(e);
      }
    });
  }
}
