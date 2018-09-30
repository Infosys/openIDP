/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from "@angular/core";

import {EncryptionDirective} from "./idpEncryption.directive";

@NgModule({
  declarations: [
    EncryptionDirective
  ],
  exports: [
    EncryptionDirective
  ]
})
export class IdpEncryptionModule {}
