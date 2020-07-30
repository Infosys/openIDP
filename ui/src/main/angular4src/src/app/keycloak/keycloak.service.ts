/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";

declare let Keycloak: any;

@Injectable()
export class KeycloakService {
  static auth: any = {};
  static init(urlKC, realmKC, clientIdKC): Promise<any> {
    const keycloakAuth: any = Keycloak({
      url: urlKC,
      realm: realmKC,
      clientId: clientIdKC,
      "ssl-required": "none",
      "public-client": true,
    });

    KeycloakService.auth.loggedIn = false;

    return new Promise((resolve, reject) => {
      keycloakAuth
        .init({ onLoad: "login-required", checkLoginIframe: false })
        .success(() => {
          // alert("Init success");
          // console.log(document.baseURI);
          // console.log(keycloakAuth);
          KeycloakService.auth.loggedIn = true;
          KeycloakService.auth.authz = keycloakAuth;
          KeycloakService.auth.logoutUrl =
            keycloakAuth.authServerUrl +
            "/realms/" +
            realmKC +
            "/protocol/openid-connect/logout?redirect_uri=" +
            document.baseURI;
          resolve();
        })
        .error(() => {
          reject();
        });
    });
  }

  static logout() {
    //console.log(encodedString)console.log("**  LOGOUT");
    KeycloakService.auth.loggedIn = false;
    KeycloakService.auth.authz = null;
    // console.log("urlll " + KeycloakService.auth.logoutUrl);
    window.location.href = KeycloakService.auth.logoutUrl;
    // alert("Logged out");
  }

  static getUsername(): string {
    return KeycloakService.auth.authz.tokenParsed.preferred_username;
  }

  static getFullName(): string {
    return KeycloakService.auth.authz.tokenParsed.name;
  }

  getToken(): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (KeycloakService.auth.authz.token) {
        if (KeycloakService.auth.authz) {
          KeycloakService.auth.authz
            .updateToken(5)
            .success(() => {
              resolve(<string>KeycloakService.auth.authz.token);
            })
            .error(() => {
              reject("Failed to refresh token");
            });
        }
      } else {
        window.location.href = KeycloakService.auth.logoutUrl;
        reject("Not logged in");
      }
    });
  }
  checkLoggedIn() {
    if (KeycloakService.auth.loggedIn === true) {
      // alert("authenticated")
      return true;
    } else {
      // alert(KeycloakService.auth.loggedIn )
      return false;
    }
  }
}
