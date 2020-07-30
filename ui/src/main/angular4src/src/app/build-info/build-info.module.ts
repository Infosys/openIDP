/**
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.”
 *
 **/
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BuildInfoComponent } from "./build-info.component";
import { buildInfoRouter } from "./build-info.router";
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { HttpModule, Http } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";
import { ParentFormConnectComponent } from "../parent-form-connect/parent-form-connect.component";
import { IdpEncryptionModule } from "../idpEncryption.module";
import { AngularCntrlComponent } from "./angular-cntrl/angular-cntrl.component";
import { AntCtrlComponent } from "./ant-ctrl/ant-ctrl.component";

import { GoCntrlComponent } from "./go-cntrl/go-cntrl.component";

import { MavenCntrlComponent } from "./maven-cntrl/maven-cntrl.component";
import { NodejsCntrlComponent } from "./nodejs-cntrl/nodejs-cntrl.component";
import { PythonCntrlComponent } from "./python-cntrl/python-cntrl.component";

import { BuildInfoSubComponent } from "./build-info-sub/build-info-sub.component";
import { MsbuildCntrlComponent } from "./msbuild-cntrl/msbuild-cntrl.component";

import { JavaGradleCntrlComponent } from "./java-gradle-cntrl/java-gradle-cntrl.component";

import { CollapseModule } from "ngx-bootstrap/collapse";

@NgModule({
  imports: [
    CommonModule,
    buildInfoRouter,
    HttpModule,
    FormsModule,
    AngularMultiSelectModule,
    IdpEncryptionModule,
    TranslateModule,
    CollapseModule,
  ],
  declarations: [
    BuildInfoComponent,
    AngularCntrlComponent,
    AntCtrlComponent,
    BuildInfoSubComponent,
    PythonCntrlComponent,
    GoCntrlComponent,

    MavenCntrlComponent,
    ParentFormConnectComponent,
    MsbuildCntrlComponent,
    NodejsCntrlComponent,

    JavaGradleCntrlComponent,
  ],
})
export class BuildInfoModule {}
