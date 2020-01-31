/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { TestBed, inject } from '@angular/core/testing';

import { SsoService } from './sso.service';

describe('SsoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SsoService]
    });
  });

  it('should be created', inject([SsoService], (service: SsoService) => {
    expect(service).toBeTruthy();
  }));
});
