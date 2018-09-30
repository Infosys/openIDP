/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { TestBed, inject } from "@angular/core/testing";

import { SubscriptionService } from "./subscription.service";

describe("SubscriptionService", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        providers: [SubscriptionService]
    });
  });

  it("should be created", inject([SubscriptionService], (service: SubscriptionService) => {
    expect(service).toBeTruthy();
  }));
});
