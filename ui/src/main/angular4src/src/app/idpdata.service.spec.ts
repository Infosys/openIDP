/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { TestBed, inject } from "@angular/core/testing";
import { IdpdataService } from "./idpdata.service";

describe("IdpdataService", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        providers: [IdpdataService]
    });
  });

  xit("should be created", inject([IdpdataService], (service: IdpdataService) => {
    expect(service).toBeTruthy();
  }));
});
