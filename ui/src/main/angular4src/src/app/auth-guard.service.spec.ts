/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { TestBed, inject } from "@angular/core/testing";
import { AuthGuardService } from "./auth-guard.service";

describe("AuthGuardService", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        providers: [AuthGuardService]
    });
  });

  xit("should be created", inject([AuthGuardService], (service: AuthGuardService) => {
    expect(service).toBeTruthy();
  }));
});
