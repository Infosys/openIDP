/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { TestBed, inject } from "@angular/core/testing";
import { LoginKcService } from "./login-kc.service";

describe("LoginKcService", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        providers: [LoginKcService]
    });
  });

  it("should be created", inject([LoginKcService], (service: LoginKcService) => {
    expect(service).toBeTruthy();
  }));
});
