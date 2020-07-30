/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import * as CryptoJS from "crypto-js";
import { IdpdataService } from "./idpdata.service";
import { Injectable } from "@angular/core";

/* tslint:disable:no-eval */

@Injectable()
export class IDPEncryption {
  constructor(private idpdataservice: IdpdataService) {}
  arrayString = [];
  key = CryptoJS.enc.Hex.parse("000102030405060708090b0c0a0d0f0e");
  iv = CryptoJS.enc.Hex.parse("0f0e0d0c0b0a09080706050403020100");

  padString(source): any {
    const paddingChar = " ";
    const size = 16;
    const x = source.length % size;
    const padLength = size - x;
    for (let i = 0; i < padLength; i++) {
      source += paddingChar;
    }
    return source;
  }
  unPadString(source): any {
    const hex = source.toString(); // force conversion
    let str = "";
    for (let i = 0; i < hex.length; i += 2) {
      str += String.fromCharCode(parseInt(hex.substr(i, 2), 16));
    }
    return str;
  }

  encryptAES(plnTxt): any {
    if (plnTxt == undefined || plnTxt == "") {
      return "";
    }
    const padMsg = this.padString(plnTxt);
    const encrypted = CryptoJS.AES.encrypt(padMsg, this.key, {
      iv: this.iv,
      padding: CryptoJS.pad.NoPadding,
      mode: CryptoJS.mode.CBC,
    });
    return encrypted.toString();
  }

  decryptAES(encTxt): any {
    const decrypted = CryptoJS.AES.decrypt(encTxt, this.key, {
      iv: this.iv,
      padding: CryptoJS.pad.NoPadding,
      mode: CryptoJS.mode.CBC,
    });
    const unpadMsg = this.unPadString(decrypted);
    return unpadMsg;
  }
  // encrypt fields for application level details in pipeline config page
  doubleEncryptApplicationDetailsPassword(data): any {
    const passwordAppDatalist = this.idpdataservice.passwordEncryptionList;
    return data;
  }

