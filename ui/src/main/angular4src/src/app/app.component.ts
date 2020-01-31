/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Component, OnInit, HostListener } from "@angular/core";
import { IdprestapiService } from "./idprestapi.service";
import { IdpdataService } from "./idpdata.service";
import { CookieService } from "ngx-cookie";
import {OAuthService, AuthConfig, NullValidationHandler, OAuthEvent, EventType} from 'angular-oauth2-oidc';
import { environment } from "../../src/environments/environment";
import {ActivationEnd, NavigationEnd, Router} from "@angular/router";
import {SsoService} from "./sso/sso.service";

export const noDiscoveryAuthConfig: AuthConfig = {
  clientId:
    'idp',
  redirectUri: 'http://localhost:4200/index.html',
  postLogoutRedirectUri: 'http://localhost:4200',
  loginUrl: 'http://localhost:8180/auth/realms/master/protocol/openid-connect/auth',
  scope: 'openid profile email',
  resource: '',
  rngUrl: '',
  oidc: true,
  requestAccessToken: true,
  options: null,
  issuer: 'http://localhost:8180/auth/realms/master',
  clearHashAfterLogin: true,
  tokenEndpoint: 'http://localhost:8180/auth/realms/master/protocol/openid-connect/token',
  userinfoEndpoint: 'http://localhost:8180/auth/realms/master/protocol/openid-connect/userinfo',
  responseType: 'token',
  showDebugInformation: true,
  silentRefreshRedirectUri: 'http://localhost:4200/silent-refresh.html',
  silentRefreshMessagePrefix: '',
  silentRefreshShowIFrame: false,
  silentRefreshTimeout: 20000,
  dummyClientSecret: null,
  requireHttps: false,
  strictDiscoveryDocumentValidation: false,
  jwks: {
    keys: [
      {
        kty: 'RSA',
        alg: 'RS256',
        use: 'sig',
        kid: '7540561fdb04b89d824a1b7b9e8849873e7cb50e',
        n:
          'sSFZrLIrXzvXBCehdPR10T-mfHWFU5ZtGzW9buI7wT_tJzZ1SRUc2l1NH92kGV9bmWRtDLjWcWFwMG7rbjX25-R-62lD1k15gQiO4bhx7gbV05e36os2vXTs0ypj9GS9y8X_2fYAnxxulMLwz4m24Ejo2tQI43-V-3Tec6cSXe0FjhRaPbGdS8GHPDKkhpJ1NHMZ38vhddIImOfvtVuz3lt_zwjBsAC6Q7PHs2GOm3KtC22DCwXMYSri4QOQcasuvTlZxIQSIksTyuH0T02IH5SJvQZSx46Vfq8BM4JP-zEEjzadoyxQPouRM6TrUeaqNv5B1f1lbH6G0G_r_ddYWQ',
        e: 'AQAB'
      },
      {
        kty: 'RSA',
        alg: 'RS256',
        use: 'sig',
        kid: '778233e8f6f342ea09e867aad25f543adeebf372',
        n:
          '8MMxQ9F7R1zJ57QvLX-HqUlTVLLofCzZ3-lxohJr8ivJDGZoCqll7ZTNO0nGMgnPpIO-3BQLkaNGQDCpnID1vNIjClFFl0E3cN5bDX15uxCQeQDsm25fTlphpy5FkdoHCviswtrsl2KKUPeRlKqCqMjlDO27KuxIwzIPdNSqv4tseZmI-biFt2JlO9htgODrVqaawdm27t9HcWfOK_a5czRFDHWck2-ZwjbCOF9CtF1ggYm11aV0TElExXr5fgjAQdZ1yGmJvir127BRUgyIy5cpyf7VRRf2Cv7whSMoVJr4W3OK0H9vkuFLnlBiBNYQmH_eWy5U4jBfZjBqvA7Oww',
        e: 'AQAB'
      },
      {
        kty: 'RSA',
        alg: 'RS256',
        use: 'sig',
        kid: '8ec17994394464d95b0b3d906326f1cdde8aee64',
        n:
          'w49KfvzGWVXH4vyUxvP29_QTmJfvLp4RPT1WlI6Wo2aNvn6j9vRSLDrK2CnOvvrrlUKvR-8FTcyNi9pRKXDwDhEJcyVFBJVi4PqDh0KIX_dOGYCulr5FUvU0HXQxlMWSHIsJjfGbMMUwM0p09y8KHL-kipiipzn80EpBmrI4Q3t6XOAZJSmbIPaGZJDjyoWWV0TDdVDBMfkqII6tOOB7Ha189AZjz7FHYXR9CIc0Jm6rFy0tVpdHFEG3ptcNQEDQ5ghyMM4PDM4ZmQ5uk3WgHVqnpdmGEfKekLwmYFWgnI-ux_MabltIxr9TE1qubEmebM64rOusHBF0mSbEwggbyw',
        e: 'AQAB'
      }
    ]
  },
  customQueryParams: null,
  silentRefreshIFrameName: 'angular-oauth-oidc-silent-refresh-iframe',
  timeoutFactor: 0.75,
  sessionCheckIntervall: 3000,
  sessionCheckIFrameName: 'angular-oauth-oidc-check-session-iframe',
  sessionChecksEnabled: true,
  disableAtHashCheck: false,
  skipSubjectCheck: false
};

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent implements OnInit {
  profile: any;


  @HostListener('window:beforeunload', ['$event'])
  unloadNotification($event: any) {
   
  }

  constructor(private oauthService: OAuthService,
              private _cookieService: CookieService,
              private idprestapiService: IdprestapiService,
              private idpdataService: IdpdataService,
              private router:Router,
              private ssoService:SsoService) {
    this.router.events.subscribe(event=>{
      if(event instanceof NavigationEnd) {
       
      }
    });
    this.configureWithoutDiscovery();
  }

  private configureWithoutDiscovery() {

    this.idprestapiService.getData()
      .then(response => {
        try {
          if (response) {
         

          this.idpdataService.devServerURL = response.json().idpresturl;
          this.idpdataService.subscriptionServerURL= response.json().idpsubscriptionurl;
          this.idpdataService.IDPDashboardURL = response.json().idpdashboardurl;
          this.idpdataService.IDPLink = response.json().IDPLink;
          this.idpdataService.geUrl = response.json().geUrl;
          this.idpdataService.geFlag = response.json().geFlag;
          this.idpdataService.serverUrl = response.json().tfsServerUrl;
          this.idpdataService.uName = response.json().uName;
          this.idpdataService.pass = response.json().pass;

          //KC properties
          this.idpdataService.keycloakUrl = response.json().keycloakUrl;
          this.idpdataService.keycloakRealm = response.json().keycloakRealm;
          this.idpdataService.keycloakClientId = response.json().keycloakClientId;
         

          
          }
      }
      catch(e){
         console.log(e);
        //alert('Failed in getting the KC properties ');
      }
      });
      noDiscoveryAuthConfig.clientId= this.idpdataService.keycloakClientId;
      noDiscoveryAuthConfig.redirectUri= window.location.href;
      noDiscoveryAuthConfig.postLogoutRedirectUri= window.location.href;
      noDiscoveryAuthConfig.silentRefreshRedirectUri= window.location.href;
      noDiscoveryAuthConfig.issuer= this.idpdataService.keycloakUrl + '/realms/' + this.idpdataService.keycloakRealm;
      noDiscoveryAuthConfig.loginUrl= this.idpdataService.keycloakUrl + '/realms/' + this.idpdataService.keycloakRealm + '/protocol/openid-connect/auth';
      noDiscoveryAuthConfig.userinfoEndpoint= this.idpdataService.keycloakUrl + '/realms/' + this.idpdataService.keycloakRealm + '/protocol/openid-connect/userinfo';
      noDiscoveryAuthConfig.tokenEndpoint= this.idpdataService.keycloakUrl + '/realms/' + this.idpdataService.keycloakRealm + '/protocol/openid-connect/token';

      this.oauthService.configure(noDiscoveryAuthConfig);
    this.oauthService.setupAutomaticSilentRefresh();
          this.oauthService.tokenValidationHandler = new NullValidationHandler();
          this.oauthService.tryLogin();
          this.oauthService.events.subscribe((event:OAuthEvent)=>{
            if(event.type === 'session_terminated'){
              this._cookieService.removeAll();
              this.idpdataService.keycloakToken = "";
              this.oauthService.initImplicitFlow();
            }
          })
    this.oauthService.events.subscribe((event:OAuthEvent)=>{
      if(event.type === 'token_received'){
        this.ssoService.getAuthorizedFromIDPServer();
      }
    })
          
    }

  getProfile() {
    this.idprestapiService.getData()
      .then(response => {
        try {
          if (response) {
            this.idpdataService.profile = response.json().profile;
            this.setProfile();
          }
        } catch (e) {
          alert("Failed to get the Properties Details");
        }
      });
  }
  setProfile() {
    this.profile = this.idpdataService.profile;
    if (this.profile === undefined || this.profile === "idp") {
      require("style-loader!./../styles.css");
    }
  }
  ngOnInit() {
    this.getProfile();
  }
}
