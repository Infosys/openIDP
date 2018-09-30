/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { TestBed, inject } from "@angular/core/testing";
import { IdpService } from "./idp-service.service";

describe("IdpServiceService", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        providers: [IdpService]
    });
  });

  xit("should be created", inject([IdpService], (service: IdpService) => {
    expect(service).toBeTruthy();
  }));
});