  doubleEncryptPassword(data): any {
    const passwordDatalist = this.idpdataservice.passwordEncryptionList;

    if (data["code"].hasOwnProperty("scm")) {
      for (const i in data["code"]["scm"]) {
        for (const j in passwordDatalist["code.scm"]) {
          const str = passwordDatalist["code.scm"][j];
          if (this.checkForKey(data["code"]["scm"][i], str)) {
            eval(
              "data.code.scm[" +
                i +
                "]." +
                str +
                " = " +
                "'" +
                this.encryptAES(eval("data.code.scm[" + i + "]." + str)) +
                "'"
            );
          }
        }
      }
    }
    if (data["code"].hasOwnProperty("buildScript")) {
      for (const i in data["code"]["buildScript"]) {
        for (const j in passwordDatalist["code.buildScript"]) {
          const str = passwordDatalist["code.buildScript"][j];
          if (this.checkForKey(data["code"]["buildScript"][i], str)) {
            eval(
              "data.code.buildScript[" +
                i +
                "]." +
                str +
                " = " +
                "'" +
                this.encryptAES(
                  eval("data.code.buildScript[" + i + "]." + str)
                ) +
                "'"
            );
          }
        }
      }
    }

    for (const i in passwordDatalist["buildInfo"]) {
      const str = passwordDatalist["buildInfo"][i];
      if (this.checkForKey(data["buildInfo"], str)) {
        eval(
          "data.buildInfo." +
            str +
            " = " +
            "'" +
            this.encryptAES(eval("data.buildInfo." + str)) +
            "'"
        );
      }
    }
    if (data["buildInfo"].hasOwnProperty("modules")) {
      for (const i in data["buildInfo"]["modules"]) {
        for (const j in passwordDatalist["buildInfo.modules"]) {
          const str = passwordDatalist["buildInfo.modules"][j];
          if (this.checkForKey(data["buildInfo"]["modules"][i], str)) {
            eval(
              "data.buildInfo.modules[" +
                i +
                "]." +
                str +
                " = " +
                "'" +
                this.encryptAES(
                  eval("data.buildInfo.modules[" + i + "]." + str)
                ) +
                "'"
            );
          }
        }
      }
    }
    if (data["deployInfo"].hasOwnProperty("deployEnv")) {
      for (const i in data["deployInfo"]["deployEnv"]) {
        if (data["deployInfo"]["deployEnv"][i].hasOwnProperty("deploySteps")) {
          for (const j in data["deployInfo"]["deployEnv"][i]["deploySteps"]) {
            for (const k in passwordDatalist[
              "deployInfo.deployEnv.deploySteps"
            ]) {
              const str =
                passwordDatalist["deployInfo.deployEnv.deploySteps"][k];
              if (
                this.checkForKey(
                  data["deployInfo"]["deployEnv"][i]["deploySteps"][j],
                  str
                )
              ) {
                eval(
                  "data.deployInfo.deployEnv" +
                    "[" +
                    i +
                    "]" +
                    ".deploySteps[" +
                    j +
                    "]." +
                    str +
                    " = " +
                    "'" +
                    this.encryptAES(
                      eval(
                        "data.deployInfo.deployEnv[" +
                          i +
                          "].deploySteps[" +
                          j +
                          "]." +
                          str
                      )
                    ) +
                    "'"
                );
              }
            }
          }
        }
      }
    }
    if (data["testInfo"].hasOwnProperty("testEnv")) {
      for (const i in data["testInfo"]["testEnv"]) {
        if (data["testInfo"]["testEnv"][i].hasOwnProperty("testSteps")) {
          for (const j in data["testInfo"]["testEnv"][i]["testSteps"]) {
            for (const k in passwordDatalist["testInfo.testEnv.testSteps"]) {
              const str = passwordDatalist["testInfo.testEnv.testSteps"][k];
              if (
                this.checkForKey(
                  data["testInfo"]["testEnv"][i]["testSteps"][j],
                  str
                )
              ) {
                eval(
                  "data.testInfo.testEnv[" +
                    i +
                    "]." +
                    "testSteps[" +
                    j +
                    "]." +
                    str +
                    " = " +
                    "'" +
                    this.encryptAES(
                      eval(
                        "data.testInfo.testEnv[" +
                          i +
                          "]." +
                          "testSteps[" +
                          j +
                          "]." +
                          str
                      )
                    ) +
                    "'"
                );
              }
            }
          }
        }
      }
    }

    return data;
  }
  doubleDecryptPassword(data): any {
    const passwordDatalist = this.idpdataservice.passwordEncryptionList;
    if (data["code"].hasOwnProperty("scm")) {
      for (const i in data["code"]["scm"]) {
        for (const j in passwordDatalist["code.scm"]) {
          const str = passwordDatalist["code.scm"][j];
          if (this.checkForKey(data["code"]["scm"][i], str)) {
            eval(
              "data.code.scm[" +
                i +
                "]." +
                str +
                " = " +
                "'" +
                this.replaceSpecialCharacter(
                  this.decryptAES(eval("data.code.scm[" + i + "]." + str))
                ) +
                "'"
            );
          }
        }
      }
    }

    for (const i in passwordDatalist["buildInfo"]) {
      const str = passwordDatalist["buildInfo"][i];
      if (this.checkForKey(data["buildInfo"], str)) {
        eval(
          "data.buildInfo." +
            str +
            " = " +
            "'" +
            this.replaceSpecialCharacter(
              this.decryptAES(eval("data.buildInfo." + str))
            ) +
            "'"
        );
      }
    }
    if (data["buildInfo"].hasOwnProperty("modules")) {
      for (const i in data["buildInfo"]["modules"]) {
        for (const j in passwordDatalist["buildInfo.modules"]) {
          const str = passwordDatalist["buildInfo.modules"][j];
          if (this.checkForKey(data["buildInfo"]["modules"][i], str)) {
            eval(
              "data.buildInfo.modules[" +
                i +
                "]." +
                str +
                " = " +
                "'" +
                this.replaceSpecialCharacter(
                  this.decryptAES(
                    eval("data.buildInfo.modules[" + i + "]." + str)
                  )
                ) +
                "'"
            );
          }
        }
      }
    }
    if (data["deployInfo"].hasOwnProperty("deployEnv")) {
      for (const i in data["deployInfo"]["deployEnv"]) {
        if (data["deployInfo"]["deployEnv"][i].hasOwnProperty("deploySteps")) {
          for (const j in data["deployInfo"]["deployEnv"][i]["deploySteps"]) {
            for (const k in passwordDatalist[
              "deployInfo.deployEnv.deploySteps"
            ]) {
              const str =
                passwordDatalist["deployInfo.deployEnv.deploySteps"][k];
              if (
                this.checkForKey(
                  data["deployInfo"]["deployEnv"][i]["deploySteps"][j],
                  str
                )
              ) {
                eval(
                  "data.deployInfo.deployEnv" +
                    "[" +
                    i +
                    "]" +
                    ".deploySteps[" +
                    j +
                    "]." +
                    str +
                    " = " +
                    "'" +
                    this.replaceSpecialCharacter(
                      this.decryptAES(
                        eval(
                          "data.deployInfo.deployEnv[" +
                            i +
                            "].deploySteps[" +
                            j +
                            "]." +
                            str
                        )
                      )
                    ) +
                    "'"
                );
              }
            }
          }
        }
      }
    }
    if (data["testInfo"].hasOwnProperty("testEnv")) {
      for (const i in data["testInfo"]["testEnv"]) {
        if (data["testInfo"]["testEnv"][i].hasOwnProperty("testSteps")) {
          for (const j in data["testInfo"]["testEnv"][i]["testSteps"]) {
            for (const k in passwordDatalist["testInfo.testEnv.testSteps"]) {
              const str = passwordDatalist["testInfo.testEnv.testSteps"][k];
              if (
                this.checkForKey(
                  data["testInfo"]["testEnv"][i]["testSteps"][j],
                  str
                )
              ) {
                eval(
                  "data.testInfo.testEnv[" +
                    i +
                    "]." +
                    "testSteps[" +
                    j +
                    "]." +
                    str +
                    " = " +
                    "'" +
                    this.replaceSpecialCharacter(
                      this.decryptAES(
                        eval(
                          "data.testInfo.testEnv[" +
                            i +
                            "]." +
                            "testSteps[" +
                            j +
                            "]." +
                            str
                        )
                      )
                    ) +
                    "'"
                );
              }
            }
          }
        }
      }
    }
    return data;
  }
  checkForKey(data: any, str: any): any {
    this.arrayString = str.split(".");
    let i = 0;
    while (i < this.arrayString.length) {
      if (!data.hasOwnProperty(this.arrayString[i])) {
        return false;
      } else {
        data = data[this.arrayString[i]];
      }
      i++;
    }
    return true;
  }

  replaceSpecialCharacter(data: String) {
    data = data.replace(/ /g, "");
    data = data.replace("\\", "\\\\");
    data = data.replace('"', '\\"');
    data = data.replace("'", "\\'");

    return data;
  }
}
