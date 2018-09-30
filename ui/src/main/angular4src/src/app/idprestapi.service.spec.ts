/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { TestBed, inject } from "@angular/core/testing";

import { IdprestapiService } from "./idprestapi.service";

describe("IdprestapiService", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        providers: [IdprestapiService]
    });
  });

  xit("should be created", inject([IdprestapiService], (service: IdprestapiService) => {
    expect(service).toBeTruthy();
  }));
});
